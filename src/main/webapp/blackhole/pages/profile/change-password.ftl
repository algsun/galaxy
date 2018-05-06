<!DOCTYPE html>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>修改密码 - 系统管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<!-- 导航栏 -->
<#--当前一级权限ID-->
<#assign currentTopPrivilege = "blackhole:profile">
<#include "../_common/header.ftl" >

<div id="gcontent" class="container">

<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:profile:password"></@subNavs>

<#include "../_common/message-tooltip.ftl" >

    <div class="row">
        <div class="span12">

            <form class="form-horizontal" action="doChangePassword.action" method="post">
                <fieldset>
                    <legend>修改密码</legend>

                    <div class="control-group">
                        <label class="control-label"><em class="required">*</em>当前密码</label>

                        <div class="controls"><input name="password" type="password"></div>
                    </div>

                    <div class="control-group">
                        <label class="control-label"><em class="required">*</em>新密码</label>

                        <div class="controls"><input id="newPassword" name="newPassword" type="password">

                            <p class="help-block">密码至少8位</p></div>
                    </div>

                    <div class="control-group">
                        <label class="control-label"><em class="required">*</em>确认密码</label>

                        <div class="controls"><input name="newPassword2" type="password"></div>
                    </div>

                    <div class="control-group">
                        <div class="controls">
                            <button class="btn btn-primary" type="submit">保存</button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<!-- 页面底部 -->
<#include "../_common/footer.ftl" >

<#--公共JS-->
<#include "../_common/common-js.ftl">

<!--表单验证-->
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript">
    $(function () {
        var passwordCheck = {
            required:true,
            rangelength:[8, 30]
        };
        $('form').validate({
            rules:{
                password:passwordCheck,
                newPassword:passwordCheck,
                newPassword2:{
                    required:true,
                    rangelength:[8, 30],
                    equalTo:'#newPassword'
                }
            }
        });
    });
</script>

<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>