function option1() {
    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: option1Citys,
            x: 'center', // 'center' | 'left' | {number},
            y: '430' // 'center' | 'bottom' | {number}
        },
        grid: {
            x: 90,
            y: 30,
            x2: 30,
            y2: 100
        },
        toolbox: {
            show: true,
            x: 'right', // 'center' | 'left' | {number},
            y: 'top', // 'center' | 'bottom' | {number}
            feature: {
                mark: {show: true},
//                dataView: {show: true, readOnly: true},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'value'
            }
        ],
        yAxis: [
            {
                type: 'category',
                data: option1yAxis
            }
        ],
        series:option1Data
    };
    return option;
}