<#--
站点列表

@author gaohui
@date 2013-05-22
-->

<#--
可见站点列表

subsystem: 业务系统名称
 -->
<#macro __logicgroup_list subsystem>
<#--当前站点-->
<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" data-hover="dropdown">
    <span class="label label-info p-v-5 elippsis il-blk"
          style="max-width: 160px; font-weight: normal; text-shadow: none;"
          title="${(Session["currentLogicGroup"].logicGroupName)!}">
    ${(Session["currentLogicGroup"].logicGroupName)!"暂无"}
    </span>
    <span class="caret"></span>
</a>
<ul class="dropdown-menu">
<#--当前站点直接子站点-->
    <#if Session["currentSubLogicGroups"]??>
        <#list Session["currentSubLogicGroups"] as logicGroup>
            <a class="dropdown-item"
               href="${switch_logicgroup_url(logicGroup, subsystem)}">${logicGroup.logicGroupName}</a>
        </#list>
    </#if>
    <#if Session["userLogicGroup"]??>
    <#--用户归属站点 (多余的判断是过滤超级管理员)-->
        <li class="dropdown-divider"></li>
        <a class="dropdown-item" href="${switch_logicgroup_url(Session["userLogicGroup"], subsystem)}">
        ${Session["userLogicGroup"].logicGroupName}<span class="muted">(归属站点)</span></a>
    </#if>
    <li class="dropdown-divider"></li>
    <a class="dropdown-item" href="#"
       onclick="javascript: App('common').header.chooseLogicGroup('${subsystem}'); return false;">其它站点...</a>
    <a class="dropdown-item" href="#"
       onclick="javascript: SWITCH_MAP.logicGroupMap('${subsystem}'); return false;">站点地图</a>
</ul>

<div class="hide">
<#--其它站点选择窗口-->
    <div id="availableLogicGroupDialog" class="span4">
        <div id="logicgroup-tree" class="ztree"></div>
        <span class="help-block red m-l-20"></span>
    </div>
<#--站点地图-->
    <div id="map-and-list">
        <div id="logicGroupMap" class="f-l" style="width: 800px;height:600px;"></div>
        <div id="logicGroupList" class="f-l" style="width:200px;height:600px; overflow-y: auto;">
            <ul>
            </ul>
        </div>
        <div style="clear: both;"></div>
    </div>
</div>
</#macro>

<#--
切换站点链接
-->
<#function switch_logicgroup_url logicGroup, subsystem>
<#--
如果是基层站点切换当前业务系统
如果是行政站点跳转到系统管理
 -->
    <#if logicGroup.site??>
        <#return "../blackhole/switchLogicGroup.action?forward=/${subsystem}/&logicGroupId=${logicGroup.id}">
    <#else>
        <#return "../blackhole/switchLogicGroup.action?forward=/blackhole/&logicGroupId=${logicGroup.id}">
    </#if>
</#function>
