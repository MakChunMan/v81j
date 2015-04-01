package com.imagsky.exception;

public class BaseException extends Exception implements ErrorCodeThrowable {

	//private throwable = null; // pls use JDK 1.4 parent class (java.lang.Exception) getCause();
	//private message = null; // pls use parent class (java.lang.Exception) getMessage();
	private String errorCode = null;
	
	// -----------
	// Constructor 
	// -----------
	
	/**
	 * @param errorCode
	 */
	public BaseException(String errorCode) {
		super();
		this.errorCode = errorCode;
	}
	/**
	 * @param errorCode
	 * @param message
	 */
	public BaseException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	/**
	 * @param message
	 * @param errorCode
	 * @param cause
	 */
	public BaseException(String message, String errorCode, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	/**
	 * @param errorCode
	 * @param cause
	 */
	public BaseException(String errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}
	
	
	
	
	// -------------
	// Getter/Setter 
	// -------------
	
	/**
	 * @return Returns the errorCode.
	 */
	public String getErrorCode() {
		return this.errorCode;
	}
	
	


}
