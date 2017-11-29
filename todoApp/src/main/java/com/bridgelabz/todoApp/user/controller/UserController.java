package com.bridgelabz.todoApp.user.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoApp.customResponse.Message;
import com.bridgelabz.todoApp.token.entity.Token;
import com.bridgelabz.todoApp.user.entity.User;
import com.bridgelabz.todoApp.user.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private UserService userService;

	private static Logger logger = Logger.getLogger(UserController.class);

	@PostMapping(value = "/create")
	public ResponseEntity<Message> create(@RequestBody User user, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException {

		// add code later for checking if email already exists
		logger.info("*********Got user :" + user);

		/*
		 * this is needed when returing void response since angular gives xml parsing
		 * error in firefox
		 */
		response.setContentType("text/plain");

		// prepare the url for sending activation mail
		String scheme = request.getScheme();
		String host = request.getHeader("Host"); // includes server name and server port
		String contextPath = request.getContextPath(); // includes leading forward slash

		String activationLink = scheme + "://" + host + contextPath + "/user/activate/";

		String messageBody = "Click on this link to activate your account " + activationLink;

		try {
			int created = userService.createUser(user, messageBody);
			if (created != -1) {

				logger.debug("*********Created user");
				logger.debug("*********User id of saved user is" + user.getUserId());

				return new ResponseEntity<Message>(HttpStatus.OK);
			} else {
				logger.debug("**********User already exists");
				return new ResponseEntity<Message>(HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			logger.info("*******Error while creating user record");
			logger.debug(e.getMessage());
			return new ResponseEntity<Message>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/activate/{activateToken}")
	public ResponseEntity<Message> activate(@PathVariable("activateToken") String activateToken) {

		// add activation token to the url later for more security

		if (userService.activate(activateToken)) {
			logger.debug("**********Activation with token " + activateToken + " successful!");

			// redirect to login page
			// response.sendRedirect

			return new ResponseEntity<Message>(new Message("Activation successful"), HttpStatus.OK);
		} else {
			logger.debug("Could not activate user");
			return new ResponseEntity<Message>(new Message("Could not activate user"), HttpStatus.UNPROCESSABLE_ENTITY);

			// redirect to appropiate error page later
		}

	}

	@PostMapping(value = "/login")
	public ResponseEntity<Map<String, Token>> login(@RequestBody User user)
			throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException {

		logger.info("******Got the user as: " + user);

		try {
			Map<String, Token> tokenMap = userService.login(user);

			if (tokenMap != null) {
				return new ResponseEntity<>(tokenMap, HttpStatus.OK);

			} else {

				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {

			e.printStackTrace();

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping(value = "/forgotpassword")
	public ResponseEntity<Token> forgotPassword(@RequestBody User user, HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException {

		// first check whether the user exists in the database
		// String email = user.getEmail();
		// String email = request.getParameter("email");

		// logger.info("Got the email: " + email);

		Token token = null;

		try {

			// prepare the url for sending activation mail

			String scheme = request.getScheme();
			String host = request.getHeader("Host");
			// includes server name and server port String contextPath =
			String contextPath = request.getContextPath(); // includes leading forward slash

			String link = scheme + "://" + host + contextPath;

			token = userService.forgotPassword(user, link);

			if (token != null) {
				return new ResponseEntity<>(token, HttpStatus.OK);

			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/reset")
	public ResponseEntity<Message> reset(@RequestHeader("forgotToken") String forgotToken, @RequestBody User user,
			HttpServletResponse response) {

		logger.info("*****Got the forgotToken as " + forgotToken);

		logger.info("********Got the user as " + user);

		response.setContentType("text/plain");

		try {
			boolean result = userService.resetPassword(forgotToken, user);

			if (result) {

				logger.debug("*******Password reset successfully");

				return new ResponseEntity<Message>(new Message("Password reset success"), HttpStatus.OK);
			} else {

				logger.debug("*******Password could not be reset");

				return new ResponseEntity<Message>(new Message("Reset failure"), HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.debug(e);

			return new ResponseEntity<Message>(new Message("Internal Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping(value = "/getUserById")
	public ResponseEntity<User> getUserById(HttpServletRequest request) {

		int userId = (int) request.getAttribute("userId");

		logger.info("********Got the userId as " + userId);
		

		try {
			User user = userService.getUser(userId);

			if (user != null) {

				logger.debug("*******Returning user");

				return new ResponseEntity<User>(user, HttpStatus.OK);
			} else {

				logger.debug("*******Could not get the user");

				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			logger.debug(e);

			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
