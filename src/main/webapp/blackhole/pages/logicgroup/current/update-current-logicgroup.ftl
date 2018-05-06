<#--
  <pre>
   添加站点
  </pre>
  @author Wang yunlong
  @time  12-12-3  下午1:11
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../../_common/common-head.ftl" >
    <title>编辑站点 - 系统管理</title>
<#include "../../_common/common-css.ftl" >
</head>
<body>

<#assign currentTopPrivilege = "blackhole:currentLogicGroup">
<#include "../../_common/header.ftl">

<div id="gcontent" class="container">

<#--二级菜单-->
<#include "../../_common/sub-navs.ftl">
<@subNavs "blackhole:currentLogicGroup:info"></@subNavs>
    <div class="row m-l-10">
    </div>
<#--消息-->
<#include "../../_common/message-tooltip.ftl">

    <div class="row">
        <div class="span12">
            <form id="currentLogicGroupForm" class="form-horizontal" action="doUpdateCurrentLogicGroup.action"
                  method="post">
                <input id="logic-group-name" type="hidden" name="id" value="${logicGroup.id!}">
                <fieldset>
                    <legend>
                        <a class="go-back" href="currentLogicGroupInfo.action">
                            <i class="mw-icon-prev"></i>
                            编辑站点信息
                        </a>
                    </legend>
                    <div class="row">
                        <div class="span12">
                            <div class="control-group">
                                <label class="control-label "><em class="required">*</em>站点名称</label>

                                <div class="controls">
                                    <input id="logicGroupName" type="text" name="logicGroupName" class="i-w-270"
                                           <#if logicGroup.site??>readonly="true"</#if>
                                           value="${logicGroup.logicGroupName!}">
                                    <span class="inline-block msg m-l-10 red"
                                          style="font-size: 80%"></span>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label ">机构代码</label>

                                <div class="controls">
                                    <input name="orgCode" type="text" class="i-w-270" value="${logicGroup.orgCode!}"
                                           maxlength="20">
                                    <span class="inline-block msg m-l-10 red"
                                          style="font-size: 80%"></span>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label ">地址</label>

                                <div class="controls">
                                    <textarea name="orgAddress" type="text" class="i-w-270"
                                            maxlength="50">${logicGroup.orgAddress!}</textarea>
                                    <span class="inline-block msg m-l-10 red"
                                          style="font-size: 80%"></span>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label ">邮编</label>

                                <div class="controls">
                                    <input name="orgZipcode" type="text" class="i-w-270"
                                           value="${logicGroup.orgZipcode!}" maxlength="6">
                                    <span class="inline-block msg m-l-10 red"
                                          style="font-size: 80%"></span>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label ">网址</label>

                                <div class="controls">
                                    <input name="orgWebsite" type="text" class="i-w-270"
                                           value="${logicGroup.orgWebsite!}" maxlength="50">
                                    <span class="inline-block msg m-l-10 red"
                                          style="font-size: 80%"></span>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label ">联系电话</label>

                                <div class="controls">
                                    <input name="orgTel" type="text" class="i-w-270" value="${logicGroup.orgTel!}">
                                    <span class="inline-block msg m-l-10 red"
                                          style="font-size: 80%"></span>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label ">传真</label>

                                <div class="controls">
                                    <input name="orgFax" type="text" class="i-w-270" value="${logicGroup.orgFax!}">
                                    <span class="inline-block msg m-l-10 red"
                                          style="font-size: 80%"></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row m-t-10">
                        <div class="span12">
                            <div class="control-group">
                                <div class="controls">
                                    <button id="logicgroup-submit" class="btn btn-primary" type="submit">保存</button>
                                    <a class="btn" href="currentLogicGroupInfo.action">返回</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
<#include "../../_common/footer.ftl">
<#include "../../_common/common-js.ftl">
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript">
    $(function () {
        /**
         (function () {
            var flag = false;
            var $logicGroupName = $("#logicGroupName");
            $logicGroupName.bind("blur", logicGroupFun);
            $("#logicgroup-submit").click(function () {
                logicGroupFun();
                return flag;
            });
            function logicGroupFun() {
                var logicGroupName = $logicGroupName.val();
                if (logicGroupName.length < 1) {
                    $logicGroupName.siblings(".msg").text("站点名称不能为空");
                    flag = false;
                } else if (logicGroupName.length > 25) {
                    $logicGroupName.siblings(".msg").text("站点名称长度不能超过25个字");
                    flag = false;
                } else {
                    flag = true;
                }
            }

            $logicGroupName.focus(function () {
                $logicGroupName.siblings(".msg").text("");
            });
        }());
         */
            // 电话号码验证
        jQuery.validator.addMethod("isPhone", function (value, element) {
            var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
            return this.optional(element) || (tel.test(value));
        }, "请正确填写您的电话号码");

        // 传真验证
        jQuery.validator.addMethod("isFax", function (value, element) {
            var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
            return this.optional(element) || (tel.test(value));
        }, "请正确填写您的传真号码");

        // 邮政编码验证
        jQuery.validator.addMethod("isZipCode", function (value, element) {
            var tel = /^[0-9]{6}$/;
            return this.optional(element) || (tel.test(value));
        }, "请正确填写您的邮政编码");

        $("#currentLogicGroupForm").validate({
            onfocusout: true,
            rules: {
                logicGroupName: {
                    required: true,
                    maxlength: 25
                },
                orgCode: {
                    number: true,
                    maxlength: 20
                },
                orgAddress: {
                    maxlength: 50
                },
                orgZipcode: {
                    isZipCode: true
                },
                orgWebsite: {
                    url: true
                },
                orgTel: {
                    isPhone: true
                },
                orgFax: {
                    isFax: true
                }

            },
            messages: {

            }

        });

    });
</script>
<#include "../../_common/last-resources.ftl">
</body>
</html>
