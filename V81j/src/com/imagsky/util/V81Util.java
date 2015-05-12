package com.imagsky.util;

import com.imagsky.common.ImagskySession;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.common.PropertiesConstants;
import com.imagsky.common.SiteResponse;
import com.imagsky.common.SystemConstants;
import com.imagsky.common.JspMapping;
import com.imagsky.v81j.domain.App;
//import com.imagsky.v6.cma.servlet.handler.LOGIN_Handler;
import com.imagsky.v81j.domain.Member;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class V81Util {
	public static final String CLASSNAME = "V81Util";
	
    public static boolean isSSLOn() {
        if (PropertiesConstants.get(PropertiesConstants.secure).equalsIgnoreCase("on")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmailOn() {
        if (PropertiesConstants.get(PropertiesConstants.email_on).equalsIgnoreCase("on")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFBLoginCheckForAuctionOn() {
        if (PropertiesConstants.get(PropertiesConstants.bidFacebookCheckLogin).equalsIgnoreCase("on")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean RedirectToLogin(HttpServletRequest req, String actionHandler, boolean isV7){
    	final String METHODNAME = "RedirectToLogin";
    	
        try {
            String[] needLoginModules = CommonUtil.stringTokenize(System.getProperty(PropertiesConstants.needLogin), ",");
            List<String> wordList = Arrays.asList(needLoginModules);
            boolean needLogin = wordList.contains(actionHandler) || isV7;

            //Secure Site
            if (needLogin) {
                req.setAttribute("SYS_SECURE", "Y");
            }

            if (!needLogin) {
                return false;
            } //LOGIN APPCODE and action = activate , not need login CHECK CHECK
            /*** CHECK CHECK
            else if (actionHandler.equalsIgnoreCase("LOGIN") && LOGIN_Handler.ACTI.equalsIgnoreCase(req.getParameter("action"))) {
                return false;
            }***/

            ImagskySession session = (ImagskySession) req.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION);
            if (session == null) {
                return true;
            }
            Member mem = session.getUser();
            if (mem == null || !session.isLogined()) {
                return true;
            }
        } catch (Exception e) {
            PortalLogger.error(CLASSNAME, METHODNAME, "RedirectToLogin Exception :", e);
            e.printStackTrace(System.err);
            return true;
        }
        return false;
    }
    public static boolean RedirectToLogin(HttpServletRequest req,
            String actionHandler) {
            return RedirectToLogin(req, actionHandler, false);
    }

    public static boolean isLogined(HttpServletRequest req) {
        if (req == null || req.getSession() == null) {
            return false;
        }
        ImagskySession session = (ImagskySession) req.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION);
        if (session == null) {
            //	PortalLogger.debug("isLogined>>>SESSION is null");
            return false;
        }
        Member mem = session.getUser();
        if (mem == null || !session.isLogined()) {
            //	PortalLogger.debug("isLogined>>>mem="+ mem);
            //	PortalLogger.debug("isLogined>>>session.isLogined="+session.isLogined());
            return false;
        }
        //PortalLogger.debug("isLogined>>>true");
        return true;
    }

    public static boolean isFBSessionExist(HttpServletRequest req) {
        ImagskySession session = (ImagskySession) req.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION);
        if (session == null) {
            //	PortalLogger.debug("isLogined>>>SESSION is null");
            return false;
        }
        //PortalLogger.debug("FBSession = "+session.getFbAccessToken());
        return !CommonUtil.isNullOrEmpty(session.getFbAccessToken());
    }
    
	public static SiteResponse v8CheckLogin(SiteResponse siteR, HttpServletRequest request){
		if(!V81Util.isLogined(request)){
			siteR.setTargetJSP(JspMapping.LOGIN_PHP);
		}
		return siteR;
	}
	
	/****
	 * After image upload to tmp folder, when the user press save in the module edit page, image file will be moved to the 
	 * user own folder
	 * @param member
	 * @param filename
	 * @return
	 */
	public static boolean imageUploadFileMove(App app, String filename){
		if(app==null)
			return false;
		if(CommonUtil.isNullOrEmpty(filename))
			return false;
		File afile =new File(PropertiesConstants.get(PropertiesConstants.v81_uploadDirectory)+"tmp/"+filename);
		if(!afile.exists())
			return false;
		new File(PropertiesConstants.get(PropertiesConstants.v81_uploadDirectory)+ app.getSys_guid() + "/").mkdir(); //Nothing happen if already exists
		return afile.renameTo(new File(PropertiesConstants.get(PropertiesConstants.v81_uploadDirectory)+ app.getSys_guid() + "/"+filename));
	}
	
	public static boolean imageFileDelete(App app, String filename){
		if(app==null)
			return false;
		if(CommonUtil.isNullOrEmpty(filename))
			return false;
		File afile =new File(PropertiesConstants.get(PropertiesConstants.v81_uploadDirectory) + app.getSys_guid() +"/"+filename);
		return afile.delete();
	}
}
