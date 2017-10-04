package com.bridgelabz.restApiDemo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.restApiDemo.entity.User;
import com.bridgelabz.restApiDemo.service.UserService;

@RestController(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping(value="/register")
	public ResponseEntity<String> register(@Valid @RequestBody User user) {
		
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
}
