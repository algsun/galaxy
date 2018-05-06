/**
 * 回到顶部小部件.
 *
 * 回到顶部 dom 动态添加到 body 中. 使用时只需要引用此 js 即可.
 * @author gaohui
 * @date 2012-10-25
 * @dependency jQuery
 */

(function ($) {
    $(function () {
        var backToTopDom = ['<div id="back-to-top" class="p-f" style="right: 10px; bottom: 50px;">',
            '<button class="btn btn-large" title="返回顶部" onclick="window.scrollTo(0,0);"><i class="icon-arrow-up"></i></button>',
            '</div>'].join('');
        var $backToTop = $(backToTopDom).appendTo($('body'));
        var hl = $.browser.webkit ? $('body')[0] : $('html')[0];
        //var $backToTop = $('#back-to-top');
        $backToTop.hide();
        $(window).scroll(function () {
            var height = hl.scrollTop;
            if (height > 150) {
                $backToTop.show();
            } else {
                $backToTop.hide();
            }
        });
    });
})(jQuery);
