package com.bridgelabz.todoApp.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoApp.entity.Token;
import com.bridgelabz.todoApp.entity.User;
import com.bridgelabz.todoApp.mailUtility.MailUtility;
import com.bridgelabz.todoApp.service.TokenService;
import com.bridgelabz.todoApp.service.UserService;

@RestController
@CrossOrigin(origins="http://localhost:8000")
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
	public ResponseEntity<String> create(@RequestBody User user, BindingResult bindingResult,
			HttpServletRequest request)
			throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException {

		// add code later for checking if email already exists
		logger.info("*********Got user :" + user);

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

				return new ResponseEntity<String>("Record created", HttpStatus.OK);
			} else {
				logger.debug("**********User already exists");
				return new ResponseEntity<String>("User already exists", HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			logger.info("*******Error while creating user record");
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("Error while creating user record", HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<String> forgotPassword(@RequestBody User user, HttpServletRequest request)
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
				String host = request.getHeader("Host"); // includes server name and server port
				String contextPath = request.getContextPath(); // includes leading forward slash

				String resultPath = scheme + "://" + host + contextPath + "/user/reset/" + token.getValue();
				logger.info("Result path: " + resultPath);

				String messageBody = "Click here to reset ur password " + resultPath;

				// Finally send the mail!
				mailUtility.sendMail(email, "Token Login", messageBody);
				return new ResponseEntity<String>("*******Reset link sent!", HttpStatus.OK);

			}

			return new ResponseEntity<String>("*******User doesn't exist", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<String>("*******Error while processing forgotPassword request",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/reset/{forgotToken}")
	public ResponseEntity<String> reset(@PathVariable("forgotToken") String forgotToken) {

		int result = tokenService.verifyToken(forgotToken);

		if (result != -1) {

			return new ResponseEntity<String>("Reset Success!", HttpStatus.OK);
		}

		return new ResponseEntity<String>("Authentication failed", HttpStatus.BAD_REQUEST);

	}

}
