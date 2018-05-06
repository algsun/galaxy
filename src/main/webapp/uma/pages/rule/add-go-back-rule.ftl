<#--
添加往返规则

@author li.jianfei
@date  2013-05-15
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>添加往返行为规则 - 人员管理</title>
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
<@subNavs "uma:rule:addGoBack"></@subNavs>

<#-- 消息提示 -->
    <#-- TODO 统一页面消息提示风格 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--设定行为规则-->
    <div class="row">
        <div class="span12">
            <form class="form-horizontal" id="ruleForm" action="addGoBackRule.action" method="post">
                <fieldset>
                    <legend>
                        添加行为规则
                    </legend>

                    <div class="control-group">
                        <label class="control-label" for="zone-input">
                            <span class="red">*</span>区域
                        </label>

                        <div class="controls">
                            <input id="zoneId" name="zoneId" type="hidden" value="">
                            <input id="zone-input" name="zoneName" data-zone-id="" type="text"
                                   value="">
                            <span id="zone-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <input type="hidden" name="type" id="type" value="2"/>
                        <label class="control-label" for="ruleName">
                            <em class="required">*</em>名称
                        </label>

                        <div class="controls">
                            <input type="text" id="ruleName" name="ruleName" value="">
                            <span id="ruleName-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="goRuleName">
                            <em class="required">*</em>往规则名称
                        </label>

                        <div class="controls">
                            <input type="text" id="goRuleName" name="goRuleName" value="">
                            <span id="addGo" class="btn btn-mini" type="button">添加往规则</span>
                            <span id="goRuleName-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="backRuleName">
                            <em class="required">*</em>返规则名称
                        </label>

                        <div class="controls">
                            <input type="text" id="backRuleName" name="backRuleName" value="">
                            <span id="addBack" class="btn btn-mini" type="button">添加返规则</span>
                            <span id="backRuleName-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">
                            预览
                        </label>

                        <div id="ruleNames" class="controls" style="margin-top: 5px">
                            <br/>
                            <span class="label label-success">往</span>
                            <span id="goRule-input-help" class="hide help-inline red">请至少添加一个往规则</span>
                            <span class="goRuleList"><br/></span>
                            <br/>
                            <span class="label label-info">返</span>
                            <span id="backRule-input-help" class="hide help-inline red">请至少添加一个返规则</span>
                            <span class="backRuleList"><br/></span>
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
        <div id="zoneTreeDialog" class="span4" style="height:400px;overflow:auto">
            <div id="zoneTree" class="ztree"></div>

            <p class="help-block m-t-10 red"></p>
        </div>


        <div id="rules" class="control-group">
        <@ruleControl '' false/>
        <@ruleControl '' false/>
            <p class="help-block m-t-10 red"></p>
        </div>

        <div id="ruleLayout"><@ruleControl/></div>

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


