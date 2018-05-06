/**
 *<pre>
 * ftp信息表单验证
 *</pre>
 * @author: Wang yunlong
 * @time: 13-3-25 上午10:12
 * @check li.jianfei 2013-3-29  #2388
 */
$(function () {
    // ip
    jQuery.validator.addMethod("ip", function (value, element) {
        return this.optional(element) || (/^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})(\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})){3}$/.test(value));
    }, message.ftpIpError);

    // 端口
    jQuery.validator.addMethod("port", function (value, element) {
        return this.optional(element) || (/^([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]{1}|6553[0-5])$/.test(value));
    }, message.ftpPortError);
    $("#ftp-form").validate({
        rules: {
            name: {
                required: true
            },
            ip: {
                required: true,
                ip: true
            },
            port: {
                required: true,
                number: true,
                digits: true,
                port: true,
                maxlength: 5
            },
            userName: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            name: {
                required: message.nameRequired
            },
            ip: {
                required: message.hostRequired
            },
            port: {
                required: message.portRequired
            },
            userName: {
                required: message.userNameRequired
            },
            password: {
                required: message.passwordRequired
            }
        },
        errorPlacement: function (error, element) {
            if (element.is(":radio")) {
                error.appendTo(element.parent());
            } else if (element.is(":checkbox")) {
                error.appendTo(element.parent());
            } else {
                error.appendTo(element.next());
            }
        }
    });
})
