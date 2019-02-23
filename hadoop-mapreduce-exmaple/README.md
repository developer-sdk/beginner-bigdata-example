# 맵리듀스 예제 
맵리듀스를 이용한 예제를 등록하는 프로젝트 입니다. 

+ 하둡 MapReduce의 워드 카운트 예제([바로가기](https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html))  
	+ 워드 카운트 
		+ 맵과 리듀스의 동작 방식 확인 
	+ 워드 카운트2
		+ setup 함수를 이용한 설정 
		+ 카운터 사용법 
		+ 분산 캐쉬 사용법 
		+ 옵션 파서 사용법 
+ 전국 CCTV 표준데이터([바로가기](https://www.data.go.kr/dataset/15013094/standard.do;jsessionid=dp1jbk0cCHOXzZB4Z0xxWr7h.node10))를 이용한 작업예제 
	+ 관리 기관명 기준으로 건수 확인 
		+ 맵과 리듀스의 동작 방식 확인 
	+ 관리 기관명(키) 기준으로 정렬하여 건수 확인
		+ 파티셔너를 이용한 정렬 사용 
	+ 관리기관, 설치목적 기준으로 정렬하여 건수 확인
		+ 세컨더리 소트 사용법 확인 