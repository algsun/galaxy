<#--
 角色管理 权限分配

@author Wang yunlong
@date  12-11-28 下午2:10
-->

<#-- 默认显示的业务系统(目前为系统管理) -->
<#assign _defaultViewSubsystem = "blackhole">

<div class="row m-t-10">
    <div class="span12">
        <div class="tabbable tabs-left">
            <ul class="nav nav-tabs">
            <#list subsystems as subsystem>
            <#--
            只显示启用的业务系统, 只屏蔽了 tab 而没有屏蔽 tab-content , 用意在于保留用户未启用的业务系统权限,
            业务系统开启后，权限是可用的。
            -->
                <#if subsystem.enable>
                    <#assign isBaseSite = Session["currentLogicGroup"].site??>
                    <#if subsystem.subsystemCode != 'biela'>
                        <li <#if subsystem.subsystemCode == _defaultViewSubsystem>class="active"</#if>>
                            <a class="subsystem" href="#${subsystem.subsystemCode!}"
                               data-toggle="tab">${subsystem.subsystemName!}</a>
                        </li>
                    <#else>
                        <#if !(isBaseSite)>
                            <li <#if subsystem.subsystemCode == _defaultViewSubsystem>class="active"</#if>>
                                <a class="subsystem" href="#${subsystem.subsystemCode!}"
                                   data-toggle="tab">${subsystem.subsystemName!}</a>
                            </li>
                        </#if>
                    </#if>

                </#if>
            </#list>
            </ul>
            <div class="tab-content">
            <#include "anonymity-privilege-tree.ftl">
            <#list privilegeList?keys as pk>
                <div class="tab-pane <#if pk == _defaultViewSubsystem>active</#if>"
                     id="${pk}">
                    <div class="tab-content">
                        <div class="accordion" id="accordion${pk_index}">
                        <#--权限以树状展示-->
                        <@openPrivileges privilegeList.get(pk)></@openPrivileges>
                        </div>
                    </div>
                </div>
            </#list>
            </div>
        </div>
    </div>
</div>

