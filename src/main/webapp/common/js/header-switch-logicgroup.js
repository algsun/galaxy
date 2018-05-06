/**
 * 页面头部
 *
 * 1.'其他站点'按钮，弹出选择站点窗口
 *
 * @author gaohui
 * @date 2013-05-22
 * @dependency jQuery, artDialog, zTree
 */

(function ($) {
    var app = App('common');
    var header = {};
    app.header = header;

    var subsystems = {
        'blueplanet': '/blueplanet/',
        'proxima': '/proxima/',
        'uma': '/uma/',
        'orion': '/orion/',
        'phoenix': '/phoenix/',
        'halley':'/halley/',
        'saturn':'/saturn/'
    };

    header.chooseLogicGroup = function (targetSubsystem) {

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
                    url: '../blackhole/querySubLogicGroups.action',
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

                    // 跳转到选中站点
                    var url = '../blackhole/switchLogicGroup.action?forward='+ subsystems[targetSubsystem] + '&logicGroupId=' + logicGroupNode.id;
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
