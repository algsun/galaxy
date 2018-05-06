$(function () {
    $.getJSON("site/sensorRealtimeData.json", {sensorId: 32}, function (value) {
        if (value.xdata.length != 0 && value.ydata.length != 0) {
            $('#container8').highcharts(chartOptions({
                title: message.realTimeHumidity,
                xAxis: value.xdata,
                series: value.ydata
            }));
        }
    });
    refreshData();

    function refreshData() {
        setInterval(function () {
            $.getJSON("site/sensorRealtimeData.json", {sensorId: 32}, function (value) {
                if (value.xdata.length != 0 && value.ydata.length != 0) {
                    $('#container8').highcharts(chartOptions({
                        title: message.realTimeHumidity,
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
                text: '（当前最新一包数据湿度值）'
            },
            xAxis: {
                categories: options.xAxis
            },
            yAxis: {
                min: 0,
                title: {
                    text: message.humidity + '(%)'
                }
            },
//            tooltip: {
//                headerFormat: '<span style="font-size:10px">{point.key}</span>',
//                pointFormat: '' +
//                    '',
//                footerFormat: '<table><tbody><tr><td style="color:{series.color};padding:0">{series.name}: </td><td style="padding:0"><b>{point.y:.1f} mm</b></td></tr></tbody></table>',
//                shared: true,
//                useHTML: true
//            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            credits: {
                enabled: false
            },
            series: [
                {
                    name: message.humidity,
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
                filename: message.realTimeHumidity
            }
        };
    }
});