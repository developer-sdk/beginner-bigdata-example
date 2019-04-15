package sdk.hive.serde;

import org.apache.hadoop.hive.serde2.SerDeException;
import org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class SampleSerDe extends LazySimpleSerDe {

	public SampleSerDe() throws SerDeException {
		super();
	}

	@Override
	public Object doDeserialize(Writable field) throws SerDeException {
		// 느낌표는 제거
		String temp = field.toString().replaceAll("!", "");
		return super.doDeserialize(new Text(temp));
	}
}