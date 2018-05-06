/**
 *
 * 历史线路
 *
 * @author: liu.zhu
 * @time: 2015-7-6
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

                var lat = $("#Lat").val();
                var lng = $("#Lng").val();
                if (marker == null) {
                    marker = new AMap.Marker({
                        map: map,
                        position: arr[0],
                        offset: new AMap.Pixel(-13, -16),
                        icon: "images/car_1.png"
                    });
                } else {
                    var position = marker.getPosition();
                    position.lng = arr[0].lng;
                    position.lat = arr[0].lat;
                    position.t = position.lng;
                    position.A = position.lat;
                    marker.setPosition(position);
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
                    var center = circle.getCenter();
                    center.lng = arr[0].lng;
                    center.lat = arr[0].lat;
                    center.t = center.lng;
                    center.A = center.lat;
                    circle.setCenter(center);
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

    var refreshData = function () {
        getPathData();
    };
    refreshData();
});