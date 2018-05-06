<#--
@author 段启鑫
@date 2013-07-04

@check li.jianfei 2013-07-08 #4416
-->

<#assign title="区域对比统计 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:blueplanet:zoneContrast">

<#include  "../_common/helper.ftl">
<#include "/common/pages/common-tag.ftl">

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
        <div class="page-header title">
            <h3>区域对比统计</h3>
        </div>
    </div>
</div>
<div>
    <form id="zoneCtt" action="zoneContrast.action" method="post">
        <div class="m-b-10 form-inline well well-small">
            <input id="target" type="hidden" name="target" value="${target!}"/>
            <#include "../_common/date-year-month-day-control.ftl">
            <input type="submit" id="query-windrose-chart" class="btn" value="查询">
        </div>
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
                    <td><span id=${(sen.sensorPhysicalid)!}class="cbox"></span>${(sen.cnName)!}（${(sen.units)!}）</td>
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

<div id="noData"></div>
    <#if indoorZoneNames != "">
    <div class="row">
        <div class="span8">
            <div id="container1"></div>
            <div>
                <@alertMsg "${indoorMarginConclusion!}"/>
            </div>
        </div>

        <div class="span8">
            <div id="container3"></div>
            <div>
                <@alertMsg "${indoorContrastConclusion!}"/>
            </div>
        </div>
    </div>
    <hr/>
    </#if>
    <#if outdoorZoneNames != "">
    <div class="row">
        <div class="span8">
            <div id="container2"></div>
            <div>
                <@alertMsg "${outdoorMarginConclusion!}"/>
            </div>
        </div>

        <div class="span8">
            <div id="container4"></div>
            <div>
                <@alertMsg "${outdoorContrastConclusion!}"/>
            </div>
        </div>
    </div>
    </#if>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/index/date-format.js"/>
    <@scriptTag "js/date-year-month-day-control.js"/>
|
<script type="text/javascript">
    var indoorShowFlag = false;
    var outdoorShowFlag = false;
    var formatUon = '计量单位 ( ${uom!} )';
    var unit = '${uom!}';
    var sensCnName = '${sensCnName!}范围';
    var subTitle = "${formatTime!}";

    var indoorZoneNames = "${indoorZoneNames!}";
    if (indoorZoneNames) {
        indoorZoneNames = indoorZoneNames.split("$");
        indoorShowFlag = true;
    }
    var indoorZoneMargin = [];
    var indoorZoneMarginTemp = "${indoorZoneMargin!}";
    if (indoorZoneMarginTemp != "") {
        indoorZoneMargin = eval('(' + indoorZoneMarginTemp + ')');
    }
    var indoorZoneContrast = [];
    var indoorZoneContrastTemp = "${indoorZoneContrast!}";
    if (indoorZoneContrastTemp != "") {
        indoorZoneContrast = eval('(' + indoorZoneContrastTemp + ')');
    }

    var outdoorZoneNames = "${outdoorZoneNames!}";
    if (outdoorZoneNames) {
        outdoorZoneNames = outdoorZoneNames.split("$");
        outdoorShowFlag = true;
    }
    var outdoorZoneMargin = [];
    var outdoorZoneMarginTemp = "${outdoorZoneMargin!}";
    if (outdoorZoneMarginTemp != "") {
        outdoorZoneMargin = eval('(' + outdoorZoneMarginTemp + ')');
    }
    var outdoorZoneContrast = [];
    var outdoorZoneContrastTemp = "${outdoorZoneContrast!}";
    if (outdoorZoneContrastTemp != "") {
        outdoorZoneContrast = eval('(' + outdoorZoneContrastTemp + ')');
    }

    $(function () {
        //设置高度
        if (indoorShowFlag) {
            $('#container1').highcharts({
                chart: {
                    type: 'columnrange',
                    inverted: true,
                    spacingRight: 30
                },
                title: {
                    text: '区域幅度统计(室内)'
                },
                subtitle: {
                    text: subTitle
                },
                xAxis: {
                    categories: indoorZoneNames
                },
                yAxis: {
                    title: {
                        text: formatUon
                    }
                },
                tooltip: {
                    valueSuffix: unit
                },
                plotOptions: {
                    columnrange: {
                        dataLabels: {
                            enabled: true,
                            formatter: function () {
                                return this.y;
                            }
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                series: [
                    {
                        name: sensCnName,
                        data: indoorZoneMargin
                    }
                ]
            });

            $('#container3').highcharts({
                chart: {
                    type: 'bar',
                    spacingRight: 30
                },
                title: {
                    text: '区域对比统计(室内)'
                },
                subtitle: {
                    text: subTitle
                },
                xAxis: {
                    categories: indoorZoneNames,
                    title: {
                        text: null
                    }
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: formatUon
                    }
                },
                tooltip: {
                    valueSuffix: unit
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            enabled: true
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                series: [
                    {
                        name: subTitle,
                        data: indoorZoneContrast
                    }
                ]
            });
        }
        if (outdoorShowFlag) {
            $('#container2').highcharts({
                chart: {
                    type: 'columnrange',
                    inverted: true,
                    spacingRight: 30
                },
                title: {
                    text: '区域幅度统计(室外)'
                },
                subtitle: {
                    text: subTitle
                },
                xAxis: {
                    categories: outdoorZoneNames
                },
                yAxis: {
                    title: {
                        text: formatUon
                    }
                },
                tooltip: {
                    valueSuffix: unit
                },
                plotOptions: {
                    columnrange: {
                        dataLabels: {
                            enabled: true,
                            formatter: function () {
                                return this.y;
                            }
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },

                series: [
                    {
                        name: sensCnName,
                        data: outdoorZoneMargin
                    }
                ]
            });

            $('#container4').highcharts({
                chart: {
                    type: 'bar',
                    spacingRight: 30
                },
                title: {
                    text: '区域对比统计(室外)'
                },
                subtitle: {
                    text: subTitle
                },
                xAxis: {
                    categories: outdoorZoneNames,
                    title: {
                        text: null
                    }
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: formatUon
                    }
                },
                tooltip: {
                    valueSuffix: unit
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            enabled: true
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                credits: {
                    enabled: false
                },
                series: [
                    {
                        name: subTitle,
                        data: outdoorZoneContrast
                    }
                ]
            });
        }
        if (!indoorShowFlag && !outdoorShowFlag) {
            $("#noData").html("<h4 >暂无数据</h4>");
        }
    });

    $(document).ready(function () {
        $("#${target}").attr("class", "cboxed");

        $(".cbox").click(function () {
            $("#target").val($(this).attr("id"));
            $("#zoneCtt").submit();
        });
    });
</script>

</#macro>