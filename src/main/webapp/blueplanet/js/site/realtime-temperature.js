$(function () {
    $.getJSON("site/sensorRealtimeData.json", {sensorId: 33}, function (value) {
        if (value.xdata.length != 0 && value.ydata.length != 0) {
            $('#container7').highcharts(chartOptions({
                title: message.realTimeTemperature,
                xAxis: value.xdata,
                series: value.ydata
            }));
        }
    });
    refreshData();

    function refreshData() {
        setInterval(function () {
            $.getJSON("site/sensorRealtimeData.json", {sensorId: 33}, function (value) {
                if (value.xdata.length != 0 && value.ydata.length != 0) {
                    $('#container7').highcharts(chartOptions({
                        title: message.realTimeTemperature,
                        xAxis: value.xdata,
                        series: value.ydata
                    }));
                }
            })
        }, 3000 * 60);
    }

    function chartOptions(options) {
        return {
            chart: {
                type: 'column'
            },
            title: {
                text: options.title
            },
            subtitle: {
                text: '（当前最新一包数据温度值）'
            },
            xAxis: {
                categories: options.xAxis
            },
            yAxis: {
                title: {
                    text: message.temperature + '（℃）'
                }
            },
            credits: {
                enabled: false
            },
            series: [
                {
                    name: message.temperature,
                    data: options.series
                }
            ],
            exporting: {
                enabled: false,
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
                filename: message.realTimeTemperature
            }
        };
    }
});
