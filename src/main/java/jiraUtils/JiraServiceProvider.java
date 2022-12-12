package jiraUtils;

import net.rcarz.jiraclient.*;

import java.io.File;

public class JiraServiceProvider {
	 public JiraClient jira;

	    public String project;

	    public JiraServiceProvider(String jiraUrl,String userName,String password,String project){
	        BasicCredentials creds=new BasicCredentials(userName,password);
	        jira=new JiraClient(jiraUrl,creds);
	        this.project=project;
	    }
	    public void createJiraTicket(String issueType,String summary,String description) throws JiraException {
	   try {
	    Issue.FluentCreate fluentCreate = jira.createIssue(project, issueType);
	    fluentCreate.field(Field.SUMMARY, summary);
	    fluentCreate.field(Field.DESCRIPTION, description);
//	    fluentCreate.field(Field.REPORTER, reporterName);
//	    String filepath=System.getProperty("user.dir")+"\\src\\main\\java\\data\\ScreenshotAttachment\\ExecutionReport.PNG";
	    Issue newIssue = fluentCreate.execute();
//	    newIssue.addAttachment(new File(filepath));
	    System.out.println("New Issue created was :" + newIssue);


	    }
	   catch(JiraException e)
	    {
	    e.printStackTrace();
	    }
	    }
}
