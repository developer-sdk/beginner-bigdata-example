-- 테이블 생성 
CREATE EXTERNAL TABLE sample_temp
(
    rawLine                STRING
)
LOCATION "/user/data/txt/";

-- trsnsform(입력 칼럼명) using 파일위치 as (출력 칼럼)
-- 이런 형태로 입력하면 아래와 같은 결과를 확인할 수 있다. 
SELECT TRANSFORM(rawLine) USING "hdfs:///user/custom_mapred.py" AS (type, dt1, dt2)
  FROM sample_temp;