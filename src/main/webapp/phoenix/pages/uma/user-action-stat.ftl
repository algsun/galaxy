<#--
规则触发频率
@author duan.qixin
@date 2013-07-15
@check @xu.baoji 2013年7月18日 #4589
-->

<#assign title="规则触发频率 - 数据分析">

<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:uma:userActionStat">
<#include  "../_common/helper.ftl">
<#include "/common/pages/common-tag.ftl">

<#macro head>

</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>规则触发频率</h3>
        </div>
    </div>
</div>
<div>
    <form id="zoneCtt" class="well well-small form-inline" action="uma/userActionStat" method="post">
        <#include "../_common/date-year-month-control.ftl">
        <input type="submit" id="query-windrose-chart" class="btn" value="查询">
    </form>
</div>


<div id="container">
</div>
    <#if actionNames != "">
    <div class="m-t-5">
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
    var actionNames = evalFmt("${actionNames!}");
    var countData = evalFmt("${countData!}");

    $(function () {
        if (actionNames) {
            hc_barView("container", {xd: actionNames, yd: countData}, "规则触发频率");
        } else {
            $("#container").html("<h4 class='m-l-20'>无数据</h4>");
        }
    });

    //字符转JSON
    function evalFmt(o) {
        if (o) {
            return eval('(' + o + ')');
        }
    };

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
                    text: '触发次数',
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
                    name: '触发次数',
                    data: data.yd
                }
            ]
        });
    };

</script>
</#macro>