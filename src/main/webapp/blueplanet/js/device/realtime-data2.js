/**
 *
 * 设备实时数据
 *
 * @author: Wang yunlong
 * @time: 13-2-26 下午2:33
 */
$(function () {
    $.ajaxSetup({traditional: true});
    var deviceId = $("#device-id").text();
    var $tableDataTemplate = $("#device-realtime-data").find(".table-data-template");
    var $tbody = $("#device-realtime-data > tbody");
    var $realtimeDataTable = $("#device-relatime-data");
    // 获取最新的 n 包数据 或 最新若干条数据
    var getData = function (count, startTime) {
        var serverTime = new Date().getTime() - ($("body").attr("data-client-time") - $("body").attr("data-server-time"));
        $.getJSON("device/" + deviceId + "/realtime-data.json", {count: count, startTime: startTime}, function (data) {
            // 清空数据
            var $noDataMsg = $(".no-data-msg");
            if ($noDataMsg[0] != undefined) {
                $noDataMsg.remove();
            }
            $realtimeDataTable.children("thead").show();
            if (data.length < 1) {
                $realtimeDataTable.after("<div class='no-data-msg' style='text-align: center; color: gray;'>该设备暂无数据</div>");
                $realtimeDataTable.children("thead").hide();
                return;
            } else {
                $realtimeDataTable.children("thead").show();
            }
            $.each(data, function (i, dataRow) {
                if (count == 0) {
                    // 列表中的最新的数据时间戳与获取数据时间戳一致，则直接返回
                    if (moment(dataRow.stamp, "YYYY-MM-DDTHH:mm:ss").format("YYYY-MM-DD HH:mm:ss") == $tbody.children(":eq(1)").find("[data-type='stamp']").text()) {
                        return;
                    }
                }

                // 复制新的一行
                var $appendTr = $tableDataTemplate.clone()
                    .removeClass("table-data-template")
                    .removeClass("hide");
                // 填数据
                $appendTr.children().each(function (index, td) {
                    var $td = $(td);
                    var dataType = $(td).attr("data-type");
                    if ($td.attr("data-dead") == "1") {
                        REALTIME_DATA_UTIL.baseAttrView($td, dataType, dataRow, serverTime);
                    } else {
                        if (dataRow.sensorinfoMap[dataType]) {
                            $td.attr("title", dataRow.sensorinfoMap[dataType].cnName);
                            // 数据如果不正常则 '--' 显示
                            if (dataRow.sensorinfoMap[dataType].state == 0) {
                                $td.text("--");
                            } else {
                                $td.text(dataRow.sensorinfoMap[dataType].sensorPhysicalValue);
                            }
                        }
                    }
                });
                // 追加到第一行
                $tbody.children(":eq(0)").after($appendTr);
                // 如果是第一次加载不需要颜色动画，反之亦然
                if (count != FIRST_DATA_NUMBER) {
                    //颜色渐变, 由透明变成 lightgrenn ，然后再变成透明
                    $appendTr.css("background-color", "transparent");
                    // lightgreen => #90ee90
                    $appendTr.animate({backgroundColor: "#90ee90"}, 1000)
                        .animate({backgroundColor: "transparent"}, 3000, function () {
                            $appendTr.removeAttr('style');
                        });
                }
            });
        });
        //实时数据表头移动
        if (!window.move) {
            new TableColumn(".move", "moveable").init();
            window.move = true;
        }
    };
    getData(window.FIRST_DATA_NUMBER, 0);
    var deleteLastColumn = function () {
        //超过 12 行，则删除最后一行
        $tbody.find("tr:gt(12)").remove();
    };

    // 更新时间显示
    var refreshTime = function () {
        var $trs = $tbody.find("tr").not(".table-data-template");
        var serverTime = new Date().getTime() - ($("body").attr("data-client-time") - $("body").attr("data-server-time"));
        $trs.each(function (index, tr) {
            var $tr = $(tr);
            var $td = $tr.find("td[data-type='stamp']");
            REALTIME_DATA_UTIL.datetimeView($td, $td.attr("time-value"), serverTime);
        });
    };
    // 定时查询最新数据
    setInterval(function () {
        var startTime = $tbody.children("tr").not(".table-data-template").eq(0).find("td[data-type=stamp]").attr("title");
        var stamp = moment(startTime, "YYYY-MM-DD HH:mm:ss");
        //0没有意义
        getData(0, stamp.valueOf());
        deleteLastColumn();
        refreshTime();
    }, window.REALTIME_REFRESH_INTERVAL);
});

// 设备状态实时刷新
$(function () {
    var deviceId = $("#device-id").text();
    var $deviceStateContainer = $("#deviceStateContainer");
    var refreshDeviceState = function () {
        $.getJSON("device/" + deviceId + ".json", function (device) {
            var state = REALTIME_DATA_UTIL.deviceStateToString(device.lowvoltage, device.anomaly);
            $deviceStateContainer.find("[data-type='state']").html(state);

            $deviceStateContainer.find("[data-type='rssi']").text(device.rssi);
            $deviceStateContainer.find("[data-type='lqi']").text(device.lqi);

            var time = device.stamp;
            time = moment(time, "YYYY-MM-DDTHH:mm:ss").format("YYYY-MM-DD HH:mm:ss");
            $deviceStateContainer.find("[data-type='time']").text(time);
        });
    };

    setInterval(refreshDeviceState, window.REALTIME_REFRESH_INTERVAL);
});
