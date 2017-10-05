package com.bridgelabz.javaMailApiTest;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.log4j.Logger;

public class TLSEmail {

	private static Logger logger = Logger.getLogger(TLSEmail.class);

	/**
	 * Outgoing Mail (SMTP) Server requires TLS or SSL: smtp.gmail.com (use
	 * authentication) Use Authentication: Yes Port for TLS/STARTTLS: 587
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final String fromEmail = "shaikhuiqbal@gmail.com";
		final String password = "";
		final String toMail = "mohdsalmanshaikh63@gmail.com";

		logger.debug("Starting TLS");

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		properties.put("mail.smtp.port", "587"); // TLS Port
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};

		Session session = Session.getInstance(properties, authenticator);

		EmailUtil.sendMail(session, fromEmail, toMail, "TLSEmail Testing", "TLS Email Testing Body");

	}

}
