/****
 * 2014-08-11 Ajax Page Handler for New Mobile Web Site
 */
package com.imagsky.v81j.servlet.handler;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagsky.common.ImagskySession;
import com.imagsky.common.SiteErrorMessage;
import com.imagsky.common.SiteResponse;
import com.imagsky.common.JspMapping;
import com.imagsky.common.PropertiesConstants;
import com.imagsky.common.SystemConstants;
import com.imagsky.exception.BaseDBException;
import com.imagsky.exception.BaseException;
import com.imagsky.util.CommonUtil;
import com.imagsky.util.MailUtil;
import com.imagsky.util.MessageUtil;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.utility.Base64;
import com.imagsky.utility.MD5Utility;
import com.imagsky.v81j.biz.MemberBiz;
import com.imagsky.dao.MemberDAO;
import com.imagsky.v81j.biz.AppBiz;
import com.imagsky.v81j.domain.Member;


public class PAGE_Handler extends BaseHandler  {

	private String[] appCodeToken;
	
    protected static final String CLASS_NAME = "PAGE_Handler.java";
    private String thisLang = null;
	
	@Override
	public SiteResponse execute(HttpServletRequest request, HttpServletResponse response) throws BaseException {
		
		SiteResponse thisResp = super.createResponse();
        PortalLogger.debug(CLASS_NAME + " " + SystemConstants.LOG_START);

        //No need check login currently
        //thisMember = ((ImagskySession) request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getUser();
        thisLang = (String) request.getAttribute(SystemConstants.REQ_ATTR_LANG);
        
		//tokenized URL 
		appCodeToken = (String[])request.getAttribute(SystemConstants.REQ_ATTR_URL_PATTERN); //[0]: "PAGE", [1]:"Pages"
		
		/****
		- 1.1 Show Email Main page
		- If Register, show input password page (1.2) and chapta
		- if Login, show login password page (1.3)
		****/
		
		if(appCodeToken.length<2){
			thisResp = null;
		} else if (appCodeToken[1].equalsIgnoreCase(Pages.INPUT_LOGIN.name())) {
            thisResp = showLogin(request, response); //ajax;
		} else if (appCodeToken[1].equalsIgnoreCase(Pages.DO_LOGIN.name())) {
			thisResp = doLogin(request, response);
		} else if (appCodeToken[1].equalsIgnoreCase(Pages.PUB_MAIN.name())) {
			thisResp = showMain(request, response);
		} else if (appCodeToken[1].equalsIgnoreCase(Pages.DO_LOGOUT.name())) {
			thisResp = doLogout(request, response);
		} else if (appCodeToken[1].equalsIgnoreCase(Pages.INPUT_REMINDER.name())) {
			thisResp = showReminder(request, response); //ajax
		} else if (appCodeToken[1].equalsIgnoreCase(Pages.DO_REMINDER.name())) {
			thisResp = doReminder(request, response); //ajax
		} else if (appCodeToken[1].equalsIgnoreCase(Pages.INPUT_REGISTER.name())) {
			thisResp = showRegister(request, response); //ajax
		} else if (appCodeToken[1].equalsIgnoreCase(Pages.DO_REGISTER.name())) {
			thisResp = doRegister(request, response); //ajax
		}
		
		
		return thisResp;
	}
	
	private SiteResponse doRegister(HttpServletRequest request, HttpServletResponse response) {
		SiteResponse thisResp = super.createResponse();
		
		MemberDAO dao = MemberDAO.getInstance();
		String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG);
		
		PortalLogger.debug("[doRegister START]");
		
		try{
			Member newMember = new Member();
			//NOT USE USER NAME
			newMember.setMem_login_email(CommonUtil.null2Empty(request.getParameter("register-email")));
			newMember.setMem_passwd(MD5Utility.MD5(CommonUtil.null2Empty(request.getParameter("register-password"))));
			//newMember.setMem_firstname(CommonUtil.null2Empty(request.getParameter("REG_MEM_FIRSTNAME")));
			//newMember.setMem_lastname(CommonUtil.null2Empty(request.getParameter("REG_MEM_LASTNAME")));
			//newMember.setMem_shopname(CommonUtil.null2Empty(request.getParameter("REG_SHOPNAME")));
			newMember.setPackage_type(CommonUtil.null2Empty(request.getParameter("PACKAGE_TYPE")));
			
			if(CommonUtil.isNullOrEmpty(newMember.getMem_login_email())){
				thisResp.addErrorMsg(new SiteErrorMessage("REG_ID_EMPTY"));
			} else if(!CommonUtil.isValidEmailAddress(newMember.getMem_login_email())){
				thisResp.addErrorMsg(new SiteErrorMessage("REG_ID_INVALID"));
			}
			
			if(request.getParameter("register-password-verify")!=null){
				if(!request.getParameter("register-password-verify").equalsIgnoreCase(request.getParameter("register-password"))){
					thisResp.addErrorMsg(new SiteErrorMessage("REG_PWD_NOT_EQUAL"));
				}
			}
			
			if(!thisResp.hasError()){
				Member enqMember = new Member();
				enqMember.setMem_login_email(newMember.getMem_login_email());
				List aList = (List)dao.findListWithSample(enqMember);
				if(aList!=null && aList.size()>0){
					thisResp.addErrorMsg(new SiteErrorMessage("REG_ID_ALREADY_EXIST"));
				}
				//ByPass URL Check for checkout page register
				/*** 2014-08-15
				if(!isCheckoutReg){
					if(dao.validURL(newMember)!=null){
						thisResp.addErrorMsg(new SiteErrorMessage("REG_URL_ALREADY_EXIST"));
					}
					int find = Arrays.binarySearch(PropertiesConstants.getList(PropertiesConstants.urlblacklist), newMember.getMem_shopurl());
					if(find>=0){
						thisResp.addErrorMsg(new SiteErrorMessage("REG_URL_ALREADY_EXIST"));
					}
				}***/
				//initialize new member
				newMember.setSys_is_live(false); //Need activate email
				newMember.setSys_is_node(false);
				newMember.setSys_is_published(false);
				newMember.setMem_max_sellitem_count(30);
				//20140820 init some number
				newMember.setMem_cash_balance(new Double(0));
				newMember.setMem_meatpoint(new Integer(0)); //--
				PortalLogger.debug("setMem_meatpoint:"+ newMember.getMem_meatpoint());
				
				newMember.setSys_create_dt(new java.util.Date());
				newMember.setSys_creator("V8 SYSTEM");
				newMember.setSys_update_dt(new java.util.Date());
				newMember.setSys_updator("V8 SYSTEM");
				newMember.setMem_cash_balance(new Double(0));
				if(CommonUtil.isNullOrEmpty(newMember.getPackage_type())){
					newMember.setPackage_type("0"); // 0 = nothing
				}
			}
			
			PortalLogger.debug("[doRegister Validation COMPLETED]");
			
			//Retain redirectURL
			if(!CommonUtil.isNullOrEmpty(request.getParameter("redirectURL"))){
				request.setAttribute("redirectURL",request.getParameter("redirectURL"));
			}
			
			if(thisResp.hasError()){
				//set to error jsp
				//PortalLogger.debug("[doRegister Validation HAS ERROR]");
				request.setAttribute("formUser",newMember);
			} else {
				if(dao.create(newMember)!=null){
					PortalLogger.debug("[doRegister DAO Create COMPLETED]");
					//TODO: Need to create a new page to handle register success page
					//thisResp.setTargetJSP(JspMapping.JSP_REGISTER_AJAX);
					request.setAttribute(SystemConstants.REQ_ATTR_DONE_MSG, MessageUtil.getV8Message((String)request.getAttribute(SystemConstants.REQ_ATTR_LANG), 
							"REG_DONE"));
					//Register Email Here
					ArrayList<String> emailParam = new ArrayList<String>();

					try{
						emailParam.add(newMember.getMem_firstname()+newMember.getMem_lastname());
						//TODO: Need to modify the email content for V81 instead of V6
						String url = SystemConstants.HTTPS + PropertiesConstants.get(PropertiesConstants.externalHost)+ request.getAttribute("contextPath")+"/do/LOGIN?action=ACTIVATE&s="+ 
							URLEncoder.encode(newMember.getMem_login_email(), "UTF-8")+
							"&code="+MD5Utility.MD5(request.getParameter("register-password")).substring(0, 10);
						url = "<a href=\""+ url + "\">" + url + "</a>";
						PortalLogger.debug("[ACTI URL] "+ url);
						emailParam.add(url);
						emailParam.add(newMember.getMem_login_email());
						emailParam.add(request.getParameter("register-password"));
						String emailContent = MessageUtil.getV8Message(lang, "EMAIL_REG_SUCCESS", emailParam);
						
						MailUtil mailer = new MailUtil();
						mailer.setToAddress(newMember.getMem_login_email());
						mailer.setSubject(MessageUtil.getV8Message(lang, "EMAIL_REG_SUCCESS_SUBJ"));
						mailer.setContent(emailContent);
						if (!mailer.send()){
							PortalLogger.error("Member registration email failed - " + newMember.getMem_login_email());
						}
						//Set Content for general message page
						ArrayList<String> genParam = new ArrayList<String>();
						genParam.add(newMember.getMem_login_email());
//						request.setAttribute(SystemConstants.REQ_ATTR_GENERAL_PARAM,  genParam);

						request.setAttribute(SystemConstants.REQ_ATTR_GENERAL_TITLE, MessageUtil.getV8Message(lang, "GENTIT_REG_SUCCESS"));
						request.setAttribute(SystemConstants.REQ_ATTR_GENERAL_MSG, MessageUtil.getV8Message(lang, "GENMSG_REG_SUCCESS",genParam));
						//thisResp.setTargetJSP(CMAJspMapping.JSP_GEN_PAGE_AJAX);
						//Temporary login for checkout register
						/***
						if(isCheckoutReg){
							//Update lastloginDate
							newMember.setMem_lastlogindate(new java.util.Date());
							dao.update(newMember);
							//Store in session
							ImagskySession session = (ImagskySession)request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION);
							session.setLogined(true);
							session.setUser(newMember);
							request.getSession().setAttribute(SystemConstants.REQ_ATTR_SESSION, session);
						}***/
					} catch (Exception e){
						PortalLogger.debug("[doRegister DAO Create FAILED]");
						PortalLogger.error("Generate Register URL Error: " + newMember.getMem_login_email(), e);
					}
					
				} else {
					PortalLogger.error("LOGIN_Handler.doRegister Error: Unknown error" , request);
				}
			}
			PortalLogger.debug("[doRegister DAO COMPLETED]");
			if(!thisResp.hasError()){
				ArrayList<String> al  = new ArrayList<String>();
				al.add(newMember.getMem_login_email());
				request.setAttribute(SystemConstants.REQ_ATTR_DONE_MSG, MessageUtil.getV8Message(lang,"REG_DONE",al));
				request.setAttribute(SystemConstants.AJAX_RESULT, SystemConstants.AJAX_RESULT_TRUE);
			}
			
			thisResp.setTargetJSP(JspMapping.COMMON_AJAX_RESPONSE);
			
			PortalLogger.debug("[doRegister : Target JSP = "+ thisResp.getTargetJSP() + "]");
			PortalLogger.debug("[doRegister JSP Assign COMPLETED]");
			
		} catch (BaseDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			PortalLogger.error("LOGIN_Handler.doRegister Error: " , request, e);
			thisResp.setTargetJSP(JspMapping.COMMON_AJAX_RESPONSE);
		}
		PortalLogger.debug("[doRegister END]");
		return thisResp;
	}

	private SiteResponse showRegister(HttpServletRequest request, HttpServletResponse response) {
		SiteResponse thisResp = super.createResponse();
		thisResp.setTargetJSP(JspMapping.INPUT_REGISTER);
		return thisResp;
	}

	private SiteResponse doReminder(HttpServletRequest request, HttpServletResponse response) {
		SiteResponse thisResp = super.createResponse();
		ArrayList<String> emailParam = new ArrayList<String>();
		String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG);
		try{
			MemberDAO dao = MemberDAO.getInstance();
			Member fpwdMember = new Member();
			fpwdMember.setMem_login_email(request.getParameter("reminder-email"));
			List<?> fpwdMemberList = dao.findListWithSample(fpwdMember);
			if(fpwdMemberList ==null || fpwdMemberList.size()==0){
				PortalLogger.error("[FORGET_PWD] Not found User: " + request.getRemoteAddr() + " MEMBER EMAIL:"+ request.getParameter("reminder-email"), request);
				thisResp.addErrorMsg(new SiteErrorMessage("FPWD_NO_USER"));
			} else {
				PortalLogger.info("[FORGET_PWD] FPWD Request: " + request.getRemoteAddr() + " MEMBER EMAIL:"+ request.getParameter("reminder-email"), request);
				//Forget Email Here
				fpwdMember = (Member)fpwdMemberList.get(0);
				emailParam.add(fpwdMember.getMem_firstname()+fpwdMember.getMem_lastname());
				String url = SystemConstants.HTTPS + PropertiesConstants.get(PropertiesConstants.externalHost)+ request.getAttribute("contextPath")+"/do/LOGIN?action=FPWD_RESET&s="+
					Base64.encode(fpwdMember.getMem_login_email())+
					//URLEncoder.encode(fpwdMember.getMem_login_email(), "UTF-8")+
					"&code="+fpwdMember.getMem_passwd().substring(0, 10);
				PortalLogger.debug("[FORGET_PWD URL] "+ url, request);
				emailParam.add("<a href=\""+ url+ "\">"+url+"</a>");
				
				MailUtil mailer = new MailUtil();
				mailer.setToAddress(fpwdMember.getMem_login_email());
				mailer.setSubject(MessageUtil.getV8Message(lang, "EMAIL_FPWD_SUBJ"));
				mailer.setContent(MessageUtil.getV8Message(lang, "EMAIL_FPWD", emailParam));
				if (!mailer.send()){
					PortalLogger.error("[FORGET_PWD] FPWD Email - " + fpwdMember.getMem_login_email(), request);
				}
				request.setAttribute(SystemConstants.REQ_ATTR_GENERAL_TITLE, MessageUtil.getV8Message(lang, "TIT_FORGETPWD"));
				request.setAttribute(SystemConstants.REQ_ATTR_GENERAL_MSG, MessageUtil.getV8Message(lang, "FPWD_MSG_ACK"));
				request.setAttribute(SystemConstants.REQ_ATTR_DONE_MSG, MessageUtil.getV8Message(lang,"FORGET_PWD_DONE"));
				request.setAttribute(SystemConstants.AJAX_RESULT, SystemConstants.AJAX_RESULT_TRUE);
			}
			
		} catch (Exception e){
			PortalLogger.error("FORGET PASSWORD Request error: ", request,e);
		}
		thisResp.setTargetJSP(JspMapping.COMMON_AJAX_RESPONSE);
		return thisResp;
	}

	/***
	 * AJAX: Display Forget Password Form 
	 * @param request
	 * @param response
	 * @return
	 */
	private SiteResponse showReminder(HttpServletRequest request, HttpServletResponse response) {
		SiteResponse thisResp = super.createResponse();
		thisResp.setTargetJSP(JspMapping.INPUT_REMINDER);
		return thisResp;
	}

	private SiteResponse doLogout(HttpServletRequest request, HttpServletResponse response) {
		SiteResponse thisResp = super.createResponse();
		
		ImagskySession session = (ImagskySession)request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION);
		Member logoutUser =  session.getUser();
		
		//LOGOUT
		session.setUser(null);
		session.setLogined(false);
		
		//FB access token
		session.setFbAccessToken(null);
		
        //Clear Reminder
		request.getSession().removeAttribute(SystemConstants.REQ_ATTR_REMINDER );
                
		request.setAttribute("LOGOUTUSER", logoutUser);
		if(logoutUser!=null){
			request.setAttribute("redirectURL", request.getAttribute("contextPath")+ "/" + logoutUser.getMem_shopurl()+"/.do");
		}
		request.setAttribute(SystemConstants.REQ_ATTR_DONE_MSG, MessageUtil.getV8Message((String)request.getAttribute(SystemConstants.REQ_ATTR_LANG), 
		"LOGOUT_DONE"));
		thisResp.setTargetJSP(JspMapping.LOGOUT);
		
		return thisResp;
	}

	private SiteResponse doLogin(HttpServletRequest request, HttpServletResponse response) {
		SiteResponse thisResp = super.createResponse();
		
		
		MemberBiz biz = MemberBiz.getInstance();
		try {
			//Check member / email exist
			Member thisLoginMember = biz.doCheckMemberExist(CommonUtil.null2Empty(request.getParameter("login-email")));
			if(thisLoginMember==null){
				//No such member
				thisResp.addErrorMsg(new SiteErrorMessage("LOGIN_NO_MEMBER"));
			} else {
				PortalLogger.debug(MD5Utility.MD5(CommonUtil.null2Empty(request.getParameter("login-password"))));
				PortalLogger.debug(thisLoginMember.getMem_passwd());
				//Validate password
				if(!thisLoginMember.isSys_is_live()){
					//NOT YET ACTIVATE
					thisResp.addErrorMsg(new SiteErrorMessage("LOGIN_ACC_NOT_ACTIVATE"));
				} else if(thisLoginMember.getFb_id()!=null && CommonUtil.isNullOrEmpty(thisLoginMember.getMem_passwd())){
					//FACEBOOK REGISTERED BUT NOT BBM => Redirect to Setting Password
					thisResp.addErrorMsg(new SiteErrorMessage("LOGIN_INV_PASSWD"));
					request.setAttribute("FB_SET_PASSWORD", "Y");
				} else if(biz.doCheckPassword(thisLoginMember, request.getParameter("login-password"))){
					//Update lastloginDate
					thisLoginMember.setMem_lastlogindate(new java.util.Date());
					biz.doSaveMember(thisLoginMember);
					//Store in session
					ImagskySession session = (ImagskySession)request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION);
					session.setLogined(true);
					session.setUser(thisLoginMember);
					request.getSession().setAttribute(SystemConstants.REQ_ATTR_SESSION, session);
					PortalLogger.debug("LOGIN:" + thisLoginMember.getMem_display_name());
					request.setAttribute(
							SystemConstants.REQ_ATTR_DONE_MSG, 
							MessageUtil.getV8Message((String)request.getAttribute(SystemConstants.REQ_ATTR_LANG),"LOGIN_DONE") 
					);
				} else {
					thisResp.addErrorMsg(new SiteErrorMessage("INVALID_PASSWORD"));
				}
			}
			
			if(thisResp.hasError()){
				thisResp.setTargetJSP(JspMapping.COMMON_AJAX_RESPONSE);
			} else {
				thisResp.setTargetJSP(JspMapping.PUB_MAIN);
			}
		} catch (Exception e){
			thisResp.addErrorMsg(new SiteErrorMessage("UNKNOWN LOGIN ERROR"));
		}
		
		if(thisResp.hasError()){
			thisResp.setTargetJSP(JspMapping.COMMON_AJAX_RESPONSE);
		} else {
			thisResp.setTargetJSP(JspMapping.PUB_MAIN);
		}
		return thisResp;
	}

	private SiteResponse showLogin(HttpServletRequest request, HttpServletResponse response) {
		SiteResponse thisResp = super.createResponse();
		thisResp.setTargetJSP(JspMapping.INPUT_LOGIN);
		return thisResp;
	}
	
	
	/****
	 * Display Main Page
	 * @param request
	 * @param response
	 * @return
	 */
	private SiteResponse showMain(HttpServletRequest request, HttpServletResponse response) {
 		SiteResponse thisResp = super.createResponse();
 		thisResp.setTargetJSP(JspMapping.PUB_MAIN);
		return thisResp;
	}


	public enum Pages { 
		PUB_MAIN,									//pub_main.jsp
		INPUT_LOGIN,								//inc_login.jsp						-	1.1 Input Password for login
		DO_LOGIN,									//(success) pub_main.jsp , (fail) return error msg
		DO_LOGOUT,
		INPUT_REMINDER,						//
		DO_REMINDER,
		INPUT_REGISTER	,						//inc_register.jsp
		DO_REGISTER
	};
}


