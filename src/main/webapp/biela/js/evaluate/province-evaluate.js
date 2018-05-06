/**
 * 该JS为首页的五个图表的
 * 初始化，数据请求，数据组装JS
 *
 *
 * @type {Array}
 */
var option1yAxis = [];
var option1Citys = [];
var option1Data = [];
var PG = {};
var mapData = {};
var flag=true;
var data = {
    comRateT: [], comRateTMin: 0, comRateTMax: 100,
    comRateH: [], comRateHMin: 0, comRateHMax: 100,
    waveValueT: [], waveValueTMin: 0, waveValueTMax: 10,
    waveValueH: [], waveValueHMin: 0, waveValueHMax: 50,
    comprehensive: [], comprehensiveMin: 0, comprehensiveMax: 100
}

function sortData(a, b) {
    return a.value - b.value
}
function sortData1(a, b) {
    return b.value - a.value
}

data.format = function (datas) {
    function pushMapData(key) {
        data[key] = datas[key];
    }

    pushMapData('comprehensive');
    pushMapData('comRateT');
    pushMapData('comRateH');
    pushMapData('waveValueT');
    pushMapData('waveValueH');
    if (data['comprehensive'] != null) {
        data['comprehensive'].sort(sortData);
        data['comRateT'].sort(sortData);
        data['comRateH'].sort(sortData);
        data['waveValueT'].sort(sortData1);
        data['waveValueH'].sort(sortData1);
    }

}

$(function () {

    var avgComRate = function (comRate) {
        var sum = 0;
        for (var c in comRate) {
            var value = parseFloat(comRate[c].comRate);
            sum = sum + value;
        }
        return ((sum / (comRate.length)) * 100).toFixed(2);
    }

    $.ajax({
        type: "GET",
        url: "findMixture",
        async: false,
        success: function (result) {
            if (result.length > 0) {

                var comRateTdataArray = [];
                var comRateHdataArray = [];
                var waveValueTdataArray = [];
                var waveValueHdataArray = [];
                var comprehensiveDataArray = [];
                $.each(result, function (index, data) {
                    var comRateTdata = {};
                    var comRateHdata = {};
                    var waveValueTdata = {};
                    var waveValueHdata = {};
                    var comprehensiveData = {};
                    var provinceName = data.provinceName;
                    var mixtures = data.mixtureVOs;
                    $.each(mixtures, function (i, mixture) {
                        var sensorId = mixture.sensorId;
                        var comRate = mixture.comRate;
                        var waveValue = mixture.waveValue;
                        if (sensorId == 33) {//温度
                            comRateTdata["name"] = provinceName;
                            comRateTdata["value"] = parseFloat((comRate * 100).toFixed(2));
                            waveValueTdata["name"] = provinceName;
                            waveValueTdata["value"] = parseFloat(waveValue.toFixed(2));
                        } else if (sensorId == 32) {//湿度
                            comRateHdata["name"] = provinceName;
                            comRateHdata["value"] = parseFloat((comRate * 100).toFixed(2));
                            waveValueHdata["name"] = provinceName;
                            waveValueHdata["value"] = parseFloat(waveValue.toFixed(2));
                        }
                    });
                    comprehensiveData["name"] = provinceName;
                    comprehensiveData["value"] = parseFloat(avgComRate(mixtures));

                    comRateTdataArray.push(comRateTdata);
                    comRateHdataArray.push(comRateHdata);
                    waveValueTdataArray.push(waveValueTdata);
                    waveValueHdataArray.push(waveValueHdata);
                    comprehensiveDataArray.push(comprehensiveData);
                });
                mapData["comRateT"] = comRateTdataArray;
                mapData["comRateH"] = comRateHdataArray;
                mapData["waveValueT"] = waveValueTdataArray;
                mapData["waveValueH"] = waveValueHdataArray;
                mapData["comprehensive"] = comprehensiveDataArray;
            } else {
                $("#ul").empty();
                $("#mixtureChartDiv").empty();
                $("#explanation").empty();
                document.getElementById("noData").style.display='';
                flag=false;
            }
        }
    });

    $.ajax({
        type: "GET",
        url: "evaluate/siteRealtimeAvgData.json",
        async: false,
        success: function (result) {
            if (result != null) {
                for (var areaName in result) {
                    option1Citys.push(areaName);
                    var realtimeAvgObject = {};
                    var realtimeAvgData = [];
                    var NodeSensorList = result[areaName];

                    var pgData = {};

                    option1yAxis[0] = "温度";
                    option1yAxis[1] = "湿度";
                    option1yAxis[2] = "光照";
                    option1yAxis[3] = "紫外";
                    option1yAxis[4] = "二氧化碳";
                    option1yAxis[5] = "VOC-高灵敏度";

                    realtimeAvgData[0] = 0;
                    realtimeAvgData[1] = 0;
                    realtimeAvgData[2] = 0;
                    realtimeAvgData[3] = 0;
                    realtimeAvgData[4] = 0;
                    realtimeAvgData[5] = 0;

                    pgData["tmt"] = 0;
                    pgData["hum"] = 0;
                    pgData["lux"] = 0;
                    pgData["uv"] = 0;
                    pgData["co2"] = 0;
                    pgData["voc-高灵敏度"] = 0;

                    $.each(NodeSensorList, function (index, nodesensor) {
                        var value = Math.round(nodesensor.sensorPhysicalValue * 100) / 100;
                        if (nodesensor.sensorPhysicalid == 33) {
                            realtimeAvgData[0] = value;
                            pgData["tmt"] = value;
                        } else if (nodesensor.sensorPhysicalid == 32) {
                            realtimeAvgData[1] = value;
                            pgData["hum"] = value;
                        } else if (nodesensor.sensorPhysicalid == 41) {
                            realtimeAvgData[2] = value;
                            pgData["lux"] = value;
                        } else if (nodesensor.sensorPhysicalid == 42) {
                            realtimeAvgData[3] = value;
                            pgData["uv"] = value;
                        } else if (nodesensor.sensorPhysicalid == 36) {
                            realtimeAvgData[4] = value;
                            pgData["co2"] = value;
                        } else if (nodesensor.sensorPhysicalid == 83) {
                            realtimeAvgData[5] = value;
                            pgData["voc-高灵敏度"] = value;
                        }
                        PG[areaName] = pgData;
                    });

                    realtimeAvgObject["name"] = areaName;
                    realtimeAvgObject["data"] = realtimeAvgData;
                    realtimeAvgObject["type"] = "line";
                    realtimeAvgObject["stack"] = "总量";
                    realtimeAvgObject["itemStyle"] = { normal: {label: {show: true, position: 'insideRight'}}};
                    option1Data.push(realtimeAvgObject);
                }
            }
        }
    });
});

require.config({
    paths: {
        echarts: '../assets/echarts/2.1.8/echarts',
        'echarts/chart/bar': '../assets/echarts/2.1.8/chart/bar',
        'echarts/chart/line': '../assets/echarts/2.1.8/chart/line',
        'echarts/chart/map': '../assets/echarts/2.1.8/chart/map',
        'echarts/chart/scatter': '../assets/echarts/2.1.8/chart/scatter'
    }
});

var EC_READY = false;
var DATA_READY = false;
var myChart1;
var myChart2;
var myChart3;
var myChart4;
var myChart5;

var ecConfig;

require(
    [
        'echarts',
        'echarts/chart/line',
        'echarts/chart/bar',
        'echarts/chart/map',
        'echarts/chart/scatter'
    ],
    function (ec) {
        if(!flag){
           return;
        }
        EC_READY = true;
        myChart1 = ec.init(document.getElementById('histogram')).showLoading({effect: 'bubble'});
        myChart2 = ec.init(document.getElementById('scatter1')).showLoading({effect: 'bubble'});
        myChart3 = ec.init(document.getElementById('scatter2')).showLoading({effect: 'bubble'});
        myChart4 = ec.init(document.getElementById('scatter3')).showLoading({effect: 'bubble'});
        myChart5 = ec.init(document.getElementById('mixtureChart')).showLoading({effect: 'bubble'});
        DATA_READY = true;
        ecConfig = require('echarts/config');
        showTabContent(1);
        showTabContent(2);
        data.format(mapData);
        showTabContent(5, 'comprehensive');
        myChart1.on(ecConfig.EVENT.LEGEND_SELECTED, legendShare);
        myChart1.on(ecConfig.EVENT.RESTORE, legendShare);
    }
);

$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    if (!EC_READY || !DATA_READY) {
        return;
    }
    if (e.target.id.match('comRate-t')) {//温度达标率
        showTabContent(5, 'comRateT');
    } else if (e.target.id.match('comRate-h')) {//湿度达标率
        showTabContent(5, 'comRateH');
    } else if (e.target.id.match('waveValue-t')) {//温度波动值
        showTabContent(5, 'waveValueT');
    } else if (e.target.id.match('waveValue-h')) {//湿度波动值
        showTabContent(5, 'waveValueH');
    } else if (e.target.id.match('comprehensive')) {
        showTabContent(5, 'comprehensive');
    }
});

var functionMap = {};
function showTabContent(idx, type) {
    functionMap['chart' + idx](type);
}

functionMap.chart1 = function () {
    myChart1.hideLoading();
    myChart1.setOption(option1());
}

functionMap.chart2 = function () {
    setTimeout(legendShare, 100);
}

functionMap.chart5 = function (type) {
    myChart5.hideLoading();
    var units;
    var flag;
    if (type == 'waveValueT') {
        units = "℃";
    } else {
        units = "%";
    }
    myChart5.on(ecConfig.EVENT.CLICK, function (param) {
        location.href = "comprehensive?name=" + param.name;
    });

    if (type == 'comRateT' || type == 'comRateH' || type == 'comprehensive') {
        flag = false;
    } else if (type == 'waveValueT' || type == 'waveValueH') {
        flag = true;
    }

    myChart5.setOption(option3(type, units, flag));
}

function legendShare() {
    var zrColor = require('zrender/tool/color');

    var legend = myChart1.component.legend;
    var selected = legend.getSelectedMap();
    for (var city in selected) {
        if (selected[city]) {
            PG[city].color = zrColor.alpha(legend.getColor(city), 0.6);
        }
    }
    myChart2.hideLoading();
    myChart3.hideLoading();
    myChart4.hideLoading();
    myChart2.setOption(option2(0, selected), true);
    myChart3.setOption(option2(1, selected), true);
    myChart4.setOption(option2(2, selected), true);
}