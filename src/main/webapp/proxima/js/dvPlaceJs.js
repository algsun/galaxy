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


    //计算两个时间的差值（单位：分钟）
    function getTimeDifference(time1, time2) {

        var min1 = parseInt(time1.substr(0, 2), 10) * 60 + parseInt(time1.substr(3, 2), 10);
        var min2 = parseInt(time2.substr(0, 2), 10) * 60 + parseInt(time2.substr(3, 2), 10);
        return min2 - min1;
    }

    var zoneCheck = null;
    (function () {
        var $zoneInput = $("#zone-input");
        var $zoneInputHelp = $("#zone-input-help");

        zoneCheck = function () {
            var zoneName = $.trim($zoneInput.val());
            if (zoneName == "") {
                helpError($zoneInputHelp, message.pleaseSelectZone);
                return false
            }

            helpOk($zoneInputHelp, "");
            return true;
        };

    })();

    //dvPlaceName check
    var dvPlaceNameCheck = null;
    var dvPlaceNameUniqueCheck = null;
    (function () {
        //摄像机点位名称是否唯一
        var isDVPlaceNameUnique = true;
        var $dvPlaceName_input = $("#dvPlaceName-input");
        var $dvPlaceName_help = $("#dvPlaceName-input-help");

        dvPlaceNameUniqueCheck = function () {
            if (!isDVPlaceNameUnique) {
                helpError($dvPlaceName_help, message.cameraPositionRepetition);
            }
            return isDVPlaceNameUnique;
        };

        dvPlaceNameCheck = function () {

            var dvPlaceName = $.trim($dvPlaceName_input.val());
            if (dvPlaceName == "") {
                helpError($dvPlaceName_help, message.positionNameRequired);
                return false;
            }

            helpOk($dvPlaceName_help, "");
            return true;
        };

        $dvPlaceName_input.focusout(function () {
            var checkResult = dvPlaceNameCheck();
            //如果通过基本检查
            if (checkResult) {
                //检查摄像机点位是否重名
                $.post('isNameUnique.action', {
                    zoneId: $("#zoneId").val(),
                    dvPlaceName: $dvPlaceName_input.val(),
                    dvPlaceId: $("#dvPlaceId").val()
                }, function (result) {
                    if (!result.unique) { //如果存在
                        helpError($dvPlaceName_help, message.cameraPositionRepetition);
                        isDVPlaceNameUnique = false;
                    } else {
                        isDVPlaceNameUnique = true;
                    }
                });
            }
        });

        $dvPlaceName_input.focusin(function () {
            helpClear($dvPlaceName_help);
            isDVPlaceNameUnique = false;
        });
    })();

    // FTP 验证
    var ftpValidate = true;
    var $ftpSelector = $("#ftp-select");
    var $ftpSelectorHelp = $("#ftp-select-help");
    var ftpCheck = function () {
        if ($ftpSelector.val() == "") {
            helpError($ftpSelectorHelp, message.pleaseSelectFTP);
            return false;
        }
        if ($ftpSelector.find("option").length == 0) {
            helpError($ftpSelectorHelp, message.initFtp);
            return false;
        }
        return true;
    };
    $ftpSelector.focusout(function () {
        ftpValidate = ftpCheck();
    });
    $ftpSelector.focusin(function () {
        helpClear($ftpSelectorHelp);
    });

    //摄像机IP验证
    var dvPlaceIpCheck = null;
    (function () {
        var dvPlaceIpRE = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\b$/;
        var $dvPlaceIp_input = $("#dvPlaceIp-input");
        var $dvPlaceIp_help = $("#dvPlaceIp-input-help");

        dvPlaceIpCheck = function () {

            var dvPlaceIp = $dvPlaceIp_input.val();
            if (dvPlaceIp == "") {
                helpError($dvPlaceIp_help, message.cameraIpRequired);
                return false;
            }

            if (!dvPlaceIpRE.test(dvPlaceIp)) {
                helpError($dvPlaceIp_help, message.cameraIPFormatError);
                return false;
            }

            helpOk($dvPlaceIp_help, "");
            return true;
        };

        $dvPlaceIp_input.focusout(function () {
            dvPlaceIpCheck();
        });

        $dvPlaceIp_input.focusin(function () {
            helpClear($dvPlaceIp_help);
        });
    })();

    //摄像机端口验证
    //dvPlaceFtpPort check
    var dvPortCheck = null;
    (function () {
        var dvPortRE = /^([1-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
        var $dvPort_input = $("#dvPort-input");
        var $dvPort_help = $("#dvPort-input-help");

        dvPortCheck = function () {

            var dvPort = $dvPort_input.val();
            if (dvPort == "") {
                helpError($dvPort_help, message.cameraPortRequired);
                return false;
            }

            if (!dvPortRE.test(dvPort)) {
                helpError($dvPort_help, message.cameraPortError);
                return false;
            }

            helpOk($dvPort_help, "");
            return true;
        };

        $dvPort_input.focusout(function () {
            dvPortCheck();
        });

        $dvPort_input.focusin(function () {
            helpClear($dvPort_help);
        });
    })();

    //IO模块IP验证
    var ioIpCheck = null;
    (function () {
        var ioIpRE = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\b$/;///^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;
        var $ioIp_input = $("#ioip-input");
        var $ioIp_help = $("#ioip-input-help");

        ioIpCheck = function () {

            var ioIp = $ioIp_input.val();
            if (ioIp == "") {
                helpError($ioIp_help, message.IOIPRequired);
                return false;
            }

            if (!ioIpRE.test(ioIp)) {
                helpError($ioIp_help, message.IOIPFormatError);
                return false;
            }

            helpOk($ioIp_help, "");
            return true;
        };

        $ioIp_input.focusout(function () {
            ioIpCheck();
        });

        $ioIp_input.focusin(function () {
            helpClear($ioIp_help);
        });
    })();

    //IO模块端口验证
    var ioPortCheck = null;
//    var ioPortUniqueCheck = null;
    (function () {
//        var isIOPortUnique = true;
        var ioPortRE = /^([1-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/;
        var $ioPort_input = $("#ioPort-input");
        var $ioPort_help = $("#ioPort-input-help");

        ioPortCheck = function () {

            var ioPort = $ioPort_input.val();
            if (ioPort == "") {
                helpError($ioPort_help, message.IOPortRequired);
                return false;
            }

            if (!ioPortRE.test(ioPort)) {
                helpError($ioPort_help, message.IOPortError);
                return false;
            }

            helpOk($ioPort_help, "");
            return true;
        };


        $ioPort_input.focusout(function () {
            var checkResult = ioPortCheck();
//            //如果通过基本检查
//            if (checkResult) {
//                //检查摄像机点位是否重名
//                $.post('isIOPortUsed.action', { dvPlaceId: $("#dvPlaceId").val(), ioPort: $ioPort_input.val()}, function (result) {
//                    if (result.used) { //如果存在
//                        helpError($ioPort_help, "端口号已被其它IO模块使用");
//                        isIOPortUnique = false;
//                    } else {
//                        isIOPortUnique = true;
//                    }
//                });
//            }
        });

        $ioPort_input.focusin(function () {
            helpClear($ioPort_help);
        });
    })();


    //根据外部控制是否选中来显示或隐藏拍照计划和IO模块录入
    (function () {
        var $remoteIo = $("#enable-external-io-display");
        var $photographSchedule = $("#enable-external-display");
        $("#lightOn-input").click(function () {
            if ($("#lightOn-input").attr("checked")) {
                $remoteIo.show();
                $photographSchedule.show();
            } else {
                $remoteIo.hide();
                $photographSchedule.hide();
            }
        });
    })();

    //根据是否开灯控制拍照前开灯时间和拍照后开灯时间是否显示
    (function () {
        //默认加载
        var $lightOn = $("#photograph_s_time");
        var $lightOff = $("#photograph_e_time");
        if ($("#isLightOn_input").attr("checked")) {
            $lightOn.show();
            $lightOff.show();
        } else {
            $lightOn.hide();
            $lightOff.hide();
        }

        $("#isLightOn_input").click(function () {
            var $lightOn = $("#photograph_s_time");
            var $lightOff = $("#photograph_e_time");
            if ($("#isLightOn_input").attr("checked")) {
                $lightOn.show();
                $lightOff.show();
            } else {
                $lightOn.hide();
                $lightOff.hide();
            }
        });
    })();


    //--------------------------------------------------------\\
    //根据外部控制是否选中来显示或隐藏拍照计划和IO模块录入
    (function () {
        var $remoteIo = $("#photograph_s_time");
        var $photographSchedule = $("#photograph_e_time");
        $("#isLightOn_input").click(function () {
            if ($("#isLightOn_input").attr("checked")) {
                $remoteIo.show();
                $photographSchedule.show();
            } else {
                $remoteIo.hide();
                $photographSchedule.hide();
            }
        });
    })();
    //-----------------------------------------------------------\\
    var showScheduleByType = function (type) {
    };
    (function () {
        showScheduleByType = function (type) {
            if (type == 'day') { //每天周期
                $(".data-everypoint").hide();
                $(".data-7day").hide();
                $(".data-7daypoint").hide();
                $(".data-everyday").show();
                return;
            }
            if (type == 'point') { //每天时间点
                $(".data-everyday").hide();
                $(".data-7day").hide();
                $(".data-7daypoint").hide();
                $(".data-everypoint").show();
                return;
            }
            if (type == '7day') { //七天周期
                $(".data-everyday").hide();
                $(".data-everypoint").hide();
                $(".data-7daypoint").hide();
                $(".data-7day").show();
                return;
            }
            if (type == '7day_point') { //七天时间点
                $(".data-everyday").hide();
                $(".data-everypoint").hide();
                $(".data-7day").hide();
                $(".data-7daypoint").show();
                return;
            }
        };
        $("input[name='radioType']").change(function () {
            showScheduleByType($(this).val());
        });
    })();

    //添加每天时间点输入框
    $("#add-data-point-button").click(function () {

        //判断是否达到12个时间点
        var $help = $("#everypoint-help");
        if ($("input[data-point-input]").length >= 12) {
            helpError($help, message.timePointLimit);
        } else {
            helpClear($help);
            var newInput = ['<div class="form-inline" style="margin-top:10px;">',
                '<label>时间点</label>',
                '<input type="text" data-point-input="0" onclick="WdatePicker({maxDate:\'%y-%M-%d\',dateFmt:\'HH:mm\'})">',
                '<span class="help-inline"><i class="icon-remove"></i></span>',
                '</div>'].join(' ');
            $(".data-everypoint").append(newInput);
        }
    });

    //添加7天时间点输入框
    $("button[data-7daypoint-button]").click(function () {
        //判断是星期几
        var whichDay = $(this).attr("data-7daypoint-button");

        //判断是否到12个时间点
        var $help = $("p[data-7daypoint-help]", $(this).parent().parent());
        if ($("input[data-7daypoint-input='" + whichDay + "']").length >= 12) {
            helpError($help, message.timePointLimit);
        } else {
            helpClear($help);
            var newInput = ['<div class="form-inline" style="margin-top:10px;">',
                '<label>时间点</label>',
                '<input type="text" data-7daypoint-input onclick="WdatePicker({maxDate:\'%y-%M-%d\',dateFmt:\'HH:mm\'})">',
                '<span class="help-inline"><i class="icon-remove"></i></span>',
                '</div>'].join(' ');
            $(this).parent().parent().append(newInput);
            var $newPointInput = $(this).parent().siblings().last();
            $newPointInput.find("input").attr("data-7daypoint-input", whichDay);
        }
    });

    //每天和七天开始时间光标移开时验证
    $("input[data-start]").focusout(function () {
        var $this = $(this);
        //推迟 200 毫秒验证(等待时间选择器赋值完成)
        setTimeout(function () {
            var $dataEnd = $this.siblings("input[data-end]");
            var $dataTooltip = $this.siblings("p");
            help($dataTooltip, "");
            if ($this.val().length == 0 && $dataEnd.val().length != 0) {
                helpError($dataTooltip, message.startTimeRequired);
            } else if ($this.val().length != 0 && $dataEnd.val().length != 0) {
                if ($this.val() >= $dataEnd.val()) {
                    helpError($dataTooltip, message.endTimeLessThanStartTime);
                }
            }
        }, 200);
    });

    //每天和七天结束时间光标移开时验证
    $("input[data-end]").focusout(function () {
        var $this = $(this);
        //推迟 200 毫秒验证(等待时间选择器赋值完成)
        setTimeout(function () {
            var $dataStart = $this.siblings("input[data-start]");
            var $dataTooltip = $this.siblings("p");
            help($dataTooltip, "");
            if ($this.val().length == 0 && $dataStart.val().length != 0) {
                helpError($dataTooltip, message.endTimeRequired);
            } else if ($this.val().length != 0 && $dataStart.val().length != 0) {
                if ($dataStart.val() >= $this.val()) {
                    helpError($dataTooltip, message.endTimeLessThanStartTime);
                }
            }
        }, 200);
    });

    //每天和七天间隔周期光标移开时验证
    $("input[data-period]").focusout(function () {
        var dataPeriodER = /^\+?[1-9][0-9]*$/;
        var $dataStart = $(this).siblings("input[data-start]");
        var $dataEnd = $(this).siblings("input[data-end]");
        var $dataTooltip = $(this).siblings("p");
        var dataStart = $dataStart.val();
        var dataEnd = $dataEnd.val();
        var timeDifference = getTimeDifference(dataStart, dataEnd);
        help($dataTooltip, "");
        if (dataStart.length == 0 && dataEnd.length != 0) {
            helpError($dataTooltip, message.startTimeRequired);
        } else if (dataStart.length != 0 && dataEnd.length == 0) {
            helpError($dataTooltip, message.endTimeRequired);
        } else if (dataStart.length == 0 && dataEnd.length == 0) {
            $(this).val(0);
        } else if (dataStart.length != 0 && dataEnd.length != 0 && dataStart >= dataEnd) {
            helpError($dataTooltip, message.endTimeLessThanStartTime);
        } else if (!dataPeriodER.test($(this).val())) {
            helpError($dataTooltip, "周期必须是1-" + timeDifference + "之间的整数");
        } else if (($(this).val()) > timeDifference) {
            helpError($dataTooltip, "周期必须是1-" + timeDifference + "之间的整数");
        }
    });

    $(document).on("mouseenter", "i.icon-remove", function () {
        $(this).css("background-color", "#ddd");
    });

    $(document).on("mouseout", "i.icon-remove", function () {
        $(this).css("background-color", "#FFF");
    });

    $(document).on("click", "i.icon-remove", function () {
        $(this).parent().parent().remove();
    });

    //表单提交
    $("#submit-button").click(function () {
        if (!zoneCheck()) {
            return false;
        }
        if (!dvPlaceNameCheck()) {
            return false;
        }
        if (!dvPlaceNameUniqueCheck()) {
            return false;
        }
        if (!ftpCheck()) {
            return false;
        }
        if (!dvPlaceIpCheck()) {
            return false;
        }

        //判断是否有外部控制
        if ($("#lightOn-input").attr("checked")) {

            if (!ioIpCheck()) {
                return false;
            }
            if (!ioPortCheck()) {
                return false;
            }
            if (!dvPortCheck()) {
                return false;
            }
            //初始化,清空之前的值
            $("input[name='everydayPeriod']").val("");  //每天周期
            $("input[name='everydayPoint']").val("");   //每天时间点
            $("input[name='sevendayPeriod']").val("");  //7天周期
            $("input[name='sevendayPoint']").val("");   //7天时间点
            //根据选取的类型获取其中的值
            var $timeType = $("[type='radio']:checked");

            //每天周期
            if ($timeType.val() == "day") {
                var dataStartValue = $("input[data-start='0']").val();
                var dataEndValue = $("input[data-end='0']").val();
                var dataPeriod = $("input[data-period='0']").val();
                var $everydayHelp = $("#everyday-help");

                if (dataStartValue != "" && dataEndValue != "" && dataPeriod != "") {
                    var timeDifference = getTimeDifference(dataStartValue, dataEndValue);
                    if (dataStartValue >= dataEndValue) {
                        helpError($everydayHelp, message.endTimeLessThanStartTime);
                        return false;
                    } else if (dataPeriod > timeDifference) {
                        helpError($everydayHelp, "间隔周期必须是1-" + timeDifference + "之间的整数");
                        return false;
                    } else {
                        //赋值给隐藏域
                        $("input[name='everydayPeriod']").val(dataStartValue + "," + dataEndValue + "," + dataPeriod);
                        helpOk($everydayHelp, "");
                    }
                } else {
                    helpError($("#everyday-help"), message.dailyCycleRequired);
                    return false;
                }
            }

            //每天时间点
            if ($timeType.val() == "point") {
                var dataPointValue = "";
                $("input[data-point-input]").each(function (i) {
                    if ($(this).val().length != 0) {
                        dataPointValue += $(this).val() + ",";
                    }
                });
                if (dataPointValue == null || dataPointValue == "") {
                    helpError($("#everypoint-help"), message.dailyTimePointRequired);
                    return false;
                } else {
                    //dataPointValue = dataPointValue.substring(0,dataPointValue.length - 1);
                    //赋值给隐藏域
                    $("input[name='everydayPoint']").val(dataPointValue);
                    helpOk($("#everypoint-help"), "");
                }
            }

            //7天周期
            if ($timeType.val() == "7day") {
                var sevendayPeriod = "";
                //循环遍历7天的周期
                for (var i = 1; i <= 7; i++) {
                    var $dataStart = $("input[data-start='" + i + "']");
                    var $dataEnd = $("input[data-end='" + i + "']");
                    var $dataTooltip = $dataStart.siblings("p");
                    var dataStartValue = $dataStart.val();
                    var dataEndValue = $dataEnd.val();
                    var dataPeriodValue = $("input[data-period='" + i + "']").val();

                    //判断开始时间、结束时间和周期是否为空
                    if (dataStartValue.length != 0 && dataEndValue.length != 0 && dataPeriodValue != "") {
                        var timeDifference = getTimeDifference(dataStartValue, dataEndValue);
                        if (dataStartValue >= dataEndValue) {
                            helpError($dataTooltip, message.endTimeLessThanStartTime);
                            return false;
                        }
                        var dataPeriodER = /^\+?[1-9][0-9]*$/;
                        if (!dataPeriodER.test(dataPeriodValue)) {
                            helpError($dataTooltip, "周期必须是1-" + timeDifference + "之间的整数");
                            return false;
                        }
                        if (dataPeriodValue > timeDifference) {
                            helpError($dataTooltip, "周期必须是1-" + timeDifference + "之间的整数");
                            return false;
                        }
                        sevendayPeriod += i + "," + dataStartValue + "," + dataEndValue + "," + dataPeriodValue + "&";
                    }
                }
                if (sevendayPeriod == null || sevendayPeriod == "") {
                    helpError($("p[data-7dayPeriod-help]"), message.sevenDayPeriodRequired);
                    return false;
                } else {
                    helpOk($("p[data-7dayPeriod-help]"), "");
                }
                //sevendayPeriod = sevendayPeriod.substring(0, sevendayPeriod.length-1);
                //赋值给隐藏域
                $("input[name='sevendayPeriod']").val(sevendayPeriod);
            }

            //7天时间点
            if ($timeType.val() == "7day_point") {
                var dataPointValue = "";
                //循环变量7天
                for (var j = 1; j <= 7; j++) {

                    //循环变量其中一天的时间点
                    $("input[data-7daypoint-input='" + j + "']").each(function (i) {
                        if ($(this).val().length != 0) {
                            dataPointValue += j + "," + $(this).val() + "&";
                        }
                    });

                }
                if (dataPointValue == null || dataPointValue == "") {
                    helpError($("#7daypoint-help"), message.sevenDayTimePointRequired);
                    return false;
                } else {
                    //dataPointValue = dataPointValue.substring(0, dataPointValue.length - 1);
                    //赋值给隐藏域
                    $("input[name='sevendayPoint']").val(dataPointValue);
                    helpOk($("#7daypoint-help"), "");
                }
            }
        }
        art.dialog({
            title: message.tips,
            content: message.sureToOperate,
            okValue: message.ok,
            ok: function () {
                form1.submit();
            },
            cancelValue: message.cancel,
            cancel: function () {
            }
        });
        return false;
    });

    //修改界面用，展示拍照计划界面数据,界面加载完成后调用
    (function () {
        var $remoteIo = $("#enable-external-io-display");
        var $photographSchedule = $("#enable-external-display");
        if ($("#lightOn-input").attr("checked")) {
            $remoteIo.show();
            $photographSchedule.show();
        } else {
            $remoteIo.hide();
            $photographSchedule.hide();
        }

        //判断展示每天时间点、每天周期、7天周期、7天时间点
        var $timeType = $("input[name='radioType']:checked");
        if ($timeType.length > 0) {
            showScheduleByType($timeType.val());
            return;
        }

        //如果任何一个类型都没有被选中，则默认选中每天周期
        $("input[name='radioType']").first().attr("checked", "checked").change();
    })();


    // 区域选择
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
                        $help.empty().append(message.pleaseSelectZone);
                        return false;
                    }
                    var node = nodes[0];
                    $zoneInput.val(node.name);
                    $zoneInput.attr("data-zone-id", node.id);
                    $("#zoneId").val(node.id);
                },
                cancelValue: message.cancel,
                cancel: function () {
                    $help.empty();
                }
            });
        };

        // 区域输入框获取焦点时
        $("#zone-input").focus(function () {
            var $this = $(this);
            $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
                // TODO issues#1495 (galaxy) @gaohui 2013-06-013
                // 初始化树
                $.fn.zTree.init($('#zoneTree'), setting, result);
                var zoneTree = $.fn.zTree.getZTreeObj("zoneTree");

                // 初始化弹出框
                showDialog($this, zoneTree);
            });
        });
    })();
});