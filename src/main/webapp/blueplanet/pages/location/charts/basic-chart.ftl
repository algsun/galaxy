<#--
基础曲线图
@check @li.jianfei 2012-03-13 #2041
-->
<#assign title=locale.getStr("blueplanet.location.basicCurveChartTitle")>


<#macro head>
    <#include "/common/pages/common-tag.ftl"/>
<link rel="stylesheet" href="../assets/select2/3.2/select2.css">
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
    <@linkTag "css/location/charts/basic-chart.css"/>
</#macro>


<#assign tabIndex = 4>
<#macro content>

<div id="location-id" class="hide">${location.id}</div>
<input class="hide" id="YaxisSensorId" value=""/>
<div id="location-name" class="hide" compare-location-id="${location.id}">${location.locationName!}</div>
<div style="min-width: 1000px;">
    <div class="form-inline well p-t-10" style="margin:0 auto;">
        <div class="f-l m-l-10 ">
            <label class="sensorinfo-select-label">${locale.getStr("blueplanet.offline.sensor")}</label>
            <select id="sensorPhysicalId" multiple="multiple" style="width:250px;">
                <#list location.sensorInfoList as sensorinfo>
                    <option value="${sensorinfo.sensorPhysicalid}"
                            <#if sensorinfo_index==0>selected="selected" </#if>>${sensorinfo.cnName}
                    </option>
                </#list>
            </select>
        </div>
        <div class="f-l m-l-10">
            <label>${locale.getStr("common.startDate")}</label>
            <input class="input-medium Wdate" id="start-time" type="text" name="startTime"
                   onfocus="var endTime=$dp.$('end-time');WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'end-time\')}'})"
                   value="${startTime?string("yyyy-MM-dd HH:mm:ss")!}"/>
        </div>
        <div class="f-l m-l-10">
            <label>${locale.getStr("common.endDate")}</label>
            <input class="input-medium Wdate" id="end-time" type="text" name="endTime"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59',minDate:'#F{$dp.$D(\'start-time\')}'})"
                   value="${endTime?string("yyyy-MM-dd HH:mm:ss")!}"/>
        </div>
        <div class="f-l m-l-10">
            <button id="query-basic-chart" class="btn">${locale.getStr("common.select")}</button>
            <span id="add-compare" class="gray">${locale.getStr("blueplanet.location.addComparision")}</span>
            <span id="cancel-all-compare" class="gray">${locale.getStr("blueplanet.location.cancelAll")}</span>
            <span id="show-hide-compare" class="gray m-l-5">${locale.getStr("blueplanet.realtimeData.hide")}</span>
        </div>
    </div>

    <div id="compare" class="m-b-10 hide compare-style">
        <div class="f-l compare-column hide compare-column-template">
            <div class="f-l compare-column-node">
                <label class="m-0" style="padding-top:3px;">${locale.getStr("blueplanet.location.selectEquipment")}</label>
                <span class="compare-location input uneditable-input">${locale.getStr("blueplanet.location.moveEquipment")}</span>
            </div>
            <div class="f-l compare-column-sensorinfo">
                <label class="sensorinfo-select-label m-0">${locale.getStr("blueplanet.location.compareMonitoring")}</label>
                <select class="sensor-physicalids" multiple="multiple" style="width:300px;">
                </select>
            </div>
            <div class="f-l compare-column-ok">
                <button class="btn btn-small">${locale.getStr("blueplanet.location.compare")}</button>
            </div>
        </div>
    </div>
<#--<span id="YaxisName"></span>-->

<#--<div class="row-fluid m-t-10">-->
<#--<div class="span11 t-a-r">-->
<#--<div id="Yaxis">-->
<#--&lt;#&ndash;todo 切换轴值要清空 换图标&ndash;&gt;-->
<#--<div class="span1 form-inline m-t-10" style="margin-left: 16px">-->
<#--<button id="yAxisUp" class="btn btn-mini" type="button"-->
<#--title="整体增加Y轴范围，如无变化请通过Y轴最大值和最小值输入框进行设置">-->
<#--<i class="icon-arrow-up"></i>-->
<#--</button>-->
<#--<button id="yAxisDomn" class="btn btn-mini" type="button"-->
<#--title="整体缩小Y轴范围，如无变化请通过Y轴最大值和最小值输入框进行设置">-->
<#--<i class="icon-arrow-down"></i>-->
<#--</button>-->
<#--</div>-->
<#--<div class="span3 form-inline m-t-10">-->
<#--Y轴最大值-->
<#--<input class="" value="" id="maxInputButtons" size="12"-->
<#--type="text" style="width:45px;height: 10px;"-->
<#--title="输入Y轴最大值后鼠标点击其它地方进行设置，最大值不能大于400，如果无变化请点击恢复默认">-->
<#--<button id="maxUp" class="btn btn-mini" type="button" title="微调Y轴最大值，如果无变化请手工通过输入框进行设置">-->
<#--<i class="icon-plus-sign"></i>-->
<#--</button>-->
<#--<button id="maxDown" class="btn btn-mini m-r-20" type="button" title="微调Y轴最大值，如果无变化请手工通过输入框进行设置">-->
<#--<i class="icon-minus-sign"></i>-->
<#--</button>-->
<#--</div>-->
<#--<div class="span3 form-inline m-t-10">-->
<#--Y轴最小值-->
<#--<input class="" value="" id="minInputButtons" size="12"-->
<#--type="text" style="width: 45px;height: 10px;"-->
<#--title="输入Y轴最小值后鼠标点击其它地方进行设置，最小值不能小于-100，如果无变化请点击恢复默认">-->
<#--<button id="minUp" class="btn btn-mini" type="button" title="微调Y轴最小值，如果无变化请手工通过输入框进行设置">-->
<#--<i class="icon-plus-sign"></i>-->
<#--</button>-->
<#--<button id="minDown" class="btn btn-mini" type="button" title="微调Y轴最小值，如果无变化请手工通过输入框进行设置">-->
<#--<i class="icon-minus-sign"></i>-->
<#--</button>-->
<#--</div>-->
<#--<div class="span3 m-t-10">-->
<#--<button id="defaultSettings" class="btn btn-mini m-l-20" type="button" title="点击恢复原始状态">-->
<#--恢复默认-->
<#--</button>-->
<#--</div>-->
<#--</div>-->
<#--</div>-->
<#--</div>-->
    <div class="row-fluid m-t-10">
        <div class="span12">
            <div id="basic-chart" style="padding-right: 10px"></div>
        </div>
    </div>
    <div class="clear"></div>
</div>
<div class="row-fluid m-t-10">
    <div id='chart-info' class="span8"></div>
    <div class="span4">
        <div id="advanceTip" style="display: none">
            <p style="color: #969696;float: right;">${locale.getStr("blueplanet.location.clickYAxis")}</p>
        </div>
        <span id="YaxisName"></span>

        <div class="row-fluid m-t-10">
            <div class="span12 t-a-r">
                <div id="Yaxis" style="display: none">
                <#--todo 切换轴值要清空 换图标-->
                    <div class="span1 form-inline m-t-10" style="margin-left: 16px">
                        <button id="yAxisUp" class="btn btn-mini" type="button"
                                title="${locale.getStr("blueplanet.location.increaseYAxis")}">
                            <i class="icon-arrow-up"></i>
                        </button>
                        <button id="yAxisDomn" class="btn btn-mini" type="button"
                                title="${locale.getStr("blueplanet.location.decreaseYAxis")}">
                            <i class="icon-arrow-down"></i>
                        </button>
                    </div>
                    <div class="span3 form-inline m-t-10">
                        ${locale.getStr("blueplanet.location.maximumYAxis")}
                        <input class="" value="" id="maxInputButtons" size="12"
                               type="text" style="width:45px;height: 10px;"
                               title="${locale.getStr("blueplanet.location.inputMaximumYaxis")}">
                    <#--<button id="maxUp" class="btn btn-mini" type="button" title="微调Y轴最大值，如果无变化请手工通过输入框进行设置">-->
                    <#--<i class="icon-plus-sign"></i>-->
                    <#--</button>-->
                    <#--<button id="maxDown" class="btn btn-mini m-r-20" type="button" title="微调Y轴最大值，如果无变化请手工通过输入框进行设置">-->
                    <#--<i class="icon-minus-sign"></i>-->
                    <#--</button>-->
                    </div>
                    <div class="span3 form-inline m-t-10">
                        ${locale.getStr("blueplanet.location.minimumYAxis")}
                        <input class="" value="" id="minInputButtons" size="12"
                               type="text" style="width: 45px;height: 10px;"
                               title="${locale.getStr("blueplanet.location.inputMinimumYaxis")}">
                    <#--<button id="minUp" class="btn btn-mini" type="button" title="微调Y轴最小值，如果无变化请手工通过输入框进行设置">-->
                    <#--<i class="icon-plus-sign"></i>-->
                    <#--</button>-->
                    <#--<button id="minDown" class="btn btn-mini" type="button" title="微调Y轴最小值，如果无变化请手工通过输入框进行设置">-->
                    <#--<i class="icon-minus-sign"></i>-->
                    <#--</button>-->
                    </div>
                    <div class="span4 m-t-10">
                        <button id="defaultSettings" class="btn btn-small m-l-20" type="button" title="${locale.getStr("blueplanet.location.originalStatus")}">
                            ${locale.getStr("blueplanet.location.restoreDefault")}
                        </button>
                    </div>
                </div>
            </div>

        </div>
        <div class="row-fluid" id="checkboxSenior" style="display: none">
            <div class="span12">
                <hr/>
                <input id="senior" type="checkbox" name="senior"/>${locale.getStr("blueplanet.location.combineYaxis")}
            </div>
        </div>
    </div>
</div>
</#macro>


<#macro script>

<script type="text/javascript" src="../assets/select2/3.2/select2.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/lang/zh-cn.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/highstock.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/underscore/1.4.2/underscore-min.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="js/pnotify.js"></script>
    <@scriptTag "js/location/charts/basic-chart.js"/>
<script type="text/javascript">
    function YaxisClick(sensorId, sensorName) {
        $("#Yaxis").show();
        $("#YaxisSensorId").attr("value", sensorId);
        $("#YaxisName").html("调整 <Strong>" + sensorName + "</Strong> 的Y轴上下限：");
    }
    ;
</script>
</#macro>