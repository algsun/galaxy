<#--
辅助工具

@author gaohui
@date 2013-05-20
@check xiedeng 2013-6-6 16:28 svn:3953
TODO 是否可以将所有的宏，函数提出来写个工具
-->

<#macro strongOutEventId outEventId>
<span style="color: black;">${outEventId?substring(0,8)}</span><strong style="font-size: 1.3em;">${outEventId?substring(8)}</strong>
</#macro>

<#macro _eventTypeName eventType>
    <#switch eventType>
        <#case 1>外出借展<#break>
        <#case 2>科研修复<#break>
    </#switch>
</#macro>

<#macro _stateName state>
    <#switch state>
        <#case 0>在库<#break>
        <#case 3>出库申请中<#break>
        <#case 1>待出库<#break>
        <#case 2>出库<#break>
    </#switch>
</#macro>

<#function _completeTaskUrl taskId, forward, vars>
    <#local varsParam = "">
    <#list vars as var>
        <#local varsParam = varsParam + "&var." + var.name + "=" + var.value + "&type." + var.name + "=" + var.type>
    </#list>
    <#return "completeTask.action?taskId=" + taskId + "&forward=" + forward + varsParam>
</#function>
