-- 테이블을 생성하고, 나중에 테이블의 위치를 ALTER 명령으로 지정 
CREATE TABLE employee (
  id         String,
  name       String );
-- ALTER 명령으로 테이블의 로케이션 변경 
ALTER TABLE employee SET LOCATION 'hdfs://127.0.0.1/user/data/';

-- 파티션 추가 
-- 신규 파티션을 추가하면서 LOCATION을 지정 
ALTER TABLE employee ADD PARTITION (yymmdd='20180510') LOCATION 'hdfs://127.0.0.1/user/';

-- 파티션 정보 수정 
-- 기존 파티션의 LOCATION을 수정  
ALTER TABLE employee PARTITION (yymmdd='20180510') SET LOCATION 'hdfs://127.0.0.1/user/';


-- 파티션 삭제 
-- 2019.02.23 일자 파티션 삭제 
ALTER TABLE table DROP PARTITION (yyyymmdd='20190223')

-- 2019.02.23 일 미만의 일자 삭제, (20, 21, 22일 삭제)
ALTER TABLE table DROP PARTITION (yyyymmdd < '20190223')

-- 2019.02.23 일 이하의 일자 삭제, (20, 21, 22, 23일 삭제)
ALTER TABLE table DROP PARTITION (yyyymmdd <= '20190223')

-- 2019.02.23 일 미만의 일자와 27일 초과의 데이터 삭제(20, 21, 22, 28, 29 일 삭제)
ALTER TABLE table DROP PARTITION (yyyymmdd < '20190223'), PARTITION (yyyymmdd > '20190227');