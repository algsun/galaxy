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
    <link rel="stylesheet" type="text/css" href="css/mode/login7.css"/>
</head>
<body>
<#include "../../_common/header-not-logined-for-galaxy.ftl">
<div class="container-fluid">
    <div class="row-fluid" style="margin-top: 100px;">
        <div class="span5 offset1" style="width: 600px;">
            <div class="row-fluid">
                <div class="span12">
                    <img src="images/mode/mode7/site-name.png" alt=""/>
                </div>
            </div>
            <div class="row-fluid">
                <div class="span12">
                    <img src="images/mode/mode7/site-img.png" class="m-l-50" style="max-width:650px;max-height: 400px; "
                         alt=""/>
                </div>
            </div>
        </div>
        <div class="span4 offset1 login-div" id="login_div" style="width: 400px;">
            <form class="form-horizontal" id="login_form" style="color: #ffffff;margin-top: 50px;margin-bottom: 190px;"
                  action="doLogin.action" method="post">
                <div class="control-group">
                    <label class="control-label" style="width: 50px;color: #5c4920">

                    </label>

                    <div class="controls" style="margin-left:180px;">
                        <span style="font-size: 24px;color:#5c4920;font-weight: bold;"> 登录 </span>
                    </div>
                </div>
                <div class="control-group m-l-20">
                    <label class="control-label" style="width: 50px;color: #5c4920">
                        用户名
                    </label>

                    <div class="controls" style="margin-left:70px;">
                        <input id="usernameInput" class="span4" style="width: 83%" type="text" placeholder="邮箱"
                               name="username"
                               value="${username!}" max="50">
                    </div>
                </div>

                <div class="control-group m-l-20">
                    <label class="control-label" style="width: 50px;color: #5c4920">
                        密码
                    </label>

                    <div class="controls" style="margin-left:70px;">
                        <input id="passwordInput" class="span4" style="width: 83%" type="password" name="password">
                    </div>
                </div>

            <#--判断是否需要验证码-->
            <#if (Session["blackhole:verifyCode:login"])??>
                <div class="control-group m-l-20">
                    <label class="control-label" style="width: 50px;color: #5c4920;">验证码</label>

                    <div class="control" style="margin-left: 70px;">
                        <input type="text" class="input-mini" autocomplete="off" name="verifyCode">
                        <img id="verifyCodeImage" src="verifyCode.action?name=login&t=${.now?time}" title="点击刷新">
                        <a id="refreshVerifyCodeImageButton" href="#" style="color: #5c4920">看不清</a>
                    </div>
                </div>
            </#if>
                <div class="control-group m-l-20">
                    <div class="controls" style="margin-left:70px;">
                    <#--<button class="btn btn-primary" style="width: 80%;"/>-->
                        <button class="btn btn-primary"style="width: 83%;"  type="submit">登录</button>
                    <#--background-image: url('images/mode/mode6/login-btn.png');height: 47px;" type="submit" >xx</button>-->
                    </div>
                </div>

                <div class="control-group m-l-20">
                    <div class="controls" style="margin-left:70px;">
                    <#if anonymityLoginEnable>
                        <a href="doLogin.action?username=guest&password=12345678"
                           class="btn">访客登录</a>
                    </#if>
                        <a class="il-blk" style="color: #5c4920" href="forgetPassword.action">忘记密码?</a>
                    <#if _message??>
                        <p class="help-block red m-t-10">${_message}</p>
                    </#if>
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>


<#include "footer6.ftl">
<@scriptTag "../common/js/footer6.js"/>
<#include "../../_common/common-js.ftl">
<#--
登录页面图片滚动显示

@gaohui
@date 2012-11-18
-->
<script type="text/javascript">

    var sumbitFun = function () {
        $("#login_form").submit();
    };

    $(function () {
        $('#footer img[data-logo]').mouseenter(function () {
            $(this).attr('src', '../common/images/logo-top-150x46.png')
        }).mouseleave(function () {
                    $(this).attr('src', '../common/images/logo-top-150x46.png')
                });

    });

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




