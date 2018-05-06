<#--
区域页面布局

@author gaohui
@date 2013-01-11
-->

<#include "/common/pages/common-tag.ftl">
<!DOCTYPE html>
<html>
<head>
<#-- import page START -->
<#-- 引入内容页面 -->
<#import _pagePath as page>
<#-- import page END -->


<#include "../_common/common-head.ftl">
    <title>${page.title}</title>

<#include "../_common/common-css.ftl">

<#-- head START -->
<@page.head/>
<#-- head END -->

</head>
<body data-server-time="${.now?long?c}">
<#include "../_common/header.ftl">


<#-- 区域设备树 -->
<#--<div.left-aside-container">-->
<#include "../_common/zone-device-tree.ftl">
<#--</div>-->

<#-- 伸缩条 -->
<div class="shrink-bar" title="${locale.getStr("blueplanet.common.shrinkBar")}">
    <div class="shrink-bar-icon"></div>
</div>

<div class="content-container">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <ul class="breadcrumb m-b-10 p-v-5" style="height: 33px;">
                    <li class="muted">${locale.getStr("blueplanet.common.currentLocation")}</li>
                    <span id="area-location-path"></span>
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

                    <@contentTab 0, page.tabIndex, "zone/${zoneId!}", locale.getStr("blueplanet.common.summary")/>
                    <@contentTab 2, page.tabIndex, "zone/${zoneId!}/plan-image",locale.getStr("blueplanet.zone.planeMap")/>
                    <@contentTab 1, page.tabIndex, "zone/${zoneId!}/realtime-data", locale.getStr("blueplanet.common.realtimeData")/>
                    <#if security.isPermitted("blueplanet:zone:thresholdAlarm")>
                        <@contentTab 3, page.tabIndex, "zone/${zoneId!}/threshold/view",locale.getStr("blueplanet.zone.alarmThreshold")/>
                    </#if>
                    <@contentTab 4, page.tabIndex, "zone/${zoneId!}/scalar-field",locale.getStr("blueplanet.zone.scalarField")/>
                    <#if security.isPermitted("blueplanet:zone:windFiled")>
                        <@contentTab 5, page.tabIndex, "zone/${zoneId!}/wind-field",locale.getStr("blueplanet.zone.windField")/>
                    </#if>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab0">

                        <#-- content START -->
                        <@page.content/>
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
    (function(){
        var BluePlanet = App("blueplanet");

        // 当前页面功能
        var _currentPage;
        BluePlanet.zoneLocationPath.atPage = function (currentPage) {
            _currentPage = currentPage;
        };

        // 区域，设备切换时
        BluePlanet.zoneLocationPath.change(function (node, dtd) {
            if (node.type === 'zone') {
                dtd.resolve();

                if (_currentPage === 'realtime-data') { // 实时数据
                    location.href = BluePlanet.basedUrl("zone/" + node.id + "/realtime-data");
                } else if (_currentPage === 'plan-image') { // 平面地图
                    location.href = BluePlanet.basedUrl("zone/" + node.id + "/plan-image");
                } else if (_currentPage ==='index'){
                    location.href = BluePlanet.basedUrl("zone/" + node.id);
                }else if(_currentPage ==='threshold'){
                    location.href = BluePlanet.basedUrl("zone/" + node.id + "/threshold/view");
                }else if(_currentPage === 'scalar-field'){
                    location.href = BluePlanet.basedUrl("zone/" + node.id + "/scalar-field");
                }else if(_currentPage === 'wind-field'){
                    location.href = BluePlanet.basedUrl("zone/" + node.id + "/wind-field");
                }
                else { // 默认
                    location.href = BluePlanet.basedUrl("zone/" + node.id + "/realtime-data");
                }

            } else if (node.type === 'device') {
                dtd.resolve();
                location.href = BluePlanet.basedUrl("device/" + node.id);
            }
        });
    })();

    $(function () {
        var BluePlanet = App("blueplanet");

        // 模拟数据
        // BluePlanet.createNode(BluePlanet.areaNodes);
        // TODO zoneName 没用到 @gaohui 2013-04-12
        var zoneName = "${(zone.zoneName)!""}"; // 此处为 freemarker
        // BluePlanet.createNode({name: zoneName});
        var zoneId = "${zoneId}"; // 此处为 freemarker
        $.getJSON("zone-location-path.json", {zoneId: zoneId}, function (nodes) {
            BluePlanet.zoneLocationPath.createNode(nodes);
        });
    });
</script>

<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    <#if zoneId??>
    BluePlanet.deviceTree.selectZone("${zoneId}");
    </#if>
</script>

<#-- script START -->
<@page.script/>
<#-- script END -->

<#include "../_common/last-resources.ftl">
</body>
</html>
