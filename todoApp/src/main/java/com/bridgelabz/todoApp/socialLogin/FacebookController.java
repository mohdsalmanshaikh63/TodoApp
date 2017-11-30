package com.bridgelabz.todoApp.socialLogin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
	public void redirectFromFacebook(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ClassNotFoundException, URISyntaxException {

		try {
			String sessionState = (String) request.getSession().getAttribute("STATE");
			String faceBookState = request.getParameter("state");

			logger.info("******in connect facebook");

			if (sessionState == null || !sessionState.equals(faceBookState)) {
				response.sendRedirect("http://localhost:8080/todoApp/#/login");
			}

			String error = request.getParameter("error");

			// change this to the front end homepage address
			if (error != null && error.trim().isEmpty()) {
				response.sendRedirect("http://localhost:8080/todoApp/#/login");
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
				user.setProfilePic(profile.get("picture").get("data").get("url").asText());
				user.setValid(true);
				userId = userService.createUser(user, null);

				logger.info("******User Created");

			}

			logger.info("***********user is not new to our db ,it is there in our db");
			Token socialToken = tokenService.generateToken("socialToken", userId);			

			String tokenValue = socialToken.getValue();
						
			// redirect to the dummy page for getting logins
			response.sendRedirect("http://localhost:8080/todoApp/#/dummypage/"+tokenValue);
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			response.sendRedirect("http://localhost:8080/todoApp/#/login");
		}
		 
	}

}
