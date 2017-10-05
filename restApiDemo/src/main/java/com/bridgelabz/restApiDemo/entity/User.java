package com.bridgelabz.restApiDemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bridgelabz.restApiDemo.customAnnotation.PasswordsEqualConstraint;

/**
 * @author Salman
 *
 */
@Entity
@Table(name = "user")
//@PasswordsEqualConstraint(message= "passwords do not match")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@Column(name = "first_name")
	@NotNull(message="*required")
	@NotEmpty(message="*required")
	private String firstName;

	@Column(name = "last_name")
	@NotNull(message="*required")
	@NotEmpty(message="*required")
	private String lastName;

	@Column(name = "email", unique = true)
	@Email(message="*please enter correct email address")
	@NotNull(message="*required")
	private String email;

	@Column(name = "password")
	@NotNull(message="*required")
	@NotEmpty(message="*required")
	private String password;

	@Transient
	@NotNull(message="*required")
	@NotEmpty(message="*required")
	private String confirmPassword;

	@Column(name = "valid")
	private boolean isValid;

	public User() {

	}

	// these might be used for Spring remove these if not needed

	public User(String firstName, String lastName, String email, String password, String confirmPasssword,
			boolean isValid) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPasssword;
		this.isValid = isValid;
	}

	public User(int userId, String firstName, String lastName, String email, String password, String confirmPassword,
			boolean isValid) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.isValid = isValid;
	}

	/*
	 * public User(String firstName, String lastName, String email, String password,
	 * boolean isValid) { this.firstName = firstName; this.lastName = lastName;
	 * this.email = email; this.password = password; this.isValid = isValid; }
	 * 
	 * public User(int userId, String firstName, String lastName, String email,
	 * String password, boolean isValid) { super(); this.userId = userId;
	 * this.firstName = firstName; this.lastName = lastName; this.email = email;
	 * this.password = password; this.isValid = isValid; }
	 */
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", confirmPassword=" + confirmPassword + ", isValid=" + isValid + "]";
	}

}
