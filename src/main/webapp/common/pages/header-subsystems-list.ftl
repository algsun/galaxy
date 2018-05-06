<#--
业务系统列表

@author gaohui
@date 2013-04-02
@author liuzhu
@date 2014-03-26
-->

<#macro _subsytem_list activeSubsystemCode>
    <#local activeSubsystem = Session["app.subsystems"][activeSubsystemCode]>

<div class="brand dropdown">
    <a href="#" class="dropdown-toggle t-d-none" style="color: ${_subsystem_color(activeSubsystemCode)};"
       data-toggle="dropdown">
        <img src="../common/images/subsystems/${activeSubsystemCode}-small.png"
             class="subsystem-head-icon">${activeSubsystem.subsystemName}
        <span class="caret" style="border-top-color: #DDD;"></span>
    </a>
<#--业务系统-->
    <ul class="dropdown-menu" style="text-shadow: none;">
    <#-- 如果是基层站点 -->
    <#if Session["currentLogicGroup"].site??>
        <#list Session["subsystemList"] as subsystem>
            <#local active = (subsystem.subsystemCode == activeSubsystemCode)>
            <#if subsystem.subsystemCode != 'biela'>
                <@_subsystem_item subsystem active/>
            </#if>
        </#list>
    <#else>
        <@_subsystem_item_view Session["app.subsystems"]["blackhole"] true/>
        <#--<@_subsystem_item_view Session["app.subsystems"]["biela"] false/>-->
    <#list Session["subsystemList"] as subsystem>
            <#local active = (subsystem.subsystemCode == activeSubsystemCode)>
            <#if subsystem.subsystemCode == 'biela'>
                <@_subsystem_item subsystem active/>
            </#if>
        </#list>
    </#if>
    </ul>
</div>
</#macro>

<#macro _subsystem_item subsystem, active = false>
    <#local subsystemCode = subsystem.subsystemCode>
    <#if subsystem.enable>
        <#if security.isUserPermitted("blackhole:subsystem:" + subsystemCode)>
            <@_subsystem_item_view subsystem active/>
        </#if>
    </#if>
</#macro>

<#macro _subsystem_item_view subsystem, active = false>
    <#local subsystemCode = subsystem.subsystemCode>
<li <#if active>class="active"</#if>>
    <a class="subsystemHead" href="../${subsystemCode}/" <#if active>style="color:white;"</#if>><img
            src="../common/images/subsystems/${subsystemCode}-small.png" class="subsystem-head-icon">
    ${subsystem.subsystemName}
    </a>
</li>
</#macro>

<#-- 返回业务系统颜色 -->
<#function _subsystem_color subsystemCode>
    <#switch subsystemCode>
        <#case "blackhole"><#return "#ddd"><#break>
        <#case "blueplanet"><#return "#73a53f"><#break>
        <#case "proxima"><#return "#377ef0"><#break>
        <#case "orion"><#return "#cf6c00"><#break>
        <#case "uma"><#return "#00ba5e"><#break>
        <#default><#return "white">
    </#switch>
</#function>
