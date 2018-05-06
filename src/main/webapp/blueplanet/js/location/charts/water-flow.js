function option(chartValues){
    var option = {
        title: {
            text: chartValues.title
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: chartValues.legend
        },
        toolbox: {
            show: true,
            feature: {
                mark: {show: false},
                dataView: {show: false, readOnly: true},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                axisLabel: {
                    show: true,
                    interval: 0
                },
                data: chartValues.stamp
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: chartValues.data
    };
    return option;
}