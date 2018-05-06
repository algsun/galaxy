/**
 *
 * 光照
 *
 * @author: liuzhu
 * @time: 2014-7-4
 */
$(function () {

    (function () {
        // 覆盖默认 formatNoMatches 配置
        $.fn.select2.defaults.formatNoMatches = function () {
            return "将位置点拖入此处";
        };
    })();

    (function () {
        var $locationSelect = $("#locationIds");
        $locationSelect.select2();
    })();

    // 位置点选择器
    var locationSelect = (function () {
        //监测指标多选
        var $locationSelect = $("#locationIds");
        return {
            data: function () {
                return $locationSelect.select2('data');
            },
            // 返回已经选择的位置点数据
            val: function () {
                return $locationSelect.select2('val');
            },
            selected: function () {
                var exitsDevices = [];

                $locationSelect.find("option:selected").each(function () {
                    exitsDevices.push($(this).val());
                });
                return exitsDevices;
            },
            // 添加选项，并且选中
            appendOption: function (locationId, locationName) {
                $locationSelect.append("<option value=" + locationId + " selected=selected>" + locationName + "</option>");
                $locationSelect.select2();
            },
            // 删除某个选项
            remove: function (locationId) {
                $("option[value='" + locationId + "']", $locationSelect).remove();
                $locationSelect.select2();
            },
            // 清空选项
            clear: function () {
                $locationSelect.empty();
                $locationSelect.select2();
            }
        };
    })();

    // 获取位置点id
    var locationId = $("#locationId").val();

    $("#add-compare").click(function () {
        var $compare = $("#compare");
        var $compareShowHide = $("#show-hide-compare");
        var $cancelAllCompare = $("#cancel-all-compare").show();
        $compareShowHide.text("隐藏");
        $compareShowHide.show();
        $cancelAllCompare.show();
        $compare.show();
        $(this).hide();
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
            $this.text("显示");
        } else {
            $this.text("隐藏");
        }
    };

    $("#cancel-all-compare").click(function () {
        $("#show-hide-compare").hide();
        $("#compare").hide();
        $(this).hide();
        $("#add-compare").show();

        // 删除对比曲线和设备
        var location = $("#locationIds").select2("data");
        $.each(location, function (index, data) {
            CHART.removeSeries(data.id);
            CHART.renderChart();
        });
        locationSelect.clear();
    });

    $.ajaxSetup({traditional: true});

    createChart();
    function createChart() {
        // 获取参数信息
        var locationId = $("#locationId").val();
        var locationIds = locationSelect.val();
        // 主设备id 在第一个
        locationIds = [locationId].concat(locationIds);

        var dateType = $("input[name='dateType']:checked").val();
        var date = $("#date").val();

        var loadSeries = function (locationIds, dateType, date, index) {
            if (index >= locationIds.length) {
                return;
            }

            var id = locationIds[index];
            $.getJSON("location/" + id + "/light-chart.json", {dateType: dateType, date: date}, function (highChartData) {
                if (index == 0) {
                    CHART.createChart(highChartData);
                    if (!highChartData.hasLightData) {
                        return;
                    }
                } else {
                    CHART.addSeries(highChartData.chartData);
                }

                loadSeries(locationIds, dateType, date, index + 1);
            });
        };

        loadSeries(locationIds, dateType, date, 0);

    }

    $("#locationIds").on("change", function (e) {
        var removedDeviceId = e.removed.id;
        locationSelect.remove(removedDeviceId);
        CHART.removeSeries(removedDeviceId);
        CHART.renderChart();
        $("#statistics div[data-device='" + removedDeviceId + "']").remove();
    });


    $("#commit").click(function () {
        var date = $("#date").val();
        if (date.length < 1) {
            art.dialog({
                id: "info",
                title: "消息提示",
                content: "在查询前请选择时间"
            });
            return;
        }
        createChart();
    });

    var CHART = {
        data: {},
        yAxis: [],
        series: [],
        createChart: function (data) {
            CHART.data = {};
            CHART.yAxis = [];
            CHART.series = [];

            CHART.data = data;

            if (!data.hasLightData) {
                $("#noData").text("暂无数据");
                $("#cancel-all-compare").trigger("click");
                $("#chart").hide();
                $("#add-compare").hide();
                $("#statistics").empty();
                return;
            } else {
                $("#noData").text("");
                $("#chart").show();
                if (!$("#cancel-all-compare").is(":visible")) {
                    $("#add-compare").show();
                } else {
                    $("#add-compare").hide();
                }
            }
            CHART.createYAxis();
            CHART.createSeries();
            CHART.renderChart();

        },
        createYAxis: function () {
            var temp = {
                sensorPhysicalId: CHART.data.chartData.sensorPhysicalId,
                title: {
                    text: CHART.data.chartData.cnName + "(" + CHART.data.chartData.units + ")"
                },
                offset: 0,
                labels: {
                    formatter: function () {
                        return this.value + CHART.data.chartData.units;
                    }
                }
            };
            CHART.yAxis.push(temp);

        },
        createSeries: function () {
            CHART.series.push(CHART.data.chartData);
        },
        addSeries: function (option) {
            CHART.series.push(option);
            CHART.renderChart();
        },
        removeSeries: function (locationId) {
            var deleteSeries = [];
            $.each(CHART.series, function (index, serie) {
                if (serie.locationId == locationId) {
                    deleteSeries.push(serie);
                }
            });
            $.each(deleteSeries, function (index, serie) {
                CHART.series = _.without(CHART.series, serie);
            });
        },
        getTickInterval: function () {
            // 返回 x 轴间隔
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
        },
        renderChart: function () {
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
                    labels: {
                        x: 25,//调节x偏移
                        y: 25,//调节y偏移
                        rotation: -30//调节倾斜角度偏移
                    },
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
                series: CHART.series,
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

            if (CHART.series.length > 0) {
                var sumHtml = "";
                $.each(CHART.series, function (index, serie) {
                    var locationName = serie.name;
                    sumHtml += "<tr>" +
                        "<td>" + serie.name.split("-")[0] + "</td>" +
                        "<td>" + serie.sumValue +
                        "</tr>";
                });

                var chartInfoTable = " <table class='table table-bordered'>" +
                    "<thead>" +
                    "<tr>" +
                    "<th>设备</th>" +
                    "<th>累计光照总和(lux·h)</th>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody>" +
                    sumHtml +
                    "</tbody>" +
                    "</table>";
                $("#statistics").html(chartInfoTable);
            }
        }
    };
    var deviceTree = App(window.BLUEPLANET).deviceTree;
    deviceTree.beforeDrag(function (treeId, treeNodes) {
        if ((treeNodes[0].type == "location") && (treeNodes[0].locationId != locationId) // 不是自己
            && ($.inArray(41, treeNodes[0].sensorPhysicalIds)) != -1) { //有光照(41)监测指标
            return true;
        }
        return false;
    });

    deviceTree.drop(null, function (e, treeId, treeNode) {
        if ($.inArray(treeNode.locationId, locationSelect.val()) != -1) {
            art.dialog({
                title: "提示",
                content: "该设备已选择"
            });
            return;
        }

        if ($(e.target).parents("#s2id_locationIds").length == 1) {
            locationSelect.appendOption(treeNode.locationId, treeNode.locationName);
            var dateType = $("input[name='dateType']:checked").val();
            var date = $("#date").val();
            $.getJSON("location/" + treeNode.locationId + "/light-chart.json", {dateType: dateType, date: date}, function (highChartData) {
                CHART.addSeries(highChartData.chartData);
            });
        }
    });
});

$(function () {
    //选择新的时间类型后 时间自动调整
    $("input[name='dateType']").change(function () {
        var $this = $(this);
        if (!$this.is(':checked')) {
            return;
        }

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
BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
    return 'location/' + location.id + "/light-chart";
});
