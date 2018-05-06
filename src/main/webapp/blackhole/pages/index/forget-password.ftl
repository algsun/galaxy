<!DOCTYPE html>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>忘记密码 - 银河</title>
<#include "../_common/common-css.ftl">
</head>
<body>

<#include "../_common/header-not-logined-for-galaxy.ftl">


<div id="gcontent" class="container">

<#if _message??>
    <div class="row m-t-20">
        <div class="span12"></div>
    </div>
</#if>
<#include "../_common/message-tooltip.ftl">

    <div class="row m-t-20">
        <div class="span12 m-t-0">
            <div class="page-header">
                <h1>重置密码</h1>
            </div>

            <form class="form-inline" action="doForgetPassword.action" method="post">
                <div class="control-group">
                    <label class="inline-block" style="width:70px;text-align: right">邮箱</label>
                    <input class="" id="usernameInput" class="input-xlarge" type="text"
                           placeholder="邮箱"
                           name="email" value="${email!}" maxlength="50">
                <#if emailLoginUrl??>
                    <a class="btn" href="${emailLoginUrl}" target="_blank"><i class="icon-envelope"></i>进入邮箱</a>
                </#if>
                </div>
                <div class="control-group">
                    <label class="inline-block" style="width: 70px; text-align: right">验证码</label>
                    <input type="text" class="input-mini" name="verifyCode" autocomplete="off" maxlength="4">
                    <img id="verifyCodeImage" src="verifyCode.action?name=forget_password&t=${.now?time}" title="点击刷新">
                    <a id="refreshVerifyCodeImageButton" href="#">看不清</a>
                </div>

                <div>
                    <button class="btn btn-primary" type="submit" style="margin-left: 70px;">发送</button>
                    <span class="muted il-blk m-h-10">|</span>
                    <a href="login.action">登录</a>
                </div>


                <p class="help-block m-t-10"></p>
            </form>
        </div>
    </div>

</div>


<#include "../_common/footer.ftl">

<#include "../_common/common-js.ftl">

<#--
登录页面图片滚动显示

@gaohui
@date 2012-11-18
-->
<script type="text/javascript">
    (function ($) {
        $(function () {
            (function () {
                //默认光标在输入框上, 如果邮箱没值光标在邮箱上；如果有值光标在密码上
                $("#usernameInput").focus();
            })();
            (function () {
                var $verifyCodeImage = $("#verifyCodeImage");
                $("#verifyCodeImage,#refreshVerifyCodeImageButton").click(function () {
                    $verifyCodeImage.attr("src", "verifyCode.action?name=forget_password&t=" + new Date().getTime());
                    return false;
                });
            })();
        });
    })(jQuery);
</script>

<#include "../_common/last-resources.ftl">
</body>
</html>