<#--
  - 人员行为规则统计
  -@author wang.geng
  -@time  2013-4-25
  -->
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
<#include "../_common/common-head.ftl">
    <title>人员行为查询 - 人员管理</title>
<#include "../_common/common-css.ftl">
    <link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">

</head>
<body>
<#assign currentTopPrivilege = "uma:statistics:staffrole">
<#include "../_common/header.ftl">
<#include "../_common/difftime.ftl">

<div id="gcontent" class="container m-t-50">

<#-- 消息提示 -->
<#-- TODO 页面信息提示工具重构为一个，统一风格 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>
<#--过滤条件-->
    <div class="row">
        <div class="span12">
            <fieldset>
                <legend>人员行为查询</legend>
            </fieldset>
            <form class="form-inline well well-small" id="userForm" action="userRule.action" method="post">
                <label for="userName">姓名</label>
            <@selectOption personList/>
                <label>开始时间</label>
                <input class="input-medium Wdate" id="start-time" type="text" name="startDate"
                       onfocus="var endTime=$dp.$('end-time');WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'end-time\')}'})"
                       value="${startDate?string("yyyy-MM-dd HH:mm:ss")}"/>
                <label>结束时间</label>
                <input class="input-medium Wdate" id="end-time" type="text" name="endDate"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59', minDate:'#F{$dp.$D(\'start-time\')}', maxDate:'%y-%M-{%d}'})"
                       value="${endDate?string("yyyy-MM-dd HH:mm:ss")}"/>
                <label class="radio">
                    <input type="radio" name="order" value="false" <#if !order>checked</#if> />时间倒序
                </label>
                <label class="radio">
                    <input type="radio" name="order" value="true" <#if order>checked</#if> />时间正序
                </label>
                <button type="button" id="submitBtn" class="btn">查询</button>
            </form>

        </div>
    </div>

<#--content 列表-->
<#if staffruleList??>
    <#if staffruleList?size != 0>
        <div class="row">
            <div class="span12">
                <table id="staffrule" class="table">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>姓名</th>
                        <th>规则</th>
                        <th>类型</th>
                        <th>时间</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list staffruleList as staffrule>
                        <tr showSub="1" ruleId="${staffrule.id}">
                            <td>${staffrule_index+1}</td>
                            <td>${staffrule.person.personName}</td>
                            <td>
                                <#if staffrule.type == 1>
                                ${staffrule.rule.ruleName}
                                <#else>
                                    <span data-type="staffrule" style="cursor: pointer;">
                                    <i class="<#if showSub == 1>icon-plus-sign<#else>icon-minus-sign</#if>"></i>
                                    ${staffrule.rule.ruleName}
                                </span>
                                </#if>
                            </td>
                            <td>
                                <#if staffrule.type==1>
                                    <i class="mw-icon-one-way"></i> 单程
                                <#elseif staffrule.type==2>
                                    <i class="mw-icon-two-way"></i> 往返
                                </#if>
                            </td>
                            <td>
                                <#if staffrule.type == 2>
                                    <#if staffrule.goAction.rule?? && staffrule.backAction.rule??>
                                    <@difftime staffrule.duration?long />
                                </#if>
                                <#elseif staffrule.occurrenceTime??>
                                ${staffrule.occurrenceTime?string("yyyy-MM-dd HH:mm:ss")}
                                </#if>
                            </td>
                        </tr>
                            <#if staffrule.type == 2>
                                <@sonrulemac staffrule.id staffrule.goAction/>
                                <@sonrulemac staffrule.id staffrule.backAction/>
                            </#if>
                        </#list>
                    </tbody>
                </table>
                <#include  "../_common/pagging.ftl">
                <#assign  url = "userRule.action?order=${order?string}&userId=${userId}&startDate=${startDate?string('yyyy-MM-dd HH:mm:ss')}&endDate=${endDate?string('yyyy-MM-dd HH:mm:ss')}">
                <@pagination url,currentPage,pageCount,"currentPage"/>
            </div>
        </div>
    </#if>
<#else>
    <#if staffruleList?size == 0>
        <h4>暂无数据</h4>
    </#if>
</#if>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#-- js -->
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="js/strToPY.js"></script>
<script type="text/javascript">
    //初始化人员列表信息
    $(function () {
        $("#userId").select2({
            placeholder: "请选择人员",
            formatNoMatches: function () {
                return "没有匹配的人员";
            },
            matcher: function (term, text, option) {
                return (text.toUpperCase().indexOf(term.toUpperCase()) >= 0
                        || makePy(option.text().toUpperCase()).join().indexOf(term.toUpperCase()) >= 0);
            }
        });
        $("#userId").select2("val",${userId});

        // 展开关闭区域
        $("span[data-type='staffrule']").on("click", function () {
            var $this = $(this);
            var $tr = $this.parent().parent();
            var ruleId = $tr.attr("ruleId");

            $this.find("i").toggleClass("icon-plus-sign");
            $this.find("i").toggleClass("icon-minus-sign");
            $("tr[data-parent-id='" + ruleId + "']").toggle();
        });

        $("#submitBtn").click(function () {
            var startTime = $("#start-time").val();
            var endTime = $("#end-time").val();
            if (startTime.length < 1) {
                art.dialog({
                    id: "info",
                    title: "消息提示",
                    content: "在查询前请选择开始时间"
                });
                return;
            }
            if (endTime.length < 1) {
                art.dialog({
                    id: "info",
                    title: "消息提示",
                    content: "在查询前请选择结束时间"
                });
                return;
            }
            $("#userForm").submit();
        });
    });

</script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
<#-- macros -->
<#macro sonrulemac id rule>
    <#if rule.rule??>
    <tr data-parent-id="${id}" class="hide">
        <td></td>
        <td>&emsp;&emsp;</td>
        <td><span class="m-l-40">${rule.rule.ruleName}</span></td>
        <td>
            <#if rule.rule.type==3>
                <i class="icon-arrow-right"></i> 往
            <#elseif rule.rule.type==4>
                <i class="icon-arrow-left"></i> 返
            </#if>
        </td>
        <td>${rule.occurrenceTime?string("yyyy-MM-dd HH:mm:ss")}</td>
    </tr>
    </#if>
</#macro>
<#macro selectOption personList>
<select style="width: 150px" name="userId" id="userId">
    <#if personList?size != 0>
        <option value="0">所有人员</option>
        <#list personList as person>
            <option value="${person.id}">${person.personName}</option>
        </#list>
    </#if>
</select>
</#macro>
