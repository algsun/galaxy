<#--
  -
  -@author li.jianfei
  -@time  13-4-11  13:40
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
        <@singleNav privilege currentTopPrivilege></@singleNav>
    </#if>
    </#list>
</ul>
</#macro>

<#macro singleNav privilege currentTopPrivilege>
    <#local active = (privilege.privilegeId == currentTopPrivilege)>
    <#if privilege.childPrivileges?size==0>
    <li class="<#if active>active</#if>"><a href="${privilege.url}">${privilege.privilegeCnName}</a></li>
    <#else>
    <li class="dropdown <#if active>active</#if>">
        <a href="#" class="dropdown-toggle"
           data-toggle="dropdown"
           data-hover="dropdown">${privilege.privilegeCnName}<b class="caret"></b></a>
        <ul class="dropdown-menu">
            <#list privilege.childPrivileges as childPrivilege>
                <#if childPrivilege.navigation>
                    <li class="<#if childPrivilege.privilegeId == currentTopPrivilege>active</#if>">
                        <a href="${childPrivilege.url}">${childPrivilege.privilegeCnName}</a></li>
                </#if>
            </#list>
        </ul>
    </li>
    </#if>
</#macro>

<#-- 下面的 11 为 "成果展示" 标识 -->
<#--归属站点-->
<#if security.atHome()>
    <@subsystem_navs Session["subsystemPrivileges"].get(11?int), currentTopPrivilege></@subsystem_navs>
<#else>
<#-- 访客权限 -->
    <@subsystem_navs Session["subsystemPrivilegesOfGuest"].get(11?int), currentTopPrivilege></@subsystem_navs>
</#if>
