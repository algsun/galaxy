/**
 * 风向玫瑰图
 *
 * @author: xuyuexi
 * @time: 14-7-10
 * @dependence highcharts, highcharts.exporting, jquery, moment, artdialog
 */
$(function () {

    $(function () {
        $("input[name='dateType']").change(function () {
            var momentFormat;
            var my97Format;
            var $this = $(this);
            if (!$this.is(':checked')) {
                return;
            }
            $("#date_value").val($this.val());
            switch ($this.val()) {
                case "1":
                    momentFormat = DateUtil.formats.YEAR;
                    my97Format = 'yyyy';
                    $("#radioYearAndMonth").show();
                    $("#radioDays").hide();
                    break;
                case "2":
                    momentFormat = DateUtil.formats.MONTH;
                    my97Format = 'yyyy-MM';
                    $("#radioYearAndMonth").show();
                    $("#radioDays").hide();
                    break;
                case "3":
                    momentFormat = DateUtil.formats.DAY;
                    my97Format = 'yyyy-MM-dd';
                    $("#radioYearAndMonth").hide();
                    $("#radioDays").show();
                    break;
            }
            var $startInput = $("#startDate");
//            var value = DateUtil.formatDate($startInput.val(), momentFormat);
            value = $startInput.val();
            $startInput.val(value);
            $startInput.unbind("click.time");
            //时间选择器
            $startInput.bind("click.time", function () {
                WdatePicker({
                    dateFmt: my97Format,
                    el: $startInput[0],
                    maxDate: '%y-%M-%d'
                });
            });
            var $endInput = $("#endDate");
//            var value = DateUtil.formatDate($endInput.val(), momentFormat);
            value = $endInput.val();
            $endInput.val(value);
            $endInput.unbind("click.time");
            //时间选择器
            $endInput.bind("click.time", function () {
                WdatePicker({
                    dateFmt: my97Format,
                    el: $endInput[0],
                    maxDate: '%y-%M-%d'
                });
            });

            var $dateInput = $("#date");
            var value = DateUtil.formatDate($dateInput.val(), momentFormat);
            $dateInput.val(value);
            $dateInput.unbind("click.time");
            //时间选择器
            $dateInput.bind("click.time", function () {
                WdatePicker({
                    dateFmt: my97Format,
                    el: $dateInput[0],
                    maxDate: '%y-%M-%d'
                });
            });
        }).change();
    });


    getData();

    function getData() {
        var dateType = $("#date_value").val();
        var data = {dateType: dateType};
        if (dateType == 1 || dateType == 2) {
            data.time = $("#date").val();
        } else {
            data.startDate = $("#startDate").val();
            data.endDate = $("#endDate").val();
        }
        var url = "location/" + $("#locationId").val() + "/windrose-chart.json";
        $.post(url, data, function (result) {
            var windcalm = result.windcalm;
            var windroseText = result.text;
            if (data.time != null) {
                if (data.dateType == 1) {
                    windroseText += data.time + "年";
                } else {
                    windroseText += data.time + "月";
                }
            } else {
                windroseText += "\r\n" + data.startDate + "~" + data.endDate;
            }
            var hasData = result.hasData;
            var indicator = [];
            var windFrequency = [];
            var windSpeed = [];
            if (hasData) {
                var directionList = result.directionList
                for (var i = 0; i < directionList.length; i++) {
                    indicator.push({
                        text: directionList[i].directionId + "\n" +
                        message.frequency + directionList[i].windFrequency + '\n' +
                        message.windSpeed + directionList[i].windSpeed
                    });
                    windFrequency.push(directionList[i].windFrequency);
                    windSpeed.push(directionList[i].windSpeed);
                }
                var myChart = echarts.init($("#windrose-chart")[0]);
                var option = {
                    title: {
                        text: windroseText,
                        subtext: message.staticWindRate + '：' + windcalm + '%'
                    },
                    tooltip: {
                        trigger: 'axis',
                        formatter: function (params, ticket, callback) {
                            var res = params[0].indicator;
                            return res;
                        }
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'right',
                        y: 'bottom',
                        selectedMode: 'single',
                        selected: {
                            '平均风速(m/s)': false
                        },
                        data: [result.windFrequencyText + "(" + result.windFrequencyUnits + ")", result.windSpeedText + '(' + result.windSpeedUnits + ')']
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            saveAsImage: {show: true}
                        }
                    },
                    polar: [
                        {
                            indicator: indicator
                        }
                    ],
                    calculable: true,
                    series: [
                        {
                            name: '',
                            type: 'radar',
                            data: [
                                {
                                    value: windFrequency,
                                    name: result.windFrequencyText + "(" + result.windFrequencyUnits + ")"
                                },
                                {
                                    value: windSpeed,
                                    name: result.windSpeedText + '(' + result.windSpeedUnits + ')'
                                }
                            ]
                        }
                    ]
                };
                myChart.setOption(option);
            } else {
                $("#windrose-chart").html("<h4>" + message.noData + "</h4>");
            }
        });
    }


    //提交表单  查询风向玫瑰图数据时间验证
    $("#query-windrose-chart").click(function () {
        //验证开始时间和结束时间大小
        var $startDate = $("input[name='startDate']");
        var $endDate = $("input[name='endDate']");
        var $check = $startDate.popover({
            title: message.tips,
            content: message.startGreaterThanEnd,
            placement: 'bottom',
            trigger: 'manual'
        });

        if ($startDate.val() > $endDate.val()) {
            $check.popover('show');
            return false;
        }

        //点击开始时间取消时间验证提示
        $startDate.click(function () {
            $check.popover('hide');
        });

        //点击结束时间取消时间验证
        $endDate.click(function () {
            $check.popover('hide');
        });

        getData();
    });
    //选择新的时间类型后 时间自动调整
    $("input[name='timeType']").change(function () {
        var $this = $(this);
        if (!$this.is(':checked')) {
            return;
        }
        getDateType($(this).val());
    });

    //位置点切换

    var BluePlanet = App("blueplanet");
    BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
        var WIND_SENSOR_PHYSICAL_ID = 48;
        if ($.inArray(WIND_SENSOR_PHYSICAL_ID, node.sensorPhysicalIds) == -1) {
            art.dialog({
                title: message.tips,
                content: message.noWindRose
            });
            return false;
        }

        return 'location/' + node.locationId + "/windrose-chart";
    });


});



