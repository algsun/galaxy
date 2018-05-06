<#--
文物了入库记录

@author gaohui
@time  13-5-28
@check xiedeng 2013-6-6 15:21 svn:3974
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>出库单核对 - 资产管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<#assign currentTopPrivilege = "orion:stockManage">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "orion:stockManage:query"></@subNavs>
 <#-- TODO 提示信息统一风格 -->
<#include "/common/pages/message-tooltip.ftl">
<@messageTooltip/>

    <fieldset>
        <legend>
            <a class="go-back" href="queryStockInAndOut.action"><i class="mw-icon-prev"></i>出库单核对</a>
        </legend>
    </fieldset>

<#include "stock-helper.ftl">

    <div class="row">
        <div class="span12">
        <#include "view-helper.ftl">
            <fieldset>
                <legend>待其他管理员审核</legend>
            <#list zones as zone>
                <#assign appState = 1>
                <#if zone.state == 0 && zone.users??>
                    <#list zone.users as u>
                        <#if u.id == userId>
                            <#assign appState = 0>
                        </#if>
                    </#list>
                <#else>
                    <#assign appState = 0>
                </#if>

                <#if appState == 1>
                    <@zoneHead zone />

                    <table class="table table-bordered table-center m-0">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>总登记号</th>
                            <th>名称</th>
                            <th>区域</th>
                            <th>级别</th>
                            <th>质地</th>
                            <th>件数</th>
                            <th>库存状态</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list zone.relics as erc>
                                <#assign state = erc.state>
                            <tr <@_stateClass state/>>
                                <td>${erc_index + 1}</td>
                                <td>${erc.totalCode}<#if erc.hasTag><i class="icon-tag"></i></#if></td>
                                <td>${erc.name}</td>
                                <td>${erc.zone.name}</td>
                                <td>${erc.level.name}</td>
                                <td>${erc.texture.name}</td>
                                <td>${erc.count}</td>
                                <td>
                                    <#if erc.state == 0>
                                        在库
                                    <#elseif erc.state == 1>
                                        待出库
                                    <#else>
                                        出库
                                    </#if>
                                </td>
                                <td><#if erc.state == 0>√<#else>X</#if></td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                    <div class="m-b-50">
                        <#if zone.state == 0>
                            状态： 待审核
                        <#elseif zone.state == 1>
                            状态：已通过 审批人：${zone.user.userName}
                        <#elseif zone.state == 2>
                            状态：不通过 审批人：${zone.user.userName}
                        </#if>
                    </div>
                </#if>
            </#list>
            </fieldset>

            <fieldset>
                <legend>待我审核</legend>
            <#assign showSt = 1>
            <#list zones as zone>
                <#assign appState = 1>
                <#if zone.state == 0 && zone.users??>
                    <#list zone.users as u>
                        <#if u.id == userId>
                            <#assign showSt = 0>
                            <#assign appState = 0>
                        </#if>
                    </#list>
                <#else>
                    <#assign showSt = 0>
                    <#assign appState = 0>
                </#if>

                <#if appState == 0>
                    <@zoneHead zone />
                    <table class="table table-bordered table-center m-0">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>总登记号</th>
                            <th>名称</th>
                            <th>区域</th>
                            <th>级别</th>
                            <th>质地</th>
                            <th>件数</th>
                            <th>库存状态</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list zone.relics as erc>
                                <#assign state = erc.state>
                            <tr <@_stateClass state/>>
                                <td>${erc_index + 1}</td>
                                <td>${erc.totalCode}<#if erc.hasTag><i class="icon-tag"></i></#if></td>
                                <td>${erc.name}</td>
                                <td><#if erc.zone ??>${erc.zone.name}</#if></td>
                                <td>${erc.level.name}</td>
                                <td>${erc.texture.name}</td>
                                <td>${erc.count}</td>
                                <td>
                                    <#if erc.state == 0>
                                        在库
                                    <#elseif erc.state == 1>
                                        待出库
                                    <#else>
                                        出库
                                    </#if>
                                </td>
                                <td><#if erc.state == 0>√<#else>X</#if></td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                    <div class="span12 m-l-0 m-b-20 m-t-10">
                        <span style="font-weight: bold">备注:</span><span class="extendMsg" style="width: 100%">${outEvent.remark!}</span>
                    </div>
                    <div class="m-b-50 m-t-5">
                        <#if zone.state == 0>
                            <#-- 区域所有文物都在库的时候才可以审核通过 @gaohui 2014-03-26 -->
                            <a class="btn btn-primary" <@disabled !zoneCouldChecks.get(zone.id)/>
                                    <#if zoneCouldChecks.get(zone.id)>
                                   href="changeStatus.action?taskId=${taskId}&outEventId=${outEventId}&state=1<#if zone.zone ??>&zoneId=${zone.zone.id}</#if>"
                                    </#if>
                                    >通过</a>
                            <a class="btn btn-primary"
                               href="changeStatus.action?taskId=${taskId}&outEventId=${outEventId}&state=2<#if zone.zone ??>&zoneId=${zone.zone.id}</#if>">不通过</a>
                        <#elseif zone.state == 1>
                            <legend>已审核 ：通过 ,审核人：${zone.user.userName}</legend>
                        </#if>
                    </div>
                </#if>
            </#list>
            <#if showSt == 1>
                <h4>无</h4>
            </#if>
            </fieldset>
        </div>
    </div>
</div>

<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">
<#include  "../_common/last-resources.ftl">
</body>
</html>


<#macro _stateClass state>
    <#switch state>
        <#case 0>class="success"<#break>
        <#default>class="error"<#break>
    </#switch>
</#macro>

<#macro zoneHead eventZone>
<div class="m-b-10">
    <#if eventZone.zone?? >
        <h4 class="f-l m-v-0">${eventZone.zone.name}</h4>
        (
        <#if eventZone.users??>
            <#list eventZone.users as u>
            ${u.userName}
                <#if u_has_next >
                    ,
                </#if>
            </#list>
        </#if >
        )
    <#else>
        <h4>暂无区域</h4>
    </#if>
</div>
</#macro>