/**
 *
 * @author gaohui
 * @date 2012-11-30
 */

$(function () {

    // 手机号码验证
    jQuery.validator.addMethod("isMobile", function (value, element) {
        var length = value.length;
        return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(147))+\d{8})$/.test(value));
    }, "请正确填写您的手机号码");

    $("#userForm").validate({
        rules:{
            userName:{
                required:true,
                rangelength:[2, 10]
            },
            password:{
                required:true,
                rangelength:[8, 30]
            },
            password2:{
                required:true,
                rangelength:[8, 30],
                equalTo:'#password'
            },
            mobile:{
                number:true,
                digits:true,
                isMobile:true,
                minlength:11,
                maxlength:11
            }
        },
        messages:{
            userName:{
                required:"请输入姓名"
            },
            mobile:{
            },
            password2:{
                equalTo:'两次密码不一致'
            }
        }
    });
});
