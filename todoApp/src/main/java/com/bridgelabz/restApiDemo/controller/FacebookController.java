package com.bridgelabz.restApiDemo.controller;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.restApiDemo.socialLogin.FacebookConnection;
import com.bridgelabz.restApiDemo.socialLogin.FacebookGraph;

@RestController
public class FacebookController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String code="";

	@GetMapping(value="/fblogin")
	public void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {		
		code = req.getParameter("code");
		if (code == null || code.equals("")) {
			throw new RuntimeException(
					"ERROR: Didn't get code parameter in callback.");
		}
		FacebookConnection FacebookConnectionion = new FacebookConnection();
		String accessToken = FacebookConnectionion.getAccessToken(code);

		FacebookGraph fbGraph = new FacebookGraph(accessToken);
		String graph = fbGraph.getFacebookGraph();
		Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
		ServletOutputStream out = res.getOutputStream();
		out.println("<h1>Facebook Login using Java</h1>");
		out.println("<h2>Application Main Menu</h2>");
		out.println("<div>Welcome "+fbProfileData.get("first_name"));
		out.println("<div>Your Email: "+fbProfileData.get("email"));
		out.println("<div>You are "+fbProfileData.get("gender"));		
	}

}