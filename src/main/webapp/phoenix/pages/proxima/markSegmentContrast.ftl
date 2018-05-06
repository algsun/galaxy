<#--
标记段统计
@author liuzhu
@date 2013-08-06
@check @gaohui #5068 2013-08-15
-->
<#assign title="标记段对比 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:proxima:markSegmentContrast">

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">
<#macro head>

</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="title page-header">
            <h3>标记段对比</h3>
        </div>
    </div>
</div>
<div>
    <form id="marksementFrom" class="well well-small form-inline" action="proxima/markSegmentContrast"
          method="post">
        <#include "../_common/date-year-month-control.ftl">
        <input type="submit" class="btn" value="查询">
    </form>

    <h4 id="noData"></h4>

    <div id="container">
    </div>
</div>

    <#if markSegmentList??>
        <#if markSegmentList?size != 0>
        <div>
            <@alertMsg "${conclusion!}"/>
        </div>
        </#if>
    </#if>
<div class="hide">
    <dl id="markSegmentquency">
        <@markSegmentData/>
    </dl>
</div>
</#macro>

<#---->
<#macro markSegmentData >
<#-- 标记段名称 -->
<dt>
    [ <#list markSegmentList as markSegment>
    <#if markSegment_index != 0> , </#if> "${markSegment.markName}(${markSegment.placeName})"
</#list> ]
</dt>

<#-- 标记段变化长度 -->
<dd>
    [ <#list markSegmentList as markSegment>
    <#if markSegment_index != 0> , </#if> ${markSegment.lengthRealDelta}
</#list> ]
</dd>
</#macro>

<#macro script>

<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/index/date-format.js"/>
    <@scriptTag "js/date-year-month-control.js"/>
<script type="text/javascript">
    $(function () {
        // 标记段数据
        var markSegments = markSegmentData();
        if (markSegments[0].length === 0) {
            $("#container").text('暂无数据');
        } else {
            $('#container').highcharts(charOptions(markSegments[0], markSegments[1]));
        }

        // 从 dom 中解析数据，并返回
        function markSegmentData() {
            var $data = $('#markSegmentquency');
            var categories = $.parseJSON($data.find('dt').text());
            var d = $.parseJSON($data.find('dd').text());

            return [categories, d];
        }

        // 生成图表
        function charOptions(categories, data) {
            return {
                exporting: {enabled: false},               //下载按钮
                credits: {enabled: false},               //版权链接选项
                chart: {                                   //图标区选项
                    type: 'bar'
                },
                title: {                                   //标题选项
                    text: '标记段对比'
                },
                xAxis: {                                   //x轴选项
                    categories: categories
                },
                yAxis: {                                   //y轴选项
                    min: 0,
                    title: {
                        text: "单位（mm）"
                    }
                },
                legend: {                                 //图例选项
                    backgroundColor: '#FFFFFF',
                    reversed: false,
                    enabled: false
                },
                plotOptions: {                          //数据点选项
                    bar: {
                        dataLabels: {
                            enabled: true
                        }
                    }
                },
                series: [                               //数据列选项
                    {
                        name: '标记段',
                        data: data
                    }
                ]
            }
        }
    });
</script>
</#macro>

