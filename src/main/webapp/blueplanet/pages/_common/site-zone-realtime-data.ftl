<#--
站点区域实时数据
@author Wang yunlong
@time  13-1-24  下午4:49
-->
<#assign title = "实时数据 - 环境监控">


<#macro head>
<style type="text/css">
    table.table > thead th.highlight {
        background-color: #fff7e2;
    }
</style>
</#macro>



<#assign tabIndex = 1>
<#macro content>
<div class="form-inline m-b-10">
    <a class="il-blk m-b-5" href="#"
       onclick="$(this).siblings().toggle(); return false;">隐藏</a>
    <label class="muted">筛选设备</label>
    <ul id="zone-sensorinfo-filter" class="il-blk p-v-0 m-v-0" style="list-style-type: none;">
    </ul>
    <button id="filter-btn" class="btn btn-small">筛选</button>
    <button id="cancel-filter-btn" class="btn btn-small" style="display: none;">取消筛选</button>
</div>

<table id="realtimedata" class="move table table-bordered ">
    <thead>
    <tr>
    </tr>
    </thead>
    <tbody>
    <tr class="table-data-template hide">
    </tr>
    </tbody>
</table>


<div class="hide">
    <div class="charts">
        <div id="little-chart" style="width:200px; height: 80px;"></div>
    </div>
</div>
</#macro>



<#macro script>

<!--[if lte IE 8]>
<script language="javascript" type="text/javascript" src="../assets/flot/d7c58b59f3/excanvas.min.js"></script>
<![endif]-->
<script type="text/javascript" src="../assets/flot/d7c58b59f3/jquery.flot.js"></script>
<script type="text/javascript" src="../assets/jquery-ui/1.9.2/js/jquery-ui.custom.min.js"></script>

<script type="text/javascript">
    (function ($) {
        $(function () {
            // 高亮监测指标, 如果监测指标 checkbox 选中, 表格表头对应的监测指标高亮
            $("input[data-type]").change(function () {
                var type = $(this).attr("data-type");
                $("th[data-type='" + type + "']").toggleClass("highlight");
            });
        });
    })(jQuery);
</script>

<script type="text/javascript" src="js/_demo/table-column-movement.0.4.js"></script>
<script type="text/javascript">
    $(function () {
        //监测指标获取 初始化实时数据表头
        (function () {
            // TODO 变量不达义 @gaohui 2013-01-29
            var $tableSensorinfos = $("#realtimedata");
            var initThead = function () {
                // TODO BUG 站点与区域实时数据链接不一样 @gaohui 2013-01-29
                $.getJSON("site/sensorinfoes.json", function (result) {
                    //实时数据呈现表格列头
                    var th = "<th data-dead='1'style='vertical-align: middle;'>区域</th>" +
                            "<th data-dead='1'style='vertical-align: middle;'>设备</th>" +
                            "<th data-dead='1'style='vertical-align: middle;'>电压</th>" +
                            "<th data-dead='1'style='vertical-align: middle;'>时间</th>";
                    //实时数据呈现模板
                    var td = "<td data-dead='1' data-type='zoneName'></td>" +
                            "<td  data-dead='1' data-type='nodeName'></td>" +
                            "<td  data-dead='1' data-type='lowvoltage'></td>" +
                            "<td  data-dead='1' data-type='stamp'></td>";
                    //监测指标过滤
                    var filter = "";
                    $.each(result, function (index, sensorinfo) {
                        th += "<th class='moveable' data-type='" + sensorinfo.sensorPhysicalid + "'>" + sensorinfo.cnName + "<br><span class='muted'>(" + sensorinfo.units + ")</span></th>";
                        td += "<td data-type='" + sensorinfo.sensorPhysicalid + "'></td>";
                        filter += "<li class='il-blk m-r-10'>" +
                                "<label class='checkbox'><input checked='checked' type='checkbox' data-type='" + sensorinfo.sensorPhysicalid + "'>" + sensorinfo.cnName + "</label>" +
                                "</li>";
                    });
                    $tableSensorinfos.children("thead").children("tr").append(th);
                    $tableSensorinfos.find(".table-data-template").append(td);
                    $("#zone-sensorinfo-filter").append(filter);
                    refresh();
                });
            };
            initThead();
            //该模块全局变量 是否有按监测指标筛选设备
            var isFilter = false;
            $("#filter-btn").click(function () {
                isFilter = true;
                $("#cancel-filter-btn").show();
                refresh();
            });
            $("#cancel-filter-btn").click(function () {
                isFilter = false;
                $(this).hide();
                $tableSensorinfos.find("thead > tr").empty();
                $tableSensorinfos.find("tbody > .table-data-template").empty();
                $("#zone-sensorinfo-filter").empty();
                initThead();
            });
            var $tableDataTemplate = $(".table-data-template");
            //记录上一包数据
            var preData = [];

            // TODO 使用常量数字 @gaohui 2013-01-29
            setInterval(function () {
                refresh();
                new TableColumn(".move", "moveable").init();
            }, 7000);
            //实时数据刷新
            var refresh = function () {
                if (isFilter) {
                    var sensorinfoes = [];
                    // 选中的监测指标
                    var $sensorinfoes = $("#zone-sensorinfo-filter").find("input:checked");
                    $sensorinfoes.each(function () {
                        sensorinfoes.push($(this).attr("data-type"));
                    });
                    //删除被过滤掉的表头
                    var $theadTh = $tableSensorinfos.children("thead").children("tr").children("th");
                    $theadTh.each(function (index, th) {
                        if ($(th).attr("data-dead") != 1) {
                            if ($.inArray($(th).attr("data-type"), sensorinfoes) == -1) {
                                $(th).remove();
                            }
                        }
                    });
                    //删除被过滤掉的模板列
                    $tableDataTemplate.children().each(function (index, td) {
                        if ($(td).attr("data-dead") != 1) {
                            if ($.inArray($(td).attr("data-type"), sensorinfoes) == -1) {
                                $(td).remove();
                            }
                        }
                    });
                    filterRealTimeDataRefresh(sensorinfoes);
                } else {
                    noFilterRealTimeDataRefresh();
                }
            };
            //不带监测指标过滤实时数据
            var noFilterRealTimeDataRefresh = function () {
                $.getJSON("site/realtime-data.json", function (data) {
                    renderRealTimeDataView(data);
                });
            };
            //带监测指标过滤实时数据
            var filterRealTimeDataRefresh = function (sensorinfoes) {
                $.ajaxSetup({traditional: true});
                $.getJSON("site/realtime-data.json", {sensorPhysicalIds: sensorinfoes}, function (data) {
                    renderRealTimeDataView(data);
                });
            };
            // 渲染实时数据表格
            var renderRealTimeDataView = function (data) {
                // 清空数据
                $("table > tbody > tr").not(".table-data-template").remove();
                if (data.length < 1) {
                    return;
                }
                $.each(data, function (index, d) {
                    // TODO 重构： 参数设备实时数据 @gaohui 2013-01-29
                    var $appendTr = $tableDataTemplate.clone()
                            .removeClass("table-data-template")
                            .removeClass("hide");
                    $appendTr.children().each(function (index, td) {
                        if ($(td).attr("data-dead") == "1") {
                            $(td).text(d[$(td).attr("data-type")]);
                        } else {
                            if (d.sensorinfoMap[$(td).attr("data-type")]) {
                                if (d.sensorinfoMap[$(td).attr("data-type")].state == 0) {
                                    $(td).css("color", "red");
                                }
                                $(td).text(d.sensorinfoMap[$(td).attr("data-type")].sensorPhysicalValue);
                            }
                        }
                    });
                    $("table>tbody").append($appendTr);
                    var refreshData = [];
                    for (var i = 0; i < preData.length; i++) {
                        if (d.nodeId == preData[i].nodeId) {
                            if (d.stamp != preData[i].stamp) {
                                refreshData.push(i);
                                //颜色渐变, 由透明变成 lightgrenn ，然后再变成透明
                                $appendTr.css("background-color", "transparent");
                                // lightgreen => #90ee90
                                $appendTr.animate({backgroundColor: "#90ee90"}, 1000)
                                        .animate({backgroundColor: "transparent"}, 3000);
                            }
                        }
                    }
                });
                preData = data;
            }
        })();
    });
</script>
</#macro>
