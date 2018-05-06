<#--
@author gaohui
@date 2013-07-03
-->


<#assign title="这是一个样例页面">
<#include  "../_common/helper.ftl">
<#macro head>

</#macro>

<#macro content>
<h3>我是例子</h3>


<@alertMsg "这是总结"/>
<@alertNested>
    <strong>这是总结</strong>
</@alertNested>
</#macro>

<#macro script>

</#macro>