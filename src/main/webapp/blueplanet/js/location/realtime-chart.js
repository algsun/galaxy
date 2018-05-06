/**
 * 设备实时图形
 *
 * @author xuyuexi
 * @date 2014-07-07
 * @dependency jquery, HighCharts
 */

(function () {
    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });
})();


$(function () {
    // 默认数据包数
    var DEFAULT_DATA_COUNT = 12;
    // 图表刷新周期(毫秒)
    var REFRESH_INTERVAL = 60000;
    // 获取设备id
    var locationId = $("#location-id").attr("data-location-id");

    // 最后一包数据时间
    var lastTime;
    var chart; // 图表对象
    var $sensorinfoSelect = $("#sensorinfoSelect");
    // 更改监测指标时
    $sensorinfoSelect.change(function () {
        var sensorPhysicalId = $sensorinfoSelect.val();
        var selectedOption = $sensorinfoSelect.find(":selected");
        var cnName = selectedOption.text();
        var units = selectedOption.attr("data-units");
        initRealtimeChart(locationId, sensorPhysicalId, cnName, units, function (newChart) {
            chart = newChart
        });
    }).change();


    //更改刷新周期
    var $refreshIntervalSelect = $("#refreshIntervalSelect");
    $refreshIntervalSelect.change(function () {
        if (chart) {
            REFRESH_INTERVAL = parseInt($refreshIntervalSelect.val());
            var sensorPhysicalId = $sensorinfoSelect.val();
            onChartLoad(locationId, sensorPhysicalId, lastTime).apply(chart);
        }
    });


    // 初始化图表
    function initRealtimeChart(locationId, sensorPhysicalId, sensorCnName, units, onComplete) {
        // 加载初始数据
        $.getJSON("location/" + locationId + "/realtime-data.json", {count: DEFAULT_DATA_COUNT}, function (result) {
            var data = [];
            $.each(result, function (i, realtimeData) {
                var time = dateStrToMoment(realtimeData.stamp).valueOf();
                var deviceData = realtimeData.sensorInfoMap[sensorPhysicalId];
                if(deviceData && deviceData.state === 1){
                    var value = deviceData.sensorPhysicalValue;
                    value = parseFloat(value);
                    data.push({
                        x: time,
                        y: value
                    });
                }
                lastTime = time;
            });

            // 生成图表
            var chart = new Highcharts.Chart(
                optionFactory({
                    id: "realtime-chart",
                    title: sensorCnName,
                    yTitle: sensorCnName + "（" + units + "）",
                    unit: units,
                    data: data,
                    locationId: locationId,
                    sensorPhysicalId: sensorPhysicalId
                })
            );
            onComplete(chart);
        });
    }


    // 生成图表参数
    function optionFactory() {
        //id, title, yTitle, unit, data, sensorPhysicalId
        var option = arguments[0];
        return {
            chart: {
                renderTo: option.id,
                type: 'spline',
                marginRight: 10,
                events: {
                    load: onChartLoad(option.locationId, option.sensorPhysicalId, option.data[option.data.length - 1].x)
                }
            },
            credits: {enabled: false}, // 取消右下角链接
            exporting: {enabled: false}, // 取消导出按钮
            title: {
                text: option.title
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150,
                dateTimeLabelFormats: {
                    day: '%m-%d',
                    week: "%m-%d",
                    month: '%Y-%m',
                    year: '%Y'
                }
            },
            yAxis: {
                title: {
                    text: option.yTitle
                },
                plotLines: [
                    {
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }
                ]
            },
            tooltip: {
                formatter: function () {
                    return this.series.name + '：' + this.y + ' ' + option.unit + '<br/>' +
                        '时间：' + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>';
                }
            },
            legend: {
                enabled: false
            },
            series: [
                {
                    name: option.title,
                    data: option.data
                }
            ]
        };
    }

    // 加载数据周期钩子, 用于在更换监测指标后将其清除
    var fetchDataIntervalHook;

    function onChartLoad(locationId, sensorPhysicalId, timeAfter) {
        var serverNow = moment(parseInt($("body").attr("data-server-time")));
        // 最后一包数据的时间
        lastTime = timeAfter || serverNow.valueOf();
        // 清除上一个监测指标的周期加载数据定时器
        clearInterval(fetchDataIntervalHook);
        return function () {
            var series = this.series[0];
            fetchDataIntervalHook = setInterval(function () {
                // 请求最新的数据
                $.getJSON("location/" + locationId + "/realtime-data.json", {startTime: lastTime}, function (result) {
                    if (result.length > 0) {
                        //记录最新的数据时间, 用于下一次请求
                        var lastRealtimeData = result[result.length - 1];
                        lastTime = dateStrToMoment(lastRealtimeData.stamp).valueOf();

                        $.each(result, function (i, realtimeData) {
                            var time = dateStrToMoment(realtimeData.stamp).valueOf();
                            var deviceData = realtimeData.sensorInfoMap[sensorPhysicalId];
                            var isShift = time ==lastTime;
                            if(deviceData && deviceData.state === 1&&!isShift){
                                var value = deviceData.sensorPhysicalValue;
                                value = parseFloat(value);
                                // 图表中加载点
                                series.addPoint([time, value], true, true);
                            }
                        });
                    }
                });
            }, REFRESH_INTERVAL);
        };
    }

    function dateStrToMoment(stamp) {
        return moment(stamp, "YYYY-MM-DDTHH:mm:ss");
    }
});
