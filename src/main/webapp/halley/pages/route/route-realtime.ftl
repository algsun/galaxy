<#--
外展管理，实时数据与运行状态

@author wang.geng
@date 2013-10-21
-->

<#assign title="运行状态 - 外展管理">
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>

</#macro>

<#macro content>

<div class="row">
    <div class="span12">
        <fieldset>
            <legend>
                <a class="go-back" href="queryExhibitions" title="返回">
                    <i class="mw-icon-prev"></i>运行状态
                </a>
            </legend>
        </fieldset>
    </div>
</div>
<div class="row">
<#-- 实时地图运行状态 -->
    <div class="span12">
        <div class="control-group hide">
            <input id="exhibitionId" type="hidden" value="${exhibitionId}"/>
            <input id="pathType" type="hidden" value="${pathType!}"/>
            <input type="hidden" value="${alarmRange!}" id="alarmRange"/>
            <#--<input id="Lat" type="hidden" value="${Session["currentLogicGroup"].site.latGoogle}"/>-->
            <#--<input id="Lng" type="hidden" value="${Session["currentLogicGroup"].site.lngGoogle}"/>-->
        </div>
        <div class="row">
            <div class="span3">
                <#if carsWithDevices??>
                    <#list carsWithDevices as car>
                        <div class="realTimeDiv p-10">
                            <h5 style="text-align: center">${car.plateNumber}</h5>
                            <div id="address_${car.plateNumber}"></div>
                            <div id="shake_${car.plateNumber}"></div>
                            <div id="accl_${car.plateNumber}"></div>
                            <div id="swh_${car.plateNumber}"></div>
                        </div>
                    </#list>
                </#if>
            </div>
            <div class="span9" id="mapContainer" style="height: 600px;"></div>
        </div>
    </div>
</div>
<div>
<#-- 实时监测数据 -->
    <#if realTimeDateList?size gt 0>
        <#list realTimeDateList as realtimeData>
            <#assign sensorinfo = realtimeData.locationSensorInfoMap>
            <div id="container${realtimeData_index}" class="span12 realTimeDiv"
                 style="margin-left: 0;margin-right: 0;width: 937px">
                <h5 style="text-align: center">${realtimeData.locationName}</h5>

                <div style="margin-left: 50px">
                    <#if realtimeData.locationSensorInfoMap??>
                        <#list sensorinfo?keys as key>
                            <#assign data = sensorinfo.get(key)>
                            <#assign picName = key+"_24.png">
                            <div class="span2 m-t-0 m-b-20" style="width: 190px">
                                <div>
                                    <img
                                            src="../blueplanet/images/sensor/${picName}" alt="" title="${data.cnName}"/>
                                    <span>${data.cnName}：</span>
                                    <#if data.state == 1>
                                        <span id="${realtimeData.nodeId}-${key?c}"
                                              stamp-time="${realtimeData.stamp?datetime}">${data.sensorPhysicalValue}${data.units}</span>
                                    <#else>
                                        --
                                    </#if>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
        </#list>
    </#if>
</div>

</#macro>

<#macro script>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=9016240f787e72ac5d8fed77b3b5cabc"></script>
<script type="text/javascript" src="../assets/jquery-ui/1.9.2/js/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="../assets/moment/2.0.0/moment.min.js"></script>
    <@scriptTag "js/route/realtime-path.js"/>
    <@scriptTag "js/route/realtime-data.js"/>
</#macro>