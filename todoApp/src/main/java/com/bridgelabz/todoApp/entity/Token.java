package com.bridgelabz.todoApp.entity;

/**
 * @author Salman
 *
 */
public class Token {

	private String tokenType;

	private String value;

	private Integer uid;

	public Token() {
	}

	public Token(String tokenType, String value, Integer uid) {
		this.tokenType = tokenType;
		this.value = value;
		this.uid = uid;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Token [tokenType=" + tokenType + ", value=" + value + ", uid=" + uid + "]";
	}

}
