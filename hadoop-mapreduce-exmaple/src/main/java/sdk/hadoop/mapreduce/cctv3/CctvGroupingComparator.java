package sdk.hadoop.mapreduce.cctv3;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * reduce()의 values를 그룹핑하는 커스텀 그룹핑 Comparator
 * 
 * @author User
 *
 */
public class CctvGroupingComparator extends WritableComparator {

	public CctvGroupingComparator() {
		super(CctvComparePair.class, true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		CctvComparePair x = (CctvComparePair) a;
		CctvComparePair y = (CctvComparePair) b;

		return x.getAdmin().compareTo(y.getAdmin());
	}
}