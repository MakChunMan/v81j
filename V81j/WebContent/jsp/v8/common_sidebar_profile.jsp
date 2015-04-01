<%--
2014-10-08 include page
 --%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.imagsky.common.*" %>
 <%@ page import="com.imagsky.v81j.domain.Member" %>
 <%@ page import="com.imagsky.util.*" %>
<%
if(!V81Util.isLogined(request)){
	out.println("<script>self.location='/v81/zh/page_ready_login.php';</script>");
} else {
Member thisUser = ((ImagskySession)request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getUser();
%>    
<%="<!-- common_sidebar_profile -->" %>    
<div class="sidebar-section">
                                <h2 class="text-light">Profile</h2>
                                <form action="index.html" method="post" class="form-control-borderless" onsubmit="return false;">
                                    <div class="form-group">
                                        <label for="side-profile-email">Login Email :</label><br/>
                                        <%=thisUser.getMem_login_email()%>
                                    </div>
                                    <div class="form-group">
                                        <label for="side-profile-name">Name :</label><br/>
                                        <%=thisUser.getMem_firstname() + thisUser.getMem_lastname()%>
                                    </div>
                                    <div class="form-group">
                                        <label for="side-profile-password">New Password</label>
                                        <input type="password" id="side-profile-password" name="side-profile-password" class="form-control">
                                    </div>
                                    <div class="form-group">
                                        <label for="side-profile-password-confirm">Confirm New Password</label>
                                        <input type="password" id="side-profile-password-confirm" name="side-profile-password-confirm" class="form-control">
                                    </div>
                                    <div class="form-group remove-margin">
                                        <button type="submit" class="btn btn-effect-ripple btn-primary" onclick="App.sidebar('close-sidebar-alt');">Save</button>
                                        <button type="button" class="btn btn-effect-ripple btn-primary" onclick="App.sidebar('close-sidebar-alt');">Edit details</button>
                                    </div>
                                </form>
</div>
<% } %>