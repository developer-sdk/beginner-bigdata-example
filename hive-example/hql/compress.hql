-- 압축 여부 선택 
set hive.exec.compress.output=true;
set mapred.output.compression.codec=org.apache.hadoop.io.compress.GzipCodec;

-- table을 읽어서 /user/tables/에  CSV 형태로 압축하여 저장 
INSERT OVERWRITE DIRECTORY 'hdfs:///user/tables/'
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
SELECT *
  FROM table
 WHERE name = 'csv'
;

-- table을 읽어서 csvsample 테이블에 저장 
CREATE TABLE csvsample
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LOCATION '/user/csv/'
AS 
SELECT *
  FROM table
 WHERE name = 'csv'
;
