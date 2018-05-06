<#--
一，二级权限菜单

@author gaohui
@date 2013-01-21
-->

<#--
参数:
privilegesOfSubsystem 用户对应业务系统权限
currentTopPrivilege : 当前选中权限, 此参数可选(xSubsystem 只是占个位，没有任何意义)

-->
<#macro subsystem_navs privilegesOfSubsystem, currentTopPrivilege = "xSubsystem">
<ul class="nav">
    <#list privilegesOfSubsystem as privilege>
        <#if privilege.navigation>
            <#local isCurrentTopPrivilege = false>
            <#if currentTopPrivilege??>
                <#if privilege.privilegeId == currentTopPrivilege><#local isCurrentTopPrivilege = true></#if>
            </#if>

            <li <#if isCurrentTopPrivilege>class="active"</#if>>
                <a href="${privilege.url}">${privilege.privilegeCnName}</a>
            </li>
        </#if>
    </#list>
</ul>
</#macro>




<#-- 一二级权限菜单 -->

<#-- 下面的 2 为 "环境监控" 标识 -->
<#--归属站点权限-->
<#if security.atHome()>
    <@subsystem_navs Session["subsystemPrivileges"].get(10?int), currentTopPrivilege></@subsystem_navs>
<#else>
<#-- 访客权限 -->
    <@subsystem_navs Session["subsystemPrivilegesOfGuest"].get(10?int), currentTopPrivilege></@subsystem_navs>
</#if>
