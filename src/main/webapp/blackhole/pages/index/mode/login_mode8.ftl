<#--
登录模版   mode0  系统默认
chen.yaofei
2016.09.19
-->

<!DOCTYPE html>
<html>
<head>
<#include "../../_common/common-head.ftl">
    <title>${locale.getStr("blackhole.login.loginTitle")}</title>
    <link rel="stylesheet" href="../common/css/base.css">
    <link rel="stylesheet" href="../blackhole/css/base.css">
    <link rel="stylesheet" href="../assets/font-awesome/3.2.1/css/font-awesome.min.css">
    <link rel="stylesheet" href="../assets/bootstrap/3.1.1/bootstrap.min.css">
    <style type="text/css">
        .bg {
            background:url("images/mode/mode8/${localeUI.getStr("app.login.background-image")}") no-repeat;
            background-size:100% 100%;
            width: 100%;
            height: 100%;
            overflow: auto;
        }
        .m-l-45{
            margin-left: 45px;
        }

        .m-r-45{
            margin-right: 45px;
        }

        .has-feedback-left{position:relative}.has-feedback-left .form-control{padding-left:35px}
        .form-control-head{
            position:absolute;
            display:block;
            width:20px;
            height:20px;
            line-height:20px;
            text-align:center;
            margin-top: 6px;
            margin-left: 5px;
        }
        .footer-style{
            position:absolute;
            bottom: 0px;
            margin-bottom: 50px;
            text-align: center;
        }
        .footer-font-color{
           color:  #969595;
        }
    </style>
</head>
<body>

<script type="text/javascript" src="../assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="../assets/bootstrap/2.3.1/js/bootstrap.min.js"></script>

<#-- 页面底部二维码 -->
<script type="text/javascript" src="../assets/jquery-qrcode/0.7.0/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="../common/js/footer.js"></script>

<script type="text/javascript">
    $(function () {
    <#--//刷新验证码-->
        (function () {
            var $verifyCodeImage = $("#verifyCodeImage");
            $("#verifyCodeImage,#refreshVerifyCodeImageButton").click(function () {
                $verifyCodeImage.attr("src", "verifyCode.action?name=login&t=" + new Date().getTime());
                return false;
            });
        })();
    //    登录栏背景高度调整
        var $addMessageHeight = $("#addMessageHeight").val();
        var $addHeightVal = $("#addHeight").val();
        if("add" == $addHeightVal){
            $("#loginDiv").css("height","350px");
        }else if("add" == $addMessageHeight){
            $("#loginDiv").css("height","300px");
        }else{
            $("#loginDiv").css("height","270px");
        }
        var divHeight = $(document).height();
        $("#documentId").css("height",divHeight);
    });
</script>
    <div id="documentId" class="bg">
    <#--国际化    -->
                <div class="container">
                    <div style="padding-top:10px;font-size: 15px;">
                        <div style="color:white;float: right;">
                            <#if locale.getLanguage()=='zh_CN'>
                                <a href="setting.action?language=en_US" style="color: white">Switch to English</a>
                            <#else>
                                <a href="setting.action?language=zh_CN" style="color: white">切换为中文</a>
                            </#if>
                        </div>
                    </div>
                </div>
    <#--标题    -->
        <div style="margin-top: 50px; padding-bottom: 20px; font-size: 40px;color: ${localeUI.getStr("app.login.title-color")};text-align: center">
            <b>${localeUI.getStr("app.name")}</b>
        </div>

    <#--内容    -->
        <div class="container-fluid" style="margin-top: 120px">
            <div class="col-sm-10 col-md-offset-1">
                <div class="row">
                <#--图文-->
                    <div class="col-sm-7">
                        <#--<div class="row">-->
                            <#--<div class="col-sm-11">-->
                                <#--<div class="row">-->
                                        <#--<img src="images/mode/mode8/${localeUI.getStr("app.login.showPicture")}" style="width: 100%;height: 100%">-->
                                <#--</div>-->
                            <#--</div>-->
                        <#--</div>-->
                    </div>
                    <div class="col-sm-5">
                        <div class="row" style="height: 350px;">
                            <div class="col-sm-10 col-md-offset-2" id="loginDiv" style="width:350px;background: rgba(0,0,0,0.4);margin-top: -30px">
                            <#--登录栏-->
                                <div class="row">
                                    <form class="form-inline" action="doLogin.action" method="post" style="padding-top: 50px">
                                        <div class="has-feedback-left form-group" style="font-size: 20px;margin-left: 25px;margin-left: 45px">
                                            <i class="icon-envelope form-control-head"
                                               style="color: #8968CD;"></i>
                                            <input class="form-control" style="width: 260px" type="text" placeholder="${locale.getStr("blackhole.login.mailbox")}"
                                                   id="usernameInput" name="username">
                                        </div>

                                        <div class="has-feedback-left form-group" style="font-size: 20px;margin-left: 45px;margin-top: 15px;">
                                            <i class="icon-lock form-control-head"
                                               style="color: #8968CD;"></i>
                                            <input class="form-control" style="width: 260px" type="password" placeholder="${locale.getStr("password")}"
                                                   id="passwordInput" name="password">
                                        </div>

                                    <#--判断是否需要验证码-->
                                    <#if (Session["blackhole:verifyCode:login"])??>
                                        <input type="hidden" id="addHeight" value="add">
                                        <div class="m-l-45" style="margin-top: 15px">
                                            <div class="control">
                                                <input type="text" class="form-control" style="width: 100px;" name="verifyCode" placeholder="${locale.getStr("blackhole.login.verificationCode")}">
                                                <img id="verifyCodeImage" src="verifyCode.action?name=login&t=${.now?time}"
                                                     title="${locale.getStr("blackhole.login.clickRefresh")}">
                                                <a id="refreshVerifyCodeImageButton" href="javascript: void(0);">${locale.getStr("blackhole.login.noSee")}</a>
                                            </div>
                                        </div>
                                    </#if>

                                        <div style="margin-top: 30px;margin-left: 45px;">
                                            <div>
                                                <button class="btn btn-primary" style="width: 260px;margin-bottom: 10px" type="submit">${locale.getStr("blackhole.login.login")}</button>
                                            </div>
                                            <#if _message??>
                                                <input  type="hidden" id="addMessageHeight" value="add">
                                                <p style="color: #FF0000;">${_message}</p>
                                            </#if>
                                        </div>

                                        <div>
                                            <a href="forgetPassword.action" class="pull-right m-r-45"
                                                    >${locale.getStr("blackhole.login.forgetPassword")}</a>
                                            <#if anonymityLoginEnable>
                                                <a href="doLogin.action?username=guest&password=12345678" class="m-l-45"
                                                        >${locale.getStr("blackhole.login.visitorLogin")}</a>
                                            </#if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <#--页脚-->
            <div id="footer" class="yahei-font footer-style col-sm-6 col-md-offset-3">
                <div class="row-fluid">
                    <div class="footer-font-color">
                        <a href="http://www.microwise-system.com" target="_blank">
                            <img data-logo src="../common/images/logo-top-small-gray-150x46.png"
                                 style=" width:80px; height:30px"></a>
                        元智系统 |
                        MicroWise System | <a href="http://products.microwise-system.com/encke2/" target="_blank">客户端下载</a> |
                        <a href="http://microwise-system.com/special.php?id=2" target="_blank" class="footer-font-color">联系我们</a> |
                        二维码访问 <img id="qrcode" style="width: 25px; height: 25px;" src="../common/images/icon-qrcode-51.png"/>
                    </div>
                </div>
            </div>
        </div>
    <div>
</body>
</html>