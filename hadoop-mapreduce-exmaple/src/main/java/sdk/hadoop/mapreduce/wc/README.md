# 워드카운트
하둡 워드카운트 에제입니다.

## 매퍼
1. 입력된 문자를 공백을 기준으로 분할하여, <문자, 1>로 씁니다. 

```java
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				context.write(word, one);
			}
```

## 리듀서 
1. 맵리듀스 프레임워크가 리듀서의 입력으로 매퍼의 출력을 정리하여 <문자, 리스트(1, 1, 1)> 형태로 입력해 줍니다. 

```java
Text key, Iterable<IntWritable> values
```

2. 키별 개수를 모두 더하여 문자의 등장 횟수를 세어줍니다. 

```java
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
```
 
## 잡 메인 
잡에 매퍼와 리듀서를 설정하여 작업을 테스트합니다. 

```java
		job.setMapperClass(TokenizerMapper.class);
		job.setReducerClass(IntSumReducer.class);
```
