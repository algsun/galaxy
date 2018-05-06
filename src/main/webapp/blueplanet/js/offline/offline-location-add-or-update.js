//author : chen.yaofei
//date : 2016-05-10
$(function () {
//校验表单
    $("#locationForm").validate();

//验证位置点名称是否重复
    var checkLocationNameFlag = true;

    $("#locationName").change(function () {
        var locationName = $(this).val().trim();
        var locationValue = $(this).data("value").trim();
        if (locationName == "") {
            var content = "<i class='mw-icon-cancel'></i> " + "必选字段";
            $("#checkLocationName").empty().append(content);
            checkLocationNameFlag = false;
            return false;
        } else if (locationValue == locationName) {
            var content = "<i class='mw-icon-ok'></i> " + "";
            $("#checkLocationName").empty().append(content);
            checkLocationNameFlag = true;
            return true;
        } else {
        }
        $.get("offline/addLocation/validateLocationName?locationName=" + locationName, function (data) {
            if (!data) {
                var content = "<i class='mw-icon-ok'></i> " + "";
                $("#checkLocationName").empty().append(content);
                checkLocationNameFlag = true;
            } else if (data) {
                var content = "<i class='mw-icon-cancel'></i> " + "此位置点名称已存在";
                $("#checkLocationName").empty().append(content);
                checkLocationNameFlag = false;
            } else {
                $("#checkLocationName").empty();
            }
        });
    });

    $("#submit-btn").click(function () {

        var locationName = $("#locationName").val().trim();
        if (locationName == "") {
            var content = "<i class='mw-icon-cancel'></i> " + "必选字段";
            $("#checkLocationName").empty().append(content);
            return false;
        }
        //校验是否选择了监测指标
        var showSensor = $("#showSensor").val();
        if ($("input:checked").size() == 0 && showSensor != "showChecked") {
            $("#checkSensorError").empty().append("请选择监测指标");
            return false;
        }
        if (!checkLocationNameFlag) {
            return false;
        }
        $("#locationForm").submit();
    });


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
                $zoneInput.data("zoneId", node.id);
                $zoneInput.next().val(node.id);
            },
            cancelValue: message.cancel,
            cancel: function () {
                $help.empty();
            },
            button: [
                {
                    value: message.clear,
                    callback: function () {
                        $zoneInput.val('');
                        $zoneInput.next().val('');
                    }
                }
            ]
        });
    };

// 区域输入框获取焦点时
    $(".zone").focus(function () {
        var $this = $(this);
        $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
            // 初始化树
            $.fn.zTree.init($('#zoneTree'), setting, result);
            var zoneTree = $.fn.zTree.getZTreeObj("zoneTree");

            // 初始化弹出框
            showDialog($this, zoneTree);
        });
    });
});
