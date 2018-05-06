<#--
页面最后的资源

@author gaohui
@date 2013-01-05
-->

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