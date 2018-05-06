<#--
设备查询

@author gaohui
@date 2013-01-24
@check @wang yunlong 2013-02-26 #1776
-->

<#assign title>${locale.getStr("blueplanet.deviceList.title")}</#assign>
<#include "/common/pages/common-tag.ftl">

<#macro head>

<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>

</#macro>


<#include "../device-helper.ftl">
<#macro content>
<div class="row-fluid m-t-10">
    <div class="span12">
        <ul class="nav nav-pills">
            <li class="active"><a href="devices">${locale.getStr("blueplanet.device.deviceList")}</a></li>
            <li><a href="devices/realtime-data">${locale.getStr("blueplanet.common.realtimeData")}</a></li>
        </ul>
    </div>
</div>

<div class="row-fluid m-t-10">
    <div class="span12">
        <form class="form-inline well well-small" action="devices" method="post">

            <label>${locale.getStr("blueplanet.controlPanel.deviceID")}</label>

            <div class="input-prepend">
                <span class="add-on">${siteId}</span>
                <input class="input-small" name="deviceId" type="text" maxlength="5"
                       placeholder="${locale.getStr("blueplanet.device.deviceID")}"
                       value="${deviceId!}">
            </div>

            <label>${locale.getStr("common.deviceType")}</label>
            <select class="input-small" name="deviceType">
                <option value="-1" <@selected -1, deviceType/>>${locale.getStr("common.all")}</option>
                <option value="1" <@selected 1, deviceType/>>${locale.getStr("common.node")}</option>
                <option value="2" <@selected 2, deviceType/>>${locale.getStr("common.relay")}</option>
                <option value="5" <@selected 5, deviceType/>>${locale.getStr("common.controlModule")}</option>
                <option value="3" <@selected 3, deviceType/>>${locale.getStr("blueplanet.location.masterModule")}</option>
                <option value="7" <@selected 7, deviceType/>>${locale.getStr("common.gateway")}</option>
            </select>
            <button class="btn" type="submit">${locale.getStr("common.select")}</button>
            <#if security.isSuperman()>
                <span style="margin-left: 20px;">
                ${locale.getStr("blueplanet.device.wholeNetPatrol")}
                </span>
                <button class="btn btn-small btn-inverse"
                        type="button"
                        id="pollingClose"
                        data-value="pollingClose"
                        data-href="pollingClose.json"
                        data-mess="${locale.getStr("blueplanet.device.closePolling")}"
                        data-title="${locale.getStr("blueplanet.device.tips")}"
                        >${locale.getStr("common.close")}
                </button>
                <button class="btn btn-small btn-success"
                        id="pollingOpen"
                        data-value="pollingOpen"
                        type="button"
                        data-href="pollingOpen.json"
                        data-mess="${locale.getStr("blueplanet.device.openPolling")}"
                        data-title="${locale.getStr("blueplanet.device.tips")}"
                        >${locale.getStr("common.open")}
                </button>
                <input type="hidden" id="poolingFlag" value="">

                <#include "../../_common/patrol_device_warning.ftl">
            </#if>
            <#if security.isPermitted("blueplanet:manage:device:delete")>
                <input id="delete_all" type="button" value="删除" class="btn btn-danger f-r"/>
            </#if>

        </form>
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>

                <th><label class="checkbox"><input type="checkbox">${locale.getStr("common.all")}</label></th>
                <th>${locale.getStr("common.number")}</th>
                <th>${locale.getStr("blueplanet.controlPanel.deviceID")}</th>
                <th>${locale.getStr("common.deviceType")}</th>
                <th>${locale.getStr("blueplanet.common.status")}</th>
                <th>${locale.getStr("blueplanet.device.sn")}</th>
                <th>${locale.getStr("blueplanet.device.workingMode")}</th>
                <th class="f-n"
                    style="font-size: 0.7em; font-weight: normal;">${locale.getStr("blueplanet.device.lqi")}</th>
                <th>${locale.getStr("common.operating")}</th>
            </tr>
            </thead>
            <tbody>

                <#list devices as device>
                <tr id="delete_${device.nodeId}">
                    <td>
                        <input name="nodeId" type="checkbox" value="${device.nodeId}" class="nodeId"/>
                    </td>
                    <td>${device_index + 1}</td>
                    <td
                        <#if device.stamp??>
                                title="${locale.getStr("blueplanet.device.stamp")}：${device.stamp?string("yyyy-MM-dd HH:mm:ss")}"
                        </#if>
                            >
                        <@typeIconOfDevice device.nodeType/>
                        <a href="device/${device.nodeId}/detail"
                           title="${locale.getStr("blueplanet.device.deviceDetail")}"><@strongNodeId device.nodeId,true/></a>
                        <#if device.nodeType == 3 >
                            <a class="muted pull-right" href="#"
                               data-master="${device.nodeId}">${locale.getStr("blueplanet.controlPanel.childNode") + "("+device.slaveModuleList?size+")"}</a>
                        </#if>
                    </td>
                    <td><@typeNameOfDevice device.nodeType,device.isHumidity/></td>
                    <td><@deviceState device/></td>
                    <td>
                        <#if device.sn??>
                            <@snOfDevice device.sn/>
                        <#else>
                        ${locale.getStr("blueplanet.device.nothing")}
                        </#if>
                    </td>
                    <td><@deviceMode device.deviceMode/></td>
                    <td><i class="mw-icon-wifi"></i> ${device.rssi} <i class="mw-icon-connect"></i> ${device.lqi!}</td>
                    <td>
                        <#if security.isPermitted("blueplanet:manage:device:edit")>
                            <a class="btn btn-mini"
                               href="device/${device.nodeId}/detail/edit">${locale.getStr("common.update")}</a>
                        </#if>
                        <#if security.isPermitted("blueplanet:manage:device:delete")>
                        <#--<a class="btn btn-mini btn-danger"-->
                        <#--title="${locale.getStr("blueplanet.device.deleteDevice")}" data-value='${device.nodeId}'-->
                        <#--data-button="deleteDevice"-->
                        <#-->${locale.getStr("common.delete")}</a>-->
                        </#if>
                        <a class="btn btn-mini" href="device/${device.nodeId}/device-state-chart"
                           title="${locale.getStr("blueplanet.device.voltageChart")}" data-value='deviceId'
                           data-button="voltageChart"
                                >${locale.getStr("blueplanet.device.voltageChart")}</a>
                    </td>
                </tr>

                <#-- 如果是主模块, 则显示从模块列表 -->
                    <#if device.nodeType == 3>
                        <#list device.slaveModuleList as slaveModule>
                        <tr class="hide" data-master-for="${device.nodeId}">
                        <#-- 如果是第一行 -->
                            <td>
                                <input name="nodeId" type="checkbox" value="${slaveModule.nodeId}" class="nodeId"/>
                            </td>
                            <#if slaveModule_index == 0>
                                <td rowspan="${device.slaveModuleList?size}"></td>
                            </#if>
                            <td><@typeIconOfDevice slaveModule.nodeType/> <a href="device/${slaveModule.nodeId}/detail"
                                                                             title="${locale.getStr("blueplanet.device.deviceDetail")}">
                                <@strongNodeId slaveModule.nodeId, true/>
                            </a></td>

                            <td><@typeNameOfDevice slaveModule.nodeType/></td>
                            <td><@deviceState slaveModule/></td>
                            <td>
                                <#if slaveModule.sn??>
                                    <@snOfDevice slaveModule.sn/>
                                <#else>
                                ${locale.getStr("blueplanet.device.nothing")}
                                </#if>
                            </td>
                            <td><@deviceMode slaveModule.deviceMode/></td>
                        <#--&lt;#&ndash; 如果是第一行 &ndash;&gt;-->
                        <#--<#if slaveModule_index == 0>-->
                        <#--<td rowspan="${device.slaveModuleList?size}" colspan="1"></td>-->
                        <#--</#if>-->
                            <td><i class="mw-icon-wifi"></i> ${slaveModule.rssi} <i
                                    class="mw-icon-connect"></i> ${slaveModule.lqi!}</td>
                            <td>
                                <#if security.isPermitted("blueplanet:manage:device:edit")>
                                    <a class="btn btn-mini"
                                       href="device/${slaveModule.nodeId}/detail/edit">${locale.getStr("common.update")}</a>
                                </#if>
                                <a class="btn btn-mini" href="device/${device.nodeId}/device-state-chart"
                                   title="${locale.getStr("blueplanet.device.voltageChart")}" data-value='deviceId'
                                   data-button="voltageChart"
                                        >${locale.getStr("blueplanet.device.voltageChart")}</a>
                            </td>
                        </tr>
                        </#list>
                    </#if>

                </#list>

            </tbody>
        </table>

        <#include "../../_common/pagging.ftl">
        <#assign pagingUrl = "devices?deviceId=${deviceId!}&deviceType=${deviceType}">
        <@pagination pagingUrl, page, pageSum/>

        <#if devices?size == 0>
            <h4>${locale.getStr("common.noData")}</h4>
        </#if>

        <@deviceStateDescription/>
    </div>

    <#include "../../_common/delete-device-model.ftl">
</div>

<div class="hide">
    <div id="zoneTreeDialog" class="span4">
        <div id="zoneTree" class="ztree"></div>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>
</#macro>

<#-- 设备 select 选中状态 -->
<#macro selected expected, value = -1>
    <#if value??>
        <#if expected == value>
        selected="selected"
        </#if>
    </#if>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
    <@scriptTag "js/pnotify.js"/>
    <@scriptTag "js/device/query-binding-devices.js"/>
    <@scriptTag "js/device/delete-device.js"/>
<script type="text/javascript">
    $(function () {
        var $nodeId = $(".nodeId");
        $nodeId.click(function () {
            var isChecked = true;
            for (var i = 0; i < $nodeId.length; i++) {
                if (!$nodeId[i].checked) {
                    isChecked = false;
                }
            }
            if (isChecked) {
                $allCheck.attr("checked", "checked");
            } else {
                $allCheck.removeAttr("checked");
            }
        });

        var $allCheck = $("th input[type='checkbox']");
        $allCheck.change(function () {
            if (this.checked) {
                $nodeId.attr("checked", "checked");
            } else {
                $nodeId.removeAttr("checked");
            }
        });
    });
</script>
</#macro>