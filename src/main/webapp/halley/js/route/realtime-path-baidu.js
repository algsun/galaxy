$(function () {

    // 百度地图API功能
    var map, circle, polyline, carPoint, myIcon, marker;
    map = new BMap.Map("allmap");
    var point = new BMap.Point(108.953507, 34.265697);
//    map.centerAndZoom(point, 10);
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

    var exhibitionId = $("#exhibitionId").val();
    var path = [];
    $.get("exhibition/" + exhibitionId + "/getPathPoint.json", function (result) {
        if (result.length > 0) {
            for (var i = 0; i < result.length; i++) {
//                path.push(new AMap.LngLat(result[i].longitude, result[i].latitude));
            }
            var p1 = new BMap.Point(108.866006, 34.202646);
            var v = new BMap.Point(108.961254, 34.230639);

            var driving = new BMap.DrivingRoute(map, {renderOptions: {map: map, autoViewport: true}});
            driving.search(p1, p1, {waypoints: [v]});//waypoints表示途经点

        }
    });

    //因为实时数据上传的时候也会直接进入到历史数据表中，所以直接查询历史数据表作为实时路线的绘制
    var getPathData = function () {
        $.get("exhibition/" + exhibitionId + "/getHistoryPathPoint.json", function (result) {
            if (result != null) {
                var arr = new Array();//经纬度坐标数组
                for (var plateNum in result) {
                    //实时历史数据绘制坐标点
                    //实时路线绘制坐标点
                    var latLongs = result[plateNum];
                    for (var i = 0; i < latLongs.length; i++) {
//                        arr.push(new AMap.LngLat(latLongs[i].longitude, latLongs[i].latitude));
                    }
                }

                if (polyline != null) {
                    map.removeOverlay(polyline);
                }
                polyline = new BMap.Polyline([
                    new BMap.Point(108.865806, 34.202619),
                    new BMap.Point(108.869938, 34.20459),
                    new BMap.Point(108.882047, 34.204321),
                    new BMap.Point(108.902313, 34.204172),
                    new BMap.Point(108.914242, 34.217695)
                ], {strokeColor: "red", strokeWeight: 6, strokeOpacity: 1});

                //创建折线
                map.addOverlay(polyline);

                carPoint = new BMap.Point(108.914242, 34.217695);
                if (circle == null) {
                    circle = new BMap.Circle(carPoint, 1000, {
                        strokeColor: "red",
                        fillColor: "#787878",
                        strokeWeight: 2,
                        strokeOpacity: 1
                    }); //创建圆
                } else {
                    circle.setCenter(carPoint);
                }

                map.addOverlay(circle);//增加圆

                //添加覆盖物
                if (myIcon == null) {
                    myIcon = new BMap.Icon("images/car_1.png", new BMap.Size(23, 23));
                }

                if (marker == null) {
                    marker = new BMap.Marker(carPoint, {icon: myIcon});  // 创建标注
                } else {
                    marker.setPosition(carPoint);
                }

                map.addOverlay(marker);

//                var pointArr = [carPoint];
//                map.setViewport(carPoint);
//                search();
            }
        });
    };

    function getAddres(plateNumber) {
        var myGeo = new BMap.Geocoder();
        var carPointRealTime = new BMap.Point(108.914242, 34.217695);
        myGeo.getLocation(carPointRealTime, function (rs) {
            var addComp = rs.addressComponents;
            var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
            var addressId = "#address_" + plateNumber;
            $(addressId)[0].innerHTML = address;
            $(addressId).css({"backgroundColor":"#90ee90"});
//            $(addressId).css({"backgroundColor":"90ee90"});
            $(addressId).animate({backgroundColor: "#90ee90"}, 1000)
                .animate({backgroundColor: "transparent"}, 3000, function () {
                    $(addressId).removeAttr('style');
                });
        });
    }

    var getCarData = function () {
        $.get("exhibition/" + exhibitionId + "/getCarInfoData.json", function (data) {
            for (var index in data) {
                var plateNumber = data[index].plateNumber;
                var sensorMap = data[index].sensorMap;
//                var longitude = sensorMap.longitude;
//                var latitude = sensorMap.latitude;

//                if (typeof(longitude) != "undefined" && typeof(latitude) != "undefined") {
                    getAddres(data[index].plateNumber);
//                }

                var shake = sensorMap.shake;
                if (typeof(shake) != "undefined") {
                    var shakeId = "#shake_" + plateNumber;
                    $(shakeId).text("震动：" + shake + "")
                }
                var accl = sensorMap.accl;
                if (typeof(accl) != "undefined") {
                    var acclId = "#accl_" + plateNumber;
                    $(acclId).text("加速度：" + accl + "");
                }

                var swh = sensorMap.swh;
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
    setInterval(refreshData, 5000);


});
