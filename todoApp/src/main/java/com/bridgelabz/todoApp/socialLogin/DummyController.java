package com.bridgelabz.todoApp.socialLogin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoApp.token.entity.Token;
import com.bridgelabz.todoApp.user.service.UserService;

@RestController
public class DummyController {

	@Autowired
	private UserService userService;
	
	Logger logger = Logger.getLogger(DummyController.class);
	
	@GetMapping("/getTokens")
	public ResponseEntity<Map<String, Token>> login(@RequestHeader("socialToken") String socialToken)
			throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException {
		

		try {
			Map<String, Token> tokenMap = userService.socialLogin(socialToken);

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
}
