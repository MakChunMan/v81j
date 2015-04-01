<%@ page import="com.imagsky.common.*" %>
<%--
2014-10-08 include page
 --%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%="<!-- common_head_right -->" %>                           
<ul class="nav navbar-nav-custom pull-right">
                <!-- Search Form -->
                <li>
                    <form action="page_ready_search_results.html" method="post" class="navbar-form-custom" role="search">
                        <input type="text" id="top-search" name="top-search" class="form-control" placeholder="Search..">
                    </form>
                </li>
                <!-- END Search Form -->

                <!-- Alternative Sidebar Toggle Button -->
                <li>
                    <a href="javascript:void(0)" onclick="App.sidebar('toggle-sidebar-alt');">
                        <i class="gi gi-settings"></i>
                    </a>
                </li>
                <!-- END Alternative Sidebar Toggle Button -->

                <!-- User Dropdown -->
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="<%=SystemConstants.V8_PATH %>img/placeholders/avatars/avatar9.jpg" alt="avatar">
                    </a>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li class="dropdown-header">
                            <strong>ADMINISTRATOR</strong>
                        </li>
                        <li>
                            <a href="page_app_email.html">
                                <i class="fa fa-inbox fa-fw pull-right"></i>
                                Inbox
                            </a>
                        </li>
                        <li>
                            <a href="page_app_social.html">
                                <i class="fa fa-pencil-square fa-fw pull-right"></i>
                                Profile
                            </a>
                        </li>
                        <li>
                            <a href="page_app_media.html">
                                <i class="fa fa-picture-o fa-fw pull-right"></i>
                                Media Manager
                            </a>
                        </li>
                        <li class="divider"><li>
                        <li>
                            <a href="javascript:void(0)" onclick="App.sidebar('toggle-sidebar-alt');">
                                <i class="gi gi-settings fa-fw pull-right"></i>
                                Settings
                            </a>
                        </li>
                        <li>
                            <a href="page_ready_lock_screen.html">
                                <i class="gi gi-lock fa-fw pull-right"></i>
                                Lock Account
                            </a>
                        </li>
                        <li>
                            <a href="/portal/PAGE/DO_LOGOUT.do">
                                <i class="fa fa-power-off fa-fw pull-right"></i>
                                Log out
                            </a>
                        </li>
                    </ul>
                </li>
                <!-- END User Dropdown -->
</ul>