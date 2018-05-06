/**
 *
 *<pre>
 * 基础曲线图
 *</pre>
 * @author: Wang yunlong
 * @time: 13-3-8 下午5:00
 * @dependence highstock, highcharts.exporting, jquery, moment, underscore, artdialog, pnotify
 * @check @li.jianfei 2013-3-13 #2055
 */
$(function () {


    $(function () {
        //二级导航
        var BluePlanet = App("blueplanet");
        BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
            return 'location/' + location.id + "/basic-chart";
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
//            if (treeNodes[0].type == "device" || treeNodes[0].type == "slaveModule") {
//                return true;
//            }
            if (treeNodes[0].type === "location") {
                return true;
            }
            return false;
        });
        deviceTree.drop("pre-selection", function (e, treeId, treeNode) {
            if ($.inArray(treeNode.locationId, findExistDevices()) == -1) {
                var $compareLocation = $(e.target);
                $compareLocation.text(treeNode.name).attr("compare-location-id", treeNode.locationId);
                var $sensorOptions = "";
                var Blueplanet = App(window.BLUEPLANET);
                var sensorinfos = Blueplanet.site.sensorinfos;
                $.each(treeNode.sensorPhysicalIds, function (index, sensorPhysicalId) {
                    $sensorOptions += "<option value='" + sensorPhysicalId + "'>" + sensorinfos[sensorPhysicalId].cnName + "</option> ";
                });
                $compareLocation.parent().parent().find("select.sensor-physicalids").html($sensorOptions);
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
        if (!BASIC_CHART.existData) {
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
    //勾选自动查询
    $("#senior").change(function () {
        $("#query-basic-chart").click();
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
        var senior = $("#senior").attr("checked") == "checked";
        $.each(sensorinfoes, function (index, sensorinfoe) {
            var temp = [sensorinfoe];
            getCompareRenderChartData($location, temp, startTime, endTime, senior);
        });
    };
    var getCompareRenderChartData = function ($location, sensorinfoes, startTime, endTime, senior) {
        $.post("location/" + $location.attr("compare-location-id") + "/basic-chart.json", {
            locationName: $location.text(),
            sensorinfoes: sensorinfoes,
            startTime: startTime,
            endTime: endTime
        }, function (data) {
            MSG.closeDialog();
            if (data.hasBasicData) {
                $("#advanceTip").show();
                $("#Yaxis").show();
                $("#checkboxSenior").show();
//                BASIC_CHART.deleteLines($location.attr("compare-location-id"));
                $.each(data.chartList, function (index, ld) {
                    BASIC_CHART.addLines(index, ld, senior);
                });
                BASIC_CHART.createSubtitleText();
                BASIC_CHART.renderChart();
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
        BASIC_CHART.deleteLines($location.attr("compare-location-id"));
        BASIC_CHART.createSubtitleText();
        BASIC_CHART.renderChart();
    });
    //取消所有对比
    $("#cancel-all-compare").click(function () {
        $(".compare-column").not(".compare-column-template").remove();
        $("#show-hide-compare").hide();
        $(this).hide();
        $("#add-compare").show();
        MSG.showMsg(message.restoringChar);
        queryBasicData();
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
    var basicData = function (locationId, locationName, sensorinfoes, startTime, endTime) {
        if (startTime == undefined) {
            startTime = null;
        }
        if (endTime == undefined) {
            endTime = null;
        }
        if (sensorinfoes == undefined) {
            sensorinfoes = [$("#sensorPhysicalId").val()];
        }
        $.post("location/" + locationId + "/basic-chart.json", {
            locationName: locationName,
            sensorinfoes: sensorinfoes,
            startTime: startTime,
            endTime: endTime
        }, function (data) {
            MSG.closeDialog();
            if (data.hasBasicData) {
                $("#advanceTip").show();
                $("#Yaxis").show();
                $("#basic-chart").empty();
                BASIC_CHART.execute(data);
            } else {
                $("#advanceTip").hide();
                $("#checkboxSenior").hide();
                $("#Yaxis").hide();
                $("#basic-chart").html("<h4>" + message.noData + "</h4>");
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
            exportButtonTitle: "导出图片",
            resetZoom: "重置",
            resetZoomTitle: "按1:1缩放",
            rangeSelectorFrom: "开始",
            rangeSelectorTo: "结束",
            rangeSelectorZoom: ""//为什么是空 去掉它

        }
    });
    $.ajaxSetup({traditional: true});
    basicData(locationId, locationName, getSelect2Values($("#sensorPhysicalId")), $("#start-time").val(), $("#end-time").val());
    //查询数据
    var queryBasicData = function () {
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
        var sensorinfoes = getSelect2Values($("#sensorPhysicalId"));
        if (sensorinfoes.length < 1) {
            art.dialog({
                id: 'info',
                title: message.tips,
                content: message.selectSensor
            });
            return;
        }
        basicData(locationId, locationName, sensorinfoes, startTime, endTime);
    };
    $("#query-basic-chart").click(function () {
//        $("#Yaxis").show();
//        $("#checkboxSenior").show();
        $("#YaxisName").html("");
        BASIC_CHART.yAxises = [];
        BASIC_CHART.series = [];
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
    //TODO 修改交互问题
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
    var BASIC_CHART = {
        data: {},
        yAxises: [],
        series: [],
        titleText: '',
        chart: {},
        subtitleText: '',
        titleMargin: 15,
        existData: false,
        execute: function (data) {
            var BASIC_CHART = this;
            BASIC_CHART.data = data;
            BASIC_CHART.series = data.chartList;
            BASIC_CHART.createTitleText();
            BASIC_CHART.createYAxises();
            //设置曲线的y轴
            $.each(BASIC_CHART.series, function (i, d) {
                BASIC_CHART.setSeriesYAxis(d);
            });
            BASIC_CHART.createSubtitleText();
            BASIC_CHART.renderChart();
        },

        //用来展示图表中数据的最大值、最小值以及波动范围
        createSubtitleText: function () {
            var BASIC_CHART = this;
            BASIC_CHART.subtitleText = '';
            BASIC_CHART.titleMargin = 15;
            BASIC_CHART.titleMargin += BASIC_CHART.series.length * 14;
            $.each(BASIC_CHART.series, function (index, chart) {
                BASIC_CHART.subtitleText += "<tr>" +
                    "<td>" + chart.name + "</td>" +
                    "<td>" + chart.maxValue.data + chart.units + " (" + moment(chart.maxValue.time).format("YYYY-MM-DD HH:mm:ss") + ")" + "</td>" +
                    "<td>" + chart.minValue.data + chart.units + " (" + moment(chart.minValue.time).format("YYYY-MM-DD HH:mm:ss") + ")" + "</td>" +
                    "<td>" + (chart.maxValue.data - chart.minValue.data).toFixed(chart.sensorPrecision) + chart.units + "</td>" +
                    "</tr>";
            });
        },
        deleteLines: function (locationId) {
            var BASIC_CHART = this;
            var deleteSeries = [];
            $.each(BASIC_CHART.series, function (index, serie) {
                if (serie.locationId == locationId) {
                    deleteSeries.push(serie);
                }
            });
            $.each(deleteSeries, function (index, serie) {
                BASIC_CHART.series = _.without(BASIC_CHART.series, serie);
            });
            //删除多余的y轴
            var seriesSensorinfoes = BASIC_CHART.getSeriesId();
            var deleteYAxises = [];
            $.each(BASIC_CHART.yAxises, function (index, yAxis) {
                if ($.inArray(yAxis.sensorPhysicalId, seriesSensorinfoes) == -1) {
                    deleteYAxises.push(yAxis);
                }
            });
            $.each(deleteYAxises, function (index, yAxis) {
                BASIC_CHART.yAxises = _.without(BASIC_CHART.yAxises, yAxis);
            });
        },
        addLines: function (index, ld, senior) {
            //如果没有相应y轴 则添加
            var yAxis;
            if (senior) {
                if (BASIC_CHART.getChartsYAxisesSensorPhysicalIds().length == 0) {
                    yAxis = BASIC_CHART.createYAxis(ld.sensorPhysicalId, ld.units, ld.units);
                    BASIC_CHART.yAxises.push(yAxis);
                }
                if ($.inArray(ld.units, BASIC_CHART.getChartsYAxisesUnits()) == -1) {
                    yAxis = BASIC_CHART.createYAxis(ld.sensorPhysicalId, ld.units, ld.units);
                    BASIC_CHART.yAxises.push(yAxis);
                }
            } else {
                if ($.inArray(ld.sensorPhysicalId, BASIC_CHART.getChartsYAxisesSensorPhysicalIds()) == -1) {
                    yAxis = BASIC_CHART.createYAxis(ld.sensorPhysicalId, ld.yText, ld.units);
                    BASIC_CHART.yAxises.push(yAxis);
                }
            }
            BASIC_CHART.setSeriesYAxis(ld);
            BASIC_CHART.series.push(ld);
        },
        createTitleText: function () {
            var BASIC_CHART = this;
            BASIC_CHART.titleText = message.basicCurveChart;
        },
        //设置曲线的y轴
        setSeriesYAxis: function (data) {
            $.each(BASIC_CHART.yAxises, function (index, yAxis) {
                if (yAxis.sensorPhysicalId == data.sensorPhysicalId) {
                    data.yAxis = index;
                }
            });
        },

        //获取图表中曲线的监测指标
        getSeriesId: function () {
            var BASIC_CHART = this;
            var sensorinfoes = [];
            $.each(BASIC_CHART.series, function (index, serie) {
                if ($.inArray(serie.sensorPhysicalId, sensorinfoes) == -1) {
                    sensorinfoes.push(serie.sensorPhysicalId);
                }
            });
            return sensorinfoes;
        },
        //获取已经存在的监测指标的y轴标识（sensroPhysicalId）
        getChartsYAxisesSensorPhysicalIds: function () {
            var BASIC_CHART = this;
            var ids = [];
            $.each(BASIC_CHART.yAxises, function (index, yAxis) {
                ids.push(yAxis.sensorPhysicalId);
            });
            return ids;
        },
        getChartsYAxisesUnits: function () {
            var BASIC_CHART = this;
            var units = [];
            $.each(BASIC_CHART.yAxises, function (index, yAxis) {
                units.push(yAxis.units);
            });
            return units;
        },
        //创建初始化的y轴
        createYAxises: function () {
            var BASIC_CHART = this;
            BASIC_CHART.yAxises = [];
            var sensorPhysicalIds = [];
            $.each(BASIC_CHART.data.chartList, function (index, cd) {
                if ($.inArray(cd.sensorPhysicalId, sensorPhysicalIds) == -1) {
                    BASIC_CHART.yAxises.push(BASIC_CHART.createYAxis(cd.sensorPhysicalId, cd.yText, cd.units));
                    sensorPhysicalIds.push(cd.sensorPhysicalId);
                }
            });
        },
        //创建一个y轴
        createYAxis: function (sensorPhysicalId, yText, units) {
            //y轴分两端展示
            var opposite = (BASIC_CHART.yAxises.length % 2 == 1);
//            var opposite = false;
            var labelsAlign = 'right';
            var labelsX = -8;
            if (opposite) {
                labelsAlign = 'left';
                labelsX = 7;
            }


            var yAxis = {
                sensorPhysicalId: sensorPhysicalId,
                units: units,
                title: {
                    text: yText
                },
                lineWidth: 2,
                labels: {
                    align: labelsAlign,
                    overflow: null,
                    style: null,
                    x: labelsX,
                    formatter: function () {
                        var sensorName = yText.substr(0, yText.lastIndexOf('('));
                        return '<a  href="javascript:YaxisClick(' + sensorPhysicalId + ',' + '\'' + sensorName + '\')">' +
                            this.value + '</a>';
                    }
                },
                opposite: opposite
            };
            return yAxis;
        },
        renderChart: function () {
            var BASIC_CHART = this;
            var startTime = $("#start-time").val();
            var endTime = $("#end-time").val();
            BASIC_CHART.chart = new Highcharts.StockChart({
                chart: {
                    renderTo: 'basic-chart',
                    type: 'spline',
                    zoomType: 'xy'
                },
                title: {
                    text: message.curveChart
                },
                legend: {
                    y: -70,
                    enabled: true
                },
                rangeSelector: {
                    buttons: [],
                    inputDateFormat: '%Y-%m-%d %H:%M',
                    inputEditDateFormat: '%Y-%m-%d %H:%M',
                    inputBoxWidth: '150px',
                    inputBoxHeight: '18px',
                    inputStyle: {
                        letterSpacing: '-1px'
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
                        millisecond: '%Y-%m-%d %H:%M:%S',
                        second: '%Y-%m-%d %H:%M:%S',
                        minute: '%Y-%m-%d %H:%M',
                        hour: '%Y-%m-%d %H:%M',
                        week: '%Y-%m-%d',
                        day: '%Y-%m-%d',
                        month: '%Y-%m',
                        year: '%Y'
                    },
                    ordinal: false
                },
                yAxis: BASIC_CHART.yAxises,
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
                series: BASIC_CHART.series,
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
            var max = BASIC_CHART.chart.yAxis[0].getExtremes().max;
            var min = BASIC_CHART.chart.yAxis[0].getExtremes().min;
            $("#maxInputButtons").val(max);
            $("#minInputButtons").val(min);

            if (BASIC_CHART.series.length > 0) {
                BASIC_CHART.existData = true;
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
                    BASIC_CHART.subtitleText +
                    "</tbody>" +
                    "</table>";
                $("#chart-info").html(chartInfoTable);
            }
            if (BASIC_CHART.series.length >= 3) {
                window.pnotify(message.tips + ":" + message.tooManyCharts, "warn");
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
    };

    $(function () {
        //增量
        var num = 10;
        //最大值、最小值
        var max = 0, min = 0;
        //原最大值、最小值
        var oldMax = 0, oldMin = 0;

        var arrayMax = [];
        var arrayMin = [];
        var arrayMax1 = [];
        var arrayMin1 = [];

        // 图形Y轴设置
//        $("#Yaxis").hide();
        $("#advanceTip").hide();

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
            var minInputButtons = $("#minInputButtons").val();
            if (!maxInputValue) {
                return;
            }
            if (!minInputButtons) {
                return;
            }

            var YaxisSensorId = $("#YaxisSensorId").val();
            var chartYAxis = BASIC_CHART.chart.yAxis;
            for (var i = 0; i < chartYAxis.length - 1; i++) {
                if (chartYAxis[i].options.sensorPhysicalId == YaxisSensorId) {
                    chartYAxis[i].setExtremes(minInputButtons, maxInputValue);
                }
            }
        });

        //通过输入框设置Y轴最小值
        $("#minInputButtons").blur(function () {
            var maxInputValue = $("#maxInputButtons").val();
            var minInputButtons = $("#minInputButtons").val();
            if (!maxInputValue) {
                return;
            }
            if (!minInputButtons) {
                return;
            }
            if (minInputButtons < -100) {
                art.dialog({
                    title: message.tips,
                    content: message.yAxisMinimumValue,
                    follow: document.getElementById("minInputButtons"),
                    time: 3000
                });
                return;
            }
            var YaxisSensorId = $("#YaxisSensorId").val();
            var chartYAxis = BASIC_CHART.chart.yAxis;
            for (var i = 0; i < chartYAxis.length - 1; i++) {
                if (chartYAxis[i].options.sensorPhysicalId == YaxisSensorId) {
                    chartYAxis[i].setExtremes(minInputButtons, maxInputValue);
                }
            }
        });

        //同时增加Y轴的最大值和最小值
        $("#yAxisUp").click(function () {
            setYaxis(5);
            resetYAxisText()
        });

        //同时减少Y轴的最大值和最小值
        $("#yAxisDomn").click(function () {
            setYaxis(6);
            resetYAxisText();
        });

        // 设置Y轴大小值
        function setYaxis(state) {

            var chartYAxis = BASIC_CHART.chart.yAxis;
            var YaxisSensorId = $("#YaxisSensorId").val();
            for (var i = 0; i < chartYAxis.length - 1; i++) {
                if (chartYAxis[i].options.sensorPhysicalId != YaxisSensorId) {
                    continue;
                }
                var oldMaxYaxis = chartYAxis[i].getExtremes().dataMax * 1;
                var oldMinYaxis = chartYAxis[i].getExtremes().dataMin * 1;
                max = chartYAxis[i].getExtremes().max * 1;
                min = chartYAxis[i].getExtremes().min * 1;
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
                    max = max / 2;
                    min = min / 2;
                }
                if (state == 6 && (max < oldMaxYaxis || min > oldMinYaxis)) {
                    window.pnotify(message.tips + "：" + message.downCannotExceed, "warn");
                    return false;
                }
                chartYAxis[i].setExtremes(min, max);
                if (state == 1) {
                    var arrayLen = arrayMax.push(( chartYAxis[i].getExtremes().max * 1) - max1);
                } else if (state == 3) {
                    var arrayLen = arrayMin.push(( chartYAxis[i].getExtremes().min * 1) - min1);
                } else if (state == 5) {
                    var arrayLen = arrayMax1.push(( chartYAxis[i].getExtremes().max * 1));
                    var arrayLen = arrayMin1.push(( chartYAxis[i].getExtremes().min * 1));
                }

            }


        }

        function resetYAxisText() {
            var max = BASIC_CHART.chart.yAxis[0].getExtremes().max;
            var min = BASIC_CHART.chart.yAxis[0].getExtremes().min;
            $("#maxInputButtons").val(max);
            $("#minInputButtons").val(min);
        }

        //恢复默认设置
        $("#defaultSettings").click(function () {
            $("#maxInputButtons").val("");
            $("#minInputButtons").val("");
            BASIC_CHART.yAxises = [];
            BASIC_CHART.series = [];
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

    });
});

