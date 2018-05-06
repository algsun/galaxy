/**
 * pnotify 简单的封装
 *
 * @author gaohui
 * @date 2013-02-04
 * @dependency jquery, pnotify
 */
(function ($, global) {
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
})(jQuery, window);
