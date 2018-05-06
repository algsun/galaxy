<#--
一级菜单

@author gaohui
@date 2012-11-28
 -->

<#--
参数:
currentTopPrivilege : 当前选中的一级菜单权限ID(可选)

-->

<#--
渲染权限的顶级权限菜单

currentTopPrivilege : 当前选中权限, 此参数可选(xSubsystem 只是占个位，没有任何意义)
-->
<#macro topnav_topnav privileges currentTopPrivilege = "xSubystem">
    <#list privileges as privilege>
        <#if !privilege.parent??>
            <#if privilege.navigation>
            <#-- TODO 重构此此代码都 java 中统一处理 @gaohui 2012-12-11 -->

                <#-- 是否是当前权限 -->
                <#local isCurrentTopPrivilege = false>
                <#if currentTopPrivilege??>
                    <#if privilege.privilegeId == currentTopPrivilege><#local isCurrentTopPrivilege = true></#if>
                </#if>

                <#if !privilegeFilter(privilege)>
                <li <#if isCurrentTopPrivilege>class="active"</#if>>
                    <a href="${privilege.url}">${privilege.privilegeCnName!}</a>
                </li>
                </#if>
            </#if>
        </#if>
    </#list>
</#macro>

<ul class="nav">
<#-- 一级权限菜单 -->

<#-- 下面的 1 为 "系统管理" 标识 -->
<#--归属站点-->
<#if security.atHome()>
    <@topnav_topnav Session["subsystemPrivileges"].get(1?int)  currentTopPrivilege></@topnav_topnav>
<#else>
<#-- 其他站点 -->
    <@topnav_topnav Session["subsystemPrivilegesOfGuest"].get(1?int)  currentTopPrivilege></@topnav_topnav>
</#if>
</ul>

<#-- 是否显示, 有些权限在某些时候不能显示。例如基层站点没有一级 "站点" 导航 -->
<#-- 过滤权限: true 不显示，false 显示 -->
<#function privilegeFilter privilege>

    <#--如果当前站点是基层站点则不显示"站点"-->
    <#if privilege.privilegeId == "blackhole:logicGroup">
        <#if Session["currentLogicGroup"].site??>
            <#return true>
        </#if>

        <#-- 站点未激活则不显示"站点" -->
        <#if Session["currentLogicGroup"].activeState != 3>
            <#return true>
        </#if>
    </#if>

    <#-- 行政站点没有区域管理 -->
    <#if privilege.privilegeId == "blackhole:zone">
        <#if !Session["currentLogicGroup"].site??>
            <#return true>
        </#if>
    </#if>

    <#return false>
</#function>