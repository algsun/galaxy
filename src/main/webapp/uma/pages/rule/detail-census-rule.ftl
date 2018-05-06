<#--
行为统计-往返行为规则详情

@author wangrensong
@date  2013-04-27
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>行为规则详情 - 人员管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "uma:statistics:actionrole">
<#include "../_common/header.ftl">
<#include "../_common/difftime.ftl">
<#include "../_common/format-date.ftl">

<div id="gcontent" class="container m-t-50">

<#-- 消息提示 -->
<#-- TODO 统一页面消息提示风格 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#assign dateStr=formatDateChinese(dateType,date)>
<#assign dateString=formatDate(dateType,date)>

<#--行为规则单往，返程详情-->
    <div class="row">
        <div class="span12">
            <fieldset>
                <legend>
                    <a class="go-back"
                       href="actionRuleView.action?ruleName=${ruleFilter!}&date=${dateString!}&dateType=${dateType!}"
                       title="返回"><i class="mw-icon-prev"></i>行为规则详情</a>
                    <h4><span style="color:">${ruleName!}<span class="p-l-20"></span></span>${dateStr!}的详细数据</h4>
                </legend>
            </fieldset>
        </div>
    </div>

<#if userCensusActionBeanList?size gt 0>
    <div class="row">
        <div class="span12">
            <table class="table">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>人员</th>
                    <th>停留时间(秒)</th>
                    <th>往</th>
                    <th>返</th>
                </tr>
                    <#list userCensusActionBeanList as userCensus>
                    <tr>
                        <td>${userCensus_index+1}</td>
                        <td>
                            <a href="userRule.action?userId=${userCensus.person.id!}">${userCensus.person.personName}</a>
                        </td>
                        <td> <@difftime userCensus.duration?long /></td>
                        <td>${userCensus.goAction.rule.ruleName} ${userCensus.goAction.occurrenceTime?string("yyyy-MM-dd HH:mm:ss")!}</td>
                        <td>${userCensus.backAction.rule.ruleName} ${userCensus.backAction.occurrenceTime?string("yyyy-MM-dd HH:mm:ss")!}</td>
                    </tr>
                    </#list>
                </thead>

            </table>
        </div>
    </div>

<#--分页-->
          <#--TODO 分页工具归总 -->
    <#include "../_common/pagging.ftl">
    <#assign url = "censusActionRuleDetail.action?date=${dateString!}&dateType=${dateType!}&ruleId=${ruleId!}&ruleName=${ruleName!}">
    <@pagination url index pageCount "index"></@pagination>

</#if>

<#if userCensusActionBeanList?size==0>
    <h4>暂无数据</h4>
</#if>

</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>

</html>



