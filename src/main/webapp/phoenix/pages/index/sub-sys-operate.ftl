<#--
业务系统操作
@author liuzhu
@date 2013.08.21

@check @wang.geng 2013年9月2日 #5271
-->

<#assign title="业务系统--数据分析">
<#include  "../_common/helper.ftl">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:index:subSysOperate">

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>业务系统</h3>
        </div>
    </div>
</div>
<div>
    <form id="zoneCtt" class="well well-small form-inline" action="index/subSysOperate" method="post">
            <label >数量</label>
            <select class="input-small" name="size" style="width: 60px;">
                <option value="5" <#if size==5>selected="selected"</#if>>5</option>
                <option value="10" <#if size==10>selected="selected"</#if>>10</option>
                <option value="20" <#if size==20>selected="selected"</#if>>20</option>
            </select>
            <#include "../_common/date-year-month-control.ftl">

            <input type="submit" id="query-windrose-chart" class="btn" value="查询">
    </form>
</div>
<div id=container></div>
<#if subsystemOperate??>
    <#if subsystemOperate?size != 0>
    <div style="clear: both;display: none;" id="msg" class="m-t-20">
        <@alertMsg "${conclusion!}"/>
    </div>
    </#if>
</#if>
<div class="hide" id="subSysOperateQuery">
    ${subSysOperateInfo}
</div>
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
    // 业务系统操作数据
    subSysOperateData();

    // 从 dom 中解析数据,并组装数据
    function subSysOperateData() {
        var $data = JSON.parse($('#subSysOperateQuery').text());
        var colorArray = new Array('#4876FF','#EEA2AD','#EEA2AD','#4876FF');//柱子的颜色
        var colorArrayIndex = 0;
        for(var i=0;i<$data.length;i++){
            var containerId = "container"+(i+1);//容器div的Id
            var containersData = "#"+ "container"+(i+1);
            var appendDiv = "<div id='"+containerId+"' class='f-l m-b-10' style='width: 340px;height: 340px;margin-left:8px'></div>"
            if(i==0){
                $("#container").after(appendDiv);
            }else{
                var containersBefore = "#"+ "container"+i;
                $(containersBefore).after(appendDiv);
            }
            //如果没有数据，则添加一张“暂无数据的图片”
            if($data[i].operates.yd.length==0 ){
                var subSysName = $data[i].subsystemName;
                $(containersData).append("<div style='font-size: 16px;color: #274b6d;text-align:center;margin-top: 10px;'> "+subSysName+"</div>")
                $(containersData).append('<div class="m-t-10" ><img src="images/noData.jpg"></img></div>');
                $(containersData).css({backgroundColor:"#ffffff"});
            }else{
                $("#msg").show();
                $(containersData).highcharts(charOptions($data[i].subsystemName,$data[i].operates.xd,$data[i].operates.yd,colorArray[colorArrayIndex]));
            }
            if((i)%4 == 0){
                colorArrayIndex = 0
            }
            colorArrayIndex++;
        }
    }

    // 生成图表
    function charOptions(sysName,categories, data,dataColor) {
        return {
            exporting:{enabled:false},               //下载按钮
            credits: {enabled: false},               //版权链接选项
            chart: {                                   //图标区选项
                type: 'bar',
                width:340,
                height:340,
                plotBorderColor:"red"
            },
            title: {                                   //标题选项
                text: sysName
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
                column: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            series: [                               //数据列选项
                {
                    color:dataColor,
                    name: '次数',
                    data: data
                }
            ]
        }
    }
    });
$(document).ready(function () {
    // 时间格式
    var format;
    //时间选择器
    $("#time").click(function () {
        WdatePicker({
            dateFmt: format,
            maxDate: '%y-%M-{%d-1}'
        });
    });
    //时间格式选择
    $("input[name='timeType']").change(function () {
        if (!$(this).is(':checked')) {
            return;
        }
        var tv = fmtType($(this).val());
        $("#time").val(tv);
    });

    function fmtType(type) {
        var tv = $("#time").val();
        if(type == 1){
            format = 'yyyy';
            DateUtil.formatDate(tv,DateUtil.formats.YEAR)
            return DateUtil.formatDate(tv, DateUtil.formats.YEAR);
        }else if (type == 2){
            format = 'yyyy-MM';
            return DateUtil.formatDate(tv, DateUtil.formats.MONTH);
        }
        return ;
    }
<#-- 注意：这里用到freemarker 了 -->
    fmtType(${timeType!});
});
</script>
</#macro>