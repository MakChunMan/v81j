package com.imagsky.common;

import com.imagsky.util.CommonUtil;

public class PropertiesConstants {

	public static final String needLogin = "needLogin";

	public static final String externalHost = "externalHost";

	public static final String staticContextRoot = "staticContextRoot";

	public static final String uploadContextRoot = "uploadContextRoot";

	public static final String uploadDirectory = "uploadDirectory";

	public static final String urlblacklist = "urlblacklist";

	public static final String mainSiteGUID = "mainsite";

	public static final String smtp = "host_smtp";

	public static final String salesAddress = "email_sales";

	public static final String searchRowPerPage = "search_rowperpage";

	public static final String secure = "secure";

	public static final String bulkOrderOn = "bulkOrderOn";

	public static final String boboOn = "boboOn";

	public static final String auctionOn = "auctionOn";

	public static final String paypalFlg = "paypal";

	public static final String cashlimit = "cash.request_without_charge";

	public static final String home_newshop = "home_newshop"; // on / off : switch to display new shops list in main page

	public static final String email_on = "email_on";

	public static final String bidFacebookCheckLogin = "bidFacebookCheckLogin";
	
	/*** V81 ***/
	public static final String v81_uploadDirectory = "v81_uploadDirectory";

	/*** FACEBOOK Properties Constants ***/
	public static final String fb_appid = "fb_appid"; // Facebook App ID
	public static final String fb_appsecret = "fb_appsecret"; // Facebook App Password
	public static final String fb_tokenurl = "fb_tokenurl"; // URL to obtain access token from FB
	public static final String fb_graph = "fb_graph"; // FB Graph API
	public static final String fb_namespace = "fb_namespace";

	/*** RBT Properties Constants ***/
	public static final String rbt_metTargetRate = "rbt_metTargetRate"; // Rate to perform rbt after the price already met the target
	public static final String rbt_strtCountDown = "rbt_strtCountDown"; // Number of minutes to start the count down logic

	public static String get(String key) {
		return System.getProperty(key);
	}

	public static String[] getList(String key) {
		return CommonUtil.stringTokenize(System.getProperty(key), ",");
	}
}
