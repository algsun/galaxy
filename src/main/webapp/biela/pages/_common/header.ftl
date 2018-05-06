<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner yahei-font">
        <div class="container-fluid" style="min-width: 800px;">
        <#-- 业务系统 -->
        <#--<#include "header/subsystems.ftl">-->
        <#--<#include "/common/pages/header-subsystems-list.ftl">-->
        <#include "header-subsystems-list.ftl">
        <@_subsytem_list "biela"/>
            <#include "header/top-navs.ftl">
            <ul class="nav pull-right">
                <#include "/common/pages/header-profile.ftl">
                <@headerProfile "biela"/>
            </ul>
        </div>
    </div>
</div>

