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

    <@linkTag "css/mode/mode1.css"/>

</head>
<body>
<#include "../../_common/header-not-logined-for-galaxy.ftl">
<div id="gcontent" class="container">
    <div class="row">
        <div class="span6 m-t-50 mode1_log" style="height: 400px;width:460px;">
            <div class=" m-t-10 m-l-10">
                <h2 class="m-v-0 f-n yahei-font">用户登录</h2>
            </div>

            <form class="form-horizontal" action="doLogin.action" method="post">
                <div class="m-t-40 m-l-20">
                    <label style="width: 50px;color:#ffffff"> 用户名</label>
                    <input id="usernameInput" class="span4 input_bg" type="text" placeholder="邮箱" name="username"
                           value="${username!}" max="60" style="height: 30px;color:#ffffff;border: 0px;">
                </div>

                <div class="m-t-10 m-l-20">
                    <label class="" style="width: 50px;color:#ffffff">密码</label>
                    <input id="passwordInput" class="span4 input_bg" type="password" name="password"
                           style="height: 30px;color:#ffffff;border: 0px;">
                </div>

            <#--判断是否需要验证码-->
            <#if (Session["blackhole:verifyCode:login"])??>
                <div class="m-t-10 m-l-20">
                    <label style="width: 50px;color:#ffffff">验证码</label>

                    <input type="text" class="input-mini input_bg" autocomplete="off" name="verifyCode"
                           style="color:#ffffff;border: 0px;">
                    <img id="verifyCodeImage" src="verifyCode.action?name=login&t=${.now?time}" title="点击刷新">
                    <a id="refreshVerifyCodeImageButton" href="#" style="width: 50px;color:#ffffff">看不清</a>
                </div>
            </#if>

                <div class="m-t-30 m-l-20">
                    <div class="">
                        <button class=" btn btn-primary" type="submit">
                            登录
                        </button>
                    <#if anonymityLoginEnable>
                        <a href="doLogin.action?username=guest&password=12345678"
                           class="btn btn-mini" style="margin-left: 100px">访客登录</a>
                    </#if>
                        <a class="il-blk m-l-10" href="forgetPassword.action" style="color:#ffffff;">忘记密码?</a>
                    <#if _message??>
                        <p class="help-block red m-t-10">${_message}</p>
                    </#if>
                    </div>
                </div>
            </form>

        </div>

        <div class=" span6 m-t-50 image_bg" style="margin-left: 0px">
            <div id="myCarousel" class="carousel slide" style="box-shadow: 1px 1px 10px #707070;">
                <div class="carousel-inner">
                <#list localeUI.getStrs("app.login.gallery") as image>
                    <div class="item <#if image_index == 0>active</#if>">
                        <img style="height: 360px;width:420px" src="images/login/${image}" class="m-t-20 m-l-20">
                    </div>
                </#list>

                </div>
            <#-- 调低左右切换按钮位置 -->
                <a class="left carousel-control" href="#myCarousel" data-slide="prev" style="top:90%;">‹</a>
                <a class="right carousel-control" href="#myCarousel" data-slide="next" style="top:90%;">›</a>
            </div>
        </div>
    </div>
    <div class="m-t-30 m-b-10" style="overflow: hidden;">
        <h4 class="m-v-0 f-n yahei-font f-l">新闻</h4><a class="f-r gray-b" style="font-size:1.0em"
                                                       href="listPosts.action?scope=1">更多...</a>
    </div>
    <div id="template">
    </div>
<#if localeUI.contains("app.login.links") && localeUI.getBool("app.login.links")>
    <#include "../login/shangbo-links.ftl">
</#if>
</div>


<#include "../../_common/footer.ftl">
<#include "../../_common/common-js.ftl">


<script type="text/javascript" src="../assets/underscore/1.4.2/underscore-min.js"></script>
<#--
登录页面图片滚动显示

@gaohui
@date 2012-11-18
-->
<script type="text/javascript">
    (function ($) {
        $(function () {
        <#-- 幻灯片切换 -->
            $("#myCarousel").carousel();

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

            // 加载新闻
            (function () {
                $.post("publicPost.action?scope=1&max=2", function (posts) {
                    $.each(posts, function (i, post) {
                        // post.createDate => "2013-05-09T12:34:56"
                        post.dateShort = post.createDate.substr(0, post.createDate.indexOf("T"));
                        post.dateLong = post.createDate.replace("T", " ");
                    });
                    var template = $("#t2").html();
                    $("#template").html(_.template(template, {posts: posts}));
                });
            })();
        });
    })(jQuery);
</script>
<script id="t2" type="text/template">
    <ul style="list-style-type:circle;">
        <% _.each(posts, function(item){ %>
        <li class="m-t-5">
            <a href="viewPost.action?id=<%= item.id %>&scope=1" class="elippsis"
               style="width:280px; display:inline-block;" title="<%= item.title %>"><%= item.title %></a>
            <span class="f-r gray-b" style="font-size:0.8em" title="<%= item.dateLong %>"><%= item.dateShort %></span>
        </li>
        <% }); %>
    </ul>
</script>
<#include "../../_common/last-resources.ftl">
</body>
</html>







