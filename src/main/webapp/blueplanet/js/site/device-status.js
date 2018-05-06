$(function () {

    $.getJSON("site/deviceStatusPieData.json", function (data) {
        var jsonData = JSON.parse(data);

        if (jsonData.hasData) {
            jsonData.pieData = fmtPoint(jsonData.pieData);
        }

        if (jsonData.hasData) {
            $('#container5').highcharts(chartOptions({
                title: message.deviceState,
                serieName: message.percent,
                serieData: jsonData.pieData
            }));
        }
    });

    function fmtPoint(data) {
        for (var i = 0; i < data.length; i++) {
            var id = fmtId(data[i][2]);
            data[i] = {name: data[i][0], y: data[i][1], id: id};
        }
        return data;
    }

    function fmtId(data) {
        var id = "";
        if (data.length > 20) {
            id = "<table><tr>";
            for (var i = 0; i < data.length; i++) {
                id += "<td>" + data[i][0] + ": " + data[i][1].toFixed(1) + "% " + data[i][2] + "个</td>";
                if ((i + 1) % 2 == 0) {
                    id += "</tr><tr>";
                }
            }
            id += "</tr></table>";
        } else {
            for (var i = 0; i < data.length; i++) {
                id += data[i][0] + ": " + data[i][1].toFixed(1) + "% " + data[i][2] + "个<br/>";
            }
        }
        return id;
    }

    function chartOptions(options) {
        return {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            credits: {enabled: false},
            title: {
                text: options.title,
                margin: 10
            },
            subtitle: {
                text: '（设备不同状态占总设备的比例）'
            },
            tooltip: {
                pointFormat: '<b>{point.percentage:.1f}%</b><br/>{point.id}',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            colors: [
                '#db3333',//超时 欢迎加入Highcharts学习交流群294191384
                '#86db33',//正常
                '#f2f038',//低电
                '#e99838',//掉电
                'grey'
            ],
            series: [
                {
                    type: 'pie',
                    itemStyle: {
                        normal: {
                            color: '#ff0000'
                        },
                        emphasis: {
                            color: '#90ee90'
                        }
                    },
                    name: options.serieName,
                    data: options.serieData
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
                filename: message.deviceState
            }
        };
    }
});