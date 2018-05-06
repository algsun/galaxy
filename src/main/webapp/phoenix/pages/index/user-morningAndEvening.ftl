<#--
用户登录时长
@author xu.baoji
@date 2013.08.20
@check li.jianfei 2013.09.02 #5259
-->


<#assign title="早晚考勤统计 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:index:morningAndEvening">

<#include  "../_common/helper.ftl">
<#include "/common/pages/common-tag.ftl">
<#macro head>
<style type="text/css">
</style>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>早晚考勤</h3>
        </div>
    </div>
</div>
<div>
    <form id="zoneCtt" class="well well-small form-inline" action="index/morningAndEvening" method="post">
        <#include "../_common/date-year-month-control.ftl">
        <label class="m-l-10">前</label>
        <select class="input-small" name="dataSize">
            <option value="10" <@selected 10,dataSize/>>10名</option>
            <option value="20" <@selected 20,dataSize/>>20名</option>
            <option value="30" <@selected 30,dataSize/>>30名</option>
        </select>
        <input type="submit" id="query-windrose-chart" class="btn " value="查询">
    </form>
</div>


<div id="morning" style="height: 500px;">
</div>

    <#if morningConclusion ??>
    <div class="m-t-5">
        <@alertMsg "${morningConclusion!}"/>
    </div>
    </#if>
<hr/>
<div id="evening" style="height:500px;">
</div>
    <#if eveningConclusion ??>
    <div class="m-t-5">
        <@alertMsg "${eveningConclusion!}"/>
    </div>
    </#if>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript"  src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/index/date-format.js"/>
    <@scriptTag "js/date-year-month-control.js"/>
<script type="text/javascript">

    $(function () {
        var morningdata = ${morningChartData};
        var eveningdata = ${eveningChartData};
        if (morningdata != null) {
            hc_barView('morning', morningdata, '最早开始工作次数统计');
            hc_barView('evening', eveningdata, '最晚离开次数统计');
        } else {
            $("#morning").html("<h4 class='m-l-20'>暂无数据</h4>");
        }
    });

    //数据图表
    function hc_barView(id, data, title, ytext) {
        $('#' + id).highcharts({
            credits: {enabled: false},
            chart: {
                type: 'bar'
            },
            title: {
                text: title
            },
            xAxis: {
                categories: data.xd
            },
            yAxis: {
                min: 0,
                title: {
                    text: '单位（次）'
                }
            },
            legend: {
                backgroundColor: '#FFFFFF',
                reversed: false,
                enabled: false
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            tooltip: {
                formatter: function () {
                    var s = "<span style='font-size: 10px'>" +
                            this.x + "</span><br/>";
                    s += "<span style=color:" + this.series.color + ">"
                            + this.y
                            + "</b> 次<br/>";
                    return s;
                }},
            series: [
                {
                    data: data.yd
                }
            ]
        });
    }
    ;

</script>
</#macro>