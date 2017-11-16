package com.bridgelabz.todoApp.socialLogin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoApp.token.entity.Token;
import com.bridgelabz.todoApp.token.service.TokenService;
import com.bridgelabz.todoApp.user.entity.User;
import com.bridgelabz.todoApp.user.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
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
	public ResponseEntity<Map<String, Token>> redirectFromFacebook(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ClassNotFoundException, URISyntaxException {

		try {
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
				logger.info("**********user is new to our db");
				user = new User();

				user.setFirstName(profile.get("first_name").asText());
				user.setLastName(profile.get("last_name").asText());
				user.setEmail(profile.get("email").asText());
				user.setValid(true);
				userId = userService.createUser(user, null);

				logger.info("******User Created");

			}

			logger.info("***********user is not new to our db ,it is there in our db");
			Token accessToken = tokenService.generateToken("accessToken", userId);
			Token refreshToken = tokenService.generateToken("refreshToken", userId);

			Map<String, Token> tokenMap = new HashMap<>();
			tokenMap.put("accessToken", accessToken);
			tokenMap.put("refreshToken", refreshToken);
			logger.info("*********TokenMap" + tokenMap);


			request.setAttribute("user", user);
			System.out.println("********** The user is "+user);
			
			// redirect to the dummy page for getting logins
			// and also save to session for retrieval
			RequestDispatcher dispatcher = request.getRequestDispatcher("fbsucess.jsp");
			dispatcher.forward(request, response);

			return new ResponseEntity<Map<String, Token>>(tokenMap, HttpStatus.OK);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// response.sendRedirect("http://localhost:8080/todoApp/fbsucess.jsp");

	}

}
