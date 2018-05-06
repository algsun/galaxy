<#--
设备分析
@author liuzhu
@date 2013.09.29
@check @xiedeng 2013-10-11 #5892

-->

<#assign title="环境监控-设备分析">
<#assign title>${locale.getStr("blueplanet.deviceAnalyse.title")}</#assign>
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:monitor:topo">
<#include "/common/pages/common-tag.ftl"/>

<#macro head>

</#macro>

<#macro content>
</div>
<ul class="nav nav-tabs">
    <li><a href="topo.action">${locale.getStr("blueplanet.topo")}</a></li>
    <li><a href="timeoutDevice.action">${locale.getStr("blueplanet.topo.timeoutDevice")}</a></li>
    <li class="active"><a href="deviceAnalyse.action">${locale.getStr("blueplanet.topo.deviceAnalyse")}</a></li>
    <li><a href="lossPackage.action">${locale.getStr("blueplanet.topo.lossPackage")}</a></li>
    <li><a href="deviceLink.action">${locale.getStr("blueplanet.topo.deviceLink")}</a></li>
</ul>
<div class="row-fluid">
    <div class="span12">
        <form class="form-inline well well-small" method="post" action="blueplanet/deviceAnalyse.action">
            <label class="m-l-20">${locale.getStr("blueplanet.topo.front")}</label>
            <select class="input-small" name="dataSize">
                <option value="10" <@selected 10 dataSize/>>10</option>
                <option value="20" <@selected 20 dataSize/>>20</option>
                <option value="40" <@selected 40 dataSize/>>40</option>
            </select>
            <label class="radio m-l-10">
                <input type="radio" name="anomaly" id="allDevice" value="0"
                       <#if anomaly==0>checked="checked" </#if>>${locale.getStr("common.all")}
            </label>
            <label class="radio m-l-10">
                <input type="radio" name="anomaly" id="normalDevice" value="1"
                       <#if anomaly==1>checked="checked" </#if> >${locale.getStr("blueplanet.topo.normalDevice")}
            </label>
            <label class="radio m-l-10">
                <input type="radio" name="anomaly" id="timeoutDevice" value="-1"
                       <#if anomaly==-1>checked="checked" </#if> >${locale.getStr("blueplanet.topo.abnormalDevice")}
            </label>
            <button class="btn" type="submit">${locale.getStr("common.select")}</button>
        </form>
    </div>
</div>

<div id="container" class="row-fluid">
    <div id="container1" class="span6"></div>
    <div id="container2" class="span6"></div>
</div>

<div class="hide">
    <dl id="loadDeviceQuery">
        <@loadDeviceData/>
    </dl>
</div>
<div class="hide">
    <dl id="rssiQuery">
        <@rssiData/>
    </dl>
</div>
</#macro>

<#---->
<#macro loadDeviceData >
<#-- 设备负载量（设备名称） -->
<dt>
    [ <#list loadDeviceList as loadDevice>
    <#if loadDevice_index != 0> , </#if>
    "${loadDevice.nodeId?substring(8,13)}<#if loadDevice.nodeName?? && loadDevice.nodeName!="">(${loadDevice.nodeName}
        )</#if>"
</#list> ]

</dt>

<#-- 设备负载量（个数） -->
<dd>
    [ <#list loadDeviceList as loadDevice>
    <#if loadDevice_index != 0> , </#if> ${loadDevice.childrenCount}
</#list> ]
</dd>
</#macro>

<#macro rssiData>
<#--设备信号质量(设备名称)-->
<dt>
    [
    <#list rssiList as rssi>
        <#if rssi_index!=0>,</#if>
        "${rssi.nodeId?substring(8,13)}<#if rssi.nodeName?? && rssi.nodeName!="">(${rssi.nodeName})</#if>"
    </#list>
    ]
</dt>
<#--设备信号质量（信号质量）-->
<dd>
    [
    <#list rssiList as rssi>
        <#if rssi_index!=0>,</#if>${rssi.rssi}
    </#list>
    ]
</dd>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript">
    $(function () {
        // 负载量大的设备
        loadDeviceData();

        // highcharts 组织数据
        function dataHighcharts(containerDiv, highchartTitle, footNuit, nuit, categories, data, color) {
            if (data.length == 0) {
                $(containerDiv).append("<div style='font-size: 16px;color: #274b6d;text-align:center;margin-top: 10px;'> " + highchartTitle + "</div>")
                $(containerDiv).append('<div class="m-t-10" ><img src="images/noData-big.jpg"></img></div>');
                $(containerDiv).css({backgroundColor: "#ffffff"});
            } else {
                $(containerDiv).highcharts(charOptions(highchartTitle, footNuit, nuit, categories, data, color));
            }
        }

        // 从 dom 中解析数据,并组装数据
        function loadDeviceData() {
            //设备负载量
            var colorArray = new Array('#4876FF', '#EEA2AD');//柱子的颜色
            var $loadDeivceData = $('#loadDeviceQuery');
            var loadDeviceCategories = $.parseJSON($loadDeivceData.find('dt').text());
            var loadDeviceData = $.parseJSON($loadDeivceData.find('dd').text());
            dataHighcharts("#container1", message.deviceConnectLoads, message.unit + '：' + message.number, message.number, loadDeviceCategories, loadDeviceData, colorArray[0]);

            //设备信号质量
            var $rssiData = $("#rssiQuery");
            var rssiCategories = $.parseJSON($rssiData.find('dt').text());
            var rssiData = $.parseJSON($rssiData.find('dd').text());
            dataHighcharts("#container2", message.devicePoorSignal, '', 'RSSI', rssiCategories, rssiData, colorArray[1])

        }

        // 生成图表
        function charOptions(graphName, yAxiaText, seriesUnit, categories, data, dataColor) {
            return {
                credits: {enabled: false},               //版权链接选项
                chart: {                                   //图标区选项
                    type: 'bar',
                    width: 530,
                    height: 750
                },
                title: {                                   //标题选项
                    text: graphName
                },
                xAxis: {                                   //x轴选项
                    categories: categories
                },
                yAxis: {                                   //y轴选项
                    title: {
                        text: yAxiaText
                    }
                },
                legend: {                                 //图例选项
                    backgroundColor: '#FFFFFF',
                    reversed: false,
                    enabled: false
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            enabled: true
                        }
                    }
                },
                series: [                               //数据列选项
                    {
                        color: dataColor,
                        name: seriesUnit,
                        data: data
                    }
                ]
            }
        }
    });
</script>
</#macro>
