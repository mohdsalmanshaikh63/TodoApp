package com.bridgelabz.maven.bookLibrary.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bridgelabz.maven.bookLibrary.dao.LibraryBookDao;
import com.bridgelabz.maven.bookLibrary.dao.LibraryBookDaoImpl;
import com.bridgelabz.maven.bookLibrary.entity.Book;
import com.bridgelabz.maven.bookLibrary.entity.LibraryUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class LibraryController
 */
@WebServlet("/LibraryController")
public class LibraryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(LibraryController.class);

	// Our DAO reference
	LibraryBookDao libraryBookUtil;

	// database resource using annotation
	@Resource(name = "jdbc/book_library")
	DataSource dataSource;

	/*
	 * We initialize the necessary objects i.e. UserDbUtil when the servlet is
	 * initialized by the container
	 */
	@Override
	public void init() throws ServletException {

		logger.debug("LibraryController Started");

		// create our student db_util ... and pass in the connection pool/datasource
		libraryBookUtil = new LibraryBookDaoImpl(dataSource);

	}

	/**
	 * All GET requests are forwarded to post
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @param request
	 * @param response
	 *            Our main controller which handles handles different requests and
	 *            directs to appropriate library operations such as displaying
	 *            homepage, adding/updating/viewing/deleting books.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			logger.info("Inside LibraryController doPost()");

			// read the command parameter
			String command = request.getParameter("command");
			logger.info("Command is " + command);

			// if the command is missing, then default to list students
			if (command == null) {
				command = "welcome";
			}

			// route to the appropiate method
			switch (command) {

			case "welcome":
				displayHomePage(request, response);
				break;

			case "list":
				listBooks(request, response);
				break;

			case "add":
				addBook(request, response);
				break;

			case "load":
				try {
					loadBook(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case "update":
				updateBook(request, response);
				break;

			case "delete":
				deleteBook(request, response);
				break;

			default:
				displayHomePage(request, response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 *             This method deletes the book sent by the request.
	 */
	private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("Inside LibraryController deleteBook()");

		Integer userId = getUserSessionId(request);
		if (userId != null) {

			String bookName = request.getParameter("bookName");

			try {
				libraryBookUtil.deleteBook(userId, bookName);
				response.setContentType("text/plain");
				response.getWriter().print("success");
			} catch (SQLException e) {
				response.setContentType("text/html");
				response.getWriter().print("error");
			}

		}

	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 *             This method updates the book sent by the request.
	 */
	private void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("Inside LibraryController updateBook()");

		Integer userId = getUserSessionId(request);
		if (userId != null) {
			String oldBookName = request.getParameter("oldBookName");
			String bookName = request.getParameter("bookName");
			String bookCategory = request.getParameter("category");
			String bookDescription = request.getParameter("bookDescription");
			String bookAuthor = request.getParameter("bookAuthor");
			Book book = new Book(bookName, bookAuthor, bookCategory, bookDescription);
			try {
				libraryBookUtil.updateBook(userId, oldBookName, book);
				response.setContentType("text/html");
				response.getWriter().print("success");
			} catch (SQLException e) {
				response.setContentType("text/html");
				response.getWriter().print("duplicate");
			}
		}

	}

	/**
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 *             This method sends the book required by the requestURL.
	 */
	private void loadBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		logger.info("Inside LibraryController loadBook()");

		Integer userId = getUserSessionId(request);
		if (userId != null) {

			String bookName = request.getParameter("bookName");
			Book book = libraryBookUtil.getBook(userId, bookName);
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			String jsonObject = gson.toJson(book);
			response.setContentType("application/json");
			response.getWriter().print(jsonObject);
		}

	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 *             This method adds the book sent by the request.
	 */
	private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

		logger.info("Inside LibraryController addBook()");

		Integer userId = getUserSessionId(request);
		if (userId != null) {
			String bookName = request.getParameter("bookName");
			String bookCategory = request.getParameter("category");
			String bookDescription = request.getParameter("bookDescription");
			String bookAuthor = request.getParameter("bookAuthor");

			if (libraryBookUtil.checkIfBookExists(userId, bookName) == false) {

				Book book = new Book(bookName, bookAuthor, bookCategory, bookDescription);
				logger.debug("Book details: " + book);
				libraryBookUtil.addBook(userId, book);
				response.setContentType("text/plain");
				response.getWriter().print("success");
			} else {
				response.setContentType("text/plain");
				response.getWriter().print("duplicate");

			}
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 *             This method returns a list of books for the given user in
	 *             response.
	 */
	private void listBooks(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		logger.info("Inside LibraryController listBooks");
		Gson gson = null;
		String category = request.getParameter("category").toLowerCase();
		Integer userId = getUserSessionId(request);
		if (userId != null) {
			List<Book> bookList = libraryBookUtil.getBooks(userId, category);
			if (bookList != null) {
				gson = new GsonBuilder().disableHtmlEscaping().create();
				JsonElement element = gson.toJsonTree(bookList, new TypeToken<List<Book>>() {
				}.getType());
				JsonArray jsonArray = element.getAsJsonArray();
				logger.info("JsonArray: " + jsonArray);
				response.setContentType("application/json");
				response.getWriter().print(jsonArray);
			}
		}

	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 *             This method forwards the request to the homepage view.
	 */
	private void displayHomePage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("Inside LibraryController dusplayHomePage()");

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("homepage.jsp");
		requestDispatcher.forward(request, response);

	}

	/**
	 * @param request
	 * @return the userId associated with the current session.
	 */
	public Integer getUserSessionId(HttpServletRequest request) {

		logger.info("Inside LibraryController getUserSessionId()");

		HttpSession httpSession = request.getSession();

		Integer userId = null;

		if (httpSession != null) {
			LibraryUser user = (LibraryUser) httpSession.getAttribute("user");
			userId = user.getUserId();
			return userId;
		}

		return userId;

	}
}