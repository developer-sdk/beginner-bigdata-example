package sdk.hive.udaf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.typeinfo.PrimitiveTypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

/**
 * String, int 를 입력받아서 합계를 반환
 * 
 * @author User
 *
 */
public class SumInt extends AbstractGenericUDAFResolver {

	@Override
	public GenericUDAFEvaluator getEvaluator(TypeInfo[] info) throws SemanticException {

		// 파라미터는 하나만 받음
		if (info.length != 1) {
			throw new UDFArgumentTypeException(info.length - 1, "Exactly one argument is expected.");
		}

		// 파라미터의 카테고리가 프리미티브 타입이 아니면 예외 처리
		if (info[0].getCategory() != ObjectInspector.Category.PRIMITIVE) {
			throw new UDFArgumentTypeException(0, "Only primitive type arguments are accepted but "
					+ info[0].getTypeName() + " was passed as parameter 1.");
		}

		// 전달된 파라미터의 타입이 스트링이면 SumStringEvaluator, 아니면 SumIntEvaluator 처리
		PrimitiveCategory category = ((PrimitiveTypeInfo) info[0]).getPrimitiveCategory();

		if (category == PrimitiveCategory.STRING || category == PrimitiveCategory.INT) {
			return new SumEvalutor();
		} else {
			throw new UDFArgumentTypeException(0, "Only string, int type arguments are accepted but "
					+ info[0].getTypeName() + " was passed as parameter 1.");
		}
	}

	@SuppressWarnings("deprecation")
	public static class SumEvalutor extends GenericUDAFEvaluator {

		protected PrimitiveObjectInspector inputOI;

		@Override
		public ObjectInspector init(Mode m, ObjectInspector[] parameters) throws HiveException {
			super.init(m, parameters);

			inputOI = (PrimitiveObjectInspector) parameters[0];
			return PrimitiveObjectInspectorFactory.javaIntObjectInspector;
		}

		static class SumAggregationBuffer implements AggregationBuffer {
			int sum;
		}

		@Override
		public AggregationBuffer getNewAggregationBuffer() throws HiveException {
			SumAggregationBuffer sum = new SumAggregationBuffer();
			sum.sum = 0;
			return sum;
		}

		@Override
		public void reset(AggregationBuffer agg) throws HiveException {
			((SumAggregationBuffer) agg).sum = 0;
		}

		@Override
		public void iterate(AggregationBuffer agg, Object[] parameters) throws HiveException {
			((SumAggregationBuffer) agg).sum += getInt(parameters[0]);
		}

		@Override
		public Object terminatePartial(AggregationBuffer agg) throws HiveException {
			return ((SumAggregationBuffer) agg).sum;
		}

		@Override
		public void merge(AggregationBuffer agg, Object partial) throws HiveException {
			((SumAggregationBuffer) agg).sum += getInt(partial);
		}

		@Override
		public Object terminate(AggregationBuffer agg) throws HiveException {
			return ((SumAggregationBuffer) agg).sum;
		}

		public int getInt(Object strObject) {
			return PrimitiveObjectInspectorUtils.getInt(strObject, inputOI);
		}
	}
}