<#--
均值比较曲线图
-->
<#assign title=locale.getStr("blueplanet.location.meanChartTitle")>


<#macro head>
    <#include "/common/pages/common-tag.ftl"/>
<link rel="stylesheet" href="../assets/select2/3.2/select2.css">
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
    <@linkTag "css/location/charts/basic-chart.css"/>
</#macro>


<#assign tabIndex = 9>
<#macro content>

<div id="location-id" class="hide">${location.id}</div>
<div id="chart-data" class="hide">${data}</div>
<div id="location-name" class="hide" compare-location-id="${location.id}">${location.locationName!location.id}</div>
<div style="min-width: 1000px;">
    <div class="form-inline well p-t-10" style="margin:0 auto;">
        <div class="f-l m-l-10 ">
            <label class="sensorinfo-select-label">${locale.getStr("common.monitoringIndicators")}</label>
            <select id="sensorPhysicalId" multiple="multiple" style="width:250px;">
                <#list sensorinfoes as sensorinfo>
                    <option value="${sensorinfo.sensorPhysicalid}"
                            <#if sensorinfo_index==0>selected="selected" </#if>>${sensorinfo.cnName}
                    </option>
                </#list>
            </select>
        </div>
        <div class="f-l m-l-10">
            <label>${locale.getStr("common.startDate")}</label>
            <input class="input-medium Wdate" id="start-time" type="text" name="startTime"
                   onfocus="var endTime=$dp.$('end-time');WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'end-time\')}'})"
                   value="${startTime?string("yyyy-MM-dd HH:mm:ss")!}"/>
        </div>
        <div class="f-l m-l-10">
            <label>${locale.getStr("common.endDate")}</label>
            <input class="input-medium Wdate" id="end-time" type="text" name="endTime"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59',minDate:'#F{$dp.$D(\'start-time\')}'})"
                   value="${endTime?string("yyyy-MM-dd HH:mm:ss")!}"/>
        </div>
        <div class="f-l m-l-10">
            <button id="query-avg-chart" class="btn">${locale.getStr("common.select")}</button>
            <span id="add-compare" class="gray">${locale.getStr("blueplanet.location.addComparision")}</span>
            <span id="cancel-all-compare" class="gray">${locale.getStr("blueplanet.location.cancelAll")}</span>
            <span id="show-hide-compare" class="gray m-l-5">${locale.getStr("blueplanet.realtimeData.hide")}</span>
        </div>
    </div>

    <div id="compare" class="m-b-10 hide compare-style">
        <div class="f-l compare-column hide compare-column-template">
            <div class="f-l compare-column-node">
                <label class="m-0" style="padding-top:3px;">${locale.getStr("blueplanet.location.selectLocation")}</label>
                <span class="compare-location input uneditable-input" compare-location-id="">${locale.getStr("blueplanet.location.moveLocation")}</span>
            </div>
            <div class="f-l compare-column-sensorinfo">
                <label class="sensorinfo-select-label m-0">${locale.getStr("blueplanet.location.compareMonitoring")}</label>
                <select class="sensor-physicalids" multiple="multiple" style="width:300px;">
                </select>
            </div>
            <div class="f-l compare-column-ok">
                <button class="btn btn-small">${locale.getStr("blueplanet.location.compare")}</button>
            </div>
        </div>
    </div>
    <div class="row-fluid m-t-10">
        <div class="span12">
            <div id="avg-chart" style="padding-right: 10px"></div>
        </div>
    </div>
    <div class="clear"></div>
</div>
<div class="row-fluid m-t-10">
    <div id='chart-info' class="span8"></div>
    <div class="span5"></div>
</div>
</#macro>


<#macro script>

<script type="text/javascript" src="../assets/select2/3.2/select2.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/lang/zh-cn.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/highstock.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/underscore/1.4.2/underscore-min.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="js/pnotify.js"></script>
    <@scriptTag "js/location/charts/average-compare.js"/>

<script type="text/javascript">
    $(function () {

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
                rangeSelectorFrom: message.start,
                rangeSelectorTo: message.end,
                rangeSelectorZoom: ""//为什么是空 去掉它

            }
        });

        var data = $("#chart-data").val();
        var AVG_CHART = {
            data: data,
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
                        inputDateFormat: '%Y-%m-%d %H:%M',
                        inputEditDateFormat: '%Y-%m-%d %H:%M',
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
                        filename: startTime + " — " + endTime
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
    });
</script>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
        return 'location/' + location.id + "/average-compare";
    });
</script>
</#macro>