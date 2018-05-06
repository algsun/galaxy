/**
 * 下面三个点状数据比对图，根据idx判断不同的两个监测指标的比对
 *
 * @param idx
 * @param selected
 */
function option2(idx, selected) {
    var option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            data: ['省/直辖市'],
            selectedMode: false,
            orient: 'vertical',
            x: 'right'
        },
        toolbox: {
            show: true,
            x:'center',
            feature: {
                mark: {show: true},
                dataZoom: {show: true},
//                dataView: {show: true, readOnly: true},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        grid: {
            x: 40,
            y: 30,
            x2: 65,
            y2: 30
        },
        xAxis: [
            {
                name: idx != 2 ? '湿度(%)' : 'CO2(ppm)',
                type: 'value',
                power: 1,
                splitNumber: 4,
                scale: true
            }
        ],
        yAxis: [
            {
                name: idx != 0 ? '光照(lux)' : 'CO2(ppm)',
                type: 'value',
                power: 1,
                splitNumber: 4,
                scale: true
            }
        ],
        series: [
            {
                name: '省/直辖市',
                type: 'scatter',
                symbolSize: 20,
                data:[]
            }
        ]
    };

    var scatterData = [];
    var tipFormatter;

    switch (idx + '') {
        case '0':
            tipFormatter = function (v) {
                return v[1] + '<br>'
                    + '湿度 : ' + v[2][0] + '(%)<br/>'
                    + 'CO2 : ' + v[2][1] + '(ppm)<br/>'
                    + '光照 ：' + v[2][2] + '(lux)';
            }
            for (var city in selected) {
                if (selected[city]) {
                    scatterData.push({
                        name: city,
                        value: [
                            PG[city].hum,
                            PG[city].co2,
                            PG[city].lux
                        ],
                        itemStyle: {normal: {color: PG[city].color}}
                    });
                }
            }
            break;
        case '1':
            tipFormatter = function (v) {
                return v[1] + '<br>'
                    + '湿度 : ' + v[2][0] + '(%)<br/>'
                    + '光照 : ' + v[2][1] + '(lux)<br/>'
                    + 'CO2 : ' + v[2][2] + '(ppm)';
            }
            for (var city in selected) {
                if (selected[city]) {
                    scatterData.push({
                        name: city,
                        value: [
                            PG[city].hum,
                            PG[city].lux,
                            PG[city].co2
                        ],
                        itemStyle: {normal: {color: PG[city].color}}
                    });
                }
            }
            break;
        case '2':
            tipFormatter = function (v) {
                return v[1] + '<br>'
                    + 'CO2  : ' + v[2][0] + '(ppm)<br/>'
                    + '光照  : ' + v[2][1] + '(lux)<br/>'
                    + '湿度  : ' + v[2][2] + '(%)';
            }
            for (var city in selected) {
                if (selected[city]) {
                    scatterData.push({
                        name: city,
                        value: [
                            PG[city].co2,
                            PG[city].lux,
                            PG[city].hum
                        ],
                        itemStyle: {normal: {color: PG[city].color}}
                    });
                }
            }
    }

    option.tooltip.formatter = tipFormatter;
    option.series[0].data = scatterData;
    return option;
}