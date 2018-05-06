<#--
author : chen.yaofei
date : 2016-05-10
-->

<#assign title>位置点信息 - 离线数据</#assign>

<#include "/common/pages/common-tag.ftl">

<#macro head>
<link rel="stylesheet" href="../assets/artDialog/5.0.1-7012746/skins/default.css">
<style type="text/css">
    #sensorInfo li {
        width: 33%;
        float: left;
        list-style: none;
    }
    .control-label {
        margin-right: 15px;
    }
    .btn-m-l{
        margin-left: 180px
    }

</style>
</#macro>

<#macro content>
<div class="container">
    <legend>
        <span title="修改">修改</span>
    </legend>
</div>
<div class="container m-t-10">
    <div class="span12">
        <form id="locationForm" action="offline/updateLocation/update" method="post" class="form-horizontal">
            <h4>基本信息</h4>
                <input type="hidden" id="locationId" name="locationId" value="${location.id}"/>
            <div class="control-group">
                <label class="control-label">${locale.getStr("blueplanet.location.LocationName")}</label>
                <input id="locationName" name="locationName" type="text"
                       value="${location.locationName}"
                       data-value="${location.locationName}">
                <span id="checkLocationName" class="m-t-10 m-l-20 red"></span>
            </div>
            <div class="control-group">
                <label class="control-label">所属区域</label>
                <input class="zone" type="text" name="zoneName"<#if location.zone??>value="${location.zone.zoneName}"</#if> data-zoneId="">
                <input type="hidden" name="zoneId"  value="${location.zoneId!}"/>
            </div>

        <#--位置点能不能显示监测指标:isChangeSensor为true显示监测指标，否则显示选中监测指标-->
            <#if changeSensor>
                <div>
                    <h4>${locale.getStr("blueplanet.offline.sensor")}</h4>
                    <div class="span10" style="margin-left: 60px">
                        <p id="checkSensorError" class="help-block m-t-10 m-l-20 red"></p>
                        <ul id="sensorInfo">
                        <#--常用监测指标-->
                            <div class="control-group">
                                <#if  offlineSensorinfoList?size != 0>
                                    <#list offlineSensorinfoList as offlineSensorinfo>
                                        <li class="sensorInfo">
                                            <label class="checkbox inline"/>
                                            <input name="checkedSensorInfoList" type="checkbox" value="${offlineSensorinfo.sensorPhysicalid}"
                                            <#--遍历被选择的指标，当选择的指标id和当前指标id相等时，说明是已选-->
                                                    <#if location.sensorInfoList??>
                                                        <#list location.sensorInfoList as checkSensor>
                                                            <#if checkSensor.sensorPhysicalid==offlineSensorinfo.sensorPhysicalid>
                                                    checked
                                                            </#if>
                                                        </#list>
                                                    </#if>
                                                    >
                                        <span>
                                        ${offlineSensorinfo.cnName}
                                        </span>
                                            <span class='muted'>(${offlineSensorinfo.units!})</span>
                                        </li>
                                    </#list>
                                </#if>
                            </div>
                        <#--全部监测指标-->
                            <button type="button" class="btn gray" data-toggle="collapse" data-target="#demo">
                                全部监测指标
                            </button>

                            <div id="demo" class="collapse">
                                <legend>
                                    <#if sensorInfos?size != 0>
                                        <#list sensorInfos as sensorInfo>
                                            <li class="sensorInfo">
                                                <label class="checkbox inline"/>
                                                <input name="checkedSensorInfoList" type="checkbox" value="${sensorInfo.sensorPhysicalid}"
                                                <#--遍历被选择的指标，当选择的指标id和当前指标id相等时，说明是已选-->
                                                        <#if location.sensorInfoList??>
                                                            <#list location.sensorInfoList as checkSensor>
                                                                <#if checkSensor.sensorPhysicalid==sensorInfo.sensorPhysicalid>
                                                       checked
                                                                </#if>
                                                            </#list>
                                                        </#if>
                                                        >
                                                            <span>
                                                            ${sensorInfo.cnName}
                                                            </span>
                                                <span class='muted'>(${sensorInfo.units!})</span>
                                            </li>
                                        </#list>
                                    </#if>
                                </legend>
                            </div>
                        </ul>
                    </div>
                </div>
            <#else>
            <#--选中监测指标-->
                <h4>${locale.getStr("blueplanet.offline.sensor")}</h4>
                <input type="hidden" id="showSensor" value="showChecked"/>
            <div class="span10" style="margin-left: 60px">
                <ul>
                    <div class="control-group">
                    <#--常用的监测指标-->
                        <#if  offlineSensorinfoList?size != 0>
                            <#list offlineSensorinfoList as offlineSensorinfo>
                                    <#if location.sensorInfoList??>
                                        <#list location.sensorInfoList as checkSensor>
                                            <#if checkSensor.sensorPhysicalid==offlineSensorinfo.sensorPhysicalid>
                                                <li style="width: 33%;float: left;list-style: none;">
                                                    <span>${offlineSensorinfo.cnName}</span>
                                                    <span class='muted'>(${offlineSensorinfo.units!})</span>
                                                </li>
                                            </#if>
                                        </#list>
                                    </#if>
                            </#list>
                        </#if>
                    <#--全部的监测指标-->
                        <#if sensorInfos?size != 0>
                            <#list sensorInfos as sensorInfo>
                                <#if location??>
                                    <#if location.sensorInfoList??>
                                        <#list location.sensorInfoList as checkSensor>
                                            <#if checkSensor.sensorPhysicalid==sensorInfo.sensorPhysicalid>
                                                <li style="width: 33%;float: left;list-style: none;">
                                                    <span>${sensorInfo.cnName}</span>
                                                    <span class='muted'>(${sensorInfo.units!})</span>
                                                </li>
                                            </#if>
                                        </#list>
                                    </#if>
                                </#if>
                            </#list>
                        </#if>
                    </div>
                </ul>
            </#if>
        </form>
    </div>
    <div>
        <a type="input" class="btn btn-primary m-t-30 btn-m-l" id="submit-btn">保存</a>
        <a type="input" class="btn m-t-30 m-l-10" href="offline/offline.action">返回</a>
    </div>
</div>
<div class="hide">
    <div id="zoneTreeDialog" class="span4">
        <div id="zoneTree" class="ztree" style="overflow: auto;height: 400px"></div>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>
</#macro>

<#macro script>
    <@scriptTag "../assets/artDialog/5.0.1-7012746/artDialog.min.js"/>
    <@scriptTag "../assets/jquery-validation/1.11.1/dist/jquery.validate.js"/>
    <@scriptTag "../assets/jquery-validation/1.11.1/localization/messages_zh.js"/>
<script type="text/javascript" src="js/offline/offline-location-add-or-update.js"></script>
</#macro>