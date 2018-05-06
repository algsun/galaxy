/**
 *
 *<pre>
 * 降雨量图
 *</pre>
 * @author: li.jianfei
 * @time: 13-3-8 下午5:34
 * @check @wang yunlong 2013-03-12 #2060
 */
$(function () {

    (function () {
        // 覆盖默认 formatNoMatches 配置
        $.fn.select2.defaults.formatNoMatches = function () {
            return message.dragDevice
        };
    })();

    (function () {
        var $deviceSelect = $("#deviceIds");
        $deviceSelect.select2();
    })();

    // 设备选择器
    var deviceSelect = (function () {
        //监测指标多选
        var $deviceSelect = $("#deviceIds");
        return {
            data: function () {
                return $deviceSelect.select2('data');
            },
            // 返回已经选择的设备数据
            val: function () {
                return $deviceSelect.select2('val');
            },
            selected: function () {
                var exitsDevices = [];

                $deviceSelect.find("option:selected").each(function () {
                    exitsDevices.push($(this).val());
                });
                return exitsDevices;
            },
            // 添加选项，并且选中
            appendOption: function (deviceId, deviceName) {
                $deviceSelect.append("<option value=" + deviceId + " selected=selected>" + deviceName + "</option>");
                $deviceSelect.select2();
            },
            // 删除某个选项
            remove: function (deviceId) {
                $("option[value='" + deviceId + "']", $deviceSelect).remove();
                $deviceSelect.select2();
            },
            // 清空选项
            clear: function () {
                $deviceSelect.empty();
                $deviceSelect.select2();
            }
        };
    })();

    // 获取设备id
    var deviceId = $("#deviceId").val();

    $("#add-compare").click(function () {
        var $compare = $("#compare");
        var $compareShowHide = $("#show-hide-compare");
        var $cancelAllCompare = $("#cancel-all-compare").show();
        $compareShowHide.text(message.hide);
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
            $this.text(message.show);
        } else {
            $this.text(message.hide);
        }
    };

    $("#cancel-all-compare").click(function () {
        $("#show-hide-compare").hide();
        $("#compare").hide();
        $(this).hide();
        $("#add-compare").show();

        // 删除对比曲线和设备
        var devices = $("#deviceIds").select2("data");
        $.each(devices, function (index, data) {
            CHART.removeSeries(data.id);
            CHART.renderChart();
        });
        deviceSelect.clear();
    });

    // 设备显示名称, 有名称返回名称，无则返回id
    var deviceDisplayName = function (highChartData) {
        if (highChartData.deviceName) {
            return highChartData.deviceName;
        } else {
            return highChartData.deviceId;
        }
    };
    $.ajaxSetup({traditional: true});

    createChart();
    function createChart() {
        // 获取参数信息
        var deviceId = $("#deviceId").val();
        var deviceIds = deviceSelect.val();
        // 主设备id 在第一个
        deviceIds = [deviceId].concat(deviceIds);

        var dateType = $("input[name='dateType']:checked").val();
        var date = $("#date").val();

        var loadSeries = function (deviceIds, dateType, date, index) {
            if (index >= deviceIds.length) {
                return;
            }

            var id = deviceIds[index];
            $.getJSON("device/" + id + "/light-chart.json", {dateType: dateType, date: date}, function (highChartData) {
                if (index == 0) {
                    CHART.createChart(highChartData);
                    if (!highChartData.hasLightData) {
                        return;
                    }
                } else {
                    CHART.addSeries(highChartData.chartData);
                }

                loadSeries(deviceIds, dateType, date, index + 1);
            });
        };

        loadSeries(deviceIds, dateType, date, 0);

    }

    $("#deviceIds").on("change", function (e) {
        var removedDeviceId = e.removed.id;
        deviceSelect.remove(removedDeviceId);
        CHART.removeSeries(removedDeviceId);
        CHART.renderChart();
        $("#statistics div[data-device='" + removedDeviceId + "']").remove();
    });

    $("#commit").click(function () {
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
                $("#noData").text(message.noData);
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
        removeSeries: function (deviceId) {
            var deleteSeries = [];
            $.each(CHART.series, function (index, serie) {
                if (serie.deviceId == deviceId) {
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
                    resetZoom: message.reset,
                    resetZoomTitle: message.zoomBy,
                    exportButtonTitle: message.export
                }
            });
            var chart = new Highcharts.Chart(options);

            if (CHART.series.length > 0) {
                var sumHtml = "";
                $.each(CHART.series, function (index, serie) {
                    sumHtml += "<tr>" +
                        "<td>" + serie.name.split("-")[0] + "</td>" +
                        "<td>" + serie.sumValue +
                        "</tr>";
                });

                var chartInfoTable = " <table class='table table-bordered'>" +
                    "<thead>" +
                    "<tr>" +
                    "<th>" + message.device + "</th>" +
                    "<th>" + message.sumLight + "(lux·h)</th>" +
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
        // 如果是设备或者从模块
        if ((treeNodes[0].type == "device" || treeNodes[0].type == "slaveModule")
            && (treeNodes[0].nodeId != deviceId) // 不是自己
            && ($.inArray(41, treeNodes[0].sensorPhysicalIds)) != -1) { //有光照(41)监测指标
            return true;
        }
        return false;
    });

    deviceTree.drop(null, function (e, treeId, treeNode) {
        if ($.inArray(treeNode.nodeId, deviceSelect.val()) != -1) {
            art.dialog({
                title: message.tips,
                content: message.equipmentSelected
            });
            return;
        }

        if ($(e.target).parents("#s2id_deviceIds").length == 1) {
            deviceSelect.appendOption(treeNode.nodeId, treeNode.name);

            var dateType = $("input[name='dateType']:checked").val();
            var date = $("#date").val();
            $.getJSON("device/" + treeNode.nodeId + "/light-chart.json", {
                dateType: dateType,
                date: date
            }, function (highChartData) {
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
BluePlanet.zoneDevicePath.redirectWhenDeviceChange(function (node) {
    var LIGHT_SENSOR_PHYSICAL_ID = 41;
    if ($.inArray(LIGHT_SENSOR_PHYSICAL_ID, node.sensorPhysicalIds) == -1) {
        art.dialog({title: message.tips, content: message.noLight});
        return false;
    }

    return 'device/' + node.id + "/light-chart";
});
