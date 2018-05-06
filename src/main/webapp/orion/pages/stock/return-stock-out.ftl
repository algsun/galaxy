<#--
文物了入库记录

@author gaohui
@time  13-5-28
@check xiedeng 2013-6-6 16:24 svn:3999
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
            <a class="go-back" href="queryStockInAndOut.action"><i class="mw-icon-prev"></i>回库核对</a>
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
                    <td>${eventRelic.relic.count}</td>
                    <td><@_stateName eventRelic.relic.state/></td>
                    <#if outEvent.state == 1>
                        <td>${eventRelic.outDate?string("yyyy-MM-dd")}</td>
                    </#if>
                </tr>
                </#list>
                </tbody>
            </table>
            <div class="span12 m-l-0 m-b-20 m-t-10">
                <span style="font-weight: bold">备注:</span><span class="extendMsg" style="width: 100%">${outEvent.remark!}</span>
            </div>
            <a id="returnButton" class="btn btn-primary" href="doReturnStockOut.action?outEventId=${outEventId}">回库</a>
        </div>
    </div>
</div>

<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">

<script type="text/javascript">
    $(function () {
        $('#returnButton').click(function () {
            if (!confirm('请确保已核对文物清单，此操作不可回退。')) {
                return false;
            }
        });
    });
</script>

<#include  "../_common/last-resources.ftl">
</body>
</html>
