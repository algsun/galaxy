archivesApp.controller('imageDatumCtrl', function ($scope, $http) {
    var repairRecordId = $("#repairRecordId").val();

    $scope.imageDatumFun = function () {
        var imageDatumUrl = 'archives/imageDatums.json?imageDatum.repairRecord.id=' + repairRecordId;
        $http.get(imageDatumUrl).success(function (response) {
            $scope.imageDatums = response.imageDatums;
        });
    }

    $scope.imageDatumDeleteFun = function (id) {
        art.dialog({
            id: "delete",
            fixed: true,
            title: "删除确认",
            content: "确定要删除？",
            okValue: "确定",
            ok: function () {
                $.get("archives/imageDatum/delete.json", {'imageDatum.id': id}, function (result) {
                    if (result) {
                        window.pnotify("删除成功", "info");
                        $scope.imageDatumFun();
                    }
                });
            },
            cancelValue: "取消",
            cancel: function () {
            }
        })
    }


    $("#imageDatum_from").validate({
        rules: {
            'imageDatum.content': {
                required: true,
                maxlength: 250
            },
            'imageDatum.media': {
                required: true,
                maxlength: 50
            },
            'imageDatum.duration': {
                required: true,
                number: true
            },
            'imageDatum.stamp': {
                required: true
            }
        },
        messages: {
            'imageDatum.content': {
                required: "请输入内容描述",
                maxlength: jQuery.format("内容描述不能大于{0}个字符")
            },
            'imageDatum.media': {
                required: "请输入介质",
                maxlength: jQuery.format("介质不能大于{0}个字符")
            },
            'imageDatum.stamp': {
                required: "请选择时间"
            },
            'imageDatum.duration': {
                required: "请输入时常",
                number: "请输入数字"
            }
        }
    });

    $("#image-datum-submit").click(function () {
        if ($("#imageDatum_from").valid()) {
            var params = $("#imageDatum_from").serialize();
            params = decodeURIComponent(params, true);
            var url = "archives/imageDatum/save.json?" + params;
            var repairRecordId = $("#repairRecordId").val();
            var $imageDatumInput = $("#image_datum_input");
            var $imageDatumInputHelp = $("#image_datum_input_help");

            var file = $imageDatumInput.val();
            if (file != "") {
                if (!/.(avi|mp4|mkv|rm|rmvb|wmv)$/.test(file.toLowerCase())) {
                    helpError($imageDatumInputHelp, "文件类型必须是avi、mp4、mkv、rm、rmvb、wmv");
                    return;
                }
                $imageDatumInputHelp.empty();
            }

            $http.get(url).success(function (response) {
                $("#content").val("");
                $("#media").val("");
                $("#duration").val("");
                $("#image_datum_stamp").val("");
                if (response != 0) {
                    $("#image_datum_id").val(response);
                    if (typeof(file) != "undefined") {
                        $('#image_datum_upload_form').ajaxSubmit({
                            dataType: 'json',
                            success: function (result) {
                                $("#imageInput").val("");
                                $scope.imageDatumFun();
                                window.pnotify("添加成功", "info");
                            }
                        });
                    }

                }

            });
        }
    });
});