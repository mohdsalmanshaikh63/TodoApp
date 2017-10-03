/**
 * 
 */
package com.bridgelabz.maven.bookLibrary.entity;

/**
 * @author Salman Shaikh This class is used to maintain Book info.
 *
 */
public class Book {

	private String bookName;
	private String bookAuthor;
	private String bookCategory;
	private String bookDescription;

	/**
	 * @param bookName
	 * @param bookAuthor
	 * @param bookCategory
	 * @param bookDescription
	 */
	public Book(String bookName, String bookAuthor, String bookCategory, String bookDescription) {
		super();
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookCategory = bookCategory;
		this.bookDescription = bookDescription;
	}

	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * @param bookName
	 *            the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * @return the bookAuthor
	 */
	public String getBookAuthor() {
		return bookAuthor;
	}

	/**
	 * @param bookAuthor
	 *            the bookAuthor to set
	 */
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	/**
	 * @return the bookCategory
	 */
	public String getBookCategory() {
		return bookCategory;
	}

	/**
	 * @param bookCategory
	 *            the bookCategory to set
	 */
	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}

	/**
	 * @return the bookDescription
	 */
	public String getBookDescription() {
		return bookDescription;
	}

	/**
	 * @param bookDescription
	 *            the bookDescription to set
	 */
	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	/*
	 * Prints all the information of the book for debugging
	 */
	@Override
	public String toString() {
		return "Book [bookName=" + bookName + ", bookAuthor=" + bookAuthor + ", bookCategory=" + bookCategory
				+ ", bookDescription=" + bookDescription + "]";
	}

}
