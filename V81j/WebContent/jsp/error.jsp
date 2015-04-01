<% response.setStatus(200); %>
<%
//response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.imagsky.util.*" %>    
<%@ page import="com.imagsky.util.logger.*" %>
<%@ page import="java.util.*" %>
<%
String url = CommonUtil.null2Empty((String)request.getAttribute("javax.servlet.error.request_uri"));
String[] a = CommonUtil.stringTokenize(url,"/");
boolean shopRedirect = true;
if (CommonUtil.isNullOrEmpty(url)){
	shopRedirect = false;
} else if(url.indexOf(".") >= 0 && a[0].indexOf(".")<0){
	shopRedirect = false; //Filter file extenstion containts "."
} else if(a.length > 1){
	shopRedirect = false; //Filter /xxx/xxx pattern
} else if(a[0].equalsIgnoreCase("files")){
	shopRedirect = false; //Filter static file apps
} else {
	out.println("<script>self.location='"+a[0].replaceAll("/","")+".do';</script>");
}
if(!shopRedirect)
	PortalLogger.error("[404]" + request.getRemoteAddr() + "|" + (CommonUtil.isNullOrEmpty(url)?request.getRequestURI():url) );
%>