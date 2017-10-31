package com.bridgelabz.todoApp.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		

	@RequestMapping(value = "/loginWithFacebook")
	public void googleConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(" in loginWithFacebook  ");
		String unid = UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", unid);
		String facebookLoginURL = facebookConnection.getFacebookAuthURL(unid);
		System.out.println("facebookLoginURL  " + facebookLoginURL);
		response.sendRedirect(facebookLoginURL);
	}

	@RequestMapping(value = "/connectFB")
	public void redirectFromFacebook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String sessionState = (String) request.getSession().getAttribute("STATE");
		String faceBookState = request.getParameter("state");

		System.out.println("in connect facebook");

		if (sessionState == null || !sessionState.equals(faceBookState)) {
			response.sendRedirect("loginWithFacebook");
		}

		String error = request.getParameter("error");
		if (error != null && error.trim().isEmpty()) {
			response.sendRedirect("userlogin");
		}

		String authCode = request.getParameter("code");
		String fbaccessToken = facebookConnection.getAccessToken(authCode);
		System.out.println("fbaccessToken " + fbaccessToken);

		JsonNode profile = facebookConnection.getUserProfile(fbaccessToken);
		System.out.println("fb profile :" + profile);
		int userId = userService.getUserId(profile.get("email").asText());
		//System.out.println("fb img "+ profile.get("picture").get("data").get("url").asText());
		User user = null;
		
		// get user profile
		if (userId == -1) {
			System.out.println(" user is new to our db");
			user = new User();
			user.setFirstName(profile.get("name").asText());
			/*user.setFirstName(profile.get("first_name").asText());
			user.setLastName(profile.get("last_name").asText());*/
			user.setEmail(profile.get("email").asText());
			user.setPassword("");
			user.setValid(true);
			
			// add isValid logic here later
			userId = userService.createUser(user);
			
			System.out.println("");
			
		}		

		System.out.println(" user is not new to our db ,it is there in our db");
		tokenService.generateToken("accessToken", userId);
	
		/*Cookie acccookie = new Cookie("socialaccessToken", tokens.getAccessToken());
		Cookie refreshcookie = new Cookie("socialrefreshToken", tokens.getRefreshToken());
		response.addCookie(acccookie);
		response.addCookie(refreshcookie);*/
		request.setAttribute("user", user);
		RequestDispatcher dispatcher = request.getRequestDispatcher("fbsucess.jsp");
		dispatcher.forward(request, response);
		
		//response.sendRedirect("http://localhost:8080/todoApp/fbsucess.jsp");
		
		System.out.println("After redirect and trying to prove Soma wrong");
	}
}