/**
 * 工具
 *
 * @author gaohui
 * @date 2013-06-04
 * @dependency jQuery
 **/

/**
 * 将url根据 base 标签转换为绝对路径如果可以；不行原样返回。
 *
 * @param url
 * @returns baseUrl
 */
function basedUrl(url) {
    var basePath = $('base').attr('href');
    if (basePath) {
        return  basePath + url;
    }

    return url;
}

/**
 * pnotify 简单的封装
 *
 * @author gaohui
 * @date 2013-02-04
 * @dependency jquery, pnotify
 */
(function ($, global) {
    // 初始化后可用
    global.pnotifyInit = function () {
        // 取消消息历史
        $.pnotify.defaults.history = false;

        // 右下角消息位置
        var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 25, "firstpos2": 25};
        // 消息通知
        global.pnotify = function (title, type) {
            $.pnotify({
                title: title,
                type: type,
                addclass: "stack-bottomright",
                stack: stack_bottomright
            });
        };
    };
})(jQuery, window);


/**
 * 获取图片原始大小
 *
 * @author gaohui
 * @date 2013-06-05
 * @dependency jQuery
 */
(function ($, global) {
    global.imageNaturalSize = function (imgSrc, callback) {
        jQuery("<img/>").load(function () {
            var naturalWidth = this.width;
            var naturalHeight = this.height;
            callback(naturalWidth, naturalHeight);
        }).attr("src", imgSrc);
    };
})(jQuery, window);

/**
 * 浏览器是否支持canvas
 *
 * @author liuzhu
 * @date 2013-10-29
 */
function isCanvasSupported(){
    var elem = document.createElement('canvas');
    return !!(elem.getContext && elem.getContext('2d'));
}
