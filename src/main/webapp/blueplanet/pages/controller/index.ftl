<#--
调控主页

@author liuzhu
@date 2015-6-16

@author chenyaofei
@date 2016-06-24
@description 翻新主动调控
-->
<#assign title=locale.getStr("blueplanet.controller.controlHome")>
<#include "/common/pages/common-tag.ftl">

<#macro head>
<link rel="stylesheet" href="../assets/font-awesome/3.2.1/css/font-awesome.min.css"/>
<style type="text/css">
    /*.humidityChart {*/
    /*height: 200px;*/
    /*width: 200px;*/
    /*}*/
</style>
</#macro>
<#macro content>
<div class="row-fluid m-t-20">
    <div class="span6">
        <div class="alert alert-info">
            <i class="icon-info-sign"></i>
        ${locale.getStr("blueplanet.controller.activeControl")}
        </div>
    </div>
</div>
    <div class="row-fluid ">
        <div class="span12 well well-small">
            <b>状态标识：</b>
            <i class="icon-ok-circle text-success m-l-10"></i>正常状态
            <i class="icon-warning-sign text-warning m-l-10"></i>异常状态
            <i class="icon-off text-error m-l-10"></i>停机状态
        </div>
    </div>
    <#if hasHumidity || hasAirConditioner>
    <div>
        <div class="span12 well well-small">
            <b>设备选择：</b>
            <#if hasHumidity><a class="btn <#if deviceType==1>btn-primary</#if>" href="controller/index" role="button">恒湿机</a></#if>
            <#if hasAirConditioner><a class="btn <#if deviceType==2>btn-primary</#if>" href="controller/index?deviceType=2" role="button">空调组</a></#if>
        </div>
    </div>

    <#list devicePropertyVOList as deviceProperty>
        <#if deviceProperty.humidityController??>
            <#local humidityControllerState=deviceProperty.humidityController.humidityControllerState/>
            <#local humidityController=deviceProperty.humidityController/>
            <#if humidityControllerState.outOfRangeCauseStop>
                <#local currentState="stop"/>
            <#elseif deviceProperty.faultCode??  || humidityControllerState.humidityAlarm || humidityControllerState.humidityHigh || humidityControllerState.humidityLow
            || humidityControllerState.waterLow || humidityControllerState.waterDrainageFull || humidityControllerState.temperatureAlarm
            || humidityControllerState.temperatureLow || humidityControllerState.outFanBreakDown || humidityControllerState.cycleFanBreakDown
            || humidityControllerState.withoutSensor || humidityControllerState.ptdTemperatureHigh || humidityControllerState.ptdTemperatureLow>
                <#local currentState="exception"/>
            <#else>
                <#local currentState="normal"/>
            </#if>
            <#assign currentText>
                <#if "stop"==currentState>
                工作停止
                <#elseif "normal"==currentState>
                工作正常
                <#else>
                    <#if humidityControllerState.humidityAlarm>
                    展柜湿度报警
                    </#if>
                    <#if humidityControllerState.humidityHigh>
                    展柜湿度过高
                    </#if>
                    <#if humidityControllerState.humidityLow>
                    展柜湿度过低
                    </#if>
                    <#if humidityControllerState.waterLow>
                    缺水
                    </#if>
                    <#if humidityControllerState.waterDrainageFull>
                    排水容器满
                    </#if>
                    <#if humidityControllerState.temperatureAlarm>
                    温度报警
                    </#if>
                    <#if humidityControllerState.temperatureLow>
                    环境温度过低
                    </#if>
                    <#if humidityControllerState.outFanBreakDown>
                    外部风扇损坏
                    </#if>
                    <#if humidityControllerState.cycleFanBreakDown>
                    循环风扇损坏
                    </#if>
                    <#if humidityControllerState.withoutSensor>
                    无环境传感器
                    </#if>
                    <#if humidityControllerState.ptdTemperatureHigh>
                    PTD温度过高
                    </#if>
                    <#if humidityControllerState.ptdTemperatureLow>
                    PTD温度过低
                    </#if>
                </#if>
            </#assign>

            <#if deviceProperty_index%3==0>
            <div class="row-fluid m-b-20">
            </#if>
        <div class="span4">
        <div class="panel panel-default">
            <div class="panel-heading"
                 style="font-size: 25px;line-height: 40px; height:40px;background-color: #36648B;border-top-left-radius: 5px;border-top-right-radius: 5px;">
                <#if "stop"==currentState>
                <#--停机状态-->
                    <span class="icon-off text-error m-l-10"></span>
                <#elseif "exception"==currentState>
                <#--异常状态-->
                    <span class="icon-warning-sign text-warning m-l-10"></span>
                <#elseif "normal"==currentState>
                <#--正常状态-->
                    <span class="icon-ok-circle text-success m-l-10"></span>
                <#else>
                </#if>
                <a class="white" style="color: #FFFFFF;text-decoration: none"
                   href="device/${deviceProperty.deviceId}/detail/edit">
                    <#if deviceProperty.location??>
                      ${deviceProperty.location.locationName}
                    <#else >
                      ${deviceProperty.deviceId?substring(8)}
                    </#if>
                </a>
            </div>
            <div class="panel-body"
                 style="border-left:1px #dddddd solid;border-right:1px #dddddd solid;background-color: #F0FFFF;padding: 10px;">
                <div class="row-fluid">
                    <div class="span5">
                        <#if "stop"==currentState>
                        <#--停机状态-->
                            <img src="images/stop.png"/>
                        <#elseif  "exception"==currentState>
                        <#--异常状态-->
                            <img src="images/exception .png"/>
                        <#else>
                        <#--正常状态 -->
                            <img src="images/normal.png"/>
                        </#if>
                    </div>

                    <div class="span7" style="height: 130px;">
                    <#--<div class="humidityChart"-->
                             <#--data-value="${deviceProperty.humidityController.currentHumidity}"></div>-->
                        <#if humidityController.highHumidity != 65535 || humidityController.lowHumidity != 65535>
                        <@panelBody humidityController/>
                    <#else>
                        <@justOne humidityController/>
                    </#if>
                    </div>
                </div>

            </div>
            <div class="panel-footer"
                 style="background-color: #F2F2F2;border:1px #dddddd solid; height: 30px;line-height:30px;border-bottom-left-radius: 5px;border-bottom-right-radius: 5px;">
                <#if "stop"==currentState>
                <#--停机状态-->
                <div class="m-l-10 text-error">
                ${currentText}
                <#elseif  "exception"==currentState>
                <#--异常状态-->
                <div class="m-l-10 text-warning">
                ${currentText}
                <#else>
                <#--正常状态 -->
                <div class="m-l-10">
                ${currentText}
                </#if>
            </div>
            </div>
            </div>
            </div>
            <#if deviceProperty_index%3==2 || !deviceProperty_has_next>
            </div>
            </#if>
        </#if>
        <#if deviceProperty.airConditionerController??>
            <#local airConditionerController=deviceProperty.airConditionerController/>
            <#if airConditionerController.switchState == 0>
                <#local currentState="off"/>
            <#else>
                <#local currentState="on"/>
            </#if>
            <#assign currentText>
                <#if "off"==currentState>
                    关机
                <#else>
                    开机
                </#if>
            </#assign>

            <#if deviceProperty_index%3==0>
            <div class="row-fluid m-b-20">
            </#if>
        <div class="span4">
        <div class="panel panel-default">
            <div class="panel-heading"
                 style="font-size: 25px;line-height: 40px; height:40px;background-color: #36648B;border-top-left-radius: 5px;border-top-right-radius: 5px;">
                <#if "off"==currentState>
                <#--关机状态-->
                    <span class="icon-off text-error m-l-10"></span>
                <#else>
                <#--开机状态-->
                    <span class="icon-ok-circle text-success m-l-10"></span>
                </#if>
                <a class="white" style="color: #FFFFFF;text-decoration: none"
                   href="device/${deviceProperty.deviceId}/detail/edit">
                <#if deviceProperty.location??>
                    ${deviceProperty.location.locationName}
                <#else >
                    ${deviceProperty.deviceId?substring(8)}
                </#if>
                </a>
            </div>
            <div class="panel-body"
                 style="border-left:1px #dddddd solid;border-right:1px #dddddd solid;background-color: #F0FFFF;padding: 10px;">
                <div class="row-fluid">
                    <div class="span5 m-l-20">
                        <#if "stop"==currentState>
                        <#--关机状态-->
                            <img style="margin:20px 20px" src="images/airConditioner.png"/>
                        <#else>
                        <#--开机状态 -->
                            <img style="margin: 20px 20px" src="images/airConditioner.png"/>
                        </#if>
                    </div>

                    <div class="span7" style="height: 130px;">
                        <@panelBodyAirConditioner airConditionerController/>
                    </div>
                </div>

            </div>
            <div class="panel-footer"
                 style="background-color: #F2F2F2;border:1px #dddddd solid; height: 30px;line-height:30px;border-bottom-left-radius: 5px;border-bottom-right-radius: 5px;">
                <#if "off"==currentState>
                <#--关机机状态-->
                <div class="m-l-10 text-error">
                ${currentText}
                <#else>
                <#--开机状态 -->
                <div class="m-l-10">
                ${currentText}
                </#if>
            </div>
            </div>
            </div>
            </div>
            <#if deviceProperty_index%3==2 || !deviceProperty_has_next>
            </div>
            </#if>
        </#if>
    </#list>
    <#else >
    <h3>暂无设备</h3>
    </#if>
    <#if deviceType == 1>
    <hr style="height: 1px;background-color: #CCCCCC"/>

    <div class="row-fluid m-t-10">

        <div class="span12">
            <div class="row-fluid">
                <div class="span6">

                    <div class="alert alert-info">
                        <i class="icon-info-sign"></i>
                    ${locale.getStr("blueplanet.controller.passiveControl")}
                    </div>
                </div>
            </div>
            <#if alarmRecords??>
                <#if (alarmRecords?size<1)>
                ${locale.getStr("blueplanet.controller.alarmNoData")}
                <#else >
                    <table class="table table-bordered table-striped table-center">
                        <thead>
                        <tr>
                            <th>${locale.getStr("common.number")}</th>
                            <th>${locale.getStr("blueplanet.controller.location")}</th>
                            <th>${locale.getStr("blueplanet.controller.factor")}</th>
                            <th>${locale.getStr("blueplanet.controller.measure")}</th>
                            <th>${locale.getStr("blueplanet.controller.alarmTime")}</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#if alarmRecords?size gt 0>
                                <#list alarmRecords as alarmRecord>
                                <tr>
                                    <td>${alarmRecord_index+1}</td>
                                    <td>${alarmRecord.locationName}</td>
                                    <td>${alarmRecord.factor}</td>
                                    <td>

                                        <ul style="list-style: none;">
                                            <#list alarmRecord.measureVOs as measure>
                                                <li>
                                                ${measure_index+1}.${measure.description}
                                                </li>
                                            </#list>
                                        </ul>
                                    </td>
                                    <td>${alarmRecord.alarmTime?string('yyyy-MM-dd HH:mm')}</td>
                                </tr>
                                </#list>
                            </#if>
                        </tbody>
                    </table>
                </#if>
            </#if>
        </div>
    </div>
    </#if>
</#macro>
<#macro panelBody humidityController>
    <div class="m-t-30">
        <div class="showHumidityDiv">
            <img class="m-l-30" src="images/normalHumidity.png"/>
            <b class="m-l-10">目标湿度：${humidityController.targetHumidity/10}%</b>
        </div>

        <div class="showHumidityDiv">
            <img class="m-l-30" src="images/highHumidity.png"/>
            <b class="m-l-10">
                湿度上限：
                <#if humidityController.highHumidity != 65535>
                ${humidityController.highHumidity/10}%
                <#else>
                    无
                </#if>
            </b>
        </div>

        <div class="showHumidityDiv">
            <img class="m-l-30" src="images/lowHumidity.png"/>
            <b class="m-l-10">
                湿度下限：
                <#if humidityController.lowHumidity != 65535>
                ${humidityController.lowHumidity/10}%
                <#else>
                    无
                </#if>
            </b>
        </div>
    </div>
</#macro>
<#macro panelBodyAirConditioner airConditionerController>
    <div class="m-t-30">
        <div>
            <img class="m-l-30" src="images/airConditionerHumidity.png"/>
            <b class="m-l-10">目标湿度:${airConditionerController.targetHumidity}%(RH)</b>
        </div>

        <div class="m-t-10">
            <img class="m-l-30" src="images/airConditionerTemperature.png"/>
            <b class="m-l-10">
                目标温度:${airConditionerController.targetTemperature}℃
            </b>
        </div>

    </div>
</#macro>
<#macro justOne humidityController>
    <div class="m-t-40">
        <div class="showHumidityDiv">
            <img class="m-l-15" style="width: 25px;height: 50px" src="images/normalHumidity.png"/>
            <b class="m-l-5" style="font-size: 20px">目标湿度：${humidityController.targetHumidity/10}%</b>
        </div>
    </div>
</#macro>
<#macro script>
    <!--[if lte IE 8]>
    <script language="javascript" type="text/javascript" src="../assets/flot/d7c58b59f3/excanvas.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
    <script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<#--<script type="text/javascript" src="js/controller/index.js"></script>-->
    <script type="text/javascript" src="../assets/echarts/2.1.8/echarts-all.js"></script>
</#macro>