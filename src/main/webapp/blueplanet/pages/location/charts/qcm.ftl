<#--
qcm
-->
<#assign title=locale.getStr("blueplanet.location.qcmTitle")>


<#macro head>
    <#include "/common/pages/common-tag.ftl"/>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
</#macro>


<#assign tabIndex = 12>
<#macro content>
<div class="row-fluid">
    <input type="hidden" id="locationId" value="${locationId!}"/>

    <form class="form-inline well well-small" id="qmc_form">
        <label for="startDate">${locale.getStr("common.monitoringIndicators")}</label>
        <select id="sensorId" style="width:250px;">
            <option value="3075">${locale.getStr("blueplanet.location.organicPollutants")}</option>
            <option value="3076">${locale.getStr("blueplanet.location.inorganicPollutants")}</option>
            <option value="3077">${locale.getStr("blueplanet.location.sulfurPollutant")}</option>
        </select>
        <label for="startDate">${locale.getStr("common.startDate")}</label>
        <input id="startDate" type="text" class="form-control"
               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
               name="startDate"
               value="${startDate?string('yyyy-MM-dd')}"/>
        <label for="endDate">${locale.getStr("common.endDate")}</label>
        <input id="endDate" type="text" class="form-control"
               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
               name="endDate"
               value="${endDate?string('yyyy-MM-dd')}"/>
        <button type="button" class="btn btn-default" id="submit-btn">${locale.getStr("common.select")}</button>
    </form>
</div>
<div class="row-fluid">
    <div id="container"></div>
</div>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/lang/zh-cn.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/exporting.js"></script>
<script type="text/javascript">
    $(function () {
        //验证开始时间和结束时间大小
        var $startDate = $("input[name='startDate']");
        var $endDate = $("input[name='endDate']");
        var $check = $startDate.popover({
            title: message.tips,
            content: message.startGreaterThanEnd,
            placement: 'bottom',
            trigger: 'manual'
        });

        $("#submit-btn").click(function () {
            if ($startDate.val() > $endDate.val()) {
                $check.popover('show');
                return false;
            }
        });

        //点击开始时间取消时间验证提示
        $startDate.click(function () {
            $check.popover('hide');
        });

        //点击结束时间取消时间验证
        $endDate.click(function () {
            $check.popover('hide');
        });
    });

    $(function () {
        qmcChart();
    });

    $(function () {
        $("#submit-btn").click(function () {
            qmcChart();
        });
    });

    var qmcChart = function () {
        var locationId = $("#locationId").val();
        var sensorId = $("#sensorId").val();
        var url = "location/" + locationId + "/qcm_data";
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        var sensorId = $("#sensorId").val();
        var qmcName = $("#sensorId").find("option:selected").text().trim();

        var data = {startDate: startDate, endDate: endDate, sensorId: sensorId};
        $.get(url, data, function (result) {
            var qcmData = [];
            for (var qcm in result) {
                var qcmObj = result[qcm];
                qcmData.push([new Date(qcmObj.datetime).getTime(), qcmObj.value]);
            }
            Highcharts.setOptions({
                global: {useUTC: true}
            });
            Highcharts.setOptions({
                lang: {
                    contextButtonTitle: message.export,
                    printChart: '打印',
                    downloadJPEG: '导出为JPEG',
                    downloadPDF: '导出为PDF',
                    downloadPNG: '导出为PNG',
                    downloadSVG: '导出为SVG',
                    resetZoom: message.reset,
                    resetZoomTitle: message.zoomBy,
                    rangeSelectorFrom: "开始",
                    rangeSelectorTo: "结束",
                    rangeSelectorZoom: ""//为什么是空 去掉它

                }
            });
            $('#container').highcharts({
                credits: {
                    enabled: false
                },

                title: {
                    text: 'QCM'
                },

                xAxis: {
                    type: 'datetime',
                    labels: {
                        x: 25,//调节x偏移
                        y: 25,//调节y偏移
                        rotation: -30//调节倾斜角度偏移
                    },
                    dateTimeLabelFormats: {
                        week: '%Y-%m-%d',
                        day: '%Y-%m-%d',
                        month: '%Y-%m',
                        year: '%Y'
                    }
                },

                yAxis: {
                    title: {
                        text: null
                    }
                },

                tooltip: {
                    crosshairs: true,
                    shared: true,
                    xDateFormat: '%Y-%m-%d %H:%M',
                    valueSuffix: 'Hz'
                },

                legend: {},

                series: [
                    {
                        name: qmcName + "(Hz)",
                        data: qcmData,
                        zIndex: 1
                    }
                ],
                exporting: {
                    enabled: true,
                    buttons: {
                        printButton: {
                            enabled: false
                        },
                        exportButton: {
                            menuItems: null,
                            onclick: function () {
                                this.exportChart();
                            }
                        }
                    },
                    url: 'export-highchart-image.action',
                    filename: "QCM"
                }               //下载按钮

            });
        });
    }


</script>
</#macro>