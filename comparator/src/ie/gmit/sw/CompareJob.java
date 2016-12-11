package ie.gmit.sw;

public class CompareJob {
	//A model class representing one string comparison job
	
	private String mTaskNumber;
	private String mStringOne;
	private String mStringTwo;
	private String mAlgorithm;
	
	public CompareJob(String taskNumber, String stringOne, String stringTwo, String algorithm) {
		this.mTaskNumber = taskNumber;
		this.mStringOne = stringOne;
		this.mStringTwo = stringTwo;
		this.mAlgorithm = algorithm;
	}
	public String getTaskNumber() {
		return mTaskNumber;
	}
	public String getStringOne() {
		return mStringOne;
	}
	public String getStringTwo() {
		return mStringTwo;
	}
	public String getAlgorithm() {
		return mAlgorithm;
	}

}
