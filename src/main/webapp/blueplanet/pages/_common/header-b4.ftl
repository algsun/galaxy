<#--
页面头部.
包括：
1. 业务系统切换
2. 一，二级权限菜单
3. 站点切换
4. 个人信息

@author gaohui
@date 2013-01-21
-->
<nav class="navbar navbar-dark navbar-fixed-top  bg-inverse">
    <ul class="nav navbar-nav yahei-font">
    <#--<div class="container-fluid" style="min-width: 800px;">-->

    <#-- 业务系统 -->
    <#-- <div.brand> -->
    <#--<#include "header/subsystems.ftl">-->
    <#include "/common/pages/header-subsystems-list-b4.ftl">
    <@_subsytem_list "blueplanet"/>
    <#-- </div> -->

    <#-- 一，二级权限菜单 -->
    <#-- <ul.nav> -->
    <#include "header/navs-b4.ftl">
    <#--</ul> -->
        <div class="pull-md-right">
            <li class="divider-vertical"></li>
            <li class="nav-item dropdown">
            <#include "/common/pages/header-logicgroup-list-b4.ftl">
                    <@__logicgroup_list "blueplanet"/>
            </li>

        <#include "/common/pages/header-profile-b4.ftl">
        <@headerProfile "blueplanet"/>
        </div>
    </ul>
<#--</div>-->
</nav>