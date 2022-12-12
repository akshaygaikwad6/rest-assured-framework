package listenerPackage;

import org.testng.ITestContext ;

import java.util.Properties;

import org.apache.commons.lang.exception.ExceptionUtils;	
import org.testng.ITestListener ;		
import org.testng.ITestResult ;

import jiraUtils.JiraPolicy;
import jiraUtils.JiraServiceProvider;
import net.rcarz.jiraclient.JiraException;
import notifiers.SlackIntegration;
import utils.PropertyFile;	

public class ListenerTest implements ITestListener {
	

    @Override		
    public void onFinish(ITestContext Result) 					
    {		
    	SlackIntegration.sendSlackMsg();		
    }		

    @Override		
    public void onStart(ITestContext Result)					
    {		
            		
    }		

    @Override		
    public void onTestFailedButWithinSuccessPercentage(ITestResult Result)					
    {		
    		
    }		

    // When Test case get failed, this method is called.		
    @Override		
    public void onTestFailure(ITestResult Result) 					
    {		
    System.out.println("The name of the testcase failed is :"+Result.getName());	
    JiraPolicy jiraPolicy=Result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraPolicy.class);
    boolean isTicketReady=jiraPolicy.logTicketReady();
    if(isTicketReady) {
        System.out.println("Is Ticket Ready for Jira  " + isTicketReady);
        JiraServiceProvider jiraSp=new JiraServiceProvider(PropertyFile.getValue("jiraUrl"),PropertyFile.getValue("jiraEmail"),PropertyFile.getValue("jiraApi"),"RES");
        String issueSummary=Result.getMethod().getConstructorOrMethod().getMethod().getName()+": failed due to exception";
        String description=Result.getThrowable().getMessage()+"\n";
        description.concat(ExceptionUtils.getFullStackTrace(Result.getThrowable()));
        try {
            jiraSp.createJiraTicket("Bug",issueSummary,description);
        } catch (JiraException e) {
            e.printStackTrace();
        }
    }
    }		

    // When Test case get Skipped, this method is called.		
    @Override		
    public void onTestSkipped(ITestResult Result)					
    {		
    System.out.println("The name of the testcase Skipped is :"+Result.getName());					
    }		

    // When Test case get Started, this method is called.		
    @Override		
    public void onTestStart(ITestResult Result)					
    {		
    System.out.println(Result.getName()+" test case started");					
    }		

    // When Test case get passed, this method is called.		
    @Override		
    public void onTestSuccess(ITestResult Result)					
    {		
    System.out.println("The name of the testcase passed is :"+Result.getName());
    
    }		


}
