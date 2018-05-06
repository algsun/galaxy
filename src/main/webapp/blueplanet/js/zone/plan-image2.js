/**
 * 平面图样式
 *
 * @author gaohui
 * @date 2013-10-09
 * @dependency angular.js, jQuery
 */
var ZoneImageOption;
window.ZoneImageOption = ZoneImageOption = {
    // 浏览模式还是编辑模式, 默认是浏览模式
    editMode: false
};

var myApp = angular.module('app', ['ngSanitize']);

myApp.controller('MainController', MainController);
// 主控制器
function MainController($scope, $http) {
    // 是否正在部署
    $scope.deploying = false;
    // 正在部署的位置点
    $scope.deployLocation = null;

    $scope.zIndex = 1;

    // 节点列表
    $scope.nodes = [];

    // 初始化
    // 参数：
    // zoneId 区域Id
    // hasPlanImage 是否有区域平面图
    $scope.init = function (zoneId, hasPlanImage) {
        // 获取 zoneId @gaohui 2013-10-10
        $scope.zoneId = zoneId;
        $scope.hasPlanImage = hasPlanImage;

        // 如果没有区域平面图，直接返回
        if (!hasPlanImage) {
            return;
        }

        // 加载区域与设备
        $http.get('zone/' + $scope.zoneId + '/children.json').success(function (result) {
            var $unDeployScope = getUnDeployScope();

            angular.forEach(result.locations, function (location) {
                var locationNode = {
                    id: location.id,
                    type: (location.type === 0 ? 'location' : 'offline-location'),
                    name: location.locationName,
                    x: location.coordinateX,
                    y: location.coordinateY,
                    zIndex: 1,
                    nodeName: location.locationName,
                    device: location.device,
                    nodeId: (location.device == null ? null : location.device.nodeId.substr(location.device.nodeId.length - 5, 5)),
                    zoneId: location.zoneId,
                    localLocation: location.localLocation,
                    photo:location.photo
                };
                if ($scope._isDeployed(location)) {
                    $scope.nodes.push(locationNode);
                } else {
                    // 添加到未部署列表
                    $unDeployScope.undeployedNodes.push(locationNode);
                }
            });

            angular.forEach(result.zones, function (zone) {
                var zoneNode = {
                    id: zone.zoneId,
                    type: 'zone',
                    name: zone.zoneName,
                    x: zone.coordinateX,
                    y: zone.coordinateY,
                    zIndex: 'auto',
                    parentId: zoneId
                };
                if ($scope._isDeployed(zone)) {
                    $scope.nodes.push(zoneNode);
                } else {
                    // 加载到未部署列表
                    $unDeployScope.undeployedNodes.push(zoneNode);
                }
            });
        });

        // 加载摄像机点位
        $http.get('../proxima/zone/' + $scope.zoneId + '/dv-places.json').success(function (results) {
            angular.forEach(results, function (dvPlace) {
                var dvPlaceNode = {
                    id: dvPlace.id,
                    type: 'dvPlace',
                    name: dvPlace.placeName,
                    x: dvPlace.coordinateX,
                    y: dvPlace.coordinateY,
                    zIndex: 'auto'
                };
                if ($scope._isDeployed(dvPlace) || $scope._isLocal(dvPlace)) {
                    $scope.nodes.push(dvPlaceNode);
                } else {
                    var $unDeployScope = getUnDeployScope();
                    $unDeployScope.undeployedNodes.push(dvPlaceNode);
                }
            });
        });
    };

    // 切换节点选中
    $scope.toggleSelect = function (node) {
        if ($scope._isNodeMoving(node)) {
            return;
        }
        angular.forEach($scope.nodes, function (nodes) {
            nodes.selected = false;
        });
        node.selected = !node.selected;
    };

    // 返回对应的图标路径
    $scope.iconOfNode = function (node) {
        if (node.type === 'zone') {
            return 'images/icon/house-24.png';
        } else if (node.type === 'dvPlace') {
            return 'images/icon/camera-24.png';
        } else if (node.type === 'offline-location') {
            return 'images/icon/offline-location-24.png';
        } else if (node.type === 'location') {
            if (node.device == null) {
                return 'images/icon/location-24.png';
            }
            return $scope._iconOfDevice(node);
        } else {
        }
    };

    // 开始移动. node
    $scope.startMove = function ($event, node) {
        if (!ZoneImageOption.editMode) {
            return;
        }
        node.temp = {
            // 开始移动
            startMove: true,
            // 是否正在移动
            moving: false,
            startX: $event.pageX,
            startY: $event.pageY,
            nodeX: node.x,
            nodeY: node.y
        };
        node.zIndex = $scope.zIndex;
        node.showTooltip = false;

        $scope._movingNode = node;
        $scope.zIndex += 1;
    };

    // 移动节点. container
    $scope.movingNode = function ($event) {
        if (!ZoneImageOption.editMode) {
            return;
        }
        if ($scope._isMoving()) {
            var node = $scope._movingNode;
            var position = $scope._nextPosition($event);
            node.x = position.x;
            node.y = position.y;
        }
    };

    // 结束移动. container
    $scope.endMove = function ($event) {
        if (!ZoneImageOption.editMode) {
            return;
        }
        var node = $scope._movingNode;
        if (node) {
            node.temp.moving = false;
            $scope._movingNode = null;
            $scope._updateCoordinate(node);
        }
    };

    // node
    $scope.nodeMouseMove = function ($event, node) {
        // 0 为无鼠标事件，1 为点击，3 为右键
        if ($event.which != undefined && $event.which != 1) {
            return;
        }
        // 判断是否满足移动条件(先点下鼠标，然后移动)
        if (node && node.temp && node.temp.startMove) {
            node.temp.moving = true;
        }

        // 检查边界. node
        if ($scope._isMoving()) {
            var position = $scope._nextPosition($event);
            if (position.x < 0) {
                $event.stopPropagation();
                return;
            }
            if (position.y < 0) {
                $event.stopPropagation();
                return;
            }
            if ((position.x + 24) > $scope._containerSize().width) {
                $event.stopPropagation();
                return;
            }
            if ((position.y + 24) > $scope._containerSize().height) {
                $event.stopPropagation();
                return;
            }
        }
    };

    // node
    $scope.nodeMouseEnter = function ($event, node) {
        node.showTooltip = true;
        // node.tempZindex = node.zIndex;
        // node.zIndex = 999;
        if (node.type === 'location') {
            if (node.device == null) {
                return;
            }
            switch (node.device.nodeType) {
                // 网关，中继
                case 2:
                case 5:
                case 7:
                    $http.get('device/' + node.device.nodeId + '/detail.json').success(function (result) {
                        node.lowvoltage = result.lowvoltage;
                        node.anomaly = result.anomaly;
                        node.rssi = result.rssi;
                        node.lqi = result.lqi;
                        node.stamp = result.stamp.replace('T', ' ');
                    });
                    break;

                // 节点，主模块，从模块
                case 1:
                case 3:
                case 4:
                    $http.get('location/' + node.id + '/realtime-data.json?count=1').success(function (results) {
                        if (results.length <= 0) {
                            return;
                        }

                        var result = results[0];
                        node.lowvoltage = result.lowvoltage;
                        node.anomaly = result.anomaly;
                        node.rssi = result.rssi;
                        node.lqi = result.lqi;
                        node.stamp = result.stamp.replace('T', ' ');
                        node.sensors = result.sensorInfoMap;
                    });
                    break;
            }
        }
    };

    // node
    $scope.nodeMouseLeave = function ($event, node) {
        node.showTooltip = false;
    };

    // 是否显示tooltip. node
    $scope.nodeShowTooltip = function (node) {
        // 如果节点正在移动，不显示 tooltip
        if ($scope._isNodeMoving(node)) {
            return false;
        }
        return node.showTooltip;
    };

    // 停止事件传播
    $scope.stopProp = function ($event) {
        $event.stopPropagation();
    };

    // 双击事件
    $scope.nodeDoubleClick = function (node) {
        if (ZoneImageOption.editMode) {
            return;
        }
        if (node.type === "location" || node.type === 'offline-location') {
            window.location.href = "location/" + node.id;
        } else if (node.type === "zone") {
            window.location.href = "zone/" + node.id + "/plan-image";
        }
    };

    // 取消部署位置点
    $scope.unDeployLocation = function () {
        if (!ZoneImageOption.editMode) {
            return;
        }

        var selectedLocationId = "";
        angular.forEach($scope.nodes, function (node) {
            if (node.type === 'location' && node.selected) {
                selectedLocationId = node.id;
            }
        });

        if (selectedLocationId === "") {
            window.pnotify(message.selectLocation, "warn");
            return;
        }

        $.ajaxSetup({traditional: true});
        $.post('location/' + selectedLocationId + '/unDeploy', {}, function (result) {
            if (result.success) {
                removeNode($scope.nodes, {id: selectedLocationId});
                // 重新加载设备树
                var blueplanet = App("blueplanet");
                blueplanet.deviceTree.reload(function () {
                    blueplanet.deviceTree.selectZone($scope.zoneId);
                    blueplanet.deviceTree.expandUndeployed();
                    blueplanet.deviceTree.expandUnbound();
                });
            }
        });
    };

    // 取消显示设备
    $scope.unShowLocation = function () {
        if (!ZoneImageOption.editMode) {
            return;
        }

        var selectedLocationId = "";
        angular.forEach($scope.nodes, function (node) {
            if (node.type === 'location' && node.selected) {
                selectedLocationId = node.id;
            }
        });
        if (selectedLocationId === "") {
            window.pnotify(message.selectDevice, "warn");
            return;
        }

        $.ajaxSetup({traditional: true});
        $.post('location/' + selectedLocationId + '/unShow', {zoneId: $scope.zoneId}, function (result) {
            if (result.success) {
                var remove = removeNode($scope.nodes, {id: selectedLocationId});
                for (var i = 0; i < remove.length; i++) {
                    var $unDeployScope = getUnDeployScope();
                    $unDeployScope.undeployedNodes.push(remove[i]);
                }
                // 重新加载设备树
                var blueplanet = App("blueplanet");
                blueplanet.deviceTree.reload(function () {
                    blueplanet.deviceTree.selectZone($scope.zoneId);
                    blueplanet.deviceTree.expandUndeployed();
                    blueplanet.deviceTree.expandUnbound();
                });
            }
        });
    };

    // 未部署的节点移动
    $scope.unDeployNodeMoving = function ($event) {
        if ($scope.deploying && $scope.deployLocation) {
            // 节点位置相对于鼠标位置有一个 (-10, -30) 的
            var x = $event.pageX - 10;
            var y = $event.pageY - 30;
            $scope.deployLocation.x = x;
            $scope.deployLocation.y = y;
            $scope.deployEle.css({left: x, top: y});
        }
    };

    $scope.unDeployNodeMouseUp = function ($event) {
        if ($scope.deploying && $scope.deployLocation) {
            if ($event.target.id === 'zone-image') {
                // 将 deployNode 从 undeployedNodes 转移到 nodes
                var node = angular.copy($scope.deployLocation);
                var position = $scope._containerPosition();
                node.x = node.x - position.x;
                node.y = node.y - position.y;
                $scope._updateCoordinate(node);
                $scope.nodes.push(node);

                var $unDeployScope = getUnDeployScope();
                removeNode($unDeployScope.undeployedNodes, node);
            }
        }

        // 清除被拖拽节点临时变量
        $scope.deploying = false;
        $scope.deployLocation = null;
        angular.element('.node-drag').remove();
    };

    // 添加节点
    $scope.addNode = function (node, event, deviceTree) {
        var position = $scope._containerPosition();
        node.x = event.pageX - position.x;
        node.y = event.pageY - position.y;

        // 绑定并更新坐标 @gaohui 2014-03-17
        $scope._updateCoordinate(node, function () {
            $scope.nodes.push(node);
            deviceTree.reload(function () {
                deviceTree.selectZone($scope.zoneId);
                deviceTree.expandUndeployed();
                deviceTree.expandUnbound();
            });
        });
    };

// 返回编辑模式
    $scope.editMode = function () {
        return ZoneImageOption.editMode;
    };

// 取消部署
    $scope.showUnDeploy = function () {
        var node;
        for (var i = 0; i < $scope.nodes.length; i++) {
            if ($scope.nodes[i].selected) {
                node = $scope.nodes[i];
            }
        }
        if (node == null) {
            return true;
        } else {
            return $scope._isLocal(node);
        }
    };

// 取消显示
    $scope.showUnShow = function () {
        var node;
        for (var i = 0; i < $scope.nodes.length; i++) {
            if ($scope.nodes[i].selected) {
                node = $scope.nodes[i];
            }
        }
        if (node == null) {
            return true;
        } else {
            return !$scope._isLocal(node);
        }
    };

    $scope.nodeStyle = function (node) {
        // return 'top: ' + node.y + 'px; left: ' + node.x + 'px; z-index: ' +  node.zIndex + ';';
        return {
            top: node.y + 'px',
            left: node.x + 'px'
        };
    };

// 工作状态
    $scope.anomaly = function (node) {
        if (node.device != null) {
            switch (node.device.anomaly) {
                case -1:
                    return message.overtime;
                case 0:
                    return message.normal;
                case 1:
                    return message.lowVoltage;
                case 2:
                    return message.powerDown;
                default :
                    return message.normal;
            }
        }
    };

    /**
     * private
     */
        // 是否已部署
    $scope._isDeployed = function (node) {
        return !(node.coordinateX < 0 || node.coordinateY < 0);
    };

// 是否是本区域的设备
    $scope._isLocal = function (node) {
        return node.localLocation;
    };

// 返回 container 大小
    $scope._containerSize = function () {
        var $container = $('.zone-image');
        return {width: $container.width(), height: $container.height()};
    };

// 返回 container 位置
    $scope._containerPosition = function () {
        var $container = $('.zone-image');
        var offset = $container.offset();
        return {x: offset.left, y: offset.top};
    };

// 是否有节点正在移动
    $scope._isMoving = function () {
        return $scope._isNodeMoving($scope._movingNode);
    };

// 节点是否正在移动
    $scope._isNodeMoving = function (node) {
        return (node && node.temp && node.temp.moving);
    };

    $scope._iconOfDevice = function (node) {
        var deviceType = "";
        switch (node.device.nodeType) {
            case 1:
                deviceType = "node";
                break;
            case 2:
                deviceType = "relay";
                break;
            case 3:
                deviceType = "master-module";
                break;
            case 4:
                deviceType = "node";
                break;
            case 5:
                deviceType = "control-module";
                break;
            case 7:
                deviceType = "router";
                break;
        }
        if (node.device.anomaly == -1) {
            return 'images/icon/' + deviceType + '-24-red.png';
        } else if (node.device.anomaly == 0) {
            return 'images/icon/' + deviceType + '-24.png';
        } else if (node.device.anomaly == 1 || node.device.anomaly == 2) {
            return 'images/icon/' + deviceType + '-24-yellow.png';
        }
    };

// 下一个位置
    $scope._nextPosition = function ($event) {
        if ($scope._isMoving()) {
            var node = $scope._movingNode;
            var x = $event.pageX - node.temp.startX;
            var y = $event.pageY - node.temp.startY;
            return {
                x: node.temp.nodeX + x,
                y: node.temp.nodeY + y
            };
        }

        return null;
    };

    // 更新坐标
    $scope._updateCoordinate = function (node, callback) {
        var processCallback = function (result, callback) {
            if (result.success) {
                if (callback) {
                    callback();
                }
            } else {
                window.pnotify(message.operationFailed, "warn");
            }
        };
        if (node.type === 'zone') {
            $scope._httpPost($http, 'zone/' + node.id + '/updateZoneCoordinate.json', {
                x: node.x,
                y: node.y,
                parentZoneId: node.parentId,
                zoneId: $scope.zoneId
            }, function (result) {
                processCallback(result, callback);
            });
        } else if (node.type === 'dvPlace') {
            $scope._httpPost($http, '../proxima/dv-place/' + node.id + '/updateCoordinate.json', {
                x: node.x,
                y: node.y,
                zoneId: $scope.zoneId
            }, function (result) {
                processCallback(result, callback);
            });
        } else if (node.type === 'location') {
            $scope._httpPost($http, 'location/' + node.id + '/updateCoordinate.json', {
                x: node.x,
                y: node.y,
                zoneId: $scope.zoneId
            }, function (result) {
                processCallback(result, callback);
            });
        }
    };

    $scope._httpPost = function ($http, url, param, successCallback) {
        $http({
            url: url,
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: $.param(param)
        }).success(successCallback);
    };
}

function UndeployedNodesController($scope) {
    // 未部署节点列表
    $scope.undeployedNodes = [
    /**
     {id: '1', name: '区域', type: 'zone', x: 10, y: 10, zIndex: 'auto'},
     {id: '2', name: '设备', type: 'device', x: 200, y: 100, zIndex: 'auto'}
     */
    ];

    $scope.nodeMouseDown = function ($event, node) {
        if (!ZoneImageOption.editMode) {
            return;
        }

        node.temp = {
            mouseDown: true,
            draging: false
        };
    };

    $scope.nodeMouseMove = function ($event, node) {
        if (!ZoneImageOption.editMode) {
            return;
        }

        if (node && node.temp && node.temp.mouseDown && !node.temp.draging) {
            node.temp.draging = true;

            // 拖拽节点
            var $drag = angular.element('<span class="node-drag" style="position: absolute;"></span>')
                .text(node.name);
            angular.element('body').append($drag);

            var $mainScope = angular.element("[ng-controller='MainController']").scope();
            $mainScope.deployLocation = node;
            $mainScope.deploying = true;
            $mainScope.deployEle = $drag;
        }
    };

    // 节点排序，根据返回数字大小从大到小排序
    $scope.orderNodes = function (node) {
        // 区域
        if (node.type === 'zone') {
            return 30;
        }

        // 摄像机点位
        if (node.type === 'dvPlace') {
            return 20;
        }

        // 设备(网关，中继，主模块，从模块，节点)
        if (node.type === 'location') {
            if (node.device == null) return;

            switch (node.nodeType) {
                case 7:
                    return 19;
                case 2:
                    return 18;
                case 5:
                    return 17;
                case 3:
                    return 16;
                case 4:
                    return 14;
                case 1:
                    return 11;
            }
        }
    };
}

function getUnDeployScope() {
    return angular.element('[ng-controller="UndeployedNodesController"]').scope();
}

// 删除节点
function removeNode(nodes, node) {
    var removeNodes = [];
    var index = -1;
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].id === node.id) {
            index = i;
            removeNodes.push(nodes[i]);
            break;
        }
    }

    if (index !== -1) {
        nodes.splice(index, 1);
    }
    return removeNodes;
}

window.Images = {
    naturalSize: function (imgSrc, callback, errorCallback) {
        jQuery("<img/>").load(function () {
                var naturalWidth = this.width;
                var naturalHeight = this.height;
                callback(naturalWidth, naturalHeight);
            })
            .error(errorCallback)
            .attr("src", imgSrc);
    }
};

$(function () {
    // 从 background-image 中获取 url, 参考 http://stackoverflow.com/a/16513369/829551 @gaohui 2013-10-11
    var imageUrl = $('.zone-image').css('background-image');
    imageUrl = /url\(\s*(['"]?)(.*?)\1\s*\)/g.exec(imageUrl)[2];
    Images.naturalSize(imageUrl, function (width, height) {
        // 初始化大小
        $('.zone-image').css({width: width, height: height});
    }, function () {
        var $mainScope = angular.element('[ng-controller="MainController"]').scope();
        $mainScope.$apply(function () {
            $mainScope.loadImageError = true;
        });
    });

    $(document).bind('selectstart', function () {
        return false;
    });

    // 编辑模式, 从设备树拖拽部署
    if (ZoneImageOption.editMode) {
        var deviceTree = App("blueplanet").deviceTree;
        deviceTree.expandUndeployed();
        deviceTree.expandUnbound();

        deviceTree.beforeDrag(function (treeId, treeNodes) {
            var type = treeNodes[0].type;
            if ($.inArray(type, ['device', 'repeator', 'gateway', 'masterModule', 'slaveModule', 'controlModule', 'location']) != -1) {
                // 只有未部署的设备可以拖 @gaohui
                //因为原来的数据库roomid字段默认为“0”，鉴于中间件修改较麻烦，故在此判断
                return treeNodes[0].zoneId === "0" || !treeNodes[0].zoneId;
            } else {
                return false;
            }
        });
        deviceTree.drop("zone-image", function (e, treeId, treeNode) {
            var $mainScope = angular.element('[ng-controller="MainController"]').scope();

            // 如果是设备，添加位置点失败后退出
            if ($.inArray(treeNode.type, ['device', 'repeator', 'gateway', 'masterModule', 'slaveModule', 'controlModule']) != -1) {
                $.post('locations', {
                    zoneId: $mainScope.zoneId,
                    nodeId: treeNode.nodeId,
                    locationName: treeNode.name
                }, function (result) {
                    if (result.success) {
                        var location = result.location;
                        var locationNode = {
                            id: location.id,
                            type: 'location',
                            iconSkin: location.iconSkin,
                            name: location.locationName,
                            zIndex: 'auto',
                            nodeName: location.locationName,
                            zoneId: location.zoneId,
                            device: location.device,
                            localLocation: true
                        };
                        $mainScope.addNode(locationNode, e, deviceTree);
                    }
                });
            } else if (treeNode.type === 'location') {
                var locationNode = {
                    id: treeNode.locationId,
                    type: treeNode.iconSkin,
                    name: treeNode.locationName,
                    zIndex: 'auto',
                    nodeName: treeNode.locationName,
                    zoneId: $mainScope.zoneId,
                    device: treeNode.device,
                    localLocation: true
                };
                $mainScope.addNode(locationNode, e, deviceTree);
            }
        });
    }
});