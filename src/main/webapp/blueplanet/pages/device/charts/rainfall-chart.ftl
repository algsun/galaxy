<#--
降雨量图
@check @wang yunlong 2012-03-12 #2062
-->
<#assign title="降雨量图 - 环境监控">
<#include "/common/pages/common-tag.ftl">

<#macro head>

<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
</#macro>


<#assign tabIndex = 6>
<#macro content>

<div id="rainfallChartForm" class="well well-small form-inline m-b-10 ">
    <input id="deviceId" type="hidden" value="${deviceId}">

    <div class="f-l m-l-10">
        <label style="padding-top: 3px;">监测指标</label>
        <select id="sensorIds" multiple="multiple" style="width:250px;">
            <#list sensorInfoList as sensorInfo>
                <#if sensorInfo.sensorPhysicalid==32 ||sensorInfo.sensorPhysicalid==33||sensorInfo.sensorPhysicalid==47>
                    <option value="${sensorInfo.sensorPhysicalid}" selected="selected">${sensorInfo.cnName}</option>
                <#else>
                    <option value="${sensorInfo.sensorPhysicalid}">${sensorInfo.cnName}</option>
                </#if>
            </#list>
        </select>
    </div>
    <label class="m-l-10">时间类型</label>
    <#if dateType==0>
        <#assign dateType=3>
    </#if>
    <label class="radio m-l-10">
        <input id="radioYear" name="dateType" type="radio" value="1" <#if dateType==1> checked </#if>>年
    </label>
    <label class="radio m-l-10">
        <input id="radioMonth" name="dateType" type="radio" value="2"<#if dateType==2> checked </#if>>月
    </label>
    <label class="radio m-l-10">
        <input id="radioDay" name="dateType" type="radio" value="3" <#if dateType==3> checked </#if>>日
    </label>
    <label class="m-l-10">时间</label>
    <#local dateString="">
    <#if dateType==1>
        <#local dateString=date?string("yyyy")!>
    <#elseif  dateType==2>
        <#local dateString=date?string("yyyy-MM")!>
    <#elseif dateType==3>
        <#local dateString=date?string("yyyy-MM-dd")!>
    </#if>
    <input id="date" name="date" class="input-medium Wdate" type="text" value="${dateString}">
    <button id="commit" class="btn">查询</button>
</div>

<H4 id="noData"></H4>
<div class="row-fluid">
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

<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/exporting.js"></script>
<#--<script type="text/javascript" src="../assets/highcharts/2.3.5/themes/grid.js"></script>-->
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/device/charts/date-format.js"/>
    <@scriptTag "js/device/charts/rainfall-chart.js"/>

</#macro>