package com.imagsky.v81j.servlet.handler;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagsky.common.SiteErrorMessage;
import com.imagsky.common.SiteResponse;
import com.imagsky.common.SystemConstants;
import com.imagsky.dao.FormSubmitDAO;
import com.imagsky.exception.BaseDBException;
import com.imagsky.exception.BaseException;
import com.imagsky.util.CommonUtil;
import com.imagsky.util.logger.PortalLogger;
import com.imagsky.v81j.domain.FormSubmit;
import com.sun.xml.messaging.saaj.util.Base64;

public class SUBMIT_Handler extends BaseHandler {

	public static final String REQ_SUBMIT = "v8jsubmit";
	
	private String[] appCodeToken;
	
    protected static final String CLASS_NAME = "SUBMIT_Handler.java";
    private String thisLang = null;
    
	@Override
	public SiteResponse execute(HttpServletRequest request, HttpServletResponse response) throws BaseException {

		SiteResponse thisResp = super.createResponse();
        PortalLogger.debug(CLASS_NAME + " " + SystemConstants.LOG_START);

        //No need check login currently
        //thisMember = ((ImagskySession) request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getUser();
        thisLang = (String) request.getAttribute(SystemConstants.REQ_ATTR_LANG);
        
		//tokenized URL 
		appCodeToken = (String[])request.getAttribute(SystemConstants.REQ_ATTR_URL_PATTERN); //[0]: "SUBMIT", [1]:"FORM", [2]:"FORM_GUID
		
		try{
			if(CommonUtil.null2Empty(appCodeToken[0]).equalsIgnoreCase("SUBMIT")){
				thisResp = doSaveForm(request, response);
			}
		} catch (Exception e){
			
		}
		
		return null;
	}

	public SiteResponse doSaveForm(HttpServletRequest request, HttpServletResponse response) {
		SiteResponse thisResp = super.createResponse();
		
		FormSubmitDAO dao = FormSubmitDAO.getInstance();
		String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG);
		
		FormSubmit formSubmit = new FormSubmit();
		try {
			if(!CommonUtil.isNullOrEmpty(request.getParameter("fguid"))){
				formSubmit.setFORM_GUID(request.getParameter("fguid"));
			} else if(appCodeToken.length>2 && appCodeToken[1].equalsIgnoreCase("FORM") && !CommonUtil.isNullOrEmpty(appCodeToken[2])){
				formSubmit.setFORM_GUID(appCodeToken[2]);
			} else {
				thisResp.addErrorMsg(new SiteErrorMessage("FORMSUBMIT_UNKNOWN_FORM"));
			}
			formSubmit.setFORM_MACHINE_ID(request.getParameter("machine_id"));
			formSubmit.setFORM_SENDER(request.getParameter("shop_id"));
			
			Base64 base64util = new Base64();
			formSubmit.setFORM_SUBMIT_STRING(
					new String(
							base64util.decode(CommonUtil.null2Empty(request.getParameter("STR64")).getBytes("UTF-8")),
							"UTF-8"
			));
			if(!thisResp.hasError()){
				formSubmit = (FormSubmit)dao.CNT_create(formSubmit);
				
			}
		} catch (BaseDBException e) {
			PortalLogger.error("SUBMIT_Handler.doSubmit()", e);
		} catch (UnsupportedEncodingException e){
			PortalLogger.error("SUBMIT_Handler.doSubmit()", e);
		}
		return thisResp;
	}
}
