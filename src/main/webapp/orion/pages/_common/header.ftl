<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner yahei-font">
        <div class="container-fluid">
        <#-- 业务系统 -->
        <#--<#include "header/subsystems.ftl">-->
        <#include "/common/pages/header-subsystems-list.ftl">
        <@_subsytem_list "orion"/>

        <#include "header/navs.ftl">
            <form class="navbar-search pull-left" action="fulltextRetrieval"
                  onsubmit="if(document.getElementById('relicQueryKeywords').value==='')return false;">
                <input id="relicQueryKeywords" name="key" type="text" value="${key!}" class="input-small search-query"
                       placeholder="全文检索">
            </form>
            <ul class="nav pull-right">
                <li class="divider-vertical"></li>
                <li class="dropdown">
                <#include "/common/pages/header-logicgroup-list.ftl">
                    <@__logicgroup_list "orion"/>
                </li>

            <#include "/common/pages/header-profile.ftl">
            <@headerProfile "orion"/>
            </ul>
        </div>
    </div>
</div>

