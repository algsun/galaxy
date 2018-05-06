<#--
外展管理，图片浏览

@author xu.yuexi
@date 2013-10-31
-->

<#assign title="图片浏览 - 外展管理">
<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">
<#macro head>
    <@linkTag "../assets/galleryView-3.0-dev/css/jquery.galleryview-3.0-dev.css"/>
<style type="text/css">
    .table thead {
        background-color: #ececec;
    }
</style>
</#macro>
<#macro content>
<div class="row">
    <div class="span12 m-l-40">
        <fieldset>
            <legend>
                <a class="go-back" href="queryExhibitionStateList/exhibition/${exhibitionId}" title="返回">
                    <i class="mw-icon-prev"></i>图片浏览
                </a>
            </legend>
        </fieldset>
    </div>
</div>
<div id="gcontent" class="container m-t-10">

    <div class="span12">
        <form class="well well-small"
              action="conditionImageView/exhibition/${exhibitionId}"
              method="post">
                     <span class="p-5 inline-block ">
                        <label for="dv-places">摄像机点位</label>
                        <select name="dvPlaceId" id="dv-places">
                            <#if opticsDVPlaces?size gt 0>
                                <#list opticsDVPlaces as place>
                                    <option value="${place.id?string}"
                                        <#if place.id==dvPlaceId> selected="selected"</#if>
                                            >${place.placeName}</option>
                                </#list>
                                 <#else>
                                     <option value="-1">暂无摄像机</option>
                            </#if>
                        </select>
                    </span>
                     <span class="p-5 inline-block ">
                         <label class="m-t-3" for="startDate">开始时间</label>
                        <input id="startDate" type="text"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 00:00'})"
                               name="startDate"
                               value="${startDate?string('yyyy-MM-dd HH:mm')}"/>
                     </span>
                        <span class="inline-block p-5 ">
                          <label class="m-t-3" for="endDate">结束时间</label>
                        <input id="endDate" type="text"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 23:59'})"
                               name="endDate"
                               value="${endDate?string('yyyy-MM-dd HH:mm')}"/>
                        <button id="submit-btn" class="btn m-b-10" type="submit">查询</button>
                    </span>

        </form>
        <#if pictures??>
            <div class="row m-b-20">
                <div class="span7">
                    共${pictures?size}  张图片
                </div>
            </div>

        <div class="row m-b-30 m-r-50">
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
        <#else>暂无数据</#if>
    </div>

        <div class="hide">
            <div class="zone-move-tree hide">
                <div id="dialog" class="span4">
                    <div id="tree" class="ztree"></div>
                    <span class="help-block red m-l-20"></span>
                </div>
            </div>
        </div>
    </div>
</div>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.timers-1.2.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.galleryview-3.0-dev.js"></script>

 <@scriptTag "js/2datepicker-form-validation.js"/>
<script type="text/javascript">
    $(function () {
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
    })();
</script>
</#macro>
