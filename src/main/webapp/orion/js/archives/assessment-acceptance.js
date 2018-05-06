$(function () {
    $("#assessment_submit_id").click(function () {
        var selfAssessment = $("#selfAssessment").val().trim();
        var assessmentStamp = $("#assessmentStamp").val().trim();
        if (selfAssessment == "") {
            art.dialog({
                id: "delete",
                fixed: true,
                title: "信息提示",
                content: "请输入自评估...",
                okValue: "确定",
                width: '30%',
                ok: function () {
                },
                cancelValue: "取消",
                cancel: function () {
                }
            })
        } else if (assessmentStamp == "") {
            art.dialog({
                id: "delete",
                fixed: true,
                title: "信息提示",
                content: "请选择时间...",
                okValue: "确定",
                ok: function () {
                },
                cancelValue: "取消",
                cancel: function () {
                }
            })
        } else {
            var param = $("#repair_assessment_form").serialize();
            var url = "archives/assessment/save.json?" + param;
            $.get(url,function(result){
                if(result){
                    art.dialog({
                        id: "delete",
                        fixed: true,
                        title: "信息提示",
                        content: "添加成功",
                        okValue: "确定",
                        ok: function () {
                        },
                        cancelValue: "取消",
                        cancel: function () {
                        }
                    })
                }
            });
//            $("#repair_assessment_form").submit();
        }
    });

    $("#no_pass_form").validate({
        rules: {
            'notByReason': {
                required: true,
                maxlength: 50
            }
        },
        messages: {
            'notByReason': {
                required: "请输入不通过原因",
                maxlength: jQuery.format("图纸类型不能大于{0}个字符")
            }
        },
        errorPlacement: function (error, element) {
            $('<br/>').appendTo(element.parent());
            error.appendTo(element.parent());
        }
    });

    $("#pass_form_submit").click(function () {
        var repairRecordId = $("#pass_form_repairRecordId").val();
        $.get("handleTask/passBeforeCheck.json", {"repairRecord.id": repairRecordId}, function (result) {
            if (!result) {
                $("#pass_form").submit();
            } else {
                window.pnotify("审核失败，请检查相关字段是否已填写!", "error");
            }
        })
    });
});