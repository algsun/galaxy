<#--
文物出入库记录

@author wanggeng
@time  13-8-1
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>文档上传 - 资产管理</title>
<#include "../_common/common-css.ftl">
    <#-- TODO 样式-->
    <style type="text/css">
        <!--
        .fileupload-box {
            position: relative;
            text-align: center;
        }

        .fileupload-btn {
            background-color: #FFF;
            border: 1px solid #CDCDCD;
            height: 24px;
            width: 90px;
        }

        .fileupload-txt {
            height: 22px;
            border: 1px solid #cdcdcd;
            width: 260px;
        }

        .fileupload-file {
            position: absolute;
            top: 0;
            right: 380px;
            height: 24px;
            filter: alpha(opacity:0);
            opacity: 0;
            width: 260px;
        }

        -->
    </style>
</head>
<body>
<#assign currentTopPrivilege = "orion:stockManage">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "orion:stockManage:query"></@subNavs>
<#-- TODO 目前有多个提示信息的工具，重构为一个，并统一风格 -->
<#include "/common/pages/message-tooltip.ftl">
<@messageTooltip/>

    <fieldset>
        <legend>
            <a class="go-back" href="queryStockInAndOut.action"><i class="mw-icon-prev"></i>挂接文档上传</a>
        </legend>
    </fieldset>

<#include "stock-helper.ftl">

    <div class="row">
        <div class="span12">
        <#include "view-helper.ftl">
            <div class="m-l-0 m-b-20 m-t-10">
                <strong>备注:</strong><span class="extendMsg" style="width: 100%">${outEvent.remark!}</span>
            </div>
            <div class="m-t-30">
                <fieldset>
                    <legend>
                        <h4>文档</h4>
                    </legend>
                </fieldset>
            </div>
            <div>
                <table class="table table-bordered table-striped table-center">
                    <thead>
                    <th style="width: 50px;">序号</th>
                    <th style="width: 150px;">上传人员</th>
                    <th style="width: 380px;">文档名称</th>
                    <th style="width: 100px;">文档上传日期</th>
                    <th style="width: 100px">操作</th>
                    </thead>
                    <tbody>
                    <#if outEventAttachmentList?size != 0>
                        <#list outEventAttachmentList as outEventAttachment>
                        <tr>
                            <td>${outEventAttachment_index+1}</td>
                            <td>${outEventAttachment.user.userName}</td>
                            <td style="text-align: left"><a
                                    href="downloadDocFile.action?downloadFileId=${outEventAttachment.id}&outEventId=${outEventAttachment.outEvent.id}">${outEventAttachment.path}</a>
                            </td>
                            <td>${outEventAttachment.date?string("yyyy-MM-dd")}</td>
                            <td>
                                <#if Session.currentUser.id == outEventAttachment.user.id>
                                    <a class="btn btn-mini btn-danger"
                                       href="deleteDoc.action?attachmentId=${outEventAttachment.id}&outEventId=${outEventAttachment.outEvent.id}"><i class="icon-stop icon-white"></i>删除</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
                <form class="form-inline m-b-0" action="uploadDocFile.action?outEventId=${outEvent.id}" method="post"
                      enctype="multipart/form-data" name="uploadfileform">
                    <div class="fileupload-box m-t-20" id="fileupload-box">
                        <input type='button' class='fileupload-btn m-b-10' value='选择文件'/>
                            <input type='text' name='hideFileName' id='textfield' class='fileupload-txt m-b-0'
                                   readonly="true"/>
                            <input type="file" name="srcUploadFile" class="fileupload-file" id="fileField" size="28"
                                   onchange="document.getElementById('textfield').value=this.value"/>

                            <div><span class="help-inline red">*单个挂接文档上传不能超过5M</span>
                            </div>
                            <div><span class="help-inline red" id="uploadfilebtn"></span>
                            </div>
                            <button class="upload-file-btn btn btn-small btn-primary f-r"
                                    id="upload-file-btn">上传
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">
<script type="text/javascript" src="../common/js/form-check.js"></script>
<script type="text/javascript">
    $(function () {

        (function () {
            $(document).on('click', '.upload-file-btn', function () {
                var flag = true;
                var $spantext = $("#uploadfilebtn");
                var $uploadfilebtn = $("#fileField");
                var file = $uploadfilebtn.val();
                if (file == "") {
                    App.helpError($spantext, "上传挂载文件不能为空");
                    flag = false;
                } else {
                    App.helpOk($spantext, '');
                }
                return flag;
            });
        })();
    });
</script>

<#include  "../_common/last-resources.ftl">
</body>
</html>