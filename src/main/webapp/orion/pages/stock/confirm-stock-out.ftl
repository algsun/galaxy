<#--
文物了入库记录

@author gaohui
@time  13-5-28
@check xiedeng 2013-6-6 15:24 svn:3974
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>出库确认 - 资产管理</title>
<#include "../_common/common-css.ftl">
    <#--TODO 样式 -->
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

<#include "/common/pages/message-tooltip.ftl">
<@messageTooltip/>

    <fieldset>
        <legend>
            <a class="go-back" href="queryStockInAndOut.action"><i class="mw-icon-prev"></i>出库确认</a>
        </legend>
    </fieldset>

<#include "stock-helper.ftl">

    <div class="row">
        <div class="span12">
        <#include "view-helper.ftl">
            <table class="table table-bordered table-center">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>总登记号</th>
                    <th>名称</th>
                    <th>级别</th>
                    <th>质地</th>
                    <th>件数</th>
                    <th>库存状态</th>
                    <th>核对状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list outEvent.eventRelics as eventRelic>
                <tr data-event-relic-id=${eventRelic.id?c} <@_stateClass eventRelic.state/> data-state=
                "${eventRelic.state}">
                <td>${eventRelic_index + 1}</td>
                <td>${eventRelic.relic.totalCode} <#if eventRelic.relic.hasTag><i class="icon-tag"></i></#if></td>
                <td>${eventRelic.relic.name}</td>
                <td>${eventRelic.relic.level.name}</td>
                <td>${eventRelic.relic.texture.name}</td>
                <td>${eventRelic.relic.count}</td>
                <td><@_stateName eventRelic.relic.state/></td>
                <td data-type="event"><@_eventRelicStateName eventRelic.state/></td>
                <td data-type="buttons">
                    <@_eventRelicStateButton eventRelic.state/>
                </td>
                </tr>
                </#list>
                </tbody>
            </table>
            <div class="m-l-0 m-b-20 m-t-0">
                <strong>备注:</strong><span class="extendMsg" style="width: 100%">${outEvent.remark!}</span>
            </div>

            <h4 class="m-t-30">文档</h4>
            <hr class="m-v-10"/>
            <table class="table table-bordered table-striped table-center">
                <thead>
                <th style="width: 30px;">序号</th>
                <th style="width: 150px;">上传人员</th>
                <th style="width: 400px;">文档名称</th>
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
                                   href="deleteDocConfirm.action?attachmentId=${outEventAttachment.id}&outEventId=${outEventAttachment.outEvent.id}&taskId=${taskId}"><i class="icon-stop icon-white"></i>删除</a>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>

            <form class="form-inline m-b-0" action="uploadDocFileConfirm.action?outEventId=${outEvent.id}&taskId=${taskId}" method="post"
                  enctype="multipart/form-data">
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
                    <button class="upload-file-btn btn btn-small"
                            id="upload-file-btn">上传
                    </button>
                </div>
            </form>

            <#--<div class="red">
                <span>* 单个挂接文档上传不能超过5M</span>
                <span id="uploadfilebtn"></span>
            </div>-->

            <div class="row">
                <div class="span12">
                    <div class="pull-right">
                    <a id="approveButton" class="btn btn-primary <#if !couldCheck>disabled</#if>"
                       data-href="doConfirmStockOut.action?outEventId=${outEventId}&taskId=${taskId}"
                            >通过</a>
                    <a class="btn btn-primary"
                       href="${_completeTaskUrl(taskId, "rejectStockOut.action?outEventId=" + outEventId, [{"name":"stockOutConfirmApproved","value":"false", "type":"boolean"}])}"
                            >不通过</a>
                    </div>
                </div>
            </div>

            <p class="help-block m-t-20 t-a-r">
                友情提示：有电子标签的文物可通过手持机进行扫描确认。
            </p>
        </div>
    </div>
</div>

<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">
<script type="text/javascript" src="../common/js/form-check.js"></script>
<script type="text/javascript">
    $(function () {
        (function () {
            var updateRow = function ($row, toState) {
                $row.attr("data-state", toState);
                $row.removeClass('success');
                if (toState == 1) {
                    $row.addClass("success");
                    $row.find('button[data-type="exist"]').remove();
                    $row.find('td[data-type="buttons"]').append('<button data-type="re-choose" class="btn btn-small">重新确认</button>');
                    $row.find('td[data-type="event"]').empty().text('确认在库 √');
                } else if (toState == 0) {
                    $row.find('button[data-type="re-choose"]').remove();
                    $row.find('td[data-type="buttons"]')
                            .append('<button data-type="exist" class="btn btn-small btn-success"><i class="icon-ok icon-white"></i> 确认在库</button>');
                    $row.find('td[data-type="event"]').empty().text('未确认 ?');
                }
            };

            $(document).on('click', 'button[data-type="exist"]', function () {
                var $row = $(this).parents('tr');
                var eventRelicId = $row.attr('data-event-relic-id');
                $.get("updateEventRelicState.action", {eventRelicId: eventRelicId, state: 1}, function (result) {
                    if (result.success) {
                        updateRow($row, 1);
                        updateAgreeButtonState();
                    }
                });
            });

            $(document).on('click', 'button[data-type="re-choose"]', function () {
                var $row = $(this).parents('tr');
                var eventRelicId = $row.attr('data-event-relic-id');
                $.get('updateEventRelicState.action', {eventRelicId: eventRelicId, state: 0, resetDate: true}, function () {
                    updateRow($row, 0);
                    updateAgreeButtonState();
                });
            });

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

        function updateAgreeButtonState() {
            if ($('tr[data-state="0"]').length > 0) {
                $('#approveButton').addClass('disabled').removeAttr("href");
            } else {
                var href = $('#approveButton').removeClass('disabled').attr("data-href");
                $('#approveButton').attr("href", href);
            }
        }

        updateAgreeButtonState();
    });
</script>

<#include  "../_common/last-resources.ftl">
</body>
</html>
<#macro _stateClass state>
    <#switch state>
        <#case 1>
        class="success"
            <#break>
        <#case 0>
        class=""
            <#break>
    </#switch>
</#macro>

<#macro _eventRelicStateName state>
    <#switch state>
        <#case 1>
        确认在库 √
            <#break>
        <#case 0>
        未确认 ?
            <#break>
    </#switch>
</#macro>

<#macro _eventRelicStateButton state>
    <#switch state>
        <#case 1>  <#-- 在库 -->
        <button class="btn btn-small" data-type="re-choose"> 重新确认</button>
            <#break>
        <#case 0>  <#-- 未确认 -->
        <button class="btn btn-small btn-success" data-type="exist"><i class="icon-ok icon-white"></i> 确认在库</button>
            <#break>
    </#switch>
</#macro>
