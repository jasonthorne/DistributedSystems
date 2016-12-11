package ie.gmit.sw.algorithms;

public class Euclidean {
	//https://www.codeproject.com/questions/480279/javapluseuclideanplusdistancepluscode
	public int distance(String s, String t)
    {
        double sum = 0.0;
        for(int i = 0; i < s.length(); i++) 
        {
        	if(t.length() <= s.length())
        	{
        		sum = sum + Math.pow((s.charAt(i)-t.charAt(i)),2.0);
        	}
        }
        return (int)Math.sqrt(sum);
    }
}
