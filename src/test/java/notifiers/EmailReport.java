package notifiers;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import utils.PropertyFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class EmailReport {
	 public static void main(String[] args) {

	        // Create object of Property file
	        Properties props = new Properties();

	        // this will set host of server- you can change based on your requirement
	        props.put("mail.smtp.host", "smtp.gmail.com");

	        // set the port of socket factory
	        props.put("mail.smtp.socketFactory.port", "465");

	        // set socket factory
	        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

	        // set the authentication to true
	        props.put("mail.smtp.auth", "true");

	        // set the port of SMTP server
	        props.put("mail.smtp.port", "465");

	        // This will handle the complete authentication
	        Session session = Session.getDefaultInstance(props,

	                new javax.mail.Authenticator() {

	                    protected PasswordAuthentication getPasswordAuthentication() {

	                        return new PasswordAuthentication(PropertyFile.getValue("infoMail"), PropertyFile.getValue("gmailApi"));

	                    }

	                });

	        try {

	            // Create object of MimeMessage class
	            Message message = new MimeMessage(session);

	            // Set the from address
	            message.setFrom(new InternetAddress("akshay.gaikwad@infobeans.com"));

	            // Set the recipient address
	            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("akshaygaikwad362@gmail.com"));

	            // Add the subject link
	            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	            LocalDateTime now = LocalDateTime.now();
	            message.setSubject("Automation Execution Report-"+dtf.format(now));

	            // Create object to add multimedia type content
	            BodyPart messageBodyPart1 = new MimeBodyPart();

	            // Set the body of email
	            messageBodyPart1.setText("Hi Team"+"\n"+"\n"+"Please Find the attachment for Automation Execution Report of rest assured + java test case"+"\n"+"\n"+"\n"+"Thanks and Regards \n "+" Akshay Gaikwad,"+"\n"+"Automation");

	             //Create another object to add another content
	            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

	            // Mention the file which you want to send
	            String filepath = System.getProperty("user.dir")+"\\src\\test\\java\\data\\sampll.jpg";
	            ;
	            String filename ="sampll.PNG";

	                    // Create data source and pass the filename
	            DataSource source = new FileDataSource(filepath);

	            // set the handler
	            messageBodyPart2.setDataHandler(new DataHandler(source));


	            messageBodyPart2.setFileName(filename);

	            // Create object of MimeMultipart class
	            Multipart multipart = new MimeMultipart();

	            // add body part 1
	            multipart.addBodyPart(messageBodyPart2);

	            // add body part 2
	            multipart.addBodyPart(messageBodyPart1);

	            // set the content
	            message.setContent(multipart);

	            // finally send the email
	            Transport.send(message);

	            System.out.println("=====Email Sent=====");

	        } catch (MessagingException e) {

	            throw new RuntimeException(e);

	        }

	    }
}
