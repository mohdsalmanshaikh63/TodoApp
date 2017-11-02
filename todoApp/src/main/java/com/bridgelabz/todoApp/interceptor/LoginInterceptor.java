package com.bridgelabz.todoApp.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bridgelabz.todoApp.entity.Token;
import com.bridgelabz.todoApp.service.TokenService;
import com.google.gson.Gson;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	TokenService tokenService;

	public static final Logger logger = Logger.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String accessToken = request.getHeader("accessToken");

		// check if accessToken has expired
		int aUserId = tokenService.verifyToken(accessToken);

		if (aUserId == -1) {

			logger.info("******Acess token expired! Checking refresh token");

			String refreshToken = request.getHeader("refreshToken");

			// check if refreshToken has expired
			int rUserId = tokenService.verifyToken(refreshToken);

			if (rUserId == -1) {

				logger.info("*******Refresh token also expired");

				// errorResponse(response, -2,"Refresh Token Expired. Redirect to login");
				Gson gson = new Gson();
				String errorMessage = gson.toJson("Refresh token also expired");

				// errorResponse(response, -1, newAcessToken);

				response.setContentType("application/json");

				response.setStatus(HttpStatus.BAD_REQUEST.value());

				PrintWriter out = response.getWriter();

				out.write(errorMessage);

				return false;

			} else {

				logger.info("*********Generating new accessToken");

				// generate an accessToken and return it to the user
				Token newAcessToken = tokenService.generateToken("accessToken", rUserId);

				Gson gson = new Gson();
				String errorMessage = gson.toJson(newAcessToken);

				// errorResponse(response, -1, newAcessToken);

				response.setContentType("application/json");

				response.setStatus(HttpStatus.BAD_REQUEST.value());

				PrintWriter out = response.getWriter();

				out.write(errorMessage);

				return false;
			}

		} else {

			logger.info("*******Authentication complete");

			// set the userId attribute and forward the request
			request.setAttribute("userId", aUserId);

			return true;

		}

	}

	/*
	 * private void errorResponse(HttpServletResponse response, int status, String
	 * string) throws IOException {
	 * 
	 * Gson gson = new Gson(); String errorMessage = gson.toJson(string);
	 * 
	 * if(out == null) { out = response.getWriter(); }
	 * 
	 * 
	 * response.setContentType("application/json");
	 * 
	 * response.setStatus(status);
	 * 
	 * 
	 * out.write(errorMessage);
	 * 
	 * }
	 */
}
