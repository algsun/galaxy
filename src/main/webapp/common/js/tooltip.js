/**
 * 显示在指定位置显示 popover
 *
 * @author gaohui
 * @date 2013-10-28
 *
 * dependency: jQuery, bootstrap-popover
 */
$(function () {
    if (window.Tooltip === undefined || window.Tooltip === null) {
        window.Tooltip = {};
    }
    (function () {
        $("body").append('<div id="_tooltip_ele" style="position: absolute;"></div>');
        //弹出新的提示(同时一时刻，只能有一个新的提示，所以会将之前的提示关闭掉，再重新创建)
        Tooltip.tooltip = function (left, top, title, content) {
            //先销毁
            Tooltip.closeTooltip();

            $("#_tooltip_ele").offset({left:left + 10, top:top})
                .popover({
                    title:title,
                    content:content,
                    html:true,
                    trigger:'manual',
                    placement:'right',
                    animation:false
                }).popover('show');
        };

        //销毁之前的 popover ，不管存在不存在
        Tooltip.closeTooltip = function () {
            $("#_tooltip_ele").popover('destroy');
        };

        // alias
        Tooltip.show = Tooltip.tooltip;
        Tooltip.close = Tooltip.closeTooltip;
    })();
});