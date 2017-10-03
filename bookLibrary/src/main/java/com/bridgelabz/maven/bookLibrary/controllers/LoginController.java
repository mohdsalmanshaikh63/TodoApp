package com.bridgelabz.maven.bookLibrary.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bridgelabz.maven.bookLibrary.dao.LibraryUserDaoImpl;
import com.bridgelabz.maven.bookLibrary.entity.LibraryUser;

/**
 * This controller is used for user authentication during login
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(LoginController.class);

	// Our DAO reference
	LibraryUserDaoImpl userDbUtil;

	// database resource using annotation
	@Resource(name = "jdbc/book_library")
	DataSource dataSource;

	/*
	 * We initialize the necessary objects i.e. UserDbUtil when the servlet is
	 * initialized by the container
	 */
	@Override
	public void init() throws ServletException {

		logger.debug("LoginController started");

		// create our student db_util ... and pass in the connection pool/datasource
		userDbUtil = new LibraryUserDaoImpl(dataSource);

	}

	/*
	 * @param request
	 * 
	 * @param response Forward all requests to do post
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Inside LibraryController doGet()");

		doPost(request, response);
	}

	/**
	 * @param request
	 * @param response
	 *            Allows the user to access the app only if he is a valid user else
	 *            sends him back to the login page with an error message
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Inside LibraryController doGet()");

		// get the email and password from the login page
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// authenticate the credentials
		boolean result = false;
		try {
			result = userDbUtil.authenticate(email, password);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		// if correct credentials then forward to LibraryController else back to login
		// page
		if (result) {

			// get user by email id and set the user for session
			try {
				LibraryUser user = userDbUtil.getUserByEmail(email);
				HttpSession httpSession = request.getSession(true);
				httpSession.setAttribute("user", user);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// RequestDispatcher requestDispatcher =
			// request.getRequestDispatcher("LibraryController?command=WELCOME");
			// requestDispatcher.forward(request, response);
			response.sendRedirect(request.getContextPath() + "/LibraryController");

		} else {
			response.sendRedirect(request.getContextPath() + "/login.jsp?message=INVALID");
		}

	}

}
