<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>添加用户 - 系统管理</title>
<#include "../_common/common-css.ftl">
    <link rel="stylesheet" type="text/css" href="../assets/jquery-easyui/1.3.4/themes/default/easyui.css">
    <style type="text/css">
        .combo {
            border-color: #ccc;
        }
    </style>
</head>
<body>

<!-- 导航栏 -->
<#assign currentTopPrivilege="blackhole:user">
<#include "../_common/header.ftl">
<div id="gcontent" class="container">

<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:user:add"></@subNavs>
<#include "../_common/message-tooltip.ftl">

    <div class="row">
        <div class="span12">
            <form class="form-horizontal" id="userForm" action="doAddUser.action" method="post">
                <fieldset>
                    <legend>
                        添加用户
                    </legend>
                    <div class="control-group">
                        <label class="control-label" for="email">
                            <em class="required">*</em>邮箱
                        </label>

                        <div class="controls">
                            <input type="text" id="email" name="email" placeholder="邮箱" value="${email!}"
                                   maxlength="50">
                            <span id="email-input-help" class="help-inline"></span>

                            <p class="help-block">
                                用户一旦添加，邮箱不能改变
                            </p>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="userName">
                            <em class="required">*</em>姓名
                        </label>

                        <div class="controls">
                            <input type="text" id="userName" name="userName" value="${userName!}">
                            <span id="userName-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">
                            性别
                        </label>

                        <div class="controls">
                            <label class="radio inline">
                                <input type="radio" name="sex" id="male" value="2" checked>男
                            </label>
                            <label class="radio inline">
                                <input type="radio" name="sex" id="female" value="1">女
                            </label>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">
                            所属部门
                        </label>

                        <div class="controls">

                            <select class="easyui-combobox" id="myCombobox" name="departmentName" style="width:200px;">
                                <option value="">无</option>
                            <#list departments as department>
                                <option value="${department.name}">${department.name}</option>
                            </#list>
                            </select>
                        </div>
                    </div>
                <#-- 添加新选项，是否发送邮箱验证邮件，若为否，则只发送通知邮件   add by wanggeng 2013-05-06 -->
                    <div class="control-group">
                        <div class="controls">
                            <label class="radio inline">
                                <input type="radio" name="SendValidationEmail" id="validation" value="true" checked>发送验证邮件
                            </label>
                            <label class="radio inline">
                                <input type="radio" name="SendValidationEmail" id="notification" value="false">仅发送通知邮件
                            </label>

                            <div class="help-block m-t-5">
                                <em class="required">*</em>注意:仅发送通知邮件不确保密码安全，建议发送验证邮箱进行激活
                            </div>
                        </div>
                    </div>
                <#-- added end -->
                    <div class="control-group">
                        <label class="control-label" for="phone">
                            手机号码
                        </label>

                        <div class="controls">
                            <input type="text" id="phone" name="mobile" placeholder="">
                            <span id="phone-input-help" class="help-inline"></span>
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
                                    <div class="m-b-10">
                                        <a id="addRole" class="btn btn-mini"><i class="icon-arrow-right"></i></a>
                                    </div>

                                    <div>
                                        <a id="removeRole" class="btn btn-mini"><i class="icon-arrow-left"></i></a>
                                    </div>
                                </div>
                                <div class="span8">
                                    <select name="roles" id="selectedRole"
                                            multiple="true" size=10
                                            style="width: 140px;">
                                    </select>
                                    <span id="role-input-help" class="help-inline"></span>
                                </div>


                            </div>

                        </div>
                    </div>


                    <div class="form-actions">
                        <button id="submit" type="submit" class="btn btn-primary">保存</button>
                        <a href="queryUser.action" class="btn">返回</a>
                    </div>

                </fieldset>
            </form>
        </div>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<script type="text/javascript" src="../assets/jquery-easyui/1.3.4/jquery.easyui.min.js"></script>

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
                $roleList.append($(this));
            })
        });

        $("#submit").click(function () {
            $selectedRole.children("option").each(function () {
                $(this).attr("selected", true);
            })
        });
    });

</script>
<#include "../_common/last-resources.ftl">
<script type="text/javascript" src="../assets/jquery-easyui/1.3.4/jquery.easyui.min.js"></script>
<#--设置combox的属性-->
<script type="text/javascript">
    $('#myCombobox').combobox({
        width: 220,
        height: 30,
        listHeight: 150
    });
</script>
</body>
</html>
