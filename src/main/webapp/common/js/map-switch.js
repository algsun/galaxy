/**
 *<pre>
 * 站点地图切换
 *</pre>
 * @author: Wang yunlong
 * @time: 13-4-7 上午10:47
 */


var SWITCH_MAP = {
    mp: new BMap.Map("logicGroupMap", {minZoom: 5, maxZoom: 16}),
    $logicGroupList: $("#logicGroupList"),
    $logicGroupMap: $("#map-and-list"),
    switchMap: function (sys, targetSubsystem) {
        var SWITCH_MAP = this;
        var path = (sys == 'blackhole') ? '' : '../blackhole/';
        //站点区域
        var areaNames = [];
        //中心点坐标
        var centerLat = 0;
        var centerLng = 0;
        //最大最小坐标
        var maxLat = 0;
        var minLat = 0;
        var maxLng = 0;
        var minLng = 0;
        var areas = [];
        $.getJSON(path + "availableAllBasicLogicGroup.action?_=" + new Date().getTime(), function (result) {
            SWITCH_MAP.$logicGroupList.find("ul").empty();
            for (var i = 0, logicgroup; i < result.length; i++) {
                logicgroup = result[i]
                if (logicgroup.site) {
                    var marker = SWITCH_MAP.createMarker(logicgroup.site, logicgroup.id, targetSubsystem);
                    SWITCH_MAP.mp.addOverlay(marker);
                    SWITCH_MAP.$logicGroupList.find("ul").append("<li class='m-b-5' style='list-style:none;padding-left:20px;'><a href='" + SWITCH_MAP.createSwitchUrl(logicgroup.id, targetSubsystem) + "'>" + logicgroup.site.siteName + "</a></li>")
                }
                //获取站点区域名
                if (i == result.length - 3) {
                    areaNames.push(logicgroup.areaNames);
                }

                //获取最大最小坐标
                if (i == result.length - 2) {
                    maxLat = logicgroup.maxLat;
                    minLat = logicgroup.minLat;
                    maxLng = logicgroup.maxLng;
                    minLng = logicgroup.minLng;
                }

                //获取中心点坐标
                if (i == result.length - 1) {
                    centerLat = logicgroup.centerLat;
                    centerLng = logicgroup.centerLng;
                }
            }
            areas = areaNames[0];
            //站点所在区域画边界，多区域时调整地图视野
            for (var i = 0; i < areas.length; i++) {
                getBoundary(areas, areas[i], centerLat, centerLng, maxLat, minLat, maxLng, minLng);
            }

        });

        SWITCH_MAP.mp.enableScrollWheelZoom();
    },
    logicGroupMap: function (sys, targetSubsystem) {
        var targetSubsys = targetSubsystem || sys;
        var SWITCH_MAP = this;
        SWITCH_MAP.switchMap(sys, targetSubsys);
        art.dialog({
            id: "map-and-list",
            title: '站点选择',
            content: SWITCH_MAP.$logicGroupMap[0]
        });
    },
    createMarker: function (site, logicGroupId, sys) {
        var SWITCH_MAP = this;
        var siteName = site.siteName;
        var myCompOverlay = new ComplexCustomOverlay(SWITCH_MAP.mp, new BMap.Point(site.lngBaiDu, site.latBaiDu), siteName, logicGroupId, sys);
        return myCompOverlay;
    },
    createSwitchUrl: function (id, sys) {
        var redirectUrl = function (subsystem, logicgroupId) {
            return SWITCH_MAP.basedUrl("../blackhole/switchLogicGroup.action?forward=/" + subsystem + "/&logicGroupId=" + logicgroupId);
        };
        if (sys == 'blackhole') {
            return SWITCH_MAP.basedUrl("switchLogicGroup.action?forward=/blackhole/&logicGroupId=" + id);
        } else {
            return redirectUrl(sys, id);
        }
    },
    basedUrl: function (url) {
        var basePath = $('base').attr('href');
        if (basePath) {
            url = basePath + url;
        }
        return url;
    }
};

function ComplexCustomOverlay(mp, point, text, changeId, sys) {
    this._point = point;
    this._text = text;
    this._changeId = changeId;
    this._sys = sys;
    this._mp = mp;
};

function getBoundary(areaNames, areaName, centerLat, centerLng, maxLat, minLat, maxLng, minLng) {
    //经纬度差
    var lat = maxLat - minLat;
    var lng = maxLng - minLng;

    var bdary = new BMap.Boundary();
    bdary.get(areaName, function (rs) {       //获取行政区域
        var count = rs.boundaries.length; //行政区域的点有多少个
        for (var i = 0; i < count; i++) {
            var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000", fillColor: ""}); //建立多边形覆盖物
            SWITCH_MAP.mp.addOverlay(ply);  //添加覆盖物
        }

        //调整视野
        if (areaNames.length == 1) {
            SWITCH_MAP.mp.centerAndZoom(areaNames[0], 10);
        } else {
            if (lat < 13 && lng < 13) {
                SWITCH_MAP.mp.centerAndZoom(new BMap.Point(centerLng, centerLat), 5);
            } else if (lat < 19 && lng < 19) {
                SWITCH_MAP.mp.centerAndZoom(new BMap.Point(centerLng, centerLat), 4);
            } else {
                SWITCH_MAP.mp.centerAndZoom("中国");
            }
        }
    });
};
ComplexCustomOverlay.prototype = new BMap.Overlay();
ComplexCustomOverlay.prototype.initialize = function (map) {
    this._map = map;
    var div = this._div = document.createElement("div");
    div.style.position = "absolute";
    div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
    div.style.backgroundColor = "#EE5D5B";
    div.style.border = "1px solid #BC3B3A";
    div.style.color = "white";
    div.style.height = "18px";
    div.style.padding = "2px";
    div.style.lineHeight = "18px";
    div.style.whiteSpace = "nowrap";
    div.style.MozUserSelect = "none";
    div.style.fontSize = "12px"
    var span = this._span = document.createElement("span");
    div.appendChild(span);
    span.appendChild(document.createTextNode(this._text));
    var that = this;

    var arrow = this._arrow = document.createElement("div");
    arrow.style.position = "absolute";
    arrow.style.width = "11px";
    arrow.style.height = "10px";
    arrow.style.top = "22px";
    arrow.style.left = "10px";
    arrow.style.overflow = "hidden";
    div.appendChild(arrow);

    div.onclick = function () {
        window.location.href = SWITCH_MAP.createSwitchUrl(that._changeId, that._sys);
    };
    div.onmouseover = function () {
        this.style.backgroundColor = "#6BADCA";
        arrow.style.backgroundPosition = "0px -20px";
    };

    div.onmouseout = function () {
        this.style.backgroundColor = "#EE5D5B";
        arrow.style.backgroundPosition = "0px 0px";
    };
    that._mp.getPanes().labelPane.appendChild(div);
    return div;
};
ComplexCustomOverlay.prototype.draw = function () {
    var map = this._map;
    var pixel = map.pointToOverlayPixel(this._point);
    this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
    this._div.style.top = pixel.y - 30 + "px";
};


