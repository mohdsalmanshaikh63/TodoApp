package com.bridgelabz.maven.bookLibrary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bridgelabz.maven.bookLibrary.entity.LibraryUser;
import com.bridgelabz.maven.bookLibrary.utilities.MyUtilitiy;


/**
 * @author Salman Shaikh
 *
 */
public class LibraryUserDaoImpl implements LibraryUserDAO {

	private Log logger = LogFactory.getLog(LibraryUserDaoImpl.class);

	// can't use @Resource annotation since this is POJO class. It applies to Java
	// EE elements only.
	private DataSource dataSource;

	/**
	 * @param dataSource
	 *            used to obtain connection to the database
	 */
	public LibraryUserDaoImpl(DataSource dataSource) {

		logger.debug("LibraryUserDaoImpl initialized");

		this.dataSource = dataSource;
	}

	/**
	 * @param email
	 * @param password
	 * @return true or false i.e. the user details are valid or not This method
	 *         checks if the user exists in the database or not
	 * @throws SQLException
	 */
	@Override
	public boolean authenticate(String email, String password) throws SQLException {

		logger.info("Inside LibraryUserDaoImpl authenticate()");

		// get a connection to database
		try (Connection connection = dataSource.getConnection()) {

			// create sql to check if a record exists with a given username and password
			String sql = "select * from appusers where email=? and password=?";

			// prepare statement
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			// set the parameters
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			// store the results in resultset
			ResultSet resultSet = preparedStatement.executeQuery();

			// check if a user record exists
			if (resultSet.next()) {
				return true;
			}
		}

		return false;
	}

	/*
	 * @param fullName
	 * 
	 * @param email
	 * 
	 * @param mobile
	 * 
	 * @param password
	 * 
	 * @param confirmPassword
	 * 
	 * @param gender
	 * 
	 * @return an error String This method tries to register a user with the given
	 * details and returns an error String if any errors encountered
	 * 
	 */
	@Override
	public String register(String fullName, String email, String mobile, String password, String confirmPassword,
			String gender) throws SQLException {

		logger.info("Inside LibraryUserDaoImpl register()");

		String error = "";

		// first check for null data or empty data and error messages accordingly
		if (MyUtilitiy.isNullOrBlank(fullName)) {
			error += "<p>Fullname required!</p>";

		} else {

			fullName = fullName.trim();
			String digits = "\\d";
			String fullnameRegex = "[a-zA-Z]+ [a-zA-Z]+( [a-zA-Z])*";

			Pattern digitPattern = Pattern.compile(digits);
			Pattern wordPattern = Pattern.compile(fullnameRegex);

			Matcher digitMatcher = digitPattern.matcher(fullName);
			Matcher wordMatcher = wordPattern.matcher(fullName);

			// check if fullname contains any numbers or less than two words
			if (digitMatcher.find()) {
				error += "<p>No numbers allowed in fullname<p>";

			} else if (!(wordMatcher.matches())) {
				error += "<p>Minimum two words in fullname required";
			}
		}

		if (MyUtilitiy.isNullOrBlank(email)) {
			error += "<p>Email required!</p>";
		} else {

			email = email.trim();
			String emailRegex = "([a-zA-Z0-9_.+-])+\\@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$";

			// check if the email address is in correct format
			Pattern emailPattern = Pattern.compile(emailRegex);

			Matcher emailMatcher = emailPattern.matcher(email);

			if (!(emailMatcher.matches())) {
				error += "<p>Incorrect email address</p>";
			} else if (checkUserExists(email)) {
				error += "<p>Email already registered</p>";
			}

		}

		if (MyUtilitiy.isNullOrBlank(mobile)) {
			error += "<p>Mobile no required!</p>";
		} else {
			mobile = mobile.trim();
			String mobileRegex = "[0-9]{10}";
			String wordRegex = "[a-zA-Z]";

			Pattern mobilePattern = Pattern.compile(mobileRegex);
			Pattern mobileWordPattern = Pattern.compile(wordRegex);

			Matcher mobileMatcher = mobilePattern.matcher(mobile);
			Matcher wordMatcher = mobileWordPattern.matcher(mobile);

			// check if mobile no doesn't contain any alphabets and contains only 10 digits
			if (wordMatcher.find()) {
				error += "<p>Only numbers allowed in mobile no</p>";
			} else if (!(mobileMatcher.find())) {
				error += "<p>Only 10 digits allowed in mobile no</p>";
			}

		}

		if (MyUtilitiy.isNullOrBlank(password)) {
			error += "<p>Password required!</p>";
		}

		if (MyUtilitiy.isNullOrBlank(confirmPassword)) {
			error += "<p>Confirm Password required!</p>";
		} else {
			if (Objects.equals(password, confirmPassword) == false) {
				error += "<p>Passwords do not match!</p>";
			}
		}

		if (MyUtilitiy.isNullOrBlank(gender)) {
			error += "<p>Gender required!</p>";
		} else {

		}

		logger.info("Register Error String: " + error);

		return error;
	}

	/**
	 * @return whether the given username is already present or not
	 * @throws SQLException
	 */
	@Override
	public boolean checkUserExists(String email) throws SQLException {

		logger.info("Inside LibraryUserDaoImpl checkUserExists()");

		// get a connection to database
		try (Connection connection = dataSource.getConnection()) {

			// create sql to check if a record exists with a given username
			String sql = "select * from appusers where email=?";

			// prepare statement
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			// set the parameters
			preparedStatement.setString(1, email);

			// store the results in resultset
			ResultSet resultSet = preparedStatement.executeQuery();

			// check if a user record exists
			if (resultSet.next()) {
				return true;
			}
		}

		return false;
	}

	/*
	 * 
	 * @see com.bridgelabz.dao.LibraryUserDAO#addUser(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void addUser(String fullName, String email, String mobile, String password, String gender)
			throws SQLException {

		logger.info("Inside LibraryUserDaoImpl addUser()");

		try (Connection connection = dataSource.getConnection()) {

			String sql = "insert into appusers(email, password) values (?,?)";
			Integer uid = null;

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			preparedStatement.executeUpdate();
			preparedStatement.close();

			// create sql to get user id
			sql = "select uid from appusers where email=?";

			// prepare statement
			preparedStatement = connection.prepareStatement(sql);

			// set the parameters
			preparedStatement.setString(1, email);

			// store the results in resultset
			ResultSet resultSet = preparedStatement.executeQuery();

			//
			while (resultSet.next()) {
				uid = resultSet.getInt("uid");
			}

			if (uid != null) {
				sql = "insert into userdetails(uid, fullname, email, phone, gender) values (?, ?, ?, ?, ?)";
				preparedStatement = connection.prepareStatement(sql);

				preparedStatement.setInt(1, uid);
				preparedStatement.setString(2, fullName);
				preparedStatement.setString(3, email);
				preparedStatement.setString(4, mobile);
				preparedStatement.setString(5, gender);

				preparedStatement.executeUpdate();
			} else {
				logger.debug("User does not exist");
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgelabz.dao.LibraryUserDAO#getUserByEmail(java.lang.String)
	 */
	@Override
	public LibraryUser getUserByEmail(String email) throws SQLException {

		logger.info("Inside LibraryUserDaoImpl getUserByEmail()");

		LibraryUser libraryUser = null;

		try (Connection connection = dataSource.getConnection()) {
			// Sql query
			String sql = "select * from userdetails where email=?";

			// Prepared Statement
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);

			ResultSet resultSet = preparedStatement.executeQuery();

			// get the user with the given email
			if (resultSet.next()) {
				Integer userId = resultSet.getInt("uid");
				String fullName = resultSet.getString("fullname");
				String mobile = resultSet.getString("phone");
				String gender = resultSet.getString("gender");
				libraryUser = new LibraryUser(userId, fullName, email, mobile, gender);
				logger.info("LibraryUser " + libraryUser);
			}

		}

		return libraryUser;

	}
}
