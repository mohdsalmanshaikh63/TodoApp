package com.bridgelabz.restApiDemo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.restApiDemo.entity.Token;
import com.bridgelabz.restApiDemo.entity.User;
import com.bridgelabz.restApiDemo.mailUtility.MailUtility;
import com.bridgelabz.restApiDemo.service.TokenService;
import com.bridgelabz.restApiDemo.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	private static Logger logger = Logger.getLogger(UserController.class);

	@PostMapping(value = "/create")
	public ResponseEntity<String> create(@RequestBody User user, BindingResult bindingResult,
			HttpServletRequest request) throws FileNotFoundException, ClassNotFoundException, IOException {

		// add code later for checking if email already exists

		boolean created = userService.createUser(user);
		if (created) {
			logger.debug("Created user");

			logger.debug("User id of saved user is" + user.getUserId());

			// prepare the url for sending activation mail

			String scheme = request.getScheme();
			String host = request.getHeader("Host"); // includes server name and server port
			String contextPath = request.getContextPath(); // includes leading forward slash

			String resultPath = scheme + "://" + host + contextPath + "/activate/" + user.getUserId();
			logger.debug("Result path: " + resultPath);

			String messageBody = "Click on this link to activate your account /n" + resultPath;

			// Finally send the mail!
			MailUtility.sendMail(user.getEmail(), "Activate your account", messageBody);

			return new ResponseEntity<String>("Record created", HttpStatus.OK);
		} else {
			logger.debug("Error while creating user record");
			return new ResponseEntity<String>("Record could not be created", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping(value = "/activate/{id}")
	public ResponseEntity<Void> activate(@PathVariable("id") int id) {

		if (userService.activate(id)) {
			logger.debug("Activation for id " + id + " successful!");
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			logger.debug("Could not activate user");
			return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@RequestBody User user, HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException {

		// send the user to the service
		boolean result = userService.login(user);

		if (result == false) {
			logger.debug("Invalid Credentials");
			return new ResponseEntity<String>("Invalid Credentials", HttpStatus.BAD_REQUEST);
		} else {

			// generate the token
			// save the token in the redis cache
			Token token = tokenService.generateToken();

			// send the email the user with the token in the url

			// prepare the url for sending activation mail

			String scheme = request.getScheme();
			String host = request.getHeader("Host"); // includes server name and server port
			String contextPath = request.getContextPath(); // includes leading forward slash

			String resultPath = scheme + "://" + host + contextPath + "/authenticate/" + user.getUserId() + "/"
					+ token.getAccessToken();
			logger.debug("Result path: " + resultPath);

			String messageBody = "Click here to login /n" + resultPath;

			// Finally send the mail!
			String workingDir = System.getProperty("user.dir");
			logger.debug("*******Working directory is: " + workingDir);
			MailUtility.sendMail(user.getEmail(), "Token Login", messageBody);
			return new ResponseEntity<String>("*******Login success!", HttpStatus.OK);

		}

	}

	@GetMapping(value = "/authenticate/{id}/{accessToken}")
	public ResponseEntity<String> authenticate(@PathVariable("id") int id,
			@PathVariable("accessToken") String accessToken) {

		logger.info("*******User with id:" + id + " is being Authenticated");

		boolean result = tokenService.verifyToken(accessToken);

		if (result) {

			return new ResponseEntity<String>("Authentication Success!", HttpStatus.OK);
		}

		return new ResponseEntity<String>("Authentication failed", HttpStatus.BAD_REQUEST);
	}
}
