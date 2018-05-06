<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>主题 - 系统管理</title>
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
            filter: alpha(opacity=0);
            margin-top: -5px;
        }

        #imageInput1 {
            position: absolute;
            z-index: 100;
            margin-left: -180px;
            font-size: 60px;
            opacity: 0;
            filter: alpha(opacity=0);
            margin-top: -5px;
        }
    </style>
</head>
<body>

<!-- 导航栏 -->
<#--当前一级权限ID-->
<#assign currentTopPrivilege = "blackhole:theme">
<#include "../_common/header.ftl">

<div id="gcontent" class="container">
<#include "../_common/sub-navs.ftl">
<#include "../_common/message-tooltip.ftl">
    <div class="row m-t-10">
        <div class="span12">
            <ul class="nav nav-pills">
                <li class="active">
                    <a href="theme.action">主题</a>
                </li>
                <li>
                    <a href="template.action">首页模版</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <div class="form-horizontal">

            <#list 1..2 as theme_index>

                <label class="control-label">
                    主题${theme_index}
                </label>

                <div class="controls m-t-20">
                    <img id="titleImage"
                         src="images/index/content-${theme_index}.jpg"
                         style="max-width: 450px;">
                    <img id="bgImage"
                         src="images/body-bg-${theme_index}.png"
                         style="max-width: 250px;max-height: 100px;border-style:solid;border-width:thin">
                           <span>
                               <#if used!=1024&&used==theme_index>
                                   <button class="btn btn-mini" style="color: green" disabled="disabled">当前应用主题</button>
                               <#else>
                                   <a href="changeTheme.action?filePathFlag=${theme_index}" class="btn btn-mini">选择此主题</a>
                               </#if>
                           </span>
                </div>
            </#list>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="span12 m-t-50">
            <div class="offset2 span5.5">
                <form id="uploadForm" enctype="multipart/form-data" action="imageUpload.action" method="post">
                    <input type="hidden" value="1" name="flag">

                    <div class="control-group">
                        <label class="control-label" for="imageInput">
                            自定义宣传图片(建议长宽940x200)
                        </label>

                        <div class="controls">
                        <#if define??>
                            <img id="showImage"
                                 src="${define}?_=${.now?long?c}"
                                 style="max-width: 450px;height: 120px;">
                        <#else>
                            <div
                                    style="width: 440px;height: 110px; background: #d3d3d3;border-style:solid;border-width:thin"></div>
                        </#if>
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
            </div>
            <div class="span3">
                <form id="uploadForm1" enctype="multipart/form-data" action="imageUpload.action" method="post">
                    <input type="hidden" value="0" name="flag">

                    <div>
                        <label class="control-label" for="imageInput1">
                            自定义背景图片(建议长宽300x300)
                        </label>
                    <#if used==1024>
                        <span style="float: right" class="m-t-50">
                            <button class="btn btn-mini" style="color: green" disabled="true">当前应用主题</button>
                        </span>
                    </#if>
                    <#if defineBg??>
                        <img id="showImage"
                             src="${defineBg}?_=${.now?long?c}"
                             style="width: 120px;height: 120px;border-style:solid;border-width:thin">
                    <#else>
                        <div style="width: 100px;height: 110px; background:#d3d3d3;border-style:solid;border-width:thin"></div>
                    </#if>

                        <div class="m-t-10">
                                    <span id="uploadImg">
                                        <input type="file" name="image" id="imageInput1" size="1">
                                        <a href="#" class="btn btn-mini">上传图片</a>
                                    </span>
                            <span class="help-inline m-l-80 red" id="imageInput1-help"></span>
                        </div>
                </form>
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
        // TODO 两个方法合并, 可以写成 $("#imageInput, #imageInput1").change(...) @gaohui 2013-12-17

        $("#imageInput").change(function () {
            var $imageInput = $("#imageInput");
            var $imageInputHelp = $("#imageInput-help");
            var file = $imageInput.val();
            if (file != "" && !/.(gif|jpg|jpeg|png)$/.test(file.toLowerCase())) {
                helpError($imageInputHelp, "文件类型必须是gif、jpg、jpeg或png");
                return;
            }
            $imageInputHelp.empty();
            $('#uploadForm').submit();

        });

        $("#imageInput1").change(function () {
            var $imageInput = $("#imageInput1");
            var $imageInputHelp = $("#imageInput1-help");
            var file = $imageInput.val();
            if (file != "" && !/.(gif|jpg|jpeg|png)$/.test(file.toLowerCase())) {
                helpError($imageInputHelp, "文件类型必须是gif、jpg、jpeg或png");
                return;
            }
            $imageInputHelp.empty();
            $('#uploadForm1').submit();
        });


        // 错误提示
        var helpError = function ($help, msg) {
            var content = msg;
            $help.empty().append(content);
        };
    })
    ;
</script>

<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
