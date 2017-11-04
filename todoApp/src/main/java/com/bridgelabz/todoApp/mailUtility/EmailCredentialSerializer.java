package com.bridgelabz.todoApp.mailUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class EmailCredentialSerializer {
	
	private static Logger logger = Logger.getLogger(EmailCredentialSerializer.class);
	
	private final String credentialFilePath = "/home/bridgeit/SALMAN/GitRepos/EclipseWorkspace/TodoApp/todoApp/myEmail.dat";

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		String workingDirectory = System.getProperty("user.dir");
		logger.info("*****Working directory is "+workingDirectory);
		//serializeCredentials("", "");		
		//EmailInfo emailInfo = getEmailInfo();
		//logger.info(emailInfo);
	}
	
	public void serializeCredentials(String email, String password) throws FileNotFoundException, IOException {
		EmailInfo emailInfo = new EmailInfo(email, password);
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(credentialFilePath))) {
			output.writeObject(emailInfo);
		}
	}
	
	public EmailInfo getEmailInfo() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(credentialFilePath))) {
			System.out.println("Inside getEmailInfo() method");			
			EmailInfo emailInfo = (EmailInfo)input.readObject();
			return emailInfo;
		}
	}

}
