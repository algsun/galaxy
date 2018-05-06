/*
 * @author wang.rensong
 * @date 2013-5-23
 */

$(function () {

    var helpClear = function ($help) {
        $help.empty();
    };

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

    //正确提示
    var help = function ($help, msg) {
        var content = msg;
        $help.empty().append(content);
    };

    $(function () {

        $("#imageInput").change(function () {
            var $imageInput = $("#imageInput");
            var $imageInputHelp = $("#imageInput-help");
            var file = $imageInput.val();
            if (file == "") {
                $("#showImage").attr("src", "../orion/images/yong.jpg");
                return;
            }
            if (file != "" && !/.(gif|jpg|jpeg|png)$/.test(file.toLowerCase())) {
                helpError($imageInputHelp, "文件类型必须是gif、jpg、jpeg或png");
                return;
            }
            if (file != "") {
//                $imageInputHelp.text(file);
                helpOk($imageInputHelp, file);
            }

        });
    });


    //方法3
    var $relicForm = $("#relicForm");
    var $relicSubmit = $("#relicSubmit");

    //验证名称
    var checkRelicName = null;
    (function () {
        var $relicName = $("#relicName");
        checkRelicName = function () {
            if ($relicName.val().trim().length < 1) {
                helpError($("#name-input-help"), "文物名称不能为空");
                return false;
            } else {
                helpOk($("#name-input-help"), "")
                return true;
            }
        };
    })();

    //验证总登记号
    var totalCodeFlag=false;
    var checkTotalCode = function () {
        totalCodeFlag=false;
        var totalCode=$("#totalCode").val();
        if (totalCode.trim().length < 1) {
            helpOk($("#totalCode-input-help"), "");
            totalCodeFlag=true;
            return true;
        }
        $.get("validateTotalCode?totalCode=" + totalCode, function (result) {
            if (result.data == "true") {
                helpOk($("#totalCode-input-help"), "");
                totalCodeFlag = true;
            } else if (result.data == "false") {
                helpError($("#totalCode-input-help"), "总登记号已经存在");
                totalCodeFlag = false;
            } else {
            }
        });
    };


//    //验证编目号
//    var checkRelicCatalogCode = null;
//    (function () {
//        var $catalogCode = $("#catalogCode");
//        checkRelicCatalogCode = function () {
//            if ($catalogCode.val().length < 1) {
//                helpError($("#catalogCode-input-help"), "编目号不能为空");
//                return false;
//            } else {
//                helpOk($("#catalogCode-input-help"), "");
//                return true;
//            }
//        };
//        return true;
//    })();

//验证分类号
//    var checkRelicTypeCode = null;
//    (function () {
//        var $typeCode = $("#typeCode");
//        checkRelicTypeCode = function () {
//            if ($typeCode.val().length < 1) {
//                helpError($("#typeCode-input-help"), "分类号不能为空");
//                return false;
//            } else {
//                helpOk($("#typeCode-input-help"), "");
//                return true;
//            }
//        };
//    })();

//验证件数
    var checkRelicCount = null;
    var $count = $("#count");
    (function () {
        checkRelicCount = function () {
            if ($count.val().length < 1) {
                helpError($("#count-input-help"), "件数不能为空");
                return false;
            }
            else if (/[^0-9]/g.test($count.val())) {
                helpError($("#count-input-help"), "件数只能为数字");
                return false;
            }else if ($count.val() < 1){
                helpError($("#count-input-help"), "件数只能为正整数");
                return false;
            }
            else {
                helpOk($("#count-input-help"), "");
                return true;
            }
        };
    })();

//验证质地
    var checkRelicTexture = null;
    (function () {
        var $texture = $("#textureId");
        checkRelicTexture = function () {
            if ($texture.val() == 0) {
                helpError($("#texture-input-help"), "请选择质地");
                return false;
            } else {
                helpOk($("#texture-input-help"), "");
                return true;
            }
        };
    })();

//验证年代
    var checkRelicEra = null;
    (function () {
        var $era = $("#eraId");
        checkRelicEra = function () {
            if ($era.val() == 0) {
                helpError($("#era-input-help"), "请选择年代");
                return false;
            } else {
                helpOk($("#era-input-help"), "");
                return true;
            }
        };
    })();

//验证级别
    var checkRelicLevel = null;
    (function () {
        var $level = $("#levelId");
        checkRelicLevel = function () {
            if ($level.val() == 0) {
                helpError($("#level-input-help"), "请选择级别");
                return false;
            } else {
                helpOk($("#level-input-help"), "");
                return true;
            }
        };
    })();

    $("#relicName").focusout(function () {
        checkRelicName();
    });

    $("#totalCode").focusout(function () {
        checkTotalCode();
    });

//    $("#catalogCode").focusout(function () {
//        checkRelicCatalogCode();
//    });
    //分类号不在被验证非空 added by 王耕 2014-11-04
//    $("#typeCode").focusout(function () {
//        checkRelicTypeCode();
//    });

    $("#count").focusout(function () {
        checkRelicCount();
    });

    $("#textureId").focusout(function () {
        checkRelicTexture();
    });
    $("#eraId").focusout(function () {
        checkRelicEra();
    });
    $("#levelId").focusout(function () {
        checkRelicLevel();
    });

//表单提交
    $relicSubmit.click(function () {
        if (!checkRelicName()) {
            return false;
        } else if (!totalCodeFlag) {
            return false;
        }
        else if (!checkRelicCount()) {
            return false;
        }
        else if (!checkRelicTexture()) {
            return false;
        }
        else if (!checkRelicEra()) {
            return false;
        }
        else if (!checkRelicLevel()) {
            return false;
        }
        else {
            $("#relicForm").submit();
        }
    });

    $("#reset").click(function () {
        //重置
        $("#relicName").val("");
        $("#name-input-help").text("");

        $("#totalCode").val("");
        $("#totalCode-input-help").text("");

        $("#catalogCode").val("");
        $("#catalogCode-input-help").text("");

        $("#catalogCode").val("");
        $("#catalogCode-input-help").text("");

        $("#typeCode").val("");
        $("#typeCode-input-help").text("");

        $("#count").val("");
        $("#count-input-help").text("");

        $("#totalCode").val("");
        $("#totalCode-input-help").text("");

        $("#zoneId").val("");
        $("#zone-input-help").text("");

    });
})
;
