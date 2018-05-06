/**
 *
 *<pre>
 * 均值比较
 *</pre>
 * @author: xu.yuexi
 * @time: 14-4-21
 */
$(function () {
    $(function () {
        //二级导航
        var BluePlanet = App("blueplanet");
        BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
            return 'location/' + node.locationId + "/average-compare";
        });
        //监测指标多选
        $("#sensorPhysicalId").select2({
            formatNoMatches: function () {
                return message.noOptionalSensor;
            }
        });
    });
    (function () {
        var deviceTree = App(window.BLUEPLANET).deviceTree;
        deviceTree.beforeDrag(function (treeId, treeNodes) {
            if (treeNodes[0].type == "location") {
                return true;
            }
            return false;
        });
        deviceTree.drop("pre-selection", function (e, treeId, treeNode) {
            if ($.inArray(treeNode.locationId, findExistDevices()) == -1) {
                var $compareDevice = $(e.target);
                $compareDevice.text(treeNode.name).attr("compare-location-id", treeNode.locationId);
                var $sensorOptions = "";
                var Blueplanet = App(window.BLUEPLANET);
                var sensorinfos = Blueplanet.site.sensorinfos;
                $.each(treeNode.sensorPhysicalIds, function (index, sensorPhysicalId) {
                    $sensorOptions += "<option value='" + sensorPhysicalId + "'>" + sensorinfos[sensorPhysicalId].cnName + "</option> ";
                });
                $compareDevice.parent().parent().find("select.sensor-physicalids").html($sensorOptions);
            } else {
                art.dialog({
                    title: message.tips,
                    content: message.locationExists
                });
            }
        });
        var findExistDevices = function () {
            var existDevices = [];
            existDevices.push($("#location-id").text());
            $(".compare-location").not(".compare-column-template").each(function (index, location) {
                existDevices.push($(location).attr("compare-location-id"));
            });
            return existDevices;
        };
    })();
    var locationId = $("#location-id").text();
    var locationName = $("#location-name").text();
    //添加对比
    $("#add-compare").click(function () {
        if (!AVG_CHART.existData) {
            art.dialog({
                id: 'info',
                title: message.tips,
                content: message.noComparison
            });
            return;
        }
        var $compare = $("#compare");
        var $compareShowHide = $("#show-hide-compare");
        if ($compare.children().length == 1) {
            $compare.show();
        } else {
            $compare.slideDown();
        }
        if ($(".compare-column").not(".compare-column-template").length < 1) {
            addCompareRow();
            CompareCancel();
        }
        $compareShowHide.text(message.hide);
        $compareShowHide.show();
        $(this).hide();
        $("#cancel-all-compare").show();
    });
    //隐藏/显示对比行
    $("#show-hide-compare").click(function () {
        var $this = $(this);
        var $compare = $("#compare");
        renderCompareShowHide($this, $compare);
        $compare.slideToggle();
    });
    //改变显示/隐藏
    var renderCompareShowHide = function ($this, $compare) {
        if ($compare.is(":visible")) {
            $this.text(message.show);
        } else {
            $this.text(message.hide);
        }
    };
    //对比行鼠标悬停 显示取消按钮
    $(document).on("mouseenter", ".compare-column", function () {
        $(this).find(".compare-column-cancel").show();
        $(this).find(".compare-column-ok").show();
    });
    //对比行鼠标离开 隐藏取消按钮
    $(document).on("mouseleave", ".compare-column", function () {
        $(this).find(".compare-column-cancel").hide();
        $(this).find(".compare-column-ok").hide();
    });
    //确定对比同时添加新的一行未确定对比
    $(document).on("click", ".compare-column-ok", function () {
        var $this = $(this);
        var $location = $this.siblings(".compare-column-node").find(".compare-location");
        var $sensorinfoSelect = $this.siblings(".compare-column-sensorinfo").find("select.sensor-physicalids");
        if ($location.attr("compare-location-id") == undefined) {
            art.dialog({
                id: "info",
                title: message.tips,
                content: message.selectLocationForContrast
            });
            return;
        }
        if ($sensorinfoSelect.find("option:selected").length < 1) {
            art.dialog({
                id: "info",
                title: message.tips,
                content: message.selectSensorForContrast
            });
            return;
        }
        MSG.showMsg(message.loading);
        compareRenderChart($sensorinfoSelect, $location);
        if (!$this.hasClass("have-done")) {
            $this.parent().parent().find(".compare-location").removeAttr("id");
            compareAppendCancel($this.parent());
            addCompareRow();
            CompareCancel();
            $this.addClass("have-done");
        }
    });
    //查询添加对比位置点的数据 并添加到图表上
    var compareRenderChart = function ($sensorinfoSelect, $location) {
        var startTime = $("#start-time").val();
        var endTime = $("#end-time").val();
        var sensorinfoes = getSelect2Values($sensorinfoSelect);
        $.post("location/" + $location.attr("compare-location-id") + "/getAverageCompare.json", {
            locationName: $location.text(),
            sensorinfoes: sensorinfoes,
            startTime: startTime,
            endTime: endTime
        }, function (data) {
            MSG.closeDialog();
            if (data.hasAvgData) {
                AVG_CHART.deleteLines($location.attr("compare-location-id"));
                AVG_CHART.addLines(data.chartList);
                AVG_CHART.createSubtitleText();
                AVG_CHART.renderChart();
            } else {
                window.pnotify(message.location + "“" + $location.text() + "”" + message.noData, "warn");
            }
        });
    };
    //取消对比 本行
    $(document).on("click", ".compare-column-cancel", function () {
        var $this = $(this);
        if ($this.parent().siblings(".compare-column").not(".compare-column-template").length < 1) {
            $("#show-hide-compare").hide();
            $("#cancel-all-compare").hide();
            $("#add-compare").show();
        }
        $this.parent().remove();
        CompareCancel();
        var $location = $this.siblings(".compare-column-node").find(".compare-location");
        AVG_CHART.deleteLines($location.attr("compare-location-id"));
        AVG_CHART.createSubtitleText();
        AVG_CHART.renderChart();
    });
    //取消所有对比
    $("#cancel-all-compare").click(function () {
        $(".compare-column").not(".compare-column-template").remove();
        $("#show-hide-compare").hide();
        $(this).hide();
        $("#add-compare").show();
        MSG.showMsg(message.restoringChart);
        queryaAvgData();
    });
    //添加对比行
    var addCompareRow = function () {
        var $compare = $("#compare");
        var $newCompareColumn = $(".compare-column-template").clone().removeClass("compare-column-template");
        $newCompareColumn.find(".compare-location").attr("id", "pre-selection");
        $compare.append($newCompareColumn);
        $newCompareColumn.find("select.sensor-physicalids").select2({
            formatNoMatches: function () {
                return message.noOptionalSensor;
            }
        });
        $newCompareColumn.slideDown();
    };
    //如果只有一行对比 即 还未确定 那么允许取消
    var CompareCancel = function () {
        var $compareColumn = $(".compare-column").not(".compare-column-template");
        if ($compareColumn.length == 1) {
            compareAppendCancel($compareColumn);
        }
    };
    //对比行添加取消按钮
    var compareAppendCancel = function ($compareColumn) {
        var $compareCancel = $compareColumn.find(".compare-column-cancel");
        if ($compareCancel[0] == undefined) {
            $compareColumn.append("<div class='compare-column-cancel' title='" + message.cancel + "'><i class=' icon-minus-sign'></i></div>");
        }
    };
    //查询数据
    var avgData = function (locationId, locationName, sensorinfoes, startTime, endTime) {
        if (startTime == undefined) {
            startTime = null;
        }
        if (endTime == undefined) {
            endTime = null;
        }
        if (sensorinfoes == undefined) {
            sensorinfoes = [$("#sensorPhysicalId").val()];
        }
        $.post("location/" + locationId + "/getAverageCompare.json", {
            locationName: locationName,
            sensorinfoes: sensorinfoes,
            startTime: startTime,
            endTime: endTime
        }, function (data) {
            MSG.closeDialog();
            if (data.hasAvgData) {
                $("#avg-chart").empty();
                AVG_CHART.execute(data);
            } else {
                $("#avg-chart").html("<h4>" + message.noData + "</h4>");
            }
        });
    };
    //获取select2数据
    var getSelect2Values = function ($select) {
        var sensorinfoes = [];
        $select.find("option:selected").each(function (index, option) {
            sensorinfoes.push(option.value);
        });
        return sensorinfoes;
    };
    //设置图表时间非国际标准
    Highcharts.setOptions({
        global: {useUTC: false}
    });
    Highcharts.setOptions({
        lang: {
            downloadJPEG: '导出为JPEG',
            downloadPDF: '导出为PDF',
            downloadPNG: '导出为PNG',
            downloadSVG: '导出为SVG',
            exportButtonTitle: message.export,
            resetZoom: message.reset,
            resetZoomTitle: message.zoomBy,
            rangeSelectorFrom: "开始",
            rangeSelectorTo: "结束",
            rangeSelectorZoom: ""//为什么是空 去掉它

        }
    });
    $.ajaxSetup({traditional: true});
    avgData(locationId, locationName, getSelect2Values($("#sensorPhysicalId")), $("#start-time").val(), $("#end-time").val());
    //查询数据
    var queryaAvgData = function () {
        var startTime = $("#start-time").val();
        var endTime = $("#end-time").val();
        var sensorinfoes = getSelect2Values($("#sensorPhysicalId"));
        if (sensorinfoes.length < 1) {
            art.dialog({
                id: 'info',
                title: message.tips,
                content: message.selectSensor
            });
            return;
        }
        avgData(locationId, locationName, sensorinfoes, startTime, endTime);
    };
    $("#query-avg-chart").click(function () {
        var startTime = $("#start-time").val();
        var endTime = $("#end-time").val();
        if (startTime.length < 1) {
            art.dialog({
                id: "info",
                title: message.tips,
                content: message.selectStartTime
            });
            return;
        }
        if (endTime.length < 1) {
            art.dialog({
                id: "info",
                title: message.tips,
                content: message.selectEndTime
            });
            return;
        }
        AVG_CHART.yAxises = [];
        AVG_CHART.series = [];
        if ($("#sensorPhysicalId").select2('val').length < 1) {
            art.dialog({
                id: 'info',
                title: message.tips,
                content: message.selectSensor
            });
            return;
        }
        MSG.showMsg(message.loading);
        reQueryCompareData();
    });
    //重新查询所有对比位置点的数据
    var reQueryCompareData = function () {
        compareRenderChart($("#sensorPhysicalId"), $("#location-name"));
        var $compares = $(".compare-column").not(".compare-column-template");
        $compares.each(function (index, compare) {
            if (index < $compares.length - 1) {
                var $compare = $(compare);
                compareRenderChart($compare.find("select.sensor-physicalids"), $compare.find(".compare-location"));
            }
        });
    };
    var AVG_CHART = {
        data: {},
        yAxises: [],
        series: [],
        titleText: '',
        chart: {},
        subtitleText: '',
        titleMargin: 15,
        existData: false,
        execute: function (data) {
            var AVG_CHART = this;
            AVG_CHART.data = data;
            AVG_CHART.series = data.chartList;
            AVG_CHART.createTitleText();
            AVG_CHART.createYAxises();
            //设置曲线的y轴
            $.each(AVG_CHART.series, function (i, d) {
                AVG_CHART.setSeriesYAxis(d);
            });
            AVG_CHART.createSubtitleText();
            AVG_CHART.renderChart();
        },

        //用来展示图表中数据的最大值、最小值以及波动范围
        createSubtitleText: function () {
            var AVG_CHART = this;
            AVG_CHART.subtitleText = '';
            AVG_CHART.titleMargin = 15;
            AVG_CHART.titleMargin += AVG_CHART.series.length * 14;
            $.each(AVG_CHART.series, function (index, chart) {
                AVG_CHART.subtitleText += "<tr>" +
                    "<td>" + chart.name + "</td>" +
                    "<td>" + chart.maxValue.data + chart.units + " (" + moment(chart.maxValue.time).format("YYYY-MM-DD HH:mm:ss") + ")" + "</td>" +
                    "<td>" + chart.minValue.data + chart.units + " (" + moment(chart.minValue.time).format("YYYY-MM-DD HH:mm:ss") + ")" + "</td>" +
                    "<td>" + (chart.maxValue.data - chart.minValue.data).toFixed(chart.sensorPrecision) + chart.units + "</td>" +
                    "</tr>";

            });
        },
        deleteLines: function (locationId) {
            var AVG_CHART = this;
            var deleteSeries = [];
            $.each(AVG_CHART.series, function (index, serie) {
                if (serie.locationId == locationId) {
                    deleteSeries.push(serie);
                }
            });
            $.each(deleteSeries, function (index, serie) {
                AVG_CHART.series = _.without(AVG_CHART.series, serie);
            });
            //删除多余的y轴
            var seriesSensorinfoes = AVG_CHART.getSeriesId();
            var deleteYAxises = [];
            $.each(AVG_CHART.yAxises, function (index, yAxis) {
                if ($.inArray(yAxis.sensorPhysicalId, seriesSensorinfoes) == -1) {
                    deleteYAxises.push(yAxis);
                }
            });
            $.each(deleteYAxises, function (index, yAxis) {
                AVG_CHART.yAxises = _.without(AVG_CHART.yAxises, yAxis);
            });
        },
        addLines: function (lineData) {
            var AVG_CHART = this;
            $.each(lineData, function (index, ld) {
                //如果没有相应y轴 则添加
                if ($.inArray(ld.sensorPhysicalId, AVG_CHART.getChartsYAxisesSensorPhysicalIds()) == -1) {
                    var yAxis = AVG_CHART.createYAxis(ld.sensorPhysicalId, ld.yText);
                    AVG_CHART.yAxises.push(yAxis);
                }
                AVG_CHART.setSeriesYAxis(ld);
                AVG_CHART.series.push(ld);
            });
        },
        createTitleText: function () {
            var AVG_CHART = this;
            AVG_CHART.titleText = message.meanComparisonCurve;
        },
        //设置曲线的y轴
        setSeriesYAxis: function (data) {
            $.each(AVG_CHART.yAxises, function (index, yAxis) {
                if (yAxis.sensorPhysicalId == data.sensorPhysicalId) {
                    data.yAxis = index;
                }
            });
        },

        //获取图表中曲线的监测指标
        getSeriesId: function () {
            var AVG_CHART = this;
            var sensorinfoes = [];
            $.each(AVG_CHART.series, function (index, serie) {
                if ($.inArray(serie.sensorPhysicalId, sensorinfoes) == -1) {
                    sensorinfoes.push(serie.sensorPhysicalId);
                }
            });
            return sensorinfoes;
        },
        //获取已经存在的监测指标的y轴标识（sensroPhysicalId）
        getChartsYAxisesSensorPhysicalIds: function () {
            var AVG_CHART = this;
            var ids = [];
            $.each(AVG_CHART.yAxises, function (index, yAxis) {
                ids.push(yAxis.sensorPhysicalId);
            });
            return ids;
        },
        //创建初始化的y轴
        createYAxises: function () {
            var AVG_CHART = this;
            AVG_CHART.yAxises = [];
            var sensorPhysicalIds = [];
            $.each(AVG_CHART.data.chartList, function (index, cd) {
                if ($.inArray(cd.sensorPhysicalId, sensorPhysicalIds) == -1) {
                    AVG_CHART.yAxises.push(AVG_CHART.createYAxis(cd.sensorPhysicalId, cd.yText));
                    sensorPhysicalIds.push(cd.sensorPhysicalId);
                }
            });
        },
        //创建一个y轴
        createYAxis: function (sensorPhysicalId, yText) {
            //y轴分两端展示
            var opposite = (AVG_CHART.yAxises.length % 2 == 1);
            var labelsAlign = 'right';
            var labelsX = -8;
            if (opposite) {
                labelsAlign = 'left';
                labelsX = 7;
            }
            var yAxis = {
                sensorPhysicalId: sensorPhysicalId,
                title: {
                    text: yText
                },
                lineWidth: 2,
                labels: {
                    align: labelsAlign,
                    overflow: null,
                    style: null,
                    x: labelsX
                },
                opposite: opposite
            };
            return yAxis;
        },
        renderChart: function () {
            var AVG_CHART = this;
            var startTime = $("#start-time").val();
            var endTime = $("#end-time").val();
            AVG_CHART.chart = new Highcharts.StockChart({
                chart: {
                    renderTo: 'avg-chart',
                    type: 'spline',
                    zoomType: 'xy'
                },
                title: {
                    text: message.meanComparisonCurve
                },
                legend: {
                    y: -70,
                    enabled: true
                },
                rangeSelector: {
                    buttons: [],
                    inputDateFormat: '%Y-%m-%d',
                    inputEditDateFormat: '%Y-%m-%d',
                    inputStyle: {
                        letterSpacing: '-1px',
                        fontSize: '11px'
                    },
                    inputPosition: {
                        align: 'left'
                    }
                },
                credits: {
                    enabled: false
                },
                xAxis: {
                    type: 'datetime',
                    labels: {
                        x: 25,//调节x偏移
                        y: 25,//调节y偏移
                        rotation: -30//调节倾斜角度偏移
                    },
                    dateTimeLabelFormats: {
                        week: '%Y-%m-%d',
                        day: '%Y-%m-%d',
                        month: '%Y-%m',
                        year: '%Y'
                    }
                },
                yAxis: AVG_CHART.yAxises,
                tooltip: {
                    shared: false,
                    crosshairs: false,
                    xDateFormat: "%Y-%m-%d %H:%M:%S"
                },
                plotOptions: {
                    spline: {
                        marker: {
                            radius: 4,
                            lineColor: '#666666',
                            lineWidth: 1
                        },
                        dataGrouping: {
                            enabled: false,
                            dateTimeLabelFormats: {
                                millisecond: ['%Y-%m-%d %H:%M:%S', '%Y-%m-%d %H:%M:%S', '— %H:%M:%S'],
                                second: ['%Y-%m-%d %H:%M:%S', '%Y-%m-%d %H:%M:%S', '— %H:%M:%S'],
                                minute: ['%Y-%m-%d %H:%M', '%Y-%m-%d %H:%M', '— %H:%M'],
                                hour: ['%Y-%m-%d %H:%M', '%Y-%m-%d %H:%M', '— %H:%M'],
                                day: ['%Y-%m-%d', '%Y-%m-%d', '— %Y-%m-%d'],
                                week: ['%Y-%m-%d', '%Y-%m-%d', '— %Y-%m-%d'],
                                month: ['%Y-%M', '%Y', '— %Y-%M'],
                                year: ['%Y', '%Y', '— %Y']
                            }
                        }
                    }
                },
                navigator: {
//                    enabled: false,
                    margin: -40,
                    xAxis: {
                        dateTimeLabelFormats: {
                            day: '%Y-%m-%d',
                            month: '%Y-%m',
                            year: '%Y'
                        }
                    }
                },
                series: AVG_CHART.series,
                exporting: {
                    buttons: {
                        printButton: {
                            enabled: false
                        },
                        exportButton: {
                            menuItems: null,
                            onclick: function () {
                                this.exportChart();
                            },
                            align: 'left',
                            x: 10
                        }
                    },
                    url: 'export-highchart-image.action',
                    filename: locationName + "—" + startTime + " — " + endTime
                }
            });
            if (AVG_CHART.series.length > 0) {
                AVG_CHART.existData = true;
                var chartInfoTable = " <table class='table table-bordered'>" +
                    "<thead>" +
                    "<tr>" +
                    "<th>" + message.curve + "</th>" +
                    "<th>" + message.maximumValue + "（" + message.time + "）</th>" +
                    "<th>" + message.minimumValue + "（" + message.time + "）</th>" +
                    "<th>" + message.fluctuationRange + "</th>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody>" +
                    AVG_CHART.subtitleText +
                    "</tbody>" +
                    "</table>";
                $("#chart-info").html(chartInfoTable);
            }
            if (AVG_CHART.series.length >= 3) {
                window.pnotify(message.tips + "：" + message.tooManyCharts, "warn");
            }
        }
    };
    var MSG = {
        closeable: false,
        showMsg: function (title) {
            var MSG = this;
            MSG.dialog = art.dialog({
                id: "info",
                title: title,
                cancel: false
            });
        },
        closeDialog: function () {
            var MSG = this;
            if (MSG.dialog) {
                MSG.dialog.close();
            }
        }
    }
});

