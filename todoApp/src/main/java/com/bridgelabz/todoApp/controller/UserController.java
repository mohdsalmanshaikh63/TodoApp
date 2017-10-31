package com.bridgelabz.todoApp.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoApp.entity.Token;
import com.bridgelabz.todoApp.entity.User;
import com.bridgelabz.todoApp.mailUtility.MailUtility;
import com.bridgelabz.todoApp.service.TokenService;
import com.bridgelabz.todoApp.service.UserService;

@RestController
@RequestMapping(value="user")
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

		int created = userService.createUser(user);
		if (created != -1) {
			logger.debug("Created user");

			logger.debug("User id of saved user is" + user.getUserId());

			// prepare the url for sending activation mail

			String scheme = request.getScheme();
			String host = request.getHeader("Host"); // includes server name and server port
			String contextPath = request.getContextPath(); // includes leading forward slash

			String resultPath = scheme + "://" + host + contextPath + "/user/activate/" + user.getUserId();
			logger.debug("Result path: " + resultPath);

			String messageBody = "Click on this link to activate your account " + resultPath;

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
		
		// add activation token to the url later for more security

		if (userService.activate(id)) {
			logger.debug("Activation for id " + id + " successful!");
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			logger.debug("Could not activate user");
			return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}

	@PostMapping(value = "/login")
	public ResponseEntity<Map<String, Token>> login(@RequestBody User user, HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException {

		// send the user to the service
		int uid  = userService.login(user);

		if (uid == -1) {
			logger.debug("Invalid Credentials");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {

			// generate access and refresh tokens
			// save the token in the redis cache
			Token accessToken = tokenService.generateToken("accessToken", uid);
			Token refreshToken = tokenService.generateToken("refreshToken", uid);

			// send the email the user with the token in the url

			// prepare the url for sending activation mail

			String scheme = request.getScheme();
			String host = request.getHeader("Host"); // includes server name and server port
			String contextPath = request.getContextPath(); // includes leading forward slash

			String resultPath = scheme + "://" + host + contextPath + "/user/authenticate/" + uid + "/"
					+ accessToken.getValue();
			logger.debug("Result path: " + resultPath);

			String messageBody = "Click here to login /n" + resultPath;

			// Finally send the mail!			
			MailUtility.sendMail(user.getEmail(), "Token Login", messageBody);
			Map<String, Token> tokenMap = new HashMap<>();
			tokenMap.put("accessToken", accessToken);
			tokenMap.put("refreshToken", refreshToken);
			return new ResponseEntity<Map<String, Token>>(tokenMap, HttpStatus.OK);

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

	@PostMapping(value = "/forgotpassword")
	public ResponseEntity<String> forgotPassword(@RequestBody User user, HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException {

		// first check whether the user exists in the database
		String email = user.getEmail();
		// String email = request.getParameter("email");

		logger.info("Got the email: " + email);

		int uid = userService.checkUser(email);

		// send email if the user exists
		if (uid != -1) {

			// Generate a token and send in the email
			Token token = tokenService.generateToken("forgotToken", uid);

			logger.info("********Token Generated: " + token + "for email: " + email);

			// send the email the user with the token in the url

			// prepare the url for sending activation mail

			String scheme = request.getScheme();
			String host = request.getHeader("Host"); // includes server name and server port
			String contextPath = request.getContextPath(); // includes leading forward slash

			String resultPath = scheme + "://" + host + contextPath + "/user/reset/" + token.getValue();
			logger.info("Result path: " + resultPath);

			String messageBody = "Click here to reset ur password \n" + resultPath;

			// Finally send the mail!
			MailUtility.sendMail(email, "Token Login", messageBody);
			return new ResponseEntity<String>("*******Reset link sent!", HttpStatus.OK);

		}

		return new ResponseEntity<String>("User doesn't exist", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/reset/{forgotToken}")
	public ResponseEntity<String> reset(@PathVariable("forgotToken") String forgotToken) {

		boolean result = tokenService.verifyToken(forgotToken);

		if (result) {

			return new ResponseEntity<String>("Reset Success!", HttpStatus.OK);
		}

		return new ResponseEntity<String>("Authentication failed", HttpStatus.BAD_REQUEST);

	}

}
