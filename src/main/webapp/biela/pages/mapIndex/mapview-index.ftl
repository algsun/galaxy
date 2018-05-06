<#--
地图站点区域概览

@author wang.geng
@date 2013-12-31
-->
<#assign title="站点概览-区域中心">
<#-- 当前权限标识 -->
application/octet-stream
<#include "/common/pages/common-tag.ftl">
<#include "../_common/helper.ftl">

<#macro head>

<link rel="stylesheet" type="text/css" href="css/style_datauri.css"/>
<style type="text/css">
    .table thead {
        background-color: #ececec;
    }

    html, body {
        margin: 0;
        padding: 0;
        height: 100%;
    }

    #container {
        height: 100%;
        margin-top: 44px;
    }

    #r-map {
        height: 100%;
        width: 80%;
        float: right;
        border-right: 0 solid #bcbcbc;
        z-index: 0;
    }

    #l-sites {
        height: 100%;
        width: 20%;
        float: right;
    }

    .siteName li:hover {
        background: #9699A0;
    }
</style>
</#macro>

<#macro content>
<#--页面以及标题-->
    <#assign currentTopPrivilege = "biela:mapOverview">

<input id="security" type="hidden" value="${security.isPermitted("biela:mapOverview:config")?string}"/>
<div id="container">
    <div id="r-map"></div>
    <div id="l-sites"></div>
</div>
<div class="map_container_tools" style="display: block;">
    <div class="map_satellite_holder not_display" id="displaySatellite" title="显示卫星地图">
        <div class="map_satellite">
            <div class="map_char"><span class="mapType_font">卫星模式</span></div>
        </div>
    </div>
    <div class="map_normal_holder" id="displayNormalMap" title="显示普通地图">
        <div class="map_normal">
            <div class="map_char"><span class="mapType_font">地图模式</span></div>
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=XT3sz5tsGfC0IetebDeYATEQ"></script>
<script type="text/javascript">
$(function () {
    window.openInfoWinFuns = null;
    (function () {
        $.getJSON("mapOverview/getSiteDatas.json", function (result) {
            var maxPoint;
            var minPoint;
            var center;
            if (result.length == 1) {
                maxPoint = null;
                minPoint = null;
                center = new BMap.Point(result[0].lngBaiDu, result[0].latBaiDu);
            } else if (result.length == 2) {
                maxPoint = new BMap.Point(result[0].lngBaiDu, result[0].latBaiDu);
                minPoint = new BMap.Point(result[1].lngBaiDu, result[1].latBaiDu);
                center = null;
            } else if (result.length > 2) {
                var point1;
                var point2;
                var point3;
                var point4;
                for (var i = 0; i < result.length; i++) {
                    if (result[i].logicGroupName == 'maxLngLatSite') {
                        maxPoint = new BMap.Point(result[i].lngBaiDu, result[i].latBaiDu);
                    } else if (result[i].logicGroupName == 'minLngLatSite') {
                        minPoint = new BMap.Point(result[i].lngBaiDu, result[i].latBaiDu);
                    } else if (result[i].logicGroupName == 'maxLntPoint') {
                        point1 = new BMap.Point(result[i].lngBaiDu, result[i].latBaiDu);
                    } else if (result[i].logicGroupName == 'minLntPoint') {
                        point2 = new BMap.Point(result[i].lngBaiDu, result[i].latBaiDu);
                    } else if (result[i].logicGroupName == 'maxLatPoint') {
                        point3 = new BMap.Point(result[i].lngBaiDu, result[i].latBaiDu);
                    } else if (result[i].logicGroupName == 'minLatPoint') {
                        point4 = new BMap.Point(result[i].lngBaiDu, result[i].latBaiDu);
                    }
                }
                var polyline = new BMap.Polyline([
                    point1,
                    point2,
                    point3,
                    point4
                ], {strokeColor: "blue", strokeWeight: 6, strokeOpacity: 0.5});
                var bounds = polyline.getBounds();
                center = bounds.getCenter();
            }

            //初始化地图
            var map = initMap(maxPoint, minPoint, center);
            //给地图添加地图切换工具
            addMapTool(map);

            for (var j = 0; j < result.length; j++) {
                if (result[j].logicGroupName == 'maxLngLatSite') {
                    result[j] = null;
                } else if (result[j].logicGroupName == 'minLngLatSite') {
                    result[j] = null;
                } else if (result[j].logicGroupName == 'maxLntPoint') {
                    result[j] = null;
                } else if (result[j].logicGroupName == 'minLntPoint') {
                    result[j] = null;
                } else if (result[j].logicGroupName == 'maxLatPoint') {
                    result[j] = null;
                } else if (result[j].logicGroupName == 'minLatPoint') {
                    result[j] = null;
                }
            }

            //加载站点数据
            loadSiteInfo(map, result);
        });

        $(document).on('click', '#go-back', function () {
            clickForGoBack();
            $('.siteName > li').hover(function () {
                $(this).css("background", "#9699A0");
            }, function () {
                $(this).css("background", "#fff");
            });
        });
    })();

    //初始化地图函数
    function initMap(maxPoint, minPoint, center) {

        var map = new BMap.Map("r-map", {mapType: BMAP_HYBRID_MAP});
        map.centerAndZoom(new BMap.Point(116.404, 39.915), 5);
        if (maxPoint != null && minPoint != null) {
            drivingSearch(map, center, maxPoint, minPoint);
        } else if (maxPoint == null && minPoint == null && center != null) {
            map.centerAndZoom(center, 11);
        }
        map.enableScrollWheelZoom();
        return map;
    }

    //添加地图切换工具函数
    function addMapTool(map) {
        $(".map_container_tools").mouseover(function () {
            $(".map_char").css("background-color", "#9ab5ff");
        });
        $(".map_container_tools").mouseout(function () {
            $(".map_char").css("background-color", "");
        });
        $(".map_container_tools").toggle(function () {
            $("#displaySatellite").removeClass("not_display");
            $("#displayNormalMap").addClass("not_display");
            map.setMapType(BMAP_NORMAL_MAP);
        }, function () {
            $("#displaySatellite").addClass("not_display");
            $("#displayNormalMap").removeClass("not_display");
            map.setMapType(BMAP_HYBRID_MAP);
        });
    }

    //加载站点数据
    function loadSiteInfo(map, result) {
        var logicGroupNames = [];
        logicGroupNames.push('<div id="sites" style="font-family: arial,sans-serif;font-size: 12px;">');
        logicGroupNames.push('<div style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">');
        logicGroupNames.push('<ol class="siteName" style="list-style: none outside none; padding: 0pt; margin: 0pt;">');
        openInfoWinFuns = [];
        for (var i = 0; i < result.length; i++) {
            if (result[i] != null) {
                var point = new BMap.Point(result[i].lngBaiDu, result[i].latBaiDu);
                var marker;
                var url;
                var logicGroupName;
                if (result[i].id == -1) {
                    var frindlySiteName = result[i].logicGroupName;
                    var siteNames = frindlySiteName.split(":");
                    url = siteNames[1] + ":" + siteNames[2];
                    logicGroupName = siteNames[0];
                    marker = addMarker(point, map, logicGroupName);

                }else{
                    logicGroupName = result[i].logicGroupName;
                    marker = addMarker(point, map, logicGroupName);
                }
                var openInfoWinFun = addInfoWindow(marker, result[i], i, map);
                var temperatureStability = result[i].temperatureStability == null ? '未知' : result[i].temperatureStability;
                var level = result[i].temperatureRank;
                var titleInfo;
                if (level == 'success') {
                    titleInfo = '好';
                } else if (level == 'warning') {
                    titleInfo = '中';
                } else if (level == 'danger') {
                    titleInfo = '差';
                } else{
                    titleInfo = '?'
                }
                openInfoWinFuns.push(openInfoWinFun);
                getBoundary(result[i].areaCodePO.areaName, map);
                var selected = "";
                logicGroupNames.push('<li id="list' + i + '" style="margin: 10px 0pt; padding: 0pt 5px 0pt 3px; cursor: pointer; overflow: hidden; line-height: 17px;' + selected + '" onclick="openInfoWinFuns[' + i + ']()">');
                logicGroupNames.push('<img src="images/green-24.png"/>');
                logicGroupNames.push('<span style="color:#666;"> - ' + logicGroupName + '</span>');
                logicGroupNames.push('<span id="showTooltip2' + i + '" data-placement="right" data-toggle="tooltip" title="室内温度稳定性:' + temperatureStability + '" style="width: 8px;height: 15px;margin-left: 7px;padding-left: 3px;padding-bottom: 3px;" class="btn btn-mini btn-' + level + '">' + titleInfo + '</span>');
                if(result[i].id == -1){
                    logicGroupNames.push('<span style="color:#666;" class="f-r m-l-10"><a href="#" onclick="return false;">配置</a></span>');
                    logicGroupNames.push('<span style="color:#666;" class="f-r"><a href="' + url + '">进入站点</a></span>');
                }else{
                    if ($("#security").val() === 'true') {
                        logicGroupNames.push('<span style="color:#666;" class="f-r m-l-10"><a href="customize/' + result[i].siteId + '">配置</a></span>');
                    }
                    logicGroupNames.push('<span style="color:#666;" class="f-r"><a href="../blackhole/doChooseLogicGroup.action?logicGroupId=' + result[i].id + '">进入站点</a></span>');
                }
                logicGroupNames.push('</li>');
                logicGroupNames.push('');
            }
        }
        logicGroupNames.push('</ol></div></div>');
        logicGroupNames.push('<div style="font-family: arial,sans-serif;font-size: 12px;">');//border: 1px solid rgb(153, 153, 153);
        logicGroupNames.push('<div id="infos" style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">');
        logicGroupNames.push('</div></div>');
        $('#l-sites').html(logicGroupNames.join(""));
        for (var j = 0; j < result.length; j++) {
            $('#showTooltip2' + j).tooltip('hide');
        }
    }


    //地图添加标记
    function addMarker(point, map, logicGroupName) {
        var myIcon = new BMap.Icon("images/green-32.png", new BMap.Size(200, 200), {
            offset: new BMap.Size(5, 50),
            imageOffset: new BMap.Size(85, 71)
        });
        var marker = new BMap.Marker(point, {icon: myIcon});
        var label = new BMap.Label(logicGroupName, {offset: new BMap.Size(65, 47)});
        marker.setLabel(label);
        map.addOverlay(marker);
        return marker;
    }

    //地图添加信息窗口
    function addInfoWindow(marker, poi, index, map) {
        if (poi.nodeSensorInfoVOList == null && poi.id != -1) {
            return;
        }
        if(poi.id == -1){
            poi.logicGroupName = poi.logicGroupName.split(":")[0];
        }
        //信息窗口标题
        var infoWindowTitle = '<div style="font-weight:bold;color:#CE5521;font-size:18px">' + poi.logicGroupName + '</div>';
        //信息窗口内容
        var infoWindowHtml = [];
        infoWindowHtml.push('<hr>');
        infoWindowHtml.push('<table cellspacing="0" style="table-layout:fixed;width:100%;font:12px arial,simsun,sans-serif"><tbody>');
        infoWindowHtml.push('<tr>');
        var temperatureStability = poi.temperatureStability == null ? '暂无数据' : poi.temperatureStability;
        var level = poi.temperatureRank;
        var titleInfo;
        if (level == 'success') {
            titleInfo = '好';
        } else if (level == 'warning') {
            titleInfo = '中';
        } else {
            titleInfo = '差';
        }
        infoWindowHtml.push('<td style="vertical-align:top;line-height:16px;font-weight: bold;font-size: 14px;">室内温度稳定性:');
        infoWindowHtml.push('<span id="showTooltip1" data-placement="top" data-toggle="tooltip" title="室内温度稳定性:' + temperatureStability + '" style="width: 8px;height: 15px;margin-left: 7px;padding-left: 3px;padding-bottom: 3px;" class="btn btn-mini btn-' + level + '">' + titleInfo);
        infoWindowHtml.push('</span></td></tr>');
        if(poi.id != -1){
            if (poi.nodeSensorInfoVOList.length > 0) {
                for (var i = 0; i < poi.nodeSensorInfoVOList.length; i++) {
                    var nodeSensorInfo = poi.nodeSensorInfoVOList[i];
                    infoWindowHtml.push('<tr>');
                    infoWindowHtml.push('<td style="vertical-align:top;line-height:16px"><i class="mw-icon-sensor sensor-' + nodeSensorInfo.sensorPhysicalid + '"></i>');
                    infoWindowHtml.push('<span style="margin-left:5px;font-weight: bold;font-size: 14px;">' + nodeSensorInfo.cnName + ':' + nodeSensorInfo.sensorPhysicalValue + nodeSensorInfo.unit + '</span>');
                    if (!$.trim(poi.customizeRemark)) {
                        infoWindowHtml.push('<span style="margin-left: 5px;color: dimgray;font-size: 8px;">' + nodeSensorInfo.customizeRemark + '</span>');
                    } else {
                        infoWindowHtml.push('<span id="deviceInfo' + i + '" data-placement="top" data-toggle="tooltip" class="m-l-10" style="color: dimgray;font-size: 8px;" title="设备:' + nodeSensorInfo.deviceName + '">' + nodeSensorInfo.zoneName + '</span>');
                    }
                    infoWindowHtml.push('</td></tr>');
                }
            }
        }
        infoWindowHtml.push('</tbody></table>');
        var infoWindow = new BMap.InfoWindow(infoWindowHtml.join(""), {title: infoWindowTitle, width: 300, enableMessage: false});
        var openInfoWinFun = function () {
            marker.openInfoWindow(infoWindow);
            map.setCenter(new BMap.Point(poi.lngBaiDu, poi.latBaiDu));
            $("#sites").css("display", "none");
            $("#infos").css("display", "");
            $.getJSON("mapOverview/getSiteOverview.json", {siteId: poi.siteId}, function (result) {
                var resultPanelHtml = [];
                resultPanelHtml.push('<div class="m-l-10 m-t-10" style="font-weight:bold;color:black;font-size:16px">');
                resultPanelHtml.push('站点平均监测指标<span id="go-back" class="btn btn-mini btn-primary" style="margin-right:10px;float:right;width:40px;text-align: right;"><<返回</span></div>');
                resultPanelHtml.push('<div class="m-l-10 m-t-10" style="font-weight:bold;color:#CE5521;font-size:12px">');
                resultPanelHtml.push(poi.logicGroupName + '</div>');
                if (result.length > 0) {
                    for (var i = 0; i < result.length; i++) {
                        resultPanelHtml.push('<div class="m-t-10">');
                        resultPanelHtml.push('<p class="m-l-20">');
                        resultPanelHtml.push('<span><i class="mw-icon-sensor sensor-' + result[i].sensorPhysicalid + '"></i></span><span class="m-l-10">');
                        resultPanelHtml.push(result[i].cnName + '：' + result[i].sensorPhysicalValue + result[i].unit);
                        resultPanelHtml.push('</span></p>');
                        resultPanelHtml.push('</div>');
                    }
                } else {
                    resultPanelHtml.push('<p>该站点暂无数据！</p>');
                }

                $('#infos').html(resultPanelHtml.join(""));
            });

            $('#showTooltip1').tooltip('hide');
            if(poi.id != -1){
                for (var j = 0; j < poi.nodeSensorInfoVOList.length; j++) {
                    $('#deviceInfo' + j).tooltip('hide');
                }
            }
        };
        marker.addEventListener("click", openInfoWinFun);
        return openInfoWinFun;
    }

    //点击返回，返回站点列表函数
    function clickForGoBack() {
        $("#sites").css("display", "");
        $("#infos").css("display", "none");
    }

    //驾车路线绘制服务
    function drivingSearch(map, center, maxPoint, minPoint) {
        var driving = new BMap.DrivingRoute(map, {
            renderOptions: {map: map}
        });
        driving.setPolylinesSetCallback(function () {
            driving.clearResults();
            if (center != null) {
                map.setCenter(center);
            }
        });
        driving.search(maxPoint, minPoint);
    }

    function getBoundary(place, map) {
        var bdary = new BMap.Boundary();
        bdary.get(place, function (rs) {       //获取行政区域
            var count = rs.boundaries.length; //行政区域的点有多少个
            for (var i = 0; i < count; i++) {
                var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000", fillColor: ""});
                map.addOverlay(ply);  //添加覆盖物
            }
        });
    }
});
</script>
</#macro>

