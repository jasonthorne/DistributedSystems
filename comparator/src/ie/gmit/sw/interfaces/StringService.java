package ie.gmit.sw.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StringService extends Remote{
	//This represents a remote interface to the string comparison functions which live on the server
	//The StringServiceImpl class implements the compare function defined below
	public Resultator compare(String s, String t, String algo) throws RemoteException; 
}
