<#--
档案填写

@author liuzhu
@time  2015-9-14
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>档案填写aaaaaaaaaaaaaaa - 资产管理</title>

<#include "../_common/common-css.ftl">
<#include "/common/pages/common-tag.ftl">
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
    <style type="text/css">
        /*照片 上传 input file 样 式*/
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
    </style>
    <fieldset>
        <legend>个人信息</legend>
        ssssssssssssssssssssssssss
        <form class="form-horizontal" id="drawRegister_form">
            <input name="drawRegister.repairRecord.id" id="repairRecordId" type="hidden"
                   value="${drawRegister.repairRecord.id}"/>
            <input name="drawRegister.id" type="hidden" value="${drawRegister.id}"/>
AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            <div class="control-group">
                <label class="control-label">图纸AAA类型</label>

                <div class="controls">
                    <input type="text" id="drawingType" name="drawRegister.drawingType"
                           value="${drawRegister.drawingType}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">简单描述</label>

                <div class="controls">
                    <input type="text" name="drawRegister.description" id="description"
                           value="${drawRegister.description}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">绘图人</label>

                <div class="controls">
                    <select name="drawRegister.drawingPerson.id" id="">
                    <#list users as user>
                        <option value="${user.id}"
                                <#if drawRegister.drawingPerson.id==user.id>selected="selected" </#if>>${user.userName}</option>
                    </#list>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">时间</label>

                <div class="controls">
                    <input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" id="stamp"
                           name="drawRegister.stamp" value="${drawRegister.stamp?string('yyyy-MM-dd')}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">备注</label>

                <div class="controls">
                    <input type="text" id="remark" name="drawRegister.remark" value="${drawRegister.remark}">
                </div>
            </div>

        </form>

        <form id="uploadForm" class="form-horizontal" enctype="multipart/form-data" action="draw_upload.action"
              method="post">
            <input name="id" type="hidden" value="${drawRegister.id}"/>

            <div class="control-group">
                <label class="control-label">上传图纸</label>

                <div class="controls">
                    <img id="showImage"
                         src="${imgPath}"
                         style="width: 120px;height: 120px;">

                    <div class="m-t-10">
                                    <span id="uploadImg">
                                        <input type="file" name="image" id="imageInput" size="1">
                                        <a href="#" class="btn btn-mini">上传图片</a>
                                    </span>

                        <span class="help-inline m-l-80 red" id="imageInput-help"></span>
                    </div>
                <#--<img id="showImage" src="${imgPath}" style="width: 120px;height: 120px;">-->
                <#--<input type="hidden" id="draw_register_id" name="id" value=""/>-->
                <#--<input type="file" name="image" id="imageInput" size="1">-->
                </div>
            </div>
        </form>

        <form class="form-horizontal ">
            <div class="control-group">
                <label class="control-label"></label>

                <div class="controls">
                    <button type="button" onclick="" class="btn" id="draw-register-submit">保AAAAAA存</button>
                </div>
            </div>
        </form>
    </fieldset>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="../assets/jquery-form-plugin/3.19/jquery.form.js"></script>
<script type="text/javascript">
    $(function () {

        $("#imageInput").change(function () {

            var $imageInput = $("#imageInput");
            var $imageInputHelp = $("#imageInput-help");

            var file = $imageInput.val();
            if (file == "") {
                return;
            }
            if (!/.(jpg|png|jpeg|bmp|gif|tiff|tif|DNG|NEF|pdf|drw|CAD|acr|crw)$/.test(file.toLowerCase())) {
                helpError($imageInputHelp, "文件类型必须是jpg、png、jpeg、bmp、gif、tiff、tif、DNG、NEF、pdf、drw、CAD、acr或crw");
                return;
            }
            $imageInputHelp.empty();

            $('#uploadForm').ajaxSubmit({
                dataType: 'json',
                success: function (result) {
                    $("#showImage").attr("src", result.filePath + "/" + result.imageFileName + "?t=" + new Date().getTime());
                }
            });
        });

        $("#drawRegister_form").validate({
            rules: {
                'drawRegister.drawingType': {
                    required: true,
                    maxlength: 25
                },
                'drawRegister.description': {
                    required: true,
                    maxlength: 100
                },
                'drawRegister.stamp': {
                    required: true
                },
                'drawRegister.remark': {
                    required: true,
                    maxlength: 100
                }
            },
            messages: {
                'drawRegister.drawingType': {
                    required: "请输入图纸类型",
                    maxlength: jQuery.format("图纸类型不能大于{0}个字符")
                },
                'drawRegister.description': {
                    required: "请输入简单描述",
                    maxlength: jQuery.format("简单描述不能大于{0}个字符")
                },
                'drawRegister.stamp': {
                    required: "请选择时间"
                },
                'drawRegister.remark': {
                    required: "请输入备注",
                    maxlength: jQuery.format("备注不能大于{0}个字符")
                }
            }
        });

        // 错误提示
        var helpError = function ($help, msg) {
            var content = msg;
            $help.empty().append(content);
        };

        $("#draw-register-submit").click(function () {
            if ($("#drawRegister_form").valid()) {
                var params = $("#drawRegister_form").serialize();
                params = decodeURIComponent(params, true);
                var url = "archives/drawRegister/do_update.json?" + params;
                var repairRecordId = $("#repairRecordId").val();
                $.get(url, function (result) {
                    if (result == true) {
                        window.location.href = "archives/index?repairRecordId=" + repairRecordId;
                    }
                });
            }
        });
    });
</script>
</body>
</html>



