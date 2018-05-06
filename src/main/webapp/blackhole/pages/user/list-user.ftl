<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>用户查询 - 系统管理</title>
<#include "../_common/common-css.ftl">
</head>

<body>

<!-- 导航栏 -->
<#assign currentTopPrivilege="blackhole:user">
<#include "../_common/header.ftl">

<div id="gcontent" class="container">

<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:user:query"></@subNavs>

<#include "../_common/message-tooltip.ftl">
    <div class="row">
        <div class="span12">
            <form class="well well-small form-inline" action="queryUser.action" method="post">
                <label for="userName">姓名</label>
                <input type="text" name="userName" id="userName" value="${userName!}">
                <button type="submit" class="btn">查询</button>
            </form>
        </div>
    </div>

<#if (pageCount>0)>
    <div class="row">
        <div class="span12">

            <table class="table">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>邮箱</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#list userList as user>
                    <tr>
                        <td>${user_index+1}</td>
                    <#--混淆邮箱-->
                        <td>
                            <#if customize == "1">
                                ${user.email}
                            <#else>
                                ${blackhole.obscuredEmail(user.email)}
                            </#if>
                        </td>
                        <td>${user.userName!}</td>
                        <td>
                            <#if user.sex==2>
                                男
                            <#elseif user.sex==1>
                                女
                            </#if>
                        </td>
                        <td>
                            <#if user.active>
                                <#if user.disable>
                                    <i class="icon-minus-sign"></i>
                                    不可用
                                <#else>
                                    <i class="icon-ok-sign"></i>
                                    可用
                                </#if>
                            <#else>
                                <i class="icon-question-sign"></i>
                                未激活
                            </#if>
                        </td>
                        <td>
                            <#if user.id==currentUser.id>
                                当前用户
                            <#else>
                            <#--当前用户是否拥有分配角色权限-->
                                <#if security.isPermitted("blackhole:user:role")>
                                    <a class="btn btn-mini btn-success" href="assignRole.action?userId=${user.id}&name=${userName!}&index=${index}">
                                        分配角色
                                    </a>
                                </#if>

                            <#--当前用户是否拥有分配站点组权限-->
                                <#if security.isUserPermitted("blackhole:user:logicGroup")>
                                <#-- 当前站点是否用户归属站点的直接子站点 -->
                                    <#if Session.subLogicGroupOfUserLogicGroup??>
                                        <#list Session.subLogicGroupOfUserLogicGroup as logicGroup>
                                            <#if logicGroup.id==Session.currentLogicGroup.id>

                                                <a btn-assignLogicGroup class="btn btn-mini btn-success"
                                                   data-userId="${user.id}">
                                                    站点组
                                                </a>
                                            <#else>
                                            </#if>
                                        </#list>
                                    <#else >
                                    </#if>
                                </#if>

                                <#if user.active>
                                <#--用户是否拥有禁用用户权限-->
                                    <#if security.isPermitted("blackhole:user:disable") && Session.currentLogicGroupManager.id!=user.id>
                                        <#if user.disable>
                                            <a class="btn btn-mini btn-info"
                                               href="updateUserState.action?userId=${user.id}">
                                                <i class="icon-ok-circle icon-white"></i>
                                                启用
                                            </a>
                                        <#else>
                                            <a class="btn btn-mini btn-warning"
                                               href="updateUserState.action?userId=${user.id}">
                                                <i class="icon-ban-circle icon-white"></i>
                                                禁用
                                            </a>
                                        </#if>
                                    </#if>
                                <#else>
                                <#--用户是否拥有删除用户权限-->
                                    <#if security.isPermitted("blackhole:user:delete") && Session.currentLogicGroupManager.id!=user.id >
                                        <a btnDelete data-userId="${user.id}" class="btn btn-mini btn-danger">
                                            <i class="icon-trash icon-white"></i>
                                            删除
                                        </a>
                                    </#if>
                                </#if>
                            </#if>
                            <#--判断用户是否有编辑部门的权限-->
                            <#if security.isPermitted("blackhole:user:updateDepartment") && Session.currentLogicGroupManager.id!=user.id>
                                <a class="btn btn-mini" href="toUpdateUser.action?userId=${user.id}&index=${index}"title="编辑部门">
                                    <i class="icon-pencil"></i>编辑部门
                                </a>
                            </#if>

                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
</#if>

    <div class="hide">
        <div id="assignLogicGroup" class="span4">
            <div id="tree" class="ztree"></div>
            <span class="help-block red m-l-20"></span>
        </div>
    </div>

<#include "../_common/pagging.ftl" >
<#assign url="queryUser.action?userName=${userName!}">
<@pagging url index pageCount></@pagging>
</div>


<!-- 页面底部 -->
<#include "../_common/footer.ftl">

<#--公共JS-->
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="js/user-assignLogicGroup.js"></script>


<script type="text/javascript">
    $(function () {

        // 删除用户
        $(document).on("click", "a[btnDelete]", function () {
            var userId = $(this).attr("data-userId");
            art.dialog({
                id: "delete",
                fixed: true,
                title: "删除确认",
                content: "确定要删除当前用户？",
                okValue: "确定",
                ok: function () {
                    window.location.href = "deleteUser.action?userId=" + userId;
                },
                cancelValue: "取消",
                cancel: function () {
                }
            });
        });
    });
</script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
