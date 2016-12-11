package ie.gmit.sw.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Resultator extends Remote{
	//The functions for this interface outline the remote methods the clients need to access (using RMI)
	//The ResultatorImpl class implements this class
	public String getResult() throws RemoteException; 
	public void setResult(String result) throws RemoteException; 
	public boolean isProcessed() throws RemoteException; 
	public void setProcessed() throws RemoteException;
}
