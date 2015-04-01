package com.imagsky.v81j.servlet.handler;

import com.imagsky.exception.*;
import com.imagsky.util.logger.*;
/**
 *
 */
public class HandlerFactory {

	private static final String CLASSNAME = "HandlerFactory";
	private static final String ACTION_PACKAGE = "com.imagsky.v81j.servlet.handler";
	private static final String ACTION_CLASS_NAME_SUFFIX = "_Handler";
	private static final String SITEACTION_PACKAGE(String site){
		return "com.imagsky."+site+".handler";
	}
	
	public static BaseHandler createAction(String appCode) throws UnsupportedAppCodeException {
		final String METHODNAME = "createAction";
		String fullClazzName = ACTION_PACKAGE + "." + appCode + ACTION_CLASS_NAME_SUFFIX;
		
		if (appCode != null && appCode.trim().length() != 0) {
			try {
				PortalLogger.debug(CLASSNAME + " "+ METHODNAME + " "+ " [ Reference Handler : "+ fullClazzName+"]");
				Class clazz = Class.forName(fullClazzName);
				Object actionInstance = clazz.newInstance();
				
				return (BaseHandler) actionInstance;
			} catch (Exception e) {
				throw new UnsupportedAppCodeException(appCode, e);				
			}
		} else {
			return new DefaultHandler();
		}
	}
	
	public static BaseHandler createAction(String appCode, String site) throws UnsupportedAppCodeException {
	 
		final String METHODNAME = "createAction";
		String fullClazzName = SITEACTION_PACKAGE(site) + "." + appCode + ACTION_CLASS_NAME_SUFFIX;
		
		if(site == null) {
			return createAction(appCode);
		} else if (appCode != null && appCode.trim().length() != 0) {
			try {
				PortalLogger.debug(CLASSNAME + " "+ METHODNAME + " "+ " [ Reference Site Handler : "+ fullClazzName+"]");
				Class clazz = Class.forName(fullClazzName);
				Object actionInstance = clazz.newInstance();
				
				return (BaseHandler) actionInstance;
			} catch (Exception e) {
				throw new UnsupportedAppCodeException(appCode, e);				
			}
		} else {
			return new DefaultHandler();
		}
	}
}
