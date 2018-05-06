/**
 * 验证用户表单信息
 * 添加用户、个人信息在用
 *
 * @author: YZ-LJF
 * @date 2012-11-26
 */
$(function () {

    // 手机号码验证
    jQuery.validator.addMethod("isMobile", function (value, element) {
        var length = value.length;
        return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(147))+\d{8})$/.test(value));
    }, "请正确填写您的手机号码");

    // 电话号码验证
    jQuery.validator.addMethod("isPhone", function (value, element) {
        var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的电话号码");

    $("#userForm").validate({
        rules:{
            email:{
                required:true,
                email:true
            },
            userName:{
                required:true,
                rangelength:[2, 10]
            },
            mobile:{
                number:true,
                digits:true,
                isMobile:true,
                minlength:11,
                maxlength:11
            },
            roles:{
                required:true
            }
        },
        messages:{
            email:{
                required:"请输入邮箱地址"
            },
            userName:{
                required:"请输入姓名"
            },
            mobile:{
            },
            roles:{
                required:"请至少选择一个用户角色"
            }
        },
        errorPlacement:function (error, element) {
            if (element.is(":radio")) {
                error.appendTo(element.parent());
            } else if (element.is(":checkbox")) {
                error.appendTo(element.parent());
            } else {
                error.appendTo(element.next());
            }
        }
    });


});
