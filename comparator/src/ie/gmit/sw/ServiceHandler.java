package ie.gmit.sw;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ie.gmit.sw.interfaces.Resultator;
import ie.gmit.sw.interfaces.StringService;

public class ServiceHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String remoteHost = null;
	//The current job number the server is processing
	private static long jobNumber = 0;
	//The queue of jobs sent to the server from the clients.
	private BlockingQueue<StringService> inQueue = new ArrayBlockingQueue<StringService>(8);
	
	//The map of outgoing jobs which may or may not be processed yet
	//The keys are the job numbers which increment each time so are always unique.
	//The values are the Resultator jobs
	private Map<Long, Resultator> outQueue = new HashMap<>();

	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER"); //Reads the value from the <context-param> in web.xml
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		//Initialise some request variables with the submitted form info. These are local to this method and thread safe
		String algorithm = req.getParameter("cmbAlgorithm");
		String s = req.getParameter("txtS");
		String t = req.getParameter("txtT");
		String taskNumber = req.getParameter("frmTaskNumber");

		System.out.println("doGet entry.....taskNumber = " + taskNumber);

		out.print("<html><head><title>Distributed Systems Assignment</title>");		
		out.print("</head>");		
		out.print("<body>");
		
		if (taskNumber == null){
			System.out.println("doGet taskNumber was null! Creating a task and adding to in-queue");
			jobNumber++;
			taskNumber = new String("T" + jobNumber);
			
			//Create the job and add it to the in-queue
			Resultator resultator = new ResultatorImpl(taskNumber, s, t, algorithm);
			StringService newJob = new StringServiceImpl(resultator);
			inQueue.add(newJob);
			
			//Immediately print the job number to inform the user some calculation is happening
			out.print("<H1>Processing request for Job#: " + taskNumber 
					+ " In-Queue Size = " + inQueue.size()
					+ "</H1>");
			out.print("<div id=\"r\"></div>");
			
			out.print("<font color=\"#993333\"><b>");
			out.print("RMI Server is located at " + remoteHost);
			out.print("<br>Algorithm: " + algorithm);		
			out.print("<br>String <i>s</i> : " + s);
			out.print("<br>String <i>t</i> : " + t);
			
			//Then poll the front of the inQueue to get the most oldest job by it's reference
			try {
				StringServiceImpl stringServiceImpl = (StringServiceImpl)inQueue.take();
				ResultatorImpl resultatorImpl = (ResultatorImpl) stringServiceImpl.getResultator();
				CompareJob job = resultatorImpl.getJob();
				
				//Kick off the potentially slow 'compare' task. This task executes on a separate thread.
				stringServiceImpl.compare(job.getStringOne(), job.getStringTwo(), job.getAlgorithm());
				//It is placed on the out-queue, but may not yet be complete
				outQueue.put(jobNumber, resultatorImpl);
				
				System.out.println("Job placed in outQueue = " + taskNumber + 
						", s = " + job.getStringOne() +
						", t = " + job.getStringTwo() +
						", algorithm = " + job.getAlgorithm());
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}else{
			System.out.println("taskNumber was NOT null....");
			//This time around we are refreshing, this means it's the same client and they are awaiting their task result
			//Check out-queue for finished job
			Resultator nextOutQueueJob = outQueue.get(jobNumber);
			if(nextOutQueueJob == null)
			{
				//The job does not exist, because it has been complete
				System.out.println("Could not find the job for this number: " + taskNumber);
				out.print("<H1>No more jobs were found!</H1>");
			}
			else if(nextOutQueueJob.isProcessed())
			{
				//Finished processing for this job
				System.out.println("Job is processed! " + taskNumber);
				out.print("<H1>We are done!: " + taskNumber + "</H1>");
				out.print("<H1>Result: " + nextOutQueueJob.getResult() + "</H1>");
				//Remove this job
				outQueue.remove(jobNumber);
			}
			else
			{
				//If the nextOutQueueJob is not null and not yet processed, update the UI to let the user know
				System.out.println("Waited 5, will refresh soon");
				out.print("<H1>Waited 5 seconds...Not done yet: " + taskNumber + "</H1>");
			}
		}
		
		out.print("<form name=\"frmRequestDetails\">");
		out.print("<input name=\"cmbAlgorithm\" type=\"hidden\" value=\"" + algorithm + "\">");
		out.print("<input name=\"txtS\" type=\"hidden\" value=\"" + s + "\">");
		out.print("<input name=\"txtT\" type=\"hidden\" value=\"" + t + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");								
		out.print("</body>");	
		out.print("</html>");	
		
		if(outQueue != null && outQueue.size() > 0)
		{
			//This auto-refreshes the form with the same submission (identified by it's taskNumber) every 5 seconds.
			//This is only done if something remains in the outQueue for processing
			out.print("<script>");
			out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 5000);");
			out.print("</script>");
		}
		else
		{
			//Otherwise create a button to go again
			out.print("<form action=\"/comparator/index.jsp\">");
			out.print("<input type=\"submit\" value=\"Have another go\"/>");
			out.print("</form>");
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
}