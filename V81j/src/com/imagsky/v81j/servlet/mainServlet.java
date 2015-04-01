package com.imagsky.v81j.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagsky.common.ImagskySession;
import com.imagsky.common.SessionManager;
import com.imagsky.common.SiteResponse;
import com.imagsky.exception.BaseException;
import com.imagsky.util.CommonUtil;
import com.imagsky.util.MessageUtil;
import com.imagsky.util.PropertiesUtil;
import com.imagsky.util.V81Util;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.v6.cma.constants.CMAJspMapping;
import com.imagsky.v6.cma.constants.ContentTypeConstants;
import com.imagsky.v6.cma.constants.SystemConstants;
import com.imagsky.v81j.servlet.handler.BaseHandler;
import com.imagsky.v81j.servlet.handler.HandlerFactory;

public class mainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public mainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
        super.init();
        String folderPath = getServletContext().getInitParameter(SystemConstants.CONTEXT_PROPERTIES_FOLDER);
        PropertiesUtil.loadProperties(folderPath);
        PropertiesUtil.getProp().put(SystemConstants.PROP_PROPERTIES_FOLDER, folderPath);

        PropertiesUtil.listProperties();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PortalLogger.debug("*******************************************");
        /**
         * *****************************************
         * ##1 Parameter Init
		 ******************************************
         */
        String appCode = ""; //Application code for choosing action handler
        String action = ""; //Action code

        boolean hasErrorMessage = false; //tell JSP to render error message

        boolean isV7 = false; //TODO: remove later when all change to v7

        String requestURI = null;
        String contextPath = null;
        StringBuffer fullRequestURL = null;
        String forwardURL = "";
        String contextRealPath = null;

        String referer = CommonUtil.null2Empty(request.getHeader("referer"));
        String lang = "zh";//TODO: Default lang : zh

        BaseHandler actionHandler = null;
        SiteResponse actionResponse = null;

        //Incoming Request Timer
        long requestStart = System.currentTimeMillis();
        request.getSession().setAttribute("P_TIMER", new Long(requestStart));


        request.setCharacterEncoding("UTF-8"); // set encoding to UTF-8 to allow UTF-8 input
        requestURI = request.getRequestURI(); //PortalLogger.debug("URI" + requestURI);
        request.setAttribute("requestURI", requestURI);
        contextPath = request.getContextPath(); //PortalLogger.debug("contextPAth" + contextPath);
        if (contextPath.equalsIgnoreCase("/")) {
            contextPath = "";
        }
        request.setAttribute("contextPath", contextPath);
        contextRealPath = this.getServletContext().getRealPath(".");
        request.setAttribute("contextRealPath", contextRealPath);
        fullRequestURL = request.getRequestURL();
        PortalLogger.info("[REQUEST_INFO] " + request.getRemoteAddr() + "|" + fullRequestURL + "|" + CommonUtil.null2Empty(request.getParameter("v")) + "|" + referer, request);
        request.setAttribute("fullRequestURL", fullRequestURL);

        //Facebook login back-redirect
        if (request.getParameterMap() != null && request.getParameterMap().size() > 0) {
            fullRequestURL.append('?').append(request.getQueryString());
        }
        request.setAttribute("fullRequestURLQueryString", fullRequestURL.toString());

        try {
            request.setAttribute(SystemConstants.REQ_ATTR_LANG, lang);

            //Session init
            ImagskySession session = SessionManager.getSession(request, getServletContext());
            //Content Type ini
            ContentTypeConstants.init();
            //AppCode init 20110920 Enhancing URL pattern : eg. /do/CAT...
            //CAT is the "appCode"
            if (requestURI.indexOf("/cma") == 0) {
                isV7 = true;
                //V7: /cma/{Handler}
                //TODO: Move /cma to Constants.propertise
                appCode = requestURI.replaceFirst(contextPath + "/cma", "/").replaceAll("//", "/");
            } else {
                //V6: /do/{Handler}
                appCode = requestURI.replaceFirst(contextPath + SystemConstants.SERVLET_URL, "/");//.replaceAll("/", "");
            }

            try {
                String[] appCodeToken = CommonUtil.stringTokenize(appCode, "/");
                PortalLogger.debug("Init appCode:" + appCode);
                appCode = appCodeToken[0];
                request.setAttribute(SystemConstants.REQ_ATTR_URL_PATTERN, appCodeToken);
                PortalLogger.debug("REQ_ATTR_URL_PATTERN size:" + ((String[]) request.getAttribute(SystemConstants.REQ_ATTR_URL_PATTERN)).length);
            } catch (Exception e) {
                PortalLogger.warn("[SUSPECT_URL] AppCode = " + appCode, request);
            }
            if (requestURI.indexOf(contextPath + SystemConstants.SERVLET_URL) >= 0 && SystemConstants.PUBLIC_VIEW_app_code.indexOf("," + appCode + ",") < 0) {
                //CMA mode (V6)
                request.setAttribute(SystemConstants.REQ_ATTR_APPCODE, appCode);
                action = CommonUtil.null2Empty(request.getParameter(SystemConstants.ACTION_NAME));
                //appCode = requestURI.replaceFirst(contextPath+SystemConstants.SERVLET_URL, "/").replaceAll("/", "");
            } else if (requestURI.indexOf("/cma") == 0) {
                //CMA mode (V7)
                request.setAttribute(SystemConstants.REQ_ATTR_APPCODE, appCode);
                action = V7Util.getCmaActionFromUrlToken(request);
            } else {
                //Public View mode
                appCode = "PUBLIC";
                request.setAttribute(SystemConstants.PUB_FLG, "Y");
                action = CommonUtil.null2Empty(request.getParameter(SystemConstants.ACTION_NAME));
            }
            //action

            request.setAttribute(SystemConstants.REQ_ATTR_ACTION, action);
            /**
             * *****************************************
             * ##2 Check Login
             * - V6: appCode in the list of needLogin properties OR
             * - V7: request URI start with /cma (All CMA V7 require login)
            *	 *******************************************
             */
            if (V6Util.RedirectToLogin(request, appCode, isV7)) { //Compatible wihth V7
                actionResponse = new SiteResponse();
                if (CommonUtil.null2Empty(request.getParameter("action")).indexOf("AJ") >= 0) {
                    actionResponse.setTargetJSP(CMAJspMapping.JSP_LOGIN_AJAX);
                } else {
                    actionResponse.setTargetJSP(CMAJspMapping.JSP_LOGIN);
                }
                request.setAttribute(SystemConstants.REQ_ATTR_DONE_MSG, MessageUtil.getV6Message(lang, "NEED_LOGIN"));
                request.setAttribute("redirectURL", fullRequestURL.toString());
                PortalLogger.info("[REDIRECT] LOGIN|" + actionResponse.getTargetJSP(), request);
                throw new Exception();
            }
            if ("Y".equalsIgnoreCase((String) request.getAttribute("SYS_SECURE"))
                    && (!request.isSecure() && request.getServerPort() != 443)
                    && V6Util.isSSLOn()) {
                actionResponse = new SiteResponse();
                actionResponse.setTargetJSP(CMAJspMapping.JSP_GEN_REDIRECT);
                request.setAttribute("redirectURL", CommonUtil.getSecureFullRequestURL(request));
                PortalLogger.info("[REDIRECT] SECURE|" + actionResponse.getTargetJSP(), request);
                throw new Exception();
            }
            /**
             * *****************************************
             * ##3 Action Handler
		 ******************************************
             */
            PortalLogger.debug("Entering MainServlet : doRequest [ STEP 3. session id = " + session.getSessionId() + " appcode = " + appCode + "; action = " + action, request);
            PortalLogger.info("[APP] " + (isV7 ? "V7|" : "") + appCode + "|" + action, request);
            boolean isValidURL = true;
            if (isValidURL && !appCode.equals("")) {

                long actionStart = System.currentTimeMillis();
                try {
                    // 3.1. Action Handler factory
                    // -------------------------
                    if (isV7) {
                        actionHandler = V7HandlerFactory.createAction("cma", appCode);
                    } else {
                        actionHandler = HandlerFactory.createAction(appCode);
                    }
                    actionResponse = actionHandler.execute(request, response);
                } catch (BaseException e) {
                    // TODO Auto-generated catch block
                    PortalLogger.error("[ STEP 3. ActionHandler exception] ", request, e);
                } // execute action
                long actionTimeTaken = (System.currentTimeMillis() - actionStart);

                PortalLogger.debug("Entering MainServlet : doRequest [ STEP 3. ActionHandler TimeTaken = " + actionTimeTaken + " ms", request);
                PortalLogger.info("[APP_PFM] " + appCode + "|" + action + "|" + actionTimeTaken + "ms", request);
                // 3.2. set SiteResponse to request
                // ----------------------------
                request.setAttribute(SystemConstants.REQ_ATTR_RESPOSNE, actionResponse); // set to request

                // 3.3. override forwardURL from Response Obj
                if (actionResponse.getTargetJSP() != null && !"".equals(actionResponse.getTargetJSP())) {
                    forwardURL = actionResponse.getTargetJSP();
                    PortalLogger.debug("Entering MainServlet : doRequest [ STEP 3.3. Override forwardURL = " + forwardURL, request);
                }
            }

            /**
             * *****************************************
             * ## 4 Dispatch Always return true to redirect as error handling
             * page is not done
		 ******************************************
             */
            if (true) {
                PortalLogger.debug("Entering MainServlet : doRequest [ STEP 4. forward to URL-- Continue]", request);
                // 11. forward to URL
                // ------------------
                PortalLogger.debug("URL forwarding " + forwardURL, request);
                if (CommonUtil.isNullOrEmpty(forwardURL) || forwardURL.trim().equals("/")) {
                    forwardURL = "/jsp/error.jsp";
                }
                response.addHeader("P3P", "cP=\"IDc DSP cOR cURa ADMa OUR IND PHY ONL cOM STA\"");
                this.getServletContext().getRequestDispatcher(forwardURL).forward(request, response);
            } else {
                PortalLogger.debug("Entering MainServlet : doRequest [ STEP 4. forward to URL-- handle error URL]", request);
                // 11. handle error URL
                // --------------------
                //request.removeAttribute(CommonConstants.COMMON_REQUEST_ATTR_FRIENDLY_URL);


                // add AMErrorMsg

                // construct error msg
			/*
                 * StringBuffer invalidURLErrMsg = new StringBuffer();
                 * invalidURLErrMsg.append("root cause of invalid URL is: "); if
                 * (invalidLang) { invalidURLErrMsg.append("Invalid-Language ");
                 * } if (notSupportedLang) {
                 * invalidURLErrMsg.append("Not-Supported-Language "); } if
                 * (invalidFriendlyURL) {
                 * invalidURLErrMsg.append("Invalid-FriendURL "); } if
                 * (invalidResultFriendlyURL) {
                 * invalidURLErrMsg.append("Invalid-FriendURL-returned-by-Action-Class
                 * "); }
                 * invalidURLErrMsg.append(genControllerDetails(fullRequestURL,
                 * actionHandler, channelIdRequested, channelAppCode,
                 * isValidURL)); if (V5CMALogger.isInfoEnabled()) {
                 * V5CMALogger.info(session, invalidURLErrMsg); // log it out
			}
                 */


                // redirect 404 (404 error page JSP be may registered in web.xml)
                //response.sendError(HttpServletResponse.SC_NOT_FOUND); // redirect to 404
                /**
                 * Uncomment this line when error handling page is ready
                 */
//			dispatchUndefinedErrorPage(request, response);
            }
            /*
             * RequestDispatcher dispatcher =
             * getServletContext().getRequestDispatcher("/jsp/main.jsp");
             * dispatcher.forward(request, response);
             */
        } catch (Exception e) {
            RequestDispatcher dispatcher = null;
            PortalLogger.debug("[Exception mainServlet debug] actionResponse "+actionResponse);
            PortalLogger.debug("[Exception mainServlet debug] action "+action);
            if (actionResponse != null && CommonUtil.null2Empty(action).indexOf("AJ") == -1) {
                dispatcher = getServletContext().getRequestDispatcher(actionResponse.getTargetJSP());
                PortalLogger.debug("Entering MainServlet : doRequest [ END. Redirect to Login Page]", request);
            } else {
                dispatcher = getServletContext().getRequestDispatcher("/error.jsp");
                PortalLogger.error("Unknown exception in mainServlet: ", request, e);
            }
            dispatcher.forward(request, response);
        }
    }
}
