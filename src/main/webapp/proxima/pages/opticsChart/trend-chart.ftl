<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.trendChart.crack.title")}</title>
<#include "../_common/common-css.ftl">
<#include "/common/pages/common-tag.ftl">
</head>
<body>
<#assign currentTopPrivilege = "proxima:crack">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:crack:trendChart"/>

    <div class="row">
        <div class="span12">
            <form class="well well-small" action="trendChart.action" method="post">
                <div class="row">
                    <div class="span1 t-a-r">
                        <label class="m-t-3">${locale.getStr("common.startDate")}</label>
                    </div>
                    <div class="span3">
                        <input type="text" class="span3"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 00:00'})"
                               name="startDate"
                               value="${startDate?string("yyyy-MM-dd HH:mm")}">
                    </div>
                    <div class="span2 t-a-r">
                        <label class="m-t-3">${locale.getStr("common.endDate")}</label>
                    </div>
                    <div class="span3">
                        <input type="text" class="span3"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 23:59'})"
                               name="endDate"
                               value="${endDate?string("yyyy-MM-dd HH:mm")}">
                    </div>
                </div>
                <div class="row m-t-10 form-inline">
                    <div class="span1 t-a-r">
                        <label for="dvPlaces">${locale.getStr("proxima.pictures.areaSelection")}</label>
                    </div>
                    <div class="span3">
                    <#-- TODO id 改为 zones-select -->
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
                        <label for="dvPlaces" class="m-t-3">${locale.getStr("proxima.pictures.cameraPosition")}</label>
                    </div>
                    <div class="span3">
                        <select name="dvPlaceId" id="dvPlaces">
                        </select>
                    </div>
                </div>
                <div class="row m-t-10 form-inline">
                    <div class="span1 t-a-r">
                        <label for="markSegements"
                               class="m-t-3">${locale.getStr("proxima.trendChart.crack.markSegment")}</label>
                    </div>
                    <div class="span3">
                        <select name="markId" id="markSegements">
                        </select>
                    </div>
                    <div class="span2">
                        &nbsp;
                    </div>
                    <div class="span2">
                        <button id="submit-btn" class="btn" type="submit">${locale.getStr("common.select")}</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

<#if mspList??>
    <#if (mspList?size < 1)>
        <h4>${locale.getStr("common.noData")}</h4>
    </#if>
    <div id="mspSize" size="${mspList?size}"></div>
</#if>
    <div id="chart" class="hide" style="width: 100%; height: 400px; margin: 0 auto;"></div>
</div>

<div class="hide">
    <div id="chartDatas">
    ${chartData!}
    </div>
    <div id="actionValues">
    ${mapSegments?size}
    <#list mapSegments?keys as dvPlaceId>
        <select id="${dvPlaceId}">
            <#list mapSegments.get(dvPlaceId) as markSegment>
                <option value="${markSegment.id}" <@selected markId, markSegment.id/>>
                ${markSegment.name}
                </option>
            </#list>
        </select>
    </#list>
    </div>
    <div id="curMarkId">
    ${markId}
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

<script type="text/javascript" src="assets/Highstock-1.1.6/highstock.js"></script>
<script type="text/javascript" src="assets/Highstock-1.1.6/themes/grid.js"></script>
<script type="text/javascript" src="assets/Highcharts-2.2.5/modules/exporting.js"></script>

<@scriptTag "js/2datepicker-form-validation.js"/>
<@scriptTag "js/opticsChart/trendChart.js"/>

<#include "../_common/last-resources.ftl">
</body>
</html>
