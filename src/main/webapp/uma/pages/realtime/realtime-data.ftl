<#--
  - 实时定位页面
  -@author li.jianfei
  -@time  2013-4-15
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>实时人员分布 - 人员行为</title>

<#include "../_common/common-css.ftl">
</head>
<body>

<#--页面以及标题-->
<#assign currentTopPrivilege = "uma:realtimeLocation">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

    <#-- TODO 注释掉的代码，是否要删除掉 -->
<#--二级菜单-->
<#--<#include "../_common/sub-navs.ftl">-->
<#--<@subNavs "uma:realtimeLocation:"></@subNavs>-->

<#--自定义二级菜单-->
    <div class="row m-t-10">
        <div class="span12">
            <ul class="nav nav-pills">
            <#--<#if security.isPermitted("uma:allotcard:haveCardUser")>-->
                <li <#if queryType==0>class="active"</#if>>
                    <a href="realtimeLocation.action?queryType=0">按规则统计</a>
                </li>
            <#--</#if>-->
            <#--<#if security.isPermitted("uma:allotcard:noCardUser")>-->
                <li <#if queryType==1>class="active"</#if>>
                    <a href="realtimeLocation.action?queryType=1">按位置统计</a>
                </li>
            <#--</#if>-->
            </ul>
        </div>
    </div>

<#-- 消息提示 -->
        <#-- TODO 整理页面提示信息工具类 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--TODO 敢不敢用css实现缩进-->
<#macro repeat count>
    <#list 1..count as x>
        <#if (x>1)>
            &emsp;&emsp;
        </#if>
    </#list>
</#macro>

<#--your content-->
    <div class="row">
        <div class="span12">
        </div>
    </div>
<#--数据列表-->
    <div class="row">
        <div class="span12">
            <fieldset>
                <form class="form-inline well well-small <#if queryType==0>hide</#if>" action="realtimeLocation.action"
                      method="post">
                    <input id="queryType" name="queryType" type="hidden" value="${queryType}"/>
                    <label for="hourInterval">最近</label>
                    <select id="hourInterval" name="hourInterval" class="span1">
                        <option value="1" <#if hourInterval==1> selected="selected" </#if>>1</option>
                        <option value="3" <#if hourInterval==3> selected="selected" </#if>>3</option>
                        <option value="6" <#if hourInterval==6> selected="selected" </#if>>6</option>
                        <option value="24" <#if hourInterval==24> selected="selected" </#if>>24</option>
                    </select>
                    <label>小时人员分布</label>
                </form>
            </fieldset>
        <#if (zoneList?size >0)>
            <table class="table" id="realtime">
                <thead>
                <tr>
                    <th>区域</th>
                    <th>人数</th>
                    <th>人员</th>
                    <th>时间</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                    <#list zoneList as zone>
                    <tr zoneId="${zone.zoneId}" parentId="${zone.parentId!}"
                        <#if zone.hasChildren>showSub="<#if zone.showSub>1<#else>0</#if>"</#if>
                        level="${zone.level}" class="<#if zone.hide>hide</#if>">
                        <td>
                            <@repeat count=zone.level />
                            <#if zone.hasChildren>
                            <span data-type="zone" style="cursor: pointer;"><i
                                    class="<#if zone.showSub>icon-minus-sign<#else>icon-plus-sign</#if>"></i>
                            </#if>
                        ${zone.zoneName}
                            <#if zone.hasChildren>
                            </span>
                            </#if>
                        </td>
                        <td>${zone.numberOfPeople}</td>
                        <td><a href="userRule.action?userId=${zone.userId!}"/>${zone.userName!}</td>
                        <td>
                            <#if zone.occurrenceTime??>
                            ${zone.timeStr}
                            </#if>
                        </td>
                        <td>
                            <a href="realtimeDetail.action?zoneId=${zone.zoneId}&hourInterval=${hourInterval}&queryType=${queryType}"
                               class="btn btn-mini btn-success"><i
                                    class="icon-list icon-white"></i>详情</a>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
        </div>
    </div>

    <div class="hide">
        <input id="queryType" type="text" value="${queryType}"/>
        <ul id="closedList">
        <#list closedZoneIds as closedZoneId>
            <li>${closedZoneId}</li>
        </#list>
        </ul>
    </div>
<#if zoneList?size == 0>
    <h4>暂无数据</h4>
</#if>
</div>
<#include "../_common/footer.ftl">

<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/moment/2.0.0/moment.min.js"></script>
<script type="text/javascript" src="../assets/moment/2.0.0/lang/zh-cn-custom.js"></script>
<script type="text/javascript">

    $(function () {

        $.ajaxSetup({traditional: true});

        // 关闭所有子区域
    <#--TODO 重构代码-->
        var closeChildren = function (zoneId) {
            $.each($("tr[parentId='" + zoneId + "']"), function (index, tr) {
                var $this = $(this);
                if (typeof($this.attr("showSub")) != "undefined") {
                    $this.attr("showSub", 0);
                    $this.find("span i").first().removeClass("icon-minus-sign");
                    $this.find("span i").first().addClass("icon-plus-sign");
                }
                $this.hide();
                closeChildren($this.attr("zoneId"));
            });
        };

        $(document).on("click", "span[data-type='zone']", function () {
            var $this = $(this);
            var $tr = $this.parent().parent();
            var zoneId = $tr.attr("zoneId");
            if ($tr.attr("showSub") == 0) { // 打开该与区的所有子区域
                if (typeof($tr.attr("showSub")) != "undefined") {
                    $tr.attr("showSub", "1");
                    $this.find("i").removeClass("icon-plus-sign");
                    $this.find("i").addClass("icon-minus-sign");
                }
                $("tr[parentId='" + zoneId + "']").show();
            } else { // 关闭该区域的所有子区域
                if (typeof($tr.attr("showSub")) != "undefined") {
                    $tr.attr("showSub", "0");
                    $this.find("i").removeClass("icon-minus-sign");
                    $this.find("i").addClass("icon-plus-sign");
                }
                closeChildren(zoneId);
            }
        });


        // 定时查询最新数据
        setInterval(function () {

            // TODO 直接使用  closed = []; @gaohui 2013-05-11
            // 获取关闭的
            var closed = new Array();
            $.each($("tr[showSub='0']"), function (index, tr) {
                var $tr = $(this);
                closed.push($tr.attr("zoneId"));
            });


            $.getJSON("ajaxRealtimeLocation.action", {hourInterval: $("#hourInterval").val(), queryType: $("#queryType").val(), closed: closed}, function (zoneList) {
                var tbodyStr = "";
                $.each(zoneList, function (index, zone) {
                    tbodyStr = tbodyStr + "<tr zoneId='" + zone.zoneId + "' parentId='" + zone.parentId + "' " +
                            (zone.hasChildren ? "showSub=" + (zone.showSub ? 1 : 0) : "") + " level='" + zone.level + "' class='" + (zone.hide ? "hide" : "")
                            + "'>";
                    // 区域名称
                    tbodyStr = tbodyStr + "<td style='padding-left: " + 40 * (zone.level - 1) + "px;'>";
                    if (zone.hasChildren) {
                        tbodyStr = tbodyStr + "<span data-type='zone' style='cursor: pointer;'><i class='" +
                                (zone.showSub ? "icon-minus-sign" : "icon-plus-sign") + "'></i>"
                    }
                    tbodyStr = tbodyStr + zone.zoneName;
                    if (zone.hasChildren) {
                        tbodyStr = tbodyStr + "</span>"
                    }
                    tbodyStr = tbodyStr + "</td>";

                    // 当前人数
                    tbodyStr = tbodyStr + "<td>" + zone.numberOfPeople + "</td>";

                    // 最新人员
                    tbodyStr = tbodyStr + "<td>" + "<a href='userRule.action?userId=" + zone.userId + "'>" + zone.userName + " </a></td>";

                    // 发生时间
                    tbodyStr = tbodyStr + "<td>" + zone.timeStr + "</td>";

                    // 操作
                    tbodyStr = tbodyStr + "<td><a class='btn btn-mini btn-success' href='realtimeDetail.action?zoneId=" +
                            zone.zoneId + "&hourInterval=" + $("#hourInterval").find("option:selected").val() + "'><i class='icon-list icon-white'></i>详情</a></td>";

                    // 结束
                    tbodyStr = tbodyStr + "</tr>";
                });
                $("#realtime").find("tbody").empty().append(tbodyStr);
            });
        }, 10000);


        $("#hourInterval").change(function () {
            $("form").submit();
        });
    });
</script>
<script type="text/javascript">
    $(function () {
        $(document).on('click', '.subsystemHead', function () {
            art.dialog({
                title: '正在努力加载!'
            });
        });
    });
</script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
