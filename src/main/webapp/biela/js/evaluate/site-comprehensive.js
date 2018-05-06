require.config({
    paths: {
        echarts: '../assets/echarts/2.1.8/echarts',
        'echarts/chart/bar': '../assets/echarts/2.1.8/chart/bar',
        'echarts/chart/line': '../assets/echarts/2.1.8/chart/line',
        'echarts/chart/map': '../assets/echarts/2.1.8/chart/map'
    }
});

var microwiseSiteGeo;
$.ajax({
    type: "GET",
    url: "findSite",
    async: false,
    success: function (result) {
        var site = {};
        for (var s in result) {
            var temp = result[s];
            var atemp = {};
            atemp["x"] = temp.lngBaiDu,
                atemp["y"] = temp.latBaiDu,
                site[temp.siteName] = atemp;
        }
        microwiseSiteGeo = site;
    }
});

var cityGeo = {};
for (var v  in microwiseSiteGeo) {
    cityGeo[v] = microwiseSiteGeo[v];
}

var EC_READY = false;
var DATA_READY = false;
var myChart0;
var avgPeak;

//颜色映射
var eColorMap = {
    't': '#87cefa',
    'h': '#ff7f50',
    'lux': '#da70d6',
    'uw': '#32cd32',
    'ppm': '#6495ed',
    'voc': '#ff69b4'
};

var mapComRateData = [];

var data = {
    oriData: [],
    cityToData: {},
    //cityArray : [],
    comprehensive: [], comprehensiveMin: 0, comprehensiveMax: 100,
    t: [], tMin: 0, tMax: 100,
    h: [], hMin: 0, hMax: 100,
    lux: [], luxMin: 0, luxMax: 100,
    uw: [], uwMin: 0, uwMax: 100,
    ppm: [], ppmMin: 0, ppmMax: 100,
    voc: [], vocMin: 0, vocMax: 100,
    geoCoord: {}
};

function sortData(a, b) {
    return a.value - b.value
}

data.format = function (oriData) {
    data.oriData = oriData;
    var city;
    var siteId;
    var cityToData = {};
    var geoCoord = {};

    function pushData(key, city, siteId, sensorId, value, cnName, units) {
        data[key].push({name: city, value: value, siteId: siteId, sensorId: sensorId, cnName: cnName, units: units});
    }

    var singleData;
    for (var i = 0, l = oriData.length; i < l; i++) {
        singleData = oriData[i]
        city = singleData.area;
        siteId = singleData.siteId;
        //cityArray.push(city);
        cityToData[city] = singleData;

        pushData('comprehensive', city, siteId, 0, singleData.comprehensive, "达标率", "%");
        pushData('t', city, siteId, 33, singleData.t, "温度", "℃");
        pushData('h', city, siteId, 32, singleData.h, "湿度", "%");
        pushData('lux', city, siteId, 41, singleData.lux, "光照", "lux");
        pushData('uw', city, siteId, 42, singleData.uw, "紫外", "uw/c㎡");
        pushData('ppm', city, siteId, 36, singleData.ppm, "二氧化碳", "ppm");
        pushData('voc', city, siteId, 83, singleData.voc, "VOC-高灵敏度", "ppb");

        geoCoord[city] = cityGeo[city];
    }

    data['t'].sort(sortData);
    data['h'].sort(sortData);
    data['lux'].sort(sortData);
    data['uw'].sort(sortData);
    data['ppm'].sort(sortData);
    data['voc'].sort(sortData);
    data['comprehensive'].sort(sortData);

    data.oriData = oriData;
    data.cityToData = cityToData;
    data.geoCoord = geoCoord;
}


var formatNum = function (value) {
    value = value * 100;
    if (value == 100 || value == 0) {
        return value;
    } else {
        return value.toFixed(2);
    }
}

var avgValue = function (comRate) {
    var sum = 0;
    for (var c in comRate) {
        var value = parseFloat(comRate[c].comValue);
        sum = sum + value;
    }
    return ((sum / (comRate.length)) * 100).toFixed(2);
}

var avgPeakFun = function () {

    var siteId = $("#siteId").val();
    var sensorId = $("#sensorId").val();
    var units = $("#units").val();
    var param = {siteId: siteId, sensorId: parseInt(sensorId), provinceName: provinceName};
    $.ajax({
        type: "POST",
        url: "findSiteAvgPeak",
        async: false,
        data: param,
        success: function (result) {
            var nodeSensor = result.locationSensorVOs;
            var data = {};
            var date = [];
            var maxValue = {};
            var minValue = {};

            var maxSensorValue = [];
            var minSensorValue = [];
            for (var node in nodeSensor) {
                stamp = nodeSensor[node].stamp.valueOf().substring(0, 10);
                date.push(stamp.substring(8) + "号");
                maxSensorValue.push(nodeSensor[node].maxSensorValue);
                minSensorValue.push(nodeSensor[node].minSensorValue);
            }
            minValue.name = "最小值";
            minValue.type = "bar";

            maxValue.name = "最大值";
            maxValue.type = "bar";

            maxValue.data = maxSensorValue;
            minValue.data = minSensorValue;
            data.minValue = minValue;
            data.maxValue = maxValue;
            data.stamp = date;

            avgPeak.hideLoading();
            if(sensorId == 0){
                $("#avgPeak").css("display", "none");
                var comprehenseive = $("#comprehenseive").val();
                $("#siteName").html(comprehenseive);
                return;
            }
            if (nodeSensor.length == 0) {
                $("#avgPeak").css("display", "none");
                $("#siteName").html("暂无数据");
                return;
            } else {
                $("#avgPeak").css('display', '');
            }
            require(
                [
                    'echarts',
                    'echarts/chart/line',
                    'echarts/chart/bar',
                    'echarts/chart/map'
                ],

                function (ec) {
                    avgPeak = ec.init(document.getElementById('avgPeak'));
                    avgPeak.setOption(optionAvgPeak(data, units));
                }
            );
        }
    });
}

require(
    [
        'echarts',
        'echarts/chart/line',
        'echarts/chart/bar',
        'echarts/chart/map'
    ],

    function (ec) {
        EC_READY = true;
        myChart0 = ec.init(document.getElementById('comRateMap')).showLoading({effect: 'bubble'});
        avgPeak = ec.init(document.getElementById('avgPeak')).showLoading({effect: 'bubble'});
        var name = $("#provinceName").val();
        $.ajax({
            type: "POST",
            url: "findSiteComplianceRate",
            async: false,
            data: {name:name},
            success: function (result) {
                var siteLogic = [];
                for (var site in result) {
                    var siteComRate = {};
                    siteComRate.area = result[site].logicGroupName;
                    siteComRate.siteId = result[site].siteId;
                    var comRate = result[site].complianceRateVOs;
                    for (var c in comRate) {
                        if (comRate[c].sensorId == 32) {
                            siteComRate["h"] = formatNum(comRate[c].comValue);
                        } else if (comRate[c].sensorId == 33) {
                            siteComRate["t"] = formatNum(comRate[c].comValue);
                        } else if (comRate[c].sensorId == 41) {
                            siteComRate["lux"] = formatNum(comRate[c].comValue);
                        } else if (comRate[c].sensorId == 42) {
                            siteComRate["uw"] = formatNum(comRate[c].comValue);
                        } else if (comRate[c].sensorId == 36) {
                            siteComRate["ppm"] = formatNum(comRate[c].comValue);
                        } else if (comRate[c].sensorId == 83) {
                            siteComRate["voc"] = formatNum(comRate[c].comValue);
                        }
                        siteComRate["comprehensive"] = avgValue(comRate)
                    }
                    siteLogic.push(siteComRate);
                }
                mapComRateData = siteLogic;

                DATA_READY = true;
                var ecConfig = require('echarts/config');
                data.format(mapComRateData);

                showTabContent(0, oCurTabIdx);
                myChart0.on(ecConfig.EVENT.MAP_ROAM, extMark);
            }
        });
    }
);

var oCurTabIdx = 't';
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    if (!EC_READY || !DATA_READY) {
        return;
    }
    if (e.target.id.match('o-')) {
        oCurTabIdx = e.target.id.replace('o-', '');
        showTabContent(0, oCurTabIdx);
    }
});

var functionMap = {};
function showTabContent(idx, type) {
    functionMap['chart' + idx](type);
}

var extShapeList = [];
var dataWorst;

var provinceName = $("#provinceName").val();
//第一张图（地图）
functionMap.chart0 = function (type) {
    myChart0.hideLoading();
    var units = "%";

    myChart0.setOption(option0(type, provinceName, units));
    dataWorst = data[type];
    setTimeout(extMark, 100);
    setTimeout(avgPeakFun, 200);
}

//地图标线
function extMark() {
    var map = myChart0.chart.map;
    var zr = myChart0.getZrender();
    zr.delShape(extShapeList);
    extShapeList = [];
    var x = Math.round(zr.getWidth() - 130);
    var y = 50;
    var pos;
    var city;
    var lineShape = require('zrender/shape/Line');
    var comIndex = 0;
    for (var v in dataWorst) {
        var tempValue = dataWorst[v].value;
        if (tempValue != null) {
            break;
        } else {
            comIndex++;
        }
    }
    if (comIndex == dataWorst.length) {
        comIndex = 0;
    }
    $("#siteId").val(dataWorst[comIndex].siteId);
    $("#sensorId").val(dataWorst[comIndex].sensorId);
    $("#units").val(dataWorst[comIndex].units);

    city = dataWorst[comIndex].name;
    pos = map.getPosByGeo(provinceName, cityGeo[city]);
    var comValue = dataWorst[comIndex].value;
    if (comValue == null) {
        comValue = "0"+"%";
    } else {
        comValue = comValue + "%";
    }
    var sensorId = dataWorst[comIndex].sensorId;


    if(sensorId != 0){
        $("#siteName").html(city + '<span class="badge badge-success">' + dataWorst[comIndex].cnName + '</span>' + "近7天的最高值最低值");
    }else{
        $("#siteName").html("");
        $("#comprehenseive").val(city+"综合达标率为："+comValue);
    }

    extShapeList.push(new lineShape({
        shape: 'line',
        zlevel: 5,
        style: {
            xStart: pos[0],
            yStart: pos[1],
            xEnd: x - 30,
            yEnd: pos[1],
            //textX : x,
            //textY : pos[1],
            strokeColor: 'green',
            lineType: 'dashed',
            lineWidth: 3,
            text: city + ' : ' + comValue,
            textPosition: 'end'//'specific'
        }
    }));
    for (var i = 0, l = extShapeList.length; i < l; i++) {
        zr.addShape(extShapeList[i])
    }
    zr.refresh();
}


