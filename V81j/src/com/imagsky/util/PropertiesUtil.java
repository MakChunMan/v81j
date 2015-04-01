package com.imagsky.util;

import java.util.*;

import java.io.*;
import javax.servlet.http.HttpServletRequest;
//import com.imagsky.v5.constants.PropertiesConstants;
import com.imagsky.v6.cma.constants.SystemConstants;
import com.imagsky.v6.dao.BulkOrderDAO;
//import com.imagsky.v6.domain.BulkOrder;
import com.imagsky.common.SiteErrorMessage;
import com.imagsky.exception.BaseDBException;
import com.imagsky.util.logger.*;
import com.imagsky.v6.domain.BulkOrderItem;
//import com.imagsky.v5.util.logger.V5CMALogger;
//import com.imagsky.v5cma.dao.STRLoaderDAO;

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
			// properties = new Properties();
			propertiesPath = path;
			System.out.println("Loading : " + path + SystemConstants.SITE_PROP_FILENAME + ".properties");
			// V5CMALogger.debug("Loading : "+path + PropertiesConstants.WEBXML_SITE_PROP+".properties");
			Properties prop = System.getProperties();
			prop.load(new FileInputStream(path + SystemConstants.SITE_PROP_FILENAME + ".properties"));

			Iterator it = prop.keySet().iterator();
			String key = "";
			while (it.hasNext()) {
				key = (String) it.next();
				System.setProperty(key, prop.getProperty(key));
				// V5CMALogger.debug(key + "=" + prop.getProperty(key));
			}
			System.setProperties(prop);

		} catch (IOException e) {
			System.err.println("Cannot load properties file : " + path + SystemConstants.SITE_PROP_FILENAME + ".properties");
		}
	}

	public static void loadBulkOrder(String lang) {
		BulkOrderDAO dao = BulkOrderDAO.getInstance();

		bulkOrderErrorList = new ArrayList<SiteErrorMessage>();

		try {
			List aList = dao.findAll(true, true);// Online, Refresh Cache
			if (aList == null || aList.size() <= 0) {
				cmaLogger.error("[ENQUIRY_BULK_ORDER_EMPTY] " + MessageUtil.getV6Message(lang, "ENQUIRY_BULK_ORDER_EMPTY"));
				bulkOrderErrorList.add(new SiteErrorMessage("ENQUIRY_BULK_ORDER_EMPTY"));
			} else {
				bulkOrderAL = new ArrayList();
				bulkOrderAL.addAll(new ArrayList<BulkOrderItem>(aList));
				sellItemBOMap = new HashMap<String, BulkOrderItem>();
				Iterator it = bulkOrderAL.iterator();
				BulkOrderItem dummy = null;
				while (it.hasNext()) {
					dummy = (BulkOrderItem) it.next();
					sellItemBOMap.put(dummy.getSellitem().getSys_guid(), dummy);
				}
			}

		} catch (BaseDBException e) {
			cmaLogger.error("[ENQUIRY_BULK_ORDER_ERROR] " + MessageUtil.getV6Message(lang, "ENQUIRY_BULK_ORDER_ERROR"));
			bulkOrderErrorList.add(new SiteErrorMessage("ENQUIRY_BULK_ORDER_ERROR"));
		}
	}

	public static BulkOrderItem getBulkOrderItem(String contentGuid) {
		if (sellItemBOMap == null) {
			loadBulkOrder("zh");
		}
		if (sellItemBOMap == null)
			return null;
		return (BulkOrderItem) sellItemBOMap.get(contentGuid);
	}

	// private static HashMap strMessageMap; //TODO: SEEMS not use
	private static String propertiesPath;
	private static ArrayList<BulkOrderItem> bulkOrderAL;
	private static ArrayList<SiteErrorMessage> bulkOrderErrorList;
	private static Map<String, BulkOrderItem> sellItemBOMap;

	public static ArrayList<SiteErrorMessage> getBulkOrderErrorList() {
		return bulkOrderErrorList;
	}

	private static String defaultLang = "zh";// Get Parameter from STR and use default lang (Share to all lang)

	public static ArrayList<BulkOrderItem> getBulkOrderList() {
		if (bulkOrderAL == null) {
			loadBulkOrder(defaultLang);
		}
		return bulkOrderAL;
	}

	public static ArrayList<BulkOrderItem> getBulkOrder(HttpServletRequest req) {
		Integer boid = CommonUtil.isNullOrEmpty(req.getParameter("boid")) ? null : new Integer(req.getParameter("boid"));
		if (boid == null)
			return getBulkOrderList();
		else {
			BulkOrderDAO dao = BulkOrderDAO.getInstance();
			try {
				BulkOrderItem dummy = new BulkOrderItem();
				cmaLogger.debug("getBulkOrder by boid = " + boid);
				dummy.setId(new Long(boid));
				List aList = dao.findListWithSample(dummy);
				cmaLogger.debug("getBulkOrder by boid : " + aList.size());
				if (aList != null && aList.size() > 0)
					return new ArrayList<BulkOrderItem>(aList);
				else
					return null;
			} catch (Exception e) {
				cmaLogger.error("PropertiesUtil.getBulkOrder error [ID=" + req.getParameter("boid") + "]", req, e);
			}
			return null;
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
			// System.setProperty(key, getProp().getProperty(key));
			cmaLogger.debug(key + "=" + System.getProperties().getProperty(key));
		}
	}
}
