-- 테이블을 생성하고, 나중에 테이블의 위치를 ALTER 명령으로 지정 
CREATE TABLE employee (
  id         String,
  name       String );
-- ALTER 명령으로 테이블의 로케이션 변경 
ALTER TABLE employee SET LOCATION 'hdfs://127.0.0.1/user/data/';

-- 신규 파티션을 추가하면서 LOCATION을 지정 
ALTER TABLE employee ADD PARTITION (yymmdd='20180510') LOCATION 'hdfs://127.0.0.1/user/';

-- 기존 파티션의 LOCATION을 수정  
ALTER TABLE employee PARTITION (yymmdd='20180510') SET LOCATION 'hdfs://127.0.0.1/user/';
