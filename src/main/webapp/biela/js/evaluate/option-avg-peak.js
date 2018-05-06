function optionAvgPeak(data, units) {
    option = {
        title: {
            text: '',
            subtext: ''
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params) {
                var res = params[0][1];
                for (var i = 0, l = params.length; i < l; i++) {
                    res += '<br/>' + params[i][0] + ' : ' + params[i][2] + units;
                }

                return res;
            }
        },
        legend: {
            orient: 'horizontal', // 'vertical'
            x: 'center', // 'center' | 'left' | {number},
            y: 'bottom', // 'center' | 'bottom' | {number}
            backgroundColor: '#eee',
            borderColor: 'rgba(178,34,34,0.8)',
            borderWidth: 1,
            padding: 5,    // [5, 10, 15, 20]
            itemGap: 1,
            data: ['最大值', '最小值']
        },
        toolbox: {
            show: true,
            x: 'right', // 'center' | 'left' | {number},
            y: 'top', // 'center' | 'bottom' | {number}
            feature: {
                mark: {show: true},
//                dataView: {show: true, readOnly: true},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                data: data.stamp
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        grid: {
            x: 50,
            y: 30,
            x2: 30,
            y2: 60
        },
        series: [
            {
                name: data.maxValue.name,
                type: data.maxValue.type,
                data: data.maxValue.data
            },
            {
                name: data.minValue.name,
                type: data.minValue.type,
                data: data.minValue.data
            }
        ]
    };
    return option;
}