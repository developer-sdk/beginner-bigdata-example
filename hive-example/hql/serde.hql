# 클래스가 들어 있는 jar 파일 추가 
ADD JAR ./hiveUDF.jar;

# 테이블 생성 시에 서데 정보 및 프로퍼티 정보 전달 
CREATE TABLE serde_tbl
(
  col1 STRING
  , col2 STRING
) 
ROW FORMAT SERDE 'com.sec.hive.serde.SampleSerDe'
WITH SERDEPROPERTIES ( "field.delim" = "\t" )
;

# 샘플 데이터를 입력 
LOAD DATA LOCAL INPATH './sample.txt' INTO TABLE serde_tbl;

# 샘플 데이터 내용 
$ cat sample.txt
david   23!
cole    3!5
anna    !92

# 데이터 조회 
select * from serde_tbl;
OK
david   23
cole    35
anna    92