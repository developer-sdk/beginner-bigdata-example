-- 데이터베이스 생성 문법 
CREATE (DATABASE|SCHEMA) [IF NOT EXISTS] database_name
  [COMMENT database_comment]
  [LOCATION hdfs_path]
  [WITH DBPROPERTIES (property_name=property_value, ...)];
  
-- db1 생성 
CREATE DATABASE db1;
 
-- db2 생성 
CREATE DATABASE IF NOT EXISTS db2;
   
-- db3 생성 
CREATE DATABASE IF NOT EXISTS db3
COMMENT "test database"
LOCATION "/user/shs/sample_database/"
WITH DBPROPERTIES (
  'key1' = 'value1',
  'key2' = 'value2'
);