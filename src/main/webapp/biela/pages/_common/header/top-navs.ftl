<#--
  -
  -@author liuzhu
  -@time  2014-2-27
  -->
<#--
参数:
privilegesOfSubsystem 用户对应业务系统权限
 : 当前选中权限, 此参数可选(xSubsystem 只是占个位，没有任何意义)
-->
<#macro topnav_topnav privileges currentTopPrivilege = "xSubystem">
    <#list privileges as privilege>
        <#if !privilege.parent??>
            <#if privilege.navigation>

            <#-- 是否是当前权限 -->
                <#local isCurrentTopPrivilege = false>
                <#if currentTopPrivilege??>
                    <#if privilege.privilegeId == currentTopPrivilege><#local isCurrentTopPrivilege = true></#if>
                </#if>

            <li <#if isCurrentTopPrivilege>class="active"</#if>>
                <a href="${privilege.url}">${privilege.privilegeCnName}</a>
            </li>
            </#if>
        </#if>
    </#list>
</#macro>

<ul class="nav">
<#-- 一级权限菜单 -->

<#-- 下面的 9 为 "区域中心" 标识 -->
<#--归属站点-->
<#if security.atHome()>
    <@topnav_topnav Session["subsystemPrivileges"].get(9?int)  currentTopPrivilege></@topnav_topnav>
<#else>
<#-- 其他站点 -->
    <@topnav_topnav Session["subsystemPrivilegesOfGuest"].get(9?int)  currentTopPrivilege></@topnav_topnav>
</#if>
</ul>