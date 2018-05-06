/**
 * 位置点概览
 *
 * @author liuzhu
 * @date 2014-6-30
 * @dependency jquery, flot
 */

(function ($) {
    // 创建图表, $chart 为要显示图形的 dom, chartOption 为图表选项
    var createChart = function ($chart, chartOption) {
        $.plot($chart, [chartOption.data], {
            grid: {
                borderSize: 1,
                borderColor: '#ddd',
                hoverable: true
            },
            tooltip: true,
            tooltipOpts: {
                content: function (xVal, yVal) {
                    return chartOption.cnName + "：" + yVal + " " + chartOption.unit + " <br>时间：%x"
                },
                xDateFormat: "%Y-%m-%d %H:%M:%S" // 时间格式
            },
            series: {
                shadowSize: 0,
                //points: {show: true}, // 去掉显示点
                lines: {show: true},
                color: "#08c"
            },
            xaxis: {
                show: true,
                mode: 'time',
                timeformat: "%H:%M",
                minTickSize: [20, "minute"],
                timezone: 'browser'
            },
            yaxis: {
                show: true,
                tickDecimals: chartOption.precision // 数据精度, 小数点后面的位数
            }
        });

        return $chart;
    };

    $(function () {

        $(".trend-chart").each(function () {
            var $this = $(this);
            // 获取图表数据
            // 用 script.text() 在 IE 为空，改用 script.html() 正常
            var dataText = $this.siblings("script[type='text/x-chart-data']").html();
            createChart($this, eval("(" + dataText + ")"));
        });
    });
})(jQuery);
