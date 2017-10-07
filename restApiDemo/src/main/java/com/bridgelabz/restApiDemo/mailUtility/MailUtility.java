package com.bridgelabz.restApiDemo.mailUtility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.logging.Logger;

public class MailUtility {

	private static Logger logger = Logger.getLogger(MailUtility.class);

	public static void sendMail(String toMail, String subject, String messageBody) throws FileNotFoundException, ClassNotFoundException, IOException {
		logger.debug("Starting TLS");

		EmailInfo emailInfo = EmailCredentialSerializer.getEmailInfo();
		String fromMail = emailInfo.getEmail();
		String password = emailInfo.getPassword();
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		properties.put("mail.smtp.port", "587"); // TLS Port
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

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
			message.setSubject(subject, "UTF-8");
			message.setText(messageBody, "UTF-8");
			message.setSentDate(new Date());

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMail, false));

			logger.debug("Message is ready");

			Transport.send(message);

			logger.debug("Email sent successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	 public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		sendMail("mohdsalmanshaikh63@gmail.com", "test", "This is a test.");
	}

}
