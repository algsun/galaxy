/**
 * 左边伸缩, 配合 _demo/full-window-layout.css 使用
 *
 * @author gaohui
 * @date 2012-12-25
 * @dependency jquery, jquery.cookie
 */

(function ($) {
    // 是否收缩
    var IS_Shrink = 'blueplanet.aside.shrink';


    var $shrinkBar = $(".shrink-bar");
    var $asideContainer = $(".left-aside-container");
    var $contentContainer = $(".content-container");
    $shrinkBar.click(function () {
        $shrinkBar.toggleClass("xclose");
        $asideContainer.toggleClass("xclose");
        $contentContainer.toggleClass("xclose");

        if($shrinkBar.hasClass('xclose')){
            // 过期时间 1 年
            $.cookie(IS_Shrink, true, { expires: 365, path: '/' });
        }else{
            $.removeCookie(IS_Shrink, {path: '/'});
        }
    });

    // 默认左侧栏打开, 如果“合上”cookie 存在则合上

    if($.cookie(IS_Shrink)){
        $shrinkBar.click();
    }
})(jQuery);
