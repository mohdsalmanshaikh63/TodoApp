package com.bridgelabz.javaMailApiTest;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class EmailUtil {
	
	private static Logger logger = Logger.getLogger(EmailUtil.class); 

	public static void sendMail(Session session,String fromMail, String toMail, String subject, String messageBody) {
		
		try {
			
			MimeMessage message = new MimeMessage(session);
			
			// set message headers
			message.addHeader("Content-type", "text/HTMl; charset=UTF-8");
			message.addHeader("format", "flowed");
			message.addHeader("Content-Transfer-Encoding", "8-bit");
			message.setFrom(new InternetAddress(fromMail, "NoReply-SAL"));
			message.setReplyTo(InternetAddress.parse(fromMail, false));
			message.setSubject(subject, "UTF-8");
			message.setText(messageBody, "UTF-8");
			message.setSentDate(new Date());
			
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail, false));
			
			logger.debug("Message is ready");
			
			Transport.send(message);
			
			logger.debug("Email sent successfully");
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
