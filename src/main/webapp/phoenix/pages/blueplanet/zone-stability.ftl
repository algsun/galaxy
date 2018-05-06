<#--
区域稳定性对比
@author wanggeng
@date 2013-08-06
@check 2013-08-14 zhangpeng svn:4923
-->
<#assign title="区域稳定性对比 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:blueplanet:zoneStability">

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
<style type="text/css">
    .cbox {
        width: 30px;
        height: 18px;
        display: inline-block;
        background-color: #CCC;
        border-radius: 5px;
        margin-right: 5px;
        cursor: pointer;
    }

    .cboxed {
        width: 30px;
        height: 18px;
        display: inline-block;
        background-color: blue;
        border-radius: 5px;
        margin-right: 5px;
    }
</style>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="title page-header">
            <h3>区域稳定性对比</h3>
        </div>
    </div>
</div>
<div>
    <form id="zoneStabilityFrom" class="well form-inline well-small" action="blueplanet/zoneStability" method="post">
        <input id="target" type="hidden" name="target" value="${target!}"/>
        <#include "../_common/date-year-month-day-control.ftl">
        <input type="submit" id="query-windrose-chart" class="btn" value="查询">
    </form>
</div>

<div>
    <#if (sens?size>0)>
        <table class="table table-bordered table-center">
            <tbody>
            <tr>
                <#list sens as sen>
                    <#if sen_index%4 == 0>
                    </tr>
                    <tr>
                    </#if>
                    <td><span id=${(sen.sensorPhysicalid)!} class="cbox"></span>${(sen.cnName)!}（${(sen.units)!}）</td>
                </#list>
            <#--补齐table空td-->
                <#if !(sens?size%4 == 0)>
                    <#list 1..(4-sens?size%4) as i>
                        <td></td>
                    </#list>
                </#if>
            </tr>
            </tbody>
        </table>
    </#if>
</div>

<div id="container">
</div>
    <#if zoneNames != "">
    <div>
        <@alertMsg "${backup!}"/>
    </div>
    <p style="color: #969696">温馨提示：稳定性系数通过方差运算获得。方差是用来度量随机变量和均值之间的偏离程度，稳定性系数越小越稳定。</p>
    </#if>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/index/date-format.js"/>
    <@scriptTag "js/date-year-month-day-control.js"/>

<script type="text/javascript">

    var zoneNames = evalFmt("${zoneNames!}");
    var stabilityData = evalFmt("${stabilityData!}");
    var zoneNamesDc = evalFmt("${zoneNamesDc!}");
    var stabilityDataDc = evalFmt("${stabilityDataDc!}");
    var subTitle = "${formatTime!}";
    var sensCnName = '【${sensCnName!}】区域对比统计';
    var stability = '稳定性系数';

    $(function () {
        if (zoneNames) {
            //最频繁
            hc_barView("container", {xd: zoneNames, yd: stabilityData}, sensCnName);

        } else {
            $("#container").html("<h4 class='m-l-20'>无数据</h4>");
        }
    });

    //字符转JSON
    function evalFmt(obj) {
        if (obj) {
            return eval('(' + obj + ')');
        }
    }
    ;

    function hc_barView(id, data, sensCnName) {
        $('#' + id).highcharts({
            credits: {enabled: false},
            chart: {
                type: 'bar'
            },
            title: {
                text: sensCnName
            },
            subtitle: {
                text: subTitle
            },
            xAxis: {
                categories: data.xd
            },
            yAxis: {
                min: 0,
                title: {
                    text: stability
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
                    name: '稳定性',
                    data: data.yd
                }
            ]
        });
    }
    ;

    $(document).ready(function () {
        $("#${target}").attr("class", "cboxed");

        $(".cbox").click(function () {
            $("#target").val($(this).attr("id"));
            $("#zoneStabilityFrom").submit();
        });
    });
</script>

</#macro>