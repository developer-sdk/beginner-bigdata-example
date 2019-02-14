package sdk.hadoop.mapreduce.cctv3;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 리듀서의 전체 입력을 정렬하는 기준이 되는 SortComparator
 * 
 * @author User
 *
 */
public class CctvSortComparator extends WritableComparator {

	public CctvSortComparator() {
		super(CctvComparePair.class, true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		CctvComparePair x = (CctvComparePair) a;
		CctvComparePair y = (CctvComparePair) b;

		return x.compareTo(y);
	}
}