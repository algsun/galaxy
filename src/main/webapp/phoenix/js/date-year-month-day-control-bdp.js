/**
 * 日期控件(年，月,日)js FOR Bootstrap-datepicker 1.7.0
 *
 * @author liuzhu
 * @date 2013-09-11
 */


$(function () {
    $("input[name='dateType']").change(function () {
        var momentFormat;
        var bdpFormat;
        var $this = $(this);
        var viewMode = 'days';
        if (!$this.is(':checked')) {
            return;
        }
        switch ($this.val()) {
            case "1":
                momentFormat = DateUtil.formats.YEAR;
                bdpFormat = 'yyyy';
                viewMode = 'years';
                break;
            case "2":
                momentFormat = DateUtil.formats.MONTH;
                bdpFormat = 'yyyy-mm';
                viewMode = 'months';
                break;
            case "3":
                momentFormat = DateUtil.formats.DAY;
                bdpFormat = 'yyyy-mm-dd';
                viewMode = 'days';
                break;
        }
        var $timeInput = $("#date");
        var value = DateUtil.formatDate($timeInput.val(), momentFormat);
        $timeInput.val(value);

        $timeInput.datepicker({
            startView: viewMode,
            minViewMode: viewMode,
            language: "zh-CN",
            format: bdpFormat,
            autoclose: true
        });
    }).change();
});