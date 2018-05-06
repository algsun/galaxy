<#--
档案填写

@author liuzhu
@time  2015-9-14
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>修改影像资料 - 资产管理</title>

<#include "../_common/common-css.ftl">
<#include "/common/pages/common-tag.ftl">
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
    <form class="form-horizontal" id="update_image_datum_form" action="archives/imageDatums/do_update.json">
        <input type="hidden" name="imageDatum.id" value="${imageDatum.id}"/>
        <input type="hidden" name="imageDatum.filePath" value="${imageDatum.filePath!}"/>
        <input type="hidden" id="update_image_repairRecordId" name="imageDatum.repairRecord.id"
               value="${imageDatum.repairRecord.id!}"/>

        <div class="control-group">
            <label class="control-label">提交人</label>

            <div class="controls">
                <select name="imageDatum.submitPerson.id" id="">
                <#list users as user>
                    <option value="${user.id}" <#if imageDatum.submitPerson.id==user.id>selected="selected" </#if>>
                    ${user.userName}</option>
                </#list>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">内容表述</label>

            <div class="controls">
                <textarea id="media" name="imageDatum.content" cols="30" rows="3">${imageDatum.content!}</textarea>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">介质</label>

            <div class="controls">
                <input type="text" name="imageDatum.media" id="content" value="${imageDatum.media}"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">时常</label>

            <div class="controls">
                <input type="text" id="duration" name="imageDatum.duration" value="${imageDatum.duration?c}">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">采集时间</label>

            <div class="controls">
                <input type="text" id="image_datum_stamp" name="imageDatum.stamp"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"
                       value="${imageDatum.stamp?string("yyyy-MM-dd")}">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">已上传文件名</label>

            <div class="controls">
            <#if imageDatum.filePath??>
            ${imageDatum.filePath}
            <#else>
                未上传
            </#if>
            </div>
        </div>
    </form>

    <form id="update_image_datum_upload_form" class="form-horizontal" enctype="multipart/form-data"
          action="image_datum_upload.action"
          method="post">
        <div class="control-group">
            <label class="control-label">上传文件</label>

            <div class="controls">
                <input type="hidden" id="update_image_datum_id" name="id" value=""/>
                <input type="file" name="image" id="fileInput" size="1">
                <span class="help-inline m-l-80 red" id="image_datum_input_help"></span>
                <span style="color: #ccc">如需重新上传请选择文件</span>
            </div>
        </div>
    </form>

    <form class="form-horizontal">
        <div class="control-group">
            <label class="control-label"></label>

            <div class="controls">
                <button type="button" class="btn" id="update_image-datum-submit">保存</button>
            </div>
        </div>
    </form>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="../assets/jquery-form-plugin/3.19/jquery.form.js"></script>
<script type="text/javascript">
    $(function () {

        $("#update_image_datum_form").validate({
            rules: {
                'imageDatum.content': {
                    required: true,
                    maxlength: 250
                },
                'imageDatum.media': {
                    required: true,
                    maxlength: 50
                },
                'imageDatum.duration': {
                    required: true,
                    number: true
                },
                'imageDatum.stamp': {
                    required: true
                }
            },
            messages: {
                'imageDatum.content': {
                    required: "请输入内容描述",
                    maxlength: jQuery.format("内容描述不能大于{0}个字符")
                },
                'imageDatum.media': {
                    required: "请输入介质",
                    maxlength: jQuery.format("介质不能大于{0}个字符")
                },
                'imageDatum.stamp': {
                    required: "请选择时间"
                },
                'imageDatum.duration': {
                    required: "请输入时常",
                    number: "请输入数字"
                }
            }
        });

        // 错误提示
        var helpError = function ($help, msg) {
            var content = msg;
            $help.empty().append(content);
        };

        $("#update_image-datum-submit").click(function () {
            if ($("#update_image_datum_form").valid()) {
                var params = $("#update_image_datum_form").serialize();
                params = decodeURIComponent(params, true);
                var url = "archives/imageDatums/do_update.json?" + params;
                $.get(url, function (result) {
                    if (result != null) {
                        var file = $("#fileInput").val();
                        var repairRecordId = $("#update_image_repairRecordId").val();
                        if (typeof(file) != "undefined") {
                            var $imageDatumInputHelp = $("#image_datum_input_help");
                            if (file != "") {
                                if (!/.(avi|mp4|mkv|rm|rmvb|wmv)$/.test(file.toLowerCase())) {
                                    helpError($imageDatumInputHelp, "文件类型必须是avi、mp4、mkv、rm、rmvb、wmv");
                                    return;
                                }
                                $imageDatumInputHelp.empty();
                            }

                            $("#update_image_datum_id").val(result);
                            $('#update_image_datum_upload_form').ajaxSubmit({
                                dataType: 'json',
                                success: function (result) {
                                    if (result != null) {
                                        window.location.href = "archives/index?repairRecordId=" + repairRecordId;
                                    }
                                }
                            });
                        } else {
                            window.location.href = "archives/index?repairRecordId=" + repairRecordId;
                        }
                    }
                });
            }
        });
    });
</script>
</body>
</html>



