<#--
行为规则管理页面

@author xubaoji
@date  2013-04-11
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>行为规则查询 - 人员管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "uma:rule">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">


<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "uma:rule:list"></@subNavs>

<#-- 消息提示 -->
<#-- TODO 统一页面消息提示风格 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--行为规则列表列表-->
    <div class="row">
        <div class="span12">
            <form class="well well-small form-inline" action="" method="post">
                <label>行为规则名称</label>
                <input type="text" name="ruleName" value="${ruleName!}">
                <label>类型</label>
                <select name="type">
                    <option value="">全部</option>
                    <option value="1"
                            <#if type?? && type==1>selected="selected"</#if>>单程
                    </option>
                    <option value="2"
                            <#if type?? && type==2>selected="selected"</#if>>往返
                    </option>
                </select>
                <button type="submit" class="btn">查询</button>
            </form>
        </div>
    </div>

<#--无结果-->
<#if (ruleList.size() > 0)>
    <div class="row">
        <div class="span12">
            <table class="table">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>行为规则名称</th>
                    <th>类型</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#list ruleList as rule >
                    <tr>
                        <td>${rule_index+1}</td>
                        <td>
                            <#if rule.type == 1>
                                <a name="ruleName" value='${rule.id}' data-toggle="popover" title='规则详情'
                                   href="javascript:void(0)"
                                   data-html="true"
                                   data-trigger="click" data-content='<#assign singleindex=1>
                              <#list rule.singleRuleList! as sub>
                              		<br>(${singleindex})<#assign singleindex=singleindex+1>
		                            <#list sub.deviceList! as device>
		                                <#if device_index==0 >${device.name!device.sn}</#if>
		                                <#if device_index!=0 >→${device.name!device.sn}</#if>
		                            </#list>
		                           </#list>'>${rule.ruleName}</a>
                            </#if>
                            <#if rule.type == 2>
                                <a name="ruleName" value='${rule.id}' data-toggle="popover" title='规则详情'
                                   href="javascript:void(0)"
                                   data-container="#container" data-trigger="hover"
                                   data-html="true" data-content='
                        <#assign goindex=1>
                        			<strong>往规则：</strong>
                                  <#list rule.goRuleList! as sub>
                        			<br><span >(${goindex})<#assign goindex=goindex+1 >
		                            <#list sub.deviceList! as device>
		                                <#if device_index==0 >${device.name!device.sn}</#if>
		                                <#if device_index!=0 >→${device.name!device.sn}</#if>
		                            </#list>
		                            </span>
		                           </#list>
		                           <#assign backindex= 1>
		                           <br> <strong>返规则：</strong>
		                           <#list rule.backRuleList! as sub>
		                           	<br>(${backindex}) <#assign backindex= backindex+1>
		                            <#list sub.deviceList! as device>
		                                <#if device_index==0 >${device.name!device.sn}</#if>
		                                <#if device_index!=0 >→${device.name!device.sn}</#if>
		                            </#list>
		                           </#list>'>${rule.ruleName}</a>
                            </#if>
                        </td>
                        <td>
                            <#if rule.type == 1>
                                <i class="mw-icon-one-way"></i> 单程
                            </#if>
                            <#if rule.type == 2>
                                <i class="mw-icon-two-way"></i> 往返
                            </#if>
                        </td>

                        <td>
                            <#if security.isPermitted("uma:rule:update")>
                                <#if rule.type==1>
                                    <a class="btn  btn-mini" href="updateSingleRuleView.action?ruleId=${rule.id}"
                                       title="修改">
                                        <i class="icon-pencil"></i>编辑
                                    </a>
                                <#elseif rule.type==2>
                                    <a class="btn  btn-mini" href="updateGoBackRuleView.action?ruleId=${rule.id}"
                                       title="修改">
                                        <i class="icon-pencil"></i>编辑
                                    </a>
                                </#if>
                            </#if>
                            <#if security.isPermitted("uma:rule:enable")>
                                <#if rule.enable>
                                    <a class="unenable-rule btn btn-danger btn-mini"
                                       href="setRuleEnable.action?id=${rule.id}&enable=false" title="停用">
                                        <i class="icon-stop icon-white"></i>停用
                                    </a>
                                <#else>
                                    <a class="enable-rule btn btn-success btn-mini"
                                       href="setRuleEnable.action?id=${rule.id}&enable=true" title="启用">
                                        <i class="icon-play icon-white"></i>启用
                                    </a>
                                </#if>
                            </#if>
                        </td>
                    </tr>
                    </#list>

                </tbody>
            </table>
        </div>
    </div>
</#if>
<#--分页-->
    <#-- TODO 分页工具 -->
<#include "../_common/pagging.ftl">
<#assign url = "queryRule.action?ruleName=${ruleName!}&type=${type!}">
<@pagination url index pageCount "index"></@pagination>

<#if ruleList?size == 0>
    <h4>暂无数据</h4>
</#if>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#include "../_common/last-resources.ftl">
<script type="text/javascript">
    $(function () {
        /*获取的本来就是数组，你要获取数组的数值就需要遍历*/
        $("a[name='ruleName']").mouseover(function () {
            $(this).popover('show');
            return false;
        }).mouseout(function () {
            $(this).popover('hide');
        });
    });
</script>
<div id="container" style="width:100%;">
</body>
</html>



