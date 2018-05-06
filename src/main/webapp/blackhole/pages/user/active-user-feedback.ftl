<#--
激活用户

@author gaohui
@date 2012-11-30
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>帐号激活 - 银河</title>
<#include "../_common/common-css.ftl">

</head>
<body>

<!-- 导航栏 -->
<#include "../_common/header-not-logined-for-galaxy.ftl">

<div id="gcontent" class="container">
    <div class="row m-t-20">
    <#-- 如果激活成功 -->
    <#if _success>
        <div class="span12">
            <div class="alert alert-success">
                <h3>${_message}</h3>
            </div>
        <#-- 传邮箱到登录页面, 这样就少输一次邮箱 -->
            <a class="btn btn-large" href="login.action?username=${user.email}">登录</a>
        </div>
    <#else>
    <#-- 链接过期 -->
        <div class="span12 alert alert-error">
            <h3>${_message}</h3>
        </div>
    </#if>
    </div>
</div>

<!-- 页面底部 -->
<#include "../_common/footer.ftl">
<#--公共JS-->
<#include "../_common/common-js.ftl">
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
