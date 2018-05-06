/**
 *
 * 行为规则 添加修改 js
 * @author xu.baoji
 * @date  2013-04-18
 */

var RULE = {
    data: []
};
$(function () {
    initDevice();

    hideDeviceError($("[id=device-input-help]"));

    var RULE_FORM = {nameUnexist: true};
    $("#ruleName").blur(function () {
        $.getJSON("ruleExist.action", {"id": $("#id").val(), "ruleName": $("#ruleName").val()}, function (result) {
            RULE_FORM.nameUnexist = result.usable;
            if (result.usable) {
                $("#ruleName-input-help").text("");
            } else {
                $("#ruleName-input-help").css('color', 'red').text("该名称已存在！");
            }
        });

    });
    //form 表单验证
    $("form").validate({
        rules: {
            zoneId: {
                required: true
            },
            zoneName: {
                required: true
            },
            ruleName: {
                required: true
            },
            goRuleName: {
                required: true
            },
            backRuleName: {
                required: true
            }
        },
        messages: {
            zoneId: {
                required: "请选择区域"
            },
            zoneName: {
                required: "请选择区域"
            },
            ruleName: {
                required: '规则名称不能为空'
            },
            goRuleName: {
                required: "往规则名称不能为空"
            },
            backRuleName: {
                required: "返规则名称不能为空"
            }
        },
        errorPlacement: function (error, element) {
            error.insertAfter(element.next());
        }
    });

    //对规则删除按钮的处理
    hideRemoveImg();

    //追加事件
    $(document).on("click", ".appendRule", function () {
        var $this = $(this);
        // 判断当前选择框是否选择
        if ($this.parent().find("[name=deviceId]").val() == 0) {
            art.dialog({
                id: 'info',
                title: '提示',
                content: '请选择当前激发器！',
                okValue: '确定',
                ok: function () {
                }
            });
            return;
        }
        //添加选择框
        $this.parent().after(createRuleDiv());
        //添加描述对应框
        var index = $this.parent().index();
        $(".ruleList").eq(index - 1).after($($(".ruleList")[0]).clone().text(""));
        hideRemoveImg();
    });

    //删除事件
    $(document).on("click", ".removeRule", function () {
        //删除时判断规则数量
        if ($("#rules").children(".rule").size() > 2) {

            // 判断 当前的上一个和 下一个是否相同
            var lastRuleValue = $(this).parent().prev().find("[name=deviceId]").select2("val");
            var nextValue = $(this).parent().next().find("[name=deviceId]").select2("val");
            if (lastRuleValue == nextValue) { // 将  当前的下一个 设置为为选择
                $(this).parent().next().find("[name=deviceId]").select2("val", "");
                // 处理对应的预览信息
//                disposeRuleList($(this));
            }
            var index = $(this).parent().index();
            $(".ruleList").eq(index - 1).remove();
            $(this).parent().remove();
        }

    });
    //select 选中事件
    $(document).on("change", "input[name=deviceId]", function () {
        //判断前一个选择框是否选择
        var lastRuleValue = $(this).parent().prev().find("[name=deviceId]").select2("val");
        if ($(this).parent().index() > 1) {
            if (lastRuleValue.length == 0) {
                art.dialog({
                    id: 'info',
                    title: '提示',
                    content: '请选择上一个激发器！',
                    okValue: '确定',
                    ok: function () {
                    }
                });
                $(this).select2("val", "");
                return;
            }
        }
        //判断与上一个是否相同
        var value = $(this).select2("val");
        // 如果当前 select 有值 则 隐藏错误信息
        if (value.length != 0) {
            hideDeviceError($(this).parent().find("#device-input-help"));
        }
        if (value == lastRuleValue && value != "") {
            art.dialog({
                id: 'info',
                title: '提示',
                content: '相邻两个激发器不可相同，请重选！',
                okValue: '确定',
                ok: function () {
                }
            });
            $(this).select2("val", "");
        }

        // 判断与下一个是否相同
        var nextValue = $(this).parent().next().find("[name=deviceId]").select2("val");
        if (value == nextValue) {
            $(this).parent().next().find("[name=deviceId]").select2("val", "");
        }
        // 处理预览信息
//        disposeRuleList($(this));

    });


    // 绑定select2事件

    // form  表单 提交回调函数
    $("#ruleForm").submit(function () {
            var is = true;
            if ($(".goRuleList").length == 0) {
                if ($(".ruleList").find("[name=exciterIds]").length == 0) {
                    $(this).parent().find("#ruleName-input-help").show();
                    is = false;
                }
            } else {
                if ($(".goRuleList").find("[name=exciterIds]").length == 0) {
                    $("#goRule-input-help").show();
                    is = false;
                }
                if ($(".backRuleList").find("[name=exciterIds]").length == 0) {
                    $("#backRule-input-help").show();
                    is = false;
                }
            }
            return is && RULE_FORM.nameUnexist;
        }
    );

    //隐藏规则选择框第一个和最后一个的删除按钮
    function hideRemoveImg() {
        //将所有删除按钮设置为可见
        $("#rules").find(".removeRule").show();
        //隐藏第一个和最后一个删除按钮
        $("#rules").find(".removeRule").first().hide();
//        $("#rules").find(".removeRule").eq(-2).hide();
        $("#rules").find(".removeRule").last().hide();
    }

    // 初始化激发器 设备 信息
    function initDevice() {
        $.getJSON("initDevice.action", function (result) {
            var deviceList = result.deviceList;
            var data = [];
            for (var i = 0; i < deviceList.length; i++) {
                if (deviceList[i].name != deviceList[i].sn) {
                    data[i] = {id: deviceList[i].id, text: deviceList[i].sn + "-" + deviceList[i].name};
                } else {
                    data[i] = {id: deviceList[i].id, text: deviceList[i].name};
                }
            }
            RULE.data = data;
            bdSelect2($(".select").not(".rule_template"));
        });
    }

    // 为 激发器 选择 input 绑定 select2 事件
    function bdSelect2(object) {
        object.select2({
            data: RULE.data,
            formatNoMatches: function () {
                return "未找到匹配的激发器";
            },
            placeholder: "请选择激发器",
            allowClear: true,
            matcher: function (term, text) {
                return (text.toUpperCase().indexOf(term.toUpperCase()) >= 0
                    || makePy(text.toUpperCase()).join().indexOf(term.toUpperCase()) >= 0);
            }
        });
    }

    // 创建添加 激发器选择 div
    function createRuleDiv() {
        var ruleDiv = $("#ruleLayout").find(".rule").clone();
        bdSelect2(ruleDiv.find("[name=deviceId]"));
        return ruleDiv;
    }

    // 隐藏 激发器 错误信息
    function hideDeviceError(obj) {
        obj.hide();
    }

    // 区域选择
    (function () {
        var dtd;

        // ztree 树配置
        var setting = {
            view: {
                showLine: false
            },
            async: {
                enable: true,
                url: '../blackhole/zone/children.json',
                autoParam: ["id=zoneId"]
            },
            callback: {
                onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    if (dtd) {
                        dtd.resolve();
                    }
                },
                onClick: function (event, treeId, treeNode, clickFlag) {
                    if (treeNode.zAsync) {
                        return;
                    }

                    //强制加载子节点
                    var zoneTree = this.getZTreeObj(treeId);
                    zoneTree.reAsyncChildNodes(treeNode, 'refresh', false);
                }
            }
        };

        var showDialog = function ($zoneInput, zoneTree) {
            var $help = $("#zoneTreeDialog .help-block.red");
            art.dialog({
                id: "zoneTreeDialog",
                title: "选择区域",
                content: $("#zoneTreeDialog")[0],
                fixed: true,
                okValue: "确定",
                ok: function () {
                    var that = this;
                    var nodes = zoneTree.getSelectedNodes();
                    if (nodes.length == 0) {
                        $help.empty().append("请选择区域");
                        return false;
                    }
                    var node = nodes[0];

                    $zoneInput.val(node.name);
                    $zoneInput.attr("data-zone-id", node.id);
                    $("#zoneId").val(node.id);
                    that.close();
                },
                cancelValue: "取消",
                cancel: function () {
                    $help.empty();
                }
            });
        };

        // 区域输入框获取焦点时
        $("#zone-input").focus(function () {
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

    $("#addRule").click(function () {

        $("#ruleName-input-help").hide();
        // 弹出站点选择对话框
        art.dialog({
            id: "logicGroup",
            title: '添加单程规则',
            content: $("#rules")[0],
            okValue: "确定",
            ok: function () {
                return addRule(0);
            },
            cancelValue: "取消",
            cancel: function () {
                clearExciters();
            }
        });
    });

    $("#addGo").click(function () {

        $("#goRule-input-help").hide();
        // 弹出站点选择对话框
        art.dialog({
            id: "logicGroup",
            title: '添加往规则',
            content: $("#rules")[0],
            okValue: "确定",
            ok: function () {
                return addRule(1);
            },
            cancelValue: "取消",
            cancel: function () {
                clearExciters();
            }
        });
    });

    $("#addBack").click(function () {
        $("#backRule-input-help").hide();
        // 弹出站点选择对话框
        art.dialog({
            id: "logicGroup",
            title: '添加返规则',
            content: $("#rules")[0],
            okValue: "确定",
            ok: function () {
                return addRule(2);
            },
            cancelValue: "取消",
            cancel: function () {
                clearExciters();
            }
        });
    });

    // 编辑规则
    $(document).on("click", "#edit", function () {

        var $this = $(this);
        var exciterIds = $this.parent().find("[name=exciterIds]").val().split(",");
        $("#rules").empty();
        $.each(exciterIds, function (index, exciterId) {
            // 前三位 为 规则ID 和 类型 和 启用状态，不做处理
            if (index >= 3) {
                var ruleDiv = $("#ruleLayout").find(".rule").clone();
                bdSelect2(ruleDiv.find("[name=deviceId]"));
                ruleDiv.find("[name=deviceId]").select2("val", exciterId);
                $("#rules").append(ruleDiv);
            }
        });

        $("#rules").append("<p class='help-block m-t-10 red'></p>");
        hideRemoveImg();
        art.dialog({
            id: "logicGroup",
            title: '添加往规则',
            content: $("#rules")[0],
            okValue: "确定",
            ok: function () {
                var type = 0; // 默认为单程规则
                if (exciterIds[1] == "3") { // 往规则
                    type = 1;
                } else if (exciterIds[1] == "4") {   // 返规则
                    type = 2;
                }
                return updateRule(type, $this);
            },
            cancelValue: "取消",
            cancel: function () {
                clearExciters();
            }
        });
    });

    // 删除规则
    $(document).on("click", "#remove", function () {
        var $this = $(this);
        $this.parent().remove();
    });

    // 停用启用规则
    $(document).on("click", "#state", function () {
        var $this = $(this);

        // 启用的往规则数目
        var goRuleCount = 0;

        // 启用的返规则数目
        var backRuleCount = 0;

        // 计算启用的往规则数目
        $.each($(".goRuleList").find("[name=exciterIds]"), function () {
            var $goRule = $(this);
            var state = $goRule.val().split(",")[2];
            if (state == "1") {
                goRuleCount = goRuleCount + 1;
            }
        });

        // 计算启用的返规则数目
        $.each($(".backRuleList").find("[name=exciterIds]"), function () {
            var $backRule = $(this);
            var state = $backRule.val().split(",")[2];
            if (state == "1") {
                backRuleCount = backRuleCount + 1;
            }
        });


        var exciterIds = $this.parent().find("[name=exciterIds]").val();
        var tempArray = exciterIds.split(",");

        if ($this.parent().parent().hasClass("goRuleList") && goRuleCount == 1 && tempArray[2] == 1) {
            art.dialog({
                title: "消息",
                content: "请至少保留一个可用的往规则",
                time: 2000
            });
            return;
        } else if ($this.parent().parent().hasClass("backRuleList") && backRuleCount == 1 && tempArray[2] == 1) {
            art.dialog({
                title: "消息",
                content: "请至少保留一个可用的返规则",
                time: 2000
            });
            return;
        }

        $this.toggleClass("btn-success");
        $this.toggleClass("btn-danger");
        $this.find("i").toggleClass("icon-play");
        $this.find("i").toggleClass("icon-stop");
        $this.attr("title", $this.attr("title") == "停用" ? "启用" : "停用");
        tempArray[2] = $this.attr("title") == "停用" ? 1 : 0;
        exciterIds = tempArray.join(",");
        $this.parent().find("[name=exciterIds]").val(exciterIds);
    });

    /**
     * 添加行为规则
     * @param type 规则类型
     * @returns {boolean}
     */
    function addRule(type) {

        var blnCanBeClose = true;
        var ruleHtml = "<span id='exciters'>";
        var exciterIds = "";
        $.each($("#rules").children("div"), function (index, ruleDiv) {
            var $this = $(this);
            var $deviceInput = $this.find("[name=deviceId]");

            // 检查是否存在未选择激发器的输入框
            if ($deviceInput.val().length <= 0) {
                $this.find("#device-input-help").show();
                blnCanBeClose = false;
                return;
            }

            var ruleValue = $deviceInput.select2("val");
            var ruleData = $deviceInput.select2("data");
            if (ruleValue.length != 0) {
                if (ruleData != null) {
                    var textShow = "";
                    if (ruleData.text.indexOf("-") > 0) {
                        textShow = ruleData.text.split("-")[1];
                    } else {
                        textShow = ruleData.text;
                    }
                    if (index >= 1) {
                        ruleHtml = ruleHtml + "<span class='muted' style='margin-right: -6px;vertical-align: 1px;'>—</span><span class='muted' style='vertical-align: 0;'>></span> <strong>" + textShow + "</strong>"
                    } else {
                        ruleHtml = ruleHtml + "<strong>" + textShow + "</strong>";
                    }
                }
                exciterIds = exciterIds + "," + ruleValue;
            }
        });
        ruleHtml = ruleHtml + "</span>";

        if (type == 1) {
            exciterIds = ",3,1" + exciterIds;
        } else if (type == 2) {
            exciterIds = ",4,1" + exciterIds;
        } else if (type == 0) {
            exciterIds = ",1,1" + exciterIds;
        }

        var $ruleList;
        // 往
        if (type == 1) {
            $ruleList = $(".goRuleList").find("[name=exciterIds]");
        } else if (type == 2) {// 返
            $ruleList = $(".backRuleList").find("[name=exciterIds]");
        } else if (type == 0) {
            $ruleList = $(".ruleList").find("[name=exciterIds]");
        }

        $.each($ruleList, function (index, ruleDiv) {
            // 检查规则是否已存在
            var $this = $(this);
            if ($this.val().substring($this.val().indexOf(",") + 1) ==
                exciterIds.substring(exciterIds.indexOf(",") + 1)) {
                blnCanBeClose = false;
                $("#rules").find(".help-block").text("已存在相同规则");
                return;
            } else {
                $("#rules").find(".help-block").text("");
            }
        });

        if (blnCanBeClose) {
            ruleHtml = "<div> <input name='exciterIds' type='hidden' value=''/><span id='edit' class='btn btn-mini'><i class='icon-pencil' /></span> " +
                "<span id='remove' class='btn btn-mini'><i class='icon-remove' /></span> " + ruleHtml + "<br/></div>";

            // 往
            if (type == 1) {
                $(".goRuleList").append(ruleHtml).find("[name=exciterIds]").last().val(exciterIds);
            } else if (type == 2) {// 返
                $(".backRuleList").append(ruleHtml).find("[name=exciterIds]").last().val(exciterIds);
            } else if (type == 0) {
                $(".ruleList").append(ruleHtml).find("[name=exciterIds]").last().val(exciterIds);
            }

            clearExciters();
        }
        return blnCanBeClose;
    }

    /**
     * 修改行为规则
     * @param type 规则类型
     * @param ruleObj DOM元素
     * @returns {boolean}
     */
    function updateRule(type, ruleObj) {
        var blnCanBeClose = true;
        var ruleHtml = "<span id='exciters'>";
        var exciterIds = "";
        var lastExciterIds = ruleObj.parent().find("[name=exciterIds]").val();
        $.each($("#rules").children("div"), function (index, ruleDiv) {
            var $this = $(this);
            var $deviceInput = $this.find("[name=deviceId]");

            // 检查是否存在未选择激发器的输入框
            if ($deviceInput.val().length <= 0) {
                $this.find("#device-input-help").show();
                blnCanBeClose = false;
                return;
            }

            var ruleValue = $deviceInput.select2("val");
            var ruleData = $deviceInput.select2("data");
            var index = $deviceInput.parent().index();
            if (ruleValue.length != 0) {
                if (ruleData != null) {
                    var textShow = "";
                    if (ruleData.text.indexOf("-") > 0) {
                        textShow = ruleData.text.split("-")[1];
                    } else {
                        textShow = ruleData.text;
                    }
                    if (index >= 1) {
                        ruleHtml = ruleHtml + "<span class='muted' style='margin-right: -6px;vertical-align: 1px;'>—</span><span class='muted' style='vertical-align: 0;'>></span> <strong>" + textShow + "</strong>"
                    } else {
                        ruleHtml = ruleHtml + "<strong>" + textShow + "</strong>";
                    }
                }
                exciterIds = exciterIds + "," + ruleValue;
            }
        });
        ruleHtml = ruleHtml + "</span>";

        exciterIds = lastExciterIds.split(",")[0] + "," + lastExciterIds.split(",")[1] + "," + lastExciterIds.split(",")[2] + exciterIds;

        var $ruleList;
        // 往
        if (type == 1) {
            $ruleList = $(".goRuleList").find("[name=exciterIds]");
        } else if (type == 2) {// 返
            $ruleList = $(".backRuleList").find("[name=exciterIds]");
        } else if (type == 0) {
            $ruleList = $(".ruleList").find("[name=exciterIds]");
        }

        $.each($ruleList, function (index, ruleDiv) {
            // 检查规则是否已存在
            var $this = $(this);
            if ($this.val() == exciterIds && lastExciterIds != exciterIds) {
                blnCanBeClose = false;
                $("#rules").find(".help-block").text("已存在相同规则");
                return;
            } else {
                $("#rules").find(".help-block").text("");
            }
        });

        if (blnCanBeClose) {
            ruleObj.parent().find("#exciters").replaceWith(ruleHtml);

            if (exciterIds.length > 0) {
                ruleObj.parent().find("[name=exciterIds]").val(exciterIds);
            }

            clearExciters();
        }
        return blnCanBeClose;
    }

    /**
     * 清除激发器选择框，并添加两个空的激发器选择框
     */
    function clearExciters() {
        $("#rules").empty().append(createRuleDiv()).append(createRuleDiv()).append("<p class='help-block m-t-10 red'></p>");
        hideRemoveImg();
    }

});