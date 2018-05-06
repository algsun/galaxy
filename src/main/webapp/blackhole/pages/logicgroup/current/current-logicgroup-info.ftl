<#--
  <pre>
   添加站点
  </pre>
  @author Wang yunlong
  @time  12-12-3  下午1:11
  -->
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
<#include "../../_common/common-head.ftl" >
    <title>站点信息 - 系统管理</title>
<#include "../../_common/common-css.ftl" >
    <style type="text/css">
        .info {
            font-size: 110%;
            font-weight: bold;
        }

        .label-info1 {
            text-align: right;
            background: url("images/body-bg.png");
        }
    </style>
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
            <fieldset>
                <legend>站点信息</legend>
                <div class="span12" style="text-align: right;">
                <#if security.isPermitted("blackhole:currentLogicGroup:update")>
                    <a class="inline-block  btn btn-success btn-mini"
                       href="updateCurrentLogicGroup.action?id=${logicGroup.id!}" title="编辑站点信息">
                        <i class="icon-pencil icon-white"></i>编辑
                    </a>

                </#if  >
                <#if canBeDelete && security.isSuperman()>
                    <a id="delete" data-logicGroupId="${logicGroup.id}"
                       class="inline-block m-l-5 btn btn-danger btn-mini" title="删除当前站点">
                        <i class="icon-trash icon-white"></i>删除
                    </a>
                </#if>
                </div>

            <#include "../logicgroup-view.ftl">
            </fieldset>
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
</div>
<#include "../../_common/footer.ftl">
<#include "../../_common/common-js.ftl">
<script type="text/javascript">
    $(function () {
        // 删除当前站点
        $("#delete").click(function () {
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

                    $.post("deleteLogicGroup.action", {logicGroupId: logicGroupId, verifyCode: verifyCode  }, function (result) {
                        if (result.success) {

                            window.location.href = "chooseLogicGroup.action";
                        } else {
                            $deleteDialog.find(".help-block").empty()
                                    .append(result.message);
                            return false;
                        }
                    }, "json");
                },
                cancelValue: "取消",
                cancel: function () {
                    $("#verifyCode").val("");
                    $deleteDialog.find(".help-block").empty();
                }
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
    });
</script>
<#include "../../_common/last-resources.ftl">
</body>
</html>
