<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
<%
String resp = (String)request.getAttribute("JsonResponse");
out.println(resp);
 %>    