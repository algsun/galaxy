$(function () {

    var isExist = true;
    var fileNames = [];
    var $imageInputHelp = $(".fileInputHelp");
    var code = $("#recode-code").val();
    // 文件上传
    $("#fileInput").change(function () {
        var $fileInput = $("#fileInput");
        var file = $fileInput.val();
        if (file.toString().trim() === "") {
            $imageInputHelp.empty();
            return;
        }

        var fileName = file.toString().substr(file.toString().lastIndexOf("\\") + 1, file.toString().length);

        $imageInputHelp.empty();
        $("#fileName").val(file.toString());
        $("#uploadForm").ajaxSubmit({
            dataType: 'json',
            success: function (result) {
                //拼接上传名称
                fileNames.push(result.pathName + "-;.~-" + result.fileName);
                $fileInput.val("");
                var len = $("table tr").length;
                var nameSplitArray = result.fileName.split(".");
                var type = nameSplitArray[nameSplitArray.length - 1];
                var src;
                if (type == "jpg" || type == "JPG" || type == "png" || type == "PNG") {
                    src = "jpg";
                } else if (type == "pdf") {
                    src = "pdf";
                } else if (type == "txt" || type == "TXT") {
                    src = "txt";
                } else if (type == "doc" || type == "docx") {
                    src = "word";
                } else if (type == "xls" || type == "xlsx") {
                    src = "excel";
                } else {
                    src = "unknow";
                }
                var today = new Date();
                var now = today.getFullYear() + "年" + (today.getMonth() + 1) + "月" + today.getDate() + "日";
                var addHtml = "<tr align='center'><td>" + len + "</td><td><div><img style='width:20px;height: 20px'" +
                    "src='images/" + src + ".png''>" +
                    result.fileName +
                    "</div></td><td>" + now + "</td><td><span class='green'>待确定</span></td></tr>";
                var length = $("tbody tr").length;
                if (length == 0) {
                    $("table").find("tbody").append(addHtml);
                } else {
                    $("table").find("tbody").last().append(addHtml);
                }
            }

        })

    });

    //校验用户输入档号是否存在，并联动案卷题名
    $(".digits").change(function () {
        var $this = $(this);
        var volumeCode = $("#volumeCode").val();
        var officialId = $("#officialId").data("id");
        var recordCode = officialId + "-" + volumeCode + $this.val();
        //用户输入档号和原始档号不一致时，开始校验联动
        if (code != $this.val()) {
            $.post("isRecordExist/" + recordCode, function (result) {
                $("#recordHelp").empty();
                if (!result.success) {
                    $("#recordHelp").empty().append("该档号已存在");
                    isExist = false;
                    return false;
                } else {
                    $.post("findVolume/" + volumeCode, function (result) {
                        if (result.success) {
                            $("#recordName").attr("value", result.success + $(".digits").val());
                        }
                    })
                    isExist = true;
                    return true;
                }
            });
        }


    });

    $("#addRecordBtn").click(function () {
        $("#attachement").val(fileNames);
        $("#addRecordForm").validate();
        if (isExist) {
            $("#addRecordForm").submit();
        }
    });

    //点击树，联动档号和案卷题名，并且修改form的卷号隐藏域值
    var volumeTree = App("cybertron").volumeTree;
    volumeTree.click(function (event, treeNode) {
        //主卷副卷备考卷下不能建档案
        if (treeNode.id > 9) {
            var volume = $("#volume").val();
            var officialId = $("#officialId").data("id");
            $("#officialId").data("volumeCode", treeNode.id);
            $("#officialId").html(officialId + "-" + treeNode.id);
            $.post("findVolume/" + treeNode.id, function (result) {
                if (result.success) {
                    $("#volumeCode").attr("value", treeNode.id);
                    $("#recordName").attr("value", result.success + $(".digits").val());
                }
            })
        }
    });

    //删除文件
    $(".deleteFile").click(function () {
        var $this = $(this);
        var attatchId = $this.data("attachment-id");
        $.post("deleteAttatch/" + attatchId, function (result) {
            if (result) {
                $this.parent().parent().remove();
            } else {
                window.pnotify.warn("删除文件失败");
            }
        });
    });


});