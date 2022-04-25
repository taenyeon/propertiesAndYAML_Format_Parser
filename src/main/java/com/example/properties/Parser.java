package com.example.properties;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

@RestController
@Slf4j
public class Parser {
    Set<String> keySet;
    List<String> keyList;

    // properties 파일을 읽어 Map 으로 변환하고 원하는 형태로 수정
    @GetMapping("/prop")
    public void getProperties() throws IOException {
        File dir = new File("properties 폴더 경로 (절대 경로)");
        File[] files = dir.listFiles();
        for (File file : files) {
            keyList = new ArrayList<>();
            keySet = new HashSet<>();
            log.info("[파일] 이름 : {}", file.getName());
            Properties map = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("properties/" + file.getName());
            map.load(inputStream);
            log.info("[Before Parsing] -> {}", map);
            propertiesParser(map);
            log.info("[After Parsing] -> {}", map);
            FileWriter writer = new FileWriter("properties 폴더 경로 (절대 경로)" + file.getName());
            map.store(writer, "test");
            validCheck();
            inputStream.close();
            writer.close();
        }
    }

    // YAML 파일을 읽어 Map 으로 변환하고 원하는 형태로 수정
    @GetMapping("/yml")
    public void getYml() throws IOException {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(true);
        Yaml yaml = new Yaml(dumperOptions);
        File dir = new File("properties 폴더 경로 (절대 경로)");
        File[] files = dir.listFiles();
        for (File file : files) {
            log.info("[파일] 이름 : {}", file.getName());
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("properties/" + file.getName());
//            LinkedHashMap<String, Object> yamlMap = yaml.load(inputStream);
            List<Object> afterYaml = new ArrayList<>();
            Iterable<Object> objects = yaml.loadAll(inputStream);
            while (objects.iterator().hasNext()) {
                keyList = new ArrayList<>();
                keySet = new HashSet<>();
                LinkedHashMap<String, Object> yamlMap = (LinkedHashMap<String, Object>) objects.iterator().next();
                log.info("[Before Parsing] -> {}", yamlMap);
                ymlParser(yamlMap, "");
                log.info("[After Parsing] -> {}", yamlMap);
                afterYaml.add(yamlMap);
                validCheck();
            }
            FileWriter writer = new FileWriter("properties 폴더 경로 (절대 경로)" + file.getName());
//            String dump = yaml.dump(yamlMap);
//            log.info("[Dump] Yml String -> {}", dump);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(afterYaml);
            log.info("[Dump] Yml String -> {}", stringBuilder);
            yaml.dumpAll(afterYaml.iterator(), writer);
//            yaml.dump(yamlMap, writer);
        }
    }

    public void ymlParser(LinkedHashMap<String, Object> yml, String subject) {
        for (String key : yml.keySet()) {
            Object value = yml.get(key);
            log.info("[class] key -> {} || class -> {}", key, value.getClass().getName());
            if (value instanceof LinkedHashMap) {
                log.info("[Continue] key -> {} || value -> {}", key, value);
                if (subject.equals("") || subject.isEmpty()) {
                    ymlParser((LinkedHashMap<String, Object>) value, key);
                } else {
                    ymlParser((LinkedHashMap<String, Object>) value, subject + "_" + key);
                }
            } else {
                // replace param 에 원하는 형태로 저장
                // 예제에서는 ${변수명:기존값} 형태로 저장
                String underBarKey = key.replace("-", "_");
                String underBarSubject = subject.replace("-", "_");
                String replaceKey = underBarSubject.toUpperCase(Locale.ROOT) + "_" + underBarKey.toUpperCase(Locale.ROOT);
                yml.replace(key, "${" + replaceKey + ":" + value + "}");
                log.info("[Replace] key -> {} || value -> {}", key, "${" + underBarSubject.toUpperCase(Locale.ROOT) + "_" + underBarKey.toUpperCase(Locale.ROOT) + ":" + value + "}");
                keyList.add(replaceKey);
                keySet.add(replaceKey);
            }
        }
    }

    public void propertiesParser(Properties properties) {
        for (Object key : properties.keySet()) {
            log.info("[Before Parse] key -> {}", key);
            String[] split = ((String) key).split("\\.");
            String s;
            if (split.length > 1) {
                List<String> strings = Arrays.asList(split);
                s = String.join("_", strings);
            } else {
                s = (String) key;
            }
            String replaceKey = s.replace("-", "_");
            // value 에 원하는 형태로 저장
            // 예제에서는 ${변수명:기존값} 형태로 저장
            String value = "${" + replaceKey.toUpperCase(Locale.ROOT) + ":" + properties.get(key) + "}";
            log.info("[After Parse] key -> {} || value -> {} \n", key, value);
            properties.replace(key, value);
            keyList.add(replaceKey.toUpperCase(Locale.ROOT));
            keySet.add(replaceKey.toUpperCase(Locale.ROOT));
        }
    }

    public void validCheck() {
        boolean isEquals = keyList.size() == keySet.size();
        // Set -> 중복 허용 x 따라서, properties 전체 key 값과 일치하지 않는다면
        // 중복 key 값이 발생했음을 알 수 있다.
        log.warn("[중복체크] {} || keys = {}, keySetSize = {}", isEquals, keyList.size(), keySet.size());
        if (!isEquals) {
            for (String key : keySet) {
                int i = 0;
                for (String key2 : keyList) {
                    if (key.equals(key2)) {
                        i++;
                    }
                }
                if (i > 1) {
                    log.error("[중복 값 발견] 해당 값은 중복 입니다. -> {}, count = {}", key, i);
                }
            }
        }
    }
}