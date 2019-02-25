-- 테이블 생성 
CREATE TABLE buckted_table (
  col1 STRING,
  col2 STRING
) CLUSTERED BY (col2) SORTED BY (col2) INTO 20 BUCKETS
LOCATION '/user/bucketed_table/'
;

-- 데이터 입력을 위한 소스 테이블 
CREATE TABLE source_table (
 col1 ARRAY<STRING>
) ROW FORMAT DELIMITED
   COLLECTION ITEMS TERMINATED BY '\t';

-- 소스 테이블의 데이터 입력 
LOAD DATA LOCAL INPATH './cctv_utf8.csv' INTO TABLE source_table;

-- 버켓팅 테이블에 데이터 입력 
INSERT INTO TABLE buckted_table
SELECT col1[0], col1[3]
  FROM source_table;