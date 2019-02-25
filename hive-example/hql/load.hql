-- hdfs의 파일을 읽어서 tbl 테이블에 입력 
LOAD DATA INPATH 'hdfs://127.0.0.1/user/data/sample.csv' INTO TABLE tbl;

-- hdfs의 파일을 읽어서 tbl 테이블의 파티션 yymmdd='20180510' 으로 입력 
LOAD DATA INPATH '/user/data/sample.csv' INTO TABLE tbl PARTITION(yymmdd='20180510');

-- 로컬의 파일을 읽어서 tbl 테이블에 입력 
LOAD DATA LOCAL INPATH './sample.csv' INTO TABLE tbl;

-- test.txt 파일을 sample1 테이블에 로드하고 데이터 조회 
LOAD DATA LOCAL INPATH './test.txt' INTO TABLE sample1;