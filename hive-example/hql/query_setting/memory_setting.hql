-- MR 메모리 설정 
-- 맵리듀스 애플리케이션 마스터의 메모리 설정 
set yarn.app.mapreduce.am.resource.mb=2048;  
set yarn.app.mapreduce.am.command-opts=-Xmx1600m;

-- 매퍼의 메모리 설정 
set mapreduce.map.memory.mb=2048;  
set mapreduce.map.java.opts=-Xmx1600m;

-- 리듀서의 메모리 설정 
set mapreduce.reduce.memory.mb=2048;  
set mapreduce.reduce.java.opts=-Xmx1600m;


-- TEZ 메모리 설정 
-- TEZ잡을 실행하는 애플리케이션 마스터의 메모리 설정은 다음과 같다. 
set tez.am.resource.memory.mb=2048;  
set tez.am.java.opts=-Xmx1600m;

-- TEZ엔진을 처리하는 컨테이너의 메모리 설정은 다음과 같다. 
set hive.tez.container.size=2048;  
set hive.tez.java.opts=-Xmx1600m;  // container의 80%

-- 출력결과를 소팅해야 할 때 사용하는 메모리 
set tez.runtime.io.sort.mb=800;   // container의 40%

-- 테즈 컨테이너 메모리와 힙사이즈 설정 
set hive.tez.container.size=2048;
set hive.tez.java.opts=-Xmx1600m;

-- 다음의 두값을 기본값보다 작게 설정 
-- 메모리가 70%가 되면 파일로 저장(기본값 0.9)
set tez.runtime.shuffle.memory.limit.percent=0.9;

-- 메모리 대비 25%인 파일은 파일로 저장(기본값 0.25)
set tez.runtime.shuffle.fetch.buffer.percent=0.25;

-- 맵조인에 사용하는 메모리 
set hive.auto.convert.join.noconditionaltask.size=600;  // container의 33%