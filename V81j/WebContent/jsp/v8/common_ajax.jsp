 <%
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.imagsky.common.*" %>    
<%@ page import="com.imagsky.util.logger.PortalLogger" %>
<%@ page import="com.imagsky.util.MessageUtil" %>
<%@ page import="com.imagsky.v81j.domain.StringMessage" %>
<%
SiteResponse sR = (SiteResponse) request.getAttribute(SystemConstants.REQ_ATTR_RESPOSNE);
try{
    if(sR!=null) { 
    java.util.List errorList= sR.getErrorMsgList();
    //Have error
    if(errorList!=null && errorList.size()>0){
        java.util.Iterator it = errorList.iterator();%>
        <%
        while(it.hasNext()){
            SiteErrorMessage tmp = (SiteErrorMessage)it.next();
            //cmaLogger.debug("Site Response Error :" + tmp.getMsgCode(), request);
            out.println("Error:"+MessageUtil.getV8Message("zh",tmp.getMsgCode()));
        }
        %></div><br/><%
    } else if(SystemConstants.AJAX_RESULT_TRUE.equalsIgnoreCase((String)request.getAttribute(SystemConstants.AJAX_RESULT))) {
        //No error
        out.println("Msg:"+ request.getAttribute(SystemConstants.REQ_ATTR_DONE_MSG));
    } else {
    	out.println("Error:"+"undefined");
    }
    } else {
    	out.println("Error:"+"undefined");
    }
} catch (Exception e){
    PortalLogger.error("Error in rendoring JSP error message",e);
} 
%>