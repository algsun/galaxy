<#--
登录模版   mode0  系统默认
xu.baoji
2014.02.17
-->


<!DOCTYPE html>
<html>
<head>
<#include "../../_common/common-head.ftl">
    <title>${locale.getStr("blackhole.login.loginTitle")}</title>
<#include "../../_common/common-css.ftl">
</head>
<body>
<#include "../../_common/header-not-logined-for-galaxy.ftl">
<div id="gcontent" class="container">
    <div class="row">
        <div class="span6 m-t-50">

            <div id="myCarousel" class="carousel slide" style="box-shadow: 1px 1px 10px #707070;">
                <div class="carousel-inner">
                <#--
                <div class="item active">
                    <img style="height: 400px;width:460px" src="images/login/blueplanet.png">
                </div>
                <div class="item">
                    <img style="height: 400px;width:460px" src="images/login/proxima02.jpg">
                </div>
                <div class="item">
                    <img style="height: 400px;width:460px" src="images/login/orion02.jpg">
                </div>
                <div class="item">
                    <img style="height: 400px;width:460px" src="images/login/uma.png">
                </div>
                -->

                <#list localeUI.getStrs("app.login.gallery") as image>
                    <div class="item <#if image_index == 0>active</#if>">
                        <img style="height: 400px;width:460px" src="images/login/${image}">
                    </div>
                </#list>

                </div>
            <#-- 调低左右切换按钮位置 -->
                <a class="left carousel-control" href="#myCarousel" data-slide="prev" style="top:90%;">‹</a>
                <a class="right carousel-control" href="#myCarousel" data-slide="next" style="top:90%;">›</a>
            </div>
        </div>
        <div class="offset1 span5 m-t-50">
            <div class="page-header m-t-0 m-b-10">
                <h4 class="m-v-0 f-n yahei-font">${locale.getStr("blackhole.login.login")}</h4>
            </div>

            <form class="form-horizontal" action="doLogin.action" method="post">
                <div class="control-group">
                    <label class="control-label" style="width: 50px">${locale.getStr("userName")}</label>

                    <div class="controls" style="margin-left:70px;">
                        <input id="usernameInput" class="span4" type="text" placeholder="${locale.getStr("blackhole.login.mailbox")}" name="username"
                               value="${username!}" max="50">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" style="width: 50px">${locale.getStr("password")}</label>

                    <div class="controls" style="margin-left:70px;">
                        <input id="passwordInput" class="span4" type="password" name="password">
                    </div>
                </div>

            <#--判断是否需要验证码-->
            <#if (Session["blackhole:verifyCode:login"])??>
                <div class="control-group">
                    <label class="control-label" style="width: 50px;">${locale.getStr("blackhole.login.verificationCode")}</label>

                    <div class="control" style="margin-left: 70px;">
                        <input type="text" class="input-mini" autocomplete="off" name="verifyCode">
                        <img id="verifyCodeImage" src="verifyCode.action?name=login&t=${.now?time}" title="${locale.getStr("blackhole.login.clickRefresh")}">
                        <a id="refreshVerifyCodeImageButton" href="#">${locale.getStr("blackhole.login.noSee")}</a>
                    </div>
                </div>
            </#if>

                <div class="control-group">
                    <div class="controls" style="margin-left:70px;">
                        <button class="btn btn-primary" type="submit">${locale.getStr("blackhole.login.login")}</button>
                        <a class="il-blk pull-right m-l-10" href="forgetPassword.action">${locale.getStr("blackhole.login.forgetPassword")}</a>
                    <#if anonymityLoginEnable>
                        <a href="doLogin.action?username=guest&password=12345678"
                           class="btn btn-mini pull-right">${locale.getStr("blackhole.login.visitorLogin")}</a>
                    </#if>

                    <#if _message??>
                        <p class="help-block red m-t-10">${_message}</p>
                    </#if>

                    </div>

                </div>
            </form>
            <div class="page-header m-t-30 m-b-10" style="overflow: hidden;">
                <h4 class="m-v-0 f-n yahei-font f-l">${locale.getStr("blackhole.home.news")}</h4><a class="f-r gray-b" style="font-size:1.0em"
                                                               href="listPosts.action?scope=1">${locale.getStr("blueplanet.controlPanel.more")}...</a>
            </div>
            <div id="template">
            </div>

        </div>
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




