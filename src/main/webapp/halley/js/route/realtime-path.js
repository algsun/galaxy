/**
 *
 * 实时路线
 *
 * @author: liuzhu
 * @time: 2015-7-10
 */
$(function () {
    var map, route, marker, circle, circleEditor, polyLine;

    //基本地图加载
    map = new AMap.Map("mapContainer", {
        resizeEnable: true
    });
    var exhibitionId = $("#exhibitionId").val();
    var alarmRange = $("#alarmRange").val();

    var path = [];
    $.get("exhibition/" + exhibitionId + "/getPathPoint.json", function (result) {
        if (result.length > 0) {
            for (var i = 0; i < result.length; i++) {
                path.push(new AMap.LngLat(result[i].longitude, result[i].latitude));
            }
            map.plugin("AMap.DragRoute", function () {
                var pathType = $("#pathType").val();
                if (pathType == 0) {
                    route = new AMap.DragRoute(map, path, AMap.DrivingPolicy.LEAST_TIME); //构造拖拽导航类
                } else if (pathType == 1) {
                    route = new AMap.DragRoute(map, path, AMap.DrivingPolicy.LEAST_FEE); //构造拖拽导航类
                } else if (pathType == 2) {
                    route = new AMap.DragRoute(map, path, AMap.DrivingPolicy.LEAST_DISTANCE); //构造拖拽导航类
                }
                route.search(); //查询导航路径并开启拖拽导航
            });
        }
    });


    //因为实时数据上传的时候也会直接进入到历史数据表中，所以直接查询历史数据表作为实时路线的绘制
    var getPathData = function () {
        $.get("exhibition/" + exhibitionId + "/getHistoryPathPoint.json", function (result) {
            if(!result){
               return;
            }else{
                var arr = new Array();//经纬度坐标数组
                for (var plateNum in result) {
                    //实时历史数据绘制坐标点
                    //实时路线绘制坐标点
                    var latLongs = result[plateNum];
                    for (var i = 0; i < latLongs.length; i++) {
                        arr.push(new AMap.LngLat(latLongs[i].longitude, latLongs[i].latitude));
                    }
                }
                if (polyLine != null) {
                    polyLine.setMap(null);
                }
                polyLine = new AMap.Polyline({
                    map: map,
                    path: arr,
                    strokeColor: "red",
                    strokeOpacity: 1,
                    strokeWeight: 2
                });

                if (marker == null) {
                    //添加点标记，并使用自己的icon
                    marker = new AMap.Marker({
                        position: arr[0],
                        draggable: true,
                        cursor: 'move'
                    });
                    marker.setMap(map);
                    // 设置点标记的动画效果，此处为弹跳效果
                    marker.setAnimation('AMAP_ANIMATION_BOUNCE');

                } else {
//                    var lnglat = AMap.LngLat(arr[0].lng,arr[0].lat);
//                    var position = marker.getPosition();
//                    position.lng = arr[0].lng;
//                    position.lat = arr[0].lat;
//                    position.t = position.lng;
//                    position.A = position.lat;
                    marker.setPosition(arr[0]);
                }

                if (circle == null) {
                    if(alarmRange == null || alarmRange == ""){
                        alarmRange = 1;
                    }
                    circle = new AMap.Circle({
                        map: map,
                        center: arr[0],
                        radius: alarmRange * 1000,
                        strokeColor: "#F33",
                        strokeOpacity: 1,
                        strokeWeight: 3,
                        fillColor: "ee2200",
                        fillOpacity: 0.35
                    });
                } else {
//                    var center = circle.getCenter();
//                    center.lng = arr[0].lng;
//                    center.lat = arr[0].lat;
//                    center.t = center.lng;
//                    center.A = center.lat;
//                    marker.setPosition(arr[0]);
                    circle.setCenter(arr[0]);
                }

                map.plugin(["AMap.CircleEditor"], function () {
                    circleEditor = new AMap.CircleEditor(map, circle);
//                circleEditor.open();
                });

//            调整视野到合适的位置及级别
                map.setFitView();
            }
        });
    };

    var getCarData = function () {
        $.get("exhibition/" + exhibitionId + "/getCarInfoData.json", function (data) {
            for (var index in data) {
                var plateNumber = data[index].plateNumber;
                var alarmRange = data[index].alarmRange;
                var sensorMap = data[index].sensorMap;
                var longitude = sensorMap.longitude;
                var latitude = sensorMap.latitude;

                if (typeof(longitude) != "undefined" && typeof(latitude) != "undefined") {
                    getAddres(longitude, latitude, plateNumber);
                }

                var shake = sensorMap.SHAKE;
                if (typeof(shake) != "undefined") {
                    var shakeId = "#shake_" + plateNumber;
                    $(shakeId).text("震动：" + shake + "")
                }
                var accl = sensorMap.ACCL;
                if (typeof(accl) != "undefined") {
                    var acclId = "#accl_" + plateNumber;
                    $(acclId).text("加速度：" + accl + "");
                }

                var swh = sensorMap.SWH;
                if (typeof(swh) != "undefined") {
                    var swhId = "#swh_" + plateNumber;
                    $(swhId).text("车门：" + (swh == 1 ? "打开" : "关闭"))
                    if (swh == 1) {
                        $(swhId).css("color", "red");
                    }
                }
            }
        });
    };


    var refreshData = function () {
        getPathData();
        getCarData();
    };
    refreshData();

    //定时发送Ajax请求，查询最新数据
    setInterval(refreshData, 10000);

    function getAddres(longitude, latitude, plateNumber) {
        var lnglatXY = new AMap.LngLat(longitude, latitude);
        //加载地理编码插件
        map.plugin(["AMap.Geocoder"], function () {
            MGeocoder = new AMap.Geocoder({
                radius: 1000,
                extensions: "all"
            });
            //返回地理编码结果
            AMap.event.addListener(MGeocoder, "complete", function (data) {
                var address = data.regeocode.formattedAddress;
                var addressId = "#address_" + plateNumber;
                $(addressId)[0].innerHTML = address;

//                $(addressId).css({"backgroundColor": "#90ee90"});
                $(addressId).animate({backgroundColor: "#90ee90"}, 1000)
                    .animate({backgroundColor: "transparent"}, 3000, function () {
                        $(addressId).removeAttr('style');
                    });
            });
            //逆地理编码
            MGeocoder.getAddress(lnglatXY);
        });
    }

    function geocoder_CallBack2(data) { //回调函数
        var address;
        //返回地址描述
        address = data.regeocode.formattedAddress;
        //返回结果拼接输出

    }

});