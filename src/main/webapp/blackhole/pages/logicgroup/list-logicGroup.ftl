<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>站点查询 - 系统管理</title>
<#include "../_common/common-css.ftl">
</head>

<body>

<!-- 导航栏 -->
<#assign currentTopPrivilege="blackhole:logicGroup">
<#if atSupermanPage>
    <#include "../_common/header-for-superman.ftl">
<#else>
    <#include "../_common/header.ftl">
</#if>

<div id="gcontent" class="container">

<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:logicGroup:query"></@subNavs>

<#include "../_common/message-tooltip.ftl">
    <div class="row">
        <div class="span12">
            <form class="well well-small form-inline" action="queryLogicGroup.action" method="post">
                <label for="logicGroupName">站点名称</label>
                <input type="text" name="logicGroupName" id="logicGroupName" value="${logicGroupName!}">
                <button type="submit" class="btn">查询</button>
            </form>
        </div>
    </div>

<#if (pageCount>0)>
    <div class="row">
        <div class="span12">

            <table class="table">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>站点名称</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#list logicGroupList as logicGroup>
                    <tr>
                        <td>${logicGroup_index+1}</td>
                        <td>${logicGroup.logicGroupName}</td>
                        <td>
                            <#if logicGroup.activeState==1>
                                未激活
                            <#elseif logicGroup.activeState==2>
                                待激活
                            <#elseif logicGroup.activeState==3>
                                已激活
                            </#if>
                        </td>
                        <td>
                            <#if security.isPermitted("blackhole:logicGroup:info")>
                                <a class="btn btn-info btn-mini" href="logicGroupInfo.action?id=${logicGroup.id!}"
                                   title="信息">
                                    详细
                                </a>
                            </#if>
                            <#if logicGroup.activeState!=3>
                            <#--当前用户是否拥有初始化管理员权限-->
                                <#if logicGroup.logicGroupType==2>
                                    <#if security.isPermitted("blackhole:logicGroup:initializeAdmin")>
                                        <a data-logicGroupId="${logicGroup.id}" btnInit
                                           class="btn btn-mini btn-success">
                                            初始化管理员
                                        </a>
                                    </#if>
                                </#if>
                            <#--当前用户是否拥有删除站点权限-->
                                <#if security.isPermitted("blackhole:logicGroup:delete")>
                                    <a btnDelete data-logicGroupId="${logicGroup.id}" class="btn btn-mini btn-danger">
                                        <i class="icon-trash icon-white"></i>
                                        删除
                                    </a>
                                </#if>
                            <#else>
                                <#if !logicGroup.site?? && !logicGroup.haveChildren>
                                    <#if security.isPermitted("blackhole:logicGroup:delete")>
                                        <a btnDelete data-logicGroupId="${logicGroup.id}"
                                           class="btn btn-mini btn-danger">
                                            <i class="icon-trash icon-white"></i>
                                            删除
                                        </a>
                                    </#if>
                                </#if>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>

        </div>
    </div>
</#if>
    <div class="hide">
        <div id="initAdmin">
            管理员邮箱<br>
            <input type="text" id="email" name="email" placeholder="邮箱" value="${email!}" maxlength="50"><br>
            <span id="email-input-help" class="help-block red"></span>
        </div>
    </div>

    <div class="hide">
        <div id="deleteDialog">
            确定删除该站点？
            <div class="form-inline m-t-10">

                <label for="verifyCode">验证码 </label>
                <input id="verifyCode" class="input-mini" type="text"
                       name="identifyCode">
                <img id="verifyCodeImage" src="verifyCode.action?name=deleteLogicGroup&t=${.now?time}"
                     style="vertical-align: middle;">
                <a id="refreshVerifyCodeImageButton" href="#">看不清</a>
                <span class="help-block red m-t-10"></span>
            </div>
        </div>
    </div>
<#include "../_common/pagging.ftl" >
<#assign url="queryLogicGroup.action?logicGroupName=${logicGroupName!}">
<@pagging url index pageCount></@pagging>
</div>


<!-- 页面底部 -->
<#include "../_common/footer.ftl">

<#--公共JS-->
<#include "../_common/common-js.ftl">

<!--表单验证-->
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript">
    $(function () {

        $("a[btnInit]").click(function () {
            var logicGroupId = $(this).attr("data-logicGroupId");
            var $initAdmin = $("#initAdmin");
            var $emailInput = $("#email");

            var $this = $(this);

            var dialog = art.dialog({
                id: "initAdminDialog",
                fixed: true,
                title: "初始化管理员",
                content: $initAdmin[0],
                okValue: "确定",
                ok: function () {
                    if ($emailInput.val() == "") {
                        $initAdmin.find(".help-block").empty()
                                .append("请输入管理员邮箱地址");
                        return false;
                    }

                    // email 正则表达式
                    var emailRE = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i;
                    if (!emailRE.test($emailInput.val())) {
                        $initAdmin.find(".help-block").empty()
                                .append("请输入有效的邮箱地址");
                        return false;
                    }

                    var success = false;
                    $.post("doInitAdmin.action", {logicGroupId: logicGroupId, email: $.trim($emailInput.val())}, function (result) {
                        if (result.success) {

                            $this.parent().prev().text("待激活");
                            var divMessage = "<div class='alert " +
                                    (result.success ? "alert-success'" : "alert-error'") +
                                    "> " +
                                    "<a class='close' data-dismiss='alert'>×</a>" +
                                    result.message +
                                    "</div>";
                            $("#alert").empty().append(divMessage);

                            $emailInput.val("");
                            $initAdmin.find(".help-block").empty();
                            success = true;
                            dialog.close();
                        } else {
                            $initAdmin.find(".help-block").empty()
                                    .append(result.message);
                        }
                    }, "json");
                    if (!success) {
                        return success;
                    }

                },

                cancelValue: "取消",
                cancel: function () {
                    $emailInput.val("");
                    $initAdmin.find(".help-block").empty();
                }
            });
        });

        // 删除站点组
        $(document).on("click", "a[btnDelete]", function () {
            var $deleteDialog = $("#deleteDialog");
            var $this = $(this);

            var dialog = art.dialog({
                id: "delete",
                fixed: true,
                title: "删除站点",
                content: $deleteDialog[0],
                okValue: "确定",
                ok: function () {

                    var logicGroupId = $this.attr("data-logicGroupId");
                    var verifyCode = $.trim($("#verifyCode").val());

                    // 验证非空
                    if (verifyCode == "") {
                        $deleteDialog.find(".help-block").empty()
                                .append("请输入验证码");
                        return false;
                    }

                    var success = false;
                    $.post("deleteLogicGroup.action", {logicGroupId: logicGroupId, verifyCode: verifyCode}, function (result) {
                        if (result.success) {

                            var divMessage = "<div class='alert " +
                                    (result.success ? "alert-success'" : "alert-error'") +
                                    "> " +
                                    "<a class='close' data-dismiss='alert'>×</a>" +
                                    result.message +
                                    "</div>";
                            $("#alert").empty().append(divMessage);

                            $this.parent().parent().remove();
                            $("#verifyCode").val("");
                            $deleteDialog.find(".help-block").empty();
                            success = true;
                            dialog.close();
                        } else {
                            $deleteDialog.find(".help-block").empty()
                                    .append(result.message);
                        }
                    }, "json");
                    if (!success) {
                        return success;
                    }
                },
                cancelValue: "取消",
                cancel: function () {
                    $("#verifyCode").val("");
                    $deleteDialog.find(".help-block").empty();
                }
            });
        });


        //刷新验证码
        (function () {
            var $verifyCodeImage = $("#verifyCodeImage");
            $("#verifyCodeImage,#refreshVerifyCodeImageButton").click(function () {
                $verifyCodeImage.attr("src", "verifyCode.action?name=deleteLogicGroup&t=" + new Date().getTime());
                return false;
            });
        })();
    });
</script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
