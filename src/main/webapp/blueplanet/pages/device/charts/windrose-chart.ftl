<#--
风向玫瑰图
@check @li.jianfei 2012-03-13 #2070
-->
<#assign title="风向玫瑰图 - 环境监控">

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
        <form action="device/${deviceId}/windrose-chart" method="post">
            <input type="hidden" name="deviceId" value="${deviceId}"/>

            <div id="time-type" class="f-l m-l-10 m-t-5">
                <label>时间类型</label>
                <input id="radio0" style="margin:0 0 5px 5px;" <#if timeType==1>checked="checked"</#if> name="timeType"
                       type="radio" value="1">
                <label for="radio0" class="m-r-5">年</label>
                <input id="radio1" style="margin:0 0 5px 5px;" <#if timeType==2>checked="checked"</#if> name="timeType"
                       type="radio" value="2">
                <label for="radio1" class="m-r-5">月</label>
                <input id="radio2" style="margin:0 0 5px 5px;" <#if timeType==3>checked="checked"</#if> name="timeType"
                       type="radio" value="3">
                <label for="radio2" class="m-r-5">日</label>
            </div>
            <div class="f-l m-l-20">
                <label>时间</label>
                <#if timeType==1>
                    <input class="input-medium Wdate" id="time" type="text" name="time"
                           value="${defaultTime?string("yyyy")}"/>
                <#elseif timeType==2>
                    <input class="input-medium Wdate" id="time" type="text" name="time"
                           value="${defaultTime?string("yyyy-MM")}"/>
                <#else>
                    <input class="input-medium Wdate" id="time" type="text" name="time"
                           value="${defaultTime?string("yyyy-MM-dd")}"/>
                </#if>
            </div>
            <div class="f-l m-l-10">
                <input type="submit" id="query-windrose-chart" class="btn" value="查询"/>
            </div>
        </form>
    </div>
    <div class="row-fluid m-t-10">
        <div class="span12">
            <div id="windrose-chart" style="height:500px;">
                <#if !data.hasData>
                    <h4>暂无数据</h4>
                </#if>
            </div>
            <div style="display:none">
                <#if data.hasData>
                    <table id="freq" border="0" cellspacing="0" cellpadding="0">
                        <tr nowrap bgcolor="#CCCCFF">
                            <th colspan="9" class="hdr">Table of Frequencies (percent)</th>
                        </tr>
                        <tr nowrap bgcolor="#CCCCFF">
                            <th class="freq">方向</th>
                            <th class="freq">${data.windFrequencyText}(${data.windFrequencyUnits})</th>
                            <th class="freq">${data.windSpeedText}(${data.windSpeedUnits})</th>
                        </tr>
                        <#list data.directionList as windrose>
                            <tr nowrap>
                                <td class="dir">${windrose.directionId} 频率:${windrose.windFrequency}${data.windFrequencyUnits} 风速:${windrose.windSpeed}${data.windSpeedUnits}</td>
                                <td class="data">${windrose.windFrequency}</td>
                                <td class="data">${windrose.windSpeed}</td>
                            </tr>
                        </#list>
                    </table>
                </#if>
            </div>
        </div>
    </div>
</div>
<span class="hide" id="has-data"><#if data.hasData>0<#else>1</#if></span>
<span class="hide" id="windcalm">${data.windcalm!}</span>
<span class="hide" id="windrose-text">${data.text!}</span>
</#macro>


<#macro script>

<script type="text/javascript" src="../assets/select2/3.2/select2.min.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/data.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/device/charts/date-format.js"/>
    <@scriptTag "js/device/charts/windrose-chart.js"/>
</#macro>
