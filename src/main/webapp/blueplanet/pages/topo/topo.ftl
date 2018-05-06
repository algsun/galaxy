<#--
网络拓扑图
@author liuzhu
@date 2013.09.29
@check @xiedeng 2013-10-11 #5865

-->

<#assign title="环境监控-网络拓扑图">
<#assign title>${locale.getStr("blueplanet.topo.title")}</#assign>
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:monitor:topo">
<#macro head>
<style type="text/css">
    #cy {
        height: 470px;
        width: 100%;
    }
</style>
<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
    <#include "../_common/common-css.ftl">
</#macro>

<#macro content>

<ul class="nav nav-tabs">
    <li class="active"><a href="topo.action">${locale.getStr("blueplanet.topo")}</a></li>
    <li><a href="timeoutDevice.action">${locale.getStr("blueplanet.topo.timeoutDevice")}</a></li>
    <li><a href="deviceAnalyse.action">${locale.getStr("blueplanet.topo.deviceAnalyse")}</a></li>
    <li><a href="lossPackage.action">${locale.getStr("blueplanet.topo.lossPackage")}</a></li>
    <li><a href="deviceLink.action">${locale.getStr("blueplanet.topo.deviceLink")}</a></li>
</ul>
    <#if topoNodes?size==0>
    <h4>${locale.getStr("common.noData")}</h4>
    <#else>
    <div id="nodeId" style="width: 100%;height: 30px;">
        <div class="row-fluid">
            <div class="f-l">
                <select style="width: 200px; height: 30px;" id="device" name="nodeId" class="easyui-combobox">
                    <optgroup label="${locale.getStr("common.gateway")}">
                        <#list topoNodes as node>
                            <#if node.nodeType==7>
                                <@selectOption node/>
                            </#if>
                        </#list>
                        <optgroup label="${locale.getStr("common.relay")}">
                            <#list topoNodes as node>
                                <#if node.nodeType==2>
                                    <@selectOption node/>
                                </#if>
                            </#list>
                            <optgroup label="${locale.getStr("common.controlModule")}">
                                <#list topoNodes as node>
                                    <#if node.nodeType==5>
                                        <@selectOption node/>
                                    </#if>
                                </#list>
                                <optgroup label="${locale.getStr("common.node")}">
                                    <#list topoNodes as node>
                                        <#if node.nodeType==1>
                                            <@selectOption node/>
                                        </#if>
                                    </#list>
                                    <optgroup label="${locale.getStr("common.mainModule")}">
                                        <#list topoNodes as node>
                                            <#if node.nodeType==3>
                                                <@selectOption node/>
                                            </#if>
                                        </#list>
                                        <optgroup label="${locale.getStr("common.childModule")}">
                                            <#list topoNodes as node>
                                                <#if node.nodeType==4>
                                                <@selectOption node/>
                                            </#if>
                                            </#list>
                </select>
            </div>
            <div class="f-r m-t-5">${locale.getStr("blueplanet.topo.deviceSize")}:<span
                    id="allDeviceCount"
                    style="font-weight: bolder">${allDeviceCount!}</span>&nbsp;&nbsp;&nbsp;${locale.getStr("blueplanet.topo.gatewaySum")}
                :<span id="gatewaySum" style="font-weight: bolder">${gatewaySum}</span> &nbsp;&nbsp;&nbsp;
            ${locale.getStr("blueplanet.topo.relaySum")}:<span id="relaySum"
                                                               style="font-weight: bolder">${relaySum} </span> &nbsp;&nbsp;&nbsp;${locale.getStr("blueplanet.topo.controlModuleSum")}
                :<span id="controlModuleSum" style="font-weight: bolder">${controlModuleSum}</span>
                &nbsp;&nbsp;&nbsp;${locale.getStr("blueplanet.topo.nodeSum")}:<span id="nodeSum"
                                                                                    style="font-weight: bolder">${nodeSum}</span>
                &nbsp;&nbsp;&nbsp;${locale.getStr("blueplanet.topo.mainModuleSum")}:
                <span id="mainModuleSum" style="font-weight: bolder">${mainModuleSum} </span>
                &nbsp;&nbsp;&nbsp;${locale.getStr("blueplanet.topo.childModuleSum")}:<span id="childModuleSum"
                                                                                           style="font-weight: bolder">${childModuleSum}</span>
            </div>
            <div id="divDeviceInfo" class="clear">&nbsp;</div>

        </div>

    </div>

    <div id="cy" class="clear"></div>
    <div><p style="color: #969696; text-align: left;">${locale.getStr("blueplanet.topo.tips")}</p></div>
    </#if>


<div id="topoData" class="hide">
${topoData!}
</div>
<div id="trData" class="hide">
${topoReferencesData!}
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/cytoscape/2.0.2/cytoscape.min.js"></script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../common/js/util.js"></script>
<script type="text/javascript">

    $("#device").select2({
        placeholder: "",
        allowClear: true,
        formatNoMatches: function () {
            return message.noData
        }
    });
    $(function () {
        if (!isCanvasSupported()) {
            $("#divDeviceInfo").after("<h4>" + message.browserDoesNotSupport + "<a href='http://windows.microsoft.com/zh-cn/internet-explorer/ie-9-worldwide-languages'>IE9+</a>、<a href='http://www.google.com/intl/zh-CN/chrome/'>Chrome</a>、Opera、Safari</h4>")
            return;
        }
        var topoData = $.parseJSON($("#topoData").text());
        var trData = $.parseJSON($("#trData").text());
        //paddingSize 为内边距 默认为30 当只有一个数据或小于1的时候，将内边距设为210，负责设备图片会很大.
        function getOptions(topoData, trData) {
            var paddingSize = 30;
            if (topoData.length <= 1) {
                paddingSize = 210;
            }
            var options = {
                container: document.getElementById('cy'),
                boxSelectionEnabled: false,
                autounselectify: true,
                layout: {
                    name: 'breadthfirst',
                    fit: true,
                    directed: false,
                    circle: false,
                    padding: paddingSize
                },
                showOverlay: false,
                minZoom: 0,
                maxZoom: 0,
                style: cytoscape.stylesheet()
                        .selector('node')
                        .css({
                            'shape': 'data(faveShape)',
                            'background-image': 'data(backgroundImage)',
                            'background-color': 'data(faveColor)',
                            'content': 'data(name)',
                            'font-family': 'helvetica',
                            'font-size': 11,
                            'text-valign': 'bottom'
                        })
                        .selector(':selected')
                        .css({
                            'background-color': '#000',
                            'line-color': '#000',
                            'target-arrow-color': '#000',
                            'text-outline-color': '#000'
                        })
                        .selector('edge')
                        .css({
                            'width': 1,
//                        'target-arrow-shape': 'triangle',
                            'line-color': 'data(lineColor)',
//                        'target-arrow-color': 'data(lineColor)'
                        }),
                elements: {
                    nodes: topoData, edges: trData

                },
                ready: function () {
                    var cy = this;
                    cy.panBy({
                        x: 0,
                        y: -20
                    });
                    var divDevice = $("#divDeviceInfo");
                    cy.on('mouseover', 'node', {foo: 'bar'}, function (evt) {
                        var node = evt.cyTarget;
                        $.post("getDeviceInfoById.action", {nodeId: node.id()}, function (result) {
                            if (result) {
                                divDevice.html(resultToDeviceInfo(result));
                            }
                            function returnNodeIdName(obj) {
                                var nodeIdName = obj.nodeId.substring(8, 16);
                                if (obj.nodeName != null && obj.nodeName != "") {
                                    nodeIdName += "(" + obj.nodeName + ")";
                                }
                                return nodeIdName;
                            }

                            function resultToDeviceInfo(obj) {
                                var deviceInfo = returnTypeName(obj.nodeType) + ":" + returnNodeIdName(obj);
                                +"&nbsp;&nbsp;";
                                if (obj.rssi <= -80) {
                                    deviceInfo += "&nbsp;" + message.signalIntensity + "（rssi）:" + "<i class='mw-icon-wifi'></i><span class='badge badge-important' style='font-weight: bolder'>" + result.rssi + "</span>&nbsp;&nbsp;"
                                    + message.linkQuality + "（lqi）:" + "<i class='mw-icon-connect'></i><span class='badge badge-important' style='font-weight: bolder'>" + result.lqi + "</span>";
                                } else {
                                    deviceInfo += "&nbsp;" + message.signalIntensity + "（rssi）" + "<i class='mw-icon-wifi'></i><span class='badge badge-success' style='font-weight: bolder'>" + result.rssi + "</span>&nbsp;&nbsp;"
                                    + message.linkQuality + "（lqi）:" + "<i class='mw-icon-connect'></i><span class='badge badge-success' style='font-weight: bolder'>" + result.lqi + "</span>";
                                }
                                return deviceInfo;
                            }

                            function returnTypeName(nodeType) {
                                var typeName = "";
                                switch (nodeType) {
                                    case 1:
                                        typeName = message.node;
                                        break;
                                    case 2:
                                        typeName = message.relay;
                                        break;
                                    case 3:
                                        typeName = message.masterModule;
                                        break;
                                    case 4:
                                        typeName = message.slaveModule;
                                        break;
                                    case 5:
                                        typeName = message.controlModule;
                                        break;
                                    case 7:
                                        typeName = message.gateway;
                                        break;

                                }
                                return typeName;
                            }
                        });
                        debugger;
                    });
//                .on('mouseout', 'node', {foo: 'bar'}, function (evt) {
//                            divDevice.text("")
//                        });
                }
            };
            return options;
        }
        $('#cy').cytoscape(getOptions(topoData, trData));

        $("#device").change(function () {
            window.location.href = "topo?nodeId=" + $('#device').val();
        });
        setInterval(function () {
            var $nodeId = $('#device').val();
            $.get("refreshShowData?nodeId=" + $nodeId, function (data) {
                $("#allDeviceCount").text(data.allDeviceCount);
                $("#gatewaySum").text(data.deviceCountMap["7"]);
                $("#relaySum").text(data.deviceCountMap["2"]);
                $("#nodeSum").text(data.deviceCountMap["1"]);
                $("#mainModuleSum").text(data.deviceCountMap["3"]);
                $("#childModuleSum").text(data.deviceCountMap["4"]);
                $("#controlModuleSum").text(data.deviceCountMap["5"]);
                topoData = $.parseJSON(data.topoData);
                trData = $.parseJSON(data.topoReferencesData);
                $('#cy').cytoscape(getOptions(topoData, trData));
            });
        }, 10000);
    });
</script>

</#macro>
<#macro selectOption node>
<option value="${node.nodeId!}"
        <#if node.nodeId==nodeId>selected="selected" </#if>>${node.nodeId?substring(8,13)}<#if node.nodeName?? && node.nodeName!="">
    (${node.nodeName!})</#if></option>
</#macro>

