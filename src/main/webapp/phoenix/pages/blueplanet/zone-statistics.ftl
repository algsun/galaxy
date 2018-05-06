<#--
区域环境统计
@author li.jianfei
@date 2013-07-03
@check duan.qixin 2013-7-8 #4409
-->
<#assign title="区域环境统计 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:blueplanet:zoneStats">

<#include "/common/pages/common-tag.ftl">

<#macro head>

</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="title page-header">
            <h3>区域环境统计</h3>
        </div>
    </div>
</div>
<div class="m-b-10 form-inline well well-small">

    <label>区域</label>
    <input id="zoneId" name="zoneId" type="hidden" value="${zoneId!}">
    <input id="zone-input" class="input-medium" name="zoneName" data-zone-id="" type="text"
           value="${zoneName!}">
    <#include "../_common/date-year-month-control.ftl">
    <button id="commit" class="btn">查询</button>
</div>

<h5 style="color: #ccc">温馨提示：红色虚线为警戒线</h5>

<H4 id="noData"></H4>
<div id="chartList">
</div>
<div class="hide">
    <div id="chartTemp">
        <div class="row-fluid m-t-20">
            <div class="span12">
                <div data-chart></div>
            </div>
        </div>
    </div>
    <div id="zoneTreeDialog" class="span4" style="height:400px;overflow:auto">
        <ul id="zoneTree" class="ztree"></ul>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>
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
//获取数据
function getData(zoneId, dateType, date, getTickInterval) {
    var dataFormat = "";
    if(dateType ==1){
        dataFormat = "%Y-%m";
    }else if(dateType ==2){
        dataFormat = "%Y-%m-%d";
    }
    $.getJSON("index/chart.json", {zoneId: zoneId, dateType: dateType, date: date}, function (zoneStatisticsList) {
        $("#chartList").empty();
        $("#noData").empty();
        var haveData = false;
        $.each(zoneStatisticsList, function (index, zoneStatistics) {
            if (zoneStatistics.hasData) {
                var target = zoneStatistics.target ;
                var floating = zoneStatistics.floating;


                var lines =[];
                if(target !=0 && floating==0){
                    var temp = {
                        width: 2,
                        dashStyle : "dash",
                        label:{
                            align:"right",
                            text:"",
                            x:-15
                        }
                    };
                    temp.value = target;
                    temp.label.text = target+zoneStatistics.units;
                    temp.color = "green";
                    lines.push(temp);
                }

                if(target != 0 && floating != 0){
                    var temp1 = {
                        width: 2,
                        dashStyle : "dash",
                        label:{
                            align:"right",
                            text:"",
                            x:-15
                        }
                    };
                    temp1.value = target+floating;
                    temp1.label.text = temp1.value + zoneStatistics.units ;
                    temp1.color = "red";
                    lines.push(temp1);
                    var temp2 = {
                        width: 2,
                        dashStyle : "dash",
                        label:{
                            align:"right",
                            text:"",
                            x:-15
                        }
                    };
                    temp2.value = target-floating;
                    temp2.label.text = temp2.value + zoneStatistics.units ;
                    temp2.color = "red";
                    lines.push(temp2);
                }

                haveData = true;
                var $chartDiv = $("#chartTemp").children().last().clone();
                $("#chartList").append($chartDiv);

                var ranges = zoneStatistics.maxAndMinDatas,
                        averages = zoneStatistics.aveDatas;
                Highcharts.setOptions({
                    global: {
                        useUTC: false
                    }
                });
                var chart = new Highcharts.Chart({
                    chart: {
                        renderTo: $chartDiv[0],
                        height: 300,
                        marginLeft: 50,
                        marginRight: 50
                    },

                    title: {
                        text: zoneStatistics.cnName
                    },

                    xAxis: {
                        type: 'datetime',
                        tickInterval: getTickInterval(),
                        dateTimeLabelFormats: {
                            day: '%Y-%m-%d',
                            month: '%Y-%m',
                            year: '%Y'
                        },
                        labels: {
                            rotation: 30
                        }
                    },

                    yAxis: {
                        title: {
                            text: null
                        },
                        plotLines:lines
                    },

                    tooltip: {
                        xDateFormat: dataFormat,
                        crosshairs: true,
                        shared: true,
                        valueSuffix: zoneStatistics.units
                    },

                    legend: {
                        enabled: false
                    },

                    exporting: {
                        enabled: false
                    },

                    credits: {
                        enabled: false
                    },

                    navigation: {
                        buttonOptions: {
                            enabled: false
                        }
                    },

                    series: [
                        {
                            name: '平均' + zoneStatistics.cnName,
                            data: averages,
                            zIndex: 1,
                            marker: {
                                fillColor: 'white',
                                lineWidth: 2,
                                lineColor: Highcharts.getOptions().colors[0]
                            }
                        },
                        {
                            name: '最大值-最小值',
                            data: ranges,
                            type: 'arearange',
                            lineWidth: 0,
                            linkedTo: ':previous',
                            color: Highcharts.getOptions().colors[0],
                            fillOpacity: 0.3,
                            zIndex: 0
                        }
                    ]
                });
            }
        });
        if (!haveData) {
            $("#noData").text("暂无数据");
        }
    });
}

$(function () {
    $("input[name='dateType']").change(function () {
        var momentFormat;
        var my97Format;
        var $this = $(this);
        if (!$this.is(':checked')) {
            return;
        }
        switch ($this.val()) {
            case "1":
                momentFormat = DateUtil.formats.YEAR;
                my97Format = 'yyyy';
                break;
            case "2":
                momentFormat = DateUtil.formats.MONTH;
                my97Format = 'yyyy-MM';
                break;
        }
        var $timeInput = $("#date");
        var value = DateUtil.formatDate($timeInput.val(), momentFormat);
        $timeInput.val(value);
        $timeInput.unbind("click.time");
        //时间选择器
        $timeInput.bind("click.time", function () {
            WdatePicker({
                dateFmt: my97Format,
                el: $timeInput[0],
                maxDate: '%y-%M-%d'
            });
        });
    }).change();

    // 区域选择
    (function () {
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
                    $zoneInput.attr("data-zone-id", node.id);
                    $("#zoneId").val(node.id);
                },
                cancelValue: "取消",
                cancel: function () {
                    $help.empty();
                }
            });
        };

        // 区域输入框获取焦点时
        $("#zone-input").focus(function () {
            var $this = $(this);
            $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
                // TODO issues#1495 (galaxy) @gaohui 2013-06-013
                // 初始化树
                $.fn.zTree.init($('#zoneTree'), setting, result);
                var zoneTree = $.fn.zTree.getZTreeObj("zoneTree");

                // 初始化弹出框
                showDialog($this, zoneTree);
            });
        });
    })();

    //默认选择一个区域
    $(function () {
        $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
            if(result != null){
                var zoneId = result[0].id;
                var zoneName = result[0].name;
                $("#zoneId").val(zoneId);
                $("#zone-input").val(zoneName);
                var dateType = $("input[name='dateType']:checked").val();
                var date = $("#date").val();
                getData(zoneId, dateType, date, getTickInterval);
            }
        });
    });

    /**
     * 返回时间间隔(毫秒数)
     * @returns {number}
     */
    var getTickInterval = function () {
        var interval = 0;
        switch ($("input[name='dateType']:checked").val()) {
            case "1":
                interval = 30 * 24 * 3600 * 1000;
                break;
            case "2":
                interval = 24 * 3600 * 1000;
                break;
            case "3":
                interval = 3600 * 1000;
                break;
        }
        return interval;
    }

    $("#commit").click(function () {
        var zoneId = $("#zoneId").val();
        var dateType = $("input[name='dateType']:checked").val();
        var date = $("#date").val();
        if (!zoneId) {
            $("#noData").text("请选择区域");
            return;
        } else {
            getData(zoneId, dateType, date, getTickInterval);
        }
    });
});
</script>
</#macro>

