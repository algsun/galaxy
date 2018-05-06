/**
 *  平面地图tooltip
 *
 * @author: Wang yunlong
 * @time: 13-4-17 下午2:11
 */
var TOOLTIP = {
    mouseFocus: false,
    dead: false,
    timer: {},
    //展示全部名称
    initToggleName: function () {
        $("#toggle-name").change(function () {
            if ($(this).is(':checked')) {
                EleOfPlanImage.showAllName();
            } else {
                EleOfPlanImage.hideAllName();
            }
        });
    },
    //鼠标移到设备上展示设备实时数据信息
    deviceInfoView: function (device) {
        var $deviceInfo = $(".device-info");
        var $masterModule = $(".master-module");
        device.addListenerEvent("mouseenter", function (that, e) {
            TOOLTIP.mouseFocus = true;
            //中继或者网关
            if (device.type == 2 || device.type == 7) {
                SERVICE.getDeviceInfo(that.id, function (data) {
                    $deviceInfo.find(".realtime-data-list").parent().hide();
                    TOOLTIP.baseDeviceInfo(data, $deviceInfo);
                    that.refreshState(data.lowvoltage, data.anomaly);
                    TOOLTIP.commonDeviceTooltipVisualization($deviceInfo, e);
                });
            } else if (device.type == 1) {
                //节点
                SERVICE.getDeviceRealTime(that.id, function (result) {
                    TOOLTIP.renderRealtimeData($deviceInfo, result);
                    var data = result[0];
                    that.refreshState(data.lowvoltage, data.anomaly);
                    TOOLTIP.commonDeviceTooltipVisualization($deviceInfo, e);
                });
            } else if (device.type == 3) {
                // 主模块
                SERVICE.getSlaveInfo(that.id, function (result) {
                    var deviceName = that.name == null ? '(暂无)' : that.name;
                    $masterModule.find(".device-cn-name").text(deviceName);
                    $masterModule.find('.device-id').text(that.id);
                    var slave = '';
                    $.each(result, function(i, data){
                        var name = data.name == null ? data.id : data.name;
                        slave += "<div class='child-module' node-id='" + data.id + "'>" + name + "</div>";
                    });
                    $masterModule.find(".child").empty().append(slave);
                    var x = e.pageX - EleOfPlanImage.lockLeftForOther + EleOfPlanImage.iconWidth / 2;
                    var y = e.pageY - EleOfPlanImage.lockTopForOther + EleOfPlanImage.iconHeight / 2;
                    // 让tooltip展示在页面可视区, 自动调整 tooltip 的位置
                    if (x + $masterModule.width() + $deviceInfo.width() > $(document).width() - EleOfPlanImage.lockLeftForOther) {
                        x = x - $masterModule.width() - EleOfPlanImage.iconWidth;
                        $masterModule.attr("float", "left"); // 状态，不是样式
                    } else {
                        $masterModule.attr("float", "right");
                    }
                    if (y + $masterModule.height() > $(document).height() - EleOfPlanImage.lockTopForOther) {
                        y = y - $masterModule.height() - EleOfPlanImage.iconHeight;
                    }
                    if (!TOOLTIP.dead) {
                        $masterModule.css({left: x, top: y}).show();
                    }
                });
            }
        });
        TOOLTIP.initTooltipViewStrategy(device);
    },
    renderRealtimeData: function($deviceInfo, result){
        var data = result[0];
        //实时数据
        var realtimeData = '';
        var sensorinfoMap = data.sensorinfoMap;
        for (var sensorKey in sensorinfoMap) {
            var sensor = sensorinfoMap[sensorKey];
            realtimeData += "<div class='sensor-real-data'>"
                + "<span class='sensor-name'>" + sensor.cnName + "：" + "</span>"
                + "<span class='sensor-value'>" + sensor.sensorPhysicalValue + sensor.units + "</span>"
                + "</div>";
        }
        $deviceInfo.find(".realtime-data-list").empty().append(realtimeData);
        $deviceInfo.find(".realtime-data-list").parent().show();
        TOOLTIP.baseDeviceInfo(data, $deviceInfo);
    },
    baseDeviceInfo: function(data, $deviceInfo){
        //设备名称
        var deviceName = (data.nodeName == null ? '(暂无)' : data.nodeName);
        $deviceInfo.find(".device-cn-name").text(deviceName);
        //设备id
        $deviceInfo.find(".device-id").text(data.nodeId);
        //状态时间
        var stamp = moment(data.stamp,'YYYY-MM-DDTHH:mm:ss');
        $deviceInfo.find(".device-state-time").text(stamp.format("YYYY-MM-DD HH:mm"));

        //设备状态
        $deviceInfo.find(".device-state").html(REALTIME_DATA_UTIL.deviceStateToString(data.lowvoltage, data.anomaly));
        //信号
        $deviceInfo.find(".device-signal").html("<i class='mw-icon-wifi'></i>" + data.rssi);
        //链路
        $deviceInfo.find(".device-link").html("<i class='mw-icon-connect'></i>" + data.lqi);
    },
    commonDeviceTooltipVisualization: function($deviceInfo, e){
        var offset = TOOLTIP.reComputePosition($deviceInfo, e);
        if (!TOOLTIP.dead) {
            $deviceInfo.css({left: offset.x, top: offset.y}).show();
        }
    },


    //区域信息展示
    zoneInfoView: function (zone) {
        zone.addListenerEvent("mouseenter", function (that, e) {
            var $zoneInfo = $(".zone-info");
            TOOLTIP.mouseFocus = true;
            SERVICE.getZoneInfo(that.id, function (result) {
                $zoneInfo.find(".zone-cn-name").text(result.name);
                if (result.planImage) {
                    $zoneInfo.find(".zone-planimage").html("<img src='"+result.resourcesPath +"/" + result.planImage + "'/>");
                } else {
                    $zoneInfo.find(".zone-planimage").html("暂无平面部署图");
                }
                var offset = TOOLTIP.reComputePosition($zoneInfo, e);
                $zoneInfo.css({left: offset.x, top: offset.y}).show();
            });
        });
        TOOLTIP.initTooltipViewStrategy(zone);
    },
    // 摄像机点位提示
    dvPlaceInfoView: function (dvPlace) {
        dvPlace.addListenerEvent('mouseenter', function(that, e){
            var $dvPlaceInfo = $(".dv-place-info");
            TOOLTIP.mouseFocus = true;
            $dvPlaceInfo.find(".dv-place-cn-name").text(dvPlace.name);
            if (dvPlace.realmap) {
                // 图片在 galaxy-resources 下 @gaohui 2013-08-29
                var baseHref = $('#proximaResourcesUrl').text();
                $dvPlaceInfo.find(".dv-place-realmap").html("<img src='" + baseHref + '/realmap/' + dvPlace.realmap + "'/>");
            } else {
                $dvPlaceInfo.find(".dv-place-realmap").html("暂无平面部署图");
            }
            var offset = TOOLTIP.reComputePosition($dvPlaceInfo, e);
            $dvPlaceInfo.css({left: offset.x, top: offset.y}).show();
        });
        TOOLTIP.initTooltipViewStrategy(dvPlace);
    },
    // 初始化 tooltip 显示事件
    initTooltipViewStrategy: function(eleOfPlaneImage){
        eleOfPlaneImage.addListenerEvent("mouseleave", function (that, e) {
            TOOLTIP.mouseFocus = false;
            TOOLTIP.hideToolTip();
        });
        eleOfPlaneImage.addListenerEvent("mousedown", function (that, e) {
            TOOLTIP.dead = true;
            TOOLTIP.mouseFocus = false;
            TOOLTIP.hideToolTip();
        });
        eleOfPlaneImage.addListenerEvent("mouseup", function (that, e) {
            TOOLTIP.dead = false;
        });
    },
    hideToolTip: function () {
        setTimeout(function () {
            if (!TOOLTIP.mouseFocus) {
                $(".master-module").hide();
                $(".device-info").hide();
                $(".zone-info").hide();
                $(".dv-place-info").hide();
            }
        }, 500);
    },
    // 重新计算坐标
    reComputePosition : function($tooltip, e){
        var x = e.pageX - EleOfPlanImage.lockLeftForOther + EleOfPlanImage.iconWidth / 2;
        var y = e.pageY - EleOfPlanImage.lockTopForOther + EleOfPlanImage.iconHeight / 2;
        //区域tooltip展示位置
        if (x + $tooltip.width() > $(document).width() - EleOfPlanImage.lockLeftForOther) {
            x = x - $tooltip.width() - EleOfPlanImage.iconWidth;
        }
        if (y + $tooltip.height() > $(document).height() - EleOfPlanImage.lockTopForOther) {
            y = y - $tooltip.height() - EleOfPlanImage.iconHeight;
        }
        return {x: x, y: y};
    },
    /**
     * 此方法已废弃 @gaohui 2013-09-25
     * @param ele
     * @deprecated
     */
    addDblclick: function (ele) {
        ele.addListenerEvent("dblclick", function (that, e) {
            //双击回调
            var base = $("base").attr("href");
            if (that instanceof Zone) {
                window.location.href = base + 'zone/' + that.id + "/plan-image/view";
            } else if (that instanceof Device) {
                if (that.type == 1) {
                    window.location.href = base + 'device/' + that.id;
                }
            }
        });
    }
};

(function(){
    TOOLTIP.initToggleName();

    // tooltip 状态
    $('.zone-info, .device-info, .master-module, .dv-place-info').each(function(){
        $(this).mouseenter(function () {
            TOOLTIP.mouseFocus = true;
        });
        $(this).mouseleave(function () {
            TOOLTIP.mouseFocus = false;
            TOOLTIP.hideToolTip();
        });
    });

    // 主模块的从模块提示
    var $deviceInfo = $('.device-info');
    var $masterModuleInfo = $('.master-module');
    $masterModuleInfo.on("mouseenter", ".child-module", function (e) {
        //注意：下面的数字为更好的展示tooltip而设置的一些偏移量
        var $this = $(this);
        SERVICE.getDeviceRealTime($this.attr("node-id"), function (result) {
            TOOLTIP.renderRealtimeData($deviceInfo, result);
            var x = $this.offset().left + $this.width() - EleOfPlanImage.lockLeftForOther + 10;
            var y = $this.offset().top - EleOfPlanImage.lockTopForOther;
            //让tooltip展示在页面可视区
            if ($this.attr("float") === 'left') {
                x = x - $deviceInfo.width() - $masterModule.width() - EleOfPlanImage.iconWidth - 14;
            }
            if (y + $deviceInfo.height() > $(document).height() - EleOfPlanImage.lockTopForOther - 20) {
                y = y - $deviceInfo.height();
            }
            $deviceInfo.css({left: x, top: y }).show();
        });
    });
})();

