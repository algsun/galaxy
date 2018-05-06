<#--
修改往返规则

@author li.jianfei
@date  2013-05-23
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>修改往返行为规则 - 人员管理</title>
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
<@subNavs "uma:rule:update"></@subNavs>

<#-- 消息提示 -->
<#-- TODO 统一页面消息提示风格 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--设定行为规则-->
    <div class="row">
        <div class="span12">
            <form class="form-horizontal" id="ruleForm" action="updateGoBackRule.action" method="post">
                <fieldset>
                    <legend>
                        修改行为规则
                    </legend>

                    <div class="control-group">
                        <label class="control-label" for="zone-input">
                            <span class="red">*</span>区域
                        </label>

                        <div class="controls">
                            <input id="zoneId" name="zoneId" type="hidden" value="${rule.zoneId}">
                            <input id="zone-input" name="zoneName" data-zone-id="" type="text"
                                   value="${rule.zoneName}">
                            <span id="zone-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <input type="hidden" name="id" id="id" value="${rule.id}"/>
                        <input type="hidden" name="type" id="type" value="2"/>
                        <label class="control-label" for="ruleName">
                            <em class="required">*</em>名称
                        </label>

                        <div class="controls">
                            <input type="text" id="ruleName" name="ruleName" value="${rule.ruleName}">
                            <span id="ruleName-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="goRuleName">
                            <em class="required">*</em>往规则名称
                        </label>

                        <div class="controls">
                            <input type="text" id="goRuleName" name="goRuleName" value="${rule.goRuleList[0].ruleName}">
                            <span id="addGo" class="btn btn-mini" type="button">添加往规则</span>
                            <span id="goRuleName-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="backRuleName">
                            <em class="required">*</em>返规则名称
                        </label>

                        <div class="controls">
                            <input type="text" id="backRuleName" name="backRuleName"
                                   value="${rule.backRuleList[0].ruleName}">
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
                            <span class="goRuleList"><br/>
                            <#list rule.goRuleList as goRule>
                                <div>
                                    <span id='edit' class='btn btn-mini'><i class='icon-pencil'></i></span>
                                    <a id="state"
                                       class="btn <#if goRule.enable>btn-danger<#else>btn-success</#if> btn-mini"
                                       title="<#if goRule.enable>停用<#else>启用</#if>">
                                        <i class="<#if goRule.enable>icon-stop<#else>icon-play</#if> icon-white"></i>
                                    </a>

                                    <span id="exciters">
                                        <#assign exciterIds =goRule.id + ",3,"+goRule.enable?string("1","0")>
                                        <#list goRule.deviceList as device>
                                            <#assign exciterIds=exciterIds+","+device.id>
                                            <#if (device_index>=1)>
                                                <span class='muted'
                                                      style='margin-right: -6px;vertical-align: 1px;'>—</span>
                                            <span class='muted' style='vertical-align: 0;'>></span>
                                            </#if>
                                            <strong>${device.name}</strong>
                                        </#list>
                                    </span>
                                    <input name='exciterIds' type='hidden' value='${exciterIds}'/>
                                    <br/>
                                </div>
                            </#list>

                            </span>
                            <br/>
                            <span class="label label-info">返</span>
                            <span class="backRuleList"><br/>
                            <#list rule.backRuleList as backRule>
                                <div>
                                    <span id='edit' class='btn btn-mini'><i class='icon-pencil'></i></span>
                                    <a id="state"
                                       class="btn <#if backRule.enable>btn-danger<#else>btn-success</#if> btn-mini"
                                       title="<#if backRule.enable>停用<#else>启用</#if>">
                                        <i class="<#if backRule.enable>icon-stop<#else>icon-play</#if> icon-white"></i>
                                    </a>

                                    <span id="exciters">
                                        <#assign exciterIds = backRule.id + ",4,"+backRule.enable?string("1","0")>
                                        <#list backRule.deviceList as device>
                                            <#assign exciterIds=exciterIds+","+device.id>
                                            <#if (device_index>=1)>
                                                <span class='muted'
                                                      style='margin-right: -6px;vertical-align: 1px;'>—</span>
                                                <span class='muted' style='vertical-align: 0;'>></span>
                                            </#if>
                                            <strong>${device.name}</strong>
                                        </#list>
                                    </span>
                                    <input name='exciterIds' type='hidden' value='${exciterIds}'/>
                                    <br/>
                                </div>
                            </#list>
                            </span>
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

