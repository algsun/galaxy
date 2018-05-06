/*
 * @author xie.deng
 * @date 2014-6-4
 */
function editRelicLabel(relicId, availableLabels) {
    //点击添加标签
    var $relicTag = $("#relicTagText");
    $relicTag.live('keydown', function (e) {
        if (e.keyCode == 13) {
            var relicTagValue = $(this).val().trim();
            if (relicTagValue === "" || relicTagValue == undefined) {
                return;
            } else {
                $.getJSON("addRelicLabel.action", { labelName: relicTagValue, relicId: relicId},
                    function (data) {
                        if (data.Success == true) {
                            $relicTag.val("").before("<button class='btn btn-small m-r-20 m-t-10 m-b-10' name='" + relicTagValue +
                                "' type='button'>" + relicTagValue + "<i data-action-id='1' data-action-type='0'class='icon-remove'></i>" +
                                "</button>");
                            //自动补全刷新内容
                            changeAvailableTags(data);
                        } else {
                            alertMsg("标签已经存在");
                        }
                    });

            }
        }
    });

    //警告提示
    var alertMsg = function (msg) {
        var divMsg = "<div class='alert'>" + "<a class='close' data-dismiss='alert' >×</a>" + msg + "</div>";
        $("#alert").empty().append(divMsg);
    }

    //自动补全
    var availableTags = availableLabels;
    $relicTag.autocomplete({
        source: availableTags
    });

    //点击删除标签
    var $removeButton = $(".icon-remove");
    $removeButton.live('click', function (e) {
        if(e.stopPropagation){
            e.stopPropagation();
            var currentButton = $(this).parent();
            var labelValue = currentButton.attr("name");
            $.getJSON("deleteRelicLabel.action", { labelName: labelValue, relicId: relicId},
                function (data) {
                    if (data.Success == true) {
                        currentButton.remove();
                        //自动补全刷新内容
                        changeAvailableTags(data);
                    } else {
                        alertMsg("删除标签失败");
                    }
                });
        }else{
            return false;
        }
    });

    $(".btn.btn-small.m-r-20.m-t-10.m-b-10").live('click', function () {
        var value = $(this).attr("name");
        window.location.href = "toRelicList.action?labelName=" + value;
    });

    //自动补全刷新内容
    function changeAvailableTags(data) {
        availableTags.splice(0, availableTags.length);
        for (var i = 0; i < data.allLabels.length; i++) {
            availableTags.push(data.allLabels[i]);
        }
    }

    //点击自定义标签 编辑本段
    $("#editRelicLabel").click(function () {
        $relicTag.toggle().focus();
    });
};