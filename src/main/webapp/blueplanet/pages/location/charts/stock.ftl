<#--
KDJ随机指标
-->
<#assign title=locale.getStr("blueplanet.location.largeDataTitle")>


<#macro head>
    <#include "/common/pages/common-tag.ftl"/>
<link rel="stylesheet" href="../assets/select2/3.2/select2.css">
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
</#macro>


<#assign tabIndex = 11>
<#macro content>
<input type="hidden" id="locationId" value="${locationId}"/>
<div class="form-inline well p-t-10" style="margin:0 auto;">
    <div class="f-l m-l-10 ">
        <label>${locale.getStr("common.monitoringIndicators")}</label>
        <select id="sensorinfoSelect">
            <#list sensorinfos as sensorinfo>
                <option data-units="${sensorinfo.units}"
                        value="${sensorinfo.sensorPhysicalid}">${sensorinfo.cnName}</option>
            </#list>
        </select>
        <button id="query-stock" class="btn">${locale.getStr("common.select")}</button>
    </div>
    <div class="f-r m-l-10">
        <span id="subscribe" class="btn pull-right" title="${locale.getStr("blueplanet.location.dailyMail")}"><#if kdjReport>${locale.getStr("blueplanet.location.cancelSubscription")}<#else>${locale.getStr("blueplanet.location.subscribe")}</#if></span>
        <abbr class="pull-right m-t-5 m-r-10" for="subscribe" title="${locale.getStr("blueplanet.location.dailyMail")}">
            <#if kdjReport>
                ${locale.getStr("blueplanet.location.alreadySubscribe")}<i class="icon-question-sign"></i>
            </#if>
        </abbr>
    </div>
</div>
<div class="row-fluid">
    <div id="container1" class="span12" style="height: 400px;">
    </div>
</div>
<div class="row-fluid">
    <div id="container2" class="span12" style="height: 200px; margin-top: -100px;">
    </div>
</div>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/echarts/2.1.8/echarts-all.js"></script>
<script type="text/javascript" src="../assets/select2/3.2/select2.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/lang/zh-cn.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="js/pnotify.js"></script>
<script type="text/javascript">
    $(function () {
        var locationId = $("#locationId").val();

        //进入KDJ随机指标TAB页，默认查询湿度的KDJ指标
        createKDJChart(locationId, 32);

        $("#query-stock").click(function () {
            var sensorId = $("#sensorinfoSelect").val();
            createKDJChart(locationId, sensorId);
        });

        $("#subscribe").click(function () {
            var $this = $(this);
            if ($this.text() == message.subscribe) {

                $.getJSON("../blueplanet/stock/location/" + locationId + "/addSubscribe.json", {subscribeType: 3}, function (success) {
                    if (success) {
                        $this.next().html(message.subscribed + "<i class='icon-question-sign'></i>");
                        $this.text(message.unsubscribe);
                    }
                });
            } else {

                $.getJSON("../blueplanet/stock/location/" + locationId + "/deleteSubscribe.json", {subscribeType: 3}, function (success) {
                    if (success) {
                        $this.next().empty();
                        $this.text(message.subscribe);
                    }
                });
            }
        });

        function createKDJChart(locationId, sensorId) {
            $.post("location/sensorid/getStocks.json", {locationId: locationId, sensorId: sensorId}, function (stocks) {
                var xAxisData = [];
                var kSerieData = [];
                var KData = [];
                var DData = [];
                var JData = [];
                var ma5Data = [];
                var ma10Data = [];
                var ma30Data = [];
                for (var i = 0; i < stocks.length; i++) {
                    var stock = stocks[i];
                    xAxisData.push(moment(stock.stamp).format("YYYY-MM-DD"));
                    // K线图数据
                    kSerieData.push([stock.beginValue, stock.endValue, stock.minValue, stock.maxValue]);

                    // ma 5 ,10 ,30 Data
                    ma5Data.push(stock.avgValue5);
                    ma10Data.push(stock.avgValue10);
                    ma30Data.push(stock.avgValue30);

                    //KDJ数据
                    KData.push(stock.k);
                    DData.push(stock.d);
                    JData.push(stock.j);
                }


                var option = {
                    tooltip: {
                        trigger: 'axis',
                        formatter: function (params) {
                            var res = params[0][0] + ' ' + params[0][1];
                            res += '<br/>  ' + message.start + ' : ' + params[0][2][0] + '  ' + message.highest + ' : ' + params[0][2][3];
                            res += '<br/>  ' + message.end + ' : ' + params[0][2][1] + '  ' + message.lowest + ' : ' + params[0][2][2];
                            res += '<br/>  ' + message.M5 + ' : ' + params[1][3];
                            res += '<br/>  ' + message.M10 + ' : ' + params[2][3];
                            res += '<br/>  ' + message.M30 + ' : ' + params[3][3];
                            return res;
                        }
                    },
                    legend: {
                        data: [message.index, message.M5, message.M10, message.M30, 'K', 'D', 'J']
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataZoom: {show: true},
                            dataView: {show: false, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    xAxis: [
                        {
                            type: 'category',
                            boundaryGap: true,
                            axisLabel: {show: false},
                            axisTick: {onGap: false},
                            splitLine: {show: false},
                            data: xAxisData
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            scale: true,
                            precision: 3,
                            boundaryGap: [0.01, 0.01]
                        }
                    ],
                    series: [
                        {
                            name: message.index,
                            type: 'k',
                            barMaxWidth: 50,
                            data: kSerieData // 开盘，收盘，最低，最高
                        },
                        {
                            name: message.M5,
                            type: 'line',
                            symbol: 'none',
                            data: ma5Data
                        },
                        {
                            name: message.M10,
                            type: 'line',
                            symbol: 'none',
                            data: ma10Data
                        },
                        {
                            name: message.M30,
                            type: 'line',
                            symbol: 'none',
                            data: ma30Data
                        },
                        {
                            name: 'K',
                            type: 'line',
                            symbol: 'none',
                            data: [],
                            itemStyle: {
                                normal: {
                                    color: '#000099',
                                    label: {
                                        show: false
                                    }
                                }
                            }
                        },
                        {
                            name: 'D',
                            type: 'line',
                            symbol: 'none',
                            data: [],
                            itemStyle: {
                                normal: {
                                    color: '#CC3366',
                                    label: {
                                        show: false
                                    }
                                }
                            }
                        },
                        {
                            name: 'J',
                            type: 'line',
                            symbol: 'none',
                            data: [],
                            itemStyle: {
                                normal: {
                                    color: '#CCFF00',
                                    label: {
                                        show: false
                                    }
                                }
                            }
                        }
                    ]
                };
                // 基于准备好的dom，初始化echarts图表
                var myChart = echarts.init($("#container1")[0]);
                // 为echarts对象加载数据
                myChart.setOption(option);


                option2 = {
                    tooltip: {
                        trigger: 'axis',
                        showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
                    },
                    legend: {
                        y: -30,
                        data: ['K', 'D', 'J']
                    },
                    toolbox: {
                        y: -30,
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataZoom: {show: true},
                            dataView: {show: false, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    dataZoom: {
                        show: true,
                        realtime: true,
                        start: 0,
                        end: 100
                    },
                    xAxis: [
                        {
                            type: 'category',
                            boundaryGap: true,
                            axisLabel: {
                                show: true
                            },
                            axisTick: {onGap: false},
                            splitLine: {show: false},
                            data: xAxisData
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            scale: true
                        }
                    ],
                    series: [
                        {
                            name: 'K',
                            type: 'line',
                            symbol: 'none',
                            data: KData,
                            itemStyle: {
                                normal: {
                                    color: '#000099',
                                    label: {
                                        show: false
                                    }
                                }
                            }
                        },
                        {
                            name: 'D',
                            type: 'line',
                            symbol: 'none',
                            data: DData,
                            itemStyle: {
                                normal: {
                                    color: '#CC3366',
                                    label: {
                                        show: false
                                    }
                                }
                            }
                        },
                        {
                            name: 'J',
                            type: 'line',
                            symbol: 'none',
                            data: JData,
                            itemStyle: {
                                normal: {
                                    color: '#CCFF00',
                                    label: {
                                        show: false
                                    }
                                }
                            }
                        }
                    ]
                };
                var myChart2 = echarts.init($("#container2")[0]);
                myChart2.setOption(option2);


                myChart.connect([myChart2]);
                myChart2.connect([myChart]);
            });
        }
    });
</script>
</#macro>