<#--
标记段幅度
@author duan.qixin
@date 2013-08-8
@check @xu.baoji 2013-8-12 #4790
-->

<#assign title="标记段幅度 - 数据分析">
<#include  "../_common/helper.ftl">
<#include "/common/pages/common-tag.ftl">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:proxima:markSegRender">
<#macro head>
<style type="text/css">
</style>
</#macro>

<#macro content>
    <#setting number_format="#">
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>标记段幅度</h3>
        </div>
    </div>
</div>
<div>
    <form id="zoneCtt" class="well well-small form-inline" action="proxima/markSegRender" method="post">
        <label class="m-l-10">标记段</label>
        <select class="input" name="markId">
            <#if (marks?size>0)>
                <#list marks as mark>
                    <option value="${mark.markId!}" <@selected mark.markId,markId></@selected>>${mark.markName!}
                        (${mark.placeName!})
                    </option>
                </#list>
            </#if>
        </select>
        <label class="m-l-10">时间</label>
        <input class="input-medium Wdate" id="time" type="text" name="year" value="${year!}">
        <input type="submit" id="query-windrose-chart" class="btn " value="查询">
    </form>
</div>

<div id="container">
</div>
    <#if monthData != "">
    <div class="m-t-5">
        <@alertMsg "${backup!}"/>
    </div>
    <p style="color: #969696">温馨提示：标记段变化幅度为本月月末标记段长度减去上个月月末标记段长度</p>
    </#if>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript">
    var monthData = evalFmt("${monthData!}");
    var markData = evalFmt("${markData!}");
    $(function () {
        if (monthData) {
            $('#container').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: '标记段变化幅度'
                },
                xAxis: {
                    categories: monthData
                },
                yAxis: {
                    title: {
                        text: '毫米'
                    }
                },
                legend: {                                 //图例选项
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                series: [markData]
            });
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

    $(document).ready(function () {
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