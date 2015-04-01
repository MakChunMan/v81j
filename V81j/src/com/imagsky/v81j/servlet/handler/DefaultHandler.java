/*
 * 
 *
 */
package com.imagsky.v81j.servlet.handler;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import com.imagsky.common.*;

/**
 * 
 *
 */
public class DefaultHandler extends BaseHandler {
	
	
	/* (non-Javadoc)
	 * @see com.asiamiles.website.handler.Action#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public SiteResponse execute(HttpServletRequest request, HttpServletResponse response) {

		SiteResponse thisResp = super.createResponse();
		
		return thisResp;
	}
}
