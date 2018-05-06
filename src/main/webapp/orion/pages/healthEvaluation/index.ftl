<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>文物健康评测 - 资产管理</title>

<#include "../_common/common-css.ftl">

    <style type="text/css">

        /* table */
       #table1 td {
            width: 40px;
            height: 40px;
            text-align: center;
            vertical-align: middle;
        }

        table {
            width: 940px;
        }
    </style>
</head>
<body>
<#--页面以及标题-->
<#include "../_common/header.ftl">

<div class="container m-t-50" id="gcontent">
    <fieldset>
        <legend>
            <a class="go-back" href="queryRelic.action" title="返回">
                <i class="mw-icon-prev"></i>评测
            </a>
        </legend>
    </fieldset>
<#-- 消息提示 -->
    <div class="row">
        <div class="span12">
        <#include "/common/pages/message-tooltip.ftl">
        <@messageTooltip></@messageTooltip>
        </div>
    </div>
    <div class="row m-t-20">
    <#assign properties = relic.relicPropertyMap/>
        <div class="span12">
            <table id="table1" border="1px" style="border:2px #000 solid;">
                <caption><h3>陕西历史博物馆馆藏文物健康评测表</h3></caption>
                <tr>
                    <td colspan="2">名称</td>
                    <td colspan="10">${(relic.name)!}</td>
                    <td colspan="2">藏品号</td>
                    <td colspan="10">${relic.totalCode!}</td>
                </tr>
                <tr>
                    <td colspan="2">材质</td>
                    <td colspan="10">${(relic.texture.name)!}</td>
                    <td colspan="2">时代</td>
                    <td colspan="10">${relic.era.name!}</td>
                </tr>
                <tr>
                    <td colspan="2">级别</td>
                    <td colspan="10">${relic.level.name!}</td>
                    <td colspan="2">重量</td>
                    <td colspan="10"><#if properties.weight??>${properties.weight.propertyValue!}</#if></td>
                </tr>
                <tr>
                    <td colspan="2">来源</td>
                    <td colspan="22"><#if properties.source??>${properties.source.propertyValue!}</#if></td>
                </tr>
                <tr>
                    <td colspan="2">尺寸(cm)</td>
                    <td colspan="22"><#if properties.sizes??>${properties.sizes.propertyValue!}</#if></td>
                </tr>
                <tr>
                    <td colspan="2">存放位置</td>
                    <td colspan="10"><#if relic.zone??>${relic.zone.name!}</#if></td>
                    <td colspan="2">存放方式</td>
                    <td colspan="10"></td>
                </tr>
                <tr>
                    <td colspan="2">信息描述</td>
                    <td colspan="22"><#if properties.describe??>${properties.describe.propertyValue!}</#if></td>
                </tr>
            </table>
        </div>
    </div>
    <input type="hidden" id="relicId" value="${relic.id}">
    <div class="row m-t-10">
        <div class="span12">
        <#if relic.healthEvaluations?size<1>
            <h4>暂无数据</h4>
        <#else>
            <table class="table table-bordered table-center">
                <thead>
                    <tr>
                        <th class="col-md-1 ">评测人员</th>
                        <th class="col-md-2">样品编号</th>
                    <#--<th class="col-md-2">样品描述</th>-->
                        <th class="col-md-2">综合评测结论</th>
                        <th class="col-md-2">保护建议</th>
                        <th class="col-md-1">评测日期</th>
                        <th class="col-md-2">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <#list relic.healthEvaluations as h>
                    <tr>
                        <td>${h.evaluator!}</td>
                        <td>${h.sampleNumber!}</td>
                    <#--<td>${h.sampleDesc}</td>-->
                        <td>
                            <#if h.conclusion==1>
                                好
                            <#elseif h.conclusion==2>
                                很好
                            <#elseif h.conclusion==3>
                                非常好
                            </#if>
                        </td>
                        <td>
                            <#if h.suggestion==1>
                                建议1
                            <#elseif h.suggestion==2>
                                建议2
                            <#elseif h.suggestion==3>
                                建议3
                            </#if>
                        </td>
                        <td>${h.evaluationDate?string('yyyy-MM-dd hh:ss')}</td>
                        <td>
                            <a class="btn btn-info btn-mini" href="healthEvaluation/detail?healthEvaluation.id=${h.id}">详情</a>
                            <a class="btn btn-danger btn-mini" data-id="${h.id}" data-button="del">删除</a>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
        </div>
    </div>
    <div class="m-t-1">
        <a class="btn btn-primary " href="healthEvaluation/create?relic.id=${relic.id}">添加</a>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script>
    $(function () {
        $("a[data-button='del']").click(function () {

            var $this = $(this);
            var healthEvaluationId = $this.data("id");
            var relicId = $("#relicId").val();
            var info = "确定要删除吗?";
            art.dialog({
                id: "delete",
                fixed: true,
                title: "删除确认",
                content: info,
                okValue: "确定",
                ok: function () {
                    window.location.href = "healthEvaluation/delete?healthEvaluation.id=" + healthEvaluationId
                            + "&relic.id=" + relicId;
                },
                cancelValue: "取消",
                cancel: function () {
                }
            })
        });
    });
</script>
</body>
</html>




