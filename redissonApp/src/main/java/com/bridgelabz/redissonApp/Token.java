package com.bridgelabz.redissonApp;

public class Token {
	
	private String accessToken;
	private int id;
	
	public Token() { }
	
	
	public Token(String accessToken, int id) {
		
		this.accessToken = accessToken;
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Token [accessToken=" + accessToken + ", id=" + id + "]";
	}
	
	
	
	

}
