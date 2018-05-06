<#--
区域稳定性对比
@author chenyaofei
@date 2016-10-08
-->
<#assign title="区域数据分布 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:blueplanet:dataDistribution">

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
    .bg-Color{
        background-color: rgba(123,104,238,0.1);
    }
</style>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>区域数据分布</h3>
        </div>
    </div>
</div>
<div>
    <form id="zoneDataDistribution" action="blueplanet/zoneDataDistribution" method="post">
        <div class="m-b-10 form-inline well well-small">
            <label class="control-label m-l-5">${locale.getStr("common.zone")}</label>
            <input id="zoneName" class="zone" type="text"
                   name="zoneName" data-zoneId="" style="width: 150px" value="${zoneName!}">
            <input type="hidden" id="zoneId" name="zoneId" value="${zoneId!}"/>
            <input id="sensorId" type="hidden" name="sensorId" value="${sensorId!}"/>
            <#include "../_common/date-year-month-day-control.ftl">
            <input type="submit" id="query-windrose-chart" class="btn m-l-5" value="查询">
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
<div class="hide">
    <div id="zoneTreeDialog" class="span4">
        <div id="zoneTree" class="ztree" style="overflow: auto;height: 400px"></div>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>
    <#if (locationDateList??) && (locationDateList?size>0)>

    <#--图表-->
    <div id="chart">
        <div id="container" style="height: 350px">
        </div>
        <div id="container1" style="height: 350px">
        </div>
    </div>

    <#--表格-->
        <table id="locationData" class="table table-bordered m-t-30">
            <thead class="bg-Color">
                <tr style="text-align: center">
                    <td rowspan="2" style="padding-top :4%;text-align: center;">监测点</td>
                    <td colspan="4" style="text-align: center">${sensCnName!}(${sensUnits!})</td>
                </tr>
                <tr>
                    <td style="text-align: center">平均值</td>
                    <td style="text-align: center">最大值</td>
                    <td style="text-align: center">最小值</td>
                    <td style="text-align: center">波动范围</td>
                </tr>
            </thead>
            <tbody>
              <#list locationDateList as locationDate>
                  <tr>
                      <td class="bg-Color" style="text-align: center">${locationDate.locationName!}</td>
                      <td style="text-align: center">${locationDate.avgValue!}</td>
                      <td style="text-align: center">${locationDate.maxValue!}</td>
                      <td style="text-align: center">${locationDate.minValue!}</td>
                      <td style="text-align: center">${locationDate.waveValue!}</td>
                  </tr>
              </#list>
            </tbody>
        </table>
        <#--封装图数据 -->
        <div id="chartData" class="hide">
           [
           <#if locationDateList?? && (locationDateList?size > 0) >
           <#list locationDateList as locationDate>
               {
                "locationName" : "${locationDate.locationName!}",
                "avgValue" : ${locationDate.avgValue!},
                "maxValue" : ${locationDate.maxValue!},
                "boxplotData" : [
               <#if boxplotDataMap?? && (boxplotDataMap?size > 0)>
                   <#list boxplotDataMap[locationDate.locationId] as value>
                   ${value}
                       <#if value_has_next>,</#if>
                   </#list>
               </#if>

               ]}
               <#if locationDate_has_next>,</#if>
           </#list>
           </#if>
            ]
        </div>
    <#else>
        <h4 class='m-l-20'>无数据</h4>
    </#if>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/echarts/3.2.3/echarts.min.js"></script>

    <@scriptTag "js/index/date-format.js"/>
    <@scriptTag "js/date-year-month-day-control.js"/>
<script type="text/javascript">

    $(document).ready(function () {

    //标记选中监测指标
        $("#${sensorId}").attr("class", "cboxed");

        $(".cbox").click(function () {
            $("#sensorId").val($(this).attr("id"));
            $("#zoneDataDistribution").submit();
        });

    //区域查询

        // ztree 树配置
        var setting = {
            view: {
                showLine: false
            },
            async: {
                enable: true,
                url: '../blackhole/zone/children.json',
                autoParam: ["id=zoneId"]
            }
        };
        var showDialog = function ($zoneInput, zoneTree) {
            var $help = $("#zoneTreeDialog .help-block");
            art.dialog({
                id: "zoneTreeDialog",
                title: "选择区域",
                content: $("#zoneTreeDialog")[0],
                fixed: true,
                okValue: "确定",
                ok: function () {
                    var nodes = zoneTree.getSelectedNodes();
                    if (nodes.length == 0) {
                        $help.empty().append("请选择区域");
                        return false;
                    }
                    var node = nodes[0];
                    $zoneInput.val(node.name);
                    $zoneInput.data("zoneId", node.id);
                    $("#zoneId").val(node.id);
                },
                cancelValue: "取消",
                cancel: function () {
                    $help.empty();
                },
                button: [
                    {
                        value: "清空",
                        callback: function () {
                            $zoneInput.val('');
                            $zoneInput.next().val('');
                        }
                    }
                ]
            });
        };

// 区域输入框获取焦点时
        $(".zone").focus(function () {
            var $this = $(this);
            $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
                // 初始化树
                $.fn.zTree.init($('#zoneTree'), setting, result);
                var zoneTree = $.fn.zTree.getZTreeObj("zoneTree");

                // 初始化弹出框
                showDialog($this, zoneTree);
            });
        });
    //图表
        var $dateType = ${dateType!};
        var $date = $("#date").val();
        var $formatTime = "";
        if($dateType == 1){
            $formatTime = $date.substr(0,4) + "年";
        }else if($dateType == 2){
            $formatTime = $date.substr(0,4) + "年" + $date.substring(5,7) + "月";
        }else{
            $formatTime = $date.substr(0,4) + "年" + $date.substring(5,7) + "月" + $date.substring(8,10) + "日";
        }
        var titleSensCnName = $formatTime + '【${sensCnName!}】区域数据分布';
        var $customYAxisTitle = "${sensCnName!}（${sensUnits!}）";
        var $columnYAxisTitle = "${sensCnName!}平均日较差（${sensUnits!}）";
        //获取位置点对象数据
        var $htmlData =$("#chartData").html();
        if(($htmlData != null) && ($htmlData.length > 0)){
            var $chartData = $.parseJSON($htmlData);
            var $xAxisName = '[';
            var $maxValue = '[';
            var $avgValue = '[';
            var $boxplotData = '[';

            //组织数据
            $.each($chartData, function(i,value){
                $xAxisName = $xAxisName + '"' + value.locationName + '"' + ',';
                $maxValue = $maxValue + value.maxValue + ',';
                $avgValue = $avgValue + value.avgValue + ',';
                var  boxplotData = value.boxplotData;
                $boxplotData = $boxplotData + "[";
                $.each(boxplotData, function(i,boxplot){
                    $boxplotData = $boxplotData + boxplot + ',';
                });
                $boxplotData = $boxplotData.substr(0,$boxplotData.length-1) + "],";
            });

            if($boxplotData.length > 1){
                //转换为chart格式
                $xAxisName = $xAxisName.substring(0,$xAxisName.length-1) + ']';
                $xAxisName = $.parseJSON($xAxisName);
                $maxValue = $maxValue.substring(0,$maxValue.length-1) + ']';
                $maxValue = $.parseJSON($maxValue);
                $avgValue = $avgValue.substring(0,$avgValue.length-1) + ']';
                $avgValue = $.parseJSON($avgValue);
                $boxplotData = $boxplotData.substring(0,$boxplotData.length-1) + ']';
                $boxplotData = $.parseJSON($boxplotData);

                //数据成图
                var option = {
                    title: [
                        {
                            text: titleSensCnName,
                            left: 'center',
                        }
                    ],
                    backgroundColor: '#F2F0FE',
                    tooltip: {
                        trigger: 'item',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    grid: {
                        top: '30%',
                        left: '15%',
                        right: '10%',
                        bottom: '5%'
                    },
                    xAxis: {
                        position  : 'top',
                        offset : 10,
                        type: 'category',
                        data: $xAxisName,
                        boundaryGap: true,
                        nameGap: 30,
                        splitArea: {
                            show: false
                        },
                        axisLabel: {
                            formatter: '{value}'
                        },
                        splitLine: {
                            show: false
                        },
                        axisLabel :{
                            interval:0,
                            rotate: -15
                        }
                    },
                    yAxis: [{
                        nameLocation: 'middle',
                        nameGap: 40,
                        name : $customYAxisTitle,
                        type: 'value',
                        scale: true
                    }],
                    series: [
                        {
                            yAxisIndex : 0,
                            name: 'boxplot',
                            type: 'boxplot',
                            data: $boxplotData,
                            tooltip: {
                                formatter: function (param) {
                                    return [
                                        param.name + ': ',
                                        '上边缘: ' + param.data[4],
                                        '上四分位: ' + param.data[3],
                                        '中位数: ' + param.data[2],
                                        '下四分位: ' + param.data[1],
                                        '下边缘: ' + param.data[0]
                                    ].join('<br/>')
                                }
                            }
                        }

                    ]
                };
                // 基于准备好的dom，初始化echarts图表
                var myChart = echarts.init($("#container")[0]);
                // 为echarts对象加载数据
                myChart.setOption(option);

                var option2 = {
                    grid: {
                        top: '5%',
                        left: '15%',
                        right: '10%',
                        bottom: '25%'
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    backgroundColor: '#F2F0FE',
                    color: ['#0067E9','#000204'],
                    xAxis:
                    {   position  : 'bottom',
                        offset : 10,
                        // onZero: false,
                        type: 'category',
                        boundaryGap: true,
                        //轴与名称的距离
                        nameGap: 30,
                        data: $xAxisName,
                        //轴名称间距
                        axisLabel :{
                            interval:0,
                            //旋转防止重叠
                            rotate: 15
                        }
                    },
                    yAxis:
                    {
                        type: 'value',
                        scale: true,
                        nameLocation: 'middle',
                        nameGap: 40,
                        name : $columnYAxisTitle,
                        inverse: true
                    }
                    ,
                    series: [
                        {
                            name:'最大值',
                            type:'bar',
                            data: $maxValue
                        },
                        {
                            name:'平均值',
                            type:'line',
                            data: $avgValue
                        }
                    ]
                };

                // 基于准备好的dom，初始化echarts图表
                var myChart2 = echarts.init($("#container1")[0]);
                // 为echarts对象加载数据
                myChart2.setOption(option2);
                //多图联动
                echarts.connect([myChart, myChart2]);
            }
        }
    });

</script>

</#macro>