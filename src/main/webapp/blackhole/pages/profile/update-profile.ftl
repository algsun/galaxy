<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>个人信息 - 系统管理</title>
<#include "../_common/common-css.ftl">
    <style type="text/css">
            /*照片上传 input file 样式*/
        #uploadImg {
            font-size: 12px;
            overflow: hidden;
            position: absolute
        }

        #imageInput {
            position: absolute;
            z-index: 100;
            margin-left: -180px;
            font-size: 60px;
            opacity: 0;
            filter: alpha(opacity = 0);
            margin-top: -5px;
        }
    </style>

</head>
<body>

<!-- 导航栏 -->
<#--当前一级权限ID-->
<#assign currentTopPrivilege = "blackhole:profile">
<#include "../_common/header.ftl">

<div id="gcontent" class="container">
<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:profile:update"></@subNavs>


<#include "../_common/message-tooltip.ftl">

    <div class="row">
        <div class="span12">
            <div class="form-horizontal">
                <fieldset>
                    <legend>个人信息</legend>

                    <form id="uploadForm" enctype="multipart/form-data" action="upload.action" method="post">
                        <div class="control-group">
                            <label class="control-label" for="imageInput">
                                照片
                            </label>

                            <div class="controls">
                                <img id="showImage"
                                     src="${filePath}"
                                     style="width: 120px;height: 120px;">

                                <div class="m-t-10">
                                    <span id="uploadImg">
                                        <input type="file" name="image" id="imageInput" size="1">
                                        <a href="#" class="btn btn-mini">上传图片</a>
                                    </span>

                                    <span class="help-inline m-l-80 red" id="imageInput-help"></span>
                                </div>
                            </div>
                        </div>
                    </form>
                    <form id="userForm" class="form-horizontal" action="doUpdateProfile.action" method="post">

                        <div class="control-group">
                            <label class="control-label" for="email">
                                邮箱
                            </label>

                            <div class="controls">
                                <input type="text" id="email" name="email" disabled value="${user.email!}">
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
                            <label class="control-label">
                                性别
                            </label>

                            <div class="controls">
                                <label class="radio inline">
                                    <input type="radio" name="sex" id="male" value=2
                                           <#if user.sex==2>checked</#if>
                                            >男
                                </label>
                                <label class="radio inline">
                                    <input type="radio" name="sex" id="female" value=1
                                           <#if user.sex==1>checked</#if>
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

                        <div class="control-group">
                            <label class="control-label">
                                <span class="red">*</span>角色
                            </label>

                            <div class="controls">
                            <#list (listRole)! as role >
                                <label class="checkbox">
                                    <input type="checkbox" name="role" disabled="disabled" checked="checked">
                                ${role.roleName}
                                </label>
                            </#list>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label"></label>

                            <div class="controls">
                                <a id="resetIndividual" class="btn">重置个性化参数</a>
                            </div>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-primary">保存</button>
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
<script type="text/javascript" src="../assets/jquery-form-plugin/3.19/jquery.form.js"></script>
<script type="text/javascript" src="../blackhole/js/user-validate.js"></script>

<script type="text/javascript">

    $(function () {
        // 重置用户个性化参数设置
        $("#resetIndividual").click(function () {

            $.post("resetIndividual.action", function (result) {
                var divMessage = "<div class='alert " +
                        (result.success ? "alert-success'" : "alert-error'")
                        + "> " +
                        "<a class='close' data-dismiss='alert'>×</a>" +
                        result.message +
                        "</div>";

                $("#alert").empty().append(divMessage);
            });
        });

        $("#imageInput").change(function () {

            var $imageInput = $("#imageInput");
            var $imageInputHelp = $("#imageInput-help");

            var file = $imageInput.val();
            if (file == "") {
                $("#showImage").attr("src", "../blackhole/images/head_portrait.png");
                return;
            }
            if (file != "" && !/.(gif|jpg|jpeg|png)$/.test(file.toLowerCase())) {
                helpError($imageInputHelp, "文件类型必须是gif、jpg、jpeg或png");
                return;
            }
            $imageInputHelp.empty();

            $('#uploadForm').ajaxSubmit({
                dataType:'json',
                success:function (result) {
                    $("#showImage").attr("src", result.filePath + "/" + result.imageFileName + "?t=" + new Date().getTime());
                }
            });
        });


        // 错误提示
        var helpError = function ($help, msg) {
            var content = msg;
            $help.empty().append(content);
        };
    });
</script>

<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
