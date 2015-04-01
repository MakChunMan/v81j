<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.imagsky.common.*" %>
        <!-- Include Jquery library from Google's CDN but if something goes wrong get Jquery from local file (Remove 'http:' if you have SSL) -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <script>!window.jQuery && document.write(decodeURI('%3Cscript src="js/vendor/jquery-2.1.1.min.js"%3E%3C/script%3E'));</script>

        <!-- Bootstrap.js, Jquery plugins and Custom JS code -->
        <script src="<%=SystemConstants.V8_PATH %>js/vendor/bootstrap.min.js"></script>
        <script src="<%=SystemConstants.V8_PATH %>js/plugins.js"></script>
        <script src="<%=SystemConstants.V8_PATH %>js/app.js"></script>
