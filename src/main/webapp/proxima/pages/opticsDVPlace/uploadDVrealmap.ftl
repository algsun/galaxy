<#--
  -上传摄像机 实景图页面
  -@author xu.baoji
  -@time  2013.08.22
  @check @li.jianfei 2013.09.02 #5272
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.jcv.cameraJCV")} - ${locale.getStr("proxima.common.systemName")}</title>

<#include "../_common/common-css.ftl">
    <style type="text/css">
        /*实景图上传 input file 样式*/
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

        #imageInput-help {
            margin-left: 90px;
        }
    </style>

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:opticsDVPlace">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:opticsDVPlace:update"></@subNavs>

<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--your content-->

    <div class="row">
        <div class="span12">
            <div class="form-horizontal">
                <fieldset>
                    <legend>
                    ${locale.getStr("proxima.pictures.JCV")}
                    </legend>
                    <form id="uploadForm" enctype="multipart/form-data" action="uploadDVrealmap.action" method="post"
                          class="form-horizontal">
                        <input type="hidden" id="dvId" name="dvId" value="${dvId!}">
                        <div class="control-group">
                            <label class="control-label" for="dvName"> ${locale.getStr("proxima.common.camera")}</label>
                            <div class="controls">
                                <input type="text" id="dvName" name="dvName" disabled value="${dvName!}">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"
                                   for="imageInput"> ${locale.getStr("proxima.common.JCV")}</label>
                            <div class="controls">
                                <img id="showImage" src="${realmapUrl!}" style="max-width: 420px;max-height: 320px;">
                                <div class="m-t-10">
                         <span id="uploadImg">
                            <input type="file" name="image" id="imageInput" size="2">
                            <a href="#" class="btn btn-mini"
                               style="font-size:14px"> ${locale.getStr("proxima.jcv.uploadJCV")}</a>
                         </span>
                                    <span class="help-inline m-l-80 red" id="imageInput-help"></span>
                                </div>
                            </div>
                        </div>
                    </form>
                </fieldset>
            </div>
        </div>
    </div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<#include "../_common/last-resources.ftl">
    <script type="text/javascript" src="../assets/jquery-form-plugin/3.19/jquery.form.js"></script>
    <script type="text/javascript">
        $("#imageInput").change(function () {

            var $imageInput = $("#imageInput");
            var $imageInputHelp = $("#imageInput-help");

            var file = $imageInput.val();
            if (file == "") {
                $("#showImage").attr("src", "../blackhole/images/head_portrait.png");
                return;
            }
            if (file != "" && !/.(gif|jpg|jpeg|png)$/.test(file.toLowerCase())) {
                helpError($imageInputHelp, message.fileTypeLimit);
                return;
            }
            $imageInputHelp.empty();

            $('#uploadForm').ajaxSubmit({
                dataType: 'json',
                success: function (result) {
                    $("#showImage").attr("src", result.realmapUrl + "?t=" + new Date().getTime());
                }
            });
        });


        // 错误提示
        var helpError = function ($help, msg) {
            var content = msg;
            $help.empty().text(content);
        };
    </script>
</body>
</html>
