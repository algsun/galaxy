var time = [];
var map = new Map();
var i = 0;
var intervalID;
var times = [];
var datas;
var chart;

$(document).ready(function () {
    (function () {
        //监测点   和  摄像机点位 二级联动
        $("#monitorPoints-select").change(function () {
            var $this = $(this);
            var monitorPointId = $this.val();
            if (monitorPointId != 0) {
                var options = $("select[data-select-for-monitor-id='" + monitorPointId + "']")
                    .children()
                    .clone();
                $("#dvPlaces").empty().append(options);
            } else {
                var dvPlaces_hide = $("#dvPlaces_hide").children().clone();
                $("#dvPlaces").empty().append(dvPlaces_hide);
            }
        }).change();
    })();

    var chartDataStr = $("#chartDatas").text();
    var chartData = eval(chartDataStr);

    // 如果查询无数据，则不执行后续代码
    if (chartData == undefined) {
        return;
    }

    Highcharts.setOptions({
        lang: {
            resetZoom: message.reset
        }
    });

    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'chart',
            type: 'line',
            events: {
                load: function () {
                }
            },
            zoomType: 'xy'
        },
        credits: {enabled: false},
        title: {
            text: message.markChangeChart,
            x: -20
        },
        lang: {
            printButtonTitle: '打印',
            exportButtonTitle: message.export,
            downloadPNG: '导出为png格式',
            downloadJPEG: '导出为jpeg格式',
            downloadPDF: '导出为pdf格式',
            downloadSVG: '导出为svg格式'
        },
        xAxis: {
            max: eval($("#maxX").text()),
            min: eval($("#minX").text()),
            minRange: 20
        },
        yAxis: {
            title: {
                text: 'px'
            },
            plotLines: [
                {
                    value: 0,
                    width: 1,
                    color: '#808080'
                }
            ],
            max: eval($("#maxY").text()),
            min: eval($("#minY").text())
        },
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name
                    + '</b><br/>' + "X:"
                    + this.x + '<br/>Y:'
                    + this.y + "<br/>";
            }
        },

        series: chartData,
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

    var loadTimes = (function () {
        return function () {
            var $times = $("#times");
            var times = [];
            $times.children().each(function () {
                var $datetime = $(this);
                var dateTime = $datetime.attr("data-time");
                times.push(dateTime);
            });
            return times;
        };
    })();

    var loadMapData = (function () {
        return function () {
            var $datas = $("#datas");
            var mapData = new Map();
            $datas.children().each(function () {
                var $mapKey = $(this);
                var mapKey = $mapKey.attr("id");
                var mapSegmentData = new Map();
                $mapKey.children().each(function () {
                    var $keyValue = $(this);
                    var dataKey = $keyValue.attr("data-key");
                    var dataValue = $keyValue.attr("data-value");
                    mapSegmentData.put(dataKey, dataValue);
                });
                mapData.put(mapKey, mapSegmentData);
            });
            return mapData;
        };
    })();

    times = loadTimes();
    datas = loadMapData();

    function drawLine() {
        var data = [];
        var mapSegmentData;
        if (i == times.length) {
            play.innerHTML = "<i class='icon-play icon-white'></i>" + message.play;
            clearInterval(intervalID);
            intervalID = 0;
            i = 0;
            return;
        }
        $.each(chart.series, function (j, serie) {
            mapSegmentData = datas.get(serie.name);
            data = eval(mapSegmentData.get(times[i]));
            serie.setData(data, true);
        });

        $("#times ").get(0).selectedIndex = i;
        $("#slider").slider("value", Math.abs(i - times.length));
        $("#times ").focus();
        i = i + 1;
    }

    $("#play").click(function () {
        if (play.innerText == message.stop) {
            play.innerHTML = "<i class='icon-play icon-white'></i>" + message.play;
            clearInterval(intervalID);
            intervalID = 0;
            i = 0;
        } else if (play.innerText == message.play) {
            play.innerHTML = "<i class='icon-stop icon-white'></i>" + message.stop;
            intervalID = setInterval(function () {
                drawLine();
            }, $("#interval").val());
        }
    });

    $("#interval").change(function () {
        if (intervalID != 0 && intervalID != undefined) {
            clearInterval(intervalID);

            intervalID = setInterval(function () {
                drawLine();
            }, $("#interval").val());
        }
    });
});

$(function () {
    var select = $("#times");
    var slider = $("#slider").slider({
        orientation: "vertical",
        min: 1,
        max: times.length,
        range: "min",
        value: times.length,
        slide: function (event, ui) {
            select[0].focus();
            select[0].selectedIndex = Math.abs(ui.value - times.length);

            var dateTime = ($("#times").find("option:selected").attr("data-time"));
            $.each(chart.series, function (i, serie) {
                mapSegementData = datas.get(serie.name);
                data = eval(mapSegementData.get(dateTime));
                serie.setData(data, true);
            });

        }
    });
    $("#times").change(function () {
        slider.slider("value", times.length - this.selectedIndex);
        var dateTimes = [];
        ($("#times").find("option:selected").each(function () {
            dateTimes.push($(this).attr("data-time"));
        }));

        $.each(chart.series, function (i, serie) {
            mapSegementData = datas.get(serie.name);
            data = [];

            for (var j = 0; j < dateTimes.length; j++) {
                var dateTime = dateTimes[j];
                var data1 = eval(mapSegementData.get(dateTime));
                if (data1 != undefined) {
                    data = data.concat(data1);
                }
                data = data.concat(null);
            }
            serie.setData(data, true);
        });
    });
});