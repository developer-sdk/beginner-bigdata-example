package sdk.hadoop.mapreduce.wc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.StringUtils;

/**
 * 워드 카운트2
 * 하둡 맵리듀스 튜토리얼의 워드카운트2 예제
 * 
 * 제네릭옵션파서, 카운터, 분산캐쉬, 설정값의 사용방법을 알아봅니다. 
 *  
 * https://hadoop.apache.org/docs/stable/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html
 * 
 * @author whitebeard-k
 *
 */
public class WordCount2 {

	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

		// 문자의 개수를 세는 카운터 
		static enum CountersEnum {
			INPUT_WORDS
		}

		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		private boolean caseSensitive;	// 대소문자 구분 
		private Set<String> patternsToSkip = new HashSet<String>();	// 문자에서 제거할 기호 

		private Configuration conf;

		// 맵작업의 설정 
		@Override
		public void setup(Context context) throws IOException, InterruptedException {
			conf = context.getConfiguration();
			caseSensitive = conf.getBoolean("wordcount.case.sensitive", true);	// 대소문자 구분 
			if (conf.getBoolean("wordcount.skip.patterns", false)) {	// 스킵 패턴 파일을 분산 캐쉬에서 가져와서 patternsToSkip에 설정 
				URI[] patternsURIs = Job.getInstance(conf).getCacheFiles();
				for (URI patternsURI : patternsURIs) {
					Path patternsPath = new Path(patternsURI.getPath());
					String patternsFileName = patternsPath.getName().toString();
					parseSkipFile(patternsFileName);
				}
			}
		}

		private void parseSkipFile(String fileName) {
			try (BufferedReader fis = new BufferedReader(new FileReader(fileName))) {
				String pattern = null;
				while ((pattern = fis.readLine()) != null) {
					patternsToSkip.add(pattern);
				}
			} catch (IOException ioe) {
				System.err.println("Caught exception while parsing the cached file '" + StringUtils.stringifyException(ioe));
			}
		}

		@Override
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

			String line = (caseSensitive) ? value.toString() : value.toString().toLowerCase();
			
			// patternsToSkip과 일치하는 문자를 제거 
			for (String pattern : patternsToSkip) {
				line = line.replaceAll(pattern, "");
			}
			
			StringTokenizer itr = new StringTokenizer(line);
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				context.write(word, one);
				
				// 카운터에 1을 더함  
				Counter counter = context.getCounter("User Custom Counter", CountersEnum.INPUT_WORDS.toString());
				counter.increment(1);
			}
		}
	}

	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		
		// 옵션 파서를 이용해서 옵션의 개수가 2 또는 4개가 아니면 오류 메시지 출력후 종료
		// -D로 전달되는 옵션은 conf에 설정 
		GenericOptionsParser optionParser = new GenericOptionsParser(conf, args);
		String[] remainingArgs = optionParser.getRemainingArgs();
		if (!(remainingArgs.length != 2 || remainingArgs.length != 4)) {
			System.err.println("Usage: wordcount <in> <out> [-skip skipPatternFile]");
			System.exit(2);
		}
		
		// 맵리듀스 잡 설정 
		Job job = Job.getInstance(conf, "word count 2");
		job.setJarByClass(WordCount2.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// 옵션으로 -skip 으로 전달된 파일 경로를 분산 캐쉬에 추가하고,
		// wordcount.skip.patterns를 true로 설정 
		List<String> otherArgs = new ArrayList<String>();
		for (int i = 0; i < remainingArgs.length; ++i) {
			if ("-skip".equals(remainingArgs[i])) {
				job.addCacheFile(new Path(remainingArgs[++i]).toUri());
				job.getConfiguration().setBoolean("wordcount.skip.patterns", true);
			} else {
				otherArgs.add(remainingArgs[i]);	// 파일경로 
			}
		}
		FileInputFormat.addInputPath(job, new Path(otherArgs.get(0)));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs.get(1)));

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}