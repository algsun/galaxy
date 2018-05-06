<#--
二级导航栏

@author gaohui
@date 2012-11-26
-->

<#--
currentPrivilege: 为当前页面的二级权限 id
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
        <#local  privileges = Session["subsystemPrivileges"].get(1?int)>
    <#else>
    <#--如果是其他站点，则使用访客权限-->
        <#local  privileges = Session["subsystemPrivilegesOfGuest"].get(1?int)>
    </#if>

    <#list privileges as topPrivilge>
        <#if topPrivilge.childPrivileges??>
        <#--遍历子权限-->
            <#list topPrivilge.childPrivileges as childPrivilege>
                <#if childPrivilege.privilegeId == currentPrivilegeId>
                    <#return topPrivilge>
                </#if>
            </#list>
        </#if>
    </#list>
    <#return {"childPrivileges":[]}>
</#function>