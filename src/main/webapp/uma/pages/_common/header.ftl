<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner yahei-font">
        <#--<div class="container">-->
        <div class="container-fluid" style="min-width: 800px;">
        <#-- 业务系统 -->
        <#--<#include "header/subsystems.ftl">-->
        <#include "/common/pages/header-subsystems-list.ftl">
        <@_subsytem_list "uma"/>

        <#include "header/navs.ftl">
            <ul class="nav pull-right">
                <li class="divider-vertical"></li>
                <li class="dropdown">
                    <#include "/common/pages/header-logicgroup-list.ftl">
                    <@__logicgroup_list "uma"/>
                </li>

                <#include "/common/pages/header-profile.ftl">
                <@headerProfile "uma"/>
            </ul>
        </div>
    </div>
</div>

