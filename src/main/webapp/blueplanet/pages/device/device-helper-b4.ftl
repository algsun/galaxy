<#--
提供设备相关帮助，工具方法

@author gaohui
@date 2013-01-25
-->

<#-- 产品序列号 -->
<#macro snOfDevice sn>
    <#if sn == "0">
    ${locale.getStr("blueplanet.device.snValue")}
    <#else>
    ${sn}
    </#if>
</#macro>

<#-- 返回设备类型对应的中文名称 -->
<#macro typeIconOfDevice deviceType>
    <#switch deviceType>
        <#case 1><i class="mw-icon-sensor"></i><#break>
        <#case 2><i class="mw-icon-repeator"></i><#break>
        <#case 3><i class="mw-icon-sensor"></i><#break>
        <#case 4><i class="mw-icon-sensor"></i><#break>
        <#case 5><i class="mw-icon-control-module"></i><#break>
        <#case 7><i class="mw-icon-gateway"></i><#break>
    </#switch>
</#macro>

<#-- 返回设备类型对应的中文名称 -->
<#macro typeNameOfDevice deviceType isHumidity=0>
    <#if isHumidity == 1>
    ${locale.getStr("common.humidity")}
    <#else>
        <#switch deviceType>
            <#case 1>${locale.getStr("common.node")}<#break>
            <#case 2>${locale.getStr("common.relay")}<#break>
            <#case 3>${locale.getStr("common.node")}<#break>
            <#case 4>${locale.getStr("common.node")}<#break>
            <#case 5>${locale.getStr("common.controlModule")}<#break>
            <#case 7>${locale.getStr("common.gateway")}<#break>
        </#switch>
    </#if>
</#macro>

<#-- 突出显示节点 id -->
<#macro strongNodeId nodeId, short=false>
<span class="text-muted <#if short>hide</#if>">${nodeId?substring(0, 8)}</span><strong>${nodeId?substring(8)}</strong>
</#macro>

<#-- 工作模式 -->
<#macro deviceMode value>
    <#if value == 0>
        ${locale.getStr("blueplanet.device.deviceMode.normal")}
    <#elseif value == 1>
    <span style="color:#8E388E">
        ${locale.getStr("blueplanet.device.deviceMode.inspection")}
    </span>
    </#if>
</#macro>

<#-- 设备状态 -->
<#macro deviceState device>
<#-- 超时 -->
    <#if device.anomaly == -1>
        <@_deviceStateVew device, "label-danger", locale.getStr("blueplanet.device.anomaly.overtime")/>
    <#-- 正常 -->
    <#elseif device.anomaly == 0 >
        <@_deviceStateVew device, "label-success", locale.getStr("blueplanet.device.anomaly.normal")/>
    <#-- 低电 -->
    <#elseif device.anomaly == 1>
        <@_deviceStateVew device, "label-warning", locale.getStr("blueplanet.device.anomaly.lowVoltage")/>
    <#-- 掉电 -->
    <#elseif device.anomaly == 2>
        <@_deviceStateVew device, "label-warning", locale.getStr("blueplanet.device.anomaly.powerDown")/>
    </#if>
</#macro>

<#macro _deviceStateVew device, class, name>
    <#if device.lowvoltage == -1>
    <span class="label ${class}">${name}</span>
    <#else>
    <span class="label ${class}">${device.lowvoltage}V</span>
    </#if>
</#macro>

<#-- 是否可控 -->
<#macro deviceControl canNotControl>
    <#if canNotControl>
    ${locale.getStr("common.no")}
    <#else>
    ${locale.getStr("common.yes")}
    </#if>
</#macro>

<#-- 工作周期 -->
<#macro deviceWorkInterval workInterval>
    <#if (workInterval < 60)>
    ${workInterval} ${locale.getStr("common.second")}
    <#else>
    ${workInterval/60} ${locale.getStr("common.minute")}
    </#if>
</#macro>

<#-- 对设备状态进行描述 -->
<#macro deviceStateDescription>
<p>${locale.getStr("blueplanet.device.anomaly.description1")}
    <span class="label label-success">${locale.getStr("blueplanet.device.anomaly.normal")}</span>
    <span class="label label-warning">${locale.getStr("blueplanet.device.anomaly.lowVoltage")}/${locale.getStr("blueplanet.device.anomaly.powerDown")}</span>
    <span class="label label-important">${locale.getStr("blueplanet.device.anomaly.overtime")}</span>
    <span class="label">${locale.getStr("blueplanet.device.anomaly.unknown")}</span>,
    ${locale.getStr("blueplanet.device.anomaly.description2")} <span class="label label-success">3.7V</span>。
</p>
</#macro>

<#-- 原始值符号类型 -->
<#macro signTypeName signType>
    <#if signType == 0>
    ${locale.getStr("blueplanet.device.formula.signType.unsigned")}
    <#else>
    ${locale.getStr("blueplanet.device.formula.signType.signed")}
    </#if>
</#macro>

<#macro rangeTypeName rangeType>
    <#switch rangeType>
        <#case 0> ${locale.getStr("blueplanet.device.formula.rangeType.unlimited")}<#break>
        <#case 1> ${locale.getStr("blueplanet.device.formula.rangeType.minimumLimit")}<#break>
        <#case 2> ${locale.getStr("blueplanet.device.formula.rangeType.maximumLimit")}<#break>
        <#case 3> ${locale.getStr("blueplanet.device.formula.rangeType.maxMinLimit")}<#break>
    </#switch>
</#macro>
