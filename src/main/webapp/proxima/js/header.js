/**
 * 页面头部
 *
 * 1.'其他站点'按钮，弹出选择站点窗口
 * TODO 使用 /common/js/header-switch-logicgroup.js @gaohui 2013-05-21
 * TODO 已经废弃 @gaohui 2013-07-25
 *
 * @author gaohui
 * @date 2012-11-28
 * @dependency jQuery, artDialog
 */

(function ($) {
    // TODO 命名有问题 @gaohui 2013-4-23
    var BluePlanet = App('proxima');
    var header = {};
    BluePlanet.header = header;

    header.chooseLogicGroup = function () {

        // Ajax 获取顶层站点 /blackhole/availableLogicGroup.action
        $.post("../blackhole/availableLogicGroup.action", function (result) {

            var logicGroupNode;
            var $availableLogicGroupDialog = $('#availableLogicGroupDialog');
            // 初始化 zTree
            $.fn.zTree.init($('#logicgroup-tree'), {
                view: {
                    showLine: false
                },
                async: {
                    enable: true,
                    url: '../blackhole/querySubLogicGroups.action', // /blackhole/querySubLogicGroups.action
                    autoParam: ["id=logicGroupId"]
                },
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        logicGroupNode = treeNode;
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
                    if (logicGroupNode) {
                        if (logicGroupNode.type === 'basicSite') {
                        } else {
                            $availableLogicGroupDialog.find('.help-block')
                                .empty().append("请选择基层站点");
                            return false;
                        }
                    } else {
                        $availableLogicGroupDialog.find(".help-block").empty()
                            .append("请选择一个站点");
                        return false;
                    }

                    // 使用 switchLogicGroup.action
                    // 跳转到选中站点
                    var url = '../blackhole/switchLogicGroup.action?forward=/proxima/&logicGroupId=' + logicGroupNode.id;
                    // 修复 IE 相对地址解析问题
                    var basePath = $("base").attr("href");
                    if (basePath) {
                        url = basePath + url;
                    }
                    location.href = url;
                },
                cancelValue: "取消",
                cancel: function () {
                    $availableLogicGroupDialog.find(".help-block").empty();
                }
            });
        });
    };

})(jQuery);
