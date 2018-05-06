<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>修改用户信息 - 系统管理</title>
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
<@subNavs "blackhole:user:query"></@subNavs>

<#include "../_common/message-tooltip.ftl">

    <div class="row">
        <div class="span12">
            <form class="form-horizontal" id="userForm" action="doUpdateUser.action?index=${index}" method="post">
                <fieldset>
                    <legend>
                        <a class="go-back" href="queryUser.action?index=${index!}" title="返回">
                            <i class="mw-icon-prev"></i>
                            修改部门
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
                            所属部门
                        </label>
                        <div class="controls">
                            <select class="easyui-combobox"  id="myCombobox" name="departmentName" style="width:200px;">
                                <option value="">无</option>
                                <#list departments as department>
                                    <option value="${department.name}"<#if (user.department)??><#if user.department.id == department.id>selected="selected" </#if> </#if> >${department.name}</option>
                                </#list>
                            </select>
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
<#include "../_common/last-resources.ftl">
<#--设置combox的属性-->
<script type="text/javascript">
    $('#myCombobox').combobox({
        width:220,
        height:30,
        listHeight:150
    });
</script>
</body>
</html>
