/**
 *  处理时间格式
 * @author:wangrensong
 * @time: 13-4-26
 */

$(function () {
    $("input[name='dateType']").change(function () {
        var $this = $(this);
        if (!$this.is(':checked')) {
            return;
        }
        var momentFormat;
        var my97Format;
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
});

$(function () {
    // 显示隐藏子规则
    $("a[data-master]").click(function () {
        var masterRuleId = $(this).attr("data-master");
        $("tr[data-master-for='" + masterRuleId + "']").toggle();
        var $iconI = $("a[data-master='" + masterRuleId + "']").find("i");

        $iconI.toggleClass("icon-plus-sign");
        $iconI.toggleClass("icon-minus-sign");

        return false;
    });
});

