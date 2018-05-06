$(function () {
    $("#addMeasure").click(function () {
        $("#addMeasureFrom").attr("action", "alarm/doAddMeasure");
        $("#fromTitle").text(message.addMeasure);
        $("#description").val("");
        $("#measureId").removeAttr("name");
        $("#measureDescSpan").text("");
        $('#addFrom').modal('show');
    });

    $("#saveBtn").click(function () {
        var description = $("#description").val().trim();
        if (description == null || description == "") {
            $("#measureDescSpan").text(message.inputMeasureDescription)
            return;
        } else {
            $("#measureDescSpan").text("");
        }
        $("#addMeasureFrom").submit();
    });

    $("#returnBtn").click(function () {
        $('#addFrom').modal('hide');
    });

    $(".updateMeasure").click(function () {
        $("#fromTitle").text(message.editMeasure);
        $("#measureDescSpan").text("");
        $("#measureId").attr("name", "measureId");
        var measureId = this.dataset.id;

        $("#addMeasureFrom").attr("action", "alarm/doUpdateMeasure");
        $.get("alarm/toUpdateMeasure/" + measureId, function (result) {
            $("#measureId").val(result.id);
            $("#description").val(result.description);
            $('#addFrom').modal('show');
        });
    });

    $(".deleteMeasure").click(function () {
        var measureId = this.dataset.id;
        art.dialog({
            id: "delete",
            fixed: true,
            title: message.tips,
            content: message.sureToDelete,
            okValue: message.ok,
            ok: function () {
                window.location.href = "alarm/deleteMeasure/" + measureId;
            },
            cancelValue: message.cancel,
            cancel: function () {
            }
        })
    });
});