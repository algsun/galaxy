<#--
<pre>
权限展示 递归遍历
</pre>
@author Wang yunlong
@date  12-11-23 下午3:52
-->

<#-- 展示权限树 -->
<#macro openPrivileges privileges>
    <#list privileges as privilege>
        <#if privilege.children?size<1 >
            <@hasNoChildren privilege></@hasNoChildren>
        <#else>
            <@hasChildren privilege></@hasChildren>
        </#if>
    </#list>
</#macro>
<#--当权限有子节点时处理-->
<#macro hasChildren privilege>
<div class="accordion-group">
    <div class="accordion-heading">
        <a class="accordion-toggle" style="text-decoration: none;" data-toggle="collapse"
           data-parent="#accordion2" href="#${privilege.name!}">
        ${privilege.name!}
        </a>
    </div>
    <div class="accordion-body collapse in">
        <div class="accordion-inner">
            <div class="inline-block level-1" style="vertical-align: top;">
                <#if security.isPermitted(privilege.id)>
                    <label class=" inline-block m-l-10 m-r-10 bold">
                        <input
                            <#if privilege.belongCurrentRole>checked="checked"</#if>
                            type="checkbox" name="privileges"
                            value="${privilege.id!}" style="margin-right: 2px;margin-top: -3px;"<#if privilege.disable>
                            disabled="disabled" </#if>/>
                        <span
                            <#if privilege.disable>style="text-decoration: line-through" </#if>>${privilege.name!}</span>
                    </label>
                </#if>
            </div>

            <div class="inline-block m-l-10 level-2">
                <@openPrivileges privilege.children></@openPrivileges>
            </div>
        </div>
    </div>
</div>
</#macro>

<#--权限没有子节点处理-->
<#macro hasNoChildren child>
    <#if security.isPermitted(child.id)>
        <#if child.id != 'blackhole:currentLogicGroup:logicGroupSubsystem' && child.id!='blackhole:currentLogicGroup:privilegeOperate'>
            <#assign isBaseSite = Session["currentLogicGroup"].site??>
            <#if child.id != 'blackhole:subsystem:biela'>
            <label class="inline-block m-l-10 m-r-10">
                <input <#if child.belongCurrentRole>checked="checked"</#if>
                       type="checkbox" name="privileges"
                       value="${child.id!}" <#if child.disable>disabled="disabled" </#if> style="margin-right: 2px;margin-top: -3px;">
                <span <#if child.disable>style="text-decoration: line-through" </#if>>${child.name!}</span>
            </label>

            <#else>
                <#if !(isBaseSite)>
                <label class="inline-block m-l-10 m-r-10">
                    <input <#if child.belongCurrentRole>checked="checked"</#if>
                           type="checkbox" name="privileges"
                           value="${child.id!}" <#if child.disable>disabled="disabled" </#if>
                           style="margin-right: 2px;margin-top: -3px;">
                    <span <#if child.disable>style="text-decoration: line-through" </#if>>${child.name!}</span>
                </label>
                </#if>
            </#if>

        </label>
        </#if>
    </#if>
</#macro>
