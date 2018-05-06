$(function () {
    var dvPlaces = $("input[name='dvPlaces']");
    //增量
    var num = 10;
    //最大值、最小值
    var max = 0, min = 0;
    //原最大值、最小值
    var oldMax = 0, oldMin = 0;

    var arrayMax = new Array();
    var arrayMin = new Array();
    var arrayMax1 = new Array();
    var arrayMin1 = new Array();

    // 图形Y轴设置
    $("#Yaxis").hide();
    $("#advancedBtn").click(function () {
        $("#Yaxis").toggle();
    });

    // 增加Y轴最大值
    $("#maxUp").click(function () {
        setYaxis(1);
    });

    // 减小Y轴最大值
    $("#maxDown").click(function () {
        setYaxis(2);
    });

    // 增加Y轴最小值
    $("#minUp").click(function () {
        setYaxis(3);
    });

    // 减小Y轴最小值
    $("#minDown").click(function () {
        setYaxis(4);
    });

    //通过输入框设置Y轴最大值
    $("#maxInputButtons").blur(function () {
        var maxInputValue = $("#maxInputButtons").val();
        if (maxInputValue < oldMax) {
            return;
        } else if (maxInputValue > 400) {
            art.dialog({
                title: message.tips,
                content: message.yAxisMaximumValue,
                follow: document.getElementById("maxInputButtons"),
                time: 3000
            });
            return;
        }
        chart.yAxis[0].setExtremes(min, maxInputValue);
    });

    //通过输入框设置Y轴最小值
    $("#minInputButtons").blur(function () {
        var minInputButtons = $("#minInputButtons").val();
        if (minInputButtons > oldMin) {
            return;
        } else if (minInputButtons < -100) {
            art.dialog({
                title: message.tips,
                content: message.yAxisMinimumValue,
                follow: document.getElementById("minInputButtons"),
                time: 3000
            });
            return;
        }
        chart.yAxis[0].setExtremes(minInputButtons, max);
    });

    //同时增加Y轴的最大值和最小值
    $("#yAxisUp").click(function () {
        setYaxis(5);
    });

    //同时减少Y轴的最大值和最小值
    $("#yAxisDomn").click(function () {
        setYaxis(6);
    });

    // 设置Y轴大小值
    function setYaxis(state) {
        var oldMaxYaxis = chart.yAxis[0].getExtremes().dataMax * 1;
        var oldMinYaxis = chart.yAxis[0].getExtremes().dataMin * 1;
        max = chart.yAxis[0].getExtremes().max * 1;
        min = chart.yAxis[0].getExtremes().min * 1;
        var max1 = max, min1 = min;
        if (state == 1) {
            max = max + num;
        } else if (state == 2) {
            var maxVal = 0;
            if (arrayMax.length > 0) {
                maxVal = arrayMax.pop();
            }
            max = max - maxVal;
        } else if (state == 3) {
            min = min - 10;
        } else if (state == 4) {
            var minVal = 0;
            if (arrayMin.length > 0) {
                minVal = arrayMin.pop();
            }
            min = min - minVal;
        } else if (state == 5) {//Y轴最大值和最小值同时增加
            max = max * 2;
            min = min * 2;
        } else if (state == 6) {//Y轴最大值和最小值同时减少
//            var maxVal = 0;
//            var minVal = 0;
//            if (arrayMax1.length > 0) {
//                maxVal = arrayMax1.pop();
//            }
//            max = max - maxVal;
//            if (arrayMin1.length > 0) {
//                minVal = arrayMin1.pop();
//            }
//            min = min - minVal;
            max = max / 2;
            min = min / 2;
        }
        if (state == 6 && (max < oldMaxYaxis || min > oldMinYaxis)) {
            window.pnotify(message.tips + "：" + message.downCannotExceed, "warn");
            return false;
        }
        chart.yAxis[0].setExtremes(min, max);
        if (state == 1) {
            var arrayLen = arrayMax.push((chart.yAxis[0].getExtremes().max * 1) - max1);
        } else if (state == 3) {
            var arrayLen = arrayMin.push((chart.yAxis[0].getExtremes().min * 1) - min1);
        } else if (state == 5) {
            var arrayLen = arrayMax1.push((chart.yAxis[0].getExtremes().max * 1) - max1);
            var arrayLen = arrayMin1.push((chart.yAxis[0].getExtremes().min * 1) - min1);
        }

    }

    //恢复默认设置
    $("#defaultSettings").click(function () {
        window.form1.submit();
        /*var i = 1;
         $.each(chart.series, function () {
         alert();
         chart.series[i].setData(chartData[i], true);
         });*/
    });


    // 监测点 change
    (function () {

        // 区域摄像机 和 摄像机点位 二级联动
        $("#zone-id")
            .change(
                function () {
                    var $this = $(this);
                    // 如果是未选择，那么摄像机点位可以全选
                    var zoneId = $this.val();
                    var places = [];
                    var options = "";
                    if (zoneId == '') {

                        for (var i = 0; i < dvPlaces.length; i++) {
                            var $option = dvPlaces[i];
                            options += " <option  value='" + $option.attributes[2].value + "'>" + $option.attributes[3].value + "</option>";
                        }
                    } else {
                        for (var i = 0; i < dvPlaces.length; i++) {
                            var $option = dvPlaces[i];
                            if (zoneId == $option.attributes[4].value) {
                                options += " <option  value='" + $option.attributes[2].value + "'>" + $option.attributes[3].value + "</option>";
                            }
                        }
                    }
                    if (options == "") {
                        options = " <option >" + message.noData + "</option>";
                    }
                    $("#dv-places").html(options);
                    $("#dv-places").change();
                });
    })();

    // 红外摄像机点位 change
    (function () {


        // 摄像机点位 和 红外分析区域 二级联动
        $("#dv-places").change(function () {
            var $this = $(this);
            var dvPlaceId = $this.val();
            $.post("ajaxMarkRegion.action", {dvPlaceId: dvPlaceId}, function (result) {
                var options = "<option value=''>" + message.all + "</option>";
                if (result.infraredMarkRegionList != null) {
                    var infraredMarkRegionList = result.infraredMarkRegionList;
                    if (infraredMarkRegionList.length > 0) {
                        for (var i = 0; i < infraredMarkRegionList.length; i++) {
                            options += "<option value=" + infraredMarkRegionList[i].id + ">" + infraredMarkRegionList[i].name + "</option>";
                        }
                    }
                }
                $("#markRegion-select").html(options);
            });

        });
    })();

    var chartDataStr = $("#chartDatas").text().trim();

    // 无数据时不加载图表
    if (chartDataStr != "") {
        $("#chart").show();
        $("#advanced").show();
    } else {
        $("#chart").hide();
        $("#advanced").hide();
        return;
    }

    var chartData = eval(chartDataStr);

    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    // Create the chart
    var chart = new Highcharts.StockChart({
        chart: {
            renderTo: 'chart',
            zoomType: 'xy'
//            events: {
//                redraw: function () {
//                    var max = 0, min = 0;
//                    max = chart.yAxis[0].getExtremes().max;
//                    min = chart.yAxis[0].getExtremes().min;
//                    var $maxInputButtons = $("#maxInputButtons");
//                    $maxInputButtons.val(max);
//                    var $minInputButtons = $("#minInputButtons");
//                    $minInputButtons.val(min);
//                }
//            }
        },

        credits: {
            enabled: false
        },

        rangeSelector: {
            enabled: true,
            buttons: [
                {
                    type: 'all',
                    text: message.reset
                }
            ],
            inputEnabled: false,
            selected: 1
        },

        lang: {
            months: ['01', '02', '03', '04', '05', '06', '07', '08', '09',
                '10', '11', '12'],
            shortMonths: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10',
                '11', '12']
        },

        xAxis: {
            dateTimeLabelFormats: {
                millisecond: "%H:%M:%S.%L",
                second: "%H:%M:%S",
                minute: "%H:%M",
                hour: "%H:%M",
                day: "%m-%d",
                week: "%e. %b",
                month: "%b '%y",
                year: "%Y"
            },
            labels: {
                x: 10,//调节x偏移
                y: 10,//调节y偏移
                rotation: -30//调节倾斜角度偏移
            }
        },

        yAxis: [
            {
                labels: {
                    formatter: function () {
                        return this.value;
                    },
                    style: {
                        color: '#4572A7'
                    },
                    align: 'right',
                    x: -10
                },
                title: {
                    text: '温度(℃)',
                    style: {
                        color: '#4572A7'
                    }
                }
            }
        ],

        lang: {
            printButtonTitle: '打印',
            exportButtonTitle: message.export,
            downloadPNG: '导出为png格式',
            downloadJPEG: '导出为jpeg格式',
            downloadPDF: '导出为pdf格式',
            downloadSVG: '导出为svg格式'
        },

        title: {
            text: message.trendChart
        },

        tooltip: {
            formatter: function () {
                /**
                 * var s = "<span style='font-size: 10px'>" +
                 * Highcharts.dateFormat('%Y-%m-%d %H:%M', this.x) + "</span><br/>";
                 */
                var s = "<span style='font-size: 10px'>"
                    + Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", new Date(
                        this.x)) + "</span><br/>";

                $.each(this.points, function (i, point) {
                    s += "<span style=color:" + point.series.color + ">"
                        + point.series.name + "</span>:<b>" + point.y
                        + "</b> ℃<br/>";
                });
                return s;
            }
        },
        series: chartData,

        plotOptions: {
            spline: {
                marker: {
                    enabled: false,
                    states: {
                        hover: {
                            enabled: true,
                            symbol: 'circle',
                            radius: 4
                        }
                    }
                }
            }
        },

        legend: {
            enabled: true,
            align: 'center',
            y: -75
        },

        navigator: {
            enabled: true,
            margin: -20,
            height: 20,
            xAxis: {
                dateTimeLabelFormats: {
                    millisecond: "%H:%M:%S.%L",
                    second: "%H:%M:%S",
                    minute: "%H:%M",
                    hour: "%H:%M",
                    day: "%m-%d",
                    week: "%e. %b",
                    month: "%b '%y",
                    year: "%Y"
                }
            }
        },

        exporting: {
            enabled: true,
            filename: 'chart',
            type: 'image/jpeg',
            url: 'exportImage.action',
            buttons: {
                printButton: {
                    enabled: false
                },
                exportButton: {
                    menuItems: [
                        {
                            text: message.export + ' JPEG',
                            onclick: function () {
                                this.exportChart(); // 800px by default
                            }
                        },
                        null,
                        null,
                        null
                    ]
                }
            }
        }
    });

    //初始化数据
    max = chart.yAxis[0].getExtremes().max * 1;
    min = chart.yAxis[0].getExtremes().min * 1;
    oldMax = max;
    oldMin = min;

    (function () {
        var max = 0, min = 0;
        max = chart.yAxis[0].getExtremes().max;
        min = chart.yAxis[0].getExtremes().min;
        var $maxInputButtons = $("#maxInputButtons");
        $maxInputButtons.val(max);
        var $minInputButtons = $("#minInputButtons");
        $minInputButtons.val(min);
    })();
});