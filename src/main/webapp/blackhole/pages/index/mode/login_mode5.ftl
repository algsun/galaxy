<#--
登录模版   mode0  系统默认
xu.baoji
2014.02.17
-->


<!DOCTYPE html>
<html>
<head>
<#include "../../_common/common-head.ftl">
    <title>登录 - 银河</title>
<#include "../../_common/common-css.ftl">
    <link rel="stylesheet" type="text/css" href="css/mode/login5.css"/>
</head>
<body class="bg">
<div class="loginwrap">
    <div class="loginheader"><img src="images/mode/mode5/logo.png"/></div>
</div>
<div class="login">
    <div class="info">
        <form action="doLogin.action" method="post">
            <div class="title">用户名</div>
            <input id="usernameInput" class="span4" type="text" placeholder="邮箱" name="username"
                   value="${username!}" max="50">

            <div class="title">密码</div>
        <#--<input name="" type="text"/>-->
            <input id="passwordInput" type="password" name="password">
        <#if (Session["blackhole:verifyCode:login"])??>
            <div class="m-t-10">
                <span style="float: left" class="m-t-10">验证码</span> <input style="float: left;width: 20%" type="text" autocomplete="off" name="verifyCode">
                <img style="float: left" class="m-t-5" id="verifyCodeImage" src="verifyCode.action?name=login&t=${.now?time}"
                     title="点击刷新">
                <a style="float: left;width: 20%;color: #0000ff;margin-left: -10px;" id="refreshVerifyCodeImageButton" href="#" style="color: #000000">看不清</a>
            </div>
        </#if>
            <input value="登 &nbsp; 录" name="" type="submit" class="button"/>
        <#if _message??>
            <p class="help-block red m-t-10">${_message}</p>
        </#if>
        <#--<a href="#">注册</a>-->
        </form>
    </div>
</div>
<div class="loginfooter">Copyright@ 陕西省考古研究院&nbsp; &nbsp; 技术支持：西安元智系统技术有限责任公司</div>

<#include "../../_common/common-js.ftl">
<script type="text/javascript">
    (function ($) {
        $(function () {
            (function () {
                //默认光标在输入框上, 如果邮箱没值光标在邮箱上；如果有值光标在密码上
                if ($.trim($("#usernameInput").val()).length == 0) {
                    $("#usernameInput").focus();
                } else {
                    $("#passwordInput").focus();
                }
            })();

            //刷新验证码
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
<#include "../../_common/last-resources.ftl">
</body>
</html>




