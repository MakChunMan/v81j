package com.imagsky.v81j.biz;

import com.imagsky.exception.UnsupportedModuleBizException;
import com.imagsky.util.logger.PortalLogger;


public class ModuleBizFactory {

	private static final String CLASSNAME = "ModuleBizFactory";
	private static final String MOD_BIZ_PACKAGE = "com.imagsky.v81j.biz";
	private static final String ACTION_CLASS_NAME_SUFFIX = "Biz";
	
	public static BaseModuleBiz createBusiness(String modTypeName) throws UnsupportedModuleBizException {
		final String METHODNAME = "createAction";
		//Change the first character to upper case
		modTypeName = modTypeName.substring(0,1).toUpperCase() + modTypeName.substring(1); 
		String fullClazzName = MOD_BIZ_PACKAGE + "." + modTypeName + ACTION_CLASS_NAME_SUFFIX;
		
		if (modTypeName != null && modTypeName.trim().length() != 0) {
			try {
				PortalLogger.debug(CLASSNAME + " "+ METHODNAME + " "+ " [ Reference Business Class : "+ fullClazzName+"]");
				Class clazz = Class.forName(fullClazzName);
				Object actionInstance = clazz.newInstance();
				
				return (BaseModuleBiz) actionInstance;
			} catch (Exception e) {
				throw new UnsupportedModuleBizException(modTypeName, e);				
			}
		} else {
			return new DefaultModuleBiz();
		}
	}
	
}
