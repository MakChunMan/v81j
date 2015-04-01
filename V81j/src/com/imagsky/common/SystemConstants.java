package com.imagsky.common;

public class SystemConstants {

	//Request Attribute name
	//System Parameter
	public static final String REQ_ATTR_PROPERTIES = "REQ_ATTR_PROPERTIES";
	public static final String REQ_ATTR_APPCODE = "portalAppCode";
	public static final String REQ_ATTR_ACTION = "portalAppAction";
	public static final String REQ_ATTR_SESSION = "portalSession";
	public static final String REQ_ATTR_RESPOSNE = "portalResponse";
	public static final String REQ_ATTR_LANG = "portalLang";
	public static final String REQ_ATTR_URL = "portalUrl";
	public static final String REQ_ATTR_URL_PATTERN = "portalUrlPattern";
	public static final String REQ_ATTR_REMINDER = "portalReminder";
        
    public static final String REQ_ATTR_JSONDATA = "portalJsonData";
        
	//JSP setting
	public static final String REQ_ATTR_DONE_MSG = "portalDoneMsg";
	public static final String REQ_ATTR_INC_PAGE = "portalIncludePage";
	
	public static final String REQ_ATTR_GENERAL_TITLE = "portalGenTtiel"; //title of general.jsp
	public static final String REQ_ATTR_GENERAL_MSG = "portalGenMsg"; //content of general.jsp
	public static final String REQ_ATTR_GENERAL_PARAM = "portalGenParam"; //content msg param
	//Return Objects
	public static final String REQ_ATTR_OBJ_LIST = "portalList";
	public static final String REQ_ATTR_OBJ = "portalObject";
    public static final String REQ_ATTR_OBJ_BO = "portalObjBO"; //BulkOrderItem
	
	
	public static final String CONTEXT_PROPERTIES_FOLDER = "contextPropertiesFolder";
	public static final String ACTION_NAME = "action";
	public static final String SERVLET_URL = "/do/";
	public static final String PUBLIC_SUFFIX = ".do";
	public static final String PUBLIC_VIEW_app_code = ",PUBLIC,SALES,";
	public static final String SYSTEM_BANNER_PREFIX = "SYSBNR_";
	
	public static final String PUBLIC_AJAX_ITEM_TEMPLATE_PREFIX = "aj_"; //Usage: this prefix + contenttype.CTTP_ITEM_TEMPLATE
	public static final String PUBLIC_AJAX_FLG = "PAJ";// "1".equalsIgnoreCase(request.getParameter(SystemConstants.PUBLIC_AJAX_FLG)) => Use AJAX Item Template
	
	//Properties Filename
	public static final String SITE_PROP_FILENAME = "v6site";
	//Properties Key
	public static final String PROP_PROPERTIES_FOLDER = "sys.propFolder";
	
	//DB
	public static final String PERSISTENCE_NAME = "V6PERSISTENCE";//Ref to persistence.xml
	public static final String DB_ASC = " asc";
	public static final String DB_DESC = " desc";
	
	//Log label
	public static final String LOG_START = "[ START ]";
	public static final String LOG_END = "[ END ]";
	
	//V6
	public static final String DB_DS_PROPERTIES_NAME = "v6.1_db";
	public static final String DB_DS_DATABASE_NAME = "v6";
	
	//NodeMap key type
	public static final String NODMAP_KEY_C_GUID = "contentGuid";
	public static final String NODMAP_KEY_N_URL = "nodeUrl";
	
	public static final String HTTPS = "https://";
	public static final String HTTP = "http://";

	public static final String MAIN_SITE_URL = "main";
	public static final String DEFAULT_LANG = "zh";
	
	//Cart setting
	public static final int PUB_CART_BO_MAX_ITEM = 5;
}
