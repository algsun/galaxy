<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>任务管理 - 资产管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<#--<#assign currentTopPrivilege = "orion:stockManage">-->

<div id="gcontent" class="container m-t-50">
<#include "../_common/header.ftl">

    <div class="row m-t-10">
        <p style="text-align: center;font-size: 20px;">
            任务列表
        <p>
    </div>

    <div class="row">
        <div class="span12">
        <#include "/common/pages/message-tooltip.ftl">
        <@messageTooltip></@messageTooltip>
        </div>
    </div>

    <table class="table table-hover">
        <tr>
            <th>档案号</th>
            <th>文物名称</th>
            <th>文物编号</th>
            <th>材质</th>
            <th>承担人</th>
            <th>提取时间</th>
            <th>归还时间</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
    <#list repairRecords as repairRecord>
        <tr>
            <td>${repairRecord.identifier?c}</td>
            <td>${repairRecord.relic.name}</td>
            <td>${repairRecord.relic.totalCode!}</td>
            <td>${repairRecord.relic.texture.name}</td>
            <td>
                <#if repairRecord.mainUser??>
                ${repairRecord.mainUser.userName!}
                </#if>
                <#if repairRecord.secondaryUserName??>
                <#list repairRecord.secondaryUserName as userName>
                ${userName}
                </#list>
            </#if>
            </td>
            <td>${repairRecord.extractDate?string("yyyy-MM-dd")}</td>
            <td style="width: 18%">
                <#if security.isPermitted("orion:repairRecord:handle")>

                    <#if repairRecord.mainUser?? && userId == repairRecord.mainUser.id>
                        <form class="form-inline" style="margin: 0 0 0 0" action="repairRecords/updateReturnDate">
                            <input type="hidden" name="repairRecord.id" value="${repairRecord.id}"/>
                            <input type="text"
                                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${repairRecord.extractDate?string("yyyy-MM-dd")}',maxDate:'2100-12-31'})"
                                   name="repairRecord.returnDate" id="returnDate"
                                   value="<#if repairRecord.returnDate??>${repairRecord.returnDate?string("yyyy-MM-dd")}</#if>"
                                   style="width: 50%"/>
                            <input class="btn btn-mini" type="submit" style="width:25%" value="确定">
                        </form>
                    <#else >
                        <#if repairRecord.returnDate??>
                        ${repairRecord.returnDate?string("yyyy-MM-dd")}
                        </#if>
                    </#if>
                <#else>
                    <#if repairRecord.returnDate??>
                    ${repairRecord.returnDate?string("yyyy-MM-dd")}
                    </#if>
                </#if>
            </td>
            <td>
                <#switch repairRecord.state>
                    <#case 1>
                        待分配
                        <#break>
                    <#case 2>
                        待接受
                        <#break>
                    <#case 3>
                        修复中
                        <#break>
                    <#case 4>
                        待审核
                        <#break>
                    <#case 5>
                        待点评
                        <#break>
                    <#case 6>
                        完成
                        <#break>
                    <#default>
                        未知
                </#switch>
            </td>
            <td>
                <#switch repairRecord.state>
                    <#case 1>
                        <#if security.isPermitted("orion:repairRecord:accept")>
                            <a class="btn btn-mini btn-info"
                               href="handleTask/to_assign_task?repairRecord.id=${repairRecord.id}&relic.id=${repairRecord.relic.id}">分配</a>
                        </#if>

                        <#if security.isPermitted("orion:repairRecord:update")>
                            <a href="repairRecords/edit?repairRecord.id=${repairRecord.id?c}"
                               class="btn btn-mini btn-default">编辑</a>
                        </#if>

                        <#if security.isPermitted("orion:repairRecord:delete")>
                            <a data-id="${repairRecord.id?c}" data-button="deleteLocation"
                               class="btn btn-mini btn-danger">删除</a>
                        </#if>
                        <#break>
                    <#case 2>
                        <#if security.isPermitted("orion:repairRecord:handle")>
                            <#if userId == repairRecord.mainUser.id>
                                <a class="btn btn-mini btn-info"
                                   href="handleTask/accept_task?repairRecord.id=${repairRecord.id}&relic.id=${repairRecord.relic.id}">接受</a>
                            </#if>
                        </#if>
                        <#if security.isPermitted("orion:repairRecord:update")>
                            <a href="handleTask/assign_task_after_update?repairRecord.id=${repairRecord.id?c}"
                               class="btn btn-mini btn-default">编辑</a>
                        </#if>
                        <#break>
                    <#case 3>
                        <#if security.isPermitted("orion:repairRecord:handle")>
                            <#if userId == repairRecord.mainUser.id>
                                <a href="archives/index?repairRecordId=${repairRecord.id}&relicId=${repairRecord.relic.id}"
                                   class="btn btn-mini">档案</a>
                            </#if>
                        </#if>
                        <#break>
                    <#case 4>
                        <#if security.isPermitted("orion:repairRecord:check")>
                            <a class="btn btn-mini"
                               href="archives/allTableBrowse?repairRecordId=${repairRecord.id}">审核</a>
                        </#if>
                        <#break>
                    <#case 5>
                        <#if security.isPermitted("orion:repairRecord:comment")>
                            <a class="btn btn-mini"
                                   href="comment/create?repairRecordId=${repairRecord.id}">点评</a>
                            <a class="btn btn-mini"
                               href="comment/detail?repairRecordId=${repairRecord.id}">详情</a>
                            <#else >
                                <a class="btn btn-mini"
                                   href="comment/detail?repairRecordId=${repairRecord.id}">详情</a>
                        </#if>
                        <#break>
                    <#case 6>
                        <a class="btn btn-mini"
                           href="comment/detail?repairRecordId=${repairRecord.id}">查看</a>
                        <#break>
                    <#default>
                </#switch>
            </td>
        </tr>
    </#list>
    </table>
<#include "/common/pages/pagging.ftl">
<#assign url = "repairRecords?">
<@pagination url, index, pageCount,"index"/>
</div>

<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">
<#include  "../_common/last-resources.ftl">
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/lodop/6.198/LodopFuncs.js"></script>
<object id="LODOP_DB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0">
    <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
    <param name="SRC" value="http://localhost:8080/galaxy-resources/orion/1/2015.pdf"/>
</object>

<script type="text/javascript">
    $(function () {
        $("a[data-button='deleteLocation']").click(function () {
            var $this = $(this);
            var id = $this.data("id");
            var info = "确认要删除吗？";
            art.dialog({
                id: "delete",
                fixed: true,
                title: "删除确认",
                content: info,
                okValue: "确定",
                ok: function () {
                    window.location.href = "repairRecords/delete?repairRecord.id=" + id;
                },
                cancelValue: "取消",
                cancel: function () {
                }
            })

        });


        //以下代码报错，不注释影响删除功能，所以...
//        var LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
//        LODOP.PRINT_INIT("");
//        LODOP.PRINT();

    })
</script>
</body>
</html>

