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
import com.imagsky.common.JspMapping;
import com.imagsky.common.SystemConstants;
import com.imagsky.v81j.servlet.handler.BaseHandler;
import com.imagsky.v81j.servlet.handler.HandlerFactory;

/***
 * MainServlet Dispatcher 
 * (1) Incoming URL 
 * 		eg. /portal/PAGE/LOGIN.do
 * @author jasonmak
 *
 */
public class mainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public mainServlet() {
        super();
    }

    public void init() throws ServletException {
        super.init();
        System.out.println("v81- mainServlet init: [START]");
        String folderPath = getServletContext().getInitParameter(SystemConstants.CONTEXT_PROPERTIES_FOLDER);
        System.out.println("v81- mainServlet init: folderPath = "+ folderPath);
        PropertiesUtil.loadProperties(folderPath);
        PropertiesUtil.getProp().put(SystemConstants.PROP_PROPERTIES_FOLDER, folderPath);
        PropertiesUtil.listProperties();
        System.out.println("v81- mainServlet init: [END]");
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
            //TODO: To be removed -- Content Type ini
            //ContentTypeConstants.init();
            
            /************
           	APPCODE : First token
           	ACTION : Second token
            *************/
           appCode = requestURI.replaceFirst(contextPath, ""); 							//remove /portal
           appCode = appCode.replaceFirst(SystemConstants.PUBLIC_SUFFIX,""); 	//remove .do

            try {
                String[] appCodeToken = CommonUtil.stringTokenize(appCode, "/");
                PortalLogger.debug("Init appCode:" + appCode);
                appCode = appCodeToken[0];
                request.setAttribute(SystemConstants.REQ_ATTR_APPCODE, appCode);
                request.setAttribute(SystemConstants.REQ_ATTR_URL_PATTERN, appCodeToken);
                PortalLogger.debug("REQ_ATTR_URL_PATTERN size:" + ((String[]) request.getAttribute(SystemConstants.REQ_ATTR_URL_PATTERN)).length);
                if(appCodeToken.length>1){
                	action = CommonUtil.null2Empty(request.getParameter(SystemConstants.ACTION_NAME));
                	request.setAttribute(SystemConstants.REQ_ATTR_ACTION, action);
                }
            } catch (Exception e) {
                PortalLogger.warn("[SUSPECT_URL] AppCode = " + appCode, request);
            }
            
            /**
             * *****************************************
             * ##2 Check Login
             * - V6: appCode in the list of needLogin properties OR
             * - V7: request URI start with /cma (All CMA V7 require login)
            *	 *******************************************
             */
            //TODO: V81 seems to check login in servlet handle
            /***
            if (V81Util.RedirectToLogin(request, appCode)) { //Compatible wihth V7
                actionResponse = new SiteResponse();
                actionResponse.setTargetJSP(JspMapping.JSP_LOGIN);
                request.setAttribute(SystemConstants.REQ_ATTR_DONE_MSG, MessageUtil.getV8Message(lang, "NEED_LOGIN"));
                request.setAttribute("redirectURL", fullRequestURL.toString());
                PortalLogger.info("[REDIRECT] LOGIN|" + actionResponse.getTargetJSP(), request);
                throw new Exception();
            }***/
            if ("Y".equalsIgnoreCase((String) request.getAttribute("SYS_SECURE"))
                    && (!request.isSecure() && request.getServerPort() != 443)
                    && V81Util.isSSLOn()) {
                actionResponse = new SiteResponse();
                actionResponse.setTargetJSP(JspMapping.JSP_GEN_REDIRECT);
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
            boolean isValidURL = true;
            if (isValidURL && !appCode.equals("")) {

                long actionStart = System.currentTimeMillis();
                try {
                    // 3.1. Action Handler factory
                    // -------------------------
                    actionHandler = HandlerFactory.createAction(appCode);
                    actionResponse = actionHandler.execute(request, response);
                } catch (BaseException e) {
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
            boolean hasError = false;
            if (!hasError) {
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
                throw (new Exception());
                // redirect 404 (404 error page JSP be may registered in web.xml)
                //response.sendError(HttpServletResponse.SC_NOT_FOUND); // redirect to 404
                //dispatchUndefinedErrorPage(request, response);
            }
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
