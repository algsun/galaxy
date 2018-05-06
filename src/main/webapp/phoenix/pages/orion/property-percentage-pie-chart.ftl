<#--
文物各个属性的占用比

@author li.jianfei
@date 2013-07-04

@check @duan.qixin 2013-7-18 #4588
-->

<#assign title="文物各属性饼状图 - 数据分析">
<#assign currentPrivilege = "phoenix:orion:relicPropertyPieChart">
<#include  "../_common/helper.ftl">

<#macro head>
<style type="text/css">
    .contmn {
        width: 49%;
        display: inline-block;
        height: 470px;
    }
</style>
</#macro>

<#macro  content>
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>文物各属性饼状图</h3>
        </div>
    </div>
</div>

<div id="chartData" class="hide">${relicBasicInfo!}</div>

<!--TODO 修改四个图像的布局样式，中间太宽-->
<div id="chartDiv" class="hide">
    <div>
        <div id="container" class="contmn">
        </div>
        <div id="container2" class="contmn">
        </div>
    </div>
    <div>
        <div id="container3" class="contmn">
        </div>
        <div id="container4" class="contmn">
        </div>
    </div>
</div>

<h4 id="noData">暂无数据</h4>

<div id="statistics" class="row-fluid hide">
    <@alertNested>
        ${date?string("yyyy")}年文物总数 <span class="bold">${relicCount?c}</span> 件。<br/>
        时代中“<span id="era" class=""></span>”占比最高（<span id="eraPercent" class="bold"></span>%），<br/>
        级别中“<span id="level" class=""></span>”占比最高（<span id="levelPercent" class="bold"></span>%），<br/>
        质地中“<span id="texture" class=""></span>”占比最高（<span id="texturePercent" class="bold"></span>%），<br/>
        区域中“<span id="zone" class=""></span>”占比最高（<span id="zonePercent" class="bold"></span>%）。
    </@alertNested>
</div>

</#macro>

<#macro  script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript">
    $(function () {

        var relicBasicStats = JSON.parse($("#chartData").text());

        var eraData = relicBasicStats.eraStat.pieData;
        var levelData = relicBasicStats.levelStat.pieData;
        var textureData = relicBasicStats.textureStat.pieData;
        var zoneData = relicBasicStats.zoneStat.pieData;

        var relicFlag = false;
        if (relicBasicStats.eraStat.hasData) {
            $("#era").text(eraData[0][0]);
            $("#eraPercent").text(eraData[0][1]);
            relicFlag = true;
        }
        if (relicBasicStats.levelStat.hasData) {
            $("#level").text(levelData[0][0]);
            $("#levelPercent").text(levelData[0][1]);
            relicFlag = true;
        }
        if (relicBasicStats.textureStat.hasData) {
            $("#texture").text(textureData[0][0]);
            $("#texturePercent").text(textureData[0][1]);
            relicFlag = true;
        }
        if (relicBasicStats.zoneStat.hasData) {
            $("#zone").text(zoneData[0][0]);
            $("#zonePercent").text(zoneData[0][1]);
            relicFlag = true;
        }
        if(relicFlag){
            $("#noData").hide();
            $("#chartDiv").show();
            $("#statistics").show();
        }


        //时代其它详细信息
        if (relicBasicStats.eraStat.hasOtherData) {
            eraData = fmtPoint(eraData, relicBasicStats.eraStat.otherData);
        }
        $('#container').highcharts(chartOptions({
            title: '时代',
            serieName: '时代',
            serieData: eraData
        }));

        //级别其它详细信息
        if (relicBasicStats.levelStat.hasOtherData) {
            levelData = fmtPoint(levelData, relicBasicStats.levelStat.otherData);
        }
        $('#container2').highcharts(chartOptions({
            title: '级别',
            serieName: '级别',
            serieData: levelData
        }));

        //质地其它详细信息
        if (relicBasicStats.textureStat.hasOtherData) {
            textureData = fmtPoint(textureData, relicBasicStats.textureStat.otherData);
        }
        $('#container3').highcharts(chartOptions({
            title: '质地',
            serieName: '质地',
            serieData: textureData
        }));

        //区域其它详细信息
        if (relicBasicStats.zoneStat.hasOtherData) {
            zoneData = fmtPoint(zoneData, relicBasicStats.zoneStat.otherData);
        }
        $('#container4').highcharts(chartOptions({
            title: '区域',
            serieName: '区域',
            serieData: zoneData
        }));

        function chartOptions(options) {
            return    {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                credits: {enabled: false},
                title: {
                    text: options.title,
                    margin: 10
                },
                tooltip: {
                    pointFormat: '<b>{point.percentage:.1f}%</b><br/>{point.id}',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            connectorWidth: 0,
                            inside: true,
                            distance: -30,
                            color: 'white'
                        }
                    }
                },
                series: [
                    {
                        type: 'pie',
                        name: options.serieName,
                        data: options.serieData
                    }
                ]
            };
        }
    });

    function fmtPoint(data, other) {
        for (var i = 0; i < data.length; i++) {
            if (data[i][0] == "其他") {
                data[i] = {name: '其他', y: data[i][1], id: fmtId(other)};
            }
        }
        return data;
    }
    ;

    //[["春秋晚期",3.20],["汉",1.26]]
    function fmtId(other) {
        var id = "";
        if (other.length > 20) {
            id = "<table><tr>";
            for (var i = 0; i < other.length; i++) {
                id += "<td>" + other[i][0] + ": " + other[i][1] + "% " + other[i][2] + "个</td>";
                if ((i + 1) % 2 == 0) {
                    id += "</tr><tr>";
                }
            }
            id += "</tr></table>";
        } else {
            for (var i = 0; i < other.length; i++) {
                id += other[i][0] + ": " + other[i][1] + "% " + other[i][2] + "个<br/>";
            }
        }
        return id;
    }
    ;
</script>
</#macro>
