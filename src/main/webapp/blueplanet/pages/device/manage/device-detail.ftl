<#--
设备详情

@author gaohui
@date 2013-01-24
@check @wang yunlong 2013-02-26 #1719
-->

<#assign title>${locale.getStr("blueplanet.device.detail.title")}</#assign>

<#macro head>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
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
</style>

</#macro>

<#include "/common/pages/common-tag.ftl">
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
            <div class="span7">
                <h4><span
                        class="muted">${locale.getStr("blueplanet.device.deviceDetail")}：</span><@typeIconOfDevice device.nodeType/> ${device.nodeName!device.nodeId?substring(8)}
                </h4>
            </div>
            <div class="span5 t-a-r">
                <#if security.isPermitted("blueplanet:manage:device:edit")>
                    <a class="btn" href="device/${deviceId}/detail/edit"><i class="icon-pencil"></i>${locale.getStr("common.update")}</a>
                </#if>
            </div>
        </div>
        <hr class="m-t-0">
    </div>
</div>
<div class="row-fluid">
    <div class="span7">
        <div class="form-horizontal">
            <fieldset>

                <#if location??>
                    <div class="control-group">
                        <label class="control-label">${locale.getStr("common.location")}</label>

                        <div class="controls p-v-5">
                            <a href="location/${location.id}">${location.locationName}</a>
                        </div>
                    </div>
                </#if>

                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplanet.action.location.nodeID")}</label>

                    <div class="controls p-v-5">
                        <@strongNodeId device.nodeId/>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">${locale.getStr("common.deviceType")}</label>

                    <div class="controls p-v-5"><@typeIconOfDevice device.nodeType/> <@typeNameOfDevice device.nodeType/></div>
                </div>
                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplanet.device.sn")}</label>

                    <div class="controls p-v-5">

                        <#if device.sn?? && device.sn !="0">
                            <@snOfDevice device.sn/>
                        <#else>
                            ${locale.getStr("blueplanet.device.nothing")}
                        </#if>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplanet.common.status")}</label>
                        <div class="controls p-v-5">
                            <@deviceState device/>
                        </div>
                </div>
                <div class="control-group">
                    <label class="control-label">rssi</label>

                    <div class="controls p-v-5"><i class="mw-icon-wifi"></i> ${device.rssi}</div>
                </div>
                <div class="control-group">
                    <label class="control-label">lqi</label>

                    <div class="controls p-v-5"><i class="mw-icon-connect"></i> ${device.lqi}</div>
                </div>
                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplanet.location.statesAtLast")}</label>

                    <div class="controls p-v-5">${device.stamp?string("yyyy-MM-dd HH:mm:ss")}</div>
                </div>
                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplanet.topo.isControllable")}</label>

                    <div class="controls p-v-5"><@deviceControl device.notControl/></div>
                </div>
                <div class="control-group">
                    <label class="control-label">${locale.getStr("blueplanet.location.actionPeriod")}</label>

                    <div class="controls p-v-5"><@deviceWorkInterval device.interval/></div>
                </div>

                <div class="control-group">
                    <div class="controls">
                        <#if security.isPermitted("blueplanet:manage:device:edit")>
                            <a class="btn" href="device/${deviceId}/detail/edit"><i class="icon-pencil"></i>${locale.getStr("common.update")}</a>
                        </#if>
                    </div>
                </div>
            </fieldset>
        </div>
    </div>
    <div class="span5">
    <#-- 如果是节点和从模块则显示监测指标 -->
        <#if device.nodeType == 1 || device.nodeType == 4>
            <h5 class="f-n">${locale.getStr("common.monitoringIndicators")}</h5>


            <ul class="list">
            <#-- 遍历监测指标 -->
                <#list sensorinfoes as sensorinfo>
                    <#local sensorId = sensorinfo.sensorPhysicalid>
                    <li data-sensorPhysicalId="${sensorId}">
                        <strong>${sensorinfo.cnName}</strong>
                        <span class="muted">(${sensorinfo.units})</span>

                        <#if formulaMap.get(sensorId)??>
                            <#local formula = formulaMap.get(sensorId) />
                            <#local default = formula.formulaParams>

                            <#if customFormulaParamMap.get(sensorId)??>
                                <#local paramMap = customFormulaParamMap.get(sensorId)>
                                <#local isCustomParam = true >
                            <#else>
                                <#local paramMap = formula.formulaParams>
                                <#local isCustomParam = false >
                            </#if>

                            <a class="muted f-r" href="#"
                               onclick="javascript: $(this).nextAll().toggle(); return false;">公式</a>

                            <ul class="hide">
                                <li>${locale.getStr("blueplanet.device.sensorPrecision")}：${sensorinfo.sensorPrecision}</li>
                                <li>${locale.getStr("blueplanet.device.formulaSignType")}：<@signTypeName formula.signType /></li>
                                <li>${locale.getStr("blueplanet.device.formula.rangeType")}：<@rangeTypeName formula.yRangeType /></li>
                                <li>${locale.getStr("blueplanet.statistics.minValue")}：${formula.minY}</li>
                                <li>${locale.getStr("blueplanet.statistics.maxValue")}：${formula.maxY}</li>
                                <li>${locale.getStr("blueplanet.device.formulaName")}：${formula.formulaName}</li>
                                <li>${locale.getStr("blueplanet.device.formulaParam")}：
                                    <ol style="list-style-type: none;">
                                        <#list paramMap?keys as key>
                                            <li>${key}：${paramMap.get(key)}

                                                <#if isCustomParam>
                                                    <span class="muted">（${locale.getStr("blueplanet.device.default")}：${default.get(key)}）</span>
                                                </#if>
                                            </li>
                                        </#list>
                                    </ol>
                                </li>
                                <hr style="border: solid 1px #D1EEEE;margin-left: -20px;">
                                <li>
                                    <#local max = sensorinfo.maxValue?c>
                                    <#local min = sensorinfo.minValue?c>
                                    ${locale.getStr("blueplanet.device.upperLimit")}： <span><#if max != "0"> ${max}<#else>${locale.getStr("blueplanet.device.nothing")}</#if></span>
                                </li>
                                <li>
                                    ${locale.getStr("blueplanet.device.lowerLimit")}： <span><#if min != "0"> ${min}<#else> ${locale.getStr("blueplanet.device.nothing")} </#if></span>
                            </ul>
                        </#if>
                    </li>
                </#list>
            </ul>

        </#if>
        <#if buzzerSwitch>
            <a id="buzzerSwitch" class="btn" data-value="${device.nodeId}">关闭报警</a>
        </#if>
    </div>
</div>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
    <@scriptTag "js/pnotify.js"/>
<script type="text/javascript">
    // 在设备树中选中当前设备
    var BluePlanet = App("blueplanet");
    BluePlanet.deviceTree.selectDevice("${deviceId}");
</script>
<script type="text/javascript">
    $("#buzzerSwitch").click(function(){
        var $deviceId = $(this).data("value");
        console.log($deviceId);
        $.get("device/buzzerSwitch?deviceId=" + $deviceId,function(data){
            console.log(data);
            if (data){
                window.pnotify("命令发送成功", "success");
            }else {
                window.pnotify("命令发送失败", "warn");
            }
        });
    });
</script>
</#macro>