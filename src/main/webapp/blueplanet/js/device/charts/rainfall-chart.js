/**
 * 降雨量图
 *
 * @author: li.jianfei
 * @time: 13-3-8 下午5:34
 *@check @wang yunlong 2013-03-13 #2076
 */
(function ($) {
    $(function () {
        // 降雨量监测指标常量
        var RAINFALL_SENSOR_ID=47;
        //监测指标多选
        $("#sensorIds").select2();
        var data = $("#sensorIds").select2("data");
        $.each(data, function (index, item) {
            if (item.id == RAINFALL_SENSOR_ID) {
                item.locked = true;
                return false;
            }
        });
        $("#sensorIds").select2("data", data);
    });
})(jQuery);

$(function () {
    $(document).ready(function () {
        $.ajaxSetup({traditional: true});

        createChart();

        function createChart() {
            // 获取参数信息
            var deviceId = $("#deviceId").val();
            var sensorIds = [];
            $("#sensorIds").find("option:selected").each(function () {
                sensorIds.push($(this).val());
            });

            var dateType = $("input[name='dateType']:checked").val();
            var date = $("#date").val();


            $.getJSON("device/" + deviceId + "/rainfall-chart.json", {deviceId: deviceId, sensorIds: sensorIds, dateType: dateType, date: date}, function (highChartData) {
                CHART.createChart(highChartData);
            });
        }

        $("#commit").click(function () {
            createChart();
        });

        var CHART = {
            data: {},
            yAxis: [],
            createChart: function (data) {
                CHART.data = data;

                if (!data.hasRainfallData) {
                    $("#noData").text("暂无数据");
                    $("#chart").hide();
                    return;
                } else {
                    $("#noData").text("");
                    $("#chart").show();
                }

                CHART.createYAxis();
                var date = $("#date").val();
                var options = {
                    chart: {
                        renderTo: 'chart',
                        zoomType: 'xy'
                    },
                    title: {
                        text: date
                    },
                    credits: {
                        enabled: false
                    },
                    xAxis: {
                        type: 'datetime',
                        tickInterval: CHART.getTickInterval(),
                        dateTimeLabelFormats: {
                            day: '%Y-%m-%d',
                            month: '%Y-%m',
                            year: '%Y'
                        }
                    },
                    yAxis: CHART.yAxis,
                    tooltip: {
                        xDateFormat: '%Y-%m-%d %H:%M:%S',
                        crosshairs: true,
                        shared: true
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.2,
                            borderWidth: 0
                        }
                    },
                    series: data.chartList,
                    exporting: {
                        buttons: {
                            printButton: {
                                enabled: false
                            },
                            exportButton: {
                                menuItems: null,
                                onclick: function () {
                                    this.exportChart();
                                }
                            }
                        },
                        url: 'export-highchart-image.action',
                        filename: date
                    }
                };

                Highcharts.setOptions({
                    global: {
                        useUTC: false
                    },
                    lang: {
                        resetZoom: "重置",
                        resetZoomTitle: "按1:1缩放",
                        exportButtonTitle: "导出图片"
                    }
                });
                var chart = new Highcharts.Chart(options);
                if (data.chartList.length > 0) {
                    var sumHtml = "";
                    $.each(data.chartList, function (index, serie) {
                        var deviceName = serie.cnName;
                        if(serie.sensorPhysicalId==47){
                            sumHtml += "<tr>" +
                                "<td>" + deviceName + "</td>" +
                                "<td>" + serie.sumValue +
                                "</tr>";
                        }
                    });

                    var chartInfoTable = " <table class='table table-bordered'>" +
                        "<thead>" +
                        "<tr>" +
                        "<th>监测指标</th>" +
                        "<th>累计降雨量总和(mm)</th>" +
                        "</tr>" +
                        "</thead>" +
                        "<tbody>" +
                        sumHtml +
                        "</tbody>" +
                        "</table>";
                    $("#statistics").html(chartInfoTable);
                }
            },
            createYAxis: function () {
                CHART.yAxis = [];
                $.each(CHART.data.chartList, function (index, chartData) {
                    var temp = {
                        sensorPhysicalId: chartData.sensorPhysicalId,
                        title: {
                            text: chartData.yText
                        },
                        lineWidth: 2,
                        offset: (CHART.yAxis.length) * 70
                    };
                    CHART.yAxis.push(temp);

                });
            },
            getTickInterval: function () {
                var interval = 0;
                switch ($("input[name='dateType']:checked").val()) {
                    case "1":
                        interval = 30 * 24 * 3600 * 1000;
                        break;
                    case "2":
                        interval = 24 * 3600 * 1000;
                        break;
                    case "3":
                        interval = 3600 * 1000;
                        break;
                }
                return interval;
            }
        }

    });
});
$(function () {
    $("input[name='dateType']").change(function () {
        var momentFormat;
        var my97Format;
        switch ($(this).val()) {
            case "1":
                momentFormat = DateUtil.formats.YEAR;
                my97Format = 'yyyy';
                break;
            case "2":
                momentFormat = DateUtil.formats.MONTH;
                my97Format = 'yyyy-MM';
                break;
            case "3":
                momentFormat = DateUtil.formats.DAY;
                my97Format = 'yyyy-MM-dd';
                break;
        }
        var $timeInput = $("#date");
        var value = DateUtil.formatDate($timeInput.val(), momentFormat);
        $timeInput.val(value);
        $timeInput.unbind("click.time");
        //时间选择器
        $timeInput.bind("click.time", function () {
            WdatePicker({
                dateFmt: my97Format,
                el: $timeInput[0],
                maxDate: '%y-%M-%d'
            });
        });
    }).change();
});
var BluePlanet = App("blueplanet");
BluePlanet.zoneDevicePath.redirectWhenDeviceChange(function (node) {
    var RAINFALL_SENSOR_PHYSICAL_ID = 47;
    if ($.inArray(RAINFALL_SENSOR_PHYSICAL_ID, node.sensorPhysicalIds) == -1) {
        art.dialog({title: '提示', content: '该设备无降雨量图'});
        return false;
    }

    return 'device/' + node.id + "/rainfall-chart";
});
