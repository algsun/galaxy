<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>分配角色 - 系统管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>

<!-- 导航栏 -->
<#include "../_common/header.ftl">
<div id="gcontent" class="container">

<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:user:query"></@subNavs>

<#include "../_common/message-tooltip.ftl">

    <div class="row">
        <div class="span12">
            <form class="form-horizontal" id="userForm" action="doAssignRole.action" method="post">
                <input type="hidden" name="name" value="${name!}">
                <input type="hidden" name="index" value="${index!}">
                <fieldset>
                    <legend>
                        <a class="go-back" href="queryUser.action?userName=${name}&index=${index}" title="返回">
                            <i class="mw-icon-prev"></i>
                            分配角色
                        </a>
                    </legend>
                    <input type="hidden" name="userId" value="${user.id!}">

                    <div class="control-group">
                        <label class="control-label">姓名</label>

                        <div class="controls">
                            <input type="text" id="userName" name="userName" value="${(user.userName)!}" disabled>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">
                            <em class="required">*</em>角色
                        </label>

                        <div class="controls">
                        <#--两个 row-fluid 的 width 写一个 -->
                            <div class="row-fluid muted" style="width:600px;">
                                <div class="span4">可用角色</div>
                                <div class="span8">已选角色</div>
                            </div>
                            <div class="row-fluid" style="width: 600px;">
                                <div class="span3">
                                    <select id="roleList"
                                            multiple="true" size="10"
                                            class="span12">
                                    <#list roleList as role>
                                        <option value="${role.id}">${role.roleName}</option>
                                    </#list>
                                    </select>
                                </div>
                                <div class="span1">
                                    <a id="addRole" class="btn btn-mini m-b-10"><i class="icon-arrow-right"></i></a>
                                    <br>
                                    <a id="removeRole" class="btn btn-mini"><i class="icon-arrow-left"></i></a>
                                </div>
                                <div class="span8">
                                    <select name="roles" id="selectedRole"
                                            multiple="true" size=10
                                            style="width: 140px;">
                                    <#list userRoleList as userRole>
                                        <option value="${userRole.id}"
                                                data-manager="${userRole.manager?string("true","false")}">${userRole.roleName}</option>
                                    </#list>
                                    </select>
                                    <span id="role-input-help" class="help-inline"></span>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="form-actions">
                        <button id="submit" type="submit" class="btn btn-primary">保存</button>
                        <a href="queryUser.action?userName=${name}&index=${index}" class="btn">返回</a>
                    </div>

                </fieldset>
            </form>
        </div>
    </div>
</div>

<#include "../_common/footer.ftl">
<#--公共JS-->
<#include "../_common/common-js.ftl">


<!--表单验证-->
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="../blackhole/js/user-validate.js"></script>

<script type="text/javascript">
    $(function () {
        var $roleList = $("#roleList");
        var $selectedRole = $("#selectedRole");

        $("#addRole").click(function () {
            $roleList.children("option:selected").each(function () {
                $selectedRole.append($(this));
            })
        });

        $("#removeRole").click(function () {
            $selectedRole.children("option:selected").each(function () {
                if ($(this).attr("data-manager") != "true") {
                    $roleList.append($(this));
                }
            })
        });

        $("#submit").click(function () {
            $selectedRole.children("option").each(function () {
                $(this).attr("selected", true);
            })
        });
    });
</script>

<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
