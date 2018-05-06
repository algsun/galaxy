/**
 * 分配站点组 js
 *
 * @author li.jianfei
 * @date 2012-11-26
 */
$(function () {
    $("a[btn-assignLogicGroup]").click(function () {

        var userId = $(this).attr("data-userId");
        var $assignLogicGroup = $("#assignLogicGroup");

        // 通过用户id 获取可用站点
        $.post("assignLogicGroup.action", {userId: userId}, function (result) {

            if (result.length == 0) {
                $assignLogicGroup.find(".help-block").empty().text("无可用站点");

                art.dialog({
                    id: "treeDialog",
                    title: "分配站点组",
                    content: $assignLogicGroup[0],
                    fixed: true,
                    locked: true,
                    cancelValue: "取消",
                    cancel: function () {
                    }
                });
                return;
            }

            showAssignLogicGroupDialog(userId, result);

        }, "json");
    });

    function showAssignLogicGroupDialog(userId, nodes) {
        var $assignLogicGroup = $("#assignLogicGroup");

        // 初始化弹出框
        art.dialog({
            id: "treeDialog",
            title: "分配站点组",
            content: $assignLogicGroup[0],
            fixed: true,
            locked: true,
            okValue: "确定",
            ok: function () {
                var zTreeObj = $.fn.zTree.getZTreeObj("tree");
                var nodes = zTreeObj.getCheckedNodes(true);

                // TODO 此判断有问题, 添加站点有用。如果去站点就有问题了，所以先注释 return @gaohui 2013-05-13
                if (nodes.length == 0) {
                    $assignLogicGroup.find(".help-block").empty()
                        .append("请至少选择一个站点");
                    // return false;
                }
                var logicGroupIds = [];
                for (var i = 0; i < nodes.length; i++) {
                    logicGroupIds.push(nodes[i].id);
                }

                doAssignLogicGroup(userId, logicGroupIds);
                $assignLogicGroup.find(".help-block").empty();
            },
            cancelValue: "取消",
            cancel: function () {
                $assignLogicGroup.find(".help-block").empty();
            }
        });

        var setting = {
            check: {
                enable: true,
                chkboxType: {"Y": "", "N": ""}
            },
            view: {
                showLine: false
            },
            async: {
                enable: true,
                url: 'assignLogicGroup.action',
                autoParam: ["id=logicGroupId"],
                otherParam: {"userId": userId}
            }
        };
        // 初始化 zTree
        $.fn.zTree.init($('#tree'), setting, nodes);
    }

    /**
     * 分配用户站点组
     *
     * @param userId
     * @param logicGroupIds
     */
    function doAssignLogicGroup(userId, logicGroupIds) {
        $.ajaxSetup({traditional:true});

        $.post("doAssignLogicGroup.action", {userId: userId, logicGroupIds:logicGroupIds}, function (result) {
            var divMessage = "<div class='alert " +
                (result.success ? "alert-success'" : "alert-error'") +
                "> " +
                "<a class='close' data-dismiss='alert'>×</a>" +
                result.message +
                "</div>";
            $("#alert").empty().append(divMessage);
        }, "json");
    }
});