package sdk.hive.udtf;

import java.util.ArrayList;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.io.Text;

@Description(name = "string_parse", value = "_FUNC_(delimiter, string) - ")
public class StringParseUDTF extends GenericUDTF {

	private transient final Object[] forwardListObj = new Object[1];
	protected PrimitiveObjectInspector inputOI1;
	protected PrimitiveObjectInspector inputOI2;

	@Override
	public StructObjectInspector initialize(ObjectInspector[] argOIs) throws UDFArgumentException {

		inputOI1 = (PrimitiveObjectInspector) argOIs[1];
		inputOI2 = (PrimitiveObjectInspector) argOIs[1];

		ArrayList<String> fieldNames = new ArrayList<String>();
		ArrayList<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();

		fieldNames.add("col");
		fieldOIs.add(inputOI1);
		fieldOIs.add(inputOI2);

		return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
	}

	@Override
	public void process(Object[] o) throws HiveException {

		String delim = (String) inputOI1.getPrimitiveJavaObject(o[0]);
		String datas = (String) inputOI2.getPrimitiveJavaObject(o[1]);

		for (String str : datas.split(delim)) {
			forwardListObj[0] = new Text(str);
			forward(forwardListObj);
		}
	}

	@Override
	public void close() throws HiveException {
	}
}