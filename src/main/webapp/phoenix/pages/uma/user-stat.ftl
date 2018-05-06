<#--
人员活动频率
@author duan.qixin
@date 2013-07-15
@check @xu.baoji 2013年7月18日 #4589
-->

<#assign title="人员活动频率 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:uma:userStat">

<#include  "../_common/helper.ftl">
<#include "/common/pages/common-tag.ftl">

<#macro head>

</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>人员活动频率</h3>
        </div>
    </div>
</div>
<div>
    <form id="zoneCtt" class="well well-small form-inline" action="uma/userStat" method="post">
        <#include "../_common/date-year-month-control.ftl">
        <label class="m-l-10">前</label>
        <select class="input-small" name="dataSize">
            <option value="10" <@selected 10, dataSize />>10名</option>
            <option value="20" <@selected 20, dataSize />>20名</option>
            <option value="30" <@selected 30, dataSize />>30名</option>
        </select>
        <input type="submit" id="query-windrose-chart" class="btn" value="查询">
    </form>
</div>


<div id="container" style="height: 500px;">
</div>
    <#if userNames != "">
    <div>
        <@alertMsg "${backup!}"/>
    </div>
    </#if>
</#macro>

<#macro script>

<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/index/date-format.js"/>
    <@scriptTag "js/date-year-month-control.js"/>

<script type="text/javascript">
    var userNames = evalFmt("${userNames!}");
    var countData = evalFmt("${countData!}");

    $(function () {
        if (userNames) {
            //最频繁
            hc_barView("container", {xd: userNames, yd: countData}, "人员活动频率");
        } else {
            $("#container").html("<h4 class='m-l-20'>无数据</h4>");
        }
    });

    //字符转JSON
    function evalFmt(o) {
        if (o) {
            return eval('(' + o + ')');
        }
    }

    //数据图表
    function hc_barView(id, data, title) {
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
                    text: '活动次数',
                    enabled: false
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
            series: [
                {
                    name: '活动次数',
                    data: data.yd
                }
            ]
        });
    }
</script>
</#macro>