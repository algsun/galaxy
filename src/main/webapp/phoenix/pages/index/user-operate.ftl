<#--
用户操作统计
@author xu.baoji
@author liuzhu
@date 2013.08.19

@check @wang.geng   2013年9月2日 #5250
-->

<#assign title="用户操作统计--数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:index:operate">

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>用户操作</h3>
        </div>
    </div>
</div>
<div>
    <form id="zoneCtt" class="well well-small form-inline" action="index/operate" method="post">
        <label>用户</label>
        <select class="input-small" name="email" style="width: 80px;">
            <#list users as user>
                <option value="${user.email}" <#if email??><#if user.email==email>selected="selected"</#if>
                        value="${user.email!}"</#if>  >${user.userName!}</option>
            </#list>
        </select>
        <label class="m-l-10">数量</label>
        <select class="input-small" name="size" style="width: 60px;">
            <option value="10"  <@selected 10,size/>>10</option>
            <option value="20" <@selected 20,size/>>20</option>
            <option value="30" <@selected 30,size/>>30</option>
        </select>
        <#include "../_common/date-year-month-control.ftl">
        <input type="submit" id="queryData" class="btn" value="查询">
    </form>
</div>

<div id="container" style="height: 500px;">
</div>
    <#if userOperates??>
        <#if userOperates?size != 0>
        <div class="m-t-5">
            <@alertMsg "${conclusion!}"/>
        </div>
        </#if>
    </#if>
<div class="hide">
    <dl id="userOperateQuency">
        <@userOperateData/>
    </dl>
</div>
</#macro>

<#---->
<#macro userOperateData >
<#-- 功能名称名称 -->
<dt>
    [ <#list userOperates as userOperate>
    <#if userOperate_index != 0> , </#if> "${userOperate.functionName}"
</#list> ]
</dt>

<#-- 功能记录次数 -->
<dd>
    [ <#list userOperates as userOperate>
    <#if userOperate_index != 0> , </#if> ${userOperate.count?c}
</#list> ]
</dd>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/index/date-format.js"/>
    <@scriptTag "js/date-year-month-control.js"/>

<script type="text/javascript">

    $(function () {
        // 标记段数据
        var userOperatets = userOperatetData();
        if (userOperatets[0].length === 0) {
            $("#container").text('暂无数据');
        } else {
            $('#container').highcharts(charOptions(userOperatets[0], userOperatets[1]));
        }

        // 从 dom 中解析数据，并返回
        function userOperatetData() {
            var $data = $('#userOperateQuency');
            var categories = $.parseJSON($data.find('dt').text());
            var numbers = $.parseJSON($data.find('dd').text());
            return [categories, numbers];
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
                    text: '用户操作记录'
                },
                xAxis: {                                   //x轴选项
                    categories: categories
                },
                yAxis: {                                   //y轴选项
                    min: 0,
                    title: {
                        text: "操作次数"
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
                        name: '次数',
                        data: data
                    }
                ]
            }
        }
    });
</script>
</#macro>