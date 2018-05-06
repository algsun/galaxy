var archivesApp = angular.module('archivesApp');
archivesApp.controller('customersCtrl', function ($scope, $http) {

    $scope.drawRegisterFun = function () {
        var repairRecordId = $("#repairRecordId").val();
        var drawRegisterUrl = 'archives/drawRegisters.json?drawRegister.repairRecord.id=' + repairRecordId;
        $http.get(drawRegisterUrl).success(function (response) {
            $scope.drawRegisters = response.drawRegisters;
        });
    }

    $scope.drawDeleteFun = function (id) {
        art.dialog({
            width: '100%',
            height: '100%',
            id: "delete",
            fixed: true,
            title: "删除确认",
            content: "确认要删除？",
            okValue: "确定",
            ok: function () {
                $.get("archives/drawRegister/delete.json", {'drawRegister.id': id}, function (result) {
                    if (result) {
                        window.pnotify("删除成功", "info");
                        $scope.drawRegisterFun();
                    }
                });
            },
            cancelValue: "取消",
            cancel: function () {
            }
        })
    }

    $("#drawRegister_form").validate({
        rules: {
            'drawRegister.drawingType': {
                required: true,
                maxlength: 25
            },
            'drawRegister.description': {
                required: true,
                maxlength: 100
            },
            'drawRegister.stamp': {
                required: true
            },
            'drawRegister.remark': {
                required: true,
                maxlength: 100
            }
        },
        messages: {
            'drawRegister.drawingType': {
                required: "请输入图纸类型",
                maxlength: jQuery.format("图纸类型不能大于{0}个字符")
            },
            'drawRegister.description': {
                required: "请输入简单描述",
                maxlength: jQuery.format("简单描述不能大于{0}个字符")
            },
            'drawRegister.stamp': {
                required: "请选择时间"
            },
            'drawRegister.remark': {
                required: "请输入备注",
                maxlength: jQuery.format("备注不能大于{0}个字符")
            }
        }
    });

    $("#draw-register-submit").click(function () {
        if ($("#drawRegister_form").valid()) {
            var params = $("#drawRegister_form").serialize();
            params = decodeURIComponent(params, true);
            var url = "archives/drawRegister/save.json?" + params;
            var repairRecordId = $("#repairRecordId").val();

            var $imageInput = $("#imageInput");
            var $imageInputHelp = $("#imageInput-help");

            var file = $imageInput.val();
            if (file != "") {
                if (!/.(jpg|png|jpeg|bmp|gif|tiff|tif|DNG|NEF|pdf|drw|CAD|acr|crw)$/.test(file.toLowerCase())) {
                    helpError($imageInputHelp, "文件类型必须是jpg、png、jpeg、bmp、gif、tiff、tif、DNG、NEF、pdf、drw、CAD、acr或crw");
                    return;
                }
                $imageInputHelp.empty();
            }

            $http.get(url).success(function (response) {
                $("#drawingType").val("");
                $("#description").val("");
                $("#stamp").val("");
                $("#remark").val("");
                window.pnotify("添加成功", "info");
                $scope.drawRegisterFun();
                if (response != 0) {
                    $("#draw_register_id").val(response);
                    if (file != "") {
                        $('#uploadForm').ajaxSubmit({
                            dataType: 'json',
                            success: function (result) {
                                $("#imageInput").val("");
//                            $("#showImage").attr("src", result.filePath + "/" + result.imageFileName + "?t=" + new Date().getTime());
                            }
                        });
                    }
                }
            }).error(function (response) {
                    window.pnotify("添加失败", "warn");
                });
        }
    });
});