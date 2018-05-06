/**
 * 日期工具类
 *
 * @author gaohui
 * @date 2013-03-12
 * @dependency moment.js
 */


window.DateUtil = {
    formats: {
        // [format, level]
        YEAR: ['YYYY', 1],
        MONTH: ['YYYY-MM', 2],
        DAY: ['YYYY-MM-DD', 3]
    },
    // 格式化日期, 将一个种日期格式转化为另一种日期格式
    formatDate: function (baseDateStr, targetFormat, chartType) {
        var baseFormat;
        var key;
        for (key in this.formats) {
            var format = this.formats[key];
            if (moment(baseDateStr, format[0]).isValid()) {
                baseFormat = format;
            }
        }
        var date = moment(baseDateStr, baseFormat[0]);
        if (baseFormat[1] < targetFormat[1]) {
            // 如果由高往低转化，空余的时间使用当前时间
            var baseLevel = baseFormat[1];
            var targetLevel = targetFormat[1];
            var now = moment();
            // 年转为月
            if (baseLevel == 1 && targetLevel == 2) {
                date.month(now.month());
            }
            // 年转为日
            else if (baseLevel == 1 && targetLevel == 3) {
                date.month(now.month());
                date.date(now.date());
            }
            // 月转为日
            else if (baseLevel == 2 && targetLevel == 3) {
                if (chartType && chartType == 'windrose') {
                    date.date(now.subtract('days', 1).date());
                } else {
                    date.date(now.date());
                }
            }
        }
        return date.format(targetFormat[0]);
    }
};
