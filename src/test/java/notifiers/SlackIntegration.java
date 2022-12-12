package notifiers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import utils.PropertyFile;

public class SlackIntegration {
	  public static void sendMessageToSlack(String channel, String webhookurl, String message){
	        try {
	            StringBuilder msgbuilder = new StringBuilder();
	            msgbuilder.append(message);
	            Payload payload = Payload.builder().channel(channel).text(msgbuilder.toString()).build();
	            Slack.getInstance().send(webhookurl, payload);

	        } catch (Exception e) {
	            e.getStackTrace();
	        }
	    }

	    public static void sendSlackMsg() {

	        String Msg="TestRun Name: Smoke Execution started by Akshay Gaikwad \n all test cases are passed ";
	        SlackIntegration.sendMessageToSlack("restapi", PropertyFile.getValue("webHook"),Msg);
	    }
}
