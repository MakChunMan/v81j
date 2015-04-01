 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ page import="com.imagsky.common.*" %>
 <%@ page import="com.imagsky.util.*" %>
 <% String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG); %>
 <%-- Resgister Form --%>
                    <div class="error-msg" id="error-msg" style="color:red"></div>
                    <%---
                    <div class="form-group">
                        <div class="col-xs-12">
                            <input type="text" id="register-username" name="register-username" class="form-control" placeholder="<%=MessageUtil.getV8Message(lang,"REG_USERNAME") %>">
                        </div>
                    </div> --%>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <input type="text" id="register-email" name="register-email" class="form-control" placeholder="<%=MessageUtil.getV8Message(lang,"LOGIN_EMAIL") %>">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <input type="password" id="register-password" name="register-password" class="form-control" placeholder="<%=MessageUtil.getV8Message(lang,"LOGIN_PASSWORD") %>">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <input type="password" id="register-password-verify" name="register-password-verify" class="form-control" placeholder="<%=MessageUtil.getV8Message(lang,"REG_VER_PWD") %>">
                        </div>
                    </div>
                    <div class="form-group form-actions">
                        <div class="col-xs-6">
                            <label class="csscheckbox csscheckbox-primary" data-toggle="tooltip" title="<%=MessageUtil.getV8Message(lang,"REG_AGREE_TERM") %>">
                                <input type="checkbox" id="register-terms" name="register-terms">
                                <span></span>
                            </label>
                            <a href="#modal-terms" data-toggle="modal"><%=MessageUtil.getV8Message(lang,"REG_TERMS") %></a>
                        </div>
                        <div class="col-xs-6 text-right">
                            <button type="submit" class="btn btn-effect-ripple btn-success"><i class="fa fa-plus"></i> 
                            <%=MessageUtil.getV8Message(lang,"REG_BTN") %></button>
                        </div>
</div>
<%-- End Resgister Form --%>