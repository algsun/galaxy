$(function () {
    //方案管理验证
    var $schemeSubmit = $("#schemeSubmit");
    $schemeSubmit.click(function(){
        if (!checkSchemeId()) {
            return false;
        }else if(!checkSchemeName()){
            return false;
        }else if(!checkInstitutionId()){
            return false;
        }else if(!checkConfirmTime()){
            return false;
        }else if(!checkConfirmNum()){
            return false;
        }else {
            $("#reasonForm").submit();
        }
    });
});
//验证方案编号
var checkSchemeId = null;
(function () {
    var $schemeId = $("#schemeId");
    checkSchemeId = function () {
        if ($schemeId.val().trim().length < 1) {
            helpError($("#schemeId-input-help"), "方案编号不能为空");
            return false;
        } else{
            helpOk($("#schemeId-input-help"), "");
            return true;
        }
    };
})();

//验证方案名称
var checkSchemeName = null;
(function () {
    var $name = $("#name");
    checkSchemeName = function () {
        if ($name.val().trim().length < 1) {
            helpError($("#name-input-help"), "方案名称不能为空");
            return false;
        } else{
            helpOk($("#name-input-help"), "");
            return true;
        }
    };
})();
//验证设计单位
var checkInstitutionId = null;
(function () {
    var $institutionId = $("#institutionId");
    checkInstitutionId = function () {
        if ($institutionId.val()== 0) {
            helpError($("#institution-input-help"), "请选择设计单位");
            return false;
        } else{
            helpOk($("#institution-input-help"), "");
            return true;
        }
    };
})();
//验证批准时间
var checkConfirmTime = null;
(function () {
    var $confirmTime = $("#confirmTime");
    checkConfirmTime = function () {
        if ($confirmTime.val().trim().length < 1) {
            helpError($("#confirmTime-input-help"), "批准时间不能为空");
            return false;
        } else{
            helpOk($("#confirmTime-input-help"), "");
            return true;
        }
    };
})();
//验证批准文号
var checkConfirmNum = null;
(function () {
    var $confirmNum = $("#confirmNum");
    checkConfirmNum = function () {
        if ($confirmNum.val().trim().length < 1) {
            helpError($("#confirmNum-input-help"), "批准文号不能为空");
            return false;
        } else{
            helpOk($("#confirmNum-input-help"), "");
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