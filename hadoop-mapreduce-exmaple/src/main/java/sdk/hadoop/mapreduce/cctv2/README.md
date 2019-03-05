# CCTV 분석 
공공데이터 포털의 전국 CCTV 표준데이터([바로가기](https://www.data.go.kr/dataset/15013094/standard.do;jsessionid=dp1jbk0cCHOXzZB4Z0xxWr7h.node10))를  이용하여 데이터를 분석합니다. 관리 기관명을 기준으로 CCTV의 건수를 확인하는 예제입니다. 데이터는 다음과 같은 형태입니다.   

관리기관명을 기준으로 데이터 건수를 세면서 관리기관명을 기준으로 정렬하여 데이터를 출력합니다. 

## 매퍼(CctvMapper.java)
1. 입력되는 라인을 탭(\t)으로 분리하여, 첫번째 인덱스의 관리기관명을 추출합니다. 

```java
		// 관리 기관명 추출
		String[] strs = value.toString().split("\t");
		word.set(strs[0]);
```

## 리듀서(CctvReducer.java)
1. 관리기관명, List(1, 1, 1, 1)로 입력되는 데이터를 관리기관명을 기준으로 모두 더해서 결과를 출력합니다. 

```java
		int sum = 0;
		for (IntWritable value : values)
			sum += value.get();
		result.set(sum);

```

## 파티셔너(CctvPartitioner)
1. 관리 기관명을 기준으로 정렬을 하기 위해서 파티셔너를 새로 구현합니다. 
	1. 기본 해쉬 파티셔너는 키의 해쉬값을 리듀서의 개수로 나머지 연산하여 파티션을 구분합니다. 
	2. 관리 기관명 기준 파티셔너는 키의 첫번째 문자를 기준으로 파티션을 선택합니다. 

```java
	// 기본 해쉬 파티셔너의 구현 
    public int getPartition(K key, V value, int numReduceTasks) {
        return (key.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
    }

	// 관리 기봔명 기준 파티셔너 구현 
	public int getPartition(Text key, IntWritable value, int numPartitions) {
		return (key.toString().charAt(0)) % numPartitions;
	}
```


## 잡 메인(CctvMain.java)
1. 파티셔너 적용을 위해서 새로 구현한 파티셔너를 등록합니다. 

```java
		job.setNumReduceTasks(1);

		job.setMapperClass(CctvMapper.class);
		job.setCombinerClass(CctvReducer.class);
		job.setPartitionerClass(CctvPartitioner.class);
		job.setReducerClass(CctvReducer.class);
```
