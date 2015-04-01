<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.imagsky.common.*" %>
  <%@ page import="com.imagsky.v81j.domain.Member" %>
 <%@ page import="com.imagsky.v81j.domain.App" %>
 <%@ page import="com.imagsky.v81j.domain.Module" %>
 <%@ page import="com.imagsky.v81j.domain.Module.*" %>
 <%@ page import="com.imagsky.v81j.domain.ModAboutPage" %>
  <%@ page import="com.imagsky.util.*" %>
<% 
App thisApp = ((ImagskySession) request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getWorkingApp();
ModAboutPage thisMod = (ModAboutPage)request.getAttribute(SystemConstants.REQ_ATTR_OBJ);
String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG); 

if(!V81Util.isLogined(request)){
    out.println("<script>self.location='/v81/zh/page_ready_login.php';</script>");
}  else {     

if(thisMod == null)
	thisMod = new ModAboutPage();
%>
<!-- Horizontal Form Title -->
      <div class="block-title">
          <h2><%=MessageUtil.getV8Message(lang,"MOD_ABOUT_US_EDIT_TITLE") %></h2>
      </div>
      <!-- END Horizontal Form Title -->

      <!-- Horizontal Form Content -->
      <form id="mod_details_edit_form" method="post" class="form-horizontal form-bordered" onsubmit="return false;">
           <input type=hidden name="MODTYPE" value="<%=Module.ModuleTypes.ModAboutPage.name()%>"/>
           <input type=hidden name="APPGUID" value="<%=thisApp.getSys_guid()%>"/>
           <% if(!CommonUtil.isNullOrEmpty(thisMod.getSys_guid())){ %>
           <input type=hidden name="MODGUID" value="<%=thisMod.getSys_guid()%>"/>
           <% } %>
          <div class="form-group">
              <label class="col-md-3 control-label" for="edit-abt-title"><%=MessageUtil.getV8Message(lang,"ABT_TITLE") %></label>
              <div class="col-md-9">
                  <input type="text" id="edit-abt-title" name="edit-abt-title" class="form-control" value="<%=CommonUtil.null2Empty(thisMod.getPageTitle())%>">
                  <span class="help-block"><%=MessageUtil.getV8Message(lang,"ABT_TITLE_LABEL") %></span>
              </div>
          </div>
          <div class="form-group">
              <label class="col-md-3 control-label" for="edit-abt-about"><%=MessageUtil.getV8Message(lang,"ABT_ABOUT") %></label>
              <div class="col-md-9">
                  <textarea  id="edit-abt-about" name="edit-abt-about" class="form-control"><%=CommonUtil.null2Empty(thisMod.getPageAbout())%></textarea>
                  <span class="help-block"><%=MessageUtil.getV8Message(lang,"ABT_ABOUT_LABEL") %></span>
              </div>
          </div>
          <div class="form-group">
              <label class="col-md-3 control-label" for="edit-abt-desc"><%=MessageUtil.getV8Message(lang,"ABT_DESC") %></label>
              <div class="col-md-9">
                  <textarea  id="edit-abt-about" name="edit-abt-desc" class="form-control"><%=CommonUtil.null2Empty(thisMod.getPageDescription())%></textarea>
                  <span class="help-block"><%=MessageUtil.getV8Message(lang,"ABT_DESC_LABEL") %></span>
              </div>
          </div>
          <div class="form-group">
              <label class="col-md-3 control-label" for="edit-abt-fb"><%=MessageUtil.getV8Message(lang,"ABT_FACEBOOK") %></label>
              <div class="col-md-9">
                  <input type="text" id="edit-abt-fb" name="edit-abt-fb" class="form-control" value="<%=CommonUtil.null2Empty(thisMod.getPageFacebookLink())%>">
                  <span class="help-block"><%=MessageUtil.getV8Message(lang,"ABT_FACEBOOK_LABEL") %></span>
              </div>
          </div>
          <div class="form-group">
              <label class="col-md-3 control-label" for="edit-abt-email"><%=MessageUtil.getV8Message(lang,"ABT_EMAIL") %></label>
              <div class="col-md-9">
                  <input type="text" id="edit-abt-email" name="edit-abt-email" class="form-control" value="<%=CommonUtil.null2Empty(thisMod.getPageEmail())%>">
                  <span class="help-block"><%=MessageUtil.getV8Message(lang,"ABT_EMAIL_LABEL") %></span>
              </div>
          </div>
          <div class="form-group">
              <label class="col-md-3 control-label" for="edit-abt-address"><%=MessageUtil.getV8Message(lang,"ABT_ADDRESS") %></label>
              <div class="col-md-9">
                  <input type="text" id="edit-abt-address" name="edit-abt-address" class="form-control" value="<%=CommonUtil.null2Empty(thisMod.getPageAddress())%>">
                  <span class="help-block"><%=MessageUtil.getV8Message(lang,"ABT_ADDRESS_LABEL") %></span>
              </div>
          </div>
        <div class="form-group">
              <label class="col-md-3 control-label" for="edit-abt-image"><%=MessageUtil.getV8Message(lang,"ABT_IMAGE") %></label>
              <div class="col-md-9">
                   <div id="abt_image_response">
                   <% if(thisMod.getPageImage()!=null){ %>
                   <img width='300' src='<%=SystemConstants.V8_PATH %>userfiles/<%=thisApp.getSys_guid() %>/<%=thisMod.getPageImage().getImageUrl() %>'>
                   <input type=hidden name=edit-abt-image value='<%=thisMod.getPageImage().getImageUrl() %>'/>
                   <% } %>
                   </div>
                  <button type=button  class="btn btn-effect-ripple btn-warning" onclick="javascript:return uploadOnClick('abt_image_response','ABT_');">
                  <%=MessageUtil.getV8Message(lang,"ABT_IMAGE_LABEL") %></button>
              </div>
       </div>          
       <jsp:include page="/jsp/v8/inc_mod_common.jsp"></jsp:include>
        <div class="form-group form-actions">
              <div class="col-md-9 col-md-offset-3">
                  <button type="submit" id="app_edit_submit" class="btn btn-effect-ripple btn-primary"><%=MessageUtil.getV8Message(lang,"BTN_SUBMIT") %></button>
                  <button type="reset" class="btn btn-effect-ripple btn-danger" onclick="javascript:$('#abt_image_response').html('');return true;"><%=MessageUtil.getV8Message(lang,"BTN_RESET") %></button>
              </div>
           </div>
        </form>
      <!-- END Horizontal Form Content -->
      <script src="<%=SystemConstants.V8_PATH %>/fileinput/js/fileinput.js" type="text/javascript"></script>
      <script>
      $('#app_edit_submit').click(function(){
          $.ajax({
              url:"/portal/MOD/DO_SAVE_MOD_CONTENT.do",
              data: $('#mod_details_edit_form').serialize(),
              type: "post",               
              cache: false
          }).done(function( html ) {
              if($.trim(html).match("^Error")){
                  // Server side validation and display error msg
                  $('#error-msg').html(html.replace("Error:","")+"<br/>");
              } else {
                  $('#modEditForm').html(
                  '<div class="alert alert-success alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'+
                  '<h4><strong><%=MessageUtil.getV8Message(lang, "COMMON_LABEL")%></strong></h4>' +
                  '<p>'+html.replace("Msg:","")+'</p></div>');
                  //Reload List
                  topRefresh();
              }
          });
      });
      </script>
<% } %>
