<#--
页面左侧导航

@author gaohui
@date 2013-07-08
-->

<#--归属站点权限-->
<#if security.atHome()>
    <@subsystem_navs Session["subsystemPrivileges"].get(6?int), currentPrivilege/>
<#else>
<#-- 访客权限 -->
    <@subsystem_navs Session["subsystemPrivilegesOfGuest"].get(6?int), currentPrivilege/>
</#if>

<#macro subsystem_navs privilegesOfSubsystem, currentPrivilege = "xSubsystem">
    <#local targetPrivilege = _getCurrentPrivilege(privilegesOfSubsystem, currentPrivilege)>


<div id="accordion" class="accordion m-t-10" role="tablist" aria-multiselectable="true">
    <#list privilegesOfSubsystem as privilege>
        <#if privilege.navigation>

        <#--<div class="accordion-group">-->
        <#--<div class="accordion-heading">-->
        <#--<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion"-->
        <#--href="#collapse-${privilege_index}">-->
        <#--${privilege.privilegeCnName}-->
        <#--</a>-->
        <#--</div>-->
        <#--<div id="collapse-${privilege_index}"-->
        <#--class="accordion-body collapse <#if _isParent(privilege, targetPrivilege)>in</#if>">-->
        <#--<div class="accordion-inner">-->
        <#--<ul class="nav nav-pills nav-stacked">-->
        <#--<#if (privilege.childPrivileges?size < 1)>-->
        <#--<li>敬请期待</li>-->
        <#--</#if>-->

        <#--<#list privilege.childPrivileges as childPrivilege>-->
        <#--<li <#if childPrivilege.privilegeId == currentPrivilege>class="active"</#if>>-->
        <#--<a href="${childPrivilege.url!}">${childPrivilege.privilegeCnName}</a>-->
        <#--</li>-->
        <#--</#list>-->
        <#--</ul>-->
        <#--</div>-->
        <#--</div>-->
        <#--</div>-->

            <div class="card">
                <div class="card-header" role="tab" id="heading${privilege_index}">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${privilege_index}"
                       aria-expanded="false"
                       aria-controls="collapse${privilege_index}">
                    ${privilege.privilegeCnName}
                    </a>
                </div>
                <div id="collapse${privilege_index}"
                     class="card-block collapse <#if _isParent(privilege, targetPrivilege)>in</#if>"
                     role="tabpanel"
                     aria-labelledby="heading${privilege_index}">
                    <ul class="nav nav-pills nav-stacked">
                        <#if (privilege.childPrivileges?size < 1)>
                            <li class="nav-item">敬请期待</li>
                        </#if>
                        <#list privilege.childPrivileges as childPrivilege>
                            <li class="nav-item">
                                <a class="nav-link <#if childPrivilege.privilegeId == currentPrivilege>active</#if>"
                                   href="${childPrivilege.url!}">${childPrivilege.privilegeCnName}</a>
                            </li>
                        </#list>
                    </ul>

                </div>
            </div>

        </#if>
    </#list>
</div>
</#macro>

<#-- 返回权限树中的选中的权限 -->
<#function _getCurrentPrivilege privileges privilegeId>
    <#list privileges as privilege>
        <#if privilege.privilegeId == privilegeId>
            <#return privilege>
        </#if>
        <#if privilege.childPrivileges??>
            <#local targetPrivilege = _getCurrentPrivilege(privilege.childPrivileges, privilegeId)>
            <#if targetPrivilege.privilegeId??>
                <#return targetPrivilege>
            </#if>
        </#if>
    </#list>

    <#return {}>
</#function>

<#-- 判断 privilege 是否是 targetPrivilege 的父亲 -->
<#function _isParent privilege, targetPrivilege>
    <#if targetPrivilege.parent??>
        <#if targetPrivilege.parent.privilegeId == privilege.privilegeId>
            <#return true>
        </#if>
    </#if>

    <#return false>
</#function>
