<#--
业务系统列表

TODO 废弃,删除 @gaohui 2013-04-19

@author gaohui
@date 2013-04-02
-->

<div class="brand dropdown" style="width:120px;">
    <a href="#" class="dropdown-toggle t-d-none" style="color:#bbb;"
       data-toggle="dropdown">
        <img src="../common/images/subsystems/blackhole-small.png" class="subsystem-head-icon">系统管理
        <span class="caret" style="border-top-color: #DDD;"></span>
    </a>
<#--业务系统-->
    <ul class="dropdown-menu" style="text-shadow: none;">
    <#-- 如果是基层站点 -->
    <#if Session["currentLogicGroup"].site??>
        <#list Session["app.subsystems"]?values as subsystem>
            <#if subsystem.subsystemCode != "blackhole">
                <@_subsystem_item subsystem/>
            </#if>
        </#list>
    </#if>
        <li class="active">
            <a href="../blackhole/" style="color:white;"><img
                    src="../common/images/subsystems/blackhole-small.png" class="subsystem-head-icon">
                系统管理
            </a>
        </li>
    </ul>
</div>

<#macro _subsystem_item subsystem>
    <#local subsystemCode = subsystem.subsystemCode>
    <#if subsystem.enable>
        <#if security.isUserPermitted("blackhole:subsystem:" + subsystemCode)>
        <li>
            <a href="../${subsystemCode}/"><img src="../common/images/subsystems/${subsystemCode}-small.png" class="subsystem-head-icon">
                ${subsystem.subsystemName}
            </a>
        </li>
        </#if>
    </#if>
</#macro>