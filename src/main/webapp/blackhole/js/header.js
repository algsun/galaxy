/**
 * 页面头部
 *
 * 1.'其他站点'按钮，弹出选择站点窗口
 *
 * @author gaohui
 * @date 2012-11-28
 * @dependency jQuery, artDialog
 */

(function ($) {
    var blackhole = App('blackhole');
    var header = {};
    blackhole.header = header;

    header.chooseLogicGroup = function () {

        // Ajax 获取顶层站点
        $.post("availableLogicGroup.action", function (result) {

            var logicGroupId = 0;
            var $availableLogicGroupDialog = $('#availableLogicGroupDialog');
            // 初始化 zTree
            $.fn.zTree.init($('#logicgroup-tree'), {
                view: {
                    showLine: false
                },
                async: {
                    enable: true,
                    url: 'assignLogicGroup.action',
                    autoParam: ["id=logicGroupId"]
                },
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        logicGroupId = treeNode.id;
                    }
                }
            }, result);

            // 弹出站点选择对话框
            art.dialog({
                id: "logicGroup",
                title: '站点选择',
                content: $availableLogicGroupDialog[0],
                okValue: "进入站点",
                ok: function () {
                    // 必须选择一个站点
                    if (logicGroupId == 0) {
                        $availableLogicGroupDialog.find(".help-block").empty()
                            .append("请选择一个站点");
                        return false;
                    }

                    // 跳转到选中站点
                    location.href = 'doChooseLogicGroup.action?logicGroupId=' + logicGroupId;
                },
                cancelValue: "取消",
                cancel: function () {
                    $availableLogicGroupDialog.find(".help-block").empty();
                }
            });
        });
    };
})(jQuery);

