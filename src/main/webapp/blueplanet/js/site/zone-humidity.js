$(function () {
    $.getJSON("summarize/zoneHumidity.json", function (returnData) {
        var $zoneList = returnData.zoneVOList;
        var $zoneHumidityData = returnData.zoneHumidityData;
        var $zoneNames = '[';
        var $floatList = '[';
        var $avgValues = '[';

        $.each($zoneList, function(i,value){
             var $zoneId = value.zoneId;
             var zoneHumidity = $zoneHumidityData[$zoneId];
               if(zoneHumidity != null){
                   $zoneNames = $zoneNames + '"' + value.zoneName + '"' + ',';
                   $floatList = $floatList + '[' + zoneHumidity.minValue + ', ' + zoneHumidity.maxValue + ']' + ',';
                   $avgValues = $avgValues +  zoneHumidity.avgValue + ',';
               }
        });

        if($zoneNames.length > 1){
            $zoneNames = $zoneNames.substring(0,$zoneNames.length-1) + ']';
            $zoneNames = $.parseJSON($zoneNames);

            $floatList = $floatList.substring(0,$floatList.length-1) + ']';
            $floatList = $.parseJSON($floatList);

            $avgValues = $avgValues.substring(0,$avgValues.length-1) + ']';
            $avgValues = $.parseJSON($avgValues);


            $('#container1').highcharts({
                chart: {
                    inverted: true
                },
                credits: {
                    enabled: false
                },
                title: {
                    text: message.humidityWaveRange
                },
                subtitle: {
                    text: '（' + message.humidityWaveRangeNotes + '）'
                },
                xAxis: {
                    categories: $zoneNames
                    //categories: ['风信子', '七色堇', '依米花', '双生花', '蒲公英']
                },

                yAxis: {
                    title: {
                        text: message.humidity + ' ( % )'
                    }
                },
                tooltip: {
                    valueSuffix: '%',
                    followPointer: true,
                    shared: true
                },
                plotOptions: {
                    columnrange: {
                        dataLabels: {
                            enabled: true,
                            formatter: function () {
                                return this.y + '%';
                            }
                        }
                    },
                    spline: {
                        lineWidth: 0,
                        states: {
                            hover: {
                                enabled: false,
                                lineWidth: 5,
                                radius: 0
                            }
                        },
                        shadow: false
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
                    name: message.range,
                    type: 'columnrange',
                    color: '#33d1db',
                    data: $floatList
                    //data: [[25.4, 55.5], [18.6, 30.3], [20.7, 35.5], [18.7, 40.7], [33.3, 44.4]]
                }, {
                    name: message.averageValue,
                    type: 'spline',
                    color: '#ffffff',
                    data: $avgValues
                }]
            });
        }
    });
});