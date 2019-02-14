package sdk.hadoop.mapreduce.cctv3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * 정렬하고자 하는 세컨더리 키를 포함하는 복합 클래스
 * 
 * @author User
 *
 */
public class CctvComparePair implements WritableComparable<CctvComparePair> {

	private String admin;
	private String purpose;

	public CctvComparePair() {

	}

	public CctvComparePair(String admin, String road) {
		super();
		this.admin = admin;
		this.purpose = road;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(admin);
		out.writeUTF(purpose);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		admin = in.readUTF();
		purpose = in.readUTF();
	}

	@Override
	public int compareTo(CctvComparePair key) {
		int result = admin.compareTo(key.admin);

		if (result == 0) {
			result = purpose.compareTo(key.purpose);
		}

		return result;

	}

	@Override
	public String toString() {
		return new StringBuffer().append(admin).append("\t").append(purpose).toString();
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String road) {
		this.purpose = road;
	}
}