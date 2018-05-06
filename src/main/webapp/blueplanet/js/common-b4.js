/**
 * 页面通用js
 *
 * @author gaohui
 * @date 2013-02-04
 */

(function ($) {

    (function () {
        // bootstrap tooltip
        $(function () {
            //$("[rel='tooltip']").tooltip();
        });
    })();

    (function () {
        // ajax 请求默认缓存，IE 系列浏览器会缓存，其他则不。所以要取消 IE 缓存
        $.ajaxSetup({cache: false});
    })();

    (function(){
        var BluePlanet = App('blueplanet');
        // 根据 "<base>" 标签将链接转换为绝对路径，如果可以转换。
        BluePlanet.basedUrl = function(url){
            var basePath = $('base').attr('href');
            if (basePath) {
                url = basePath + url;
            }
            return url;
        };
    })();

})(jQuery);