<#--
  -区域概览
  -@author xiedeng
  -@time  2013-11-1
  -@check liu.zhu 2013-11-14 #:6621
  -->
<#---->
<#assign title>${locale.getStr("blueplanet.index.title")}</#assign>
<#include "/common/pages/common-tag.ftl"/>
<#include "/blueplanet/pages/device/device-helper.ftl">

<#macro head>
<link type="text/css" rel="stylesheet" href="../blueplanet/css/index.css">
</#macro>
<#assign tabIndex = 0>
<#macro content>
<div class="row m-t-10">
    <div class="col-md-12">
        <#if alarmRecords??>
            <table class="table table-bordered table-striped table-center">
                <thead>
                <tr>
                    <th>${locale.getStr("common.number")}</th>
                    <th>${locale.getStr("common.location")}</th>
                    <th>${locale.getStr("blueplanet.alarm.alarmFactors")}</th>
                <#--<th>${locale.getStr("blueplanet.alarm.measure")}</th>-->
                    <th>${locale.getStr("blueplanet.alarm.alarmTime")}</th>
                </tr>
                </thead>
                <tbody>
                    <#if alarmRecords?size gt 0>
                        <#list alarmRecords as alarmRecord>
                        <tr>
                            <td>${alarmRecord_index+1}</td>
                            <td>${alarmRecord.locationName}</td>
                            <td>${alarmRecord.factor}</td>
                        <#--<td>-->
                        <#--<ul style="list-style: none;">-->
                        <#--<#list alarmRecord.measureVOs as measure>-->
                        <#--<li>-->
                        <#--${measure_index+1}.${measure.description}-->

                        <#--</li>-->
                        <#--</#list>-->
                        <#--</ul>-->
                        <#--</td>-->
                            <td>${alarmRecord.alarmTime?string('yyyy-MM-dd HH:mm')}</td>
                        </tr>
                        </#list>
                    </#if>
                </tbody>
            </table>
        </#if>
    </div>

    <#if findLocationFiveDay?? && findLocationFiveDay?size!=0>
        <div class="col-md-12">
            <p>
            ${locale.getStr("blueplanet.zone.maxValue")} <span class="label"
                                                               style="background-color: #ff2e3d;width:20px;height:10px;display:inline-block;">
                          </span> &nbsp;&nbsp;&nbsp;&nbsp;
            ${locale.getStr("blueplanet.zone.minValue")}<span class="label"
                                                              style="background-color: #0ebf00;width:20px;height:10px;display:inline-block;"></span>
                &nbsp;&nbsp;&nbsp;&nbsp;
                均值<span class="label"
                        style="background-color: #2eaeff;width:20px;height:10px;display:inline-block;"></span>
            </p>
            <#list findLocationFiveDay as locationData>
                <#if locationData_index%3==0>
                <div class="row m-b-20 ">
                </#if>
                <div class="col-md-4">
                    <p style="font-size: 18px;">${locationData.cnName!}：<span
                            style="font-weight: bolder">${locationData.sensorPhysicalValue!}${locationData.units!}</span>
                    </p>

                    <div class="trend-chart" style="height: 160px; width: 400px; border: solid 1px #ccc;"></div>
                    <div class="hide">
                        {
                    <#--<#if locationData.sensorPhysicalid==33>-->
                    <#--data:[-->
                    <#--<#list locationData.sensorPhysicalValueMap.keySet() as locationDataKey>-->
                    <#--[ ${locationDataKey?long?c}-->
                    <#--,${locationData.sensorPhysicalValueMap.get(locationDataKey).bigValue}-->
                    <#--,${locationData.sensorPhysicalValueMap.get(locationDataKey).smallValue}]-->
                    <#--<#if locationDataKey_has_next>,</#if>-->
                    <#--</#list>-->
                    <#--],-->
                    <#--<#else>-->
                        maxData:[
                        <#list locationData.sensorPhysicalValueMap.keySet() as locationDataBigKey>
                            [${locationDataBigKey?long?c}
                            ,${locationData.sensorPhysicalValueMap.get(locationDataBigKey).bigValue}]
                            <#if locationDataBigKey_has_next >,</#if>
                        </#list>
                        ],
                        minData:[
                        <#list locationData.sensorPhysicalValueMap.keySet() as locationDataSmallKey>
                            [${locationDataSmallKey?long?c}
                            ,${locationData.sensorPhysicalValueMap.get(locationDataSmallKey).smallValue}]
                            <#if locationDataSmallKey_has_next >,</#if>
                        </#list>
                        ],
                        avgData:[
                        <#list locationData.sensorPhysicalValueMap.keySet() as locationDataAvgKey>
                            [${locationDataAvgKey?long?c}
                            ,${locationData.sensorPhysicalValueMap.get(locationDataAvgKey).avgValue}]
                            <#if locationDataAvgKey_has_next >,</#if>
                        </#list>
                        ],
                    <#--</#if>-->
                        cnName: "${locationData.cnName!}",
                        unit: "${locationData.units!}",
                        sensorPhysicalId:${locationData.sensorPhysicalid?c}
                        }
                    </div>
                </div>
                <#if locationData_index%3==2 || !locationData_has_next>
                </div>
                </#if>
            </#list>
        </div>
    <#else >
        <h4>${locale.getStr("common.noData")}</h4>
    </#if>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/exporting.js"></script>
    <@scriptTag "js/zone/index.js"/>
<script type="text/javascript">
    var blueplanet = App("blueplanet");
    blueplanet.zoneLocationPath.atPage("index");
</script>
</#macro>
