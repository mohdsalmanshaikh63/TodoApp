package com.bridgelabz.restApiDemo.mailUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EmailCredentialSerializer {
	
	public static final String credentialFilePath = "/home/bridgeit/SALMAN/GitRepos/EclipseWorkspace/TodoApp/myEmail.dat";

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		serializeCredentials("abc@xyz.com", "password");		
		EmailInfo emailInfo = getEmailInfo();
		System.out.println(emailInfo);
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
			System.out.println("*****Working directory is ");
			EmailInfo emailInfo = (EmailInfo)input.readObject();
			return emailInfo;
		}
	}

}
