<#--
设备编辑

@author gaohui
@date 2013-01-24
@check @wang yunlong 2013-02-26 #1777
-->

<#assign title>${locale.getStr("blueplanet.device.detailEdit.title")}</#assign>
<#include "/common/pages/common-tag.ftl">

<#macro head>
<link rel="stylesheet" href="../assets/bootstrap-switch/2.0.1/css/bootstrap2/bootstrap-switch.min.css"/>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
    /* 右侧监测指标列表样式 */

    ul.list {
        border-top: 1px solid #ddd;
        list-style-type: none;
        margin-left: 0;
    }

    ul.list > li {
        border-bottom: 1px solid #ddd;
        padding-top: 5px;
        padding-bottom: 5px;
    }

    ul.list > li:hover {
        background-color: #fff;
    }

    .small {
        /*font-size: small;*/
        font-style: italic;
    }
</style>

</#macro>


<#include "../device-helper.ftl">
<#macro content>
<div class="row-fluid m-t-10">
    <div class="span12">
        <ul class="nav nav-pills">
            <li><a href="devices">${locale.getStr("blueplanet.device.deviceList")}</a></li>
            <li><a href="devices/realtime-data">${locale.getStr("blueplanet.common.realtimeData")}</a></li>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span12 p-t-10">
        <div class="row-fluid">
            <div class="span12">
                <h4><span
                        class="muted">${locale.getStr("blueplanet.device.deviceDetail")}
                    ：</span><@typeIconOfDevice device.nodeType/> ${device.nodeName!device.nodeId?substring(8)}
                </h4>
            </div>
        </div>
        <hr class="m-t-0">

    <#-- 显示消息 -->
        <#if _message?? >
            <#if _success>
            <div class="alert alert-success">
            <#else>
            <div class="alert alert-error">
            </#if>
            <a class="close" data-dismiss="alert">×</a>
        ${_message}
        </div>
        </#if>
    </div>
    </div>


    <div class="row-fluid m-t-10">
        <div class="span7">
            <form class="form-horizontal" action="device/${device.nodeId}/detail" method="post">
                <input type="hidden" name="deviceType" value="${device.nodeType}">
                <input id="zoneIdInput" type="hidden" name="zoneId" value="${device.zoneId!}">

                <fieldset>
                    <div class="control-group">
                        <label class="control-label">${locale.getStr("blueplanet.controlPanel.deviceID")}</label>

                        <div class="controls p-v-5">
                            <@strongNodeId device.nodeId/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">${locale.getStr("common.deviceType")}</label>

                        <div class="controls p-v-5">
                            <@typeIconOfDevice device.nodeType/> <@typeNameOfDevice device.nodeType/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">电压阈值</label>
                        <div class="controls">
                            <input id="voltageThreshold" name="voltageThreshold" placeholder="3.5" class="input"
                                   maxlength="25" type="text"
                                   value="${device.voltageThreshold!}"
                            >
                            <span style="color:#969696;">（单位：伏，范围：大于0，默认为3.5）</span>
                            <span class="help-inline red"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">预热时间 </label>
                        <div class="controls p-v-5">
                            <span>${device.warmUp}</span>
                            <span style="color:#969696;">（单位：秒）</span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label">${locale.getStr("blueplanet.controlPanel.workingPeriod")}</label>
                        <div class="controls">
                            <#if device.nodeVersion==3 && device.notControl>
                                <select name="interval" class="selectpicker" disabled="disabled">
                                    <option value="${device.interval}"><@deviceWorkInterval device.interval/></option>
                                </select>
                            <#else>
                                <select id="interval" name="interval" class="selectpicker">
                                </select>
                            </#if>
                            <input name="control" type="hidden"
                                   value="${device.notControl?string('true','false')}">
                            <input id="intervalChange" name="isIntervalChange" type="hidden" value="false">
                            <input class="hide" name="nodeVersion" type="text" value="${device.nodeVersion}">
                            <input class="hide" id="intervalValue" type="text" value="${device.interval?c}">
                            <input id="warmUp" class="hide" type="text" value="${device.warmUp?c}">
                            <input id="deviceWorkInterval" class="hide" type="text"
                                   value="<@deviceWorkInterval device.interval/>">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button id="updateSubmitButton" class="btn btn-primary"
                                    type="submit">${locale.getStr("common.save")}</button>
                            <a class="btn"
                               href="device/${deviceId}/detail">${locale.getStr("blueplanet.controlPanel.detail")}</a>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>

        <div class="span5">
        <#-- 如果是节点和从模块则显示监测指标 -->
        <#--<#if device.nodeType == 1 || device.nodeType == 4>-->
            <h5 class="f-n">${locale.getStr("blueplanet.controlPanel.monitoringIndicators")}</h5>
            <ul class="list">
                <#list sensorinfoes as sensorinfo>
                    <#local sensorId = sensorinfo.sensorPhysicalid>
                    <li data-sensorPhysicalId="${sensorId?c}">
                        <strong>${sensorinfo.cnName}</strong>
                        <span class="muted">(${sensorinfo.units})</span>

                    <#-- 如果有公式系数 -->
                        <#if formulaMap.get(sensorId)??>
                            <#local formula = formulaMap.get(sensorId)>
                            <#local default = formula.formulaParams>

                            <#if customFormulaParamMap.get(sensorId)??>
                                <#local paramMap = customFormulaParamMap.get(sensorId)>
                                <#local isCustomParam = true >
                            <#else>
                                <#local paramMap = formula.formulaParams>
                                <#local isCustomParam = false >
                            </#if>

                            <a class="muted f-r" href="#"
                               onclick="javascript: $(this).next().toggle(); return false;"><i
                                    class="icon-pencil"></i>${locale.getStr("blueplanet.location.edit")}</a>

                            <ul>
                                <li>${locale.getStr("blueplanet.device.sensorPrecision")}
                                    ：${sensorinfo.sensorPrecision}${locale.getStr("blueplanet.device.digite")}</li>
                                <li>${locale.getStr("blueplanet.device.symbolType")}
                                    ：<@signTypeName formula.signType /></li>
                                <li>${locale.getStr("blueplanet.device.formula.rangeType")}
                                    ：<@rangeTypeName formula.yRangeType /></li>
                                <li>${locale.getStr("proxima.common.type")}：${formula.formulaName}</li>

                                <#if customFloatValueMap.get(sensorId)??>
                                    <#local floatValue = customFloatValueMap.get(sensorId)>
                                    <#local defaultFloat = floatValueMap.get(sensorId)>
                                    <#local isCustomFloatValue =true >
                                    <#local isFoundFloatValue =true >
                                <#elseif floatValueMap.get(sensorId)??>
                                    <#local floatValue = floatValueMap.get(sensorId)>
                                    <#local isCustomFloatValue =false >
                                    <#local isFoundFloatValue =true >
                                <#else>
                                    <#local isCustomFloatValue =false >
                                    <#local isFoundFloatValue =false >
                                </#if>
                                <#if formula.minY == 0>

                                    <li>${locale.getStr("blueplanet.device.maxResults")}：${formula.maxY}</li>
                                    <ol class="m-l-5" style="list-style-type: none;" data-params>
                                        <li>
                                            <lable class="small">向上浮动值</lable>
                                            <a data-toggle="tooltip"
                                               title="设置后，最大值<结果值≤最大值+输入值，将被视为最大值。如：最大值100，输入值5，结果值102，那么100<102≤100+5，最终结果值为100"><i
                                                    class="icon-question-sign"></i></a>：
                                            <input type="text" class="input-small m-t-5"
                                                   id="maxUpFloat${deviceId}${sensorId?c}"
                                                <#if isFoundFloatValue>
                                                   value="${floatValue.maxUpFloat}"
                                                <#else>
                                                   value="0"
                                                </#if>
                                            />
                                            <span id="maxUpFloatSpan${deviceId}${sensorId?c}" class="m-l-10"
                                                  style="color: red"></span>
                                        </li>

                                    </ol>
                                    <li>${locale.getStr("blueplanet.device.minResults")}：${formula.minY}</li>
                                    <ol class="m-l-5" style="list-style-type: none;" data-params>
                                        <li>
                                            <lable class="small">向上浮动值</lable>
                                            <a data-toggle="tooltip"
                                               title="设置后，最小值<结果值≤最小值+输入值，将被视为最小值。如：最小值0，输入值5，结果值2，那么0<2≤0+5，最终结果值为0"><i
                                                    class="icon-question-sign"></i></a>：
                                            <input type="text" class="input-small m-t-5"
                                                   id="minUpFloat${deviceId}${sensorId?c}"
                                                <#if isFoundFloatValue>
                                                   value="${floatValue.minUpFloat}"
                                                <#else>
                                                   value="0"
                                                </#if>
                                                   data-zero="dataZero"/>
                                            <span id="minUpFloatSpan${deviceId}${sensorId?c}" class="m-l-10"
                                                  style="color: red"></span>
                                        </li>
                                        <li>
                                            <lable class="small">向下浮动值</lable>
                                            <a data-toggle="tooltip"
                                               title="设置后，最小值>结果值≥最小值-输入值的绝对值，将被视为最小值。如：最小值0，输入值3，结果值-2，那么0>-2≥0-3，最终结果值为0"><i
                                                    class="icon-question-sign"></i></a>：
                                            <input type="text" class="input-small m-t-5"
                                                   id="minDownFloat${deviceId}${sensorId?c}"
                                                <#if isFoundFloatValue>
                                                   value="${floatValue.minDownFloat}"
                                                <#else>
                                                   value="0"
                                                </#if>
                                            />
                                        </li>
                                    </ol>
                                <#else>
                                    <li>${locale.getStr("blueplanet.device.maxResults")}：${formula.maxY}</li>
                                    <ol class="m-l-5" style="list-style-type: none;" data-params>
                                        <li>
                                            <lable class="small">向上浮动值</lable>
                                            <a data-toggle="tooltip"
                                               title="设置后，最大值<结果值≤最大值+输入值，将被视为最大值。如：最大值100，输入值5，结果值102，那么100<102≤100+5，最终结果值为100"><i
                                                    class="icon-question-sign"></i></a>：
                                            <input type="text" class="input-small m-t-5"
                                                   id="maxUpFloat${deviceId}${sensorId?c}"
                                                <#if isFoundFloatValue>
                                                   value="${floatValue.maxUpFloat}"
                                                <#else>
                                                   value="0"
                                                </#if>
                                            />
                                            <span id="maxUpFloatSpan${deviceId}${sensorId?c}" class="m-l-10"
                                                  style="color: red"></span>
                                        </li>
                                    </ol>
                                    <li>${locale.getStr("blueplanet.device.minResults")}：${formula.minY}</li>
                                    <ol class="m-l-5" style="list-style-type: none;" data-params>
                                        <li>
                                            <lable class="small">向下浮动值</lable>
                                            <a data-toggle="tooltip"
                                               title="设置后，最小值>结果值≥最小值-输入值的绝对值，将被视为最小值。如：最小值0，输入值3，结果值-2，那么0>-2≥0-3，最终结果值为0"><i
                                                    class="icon-question-sign"></i></a>：
                                            <input type="text" class="input-small m-t-5"
                                                   id="minDownFloat${deviceId}${sensorId?c}"
                                                <#if isFoundFloatValue>
                                                   value="${floatValue.minDownFloat}"
                                                <#else>
                                                   value="0"
                                                </#if>
                                            />
                                        </li>
                                    </ol>
                                </#if>
                                <lable>
                                    <button class="m-l-20 btn btn-mini btn-primary"
                                            data-deviceId="${deviceId}"
                                            data-sensorId="${sensorId?c}"
                                            data-button="custom-floatValue">
                                    ${locale.getStr("common.save")}
                                    </button>
                                    <#if isCustomFloatValue>
                                        <button class="btn btn-mini"
                                                id="reset${deviceId}${sensorId?c}"
                                                data-deviceId="${deviceId}"
                                                data-sensorId="${sensorId?c}"
                                                data-button="reset-floatValue">
                                            恢复默认
                                        </button>
                                    </#if>
                                </lable>


                                <hr style="border: solid 1px #D1EEEE;margin-left: -20px;">
                                <li>${locale.getStr("blueplanet.device.formulaParam")}：

                                    <ol class="m-l-5" style="list-style-type: none;" data-params>
                                        <#list paramMap?keys as key>
                                            <li><i class="m-r-5">${key}. </i>
                                                <input class="input-small"
                                                       type="text"
                                                       data-coefficient-name="${key}"
                                                       data-default="${default.get(key)}"
                                                       value="${paramMap.get(key)}">

                                                <#if isCustomParam>
                                                    <span class="muted">（${locale.getStr("blueplanet.device.default")}
                                            ：${default.get(key)}）</span>
                                                </#if>
                                            </li>
                                        </#list>
                                    </ol>
                                    <button class="m-l-20 btn btn-mini btn-primary"
                                            data-device-id="${deviceId}"
                                            data-sensorPhysicalId="${sensorId?c}"
                                            data-button="custom-coefficient">${locale.getStr("common.save")}
                                    </button>
                                    <#if isCustomParam>
                                        <button class="btn btn-mini"
                                                data-device-id="${deviceId}"
                                                data-sensorPhysicalId="${sensorId?c}"
                                                data-button="reset-coefficient">恢复默认
                                        </button>
                                    </#if>
                                </li>
                                <hr style="border: solid 1px #D1EEEE;margin-left: -20px;">
                                <li>
                                    <#local max = sensorinfo.maxValue?c>
                                    <#local min = sensorinfo.minValue?c>
                                ${locale.getStr("blueplanet.device.upperLimit")}：<input type="text"
                                                                                        class="input-small m-t-5"
                                                                                        id="max${deviceId}${sensorId}"
                                    <#if max != "0">
                                                                                        value="${max}"
                                    </#if>/>
                                    <span id="maxSpan${deviceId}${sensorId}" class="m-l-10"
                                          style="color:red"></span>
                                </li>
                                <li>
                                ${locale.getStr("blueplanet.device.lowerLimit")}：<input type="text"
                                                                                        class="input-small m-t-5"
                                                                                        id="min${deviceId}${sensorId}"
                                                                                        <#if min != "0">value="${min}"</#if>/>
                                    <span id="minSpan${deviceId}${sensorId}" class="m-l-10"
                                          style="color: red"></span>
                                    <br>
                                    <button class="m-l-20 btn btn-mini btn-primary setSensorThreshold"
                                            name="${deviceId}"
                                            data-sensorId="${sensorId}">
                                    ${locale.getStr("common.save")}
                                    </button>
                                </li>
                            </ul>
                        <#else>
                            <span class="muted pull-right">${locale.getStr("blueplanet.device.noCoefficient")}</span>
                        </#if>
                    </li>
                </#list>
            </ul>

            <button class="btn btn-mini m-b-20"
                    data-device-id="${deviceId}"
                    data-button="reset-all-coefficient">${locale.getStr("blueplanet.device.allResetCoefficients")}
            </button>
        <#--</#if>-->

            <#if isHumCompensate>
                <div class="well well-small">
                    <strong style="margin-right: 20px;">${locale.getStr("blueplanet.device.humidityCompensation")}</strong>
                    <input class="switch-mini" type="checkbox"
                           <#if device.humCompensate == 1>checked="checked"</#if>
                           data-device-id="${deviceId}"
                           data-type="switch-button"
                           data-on-label=${locale.getStr("blueplanet.offline.open")}
                           data-off-label=${locale.getStr("blueplanet.offline.close")}
                           data-route="${device.humCompensate}"/>
                </div>
            </#if>

            <#if device.nodeType==1>
                <div class="well well-small">
                    <strong style="margin-right: 20px;">${locale.getStr("blueplanet.device.calibrationMode")}</strong>
                    <input id="demarcate" class="switch-mini" type="checkbox"
                           <#if device.demarcate == 1>checked="checked"</#if>
                           data-device-id="${deviceId}"
                           data-on-label=${locale.getStr("blueplanet.offline.open")}data-off-label=${locale.getStr("blueplanet.offline.close")}
                           data-demarcate="${device.demarcate}"/>
                </div>

                <div class="well well-small">
                    <strong style="margin-right: 20px;">
                        &nbsp;&nbsp;&nbsp;&nbsp;${locale.getStr("blueplanet.device.screen")}</strong>
                    <input id="switchScreen" class="switch-mini" type="checkbox"
                           <#if device.screenState == 1>checked="checked"</#if>
                           data-device-id="${deviceId}"
                           data-on-label=${locale.getStr("blueplanet.offline.open")}data-off-label=${locale.getStr("blueplanet.offline.close")}
                           data-screenState="${device.screenState}"/>
                </div>
            </#if>
        <#-- 可控 -->
            <#if !device.notControl>
                <h5 class="f-n">${locale.getStr("blueplanet.device.control")}</h5>

                <div class="well well-small">
                ${locale.getStr("blueplanet.device.specifiesParent")}
                    <form class="form-inline f-r" action="device/${deviceId}/default-parent">
                        <input name="parentId" type="text"/>
                        <button class="btn btn-small"
                                type="submit">${locale.getStr("blueplanet.controlPanel.setting")}</button>
                    </form>
                </div>

                <div class="well well-small">
                ${locale.getStr("blueplanet.device.RFNotHibernate")}
                    <form class="form-inline f-r" action="device/${deviceId}/rf-alive">
                        <input name="enable" type="hidden" value="false"/>
                        <button class="btn btn-small"
                                type="submit">${locale.getStr("blueplanet.offline.close")}</button>
                    </form>
                    <form class="form-inline f-r" action="device/${deviceId}/rf-alive">
                        <input name="enable" type="hidden" value="true"/>
                        <button class="btn btn-small" type="submit">${locale.getStr("blueplanet.offline.open")}</button>
                    </form>
                </div>
                <div class="well well-small">
                ${locale.getStr("blueplanet.device.PositionDevice")}
                    <button id="locate" class="btn btn-small"
                            type="button">${locale.getStr("blueplanet.offline.position")}</button>
                </div>
                <div class="well well-small">
                    <button id="reboot" class="btn btn-small"
                            type="button">${locale.getStr("blueplanet.device.reboot")}</button>
                </div>
                <span class="help-inline red"></span>
            </#if>

            <#if isHumidityController>
                <input type="hidden" id="isHumidityController" value="true"/>
                <h5 class="f-n">${locale.getStr("blueplanet.device.humidityState")}</h5>
                <#if humidityControllerState??>
                    <#if humidityType==1>
                        <table class="table table-striped">
                            <tr>
                                <td style="width: 100px;">${locale.getStr("blueplanet.controller.humidityHigh")}：
                                </td><@checkStatus humidityControllerState.humidityHigh/>
                                <td style="width: 100px;">${locale.getStr("blueplanet.device.showcaseHumidityLow")}：
                                </td><@checkStatus humidityControllerState.humidityLow/>
                            <tr>
                            <tr>
                                <td>${locale.getStr("blueplanet.device.dry")}：
                                </td><@checkStatus humidityControllerState.waterLow/>
                                <td>${locale.getStr("blueplanet.device.drainFull")}：
                                </td><@checkStatus humidityControllerState.waterDrainageFull/>
                            <tr>
                            <tr>
                                <td>${locale.getStr("blueplanet.device.temperatureLow")}：
                                </td><@checkStatus humidityControllerState.temperatureLow/>
                                <td>${locale.getStr("blueplanet.device.humidityOverrun")}：
                                </td><@checkStatus humidityControllerState.outOfRangeCauseStop/>
                            <tr>
                            <tr>
                                <td>${locale.getStr("blueplanet.device.externalFanDamage")}：
                                </td><@checkStatus humidityControllerState.outFanBreakDown/>
                                <td>${locale.getStr("blueplanet.device.circulationFanDamage")}：
                                </td><@checkStatus humidityControllerState.cycleFanBreakDown/>
                            <tr>
                            <tr>
                                <td>${locale.getStr("blueplanet.device.noEnvironmentalSensors")}：
                                </td><@checkStatus humidityControllerState.withoutSensor/>
                                <td>${locale.getStr("blueplanet.device.PTDTemperatureHigh")}：
                                </td><@checkStatus humidityControllerState.ptdTemperatureHigh/>
                            <tr>
                            <tr>
                                <td>${locale.getStr("blueplanet.device.PTDTemperatureLow")}：
                                </td><@checkStatus humidityControllerState.ptdTemperatureLow/>
                                <td/>
                                <td/>
                            <tr>
                        </table>
                    <#elseif humidityType==2>
                        <table class="table table-striped">
                            <tr>
                                <td>${locale.getStr("blueplanet.device.dry")}：
                                </td><@checkStatus humidityControllerState.waterLow/>
                            </tr>
                            <tr>
                                <td>${locale.getStr("blueplanet.device.humidityAlarm")}：
                                </td><@checkStatus humidityControllerState.humidityAlarm/>
                            </tr>
                            <tr>
                                <td>${locale.getStr("blueplanet.device.temperatureAlarm")}：
                                </td><@checkStatus humidityControllerState.temperatureAlarm/>
                            </tr>
                        </table>
                    </#if>
                </#if>
                <h5 class="f-n">${locale.getStr("blueplanet.device.hangWetDate")}</h5>

                <div class="well well-big">
                    <span>${locale.getStr("blueplanet.device.currentTemperature")}：</span> <span id="temperature"
                                                                                                 style="margin-left: 10px">${temperature?string("0.0")}</span>
                    ℃</p>
                    <span>${locale.getStr("blueplanet.device.currentHumidity")}：</span> <span id="humidity"
                                                                                              style="margin-left: 10px">${humidity?string("0.0")}</span>
                    %</p>
                    <span>${locale.getStr("blueplanet.device.dataTime")}：</span> <span id="createTime"
                                                                                       style="margin-left: 10px">${createTime?string("yyyy-MM-dd HH:mm:ss")}</span></p>
                </div>

                <#if humidityType==1>
                    <h5 class="f-n">${locale.getStr("blueplanet.device.hangwetSettings")}</h5>

                    <div class="well well-big">
                        <div class="input-group">
                        ${locale.getStr("blueplanet.device.targetHumidity")}
                            <div class="input-append">
                                <input id="targetHumidity" value="${targetHumidity?string("0.#")}" name="targetHumidity"
                                       class="span3"
                                       style="margin-left: 10px"
                                       type="text"/>

                                <span class="add-on">%</span>
                            </div>
                        </div>
                        <div class="input-group <#if highHumidity==6553.5>hide</#if>">
                        ${locale.getStr("blueplanet.device.humidityUpper")}
                            <div class="input-append">
                                <input id="highHumidity" value="${highHumidity?string("0.#")}" name="highHumidity"
                                       class="span3"
                                       style="margin-left: 10px"
                                       type="text"/>

                                <span class="add-on">%</span>
                            </div>
                        </div>
                        <div class="input-group <#if lowHumidity==6553.5>hide</#if>">
                        ${locale.getStr("blueplanet.device.humidityLower")}
                            <div class="input-append">
                                <input id="lowHumidity" value="${lowHumidity?string("0.#")}" name="lowHumidity"
                                       class="span3"
                                       style="margin-left: 10px"
                                       type="text"/>

                                <span class="add-on">%</span>
                            </div>
                        </div>

                        <button id="setHumidity" class="btn btn-small" style="margin-left: 100px"
                                type="button">${locale.getStr("blueplanet.controlPanel.setting")}
                        </button>
                    </div>
                </#if>
            </#if>

            <#if isAirConditionerController >
                <input type="hidden" id="isAirConditionerController" value="true"/>

                <#if airConditionerController.type==1>
                    <h5 class="f-n">空调设置</h5>

                    <div class="well well-small">
                        <div class="input-group">
                        ${locale.getStr("blueplanet.device.targetHumidity")}
                            <div class="input-append">
                                <input id="airConditionerHumidity" value="${airConditionerController.targetHumidity?string("0.#")}"
                                        class="span3" style="margin-left: 10px" type="text"/>
                                <span class="add-on">%</span>
                            </div>
                            <button id="setAirConditionerHumidity" class="btn btn-small" style="margin-left: 100px"
                                    type="button">${locale.getStr("blueplanet.controlPanel.setting")}
                            </button>
                        </div>


                    </div>
                    <div class="well well-small">
                        <div class="input-group">
                        ${locale.getStr("blueplanet.device.targetTemperature")}
                            <div class="input-append">
                                <input id="targetTemperature" value="${airConditionerController.targetTemperature?string("0.#")}"
                                       class="span3" style="margin-left: 10px" type="text"/>
                                <span class="add-on">℃</span>
                            </div>
                            <button id="setTargetTemperature" class="btn btn-small" style="margin-left: 100px"
                                    type="button">${locale.getStr("blueplanet.controlPanel.setting")}
                            </button>
                        </div>



                    </div>
                    <div class="well well-small">
                    ${locale.getStr("blueplanet.device.switchState")}&nbsp;&nbsp;&nbsp;&nbsp;
                                <input class="switch-mini" type="checkbox" id="switchState"
                                       <#if airConditionerController.switchState == 1>checked="checked"</#if>
                                       data-device-id="${deviceId}"
                                       data-on-label=${locale.getStr("blueplanet.offline.open")}
                                               data-off-label=${locale.getStr("blueplanet.offline.close")}
                                       data-route="${device.humCompensate}"/>
                    </div>

                </#if>
            </#if>

        </div>
    </div>


    <div class="hide">
        <div id="zoneTreeDialog" class="span4">
            <div id="zoneTree" class="ztree"></div>
            <p class="help-block m-t-10 red"></p>
        </div>
    </div>

</#macro>

<#macro checkStatus isTrue>
    <td>
        <#if isTrue>
            <span class="label label-important">${locale.getStr("common.yes")}</span>
        <#else>
            <span class="label label-success">${locale.getStr("common.no")}</span>
        </#if>
    </td>
</#macro>

<#macro script>
    <script type="text/javascript" src="../assets/bootstrap-switch/2.0.1/js/bootstrap-switch.min.js"></script>
    <script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
    <script type="text/javascript" src="../assets/moment/2.0.0/moment.min.js"></script>
    <@scriptTag "js/pnotify.js"/>

    <script type="text/javascript">
        // 在设备树中选中当前设备
        var BluePlanet = App("blueplanet");
        BluePlanet.deviceTree.selectDevice("${deviceId}");
    </script>

    <script type="text/javascript">
        $(function () {

            $("[data-toggle='tooltip']").tooltip();

            $('input[data-type="switch-button"]').bootstrapSwitch()
                    .bootstrapSwitch('setAnimated', false)
                    .bootstrapSwitch('setOnClass', 'success')
                    .on('switch-change', function (e) {
                        var humCompensate = 0;
                        if ($(this).attr("checked") == "checked") {
                            humCompensate = 1;
                        }
                        var deviceId = $(this).data('device-id');
                        var $this = $(this);
                        $.post("device/" + deviceId + "/setHumCompensate/" + humCompensate, function (result) {
                            if (result != null && result.success) {
                                window.pnotify(message.executeSuccess, "info");
                            } else {
                                window.pnotify(message.executionFailed, "warn");
                                $this.bootstrapSwitch('toggleState', true);
                            }
                        });

                    });

            $("#demarcate").bootstrapSwitch()
                    .bootstrapSwitch('setAnimated', false)
                    .bootstrapSwitch('setOnClass', 'success')
                    .on('switch-change', function (e) {
                        var demarcate = 0;
                        if ($(this).attr("checked") == "checked") {
                            demarcate = 1;
                        }
                        var deviceId = $(this).data('device-id');
                        var $this = $(this);
                        $.post("device/" + deviceId + "/demarcate?demarcate=" + demarcate, function (result) {
                            if (result != null && result.success) {
                                window.pnotify(message.executeSuccess, "info");
                            } else {
                                window.pnotify(message.executionFailed, "warn");
                                $this.bootstrapSwitch('toggleState', true);
                            }
                        });

                    });

            $("#switchScreen").bootstrapSwitch()
                    .bootstrapSwitch('setAnimated', false)
                    .bootstrapSwitch('setOnClass', 'success')
                    .on('switch-change', function (e) {
                        var screenState = 0;
                        if ($(this).attr("checked") == "checked") {
                            screenState = 1;
                        }
                        var deviceId = $(this).data('device-id');
                        var $this = $(this);
                        $.post("device/" + deviceId + "/switchScreen?screenState=" + screenState, function (result) {
                            if (result != null && result.success) {
                                window.pnotify(message.executeSuccess, "info");
                            } else {
                                window.pnotify(message.executionFailed, "warn");
                                $this.bootstrapSwitch('toggleState', true);
                            }
                        });

                    });
            //设置空调开关状态
            $("#switchState").bootstrapSwitch()
                    .bootstrapSwitch('setAnimated', false)
                    .bootstrapSwitch('setOnClass', 'success')
                    .on('switch-change', function (e) {
                        var switchState = 0;
                        if ($(this).attr("checked") == "checked") {
                            switchState = 1;
                        }
                        var deviceId = $(this).data('device-id');
                        var $this = $(this);
                        $.post("devices/" + deviceId + "/setAirConditionerSwitchState?switchState=" + switchState, function (result) {
                            if (result != null && result.success) {
                                window.pnotify(message.executeSuccess, "info");
                            } else {
                                window.pnotify(message.executionFailed, "warn");
                                $this.bootstrapSwitch('toggleState', true);
                            }
                        });

                    });
            //设置空调目标湿度
            $("#setAirConditionerHumidity").click(function () {
                var $targetHumidity = $("#airConditionerHumidity").val().trim();
                var url = "/galaxy/blueplanet/devices/${deviceId}/setAirConditionerHumidity";
                if ($targetHumidity == "") {
                    window.pnotify(message.humidityEmpty, "warn");
                    return;
                }
                var data = {
                    "targetHumidity": $targetHumidity
                };
                $.get(url, data, function (result) {
                    if (result.success) {
                        window.pnotify(message.executeSuccess, "success");
                    } else {
                        window.pnotify(message.executionFailed, "warn");
                    }
                })
            });

            //设置空调目标温度
            $("#setTargetTemperature").click(function () {
                var $targetTemperature = $("#targetTemperature").val().trim();
                var url = "/galaxy/blueplanet/devices/${deviceId}/setAirConditionerTemperature";
                if ($targetTemperature == "") {
                    window.pnotify(message.temperatureEmpty, "warn");
                    return;
                }
                var data = {
                    "targetTemperature": $targetTemperature
                };
                $.get(url, data, function (result) {
                    if (result.success) {
                        window.pnotify(message.executeSuccess, "success");
                    } else {
                        window.pnotify(message.executionFailed, "warn");
                    }
                })
            });

            function refreshHumidityController() {
                $.getJSON("/galaxy/blueplanet/device/${deviceId}/showHumidityController.json", function (data) {
                    if (data) {
                        $("#targetHumidity").val(data["targetHumidity"] * 0.1);
                        $("#highHumidity").val(data["highHumidity"] * 0.1);
                        $("#lowHumidity").val(data["lowHumidity"] * 0.1);
                        $("#humidity").text(data["humidity"]);
                        $("#temperature").text(data["temperature"]);
                        $("#createTime").text(moment(data["createTime"]).format("YYYY-MM-DD HH:mm:ss"));
                    }
                });
            }

            var $isHumidityController = $("#isHumidityController").val();
            if ($isHumidityController == "true") {
                setInterval(refreshHumidityController, 30000);
            }

            $("#setHumidity").click(function () {
                var $targetHumidity = $("#targetHumidity").val().trim();
                var $highHumidity = $("#highHumidity").val().trim();
                var $lowHumidity = $("#lowHumidity").val().trim();
                var url = "/galaxy/blueplanet/device/${deviceId}/setHumidityController";
                if ($targetHumidity == "") {
                    window.pnotify(message.humidityEmpty, "warn");
                    return;
                }else if($targetHumidity<35 || $targetHumidity>65) {
                    window.pnotify("目标湿度必须在35-65", "warn");
                    return;
                }
                var data = {
                    "targetHumidity": $targetHumidity,
                    "highHumidity": $highHumidity, "lowHumidity": $lowHumidity
                };
                $.get(url, data, function (result) {
                    if (result.success) {
                        window.pnotify(message.setSuccess, "success");
                    } else {
                        window.pnotify(message.setFailed, "warn");
                    }
                })
            });

            $(".setSensorThreshold").click(function () {
                var $this = $(this);
                var deviceId = $this.attr("name");
                var sensorId = $this.data("sensorid");
                var max = $("#max" + deviceId + sensorId).val();
                var min = $("#min" + deviceId + sensorId).val();
                var flag = check(deviceId, sensorId, max, min);
                if (flag) {
                    var url = "/galaxy/blueplanet/device/setSensorThreshold/" + deviceId;
                    var data = {"maxValue": max, "minValue": min, "sensorId": sensorId};
                    $.get(url, data, function (result) {
                        if (result.success) {
                            window.pnotify(message.setThresholdSuccess, "success");
                        } else {
                            window.pnotify(message.setThresholdFailed, "warn")
                        }
                    })
                }

            });

            $("#locate").click(function () {
                var url = "/galaxy/blueplanet/device/${deviceId}/locate";
                $.get(url, function (result) {
                    if (result.success && result.doSuccess && result.sendSuccess) {
                        window.pnotify(message.locationSuccess, "success");
                    } else {
                        window.pnotify(message.locationFailed, "warn")
                    }
                })
            });
            //设备重启
            $("#reboot").click(function () {
                var url = "/galaxy/blueplanet/device/${deviceId}/reboot";
                $.get(url, function (result) {
                    if (result.success && result.doSuccess && result.sendSuccess) {
                        window.pnotify(message.rebootSuccess, "success");
                    } else {
                        window.pnotify(message.rebootFailed, "warn")
                    }
                })
            });

            var check = function (deviceId, sensorId, max, min) {
                if (max == "" || isNaN(max)) {
                    $("#maxSpan" + deviceId + sensorId).text(message.inputNumber);
                    $("#max" + deviceId + sensorId).focus(function () {
                        $("#maxSpan" + deviceId + sensorId).text("");
                    });
                    return false;
                }

                if (min == "" || isNaN(min)) {
                    $("#minSpan" + deviceId + sensorId).text(message.inputNumber);
                    $("#min" + deviceId + sensorId).focus(function () {
                        $("#minSpan" + deviceId + sensorId).text("");
                    });
                    return false;
                }

                if (min >= max) {
                    $("#minSpan" + deviceId + sensorId).text(message.lowerLimitGreaterThanUpperLimit);
                    $("#min" + deviceId + sensorId).focus(function () {
                        $("#minSpan" + deviceId + sensorId).text("");
                    });
                    return false;
                }
                return true;
            }
        })
    </script>

    <@scriptTag "js/device/device-detail-edit.js"/>
</#macro>