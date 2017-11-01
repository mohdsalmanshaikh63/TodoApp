package com.bridgelabz.todoApp.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bridgelabz.todoApp.service.TokenService;

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

			String refreshToken = request.getHeader("refreshToken");

			// check if refreshToken has expired
			int rUserId = tokenService.verifyToken(refreshToken);

			if (rUserId == -1) {
				
				PrintWriter out = response.getWriter();
				
				response.setContentType("application/json");

			} else {
				// set the userId attribute and forward the request
				request.setAttribute("userId", rUserId);
				return true;
			}

		} else {

			// set the userId attribute and forward the request
			request.setAttribute("userId", aUserId);
			return true;
		}

		return false;
	}
}
