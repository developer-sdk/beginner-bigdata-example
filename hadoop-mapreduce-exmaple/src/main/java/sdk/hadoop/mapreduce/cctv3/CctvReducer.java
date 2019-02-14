package sdk.hadoop.mapreduce.cctv3;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CctvReducer extends Reducer<CctvComparePair, Text, Text, Text> {

	private Text outputKey = new Text();
	private Text outputValue = new Text();

	@Override
	protected void reduce(CctvComparePair key, Iterable<Text> values,
			Reducer<CctvComparePair, Text, Text, Text>.Context context) throws IOException, InterruptedException {

		int uniq = 0;
		int sum = 0;

		String previous = "";
		for (Text value : values) {

			String current = value.toString();

			if (!previous.equals(current)) {
				uniq++;
				previous = current;
			}

			sum++;
		}

		outputKey.set(key.getAdmin());
		outputValue.set(new StringBuffer().append(uniq).append("\t").append(sum).toString());

		context.write(outputKey, outputValue);
	}
}