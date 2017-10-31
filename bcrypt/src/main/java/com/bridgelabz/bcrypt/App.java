package com.bridgelabz.bcrypt;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Hashing the password 'salman'");
        String hashString = BCrypt.hashpw("salman", BCrypt.gensalt());
        System.out.println("Hashed password is "+hashString);
        if(BCrypt.checkpw("salman", hashString)) {
        	System.out.println("Both are the same passwords!");
        } else {
        	System.out.println("They are not the same!");
        }
    }
}
