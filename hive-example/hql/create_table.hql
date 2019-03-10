-- 테이블 생성 
CREATE TABLE tbl (
 col1 STRING,
 col2 ARRAY<STRING>, 
 col3 MAP<STRING, STRING>
) ROW FORMAT DELIMITED
   FIELDS TERMINATED BY ','
   COLLECTION ITEMS TERMINATED BY '^'
   MAP KEYS TERMINATED BY ':';
   
-- RegEx 서데 
-- 127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] "GET /apache_pb.gif HTTP/1.0" 200 2326
CREATE TABLE apachelog (
  host      STRING,
  identity  STRING,
  user      STRING,
  time      STRING,
  request   STRING,
  status    STRING,
  size      STRING,
  referer   STRING,
  agent     STRING )
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.RegexSerDe'
WITH SERDEPROPERTIES (
  "input.regex" = "([^]*) ([^]*) ([^]*) (-|\\[^\\]*\\]) ([^ \"]*|\"[^\"]*\") (-|[0-9]*) (-|[0-9]*)(?: ([^ \"]*|\".*\") ([^ \"]*|\".*\"))?"
);

-- JSON 서데 
CREATE TABLE my_table(
  a string, 
  b bigint 
) ROW FORMAT SERDE 'org.apache.hive.hcatalog.data.JsonSerDe'
STORED AS TEXTFILE;

-- CSV 서데 
CREATE TABLE my_table(
  a string, 
  b string
) ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
WITH SERDEPROPERTIES (
   "separatorChar" = "\t",
   "quoteChar"     = "'",
   "escapeChar"    = "\\"
)  
STORED AS TEXTFILE;

-- 저장 포맷을 ORC로 설정하고, ORC 관련 설정정보 전달 
CREATE TABLE tbl (
  col1 STRING
) STORED AS ORC 
TBLPROPERTIES ("orc.compress"="SNAPPY");

-- INPUTFORMAT, OUTPUTFORMAT을 따로 지정하는 것도 가능 
CREATE TABLE tbl1 (
  col1 STRING 
) STORED AS INPUTFORMAT  "com.hadoop.mapred.DeprecatedLzoTextInputFormat"
            OUTPUTFORMAT "org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat";
            
-- CREATE 문과 동일하게 생성하고 AS 문 다음에 SELECT 문 입력 
CREATE TABLE new_key_value_store
   ROW FORMAT SERDE "org.apache.hadoop.hive.serde2.columnar.ColumnarSerDe"
   STORED AS RCFile
   AS
SELECT (key % 1024) new_key, concat(key, value) key_value_pair
  FROM key_value_store
  SORT BY new_key, key_value_pair;