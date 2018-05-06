<#--
  - 图片浏览
  -@author Wang yunlong
  -@time  13-3-25  上午10:23
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.pictures.pictureBrowse")} - ${locale.getStr("proxima.common.systemName")}</title>

<#include "../_common/common-css.ftl">
<#include  "/common/pages/common-tag.ftl">
<@linkTag "../assets/galleryView-3.0-dev/css/jquery.galleryview-3.0-dev.css"/>
    <style type="text/css">
        .query-input {
        <#if !inputView>
            display: none;
        </#if>
        }

        .open-input, .close-input {
            cursor: pointer;
            color: #dd4444;
            position: absolute;
            top: 60px;
            right: 210px;
            z-index: 99;
        }
    </style>
</head>

<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:opticsDVPImage">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:dVImage:optics"></@subNavs>
    <input type="hidden" name="inputView" value="${inputView?string("true","false")}">
    <div class="close-input m-v-10 f-r <#if !inputView>hide</#if>"
         id="close"> ${locale.getStr("proxima.pictures.hide")}</div>
    <div class="open-input m-v-10 f-r <#if inputView>hide</#if>"
         id="open">${locale.getStr("proxima.pictures.queryCriteria")}</div>
    <div class="row">
        <div class="query-input span12">
            <form class="well well-small"
                  action="viewOpticsImage.action"
                  method="post">
                <div class="row">
                    <div class="span1 t-a-r">
                        <label for="zone-id">${locale.getStr("proxima.pictures.areaSelection")}</label>
                    </div>

                    <div class="span3">
                        <select id="zone-id" name="zoneId">
                            <option value="">${locale.getStr("common.all")}</option>
                        <#list zones as zone>
                            <option value="${zone.id}"
                                    <#if zone.id==zoneId!"">selected="selected"</#if>>${zone.name}</option>
                        </#list>
                        </select>
                    </div>

                    <div class="span2 t-a-r">
                        <label for="dv-places">${locale.getStr("proxima.pictures.cameraPosition")}</label>
                    </div>
                    <div class="span3">
                        <select name="dvPlaceId" id="dv-places">
                        <#list opticsDVPlaces as place>
                            <option value="${place.id}"
                                <#if place.id==dvPlaceId> selected="selected"</#if>
                            >${place.placeName}</option>
                        </#list>
                        </select>
                    </div>
                    <div class="span2"></div>
                </div>
                <div class="row m-t-10 ">
                    <div class="span1 t-a-r">
                        <label class="m-t-3" for="startDate">${locale.getStr("common.startDate")}</label>
                    </div>
                    <div class="span3">
                        <input id="startDate" type="text" class="span3"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})"
                               name="startDate"
                               value="${startDate?string('yyyy-MM-dd HH:mm')}"/>
                    </div>


                    <div class="span2 t-a-r">
                        <label class="m-t-3" for="endDate">${locale.getStr("common.endDate")}</label>
                    </div>
                    <div class="span3">
                        <input id="endDate" type="text" class="span3"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm'})"
                               name="endDate"
                               value="${endDate?string('yyyy-MM-dd HH:mm')}"/>
                    </div>
                    <div class="span2">
                        <button id="submit-btn" class="btn" type="submit">${locale.getStr("common.select")}</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
<#if pictures??>
    <div class="query-input row m-b-20">
        <div class="span7">
        ${pictures?size}  ${locale.getStr("proxima.pictures.unit")}
        </div>
        <div class="span5 t-a-r">
            <span>${dvName!}</span>
            <button id="submit-realmap" class="btn" dvPlaceId="${dvPlaceId!}"
                    realmap-href="${realmapUrl!}">${locale.getStr("proxima.common.JCV")}</button>
        </div>
    </div>

    <div class="row m-b-30">
        <div class="span12">
            <ul id="myGallery">
                <#list pictures as picture>
                    <li>
                        <img src="${picturesBasePath}/${picture.path}/${picture.name}"
                             title="${picture.saveTime?string("yyyy-MM-dd HH:mm:ss")}" data-pic-id="${picture.id}">
                    </li>
                </#list>
            </ul>
        </div>
    </div>
<#else>${locale.getStr("common.noData")}</#if>

</div>
<div class="hide">
    <div class="zone-move-tree hide">
        <div id="dialog" class="span4">
            <div id="tree" class="ztree"></div>
            <span class="help-block red m-l-20"></span>
        </div>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/jquery-ui/1.8.18/js/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.timers-1.2.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.galleryview-3.0-dev.js"></script>

<@scriptTag "js/2datepicker-form-validation.js"/>
<script type="text/javascript">
    $(function () {
        var $queryInput = $(".query-input");
        var $open = $(".open-input");
        var $close = $(".close-input");
        var $inputView = $("input[name=inputView]");
        $open.click(function () {
            $queryInput.slideDown();
            $inputView.val("true");
            $open.hide();
            $close.show();
        });
        $close.click(function () {
            $queryInput.slideUp();
            $inputView.val("false");
            $close.hide();
            $open.show();
        });

        $('#submit-realmap').click(function () {
            var $this = $(this);
            var imgSrc = $this.attr('realmap-href');
            var dvplaceId = $this.attr('dvPlaceId');
            var dialog = art.dialog({title: message.cameraJCV});
            var content = [
                '<div>',
                '<div style="width:560px; height:360px;text-align:center;">',
                '<img  src="', imgSrc, '" style="max-width:460px; max-height:320px;vertical-align:middle;margin-top:15px"/>',
                '</div>',
                '<div style="text-align:center;">',
                '<a href="toUploadDVrealmapView.action?dvId=', dvplaceId, '" class="btn btn-mini" style="font-size:12px" > ' + message.uploadJCV + '</a>',
                '</div>',
                '</div>'
            ].join('');
            dialog.close();
            art.dialog({title: message.cameraJCV, content: content});
        });
        if ($("#myGallery").length != 0) {
            var result = $('#myGallery').galleryView({
                panel_scale: 'fit',
                panel_width: 930,
                panel_height: 500,
                panel_animation: 'crossfade',
                frame_width: 150,
                frame_height: 100,
                frame_opacity: 0.6,
                filmstrip_position: "bottom",
                show_captions: true,
                transition_interval: 1000
            });
        }
        (function () {
            var $zoneId = $("#zone-id");
            var $dvPlaces = $("#dv-places");

            $zoneId.change(function () {
                // TODO 使用 var 声明变量 @gaohui 2013-06-14
                $this = $(this);
                $.getJSON("getDvPlaces.action", {zoneId: $this.val()}, function (result) {
                    var option = '';
                    for (var i = 0, place; place = result[i]; i++) {
                        var selected = '';
                        if (place.id == $dvPlaces.val()) {
                            selected = "selected=selected";
                        }
                        option += "<option value='" + place.id + "' " + selected + ">" + place.placeName + "</option>";
                    }
                    $dvPlaces.empty().append(option);
                });
            });
        })();
    });
</script>
<script type="text/javascript">
    $(function () {
        $(document).on('click', '.subsystemHead', function () {
            art.dialog({
                title: message.loading
            });
        });
    });
</script>

<#include "../_common/last-resources.ftl">
</body>
</html>
