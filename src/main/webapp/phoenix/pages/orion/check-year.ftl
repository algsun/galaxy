<#--
@author duan.qixin
@date 2013-07-15
@check @xu.baoji 2013年7月18日 #4589

@check @wanggeng 2013年7月29日 #4670
-->


<#assign title="年度盘点统计 - 数据分析">
<#include  "../_common/helper.ftl">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:orion:checkYear">
<#macro head>
<style type="text/css">
</style>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>年度盘点统计</h3>
        </div>
    </div>
</div>

<div id="container">
</div>
<div id="container1" class="m-t-10">
</div>

    <#if yearData != "">
    <div>
        <@alertMsg "${backup!}"/>
    </div>
    </#if>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript">
    var yearData = evalFmt("${yearData!}");
    var relicData = evalFmt("${relicData!}");
    var relicYearData = evalFmt("${relicYearData!}");


    $(function () {
        if (yearData) {
            hc_barView("container", {
                years: yearData,
                name: ["盘点次数"],
                xd: relicData.cData
            }, "文物盘点");
            hc_barView("container1", {
                years: yearData,
                name: ["年度盘点识别率(%)"],
                xd: relicYearData.cData
            }, "文物盘点");
        } else {
            $("#container").html("<h4 class='m-l-20'>暂无数据</h4>");
        }
    });

    function hc_barView(id, data, text) {
        $('#' + id).highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: text
            },
            xAxis: {
                categories: data.years
            },
            yAxis: {
                min: 0,
                title: {
                    text: text
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y}</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            credits: {
                enabled: false
            },
            series: [{
                name: data.name[0],
                data: data.xd

            }]
        });
    };

    //字符转JSON
    function evalFmt(o) {
        if (o) {
            return eval('(' + o + ')');
        }
    };

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