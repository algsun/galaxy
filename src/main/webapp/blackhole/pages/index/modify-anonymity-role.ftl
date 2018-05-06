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
    <title>访客权限管理 - 系统管理</title>
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
<#include "../_common/header-for-superman.ftl">
<div id="gcontent" class="container">

<#--消息-->
<#include "../_common/message-tooltip.ftl">

    <form id="role-form" class="form-horizontal" action="saveAnonmityRole.action" method="post">
        <fieldset>
            <div class="row">
                <div class="span12 m-t-40">
                    <div class="control-group">
                    <#if enable>
                        <span class="m-r-30">状态：访客权限<span style="color: green">已启用</span></span>
                        <a class="btn btn-mini btn-warning"
                           href="disableAnonmity.action">
                            <i class="icon-ban-circle icon-white"></i>
                            禁用
                        </a>
                    <#else>
                        <span class="m-r-30">状态：访客权限<span style="color: red">已禁用</span></span>
                        <a class="btn btn-mini btn-info"
                           href="enableAnonmity.action">
                            <i class="icon-ok-circle icon-white"></i>
                            启用
                        </a>
                    </#if>
                    </div>
                </div>
            </div>
        <#include "anonymity-role-privilege-view.ftl">
            <div class="row m-t-10">
                <div class="span12">
                    <div class="control-group">
                        <div class="controls" id="buttons">
                            <button id="role-submit" class="btn btn-primary" type="submit">保存</button>
                            <a class="btn" href="chooseLogicGroup.action">返回</a>
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
<script type="text/javascript">
    // freemarker标签
    if (${enable?string}) {
        $("body").find("input").attr("disabled", false);
        $("#buttons").show();
    } else {
        $("body").find("input").attr("disabled", true);
        $("#buttons").hide();
    }
</script>
</body>
</html>

