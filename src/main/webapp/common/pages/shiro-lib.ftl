<#--
 提供 shiro 框架 freemarker 标签支持

@author gaohui
@date 2012-11-20
-->

<#--
是否认证

用法: <#if _isAuthenticated()>content</#if>

可以通过在 freemarker.Configuration 中添加共享变量来做, 参考 Freemarker.pdf 2.5.1
-->
<#--<#assign _isAuthenticated = "com.microwise.common.sys.freemarker.shiro.IsAuthenticatedTool"?new()>-->
<#--<#assign _isPermitted = "com.microwise.common.sys.freemarker.shiro.IsPermittedTool"?new()>-->

<#--
是否认证

用法: <@isAuthenticated>content</@isAuthenticated>
-->
<#macro isAuthenticated>
    <#if security.isAuthenticated()>
        <#nested>
    </#if>
</#macro>

<#macro isPermitted permission>
    <#if security.isPermitted(permission)>
        <#nested>
    </#if>
</#macro>

<#--TODO bug, do not works @gaohui 2012-12-19 -->
<#macro isPermittedAll permisssions>
    <#if security.isPermittedAll(permisssions)>
        <#nested>
    </#if>
</#macro>

<#macro hasRole role>
    <#if security.hasRole(role)>
        <#nested>
    </#if>
</#macro>

<#--TODO bug, do not works @gaohui 2012-12-19 -->
<#macro hasAllRoles roles>
    <#if security.hasAllRoles>
        <#nested>
    </#if>
</#macro>
