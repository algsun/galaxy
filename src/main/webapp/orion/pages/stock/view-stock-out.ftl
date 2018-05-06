<#--
文物了入库记录

@author gaohui
@time  13-5-28
@check xiedeng 2013-6-6 16:31 svn:3999
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>出库申请详情 - 资产管理</title>
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

   <fieldset>
       <legend>
           <a class="go-back" href="queryStockInAndOut.action"><i class="mw-icon-prev"></i>出库申请单详情</a>
       </legend>
   </fieldset>

<#include "stock-helper.ftl">

    <div class="row">
        <div class="span12">
        <#include "view-helper.ftl">
            <table class="table table-bordered table-striped table-center">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>总登记号</th>
                    <th>名称</th>
                    <th>级别</th>
                    <th>质地</th>
                    <th>件数</th>
                    <th>库存状态</th>
                    <#if outEvent.state == 1>
                        <th>出库时间</th>
                    </#if>
                    <#if outEvent.state == 2>
                        <th>出库时间</th>
                        <th>回库时间</th>
                    </#if>
                </tr>
                </thead>
                <tbody>
                <#list outEvent.eventRelics as eventRelic>
                <tr>
                    <td>${eventRelic_index + 1}</td>
                    <td>${eventRelic.relic.totalCode} <#if eventRelic.relic.hasTag><i class="icon-tag"></i></#if></td>
                    <td>${eventRelic.relic.name}</td>
                    <td>${eventRelic.relic.level.name}</td>
                    <td>${eventRelic.relic.texture.name}</td>
                    <td>${eventRelic.relic.count!}</td>
                    <td><@_stateName eventRelic.relic.state/></td>
                    <#if outEvent.state == 1>
                        <td>${eventRelic.outDate?string("yyyy-MM-dd")}</td>
                    </#if>
                    <#if outEvent.state == 2>
                        <td>${eventRelic.outDate?string("yyyy-MM-dd")}</td>
                        <td>${eventRelic.inDate?string("yyyy-MM-dd")}</td>
                    </#if>
                </tr>
                </#list>
                </tbody>
            </table>
            <div class="span12 m-l-0 m-b-20 m-t-10">
                <span style="font-weight: bold">备注:</span><span class="extendMsg" style="width: 100%">${outEvent.remark!}</span>
            </div>
            <div class="m-t-30">
                <h4>文档列表</h4>
                <hr class="m-v-10"/>
                <table class="table table-bordered table-striped table-center">
                    <thead>
                    <th style="width: 50px;">序号</th>
                    <th style="width: 200px;">上传人员</th>
                    <th style="width: 330px;">文档名称</th>
                    <th style="width: 100px;">文档上传日期</th>
                    </thead>
                    <tbody>
                    <#if outEventAttachmentList?size != 0>
                        <#list outEventAttachmentList as outEventAttachment>
                        <tr>
                            <td>${outEventAttachment_index+1}</td>
                            <td>${outEventAttachment.user.userName}</td>
                            <td style="text-align: left"><a
                                    href="downloadDocFile.action?downloadFileId=${outEventAttachment.id}&outEventId=${outEventAttachment.outEvent.id}">${outEventAttachment.path}</a>
                            </td>
                            <td>${outEventAttachment.date?string("yyyy-MM-dd")}</td>
                        </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">
<#include  "../_common/last-resources.ftl">
</body>
</html>
