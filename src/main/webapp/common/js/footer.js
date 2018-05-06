/**
 * 页面底部位置控制.
 *
 * 1.如果 window 窗口 比内容(body)高时, footer fixed 到底部
 * 2.鼠标停留公司 logo 变色
 *
 * @author gaohui
 * @date 2012-10-24
 * @dependency jQuery, jQuery.qrcode
 */
(function ($) {
    $(function () {
        //鼠标停留公司 logo 变色
        $('#footer img[data-logo]').mouseenter(function () {
            $(this).attr('src', '../common/images/logo-top-150x46.png')
        }).mouseleave(function () {
                $(this).attr('src', '../common/images/logo-top-small-gray-150x46.png')
            });

        // 页面底部二维码
        (function () {
            var $base = $('base');
            if($base.length <= 0){
                return;
            }

            var apiUrl = $base.data('apiUrl');
            if(!apiUrl){
                return;
            }

            var $hugeQRCode = $('<div></div>');
            $hugeQRCode.qrcode({render: 'div', size: 200, text: apiUrl});

            $('#qrcode').popover({
                placement: 'top',
                trigger: 'hover',
                html: true,
                content: $hugeQRCode.html()
            });


            var $profileLi = $('#qrcode-profile');
            if($profileLi.length > 0){
                var username = $profileLi.data('username');
                var text = apiUrl + " | " + username;

                var $profileQRCode = $('<div></div>');
                $profileQRCode.qrcode({render: 'div', size: 200, text: text});

                $profileLi.popover({
                    placement: 'left',
                    trigger: 'hover',
                    html: true,
                    content: $profileQRCode.html()
                });
            }

        })();
    });
})(jQuery);

