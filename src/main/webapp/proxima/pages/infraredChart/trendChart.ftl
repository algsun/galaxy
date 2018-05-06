<#--
  - 光学摄像机管理
  -@author xu.yuexi
  -@time  14-4-9
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.trendChart.title")} - ${locale.getStr("proxima.common.systemName")}</title>
<#include "../_common/common-css.ftl">
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:dVPlace:list">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:dVImage:trendChart"></@subNavs>

<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--your content-->
    <div class="container-wrapper">
        <div id="container" class="container">

            <div class="row">
                <div class="span12">

                    <form name="form1" class="well well-small" action="infraredTrendChart.action"
                          method="post">
                        <div class="row m-t-10 ">
                            <div class="span1 t-a-r">
                                <label class="m-t-3" for="startDate">${locale.getStr("common.startDate")}</label>
                            </div>
                            <div class="span3">
                                <input id="startDate" type="text" class="span3"
                                       onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 00:00'})"
                                       name="startDate"
                                       value="${startDate?string('yyyy-MM-dd HH:mm')}"/>
                            </div>


                            <div class="span2 t-a-r">
                                <label class="m-t-3" for="endDate">${locale.getStr("common.endDate")}</label>
                            </div>
                            <div class="span3">
                                <input id="endDate" type="text" class="span3"
                                       onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 23:59'})"
                                       name="endDate"
                                       value="${endDate?string('yyyy-MM-dd HH:mm')}"/>
                            </div>

                        </div>

                        <div class="row">
                            <div class="span1 t-a-r">
                                <label for="zone-id">${locale.getStr("proxima.pictures.areaSelection")}</label>
                            </div>

                            <div class="span3">
                                <select id="zone-id" name="zoneId">
                                    <option value="">${locale.getStr("common.all")}</option>
                                <#list zones as zone>
                                    <option value="${zone.id}" name="test"
                                            <#if zone.id==zoneId!"">selected="selected"</#if>>${zone.name}</option>
                                </#list>
                                </select>
                            </div>

                            <div class="span2 t-a-r">
                                <label for="dv-places">${locale.getStr("proxima.pictures.cameraPosition")}</label>
                            </div>
                            <div class="span3">
                            <#if infraredDVPlaces??>
                                <#list infraredDVPlaces as place>
                                    <input type="hidden" name="dvPlaces" value="${place.id}"
                                           data-name="${place.placeName}" data-zone="${place.zone.id}"/>
                                </#list>
                                <select name="dvPlaceId" id="dv-places">
                                    <#list infraredDVPlaces as place>
                                        <option value="${place.id}" data-name="${place.placeName}"
                                                data-zone="${place.zone.id}"
                                                <#if place.id == dvPlaceId>selected="true"</#if>>${place.placeName}</option>
                                    </#list>
                                </select>
                            </#if>
                            </div>

                        </div>

                        <div class="row form-inline">
                            <div class="span1 t-a-r">
                                <label for="markRegion-select" class="m-t-3">
                                ${locale.getStr("proxima.infrared.region")}
                                </label>
                            </div>
                            <div class="span3">
                                <select name="markRegionId" id="markRegion-select">
                                    <option value="">${locale.getStr("common.all")}</option>
                                <#if infraredMarkRegionList??>
                                    <#list infraredMarkRegionList as infraredMarkRegion>
                                        <option value="${infraredMarkRegion.id}"
                                                <#if infraredMarkRegion.id == markRegionId!"">selected="true" </#if>>${infraredMarkRegion.name}</option>
                                    </#list>
                                </#if>
                                </select>
                            </div>

                            <div class="span2">
                                &nbsp;
                            </div>
                            <div class="span2">
                                <button id="submit-btn" class="btn" type="submit">
                                ${locale.getStr("common.select")}
                                </button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        <#if !chartData??||chartData==''>
        ${locale.getStr("common.noData")}
        <#else>
            <div class="row">
                <div class="span12 t-a-r">
                    <div id="advanced" style="display: none;" class="span1 t-a-l">
                        <button id="advancedBtn" class="btn" type="button"
                                title="请慎用，由于设置时必须根据图形本身的规则进行设置，所以有些设置不起作用">
                        ${locale.getStr("proxima.trendChart.senior")}
                        </button>
                    </div>
                    <div id="Yaxis" class="input-append">
                        <div class="span5">
                            <button id="defaultSettings" class="btn btn-mini" type="button" title="点击恢复原始状态">
                            ${locale.getStr("blueplanet.location.restoreDefault")}
                            </button>
                        </div>
                        <div class="span1">
                            <button id="yAxisUp" class="btn btn-mini" type="button"
                                    title="整体增加Y轴范围，如无变化请通过Y轴最大值和最小值输入框进行设置">
                                <i class="icon-plus-sign"></i>
                            </button>
                            <button id="yAxisDomn" class="btn btn-mini" type="button"
                                    title="整体缩小Y轴范围，如无变化请通过Y轴最大值和最小值输入框进行设置">
                                <i class="icon-minus-sign"></i>
                            </button>
                        </div>
                        Y轴最大值
                        <input class="span1" value="" id="maxInputButtons" size="16"
                               type="text" style="width: 25px;height: 15px;"
                               title="输入Y轴最大值后鼠标点击其它地方进行设置，最大值不能大于400，如果无变化请点击恢复默认">
                        <button id="maxUp" class="btn btn-mini" type="button" title="微调Y轴最大值，如果无变化请手工通过输入框进行设置">
                            <i class="icon-arrow-up"></i>
                        </button>
                        <button id="maxDown" class="btn btn-mini" type="button" title="微调Y轴最大值，如果无变化请手工通过输入框进行设置">
                            <i class="icon-arrow-down"></i>
                        </button>
                        Y轴最小值
                        <input class="span1" value="" id="minInputButtons" size="16"
                               type="text" style="width: 25px;height: 15px;"
                               title="输入Y轴最小值后鼠标点击其它地方进行设置，最小值不能小于-100，如果无变化请点击恢复默认">
                        <button id="minUp" class="btn btn-mini" type="button" title="微调Y轴最小值，如果无变化请手工通过输入框进行设置">
                            <i class="icon-arrow-up"></i>
                        </button>
                        <button id="minDown" class="btn btn-mini" type="button" title="微调Y轴最小值，如果无变化请手工通过输入框进行设置">
                            <i class="icon-arrow-down"></i>
                        </button>
                    </div>
                </div>
            </div>
        </#if>
            <div id="chart"
                 style="display: none; width: 100%; height: 400px; margin: 0 auto;"></div>
        </div>
    </div>

    <div class="hide">
        <div id="chartDatas">
        <#if chartData??>
             ${chartData}
        </#if>
        </div>
        <div id="monitorPoint-dvPlaces">
            <ul>
            <#list zones as zone>
                <li data-monitor-name="${zone.name}">
                    <select id="zone-id" name="zoneId">
                        <option value="">${locale.getStr("common.all")}</option>

                        <option value="${zone.id}">${zone.name}</option>
                    </select>
                </li>
            </#list>
            </ul>
        </div>

    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<@scriptTag "../common/js/util.js"/>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/artDialog/5.0.1-7012746/artDialog.min.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highstock.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/themes/grid.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/exporting.js"></script>
<script type="text/javascript" src="js/infraredChart/trendChart.js"></script>
<script type="text/javascript" src="js/pnotify.js"></script>
<@scriptTag "js/2datepicker-form-validation.js"/>
<#include "../_common/last-resources.ftl">

</body>
</html>

