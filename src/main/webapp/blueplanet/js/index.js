/* check xie.deng 2013-11-14 #6621*/
    /*
     原理：1.把所有的li的高度值放到数组里面
     2.第一行的top都为0
     3.计算高度值最小的值是哪个li
     4.把接下来的li放到那个li的下面
     */
$(function () {
    var margin = 8;//这里设置间距
    var li = $("#li-box li");//这里是所有li元素
    if (li.length > 0) {
        var li_W = li[0].offsetWidth + margin;//取区块的实际宽度（包含间距，这里使用源生的offsetWidth函数，不适用jQuery的width()函数是因为它不能取得实际宽度，例如元素内有pandding就不行了）
    }
    function liBoxStream() {//定义成函数便于调用
        var h = [];//记录区块高度的数组
        var n = 3;//窗口的宽度除以区块宽度就是一行能放几个区块
        for (var i = 0; i < li.length; i++) {//有多少个li就循环多少次
            li_H = li[i].offsetHeight;//获取每个li的高度
            if (i < n) {//n是一行最多的li，所以小于n就是第一行了
                h[i] = li_H;//把每个li放到数组里面
                li.eq(i).css("top", 0);//第一行的Li的top值为0
                li.eq(i).css("left", i * li_W);//第i个li的左坐标就是i*li的宽度
            }
            else {
                min_H = Math.min.apply(null, h);//取得数组中的最小值，区块中高度值最小的那个
                minKey = getarraykey(h, min_H);//最小的值对应的指针
                h[minKey] += li_H + margin;//加上新高度后更新高度值
                li.eq(i).css("top", min_H + margin);//先得到高度最小的Li，然后把接下来的li放到它的下面
                li.eq(i).css("left", minKey * li_W);	//第i个li的左坐标就是i*li的宽度
            }
        }
    }

    /* 使用for in运算返回数组中某一值的对应项数(比如算出最小的高度值是数组里面的第几个) */
    function getarraykey(s, v) {
        for (k in s) {
            if (s[k] == v) {
                return k;
            }
        }
    }

    /*onload的时候调用*/
    window.onload = function () {
        if (li.length > 0) {
            liBoxStream();
        }
    };

    $(function () {

        Highcharts.setOptions({
            global: {useUTC: false}
        });
        $(".trend-chart").each(function () {
            var $this = $(this);
            // 获取图表数据
            var dataText = $this.next().html();
            var $jsonData = eval("(" + dataText + ")");
            if ($jsonData.sensorPhysicalId == 33) {
                if ($jsonData.data.length > 0) {
                    $(this).highcharts(charOptionsCustomize($jsonData.data, $jsonData.cnName, $jsonData.unit));
                } else {
                    $(this).remove();
                }
            } else {
                if ($jsonData.maxData.length > 0) {
                    $(this).highcharts(charOptions($jsonData.maxData, $jsonData.minData, $jsonData.avgData, $jsonData.unit, 'line'));
                } else {
                    $(this).remove()
                }
            }
        });

    });


    // 生成图表  一般的检测指标
    function charOptions(maxData, minData, avgData, unit, chatType, isCustomize) {
        return {
            credits: {enabled: false}, // 取消右下角链接
            exporting: {enabled: false}, // 取消导出按钮
            title: {
                text: null
            },
            colors: [
                '#F08080',
                '#458B74',
                '#2eaeff'
            ],
            xAxis: {
                type: 'datetime',
                tickInterval: 24 * 3600 * 1000,
                dateTimeLabelFormats: {
                    day: '%m-%d'
                },
                labels: {
                    x: 10,//调节x偏移
                    y: 10,//调节y偏移
                    rotation: -30//调节倾斜角度偏移
                }
            },
            yAxis: {
                title: {
                    text: null
                }, labels: {
                    formatter: function () {
                        return this.value + unit
                    }
                }
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            tooltip: {
                crosshairs: true,
                shared: true,
                valueSuffix: unit,
                dateTimeLabelFormats: {
                    day: '%Y-%m-%d'
                }
            },

            legend: {
                enabled: false
            },
            chart: {
                type: chatType,
                marginRight: 25
            },
            series: [
                {
                    name: message.maximumValue,
                    data: maxData
                },
                {
                    name: message.minimumValue,
                    data: minData
                },
                {
                    name: "平均值",
                    data: avgData
                }
            ]

        }
    }

    // 生成图表 定制的检测指标
    function charOptionsCustomize(data, title, unit) {
        //设置图表时间非国际标准

        return {

            chart: {
                type: 'arearange',
                marginRight: 25
            },
            title: {
                text: ''
            },
            credits: {enabled: false}, // 取消右下角链接
            exporting: {enabled: false}, // 取消导出按钮

            xAxis: {
                type: 'datetime',
                tickInterval: 24 * 3600 * 1000,
                dateTimeLabelFormats: {
                    day: '%m-%d'
                },
                labels: {
                    x: 10,//调节x偏移
                    y: 10,//调节y偏移
                    rotation: -30//调节倾斜角度偏移
                }
            },

            yAxis: {
                title: {
                    text: null
                }, labels: {
                    formatter: function () {
                        return this.value + unit
                    }
                },
                tickInterval: 15
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            tooltip: {
                crosshairs: true,
                shared: true,
                valueSuffix: '°C',
                dateTimeLabelFormats: {
                    day: '%Y-%m-%d'
                }
            },

            legend: {
                enabled: false
            },

            series: [
                {
                    name: title,
                    data: data
                }
            ]

        }
    }
});