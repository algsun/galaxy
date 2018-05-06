<#--
历史数据

@author gaohui
@date 2013-02-25
@check @wang yunlong 2013-02-25 #1710
-->
<#assign title=locale.getStr("blueplanet.location.historicalDataTitle")>
<#include "/common/pages/common-tag.ftl">


<#macro head>
</#macro>


<#assign tabIndex = 3>
<#include "../device/device-helper.ftl">
<#macro content>
<form id="historyDataForm" class="well well-small form-inline m-b-10" action="location/${locationId}/history-data"
      method="post">
    <label>${locale.getStr("common.startDate")}</label>
    <input class="input-medium Wdate" id="start-time" type="text" name="startTime"
           onfocus="var endTime=$dp.$('end-time');WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'end-time\')}'})"
           value="${startTime?string("yyyy-MM-dd HH:mm:ss")!}"/>
    <label>${locale.getStr("common.endDate")}</label>
    <input class="input-medium Wdate" id="end-time" type="text" name="endTime"
           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'start-time\')}', maxDate:'%y-%M-{%d} 23:59:59'})"
           value="${endTime?string("yyyy-MM-dd HH:mm:ss")!}"/>
    <button class="btn" id="submitBtn" type="button">${locale.getStr("common.select")}</button>


    <#if security.isPermitted("blueplanet:location:export")>
        <span id="exportButton" data-location-id="${locationId}" class="btn m-l-20">
            <i class="icon-download-alt"></i>${locale.getStr("blueplanet.location.exportExcel")}
        </span>
    </#if>

</form>

<table class="table table-bordered table-striped table-hover table-center m-t-20">
    <thead>
    <th>${locale.getStr("common.number")}</th>
    <th>${locale.getStr("common.time")}</th>
    <th>${locale.getStr("blueplanet.common.status")}</th>
        <#list sensorinfos as sensorinfo>
        <th>
        ${sensorinfo.cnName}<br>
            <span class="muted f-n">(${sensorinfo.units})</span>
        </th>
        </#list>
    </thead>
    <tbody>
        <#list historyDatas as historyData>
            <#local data = historyData.sensorInfoMap>
        <#-- 设备状态 -->
            <#local _deviceState = {"anomaly": historyData.anomaly, "lowvoltage": historyData.lowvoltage}>
        <tr>
            <td>${historyData_index + 1}</td>
            <td>${historyData.stamp?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td><@deviceState _deviceState/></td>
            <#list sensorinfos as sensorinfo>
                <#if data.get(sensorinfo.sensorPhysicalid)??>
                    <#local deviceData = data.get(sensorinfo.sensorPhysicalid)>
                    <td
                        <#if deviceData.valueState==1>
                                style="background-color: #faf2cc;"
                        <#elseif deviceData.valueState==2>
                                style="background-color: #c4e3f3"
                        </#if>
                                <#if deviceData.state == 0>title="${deviceData.sensorPhysicalValue}"</#if>>
                    <#-- 采样失败 -->
                        <#if deviceData.state == 0>
                            --
                        <#else>
                        ${deviceData.sensorPhysicalValue}
                        </#if>
                    </td>
                <#else>
                    <td></td>
                </#if>
            </#list>
        </tr>
        </#list>

    </tbody>
</table>

    <#include "../_common/pagging.ftl">
    <#assign url = "location/${locationId}/history-data?startTime=${startTime?string('yyyy-MM-dd HH:mm:ss')}&endTime=${endTime?string('yyyy-MM-dd HH:mm:ss')}">
    <@pagination url, page, pageSum/>

    <#if historyDatas?size == 0>
    <h4>${locale.getStr("common.noData")}</h4>
    </#if>


</#macro>


<#macro script>

<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
    <@scriptTag "js/location/history-data.js"/>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
        return 'location/' + location.id + "/history-data";
    });
</script>
</#macro>