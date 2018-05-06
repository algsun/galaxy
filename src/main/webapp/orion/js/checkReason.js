$(function () {
    //原因管理验证
    var $reasonSubmit = $("#reasonSubmit");
    $reasonSubmit.click(function () {
        if (!checkReasonName()) {
            return false;
        }
        //原因重复验证
        var reason = $("#repairReason").val();
        var id=$("#repairReasonId").val();

        $.get("reason/validationText?reason=" + reason+"&id="+id, function (validation) {
            if (!validation) {
                helpError($("#repairReason-input-help"), "原因已经存在！");
                return false;
            } else {
                helpOk($("#repairReason-input-help"), "")
                $("#reasonForm").submit();
            }
        });
    });
});
//验证名称
var checkReasonName = null;
(function () {
    var $reason = $("#repairReason");
    checkReasonName = function () {
        if ($reason.val().trim().length < 1) {
            helpError($("#repairReason-input-help"), "原因不能为空");
            return false;
        } else {
            helpOk($("#repairReason-input-help"), "")
            return true;
        }
    };
})();
//错误提示
var helpError = function ($help, msg) {
    var content = "<i class='mw-icon-cancel'></i> " + msg;
    $help.empty().append(content);
};

//正确提示
var helpOk = function ($help, msg) {
    var content = "<i class='mw-icon-ok'></i> " + msg;
    $help.empty().append(content);
};