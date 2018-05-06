<#--
外展管理，装箱单

@author li.jianfei
@date 2013-09-27
-->
<#assign title="线路预设 - 外展管理">
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
<style type="text/css">
    /* 解决 google map  https://mts0.googleapis.com/vt load fail 问题 */
    #mapCanvas {
        width: 100%;
        height: 100%;
    }

    /* 解决 bootstrap css 与google map 冲突问题*/
    #mapCanvas img {
        max-width: none;
    }
</style>
</#macro>


<#macro content>
<div class="row">
    <div class="span12">
        <fieldset>
            <legend>
                <a class="go-back" href="queryExhibitionStateList/exhibition/${exhibitionId}" title="返回">
                    <i class="mw-icon-prev"></i>线路预设
                </a>
            </legend>
        </fieldset>
    </div>
</div>

<div class="row-fluid">

    <div class="span3">
        <div class="row-fluid">
            <div class="span12">
                <div class="control-group">
                    <input id="editable" type="hidden" value="${editable?string('true','false')}"/>
                    <input id="exhibitionId" type="hidden" value="${exhibitionId}"/>
                    <input id="path" type="hidden" value='${path!}'>
                    <input id="point" type="hidden" value='${point!}'>
                    <input id="Lat" type="hidden" value="${Session["currentLogicGroup"].site.latBaiDu}"/>
                    <input id="Lng" type="hidden" value="${Session["currentLogicGroup"].site.lngBaiDu}"/>

                    <input type="text" class="span10" disabled value='${Session["currentLogicGroup"].site.siteName}'/>
                </div>

                <div id="destinations">
                    <#if (pathList?size>0)>
                        <#list pathList as path>
                        <#if path.dataType==1>
                            <@destinationInput path_index, path.destinationName/>
                        </#if>
                    </#list>
                    <#else>
                        <@destinationInput/>
                    </#if>
                </div>
                <div class="control-group">
                    <input type="text" class="span10" disabled value='${Session["currentLogicGroup"].site.siteName}'/>
                </div>

                <div class="span10 control-group">
                    <a id="addDestination" class="text-info">
                        <small>添加更多目的地</small>
                    </a>
                    <button id="btnQuery" class="btn btn-mini pull-right" style="margin-right: 25px;">查询线路
                    </button>
                </div>
                <div class="span10 control-group">
                    <button id="btnSave" class="btn btn-mini btn-success pull-right" style="margin-right: 25px;">保存线路
                    </button>
                </div>
            </div>
        </div>
        <div class="row-fluid">
            <div id="total" class="span12"></div>
        </div>
    </div>
    <div class="span9">
        <div id="mapCanvas"></div>
    </div>

</div>

<div id="destinationLayout" class="hide">
    <div id="destination" class="control-group">
        <input name="destination" type="text" class="span10" placeholder="请输入目的地"/><span
            class="span2 pull-right"
            id="deleteDestination"
            style="cursor: pointer;"><i
            class="icon-remove"></i></span>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=OydHGQX0xSRR97mmIG813SsZ"></script>
<script type="text/javascript">
    $(function () {

        var exhibitionId = $("#exhibitionId").val();
//        var sitePosition = new BMap.Point($("#Lng").val(), $("#Lat").val());
        var sitePosition = new BMap.Point(108.866424, 34.203318);
        // 百度地图API功能
        var map = new BMap.Map("mapCanvas");
        map.centerAndZoom(sitePosition, 12);
        map.enableScrollWheelZoom(true);
        var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
        var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
        map.addControl(top_left_control);
        map.addControl(top_left_navigation);
        var marker = new BMap.Marker(sitePosition);  // 创建标注
        map.addOverlay(marker);              // 将标注添加到地图中

        // 解决 google map 与 bootstrap 冲突问题
        $(window).resize(function () {
            var h = $(window).height(),
                    offsetTop = 200; // Calculate the top offset
            $('#mapCanvas').css('height', (h - offsetTop));
        }).resize();
        updateAutoCompleteInput();

        // 为目的地输入框添加自动提示
        function updateAutoCompleteInput() {

            $.each($("input[name='destination']"), function (i, input) {
                var $input = $(input);
                //建立一个自动完成的对象
                var autoComplete = new BMap.Autocomplete({
                    "input": $input[0].id,
                    "location": map
                });

                autoComplete.addEventListener("onconfirm", function (e) {    //鼠标点击下拉列表后的事件
                    map.clearOverlays();    //清除地图上所有覆盖物

                    var local = new BMap.LocalSearch(map, { //智能搜索
                        onSearchComplete: function () {
                            var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
                            map.centerAndZoom(pp, 12);
                            map.addOverlay(new BMap.Marker(pp));    //添加标注
                        }
                    });
                    local.search($input.val());
                });
            });
        }

        // 禁用保存按钮，未选择线路时不能直接保存
        $("#btnSave").attr("disabled", "disabled");
        // 处理控件可编辑状态
        function loadEditableState() {
            var editable = $("#editable").val() === 'true';

            // 停用或启用输入框
            $.each($("input[name='destination']"), function (i, input) {
                var $input = $(input);
                if (editable) {
                    $input.removeAttr("disabled");
                } else {
                    $input.attr("disabled", "disabled");
                }
            });

            if (!editable) {
                $("#addDestination").hide();
                $("#btnQuery").hide();
                $("#btnSave").hide();
            } else {
                $("#addDestination").show();
                $("#btnQuery").show();
                $("#btnSave").show();
            }
        }

        loadEditableState();

        // 加载预设线路
        function loadPath() {
            var location;
            var waypoints = [];

            // 是否存在预设线路
            var destinations = JSON.parse($("#path").val());
            if (destinations.length != 0) {
                var drivingRoute = new BMap.DrivingRoute(map, {
                    renderOptions: {
                        map: map,
                        enableDragging: true //起终点可进行拖拽
                    },
                });

                $.each(destinations, function (i, destination) {
                    location = new BMap.Point(destination.longitude, destination.latitude);
                    waypoints.push(location);
                });

                drivingRoute.setSearchCompleteCallback(searchComplete);
                drivingRoute.search(sitePosition, sitePosition, {
                    "waypoints": waypoints
                });
            }
        }

        loadPath();

        function searchComplete(result) {
        }


        addOrRemoveDelete();

        // 添加目的地
        $("#addDestination").click(function () {
            var $cloneObj = $("#destinationLayout").children().clone();
            $cloneObj.find("input[name='destination']")[0].id = "keyword" + $("#destinations").children().length;
            $("#destinations").append($cloneObj);
            updateAutoCompleteInput();
            addOrRemoveDelete();
        });

        // 删除目的地
        $(document).on("click", "span[id='deleteDestination']", function () {
            var $this = $(this);
            $this.parent().remove();
            // 添加或隐藏目的地输入框后的删除按钮
            addOrRemoveDelete();
        });

        // 添加或隐藏目的地输入框后的删除按钮
        function addOrRemoveDelete() {
            if ($("#destinations").children().length > 1) {
                if ($("#destination").first().children().length === 1)
                    $("input[name='destination']").first().after($("#destinationLayout").find("#deleteDestination").clone());
            } else {
                $("#deleteDestination").first().remove()
            }
        }

        // 保存预设线路
        $("#btnSave").click(function () {
            var exhibitionId = $("#exhibitionId").val();
            var destinations = $("#path").val();
            var gpsPoints = $("#point").val();
            $.ajaxSetup({traditional: true});
            $.post("routePlanning/exhibition/" + exhibitionId + "/save", {
                path: destinations,
                point: gpsPoints
            }, function (result) {
                if (result.success) {
                    window.location.href = "queryExhibitionStateList/exhibition/" + exhibitionId;
                }
            });
        });

        // 更新 wayPoints
        $("#btnQuery").click(function () {
            var $destinations = $("input[name='destination']:visible")
            var waypoints = [];

            map.clearOverlays();
            var drivingRoute = new BMap.DrivingRoute(map, {
                renderOptions: {
                    map: map,
                    enableDragging: true //起终点可进行拖拽
                },
            });
            $.each($destinations, function (i, destination) {
                var $destination = $(destination);
                if ($.trim(destination.value) === "") {
                    $destination.parent().addClass("error");
                    return;
                } else {
                    $destination.parent().removeClass("error");
                    waypoints.push($destination.val());
                }
            });

            drivingRoute.setSearchCompleteCallback(searchComplete);
            drivingRoute.search(sitePosition, sitePosition, {
                "waypoints": waypoints
            });
        });
    });

    //$(function () {
    //    function initialize() {
    //
    //        // 创建地图对象
    //        var mapOptions = {
    //            zoom: 8,
    //            center: currentSiteLocation,
    //            mapTypeId: google.maps.MapTypeId.ROADMAP
    //        };
    //        map = new google.maps.Map(document.getElementById("mapCanvas"), mapOptions);
    //
    //        // 为目的地输入框添加自动提示功能
    //        updateAutoCompleteInput();
    //
    //        // 添加当前站点标记
    //        var marker = new google.maps.Marker({
    //            position: currentSiteLocation,
    //            map: map
    //        });
    //        directionsDisplay.setMap(map);
    //
    //        google.maps.event.addListener(directionsDisplay, 'directions_changed', function () {
    //            computeTotalDistance(directionsDisplay.directions);
    //            var exhibitionId = $("#exhibitionId").val();
    //            var destination;
    //            var destinations = [];
    //            var gpsPoint;
    //            var gpsPoints = [];
    //
    //            // 保存坐标点
    //            $.each(directionsDisplay.directions.routes[0].legs, function (i, leg) {
    //
    //                if (leg.via_waypoints) {
    //                    var viaWayPoint;
    //                    $.each(leg.via_waypoints, function (i, waypoint) {
    //                        viaWayPoint = {
    //                            exhibitionId: exhibitionId,
    //                            latitude: waypoint.lat(),
    //                            longitude: waypoint.lng(),
    //                            dataType: 0,
    //                            destinationName: ""
    //                        };
    //                        destinations.push(viaWayPoint);
    //                    });
    //                }
    //
    //                var endLocation = {
    //                    exhibitionId: exhibitionId,
    //                    latitude: leg.end_location.lat(),
    //                    longitude: leg.end_location.lng(),
    //                    dataType: 1,
    //                    destinationName: ""
    //                };
    //                destinations.push(endLocation);
    //
    //                if (i === directionsDisplay.directions.routes[0].legs.length - 1) {
    //                    destinations.pop();
    //                }
    //            });
    //
    //            //保存gps路径点
    //            $.each(directionsDisplay.directions.routes, function (i, route) {
    //                $.each(route.overview_path, function (j, point) {
    //                    gpsPoint = {exhibitionId: exhibitionId, latitude: point.lat(), longitude: point.lng()};
    //                    gpsPoints.push(gpsPoint);
    //                })
    //            });
    //            // 匹配位置信息
    //            var $destinations = $("input[name='destination']:visible");
    //            $.each($destinations, function (i, destinationDiv) {
    //                var $destination = $(destinationDiv);
    //                for (var j = 0; j < destinations.length; j++) {
    //                    if (destinations[i].dataType === 1) {
    //                        destinations[i].destinationName = $destination.val();
    //                    } else {
    //                        j = j - 1;
    //                    }
    //                }
    //            });
    //
    //            $("#path").val(JSON.stringify(destinations));
    //            $("#point").val(JSON.stringify(gpsPoints));
    //        });
    //    }
    //
    //// 为目的地输入框添加自动提示
    //    function updateAutoCompleteInput() {
    //
    //        $.each($("input[name='destination']"), function (i, input) {
    //            var autocomplete = new google.maps.places.Autocomplete(input);
    //            autocomplete.bindTo('bounds', map);
    //            autocomplete.setTypes([]);
    //
    //            google.maps.event.addListener(autocomplete, 'place_changed', function () {
    //                var place = autocomplete.getPlace();
    //                if (!place.geometry) {
    //                    // 无法匹配就返回
    //                    return;
    //                }
    //
    //                // If the place has a geometry, then present it on a map.
    //                if (place.geometry.viewport) {
    //                    map.fitBounds(place.geometry.viewport);
    //                }
    //
    //                var address = '';
    //                if (place.address_components) {
    //                    address = [
    //                        (place.address_components[0] && place.address_components[0].short_name || ''),
    //                        (place.address_components[1] && place.address_components[1].short_name || ''),
    //                        (place.address_components[2] && place.address_components[2].short_name || '')
    //                    ].join(' ');
    //                }
    //            });
    //        });
    //    }
    //
    //
    //    function calcRoute(waypoints) {
    //
    //        var request = {
    //            origin: currentSiteLocation,
    //            destination: currentSiteLocation,
    //            waypoints: waypoints,
    //            travelMode: google.maps.DirectionsTravelMode.DRIVING
    //        };
    //        directionsService.route(request, function (response, status) {
    //            if (status == google.maps.DirectionsStatus.OK) {
    //                // 线路变化后保存线路可用
    //                $("#btnSave").removeAttr("disabled");
    //                directionsDisplay.setDirections(response);
    //            } else {
    //                alert("未找到线路");
    //            }
    //        });
    //    }
    //
    //    function computeTotalDistance(result) {
    //        var total = 0;
    //        var myroute = result.routes[0];
    //        for (var i = 0; i < myroute.legs.length; i++) {
    //            total += myroute.legs[i].distance.value;
    //        }
    //        total = total / 1000;
    //        document.getElementById('total').innerHTML = "全程" + total + ' km';
    //    }
    //
    //// 添加或隐藏目的地输入框后的删除按钮
    //    function addOrRemoveDelete() {
    //        if ($("#destinations").children().length > 1) {
    //            if ($("#destination").first().children().length === 1)
    //                $("input[name='destination']").first().after($("#destinationLayout").find("#deleteDestination").clone());
    //        } else {
    //            $("#deleteDestination").first().remove()
    //        }
    //    }
    //
    //    google.maps.event.addDomListener(window, 'load', initialize);
    //})
    //;


</script>
</#macro>
<#macro destinationInput index=0, destinationName=''>
<div id="destination" class="control-group">
    <input id="keyword${index}" name="destination" type="text" class="span10" placeholder="请输入目的地"
           value='${destinationName}'/><span
        class="span2 pull-right"
        id="deleteDestination"
        style="cursor: pointer;"><i
        class="icon-remove"></i></span>
</div>
</#macro>