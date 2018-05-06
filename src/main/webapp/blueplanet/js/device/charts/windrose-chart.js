/**
 * 风向玫瑰图
 *
 * @author: Wang yunlong
 * @time: 13-3-8 下午5:34
 * @dependence highcharts, highcharts.exporting, jquery, moment, artdialog
 * @check @li.jianfei 2013-3-13 #2078
 */
$(function () {
    (function () {
        //监测指标多选
        $("#sensorPhysicalId").select2();
        $("#target").select2();
    })();
    (function () {
        //提交表单  查询风向玫瑰图数据时间验证
        $("#query-windrose-chart").click(function () {
            if ($("#time").val().length < 1) {
                art.dialog({
                    id: "info",
                    title: "消息提示",
                    content: "在查询前请选择时间"
                });
                return false;
            } else {
                return true;
            }
        });
        //选择新的时间类型后 时间自动调整
        $("input[name='timeType']").change(function () {
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
            var $timeInput = $("#time");
            var value = DateUtil.formatDate($timeInput.val(), momentFormat,'windrose');
            $timeInput.val(value);
            $timeInput.unbind("click.time");
            //时间选择器
            $timeInput.bind("click.time", function () {
                WdatePicker({
                    dateFmt: my97Format,
                    el: $timeInput[0],
                    maxDate: '%y-%M-{%d-1}'
                });
            });
        }).change();
    })();
    //设备切换
    (function () {
        var BluePlanet = App("blueplanet");
        BluePlanet.zoneDevicePath.redirectWhenDeviceChange(function (node) {
            var WIND_SENSOR_PHYSICAL_ID = 48;
            if ($.inArray(WIND_SENSOR_PHYSICAL_ID, node.sensorPhysicalIds) == -1) {
                art.dialog({
                    title: '提示',
                    content: '该设备无风向玫瑰图'
                });
                return false;
            }

            return 'device/' + node.id + "/windrose-chart";
        });
    })();
    (function () {
        Highcharts.setOptions({
            lang: {
                downloadJPEG: '导出为JPEG',
                downloadPDF: '导出为PDF',
                downloadPNG: '导出为PNG',
                downloadSVG: '导出为SVG',
                exportButtonTitle: "导出图片"
            }
        });
        var windcalm = $("#windcalm").text();
        var windroseText = $("#windrose-text").text();
        var hasData = $("#has-data").text();
        if (hasData == "0") {

            Highcharts.data({
                table: 'freq',
                startRow: 1,
                endRow: 17,
                endColumn: 7,
                complete: function (options) {
                    // Some further processing of the options
                    options.series.reverse(); // to get the stacking right


                    // Create the chart
                    window.chart = new Highcharts.Chart(Highcharts.merge(options, {

                        chart: {
                            renderTo: 'windrose-chart',
                            polar: true,
                            type: 'line'
                        },
                        credits: {
                            enabled: false
                        },

                        title: {
                            text: windroseText
                        },
                        subtitle: {
                            text: '静风率：' + windcalm + '%',
                            x: 100
                        },

                        legend: {
                            reversed: true,
                            align: 'right',
                            verticalAlign: 'top',
                            y: 100,
                            layout: 'vertical'
                        },

                        xAxis: {
                            tickmarkPlacement: 'on'
                        },

                        yAxis: {
                            min: 0,
                            endOnTick: false,
                            showLastLabel: true,
                            labels: {
                                formatter: function () {
                                    return this.value;
                                }
                            }
                        },

                        tooltip: {
                            enabled: false,
                            valueSuffix: '%',
                            shared: true
                        },

                        plotOptions: {
                            series: {
                                stacking: 'normal',
                                shadow: false,
                                groupPadding: 0,
                                pointPlacement: 'on'
                            }
                        },
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
                            filename: windroseText
                        }
                    }));
                }
            });
        }
    })();
});
