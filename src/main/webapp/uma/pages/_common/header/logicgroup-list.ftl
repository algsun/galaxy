<#--
TODO 废弃 @gaohui 2013-05-22

@author li.jianfei
@time  13-4-11  13:39
-->


<#--当前站点-->
<a class="dropdown-toggle" data-toggle="dropdown" href="#" data-hover="dropdown">
    <span class="label label-info p-v-5 elippsis il-blk"
          style="max-width: 160px; font-weight: normal; text-shadow: none;"
          title="${(Session["currentLogicGroup"].logicGroupName)!}">
    <#if Session["currentLogicGroup"]??>
    ${Session["currentLogicGroup"].logicGroupName}
    <#else>
        暂无
    </#if>
    </span>
    <span class="caret"></span>
</a>
<ul class="dropdown-menu">
<#--当前站点直接子站点-->
<#if Session["currentSubLogicGroups"]??>
    <#list Session["currentSubLogicGroups"] as logicGroup>
        <li>
            <a href="${switch_logicgroup_url(logicGroup)}">${logicGroup.logicGroupName}</a>
        </li>
    </#list>
</#if>
<#if Session["userLogicGroup"]??>
<#--用户归属站点 (多余的判断是过滤超级管理员)-->
    <li class="divider"></li>
    <li>
        <a href="${switch_logicgroup_url(Session["userLogicGroup"])}">
        ${Session["userLogicGroup"].logicGroupName}<span class="muted">(归属站点)</span></a>
    </li>
</#if>
    <li class="divider"></li>
    <li><a href="#" onclick="javascript: App('common').header.chooseLogicGroup('uma'); return false;">其它站点...</a></li>
    <li><a href="#" onclick="javascript: SWITCH_MAP.logicGroupMap('uma'); return false;">站点地图</a></li>
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

<#--
切换站点链接
-->
<#function switch_logicgroup_url logicGroup>
<#--
如果是基层站点切换当前业务系统
如果是行政站点跳转到系统管理
 -->
    <#if logicGroup.site??>
        <#return "../blackhole/switchLogicGroup.action?forward=/uma/&logicGroupId=${logicGroup.id}">
    <#else>
        <#return "../blackhole/switchLogicGroup.action?forward=/blackhole/&logicGroupId=${logicGroup.id}">
    </#if>
</#function>
