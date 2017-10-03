/**
 * 
 */
package com.bridgelabz.maven.bookLibrary.utilities;

/**
 * @author Salman Shaikh
 *
 */
public class MyUtilitiy {

	/**
	 * @param s
	 * @return true or false
	 * Returns whether the given String is blank/empty or not.
	 * 
	 */
	public static boolean isNullOrBlank(String s) {
	
		return (s == null || s.trim().equals(""));
	}

}
