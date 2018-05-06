<#--
布局配置 - 数据中心

@author wang.geng
@date 2014-01-22
-->

<#assign title=locale.getStr("blueplanet.dataCenter.layoutConfiguration")>
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
<style type="text/css">

    .fileupload-box {
        position: relative;
        text-align: center;
    }

    .fileupload-txt {
        height: 22px;
        border: 1px solid #cdcdcd;
        width: 260px;
    }

    .fileupload-btn {
        background-color: #FFF;
        border: 1px solid #CDCDCD;
        height: 24px;
        width: 90px;
    }

    .fileupload-file {
        position: absolute;
        top: 0;
        right: 320px;
        height: 24px;
        filter: alpha(opacity:0);
        opacity: 0;
        width: 260px;
    }
</style>
</#macro>

<#macro content>
<div class="row">
    <div class="offset3 span12 m-t-10">
        <fieldset>
            <legend>
                <a href="dataCenter/charts/listLayout" style="height: 24px;" title="${locale.getStr("common.return")}">
                    <i class="mw-icon-prev"></i>
                    <span style="font-size: 18px;">${locale.getStr("blueplanet.dataCenter.layoutSetting")}</span>

                </a>
            </legend>
        </fieldset>
        <div class="span8 m-t-40">
            <form action="dataCenter/charts/uploadBgImage" method="post" enctype="multipart/form-data"
                  name="uploadfileform" class="form-horizontal">
                <div style="margin-left: 330px">
                    <#if url == "">
                        <div style="background: #004756;width: 200px;height: 200px;">
                        </div>
                    <#else>
                        <img src="${picturesBasePath}/${layoutId}.jpg" style="width: 200px;height: 200px;" alt=""/>
                    </#if>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="fileupload-box m-t-20" id="fileupload-box">
                            <input type="hidden" name="layoutId" value="${layoutId}"/>
                            <input type='button' class='fileupload-btn m-b-10'
                                   value='${locale.getStr("blueplanet.dataCenter.selectImages")}'/>
                            <input type='text' name='hideFileName' id='textfield' class='fileupload-txt m-b-0'
                                   readonly="true"/>
                            <input type="file" name="srcUploadFile" class="fileupload-file" id="fileField" size="28"
                                   onchange="document.getElementById('textfield').value=this.value"/>

                            <div><span id="uploadphoto" class="help-inline red"></span></div>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <a class="btn btn-small btn-success f-r m-l-10"
                           href="dataCenter/charts/reset/${layoutId}">${locale.getStr("blueplanet.dataCenter.reset")}</a>
                        <button class="upload-file-btn btn btn-small btn-primary f-r" type="submit"
                                id="upload-file-btn">
                        ${locale.getStr("blueplanet.dataCenter.upload")}
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../common/js/form-check.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript">
    (function () {
        $(document).on('click', '#upload-file-btn', function () {
            var flag = true;
            var $spantext = $("#uploadphoto");
            var $imageInput = $("#fileField");
            var file = $imageInput.val();
            if (file == "") {
                App.helpError($spantext, message.pictureCannotBeEmpty);
                flag = false;
            } else if (!/.(gif|jpg|jpeg|png)$/.test(file.toLowerCase())) {
                App.helpError($spantext, message.fileTypeLimit);
                flag = false;
            } else {
                App.helpOk($spantext, '');
            }

            return flag;
        });
    })();
</script>
</#macro>