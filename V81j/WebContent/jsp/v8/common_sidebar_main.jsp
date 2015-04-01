<%--
2014-10-27 include page (left side bar)
 --%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.imagsky.common.*" %>
<%@ page import="com.imagsky.v81j.domain.Member" %>
<%@ page import="com.imagsky.v81j.domain.App" %>
<%@ page import="com.imagsky.common.*" %>
  <%@ page import="com.imagsky.util.*" %>
 <%@ page import="java.util.*" %>
 <%
String lang = (String)request.getAttribute(SystemConstants.REQ_ATTR_LANG); 
String appCode = CommonUtil.null2Empty(request.getAttribute(SystemConstants.REQ_ATTR_APPCODE));
String[] token = (String[])request.getAttribute(SystemConstants.REQ_ATTR_URL_PATTERN);
Member thisUser = null;
App thisApp = null;
if(!V81Util.isLogined(request)){
    out.println("<script>self.location='/v81/zh/page_ready_login.php';</script>");
} else {
    thisUser = ((ImagskySession)request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getUser();
//    thisApp = ((ImagskySession) request.getSession().getAttribute(SystemConstants.REQ_ATTR_SESSION)).getWorkingApp();
%>   
<%="<!-- common_sidebar_main -->" %> 
                            <ul class="sidebar-nav">
                                <li>
                                    <a href="/do/PAGE/PUB_MAIN" <%="PAGE".equalsIgnoreCase(appCode)?"class='active'":"" %>><i class="gi gi-compass sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">Dashboard</span></a>
                                </li>
                                <li class="sidebar-separator">
                                    <i class="fa fa-ellipsis-h"></i>
                                </li>
                                <li <%=("APP".equalsIgnoreCase(appCode) || "MOD".equalsIgnoreCase(appCode))?"class='active'":"" %>>
                                    <%--<a href="/do/APP/APP_MAIN" ><i class="gi gi-inbox sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">Mobile App</span></a> --%>
                                    <a href="/portal/APP/APP_MAIN.do" class="sidebar-nav-menu"><i class="fa fa-chevron-left sidebar-nav-indicator sidebar-nav-mini-hide"></i><i class="gi gi-inbox sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">Your Mobile App</span></a>
                                    <ul>
                                        <% 
                                        Iterator it = thisUser.getApps().iterator();
                                        while(it.hasNext()){
                                            thisApp = (App)it.next();                                     	
                                        %>
                                        <li>
                                            <a href="/portal/MOD/MOD_ADD_MAIN/<%=thisApp.getSys_guid()%>.do"  <%=
                                            		token!=null && token.length>2 && token[2].equalsIgnoreCase(thisApp.getSys_guid())
                                            	    ?"class='active'":"" %>><%=thisApp.getAPP_NAME() %></a>
                                        </li>
                                        <% } %>
                                        <li>
                                            <a href="/portal/APP/APP_MAIN.do"  <%=
                                                    token!=null && token.length>1 && token[1].equalsIgnoreCase("APP_MAIN")?"class='active'":"" %>><i class="gi gi-circle_plus sidebar-nav-icon"></i>Create new app</a>
                                        </li>
                                    </ul>
                                </li>                                
                                <li>
                                    <a href="#" class="sidebar-nav-menu"><i class="fa fa-chevron-left sidebar-nav-indicator sidebar-nav-mini-hide"></i><i class="fa fa-rocket sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">User Interface</span></a>
                                    <ul>
                                        <li>
                                            <a href="/v81/index.html">V81 UI site</a>
                                        </li>
                                        <li>
                                            <a href="page_ui_widgets.html">Widgets</a>
                                        </li>
                                        <li>
                                            <a href="#" class="sidebar-nav-submenu"><i class="fa fa-chevron-left sidebar-nav-indicator"></i>Elements</a>
                                            <ul>
                                                <li>
                                                    <a href="page_ui_blocks_grid.html">Blocks &amp; Grid</a>
                                                </li>
                                                <li>
                                                    <a href="page_ui_typography.html">Typography</a>
                                                </li>
                                                <li>
                                                    <a href="page_ui_buttons_dropdowns.html">Buttons &amp; Dropdowns</a>
                                                </li>
                                                <li>
                                                    <a href="page_ui_navigation_more.html">Navigation &amp; More</a>
                                                </li>
                                                <li>
                                                    <a href="page_ui_progress_loading.html">Progress &amp; Loading</a>
                                                </li>
                                                <li>
                                                    <a href="page_ui_tables.html">Tables</a>
                                                </li>
                                            </ul>
                                        </li>
                                        <li>
                                            <a href="#" class="sidebar-nav-submenu"><i class="fa fa-chevron-left sidebar-nav-indicator"></i>Forms</a>
                                            <ul>
                                                <li>
                                                    <a href="page_forms_components.html">Components</a>
                                                </li>
                                                <li>
                                                    <a href="page_forms_wizard.html">Wizard</a>
                                                </li>
                                                <li>
                                                    <a href="page_forms_validation.html">Validation</a>
                                                </li>
                                            </ul>
                                        </li>
                                        <li>
                                            <a href="#" class="sidebar-nav-submenu"><i class="fa fa-chevron-left sidebar-nav-indicator"></i>Icon Packs</a>
                                            <ul>
                                                <li>
                                                    <a href="page_ui_icons_fontawesome.html">Font Awesome</a>
                                                </li>
                                                <li>
                                                    <a href="page_ui_icons_glyphicons_pro.html">Glyphicons Pro</a>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#" class="sidebar-nav-menu"><i class="fa fa-chevron-left sidebar-nav-indicator sidebar-nav-mini-hide"></i><i class="gi gi-airplane sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">Components</span></a>
                                    <ul>
                                        <li>
                                            <a href="page_comp_todo.html">To-do List</a>
                                        </li>
                                        <li>
                                            <a href="page_comp_gallery.html">Gallery</a>
                                        </li>
                                        <li>
                                            <a href="page_comp_maps.html">Google Maps</a>
                                        </li>
                                        <li>
                                            <a href="page_comp_calendar.html">Calendar</a>
                                        </li>
                                        <li>
                                            <a href="page_comp_charts.html">Charts</a>
                                        </li>
                                        <li>
                                            <a href="page_comp_animations.html">CSS3 Animations</a>
                                        </li>
                                        <li>
                                            <a href="page_comp_tree.html">Tree View Lists</a>
                                        </li>
                                        <li>
                                            <a href="page_comp_nestable.html">Nestable &amp; Sortable Lists</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#" class="sidebar-nav-menu"><i class="fa fa-chevron-left sidebar-nav-indicator sidebar-nav-mini-hide"></i><i class="gi gi-more_items sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">UI Layouts</span></a>
                                    <ul>
                                        <li>
                                            <a href="page_layout_static.html">Static</a>
                                        </li>
                                        <li>
                                            <a href="page_layout_static_fixed_width.html">Static Fixed Width</a>
                                        </li>
                                        <li>
                                            <a href="page_layout_fixed_top.html">Top Header (Fixed)</a>
                                        </li>
                                        <li>
                                            <a href="page_layout_fixed_bottom.html">Bottom Header (Fixed)</a>
                                        </li>
                                        <li>
                                            <a href="page_layout_static_sidebar_mini.html">Sidebar Mini (Static)</a>
                                        </li>
                                        <li>
                                            <a href="page_layout_fixed_sidebar_mini.html">Sidebar Mini (Fixed)</a>
                                        </li>
                                        <li>
                                            <a href="page_layout_alternative_sidebar_visible.html">Visible Alternative Sidebar</a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#" class="sidebar-nav-menu"><i class="fa fa-chevron-left sidebar-nav-indicator sidebar-nav-mini-hide"></i><i class="fa fa-gift sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">Extra Pages</span></a>
                                    <ul>
                                        <li>
                                            <a href="page_ready_error.html">Error Page</a>
                                        </li>
                                        <li>
                                            <a href="page_ready_blank.html">Blank</a>
                                        </li>
                                        <li>
                                            <a href="page_ready_article.html">Article</a>
                                        </li>
                                        <li>
                                            <a href="page_ready_timeline.html">Timeline</a>
                                        </li>
                                        <li>
                                            <a href="page_ready_invoice.html">Invoice</a>
                                        </li>
                                        <li>
                                            <a href="page_ready_search_results.html">Search Results</a>
                                        </li>
                                        <li>
                                            <a href="page_ready_pricing_tables.html">Pricing Tables</a>
                                        </li>
                                        <li>
                                            <a href="page_ready_faq.html">FAQ</a>
                                        </li>
                                        <li>
                                            <a href="page_ready_profile.html">User Profile</a>
                                        </li>
                                        <li>
                                            <a href="#" class="sidebar-nav-submenu"><i class="fa fa-chevron-left sidebar-nav-indicator"></i>Login, Register &amp; Lock</a>
                                            <ul>
                                                <li>
                                                    <a href="page_ready_login.html">Login</a>
                                                </li>
                                                <li>
                                                    <a href="page_ready_reminder.html">Password Reminder</a>
                                                </li>
                                                <li>
                                                    <a href="page_ready_register.html">Register</a>
                                                </li>
                                                <li>
                                                    <a href="page_ready_lock_screen.html">Lock Screen</a>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </li>
                                <li class="sidebar-separator">
                                    <i class="fa fa-ellipsis-h"></i>
                                </li>
                                <li>
                                    <a href="page_app_email.html"><i class="gi gi-inbox sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">Email Center</span></a>
                                </li>
                                <li>
                                    <a href="page_app_social.html"><i class="fa fa-share-alt sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">Social Net</span></a>
                                </li>
                                <li>
                                    <a href="page_app_media.html"><i class="gi gi-picture sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">Media Box</span></a>
                                </li>
                                <li>
                                    <a href="page_app_estore.html"><i class="gi gi-shopping_cart sidebar-nav-icon"></i><span class="sidebar-nav-mini-hide">eStore</span></a>
                                </li>
                            </ul>
<% }  %>                            