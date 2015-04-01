package com.imagsky.util;

import com.imagsky.common.ImagskySession;
import com.imagsky.util.logger.cmaLogger;
import com.imagsky.utility.MD5Utility;
import com.imagsky.v6.cma.constants.ContentTypeConstants;
import com.imagsky.v6.cma.constants.PropertiesConstants;
import com.imagsky.v6.cma.constants.SystemConstants;
import com.imagsky.v6.cma.servlet.handler.LOGIN_Handler;
import com.imagsky.v6.dao.NodeDAO;
import com.imagsky.v6.domain.ContentType;
import com.imagsky.v6.domain.Member;
import com.imagsky.v6.domain.Node;
import com.imagsky.v6.domain.SysObject;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class V81Util {

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
            } //LOGIN APPCODE and action = activate , not need login
            else if (actionHandler.equalsIgnoreCase("LOGIN") && LOGIN_Handler.ACTI.equalsIgnoreCase(req.getParameter("action"))) {
                return false;
            }

            ImagskySession session = (ImagskySession) req.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION);
            if (session == null) {
                return true;
            }
            Member mem = session.getUser();
            if (mem == null || !session.isLogined()) {
                return true;
            }
        } catch (Exception e) {
            cmaLogger.error("RedirectToLogin Exception :", e);
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
            //	cmaLogger.debug("isLogined>>>SESSION is null");
            return false;
        }
        Member mem = session.getUser();
        if (mem == null || !session.isLogined()) {
            //	cmaLogger.debug("isLogined>>>mem="+ mem);
            //	cmaLogger.debug("isLogined>>>session.isLogined="+session.isLogined());
            return false;
        }
        //cmaLogger.debug("isLogined>>>true");
        return true;
    }

    public static boolean isFBSessionExist(HttpServletRequest req) {
        ImagskySession session = (ImagskySession) req.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION);
        if (session == null) {
            //	cmaLogger.debug("isLogined>>>SESSION is null");
            return false;
        }
        //cmaLogger.debug("FBSession = "+session.getFbAccessToken());
        return !CommonUtil.isNullOrEmpty(session.getFbAccessToken());
    }

    public static boolean isMainsite(HttpServletRequest req) {
        Member thisShop = (Member) req.getSession().getAttribute(SystemConstants.PUB_SHOP_INFO);
        if (thisShop != null) {
            //cmaLogger.debug((PropertiesConstants.get(PropertiesConstants.mainSiteGUID)));
            //cmaLogger.debug(thisShop.getSys_guid());
            //cmaLogger.debug("isMainsite:"+ (PropertiesConstants.get(PropertiesConstants.mainSiteGUID)).equalsIgnoreCase(thisShop.getSys_guid()));
            if (CommonUtil.isNullOrEmpty(thisShop.getSys_guid())) {
                return true;
            } else {
                return (PropertiesConstants.get(PropertiesConstants.mainSiteGUID)).equalsIgnoreCase(thisShop.getSys_guid());
            }
        } else {
            //cmaLogger.debug("isMainsite:false");
            return false;
        }
    }

    public static boolean isMainsiteLogin(Member member) {
        if (member == null) {
            return false;
        }
        return member.getSys_guid().equalsIgnoreCase(PropertiesConstants.get(PropertiesConstants.mainSiteGUID));
    }

    public static boolean isBoboModuleOn() {
        String propertiesOnOff = PropertiesConstants.get(PropertiesConstants.boboOn);
        if (propertiesOnOff == null || !"on".equalsIgnoreCase(propertiesOnOff)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isBulkOrderModuleOn() {
        String propertiesOnOff = PropertiesConstants.get(PropertiesConstants.bulkOrderOn);
        if (propertiesOnOff == null || !"on".equalsIgnoreCase(propertiesOnOff)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isAuctionModuleOn() {
        String propertiesOnOff = PropertiesConstants.get(PropertiesConstants.auctionOn);
        if (propertiesOnOff == null || !"on".equalsIgnoreCase(propertiesOnOff)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isNewshopListOn() {
        String propertiesOnOff = PropertiesConstants.get(PropertiesConstants.home_newshop);
        if (propertiesOnOff == null || !"on".equalsIgnoreCase(propertiesOnOff)) {
            return false;
        } else {
            return true;
        }
    }

    public static Node autoAssociate(Object obj, Member owner, String[] keywordDescription, String nodeUrl) {
        Class clazz = obj.getClass();
        try {
            cmaLogger.debug("Start AutoAssociate");
            SysObject sysobj = (SysObject) obj;
            Node node = new Node();
            NodeDAO dao = NodeDAO.getInstance();

            node.setNod_contentGuid(sysobj.getSys_guid());
            String[] tmpToken = CommonUtil.stringTokenize(clazz.getName(), ".");
            String contentTypeName = tmpToken[tmpToken.length - 1];

            ContentType ct = ContentTypeConstants.getCT(contentTypeName);
            cmaLogger.debug("ContentType: " + contentTypeName);
            node.setNod_contentType(ct.getSys_guid());
            node.setNod_owner(owner.getSys_guid());
            if (!CommonUtil.isNullOrEmpty(nodeUrl)) {
                node.setNod_url(checkUniqueURL(nodeUrl));
            } else {
                node.setNod_url(genUniqueNodeURL(sysobj, ct, owner));
            }
            node.setNod_bannerurl(null);
            if (keywordDescription != null && keywordDescription.length == 2) {
                if (!CommonUtil.isNullOrEmpty(keywordDescription[0])) {
                    node.setNod_keyword(keywordDescription[0]);
                }
                if (!CommonUtil.isNullOrEmpty(keywordDescription[1])) {
                    node.setNod_description(keywordDescription[1]);
                }
            }
            dao.create(node);
            return node;
        } catch (Exception e) {
            cmaLogger.error("AutoAssoicate Fail: ", e);
            return null;
        }
    }

    public static Node autoAssociate(Object obj, Member owner, String[] keywordDescription) {
        return autoAssociate(obj, owner, keywordDescription, null);
    }

    public static Node autoAssociate(Object obj, Member owner) {
        String[] keywordDescription = new String[]{null, null};
        return autoAssociate(obj, owner, keywordDescription);
    }

    public static boolean disassociate(String contentGuid, Member owner) {

        try {
            Node node = new Node();
            node.setNod_contentGuid(contentGuid);
            node.setNod_owner(owner.getSys_guid());

            NodeDAO dao = NodeDAO.getInstance();
            Node tmpNode;
            List nodeList = dao.CNT_findListWithSample(node);
            if(!CommonUtil.isNullOrEmpty(nodeList)){
            	for(Object obj : nodeList){
            		tmpNode = (Node)obj;
            		dao.CNT_delete(tmpNode);
            	}
            }
            return true;
        } catch (Exception e) {
            cmaLogger.error("disassociate Fail: ", e);
            return false;
        }
    }

    private static String checkUniqueURL(String url) {
        int x = 0;
        NodeDAO dao = NodeDAO.getInstance();
        Node node;
        List al = null;
        try {
            while (al == null || al.size() > 0) {
                node = new Node();
                node.setNod_url(url.replace(".do", "") + (x == 0 ? "" : x + "") + ".do");
                al = dao.findListWithSample(node);
                if (al.size() == 0) {
                    return (url.replace(".do", "") + (x == 0 ? "" : x + "") + ".do");
                }
                x++;
            }
        } catch (Exception e) {
            cmaLogger.error("checkUniqueURL Error: " + url, e);
            return null;
        }
        return null;
    }

    private static String genUniqueNodeURL(Object obj, ContentType ct, Member owner) {
        Class clazz = obj.getClass();
        int x = 0;
        boolean go = true;
        try {
            SysObject sysobj = (SysObject) obj;
            NodeDAO dao = NodeDAO.getInstance();
            String tmpURL = null;
            Node node;
            while (go) {
                node = new Node();
                tmpURL = MD5Utility.MD5(sysobj.getSys_guid().substring(0, 5) + x).toLowerCase().substring(0, 6);
                tmpURL = "/" + ct.getCma_name().toLowerCase() + "_" + tmpURL
                        + SystemConstants.PUBLIC_SUFFIX;
                node.setNod_owner(owner.getSys_guid());
                node.setNod_url(tmpURL);
                List al = dao.findListWithSample(node);
                if (al.size() == 0) {
                    go = false;
                }
                x++;
            }
            return tmpURL;
        } catch (Exception e) {
            cmaLogger.error("getUniqueNodeURL Error", e);
            return null;
        }
    }

    public static ArrayList<String> genHotKeySearch(String lang) {
        String[] keywords = CommonUtil.stringTokenize(MessageUtil.getV6Message(lang, "COMMON_HOT_KEYWORD"), ";");
        ArrayList<String> returnList = new ArrayList<String>();

        if (keywords != null && keywords.length > 0) {
            for (int x = 0; x < keywords.length; x++) {
                try {
                    returnList.add("<a href=\"/do/SEARCH?keyw=" + URLEncoder.encode(keywords[x], "UTF-8") + "&source=hkey\">" + keywords[x] + "</a>");
                } catch (Exception e) {
                    cmaLogger.error(keywords[x], e);
                }
            }
        }
        return returnList;
    }
}
