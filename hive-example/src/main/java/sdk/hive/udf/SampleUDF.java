package sdk.hive.udf;

import java.util.Map;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class SampleUDF extends UDF {

	public Text evaluate(Text text) {
		// 입력받은 문자를 대문자로 반환
		return new Text(text.toString().toUpperCase());
	}

	public int evaluate(int number) {
		// 입력받은 숫자에 1을 더하여 반환
		return number + 1;
	}

	public String evaluate(Map<String, String> map, String key) {
		// 입력받은 키의 밸류가 있으면 반환하고, 없으면 None를 반환
		return map.containsKey(key) ? map.get(key) : "None";
	}
}