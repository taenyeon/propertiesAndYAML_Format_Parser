#PropertiesAndYAML Format Parser

### ๐์ฌ์ฉ ๋ฐฉ๋ฒ
properties ๋ yml ํ์ผ๋ค์ resources ํด๋ ๋ด properties ํด๋๋ฅผ ์์ฑํ์ฌ ๋ฃ๊ณ ,  
ํด๋น ์ปจํธ๋กค๋ฌ ์คํ -> ${๋ณ์๋ช:๊ธฐ์กด๊ฐ} ํํ๋ก ์ ์ฅ.  
ํด๋ ๋จ์๋ก ์ฝ๊ธฐ ๋๋ฌธ์ ์ฌ๋ฌ๊ฐ์ ํ์ผ์ ํ๋ฒ์ ์ฒ๋ฆฌ ๊ฐ๋ฅ.
##### ํจํด
properties : datasource.hikari.url -> HIKARI_URL (๋ค์์ 2์๋ฆฌ) -> ์ค๋ณต ๋ฐ์ ๊ฐ๋ฅ  
yml : datasource.hikari.url -> DATASOURCE_HIKARI_URL (๋ชจ๋  ์๋ฆฌ) -> ์ค๋ณต x
###### ํจํด ์์ ๋กญ๊ฒ ๋ณ๊ฒฝ ๊ฐ๋ฅ
### ๐๊ฐ๋ฐ ์ทจ์ง
ํ์ฌ๋ด์์ ๋๊ท๋ชจ ์ด๊ด์ด ๊ณํ๋์๋ค.  
์ด๋ ๋ฌธ์ ์ ์ผ๋ก ๋์จ๊ฒ์ด ์ด 2๊ฐ์ง๊ฐ ์๋ค.
#####1. ํฅํ ๊ด๋ฆฌ๋ฅผ ์๊ฐํ์ฌ MSA ํ๊ฒฝ์์ ์ฌ๊ธฐ์ ๊ธฐ ๋ถ์ฐ๋ properties๋ yml ์ค์  ํ์ผ๋ค์ ํตํฉํด์ผํ๋ค.
#####2. ํ์ฌ ํ๋ก์ ํธ์ GIT์ ๊ฐ์ด ๊ณต์ ํ๋ ๋ฐฉํฅ์ผ๋ก ์ค์  ํ์ผ๋ค์ ์ด๊ด ํ๊ฒฝ์ ๋ง๊ฒ ์ ๋์ ์ผ๋ก ๋ณ๊ฒฝํ  ์ ์์ด์ผํ๋ค.
์ด๋ฅผ ํด๊ฒฐํ๊ธฐ ์ํด์๋ ์ค์  ํ์ผ๋ค์ด AWS,Docker ์ ๊ฐ์ ์ฌ๋ฌ ํ๊ฒฝ์ ๋ฐ๋ผ ๊ฐ์ ์ฃผ์๋ฐ์ ์ ์๋๋ก  
ํด์ผํ๋ฉฐ, ๊ทธ๋ ๊ฒ ํ๊ธฐ์ํด์  ๋ชจ๋  properties๋ yml ํ์ผ์ ๊ฐ์ ์ฃผ์๋ฐ์ ์ ์๋ ํํ๋ก ์์ ํ  ํ์๊ฐ ์์๋ค.  
ํ์ฌ ํ๋ก์ ํธ์๋ ์์ญ๊ฐ์ API ์๋ฒ๊ฐ ์๋ก ํต์ ์ ์ฃผ๊ณ ๋ฐ๊ณ  ์์ผ๋ฉฐ ๊ฐ๋ฐ, ํ์คํธ, ์ค์ฉํ๋ฑ ์ฌ๋ฌ ์ฐ์์ ๋ฐ๋ผ   
ํ๋์ API ์๋ฒ๊ฐ ์ฌ๋ฌ ํํ๋ก ์กด์ฌํ๊ณ  ์๋ค.  
๊ทธ๋ ๊ธฐ๋๋ฌธ์ ์ฌ๋์ด ์ผ์ผ์ด ์์ ํ๋๊ฒ๋ณด๋จ ์๋ํ๋ฅผ ํ๋ฉด ์ถํ์ ๊ณตํต์ผ๋ก ์ฌ์ฉ๋๋ property ์ ๋ํด์๋  
๋์ฒํ๊ธฐ ์ฌ์ธ๊ฒ์ด๋ผ๊ณ  ํ๋จํ์ฌ ๊ฐ๋ฐํ๊ฒ ๋์๋ค.


### ๐์๋ ์๋ฆฌ
YAML์ ๊ฒฝ์ฐ์๋ String๊ณผ Map์ ์กฐํฉ์ผ๋ก ๊น์ด๋ฅผ ๊ตฌ์ฑํ๊ณ  ์๊ธฐ ๋๋ฌธ์, ์ค์  ๊ฐ์ธ String๊ณผ ๊ตฌ์กฐ ํํ์ Map์ ๊ตฌ๋ถํ๋๊ฒ์ด ์ค์ํ๋ค.  
๊ทธ๋์ value ๊ฐ์ ๋ฐ๋ผ ๋ค์๊ณผ ๊ฐ์ด ๋์ํ  ์ ์๋๋ก ์ ๋ฆฌํ๋ค.  

if) Map์ value ๊ฐ์ผ๋ก String์ด ์ฌ ๊ฒฝ์ฐ -> String ๋ณ๊ฒฝ  
if) Map์ value ๊ฐ์ผ๋ก Map์ด ์ฌ ๊ฒฝ์ฐ -> ์ฌ๊ทํจ์๋ฅผ ํตํด, value๊ฐ String ์ผ๋๊น์ง ๋ฐ๋ณต

properties์ ๊ฒฝ์ฐ์๋ YAML๊ณผ ๋ฌ๋ฆฌ ๋ชจ๋  ๊น์ด๋ฅผ '.'์ผ๋ก ํํํ์ฌ ๊ฐ๋จํ Map ํํ๋ก ์ ์ฅํด๋จ๊ธฐ ๋๋ฌธ์ ์กฐ๊ธ ๋ ๊ฐ๋จํ์๋ค.  
๋ถ๊ธฐ๊ฐ ๋ฐ๋ก ํ์์์ด Map์ ๋ชจ๋  ๊ฐ๋ค์ ๋ณ๊ฒฝ๋ง ํด์ฃผ์๋ค.

### ๐๊ฐ์ ์ 
๋ณ์๋ช์ ์ด๋ป๊ฒ ๋ฃ์ด์ฃผ๋๋์ ๋ฐ๋ผ์ ์ฅ๋จ์ ์ด ์กด์ฌํ๊ธฐ ๋๋ฌธ์ ๋ ์ฐ๊ตฌ๊ฐ ํ์ํ ๊ฒ์ผ๋ก ๋ณด์ธ๋ค.  

๋ณ์๋ฅผ ์ง์ ํ๋ ๋ถ๋ถ์์ ๋ชจ๋  ๊น์ด๋ก ๋ณ์๋ช์ ์์ฑํ  ๊ฒฝ์ฐ, ์ค๋ณต๊ฐ์ด ์ ๋ ์๊ธฐ์ง ์๋๋ค๋ ์ฅ์ ์ด ์๋ค.  
๋จ์ ์ผ๋ก๋ ๋ณ์๋ช์ด ๋๋ฌด ๊ธธ์ด์ง๋ค๋ ๋จ์ ์ด ์กด์ฌํ๋ค.  

๋ฐ๋๋ก ์ผ์  ๊น์ด๋ง ๋ณ์๋ช์ผ๋ก ์์ฑํ  ๊ฒฝ์ฐ, ๋ณ์๋ช์ ์งง๊ฒ ๋ง๋ค ์ ์๋ค๋ ์ฅ์ ์ด ์๋ค.  
๋จ์ ์ผ๋ก๋ ์ํฉ์ ๋ฐ๋ผ ์ค๋ณต๊ฐ์ด ์๊ธธ ์ ์๋ ์ฌ์ง๊ฐ ์๋ค.