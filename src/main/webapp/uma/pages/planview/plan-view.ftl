<#--
  - 平面图
  -@author li.jianfei
  -@time  2013-5-31
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>平面图 - 人员行为</title>

<#include "../_common/common-css.ftl">
    <style type="text/css">
        .zone-image-container {
            overflow: auto;
        }


        .zone-image {
            position: relative;
            margin: 0 auto;
            background-image: url('${resourcesPath!}/${zone.planImage!}?_=${.now?long?c}');
        }
    </style>
</head>
<body>

<#--页面以及标题-->
<#assign currentTopPrivilege = "uma:realtimeLocation">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#--<#include "../_common/sub-navs.ftl">-->
<#--<@subNavs "uma:realtimeLocation:"></@subNavs>-->

<#--自定义二级菜单-->
    <div class="row m-t-10">
        <div class="span12">
            <ul class="nav nav-pills">
                <li <#if queryType==0>class="active"</#if>>
                    <a href="realtimeLocation.action?queryType=0">按规则统计</a>
                </li>
                <li <#if queryType==1>class="active"</#if>>
                    <a href="realtimeLocation.action?queryType=1">按位置统计</a>
                </li>
            </ul>
        </div>
    </div>

<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--your content-->
    <div class="row">
        <div class="span12">
            <ul class="breadcrumb m-b-10 p-v-5" style="height: 33px;">
                <li class="muted">当前位置</li>
                <span id="area-device-path"></span>
            </ul>
        </div>
    </div>


    <div class="zone-image-container">
    <#if zone.planImage??>
        <div id="drag-container">
            <div id="zone-image" class="zone-image"
                 image-src="${resourcesPath!}/${zone.planImage}"></div>
        </div>
    <#else>
        <h4>该区域无平面图</h4>

        <div class="m-t-10 mutted">请先到区域管理上传平面图</div>
    </#if>
    </div>
<#--数据列表-->
</div>


<#include "../_common/zone-device-path-template.ftl">
<#include "../_common/footer.ftl">

<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/moment/2.0.0/moment.min.js"></script>
<script type="text/javascript" src="../assets/moment/2.0.0/lang/zh-cn-custom.js"></script>
<@scriptTag "js/planview/plan-image-load.js" />

<script type="text/javascript">

    $(function () {
    <#--var BluePlanet = App("blueplanet");-->

    <#--// 模拟数据-->
    <#--var zoneId = "${zoneId}"; // 此处为 freemarker-->
    <#--$.getJSON("zone-device-path.json", {zoneId: zoneId}, function (nodes) {-->
    <#--BluePlanet.zoneDevicePath.createNode(nodes);-->
    <#--});-->
        //区域平面图大小确定  获取平面图原始长、宽,然后设置显示长、宽（background-image样式）
        var $zoneImage = $("#zone-image");
        var imageUrl = $zoneImage.attr('image-src');
        Images.naturalSize(imageUrl, function (width, height) {
            $zoneImage.css('width', width);
            $zoneImage.css('height', height);
        });
//        TOOLTIP.initToggleName();



        // 平面图高度自适应
        var scrollBarWidth = 30; // 浏览器滚动条宽度(比正常稍大一些)
        var $ele = $("#drag-container");
        $(window).resize(function (event) {
            var y = $ele.offset().top;
            var windowHeight = $(window).height();
            // 表格高度 = 窗口高度 - 表格位置 - 滚动条宽度
            $ele.css("height", windowHeight - y - scrollBarWidth);
        }).resize();



        $(document).bind("selectstart", function () {
            return false; // 取消文本选中
        });
        SERVICE.getDeployInfo(zoneId, function (result) {
            deployZones(result.zones);
        });
        var PLAN_IMAGE_UTIL = {
            //呈现已部署设备
            deployDevices: function (devices) {
                for (var i = 0, device; device = devices[i]; i++) {
                    //如果设备坐标不是-1 则该设备已部署过 添加在平面图的相应位置  否则添加在未部署列表
                    if (device.coordinateX < 0 || device.coordinateY < 0) {
                    } else {
                        var deviceView = new Device(device.nodeId, device.nodeName, device.coordinateX, device.coordinateY, device.nodeType, device.lowvoltage, device.anomaly);
                        deviceView.join();
                        TOOLTIP.addDblclick(deviceView);
                        TOOLTIP.deviceInfoView(deviceView);
                    }
                }
            },
            //呈现已部署的区域
            deployZones: function (zones) {
                for (var i = 0, zone; zone = zones[i]; i++) {
                    if (zone.coordinateX < 0 || zone.coordinateY < 0) {
                    } else {
                        var zoneView = new Zone(zone.zoneId, zone.zoneName, zone.coordinateX, zone.coordinateY);
                        zoneView.join();
                        TOOLTIP.addDblclick(zoneView);
                        TOOLTIP.zoneInfoView(zoneView);
                    }
                }
            }
        };
    });
</script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
