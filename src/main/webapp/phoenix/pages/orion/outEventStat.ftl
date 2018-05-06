<#--

出库事件统计
@author duanqixin
@date 2013-07-09
@check @gaohui #4495 2013-07-12
-->


<#assign title="出库事件统计 - 数据分析">
<#include  "../_common/helper.ftl">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:orion:outEvent">

<#macro head>
<style type="text/css">
    .contmn {
        width: 49%;
        display: inline-block;
    }

    .tab_title td {
        text-align: center;
        background-color: #CCC;
    }

    .tab_sum {
        background-color: #FFCC00;
    }
</style>
</#macro>

<#macro content>
    <#setting number_format="#">
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>出库事件统计</h3>
        </div>
    </div>
</div>
<div>
    <form id="zoneCtt" class="well well-small form-inline" action="orion/outEvent.action" method="post">
        <label>时间</label>
        <input class="input-medium Wdate" id="time" type="text" name="year" value="${year!}">
        <input type="submit" id="query-windrose-chart" class="btn" value="查询">
    </form>
</div>

<div id="container1">
</div>
<div id="container6">
</div>
<div style="font-size:120%">
</div>
    <#if (outShow.outEventInfos?size>0) || (scientific.outEventInfos?size>0)>
    <hr/>
    <div>
        <div id="container2" class="contmn">
        </div>
        <div id="container3" class="contmn">
        </div>
    </div>
    <div>
        <div id="container4" class="contmn">
        </div>
        <div id="container5" class="contmn">
        </div>
    </div>
    <hr/>
    <div>
        <div class="t-a-c"><h4>${groupName!}出库情况统计</h4></div>
        <div>
            <div class="f-l">单位：${groupName!}</div>
            <div class="f-r">时间：${year!}年</div>
        </div>
        <table class="table table-bordered table-center">
            <tbody>
            <tr class="tab_title">
                <td colspan=3>外出展览 ${outShow.typeCount!0}次，共计${outShow.typeSum!0}个</td>
            </tr>
                <#list outShow.outEventInfos as info>
                <tr>
                    <td>${info.eventName!}</td>
                    <td>${info.eventDate?string('yyyy年MM月dd日')}</td>
                    <td>${info.count!}个</td>
                </tr>
                </#list>
            <tr class="tab_title">
                <td colspan=3>科研修复${scientific.typeCount!0}次，共计${scientific.typeSum!0}个</td>
            </tr>
                <#list scientific.outEventInfos as info>
                <tr>
                    <td>${info.eventName!}</td>
                    <td>${info.eventDate?string('yyyy年MM月dd日')}</td>
                    <td>${info.count!}个</td>
                </tr>
                </#list>
            <tr class="tab_sum">
                <td>合计</td>
                <td colspan=2>${sumText!}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div>
        <@alertMsg "${sumUp!}"/>
    </div>
    </#if>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript">
    var evalFromStr = function (str) {
        if (str) {
            return eval('(' + str + ')')
        }
        return str;
    };

    var yearData = evalFromStr("${yearData!}");
    var outCount = evalFromStr("${outCount!}");
    var relicSum = evalFromStr("${relicSum!}");

    //数据为空时不显示图表
    var showFlag = true;
    if (outCount[0] == 0 && outCount[1] == 0) {
        showFlag = false;
    }
    $(function () {
        if (showFlag) {
            $('#container1').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '出库事件统计-出库次数'
                },
                xAxis: {
                    categories: yearData
                },
                yAxis: {
                    min: 0,
                    title: {
                        enabled: false
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                            '<td style="padding:0"><b>{point.y} </b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
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
                        name: '出库次数',
                        data: outCount

                    }
                ]
            });

            $('#container6').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '出库事件统计-文物数量'
                },
                xAxis: {
                    categories: yearData
                },
                yAxis: {
                    min: 0,
                    title: {
                        enabled: false
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                            '<td style="padding:0"><b>{point.y} </b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
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
                        name: '文物数量',
                        data: relicSum

                    }
                ]
            });
        } else {
            $("#container1").html("<h4 class='m-l-20'>暂无数据</h4>");
        }
    });

    /**
     *
     * @param data 图表数据
     * @param title 图表标题
     * @param chartId 图表 dom id
     */
    var renderPieChart = function (data, title, chartId) {
        var chartData = data;
        if (chartData) {
            chartData = eval('(' + chartData + ')');
            $(function () {
                $('#' + chartId).highcharts(chartOptions({
                    title: title,
                    serieName: title,
                    serieData: chartData
                }));
            });
        }
    };

    //饼图--时代
    renderPieChart("${timeData!}", '文物时代分布图', 'container2');

    //饼图--级别
    renderPieChart("${levelData!}", '文物级别分布图', 'container3');

    //饼图--质地
    renderPieChart("${grainData!}", '文物质地分布图', 'container4');

    //饼图--库房位次
    renderPieChart("${zoneData!}", '文物库房位次分布图', 'container5');

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
            tooltip: {
                pointFormat: '<b>{point.percentage:.1f}%</b><br/>{point.id}',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                pie: {
                    allowPointSelect: false,
                    cursor: 'pointer',
                    dataLabels: {
                        connectorWidth: 0,
                        inside: true,
                        distance: -30,
                        color: 'white'
                    }
                }
            },
            series: [
                {
                    type: 'pie',
                    name: options.serieName,
                    data: options.serieData
                }
            ]
        };
    }

    $(function () {
        //时间选择器
        $("#time").click(function () {
            WdatePicker({
                dateFmt: 'yyyy',
                maxDate: '%y-%M-{%d-1}'
            });
        });
    });
</script>
</#macro>