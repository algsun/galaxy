/**
 * 已绑定设备
 *
 * @author gaohui
 * @date 2013-02-26
 * @dependency jquery, pnotify, ztree, artDialog
 * @check @wang yunlong 2013-02-26 #1776
 */

(function ($) {
    $(function () {
        (function () {
            // 显示隐藏从模块
            $("a[data-master]").click(function () {
                var masterModuleId = $(this).attr("data-master");
                $("tr[data-master-for='" + masterModuleId + "']").toggle();
                return false;
            });
        })();

        (function () {
            // 开启、关闭巡检
            $("button[id='pollingOpen'],button[id='pollingClose']").click(function () {
                var $this = $(this);
                var poolingFlag = $("#poolingFlag").val();
                var openOrClose = $this.attr("data-value");
                if (openOrClose == poolingFlag) {
                    art.dialog({
                        id: "mess",
                        fixed: true,
                        title: message.tips,
                        content: message.actionDown,
                        okValue: message.ok,
                        ok: function () {
                        },
                        cancelValue: message.cancel,
                        cancel: function () {
                        }
                    });
                } else {
                    $('#patrolModal').modal('show');
                    $("#patrolVerifyCodeImage").attr("src", "verifyCode.action?name=patrol&t=" + new Date().getTime());
                    $("#Patrol").attr("data-href",$this.attr("data-href"));
                    $("#Patrol").attr("data-title",$this.attr("data-title"));
                    $("#Patrol").attr("data-mess",$this.attr("data-mess"));
                }

            });
        })();

        //警告栏确定
        (function () {
               $("#Patrol").click(function(){
                   var verifyCode=$("#patrolVerifyCode").val();
                   var $this = $(this);
                   var href = $this.attr("data-href");
                   var title = $this.attr("data-title");
                   var mess = $this.attr("data-mess");
                   if(verifyCode==""){
                       $("#patrolVerifyCode").val("");
                       $("#patrolVerifyMessage").text(message.inputVerificationCode);
                       $("#patrolVerifyCodeImage").attr("src", "verifyCode.action?name=patrol&t=" + new Date().getTime());
                       return false;
                   }
                    href=href+"?verifyCode="+ verifyCode;
                    $.getJSON(href,function (result) {
                        if (!result.flagVerifyCode){
                            $("#patrolVerifyCode").val("");
                            $("#patrolVerifyMessage").text(message.verificationCodeError);
                            $("#patrolVerifyCodeImage").attr("src", "verifyCode.action?name=patrol&t=" + new Date().getTime());
                            return;
                        }else{
                            $("#patrolModal").modal('hide');
                            $("#patrolVerifyCode").val("");
                            $("#patrolVerifyMessage").text("");
                        }
                        if (result.success){
                            art.dialog({
                                id: "mess",
                                fixed: true,
                                title: title,
                                content: mess,
                                okValue: message.ok,
                                ok: function () {
                                },
                                cancelValue: message.cancel,
                                cancel: function () {
                                }
                            });
                        }else{
                            art.dialog({
                                id: "mess",
                                fixed: true,
                                title: title,
                                content: message.patrolBeat,
                                okValue: message.ok,
                                ok: function () {
                                },
                                cancelValue: message.cancel,
                                cancel: function () {
                                }
                            });
                        }

                    });


               });
        })();

        //刷新验证码
        (function () {
            var $verifyCodeImage = $("#patrolVerifyCodeImage");
            $("#patrolVerifyCodeImage,#refreshVerifyCodeImagePatrol").click(function () {
                $verifyCodeImage.attr("src", "verifyCode.action?name=patrol&t=" + new Date().getTime());
                return false;
            });
        })();

        //关闭对话框
        (function () {
            // 显示隐藏从模块
            $("#closePatrol").click(function () {
                $("#patrolModal").modal('hide');
                $("#patrolVerifyCode").val("");
                $("#patrolVerifyMessage").text("");
            });
        })();
    });
})(jQuery);
