package com.bridgelabz.todoApp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoApp.entity.Token;
import com.bridgelabz.todoApp.entity.User;
import com.bridgelabz.todoApp.service.TokenService;
import com.bridgelabz.todoApp.service.UserService;
import com.bridgelabz.todoApp.socialLogin.FacebookConnection;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class FacebookController {

	@Autowired
	FacebookConnection facebookConnection;

	@Autowired
	UserService userService;

	@Autowired
	TokenService tokenService;

	Logger logger = Logger.getLogger(FacebookConnection.class);

	@GetMapping(value = "/loginWithFacebook")
	public void facebookConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("*******Inside loginWithFacebook");
		String unid = UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);
		String facebookLoginURL = facebookConnection.getFacebookAuthURL(unid);
		logger.info("*******facebookLoginURL  " + facebookLoginURL);
		response.sendRedirect(facebookLoginURL);
	}

	@RequestMapping(value = "/connectFB")
	public ResponseEntity<List<Token>> redirectFromFacebook(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String sessionState = (String) request.getSession().getAttribute("STATE");
		String faceBookState = request.getParameter("state");

		logger.info("******in connect facebook");

		if (sessionState == null || !sessionState.equals(faceBookState)) {
			response.sendRedirect("loginWithFacebook");
		}

		String error = request.getParameter("error");

		// change this to the front end homepage address
		if (error != null && error.trim().isEmpty()) {
			response.sendRedirect("userlogin");
		}

		String authCode = request.getParameter("code");
		String fbaccessToken = facebookConnection.getAccessToken(authCode);
		logger.info("******fbaccessToken " + fbaccessToken);

		JsonNode profile = facebookConnection.getUserProfile(fbaccessToken);
		logger.info("*******Fb profile :" + profile);
		int userId = userService.getUserId(profile.get("email").asText());
		User user = null;

		// get user profile
		if (userId == -1) {
			logger.info(" user is new to our db");
			user = new User();
			user.setFirstName(profile.get("name").asText());
			/*
			 * user.setFirstName(profile.get("first_name").asText());
			 * user.setLastName(profile.get("last_name").asText());
			 */
			user.setEmail(profile.get("email").asText());
			user.setPassword("");
			user.setValid(true);

			// add isValid logic here later
			userId = userService.createUser(user);

			System.out.println("******User Created");

		}

		logger.info(" user is not new to our db ,it is there in our db");
		Token acessToken = tokenService.generateToken("accessToken", userId);
		Token refreshToken = tokenService.generateToken("refreshToken", userId);

		List<Token> tokenList = new ArrayList<>();
		tokenList.add(acessToken);
		tokenList.add(refreshToken);

		logger.info("TokenList " + tokenList);

		return new ResponseEntity<List<Token>>(tokenList, HttpStatus.OK);

		/*
		 * request.setAttribute("user", user); RequestDispatcher dispatcher =
		 * request.getRequestDispatcher("fbsucess.jsp"); dispatcher.forward(request,
		 * response);
		 */
		// response.sendRedirect("http://localhost:8080/todoApp/fbsucess.jsp");

	}

}
