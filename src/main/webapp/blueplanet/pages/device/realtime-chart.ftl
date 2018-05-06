<#--
设备实时图形

@author gaohui
@date 2013-02-17
@check @wang yunlong 2013-02-25 #1716
-->
<#assign title="实时图形 - 环境监控">
<#include "/common/pages/common-tag.ftl">


<#macro head>
</#macro>


<#assign tabIndex = 2>
<#macro content>
<div id="device-id" data-device-id="${deviceId}" class="hide"></div>

<div class="row-fluid">
    <div class="span12">
        <div class="form-inline">
            <label>监测指标</label>
            <select id="sensorinfoSelect">
                <#list sensorinfos as sensorinfo>
                    <option data-units="${sensorinfo.units}"
                            value="${sensorinfo.sensorPhysicalid}">${sensorinfo.cnName}</option>
                </#list>
            </select>
            <label>刷新频率</label>
            <select id="refreshIntervalSelect" class="input-small">
                <option value="60000" selected="selected">60 秒</option>
                <option value="30000">30 秒</option>
                <option value="100000">10 秒</option>
                <option value="5000">05 秒</option>
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

<@scriptTag "js/device/realtime-chart.js"/>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    BluePlanet.zoneDevicePath.redirectWhenDeviceChange(function (node) {
        return 'device/' + node.id + '/realtime-chart';
    });
</script>
</#macro>