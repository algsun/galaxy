<#--
  -站点区域实时数据
  -@author Wang yunlong
  -@time  13-1-24  下午4:49
  @check @gaohui 2013-02-25 #1729
  -->
<#assign title>${locale.getStr("blueplanet.device.realtime.title")}</#assign>

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

    .hide-filters:hover {
        color: #87ceeb;
    }
</style>
</#macro>



<#assign tabIndex = 1>
<#macro content>
<div class="row-fluid m-t-10">
    <div class="span12">
        <ul class="nav nav-pills">
            <li><a href="devices">设备列表</a></li>
            <li class="active"><a href="devices/realtime-data">实时数据</a></li>
        </ul>
    </div>
</div>

<div class="form-inline m-b-10">
    <div class="m-b-10">
        <span class="il-blk m-b-5 hide-filters hide ">隐藏</span>
        <span class="btn btn-mini il-blk m-b-5 show-filters">监测指标筛选</span>
        <input id="is_show_filters" name="showFilters" class="hide" value="${showFilters}">
        <#list sensorPhysicalIds! as sensorPhysicalId>
            <input class="sensorPhysicalId hide" value="${sensorPhysicalId}">
        </#list>
        <button id="filter-btn" class="btn btn-small btn-success hide">筛选</button>
        <a id="cancel-filter-btn" class="btn btn-small hide" href="devices/realtime-data">取消筛选</a>
        <label id="select-all" class="checkbox m-l-10" style="display: none"><input
                type="checkbox" checked="checked">全选</label>
    </div>
<#-- TODO 分割符一致 @gaohui 2013-02-25 -->
    <div class="hide filter_sensorinfoes m-b-10">
        <ul id="zone-sensorinfo-filter" class="il-blk p-v-0 m-v-0" style="list-style-type: none;">
            <#list sensorinfoes! as sensorinfo>
                <li class='il-blk m-r-10'>
                    <label class='checkbox'>
                        <input class='sensorinfo-filter-input' checked="checked" type='checkbox'
                               data-type="${sensorinfo.sensorPhysicalid?c}"> ${sensorinfo.cnName} </label>
                </li>
            </#list>
        </ul>
    </div>
</div>
<div id="table-container" style="overflow: auto;min-height: 200px; height: 500px;">
    <div id="real_time_div">
        <table id="real_time_data_table_mould"
               class="move table table-bordered table-striped table-center realtime-table hide" style="margin-bottom:0">
            <thead>
            <tr>
                <th data-dead='1' data-type='nodeName' style='vertical-align: middle;'><span>设备</span></th>
                <th data-dead='1' data-type='state' style='vertical-align: middle;'><span>状态</span></th>
                <th data-dead='1' data-type='stamp' style='vertical-align: middle;'><span>时间</span></th>
                <#list sensorinfoes! as sensorinfo>
                    <th class='moveable' data-type='${sensorinfo.sensorPhysicalid?c}'>
                        <span> ${sensorinfo.cnName!}</span><br><span class='muted'>(${sensorinfo.units!})</span>
                    </th>
                </#list>
            </tr>
            </thead>
            <tbody>
                <#list data! as realtimeData>
                <tr id="${realtimeData.nodeId!}">
                    <td data-dead='1' data-type='nodeName'></td>
                    <td data-dead='1' data-type='state'><span></span></td>
                    <td data-dead='1' data-type='stamp'></td>
                    <#list sensorinfoes! as sensorinfo>
                        <td data-type="${sensorinfo.sensorPhysicalid?c}"></td>
                    </#list>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>

    <#include "../device-helper.ftl">
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
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/lang/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8" src="../assets/dataTables/1.9.4/js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8"
        src="../assets/dataTables/1.9.4/FixedColumns/js/FixedColumns.js"></script>
<script type="text/javascript" charset="utf-8" src="../assets/dataTables/1.9.4/FixedHeader/js/FixedHeader.js"></script>
    <@scriptTag "js/table-column-movement.0.4.js"/>
    <@scriptTag "js/realtime-data/device/realtime-data-util.js"/>
    <@scriptTag "js/realtime-data/realtime-table.js"/>
    <@scriptTag "js/realtime-data/device/site-zone-realtime-data.js"/>
    <@scriptTag "js/realtime-data/realtime-data-filter.js"/>

<script type="text/javascript">
    $(function () {
        $.ajaxSetup({traditional: true});
        //监测指标获取 初始化实时数据表头
        var realtimeDataFilterUrl = "devices/realtime-data-filter";
        var realtimeDataUrl = "devices/realtime-data.json";
        var sensorinfoesUrl = "devices/sensorinfoes.json";
        REAL_TIME.execute(sensorinfoesUrl, realtimeDataUrl, realtimeDataFilterUrl);

    });
</script>
</#macro>

