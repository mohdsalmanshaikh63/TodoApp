package com.bridgelabz.restApiDemo.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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

import com.bridgelabz.restApiDemo.entity.User;
import com.bridgelabz.restApiDemo.mailUtility.MailUtility;
import com.bridgelabz.restApiDemo.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	private static Logger logger = Logger.getLogger(UserController.class);

	/*
	 * private static final Validator validator;
	 * 
	 * static { Configuration<?> config =
	 * Validation.byDefaultProvider().configure(); ValidatorFactory factory =
	 * config.buildValidatorFactory(); validator = factory.getValidator();
	 * factory.close(); }
	 * 
	 * private static void printError(ConstraintViolation<User> violation) {
	 * System.out.println(violation.getPropertyPath() + " " +
	 * violation.getMessage()); }
	 */

	@PostMapping(value = "/create")
	public ResponseEntity<String> create(@Valid @RequestBody User user, BindingResult bindingResult,
			HttpServletRequest request) {

		// add code later for checking if email already exists

		if (bindingResult.hasErrors()) {
			logger.debug("Following errors were returned" + bindingResult.getAllErrors());
			return new ResponseEntity<String>("Failed to create record", HttpStatus.NOT_ACCEPTABLE);

		} else {

			/*
			 * Set<ConstraintViolation<User>> constraintViolations =
			 * validator.validate(user);
			 * 
			 * if (constraintViolations.size() > 0) {
			 * System.out.println("Passwords do not match");
			 * constraintViolations.stream().forEach(UserController::printError); return new
			 * ResponseEntity<String>("Failed to create record", HttpStatus.NOT_ACCEPTABLE);
			 * } else {
			 */
			boolean created = userService.createUser(user);
			if (created) {
				logger.debug("Created user");
				
				logger.debug("User id of saved user is"+user.getUserId());

				// prepare the url for sending activation mail

				String scheme = request.getScheme();
				String host = request.getHeader("Host"); // includes server name and server port
				String contextPath = request.getContextPath(); // includes leading forward slash

				String resultPath = scheme + "://" + host + contextPath+"user/activate/"+user.getUserId();
				logger.debug("Result path: " + resultPath);
				
				String messageBody = "Click on this link to activate your account /n"+resultPath;
				
				// Finally send the mail!
				MailUtility.sendMail(user.getEmail(), "Activate your account", messageBody);
				

				return new ResponseEntity<String>("Record created", HttpStatus.OK);
			} else {
				logger.debug("Error while creating user record");
				return new ResponseEntity<String>("Record could not be created", HttpStatus.INTERNAL_SERVER_ERROR);
				// }

			}

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
}
