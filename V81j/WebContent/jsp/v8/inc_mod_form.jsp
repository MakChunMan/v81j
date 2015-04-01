<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.imagsky.common.*" %>
 <%@ page import="com.imagsky.comparator.*" %>
  <%@ page import="com.imagsky.v81j.domain.Member" %>
 <%@ page import="com.imagsky.v81j.domain.App" %>
 <%@ page import="com.imagsky.v81j.domain.Module" %>
 <%@ page import="com.imagsky.v81j.domain.Module.*" %>
 <%@ page import="com.imagsky.v81j.domain.ModForm" %>
 <%@ page import="com.imagsky.v81j.domain.FormField" %>
 <%@ page import="com.imagsky.v81j.domain.FormFieldType" %>
  <%@ page import="com.imagsky.util.*" %>
 <%@ page import="java.util.*" %>
<% 
App thisApp = ((ImagskySession) request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getWorkingApp();
ModForm thisMod = (ModForm)request.getAttribute(SystemConstants.REQ_ATTR_OBJ);
String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG); 

if(!V81Util.isLogined(request)){
    out.println("<script>self.location='/v81/zh/page_ready_login.php';</script>");
}  else {     

if(thisMod == null)
    thisMod = new ModForm();
%>
<!-- Horizontal Form Title -->
      <div class="block-title">
          <h2><%=MessageUtil.getV8Message(lang,"MOD_FORM_EDIT_TITLE") %></h2>
      </div>
      <!-- END Horizontal Form Title -->

      <!-- Horizontal Form Content -->
      <form id="mod_details_edit_form" method="post" class="form-horizontal form-bordered" onsubmit="return false;">
           <input type=hidden name="MODTYPE" value="<%=Module.ModuleTypes.ModForm.name()%>"/>
           <input type=hidden name="APPGUID" value="<%=thisApp.getSys_guid()%>"/>
           <% if(!CommonUtil.isNullOrEmpty(thisMod.getSys_guid())){ %>
           <input type=hidden name="MODGUID" value="<%=thisMod.getSys_guid()%>"/>
           <% } %>
          <div class="form-group">
              <label class="col-md-3 control-label" for="edit-abt-title"><%=MessageUtil.getV8Message(lang,"FORM_TITLE") %></label>
              <div class="col-md-9">
                  <input type="text" id="edit-form-title" name=edit-form-title class="form-control" value="<%=CommonUtil.null2Empty(thisMod.getForm_name())%>">
                  <span class="help-block"><%=MessageUtil.getV8Message(lang,"FORM_TITLE_LABEL") %></span>
              </div>
          </div>
          <div class="form-group" id="form-field-div">
                    <label class="col-md-3 control-label" for="edit-abt-title"><%=MessageUtil.getV8Message(lang,"FORM_FIELDS") %></label>
                    <div class="col-md-9">
                         <table id="general-table" class="table table-striped table-bordered table-vcenter">
                    <thead>
                        <tr>
                            <th style='font-size:50%'><%=MessageUtil.getV8Message(lang,"FIELD_NAME") %></th>
                            <th style='font-size:50%'><%=MessageUtil.getV8Message(lang,"FIELD_TYPE") %></th>
                            <th style="width: 150px;font-size:50%" class="text-center"><i class="fa fa-flash"></i></th>
                        </tr>
                    </thead>
                    <tbody id=listbody>
                        <%
                        int x = 0;
                        Set<FormField> aSet = thisMod.getForm_fields();
                        ArrayList<FormField> aList = new ArrayList<FormField>();
                        if(aSet !=null)
                            aList.addAll(aSet);
                        Collections.sort(aList, new FormFieldOrderComparator());
                        if(aSet!=null || !CommonUtil.isNullOrEmpty(aList)){
                              Iterator<FormField> it = aSet.iterator();
                              FormField field = null;
                              while(it.hasNext()){
                                  x++;
                                  field = (FormField)it.next();
                        %>
                        <tr id=current<%=x %> class='rowchild'>
                            <td><input type=text name="fieldname<%=x %>" value="<%=field.getFormfield_label()%>"><input type=hidden name="field<%=x %>" value="<%=field.getSys_guid() %>"/></td>
                            <td><%=field.getFormfield_type_code() %></td>
                            <td class="text-center">
                                        <a href="javascript:removefield(<%=x %>,'old')" data-toggle="tooltip" title="Delete Field" class="btn btn-effect-ripple btn-sm btn-danger"><i class="fa fa-times"></i></a>
                                        <input type=hidden name="fieldorder<%=x %>" id="fieldorder<%=x %>" value=""/>
                            </td>
                        </tr>
                        <% 
                              }
                              out.println("<input type=hidden name=oldfieldcount value='"+ x + "'/>");
                        } else { %>
                        <tr id="nofield_display">
                            <td colspan=4>
                            <span class="label label-primary"><i class="fa fa-exclamation-circle"></i></span> <%=MessageUtil.getV8Message(lang,"No Field") %>
                            </td>
                        </tr>   
                        <% 
                        }
                        %>
                        <tr id='addfieldrow'>
                            <td class="form-inline" colspan="4">
                                <div class="form-group">
                                   <input type="text" id="new_field_name" name="new_field_name" class="form-control" placeholder="New Field Name">
                                   <select id="new_fieldtype-select" name="new_fieldtype-select" class="form-control" size="1">
                                           <option value="0">Please select</option>
                                           <% 
                                           List<FormFieldType.Type>  typeList = FormFieldType.getFormFieldTypeSelector();
                                           for(FormFieldType.Type thisType : typeList){
                                               out.println("<option value="+thisType.ordinal()+">"+ MessageUtil.getV8Message(lang,thisType.name())+"</option>");   
                                           }
                                           %>
                                   </select>
                               </div>
                               <div class="form-group">
                                   <button type="button" id="addfieldbutton" class="btn btn-effect-ripple btn-primary">Add Field</button>
                               </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                </div>
          </div>
        <jsp:include page="/jsp/v8/inc_mod_common.jsp"></jsp:include>
        <div class="form-group form-actions">
              <div class="col-md-9 col-md-offset-3">
                  <button type="submit" id="mod_form_submit" class="btn btn-effect-ripple btn-primary"><%=MessageUtil.getV8Message(lang,"BTN_SUBMIT") %></button>
                  <button type="reset" class="btn btn-effect-ripple btn-danger" onclick="javascript:$('#abt_image_response').html('');return true;"><%=MessageUtil.getV8Message(lang,"BTN_RESET") %></button>
              </div>
        </div>
        <input type=hidden id="newaddrow" name="newaddrow" value="" />
        </form>
        <a href="javascript:$('#debug_src').text($('#form-field-div').html())">Source</a>
        <div id="debug_src">
        </div>
      <script>
      var fieldCount = <%=x%>; //New + old
      var newIdx = 0;
      var newAddString = ",";
      $('#addfieldbutton').click(function(){
          newIdx++;
          newAddString += newIdx + ",";
          var str = "<tr id='row"+ newIdx + "' class='rowchild'><td><input type=text name=newfieldname"+ newIdx +" value='"+ $('#new_field_name').val()+ "'></td>"  +
            "<td>"+ $('#new_fieldtype-select').val()+"<input type=hidden name=newfieldtype"+ newIdx +" value='"+ $('#new_fieldtype-select').val()+ "'></td>"    +
            "<td class=\"text-center\">"   +
            "<a href=\"javascript:removefield("+ newIdx + ",'new')\" data-toggle=\"tooltip\" title=\"Delete Field\" class=\"btn btn-effect-ripple btn-sm btn-danger\"><i class=\"fa fa-times\"></i></a> "+
            "<input type=hidden name=\"newfieldorder"+ newIdx + "\" id=\"newfieldorder" +newIdx + "\" value=\"\"/></td></tr>";
            $('#addfieldrow').before(str);
            $('#new_field_name').val("");
            $('#new_fieldtype-select').val("");
            $('#nofield_display').hide();
      });
      
      function removefield(id, t){
          if(t=="new"){
              $('#row'+id).remove();
              newAddString = newAddString.replace(","+id+",",",");
              fieldCount--;
          } else if(t=="old"){
              $('#current'+id).remove();
              fieldCount--;
          }
      }
      
      $('#mod_form_submit').click(function(){
          $('#newaddrow').val(newAddString);
          //Reassign display order before submit
          var i = 0;
          $.each($('#listbody').children('.rowchild'), function(index, item) {
              if($(item).attr("id").indexOf("row")>=0){
                  //New
                  $('#newfieldorder'+$(item).attr("id").replace("row","")).val(i++);
              } else {
                  //Old
                  $('#fieldorder'+$(item).attr("id").replace("current","")).val(i++);
              }
              //alert($(item).attr("id"));
          });
          $.ajax({
              url:"/portal/MOD/DO_SAVE_MOD_CONTENT.do",
              data: $('#mod_details_edit_form').serialize(),
              cache: false,
              type: 'POST',
              success: function(data){
                  if($.trim(data).match("^Error")){
                      // Server side validation and display error msg
                      $('#error-msg').html(data.replace("Error:","")+"<br/>");
                  } else {
                      $('#modEditForm').html(
                              '<div class="alert alert-success alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'+
                              '<h4><strong><%=MessageUtil.getV8Message(lang, "COMMON_LABEL")%></strong></h4>' +
                              '<p>'+data.replace("Msg:","")+'</p></div>');
                              //Reload List
                              topRefresh();
                  }
              }
          });
      });
      </script>
<% } %>
