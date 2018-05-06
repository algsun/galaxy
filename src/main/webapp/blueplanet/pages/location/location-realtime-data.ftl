<#--
  - 位置点实时数据
  -@author xuyuexi
  -@time 14-7-1
-->
<#assign title=locale.getStr("blueplanet.location.locationRealtime")>


<#macro head>

<style type="text/css">
        /* 覆盖 bootstrap 样式 START */
        /* 调小水平间距 */
    .table th, .table td {
        padding: 5px 2px;
    }

        /* 覆盖 END */
</style>

</#macro>


<#assign tabIndex = 1>
<#include "../device/device-helper.ftl">
<#macro content>
    <table id="device-realtime-data" class="realtime-table table table-bordered table-striped table-center move">
        <thead>
        <tr>
            <th data-type="time">${locale.getStr("common.time")}</th>
            <th data-type="state">${locale.getStr("blueplanet.common.status")}</th>
        <#--<th data-type="voltage">电压</th>-->
        <#--<th data-type="mode">模式</th>-->
            <#assign sensorinfoes=location.sensorInfoList/>
            <#list sensorinfoes as sensorinfo>
                <th class="moveable" data-type="${sensorinfo.sensorPhysicalid?c}">
                ${sensorinfo.cnName}<br>
                    <span class='muted'> (${sensorinfo.units}) </span>
                </th>
            </#list>
        </tr>
        </thead>
        <tbody>
        <tr class="table-data-template hide">
            <td data-dead="1" data-type="stamp"></td>
            <td data-dead="1" data-type="state"></td>
        <#--<td data-dead="1" data-type="lowvoltage"></td>-->
        <#--<td data-dead="1" data-type="deviceMode"></td>-->
            <#list sensorinfoes as sensorinfo>
                <td class="moveable" data-type="${sensorinfo.sensorPhysicalid?c}"></td>
            </#list>
        </tr>
        </tbody>
    </table>

<input type="hidden" id="locationId" value="${locationId}"/>
<div class="hide">
    <div class="charts">
        <div id="little-chart" style="width:200px; height: 80px;"></div>
    </div>
</div>

</#macro>


<#include "/common/pages/common-tag.ftl">
<#macro script>

<script type="text/javascript" src="../assets/jquery-ui/1.9.2/js/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/lang/zh-cn.js"></script>
    <@scriptTag "js/realtime-data/realtime-data-util.js"/>
    <@scriptTag "js/table-column-movement.0.4.js"/>
    <@scriptTag "js/location/realtime-data.js"/>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
        return 'location/' + location.id+"/realtime-data";
    });
</script>
</#macro>