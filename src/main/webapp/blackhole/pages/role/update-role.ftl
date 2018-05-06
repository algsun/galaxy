<#--
  --<pre>
  --更新角色
  --</pre>
  --@author Wang yunlong
  --@date  12-11-28 下午1:54
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl" >
    <title>编辑角色 - 系统管理</title>
<#include "../_common/common-css.ftl" >
    <style type="text/css">
        .active .subsystem {
            background: url("images/body-bg.png");
        }
    </style>
</head>
<body>
<#assign currentTopPrivilege = "blackhole:role">
<#include "../_common/header.ftl">
<div id="gcontent" class="container">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:role:query"></@subNavs>

<#--消息-->
<#include "../_common/message-tooltip.ftl">

    <form id="role-form" class="form-horizontal" action="doUpdateRole.action" method="post">
        <input id="role-id" type="hidden" name="roleId" value="${role.id!}">
        <fieldset>
            <legend>
                <a class="go-back" href="queryRole.action" title="返回">
                    <i class="mw-icon-prev"></i>
                    编辑角色信息
                </a>
            </legend>
            <div class="row">
                <div class="span12">
                    <div class="control-group">
                        <label class="control-label"><em class="required">*</em>角色名称</label>

                        <div class="controls">

                            <input id="role-name" type="text" name="roleName" class="required"
                                   value="${role.roleName!}">
                            <span class="inline-block role-name-enable m-l-10" style="color:red;font-size: 80%"></span>
                        </div>
                    </div>
                </div>
            </div>
            <#--权限展示-->
            <#include "role-privilege-view.ftl">
            <div class="row m-t-10">
                <div class="span12">
                    <div class="control-group">
                        <div class="controls">
                            <button id="role-submit" class="btn btn-primary" type="submit">保存</button>
                            <a class="btn" href="queryRole.action">返回</a>
                        </div>
                    </div>
                </div>
            </div>
        </fieldset>
    </form>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="js/role-privilege.js"></script>
<#include "../_common/last-resources.ftl">
</body>
</html>

