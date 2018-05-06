/**
 * 光学摄像机点位列表
 *
 * @author gaohui
 * @date 2013-06-24
 * @dependency jquery, artDialog, pnotify, bootstrap.tooltip
 */

$(function () {
    window.pnotifyInit();

    // 调整图片实景宽度
    $('span[data-role="imageRealWidth"]').click(function () {
        var $this = $(this);
        var dvPlaceId = $this.attr('data-id');
        var imageRealWidth = $this.attr('data-width');
        var $content = $('#imageRealWidthDialog').clone();
        $content.find('input').val(imageRealWidth);
        var dialog = art.dialog({
            id: 'imageRealWidth',
            title: message.JCVWidth,
            content: $content[0],
            okValue: message.ok,
            ok: function () {
                var width = $content.find('input').val();
                if (!width) {
                    $content.find('.help-block').text(message.pleaseInput);
                    return false;
                }

                // 验证数字
                width = $.trim(width);
                if (!/^(0|([1-9]\d*))$/.test(width)) {
                    $content.find('.help-block').text(message.positiveInteger);
                    return false;
                }

                $.get('updateImageRealWidth.action', {dvPlaceId: dvPlaceId, width: width}, function (result) {
                    if (result.success) {
                        $this.attr('data-width', width);
                        $this.attr('title', width + ' mm');
                        $this.tooltip('destroy').tooltip(); // 刷新 tooltip
                        window.pnotify(message.saveJCVWidthSuccess, 'info');
                        dialog.close();
                    } else {
                        window.pnotify(message.saveJCVWidthFailed, 'notice');
                    }
                });
                return false;
            },
            cancelValue: message.cancel,
            cancel: function () {
            }
        });
    }).tooltip();
});
