<#--
均峰值

@author wang.geng
@date 2014-4-16
-->
<#assign title=locale.getStr("blueplanet.location.valueTitle")>
<#include "/common/pages/common-tag.ftl">


<#macro head>
</#macro>


<#assign tabIndex = 8>
<#include "../../device/device-helper.ftl">
<#macro content>
<div class="span12">
    <div class="m-b-10 form-inline well well-small" style="height: 30px">
        <input id="locationId" type="hidden" value="${locationId!}"/>
        <input id="location-name" type="hidden" value="${location.locationName!}"/>
        <input id="dateType" type="hidden" value="${dateType!}"/>
        <label>${locale.getStr("common.startDate")}</label>
        <input id="startDate" class="input-small" name="startDate" type="text"
               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
               value="${(startDate?string("yyyy-MM-dd"))!}"/>
        <label>${locale.getStr("common.endDate")}</label>
        <input id="endDate" class="input-small" name="endDate" type="text"
               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
               value="${(endDate?string("yyyy-MM-dd"))!}"/>
        <button id="commit" class="btn">${locale.getStr("common.select")}</button>
        <span id="exportButton" class="btn m-l-20">
            <i class="icon-download-alt"></i>${locale.getStr("blueplanet.location.exportExcel")}
        </span>
    </div>
</div>
<h4 id="noData"></h4>
<div id="chartList">
</div>
<div class="hide">
    <div id="chartTemp">
        <div class="m-t-20 m-l-10 f-l">
            <div class="span12">
                <div data-chart></div>
            </div>
        </div>
    </div>
</div>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
    <@scriptTag "js/date-format.js"/>
<script type="text/javascript">
    $(function () {
        var locationId = $("#locationId").val();
        var locationName = $("#location-name").text();
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();

        //进入均峰值页面，执行方法，获取初始化图表，默认当月
        getJSONData(startDate, endDate, locationId);

        //给查询按钮绑定AJAX查询，查询图表数据
        $(document).on('click', '#commit', function () {
            var start = $("#startDate").val();
            var end = $("#endDate").val();
            if (start.length < 1) {
                art.dialog({
                    id: "info",
                    title: message.tips,
                    content: message.selectStartTime
                });
                return;
            }
            if (end.length < 1) {
                art.dialog({
                    id: "info",
                    title: message.tips,
                    content: message.selectEndTime
                });
                return;
            }
            getJSONData(start, end, locationId);
        });

        // 导出按钮点击
        $(document).on('click', '#exportButton', function () {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            // 导出等待页面链接
            var url = "location/" + locationId + "/waitingForExport?startDate=" + startDate + "&endDate=" + endDate;
            var basePath = $("base").attr("href");
            if (basePath) {
                url = basePath + url;
            }
            // 打开等待页面
            window.open(url, "blank", "height=400, width=400");
        });
    });

    var createHighChart = function (locationAvgdatas) {
        var haveData = false;
        var startTime = $("#startDate").val();
        var endTime = $("#endDate").val();
        var locationName = $("#location-name").val();
        $.each(locationAvgdatas, function (index, locationAvgdata) {
            if (locationAvgdata.hasData) {
                haveData = true;
                var $chartDiv = $("#chartTemp").children().last().clone();
                $("#chartList").append($chartDiv);
                var ranges = getDatas(locationAvgdata, "ranges");
                var averages = getDatas(locationAvgdata, "averages");
                $("#dateType").val(locationAvgdata.dateType);
                Highcharts.setOptions({
                    global: {
                        useUTC: false
                    }
                });
                Highcharts.setOptions({
                    lang: {
                        exportButtonTitle: message.export,
                        contextButtonTitle: message.export,
                        downloadJPEG: '导出为JPEG',
                        downloadPDF: '导出为PDF',
                        downloadPNG: '导出为PNG',
                        downloadSVG: '导出为SVG',
                        resetZoom: message.reset,
                        resetZoomTitle: message.zoomBy,
                        rangeSelectorFrom: message.start,
                        rangeSelectorTo: message.end,
                        rangeSelectorZoom: ""//为什么是空 去掉它

                    }
                });
                var chart = new Highcharts.Chart({
                    chart: {
                        renderTo: $chartDiv[0],
                        borderColor: '#F0F0F0',
                        borderWidth: 2,
                        type: 'line',
                        marginTop: 50,
                        marginLeft: 50,
                        height: 300,
                        width: 530
                    },
                    title: {
                        text: locationName + ":" + startTime + "~" + endTime + "<br>" + locationAvgdata.cnName + '(' + locationAvgdata.units + ')'
                    },
                    xAxis: {
                        type: 'datetime',
                        tickInterval: getTickInterval(),
                        dateTimeLabelFormats: {
                            day: '%Y-%m-%d',
                            week: '%Y-%m-%d',
                            month: '%Y-%m',
                            year: '%Y'
                        },
                        labels: {
                            x: 10,//调节x偏移
                            y: 10,//调节y偏移
                            rotation: -30//调节倾斜角度偏移
                        }
                    },
                    yAxis: {
                        title: {
                            text: null
                        },
                        labels: {
                            x: 15,//调节x偏移
                            y: 0//调节y偏移
                        }
                    },
                    tooltip: {
                        xDateFormat: '%Y-%m-%d',
                        crosshairs: true,
                        valueSuffix: locationAvgdata.units,
                        style: {
                            padding: '8px'
                        }
                    },
                    legend: {
                        enabled: false
                    },
                    credits: {
                        enabled: false
                    },

                    series: [
                        {
                            name: '平均' + locationAvgdata.cnName,
                            data: averages,
                            zIndex: 1,
                            marker: {
                                enabled: false
                            }
                        },
                        {
                            name: message.maximumValue + '-' + message.minimumValue,
                            data: ranges,
                            type: 'arearange',
                            lineWidth: 0,
                            linkedTo: ':previous',
                            color: Highcharts.getOptions().colors[0],
                            fillOpacity: 0.3,
                            zIndex: 0
                        }
                    ],
                    exporting: {
                        filename: locationName + "~" + startTime + "~" + endTime,
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
                        url: 'export-highchart-image.action'


                    }
                });
            }
        });
        if (!haveData) {
            $("#noData").text(message.noData);
        }
    };

    /**
     * AJAX请求获取图表数据
     * @param startDate
     * @param endDate
     * @param locationId
     */
    var getJSONData = function (startDate, endDate, locationId) {
        $.getJSON("location/" + locationId + "/getAvgPeakDataByTime.json", {
            startDate: startDate,
            endDate: endDate
        }, function (locationAvgdatas) {
            $("#chartList").empty();
            $("#noData").empty();
            createHighChart(locationAvgdatas);
        });
    };

    /**
     * 组装Highchart的数据
     *
     * @param locationAvgdata
     * @param type
     * @returns {Array}
     */
    var getDatas = function (locationAvgdata, type) {
        var avgdataList = locationAvgdata.avgdataList;
        var returnArray = [];
        $.each(avgdataList, function (index, AvgdataPO) {
            var ranges = [];
            var averages = [];
            ranges[0] = (new Date(AvgdataPO.msDate)).getTime();
            ranges[1] = AvgdataPO.maxValue;
            ranges[2] = AvgdataPO.minValue;

            averages[0] = (new Date(AvgdataPO.msDate)).getTime();
            averages[1] = AvgdataPO.avgValue;

            if (type == "ranges") {
                returnArray[index] = ranges;
            } else if (type == "averages") {
                returnArray[index] = averages;
            }
        });
        return returnArray;
    };

    /**
     * 返回时间间隔(毫秒数)
     * @returns {number}
     */
    var getTickInterval = function () {
        var interval = 0;
        var dateType = $("#dateType").val();
        switch (dateType) {
            case "1":
                interval = 30 * 24 * 3600 * 1000;
                break;
            case "2":
                interval = 7 * 24 * 3600 * 1000;
                break;
            case "3":
                interval = 3600 * 1000;
                break;
        }
        return interval;
    };
</script>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
        return 'location/' + location.id + "/average-peak-data";
    });
</script>
</#macro>