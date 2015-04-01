<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ page import="com.imagsky.common.*" %>
 <%@ page import="com.imagsky.v81j.domain.App" %>
 <%@ page import="com.imagsky.v81j.domain.Member" %>
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
<!DOCTYPE html>
<!--[if IE 9]>         <html class="no-js lt-ie10"> <![endif]-->
<!--[if gt IE 9]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
    <jsp:include page="/jsp/v8/common_head_css_js.jsp"></jsp:include>
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
                                        <h1><%=MessageUtil.getV8Message(lang,"APP_MGMT_TITLE") %></h1>
                                    </div>
                                </div>
                                <div class="col-sm-6 hidden-xs">
                                    <div class="header-section">
                                        <ul class="breadcrumb breadcrumb-top">
                                            <li>XX</li>
                                            <li>XX</li>
                                            <li><a href=""><%=MessageUtil.getV8Message(lang,"APP_MGMT_TITLE") %></a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- END Forms Components Header -->
                        <!-- First Row -->
                        <div class="row">
                            <div class="col-sm-6 col-lg-8">
                                <!-- Data Table -->
                                <div class="widget">
                                    <div class="widget-content widget-content-mini themed-background-dark text-light-op">
                                        <i class="fa fa-fw fa-database"></i> <strong><%=MessageUtil.getV8Message(lang,"APP_WORK_APP") %></strong>
                                    </div>
                                    <div class="widget-content">
                                            <!-- Table Styles Content -->
				                            <div class="table-responsive" id="app-list">
				                            </div>
				                            <!-- END Table Styles Content -->
				                            
				                            <!-- Ajax - Edit Form /do/APP/EDIT-->
			                                <div class="block" id='APP_EDIT_FORM'>
			                                </div>
				                            <!-- END Edit Form -->
                                    </div>
                                </div>
                                <!-- END Data Table -->
                            </div>
                            <div class="col-sm-6 col-lg-4">
                                <!-- Stats User Widget -->
                                <a href="javascript:void(0)" class="widget">
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
                                            <button id="app-add-submit" type="submit" class="btn btn-effect-ripple btn-primary">Create</button>
                                        </div>
                                    </form>
                                    </div>
                                    <div class="widget-content">
                                        <div class="row text-center">
                                            <div class="col-xs-4">
                                                <h3 class="widget-heading"><i class="gi gi-share_alt text-success push-bit"></i> <br><small>1 Admin Users</small></h3>
                                            </div>
                                            <div class="col-xs-4">
                                                <h3 class="widget-heading"><i class="gi gi-briefcase text-info push-bit"></i> <br><small><%=thisUser.getApps().size() %> / 10 Projects</small></h3>
                                            </div>
                                            <div class="col-xs-4">
                                                <h3 class="widget-heading"><i class="gi gi-heart_empty text-danger push-bit"></i> <br><small>5.3k Likes</small></h3>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                                <!-- END Stats User Widget -->

                                <!-- Mini Widgets Row -->
                                <div class="row">
                                    <div class="col-xs-6">
                                        <a href="javascript:void(0)" class="widget text-center">
                                            <div class="widget-content themed-background-info text-light-op text-center">
                                                <div class="widget-icon center-block push">
                                                    <i class="fa fa-facebook"></i>
                                                </div>
                                                <strong>98k Likes</strong>
                                            </div>
                                        </a>
                                    </div>
                                    <div class="col-xs-6">
                                        <a href="javascript:void(0)" class="widget">
                                            <div class="widget-content themed-background-danger text-light-op text-center">
                                                <div class="widget-icon center-block push">
                                                    <i class="fa fa-database"></i>
                                                </div>
                                                <strong>3 Servers</strong>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                                <!-- END Mini Widgets Row -->
                            </div>
                        </div>
                        <!-- END Second Row -->
                    </div>
                    <!-- END Page Content -->
                </div>
                <!-- END Main Container -->
            </div>
            <!-- END Page Container -->
        </div>
        <!-- END Page Wrapper -->

    
        <!-- Include Jquery library from Google's CDN but if something goes wrong get Jquery from local file (Remove 'http:' if you have SSL) -->
        <jsp:include page="/jsp/v8/common_footer_js.jsp"></jsp:include>
        
        <!-- Load and execute javascript code used only in this page -->
        <script src="<%=SystemConstants.V8_PATH %>js/pages/readyDashboard.js"></script>
        <script>$(function(){ ReadyDashboard.init(); });</script>
        <script>
        function appEdit(guid){
        	$.ajax({
        		url:"/portal/APP/AJ_SHOWEDIT.do?guid="+guid,
        		cache: false
        	}).done(function( html ) {
                if($.trim(html).match("^Error")){
                    // Server side validation and display error msg
                    $('#error-msg').html(html.replace("Error:","")+"<br/>");
                } else {
                	$("#APP_EDIT_FORM").html(html);
                	$('#edit-app-name').focus();
                }
            });
        }
        $.ajax({
            url:"/portal/APP/AJ_LIST.do",
            cache: false
        }).done(function( html ) {
                $('#app-list').html(html);
        });
        
        $('#app-add-submit').click(function(){
        	$.ajax({
                url:"/portal/APP/AJ_APP_ADD.do",
                data: $('#app_add_form').serialize(),
                type: "post",               
                cache: false
            }).done(function( html ) {
                if($.trim(html).match("^Error")){
                    // Server side validation and display error msg
                    $('#error-msg').html(html.replace("Error:","")+"<br/>");
                } else {
                	$('#app-name').val("");
                	$('#app-type').val("");
                    $('#APP_EDIT_FORM').html(
                    '<div class="alert alert-success alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'+
                    '<h4><strong>Success</strong></h4>' +
                    '<p>'+html.replace("Msg:","")+'</p></div>');
                    //Reload List
                    $.ajax({
                        url:"/portal/APP/AJ_LIST.do",
                        cache: false
                    }).done(function( html ) {
                            $('#app-list').html(html);
                    });
                }
            });
        });
        </script>
    </body>
</html>
<%}%>