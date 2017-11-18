package com.bridgelabz.todoApp.rabbitMQ;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.todoApp.commonUtils.JSONHelper;
import com.bridgelabz.todoApp.mailUtility.Email;
import com.bridgelabz.todoApp.mailUtility.MailUtility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class EmailConsumer implements MessageListener {
	
	@Autowired
	MailUtility mailUtility;
	
	@Autowired
	JSONHelper jsonHelper;

	@Override
	public void onMessage(Message message) {
		
		// convert message to Email type from JSON
		
		System.out.println("************Message body is "+message.getBody().toString());
		
		Gson gson = new GsonBuilder().create();
		
		//Email email = jsonHelper.convertFromJSON(message.getBody().toString());
		
		Email email = gson.fromJson(message.getBody().toString(), Email.class);
		
		
		//message.getBody();
		try {
			mailUtility.sendMail(email);
		} catch (ClassNotFoundException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		// send email 

	}

}
