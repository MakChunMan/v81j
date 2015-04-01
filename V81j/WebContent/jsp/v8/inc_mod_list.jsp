<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ page import="com.imagsky.common.*" %>
 <%@ page import="com.imagsky.v81j.domain.App" %>
 <%@ page import="com.imagsky.v81j.domain.Module" %>
  <%@ page import="com.imagsky.v81j.domain.Member" %>
 <%@ page import="com.imagsky.util.*" %>
 <%@ page import="java.util.*" %>
 <% 
 String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG);
 Member thisUser = null;
 App thisApp = null;
 Module thisModule = null;
 ArrayList al = null;
 boolean isCurrentFirst = true;
 if(!V81Util.isLogined(request)){
     out.println("<script>self.location='/v81/zh/page_ready_login.php';</script>");
 } else {
     thisUser = ((ImagskySession)request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getUser();
     thisApp = ((ImagskySession)request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getWorkingApp();
 %>
  <div class="row block-section">
<% for (int x =0 ; x < SystemConstants.V8_MAX_NO_MODULE ;x++){ 
                       if(thisApp.getModules()!=null && thisApp.getModules().size()>x){
                    	   if(al==null) al = new ArrayList(thisApp.getModules());                    	   
                    	   thisModule = (Module)al.get(x);
                    	   out.println("<div class=\"col-xs-2 text-center clickbind\" id=\"module"+ (x+1)+ "\" style=\"padding: 5px;\" typename=\""+thisModule.getModuleTypeName()+"\">");
                    	   out.println(ModuleTemplateUIConstants.getUIHtml_modListPage(thisModule.getModuleTypeName()));
                    	   out.println("<br/>"+thisModule.getModuleTitle());
                    	   out.println("<input type=\"hidden\" name=\"module"+(x+1)+"\" value=\""+thisModule.getSys_guid()+"\">");
                    	   out.println("</div>");
                       } else if(isCurrentFirst){
                    	    isCurrentFirst = false;
                       %>
                    	<div class="col-xs-2" id="module<%=(x+1)%>">
                            <button class="btn btn-lg btn-success" onClick="javascript:$('#moduleTemplateRow').show();clearAllBorder();$('#moduleEditRow').hide();$('#topDeleteBtn').hide();return false;"><i class="fa fa-plus"></i> Add</button>
                        </div>
                 <% } else { %>
                        <div class="col-xs-2" id="module<%=(x+1)%>">
                            <button class="btn btn-sm btn-default disabled"><i class="fa fa-plus"></i> Empty</button>
                        </div>    	   
                <%  } %>       
<% } %>
<script>var currentAdd = <%=thisApp.getModules()==null?0:thisApp.getModules().size()%>;
bindClickEvent();
</script>
</div>
<% } %>
