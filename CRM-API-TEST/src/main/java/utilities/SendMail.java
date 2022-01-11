package utilities;


import java.io.File;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class SendMail
{

	public static void sendEmail(String message, String subject, String to, String from) {
		System.out.println( "preparing to send message..." );
		//variavle for gmail 	
	String host="smtp.gmail.com";
	
	//get the system properties
	Properties properties = System.getProperties();
	System.out.println("PROPERTIES "+properties);
	
	
	//setting impotant information to properties object
	
	//host set
	properties.put("mail.smtp.host", host);
	properties.put("mail.smtp.host.port", "465");
	properties.put("mail.smtp.ssl.enable", "true");
	properties.put("mail.smtp.auth", "true");
	
	//step 1: get the session object...
	
	Session session=Session.getInstance(properties, new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			
			return new PasswordAuthentication("pr0450956@gmail.com", "Priya@@64");
			
		}
		
		
		
	});
	
	session.setDebug(true);
	//step 2: compose the message[text,multi media]
	
	MimeMessage m = new MimeMessage(session);
	try {
	//from email
	m.setFrom(from);
	
	//addting recipient to message
	m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	
	//adding subject to message
	m.setSubject(subject);
	
	//add text to message
	m.setText(message);
	File attachmentFile = new File(System.getProperty("user.dir") + "/target/xlsx-report/report.xlsx");
	MimeBodyPart mbp2 = new MimeBodyPart();
	mbp2.attachFile(attachmentFile);
	MimeMultipart mp = new MimeMultipart();
//	mp.addBodyPart(mbp1);
	mp.addBodyPart(mbp2);
	//step 3: send the message using trasport class
	m.setContent(mp);
	Transport.send(m);
	
	System.out.println("sent success..............");
	
	
	}catch (Exception e) {
		e.printStackTrace();
	}
}
}