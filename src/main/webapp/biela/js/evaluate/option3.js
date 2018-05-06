function option3(name, units, flag) {
    var count = 5;
    var nameWorst = [];
    if (data[name] == null) {
       return;
    }
    var dataWorst = data[name].slice(-count);
    for (var i = 0, l = dataWorst.length; i < l; i++) {
        nameWorst.push(dataWorst[i].name);
    }

    var nameBest = [];
    var dataBest = data[name].slice(0, count);
    dataBest.reverse();
    for (var i = 0, l = dataBest.length; i < l; i++) {
        dataBest[i] = {
            name: dataBest[i].name,
            value: -dataBest[i].value
        };
        nameBest.push(dataBest[i].name);
    }
    var option = {
        title: {
            text: '',
            subtext: '',
            sublink: '',
            itemGap: 5,
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: function (v) {
                return  v[1] + ' : ' + (Math.abs(v[2])) + units;
            }
        },
        toolbox: {
            show: true,
            //orient : 'vertical',
            //x: 'right',
            //y: 'center',
            feature: {
                mark: {show: true},
//                dataView: {show: true, readOnly: true},
                //magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        dataRange: {
            orient: 'right',
            show: true,
            x: 'right',
            precision: name != 'co' ? 0 : 2,
            min: data[name + 'Min'],
            max: data[name + 'Max'],
            text: flag ? ['差', '优'] : ['优', '差'],           // 文本，默认为数值文本
            calculable: true,
            itemWidth: 40,
            itemHeight: 40,
//            color: flag ?['maroon', 'purple', 'red', 'yellow', 'lightgreen'] : ['lightgreen', 'yellow', 'red', 'purple', 'maroon']
            color: flag ? ['red', 'yellow', 'lightgreen'] : ['lightgreen', 'yellow', 'red']
        },
        grid: {
            x: 0,
            y: 50,
            x2: 0,
            y2: 0,
            borderWidth: 0
        },
        xAxis: [
            {
                type: 'value',
                position: 'top',
                splitLine: {show: false},
                axisLine: {show: false},
                axisLabel: {show: false},
                min: data[name + 'Min'],
                max: data[name + 'Max'] * 3
            },
            {
                type: 'value',
                position: 'bottom',
                splitLine: {show: false},
                axisLine: {show: false},
                min: -1000,
                max: 0,
                axisLabel: {
                    show: false,
                    formatter: function (v) {
                        return -v;
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'category',
                splitLine: {show: false},
                axisLine: {show: false},
                axisLabel: {show: false},
                data: nameWorst
            },
            {
                type: 'category',
                splitLine: {show: false},
                axisLine: {show: false},
                axisLabel: {show: false},
                data: nameBest
            }
        ],
        animation: false,
        series: [
            {
                name: '综合评估（' + name + '）',
                type: 'map',
                mapType: 'china',
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        }
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: function (v) {
                        var value = v[2];
                        if (value == "-") {
                            value = "暂无数据"
                        } else {
                            value = (Math.abs(value)) + units;
                        }
                        return  v[1] + ' : ' + value;
                    }
                },
                //roam: true,
                mapLocation: {
                    x: '30%',
                    y: 30,
                    width: '50%'
                },
                data: data[name]
            },
            {
                name: '综合评估最差（' + name + '）',
                type: 'bar',
                tooltip: {
                    trigger: 'item',
                    formatter: function (v) {
                        return  v[1] + ' : ' + (v[2]) + units;
                    }
                },
                barMaxWidth: 30,
                itemStyle: {
                    normal: {
                        color: (function () {
                            var zrColor = require('zrender/tool/color');
                            return zrColor.getLinearGradient(
                                0, 80, 0, 700,
                                //['orangered','yellow','lightskyblue']
                                [
                                    [0.3, 'lightgreen'],
                                    [0.5, 'yellow'],
                                    [1, 'red']
                                ]
//                                ['maroon','purple','red','orange','yellow','lightgreen']
                            )
                        })(),
                        label: {
                            show: true,
                            position: 'top',
                            formatter: '{b} : {c}' + units,
                            textStyle: {
                                fontSize: '15',
                                fontFamily: '微软雅黑',
                                fontWeight: 'bold',
                                color: '#000'
                            }
                        }
                    }
                },
                data: dataWorst
            }
        ]
    };
    return option;
}