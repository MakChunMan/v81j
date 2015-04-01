<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="com.imagsky.common.*" %>
 <%@ page import="com.imagsky.v81j.domain.Member" %>
 <%@ page import="com.imagsky.v81j.domain.App" %>
 <%@ page import="com.imagsky.util.*" %>
 <%@ page import="com.imagsky.common.*" %>
 <%@ page import="java.util.*" %>
<%
String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG); 
Member thisUser = null;
App thisApp = null;
if(!V81Util.isLogined(request)){
    out.println("<script>self.location='/v81/zh/page_ready_login.php';</script>");
} else {
    thisUser = ((ImagskySession)request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getUser();
    thisApp = ((ImagskySession) request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getWorkingApp();
%>
<!DOCTYPE html>
<!--[if IE 9]>         <html class="no-js lt-ie10"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
    <jsp:include page="/jsp/v8/common_head_css_js.jsp"></jsp:include>
    <link href="<%=SystemConstants.V8_PATH %>/fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <!-- Page Wrapper -->
        <!-- In the PHP version you can set the following options from inc/config file -->
        <!--
            Available classes:

            'page-loading'      enables page preloader
        -->
        <div id="page-wrapper" class="page-loading">
            <!-- Preloader -->
            <!-- Preloader functionality (initialized in js/app.js) - pageLoading() -->
            <!-- Used only if page preloader enabled from inc/config (PHP version) or the class 'page-loading' is added in #page-wrapper element (HTML version) -->
            <div class="preloader">
                <div class="inner">
                    <!-- Animation spinner for all modern browsers -->
                    <div class="preloader-spinner themed-background hidden-lt-ie10"></div>

                    <!-- Text for IE9 -->
                    <h3 class="text-primary visible-lt-ie10"><strong>Loading..</strong></h3>
                </div>
            </div>
            <!-- END Preloader -->

            <!-- Page Container -->
            <!-- In the PHP version you can set the following options from inc/config file -->
            <!--
                Available #page-container classes:

                'sidebar-visible-lg-mini'                       main sidebar condensed - Mini Navigation (> 991px)
                'sidebar-visible-lg-full'                       main sidebar full - Full Navigation (> 991px)

                'sidebar-alt-visible-lg'                        alternative sidebar visible by default (> 991px) (You can add it along with another class)

                'header-fixed-top'                              has to be added only if the class 'navbar-fixed-top' was added on header.navbar
                'header-fixed-bottom'                           has to be added only if the class 'navbar-fixed-bottom' was added on header.navbar

                'fixed-width'                                   for a fixed width layout (can only be used with a static header/main sidebar layout)
            -->
            <div id="page-container" class="header-fixed-top sidebar-visible-lg-full">
                <!-- Alternative Sidebar -->
                <div id="sidebar-alt" tabindex="-1" aria-hidden="true">
                    <!-- Toggle Alternative Sidebar Button (visible only in static layout) -->
                    <a href="javascript:void(0)" id="sidebar-alt-close" onclick="App.sidebar('toggle-sidebar-alt');"><i class="fa fa-times"></i></a>

                    <!-- Wrapper for scrolling functionality -->
                    <div id="sidebar-scroll-alt">
                        <!-- Sidebar Content -->
                        <div class="sidebar-content">
                            <!-- Profile -->
                            <jsp:include page="/jsp/v8/common_sidebar_profile.jsp"></jsp:include>
                            <!-- END Profile -->

                            <!-- Settings -->
                            <jsp:include page="/jsp/v8/common_sidebar_setting.jsp"></jsp:include>
                            <!-- END Settings -->
                        </div>
                        <!-- END Sidebar Content -->
                    </div>
                    <!-- END Wrapper for scrolling functionality -->
                </div>
                <!-- END Alternative Sidebar -->

                <!-- Main Sidebar -->
                <div id="sidebar">
                    <!-- Sidebar Brand -->
                    <div id="sidebar-brand" class="themed-background">
                        <a href="index.html" class="sidebar-title">
                            <i class="fa fa-cube"></i> <span class="sidebar-nav-mini-hide">App<strong>UI</strong></span>
                        </a>
                    </div>
                    <!-- END Sidebar Brand -->

                    <!-- Wrapper for scrolling functionality -->
                    <div id="sidebar-scroll">
                        <!-- Sidebar Content -->
                        <div class="sidebar-content">
                            <!-- Sidebar Navigation -->
                            <jsp:include page="/jsp/v8/common_sidebar_main.jsp"></jsp:include>
                            <!-- END Sidebar Navigation -->

                            <!-- Color Themes -->
                            <!-- Preview a theme on a page functionality can be found in js/app.js - colorThemePreview() -->
                            <jsp:include page="/jsp/v8/common_themecolor.jsp"></jsp:include>
                            <!-- END Color Themes -->
                        </div>
                        <!-- END Sidebar Content -->
                    </div>
                    <!-- END Wrapper for scrolling functionality -->

                    <!-- Sidebar Extra Info -->
                    <jsp:include page="/jsp/v8/common_sidebar_left_extra.jsp"></jsp:include>
                    <!-- END Sidebar Extra Info -->
                </div>
                <!-- END Main Sidebar -->

                <!-- Main Container -->
                <div id="main-container">
                    <!-- Header -->
                    <!-- In the PHP version you can set the following options from inc/config file -->
                    <!--
                        Available header.navbar classes:

                        'navbar-default'            for the default light header
                        'navbar-inverse'            for an alternative dark header

                        'navbar-fixed-top'          for a top fixed header (fixed main sidebar with scroll will be auto initialized, functionality can be found in js/app.js - handleSidebar())
                            'header-fixed-top'      has to be added on #page-container only if the class 'navbar-fixed-top' was added

                        'navbar-fixed-bottom'       for a bottom fixed header (fixed main sidebar with scroll will be auto initialized, functionality can be found in js/app.js - handleSidebar()))
                            'header-fixed-bottom'   has to be added on #page-container only if the class 'navbar-fixed-bottom' was added
                    -->
                    <header class="navbar navbar-inverse navbar-fixed-top">
                        <!-- Left Header Navigation -->
                        <jsp:include page="/jsp/v8/common_head_left.jsp"></jsp:include>
                        <!-- END Left Header Navigation -->

                        <!-- Right Header Navigation -->
                        <jsp:include page="/jsp/v8/common_head_right.jsp"></jsp:include>
                        <!-- END Right Header Navigation -->
                    </header>
                    <!-- END Header -->

                    <!-- Page content -->
                    <div id="page-content">
                        <!-- Breadcrumb Header -->
                        <div class="content-header">
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="header-section">
                                        <h1>手機程式 - <%=thisApp.getAPP_NAME()%></h1>
                                    </div>
                                </div>
                                <div class="col-sm-6 hidden-xs">
                                    <div class="header-section">
                                        <ul class="breadcrumb breadcrumb-top">
                                            <li>XX</li>
                                            <li><a href="/do/APP/APP_MAIN"><%=MessageUtil.getV8Message(lang,"APP_MGMT_TITLE") %></a></li>
                                            <li><Strong><%=thisApp.getAPP_NAME()%></Strong></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- END Forms Components Header -->
                        <!-- First Row (My Module) -->
                        <div class="row">
                            <div class="col-sm-6 col-lg-8">
                                <!-- Button Styles Block -->
                                <div class="block">
                                    <!-- Button Styles Title -->
                                    <div class="block-title">
                                        <div class="block-options pull-right">
                                            <a href="javascript:$('#moduleTemplateRow').show();$('#moduleEditRow').hide();" class="btn btn-effect-ripple btn-default" data-toggle="tooltip" title="Add"><i class="fa fa-plus"></i></a>
                                            <a href="javascript:topSave()" class="btn btn-effect-ripple btn-default" data-toggle="tooltip" title="<%=MessageUtil.getV8Message(lang,"BTN_SAVE")%>"><i class="fa fa-file-o"></i></a>
                                            <a href="javascript:topDelete()" class="btn btn-effect-ripple btn-default" data-toggle="tooltip" title="<%=MessageUtil.getV8Message(lang,"BTN_DELETE")%>" id="topDeleteBtn" style='display:none'><i class="fa fa-trash-o"></i></a>
                                            <a href="javascript:topRefresh()" class="btn btn-effect-ripple btn-default" data-toggle="tooltip" title="<%=MessageUtil.getV8Message(lang,"BTN_RESET")%>"><i class="fa fa-refresh"></i></a>
                                        </div>
                                        <h2><i class="fa fa-fw fa-database"></i> <strong>我的程式頁 - 共<span id="page-count"> </span>頁</strong></h2>
                                    </div>
                                    <!-- END Button Styles Title -->

                                    <!-- Ajax Form  -->
                                    <form id="moduleListForm">
                                            
                                   </form>
                                   <div class="form-group" id="save_alert">
                                            <%=MessageUtil.getV8Message(lang,"MOD_SAVE_ALERT") %> <button type="button" class="btn btn-xs btn-effect-ripple btn-primary" onclick="javascript:topSave()"><%=MessageUtil.getV8Message(lang,"BTN_SAVE")%></button>
                                   </div>
                                   <!-- Debug -->
                                   <a href="javascript:void(0)" onclick="$('#ajax_source').text($( '#moduleListForm').html());">Source</a>
                                    <div id="ajax_source" style="color:grey"></div>
                                   <!-- End Debug -->
                                   <!-- END Button Styles Content -->
                                </div>
                                <!-- END Button Styles Block -->
                            </div>
                            <div class="col-sm-6 col-lg-4">
                                <!-- Stats User Widget -->
                                    <div class="widget-content widget-content-mini themed-background-dark text-light-op">
                                        <i class="fa fa-fw fa-trophy"></i> <strong><%=MessageUtil.getV8Message(lang,"APP_NEW_APP") %></strong>
                                    </div>
                                    <div class="widget-content themed-background-muted text-dark text-center">
                                     <form id="app_add_form" method="post" class="form-inline" onsubmit="return false;">
                                        <div class="form-group">
                                            <input type="text" id="app-name" name="app-name" class="form-control" placeholder="<%=MessageUtil.getV8Message(lang,"APP_NEW_APP_NAME")%>">
                                        </div>
                                        <div class="form-group">
                                             <select id="app-type" name="app-type" class="form-control" size="1">
                                                        <option value="0"><%=MessageUtil.getV8Message(lang,"APP_NEW_SELECT_TYPE")%></option>
                                                        <option value="1"><%=MessageUtil.getV8Message(lang,"APP_TYPE_BASIC")%></option>
                                                        <option value="2"><%=MessageUtil.getV8Message(lang,"APP_TYPE_SHOP")%></option>
                                                        <option value="3"><%=MessageUtil.getV8Message(lang,"APP_TYPE_PDA")%></option>
                                                    </select>
                                        </div>
                                        <div class="form-group">
                                            <button id="app-add-submit" type="submit" class="btn btn-effect-ripple btn-primary"><%=MessageUtil.getV8Message(lang,"BTN_CREATE")%></button>
                                        </div>
                                    </form>
                                    </div>
                                <!-- END Stats User Widget -->
                            </div>
                        </div>
                        <!-- END First Row -->
                        <!-- Second Row (Template Sample Row) -->
                        <div class="row" id="moduleTemplateRow" style="display:none">
                            <div class="col-sm-6 col-lg-8">
                                <!-- Data Table -->
                                <div class="block">
                                    <div class="block-title">
                                        <div class="block-options pull-right">
                                            <a href="javascript:$('#moduleTemplateRow').hide();" class="btn btn-effect-ripple btn-default" data-toggle="tooltip" title="Close"><i class="fa fa-minus"></i></a>
                                        </div>
                                        <h2><i class="fa fa-fw fa-database"></i> <strong>Recommanded App Pages</strong></h2>
                                    </div>
                                    <div class="widget-content">
                                            <div class="row text-center">
                                            <div class="col-xs-2">
                                                <a class="widget moduleWidget" id="moduleAboutPage"  typename="ModAboutPage">
                                                <%=ModuleTemplateUIConstants.getUIHtml_modListPage("modAboutPage") %>
                                               <br>About Us <i class="fa fa-info-circle info"></i>
                                               </a>
                                            </div>
                                            <div class="col-xs-2">
                                                <a class="widget moduleWidget" id="moduleCatalog" typename="ModCatalog">
                                                <%=ModuleTemplateUIConstants.getUIHtml_modListPage("ModCatalog") %>
                                                <br>Catalog <i class="fa fa-info-circle info"></i>
                                                </a>
                                            </div>
                                            <div class="col-xs-2">
                                                <a class="widget moduleWidget" id="moduleForm" typename="ModForm"> 
                                                <%=ModuleTemplateUIConstants.getUIHtml_modListPage("ModForm") %>
                                                <br>Online Form <i class="fa fa-info-circle info"></i>
                                                </a>
                                            </div>
                                            <div class="col-xs-2">
                                            </div>
                                            <div class="col-xs-2">
                                            </div>
                                            <div class="col-xs-2">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- END Data Table -->
                            </div>
                        </div>
                        <!-- END Second Row -->
                        <!--  Third Row (Edit widget)-->
                        <div class="row" id="moduleEditRow">
                            <div class="col-sm-6 col-lg-8">
                                <!-- AJAX EDIT Table -->
                                <div class="block" id="modEditForm">
                                </div>
                                <!-- END AJAX EDIT Table -->                                 
                        </div>
                        <!--  End Third Row -->
                    </div>
                    <!-- END Page Content -->
                </div>
                <!-- END Main Container -->
            </div>
            <!-- END Page Container -->
        </div>
        <!-- END Page Wrapper -->
        
       <!-- Upload Modal -->
        <div id="modal-upload" class="modal" tabindex="-1" role="dialog" aria-hidden="true">
            <form enctype="multipart/form-data">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h5 class="modal-title"><strong>Upload Modal</strong></h5>
                    </div>
                    <div class="modal-body">
                        <input id="file-1a" name="imageURL" type="file" multiple=true class="file" data-show-upload='false' data-preview-file-type="any" data-initial-caption="Please" data-overwrite-initial="false">
                    </div>
                    <div class="modal-footer">
                        <button type="button" id="btn_image_upload_add" class="btn btn-effect-ripple btn-primary">Add</button>
                        <button type="button" class="btn btn-effect-ripple btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
            </form>
        </div>
        <!-- END Upload Modal -->
        
        <!-- Include Jquery library from Google's CDN but if something goes wrong get Jquery from local file (Remove 'http:' if you have SSL) -->
        <jsp:include page="/jsp/v8/common_footer_js.jsp"></jsp:include>
        <script src="<%=SystemConstants.V8_PATH %>/fileinput/js/fileinput.js" type="text/javascript"></script>
        <script>
        //Event Handler for hidden Modal of Image Upload
        var workingImage = "";
        var workingModTypeForImage = "";
        $('#btn_image_upload_add').click(function(){
            var data = new FormData();
            jQuery.each($('#file-1a')[0].files, function(i, file) {
                data.append('file-'+i, file);
            });
            data.append('modtype',workingModTypeForImage); //Manually add parameter
            $.ajax({
                url: '<%=SystemConstants.V8_PATH %>php/filehandler.php',
                contentType:"multipart/form-data",
                data: data,
                cache: false,
                contentType: false,
                processData: false,
                type: 'POST',
                success: function(data){
                    if($.trim(data).match("^Error")){
                        // Server side validation and display error msg
                        $('#'+workingImage).html(datas.replace("Error:","")+"<br/>");
                    } else {
                        $('#'+workingImage).html("<img width='300' src='<%=SystemConstants.V8_PATH %>userfiles/tmp/"+data.replace("Msg:","")+"'><input type=hidden name="+workingImage+" value='"+data.replace("Msg:","")+"'/>");
                        $('#modal-upload').modal('hide');   
                    }
                }
            });
        });
        </script>
        <!-- Load and execute javascript code used only in this page -->
        <script src="<%=SystemConstants.V8_PATH %>js/v81/pages/modMain.js"></script>
    </body>
</html>
<%}%>
