<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ page import="com.imagsky.common.*" %>
 <%@ page import="com.imagsky.util.*" %>
 <% String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG); %>
<!--Reminder Form --> 
                    <div class="error-msg" id="error-msg" style="color:red"></div>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <input type="text" id="reminder-email" name="reminder-email" class="form-control" placeholder="<%=MessageUtil.getV8Message(lang,"FPWD_EMAIL") %>">
                        </div>
                    </div>
                    <div class="form-group form-actions">
                        <div class="col-xs-12 text-right">
                            <button type="submit" class="btn btn-effect-ripple btn-sm btn-primary"><i class="fa fa-check"></i> <%=MessageUtil.getV8Message(lang,"FPWD_SUBMIT") %></button>
                        </div>
                    </div>
<!--End Reminder Form -->