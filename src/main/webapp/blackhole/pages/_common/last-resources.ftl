<#--
加载最后的公共资源. 如 google 分析，IE6 升级提示.

@author gaohui
@date 2012-11-22
-->

<#-- 收集用户反馈 -->
<#--<#include "/common/pages/geekui.ftl">-->

<#--
google 分析
@author gaohui
@date 2013-01-10
-->
<#if Application["app.stage.product"]>
    <#include "/common/pages/google-analytics.ftl">
</#if>
<#--开发环境分析-->
<#if Application["app.stage.develop"]>
    <#include "/common/pages/google-analytics-dev.ftl">
</#if>

<#include "/common/pages/bye-ie6.ftl">