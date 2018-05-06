/**
 *
 *<pre>
 *
 *</pre>
 * @author: Wang yunlong
 * @time: 13-3-27 下午3:06
 * @check li.jianfei 2013-3-29  #2388
 */
$(function () {
    $("#test-connect").click(function () {
        var $this = $(this);
        $this.attr("disabled", "disabled").addClass("disabled");
        var $msg = $(this).siblings(".help-inline");
        $msg.html("<img src='images/loading.gif'/>");
        $.post("testFTPConnect.action", {
            ip: $("#ip").val(),
            port: $("#port").val(),
            userName: $("#userName").val(),
            password: $("#password").val()
        }, function (result) {
            if (result) {
                $msg.css("color", "red").text(result);
            } else {
                $msg.css("color", "green").html(message.connectTestSuccess);
            }
            $this.removeAttr("disabled").removeClass("disabled");
        });
        return false;
    });
});
