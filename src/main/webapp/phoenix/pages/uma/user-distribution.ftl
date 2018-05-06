<#--
区域活动分布
@author li.jianfei
@date 2013-07-09
-->
<#assign title="区域活动分布 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:uma:distribution">

<#include  "../_common/helper.ftl">
<#include "/common/pages/common-tag.ftl">

<#macro head>

</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="title page-header">
            <h3>区域活动分布</h3>
        </div>
    </div>
</div>
<div class="m-b-10 form-inline well well-small">
    <#include "../_common/date-year-month-control.ftl">
    <button id="commit" class="btn">查询</button>
</div>

<H4 id="noData"></H4>
<div class="row-fluid m-t-20">
    <div class="span12">
        <div id="chart"></div>
    </div>
</div>
<div id="statistics" class="row-fluid hide">
    <@alertNested>
        <span id="queryDate"></span>
        <br/>
        活动密度最高区域：<span id="maxActiveArea" class="bold"></span>(<span id="maxActiveAreaCount" class="bold "></span>次)
        <br/>
        活动最频繁的时段：<span id="maxActiveTime" class="bold"></span>点(<span id="maxActiveTimeCount" class="bold"></span>次)
        <br/>
        <br/>
        其中<span id="maxActiveTimeRange" class="bold "></span>点间 <span id="maxActiveTimeArea" class="bold"></span>
        人员活动最为密集(<span
            id="maxActiveTimeAreaCount" class="bold"></span>次)
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
             if(!date) {
            	if($("#radioYear").attr("checked")) {
		    		date = new Date().getFullYear() + "";
            	}else {
	            	var month = new Date().getMonth() + 1;
			    	if(month < 10) {
			    		month = "0" + month;
			    	}
			    	date = new Date().getFullYear() + "-" + month;
            	}
            }
            $.getJSON("uma/chart.json", {dateType: dateType, date: date}, function (userDistribution) {
                $("#noData").empty();
                if (userDistribution.hasData) {

                    var categories = userDistribution.districtList;
                    var seriesData = new Array();
                    var jsonString = "";
                    $.each(userDistribution.data, function (index, serieData) {
                        jsonString = '{"data":' + JSON.stringify(serieData) + '}';
                        seriesData.push(JSON.parse(jsonString));
                    });


                    Highcharts.setOptions({
                        global: {
                            useUTC: false
                        }
                    });
                    var chart = new Highcharts.Chart({
                        chart: {
                            type: 'bubble',
                            renderTo: 'chart',
                            inverted: true
                        },

                        title: {
                            text: null
                        },

                        xAxis: {
                            categories: categories
                        },

                        yAxis: {
                            title: {
                                text: null
                            },
                            min: -1,
                            max: 23,
                            allowDecimals: false,
                            showFirstLabel: false
                        },
                        plotOptions: {
                            bubble: {
                                minSize: 2,
                                maxSize: 30
                            }
                        },

                        tooltip: {
                            formatter: function () {
                                /**
                                 * var s = "<span style='font-size: 10px'>" +
                                 * Highcharts.dateFormat('%Y-%m-%d %H:%M', this.x) + "</span><br/>";
                                 */
                                var s = "<span style='font-size: 10px'>" +
                                        this.x + "</span><br/>";

                                s += "<span style=color:" + this.series.color + ">"
                                        + this.point.z
                                        + "</b>人次<br/>";
                                return s;
                            }},

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

                        series: seriesData
                    });


                    if (userDistribution.maxActiveArea) {
                        $("#statistics").show();
                    } else {
                        $("#statistics").hide();
                    }

                    if (moment(date, "YYYY-MM").isValid()) {
                        $("#queryDate").text(moment(date, "YYYY-MM").format("YYYY年MM月"));
                    } else {
                        $("#queryDate").text(moment(date, "YYYY").format("YYYY年"));
                    }

                    $("#maxActiveArea").text(userDistribution.maxActiveArea);
                    $("#maxActiveAreaCount").text(userDistribution.maxActiveAreaCount);
                    $("#maxActiveTime").text(userDistribution.maxActiveTime);
                    $("#maxActiveTimeRange").text(userDistribution.maxActiveTimeRange);
                    $("#maxActiveTimeCount").text(userDistribution.maxActiveTimeCount);
                    $("#maxActiveTimeArea").text(userDistribution.maxActiveTimeArea);
                    $("#maxActiveTimeAreaCount").text(userDistribution.maxActiveTimeAreaCount);
                } else {
                    $("#chart").empty();
                    $("#statistics").hide();
                    $("#noData").text("暂无数据");
                }
            });
        }).click();
    })
    ;
</script>
</#macro>
