# CCTV 분석 
관리 기관명을 기준으로 CCTV의 건수를 확인하는 예제입니다 

## 매퍼(CctvMain.java)
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

## 잡 메인 
1. 매퍼 리듀서를 이용해서 결과를 출력합니다. 