<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.imagsky.common.*" %>
  <%@ page import="com.imagsky.v81j.domain.Member" %>
 <%@ page import="com.imagsky.util.*" %>
 <%--
2014-10-08 include page
 --%>
<%
Member thisUser = null;
if(!V81Util.isLogined(request)){
	out.println("<script>self.location = '/v81/zh/page_ready_login.php';</script>");
} else {
	thisUser =  ((ImagskySession)request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getUser();
%>    
<%="<!-- common_head_left -->" %>                           
<ul class="nav navbar-nav-custom">
                            <!-- Main Sidebar Toggle Button -->
                            <li>
                                <a href="javascript:void(0)" onclick="App.sidebar('toggle-sidebar');">
                                    <i class="fa fa-ellipsis-v fa-fw animation-fadeInRight" id="sidebar-toggle-mini"></i>
                                    <i class="fa fa-bars fa-fw animation-fadeInRight" id="sidebar-toggle-full"></i>
                                </a>
                            </li>
                            <!-- END Main Sidebar Toggle Button -->

                            <!-- Header Link -->
                            <li class="hidden-xs animation-fadeInQuick">
                                <a href=""><strong>WELCOME, <%=thisUser.getMem_display_name() %></strong></a>
                            </li>
                            <!-- END Header Link -->
</ul>
<% } %>
