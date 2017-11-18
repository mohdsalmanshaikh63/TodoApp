package com.bridgelabz.todoApp.mailUtility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailUtility {

	private static Logger logger = Logger.getLogger(MailUtility.class);
	
	@Autowired
	private EmailCredentialSerializer emailCredentialSerializer;
	
	Properties properties;
	
	@PostConstruct
	public void  init() {
		
		logger.info("*******INSIDE INITIALIZING MAIL PROPERTIES");
		
		properties = new Properties();
		
		properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		properties.put("mail.smtp.port", "587"); // TLS Port
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

	}

	public void sendMail(Email email) throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException {
		logger.debug("Starting TLS");

		// add relative path to the java class later we have used hardcoded path
		EmailInfo emailInfo = emailCredentialSerializer.getEmailInfo();
		String fromMail = emailInfo.getEmail();
		String password = emailInfo.getPassword();
		
		
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(fromMail, password);
			}
		};

		Session session = Session.getInstance(properties, authenticator);

		try {

			MimeMessage message = new MimeMessage(session);

			// set message headers
			message.addHeader("Content-type", "text/HTMl; charset=UTF-8");
			message.addHeader("format", "flowed");
			message.addHeader("Content-Transfer-Encoding", "8-bit");
			message.setFrom(new InternetAddress(fromMail, "NoReply-SAL"));
			message.setReplyTo(InternetAddress.parse(fromMail, false));
			message.setSubject(email.getSubject(), "UTF-8");
			message.setText(email.getMessage(), "UTF-8");
			message.setSentDate(new Date());

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo(), false));

			logger.debug("Message is ready");

			Transport.send(message);

			logger.debug("Email sent successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	 public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		//sendMail("mohdsalmanshaikh63@gmail.com", "test", "This is a test.");
	}

}
