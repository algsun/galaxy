<#--
均峰值

@author wang.geng
@date 2014-4-16
-->
<#assign title="均峰值 - 环境监控">
<#include "/common/pages/common-tag.ftl">


<#macro head>
</#macro>


<#assign tabIndex = 8>
<#include "device-helper.ftl">
<#macro content>
<div class="span12">
    <div class="m-b-10 form-inline well well-small" style="height: 30px">
        <input id="deviceId" type="hidden" value="${deviceId!}"/>
        <input id="dateType" type="hidden" value="${dateType!}"/>
        <label>开始时间</label>
        <input id="startDate" class="input-small" name="startDate" type="text"
               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
               value="${(startDate?string("yyyy-MM-dd"))!}"/>
        <label>结束时间</label>
        <input id="endDate" class="input-small" name="endDate" type="text"
               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
               value="${(endDate?string("yyyy-MM-dd"))!}"/>
        <button id="commit" class="btn">查询</button>
        <span id="exportButton" class="btn m-l-20">
            <i class="icon-download-alt"></i>导出Excel
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
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
    <@scriptTag "js/date-format.js"/>
<script type="text/javascript">
    $(function () {
        var deviceId = $("#deviceId").val();
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();

        //进入均峰值页面，执行方法，获取初始化图表，默认当月
        getJSONData(startDate,endDate,deviceId);

        //给查询按钮绑定AJAX查询，查询图表数据
        $(document).on('click', '#commit', function () {
            var start = $("#startDate").val();
            var end = $("#endDate").val();
            getJSONData(start,end,deviceId);
        });

        // 导出按钮点击
        $(document).on('click', '#exportButton', function () {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            // 导出等待页面链接
            var url = "device/" + deviceId + "/waitingForExport?startDate=" + startDate + "&endDate=" + endDate;
            var basePath = $("base").attr("href");
            if (basePath) {
                url = basePath + url;
            }
            // 打开等待页面
            window.open(url, "blank", "height=400, width=400");
        });
    });

    var createHighChart = function (deviceAvgdatas) {
        var haveData = false;
        var startTime = $("#startDate").val();
        var endTime = $("#endDate").val();
        $.each(deviceAvgdatas, function (index, deviceAvgdata) {
            if (deviceAvgdata.hasData) {
                haveData = true;
                var $chartDiv = $("#chartTemp").children().last().clone();
                $("#chartList").append($chartDiv);
                var ranges = getDatas(deviceAvgdata,"ranges");
                var averages = getDatas(deviceAvgdata,"averages");
                $("#dateType").val(deviceAvgdata.dateType);
                Highcharts.setOptions({
                    global: {
                        useUTC: false
                    }
                });
                var chart = new Highcharts.Chart({
                    chart: {
                        renderTo: $chartDiv[0],
                        borderColor: '#F0F0F0',
                        borderWidth: 2,
                        type: 'line',
                        marginLeft: 50,
                        height: 300,
                        width: 530
                    },
                    title: {
                        text: deviceAvgdata.cnName + '(' + deviceAvgdata.units + ')'
                    },
                    xAxis: {
                        type: 'datetime',
                        tickInterval: getTickInterval(),
                        dateTimeLabelFormats: {
                            day: '%Y-%m-%d',
                            week: '%Y-%m-%d',
                            month: '%Y-%m',
                            year: '%Y'
                        }
                    },
                    yAxis: {
                        title: {
                            text: null
                        }
                    },
                    tooltip: {
                        xDateFormat: '%Y-%m-%d',
                        crosshairs: true,
                        valueSuffix: deviceAvgdata.units,
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
                            name: '平均' + deviceAvgdata.cnName,
                            data: averages,
                            zIndex: 1,
                            marker: {
                                enabled: false
                            }
                        },
                        {
                            name: '最大值-最小值',
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
                        filename: startTime + "~" + endTime,
                        buttons: {
                            contextButton: {
                                menuItems: null,
                                onclick: function () {
                                    this.exportChart();
                                }
                            },
                            url: '../proxima/exportImage.action'


                        }
                    }
                });
            }
        });
        if (!haveData) {
            $("#noData").text("暂无数据");
        }
    };

    /**
     * AJAX请求获取图表数据
     * @param startDate
     * @param endDate
     * @param deviceId
     */
    var getJSONData = function(startDate,endDate,deviceId){
        $.getJSON("device/" + deviceId + "/getAvgPeakDataByTime.json", {startDate: startDate, endDate: endDate}, function (deviceAvgdatas) {
            $("#chartList").empty();
            $("#noData").empty();
            createHighChart(deviceAvgdatas);
        });
    };

    /**
     * 组装Highchart的数据
     *
     * @param deviceAvgdata
     * @param type
     * @returns {Array}
     */
    var getDatas = function (deviceAvgdata,type) {
        var avgdataList = deviceAvgdata.avgdataList;
        var returnArray = [];
        $.each(avgdataList, function (index, AvgdataPO) {
            var ranges = [];
            var averages = [];
            ranges[0] = (new Date(AvgdataPO.msDate)).getTime();
            ranges[1] = AvgdataPO.maxValue;
            ranges[2] = AvgdataPO.minValue;

            averages[0] = (new Date(AvgdataPO.msDate)).getTime();
            averages[1] = AvgdataPO.avgValue;

            if(type == "ranges"){
                returnArray[index] = ranges;
            }else if(type == "averages"){
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
</#macro>