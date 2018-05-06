<#--<#setting number_format="#.00000">-->
<#assign title=locale.getStr("blueplanet.controlPanel.controlActionTitle")>
<#include  "../_common/header/helper.ftl">
<#macro head>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<style type="text/css">
    /* 条件反射 */
    #configForm label.control-label {
        width: 60px;
    }

    #configForm .controls {
        margin-left: 80px;
    }

    /* 动作 */
    #actionForm label.control-label {
        width: 100px;
    }

    #actionForm .controls {
        margin-left: 110px;
    }

    /* 计算原始值 */
    #originValueForm label.control-label {
        width: 100px;
    }

    #originValueForm .controls {
        margin-left: 110px;
    }

    table .close1,
    table .close2 {
        background-color: #aaa;
        float: left;
        text-align: center;
        border: thin solid #d3d3d3;
    }

    table .open1,
    table .open2 {
        background-color: #5bb75b;
        float: left;
        text-align: center;
        border: thin solid #d3d3d3;
    }

    table .close1,
    table .open1 {
        width: 30%;
    }

    table .close2,
    table .open2 {
        width: 45%;
    }

    .text {
        color: #ffffff;
    }

    .actions {
        border: 1px solid #808080;
        border-radius: 5px;
    }

    #actionUl .noDecora {
        text-decoration: none
    }
</style>
</#macro>
<#include "/common/pages/common-tag.ftl">
<#include "../device/device-helper.ftl">
<#macro content>

<div class="row-fluid">

    <div class="span12">
    <h3><a class="f-n" href="control-panel">${locale.getStr("blueplanet.controlPanel")}</a> /
        <@typeIconOfDevice device.nodeType/> ${deviceId?substring(8)}
        <#if device.nodeName??>
            <span class="muted f-n" style="font-size: 0.7em;">${device.nodeName}</span></h3>
        </#if>
    </div>

</div>

<ul class="nav nav-tabs">
    <li>
        <a href="control-panel/${device.nodeId}">${locale.getStr("blueplanet.controlPanel.basic")}</a>

    </li>
    <li class="active"><a
            href="control-panel/${device.nodeId}/actions">${locale.getStr("blueplanet.controlPanel.action")}</a></li>
    <li><a href="control-panel/${device.nodeId}/activities">${locale.getStr("blueplanet.controlPanel.activity")}</a>
    </li>
</ul>

<div class="row-fluid">
<div class="span12">
<h4 class="pull-left">${locale.getStr("blueplanet.controlPanel.conditionedReflex")}</h4>
<a id="originBtn" class="btn btn-small pull-right"><i
        class="icon-wrench">${locale.getStr("blueplanet.controlPanel.calculateRawValue")}</i></a>
<table class="table table-bordered">
    <thead>
    <tr>
        <th>${locale.getStr("blueplanet.controlPanel.port")}</th>
        <th>${locale.getStr("blueplanet.controlPanel.device")}</th>
        <th>${locale.getStr("blueplanet.controlPanel.monitoringIndicators")}</th>
        <th>${locale.getStr("blueplanet.controlPanel.triggerAction")}</th>
        <#if security.isPermitted("blueplanet:monitor:controlPanel:edit")>
            <th>${locale.getStr("common.operating")}</th>
        </#if>
    </tr>
    </thead>
    <tbody>

        <#list switches.values as switch>
            <#if switch.enable>
                <#if conditionReflOfSwitch(conditionRefls, switch.route)??>
                    <#assign conditionRefl = conditionReflOfSwitch(conditionRefls, switch.route)>

                    <#if conditionRefl.action == 8>
                    <tr>
                        <td><strong>#${switch.route}</strong></td>
                        <td colspan="3">${locale.getStr("blueplanet.controlPanel.unconditionalReflex")}</td>
                        <#if security.isPermitted("blueplanet:monitor:controlPanel:edit")>
                            <td>
                                <a name="config" class="btn btn-mini" data-route="${switch.route}"><i
                                        class="icon-cog"></i>${locale.getStr("blueplanet.controlPanel.setting")}</a>
                            </td>
                        </#if>

                    </tr>

                    <#else>

                    <tr>
                        <td><strong>#${conditionRefl.route}</strong></td>
                        <td>${statics["java.lang.String"].format("%05d", conditionRefl.subTerminalId)}</td>
                        <td><#if conditionRefl.sensorinfoVO??>${conditionRefl.sensorinfoVO.cnName}</#if></td>
                        <td class="text">
                            <#if conditionRefl.action==2||conditionRefl.action==5>
                                <div <#if conditionRefl.action==2>class="close1"<#else>class="open1"</#if>>
                                                    <span class="f-l">&nbsp;<#if conditionRefl.action==2>
                                                    ${locale.getStr("common.close")}<#else>
                                                    ${locale.getStr("common.open")}</#if></span></div>
                                <div <#if conditionRefl.action==2>class="open1"<#else>class="close1"</#if>>
                                                    <span class="f-l">
                                                    ${conditionRefl.lowTarget}<#if conditionRefl.sensorinfoVO??>${conditionRefl.sensorinfoVO.units}</#if></span><span
                                        class="m-l-10"><#if conditionRefl.action==2>
                                    ${locale.getStr("common.open")}<#else>
                                ${locale.getStr("common.close")}</#if></span><span class="f-r">
                                                ${conditionRefl.highTarget}<#if conditionRefl.sensorinfoVO??>${conditionRefl.sensorinfoVO.units}</#if></span>
                                </div>
                                <div <#if conditionRefl.action==2>class="close1"<#else>class="open1"</#if>>
                                                    <span class="f-r m-l-10"><#if conditionRefl.action==2>${locale.getStr("common.close")}<#else>
                                                    ${locale.getStr("common.open")}</#if>&nbsp;</span></div>

                            <#elseif  conditionRefl.action==3||conditionRefl.action==4>
                                <div <#if conditionRefl.action==3>class="close2"<#else>class="open2"</#if>>
                                                    <span class="f-l">&nbsp;<#if conditionRefl.action==3>
                                                    ${locale.getStr("common.close")}<#else>
                                                    ${locale.getStr("common.open")}</#if></span><#if conditionRefl.action==4>
                                    <span class="f-r">
                                                    ${conditionRefl.lowTarget}<#if conditionRefl.sensorinfoVO??>${conditionRefl.sensorinfoVO.units}</#if></span></#if></span>
                                </div>
                                <div <#if conditionRefl.action==3>class="open2"<#else>class="close2"</#if>>
                                    <#if conditionRefl.action==3> <span class="f-l">
                                                    ${conditionRefl.lowTarget}<#if conditionRefl.sensorinfoVO??>${conditionRefl.sensorinfoVO.units}</#if></span> </#if>
                                    <span class="f-r"><#if conditionRefl.action==3>${locale.getStr("common.open")}<#else>
                                    ${locale.getStr("common.close")}</#if>&nbsp;</span>
                                </div>
                            <#else>
                                <span style="color: #000000">
                                ${ descOfCondtionReflAction(conditionRefl.action)!"${locale.getStr("blueplanet.controlPanel.invalidValue")}"}
                                </span>
                            </#if>
                        </td>
                        <#if security.isPermitted("blueplanet:monitor:controlPanel:edit")>
                            <td>
                                <a name="config" class="btn btn-mini" data-route="${switch.route}"><i
                                        class="icon-cog"></i>${locale.getStr("blueplanet.controlPanel.setting")}</a>

                                <a href="control-panel/${deviceId}/logic-refl?route=${switch.route}&subNodeId=${0}&sensorId=${0}&switchAction=${8}"
                                   class="btn btn-mini"
                                   onclick="javascript:return confirm('${locale.getStr("blueplanet.controlPanel.sureTips")}')"><i
                                        class="icon-trash"></i>${locale.getStr("blueplanet.controlPanel.cancel")}</a>
                            </td>
                        </#if>
                    </tr>
                    </#if>

                <#else>

                <tr>
                    <td><strong>#${switch.route}</strong></td>
                    <td colspan="3">${locale.getStr("blueplanet.controlPanel.unconditionalReflex")}</td>
                    <#if security.isPermitted("blueplanet:monitor:controlPanel:edit")>
                        <td>
                            <a name="config" class="btn btn-mini" data-route="${switch.route}"><i
                                    class="icon-cog"></i>${locale.getStr("blueplanet.controlPanel.setting")}</a>
                        </td>
                    </#if>
                </tr>
                </#if>
            </#if>
        </#list>
    </tbody>
</table>
<p style="color: #969696;">${locale.getStr("blueplanet.controlPanel.tips")}</p>

<h4>${locale.getStr("blueplanet.controlPanel.action")}</h4>

    <#if switchActionMap??>
    <ul id="actionUl">
        <#list switches.values as switch>
            <#if switch.enable>
                <li class="m-t-10"><span
                        class="label label-info">${switch.route}${locale.getStr("blueplanet.controlPanel.port")}</span>
                    <#if conditionActionVOMap.get(switch.route)??>
                        <#assign conditionActionVO=conditionActionVOMap.get(switch.route)>
                        <#if conditionActionVO??>
                            <#list conditionActionVO.conditionActions as condition>
                                <a class="btn btn-small btn-link actions noDecora">
                                ${locale.getStr("blueplanet.controlPanel.device")}<#if condition.deviceVO.nodeName??>
                                ${condition.deviceVO.nodeName}
                                <#else>
                                ${condition.deviceVO.nodeId?substring(8)}
                                </#if>
                                ${locale.getStr("blueplanet.controlPanel.monitoringIndicators")} ${condition.sensorinfoVO.cnName}
                                    <#if condition.operator==1>${locale.getStr("blueplanet.controlPanel.moreThanThe")}</#if>
                                    <#if condition.operator==2>${locale.getStr("blueplanet.controlPanel.lessThan")}</#if>
                                    <#if condition.operator==3>${locale.getStr("blueplanet.controlPanel.equal")}</#if>
                                ${condition.conditionValue} ${condition.sensorinfoVO.units}
                                    <i data-action-id="${condition.id}" data-action-type="0"
                                       class="icon-remove"></i>
                                </a>
                                <#assign conditionSize=conditionActionVO.conditionActions?size>
                                <#if conditionSize gt 1&&condition_index+1!=conditionSize>
                                    <span>  <#if conditionActionVO.logic==1>${locale.getStr("blueplanet.controlPanel.and")}</#if>
                                        <#if conditionActionVO.logic==0>${locale.getStr("blueplanet.controlPanel.or")}</#if> </span>
                                </#if>
                                <#if condition_index+1==conditionSize>
                                    <span><#if conditionActionVO.action==1>
                                    ${locale.getStr("common.open")}
                                    <#else>
                                    ${locale.getStr("common.close")}
                                    </#if>
                                    </span>
                                </#if>
                            </#list>
                        </#if>
                    </#if>
                    <#if switchActionMap.get(switch.route)??>
                        <#assign switchActionVO=switchActionMap.get(switch.route)>
                        <#list switchActionVO.switchDailyActions as switchDailyAction>
                            <a class="btn btn-small btn-link actions noDecora">${locale.getStr("blueplanet.controlPanel.atIntervalsOf")} ${switchDailyAction.time?string('HH:mm')}
                                <#if switchDailyAction.action==1>${locale.getStr("common.open")}</#if>
                                <#if switchDailyAction.action==0>${locale.getStr("common.close")}</#if>
                                <#if security.isPermitted("blueplanet:monitor:controlPanel:edit")>
                                    <i data-action-id="${switchDailyAction.id}" data-action-type="1"
                                       class="icon-remove"></i>
                                </#if>
                            </a>
                        </#list>

                        <#if switchActionVO.switchIntervalAction??>
                            <#assign switchInterval=switchActionVO.switchIntervalAction>
                            <a class="btn btn-small btn-link actions noDecora"> ${locale.getStr("blueplanet.controlPanel.everyDay")}
                                <#assign minute=switchInterval.intervalTime/60>
                                <#assign second=switchInterval.intervalTime%60>
                                <#if switchInterval.intervalTime<60>
                                ${switchInterval.intervalTime}${locale.getStr("blueplanet.controlPanel.second")}
                                <#elseif second==0>
                                ${minute}${locale.getStr("blueplanet.controlPanel.minute")}
                                <#else>
                                ${minute?int}${locale.getStr("blueplanet.controlPanel.minute")}  ${second}${locale.getStr("blueplanet.controlPanel.second")}
                                </#if>

                                <#if switchInterval.action==1>${locale.getStr("common.open")}</#if>
                                <#if switchInterval.action==0>${locale.getStr("common.close")}</#if>
                                ， ${locale.getStr("blueplanet.controlPanel.continued")}
                                <#assign minute1=switchInterval.executionTime/60>
                                <#assign second1=switchInterval.executionTime%60>
                                <#if switchInterval.executionTime<60>
                                ${switchInterval.executionTime}${locale.getStr("blueplanet.controlPanel.second")}
                                <#elseif second1==0>
                                ${minute1}${locale.getStr("blueplanet.controlPanel.minute")}
                                <#else>
                                ${minute1?int}${locale.getStr("blueplanet.controlPanel.minute")}  ${second1}${locale.getStr("blueplanet.controlPanel.second")}
                                </#if>
                                <#if security.isPermitted("blueplanet:monitor:controlPanel:edit")>
                                    <i data-action-id="${switchInterval.id}" data-action-type="2"
                                       class="icon-remove"></i>
                                </#if>
                            </a>
                        </#if>
                    </#if>

                    <#if security.isPermitted("blueplanet:monitor:controlPanel:edit")>
                        <#assign conditionExist=conditionActionVOMap.get(switch.route)??/>
                        <#assign switchActionExist=switchActionMap.get(switch.route)??/>
                        <a class="btn btn-small <#if conditionExist>
                        hide
                        <#elseif switchActionExist>
                        <#if switchActionMap.get(switch.route).switchIntervalAction??>
                        hide</#if>
                         </#if>"
                           name="addAction" data-route=${switch.route}><i
                                class="icon-plus"></i>${locale.getStr("blueplanet.controlPanel.time")}</a>

                        <a class="btn btn-small <#if switchActionExist>hide</#if>"
                           name="addCondition"
                                data-route=${switch.route} <#if conditionExist>data-logic=${switch.route}</#if>><i
                                class="icon-plus"></i>${locale.getStr("blueplanet.controlPanel.condition")}</a>
                        <a class="btn btn-small <#if (!switchActionExist&&!conditionExist)||switchActionExist>hide</#if>"
                           name="editCondition"
                           data-route=${switch.route}><i
                                class="icon-pencil"></i>${locale.getStr("common.save")}</a>
                    </#if>
                </li>
            </#if>
        </#list>
    </ul>
    </#if>


</div>
</div>

<div id="configFl" class="hide">
    <form id="configForm" class="form-horizontal" action="control-panel/${deviceId}/logic-refl" method="post">
        <input type="hidden" id="deviceId" name="deviceId" value="${deviceId}"/>
        <input type="hidden" id="configRoute" name="route"/>

        <p id="errorHelper" class="help-block m-l-80 red"></p>

        <div class="control-group hide" id="subNodeDiv" name="subNodeDiv">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.childNode")}</label>

            <div class="controls">
                <select name="subNodeId" id="subNodeId">
                    <option value="-1">==${locale.getStr("blueplanet.controlPanel.pleaseChoose")}==</option>
                    <#list deviceVOs as device>
                        <option value="${device.nodeId}">
                            <#if device.nodeName??>
                            ${device.nodeName}
                            <#else>
                            ${device.nodeId?substring(8)}
                            </#if>
                        </option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="control-group hide" id="sensorDiv" name="sensorDiv">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.monitoringIndicators")}</label>

            <div class="controls">
                <select name="sensorId" id="sensorId" class="sensor">
                    <option>==${locale.getStr("blueplanet.controlPanel.pleaseChoose")}==</option>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.triggerAction")}</label>

            <div class="controls">
                <select name="switchAction" id="switchAction">
                    <option value="8">${locale.getStr("blueplanet.controlPanel.unconditionalReflex")}</option>
                    <option value="0">${locale.getStr("blueplanet.controlPanel.unconditionalOff")}</option>
                    <option value="7">${locale.getStr("blueplanet.controlPanel.unconditionallyOpen")}</option>
                    <option value="2">${locale.getStr("blueplanet.controlPanel.withinCustoms")}</option>
                    <option value="5">${locale.getStr("blueplanet.controlPanel.withinOpen")}</option>
                    <option value="3">${locale.getStr("blueplanet.controlPanel.openOff")}</option>
                    <option value="4">${locale.getStr("blueplanet.controlPanel.offOPen")}</option>
                </select>
            </div>
        </div>
        <div class="control-group hide" id="threshold">
            <label class="control-label">${locale.getStr("blueplanet.alarm.threshold")}</label>

            <div class="controls">
                <input type="text" name="threshold" value="0" required/>
            </div>
        </div>

        <div class="control-group hide" id="lowThreshold">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.lowThreshold")}</label>

            <div class="controls">
                <input type="text" name="low" id="low"/>
            </div>
        </div>
        <div class="control-group hide" id="highThreshold">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.highThreshold")}</label>

            <div class="controls">
                <input type="text" name="high" id="high"/>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button class="btn btn-primary" type="submit" id="btnSubmit">${locale.getStr("common.save")}</button>
                <a class="btn" id="btnCancel">${locale.getStr("blueplanet.controlPanel.cancel")}</a>
            </div>
        </div>
    </form>
</div>
<div id="addActionDialog" class="hide">
    <form id="actionForm" class="form-horizontal" action="control-panel/${deviceId}/addActions" method="post">
        <input name="actionRoute" type="hidden" id="actionRoute"/>

        <div class="control-group">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.actionType")}</label>

            <div class="controls">
                <select name="type" id="type">
                    <option value="1">${locale.getStr("blueplanet.controlPanel.dailyScheduled")}</option>
                    <option value="2">${locale.getStr("blueplanet.controlPanel.intervals")}</option>
                </select>
            </div>
        </div>
        <div class="control-group" id="daily">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.dailyTiming")}</label>

            <div class="controls">
                <input id="daily" type="text"
                       onclick="WdatePicker({dateFmt:'HH:mm:ss'})"
                       name="daily"
                       required/>
            </div>
        </div>
        <div class="control-group hide" id="interval">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.intervals")} (${locale.getStr("blueplanet.controlPanel.second")})</label>

            <div class="controls">
                <input id="intervalTime" name="intervalTime" type="text" required/>
            </div>
        </div>
        <div class="control-group hide" id="execute">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.executionTime")} (${locale.getStr("blueplanet.controlPanel.second")})</label>

            <div class="controls">
                <input id="executionTime" name="executionTime" type="text" required/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.executiveAction")}</label>

            <div class="controls">
                <select name="action" id="action">
                    <option value="1">${locale.getStr("common.open")}</option>
                    <option value="0">${locale.getStr("common.close")}</option>
                </select>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <button class="btn btn-primary" type="submit" id="actionSubmit">${locale.getStr("common.save")}</button>
                <a class="btn" id="actionCancel">${locale.getStr("blueplanet.controlPanel.cancel")}</a>
            </div>
        </div>
    </form>
</div>
<div id="addConditionDialog" class="hide">
    <form id="conditionForm" class="form-horizontal" action="control-panel/${deviceId}/addCondition" method="post">
        <input name="actionRoute" type="hidden" id="conditionRoute"/>

        <div class="control-group" name="subNodeDiv">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.device")}</label>

            <div class="controls">
                <select name="subNodeId">
                    <option value="-1">==${locale.getStr("blueplanet.controlPanel.pleaseChoose")}==</option>
                <#--所有子节点-->
                    <#list allDevices as device>
                        <option value="${device.nodeId}">
                            <#if device.nodeName??>
                            ${device.nodeName}
                            <#else>
                            ${device.nodeId?substring(8)}
                            </#if>
                        </option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="control-group" name="sensorDiv">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.monitoringIndicators")}</label>

            <div class="controls">
                <select name="sensorId" class="sensor">
                    <option>==${locale.getStr("blueplanet.controlPanel.pleaseChoose")}==</option>
                </select>
            </div>
        </div>

        <div class="control-group" name="conditions">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.condition")}</label>

            <div class="controls">
                <select name="operator">
                    <option value="1"> ${locale.getStr("blueplanet.controlPanel.moreThanThe")}</option>
                    <option value="2"> ${locale.getStr("blueplanet.controlPanel.lessThan")}</option>
                    <option value="3"> ${locale.getStr("blueplanet.controlPanel.equal")}</option>
                </select>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.value")}</label>

            <div class="controls">
                <input type="text" name="conditionValue" id="conditionValue"/>
            </div>
        </div>

        <div class="control-group" id="conditionAction">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.executiveAction")}</label>

            <div class="controls">
                <select name="action" id="action">
                    <option value="1">${locale.getStr("common.open")}</option>
                    <option value="0">${locale.getStr("common.close")}</option>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.relationship")}</label>

            <div class="controls">
                <select name="logic" id="logic">
                    <option value="1">${locale.getStr("blueplanet.controlPanel.and")}</option>
                    <option value="0">${locale.getStr("blueplanet.controlPanel.or")}</option>
                </select>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <button class="btn btn-primary" type="submit" id="conditionSubmit">${locale.getStr("common.save")}</button>
                <a class="btn" id="conditionCancel">${locale.getStr("blueplanet.controlPanel.cancel")}</a>
            </div>
        </div>
    </form>
</div>
<div id="editConditionDialog" class="hide">
    <form id="editConditionForm" class="form-horizontal" action="control-panel/${deviceId}/editConditions"
          method="post">
        <input name="actionRoute" type="hidden" id="editRoute"/>

        <div class="control-group" id="editCondition">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.executiveAction")}</label>

            <div class="controls">
                <select name="action">
                    <option value="1">${locale.getStr("common.open")}</option>
                    <option value="0">${locale.getStr("common.close")}</option>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.relationship")}</label>

            <div class="controls">
                <select name="logic">
                    <option value="1">${locale.getStr("blueplanet.controlPanel.and")}</option>
                    <option value="0">${locale.getStr("blueplanet.controlPanel.or")}</option>
                </select>
            </div>
        </div>

        <div class="control-group">
            <div class="controls">
                <button class="btn btn-primary" type="submit" id="editSubmit">${locale.getStr("common.save")}</button>
                <a class="btn" id="editCancel">${locale.getStr("blueplanet.controlPanel.cancel")}</a>
            </div>
        </div>
    </form>
</div>
<div id="originValueDialog" class="hide">
    <form id="originValueForm" class="form-horizontal" action="control-panel/${deviceId}/originValue" method="post">
        <div class="control-group" name="subNodeDiv">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.device")}</label>

            <div class="controls">
                <select name="subNodeId" id="originDevice">
                    <option value="-2">${locale.getStr("blueplanet.controlPanel.defaultNo")}</option>
                <#--所有节点-->
                    <#list allDevices as device>
                        <option value="${device.nodeId}">
                            <#if device.nodeName??>
                            ${device.nodeName}
                            <#else>
                            ${device.nodeId?substring(8)}
                            </#if>
                        </option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="control-group" name="sensorDiv">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.monitoringIndicators")}</label>

            <div class="controls">
                <select name="sensorId" class="sensor" id="originSensor">
                ${locale.getStr("blueplanet.controlPanel.noIndex")}
                    <#list allSensorinfoVOs as sensor>
                        <option value="${sensor.sensorPhysicalid}">
                        ${sensor.cnName}
                        </option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.value")}</label>

            <div class="controls">
                <input type="text" name="originValue" id="originValue"/>
            </div>
        </div>

        <div class="control-group hide" id="originLeftDiv">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.Lvalue")}</label>

            <div class="controls">
                <input type="text" disabled="disabled" name="originLeft" id="originLeft"/>
            </div>
        </div>
        <div class="control-group hide" id="originDiv">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.originalValue")}</label>

            <div class="controls">
                <input type="text" disabled="disabled" name="origin" id="origin"/>
            </div>
        </div>
        <div class="control-group hide" id="originRightDiv">
            <label class="control-label">${locale.getStr("blueplanet.controlPanel.Rvalue")}</label>

            <div class="controls">
                <input type="text" disabled="disabled" name="originRight" id="originRight"/>
            </div>
        </div>
    </form>
</div>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
    <@scriptTag "js/pnotify.js"/>
    <@scriptTag "../assets/artDialog/5.0.1-7012746/artDialog.min.js"/>
<script type="text/javascript" src="../assets/jquery-validation/1.11.1/dist/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.11.1/localization/messages_zh.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/angular/1.0.8/angular.min.js"></script>
<script type="text/javascript" src="../assets/angular/1.0.8/angular-sanitize.min.js"></script>
    <@scriptTag "js/2datepicker-form-validation.js"/>
    <@scriptTag "js/controlpanel/control-module-actions.js"/>
</#macro>

<#-- 返回某一路的条件反射参数, 如果无返回 null -->
<#function conditionReflOfSwitch conditionRefls, route>
    <#list conditionRefls as conditionRefl>
        <#if conditionRefl.route == route><#return conditionRefl></#if>
    </#list>
</#function>

<#-- 返回条件反射运作描述 -->
<#function descOfCondtionReflAction action>
    <#switch action>
        <#case 0><#return locale.getStr("blueplanet.controlPanel.unconditionalOff")>
        <#case 2><#return locale.getStr("blueplanet.controlPanel.withinCustoms")>
        <#case 3><#return locale.getStr("blueplanet.controlPanel.openOff")>
        <#case 4><#return locale.getStr("blueplanet.controlPanel.offOPen")>
        <#case 5><#return locale.getStr("blueplanet.controlPanel.withinOpen")>
        <#case 7><#return locale.getStr("blueplanet.controlPanel.unconditionallyOpen")>
        <#case 8><#return locale.getStr("blueplanet.controlPanel.unconditionalReflex")>
    </#switch>
</#function>
