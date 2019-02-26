-- 스큐 테이블 생성 
-- col2에 들어오는 값중 1로 들어오는 값만 스큐로 저장 
CREATE TABLE skewed_table (
  col1 STRING,
  col2 STRING
) SKEWED BY (col2) ON ('1')
STORED AS DIRECTORIES
LOCATION '/user/skewed_table/';

-- 소스 테이블의 데이터를 스큐 테이블에 입력 
INSERT INTO TABLE skewed_table
SELECT col1[0], col1[4]
  FROM source_table;