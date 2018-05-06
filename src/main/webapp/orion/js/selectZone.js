/**
 *
 * @author gaohui
 * @date 2013-02-26
 * @dependency jquery, pnotify, ztree, artDialog
 */

(function ($) {
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
                        $("#zoneIdInput").val(node.id);
                        $("#zoneNameInput").val(node.name);
                    },
                    cancelValue: "取消",
                    cancel: function () {
                        $help.empty();
                    },
                    button: [
                        {
                            value: "清空",
                            callback: function () {
                                $zoneInput.val('');
                                $("#zoneIdInput").val('');
                                $("#zoneNameInput").val('');
                            }
                        }
                    ]
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
    });
})(jQuery);
