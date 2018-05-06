<#--
  - 添加ftp
  -@author Wang yunlong
  -@time  13-3-22  下午4:45
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.ftp.add.title")} - ${locale.getStr("proxima.common.systemName")}</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:ftp">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:ftp:add"></@subNavs>

<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--your content-->
    <div class="row">
        <div class="span12">
            <form class="form-horizontal" id="ftp-form" action="addFTP.action" method="post">
                <fieldset>
                    <legend>
                    ${locale.getStr("proxima.ftp.add.title")}
                    </legend>
                    <div class="control-group">
                        <label class="control-label" for="name">
                            <em class="required">*</em>${locale.getStr("proxima.ftp.name")}
                        </label>

                        <div class="controls">
                            <input type="text" id="name" name="name" placeholder="名称" value="${name!}" maxlength="50">
                            <span id="name-input-help" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="ip">
                            <em class="required">*</em>${locale.getStr("proxima.ftp.host")}
                        </label>

                        <div class="controls">
                            <input type="text" id="ip" name="ip" value="${ip!}">
                            <span id="ip-input-help" class="help-inline"></span>
                            <p class="help-block">${locale.getStr("proxima.ftp.host.remark")}</p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="port">
                            <em class="required">*</em>${locale.getStr("proxima.ftp.port")}
                        </label>

                        <div class="controls">
                            <input type="text" id="port" name="port" value="${(port!)?c}">
                            <span id="port-input-help" class="help-inline"></span>
                            <p class="help-block">${locale.getStr("proxima.ftp.port.remark")}</p>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="userName">
                            <em class="required">*</em>${locale.getStr("userName")}
                        </label>

                        <div class="controls">
                            <input type="text" id="userName" name="userName" value="${userName!}">
                            <span id="userName-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="password">
                            <em class="required">*</em>${locale.getStr("proxima.ftp.password")}
                        </label>

                        <div class="controls">
                            <input type="password" id="password" name="password" value="${password!}">
                            <span id="password-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">
                        </label>

                        <div class="controls">
                            <button id="test-connect"
                                    class="btn btn-mini">${locale.getStr("proxima.ftp.connectionTest")}</button>
                            <span class="help-inline"></span>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button id="submit" type="submit"
                                class="btn btn-primary">${locale.getStr("common.save")}</button>
                        <a href="FTPList.action" class="btn">${locale.getStr("common.return")}</a>
                    </div>

                </fieldset>
            </form>
        </div>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#--your js-->
<!--表单验证-->
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="js/ftp/ftp-form.js"></script>
<script type="text/javascript" src="js/ftp/ftp-connect.js"></script>

<#include "../_common/last-resources.ftl">
</body>
</html>
