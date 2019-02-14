package sdk.hadoop.mapreduce.cctv3;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CctvMapper extends Mapper<Object, Text, CctvComparePair, Text> {

	private final static Text one = new Text();

	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, CctvComparePair, Text>.Context context)
			throws IOException, InterruptedException {

		String[] strs = value.toString().split("\t");
		String admin = strs[0]; // 관리 기관명 추출
		String purpose = strs[3]; // 설치 목적 추출

		CctvComparePair pair = new CctvComparePair(admin, purpose);
		one.set(purpose);

		context.write(pair, one);
	}
}