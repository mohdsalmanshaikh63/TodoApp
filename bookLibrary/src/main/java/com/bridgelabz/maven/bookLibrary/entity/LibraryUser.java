package com.bridgelabz.maven.bookLibrary.entity;

/**
 * @author Salman Shaikh This class represents our library users
 *
 */
public class LibraryUser {

	private Integer userId;
	private String fullName;
	private String email;
	private String mobile;
	private String gender;

	public LibraryUser() {
	}

	/**
	 * @param userId
	 * @param fullName
	 * @param email
	 * @param mobile
	 * @param gender
	 *            This constructor uses all fields
	 */
	public LibraryUser(Integer userId, String fullName, String email, String mobile, String gender) {
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.mobile = mobile;
		this.gender = gender;
	}

	/**
	 * @param fullName
	 * @param email
	 * @param mobile
	 * @param gender
	 *            This constructor uses all fields except id
	 */
	public LibraryUser(String fullName, String email, String mobile, String gender) {
		this.fullName = fullName;
		this.email = email;
		this.mobile = mobile;
		this.gender = gender;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/*
	 * Print all the information of the user
	 */
	@Override
	public String toString() {
		return "LibraryUser [userId=" + userId + ", fullName=" + fullName + ", email=" + email + ", mobile=" + mobile
				+ ", gender=" + gender + "]";
	}

}
