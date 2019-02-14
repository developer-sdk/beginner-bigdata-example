package sdk.hadoop.mapreduce.cctv3;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CctvMain {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		Configuration conf = new Configuration();

		Job job = Job.getInstance(conf, "cctv");
		job.setJarByClass(CctvMain.class);

		// 매퍼 리듀서
		job.setMapperClass(CctvMapper.class);
		job.setReducerClass(CctvReducer.class);

		// 파티셔너, 소트, 그룹핑
		job.setPartitionerClass(CctvPartitioner.class);
		job.setSortComparatorClass(CctvSortComparator.class);
		job.setGroupingComparatorClass(CctvGroupingComparator.class);

		// 맵의 출력
		job.setMapOutputKeyClass(CctvComparePair.class);
		job.setMapOutputValueClass(Text.class);
		// 리듀서의 출력
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}