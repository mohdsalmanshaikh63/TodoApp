/**
 * 
 */
package com.bridgelabz.maven.bookLibrary.dao;

import java.sql.SQLException;
import java.util.List;

import com.bridgelabz.maven.bookLibrary.entity.Book;

/**
 * @author Salman Shaikh Our DAO class for LibraryBooks.
 *
 */
public interface LibraryBookDao {

	/**
	 * @param userId
	 * @param category
	 * @return a list of Books.
	 * @throws SQLException
	 *             This method returns a list of Books for the given userId and
	 *             category
	 */
	public List<Book> getBooks(Integer userId, String category) throws SQLException;

	/**
	 * @param userId
	 * @param bookName
	 * @return
	 * @throws SQLException
	 *             Returns a book for the logged in user with the given bookName.
	 */
	public Book getBook(Integer userId, String bookName) throws SQLException;

	/**
	 * @param userId
	 * @param book
	 * @throws SQLException
	 *             Adds a new book the logged in user's library.
	 */
	public void addBook(Integer userId, Book book) throws SQLException;

	/**
	 * @param userId
	 * @param bookName
	 * @throws SQLException
	 * Deletes the given books from the user's library
	 */
	public void deleteBook(Integer userId, String bookName) throws SQLException;

	/**
	 * @param userId
	 * @param bookName
	 * @return
	 * @throws SQLException
	 * Checks whether the book with the given name already exists
	 * in the user's library.
	 */
	public boolean checkIfBookExists(Integer userId, String bookName) throws SQLException;

	/**
	 * @param userId
	 * @param oldBookName
	 * @param book
	 * @throws SQLException
	 * Updates the given book's information in the user's library.
	 */
	public void updateBook(Integer userId, String oldBookName, Book book) throws SQLException;

}
