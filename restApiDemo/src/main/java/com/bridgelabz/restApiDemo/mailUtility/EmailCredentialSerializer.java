package com.bridgelabz.restApiDemo.mailUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class EmailCredentialSerializer {
	
	public static Logger logger = Logger.getLogger(EmailCredentialSerializer.class);
	
	public static final String credentialFilePath = "/home/salman/Salman/GitRepos/EclipseWorkspace/TodoApp/restApiDemo/src/main/resources/myEmail.dat";

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		String workingDirectory = System.getProperty("user.dir");
		logger.info("*****Working directory is "+workingDirectory);
		serializeCredentials("shaikhuiqbal@gmail.com", "786Start!");		
		EmailInfo emailInfo = getEmailInfo();
		logger.info(emailInfo);
	}
	
	public static void serializeCredentials(String email, String password) throws FileNotFoundException, IOException {
		EmailInfo emailInfo = new EmailInfo(email, password);
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(credentialFilePath))) {
			output.writeObject(emailInfo);
		}
	}
	
	public static EmailInfo getEmailInfo() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(credentialFilePath))) {
			System.out.println("Inside getEmailInfo() method");			
			EmailInfo emailInfo = (EmailInfo)input.readObject();
			return emailInfo;
		}
	}

}
