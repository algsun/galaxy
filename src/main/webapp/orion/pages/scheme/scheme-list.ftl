<#--
方案列表

@author 王耕
@time  2015-9-11
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>方案管理 - 资产管理</title>

<#include "../_common/common-css.ftl">
<#include "/common/pages/common-tag.ftl">
    <style type="text/css">
    </style>
</head>
<body>
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#-- 消息提示 -->
<#include "/common/pages/message-tooltip.ftl">
<@messageTooltip></@messageTooltip>

    <div class="row">
        <div class="span12">
            <fieldset>
                <legend>
                    <a class="go-back" href="queryRelic.action" title="返回">
                        <i class="mw-icon-prev"></i>方案列表
                    </a>
                </legend>
            </fieldset>
        </div>
    </div>

    <div class="row">
        <div class="span12">
            <a href="schemes/toAddScheme" class="btn btn-samll btn-success m-b-30">新增</a>
            <table class="table table-bordered table-striped table-center">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>方案编号</th>
                    <th>方案名称</th>
                    <th>设计单位</th>
                    <th>批准时间</th>
                    <th>批准文号</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#if schemes??>
                    <#list schemes as scheme>
                    <tr>
                        <td>${scheme_index+1}</td>
                        <td>${scheme.schemeId!}</td>
                        <td>${scheme.name!}</td>
                        <td>${scheme.institution.name!}</td>
                        <td>${scheme.confirmTime?string("yyyy-MM-dd")}</td>
                        <td>${scheme.confirmNum!}</td>
                        <td>
                            <a href="schemes/toUpdateScheme?id=${scheme.id}" class="btn btn-samll">修改</a>
                            <a id="delete-scheme" schemeId="${scheme.id}" class="btn btn-samll btn-danger">删除</a>
                        </td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>


        </div>
    </div>
</div>


<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<@scriptTag "../assets/artDialog/5.0.1-7012746/artDialog.min.js"/>
<#--your js-->
<script type="text/javascript">
    $(function () {
        $(document).on("click", "#delete-scheme", function () {
            var $this = $(this);
            art.dialog({
                id: "delete",
                fixed: true,
                title: "删除确认",
                content: "确定删除方案？",
                okValue: "确定",
                ok: function () {
                    window.location.href = "schemes/deleteScheme?id=" + $this.attr("schemeId");
                },
                cancelValue: "取消",
                cancel: function () {
                }
            });
        });
    });
</script>
</body>
</html>