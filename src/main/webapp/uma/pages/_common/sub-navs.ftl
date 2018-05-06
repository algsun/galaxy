<#--
二级导航栏

@author gaohui
@date 2012-11-26
-->

<#--
currentPrivilege: 为当前页面的权限 id
 -->
<#macro subNavs currentPrivilegeId>
<div class="row m-t-10">
    <div class="span12">
        <ul class="nav nav-pills">
            <#list subnav_parentPrivilege(currentPrivilegeId).childPrivileges as privilege>
                <#if privilege.navigation>
                    <li <#if privilege.privilegeId == currentPrivilegeId>class="active"</#if>>
                        <a href="${privilege.url}">${privilege.privilegeCnName}</a>
                    </li>
                </#if>
            </#list>
        </ul>
    </div>
</div>
</#macro>

<#--返回某个权限的父权限-->
<#function subnav_parentPrivilege currentPrivilegeId>
<#--系统管理权限-->
    <#local privileges = []>
<#--如果是归属站点-->
    <#if security.atHome()>
        <#local  privileges = Session["subsystemPrivileges"].get(5?int)>
    <#else>
    <#--如果是其他站点，则使用访客权限-->
        <#local  privileges = Session["subsystemPrivilegesOfGuest"].get(5?int)>
    </#if>

    <#list privileges as topPrivilge>
        <#local targetPrivilege = subnav_parentPrivilegeOfTree(topPrivilge, currentPrivilegeId)>
        <#if targetPrivilege.privilegeId != "nil">
            <#return targetPrivilege>
        </#if>
    </#list>
    <#return {"childPrivileges":[]}>
</#function>

<#-- 返回某个权限的父权限 -->
<#function subnav_parentPrivilegeOfTree parent childPrivilegeId>
    <#if parent.childPrivileges??>

        <#list parent.childPrivileges as childPrivilege>
            <#-- 如果直接孩子相同, 返回父权限 -->
            <#if childPrivilege.privilegeId == childPrivilegeId>
                <#return parent>
            <#else>
                <#assign _parent = subnav_parentPrivilegeOfTree(childPrivilege, childPrivilegeId)>
                <#if _parent.privilegeId != "nil">
                    <#return _parent>
                </#if>
            </#if>
        </#list>
    </#if>

    <#-- 不存在, 返回 id 为 -1 -->
    <#return {"privilegeId":"nil"}>
</#function>
