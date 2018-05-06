<#--
设备状态图
-->
<#assign title>${locale.getStr("blueplanet.device.state.title")}</#assign>


<#macro head>
    <#include "/common/pages/common-tag.ftl"/>
<link rel="stylesheet" href="../assets/select2/3.2/select2.css">
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
    <@linkTag "css/location/charts/basic-chart.css"/>
</#macro>

<#macro content>

<h3><a href="devices">${locale.getStr("blueplanet.device.deviceList")}</a></h3>
<input class="hide" name="deviceId" id="deviceId" value="${deviceId}">
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/highstock/4.2.5/highstock.js"></script>
<script type="text/javascript">
    $(function () {
        $.getJSON('device/' + $("#deviceId").val() + '/device-state-chart.json', function (data) {

            if (!data) {
                $('#container').html(message.noData);
                return;
            }

            // Create a timer
            var start = +new Date();

            Highcharts.setOptions({
                global: {useUTC: false},
                lang: {
                    rangeSelectorFrom: '开始',
                    rangeSelectorTo: '结束 ',
                    rangeSelectorZoom: '',
                    downloadJPEG: '导出为JPEG',
                    downloadPDF: '导出为PDF',
                    downloadPNG: '导出为PNG',
                    downloadSVG: '导出为SVG',
                    resetZoom: '重置',
                    resetZoomTitle: '按1:1缩放'
                }
            });

            // Create the chart
            $('#container').highcharts('StockChart', {
                chart: {
                    events: {
                        load: function () {
                            this.setTitle(null, {
                                text: message.chartCreateTime + ':' + (new Date() - start) + 'ms'
                            });
                        }
                    },
                    zoomType: 'x'
                },
                xAxis: {
                    type: 'datetime',
                    labels: {
                        x: 25,//调节x偏移
                        y: 25//调节y偏移
                    },
                    dateTimeLabelFormats: {
                        second: '%m-%d %H:%M',
                        minute: '%H:%M',
                        hour: '%m-%d %H:%M',
                        day: '%Y-%m-%d',
                        month: '%Y-%m',
                        year: '%Y'
                    },
                    ordinal: false
                },

                tooltip:{
                    formatter:function(){
                        var info='';
                        $.each(this.points,function (i,item) {
                            info += '<span style="color:'+item.point.color+'">'+item.series.name+'</span>'+':'+item.point.y+'<br>'
                        });
                        return Highcharts.dateFormat("%Y-%m-%d %H:%M:%S",this.x)+'<br>'+info;
                    }
                },
                rangeSelector: {

                    buttons: [
                        {
                            type: 'day',
                            count: 1,
                            text: '1' + message.day
                        },
                        {
                            type: 'week',
                            count: 1,
                            text: '1' + message.week
                        },
                        {
                            type: 'month',
                            count: 1,
                            text: '1' + message.month
                        },
                        {
                            type: 'month',
                            count: 6,
                            text: '6' + message.month
                        },
                        {
                            type: 'year',
                            count: 1,
                            text: '1' + message.year
                        },
                        {
                            type: 'all',
                            text: message.all
                        }

                    ],
                    selected: 3,
                    inputDateFormat: '%Y-%m-%d'
                },
                credits: {
                    enabled: false
                },
                legend: {
                    enabled: true

                },
                yAxis: {
                    title: {
                        text: ''
                    }
                },

                title: {
                    text: message.voltage + '、RSSI、LQI'
                },

                subtitle: {
                    text: ''
                },

                series: [
                    {
                        tooltip: {
                            shared: true,
                            valueDecimals: 2
                        },
                        name: message.voltage,
                        data: data[0].data,
                        pointInterval: 3600 * 1000


                    },
                    {
                        tooltip: {
                            shared: true,
                            valueDecimals: 2
                        },
                        name: data[1].name,
                        data: data[1].data,
                        pointInterval: 3600 * 1000


                    },
                    {
                        tooltip: {
                            shared: true,
                            valueDecimals: 2
                        },
                        name: data[2].name,
                        data: data[2].data,
                        pointInterval: 3600 * 1000


                    }
                ],
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
                    margin: 20,
                    xAxis: {
                        dateTimeLabelFormats: {
                            day: '%Y-%m-%d',
                            month: '%Y-%m',
                            year: '%Y'
                        }
                    }
                }




            });
        });

    });
</script>
</#macro>