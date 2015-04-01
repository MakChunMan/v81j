package com.imagsky.exception;

public class UnsupportedModuleBizException extends BaseException {

	private static final long serialVersionUID = 1L;
	private String unsupportedModuleBizCode = null;

	public UnsupportedModuleBizException(String unsupportedModuleBizType) {
		super("App Code \"" + unsupportedModuleBizType + "\" not supported by the the system.", "ErrorCodeConstants.ERR_SYS_MODULE_BIZ_CLASS_NOT_FOUND");
		this.unsupportedModuleBizCode = unsupportedModuleBizType;
	}	

	public UnsupportedModuleBizException(String unsupportedModuleBizType, Throwable cause) {
		super("ErrorCodeConstants.ERR_SYS_MODULE_BIZ_CLASS_NOT_FOUND", cause);
		this.unsupportedModuleBizCode = unsupportedModuleBizType;
	}
	
	public String getUnsupportedAppCode() {
		return this.unsupportedModuleBizCode; 
	}
}
