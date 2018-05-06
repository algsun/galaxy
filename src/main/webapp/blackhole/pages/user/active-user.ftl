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

<#include "../_common/message-tooltip.ftl">

    <div class="row m-t-20">
        <div class="span12">
            <div class="form-horizontal">
                <fieldset>
                    <legend><h2>帐号激活</h2></legend>

                    <form id="userForm" class="form-horizontal" action="doActiveUser.action" method="post">
                        <input type="hidden" name="token" value="${token}"/>

                        <div class="control-group">
                            <label class="control-label">站点</label>

                            <div class="controls">
                                <input type="text" name="logicGroup" readonly="readonly" value="${logicGroupName}">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="email">邮箱</label>

                            <div class="controls">
                                <input type="text" id="email" disabled="disabled" value="${user.email!}">
                                <span id="email-input-help" class="help-inline"></span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="userName">
                                <span class="red">*</span>姓名
                            </label>

                            <div class="controls">
                                <input type="text" id="userName" name="userName" value=${user.userName!}>
                                <span id="userName-input-help" class="help-inline"></span>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label"><span class="red">*</span>密码</label>

                            <div class="controls">
                                <input id="password" name="password" type="password">
                                <span class="help-inline"></span>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label"><span class="red">*</span>确认密码</label>

                            <div class="controls">
                                <input name="password2" type="password">
                                <span class="help-inline"></span>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label">
                                性别
                            </label>

                            <div class="controls">
                                <label class="radio inline">
                                    <input type="radio" name="sex" id="male" value=2
                                           <#if user.sex != 1 && user.sex != 2>checked="checked"</#if>
                                           <#if user.sex==2>checked="checked"</#if>
                                            >男
                                </label>
                                <label class="radio inline">
                                    <input type="radio" name="sex" id="female" value=1
                                           <#if user.sex==1>checked="checed"</#if>
                                            >女
                                </label>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="phone">
                                手机号码
                            </label>

                            <div class="controls">
                                <input type="text" id="phone" name="mobile" placeholder="" value="${user.mobile!}">
                                <span id="phone-input-help" class="help-inline"></span>
                            </div>
                        </div>

                        <div class="control-group m-t-40">
                            <div class="controls">
                                <div style="width:100px;">
                                    <button type="submit" class="btn btn-primary btn-large btn-block">激活</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </fieldset>
            </div>
        </div>
    </div>
</div>

<!-- 页面底部 -->
<#include "../_common/footer.ftl">
<#--公共JS-->
<#include "../_common/common-js.ftl">
<!--表单验证-->
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>

<script type="text/javascript" src="../blackhole/js/active-user.js"></script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
