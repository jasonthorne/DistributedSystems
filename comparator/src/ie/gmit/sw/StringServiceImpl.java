package ie.gmit.sw;

import java.rmi.RemoteException;

import ie.gmit.sw.algorithms.DamerauLevenshtein;
import ie.gmit.sw.algorithms.Euclidean;
import ie.gmit.sw.algorithms.HammingDistance;
import ie.gmit.sw.algorithms.Levenshtein;
import ie.gmit.sw.interfaces.Resultator;
import ie.gmit.sw.interfaces.StringService;

public class StringServiceImpl implements StringService
{
	//This is a remote object class, whose compare function handles the computation at the client's request
	//The resultator object reference passed in via the constructor has it's result set here, which the client can retrieve via the getter
	private Resultator resultator;
	public StringServiceImpl(Resultator r) throws RemoteException{
		this.resultator = r;
	}
	
	public Resultator getResultator() throws RemoteException {
		return resultator;
	}

	@Override
	public Resultator compare(final String s, final String t, final String algorithm) throws RemoteException {
		//http://stackoverflow.com/questions/3489543/how-to-call-a-method-with-a-separate-thread-in-java
		Thread t1 = new Thread(new Runnable() {
		     public void run() {
		    	 try {
		 			//Simulate a delay of 10 seconds
		 			Thread.sleep(10000);
		 		} catch (InterruptedException e) {
		 			e.printStackTrace();
		 		}
		 		
		 		if(algorithm.startsWith("Damerau-Levenshtein"))
		 		{
		 			DamerauLevenshtein dlAlgorithm = new DamerauLevenshtein();
		 			try {
						resultator.setResult("Distance = " + dlAlgorithm.distance(s, t));
			 			resultator.setProcessed();
					} catch (RemoteException e) {
						
						e.printStackTrace();
					}
		 		}
		 		else if(algorithm.startsWith("Hamming"))
		 		{
		 			HammingDistance hAlgorithm = new HammingDistance();
		 			try {
						resultator.setResult("Distance = " + hAlgorithm.distance(s, t));
			 			resultator.setProcessed();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
		 		}
		 		else if(algorithm.startsWith("Levenshtein"))
		 		{
		 			Levenshtein lAlgorithm = new Levenshtein();
		 			try {
						resultator.setResult("Distance = " + lAlgorithm.distance(s, t));
			 			resultator.setProcessed();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
		 		}
		 		else if(algorithm.startsWith("Needleman-Wunsch"))
		 		{
		 			
		 			Levenshtein lAlgorithm = new Levenshtein();
		 			try {
						resultator.setResult("Distance = " + lAlgorithm.distance(s, t));
			 			resultator.setProcessed();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
		 		}
		 		else if(algorithm.startsWith("Smith Waterman"))
		 		{
		 			
		 			Levenshtein lAlgorithm = new Levenshtein();
		 			try {
						resultator.setResult("Distance = " + lAlgorithm.distance(s, t));
			 			resultator.setProcessed();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
		 		}
		 		else if(algorithm.startsWith("Euclidean"))
		 		{
		 			Euclidean eAlgorithm = new Euclidean();
		 			try {
						resultator.setResult("Distance = " + eAlgorithm.distance(s, t));
			 			resultator.setProcessed();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
		 		}
		 		else if(algorithm.startsWith("Hirschberg"))
		 		{
		 			
		 			Levenshtein lAlgorithm = new Levenshtein();
		 			try {
						resultator.setResult("Distance = " + lAlgorithm.distance(s, t));
			 			resultator.setProcessed();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
		 		}
		 		else if(algorithm.startsWith("Jaro"))
		 		{
		 			
		 			Levenshtein lAlgorithm = new Levenshtein();
		 			try {
						resultator.setResult("Distance = " + lAlgorithm.distance(s, t));
			 			resultator.setProcessed();
					} catch (RemoteException e) {
						e.printStackTrace();
					}
		 		}
		     }
		});  
		t1.start();
		return resultator;
	}
}
