package com.bridgelabz.restApiDemo.socialLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;

@Component
public class FacebookConnection {

	public static final String FB_APP_ID = "876789189147877";
	public static final String FB_APP_SECRET = "541fede0105b637db41c560b3548e7b9";
	public static final String REDIRECT_URI = "http://localhost:8080/todoApp/welcome";

	static String accessToken = "";

	public String getFBAuthUrl() {
		String fbLoginUrl = "";
		try {
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id=" + FacebookConnection.FB_APP_ID
					+ "&redirect_uri=" + URLEncoder.encode(FacebookConnection.REDIRECT_URI, "UTF-8") + "&scope=email";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbLoginUrl;
	}

	public String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?" + "client_id=" + FacebookConnection.FB_APP_ID
					+ "&redirect_uri=" + URLEncoder.encode(FacebookConnection.REDIRECT_URI, "UTF-8") + "&client_secret="
					+ FB_APP_SECRET + "&code=" + code;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbGraphUrl;
	}

	public String getAccessToken(String code) {
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				fbGraphURL = new URL(getFBGraphUrl(code));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection FacebookConnection;
			StringBuffer b = null;
			try {
				FacebookConnection = fbGraphURL.openConnection();
				try (BufferedReader in = new BufferedReader(
						new InputStreamReader(FacebookConnection.getInputStream()))) {
					String inputLine;
					b = new StringBuffer();
					while ((inputLine = in.readLine()) != null)
						b.append(inputLine + "\n");
				}

			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Unable to connect with Facebook " + e);
			}

			accessToken = b.toString();
			if (accessToken.startsWith("{")) {
				throw new RuntimeException("ERROR: Access Token Invalid: " + accessToken);
			}
		}
		return accessToken;
	}

}
