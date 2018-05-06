/**
 * 公共 js
 *
 * @author gaohui
 * @date 2013-05-11
 */


(function () {
    // ajax 请求默认缓存，IE 系列浏览器会缓存，其他则不。所以要取消 IE 缓存
    $.ajaxSetup({cache: false});
})();