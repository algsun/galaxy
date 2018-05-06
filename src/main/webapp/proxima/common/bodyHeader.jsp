<%@page import="com.microwise.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div id="templatemo_banner_wrapper">
	<div id="templatemo_banner">
		<div id="banner_header_01" style="width:180px; float:left;">
			<a href="home.action" style="color: #bbb; text-decoration: none;">本体监测</a>
		</div>
		<div class="m-t-30" style="color: white;float:left;">
			<span class="m-r-30" style=""><i class="icon-white icon-user"></i>用户：<span
				class="badge badge-success"><s:property value="#session.userName"/></span> </span>	
			<span class="m-r-30" style=""><i class="icon-white icon-home"></i>当前站点：<span
				class="badge badge-warning"><s:property value="#session.sitename"/></span> </span>
			<a style="color: white;" href="logout.action"><i class="icon-off icon-white"></i>退出</a>
		</div>
		<div style="clear:both;"></div>
	</div>
	<!-- end of banner -->
</div>
<!-- end of banner wrapper -->

<div id="templatemo_menu_wrapper">
	<div id="templatemo_menu">
		<ul>
			<s:iterator id="popedom" value="@com.microwise.util.PopedomUtil@getTopLevel(#session.popedomsOfUser)">
				<%-- 判断是否是一级权限 --%>
				<s:if test="#popedom.navigable">
					<s:if test="#popedom.popedomId == 'dvPlaceManage'">
						<%-- 判断权限，如果有管理员权限, 才能看到点位管理 --%>
						<s:if test="#session.roleId != null">
							<s:if test="@com.microwise.util.CollectionUtil@contains(#application.rolesCouldUseDvPlaceManage, #session.roleId)">
								<li>
									<a <s:if test="#currentNav == 'dvPlaceManage'"> class="current"</s:if> href="queryOpticsDVPlace.action"><span></span><s:property value="#popedom.popedomName" /></a>
								</li>
							</s:if>
						</s:if>
					</s:if>
					<s:else>
						<li>
							<%-- 标签里的判断是为了设置默认选中哪个标头 --%>
							<a <s:if test="#currentNav == #popedom.popedomId"> class="current"</s:if> href="<s:property value="#popedom.url"/>"><span></span>
							<s:property value="#popedom.popedomName" />
							</a>
						</li>
					</s:else>
				</s:if>

			</s:iterator>
			
		</ul>
	</div>
	<!-- end of menu -->
</div>
<!-- end of menu wrapper -->
