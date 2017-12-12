package com.bridgelabz.todoApp.socialLogin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FacebookConnection {

	private final String App_Id = "your client id";
	private final String Secret_Id = "your secre";
	private final String Redirect_URI = "http://localhost:8080/todoApp/connectFB";
	private final String BINDING = "&fields=id,name,email,first_name,last_name,picture";

	public String getFacebookAuthURL(String unid) {

		String facebookLoginURL = "";

		try {
			facebookLoginURL = "http://www.facebook.com/dialog/oauth?" + "client_id=" + App_Id + "&redirect_uri="
					+ URLEncoder.encode(Redirect_URI, "UTF-8") + "&state=" + unid + "&response_type=code"
					+ "&scope=public_profile,email";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("inside google authentication" + facebookLoginURL);
		return facebookLoginURL;
	}

	public String getAccessToken(String authCode) throws UnsupportedEncodingException {
		String fbaccessTokenURL = "https://graph.facebook.com/v2.10/oauth/access_token?" + "client_id=" + App_Id
				+ "&redirect_uri=" + URLEncoder.encode(Redirect_URI, "UTF-8") + "&client_secret=" + Secret_Id + "&code="
				+ authCode;

		ResteasyClient restCall = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = restCall.target(fbaccessTokenURL);

		Form f = new Form();
		f.param("client_id", App_Id);
		f.param("client_secret", Secret_Id);
		f.param("redirect_uri", Redirect_URI);
		f.param("code", authCode);
		f.param("grant_type", "authorization_code");
		// using post request we are sending an data to web service and getting
		// json response back
		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(f));
		System.out.println("resp ::" + response);

		String facebookAccessToken = response.readEntity(String.class);
		ObjectMapper mapper=new ObjectMapper();
		String acc_token = null;
		try {
			acc_token = mapper.readTree(facebookAccessToken).get("access_token").asText();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return acc_token;
	}

	public JsonNode getUserProfile(String fbaccessToken) {

		String fbgetUserURL = "https://graph.facebook.com/v2.10/me?access_token=" + fbaccessToken + BINDING;
		System.out.println("fb get user details " + fbgetUserURL);
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = restCall.target(fbgetUserURL);

		String headerAuth = "Bearer " + fbaccessToken;
		Response response = target.request().header("Authorization", headerAuth).accept(MediaType.APPLICATION_JSON)
				.get();
		String profile =  response.readEntity(String.class);
		ObjectMapper mapper=new ObjectMapper();
		
		JsonNode FBprofile = null;
		try {
			FBprofile = mapper.readTree(profile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		restCall.close();
		return FBprofile;
		
	}
}