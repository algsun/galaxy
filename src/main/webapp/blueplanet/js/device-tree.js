/**
 * 左侧的设备树
 *
 * @author gaohui
 * @date 2012-12-26
 * @dependency jquery, ztree.core, ztree.exhide, ztree.exedit
 */
!function ($) {

    (function () {
        // 提供外部对树操作的接口
        var BluePlanet = App(window.BLUEPLANET);
        var deviceTree = BluePlanet.deviceTree = {
            treeId: "zone-device-tree",
            // 树初始化回调
            initCallbacks: $.Callbacks("once memory"),
            // 拖拽回调
            dropCallbacks: $.Callbacks(),
            // 单击回调
            _clickCallbacks: $.Callbacks(),
            // 返回 ztree 树对象
            getDeviceTree: function () {
                return $.fn.zTree.getZTreeObj(this.treeId);
            },
            // 选中设备
            selectDevice: function (locationId) {
                this.initCallbacks.add(function (treeObj) {
                    var treeNodes = treeObj.getNodesByFilter(function (node) {
                        if (!(node.type === "device" ||
                                node.type === "masterModule" ||
                                node.type === "slaveModule" ||
                                node.type === "controlModule" ||
                                node.type === "location"
                            )) {
                            return false;
                        }

                        return (node.locationId === locationId);
                    });
                    if (treeNodes.length == 0) {
                        return;
                    }

                    treeObj.selectNode(treeNodes[0]);
                });
            },
            // 选中区域
            selectZone: function (zoneId) {
                this.initCallbacks.add(function (treeObj) {
                    var treeNodes = treeObj.getNodesByFilter(function (node) {
                        if (node.type !== "zone") {
                            return false;
                        }

                        return (node.zoneId === zoneId);
                    });
                    if (treeNodes.length == 0) {
                        return;
                    }

                    treeObj.selectNode(treeNodes[0]);
                    treeObj.expandNode(treeNodes[0], true);
                });
            },
            // 打开未部署位置点
            expandUndeployed: function () {
                this.initCallbacks.add(function (treeObj) {
                    // 找出 "未部署设备" 分类位置点
                    var treeNodes = treeObj.getNodesByFilter(function (node) {
                        return (node.type === "undeployed");
                    });
                    if (treeNodes.length == 0) {
                        return;
                    }

                    treeObj.expandNode(treeNodes[0], true);
                });
            },
            expandUnbound: function () {
                this.initCallbacks.add(function (treeObj) {
                    // 找出 "未部署设备" 分类位置点
                    var treeNodes = treeObj.getNodesByFilter(function (node) {
                        return (node.type === "unbound");
                    });
                    if (treeNodes.length == 0) {
                        return;
                    }

                    treeObj.expandNode(treeNodes[0], true);
                });
            },
            // 单击回调
            click: function (callback) {
                this._clickCallbacks.add(function (event, treeId, treeNode, clickFlag) {
                    callback(event, treeNode);
                });
            },
            /**
             * 在树上绑定节点拖拽事件
             * @param id 指定拖放到目标的 dom id
             * @param callback 指定拖放后的回调. e 事件, treeId 树节点 id, treeNode 树节点
             */
            drop: function (id, callback) {
                this.dropCallbacks.add(function (e, treeId, treeNode, moveType) {
                    if (moveType) {
                        return;
                    }

                    if (id) { // 如果 id 不为空
                        if (e.target.id === id) {
                            callback(e, treeId, treeNode);
                        }
                    } else {
                        callback(e, treeId, treeNode);
                    }
                });
            },
            /**
             * 设置能否拖拽回调.
             * callback 会被传入 treeId 和 treeNodes, 返回 true 则可拖，反之亦然
             *
             * @param callback
             */
            beforeDrag: function (callback) {
                this._beforeDrag = callback;
            },
            // 拖拽树节点
            _onDrop: function (e, treeId, treeNodes, targetNode, moveType) {
                deviceTree.dropCallbacks.fire(e, treeId, treeNodes[0], moveType);
            },
            // 拖之前
            _onBeforeDrag: function (treeId, treeNodes) {
                if ($.isFunction(deviceTree._beforeDrag)) {
                    return deviceTree._beforeDrag(treeId, treeNodes);
                }

                return true;
            },
            // 双击树节点
            _onDbClickOnTree: function (event, treeId, treeNode) {
                var url = null;
                if (treeNode.type === "device" || treeNode.type === "masterModule" || treeNode.type === "slaveModule") {
                    //跳转到 设备概览
                    url = "device/" + treeNode.nodeId + "/detail";
                } else if (treeNode.type === "controlModule") {
                    url = "control-panel/" + treeNode.nodeId;
                } else if (treeNode.type === "zone") {
                    //跳转到 区域概览
                    url = "zone/" + treeNode.zoneId;
                } else if (treeNode.type === "location") {
                    // 跳转到 位置点概览
                    url = "location/" + treeNode.locationId;
                    if (treeNode.iconSkin === 'control-module') {
                        url = "control-panel/" + device.nodeId;
                    }
                } else if (treeNode.type === "site") {
                    //跳转到 站点实时数据
                    url = "summarize";
                }
                if (url) {
                    // 获取 base path, IE(8, 9) 下 base path 不起作用，需要手动加 base path
                    var basePath = $('base').attr('href');
                    if (basePath) {
                        url = basePath + url;
                    }
                    window.location.href = url;
                }
            },
            // ztree 单击
            _onClick: function (event, treeId, treeNode, clickFlag) {
                deviceTree._clickCallbacks.fire(event, treeId, treeNode, clickFlag);
            }
        };
    })();

    $(function () {
        var BluePlanet = App("blueplanet");
        var deviceTree = BluePlanet.deviceTree;

        var setting = {
            edit: {
                enable: true, //设置 zTree 是否处于编辑状态
                showRemoveBtn: false, //设置是否显示删除按钮
                showRenameBtn: false, //设置是否显示编辑名称按钮
                drag: {      // 拖拽
                    prev: false, //拖拽到目标节点时，设置是否允许移动到目标节点前面的操作
                    next: false, //拖拽到目标节点时，设置是否允许移动到目标节点后面的操作
                    inner: false //拖拽到目标节点时，设置是否允许成为目标节点的子节点。
                }
            },
            data: {
                keep: {
                    parent: true, //zTree 的节点父节点属性锁，是否始终保持 isParent = true
                    leaf: true   //zTree 的节点叶子节点属性锁，是否始终保持 isParent = false
                },
                simpleData: {
                    enable: true //简单数据模式
                },
                key: {
                    title: "description"
                }
            },
            callback: {
                beforeDrag: deviceTree._onBeforeDrag, //用于捕获节点被拖拽之前的事件回调函数，并且根据返回值确定是否允许开启拖拽操作
                onDrop: deviceTree._onDrop,   //用于捕获节点拖拽操作结束的事件回调函数
                onDblClick: deviceTree._onDbClickOnTree,
                onClick: deviceTree._onClick
            },
            view: {
                showLine: false,
                selectedMulti: true
            }
        };

        // 输入框按名称过虑节点
        var initNameFilter = function (deviceTree) {
            $("#device-tree-filter").keydown(function (e) {
                if (e.keyCode == "13") {
                    var keyword = $.trim($(this).val());
                    if (keyword.length == 0) {
                        //如果名称为空，清空选中状态
                        deviceTree.refresh();
                        return;
                    }

                    // 按名称搜
                    var nodes = deviceTree.getNodesByParamFuzzy("name", keyword);
                    // 按nodeId 搜索
                    var selectedNodesById = deviceTree.getNodesByParamFuzzy("shortNodeId", keyword);
                    // 过滤重复的数据, 有重复数据暂时不影响 @gaohui 2013-10-31
                    var uniqueNodes = [];
                    $.each(selectedNodesById, function (i, nodeById) {
                        var duplicated = false;
                        $.each(nodes, function (i, node) {
                            if (nodeById.tId === node.tId) {
                                duplicated = true;
                            }
                        });
                        if (!duplicated) {
                            uniqueNodes.push(nodeById);
                        }
                    });
                    // 合并
                    nodes = nodes.concat(uniqueNodes);
                    //倒序展开节点, 这样子第一个就会在上面显示了.
                    // (因为每展开一次节点，ztree 都要强制获取一次焦点。
                    // 如果正序展开，最后一个也就是最下面那个会显示，那么第一个不一定就显示出来了)
                    for (var nodeIndex = nodes.length - 1; nodeIndex >= 0; nodeIndex--) {
                        var node = nodes[nodeIndex];

                        //打开父节点
                        deviceTree.expandNode(node.getParentNode(), true);
                        // 如果是第一个，则单选(可以去掉之前的搜索选中状态); 其他多选
                        if (nodeIndex == (nodes.length - 1)) {
                            deviceTree.selectNode(node);
                        } else {
                            deviceTree.selectNode(node, true);
                        }
                    }

                    // ztree 在展开父节点时，会强走焦点，现在输入框要把焦点强回来
                    $(this).focus();
                }
            });
        };

        // 类型过虑, 符合类型的显示，不符合的隐藏
        var initSensorPhysicalIdFilter = function (tree) {
            // 返回所有的节点
            var findAllNodes = function () {
                return tree.getNodesByFilter(function (node) {
                    return true;
                });
            };
            // 返回所有设备, 从模块节点
            var findAllDeviceNodes = function () {
                return tree.getNodesByFilter(function (node) {
                    return (node.type && (node.type === "device" || node.type === "location" || node.type === "slaveModule"));
                });
            };

            // 隐藏所有节点
            var hideAllNodes = function () {
                tree.hideNodes(findAllNodes());
            };

            // 显示所有节点
            var showAllNodes = function () {
                tree.showNodes(findAllNodes());
            };

            // 根据监测指标过滤设备
            $("#zone-device-tree-sensorinfo-select").change(function () {
                showAllNodes();

                // 选择的监测指标
                var selectedSensor = $("option:selected", $(this)).val();
                selectedSensor = parseInt(selectedSensor);
                if (selectedSensor == -1) {
                    return;
                }

                // 隐藏所有设备节点
                tree.hideNodes(findAllDeviceNodes());

                // 返回不包含监测指标的设备节点
                var nodes = tree.getNodesByFilter(function (node) {
                    // 如果是设备, 或者从模块
                    if (node.type && (node.type === "device" || node.type === "location" || node.type === "slaveModule")) {
                        // 包含某监测指标
                        return ($.inArray(selectedSensor, node.sensorPhysicalIds) != -1);
                    }

                    return false;
                });

                // 显示拥有某监测指标的节点
                tree.showNodes(nodes);
            });
        };

        (function () {
            var BluePlanet = App("blueplanet");
            var deviceTree = BluePlanet.deviceTree;
            // 重新加载树
            deviceTree.reload = function (callback) {
                var that = this;
                // 初始化树
                $.get("zone-device-tree.json", function (zoneDeviceTree) {
                    $.fn.zTree.init($("#" + that.treeId), setting, zoneDeviceTree);

                    var treeObj = that.getDeviceTree();
                    initNameFilter(treeObj);
                    initSensorPhysicalIdFilter(treeObj);

                    // 触发回调
                    that.initCallbacks.fire(treeObj);
                    if (callback) {
                        callback();
                    }
                });
            };

            deviceTree.reload();
        })();

        // 收起，展开
        (function () {
            var Blueplanet = App(window.BLUEPLANET);
            var deviceTree = Blueplanet.deviceTree;
            // 收起
            $('#zone-device-tree-collapse-all').click(function () {
                var treeObject = deviceTree.getDeviceTree();
                var topNode = treeObject.getNodeByParam('id', 1); // 最顶级节点
                $.each(topNode.children, function (i, node) {
                    treeObject.expandNode(node, false, true); // 收起第二层节点
                });
                return false;
            });

            // 展开
            $('#zone-device-tree-expand-all').click(function () {
                deviceTree.getDeviceTree()
                    .expandAll(true);
                return false;
            });
        })();

        (function () {
            var Blueplanet = App(window.BLUEPLANET);
            // 站点
            var site = Blueplanet.site = Blueplanet.site || {};
            // 站点下的监测指标 sensorPhysicalId => sensorinfo
            site.sensorinfos = {};

            // 加载可用监测指标
            $.get("site/sensorinfoes.json", function (sensorinfoes) {
                var $sensorinfoSelect = $("#zone-device-tree-sensorinfo-select");
                $sensorinfoSelect.empty().append('<option value="-1"></option>');

                var i;
                for (i = 0; i < sensorinfoes.length; i++) {
                    var sensorinfo = sensorinfoes[i];
                    site.sensorinfos[sensorinfo.sensorPhysicalid] = sensorinfo;

                    var option = ['<option ',
                        'value="',
                        sensorinfo.sensorPhysicalid,
                        '">',
                        sensorinfo.cnName,
                        '</option>'].join('');

                    $sensorinfoSelect.append(option);
                }
            });
        })();
    });
}(jQuery);

