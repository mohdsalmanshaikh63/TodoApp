package com.bridgelabz.restApiDemo.mailUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EmailCredentialSerializer {
	
	public static final String credentialFilePath = "src/main/resources/myEmail.dat";

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		serializeCredentials("somasingh1701@gmail.com", "soma@123");		
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
			
			EmailInfo emailInfo = (EmailInfo)input.readObject();
			return emailInfo;
		}
	}

}
