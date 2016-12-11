package ie.gmit.sw;

import java.rmi.RemoteException;

import ie.gmit.sw.interfaces.Resultator;

public class ResultatorImpl implements Resultator {

	private CompareJob compareJob;
	private boolean isProcessed = false;
	private String distanceResult = null;
	public ResultatorImpl(String jobNumber, String one, String two, String algorithm) throws RemoteException{
		this.compareJob = new CompareJob(jobNumber, one, two, algorithm);
	}
	
	public CompareJob getJob(){
		return compareJob;
	}
	
	@Override
	public String getResult() throws RemoteException {
		return distanceResult;
	}

	@Override
	public void setResult(String result) throws RemoteException {
		distanceResult = result;
	}

	@Override
	public boolean isProcessed() throws RemoteException {
		return isProcessed;
	}

	@Override
	public void setProcessed() throws RemoteException {
		isProcessed = true;
	}

}
