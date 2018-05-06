<#--
累积光照图
@check @wang yunlong 2012-03-12 #2062
-->
<#assign title=locale.getStr("blueplanet.location.lightChartTitle")>
<#include "/common/pages/common-tag.ftl">

<#macro head>
<link rel="stylesheet" href="../assets/select2/3.2/select2.css">
<style type="text/css">
    #show-hide-compare {
        cursor: pointer;
        display: none;
    }

    #add-compare {
        cursor: pointer;
        display: none;
    }

    #cancel-all-compare {
        display: none;
        cursor: pointer;
    }

    #add-compare:hover, #show-hide-compare:hover, #cancel-all-compare:hover {
        color: #08c;
    }
</style>
</#macro>

<#assign tabIndex = 7>
<#macro content>

<div id="rainfallChartForm" class="well well-small form-inline m-b-10 ">
    <input id="locationId" type="hidden" value="${locationId}">
    <label class="m-l-10">${locale.getStr("common.timeType")}</label>
    <#if dateType==0>
        <#assign dateType=3>
    </#if>
    <label class="radio m-l-10">
        <input id="radioYear" name="dateType" type="radio" value="1" <#if dateType==1> checked </#if>>${locale.getStr("common.year")}
    </label>
    <label class="radio m-l-10">
        <input id="radioMonth" name="dateType" type="radio" value="2"<#if dateType==2> checked </#if>>${locale.getStr("common.month")}
    </label>
    <label class="radio m-l-10">
        <input id="radioDay" name="dateType" type="radio" value="3" <#if dateType==3> checked </#if>>${locale.getStr("common.day")}
    </label>
    <label class="m-l-10">${locale.getStr("common.time")}</label>
    <#local dateString="">
    <#if dateType==1>
        <#local dateString=date?string("yyyy")!>
    <#elseif  dateType==2>
        <#local dateString=date?string("yyyy-MM")!>
    <#elseif dateType==3>
        <#local dateString=date?string("yyyy-MM-dd")!>
    </#if>
    <input id="date" name="date" class="input-medium Wdate" type="text" value="${dateString}">
    <button id="commit" class="btn">${locale.getStr("common.select")}</button>
    <span id="add-compare" class="gray m-l-10">${locale.getStr("blueplanet.location.addComparision")}</span>
    <span id="cancel-all-compare" class="gray m-l-10">${locale.getStr("blueplanet.location.cancelAll")}</span>
    <span id="show-hide-compare" class="gray m-l-5">${locale.getStr("blueplanet.realtimeData.hide")}</span>
</div>

<div id="compare" class="form-inline hide m-l-20">
    <label class="m-t-5">${locale.getStr("blueplanet.location.selectLocation")}</label>
    <select id="locationIds" name="locationIds" multiple placeholder="${locale.getStr("blueplanet.location.moveLocation")}" style="width:400px;"> </select>
</div>
<h4 id="noData"></h4>
<div class="row-fluid m-t-10">
    <div class="span12">
        <div id="chart"></div>
    </div>
</div>
<div class="row-fluid m-t-10">
    <div id="statistics" class="span5"></div>
    <div class="span7"></div>
</div>
</#macro>

<#macro script>

<script type="text/javascript" src="../assets/select2/3.2/select2.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/device/charts/date-format.js"/>
    <@scriptTag "js/location/charts/light-chart.js"/>

</#macro>