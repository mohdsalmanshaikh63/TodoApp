package com.bridgelabz.todoApp.user.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
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

import com.bridgelabz.todoApp.mailUtility.Email;
import com.bridgelabz.todoApp.mailUtility.MailUtility;
import com.bridgelabz.todoApp.token.entity.Token;
import com.bridgelabz.todoApp.token.service.TokenService;
import com.bridgelabz.todoApp.user.entity.User;
import com.bridgelabz.todoApp.user.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private MailUtility mailUtility;

	private static Logger logger = Logger.getLogger(UserController.class);

	@PostMapping(value = "/create")
	public ResponseEntity<Void> create(@RequestBody User user, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response)
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

				return new ResponseEntity<Void>(HttpStatus.OK);
			} else {
				logger.debug("**********User already exists");
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			logger.info("*******Error while creating user record");
			logger.debug(e.getMessage());
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/activate/{activateToken}")
	public ResponseEntity<Void> activate(@PathVariable("activateToken") String activateToken) {

		// add activation token to the url later for more security

		if (userService.activate(activateToken)) {
			logger.debug("**********Activation with token " + activateToken + " successful!");

			// redirect to login page
			// response.sendRedirect

			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			logger.debug("Could not activate user");
			return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);

			// redirect to appropiate error page later
		}

	}

	@PostMapping(value = "/login")

	public ResponseEntity<Map<String, Token>> login(@RequestBody User user, HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException {

		logger.info("******Got the user as: " + user);

		// send the user to the service
		int uid = userService.login(user);

		if (uid == -1) {
			logger.debug("******Invalid Credentials");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {

			// generate access and refresh tokens
			// save the token in the redis cache
			Token accessToken = tokenService.generateToken("accessToken", uid);
			Token refreshToken = tokenService.generateToken("refreshToken", uid);

			Map<String, Token> tokenMap = new HashMap<>();
			tokenMap.put("accessToken", accessToken);
			tokenMap.put("refreshToken", refreshToken);
			return new ResponseEntity<Map<String, Token>>(tokenMap, HttpStatus.OK);

		}

	}

	@PostMapping(value = "/forgotpassword")
	public ResponseEntity<Token> forgotPassword(@RequestBody User user, HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException {

		// first check whether the user exists in the database
		String email = user.getEmail();
		// String email = request.getParameter("email");

		logger.info("Got the email: " + email);

		try {
			int uid = userService.checkUser(email);

			// send email if the user exists
			if (uid != -1) {

				// Generate a token and send in the email
				Token token = tokenService.generateToken("forgotToken", uid);

				logger.info("********Token Generated: " + token + "for email: " + email);

				// send the email the user with the token in the url

				// prepare the url for sending activation mail

				String scheme = request.getScheme();
				String host = request.getHeader("Host");
				// includes server name and server port String contextPath =
				String contextPath = request.getContextPath(); // includes leading forward slash

				String resultPath = scheme + "://" + host + contextPath + "/#/resetpassword/" + token.getValue();

				String messageBody = "Click here to reset ur password " + resultPath;

				// Finally send the mail!
				mailUtility.sendMail(new Email(email, "Token Login", messageBody));
				return new ResponseEntity<>(token, HttpStatus.OK);

			}

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/reset")
	public ResponseEntity<Void> reset(@RequestHeader("forgotToken") String forgotToken, @RequestBody User user,
			HttpServletResponse response) {

		logger.info("*****Got the forgotToken as " + forgotToken);

		logger.info("********Got the user as " + user);

		response.setContentType("text/plain");

		try {
			int userId = tokenService.verifyToken(forgotToken);

			if (userId != -1) {

				userService.changePassword(userId, user);

				logger.debug("*******Password reset successfully");

				return new ResponseEntity<>(HttpStatus.OK);
			}

			logger.debug("*******Password could not be reset");

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.debug(e);

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
