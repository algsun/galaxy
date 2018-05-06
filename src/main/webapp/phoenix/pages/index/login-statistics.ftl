<#--
登录习惯统计
@author li.jianfei
@date 2013-07-23
@check @duan.qixin 2013年7月29日 #4694
-->
<#assign title="登录习惯统计 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:index:loginStatistics">

<#include  "../_common/helper.ftl">
<#include "/common/pages/common-tag.ftl">

<#macro head>

</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="title page-header">
            <h3>登录习惯统计</h3>
        </div>
    </div>
</div>
<div class="m-b-10">
    <form class="form-inline well well-small" action="index/loginStatistics" method="post">
        <#include "../_common/date-year-month-control.ftl">
        <button id="commit" type="submit" class="btn">查询</button>
    </form>
</div>

<H4 id="noData"></H4>
<div id="chartData" class="hide">
${userLoginStatJson}
</div>
<div class="row-fluid m-t-20">
    <div class="span12">
        <div id="weekChart"></div>
    </div>
</div>
<div id="weekStatistics" class="row-fluid">
    <@alertNested>
        在<span class="queryDate"></span>，
        <#assign weekNameArray =["一","二","三","四","五","六","日"]>
        <strong>周${weekNameArray[maxInWeek]}</strong>系统登录人数最多。
        <br/>
        <#if (maxInWeek>=5)>
            双休日要注意休息，劳逸结合嘛。
        <#else>
            同志们工作很努力呀。
        </#if>
    </@alertNested>
</div>

<div class="row-fluid m-t-20">
    <div class="span12">
        <div id="dayChart"></div>
    </div>
</div>
<div id="dayStatistics" class="row-fluid">
    <@alertNested>
        在<span class="queryDate"></span>，
        <#assign timeNameArray =["凌晨","早上","上午","中午","下午","晚上"]>
        每天<strong>${timeNameArray[maxInDay]}</strong>系统登录人数最多。
        <br/>
        <#if (maxInDay==0 || maxInDay==5)>
            大家都习惯于下班后办公，要注意休息。
        <#elseif (maxInDay==1)>
            上班第一件事就是登陆系统。
        <#elseif (maxInDay==3)>
            午休很重要的。
        <#elseif (maxInDay==2 || maxInDay==4)>
            上班时间工作，系统使用效率很高呀。
        </#if>
    </@alertNested>
</div>
</#macro>

<#macro script>

<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/index/date-format.js"/>
    <@scriptTag "js/date-year-month-day-control.js"/>
<script type="text/javascript">
    $(function () {
        var dateType = $("input[name='dateType']:checked").val();
        var date = $("#date").val();

        var userLoginStat = JSON.parse($("#chartData").text());
        $("#noData").empty();
        if (userLoginStat.hasData) {

            var weekData = userLoginStat.weekStat,
                    dayData = userLoginStat.dayStat;

            var dateTitle;
            $queryDate = $(".queryDate");
            if (moment(date, "YYYY-MM").isValid()) {
                dateTitle = moment(date, "YYYY-MM").format("YYYY年MM月");
                $queryDate.text(moment(date, "YYYY-MM").format("YYYY年MM月"));
            } else {
                dateTitle = moment(date, "YYYY").format("YYYY年");
                $queryDate.text(moment(date, "YYYY").format("YYYY年"));
            }

            var chart = new Highcharts.Chart({
                chart: {
                    type: "column",
                    renderTo: 'weekChart',
                    marginLeft: 50,
                    marginRight: 50
                },

                title: {
                    text: "周几系统登录人数最多？"
                },
                subtitle: {
                    text: dateTitle
                },

                xAxis: {
                    categories: [
                        "周一",
                        "周二",
                        "周三",
                        "周四",
                        "周五",
                        "周六",
                        "周日"
                    ]
                },

                yAxis: {
                    title: {
                        text: null
                    },
                    labels: {
                        format: '{value}%'
                    },
                    max: 100,
                    min: 0
                },
                tooltip: {
                    valueSuffix: '%'
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
                        name: "登录百分比",
                        data: weekData
                    }
                ]
            });

            var chart = new Highcharts.Chart({
                chart: {
                    type: "column",
                    renderTo: 'dayChart',
                    marginLeft: 50,
                    marginRight: 50
                },

                title: {
                    text: "一天哪个时段登录人数最多？"
                },
                subtitle: {
                    text: dateTitle
                },

                xAxis: {
                    categories: [
                        "凌晨<br/>00:01-6:00",
                        "早上<br/>06:01-9:00",
                        "上午<br/>09:01-12:00",
                        "中午<br/>12:01-14:00",
                        "下午<br/>14:01-18:00",
                        "晚上<br/>18:01-00:00"
                    ]
                },

                yAxis: {
                    title: {
                        text: null
                    },
                    max: 100,
                    min: 0,
                    labels: {
                        format: '{value}%'
                    }
                },
                tooltip: {
                    valueSuffix: '%'
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
                        name: "登录百分比",
                        data: dayData
                    }
                ]
            });

        } else {
            $("#weekChart").empty();
            $("#dayChart").empty();
            $("#dayStatistics").hide();
            $("#noData").text("暂无数据");
            $("#weekStatistics").hide();
        }
    });
</script>
</#macro>
