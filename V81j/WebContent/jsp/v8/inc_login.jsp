<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ page import="com.imagsky.common.*" %>
 <%@ page import="com.imagsky.util.*" %>
 <% String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG); %>
 <!-- Login Form -->
                    <div class="error-msg" id="error-msg" style="color:red"></div>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <input type="text" id="login-email" name="login-email" class="form-control" placeholder="<%=MessageUtil.getV8Message(lang,"LOGIN_EMAIL") %>">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <input type="password" id="login-password" name="login-password" class="form-control" placeholder="<%=MessageUtil.getV8Message(lang,"LOGIN_PASSWORD") %>">
                        </div>
                    </div>
                    <div class="form-group form-actions">
                        <div class="col-xs-8">
                            <label class="csscheckbox csscheckbox-primary">
                                <input type="checkbox" id="login-remember-me" name="login-remember-me">
                                <span></span>
                            </label>
                            <%=MessageUtil.getV8Message(lang,"LOGIN_REMEMBERME") %>
                        </div>
                        <div class="col-xs-4 text-right">
                            <button type="submit" id="btn_submit" class="btn btn-effect-ripple btn-sm btn-primary"><i class="fa fa-check"></i> <%=MessageUtil.getV8Message(lang,"BTN_SUBMIT") %></button>
                        </div>
                    </div>
<!-- END Login Form -->