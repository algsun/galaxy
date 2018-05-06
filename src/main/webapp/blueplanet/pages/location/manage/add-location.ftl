<#--
位置点添加

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
<script type="text/javascript" src="../assets/jquery-form-plugin/3.19/jquery.form.js"></script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
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

//    $("#locationName").onblur(function () {
//        var $locationName = $("#locationName");
//        if ($locationName.val().trim() === "") {
//            $locationName.next().empty().text(message.inputLocationName);
//            return;
//        }else{
//            $locationName.next().empty().text();
//        }
//    });
//    $("#lng").onblur(function () {
//        var $lng = $("#lng");
//        if($lng.val()>180 || $lng.val()<-180){
//            $lng.next().empty().text(message.notProperLng);
//            return;
//        }else{
//            $lng.next().empty().text();
//        }
//    });
//    $("#lat").onblur(function () {
//        var $lat = $("#lat");
//        if($lat.val()>180 || $lat.val()<-180){
//            $lat.next().empty().text(message.notProperLng);
//            return;
//        }else{
//            $lat.next().empty().text();
//        }
//    });



    function format(state) {
        var imgName = $(state.element).data("mark");
        if (imgName == undefined) {
            return state.text;
        }
        return "<img class='flag' src='./images/icon/" + imgName + ".png'/>" + state.text;
    }

    // todo select2 无数据中文提示
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

//        // todo select2 无数据中文提示
//        $("#relic").select2({
//            placeholder: "请选择文物",
//            formatNoMatches: function () {
//                return message.noData;
//            }
//        });



    $("#submitButton").click(function () {
//        var $locationName = $("#locationName");
//        if ($locationName.val().trim() === "") {
//            $locationName.next().empty().text(message.inputLocationName);
//            return;
//        }else{
//            $locationName.next().empty().text();
//        }
//        var $lng = $("#lng");
//        if($lng.val()>180 || $lng.val()<-180){
//            $lng.next().empty().text(message.notProperLng);
//            return;
//        }else{
//            $locationName.next().empty().text();
//        }
//        var $lat = $("#lat");
//        if($lat.val()>90 || $lat.val()<-90){
//            $lat.next().empty().text(message.notProperLat);
//            return;
//        }else{
//            $locationName.next().empty().text();
//        }
        var relics = $(".relic");
        var array = new Array();
        for (var index = 0; index < relics.length; index++) {
            var $temp = relics[index];
            if ($temp.value != "") {
                var value = $temp.value;
                array.push(value);
            }
        }

        var repeatFlag = false;
        for (var index = 0; index < array.length; index++) {
            if (array[index] == array[index + 1]) {
                window.pnotify("绑定文物重复。", "error");
                repeatFlag = true;
                return;
            }
        }

        if (repeatFlag) {
            return;
        }
        var $locationName=$("#locationName");
        $.getJSON("isExistLocationName", {locationName: $locationName.val()},
                function (data) {
                    if (data.success) {
                        $locationName.next().empty().text(message.locationNameExists);
                    } else {
                        $("#addLocationForm").submit();
                    }
                });
    });

    $("#cancelButton").click(function () {
        window.location.href = "queryLocations";
    });
    //警告提示
//        var alertMsg = function (msg) {
//            var divMsg = "<div class='alert'>" + "<a class='close' data-dismiss='alert' >×</a>" + msg + "</div>";
//            $("#alert").empty().append(divMsg);
//        };

    $(window).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#submitButton").click();
            return false;
        }
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

    var option = {
        placeholder: "请输入文物名称或文物总登记号",
        minimumInputLength: 1,
        ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
            url: "getRelics.json",
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
                console.log(data)
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

    $("#plus").click(function () {
        var temp = '<div class="control-group" id="relic">' +
                ' <label class="control-label">文物</label>' +
                '<div class="controls">' +
                '<input type="hidden" class="relic" name="relic" style="width: 380px;">' +
                '<button type="button" class="btn btn-mini"  style="margin-left:15px;margin-top: 2px;">' +
                '<i class="icon-minus"></i>' +
                '</button>' +
                '</div>' +
                '</div>';
        $("#insert").before(temp);
        $("#insert").prev().find(".relic").select2(option);
    });

    $(".icon-minus").live("click", function () {
        var $this = $(this);
        $this.parent().parent().parent().remove();
    });

    function repoFormatResult(relic) {
        var photo = "";
        if (relic.photos.length > 0) {
            photo = "http://" + window.location.host + "/galaxy-resources/orion/images/" + relic.siteId + "/" + relic.id + "/" + relic.photos[0].path;
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
});
</script>
</#macro>

<#macro content>
    <#include "../../device/device-helper.ftl">
<div class="row-fluid m-t-10">
    <div class="span12">
        <ul class="nav nav-pills">
            <li><a href="toAddLocation">${locale.getStr("blueplanet.location.addLocation")}</a></li>
        </ul>
    </div>
</div>
<#-- 显示消息 -->
    <#if _message??>
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
            <form id="uploadForm" enctype="multipart/form-data" class="form-horizontal" action="upload.action"
                  method="post">
                <div class="control-group">
                    <label class="control-label" for="imageInput">
                    ${locale.getStr("proxima.common.JCV")}
                    </label>

                    <div class="controls">
                        <img id="showImage"
                             src="${photo!}"
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

            <form id="addLocationForm" class="form-horizontal" action="addLocation" method="post">

                <input type="hidden" value="" id="photo" name="photo"/>

                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplanet.location.LocationName")}</label>

                    <div class="controls">
                        <input style="width: 180px;" class="input" id="locationName" name="locationName"
                               type="text" onblur="locationName1();">
                        <span class="help-inline red"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplanet.location.equipmentName")}</label>

                    <div class="controls">
                        <select id="nodeId" name="nodeId" style="width: 200px;">
                            <option></option>
                            <#list deviceTypes as deviceType>
                                <#if deviceType == 7>
                                    <optgroup label="${locale.getStr("common.gateway")}">
                                        <@selectOption 7 "router-16"/>
                                    </optgroup>
                                </#if>
                                <#if deviceType == 2>
                                    <optgroup label="${locale.getStr("common.relay")}">
                                        <@selectOption 2 "relay-16"/>
                                    </optgroup>
                                </#if>
                                <#if deviceType == 1>
                                    <optgroup label="${locale.getStr("common.node")}">
                                        <@selectOption 1 "node-16"/>
                                    </optgroup>
                                </#if>
                                <#if deviceType == 3>
                                    <optgroup label="${locale.getStr("blueplanet.location.masterModule")}">
                                        <@selectOption 3 "master-module-16"/>
                                    </optgroup>
                                </#if>
                                <#if deviceType == 4>
                                    <optgroup label="${locale.getStr("blueplanet.location.slaveModule")}">
                                        <@selectOption 4 "node-16"/>
                                    </optgroup>
                                </#if>
                                <#if deviceType == 5>
                                    <optgroup label="${locale.getStr("common.controlModule")}">
                                        <@selectOption 5 "control-module-16"/>
                                    </optgroup>
                                </#if>
                            </#list>
                        </select>
                    </div>
                </div>

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
                               type="text" maxlength="12" onblur="lng1();">
                        <span class="help-inline red"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplaent.location.dimension")}</label>

                    <div class="controls">
                        <input style="width: 180px;" class="input" id="lat" name="lat"
                               type="text" maxlength="11" onblur="lat1();">
                        <span class="help-inline red"></span>
                    </div>
                </div>
                <div class="control-group" id="remarkDiv">
                    <label class="control-label">${locale.getStr("blueplanet.location.remarks")}</label>

                    <div class="controls">
                        <textarea style="width: 180px;" id="remark" name="remark" maxlength="200"></textarea>
                        <span class="help-inline red"></span>
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

            </form>
        </div>
    </div>
</#macro>

<#macro selectOption devcieType imageName>
    <#list unbindDeviceList as device>
        <#if device.nodeType == devcieType>
            <option value="${device.nodeId}" data-mark="${imageName}">
                <#if device.nodeName??>
                ${device.nodeName}
                <#else>
                ${device.nodeId}
                </#if>
            </option>
        </#if>
    </#list>
</#macro>>



