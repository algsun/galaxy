$(function () {
//全选
    $("#all").change(function () {
        var checked = $(this).is(":checked");
        var doCheck = function ($checkboxes, checked) {
            $checkboxes.each(function () {
                if (checked) {
                    $(this).attr('checked', 'checked');
                } else {
                    $(this).removeAttr('checked');
                }
            });
        };
        var $pro = $("#target input[name='sensor']");
        doCheck($pro, checked);
    });

    $("#add").click(
        function () {
            var addSize = 0;
            var checkSensor = "";
            var sensorId = "";
            var sensorValue = "";
            var appendParams = function ($checkboxes) {
                $checkboxes.each(function () {
                    checkSensor += $(this).attr('value') + ",";
                    sensorValue += $(this).attr('id') + ",";
                    sensorId += $(this).attr('title') + ",";
                    addSize++;
                });
            };
            var sen = $("#target input[name='sensor']:checked");
            appendParams(sen);
            if (checkSensor == "") {
                art.dialog({
                    id: "mess",
                    fixed: true,
                    title: message.tips,
                    content: message.atLeastOne,
                    okValue: message.ok,
                    ok: function () {
                    },
                    cancelValue: message.cancel,
                    cancel: function () {
                    }
                });
                return;
            } else {
                var dataTable = $("#dataTable");
                var appStr = '<tr><td>' + message.sensor + '</td><td>' + message.conditionType
                    + '</td><td>' + message.targetValue + '</td><td>' + message.floatingValue
                    + '</td><td>' + message.operation + '</td></tr>';
                checkSensor = checkSensor.substring(0, (checkSensor.length - 1));
                sensorId = sensorId.substring(0, (sensorId.length - 1));
                sensorValue = sensorValue.substring(0, (sensorValue.length - 1));
                var cnNameUnits = checkSensor.split(",");
                var id = sensorId.split(",");
                var cnName = sensorValue.split(",")
                var appData = "";

                //获取tabel的行数
                //var currentNum = $("#thresholdSize").val();
                var trSize = $("#dataTable").find("tr").length - 1;
                if (trSize < 0) {
                    trSize = 0
                }
                for (var i = trSize; i < trSize + addSize; i++) {
                    appData += '<tr id="' + id[i - trSize] + '">' +
                    '<td>' + cnNameUnits[i - trSize] + '</td>' +
                    '<td><select class = "conditionType" style="width: 100px;" id="' + id[i - trSize] + 'conditionType" name="maps[' + i + '].conditionType">' +
                    '<option value="1">' + message.range + '</option><option value="2">' + message.greaterThan +
                    '</option><option value="3">' + message.lessThan + '</option>' +
                    '<option  value="4">' + message.greaterEqual + '</option><option  value="5">' + message.lessThan +
                    '</option><option value="6">' + message.equalTo + '</option></select></td>' +
                    '<td><input type="hidden" name="maps[' + i + '].sensorPhysicalId" value="' + id[i - trSize] + '">' +
                    '<input type="hidden" name="maps[' + i + '].cnName" value="' + cnName[i - trSize] + '">' +
                    '<input class="target" style="width: 100px;" type="text" id="' + id[i - trSize] + 'target" name="maps[' + i + '].target" >' +
                    '<span id="' + id[i - trSize] + 'targetMsg" class="inline" style="color:red;"></span></td>' +
                    '<td><input class="floating" style="width: 100px;" type="text" id="' + id[i - trSize] + 'floating" name="maps[' + i + '].floating" ></td>' +
                    '<span id="' + id[i - trSize] + 'floatingMsg" class="inline" style="color:red;"></span></td>' +
                    '<td> <span  class="btn btn-mini btn-danger" ' +
                    'onclick="deleteThreshold(' + id[i - trSize] + ',0,0)"><i class="icon-trash icon-white"></i> ' + message.delete + '</span></td></tr>';
                    //currentNum++;
                }
                var content = $(dataTable).find("tr");
                if (content.length <= 0) {
                    dataTable.append(appStr);
                    dataTable.append(appData);
                } else {
                    dataTable.append(appData);
                }
                //选择完监控指标后删除相应的checkbox
                var doHide = function ($checkboxes) {
                    $checkboxes.each(function () {
                        $(this).parent().hide();
                    });
                    $("#target input[name='sensor']").removeAttr("checked")
                    var targetLabel = $("#target").find("label").filter(':visible');
                    if (targetLabel.length <= 0) {
                        $("#chooseTarget").hide();
                    }
                };
                var $sensor = $("#target input[name='sensor']:checked");
                doHide($sensor);
            }
        }
    );
    $(".conditionType").live("change", function () {
        var $selectedValue = $(this).val().trim();
        var $floatTextInput = $($(this).parent().next().next().children()[0]);
        if ($selectedValue == 1) {
            $floatTextInput.removeAttr("disabled");
        } else {
            $floatTextInput.val("");
            $floatTextInput.attr("disabled", "disabled");
        }
    });

    $(".target,.floating").live("focus", function () {
        var msg = "#" + $(this).attr('id') + "Msg";
        $(msg).show().text('');
    });
    flag = false;
//点击保存按钮
    $("#threshold-submit").click(function () {
        var trSize = $("#dataTable").find("tr");
        if (trSize.length <= 1) {
            alertMsg(message.selectSensor);
            return;
        }
        addThresholdCheck();
        if (!checkTargetAndFloatInput()) {
            return;
        }
        if (flag) {
            var data = $("#thresholdData").serialize();
            var url = "saveThreshold.action";
            var zoneId = $("#zoneId").val();
            $.ajax({
                type: "post",
                url: url,
                data: data,
                dataType: 'json',
                success: function (msg) {
                    if (msg.flag) {
                        alertMsg(message.setThresholdSuccess);
                    } else {
                        alertMsg(message.setThresholdFailed);
                    }
                }
            });
        }
    });
});
var alertMsg = function (msg) {
    art.dialog({
        id: "mess",
        fixed: true,
        title: message.tips,
        content: msg,
        okValue: message.ok,
        ok: function () {
        },
        cancelValue: message.cancel,
        cancel: function () {
        }
    });
};

$("#deleteAll").click(function () {
    var zoneId = $("#zoneId").val();
    var trSize = $("#dataTable").find("tr");
    if (trSize.length <= 1) {
        art.dialog({
            id: "mess",
            fixed: true,
            title: message.tips,
            content: message.pleaseSetThreshold,
            okValue: message.ok,
            ok: function () {

            },
            cancelValue: message.cancel,
            cancel: function () {
            }
        });
    } else {
        $.get("zone/findZoneThreshold/" + zoneId, function (result) {
            if (result) {
                art.dialog({
                    id: "mess",
                    fixed: true,
                    title: message.tips,
                    content: message.sureToDeleteThreshold,
                    okValue: message.ok,
                    ok: function () {
                        location.href = "zone/deleteAllThreshold/" + zoneId;
                    },
                    cancelValue: message.cancel,
                    cancel: function () {
                    }
                });
            } else {
                art.dialog({
                    id: "mess",
                    fixed: true,
                    title: message.tips,
                    content: message.saveThenDelete,
                    okValue: message.ok,
                    ok: function () {
                    },
                    cancelValue: message.cancel,
                    cancel: function () {
                    }
                });
            }
        });
    }
});

//点击删除按钮
function deleteThreshold(id, name, nameUnits) {
    var deleteId = "#" + id;
    var trSize = $("#dataTable").find("tr");
    if (trSize.length <= 2) {
        art.dialog({
            id: "mess",
            fixed: true,
            title: message.tips,
            content: message.deleteThresholdFailed,
            okValue: message.ok,
            ok: function () {
            },
            cancelValue: message.cancel,
            cancel: function () {
            }
        });
    } else {
        $(deleteId).remove();
        $("#chooseTarget").show();
        $("#all").removeAttr("checked");
        var $targetObj = $("#target input[title=" + id + "]");
        if ($targetObj.length == 1) {
            $targetObj.removeAttr("checked");
            $targetObj.parent().show();
        } else {
            var sensor = "<label class='checkbox inline '><input type='checkbox' name='sensor' title='" + id + "'" +
                " id='" + name + "' value='" + nameUnits + "'>" + name + "</label>";
            $("#target").append(sensor);
        }
    }
}

var check = function ($input) {
    var msg = "#" + $input.attr('id') + "Msg";
    $(msg).show().text(message.notEmpty);
    $("#" + $input.attr('id')).bind('focus', function () {
        $(msg).text("").hide();
    })
};

var checkTargetAndFloatInput = function () {
    var isError = true;
    $(".target,.floating").each(function () {
        var value = $(this).val().trim();
        var attr = $(this).attr("disabled");
        var msg = "#" + $(this).attr('id') + "Msg";
        if (attr != "disabled" && value == "") {
            $(msg).show().text(message.notEmpty);
            isError = false;
        }
        if (attr != "disabled" && isNaN(value)) {
            $(msg).show().text(message.inputNumber);
            isError = false;
        }
    });
    return isError;
};

//添加验证
function addThresholdCheck() {
    var spanFlag = false;
    var $span = $("#thresholdInfo span[class='inline']");
    $span.each(function (i, itemSpan) {
        var $item = $(itemSpan);
        if ($item.text() != "") {
            spanFlag = true;
        }
    });

    if (!flag) {
        flag = true;
    }

    //var chk_value = [];
    //$('input[name="alarmTypes"]').each(function () {
    //    chk_value.push($(this).val());
    //});
    //if ($("#users").val() == null) {
    //    $usersMsgEnable.text(message.notifierCannotEmpty);
    //} else if (spanFlag) {
    //} else if ($("#measureIds").val() == null) {
    //    $decisionMsgEnable.text(message.selectMeasures);
    //} else if (chk_value.length == 0) {
    //    $alarmTypeMsgEnable.text(message.alarmTypeCannotEmpty);
    //} else {
    //    flag = true;
    //}
}