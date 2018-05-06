<#--
用户登录时长统计
@author duan.qixin
@date 2013-08-12
@check @xu.baoji 2013-8-12 #5009
-->

<#assign title="用户登录时长 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:index:duration">

<#include  "../_common/helper.ftl">
<#include "/common/pages/common-tag.ftl" >

<#macro head>
<style type="text/css">
</style>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>登录时长</h3>
        </div>
    </div>
</div>
<div>
    <form id="zoneCtt" class="well well-small form-inline" action="index/duration" method="post">
        <#include "../_common/date-year-month-control.ftl">
        <label class="m-l-20">前</label>
        <select class="input-small" name="size">
            <option value="10" <@selected 10,size/>>10名</option>
            <option value="20" <@selected 20,size/>>20名</option>
            <option value="30" <@selected 30,size/>>30名</option>
        </select>
        <input type="submit" id="queryDate-chart" class="btn" value="查询">
    </form>
</div>

<div id="container" style="height: 500px;">
</div>

    <#if userNames != "">
    <div class="m-t-5">
        <@alertMsg "${conclusion!}"/>
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
    var durations = evalFmt("${durations!}");

    $(function () {
        if (userNames) {
            //登录时间最长
            hc_barView("container", {xd: userNames, yd: durations}, "登录时长统计");
        } else {
            $("#container").html("<h4 class='m-l-20'>暂无数据</h4>");
        }
    });

    //字符转JSON
    function evalFmt(o) {
        if (o) {
            return eval('(' + o + ')');
        }
    }
    ;

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
                title: {
                    text: '登录时长(单位：小时)',
                    enabled: true
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
                    name: '登录时长',
                    data: data.yd
                }
            ]
        });
    } ;
</script>
</#macro>