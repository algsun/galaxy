<#--
@author chen.yaofei
@date 2016.04.27
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
            <span title="添加">${locale.getStr("blueplanet.offline.add")}</span>
        </legend>
</div>
<div class="container m-t-10">
    <div class="span12">
        <form id="locationForm" action="offline/addLocation/add" method="post" class="form-horizontal">
            <h4>${locale.getStr("blueplanet.offline.baseInfo")}</h4>
            <div class="control-group">
                <label class="control-label">${locale.getStr("blueplanet.location.LocationName")}</label>
                <input id="locationName" name="locationName" type="text" data-value=""/>
                <span id="checkLocationName" class="m-t-10 m-l-20 red"></span>
            </div>
            <div class="control-group">
                <label class="control-label">${locale.getStr("common.zone")}</label>
                <input class="zone" type="text"
                       name="zoneName" data-zoneId="">
                <input type="hidden" name="zoneId"/>
            </div>


            <div>
                <h4>${locale.getStr("blueplanet.offline.sensor")}</h4>
                <div class="span10" style="margin-left: 60px">
                    <p id="checkSensorError" class="help-block m-t-10 m-l-20 red"></p>
                    <ul id="sensorInfo">
                    <#--常用监测指标-->
                        <div class="control-group">
                            <#if  offlineSensorinfoList??>
                                <#list offlineSensorinfoList as offlineSensorinfo>
                                    <li class="sensorInfo">
                                        <label class="checkbox inline"/>
                                        <input name="checkedSensorInfoList" type="checkbox"
                                               value="${offlineSensorinfo.sensorPhysicalid}"/>
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
                            ${locale.getStr("blueplanet.offline.allSensor")}
                        </button>

                        <div id="demo" class="collapse">
                            <legend>
                                <#if sensorInfos??>
                                    <#list sensorInfos as sensorInfo>
                                        <li class="sensorInfo">
                                            <label class="checkbox inline"/>
                                            <input name="checkedSensorInfoList" type="checkbox"
                                                   value="${sensorInfo.sensorPhysicalid}"/>
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
        </form>
    </div>
    <div>
        <a type="input" class="btn btn-primary m-t-30 btn-m-l" id="submit-btn">
            ${locale.getStr("common.save")}
        </a>
        <a type="input" class="btn m-t-30 m-l-10" href="offline/offline.action">
            ${locale.getStr("common.return")}
        </a>
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