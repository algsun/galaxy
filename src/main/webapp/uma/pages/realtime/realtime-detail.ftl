<#--
  - 实时定位详情页面
  -@author li.jianfei
  -@time  2013-4-11 13:16
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>实时定位 - 人员行为</title>

<#include "../_common/common-css.ftl">

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "uma:home">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

    <#-- TODO 无用代码，删除 -->
<#--二级菜单-->
<#--<#include "sub-navs.ftl">-->
<#--<@subNavs "uma:ftp:list"></@subNavs>-->

<#-- 消息提示 -->
        <#-- TODO 整理页面提示信息工具类 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>


<#--your content-->
<#--数据列表-->
    <div class="row">
        <div class="span12">
            <fieldset>
                <legend>
                    <a class="go-back"
                       href="realtimeLocation.action?hourInterval=${hourInterval}&queryType=${queryType}" title="返回"><i
                            class="mw-icon-prev"></i>区域当前人员</a>
                </legend>
            </fieldset>
        </div>
    </div>
<#if (zoneList?size > 0) >
    <div class="row">
        <div class="span12">
            <input id="zoneId" type="hidden" value="${zoneId}"/>
            <input id="hourInterval" type="hidden" value="${hourInterval}">
            <input id="queryType" type="hidden" value="${queryType}">
            <table id="realtime" class="table">
                <thead>
                <tr>
                    <th>区域</th>
                    <th>人员</th>
                    <th>进入时间</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                    <#list zoneList as zone>
                    <tr>
                        <td>${zone.zoneName!}</td>
                        <td><a href="userRule.action?userId=${zone.userId!}">${zone.userName!}</a></td>
                        <td>
                            <#if zone.occurrenceTime??>
                            ${zone.timeStr}
                        </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>

        </div>
    </div>
<#elseif zoneList?size==0>
    <h4>暂无数据</h4>
</#if>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<#--your js-->
<script type="text/javascript">
    $(function () {
        // 定时查询最新数据
        setInterval(function () {
            $.getJSON("ajaxRealtimeDetail.action",
                    {zoneId: $("#zoneId").val(), hourInterval: $("#hourInterval").val(), queryType: $("#queryType").val()},
                    function (zoneList) {

                        var tbodyStr = "";
                        $.each(zoneList, function (index, zone) {
                            tbodyStr = tbodyStr + "<tr>";
                            tbodyStr = tbodyStr + "<td>" + zone.zoneName + "</td>";
                            tbodyStr = tbodyStr + "<td>" + "<a href='userRule.action?userId=" + zone.userId + "'>" + zone.userName + " </a></td>";

                            tbodyStr = tbodyStr + "<td>" + zone.timeStr + "</td>";
                            tbodyStr = tbodyStr + "</tr>"
                        });
                        $("#realtime").find("tbody").empty().append(tbodyStr);
                    });
        }, 10000);
    });
</script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
