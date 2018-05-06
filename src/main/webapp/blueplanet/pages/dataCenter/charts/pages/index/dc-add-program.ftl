<#--
节目管理 - 数据中心

@author wang.geng
@date 2014-2-7
-->

<#assign title=locale.getStr("blueplanet.dataCenter.programManagement")>
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
<meta charset="utf-8" xmlns="http://www.w3.org/1999/html">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" type="text/css" href="../assets/select2/3.3.1/select2.css">
<style type="text/css">

    .fileupload-box {
        position: relative;
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
                <a class="go-back" href="dataCenter/charts/index/slide/toSlideList/${uuid}/${itemId}"
                   title="${locale.getStr("common.return")}">
                    <i class="mw-icon-prev"></i>
                ${locale.getStr("blueplanet.dataCenter.slideshowList")}
                </a>
            </legend>
        </fieldset>
        <div class="span7">
            <form id="programFrom" class="form-horizontal"
                  action="dataCenter/charts/index/slide/addSlide/${uuid}/${itemId}" method="post"
                  enctype="multipart/form-data">
                <input type="hidden" value="${dcSlidePO.id!0}" name="dcSlidePOId"/>

                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplanet.dataCenter.title")}：</label>

                    <div class="controls">
                        <input type="text" name="slideTitle" id="slideTitle" value="${dcSlidePO.title!}">
                        <span id="slideTitle-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label radio inline">${locale.getStr("blueplanet.dataCenter.playbackDuration")}
                        ：</label>

                    <div class="controls">
                        <label class="radio inline">
                            <input id="slidePlayTime10" name="slidePlayTime" type="radio"
                                   value="10" <@checked 10,dcSlidePO.refresh/>>10秒
                        </label>
                        <label class="radio inline">
                            <input id="slidePlayTime20" name="slidePlayTime" type="radio"
                                   value="20" <@checked 20,dcSlidePO.refresh/>>20秒
                        </label>
                        <label class="radio inline">
                            <input id="slidePlayTime30" name="slidePlayTime" type="radio"
                                   value="30" <@checked 30,dcSlidePO.refresh/>>30秒
                        </label>
                    </div>
                </div>
                <input type="hidden" id="site" value="${siteId!}">
                <input type="hidden" id="locationSiteId" value="${locationSiteId!}">
                <input type="hidden" id="locationId" value="${dcSlidePO.locationId!}">
                <#if !siteId??>
                    <div class="control-group">

                        <label class="control-label">${locale.getStr("blueplanet.dataCenter.site")}：</label>

                        <div class="controls">
                            <select id="siteId" name="siteId" style="width: 220px;">
                                <#if siteVOList??>
                                    <#list siteVOList as siteVO>
                                        <option <#if locationSiteId! == siteVO.siteId>selected="true" </#if>
                                                value="${siteVO.siteId}">${siteVO.logicGroupName}</option>
                                    </#list>
                                </#if>
                            </select>
                            <span style="color: red" id="siteId-help"></span>
                        </div>
                    </div>
                </#if>
                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplanet.dataCenter.location")}：</label>

                    <div class="controls">
                        <select id="locationIdselecter" name="locationId" style="width: 220px;">
                        <#--<#if zoneLocationList?? && zoneLocationList?size != 0>-->
                                <#--<#list zoneLocationList as zoneLocation>-->
                                    <#--<optgroup label="${zoneLocation.zoneName}">-->
                                        <#--<#if zoneLocation.locationList?? && zoneLocation.locationList?size != 0>-->
                                            <#--<#list zoneLocation.locationList as location>-->
                                                <#--<option value="${location.id}"-->
                                                        <#--<#if dcSlidePO.locationId! == location.id>selected="true" </#if> >-->
                                                        <#--${location.locationName!}-->
                                                <#--</option>-->
                                            <#--</#list>-->
                                        <#--</#if>-->
                                    <#--</optgroup>-->
                                <#--</#list>-->
                            <#--</#if>-->
                        </select>
                        <span style="color: red" id="locationId-help"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplanet.dataCenter.description")}：</label>

                    <div class="controls">
                        <textarea cols="10" rows="4" name="slideRemark" id="slideRemark"
                                  style="width: 320px; margin: 0px; height: 85px;"/>${dcSlidePO.detail!}</textarea>
                        <span id="slideRemark-help" class="help-inline"></span>
                    </div>
                </div>
                <#if dcSlidePO.id != 0>
                    <div class="control-group">
                        <div class="controls">
                            <img id="uploadImg" style="width: 200px;height: 200px;"
                                 src="${picturesBasePath}/${dcSlidePO.url!}" alt=""/>
                        </div>
                    </div>
                </#if>
                <div class="control-group">
                    <div class="controls">
                        <div class="fileupload-box m-t-20" id="fileupload-box">
                            <input type='button' class='fileupload-btn m-b-10'
                                   value='${locale.getStr("blueplanet.dataCenter.selectImages")}'/>
                            <input type='text' name='hideFileName' id='textfield' class='fileupload-txt m-b-0'
                                   readonly="true" value="${dcSlidePO.url!}"/>
                            <input type="file" name="srcUploadFile" class="fileupload-file" id="fileField" size="28"
                                   onchange="uploadOnChange()"/>
                            <span id="" class="help-inline"></span>
                        </div>
                    </div>
                </div>

                <div class="control-group">
                    <div class="controls">
                        <div class="m-t-20">
                            <input type="button" id="programBtn" class="btn btn-small btn-primary f-l m-r-10"
                                   value="${locale.getStr("common.save")}"/>
                            <input type="reset" class="btn btn-small f-l"/>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="span4 muted" style="margin-top: 120px">
            <span>${locale.getStr("common.tips")}：</span>
            <ul>
                <li>${locale.getStr("blueplanet.dataCenter.titleTips")}</li>
                <li>${locale.getStr("blueplanet.dataCenter.descriptionTips")}</li>
                <li>${locale.getStr("blueplanet.dataCenter.imageTips")}</li>
            </ul>
        </div>
    </div>
</div>
</#macro>
<#macro script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.11.1/dist/jquery.validate.min.js"></script>
<script type="text/javascript">
    function uploadOnChange() {
        var value = $("#fileField").val();
        $("#textfield").val(value);
    }
    function findZoneLocationBySiteId() {
        var site = $("#site").val();
        if (site == "") {
            site = $("#siteId").val();
        }
        var locationId = $("#locationId").val();
        var str = "";
        $.get("dataCenter/charts/index/slide/findZoneLocationById/" + site, function (result) {
            for (var zoneLocation in result) {
                str += '<optgroup label="' + result[zoneLocation].zoneName + '">'
                for (var location in result[zoneLocation].locationList) {
                    var location = result[zoneLocation].locationList[location];
                    if (locationId == location.id) {
                        str += ' <option selected="selected" value="' + location.id + '">';
                    } else {
                        str += ' <option value="' + location.id + '">';
                    }
//                        str += ' <option value="' + location.id + '">';
                    var nodeName = location.locationName;
                    str += nodeName + "</option>";
                }
                str += "</optgroup>";
            }
            $("#locationIdselecter").html(str).select2({
                formatNoMatches: function () {
                    return message.noData;
                }
            });
        });

    }
    $(function () {
        var programFrom = $("#programFrom").validate({
            rules: {
                slideTitle: {required: true, maxlength: 25},
                slideRemark: {required: true, maxlength: 200},
                hideFileName: {required: true}
            },
            messages: {
                slideTitle: {required: message.inputTitle, maxlength: message.upTo25Characters},
                slideRemark: {required: message.inputRemark, maxlength: message.upTo200Characters},
                hideFileName: {required: message.selectPicture}
            }
        });

        $("#programBtn").click(function () {
            var check = programFrom.form();
            if (check) {
                $("#programFrom").submit();
            }
        });
        $("#siteId").select2({
            formatNoMatches: function () {
                return message.noData;
            }
        });

        $("#locationIdselecter").select2({
            formatNoMatches: function () {
                return message.noData;
            }
        });

        $("#siteId").change(function () {
            findZoneLocationBySiteId();
        });
        $(function () {
            findZoneLocationBySiteId();
        });
    })
</script>
</#macro>