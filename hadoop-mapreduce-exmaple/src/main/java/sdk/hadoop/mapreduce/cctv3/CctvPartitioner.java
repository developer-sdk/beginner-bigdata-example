package sdk.hadoop.mapreduce.cctv3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * 관리 기관을 기준으로 하는 커스텀 파티셔너
 * 
 * @author User
 *
 */
public class CctvPartitioner extends Partitioner<CctvComparePair, Text> {

	@Override
	public int getPartition(CctvComparePair key, Text value, int numPartitions) {
		return (key.getAdmin().charAt(0)) % numPartitions;
	}

}