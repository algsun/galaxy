<#--
页面头部

@author gaohui
@date 2012-11-28
-->

<#-- .data-header 样式只是为了选择，他上面没有样式 -->
<div class="data-header navbar navbar-inverse navbar-static-top">
    <div class="navbar-inner yahei-font">
        <#--<div class="container">-->
        <div class="container-fluid" style="min-width: 800px;">
            <#-- 业务系统列表 -->
            <#--<div class="brand dropdown">-->
            <#include "/common/pages/header-subsystems-list.ftl">
            <@_subsytem_list "blackhole"/>
            <#--</div>-->

        <#-- 一级权限菜单 -->
        <#--<ul class="nav">-->
        <#include "header/top-navs.ftl">
        <#--</ul>-->

            <ul class="nav pull-right">
                <li class="divider-vertical"></li>
                <li class="dropdown">
                <#--站点状态我站点列表-->
                    <#include "header/logicgroup-list.ftl">
                </li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" data-hover="dropdown">
                        <span class="elippsis il-blk" style="max-width: 70px;"
                              title="${Session.currentUser.userName}">${Session.currentUser.userName}</span>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                    <#-- 超级管理员回到顶级站点页面 -->
                    <#if security.isSuperman()>
                        <li><a href="clearCurrentLogicGroup.action?forward=/blackhole/chooseLogicGroup.action">
                            <i class="icon-home"></i>顶级站点</a>
                        </li>
                    </#if>

                    <#if !security.isAnonymity()>
                        <li><a href="toHome.action?forward=/blackhole/updateProfile.action"><i class="icon-user"></i>个人信息</a>
                        </li>
                        <li><a href="toHome.action?forward=/blackhole/changePassword.action"><i class="icon-lock"></i>修改密码</a>
                        </li>
                        <li id="qrcode-profile" data-username="${Session.currentUser.email}"><a href="javascript: void(0);"><i class="icon-qrcode"></i>我的二维码</a></li>
                    </#if>
                        <li><a href="logout.action?from=blackhole"><i class="icon-off"></i>退出</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>

