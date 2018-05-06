<#--
  --<pre>
  --角色信息
  --</pre>
  --@author Wang yunlong
  --@date  12-11-28 下午1:53
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl" >
    <title>角色信息 - 系统管理</title>
<#include "../_common/common-css.ftl" >
    <#--<style type="text/css">-->
        <#--.active .subsystem {-->
            <#--background: url("images/body-bg-1.png");-->
        <#--}-->
    <#--</style>-->
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
    <fieldset>
        <legend>
            <a class="go-back" href="queryRole.action" title="返回">
                <i class="mw-icon-prev"></i>角色信息</a>
        </legend>
        <div class="row">
            <div class="span12">
                <div class="control-group">
                    <label class="m-l-50 inline-block gray">角色名称:</label>

                    <div class="inline-block">
                        <label class="inline-block" style="padding-top:7px;font-size: 120%;font-weight: bold;"
                                >${role.roleName!}</label>
                    </div>
                <#if security.isPermitted("blackhole:role:update")>
                    <a class="inline-block f-r btn btn-success btn-mini"
                       href="updateRole.action?roleId=${role.id!}">
                        <i class="icon-pencil icon-white"></i>编辑
                    </a>
                </#if>
                </div>
            </div>
        </div>
    <#include "role-privilege-view.ftl">
    </fieldset>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript">
    $(function () {
        //在角色信息查看页面将角色拥有的角色凸出显示
        $("body").find("input").attr("disabled", true);
        var $checkedBox = $("body").find("input[checked!='checked']");
        $checkedBox.parent().attr("style", "text-decoration:line-through;");
    });
</script>
<#include "../_common/last-resources.ftl">
</body>
</html>