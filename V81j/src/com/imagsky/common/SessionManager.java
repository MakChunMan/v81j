package com.imagsky.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import com.imagsky.common.SystemConstants;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.utility.MD5Utility;

public class SessionManager {

	protected static final String CLASS_NAME = "SessionManager";
	
	public static ImagskySession getSession(HttpServletRequest request){
		return getSession(request, null);
	}
	public static ImagskySession getSession(HttpServletRequest request, ServletContext context){
		final String METHOD_NAME = "getSession";
		
		PortalLogger.debug(CLASS_NAME+ " " + METHOD_NAME + SystemConstants.LOG_START,request);
		if(request.getSession()==null){
			request.getSession(true);
		}
		ImagskySession session = (ImagskySession)request.getSession(false).getAttribute(SystemConstants.REQ_ATTR_SESSION);
		if(session!=null){
			session.setSessionLastUpdateDate(new java.util.Date());
		} else {
			PortalLogger.debug(CLASS_NAME+ " " + METHOD_NAME + " Existing" ,request);
			session = new ImagskySession();
			session.setLogined(false);
			session.setSessionCreateDate(new java.util.Date());
			session.setSessionLastUpdateDate(new java.util.Date());
			session.setSessionId(MD5Utility.MD5(request.getSession().getId()));
			//session.setSite(getSiteInfo(request));
			//session.setUser(CMAUser.getDummyUser());
		}
		
		if(context!=null){
			session.setContext(context);
		}
		session.setRequest(request);
		PortalLogger.debug(CLASS_NAME+ " " + METHOD_NAME + SystemConstants.LOG_END,request);
		request.getSession(false).setAttribute(SystemConstants.REQ_ATTR_SESSION, session);
		return session;

	}
	
	public static void uploadSessionWithLoginInfo(HttpServletRequest request, boolean isLogin, String loginUserID){
		final String METHOD_NAME = "uploadSessionWithLoginInfo";
		PortalLogger.debug(CLASS_NAME+ " " + METHOD_NAME + SystemConstants.LOG_START);
		ImagskySession session = getSession(request);
		session.setLogined(isLogin);
		//session.setUserid(loginUserID);
		request.getSession(false).setAttribute(SystemConstants.REQ_ATTR_SESSION, session);
		PortalLogger.debug(CLASS_NAME+ " " + METHOD_NAME + SystemConstants.LOG_END);
	}
	
	public static boolean isLogin(HttpServletRequest request){
		ImagskySession session = getSession(request);
		return session.isLogined();
	}
	
	/*
	private static Site getSiteInfo(HttpServletRequest request){
		// TODO: Implement DAO to get site infomation from DB later
		// Current return dummy site for 3T
		return Site.getDummySite();
	}
	*/
}
