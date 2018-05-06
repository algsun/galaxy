$(function () {

    zoneStability();
    refreshData();
    function refreshData() {
        setInterval(zoneStability, 3000 * 60)
    }

    function zoneStability() {
        $.post("summarize/zoneStability.json", {dateType: 3, sensorId: 32}, function (result) {

            var data = new Array();
            var text = "";
            if (result) {
                if (result.length > 0) {
                    text = "(统计区域当天数据与平均值得相差幅度大小)";
                    for (var i = 0; i < result.length; i++) {
                        var zoneStability = result[i];
                        data.push([zoneStability.zoneName, zoneStability.stability]);
                    }
                } else {
                    data.push(['', 0.0]);
                    text = "<span class='muted'>(" + message.noData + ")</span>";
                    return;
                }
            }


            $('#container2').highcharts({
                chart: {
                    type: 'bar'
                },
                title: {
                    text: message.regionalStability
                },
                subtitle: {
                    text: text
                },
                xAxis: {
                    type: 'category'
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: ''
                    }
                },
                legend: {
                    enabled: false
                },
                tooltip: {
                    pointFormat: message.stability + ': <b>{point.y:.2f}</b>'
                },
                credits: {
                    enabled: false
                },
                series: [
                    {
                        name: 'Population',
                        data: data,
                        dataLabels: {
                            enabled: true,
                            rotation: 0,
                            color: '#FFFFFF',
                            align: 'right',
                            x: 4,
                            y: 0,
                            style: {
                                fontSize: '13px',
                                fontFamily: 'Verdana, sans-serif',
                                textShadow: '0 0 3px black'
                            }
                        }
                    }
                ], exporting: {
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
                    filename: message.regionalStability
                }
            });
        });
    }

});
