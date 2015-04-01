package com.imagsky.util;

import java.util.*;

import java.io.*;
import javax.servlet.http.HttpServletRequest;
import com.imagsky.common.SystemConstants;
import com.imagsky.common.SiteErrorMessage;
import com.imagsky.util.logger.*;

public class PropertiesUtil {

	public static void loadProperties(HttpServletRequest request) {
		if (request.getAttribute(SystemConstants.REQ_ATTR_PROPERTIES) != null) {
			System.setProperties((Properties) request.getAttribute(SystemConstants.REQ_ATTR_PROPERTIES));
		}
	}

	public static Properties getProp() {
		return System.getProperties();
	}

	public static void loadProperties(String path) {
		try {
			System.out.println("Loading : " + path + SystemConstants.SITE_PROP_FILENAME + ".properties");
			// ProtalLogger.debug("Loading : "+path + PropertiesConstants.WEBXML_SITE_PROP+".properties");
			Properties prop = System.getProperties();
			prop.load(new FileInputStream(path + SystemConstants.SITE_PROP_FILENAME + ".properties"));

			Iterator it = prop.keySet().iterator();
			String key = "";
			while (it.hasNext()) {
				key = (String) it.next();
				System.setProperty(key, prop.getProperty(key));
				// ProtalLogger.debug(key + "=" + prop.getProperty(key));
			}
			System.setProperties(prop);

		} catch (IOException e) {
			System.err.println("Cannot load properties file : " + path + SystemConstants.SITE_PROP_FILENAME + ".properties");
		}
	}


	// JSP Properties
	public static String getJspProperty(HttpServletRequest request, String key) {
		loadProperties(request);
		return System.getProperties().getProperty(key);
	}

	// Get Special field
	public static String getDomain() {
		String platform = System.getProperties().getProperty("sys.platform");
		System.out.println("sys.platform  = " + platform);
		return System.getProperties().getProperty("sys." + platform + ".domain");
	}

	public static void setProperties(Properties prop) {
		System.setProperties(prop);
	}

	public static String getFrontendDomain() {
		String platform = System.getProperties().getProperty("sys.platform");
		return System.getProperties().getProperty("sys." + platform + ".frontend.domain");
	}

	public static String getFrontendDomain(HttpServletRequest request) {
		loadProperties(request);
		return getFrontendDomain();
	}

	public static void listProperties() {
		Iterator<Object> it = System.getProperties().keySet().iterator();
		String key = "";
		while (it.hasNext()) {
			key = (String) it.next();
			PortalLogger.debug(key + "=" + System.getProperties().getProperty(key));
		}
	}
}
