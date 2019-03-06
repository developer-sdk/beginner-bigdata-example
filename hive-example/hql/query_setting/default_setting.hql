-- 하이브 실행 엔진 설정 
set hive.execution.engine=tez;
set tez.queue.name=q2;

-- 애플리케이션 마스터 메모리 설정 
set hive.tez.container.size=2048;
set hive.tez.java.opts=-Xmx1600m;

-- 테즈 엔진의 매퍼 개수 설정 
-- 매퍼는 256MB에 하나씩 생성 
set tez.grouping.max-size=256000000;
set tez.grouping.min-size=128000000;

-- 테즈 엔진의 리듀서 개수 설정 
-- 128MB에 하나씩 생성 
set mapred.reduce.tasks=-1;
set hive.exec.reducers.bytes.per.reducer=128000000;

-- 리듀서 10개 고정 
set mapred.reduce.tasks=10;

-- 다이나믹 파티션 설정 
set hive.exec.dynamic.partition.mode=nonstrict;