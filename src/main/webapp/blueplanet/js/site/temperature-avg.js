$(function () {
    $.post("summarize/getAverage.json", function (result) {
        var zoneData = [];
        var titleDate = "";
        var data = [];
        var text = "";
        var categories = [];
        if (result && getJsonLength(result) > 0) {

            //组织日期和区域
            var dates = [];
            var zoneIds = [];
            Array.prototype.contains = function(item){
                return RegExp(item).test(this);
            };
            for (var i = 0; i < result.length; i++) {
                var zoneAvgDatas = result[i];
                var zoneId = zoneAvgDatas.zoneId;
                if (!zoneIds.contains(zoneId)) {
                    zoneIds.push(zoneId);
                }
                var msDate = zoneAvgDatas.msDate;
                if (!dates.contains(msDate)) {
                    dates.push(msDate);
                }
            }

            //日期升序排序
            function down(a, b) {
                return (a > b) ? 1 : -1
            }
            dates.sort(down);
            zoneIds.sort(down);

            //组织图表数据日期
            text = "（统计站点一月温度日均值）";
            for (var n = 0; n < dates.length; n++) {
                categories.push(moment(dates[n]).format("MM-DD"));
                titleDate = '(' + moment(dates[n]).format("YYYY·MM") + ')';
            }

            //for (var i = 0; i < result.data.length; i++) {
            //    data = [];
            //    var tempert = result.data[i];
            //    var tempList = tempert.avgdataList;
            //    if (tempList.length > 0) {
            //        for (var j = 0; j < tempList.length; j++) {
            //            date = '(' + moment(tempList[j].msDate).format("YYYY·MM") + ')';
            //            //判断是否没有值 ，没有值set空占位
            //            if (tempList[j].avgValue > -9999.0) {
            //                data.push(tempList[j].avgValue);
            //            } else {
            //                data.push(null);
            //            }
            //
            //        }
            //    }
            //    zoneData.push({name: tempert.zoneName, data: data});
            //}

            //根据区域集合装填返回数据中数据
            for (var i = 0; i < zoneIds.length; i++) {
                data = [];
                var datas = [];
                var dataDates = [];
                var zoneId = zoneIds[i];
                var zoneName = "";

                //组织区域数据
                for (var n = 0; n < result.length; n++) {
                    var resultData = result[n];
                    if (resultData.zoneId == zoneId) {
                        datas.push(resultData.avgValue);
                        dataDates.push(resultData.msDate);
                        zoneName = resultData.zoneName;
                    }
                }

                //数据按日期排序
                for (var n = 0; n < dates.length; n++) {
                    var date = dates[n];
                    //如果当前区域下没有遍历数据的日期，就在该日期设为空值。
                    if (!dataDates.contains(date)) {
                        data.push(null);
                        continue;
                    }
                    //如果当前区域下有遍历数据的日期，那么赋值。
                    for (var j = 0; j < dataDates.length; j++) {
                        if (date == dataDates[j]) {
                            data.push(datas[j]);
                        }
                    }
                }
                //区域数据添加到图表数据
                zoneData.push({name: zoneName, data: data});
            }
        } else {
            zoneData.push({name: "", data: [0.0]});
            text = "<span class='muted'>(" + message.noData + ")</span>";
            categories.push("");
            return;
        }
        $('#container6').highcharts({
            title: {
                text: message.dailyMeanTemperatureStatistics + titleDate,
                x: -20 //center
            },
            subTitle: {
                text: text
            },
            xAxis: {
                categories: categories,
                labels: {
                    x: 10,//调节x偏移
                    y: 10,//调节y偏移
                    rotation: -30//调节倾斜角度偏移
                }
            },
            yAxis: {
                title: {
                    text: message.dailyMeanTemperature + '(°C)'
                },
                plotLines: [
                    {
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }
                ]
            },
            tooltip: {
                valueSuffix: '°C'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            credits: {
                enabled: false
            },
            series: zoneData,
            exporting: {
                enabled: false,
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
                filename: message.areaDailyMeanTemperature
            }
        });
    });
});
function getJsonLength(jsonData) {
    var length = 0;
    for (var item in jsonData) {
        length++;
    }
    return length;
}