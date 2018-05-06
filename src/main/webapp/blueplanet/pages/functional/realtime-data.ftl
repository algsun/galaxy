<!DOCTYPE html>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>实时数据 - 环境监控</title>

<#include "../_common/common-css.ftl">
<#include "../_common/ztree-css.ftl">
    <link rel="stylesheet" href="css/_demo/trend.css">

</head>
<body>

<#include "../_common/header.ftl">

<#-- 区域设备树 -->
<#--<div.left-aside-container">-->
<#include "../_common/zone-device-tree.ftl">
<#--</div>-->

<#--伸缩条-->
<div class="shrink-bar" title="点击收缩">
    <div class="shrink-bar-icon"></div>
</div>

<div class="content-container">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <ul class="breadcrumb">
                    <li class="muted">当前位置</li>
                    <span id="area-device-path"></span>
                    <a class="btn m-l-20">确定</a>
                </ul>

                <div class="tabbable">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab0" data-toggle="tab">实时数据</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab0">
                            <table class="move table table-bordered ">
                                <thead>
                                <tr>
                                    <th style="vertical-align: middle;">区域</th>
                                    <th style="vertical-align: middle;">设备</th>
                                    <th style="vertical-align: middle;">电压</th>
                                    <th style="vertical-align: middle;">时间</th>
                                    <th class="moveable">温度<br>(℃)</th>
                                    <th class="moveable">露点<br>(℃)</th>
                                    <th class="moveable">湿度<br>(%)</th>
                                    <th class="moveable">二氧化碳<br>(ppm)</th>
                                    <th class="moveable">照度<br>(lx)</th>
                                    <th class="moveable">表面温度<br>(℃)</th>
                                    <th class="moveable">紫外线强度<br>(uw/cm²)</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr class="table-data-template" style="display: none;">
                                    <td data-type="zone"></td>
                                    <td data-type="eq"></td>
                                    <td data-type="press"></td>
                                    <td data-type="time" title="2012-11-13 13:55"></td>
                                    <td data-type="wendu"></td>
                                    <td data-type="ludian"></td>
                                    <td data-type="shidu"></td>
                                    <td data-type="eryanghuatan"></td>
                                    <td data-type="zhaodu"></td>
                                    <td data-type="biaomianwendu"></td>
                                    <td data-type="ziwaixian"></td>
                                </tr>
                                <tr>
                                    <td data-type="zone">左侧</td>
                                    <td data-type="eq">设备1</td>
                                    <td data-type="dianya">正常</td>
                                    <td data-type="time" title="2012-11-13 13:55">3分钟前</td>
                                    <td data-type="wendu">17.56 <a class="trend-up" rel="popover"></a></td>
                                    <td data-type="ludian">4.48 <a class="trend-retain"></a></td>
                                    <td data-type="shidu">41.23 <a class="trend-down"></a></td>
                                    <td data-type="eryanghuatan">589 <a class="trend-up"></a></td>
                                    <td data-type="zhaodu">345.67 <a class="trend-retain"></a></td>
                                    <td data-type="biaomianwendu">10.66 <a class="trend-up"></a></td>
                                    <td data-type="ziwaixian">2.45 <a class="trend-retain"></a></td>
                                </tr>
                                <tr>
                                    <td>左侧</td>
                                    <td>设备2</td>
                                    <td>正常</td>
                                    <td title="2012-11-13 13:55">1分钟前</td>
                                    <td>17.56 <a class="trend-up"></a></td>
                                    <td>4.48 <a class="trend-retain"></a></td>
                                    <td>41.23 <a class="trend-down"></a></td>
                                    <td>589 <a class="trend-up"></a></td>
                                    <td>345.67 <a class="trend-retain"></a></td>
                                    <td>10.66 <a class="trend-up"></a></td>
                                    <td>2.45 <a class="trend-retain"></a></td>
                                </tr>
                                <tr>
                                    <td>左侧</td>
                                    <td>设备3</td>
                                    <td>正常</td>
                                    <td title="2012-11-13 13:55">9分钟前</td>
                                    <td>17.56 <a class="trend-up"></a></td>
                                    <td>4.48 <a class="trend-retain"></a></td>
                                    <td>41.23 <a class="trend-down"></a></td>
                                    <td>589 <a class="trend-up"></a></td>
                                    <td>345.67 <a class="trend-retain"></a></td>
                                    <td>10.66 <a class="trend-up"></a></td>
                                    <td>2.45 <a class="trend-retain"></a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/ztree/3.4/js/jquery.ztree.core-3.4.min.js"></script>
<script type="text/javascript" src="../assets/ztree/3.4/js/jquery.ztree.excheck-3.4.min.js"></script>
<script type="text/javascript" src="../assets/underscore/1.4.2/underscore-min.js"></script>

<!--[if lte IE 8]>
<script language="javascript" type="text/javascript" src="../assets/flot/d7c58b59f3/excanvas.min.js"></script>
<![endif]-->
<script type="text/javascript" src="../assets/flot/d7c58b59f3/jquery.flot.js"></script>

<#include "../_common/zone-device-path-template.ftl">
<script type="text/javascript">
    $(function () {
        var BluePlanet = App("blueplanet");
        BluePlanet.createNode(BluePlanet.allNodes);
    });
</script>

<script type="text/javascript" src="js/device-tree.js"></script>

<script type="text/javascript">
    (function ($) {
        var createChart = function () {
            var $chart = $("<div style='width: 200px; height: 80px; position: relative;'></div>");

            $.plot($chart, [
                randomData(6)
            ], {
                grid: {
                    borderSize: 1,
                    borderColor: '#ddd'
                },
                series: {
                    shadowSize: 0,
                    lines: {show: true, fill: true},
                    color: "#08c"
                }
            });

            return $chart;
        };

        window.refreshTrendChart = function () {
            $(".trend-up,.trend-retain, .trend-down").popover({
                        html: true,
                        title: '趋势',
                        trigger: 'hover',
                        placement: 'top',
                        content: function () {
                            return createChart()[0];
                        }
                    }
            );
        };

        window.refreshTrendChart();

        function randomData(size) {
            var data = [];
            var i = 0;
            for (i = 0; i < size; i++) {
                data.push([i, Math.random() * (80 - 5) + 5]);
            }
            return data;
        }
    })(jQuery);
</script>


<#include "../_common/last-resources.ftl">
</body>
</html>