<#--
蒸发量图
-->
<#assign title=locale.getStr("blueplanet.location.evaporativeTitle")>
<#include "/common/pages/common-tag.ftl">

<#macro head>

<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
</#macro>

<#assign tabIndex = 10>
<#macro content>

<form id="evaporationChartForm" action="location/${locationId}/evaporation-chart"
      class="well well-small form-inline m-b-10 ">
    <input id="locationId" type="hidden" value="${locationId}" data-value=${locationName}>
    <label class="m-l-10">${locale.getStr("common.timeType")}</label>
    <#if dateType==0>
        <#assign dateType=2>
    </#if>
    <label class="radio m-l-10">
        <input id="radioYear" name="dateType" type="radio" value="1" <@checked 1,dateType/> >${locale.getStr("common.year")}
    </label>
    <label class="radio m-l-10">
        <input id="radioMonth" name="dateType" type="radio" value="2" <@checked 2,dateType/>>${locale.getStr("common.month")}
    </label>
    <label class="radio m-l-10">
        <input id="radioDay" name="dateType" type="radio" value="3" <@checked 3,dateType/>>${locale.getStr("blueplanet.location.customTime")}
    </label>
    <input type="hidden" id="date_value">
    <#assign chartTime="">
    <#if dateType==1>
        <#assign chartTime=date?string("yyyy")!>
    <#elseif  dateType==2>
        <#assign chartTime=date?string("yyyy-MM")!>
    <#elseif dateType==3>
        <#assign  chartTime=date?string("yyyy-MM-dd")!>
    </#if>

    <div id="radioYearAndMonth" style="display:inline">
        <label class="m-l-10">${locale.getStr("common.time")}</label>
        <input id="date" name="date" class="input-medium Wdate" type="text" value="${chartTime}">
    </div>
    <div id="radioDays" class="hide" style="display:inline">
        <label class="m-l-10">${locale.getStr("common.startDate")}</label>
        <input id="startDate" name="startDate" class="input-medium Wdate" type="text"
               value="${startDate?string('yyyy-MM-dd')}">
        <label class="m-l-10">${locale.getStr("common.endDate")}</label>
        <input id="endDate" name="endDate" class="input-medium Wdate" type="text"
               value="${endDate?string('yyyy-MM-dd')}">
    </div>
    <button id="commit" type="button" class="btn">${locale.getStr("common.select")}</button>
</form>

<H4 id="noData"></H4>
<div class="row-fluid">
    <div class="span12">
        <div id="chart" style="height: 260px;"></div>
    </div>
</div>
<div class="row-fluid m-t-10">
    <#list chartVOs as chartVO>
        <div class="charts span2" style="height: 200px"></div>
        <div class="hide">
            {
            <#if chartVO.hasData>
                date:['${chartVO.dateString}'],
                value:[${chartVO.chartData.get(0).get('data')}]
            </#if>
            }
        </div>
    </#list>
</div>
<dl id="evap" class="hide">
    <@evapData/>
</dl>
</#macro>

<#--时间-->
<#macro evapData >
<dt>
    [ <#list chartVO.chartData as chartData>
    <#if chartData_index != 0> , </#if>
    <#assign chartTime="">
    <#if dateType==1>
        <#assign chartTime=chartData.time?string("yyyy-MM")!>
    <#else>
        <#assign chartTime=chartData.time?string("yyyy-MM-dd")!>
    </#if>
    "${chartTime}"
</#list> ]
</dt>

<#-- 数据 -->
<dd>
    [ <#list chartVO.chartData as chartData>
    <#if chartData_index != 0> , </#if> ${chartData.data}
</#list> ]
</dd>
</#macro>
<#macro script>

<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/exporting.js"></script>
<#--<script type="text/javascript" src="../assets/highcharts/2.3.5/modules/exporting.js"></script>-->
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
    <@scriptTag "js/device/charts/date-format.js"/>
<script type="text/javascript">
    $(function () {

        var flag = true;
        $("#commit").click(function () {
            var dateType = $("#date_value").val();
            if (dateType == 3) {
                var startTime = $("#startDate").val();
                var endTime = $("#endDate").val();
                if (startTime.length < 1) {
                    art.dialog({
                        id: "info",
                        title: message.tips,
                        content: message.selectStartTime
                    });
                    flag = false;
                }
                if (endTime.length < 1) {
                    art.dialog({
                        id: "info",
                        title: message.tips,
                        content: message.selectEndTime
                    });
                    flag = false;
                }
            } else {
                var date = $("#date").val();
                if (date.length < 1) {
                    art.dialog({
                        id: "info",
                        title: message.tips,
                        content: message.selectTime
                    });
                    flag = false;
                }
            }
        });

        if (flag) {

            var colorArray = ['#4876FF', '#EEA2AD'];//柱子的颜色
            var nodeName = $("#locationId").data("value");
            var maxValue = $("#locationId").data("maxvalue");
            Highcharts.setOptions({
                global: {useUTC: false}
            });
            Highcharts.setOptions({
                lang: {
                    contextButtonTitle: message.export,
                    downloadJPEG: '导出为JPEG',
                    downloadPDF: '导出为PDF',
                    downloadPNG: '导出为PNG',
                    downloadSVG: '导出为SVG',
                    resetZoom: message.reset,
                    resetZoomTitle: message.zoomBy,
                    rangeSelectorFrom: message.start,
                    rangeSelectorTo: message.end,
                    rangeSelectorZoom: ""//为什么是空 去掉它

                }
            });
            // 蒸发量数据
            var chartDatas = chartData();
            if (chartDatas == null) {
                $("#chart").text(message.noData);
            } else {
                $('#chart').highcharts(charOptions(chartDatas.date, chartDatas.data, 18, message.evaporationCapacity, message.unit + "（mm）", colorArray[0], true, chartDatas.maxValue));
            }
            // 从 dom 中解析数据，并返回
            function chartData() {
                var $data = $('#evap');
                var categories = $.parseJSON($data.find('dt').text());
                var values = $.parseJSON($data.find('dd').text());
                var maxValue = Math.max.apply(Math, values);
                var datas = {date: categories, data: values, maxValue: [maxValue]};
                return datas;
            }


            var colorIndex = 0;
            $(".charts").each(function () {
                var $this = $(this);
                // 获取图表数据
                var dataText = $this.next().html();
                var $jsonData = eval("(" + dataText + ")");
                colorIndex++;
                var index = colorIndex % 2;
                if ($jsonData.date != null) {
                    $(this).highcharts(charOptions($jsonData.date, $jsonData.value, 12, message.evaporationCapacity + "（mm）", "", colorArray[index], false, maxValue));
                } else {
                    $(this).remove()
                }
            });
            // 生成图表
            function charOptions(categories, data, fontSize, sensorNameUnits, unitsStr, colorData, isMain, maxValue) {
                return {
                    chart: {
                        type: 'column'
                    },

                    credits: {enabled: false},               //版权链接选项
                    title: {                                   //标题选项
                        text: '<span style="font-size: ' + fontSize + 'px;">' + sensorNameUnits + '</span>'
                    },
                    xAxis: {                                   //x轴选项
                        categories: categories,
                        labels: {
                            x: 10,//调节x偏移
                            y: 10,//调节y偏移
                            rotation: -30//调节倾斜角度偏移
                        }
                    },
                    yAxis: {                                   //y轴选项
                        min: 0,
                        max: maxValue,
                        title: {
                            text: unitsStr
                        }
                    },
                    tooltip: {
                        valueSuffix: 'mm'
                    },
                    legend: {
                        labelFormatter: function () {
                            return this.name + '(' + nodeName + ')';
                        },
                        backgroundColor: '#FFFFFF',
                        reversed: isMain,
                        enabled: isMain
                    },
                    plotOptions: {                          //数据点选项
                        series: {
                            dataLabels: {
                                enabled: true
                            }
                        }
                    },
                    series: [                               //数据列选项
                        {
                            color: colorData,
                            name: message.evaporationCapacity,
                            data: data
                        }
                    ],
                    exporting: {
                        enabled: isMain,
                        buttons: {
                            printButton: {
                                enabled: false
                            },
                            exportButton: {
                                menuItems: null,
                                onclick: function () {
                                    this.exportChart();
                                },
                                align: 'left',
                                x: 10
                            }
                        },
                        url: 'export-highchart-image.action',
                        filename: message.evaporationCapacity
                    }               //下载按钮
                }
            }
        }
    })
    ;

    $(document).ready(function () {
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
            var value = DateUtil.formatDate($startInput.val(), momentFormat);
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
            var value = DateUtil.formatDate($endInput.val(), momentFormat);
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
    })
</script>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
        return 'location/' + location.id + "/evaporation-chart";
    });
</script>
</#macro>