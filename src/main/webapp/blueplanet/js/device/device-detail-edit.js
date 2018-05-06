/**
 * 设备编辑
 *
 * @author gaohui
 * @date 2013-02-26
 * @check @wang yunlong 2013-02-26 #1777
 */
$(function () {
    // 选择区域
    (function () {
        // ztree 树配置
        var setting = {
            view: {
                showLine: false
            },
            async: {
                enable: true,
                url: '../blackhole/zone/children.json',
                autoParam: ["id=zoneId"]
            }
        };

        var showDialog = function ($zoneInput, zoneTree) {
            var $help = $("#zoneTreeDialog .help-block");
            art.dialog({
                id: "zoneTreeDialog",
                title: message.selectZone,
                content: $("#zoneTreeDialog")[0],
                fixed: true,
                okValue: message.ok,
                ok: function () {
                    var nodes = zoneTree.getSelectedNodes();
                    if (nodes.length == 0) {
                        $help.empty().append(message.pleaseSelect);
                        return false;
                    }
                    var node = nodes[0];
                    $zoneInput.val(node.name);
                    $("#zoneIdInput").val(node.id);
                },
                cancelValue: message.cancel,
                cancel: function () {
                    $help.empty();
                }
            });
        };

        // 区域输入框获取焦点时
        $("#parentZoneInput").focus(function () {
            var $this = $(this);
            $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
                // 初始化树
                $.fn.zTree.init($('#zoneTree'), setting, result);
                var zoneTree = $.fn.zTree.getZTreeObj("zoneTree");

                // 初始化弹出框
                showDialog($this, zoneTree);
            });
        });

    })();

    (function () {

        //给interval下拉框赋值
        var opValue = [6, 30, 60, 120, 300, 600, 900, 1200, 1800, 3600];
        var opText = ['6秒', '30秒', '1分钟', '2分钟', '5分钟', '10分钟', '15分钟', '20分钟', '30分钟', '60分钟'];
        var idx;
        var $intervalValue = parseInt($("#intervalValue").val());
        var $deviceWorkInterval = $('#deviceWorkInterval').val();
        var flag = ((opValue.indexOf($intervalValue) == -1) ? true : false);
        if (flag) {
            opValue.push($intervalValue);
            opValue.sort(function (a, b) {
                return a - b;
            });
            idx = opValue.indexOf($intervalValue);
            opText.splice(idx, 0, $deviceWorkInterval);
        }
        var options = [];
        for (var i = 0; i < opValue.length; i++) {
            if (opValue[i] > $("#warmUp").val()) {
                if (opValue[i] == $intervalValue) {
                    options.push("<option value=" + opValue[i] + " selected='selected'>" + opText[i] + "</option>");
                } else {
                    options.push("<option value=" + opValue[i] + ">" + opText[i] + "</option>");
                }
            }
        }
        for (var i = 0; i < options.length; i++) {
            $("#interval").append(options[i]);
        }

        //判断工作周期是否改变
        var selector = document.getElementById('interval');
        var index;
        if (selector != null) {
            index = selector.selectedIndex;
        }
        var button = document.getElementById('updateSubmitButton');
        button.onclick = function () {
            var _index;
            if (selector != null) {
                _index = selector.selectedIndex;
            }
            if (_index !== index) {
                index = _index;
                $("#intervalChange").attr("value", "true");
            } else {
                $("#intervalChange").attr("value", "false");
            }
        };
        // 表单验证
        $("#updateSubmitButton").click(function () {
            //电压阈值验证
            var $voltageThreshold = $("#voltageThreshold");
            var voltageThresholdValue = $voltageThreshold.val();
            if (isNaN(voltageThresholdValue)) {
                $voltageThreshold.next().next().empty().text("电压阈值必须为数字");
                return false;
            } else if (voltageThresholdValue <= 0 && voltageThresholdValue.trim().length > 0) {
                $voltageThreshold.next().next().empty().text("电压阈值必须大于0");
                return false;
            } else {
            }
        });
    })();


    // 保存自定义公式系数, 恢复默认自定义公系数
    (function () {
        // 恢复默认公式系数
        var resetToDefault = function ($ol) {
            $ol.children().each(function () {
                var $li = $(this);
                var $input = $li.find("input");
                $input.val($input.attr("data-default"));
                $("span", $li).remove();
            });
        };

        // 自定义公式系数
        var setCustom = function ($ol, deviceId, sensorPhysicalId) {
            $ol.children().each(function () {
                var $li = $(this);
                var $input = $li.find("input");
                $input.next().remove();
                $li.append("<span class='muted'>（" + message.default + "：" + $input.attr('data-default') + "）</span>");
            });

            // "恢复默认" 按钮
            var resetButton = [
                '<button class="btn btn-mini" ',
                'data-device-id="', deviceId, '" ',
                'data-sensorPhysicalId="', sensorPhysicalId, '" ',
                'data-button="reset-coefficient">' + message.restore,
                '</button>'].join('');
            $ol.parent().children("[data-button='reset-coefficient']").remove();
            $ol.parent().append(resetButton);
        };


        var numberRE = /^-?([1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/;

        // 保存自定义公式系数
        $(document).on("click", "button[data-button='custom-coefficient']", function () {
            var $this = $(this);
            var deviceId = $this.attr("data-device-id");
            var sensorPhysicalId = $this.attr("data-sensorPhysicalId");
            var params = {};
            var inputInvalid = false;
            $this.siblings("ol").children().each(function () {
                var $input = $(this).find("input");
                var name = "params." + $input.attr("data-coefficient-name");
                var value = $input.val();
                if (!numberRE.test(value)) {
                    inputInvalid = true;
                    art.dialog({title: message.tips, content: message.numberInputError});
                    return;
                }
                params[name] = value;
            });

            // 如果输入有误, 直返回
            if (inputInvalid) {
                return;
            }

            var url = "device/" + deviceId + "/sensorinfo/" + sensorPhysicalId + ".json";
            $.get(url, params, function (result) {
                if (result.success) {
                    window.pnotify(message.saveFormulaSuccess, "success");
                    setCustom($this.siblings("ol"), deviceId, sensorPhysicalId);
                } else {
                    window.pnotify(message.saveFormulaFailed, "warn");
                }
            });
        });


        // 恢复默认自定义系数
        $(document).on("click", "button[data-button='reset-coefficient']", function () {
            var $this = $(this);
            var deviceId = $this.attr("data-device-id");
            var sensorPhysicalId = $this.attr("data-sensorPhysicalId");
            var url = "device/" + deviceId + "/sensorinfo/" + sensorPhysicalId + ".json?reset";
            $.getJSON(url, function (result) {
                if (result.success) {
                    window.pnotify(message.restoreFormulaSuccess, "success");
                    resetToDefault($this.siblings("ol"));
                    $this.remove();
                } else {
                    window.pnotify(message.restoreFormulaFailed, "warn");
                }
            });
        });

        //保存自定义浮动值
        $(document).on("click", "button[data-button='custom-floatValue']", function () {
            var $this = $(this);
            var deviceId = $this.attr("data-deviceId");
            var sensorId = $this.attr("data-sensorId");
            var maxUpFloat = $("#maxUpFloat" + deviceId + sensorId).val();
            var minDownFloat = $("#minDownFloat" + deviceId + sensorId).val();
            var minUpFloat = $("#minUpFloat" + deviceId + sensorId).val();
            var zero = $("#minUpFloat" + deviceId + sensorId).data("zero");

            //上限浮动值和下限向上浮动值必须大于零
            if ("dataZero" != zero) {
                if ((!numberRE.test(maxUpFloat)) || (!numberRE.test(minDownFloat))) {
                    art.dialog({title: message.tips, content: message.numberInputError});
                    return;
                }
            } else {
                if ((!numberRE.test(maxUpFloat)) || (!numberRE.test(minDownFloat)) || (!numberRE.test(minUpFloat))) {
                    art.dialog({title: message.tips, content: message.numberInputError});
                    return;
                }
            }
            $("#maxUpFloatSpan" + deviceId + sensorId).text("");
            $("#minUpFloatSpan" + deviceId + sensorId).text("");
            if (maxUpFloat < 0) {
                $("#maxUpFloatSpan" + deviceId + sensorId).text("向上浮动值不能为负数");
                return;
            }
            if (minUpFloat < 0) {
                $("#minUpFloatSpan" + deviceId + sensorId).text("向上浮动值不能为负数");
                return;
            }


            //提交
            $.get("device/customFloatValue", {
                'floatValue.deviceId': deviceId,
                'floatValue.sensorId': sensorId,
                'floatValue.maxUpFloat': maxUpFloat,
                'floatValue.minDownFloat': minDownFloat,
                'floatValue.minUpFloat': minUpFloat
            }, function (result) {
                if (result) {
                    window.pnotify("浮动值设置成功", "success")
                    var resetButton = [
                        '<button class="btn btn-mini" ',
                        'data-deviceId="', deviceId, '" ',
                        'data-sensorId="', sensorId, '" ',
                        'data-button="reset-floatValue">' + message.restore,
                        '</button>'].join('');
                    $this.parent().children("[data-button='reset-floatValue']").remove();
                    $this.parent().append(resetButton);
                } else {
                    window.pnotify("浮动值设置失败", "warn");
                }
            });
        });

        //浮动值恢复默认设置
        $(document).on("click", "button[data-button='reset-floatValue']", function () {
            var $this = $(this);
            var deviceId = $this.attr("data-deviceId");
            var sensorId = $this.attr("data-sensorId");
            $.get("device/resetFloatValue", {
                'floatValue.deviceId': deviceId,
                'floatValue.sensorId': sensorId
            }, function (result) {
                if (result.isReset) {
                    window.pnotify("浮动值恢复默认成功", "success");
                    $this.remove();
                    $("#maxUpFloat" + deviceId + sensorId).attr("value", result.floatValue.maxUpFloat);
                    $("#minDownFloat" + deviceId + sensorId).attr("value", result.floatValue.minDownFloat);
                    $("#minUpFloat" + deviceId + sensorId).attr("value", result.floatValue.minUpFloat);
                } else {
                    window.pnotify("浮动恢复默认失败", "warn");
                }
            });


        });

        // 全部恢复默认公式系数
        $(document).on("click", "button[data-button='reset-all-coefficient']", function () {
            var $this = $(this);
            var deviceId = $this.attr("data-device-id");
            $.getJSON("device/" + deviceId + "/sensorinfoes/reset-all.json", function (result) {
                if (result.success) {
                    window.pnotify(message.restoreAllFormulaSuccess, "success");
                    $this.prev().find('ol[data-params]').each(function () {
                        resetToDefault($(this));
                    });
                    $('button[data-button="reset-coefficient"]').remove();
                } else {
                    window.pnotify(message.restoreAllFormulaFailed, "warn");
                }
            });
        });
    })();
});
