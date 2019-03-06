-- 실행 엔진 설정 
set hive.execution.engine=mr;
set hive.execution.engine=tez;
set hive.execution.engine=spark;

-- 실행 엔진별 커패시티 스케줄러의 큐 이름 설정 
set mapred.job.queue.name=queueName;
set tez.queue.name=queueName;
set spark.job.queue.name=queueName;