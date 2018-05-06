<#--
行为规则查询

@author xubaoji
@date  2013-04-11
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>添加行为规则 - 人员管理</title>
<#include "../_common/common-css.ftl">
    <style type="text/css">

        .rule {
            margin-top: 10px;
        }
    </style>
    <link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "uma:rule">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "uma:rule:add"></@subNavs>

<#-- 消息提示 -->
<#-- TODO 统一页面消息提示风格 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--设定行为规则-->
    <div class="row">
        <div class="span12">
            <form class="form-horizontal" id="ruleForm" action="addSingleRule.action" method="post">
                <fieldset>
                    <legend>
                        添加行为规则
                    </legend>
                    <div class="control-group">
                        <input type="hidden" name="type" id="type" value="1"/>
                        <input id="id" name="id" value="" type="hidden"/>
                        <label class="control-label" for="ruleName">
                            <em class="required">*</em>名称
                        </label>

                        <div class="controls">
                            <input type="text" id="ruleName" name="ruleName" value="">
                            <span id="addRule" class="btn btn-mini" type="button">添加规则</span>
                            <span id="ruleName-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">
                            预览
                        </label>

                        <div id="ruleNames" class="controls" style="margin-top: 5px">
                            <span class="ruleList"></span>
                            <span id="backRule-input-help" class="hide help-inline red">请至少添加一个单程规则</span>
                        </div>
                    </div>


                    <div class="form-actions">
                        <button type="submit" id="submit-button"
                                class="btn btn-primary">
                            确定
                        </button>
                        <a href="queryRule.action" class="btn">返回</a>
                    </div>

            </form>

        </div>
    </div>

    <div class="hide">
        <div id="zoneTreeDialog" class="span4">
            <div id="zoneTree" class="ztree"></div>

            <p class="help-block m-t-10 red"></p>
        </div>

        <div id="rules" class="control-group">
            <label class="control-label" for="ruleName">
                <em class="required">*</em>规则
            </label>

        <@ruleControl '' false/>
        <@ruleControl '' false/>
            <p class="help-block m-t-10 red"></p>
        </div>

        <div id="ruleLayout">
        <@ruleControl/>
        </div>
    </div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#--your js-->
    <script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
    <script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
    <script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
    <script type="text/javascript" src="js/strToPY.js"></script>
    <script type="text/javascript" src="js/rule/rule.js"></script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
<#macro ruleControl ruleDeviceId='' new=true>
<div class="rule controls">
    <input style="width: 200px" type="text" name="deviceId" class="select <#if new>rule_template</#if>"
           value="${ruleDeviceId}"/>
    <span class="btn btn-mini appendRule" style="margin-top: 3px;"><i class="icon-plus"></i></span>
    <span class="btn btn-mini removeRule" style="margin-top: 3px;"><i class="icon-remove"></i></span>
    <span id="device-input-help" class="help-inline hide">
        <label class="error" for="device" generated="true">行为规则不能为空</label>
    </span>
</div>
</#macro>



