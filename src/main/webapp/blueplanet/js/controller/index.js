/**
 * 调控页面
 *
 * @author liuzhu
 * @date 2015-6-18
 *
 * @author chenyaofei
 * @data 2016-6-22
 * @description  修改主动调控
 */
$(function () {
    function chartOption($thisData){
        return {
            tooltip : {
                formatter: "{a} <br/>{b} : {c}%"
            },
            toolbox: {
                feature: {
                    restore: {},
                    saveAsImage: {}
                }
            },
            series : [
                {
                    type: 'gauge',
                    center : ['50%', '50%'],    // 默认全局居中
                    startAngle: 180,
                    endAngle: 0,

                    radius: '100%',
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            width: 10
                        }
                    },
                    axisTick: {            // 坐标轴小标记
                        length: 5,        // 属性length控制线长
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: 'white'
                        }
                    },
                    splitLine: {           // 分隔线
                        length: 10,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            color: 'white'
                        }
                    },
                    title : {
                        show: false
                    },
                    detail: {
                        formatter:'目标湿度：{value}%',
                        offsetCenter:[0,5],
                        textStyle:{
                            fontSize: 12,
                            fontWeight: 'bolder'
                        }
                    },
                    data:[{value: $thisData.currentHumidity,name: '目标湿度'}]
                }
            ]
        };
    }
    $(function () {
        $(".humidityChart").each(function(){
            var $thisChart = $(this);
            var $thisData= {
                currentHumidity: $thisChart.data("value")
            };
            var myChart= echarts.init($thisChart[0]);
            myChart.setOption(chartOption($thisData));
        });
    });
});

