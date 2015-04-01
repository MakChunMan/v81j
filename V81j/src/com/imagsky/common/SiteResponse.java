package com.imagsky.common;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class SiteResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6246795695590152493L;
	private List errorMsgList = null;
	private String targetJSP = "/";
	//private String targetJSP =PageDefinationConstants.MAIN_LOGIN; 
	
	public List getErrorMsgList() {
		if (errorMsgList == null) {
			errorMsgList = new ArrayList();
		}
		
		return this.errorMsgList;
	}
	/**
	 * @param errorMsg
	 * @return
	 */
	public boolean addErrorMsg(SiteErrorMessage errorMsg) {
		if (errorMsgList == null) {
			errorMsgList = new ArrayList();
		}
		
		return errorMsgList.add(errorMsg);
	}
	/**
	 * @param extraErrorMsgList
	 * @return
	 */
	public boolean addErrorMsg(List extraErrorMsgList) {
		if (errorMsgList == null) {
			errorMsgList = new ArrayList();
		}
		
		return errorMsgList.addAll(extraErrorMsgList);
	}
	/**
	 * @return the targetJSP
	 */
	
	public String getTargetJSP() {
		return targetJSP;
	//	return "";
	}
	/**
	 * @param targetJSP the targetJSP to set
	 */
	public void setTargetJSP(String targetJSP) {
		this.targetJSP = targetJSP;
	}
	
	public boolean hasError(){
		return (errorMsgList!=null && errorMsgList.size()>0);
	}
	
}
