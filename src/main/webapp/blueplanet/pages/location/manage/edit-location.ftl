<#--
位置点修改

@author xiedeng
@date 2014-06-24
-->

<#assign title=locale.getStr("blueplanet.location.locationManagement")>
<#include "/common/pages/common-tag.ftl">

<#macro head>

<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="../assets/gridster/0.2.1/css/jquery.gridster.css">
<link rel="stylesheet" type="text/css" href="../assets/select2/3.3.1/select2.css">

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
</style>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../assets/jquery-form-plugin/3.19/jquery.form.js"></script>
<script type="text/javascript" src="../assets/jquery-file-upload-9.6.0/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="../assets/jquery-file-upload-9.6.0/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="../assets/jquery-file-upload-9.6.0/js/jquery.iframe-transport.js"></script>

    <@scriptTag "js/pnotify.js"/>
<script type="text/javascript">
    function lng1(){
        var $lng = $("#lng");
        if($lng.val()>180 || $lng.val()<-180){
            $lng.next().empty().text(message.notProperLng);
            return;
        }else{
            $lng.next().empty().text();
        }
    }

    function lat1() {
        var $lat = $("#lat");
        if($lat.val()>90 || $lat.val()<-90){
            $lat.next().empty().text(message.notProperLat);
            return;
        }else{
            $lat.next().empty().text();
        }
    }

    function locationName1(){
        var $locationName = $("#locationName");
        if ($locationName.val().trim() === "") {
            $locationName.next().empty().text(message.inputLocationName);
            return;
        }else{
            $locationName.next().empty().text();
        }
    }
$(function () {
    function format(state) {
        var imgName = $(state.element).data("mark");
        if (imgName == undefined) {
            return state.text;
        }
        return "<img class='flag' src='./images/icon/" + imgName + ".png'/>" + state.text;
    }

    $("#nodeId").select2({
        placeholder: message.pleaseSelectDevice,
        formatResult: format,
        formatSelection: format,
        escapeMarkup: function (m) {
            return m;
        },
        formatNoMatches: function () {
            return message.noData;
        }
    });

    $("#cancelButton").click(function () {
        window.location.href = "queryLocations?query&page=" + ${page};
    });

    $("#submitButton").click(function () {
        var $locationName = $("#locationName");
        $.getJSON("isExistLocationNameExpectSelf", {
                    locationName: $locationName.val(),
                    locationId: $("#locationId").val()
                },
                function (data) {
                    if (data.success) {
                        $locationName.next().empty().text(message.locationNameExists);
                    } else {
                        $("#editLocationForm").submit();
                    }
                });
    });

    //警告提示
    var alertMsg = function (msg) {
        var divMsg = "<div class='alert alert-success'>" + "<a class='close' data-dismiss='alert' >×</a>" + msg + "</div>";
        $("#alert").empty().append(divMsg);
    };

    $(window).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#submitButton").click();
            return false;
        }
    });

    var $filePath = $('#filePath');
    $("#uploadFile").change(function () {
        $filePath.val($(this).val());
    });


    var $uploadFilename = $("#locationId").val();
    var uploads = $("#uploadFile").fileupload({
        autoUpload: true,
        formData: {
            uploadFilename: $uploadFilename
        },//如果需要额外添加参数可以在这里添加
        complete: function (e, result) {
            if (result) {
                alertMsg(message.uploaded);
            }
        }
    });

    $(".icon-remove").click(function () {
        var $this = $(this);
        var id = $this.data("id");
        var locationId = $("#locationId").val();
        console.log("abc" + locationId + "cba");
        $.get("deleteLocationRelic", {id: id, locationId: locationId}, function (result) {
            if (result) {
                $this.parent().remove();
                window.pnotify("删除成功", "info");
            } else {
                window.pnotify("删除失败", "error");
            }
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
            dataType: 'json',
            success: function (result) {
                $("#photo").val(result.imageFileName);
                $("#showImage").attr("src", result.filePath + "/" + result.imageFileName + "?t=" + new Date().getTime());
            }
        });
    });

    // 错误提示
    var helpError = function ($help, msg) {
        var content = msg;
        $help.empty().append(content);
    };

    var locationId = $("#locationId").val();
    var option = {
        placeholder: "请输入文物名称或文物总登记号",
        minimumInputLength: 1,
        ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
            url: "getRelicsEditLocation.json?locationId=" + locationId,
            dataType: 'json',
            quietMillis: 1000,
            data: function (term, page) {
                return {
                    relicNameTotalCode: term, // search term
                    pageSize: 5,  //一次性加载的数据条数
                    pageIndex: page, //页码
                    time: new Date()//测试
                };
            },
            results: function (data, page) { // parse the results into the format expected by Select2.
                // since we are using custom formatting functions we do not need to alter the remote JSON data
                var more = (page * 5) < data.total_count;
                return { results: data.relics, more: more  };
            },
            cache: true
        },
        formatResult: repoFormatResult, // omitted for brevity, see the source of this page
        formatSelection: repoFormatSelection,  // omitted for brevity, see the source of this page
//            dropdownCssClass: "bigdrop", // apply css that makes the dropdown taller
        formatInputTooShort: function () {
            return '至少输入一个字符';
        },
        formatLoadMore: function () {
            return '更多'
        },
        formatSearching: function () {
            return '正在加载，请等待...'
        },
        formatNoMatches: function () {
            return '无结果，请重新输入'
        },
        escapeMarkup: function (m) {
            return m;
        } // we do not want to escape markup since we are displaying html in results
    };


    function repoFormatResult(relic) {
        var photo = "";
        if (relic.photos.length > 0) {
            photo = "http://"+ window.location.host+"/galaxy-resources/orion/images/" + relic.siteId + "/" + relic.id + "/" + relic.photos[0].path;
        }
        var markup = '<div class="row-fluid">' +
                '<div class="span3"><img style="max-height: 70%;max-width: 70%;" src="' + photo + '" /></div>' +
                '<div class="span9 m-t-10">' +
                '<div class="row-fluid">' +
                '<div class="span4">名称</div>' +
                '<div class="span4">登记号</div>' +
                '<div class="span4">时代</div>' +
                '</div>' +
                '<div class="row-fluid">' +
                '<div class="span4">' + relic.name + '</div>' +
                '<div class="span4">' + relic.totalCode + '</div>' +
                '<div class="span4">' + relic.era.name + '</div>' +
                '</div>';
        markup += '</div></div>';
        return markup;
    }

    function repoFormatSelection(relic) {
        return relic.name + "(" + relic.totalCode + ")";
    }

    $(".relic").select2(option);

    $("#plus").click(function () {

        var temp = '<div class="control-group" id="relic">' +
                ' <label class="control-label">文物</label>' +
                '<div class="controls">' +
                '<input type="hidden" class="relic" name="relic" style="width: 380px;">' +
                '<button type="button" class="btn btn-mini"  style="margin-left:15px;margin-top: 2px;">' +
                '<i class="icon-minus add"></i>' +
                '</button>' +
                '</div>' +
                '</div>';
        $("#insert").before(temp);
        $("#insert").prev().find(".relic").select2(option);
    });

    $(".icon-minus, .add").live("click", function () {
        var $this = $(this);
        $this.parent().parent().parent().remove();
    });
});


</script>
</#macro>

<#macro content>
    <#include "../../device/device-helper.ftl">
<div class="row-fluid m-t-10">
    <div class="span12">
        <ul class="nav nav-pills">
            <li><a href="toEditLocation?locationId=${locationId}&page=${page}<#if nodeId??>&nodeId=${nodeId}</#if>">修改位置点</a>
            </li>
        </ul>
    </div>
</div>
<#-- 显示消息 -->
    <#if _message?? >
        <#if _success>
        <div class="alert alert-success">
        <#else>
        <div class="alert alert-error">
        </#if>
        <a class="close" data-dismiss="alert">×</a>
    ${_message}
    </div>
    </#if>
    <div id="alert"></div>

    <div class="row-fluid m-t-10">
        <div class="span7">
            <input name="excludeIds" id="excludeIds" type="hidden"/>

            <form id="uploadForm" enctype="multipart/form-data" class="form-horizontal" action="upload.action"
                  method="post">
                <input type="hidden" id="locationId" name="locationId" value="${locationId}"/>

                <div class="control-group">
                    <label class="control-label" for="imageInput">
                    ${locale.getStr("proxima.common.JCV")}
                    </label>

                    <div class="controls">
                        <img id="showImage"
                             src="${filePath!}${location.photo!}"
                             style="width: 120px;height: 120px;">

                        <div class="m-t-10">
                                    <span id="uploadImg">
                                        <input type="file" name="image" id="imageInput" size="1">
                                        <a href="#" class="btn btn-mini">
                                        ${locale.getStr("blueplanet.common.updateImage")}
                                        </a>
                                    </span>

                            <span class="help-inline m-l-80 red" id="imageInput-help"></span>
                        </div>
                    </div>
                </div>
            </form>

            <form id="editLocationForm" class="form-horizontal" action="editLocation" method="post">
                <input type="hidden" name="page" value="${page}">
                <input type="hidden" name="zoneId" value="${zoneId!}">
                <input type="hidden" name="unbindNodeId" value="${nodeId!}">
                <fieldset>
                    <div class="control-group">
                        <label class="control-label">${locale.getStr("blueplanet.location.LocationName")}</label>

                        <div class="controls">
                            <input style="width: 180px;" class="input" id="locationName" name="locationName"
                                   type="text" value="${location.locationName}" onblur="locationName1();">
                            <span class="help-inline red"></span>
                            <input type="hidden" value="${locationId}" id="locationId" name="locationId"/>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">${locale.getStr("blueplanet.location.equipmentName")}</label>

                        <div class="controls">
                            <select id="nodeId" name="nodeId" style="width: 200px;">
                                <option value="unbind">${locale.getStr("blueplanet.location.unbindedEquipment")}</option>
                                <#list deviceTypes as deviceType>
                                    <#if deviceType == 7>
                                        <optgroup label="${locale.getStr("common.gateway")}">
                                            <@selectOption 7 "router-16" nodeId/>
                                        </optgroup>
                                    </#if>
                                    <#if deviceType == 2>
                                        <optgroup label="${locale.getStr("common.relay")}">
                                            <@selectOption 2 "relay-16"  nodeId/>
                                        </optgroup>
                                    </#if>
                                    <#if deviceType == 1>
                                        <optgroup label="${locale.getStr("common.node")}">
                                            <@selectOption 1 "node-16" nodeId/>
                                        </optgroup>
                                    </#if>
                                    <#if deviceType == 3>
                                        <optgroup label="${locale.getStr("blueplanet.location.masterModule")}">
                                            <@selectOption 3 "master-module-16" nodeId/>
                                        </optgroup>
                                    </#if>
                                    <#if deviceType == 4>
                                        <optgroup label="${locale.getStr("blueplanet.location.slaveModule")}">
                                            <@selectOption 4 "node-16" nodeId/>
                                        </optgroup>
                                    </#if>
                                    <#if deviceType == 5>
                                        <optgroup label="${locale.getStr("blueplanet.location.controlModule")}">
                                            <@selectOption 5 "control-module-16" nodeId/>
                                        </optgroup>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <#if relics?size != 0 >
                        <div class="control-group">
                            <label class="control-label">
                            ${locale.getStr("blueplaent.location.bindingRelic")}
                            </label>

                            <div class="controls">
                                <#list relics as relic>
                                    <button class="btn btn-small m-5" type="button">
                                    ${relic.name}(${relic.totalCode})
                                        <i data-id="${relic.locationRelicId?c}" data-locationid="${location.id}"
                                           data-action-type="0"
                                           class="icon-remove"></i>
                                    </button>
                                </#list>
                            </div>
                        </div>
                    </#if>

                    <#if orion>
                        <div class="control-group" id="relic">
                            <label class="control-label">
                            ${locale.getStr("blueplaent.location.relic")}
                            </label>

                            <div class="controls">
                                <input type="hidden" class="relic" name="relic" style="width: 380px;">
                                <button type="button" class="btn btn-mini m-l-10" id="plus" style="margin-top: 2px;">
                                    <i class="icon-plus"></i>
                                </button>
                            </div>
                        </div>
                    </#if>

                    <div class="control-group" id="insert">
                        <label class="control-label">${locale.getStr("blueplaent.location.longitude")}</label>

                        <div class="controls">
                            <input style="width: 180px;" class="input" id="lng" name="lng"
                                   type="text" value="<#if location.lng??>#{location.lng;M7}</#if>" maxlength="12" onblur="lng1();">
                            <span class="help-inline red"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">${locale.getStr("blueplaent.location.dimension")}</label>

                        <div class="controls">
                            <input style="width: 180px;" class="input" id="lat" name="lat"
                                   type="text" value="<#if location.lat??>#{location.lat;M7}</#if>" maxlength="11" onblur="lat1();">
                            <span class="help-inline red"></span>
                        </div>
                    </div>

                    <div class="control-group" id="remarkDiv">
                        <label class="control-label">${locale.getStr("blueplanet.location.remarks")}</label>

                        <div class="controls">
                            <textarea style="width: 180px;" id="remark" name="remark"
                                      maxlength="200">${location.remark!}</textarea>
                            <span class="help-inline red"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">${locale.getStr("blueplanet.location.uploadDate")}</label>

                        <div class="controls">
                            <div class="input-append">
                                <input id="filePath" class="input-large" type="text" readonly="readonly"
                                       style="width: 150px;">
                                <a class="btn btn-info"
                                   onclick="$('input[id=uploadFile]').click();">${locale.getStr("blueplanet.location.uploadFile")}</a>
                            </div>
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="controls">
                            <button class="btn btn-primary" type="button" id="submitButton" class="btn"
                                    style="margin-left: 20px;">
                            ${locale.getStr("common.save")}
                            </button>
                            <button type="button" id="cancelButton" class="btn"
                                    style="margin-left: 20px;">${locale.getStr("common.return")}
                            </button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
    <input id="uploadFile" name="uploadFile" type="file" style="display:none" data-url="uploadLocationDataFile">
</#macro>

<#macro selectOption devcieType imageName deviceId>
    <#list unbindDeviceList as device>
        <#if device.nodeType == devcieType>
            <option value="${device.nodeId}" data-mark="${imageName}"
                    <#if device.nodeId == deviceId>selected="true"</#if>>
                <#if device.nodeName??>
                ${device.nodeName}
                <#else>
                ${device.nodeId}
                </#if>
            </option>
        </#if>
    </#list>
</#macro>>
