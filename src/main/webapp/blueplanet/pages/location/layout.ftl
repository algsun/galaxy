<#--
设备页面布局

@author liuzhu
@date 2014-6-26
-->

<#include "/common/pages/common-tag.ftl">

<!DOCTYPE html>
<html>
<head>

<#-- import page START -->
<#-- 引入内容页面 -->
<#import _pagePath as _page>
<#-- import page END -->


<#include "../_common/common-head.ftl">
    <title>${_page.title}</title>

<#include "../_common/common-css.ftl">

<#-- head START -->
<@_page.head/>
<#-- head END -->

</head>
<body data-server-time="${.now?long?c}">

<#include "../_common/header.ftl">

<#-- 区域设备树 -->
<#--<div.left-aside-container">-->
<#include "../_common/zone-device-tree.ftl">
<#--</div>-->

<#--伸缩条-->
<div class="shrink-bar" title="${locale.getStr("blueplanet.common.shrinkBar")}">
    <div class="shrink-bar-icon"></div>
</div>

<div class="content-container">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <ul id="area-location-path" class="breadcrumb m-b-10 p-v-5" style="height: 33px;">
                    <li class="muted">${locale.getStr("blueplanet.common.currentLocation")}</li>
                </ul>

                <div class="tabbable">
                    <ul class="nav nav-tabs">

                    <#-- 显示功能 tab -->
                    <#macro contentTab tabIndex, currentIndex, href, name>
                        <li <#if currentIndex == tabIndex>class="active"</#if>>
                            <a <#if currentIndex == tabIndex>
                                    href="#tab0" data-toggle="tab"
                            <#else>
                                    href="${href}"
                            </#if>
                                    >${name}</a>
                        </li>
                    </#macro>
                    <#macro containsSensorinfo sensorPhysicalId>
                        <#list _sensorinfoesOfLocation as sensorinfo>
                            <#if sensorinfo.sensorPhysicalid == sensorPhysicalId>
                                <#nested>
                            </#if>
                        </#list>
                    </#macro>

                    <#macro containsSensorinfos sensorPhysicalIdOP,sensorPhysicalIdIP,sensorPhysicalIdSP>
                        <#list _sensorinfoesOfLocation as sensorinfo>
                            <#if sensorinfo.sensorPhysicalid == sensorPhysicalIdOP ||
                            sensorinfo.sensorPhysicalid == sensorPhysicalIdIP ||
                            sensorinfo.sensorPhysicalid == sensorPhysicalIdSP>
                                <#nested>
                                <#break >
                            </#if>
                        </#list>
                    </#macro>

                    <@contentTab 0, _page.tabIndex, "location/${locationId!}",locale.getStr("blueplanet.common.summary")/>
                    <@contentTab 1, _page.tabIndex, "location/${locationId!}/realtime-data",locale.getStr("blueplanet.common.realtimeData")/>
                    <@contentTab 2, _page.tabIndex, "location/${locationId!}/realtime-chart",locale.getStr("blueplanet.location.realtimeGraphic")/>
                    <@contentTab 3, _page.tabIndex, "location/${locationId!}/history-data",locale.getStr("blueplanet.location.historicalData")/>
                    <#--&lt;#&ndash;-->
                    <#--&ndash;&gt;-->
                    <#--<li><a>位置呈现</a></li>-->
                    <@contentTab 4, _page.tabIndex, "location/${locationId!}/basic-chart",locale.getStr("blueplanet.location.basicCurveChart")/>
                    <@containsSensorinfo 48>
                        <@contentTab 5, _page.tabIndex, "location/${locationId!}/windrose-chart",locale.getStr("blueplanet.location.windRoseDiagram")/>
                    </@containsSensorinfo>
                    <@containsSensorinfo 47>
                        <@contentTab 6, _page.tabIndex, "location/${locationId!}/rainfall-chart",locale.getStr("blueplanet.location.rainfallGraph")/>
                    </@containsSensorinfo>
                    <@containsSensorinfo 41>
                        <@contentTab 7, _page.tabIndex, "location/${locationId!}/light-chart",locale.getStr("blueplanet.location.illuminationGraph")/>
                    </@containsSensorinfo>
                    <@contentTab 8,_page.tabIndex,"location/${locationId}/average-peak-data",locale.getStr("blueplanet.location.averagePeakValue")/>
                    <@contentTab 9,_page.tabIndex,"location/${locationId!}/average-compare",locale.getStr("blueplanet.location.averageValueRatio")/>
                    <@containsSensorinfo 80>
                        <@contentTab 10,_page.tabIndex,"location/${locationId}/evaporation-chart",locale.getStr("blueplanet.location.evaporationMap")/>
                    </@containsSensorinfo>
                    <#if security.isPermitted("blueplanet:location:stock")>
                        <@contentTab 11, _page.tabIndex, "location/${locationId!}/stock",locale.getStr("blueplanet.location.earlyWarningOfBigDataAnalysis")/>
                    </#if>
                    <@containsSensorinfos 3075,3076,3077>
                        <@contentTab 12, _page.tabIndex, "location/${locationId!}/qcm", locale.getStr("blueplanet.location.QCMdifferencePlot")/>
                        <@contentTab 13, _page.tabIndex, "location/${locationId!}/qcm_level", locale.getStr("blueplanet.location.QCMlevel")/>
                    </@containsSensorinfos>
                    <@containsSensorinfo 97>
                        <@contentTab 14, _page.tabIndex, "location/${locationId!}/water-flow",locale.getStr("blueplanet.location.waterFlowMonitoring")/>
                    </@containsSensorinfo>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab0">

                        <#-- content START -->
                        <@_page.content/>
                        <#-- content END -->

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "../_common/common-js.ftl">

<script type="text/javascript" src="../assets/underscore/1.4.2/underscore-min.js"></script>

<@scriptTag "js/device-tree.js"/>

<#include "../_common/zone-device-path-template.ftl">
<@scriptTag "js/zone-location-path.js"/>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");

    // 当设备选中时
    var whenLocationChange = function (node, dtd, callback) {
        // 如果
        var url = callback(node);
        if (!url) {
            dtd.reject();
            return;
        }

        dtd.resolve();
        location.href = BluePlanet.basedUrl(url);
    };

    // 当选中区域
    var whenZoneChange = function (node, dtd) {
        dtd.resolve();
        location.href = BluePlanet.basedUrl("zone/" + node.id + "/realtime-data");
    };

    // 当设备改变时跳转, callback 返回要跳转到的地址,相对于 "/blueplanet/".
    // 如果不需要跳转 callback 返回 false 即可
    BluePlanet.zoneLocationPath.redirectWhenLocationChange = function (callback) {
        this.change(function (node, dtd) {
            if (node.type === 'location') {
                whenLocationChange(node, dtd, callback);
            } else if (node.type === 'zone') {
                whenZoneChange(node, dtd);
            }
        });
    };
    $(function () {
        // 模拟数据
        //BluePlanet.createNode(BluePlanet.deviceNodes);
        // 此处为 freemarker
        var locationId = "${locationId}";
        $.getJSON("zone-location-path.json", {locationId: locationId}, function (nodes) {
            BluePlanet.zoneLocationPath.createNode(nodes);
        });
    });
</script>

<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    <#if locationId??>
    BluePlanet.deviceTree.selectDevice("${locationId}");
    </#if>
</script>


<#-- script START -->
<@_page.script/>
<#-- script END -->

<#include "../_common/last-resources.ftl">
</body>
</html>