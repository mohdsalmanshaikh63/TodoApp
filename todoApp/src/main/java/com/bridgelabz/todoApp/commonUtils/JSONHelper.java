package com.bridgelabz.todoApp.commonUtils;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.bridgelabz.todoApp.mailUtility.Email;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class JSONHelper {

	Gson gson;

	@PostConstruct
	public void init() {
		gson = new GsonBuilder().create();
	}

	// make this class generic later
	public Email convertFromJSON(String message) {
		
		Email email = gson.fromJson(message, Email.class);
		
		return email;
	}

}
