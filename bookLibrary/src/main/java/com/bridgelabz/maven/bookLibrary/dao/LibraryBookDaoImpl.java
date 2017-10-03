package com.bridgelabz.maven.bookLibrary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bridgelabz.maven.bookLibrary.entity.Book;


/**
 * @author Salman Shaikh
 *
 */
public class LibraryBookDaoImpl implements LibraryBookDao {

	private Log logger = LogFactory.getLog(LibraryUserDaoImpl.class);

	// can't use @Resource annotation since this is POJO class. It applies to Java
	// EE elements only.
	private DataSource dataSource;

	/**
	 * @param dataSource
	 *            used to obtain connection to the database
	 */
	public LibraryBookDaoImpl(DataSource dataSource) {

		logger.debug("LibraryBookDaoImp initialized");
		this.dataSource = dataSource;
	}

	/*
	 * @see com.bridgelabz.dao.LibraryBookDao#getBooks(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public List<Book> getBooks(Integer userId, String bookCategory) throws SQLException {

		logger.info("Inside LibraryBookDaoImpl getBooks()");

		List<Book> bookList = null;

		try (Connection connection = dataSource.getConnection()) {

			String sql = "select * from userbooks where uid=? and bookCategory=?";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, bookCategory);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				if (bookList == null) {
					bookList = new ArrayList<>();
				}

				String bookName = resultSet.getString("bookname");
				String bookAuthor = resultSet.getString("bookauthor");
				String bookDescription = resultSet.getString("description");

				bookList.add(new Book(bookName, bookAuthor, bookCategory, bookDescription));
				logger.info("BookList: " + bookList);
			}
		}
		return bookList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgelabz.dao.LibraryBookDao#getBook(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public Book getBook(Integer userId, String bookName) throws SQLException {
		logger.info("Inside LibraryBookDaoImpl getBook()");

		Book book = null;

		try (Connection connection = dataSource.getConnection()) {

			String sql = "select * from userbooks where uid=? and bookName=?";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, bookName);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				String bookAuthor = resultSet.getString("bookauthor");
				String bookCategory = resultSet.getString("bookCategory");
				String bookDescription = resultSet.getString("description");

				book = new Book(bookName, bookAuthor, bookCategory, bookDescription);
			}
			logger.info("Book: " + book);
		}
		return book;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgelabz.dao.LibraryBookDao#addBook(java.lang.Integer,
	 * com.bridgelabz.entity.Book)
	 */
	@Override
	public void addBook(Integer userId, Book book) throws SQLException {

		logger.info("Inside LibraryBookDaoImpl addBook()");

		try (Connection connection = dataSource.getConnection()) {

			String sql = "insert into userbooks (uid, bookname, bookauthor, bookcategory, description) values (?, ?, ?, ?, ?)";

			String bookName = book.getBookName();
			String bookAuthor = book.getBookAuthor();
			String bookCategory = book.getBookCategory();
			String description = book.getBookDescription();

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, bookName);
			preparedStatement.setString(3, bookAuthor);
			preparedStatement.setString(4, bookCategory);
			preparedStatement.setString(5, description);

			preparedStatement.executeUpdate();

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgelabz.dao.LibraryBookDao#checkIfBookExists(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public boolean checkIfBookExists(Integer userId, String bookName) throws SQLException {

		logger.info("Inside LibraryBookDaoImpl checkIfBookExists()");

		try (Connection connection = dataSource.getConnection()) {

			String sql = "select * from userbooks where uid=? and bookname=?";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, bookName);

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;
			}
		}

		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgelabz.dao.LibraryBookDao#deleteBook(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public void deleteBook(Integer userId, String bookName) throws SQLException {

		logger.info("Inside LibraryBookDaoImpl deleteBook()");

		try (Connection connection = dataSource.getConnection()) {

			String sql = "delete from userbooks where uid=? and bookname=?";

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, bookName);

			preparedStatement.executeUpdate();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgelabz.dao.LibraryBookDao#updateBook(java.lang.Integer,
	 * java.lang.String, com.bridgelabz.entity.Book)
	 */
	@Override
	public void updateBook(Integer userId, String oldBookName, Book book) throws SQLException {

		logger.info("Inside LibraryBookDaoImpl updateBook()");

		try (Connection connection = dataSource.getConnection()) {

			String sql = "update userbooks set bookname=? , bookauthor=? , bookcategory=?, description=? where bookname=? and uid=?";

			String bookName = book.getBookName();
			String bookAuthor = book.getBookAuthor();
			String bookCategory = book.getBookCategory();
			String description = book.getBookDescription();

			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, bookName);
			preparedStatement.setString(2, bookAuthor);
			preparedStatement.setString(3, bookCategory);
			preparedStatement.setString(4, description);
			preparedStatement.setString(5, oldBookName);
			preparedStatement.setInt(6, userId);

			preparedStatement.executeUpdate();

		}

	}

}
