<#--
文物了入库记录

@author gaohui
@time  13-5-28
@check xiedeng 2013-6-6 14:26 svn:4091
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>出入库查询 - 资产管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<#assign currentTopPrivilege = "orion:stockManage">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "orion:stockManage:query"></@subNavs>
<#-- TODO 目前有多个提示信息的工具，重构为一个，并统一风格 -->
<#include "/common/pages/message-tooltip.ftl">
<@messageTooltip/>

<#include "stock-helper.ftl">

    <div class="row">
        <div class="span12">
            <table class="table table-bordered table-striped table-center">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>申请单号</th>
                    <th>申请人</th>
                    <th>提用目的</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>当前状态</th>
                    <th>操作</th>
                    <th>挂接文档</th>
                </tr>
                </thead>
                <tbody>
                <#list outEvents as outEvent>
                <tr>
                    <td>${outEvent_index + 1}</td>
                    <td>
                        <small><a href="viewStockOut.action?outEventId=${outEvent.id}"
                                  title="详情"><@strongOutEventId outEvent.id/></a></small>
                    </td>
                    <td>${outEvent.applicant}</td>
                    <td>${outEvent.useFor}</td>
                    <td>${outEvent.beginDate?string("yyyy-MM-dd")}</td>
                    <td>${outEvent.endDate?string("yyyy-MM-dd")}</td>
                    <td>
                        <#if processes.get(outEvent.id)??>
                            <#assign curAct = processes.get(outEvent.id).activity>

                            <a title="跟踪流程" style="cursor: pointer;" data-type="trackButton"
                               data-href="trackFlowChart.action?processInstanceId=${processes.get(outEvent.id).prcIns.processInstanceId}"
                               data-x="${curAct.x}" data-y="${curAct.y}" data-width="${curAct.width}"
                               data-height="${curAct.height}"
                               data-test="queryHandleListAgencyList.action?processInstanceId=${processes.get(outEvent.id).prcIns.processInstanceId}">
                            ${curAct.getProperty("name")}
                            </a>
                        <#else>
                            <#if outEvent.state == 1>
                                已出库
                            <#elseif outEvent.state == 2>
                                已回库
                            </#if>
                        </#if>
                    </td>
                    <td>
                        <@_operation outEvent, processes/>
                        <#if processes.get(outEvent.id)?? &&outEvent.state ==0 && Session.currentUser.id==1>
                            <a href="deleteStockOutTask.action?processInstanceId=${processes.get(outEvent.id).prcIns.processInstanceId}&outEventId=${outEvent.id}">删除任务</a>
                        </#if>
                    </td>
                    <td>
                        <#if outEvent.state != 2>
                            <a class="btn btn-mini" href="toUploadDoc.action?outEventId=${outEvent.id}"><i
                                    class="icon-file"></i>文档上传</a>
                        </#if>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>

            <h4><#if outEvents?size == 0>暂无数据</#if></h4>

        <#include "/common/pages/pagging.ftl">
        <@pagination "queryStockInAndOut.action?1=1", page, pageCount />
        </div>
    </div>
</div>

<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">

<@scriptTag "../common/js/util.js"/>

<script type="text/javascript">
    $(function () {
        $('a[data-type="trackButton"]').click(function () {
            var $this = $(this);
            var imgSrc = $this.attr('data-href');
            var queryHandleListAgencyListUrl = $this.attr('data-test');
            $.get(queryHandleListAgencyListUrl, function (result) {
                var dialog = art.dialog({title: '流程图'});
                // 获取图片原始大小
                window.imageNaturalSize(imgSrc, function (imgWidth, imgHeight) {
                    var x = $this.attr('data-x');
                    var y = $this.attr('data-y');
                    y = parseInt(y);
                    var width = $this.attr('data-width');
                    var height = $this.attr('data-height');

                    var content = [
                        '<div style="position: relative;">',
                        '<img src="', imgSrc, '" style="width:', imgWidth, 'px; height:', imgHeight, 'px;"/>',
                        '<div style="border: solid 2px red; position: absolute; ', 'top: ', 48, 'px; left: ', 575, 'px; width:', width, 'px; height:', height, 'px;', '"></div>',
                        '<div style="border: solid 2px red; position: absolute; ', 'top: ', 370, 'px; left: ', 65, 'px; width:', width * 9, 'px; height:', 110, 'px;', '">' +
                        '<span style="top:15px;;left: 10px;position: absolute;">待办人：' + result + '</span><br/><br/>' +
                        '</div>',
                        '</div>'
                    ].join('');
                    dialog.close();
                    art.dialog({title: '流程图', content: content});
                });
            });
        });
    });
</script>

<#include  "../_common/last-resources.ftl">
</body>
</html>

<#-- 操作 -->
<#macro _operation outEvent, processes>
<#-- 流程未结束 -->
    <#if processes.get(outEvent.id)??>
        <#if processes.get(outEvent.id).task??>
            <#assign task = processes.get(outEvent.id).task>
        <#-- 出库单核对 -->
            <#if task.taskDefinitionKey == "stockOutListCheckTask">
            <a class="btn btn-mini"
               href="stockOutCheck.action?outEventId=${outEvent.id}&taskId=${task.id}">核对出库单</a>
            <#else>
                <#if task.assignee??>
                    <#switch task.taskDefinitionKey>
                        <#case "adjustApplicationTask"> <#-- 调整申请 -->
                        <a class="btn btn-mini"
                           href="adjustStockOut.action?outEventId=${outEvent.id}&taskId=${task.id}">重新申请</a>
                        <a class="btn btn-mini"
                           href="cancelStockOut.action?outEventId=${outEvent.id}&taskId=${task.id}">取消申请</a>
                            <#break>
                        <#case "internalVerifyTask">
                        <a class="btn btn-mini"
                           href="${_completeTaskUrl(task.id, "/orion/queryStockInAndOut.action", [{"name":"internalVerifyApproved", "value":"true", "type":"boolean"}])}">同意</a>
                        <a class="btn btn-mini"
                           href="${_completeTaskUrl(task.id, "rejectStockOut.action?outEventId=" + outEvent.id, [{"name":"internalVerifyApproved", "value":"false", "type":"boolean"}])}">不同意</a>
                            <#break>
                        <#case "externalVerifyTask">
                        <a class="btn btn-mini"
                           href="${_completeTaskUrl(task.id, "/orion/queryStockInAndOut.action", [{"name":"externalVerifyApproved", "value":"true", "type":"boolean"}])}">同意</a>
                        <a class="btn btn-mini"
                           href="${_completeTaskUrl(task.id, "rejectStockOut.action?outEventId=" + outEvent.id, [{"name":"externalVerifyApproved", "value":"false", "type":"boolean"}])}">不同意</a>
                            <#break>
                        <#case "stockOutConfirmTask">
                        <a class="btn btn-mini"
                           href="confirmStockOut.action?taskId=${task.id}&outEventId=${outEvent.id}">库房确认</a>
                            <#break>
                    </#switch>
                <#else>
                <a class="btn btn-mini" href="claimTask.action?taskId=${task.id}">签收</a>
                </#if>
            </#if>
        </#if>
    <#else>
        <#if outEvent.state == 1>
            <#if security.isPermitted("orion:stockManage:returnConfirm")>
            <a class="btn btn-mini" href="returnStockOut.action?outEventId=${outEvent.id}">回库</a>
            </#if>
        </#if>
    </#if>
</#macro>
