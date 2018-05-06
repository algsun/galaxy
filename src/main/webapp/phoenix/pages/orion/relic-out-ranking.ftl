<#--
文物出库排名

@author gaohui
@date 2013-07-9
@check @duan.qixin 13-7-12 #4494-->
-->


<#assign title = "文物出库排名 - 数据分析">
<#assign currentPrivilege = "phoenix:orion:relicOutRanking">
<#include "/common/pages/common-tag.ftl"/>

<#macro head>

</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="title page-header">
            <h3>文物出库排名</h3>
        </div>
    </div>
</div>

<div class="row-fluid">
    <div class="span12">
        <form class="form-inline well well-small" method="post" action="orion/relic-out-ranking">
            <label>年份</label>
            <select class="input-small" name="year">
                <#if maxYear == minYear>
                    <option value="${minYear?c}">${minYear?c}年</option>
                <#else>
                    <#list (maxYear..minYear) as y>
                        <option value="${y?c}" <@selected y, year/>>${y?c}年</option>
                    </#list>
                </#if>
            </select>

            <label class="m-l-20">前</label>
            <select class="input-small" name="topN">
                <option value="10" <@selected 10, topN/>>10名</option>
                <option value="20" <@selected 20, topN/>>20名</option>
                <option value="30" <@selected 30, topN/>>30名</option>
            </select>

            <button class="btn" type="submit">查询</button>
        </form>
    </div>
</div>


<div class="row">
    <div class="span4">

        <h4>年度出库排名</h4>

        <div id="container" style="height: 500px;"></div>
    </div>

    <div class="offset1 span4">

        <h4>出库总排名</h4>

        <div id="container2" style="height: 500px;"></div>
    </div>
</div>

<div class="hide">
    <dl id="yearFrequency">
        <@relicFrequencyData relicFrequenciesOfYear/>
    </dl>

    <dl id="allFrequency">
        <@relicFrequencyData relicFrequenciesAll/>
    </dl>
</div>

</#macro>

<#---->
<#macro relicFrequencyData relicFrequencies>
<#-- 文物名称 -->
<dt>
    [ <#list relicFrequencies as relicFreq>
    <#if relicFreq_index != 0> , </#if> "(${relicFreq.relicName})${relicFreq.relicTotalCode}"
</#list> ]
</dt>

<#-- 次数 -->
<dd>
    [ <#list relicFrequencies as relicFreq>
    <#if relicFreq_index != 0> , </#if> ${relicFreq.count}
</#list> ]
</dd>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>

<script type="text/javascript">
    $(function () {
        // 年度排名数据
        var yearFreqData = relicFreqData('yearFrequency');
        // 总排名数据
        var allFreqData = relicFreqData('allFrequency');
        if (yearFreqData[0].length === 0) {
            $("#container").text('暂无数据');
        } else {
            $('#container').highcharts(charOptions(yearFreqData[0], yearFreqData[1]));
        }

        if (allFreqData[0].length === 0) {
            $('#container2').text('暂无数据');
        } else {
            $('#container2').highcharts(charOptions(allFreqData[0], allFreqData[1]));
//            Highcharts.chart('#container2',charOptions(yearFreqData[0], yearFreqData[1]),test());
        }

        // 从 dom 中解析数据，并返回
        function relicFreqData(dataId) {
            var $data = $('#' + dataId);
            var categories = $.parseJSON($data.find('dt').text());
            var data = $.parseJSON($data.find('dd').text());
            return [categories, data];
        }

        // 生成图表
        function charOptions(categories, data) {
            return {
                credits: {enabled: false},
                chart: {
                    type: 'bar'
                },
                title: {
                    text: '出库次数'
                },
                xAxis: {
                    categories: categories
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '出库次数',
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
                        name: '出库次数',
                        data: data
                    }
                ]
            }
        };
    });
</script>

</#macro>