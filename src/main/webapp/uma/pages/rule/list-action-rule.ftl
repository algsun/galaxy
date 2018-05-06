<#--
行为统计页面

@author wangrensong
@date  2013-04-26
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>行为统计 - 人员管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<#assign currentTopPrivilege = "uma:statistics:actionrole">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#-- 消息提示 -->
<#-- TODO 统一页面消息提示风格 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#if dateType==0>
    <#assign dateType=3>
</#if>
<#assign dateString="">
<#if dateType==1>
    <#assign dateString=date?string("yyyy")!>
<#elseif dateType==2>
    <#assign dateString=date?string("yyyy-MM")!>
<#elseif dateType==3>
    <#assign dateString=date?string("yyyy-MM-dd")!>
</#if>

<#--行为规则列表查询-->
    <div class="row">
        <div class="span12">
            <fieldset>
                <legend>行为统计</legend>
            </fieldset>
            <form class="well well-small form-inline"
                  action="actionRuleView.action" method="post">
                <label class="m-l-10">时间类型</label>

                <label class="radio m-l-10">
                    <input id="radioYear" name="dateType" type="radio" value="1" <#if dateType==1> checked </#if>>年
                </label>
                <label class="radio m-l-10">
                    <input id="radioMonth" name="dateType" type="radio" value="2" <#if dateType==2> checked </#if>>月
                </label>
                <label class="radio m-l-10">
                    <input id="radioDay" name="dateType" type="radio" value="3" <#if dateType==3> checked </#if>>日
                </label>
                <label class="m-l-10">时间</label>

                <input id="date" name="date" class="input-medium Wdate" type="text" value="${dateString}">

                <label style="padding-left:10px">行为规则名称</label>
                <input type="text" name="ruleName" value="${ruleName!}">

                <button id="commit" class="btn" type="submit">查询</button>
            </form>
        </div>
    </div>

<#if !actionRulelist??>
    <h4>暂无数据</h4>
<#elseif actionRulelist?size <= 0>
    <h4>暂无数据</h4>
</#if>


<#if actionRulelist??>
    <#if (actionRulelist?size > 0)>
        <div class="row">
            <div class="span12">
                <table class="table">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>名称</th>
                        <th>类型</th>
                        <th>统计</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list actionRulelist as rule>
                        <tr>
                            <td>${rule_index+1}</td>
                            <td>
                                <#if rule.type==2>
                                    <a class="muted pull-left" data-master="${rule.id}"
                                       style="color: #000000;text-decoration: none;cursor: pointer;">
                                        <i class="<#if rule.childRuleStats??>icon-plus-sign<#else>icon-minus-sign</#if>">
                                        </i>
                                    ${rule.ruleName}
                                    </a>
                                </#if>

                                <#if rule.type==1>
                                ${rule.ruleName}
                                </#if>
                            </td>
                            <td>
                                <#if rule.type=1> <i class="mw-icon-one-way"></i> 单程
                                <#elseif rule.type=2> <i class="mw-icon-two-way"></i> 往返</#if>
                            </td>
                            <td>${rule.count}</td>
                            <td>
                                <a href="<#if rule.type=1>singleActionRuleDetail.action<#elseif rule.type=2>censusActionRuleDetail.action</#if>?ruleFilter=${ruleName!}&ruleId=${rule.id}&date=${dateString}&dateType=${dateType}&ruleName=${rule.ruleName}"
                                   class="btn btn-mini btn-success">
                                    <i class="icon-list icon-white"></i>详情
                                </a>
                            </td>
                        </tr>

                        <#-- 如果是往返规则, 则显示从单程子规则 -->
                            <#if rule.type==2>
                                <#list rule.childRuleStats as childRule>
                                <tr class="hide" data-master-for= ${childRule.parentId}>
                                    <td></td>
                                    <td style="padding-left: 40px;">${childRule.ruleName}</td>
                                    <td>
                                        <#if childRule.type==3> <i class="icon-arrow-right"></i> 往
                                        <#elseif childRule.type==4><i class="icon-arrow-left"></i> 返
                                        </#if>
                                    </td>
                                    <td>${childRule.count}</td>
                                    <td>
                                        <a href="singleActionRuleDetail.action?ruleFilter=${ruleName!}&ruleId=${childRule.id}&date=${dateString!}&dateType=${dateType!}&ruleName=${childRule.ruleName!}"
                                           class="btn btn-mini btn-success">
                                            <i class="icon-list icon-white"></i>详情
                                        </a>
                                    </td>
                                </tr>
                                </tr>
                                </#list>
                            </#if>

                        </#list>
                    </tbody>

                </table>
            </div>
        </div>
    </#if>
</#if>

</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<#--your js-->
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<@scriptTag "js/rule/date-format.js"/>
<@scriptTag "js/rule/actionrule.js"/>


<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>




