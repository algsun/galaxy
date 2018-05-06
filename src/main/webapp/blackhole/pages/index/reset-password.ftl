<#--Author Wang yunlong-->
<#--Date  12-11-26 下午1:17-->
<!DOCTYPE html>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>重置密码 - 银河</title>
<#include "../_common/common-css.ftl">
</head>
<body>

<#include "../_common/header-not-logined-for-galaxy.ftl">


<div id="gcontent" class="container">
    <div class="row m-t-20">
        <div class="span12">
        <#if canDo>
            <form class="form-horizontal" action="doResetPassword.action" method="post">
                <fieldset>
                    <legend><h1>重置密码</h1></legend>
                    <input type="hidden" name="token" value="${token!}">

                    <div class="control-group">
                        <label class="control-label"><em class="required">*</em>新密码</label>

                        <div class="controls">
                            <input id="newPassword" name="password" type="password">
                            <span class="inline-block  m-l-10" style="color:red;font-size: 80%"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label"><em class="required">*</em>确认密码</label>

                        <div class="controls">
                            <input id="newPassword2" name="newPassword2" type="password">
                            <span class="inline-block  m-l-10" style="color:red;font-size: 80%"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="controls">
                            <button id="submit" class="btn btn-primary" type="submit">保存</button>
                        </div>
                    </div>
                </fieldset>
            </form>
        <#else>
            <#if !complete>
                <div class="alert alert-error">
                    <h3>链接无效或过期</h3>
                </div>
            </#if>
        </#if>
        </div>
    </div>

<#if _message?? >
    <div class="row m-t-20">
        <div class="span12">
            <div id="alert" class="alert alert-success">
                <#if _success>
                    <h3>${_message!}</h3>
                <#else>
                    <h3>${_message!}</h3>
                </#if>
            </div>
            <a class="btn btn-large" href="login.action?username=${email}" target="_blank">登录</a>
        </div>
    </div>
</#if>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<script type="text/javascript">
    $(function () {
        var pwdFlag = false;
        var pwdFlag2 = false;
        var $pwd = $("#newPassword");
        var $pwd2 = $("#newPassword2");
        $pwd.bind('focusout', pwdFun);
        $pwd2.bind('focusout', pwd2Fun);
        $pwd.bind('focusin', cancelMsg);
        $pwd2.bind('focusin', cancelMsg);
        $("#submit").click(function () {
            pwdFun();
            pwd2Fun();
            if (pwdFlag && pwdFlag2) {
                return true;
            } else {
                return false;
            }
        });
        function cancelMsg() {
            $(this).siblings("span").text("");
        }

        function pwdFun() {
            if ($pwd.val().length >= 8 && $pwd.val().length<=30) {
                pwdFlag = true;
            } else {
                $pwd.siblings("span").text("密码长度为8到30个字符");
                pwdFlag = false;
            }
        }

        function pwd2Fun() {
            if ($.trim($pwd2.val()) == $.trim($pwd.val())) {
                pwdFlag2 = true;
            } else {
                $pwd2.siblings("span").text("两次输入的密码不一致");
                pwdFlag2 = false;
            }
        }
    });
</script>

<#include "../_common/last-resources.ftl">
</body>
</html>

