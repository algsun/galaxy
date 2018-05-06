/**
 * 查询未绑定设备
 *
 * @author gaohui
 * @date 2013-02-26
 * @dependency jquery, artDialog, ztree, pnotify
 * @check @wang yunlong 2013-02-26 #1776
 */
$(function () {
    (function () {
        // 显示隐藏从模块
        $("a[data-master]").click(function () {
            var masterModuleId = $(this).attr("data-master");
            $("tr[data-master-for='" + masterModuleId + "']").toggle();
            return false;
        });
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
                title: "选择区域",
                content: $("#zoneTreeDialog")[0],
                fixed: true,
                okValue: "确定",
                ok: function () {
                    var nodes = zoneTree.getSelectedNodes();
                    if (nodes.length == 0) {
                        $help.empty().append("请选择区域");
                        return false;
                    }
                    var node = nodes[0];
                    $zoneInput.val(node.name);
                    $zoneInput.attr("data-zone-id", node.id);
                },
                cancelValue: "取消",
                cancel: function () {
                    $help.empty();
                }
            });
        };

        // 区域输入框获取焦点时
        $("#zoneInput").focus(function () {
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

        // 列表全选按钮
        $("th input[type='checkbox']").change(function () {
            if ($(this).attr("checked")) {
                $("td input[type='checkbox']").attr("checked", "true");
            } else {
                $("td input[type='checkbox']").removeAttr("checked");
            }
        });

        // 绑定设备
        $("#bindDeviceButton").click(function () {
            var zoneId = $("#zoneInput").attr("data-zone-id");
            if (!zoneId) {
                art.dialog({title: "提示", content: "请选择区域"});
                return;
            }

            var checkDeviceIds = []; // 选中的设备
            var $checkedDevices = $("tbody input[type='checkbox']:checked");
            $checkedDevices.each(function () {
                checkDeviceIds.push($(this).val());
            });

            if (checkDeviceIds.length == 0) {
                art.dialog({title: "提示", content: "请选择设备"});
                return;
            }

            $.ajaxSetup({traditional: true});
            $.getJSON("device/bind.json", {zoneId: zoneId, deviceIds: checkDeviceIds}, function (result) {
                if (result.success) {
                    // 刷新设备树
                    App(window.BLUEPLANET).deviceTree.reload();
                    window.pnotify("绑定成功", "info");
                    // 删除选中的设备 tr
                    $checkedDevices.parent().parent().remove();
                } else {
                    window.pnotify("绑定失败", "notice");
                }
            });
        });
    })();
});
