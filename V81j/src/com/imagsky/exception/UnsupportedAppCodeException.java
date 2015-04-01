/*
 * 
 *
 */
package com.imagsky.exception;
/**
 *
 */
public class UnsupportedAppCodeException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unsupportedAppCode = null;

	/**
	 * 
	 */
	public UnsupportedAppCodeException(String unsupportedAction) {
		super("App Code \"" + unsupportedAction + "\" not supported by the the system.", "ErrorCodeConstants.ERR_SYS_ACTION_CLASS_NOT_FOUND");
		this.unsupportedAppCode = unsupportedAction;
	}	
	/**
	 * @param cause
	 */
	public UnsupportedAppCodeException(String unsupportedAppCode, Throwable cause) {
		super("ErrorCodeConstants.ERR_SYS_ACTION_CLASS_NOT_FOUND", cause);
		this.unsupportedAppCode = unsupportedAppCode;
	}
	
	
	
	
	
	public String getUnsupportedAppCode() {
		return this.unsupportedAppCode; 
	}
}
