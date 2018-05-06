/**
 * 左侧的卷树
 *
 * @author liuzhu
 * @date 2014-7-17
 * @dependency jquery, ztree.core, ztree.exhide, ztree.exedit
 */

(function () {
    // 提供外部对树操作的接口
    var Cybertron = App(window.CYBERTRON);
    var volumeTree = Cybertron.volumeTree = {
        treeId: "volume-tree",
        // 树初始化回调
        initCallbacks: $.Callbacks("once memory"),
        // 单击回调
        _clickCallbacks: $.Callbacks(),
        // 返回 ztree 树对象
        getVolumeTree: function () {
            return $.fn.zTree.getZTreeObj(this.treeId);
        },
        // 选中卷(子卷)
        selectVolume: function (nodeId) {
            this.initCallbacks.add(function (treeObj) {
                var treeNodes = treeObj.getNodesByFilter(function (node) {
                    return (node.id === nodeId);
                });
                if (treeNodes.length == 0) {
                    return;
                }
                treeObj.expandAll(false);
                treeObj.selectNode(treeNodes[0]);
            });
        },
        // 选中卷(父卷)
        selectVolumes: function (zoneId) {
            this.initCallbacks.add(function (treeObj) {
                var treeNodes = treeObj.getNodesByFilter(function (node) {
                    return (node.id === zoneId);
                });
                if (treeNodes.length == 0) {
                    return;
                }
                treeObj.expandAll(false);
                treeObj.expandNode(treeNodes[0], true);
            });
        },
        // 单击回调
        click: function (callback) {
            this._clickCallbacks.add(function (event, treeId, treeNode, clickFlag) {
                callback(event, treeNode);
            });
        },
        // ztree 单击
        _onClick: function (event, treeId, treeNode, clickFlag) {
            if (!treeNode.open) {
                if (treeNode.isParent) {
                    var treeObj = volumeTree.getVolumeTree();
                    treeObj.expandAll(false);
                    treeObj.expandNode(treeNode, true, true, true);
                }
            }

            volumeTree._clickCallbacks.fire(event, treeId, treeNode, clickFlag);
        }
    };
})();

$(function () {
    var Cybertron = App("cybertron");
    var volumeTree = Cybertron.volumeTree;
    var setting = {
        view: {
            showLine: true,
            selectedMulti: false,
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onNodeCreated: this.onNodeCreated,
            onClick: volumeTree._onClick
        }
    };

    $(function () {
        var Cybertron = App("cybertron");
        var volumeTree = Cybertron.volumeTree;
        // 重新加载树
        volumeTree.reload = function (callback) {
            var that = this;
            // 初始化树
            $.get("find-volume-tree.json", function (volumeTreeData) {
                $.fn.zTree.init($("#" + that.treeId), setting, volumeTreeData);

                var treeObj = that.getVolumeTree();
                // 触发回调
                that.initCallbacks.fire(treeObj);
                if (callback) {
                    callback();
                }
            });
        };
        volumeTree.reload();
    });
});

