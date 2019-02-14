package sdk.hadoop.mapreduce.cctv2;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import sdk.hadoop.mapreduce.cctv.CctvMapper;
import sdk.hadoop.mapreduce.cctv.CctvReducer;

/**
 * 관리기관명(키) 기준으로 정렬하여 건수 확인 
 * 
 * @author whitebeard-k
 *
 */
public class CctvMain {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf, "cctv");
		job.setJarByClass(CctvMain.class);

		job.setNumReduceTasks(1);

		job.setMapperClass(CctvMapper.class);
		job.setCombinerClass(CctvReducer.class);
		job.setPartitionerClass(CctvPartitioner.class);
		job.setReducerClass(CctvReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}