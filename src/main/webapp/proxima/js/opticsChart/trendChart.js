$(function () {
    (function () {
        $("#dvPlaces").change(function () {
            var $marks = $("#" + $("#dvPlaces").val());
            $("#markSegements").empty();
            $("#markSegements").append($marks.children().clone());
        });

        //监测点   和  摄像机点位 二级联动
        $("#monitorPoints-select").change(function () {
            var $this = $(this);
            var monitorPointId = $this.val();
            if (monitorPointId != "0") {
                var options = $("select[data-select-for-monitor-id='" + monitorPointId + "']").children()
                    .clone();
                $("#dvPlaces").empty().append(options);
            } else {
                var dvplaces_hide = $("#dvPlaces_hide").children().clone();
                $("#dvPlaces").empty().append(dvplaces_hide);
            }
            $("#dvPlaces").change();
        }).change();
    })();

    if (parseInt($("#mspSize").attr("size")) > 0) {
        $("#chart").show();
    } else {
        $("#chart").hide();
        return;
    }

    var chartDataStr = $("#chartDatas").text();
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
            zoomType: 'x'
        },

        credits: {enabled: false},

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
            months: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
            shortMonths: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
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
                        return this.value + 'mm';
                    },
                    style: {
                        color: '#4572A7'
                    },
                    align: 'right',
                    x: -20
                },
                title: {
                    text: message.crackLength,
                    style: {
                        color: '#4572A7'
                    }
                }
            },
            {
                labels: {
                    formatter: function () {
                        return this.value + 'mm';
                    },
                    style: {
                        color: '#89A54E'
                    },
                    x: 50
                },
                title: {
                    text: message.variationRange,
                    style: {
                        color: '#89A54E'
                    },
                    margin: 70
                },
                opposite: true
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
            text: message.crackPosition
        },

        tooltip: {
            formatter: function () {
                var s = "<span style='font-size: 10px'>"
                    + Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", new Date(
                        this.x)) + "</span><br/>";

                $.each(this.points, function (i, point) {
                    s += "<span style='color:" + point.series.color + ">"
                        + point.series.name + "</span>:<b>" + point.y
                        + "</b> mm<br/>";
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
            y: -50
        },

        navigator: {
            enabled: true,
            margin: -30,
            height: 20, xAxis: {
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
});