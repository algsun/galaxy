/**
 * 删除设备
 *
 * @author liuzhu
 * @date 2013-10-17
 * @dependency jquery, pnotify, ztree, model
 */

(function ($) {
    $(function () {
        // 删除设备
        var okCallback;
        var deviceId;
        var $this = $(this);
        (function () {
            var nodeIds = [];
            $("#delete_all").click(function () {
                var $nodeIds = $("tbody input[type='checkbox']:checked");
                if ($nodeIds.length != 0) {
                    $('#deleteDeviceModal').modal('show');
                    $("#verifyCodeImage").attr("src", "verifyCode.action?name=deleteDevice&t=" + new Date().getTime());
                }else{
                    art.dialog({
                        id: "mess",
                        fixed: true,
                        title: message.tips,
                        content: "请先选择设备",
                        okValue: message.ok,
                        ok: function () {
                        },
                        cancelValue: message.cancel,
                        cancel: function () {
                        }
                    });
                }
            });

        })();
        (function () {
            $("#deleteConfirm").click(function () {

                var verifyCodeText = $("#verifyCode").val();
                var $verifyMessage = $("#verifyMessage");
                var $verifyCode = $("#verifyCode");
                if (verifyCodeText == "") {
                    $verifyMessage.text(message.inputVerificationCode);
                    return;
                }

                var deviceIds = new Array();
                var $nodeIds = $("tbody input[type='checkbox']:checked");
                $nodeIds.each(function () {
                    deviceIds.push($(this).val());
                });

                var url = "device/deleteDevice?deviceIds=" + deviceIds + "&verifyCode=" + verifyCodeText;
                $.getJSON(url, function (result) {
                    if (result.codeSuccess) {
                        $verifyCode.val("");
                        $verifyMessage.text("");
                        if (result.deleteSuccess) {
                            // 刷新设备树
                            App(window.BLUEPLANET).deviceTree.reload();
                            window.pnotify(message.deleteDeviceSuccess, "info");
                            $('#deleteDeviceModal').modal('hide');
                            // 删除选中的设备 tr
                            $nodeIds.each(function () {
                                var deleteRow = "#delete_"+$(this).val();
                                $(deleteRow).remove();
                            });
                        } else {
                            window.pnotify(message.deleteDeviceFailed, "error");
                            $('#deleteDeviceModal').modal('hide');
                        }
                    } else {
                        $verifyCode.val("");
                        $verifyMessage.text(message.verificationCodeError);
                        $("#verifyCodeImage").attr("src", "verifyCode.action?name=deleteDevice&t=" + new Date().getTime());
                    }
                });
            });
        })();

        //刷新验证码
        (function () {
            var $verifyCodeImage = $("#verifyCodeImage");
            $("#verifyCodeImage,#refreshVerifyCodeImageButton").click(function () {
                $verifyCodeImage.attr("src", "verifyCode.action?name=deleteDevice&t=" + new Date().getTime());
                return false;
            });
        })();

        //关闭对话框
        (function () {
            // 显示隐藏从模块
            $("#closeDevice").click(function () {
                $('#deleteDeviceModal').modal('hide');
                $("#verifyCode").val("");
                $("#verifyMessage").text("");
            });
        })();
    });
})(jQuery);

