<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ page import="com.imagsky.common.*" %>
  <%@ page import="com.imagsky.v81j.domain.Member" %>
 <%@ page import="com.imagsky.v81j.domain.App" %>
 <%@ page import="com.imagsky.util.*" %>
 <%@ page import="java.util.*" %>
 <% 
 String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG);
 Member thisUser = null;
 if(!V81Util.isLogined(request)){
     out.println("<script>self.location='/v81/zh/page_ready_login.php';</script>");
 } else {
     thisUser = ((ImagskySession)request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getUser();
 %>
<!--
    Available Table Classes:
        'table'             - basic table
        'table-bordered'    - table with full borders
        'table-borderless'  - table with no borders
        'table-striped'     - striped table
        'table-condensed'   - table with smaller top and bottom cell padding
        'table-hover'       - rows highlighted on mouse hover
        'table-vcenter'     - middle align content vertically
    -->
    <table id="general-table" class="table table-striped table-bordered table-vcenter">
        <thead>
            <tr>
                <th><%=MessageUtil.getV8Message(lang,"APP_NAME") %></th>
                <th><%=MessageUtil.getV8Message(lang,"APP_TYPE") %></th>
                <th style="width: 320px;"><%=MessageUtil.getV8Message(lang,"APP_DESC") %></th>
                <th style="width: 120px;" class="text-center"><i class="fa fa-flash"></i></th>
            </tr>
        </thead>
        <tbody>
            <%
            Iterator it = thisUser.getApps().iterator();
            App aApp;
            while (it.hasNext()){
                aApp = (App)it.next();
            %>
            <tr>
                <td><strong><a href="/portal/MOD/MOD_ADD_MAIN/<%=aApp.getSys_guid()%>/.do"><%=aApp.getAPP_NAME() %></a></strong></td>
                <td><%
                if(1==aApp.getAPP_TYPE()){ out.println(MessageUtil.getV8Message(lang,"APP_TYPE_BASIC"));}
                else if(2==aApp.getAPP_TYPE()){ out.println(MessageUtil.getV8Message(lang,"APP_TYPE_SHOP"));}
                else if(3==aApp.getAPP_TYPE()){ out.println(MessageUtil.getV8Message(lang,"APP_TYPE_PDA"));}
                %></td>
                <td><%=aApp.getAPP_DESC() %></td>
                <td class="text-center">
                    <a href="javascript:appEdit('<%=aApp.getSys_guid() %>')" data-toggle="tooltip" title="Edit User" class="btn btn-effect-ripple btn-sm btn-success"><i class="fa fa-pencil"></i></a>
                    <a href="javascript:void(0)" data-toggle="tooltip" title="Delete User" class="btn btn-effect-ripple btn-sm btn-danger"><i class="fa fa-times"></i></a>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
    <% } %>