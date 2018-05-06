<#--
设备实时图形

@author xuyuexi
@date 2014-07-07
-->
<#assign title=locale.getStr("blueplanet.location.realtimeGraphicEnvironmentalMonitoring")>
<#include "/common/pages/common-tag.ftl">


<#macro head>
</#macro>


<#assign tabIndex = 2>
<#macro content>
<div id="location-id" data-location-id="${location.id}" class="hide"></div>

<div class="row-fluid">
    <div class="span12">
        <div class="form-inline">
            <label>${locale.getStr("blueplanet.controlPanel.monitoringIndicators")}</label>
            <select id="sensorinfoSelect">
                <#list sensorinfos as sensorinfo>
                    <option data-units="${sensorinfo.units}"
                            value="${sensorinfo.sensorPhysicalid?c}">${sensorinfo.cnName}</option>
                </#list>
            </select>
            <label>${locale.getStr("blueplanet.location.refreshRate")}</label>
            <select id="refreshIntervalSelect" class="input-small">
                <option value="60000" selected="selected">60 ${locale.getStr("common.second")}</option>
                <option value="30000">30 ${locale.getStr("common.second")}</option>
                <option value="10000">10 ${locale.getStr("common.second")}</option>
                <option value="5000">05 ${locale.getStr("common.second")}</option>
            </select>
        </div>
    </div>
</div>

<div class="row-fluid m-t-30">
    <div class="span12">
        <div class="p-v-5">
            <div id="realtime-chart" style="height: 350px;"></div>
        </div>
    </div>
</div>
</#macro>



<#macro script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/exporting.js"></script>
<#--<script type="text/javascript" src="../assets/highcharts/2.3.5/themes/gray.js"></script>-->
<@scriptTag "js/location/realtime-chart.js"/>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
        return 'location/' + location.id+"/realtime-chart";
    });
</script>
</#macro>