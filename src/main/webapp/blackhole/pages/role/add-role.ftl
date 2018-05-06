<#--
<pre>
添加角色页面
</pre>
@author Wang yunlong
@date  12-11-28 下午1:53
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl" >
    <title>添加角色 - 系统管理</title>
<#include "../_common/common-css.ftl" >
    <style type="text/css">
            /* 去掉 tab 白色背景 */
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
<@subNavs "blackhole:role:add"></@subNavs>

<#--消息-->
<#include "../_common/message-tooltip.ftl">

    <form id="role-form" class="form-horizontal" action="doAddRole.action" method="post">
        <fieldset>
            <legend>
                添加角色
            </legend>
            <div class="row">
                <div class="span12">
                    <div class="control-group">
                        <label class="control-label"><em class="required">*</em>角色名称</label>

                        <div class="controls">
                            <input id="role-name" type="text" name="roleName" class="required" value="${roleName!}">
                            <span class="inline-block role-name-enable m-l-10 red" style="font-size: 80%"></span>
                        </div>
                    </div>
                </div>
            </div>
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

