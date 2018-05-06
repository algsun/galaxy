<#--
风向玫瑰图
-->
<#assign title=locale.getStr("blueplanet.location.windRoseDiagramTitle")>

<#include "/common/pages/common-tag.ftl"/>
<#macro head>

<link rel="stylesheet" href="../assets/select2/3.2/select2.css">
<style type="text/css">
    ul {
        list-style: none;
    }
</style>

</#macro>

<#assign tabIndex = 5>
<#macro content>
<div>
    <div class="form-inline well p-t-10" style="margin:0 auto;">
        <input type="hidden" id="locationId" name="locationId" value="${locationId}"/>

        <label class="m-l-10">${locale.getStr("common.timeType")}</label>
        <#if dateType==0>
            <#assign dateType=2>
        </#if>
        <label class="radio m-l-10">
            <input id="radioYear" name="dateType" type="radio" value="1" <@checked 1,dateType/> >${locale.getStr("common.year")}
        </label>
        <label class="radio m-l-10">
            <input id="radioMonth" name="dateType" type="radio" value="2" <@checked 2,dateType/>>${locale.getStr("common.month")}
        </label>
        <label class="radio m-l-10">
            <input id="radioDay" name="dateType" type="radio" value="3" <@checked 3,dateType/>>${locale.getStr("blueplanet.location.customTime")}
        </label>
        <input type="hidden" id="date_value">
        <#assign chartTime="">
        <#if dateType==1>
            <#assign chartTime=date?string("yyyy")!>
        <#elseif  dateType==2>
            <#assign chartTime=date?string("yyyy-MM")!>
        <#elseif dateType==3>
            <#assign  chartTime=date?string("yyyy-MM-dd")!>
        </#if>

        <div id="radioYearAndMonth" style="display:inline">
            <label class="m-l-10">${locale.getStr("common.time")}</label>
            <input id="date" name="date" class="input-medium Wdate" type="text" value="${chartTime}">
        </div>
        <div id="radioDays" class="hide" style="display:inline">
            <label class="m-l-10">${locale.getStr("common.startDate")}</label>
            <input id="startDate" name="startDate" class="input-medium Wdate" type="text"
                   value="${startDate?string('yyyy-MM-dd')}">
            <label class="m-l-10">${locale.getStr("common.endDate")}</label>
            <input id="endDate" name="endDate" class="input-medium Wdate" type="text"
                   value="${endDate?string('yyyy-MM-dd')}">
        </div>
        <button id="query-windrose-chart" type="button" class="btn">${locale.getStr("common.select")}</button>
    </div>
    <div class="row-fluid m-t-10">
        <div class="span12">
            <div id="windrose-chart" style="height:500px;">
            </div>
        </div>
    </div>
</div>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/echarts/2.1.8/echarts-all.js"></script>
<script type="text/javascript" src="../assets/select2/3.2/select2.min.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/data.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/device/charts/date-format.js"/>
    <@scriptTag "js/location/charts/windrose-chart.js"/>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
        return 'location/' + location.id + "/windrose-chart";
    });
</script>
</#macro>
