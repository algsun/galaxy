<#--
显示当前站点，当前站点直接子站点, 归属站点

@author 2012-11-28
@date 2012-11-28
-->

<#--当前站点-->
<a class="dropdown-toggle" style="padding-left: 5px; padding-right: 5px;" data-toggle="dropdown" href="#" data-hover="dropdown">
    <span class="label label-info p-v-5 elippsis il-blk"
          style="max-width: 130px;font-weight: normal; text-shadow: none;"
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
            <a href="doChooseLogicGroup.action?logicGroupId=${logicGroup.id}">${logicGroup.logicGroupName}</a>
        </li>
    </#list>
</#if>
<#if Session["userLogicGroup"]??>
<#--用户归属站点 (多余的判断是过滤超级管理员)-->
    <li class="divider"></li>
    <li>
        <a href="doChooseLogicGroup.action?logicGroupId=${Session["userLogicGroup"].id}">
        ${Session["userLogicGroup"].logicGroupName}<span class="muted">(归属站点)</span></a>
    </li>
</#if>
    <li class="divider"></li>
    <li><a href="#" onclick="App('blackhole').header.chooseLogicGroup(); return false;">其它站点...</a></li>
    <li><a href="#" onclick="javascript:SWITCH_MAP.logicGroupMap('blackhole'); return false;">站点地图</a></li>
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
