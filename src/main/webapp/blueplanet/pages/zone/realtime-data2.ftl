<#--
站点区域实时数据
@author Wang yunlong
@time  13-1-24  下午4:49
@check @gaohui 2013-02-25 #1729

-->
<#assign title>${locale.getStr("blueplanet.realtimeChart.title")}</#assign>

<#include "/common/pages/common-tag.ftl"/>

<#macro head>
<style type="text/css">
        /* 覆盖 bootstrap 样式 START */
        /* 调小水平间距 */
    .table th, .table td {
        padding: 5px 2px;
    }

        /* 覆盖 END */

    .hide-filters, .show-filters {
        color: gray;
        cursor: pointer;
    }

    .hide-filters:hover, .show-filters:hover {
        color: #87ceeb;
    }
</style>
<link rel="stylesheet" href="../assets/dataTables/1.10.10/css/fixedColumns.bootstrap.min.css"/>
</#macro>



<#assign tabIndex = 1>
<#macro content>
<div id="zone-id" class="hide">${zoneId!}</div>
<div class="form-inline m-b-10">
    <div class="m-b-10">
        <span class="il-blk m-b-5 hide-filters hide ">${locale.getStr("blueplanet.realtimeData.hide")}</span>
        <span class="btn btn-mini il-blk m-b-5 show-filters">${locale.getStr("blueplanet.realtimeData.filter")}</span>
        <input id="is_show_filters" name="showFilters" class="hide" value="${showFilters}">
        <#list sensorPhysicalIds! as sensorPhysicalId>
            <input class="sensorPhysicalId hide" value="${sensorPhysicalId}">
        </#list>
        <button id="filter-btn" class="btn btn-small btn-success hide">${locale.getStr("blueplanet.realtimeData.search")}</button>
        <a id="cancel-filter-btn" class="btn btn-small hide" href="zone/${zoneId}/realtime-data?isShowFilters=false">${locale.getStr("blueplanet.realtimeData.reset")}</a>
        <label id="select-all" class="checkbox m-l-10" style="display: none"><input
                type="checkbox" checked="checked">${locale.getStr("common.all")}</label>
    </div>
    <div class="hide filter_sensorinfoes m-b-10">
        <ul id="zone-sensorinfo-filter" class="il-blk p-v-0 m-v-0" style="list-style-type: none;">
            <#list sensorinfoes as sensorinfo>
                <li class='il-blk m-r-10'>
                    <label class='checkbox'>
                        <input class='sensorinfo-filter-input' checked='checked' type='checkbox'
                               data-type="${sensorinfo.sensorPhysicalid?c}"> ${sensorinfo.cnName} </label>
                </li>
            </#list>
        </ul>
    </div>
</div>
<div id="table-container" style="overflow: auto;min-height: 200px; height: 500px;">
    <div>
        <table id="real_time_data_table_mould"
               class="move table table-bordered table-striped table-center realtime-table hide" style="margin-bottom:0">
            <thead>
            <tr>
                <th data-dead='1' data-type='zoneName' style='vertical-align: middle;'><span>${locale.getStr("common.zone")}</span></th>
                <th data-dead='1' data-type='locationName' style='vertical-align: middle;'><span>${locale.getStr("common.location")}</span></th>
                <th data-dead='1' data-type='state' style='vertical-align: middle;'><span>${locale.getStr("blueplanet.common.status")}</span></th>
                <th data-dead='1' data-type='stamp' style='vertical-align: middle;'><span>${locale.getStr("common.time")}</span></th>
                <#list sensorinfoes as sensorinfo>
                    <th class='moveable' data-type='${sensorinfo.sensorPhysicalid?c}'>
                        <span> ${sensorinfo.cnName}</span><br><span class='muted'>(${sensorinfo.units})</span>
                    </th>
                </#list>
            </tr>
            </thead>
            <tbody>
                <#list data as realtimeData>
                <tr id="${realtimeData.locationId!}">
                    <td data-dead='1' data-type='zoneName'></td>
                    <td data-dead='1' data-type='locationName'></td>
                    <td data-dead='1' data-type='state'><span></span></td>
                    <td data-dead='1' data-type='stamp'></td>
                    <#list sensorinfoes as sensorinfo>
                        <td data-type="${sensorinfo.sensorPhysicalid?c}"></td>
                    </#list>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
    <#include "../device/device-helper.ftl">
    <@deviceStateDescription/>

<div class="hide">
    <div class="charts">
        <div id="little-chart" style="width:200px; height: 80px;"></div>
    </div>
</div>
</#macro>



<#macro script>

<!--[if lte IE 8]>
<script language="javascript" type="text/javascript" src="../assets/flot/d7c58b59f3/excanvas.min.js"></script>
<![endif]-->
<script type="text/javascript" src="../assets/flot/d7c58b59f3/jquery.flot.js"></script>
<script type="text/javascript" src="../assets/jquery-ui/1.9.2/js/jquery-ui.custom.min.js"></script>
    <@scriptTag "js/realtime-data/realtime-data-filter.js"/>

<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/lang/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="../assets/dataTables/1.10.10/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../assets/dataTables/1.10.10/FixedColumns/js/dataTables.fixedColumns.min.js"></script>
    <@scriptTag "js/table-column-movement.0.4.js"/>
    <@scriptTag "js/realtime-data/realtime-data-util.js"/>
    <@scriptTag "js/realtime-data/realtime-table.js"/>
    <@scriptTag "js/realtime-data/site-zone-realtime-data.js"/>
<script type="text/javascript">
    $(function () {
        $.ajaxSetup({traditional: true});
        //监测指标获取 初始化实时数据表头
        var zoneId = $("#zone-id").text();
        var realtimeDataUrl = "zone/" + zoneId + "/realtime-data.json";
        var sensorinfoesUrl = "zone/" + zoneId + "/sensorinfoes.json";
        var realtimeDataFilterUrl = "zone/" + zoneId + "/realtime-data-filter";
        REAL_TIME.execute(sensorinfoesUrl, realtimeDataUrl,realtimeDataFilterUrl);
    });
</script>
</#macro>
