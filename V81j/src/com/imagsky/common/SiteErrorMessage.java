package com.imagsky.common;

import java.io.Serializable;
import java.util.List;
import com.imagsky.util.MessageUtil;


public class SiteErrorMessage  implements Serializable {

	private String msgCode = null; // STR messageCode
	private Throwable cause = null; // the exception that led to this errorMsg Shown; null if it is validation error
	private List msgParam = null; // msg parameter in form of {0}, {1}
	private String renderedMsg = null;
	
	private String lang =null;
	
	
	
	/**
	 * @param msgCode
	 * @param cause
	 */
	public SiteErrorMessage(String msgCode, List msgParam) {
		this.msgCode = msgCode;
		this.cause = null;
		this.msgParam = null;
	}
	/**
	 * @param msgCode
	 * @param msgParam
	 * @param cause
	 */
	public SiteErrorMessage(String msgCode, List msgParam, Throwable cause) {
		this.msgCode = msgCode;
		this.cause = cause;
		this.msgParam = msgParam;
	}	
	/**
	 * @param msgCode
	 * @param cause
	 */
	public SiteErrorMessage(String msgCode, Throwable cause) {
		this.msgCode = msgCode;
		this.cause = cause;
		this.msgParam = null;
	}
	/**
	 * @param msgCode
	 * @param cause
	 */
	public SiteErrorMessage(String msgCode) {
		this.msgCode = msgCode;
		this.cause = null;
		this.msgParam = null;
	}
	
	/**
	 * @return Returns the cause.
	 */
	public Throwable getCause() {
		return cause;
	}
	/**
	 * @return Returns the msgCode.
	 */
	public String getMsgCode() {
		return msgCode;
	}
	/**
	 * @return Returns the msgParam.
	 */
	public List getMsgParam() {
		return msgParam;
	}
	/**
	 * @return Returns the renderedMsg.
	 */
	public String getRenderedMsg() {
		return renderedMsg;
	}
	/**
	 * @param renderedMsg The renderedMsg to set.
	 */
	public void setRenderedMsg(String renderedMsg) {
		this.renderedMsg = renderedMsg;
	}
	
	public boolean isMsgRendered() {
		return (this.renderedMsg != null);
	}
	
	public void setLang(String lang){
		this.lang = lang;
	}
	
}

