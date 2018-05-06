/**
 * 日期控件(年，月,日)js
 *
 * @author liuzhu
 * @date 2013-09-11
 */


$(document).ready(function () {
    $("input[name='dateType']").change(function () {
        var momentFormat;
        var my97Format;
        var $this = $(this);
        if (!$this.is(':checked')) {
            return;
        }
        switch ($this.val()) {
            case "1":
                momentFormat = DateUtil.formats.YEAR;
                my97Format = 'yyyy';
                break;
            case "2":
                momentFormat = DateUtil.formats.MONTH;
                my97Format = 'yyyy-MM';
                break;
            case "3":
                momentFormat = DateUtil.formats.DAY;
                my97Format = 'yyyy-MM-dd';
                break;
        }
        var $timeInput = $("#date");
        var value = DateUtil.formatDate($timeInput.val(), momentFormat);
        $timeInput.val(value);
        $timeInput.unbind("click.time");
        //时间选择器
        $timeInput.bind("click.time", function () {
            WdatePicker({
                dateFmt: my97Format,
                el: $timeInput[0],
                maxDate: '%y-%M-%d'
            });
        });
    }).change();
})