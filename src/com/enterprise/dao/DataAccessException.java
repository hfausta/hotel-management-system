/*
 * DataAccessException.java
 * Created on 10/08/2003
 *
 */
package com.enterprise.dao;



/**
 * @author yunki
 */
public class DataAccessException extends RuntimeException {


	/**
	 * @param message
	 */
	public DataAccessException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

}
