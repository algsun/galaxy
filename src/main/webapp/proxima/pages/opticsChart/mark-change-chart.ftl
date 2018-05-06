<#--
裂隙位置变化图

@author li.jianfei
@date 2012-08-13
@author gaohui
@date 2013-06-18
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.trendChart.markSegment.title")}</title>
<#include "../_common/common-css.ftl">
<#include "/common/pages/common-tag.ftl">

    <link rel="stylesheet" href="assets/jquery-ui-1.8.22.custom/css/custom-theme/jquery-ui-1.8.22.custom.css"
          type="text/css"/>

</head>
<body>
<#assign currentTopPrivilege = "proxima:crack">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:crack:positionChangeChart"/>

    <div class="row">
        <div class="span12">

            <form class="well well-small" action="markChangeChart.action" method="post">
                <div class="row">
                    <div class="span1 t-a-r">
                        <label for="dvPlaces">${locale.getStr("proxima.pictures.areaSelection")}</label>
                    </div>
                    <div class="span3">
                        <select id="monitorPoints-select" name="zoneId">
                            <option value="0">
                            ${locale.getStr("common.all")}
                            </option>
                        <#list informationmap?keys as zone>
                            <option value="${zone.id}" <@selected zoneId, zone.id/>>
                            ${zone.name}
                            </option>
                        </#list>
                        </select>
                    </div>
                    <div class="span2 t-a-r">
                        <label for="dvPlaces">${locale.getStr("proxima.pictures.cameraPosition")}</label>
                    </div>
                    <div class="span3">
                        <select name="dvPlaceId" id="dvPlaces">
                        </select>
                    </div>
                    <div class="span2"></div>
                </div>
                <div class="row m-t-10 ">
                    <div class="span1 t-a-r">
                        <label class="m-t-3" for="">${locale.getStr("common.startDate")}</label>
                    </div>
                    <div class="span3">
                        <input type="text" class="span3"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 00:00'})"
                               name="startDate"
                               value="${startDate?string("yyyy-MM-dd HH:mm")}">
                    </div>


                    <div class="span2 t-a-r">
                        <label class="m-t-3" for="">${locale.getStr("common.endDate")}</label>
                    </div>
                    <div class="span3">
                        <input type="text" class="span3"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 23:59'})"
                               name="endDate"
                               value="${endDate?string("yyyy-MM-dd HH:mm")}">
                    </div>
                    <div class="span2">
                        <button id="submit-btn" class="btn" type="submit">${locale.getStr("common.select")}</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

<#if mspList??>
    <#if (mspList?size > 0)>

        <div class="row m-b-30">
            <div id="chart" class="span9" style="height: 430px;"></div>
            <div class="span3">
                <div>
                    <label for="interval">
                        <i class="icon-time"></i>${locale.getStr("proxima.trendChart.markSegment.playbackInterval")}
                    </label>
                    <div class="form-inline">
                        <select id="interval" style="width: 100px;">
                            <option value="300">300 ${locale.getStr("proxima.trendChart.markSegment.ms")}</option>
                            <option value="500">500 ${locale.getStr("proxima.trendChart.markSegment.ms")}</option>
                            <option value="1000" selected="selected">
                                1000 ${locale.getStr("proxima.trendChart.markSegment.ms")}
                            <option>
                        </select>

                        <button id="play" class="btn btn-info">
                            <i class="icon-play icon-white"></i>${locale.getStr("blueplanet.zone.play")}
                        </button>
                    </div>
                </div>

                <div class="m-t-10">
                    <div>时间点:</div>
                    <div style="float: left;">
                        <select id="times" class="unstyled"
                                style="margin: 0; width: 165px; height: 340px; overflow: auto;"
                                multiple="multiple">
                            <#list listTime as date>
                                <option data-time="${date?string("yyyy-MM-dd HH:mm:ss")}">
                                ${date?string("yyyy-MM-dd HH:mm:ss")}
                                </option>
                            </#list>
                        </select>
                    </div>
                    <div style="float: left; height: 340px;">
                        <button class="btn btn-mini" style="position: relative; left: -18px; display: none;">
                            <i class="icon-chevron-up"></i>
                        </button>
                        <div id="slider"
                             style="position: relative; left: -16px; font-size: 1.3em; height: 340px;"></div>
                        <button class="btn btn-mini"
                                style="position: relative; left: -18px; top: 10px; display: none;">
                            <i class="icon-chevron-down"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        <h4>${locale.getStr("common.noData")}</h4>
    </#if>
</#if>
</div>

<div id="actionValues" style="display: none;">
    <div id="chartDatas">
    ${chartData!}
    </div>
    <div id="maxX">
    ${maxX?c}
    </div>
    <div id="maxY">
    ${maxY?c}
    </div>
    <div id="minX">
    ${minX?c}
    </div>
    <div id="minY">
    ${minY?c}
    </div>
    <div id="datas">
    <#if mapData??>
    ${mapData?size}

        <#list mapData?keys as key>
            <ul id="${key}">
                <#list mapData.get(key)?keys as date>
                    <li data-key="${date?string("yyyy-MM-dd HH:mm:ss")}"
                        data-value="${mapData.get(key).get(date)}"></li>
                </#list>
            </ul>
        </#list>
    </#if>
    </div>
</div>
<div class="hide">
    <div id="dvPlaces-select">
        <ul>
        <#list informationmap.entrySet() as entry>
            <#assign zone = entry.key>
            <li data-monitor-name="${zone.name}">
                <select data-select-for-monitor-id="${zone.id}">
                    <#list entry.value as dvPlace>
                        <option value="${dvPlace.id}" <@selected dvPlaceId, dvPlace.id/>>
                        ${dvPlace.placeName}
                        </option>
                    </#list>
                </select>
            </li>
        </#list>
        </ul>
    </div>
</div>

<div class="hide">
    <div class="span3">
        <select name="dvPlaceId" id="dvPlaces_hide">
        <#list informationmap.entrySet() as entry>
            <#assign zone = entry.key>
            <#list entry.value as dvPlace>
                <option value="${dvPlace.id}" <@selected dvPlaceId, dvPlace.id/>>
                ${dvPlace.placeName}
                </option>
            </#list>
        </#list>
        </select>
    </div>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="assets/jquery-ui-1.8.22.custom/js/jquery-ui-1.8.22.custom.min.js"></script>

<script type="text/javascript" src="assets/Highcharts-2.2.5/highcharts.js"></script>
<script type="text/javascript" src="assets/Highcharts-2.2.5/themes/grid.js"></script>
<script type="text/javascript" src="assets/Highcharts-2.2.5/modules/exporting.js"></script>


<@scriptTag "js/map.js"/>
<@scriptTag "js/2datepicker-form-validation.js"/>
<@scriptTag "js/opticsChart/markChangeChart.js"/>

<#include "../_common/last-resources.ftl">
</body>
</html>
