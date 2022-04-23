package com.example.properties;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

@RestController
@Slf4j
public class Parser {
    // properties 파일을 읽어 Map 으로 변환하고 원하는 형태로 수정
    @GetMapping("/prop")
    public void getProperties() throws IOException {
        File dir = new File("{resources 내에 properties 경로(폴더)}");
        File[] files = dir.listFiles();
        for (File file : files) {
            log.info("[파일] 이름 : {}",file.getName());
            Properties map = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("properties/" + file.getName());
            map.load(inputStream);
            log.info("[Before Parsing] -> {}", map);
            propertiesParser(map);
            log.info("[After Parsing] -> {}", map);
            FileWriter writer = new FileWriter("{생성할 파일 이름.properties}");
            map.store(writer, "test");
        }
    }
    // YAML 파일을 읽어 Map 으로 변환하고 원하는 형태로 수정
    @GetMapping("/yml")
    public void getYml() throws IOException {
        Yaml yaml = new Yaml();
        File dir = new File("{resources 내에 properties 경로(폴더)}");
        File[] files = dir.listFiles();
        for (File file : files) {
            log.info("[파일] 이름 : {}",file.getName());
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("properties/" + file.getName());
            LinkedHashMap<String, Object> yamlMap = yaml.load(inputStream);
        log.info("[Before Parsing] -> {}", yamlMap);
        ymlParser(yamlMap, "");
        log.info("[After Parsing] -> {}", yamlMap);
        FileWriter writer = new FileWriter("{생성할 파일 이름.yml}");
        String dump = yaml.dump(yamlMap);
        log.info("[Dump] Yml String -> {}", dump);
        yaml.dump(yamlMap, writer);
        }
    }

    public void ymlParser(LinkedHashMap<String, Object> yml, String subject) {
        for (String key : yml.keySet()) {
            Object value = yml.get(key);
            log.info("[class] key -> {} || class -> {}", key, value.getClass().getName());
            if (value instanceof LinkedHashMap) {
                log.info("[Continue] key -> {} || value -> {}", key, value);
                ymlParser((LinkedHashMap<String, Object>) value, key);
            } else {
                // replace param 에 원하는 형태로 저장
                // 예제에서는 ${변수명:기존값} 형태로 저장
                String underBarKey = key.replace("-", "_");
                String underBarSubject = subject.replace("-", "_");
                yml.replace(key, "${" + underBarSubject.toUpperCase(Locale.ROOT) + "_" + underBarKey.toUpperCase(Locale.ROOT) + ":" + value + "}");
                log.info("[Replace] key -> {} || value -> {}", key, "${" + underBarSubject.toUpperCase(Locale.ROOT) + "_" + underBarKey.toUpperCase(Locale.ROOT) + ":" + value + "}");
            }
        }
    }

    public void propertiesParser(Properties properties) {
        Set<String> keySet = new HashSet<>();
        List<String> keyList = new ArrayList<>();
        for (Object key : properties.keySet()) {
            log.info("[Before Parse] key -> {}", (String) key);
            String[] split = ((String) key).split("\\.");
            String s;
            if (split.length > 1) {
                List<String> strings = Arrays.asList(split);
                strings.remove(0);
                s = String.join("_", strings);
            } else {
                s = (String) key;
            }
            String replaceKey = s.replace("-", "_");
            // value 에 원하는 형태로 저장
            // 예제에서는 ${변수명:기존값} 형태로 저장
            String value = "${" + replaceKey.toUpperCase(Locale.ROOT) + ":" + properties.get(key) + "}";
            log.info("[After Parse] key -> {} || value -> {}", key, value);
            properties.replace(key, value);
            keyList.add(replaceKey.toUpperCase(Locale.ROOT));
            keySet.add(replaceKey.toUpperCase(Locale.ROOT));
        }
        boolean isEquals = keyList.size() == keySet.size();
        // Set -> 중복 허용 x 따라서, properties 전체 key 값과 일치하지 않는다면
        // 중복 key 값이 발생했음을 알 수 있다.
        log.info("[중복체크] {} || keys = {}, keySetSize = {}", isEquals, keyList.size(), keySet.size());
        if (!isEquals) {
            for (String key : keySet) {
                int i = 0;
                for (String key2 : keyList) {
                    if (key.equals(key2)) {
                        i++;
                    }
                }
                if (i > 1) {
                    log.info("[중복 값 발견] 해당 값은 중복 입니다. -> {}, count = {}", key, i);
                }
            }
        }
    }
}