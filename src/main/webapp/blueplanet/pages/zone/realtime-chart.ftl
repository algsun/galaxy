<#--
实时图形， 暂时没有用到

@author gaohui
@date 2013-01-01
-->
<#assign title>${locale.getStr("blueplanet.realtimeChart.title")}</#assign>


<#macro head>

</#macro>


<#assign tabIndex = 2>
<#macro  content>
<#-- TODO 使用 spanx 布局，而不是写死 @gaohui 2012-12-22 -->
<div class="row-fluid">
    <div class="span6">
        <div class="p-v-5">
            <div id="realtime-chart" style="height: 250px;"></div>
        </div>
    </div>
    <div class="span6">
        <div class="p-v-5">
            <div id="realtime-chart2" style="height: 250px;"></div>
        </div>
    </div>
</div>
<div class="row-fluid">
    <div class="span6">
        <div class="p-v-5">
            <div id="realtime-chart3" style="height: 250px;"></div>
        </div>
    </div>
    <div class="span6">
        <div class="p-v-5">
            <div id="realtime-chart4" style="height: 250px;"></div>
        </div>
    </div>
</div>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/highcharts/2.3.5/themes/gray.js"></script>
<script type="text/javascript" src="../assets/jquery-fullscreen-plugin/native-fullScreen-v.391ae1.js"></script>

<script type="text/javascript">
    //实时图表
    (function ($) {
        $(function () {
            Highcharts.setOptions({
                global: {
                    useUTC: false
                }
            });

            var optionFactory = function (id, title, yTitle, unit) {
                return {
                    chart: {
                        renderTo: id,
                        type: 'spline',
                        marginRight: 10,
                        events: {
                            load: function () {
                                // set up the updating of the chart each second
                                var series = this.series[0];
                                setInterval(function () {
                                    var x = (new Date()).getTime(), // current time
                                            y = Math.random();
                                    series.addPoint([x, y], true, true);
                                }, 2000);
                            }
                        }
                    },
                    title: {
                        text: title
                    },
                    xAxis: {
                        type: 'datetime',
                        tickPixelInterval: 150
                    },
                    yAxis: {
                        title: {
                            text: yTitle
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
                            return '<b>' + this.series.name + '</b><br/>' +
                                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                                    Highcharts.numberFormat(this.y, 2);
                        }
                    },
                    legend: {
                        enabled: false
                    },
                    exporting: {
                        buttons: [
                            {
                                symbol: 'diamond',
                                x: -62,
                                symbolFill: '#B5C9DF',
                                hoverSymbolFill: '#779ABF',
                                _titleKey: 'printButtonTitle',
                                onclick: function () {
                                    //全屏图表
                                    if (fullScreenApi.supportsFullScreen) {
                                        var $chart = $("#" + id);
                                        if (fullScreenApi.isFullScreen()) {
                                            $chart.css("height", 250);
                                            fullScreenApi.cancelFullScreen($chart[0]);
                                        } else {
                                            $chart.css("width", '100%');
                                            $chart.css("height", '100%');
                                            fullScreenApi.requestFullScreen($("#" + id)[0]);
                                        }
                                    }
                                }
                            }
                        ]
                    },
                    series: [
                        {
                            name: unit,
                            data: (function () {
                                // generate an array of random data
                                var data = [],
                                        time = (new Date()).getTime(),
                                        i;

                                for (i = -19; i <= 0; i++) {
                                    data.push({
                                        x: time + i * 1000,
                                        y: Math.random()
                                    });
                                }
                                return data;
                            })()
                        }
                    ]
                };
            };


            var chart = new Highcharts.Chart(optionFactory("realtime-chart", "温度", "温度（℃）", '温度'));
            var chart2 = new Highcharts.Chart(optionFactory("realtime-chart2", "湿度", "湿度", "温度"));
            var chart3 = new Highcharts.Chart(optionFactory("realtime-chart3", "紫外", "紫外", "紫外"));
            var chart4 = new Highcharts.Chart(optionFactory("realtime-chart4", '二氧化碳', '二氧化碳', '二氧化碳'));

            if (fullScreenApi.supportsFullScreen) {
                $('#realtime-chart, #realtime-chart2, #realtime-chart3, #realtime-chart4').click(function () {
                    fullScreenApi.requestFullScreen($(this)[0]);
                });
            }
        });
    })(jQuery);
</script>
</#macro>