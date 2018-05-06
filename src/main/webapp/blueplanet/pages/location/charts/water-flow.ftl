<#--
水流量监测
-->
<#assign title=locale.getStr("blueplanet.location.waterFlowTitle")>
<#macro head>
    <#include "/common/pages/common-tag.ftl"/>
</#macro>

<#assign tabIndex = 14>
<#macro content>
<div>
    <form class="well form-inline well-small">
        <#include "/common/pages/date-year-month-day-control.ftl">
        <input type="hidden" value="${sensorId}" id="sensorId"/>
        <input type="hidden" value="${locationId}" id="locationId"/>
        <span id="submit" class="btn btn-mini">${locale.getStr("common.select")}</span>
        <input class="radioItem" type="radio" name="sensorId" value="97" checked/>${locale.getStr("blueplanet.location.waterFlow")}
        <input class="radioItem" type="radio" name="sensorId" value="90"/>${locale.getStr("blueplanet.location.waterSpeed")}
        <input class="radioItem" type="radio" name="sensorId" value="96"/>${locale.getStr("blueplanet.location.waterLevel")}
    </form>
</div>
<h4 id="noData"></h4>
<div class="row-fluid">
    <div id="container" class="span12" style="height: 400px;">
    </div>
</div>
<div class="row-fluid">
    <div id="backDiv" style="display:none">
        <div id="backupi" class="alert alert-info">

        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/echarts/esl.js"></script>
    <@scriptTag "js/date-format.js"/>
    <@scriptTag "js/location/charts/water-flow.js"/>
    <@scriptTag "js/date-year-month-day-control.js"/>
<script type="text/javascript">
    var myChart;
    var ecConfig;
    $(function () {
        var sensorId = $("#sensorId").val();
        var dateType = $('input[name="dateType"]:checked').val();
        var locationId = $("#locationId").val();
        var dat = $("#date").val();
        getChartData(dateType, dat, sensorId, locationId);
        $(".radioItem").change(function () {
            var $this = $(this);
            var dateType = $('input[name="dateType"]:checked').val();
            var date = $("#date").val();
            getChartData(dateType, date, $this.val(), locationId);
        });
        $("#submit").click(function () {
            var dateType = $('input[name="dateType"]:checked').val();
            var sensorId = $('input[name="sensorId"]:checked').val();
            var date = $("#date").val();
            getChartData(dateType, date, sensorId, locationId);
        });
    });

    function getChartData(dateType, date, sensorId, locationId) {
        $.getJSON("location/sensorId/water-flow-data.json", {
            dateType: dateType,
            date: date,
            sensorId: sensorId,
            locationId: locationId
        }, function (chartValues) {
            var chartValueStr = [];
            chartValueStr["legend"] = packageLegend(chartValues);
            chartValueStr["stamp"] = packageStamp(chartValues);
            chartValueStr["data"] = packageData(chartValues);
            chartValueStr["title"] = chartValues[0].title;
            $(".backup").remove();

            if (chartValueStr["data"].length == 0) {
                $("#noData").text(message.noData);
                $("#container").children().remove();
                $("#backDiv").hide();
            } else {
                $("#noData").text("");
                $("#backDiv").show();
                createChart(chartValueStr, locationId, chartValues[0].dateType);
            }
        });
    }
    function createChart(chartValues, locationId, dateType) {
        require.config({
            paths: {
                echarts: '../assets/echarts/2.1.8/echarts',
                'echarts/chart/bar': '../assets/echarts/2.1.8/chart/bar',
                'echarts/chart/line': '../assets/echarts/2.1.8/chart/line'
            }
        });
        require(
                [
                    'echarts',
                    'echarts/chart/line',
                    'echarts/chart/bar'
                ],
                function (ec) {
                    myChart = ec.init($("#container")[0]);
                    ecConfig = require('echarts/config');
                    myChart.setOption(option(chartValues), true);
                    $.getJSON("location/waterflow-location-name.json", {locationId: locationId}, function (result) {
                        var selected = myChart.component.legend.getSelectedMap();
                        for (var key in selected) {
                            if (key == result) {
                                myChart.component.legend.setSelected(key, true);
                            } else {
                                myChart.component.legend.setSelected(key, false);
                            }
                        }
                    });

                    myChart.on(ecConfig.EVENT.LEGEND_SELECTED, function (param) {
                        var selected = param.selected;

                        var names = "";
                        for (var key in selected) {
                            if (selected[key]) {
                                names = names + key + ","
                            }
                        }
                        var dateType = $('input[name="dateType"]:checked').val();
                        var date = $("#date").val();
                        var sensorId = $('input[name="sensorId"]:checked').val();
                        $.getJSON("location/waterflow-backup.json", {
                            names: names,
                            date: date,
                            sensorId: sensorId,
                            dateType: dateType
                        }, function (data) {
                            $(".backup").remove();
                            if (data != null && data != "") {
                                $("#backupi").append("<span class='backup'>" + data + "</span>");
                            }
                        });
                    });
                }
        );
    }
    function packageLegend(chartValues) {
        var legend = [];
        $.each(chartValues, function (index, chartValue) {
            legend.push(chartValue.name);
        });
        return legend;
    }
    function packageStamp(chartValues) {
        var dateType = chartValues[0].dateType;
        var days = chartValues[0].days;
        var stamp = [];
        if (dateType == 1) {
            stamp = [message.january, message.february, message.march, message.april, message.may, message.june,
                message.july, message.august, message.september, message.october, message.november, message.december]
        } else if (dateType == 2) {
            for (var i = 1; i <= days; i++) {
                stamp.push(i + message.daytime);
            }
        } else if (dateType == 3) {
            stamp = ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00'];
        }
        return stamp;
    }
    function packageData(chartValues) {
        var series = [];
        $.each(chartValues, function (index, chartValue) {
            if (typeof(chartValue.data) != "undefined") {
                var data = {};
                data["name"] = chartValue.name;
                data["type"] = "bar";
                data["data"] = chartValue.data;

                series.push(data);
            }
        });
        return series;
    }
</script>
</#macro>
<#macro scriptTag src>
<script type="text/javascript" src="${src}?_=${Application['app.startTime']?long?c}"></script>
</#macro>