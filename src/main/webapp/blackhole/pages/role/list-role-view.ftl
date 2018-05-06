<#--
角色列表部分

@author Wang yunlong
@date  12-11-28 下午1:53
-->

<div class="row">
    <div class="span12">
        <form class="well well-small form-inline" action="" method="post">
            <label>角色名称</label>
            <input type="text" name="roleName" value="${roleName!}">
            <button type="submit" class="btn">查询</button>
        </form>
    </div>
</div>

<#--无结果-->
<#if (count > 0)>
<div class="row">
    <div class="span12">
        <table class="table">
            <thead>
            <tr>
                <th>序号</th>
                <th>角色名称</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                <#list roles as role>
                <tr>
                    <td>${role_index+1}</td>
                    <td>${role.roleName!}</td>
                    <td>
                        <#if !role.manager>
                            <#if security.isPermitted("blackhole:role:info")>
                                <a class="btn btn-info btn-mini" href="roleInfo.action?roleId=${role.id!}" title="修改">
                                    详细
                                </a>
                            </#if>
                            <#if security.isPermitted("blackhole:role:update")>
                                <a class="btn btn-success btn-mini" href="updateRole.action?roleId=${role.id!}"
                                   title="修改">
                                    <i class="icon-pencil icon-white"></i>编辑
                                </a>
                            </#if>
                            <#if security.isPermitted("blackhole:role:delete")>
                                <a class="delete-role btn btn-danger btn-mini" roleId="${role.id!}" title="删除">
                                    <i class="icon-trash icon-white"></i>删除
                                </a>
                            </#if>
                        <#else>
                            <label>站点管理员</label>
                        </#if>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
</#if>
<#include "../_common/pagging.ftl">
<#--分页-->
<#assign url = "queryRole.action?roleName=${roleName!}">
<@pagging url index count></@pagging>

