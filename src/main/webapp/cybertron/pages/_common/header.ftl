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
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner yahei-font">
        <div class="container-fluid" style="min-width: 800px;">

            <#-- 业务系统 -->
            <#-- <div.brand> -->
            <#include "/common/pages/header-subsystems-list.ftl">
            <@_subsytem_list "cybertron"/>
            <#-- </div> -->

            <#-- 一，二级权限菜单 -->
            <#-- <ul.nav> -->
            <#include "header/navs.ftl">
            <#-- </ul> -->

            <ul class="nav pull-right">
                <li class="divider-vertical"></li>
                <li class="dropdown">
                    <#include "/common/pages/header-logicgroup-list.ftl">
                    <@__logicgroup_list "cybertron"/>
                </li>

                <#include "/common/pages/header-profile.ftl">
                <@headerProfile "cybertron"/>
            </ul>
        </div>
    </div>
</div>