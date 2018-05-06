/**
 * 控制模块动作
 *
 * @author xu.yuexi
 * @date 2014-3-21
 * @dependency jQuery
 */


$(function () {
    var logic = -1;
    jQuery.validator.addMethod("selectSubNode", function (value, element) {
        return this.optional(element) || value != -1;
    }, message.selectChildNode);

    jQuery.validator.addMethod("moreThan", function (value, element, param) {
        return parseFloat(value) > parseFloat($(param).val());
    }, message.lowGreaterThanHigh);

    //二级联动
    $("body").on("change", "[name='subNodeId']", function () {
        var $this = $(this);
        var $subDeviceId = $this.val();
        var $subFisrst = $("[name='subNodeId'] :first-child");
        if ($subFisrst.val() == -1) {
            $subFisrst.hide();
        }
        if ($subDeviceId == -2) {
            $.post("control-panel/" + $subDeviceId + "/allSensors", function (result) {
                if (result.success) {
                    var sensorList = result.allSensorinfoVOs;
                    var options = "";
                    if (sensorList.length > 0) {
                        for (var i = 0; i < sensorList.length; i++) {
                            options += "<option value=" + sensorList[i].sensorPhysicalid + ">" + sensorList[i].cnName + "</option>";
                        }
                    } else {
                        options += "<option>" + message.noData + "</option>";
                    }
                    $this.parent().parent().next().find("select").html(options);
                }
            });
        } else {
            $.post("control-panel/" + $subDeviceId + "/sensors", function (result) {
                if (result.success) {
                    var sensorList = result.sensorinfo;
                    var options = "";
                    if (sensorList.length > 0) {
                        for (var i = 0; i < sensorList.length; i++) {
                            options += "<option value=" + sensorList[i].sensorPhysicalid + ">" + sensorList[i].cnName + "</option>";
                        }
                    } else {
                        options += "<option>" + message.noData + "</option>";
                    }
                    $this.parent().parent().next().find("select").html(options);
                }
            });
        }
    });

    $("body").on("change", "#type", function () {
        var type = $("#type").val();
        if (type == 2) {
            $("#interval").show();
            $("#execute").show();
            $("#daily").hide();
        } else {
            $("#interval").hide();
            $("#execute").hide();
            $("#daily").show();
        }

    });

    var myDialog = null;
    var addAction = null;
    var addCondition = null;
    var originValueDialog = null;
    $("a[name='config']").click(function () {
        var deviceId = $("#deviceId").val();
        var route = $(this).data("route");
        $("#configRoute").val(route);
        var config = $("#configFl").html();

        myDialog = art.dialog({
            id: 'id-myDialog',
            title: message.setConditionalReflection,
            content: config,
            okValue: message.ok
        });


        $("#configForm").validate({
            rules: {
                subNodeId: "selectSubNode",
                low: {
                    required: true,
                    number: true
                },
                high: {
                    required: true,
                    moreThan: "#low",
                    number: true
                }
            }
        });

        $("body").on("click", "#btnCancel", function () {
            myDialog.close();
        });
    });

    $("#originBtn").click(function () {
        var config = $("#originValueDialog").html();

        originValueDialog = art.dialog({
            id: 'id-originValue',
            title: message.calculateOriginalValue,
            content: config,
            okValue: message.calculate,
            cancelValue: message.cancel,
            ok: function () {
                var deviceId = $("#originDevice").val();
                var sensorId = $("#originSensor").val();
                var originValue = $("#originValue").val();
                $.post("control-panel/" + deviceId + "/originValue", {
                    sensorId: sensorId,
                    originValue: originValue
                }, function (result) {
                    if (result.success) {
                        $("#origin").val(result.origin);
                        $("#originRight").val(result.originRight);
                        $("#originLeft").val(result.originLeft);
                        $("#originRightDiv").show();
                        $("#originLeftDiv").show();
                        $("#originDiv").show();
                    } else {
                        window.pnotify(message.calculationError, "warn");
                        return true;
                    }
                });
                return false;
            },
            cancel: function () {
            }
        });


        $("#originValueForm").validate({
            rules: {
                initValue: {
                    required: true,
                    number: true
                }
            }
        });

    });
    $("body").on("change", "#switchAction", function () {
        var value = $(this).val();
        if (value == 3 || value == 4) {
            $("#threshold").show();
            $("#lowThreshold").hide();
            $("#highThreshold").hide();
            $("#subNodeDiv").show();
            $("#sensorDiv").show();
        } else if (value == 8 || value == 0 || value == 7) {
            $("#lowThreshold").hide();
            $("#highThreshold").hide();
            $("#threshold").hide();
            $("#subNodeDiv").hide();
            $("#sensorDiv").hide();
        } else {
            $("#threshold").hide();
            $("#lowThreshold").show();
            $("#highThreshold").show();
            $("#subNodeDiv").show();
            $("#sensorDiv").show();
        }
    });

    $("[name='addAction']").click(function () {
        var deviceId = $("#deviceId").val();
        var route = $(this).data("route");
        $("#actionRoute").attr('value', route);
        var add = $("#addActionDialog").html();
        addAction = art.dialog({
            id: 'id-addAction',
            title: message.setAction,
            content: add
        });


        $("#actionForm").validate();

        $("body").on("click", "#actionCancel", function () {
            addAction.close();
        });
    });

    $("[name='editCondition']").click(function () {
        var deviceId = $("#deviceId").val();
        var route = $(this).data("route");
        $("#editRoute").attr('value', route);
        var edit = $("#editConditionDialog").html();
        var editDialog = art.dialog({
            id: 'id-editDialog',
            title: message.editAction,
            content: edit
        });
        $("body").on("click", "#editSubmit", function () {
            return confirm(message.confirmChanges);
        });
        $("body").on("click", "#editCancel", function () {
            editDialog.close();
        });

    });

    $(".icon-remove").click(function () {
        var deviceId = $("#deviceId").val();
        var $this = $(this);
        var actionId = $this.data("action-id");
        var type = $this.data("action-type");
        var r = confirm(message.confirmDeletion);
        if (r == true) {
            if (type >= 1) {
                $.post("control-panel/" + actionId + "/deleteActions", {
                    type: type,
                    deviceId: deviceId
                }, function (result) {
                    var $parent = $this.parent();
                    if (result.success) {
                        //如果前面是span后面是按钮表示本行唯一的数据被删除
                        if ($parent.prev().get(0).tagName == "SPAN"
                            && ($parent.next().get(0).tagName == "A")) {
                            //显示被隐藏的按钮
                            $parent.next().next().show();
                            if (type == 2) {
                                $parent.next().show();
                            }
                        }
                        $parent.remove();
                    } else {
                        window.pnotify(message.deleteFailed, "warn");
                    }
                });
            } else {
                $.post("control-panel/" + actionId + "/deleteConditions", {deviceId: deviceId}, function (result) {
                    if (result.success) {
                        var $next = $this.parent().next();
                        //本行唯一的数据被删除
                        if (result.unique) {
                            //显示被隐藏的按钮
                            $next.next().show();
                            //隐藏编辑按钮
                            $this.parent().parent().find("[name='editCondition']").hide();
                            //删除后面的字
                            $next.remove();
                            //修复ajax时addCondition不跟着联动 的问题
                            logic = 0;
                        } else if ($next.text() == message.on || $next.text() == message.off) {
                            //是最后一条数据
                            //删除前面的字
                            $this.parent().prev().remove();
                        } else {
                            //删除后面的字
                            $next.remove();
                        }
                        $this.parent().remove();
                    } else {
                        window.pnotify(message.deleteFailed, "warn");
                    }
                });

            }
        }
        else {
            return false;
        }

    });

    $("body").on("change", "#actionRoute", function () {
        $("#actionRoute :first-child").hide();
        var route = $(this).val();
        var deviceId = $("#deviceId").val();
        $.post("control-panel/" + deviceId + "/select", {actionRoute: route}, function (result) {
            if (result.success) {
                if (result.hasInterval) {
                    alert("#" + route + message.canNotAdd);
                    addAction.close();
                }
                if (result.hasDaily) {
                    var options = "<option value='1'>" + message.dailyRegularExecution + "</option>";
                    $("#type").html(options);
                }

            }
        });
    });

    $("[name='addCondition']").click(function () {
        var deviceId = $("#deviceId").val();
        var route = $(this).data("route");
        $("#conditionRoute").attr('value', route);
        var $conditionAction = $("#conditionAction");
        //修复ajax时addCondition不跟着联动 的问题
        if (logic < 0) {
            logic = $(this).data("logic");
        }
        if (logic == route) {
            //逻辑选择部分被隐藏时，添加-1到第一行，传到后台
            $("#logic").prepend("<option value='-1'>" + message.pleaseSelect + "</option>");
            $conditionAction.hide();
            $conditionAction.next().hide();
        } else {
            $conditionAction.show();
            $conditionAction.next().show();
            //移除值为-1的选项
            $("#logic option[value='-1']").remove();
        }

        var add = $("#addConditionDialog").html();
        addCondition = art.dialog({
            id: 'id-addCondition',
            title: message.setCondition,
            content: add
        });
        $("#conditionForm").validate({
            rules: {
                subNodeId: "selectSubNode",
                conditionValue: {
                    required: true,
                    number: true
                }
            }
        });

        $("#conditionForm").validate();

        $("body").on("click", "#conditionCancel", function () {
            addCondition.close();
        });
    });

});
