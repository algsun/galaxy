/**
 *
 * 位置点实时数据
 *
 * @author: xuyuexi
 * @time: 14-7-1
 */
$(function () {
    $.ajaxSetup({traditional: true});
    var locationId = $("#locationId").val();
    var $tableDataTemplate = $("#device-realtime-data").find(".table-data-template");
    var $tbody = $("#device-realtime-data > tbody");
    var $realtimeDataTable = $("#device-relatime-data");
    // 获取最新的 n 包数据 或 最新若干条数据
    var getData = function (count, startTime) {
        var serverTime = new Date().getTime() - ($("body").attr("data-client-time") - $("body").attr("data-server-time"));
        $.getJSON("location/" + locationId + "/realtime-data.json", {
            count: count,
            startTime: startTime
        }, function (data) {
            // 清空数据
            var $noDataMsg = $(".no-data-msg");
            if ($noDataMsg[0] != undefined) {
                $noDataMsg.remove();
            }
            $realtimeDataTable.children("thead").show();
            if (data.length < 1) {
                $realtimeDataTable.after("<div class='no-data-msg' style='text-align: center; color: gray;'>" + message.noData + "</div>");
                $realtimeDataTable.children("thead").hide();
                return;
            } else {
                $realtimeDataTable.children("thead").show();
            }
            $.each(data, function (i, dataRow) {

                var timeStamp = moment($tbody.children(":eq(1)").find("[data-type='stamp']").val(), "YYYY-MM-DDTHH:mm:ss").format("YYYY-MM-DD HH:mm:ss");

                if (count == 0) {
                    // 列表中的最新的数据时间戳与获取数据时间戳一致，则直接返回
                    if (moment(dataRow.stamp, "YYYY-MM-DDTHH:mm:ss").format("YYYY-MM-DD HH:mm:ss") == timeStamp) {
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
                        if (dataRow.sensorInfoMap[dataType]) {
                            $td.removeAttr('style');
                            if (dataRow.sensorInfoMap[dataType].valueState == 3) {
                                classValue = '';
                            } else if (dataRow.sensorInfoMap[dataType].valueState == 1) {
                                classValue = 'background-color:#faf2cc';
                                $td.attr("style", classValue)
                            } else if (dataRow.sensorInfoMap[dataType].valueState == 2) {
                                classValue = 'background-color:#c4e3f3';
                                $td.attr("style", classValue)
                            }
                            $td.attr("title", dataRow.sensorInfoMap[dataType].cnName);
                            // 数据如果不正常则 '--' 显示
                            if (dataRow.sensorInfoMap[dataType].state == 0) {
                                $td.text("--");
                            } else {
                                $td.text(dataRow.sensorInfoMap[dataType].sensorPhysicalValue);
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