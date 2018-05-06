
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>文物健康评测详情 - 资产管理</title>

<#include "../_common/common-css.ftl">
    <style type="text/css">

        /* table */
        td {
            width: 40px;
            height: 40px;
            text-align: center;
            vertical-align: middle;
        }

        table {
            width: 960px;
        }
    </style>
</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "orion:culturalRelic">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

    <div class="row m-t-20">
    <#assign properties = healthEvaluation.relic.relicPropertyMap/>
    <#assign relic = healthEvaluation.relic/>

        <div class="span12">
            <legend>
                文物健康评测详情
            </legend>
            <table border="1px" style="border:2px #000 solid;">
               <caption><h3>陕西历史博物馆馆藏文物健康评测表</h3></caption>
                <tr>
                    <td colspan="2">文物名称</td>
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
                    <td colspan="22"><#if properties.sizes??>${(properties.sizes.propertyValue)!}</#if></td>
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
                <tr>
                    <td colspan="2">现状描述</td>
                    <td colspan="22" class="t-a-l">
                        <ul style="list-style-type: none;">
                       <#if relic.statusQuos??>
                        <#list relic.statusQuos as statusQuo>
                            <li>
                                <blockquote>
                                    <p style="font-size:16px;">${statusQuo.quoInfo!}</p>
                                    <small>
                                        <cite title="现状日期">${statusQuo.quoDate?string("yyyy-MM-dd")}</cite>
                                    </small>
                                </blockquote>
                            </li>
                        </#list>
                       </#if>
                        </ul>
                    </td>
                </tr>

                <tr>
                    <td colspan="2">评测人员</td>
                    <td colspan="22">${healthEvaluation.evaluator!}</td>
                </tr>
                <tr>
                    <td colspan="2">样品编号</td>
                    <td colspan="22">${healthEvaluation.sampleNumber!}</td>
                </tr>
                <tr>
                    <td colspan="2">综合评测结论</td>
                    <td colspan="22">
                        <#if healthEvaluation.conclusion==1>
                            好
                            <#elseif healthEvaluation.conclusion==2>
                            很好
                            <#elseif healthEvaluation.conclusion==3>
                            非常好
                        </#if>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">保护建议</td>
                    <td colspan="22">
                        <#if healthEvaluation.suggestion==1>
                            建议1
                        <#elseif healthEvaluation.suggestion==2>
                            建议2
                        <#elseif healthEvaluation.suggestion==3>
                            建议3
                        </#if>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">评测日期</td>
                    <td colspan="22">${healthEvaluation.evaluationDate?string('yyyy-MM-dd hh:ss')}</td>
                </tr>
            </table>
            <div class="m-t-5">
                <a class="btn btn-default " href="healthEvaluation/index?relic.id=${relic.id}">返回</a>
            </div>
        </div>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
</body>
</html>


