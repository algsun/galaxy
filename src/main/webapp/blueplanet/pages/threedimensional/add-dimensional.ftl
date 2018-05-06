<#--
3d模型管理
@author 王耕
@date 2015.06.12

-->

<#assign title>${locale.getStr("blueplanet.threeDimensional.title")}</#assign>
<#include "/common/pages/common-tag.ftl"/>
<#-- 当前权限标识 -->
<#assign currentPrivilege = "blueplanet:monitor:threedimensional">
<#macro head>
<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
<style type="text/css">

    .fileupload-box {
        position: relative;
    }

    .fileupload-txt {
        height: 22px;
        border: 1px solid #cdcdcd;
        width: 260px;
    }

    .fileupload-file {
        position: absolute;
        top: 0;
        left: 0;
        height: 24px;
        filter: alpha(opacity:0);
        opacity: 0;
        width: 260px;
    }
</style>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span12">
        <div class="page-header title">
            <h3>
                <span class="f-n">${locale.getStr("blueplanet.threeDimensional.newModel")}</span>
            </h3>
        </div>
    </div>
</div>
<div class="row-fluid">
    <form id="uploadForm" class="form-horizontal" action="three-dimensional/uploadFile" method="post"
          enctype="multipart/form-data" name="uploadfileform">
        <input type="hidden" name="dimensionalId" value=""/>

        <div class="control-group">
            <label class="control-label">${locale.getStr("blueplanet.threeDimensional.describe")}</label>

            <div class="controls">
                <textarea name="remark" id="" cols="180" rows="10" style="width:380px"></textarea>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">${locale.getStr("common.location")}</label>

            <div class="controls">
                <@selectOption locationMap/>
                <span class="help-block red" id="designeeSpan"></span>
                <span class="help-inline gray" id="span3">${locale.getStr("blueplanet.threeDimensional.requiredOptions")}</span> <br>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">${locale.getStr("blueplanet.threeDimensional.selectionModel")}</label>

            <div class="controls fileupload-box" id="fileupload-box">
                <input type='text' name='hideFileName' id='textfield' class='fileupload-txt m-b-0'
                       readonly="true"/>
                <input type="file" name="srcUploadFile" class="fileupload-file" id="fileField" size="28"
                       onchange="document.getElementById('textfield').value=this.value"/>

                <div><span class="help-inline red" id="uploadfilebtn"></span>
                </div>


                <span class="help-inline gray" id="span1">${locale.getStr("blueplanet.threeDimensional.modeSize")}</span> <br>
                <span class="help-inline gray" id="span2">${locale.getStr("blueplanet.threeDimensional.modeType")}</span>
                <div class="m-t-20">
                    <button class="upload-file-btn btn btn-small btn-primary" type="button" id="upload-file-btn-submit">
                        ${locale.getStr("common.save")}
                    </button>
                    <a class="upload-file-btn btn btn-small" href="three-dimensional" id="upload-file-btn">
                        ${locale.getStr("common.return")}
                    </a>
                </div>
            </div>
        </div>
    </form>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../common/js/form-check.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#locationIds").select2();
        $("#upload-file-btn-submit").click(function () {
            var $spantext = $("#uploadfilebtn");
            var $uploadfilebtn = $("#fileField");
            var locationIds = $("#locationIds").val();
            var flag = true;
            if (locationIds == null || locationIds == "") {
                App.helpError($("#designeeSpan"), "*" + message.locationEmpty);
                flag = false;
            } else {
                App.helpClear($("#designeeSpan"));
            }

            var file = $uploadfilebtn.val();
            if (file == null || file == "") {

                App.helpError($spantext, message.modelCannotBeEmpty);
                flag = false;
            } else {
                var length = $("#uploadForm")[0][5].files[0].size;
                if (file != "" && !/.(obj|stl|vtk)$/.test(file.toLowerCase())) {
                    App.helpError($spantext, message.modelTypeLimit);
                    flag = false;
                } else if (length >= 200000000) {
                    App.helpError($spantext, message.modelSizeLimit);
                    flag = false;
                } else {
                    App.helpClear($spantext);

                }
            }
            var ajaxFile = {"hideFileName": file};
            var url = "three-dimensional/validateThreeDimensionalPath";
            $.get(url, ajaxFile, function (result) {
                if (result) {
                    if (flag) {
                        $("#uploadForm").submit();
                    }
                    ;
                } else {
                    App.helpError($spantext, message.modelExists);
                }
                ;
            });
        });
    });
</script>
</#macro>
<#macro selectOption locationMap>
<select multiple='' style="width: 300px;margin-left: 8px;" name="locationIds" id="locationIds">
    <#if locationMap?size != 0>
        <#list locationMap?keys as key>
        <optgroup label="${key}">
            <#list locationMap[key] as location>
                <option value="${location.id}">${location.locationName}</option>
            </#list>
        </#list>
    </#if>
</select>
</#macro>

