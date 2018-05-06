<#--
丢包率
@author liuzhu
@date 2013.09.29
@check @xiedeng 2013-10-11 #5892
-->

<#assign title="环境监控-丢包率">
<#assign title>${locale.getStr("blueplanet.lossPackage.title")}</#assign>
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:monitor:topo">
<#include "/common/pages/common-tag.ftl">
<#macro head>

</#macro>

<#macro content>
</div>
<ul class="nav nav-tabs">
    <li><a href="topo.action">${locale.getStr("blueplanet.topo")}</a></li>
    <li><a href="timeoutDevice.action">${locale.getStr("blueplanet.topo.timeoutDevice")}</a></li>
    <li><a href="deviceAnalyse.action">${locale.getStr("blueplanet.topo.deviceAnalyse")}</a></li>
    <li class="active"><a href="lossPackage.action">${locale.getStr("blueplanet.topo.lossPackage")}</a></li>
    <li><a href="deviceLink.action">${locale.getStr("blueplanet.topo.deviceLink")}</a></li>
</ul>
<div class="row-fluid">
    <div class="span12">

        <form id="marksementFrom" class="well well-small form-inline" action="blueplanet/lossPackage.action"
              method="post">
            <label class="m-l-20">${locale.getStr("blueplanet.topo.front")}</label>
            <select class="input-small" name="dataSize">
                <option value="10" <@selected 10 dataSize/>>10</option>
                <option value="20" <@selected 20 dataSize/>>20</option>
                <option value="40" <@selected 40 dataSize/>>40</option>
            </select>
            <label class="m-l-20" for="startDate">${locale.getStr("common.startDate")}</label>
            <input id="startDate" type="text"
                   name="startDate"
                   class="input-medium Wdate"
                   value="${startDate?string('yyyy-MM-dd HH:mm')}"/>
            <label class="m-l-20" for="endDate">${locale.getStr("common.endDate")}</label>
            <input id="endDate" type="text"
                   name="endDate"
                   class="input-medium Wdate"
                   value="${endDate?string('yyyy-MM-dd HH:mm')}"/>
            <input type="submit" class="btn" id="submit-btn" value="${locale.getStr("common.select")}">
        </form>

    </div>
</div>
<div><p style="color: #969696; text-align: left;">
${locale.getStr("blueplanet.topo.lossPackageTips")}
<div id="container" style="width: 98%"></div>
<div class="hide">
    <dl id="lossPackageQuery">
        <@lossPackageData/>
    </dl>
</div>
<div id="lossPackageJson" class="hide">
${lossPageageListJson}
</div>
</#macro>
<#---->
<#macro lossPackageData >
<#-- 设备名称 -->
<dt>
    [ <#list lossPackageList as lossPageage>
    <#if lossPageage_index != 0> , </#if>
    "${lossPageage.nodeId?substring(8,13)}<#if lossPageage.nodeName?? && lossPageage.nodeName!="">
        (${lossPageage.nodeName})</#if>"
</#list> ]
</dt>

<#-- 丢包率  -->
<dd>
    [ <#list lossPackageList as lossPageage>
    <#if lossPageage_index != 0> , </#if> ${lossPageage.loseRate}
</#list> ]
</dd>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
    <@scriptTag "js/2datepicker-form-validation.js"/>
    <@scriptTag "js/date-format.js"/>
<script type="text/javascript">

    $(document).ready(function () {
        var $timeInput = $("#startDate");
        var $endDate = $("#endDate");
        $timeInput.unbind("click.time");
        function planningDate(month) {
            var uom = new Date();
            uom.setMonth(uom.getMonth() + month)
            uom = uom.getFullYear() + "-" + (uom.getMonth() + 1) + "-" + uom.getDate();
            return uom;
        };
        //前3个月的数据
        var threeMonthAgo = planningDate(-3);
        var nowTime = new Date();
        //时间选择器
        $timeInput.bind("click.time", function () {
            WdatePicker({
                dateFmt: 'yyyy-MM-dd HH:mm',
                el: $timeInput[0],
                minDate: threeMonthAgo,
                maxDate: '%y-%M-%d'
            });
        });
        $endDate.bind("click.time", function () {
            WdatePicker({
                dateFmt: 'yyyy-MM-dd HH:mm',
                minDate: threeMonthAgo,
                maxDate: '%y-%M-%d'
            });
        });
    });

    $(function () {
        // 丢包率数据
        var lossPackageData = lossPackageData();
        if (lossPackageData[0].length === 0) {
            $("#container").html('<h4>' + message.noData + '</h4>');
        } else {
            $('#container').highcharts(charOptions(lossPackageData[0], lossPackageData[1]));
        }

        // 从 dom 中解析数据，并返回
        function lossPackageData() {
            var $lossPackageData = $('#lossPackageQuery');
            var lossPackageCategories = $.parseJSON($lossPackageData.find('dt').text());
            var lossPackageData = $.parseJSON($lossPackageData.find('dd').text());
            return [lossPackageCategories, lossPackageData];
        }

        // 生成图表
        function charOptions(categories, data) {
            return {
                exporting: {enabled: false},               //下载按钮
                credits: {enabled: false},               //版权链接选项
                chart: {                                   //图标区选项
                    type: 'bar',
                    height: 750
                },
                title: {                                   //标题选项
                    text: message.lostRate
                },
                xAxis: {                                   //x轴选项
                    categories: categories
                },
                yAxis: {                                   //y轴选项
                    title: {
                        text: message.unit + "：%"
                    },
                    labels: {
                        format: '{value}%'
                    }
                },
                tooltip: {
                    formatter: function () {
                        var nodeInfo = "";
                        nodeInfo += this.x + "<br/>";
                        var lossPackageJSON = $.parseJSON($(lossPackageJson).text());
                        for (var lossPackage in lossPackageJSON) {
                            var targetNodeId = lossPackageJSON[lossPackage].nodeId.substr(8);
                            if (this.x.substr(0, 5) == targetNodeId) {
                                nodeInfo += message.estimated + "：" + lossPackageJSON[lossPackage].expectedCount
                                        + "   " + message.actual + "：" + lossPackageJSON[lossPackage].actualCount + "<br/>"
                            }
                        }
                        nodeInfo += message.packetLostRate + "：" + this.y + "%";
                        return nodeInfo;
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
                            enabled: true,
                            format: '{y}%'
                        }
                    }
                },
                series: [                               //数据列选项
                    {
                        name: message.packetLostRate,
                        data: data
                    }
                ]
            }
        }
    });
</script>
</#macro>