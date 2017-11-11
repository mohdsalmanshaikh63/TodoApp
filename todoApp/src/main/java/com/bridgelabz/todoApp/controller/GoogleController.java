package com.bridgelabz.todoApp.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoApp.entity.Token;
import com.bridgelabz.todoApp.entity.User;
import com.bridgelabz.todoApp.service.TokenService;
import com.bridgelabz.todoApp.service.UserService;
import com.bridgelabz.todoApp.socialLogin.GoogleConnection;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@CrossOrigin(origins="http://localhost:8000")
public class GoogleController {

	@Autowired
	GoogleConnection googleConnection;

	@Autowired
	UserService userService;

	@Autowired
	TokenService tokenService;

	Logger logger = Logger.getLogger(GoogleController.class);

	@GetMapping(value = "/loginWithGoogle")
	public void googleConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("*******Inside loginWithGoogle");
		
		try {
			
			String unid = UUID.randomUUID().toString();
			request.getSession().setAttribute("STATE", unid);
			String googleLoginURL = googleConnection.getGoogleAuthURL(unid);
			logger.info("*******GoogleLoginURL " + googleLoginURL);
			response.sendRedirect(googleLoginURL);
		} catch (Exception e) {
			logger.info("******Error while logging in with Google \n"+e.getMessage());
			
			// redirect to appropiate error page later
		}

	}

	@GetMapping(value = "/connectGoogle")
	public ResponseEntity<List<Token>> redirectFromGoogle(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, URISyntaxException {

		logger.info("*******Inside connectGoogle");

		try {
			String sessionState = (String) request.getSession().getAttribute("STATE");
			String googlestate = request.getParameter("state");

			if (sessionState == null || !sessionState.equals(googlestate)) {
				response.sendRedirect("loginWithGoogle");
			}

			String error = request.getParameter("error");

			// change this to the front end homepage address
			if (error != null && error.trim().isEmpty()) {
				response.sendRedirect("userlogin");
			}

			String authCode = request.getParameter("code");
			String googleAccessToken = googleConnection.getAccessToken(authCode);
			logger.info("*****GoogleAccessToken: " + googleAccessToken);

			JsonNode profile = googleConnection.getUserProfile(googleAccessToken);
			logger.info("*******Google Profile :" + profile);

			int userId = userService.getUserId(profile.get("emails").get(0).get("value").asText());
			User user = null;

			// get user profile
			if (userId == -1) {
				logger.info("******User is new to our db");
				user = new User();
				user.setFirstName(profile.get("name").get("givenName").asText());			
				user.setLastName(profile.get("name").get("familyName").asText());			
				user.setEmail(profile.get("emails").get(0).get("value").asText());
				//user.setPassword("");
				user.setValid(true);

				userId = userService.createUser(user, null);

				logger.info("********User Created");

			}

			logger.info("**********User is not new to our db ,it is there in our db");
			Token acessToken = tokenService.generateToken("accessToken", userId);
			Token refreshToken = tokenService.generateToken("refreshToken", userId);

			
			// request.setAttribute("user", user);
			// RequestDispatcher dispatcher = request.getRequestDispatcher("fbsucess.jsp");
			// dispatcher.forward(request, response);

			List<Token> tokenList = new ArrayList<>();
			tokenList.add(acessToken);
			tokenList.add(refreshToken);

			logger.info("TokenList " + tokenList);

			return new ResponseEntity<List<Token>>(tokenList, HttpStatus.OK);
		} catch (Exception e) {
			logger.info("*****Error while connecting with Google. "+e.getMessage());
			return new ResponseEntity<List<Token>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
