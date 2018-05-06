<nav class="navbar navbar-dark navbar-fixed-top  bg-inverse">
    <ul class="nav navbar-nav yahei-font">
    <#-- 业务系统 -->
    <#--<#include "header/subsystems.ftl">-->
    <#include "/common/pages/header-subsystems-list-b4.ftl">
    <@_subsytem_list "phoenix"/>

    <#-- TODO 暂时注释 @gaohui 2013-07-02 -->
    <#--<#include "header/navs.ftl">-->

        <div class="pull-md-right">
            <li class="divider-vertical"></li>
            <li class="nav-item dropdown">
            <#include "/common/pages/header-logicgroup-list-b4.ftl">
                    <@__logicgroup_list "phoenix"/>
            </li>


        <#include "/common/pages/header-profile-b4.ftl">
        <@headerProfile "phoenix"/>
        </div>
    </ul>
</nav>

