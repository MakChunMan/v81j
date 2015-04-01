package com.imagsky.v81j.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagsky.exception.*;
import com.imagsky.common.SiteResponse;

/**
 * @author Jason
 *
 */
public abstract class BaseHandler {

	public abstract SiteResponse execute(HttpServletRequest request, HttpServletResponse response) throws BaseException;
	
	protected SiteResponse createResponse() {
		SiteResponse defaultResponse = new SiteResponse();
		return defaultResponse;
	}
}
