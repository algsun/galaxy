<#--
人员停留情况
@author li.jianfei
@date 2013-07-11

@check @duan.qixin 2013-7-18 #4855
-->
<#assign title="人员停留情况 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:uma:stopover">

<#include  "../_common/helper.ftl">
<#include "/common/pages/common-tag.ftl">

<#macro head>

</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="title page-header">
            <h3>人员停留情况</h3>
        </div>
    </div>
</div>
<div class="m-b-10 form-inline well well-small">
    <#include "../_common/date-year-month-control.ftl">
    <!--TODO 调整样式，将此查询按钮，放置到form的最右边-->
    <button id="commit" class="btn">查询</button>
</div>

<H4 id="noData"></H4>
<div class="row-fluid m-t-20">
    <div class="span12">
        <div id="columnChart"></div>
    </div>
</div>
<div class="row-fluid m-t-20">
    <div class="span12">
        <div id="lineChart"></div>
    </div>
</div>
<div id="statistics" class="row-fluid hide">
    <@alertNested>
        <span id="queryDate"></span>
        <br/>
        停留总时间最长的位置是<span id="inZoneName" class="bold"></span>，总计<span id="maxInTime" class="bold"></span>小时
        <br/>
        平均停留时间最长的位置是<span id="avgZoneName" class="bold"></span>，时长为<span id="maxAvgTime" class="bold"></span>小时
        <br/>
        <br/>
        <span id="importantZoneName" class="bold "></span>是重点管理区域呀。
    </@alertNested>
</div>

</#macro>

<#macro script>

<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/index/date-format.js"/>
    <@scriptTag "js/date-year-month-control.js"/>

<script type="text/javascript">
    $(function () {

        $("#commit").click(function () {
            var dateType = $("input[name='dateType']:checked").val();
            var date = $("#date").val();
            if (!date) {
                if ($("#radioYear").attr("checked")) {
                    date = new Date().getFullYear() + "";
                } else {
                    var month = new Date().getMonth() + 1;
                    if (month < 10) {
                        month = "0" + month;
                    }
                    date = new Date().getFullYear() + "-" + month;
                }
            }
            $("#date").val(date);
            $.getJSON("uma/userStopoverChart.json", {dateType: dateType, date: date}, function (userStopover) {
                $("#noData").empty();
                if (userStopover.hasData) {

                    var categories = userStopover.zoneNames,
                            avgData = userStopover.avgTime,
                            sumData = userStopover.inTime;

                    var dateTitle;
                    if (moment(date, "YYYY-MM").isValid()) {
                        dateTitle = moment(date, "YYYY-MM").format("YYYY年MM月");
                    } else {
                        dateTitle = moment(date, "YYYY").format("YYYY年");
                    }

                    $("#statistics").show();
                    $("#queryDate").text(dateTitle);
                    $("#inZoneName").text(userStopover.inZoneName);
                    $("#maxInTime").text(userStopover.maxInTime);
                    $("#avgZoneName").text(userStopover.avgZoneName);
                    $("#maxAvgTime").text(userStopover.maxAvgTime);
                    $("#importantZoneName").text(userStopover.inZoneName);
                    var chart = new Highcharts.Chart({
                        chart: {
                            type: "column",
                            renderTo: 'columnChart',
                            marginLeft: 50,
                            marginRight: 50
                        },

                        title: {
                            text: "人员停留时间总和"
                        },
                        subtitle: {
                            text: dateTitle
                        },

                        xAxis: {
                            categories: categories
                        },

                        yAxis: {
                            title: {
                                text: null
                            }
                        },
                        tooltip: {
                            valueSuffix: '·h'
                        },
                        legend: {
                            enabled: false
                        },

                        exporting: {
                            enabled: false
                        },
                        plotOptions: {                          //数据点选项
                            series: {
                                dataLabels: {
                                    enabled: true
                                }
                            }
                        },
                        credits: {
                            enabled: false
                        },

                        navigation: {
                            buttonOptions: {
                                enabled: false
                            }
                        },

                        series: [
                            {
                                name: "停留时间总和",
                                data: sumData
                            }
                        ]
                    });

                    var chart = new Highcharts.Chart({
                        chart: {
                            type: "spline",
                            renderTo: 'lineChart',
                            marginLeft: 50,
                            marginRight: 50
                        },

                        title: {
                            text: "人员平均停留时间"
                        },
                        subtitle: {
                            text: dateTitle
                        },

                        xAxis: {
                            categories: categories
                        },

                        yAxis: {
                            title: {
                                text: null
                            }
                        },
                        tooltip: {
                            valueSuffix: '·h'
                        },
                        legend: {
                            enabled: false
                        },

                        exporting: {
                            enabled: false
                        },

                        credits: {
                            enabled: false
                        },

                        navigation: {
                            buttonOptions: {
                                enabled: false
                            }
                        },

                        series: [
                            {
                                name: "人均停留时间",
                                data: avgData
                            }
                        ]
                    });

                } else {
                    $("#columnChart").empty();
                    $("#lineChart").empty();
                    $("#statistics").hide();
                    $("#noData").text("暂无数据");
                }
            });
        }).click();
    });
</script>
</#macro>
