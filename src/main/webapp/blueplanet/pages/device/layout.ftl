<#--
设备页面布局

@author gaohui
@date 2013-01-15
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
<div class="shrink-bar" title="点击收缩">
    <div class="shrink-bar-icon"></div>
</div>

<div class="content-container">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <ul id="area-device-path" class="breadcrumb m-b-10 p-v-5" style="height: 33px;">
                    <li class="muted">当前位置</li>
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
                        <#list _sensorinfosOfDevice as sensorinfo>
                            <#if sensorinfo.sensorPhysicalid == sensorPhysicalId>
                                <#nested>
                            </#if>
                        </#list>
                    </#macro>

                    <@contentTab 0, _page.tabIndex, "device/${deviceId!}", "概览"/>
                    <@contentTab 1, _page.tabIndex, "device/${deviceId!}/realtime-data", "实时数据"/>
                    <@contentTab 2, _page.tabIndex, "device/${deviceId!}/realtime-chart", "实时图形"/>
                    <@contentTab 3, _page.tabIndex, "device/${deviceId!}/history-data", "历史数据"/>
                    <#--
                    -->
                    <#--<li><a>位置呈现</a></li>-->
                    <@contentTab 4, _page.tabIndex, "device/${deviceId!}/basic-chart", "基础曲线图"/>
                    <@containsSensorinfo 48>
                        <@contentTab 5, _page.tabIndex, "device/${deviceId!}/windrose-chart", "风向玫瑰图"/>
                    </@containsSensorinfo>
                    <@containsSensorinfo 47>
                        <@contentTab 6, _page.tabIndex, "device/${deviceId}/rainfall-chart", "降雨量图"/>
                    </@containsSensorinfo>
                    <@containsSensorinfo 41>
                        <@contentTab 7, _page.tabIndex, "device/${deviceId}/light-chart", "累积光照图"/>
                    </@containsSensorinfo>
                    <@contentTab 8,_page.tabIndex,"device/${deviceId}/average-peak-data","均峰值"/>
                    <@contentTab 9,_page.tabIndex,"device/${deviceId}/average-compare","均值比较"/>
                    <@containsSensorinfo 80>
                        <@contentTab 10,_page.tabIndex,"device/${deviceId}/evaporation-chart","蒸发量图"/>
                    </@containsSensorinfo>
                    <#--<li><a href="device-realtime-data.action">实时数据</a></li>-->
                    <#--<li><a>实时图形</a></li>-->
                    <#--<li><a href="device-history-data.action">历史数据</a></li>-->
                    <#--<li><a href="device-basic-chart.action">基础曲线图</a></li>-->
                    <#--<li><a href="device-windrose-chart.action">风向玫瑰图</a></li>-->
                    <#--<li><a href="device-column-chart.action">降雨量柱状图</a></li>-->
                    <#--<li><a>累计光照阴影图</a></li>-->
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
    var whenDeviceChange = function (node, dtd, callback) {
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
    BluePlanet.zoneDevicePath.redirectWhenDeviceChange = function (callback) {
        this.change(function (node, dtd) {
            if (node.type === 'device') {
                whenDeviceChange(node, dtd, callback);
            } else if (node.type === 'zone') {
                whenZoneChange(node, dtd);
            }
        });
    };
    $(function () {
        // 模拟数据
        //BluePlanet.createNode(BluePlanet.deviceNodes);
        // 此处为 freemarker
        var deviceId = "${deviceId}";
        $.getJSON("zone-device-path.json", {deviceId: deviceId}, function (nodes) {
            BluePlanet.zoneDevicePath.createNode(nodes);
        });
    });
</script>

<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    <#if deviceId??>
    BluePlanet.deviceTree.selectDevice("${deviceId}");
    </#if>
</script>


<#-- script START -->
<@_page.script/>
<#-- script END -->

<#include "../_common/last-resources.ftl">
</body>
</html>