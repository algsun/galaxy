/**
 *<pre>
 * 角色管理分配权限的交互处理
 *</pre>
 * @author Wang yunlong
 * @date 12-11-28
 * @time 下午2:04
 */
$(function () {
    // 权限级联选中状态
    (function () {
        var $level2 = $(".level-2");
        var $level1 = $(".level-1");
        $level2.find("input").live("click", onLevel2InputClick);
        $level1.find("input").live("click", onLevel1InputClick);
    })();

    // 表单验证和验证提交
    (function () {
        var roleExist = false;
        var $roleName = $("#role-name");
        var $roleSubmit = $("#role-submit");
        var $roleNameEnableMsg = $(".role-name-enable");
        var flag = false;
        //角色重名判断
        $roleName.bind('blur', roleNameFun);
        //表单提交
        $roleSubmit.click(function () {
            roleNameFun();
            if (flag) {
                $.post("roleExist.action", {roleId: $("#role-id").val(), roleName: $("#role-name").val()}, function (result) {
                    if (result.exist) {
                        $roleNameEnableMsg.text("该角色名称已存在").show();
                    } else {
                        $("#role-form").submit();
                    }
                });
            }
            return false;
        });

        //获得焦点时，清空消息
        $roleName.focus(function () {
            $roleNameEnableMsg.text("").hide();
        });
        function roleNameFun() {
            var $this = $roleName;
            if ($this.val().length < 1) {
                $roleNameEnableMsg.text("角色名不能为空").show();
                flag = false;
            } else if ($this.val().length > 20) {
                $roleNameEnableMsg.text("角色名称长度不能超过20个字").show();
                flag = false;
            } else {
                flag = true;
            }
        }
    })();
});

//选中2级权限checked消息响应
function onLevel2InputClick() {
    var $this = $(this);
    var thisValue = $this.attr("checked");
    var $thisLevel1Privileges = $this.parent().parent().siblings(".level-1").find("input");
    if (thisValue == "checked") {
        $thisLevel1Privileges.attr("checked", "checked");
    }
    var $sibs = $this.parent().siblings("label").find("input");
    var flag = ($this.attr("checked") == "checked");
    $.each($sibs, function (index, sib) {
        flag = flag || ($(sib).attr("checked") == "checked");
    });
    $thisLevel1Privileges.attr("checked", flag);
}
//选中1级权限checked消息响应
function onLevel1InputClick() {
    var $this = $(this);
    var thisValue = $this.attr("checked");
    var $thisLevel2Privileges = $this.parent().parent().siblings(".level-2").find("input");
    $thisLevel2Privileges.attr("checked", thisValue == "checked");
}

