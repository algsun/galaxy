var myApp = angular.module('app', ['ngSanitize']);

myApp.controller('RecordControl', RecordController);
// 主控制器
function RecordController($scope, $http) {
    $scope.hasData = false;
    // 初始化
    $scope.init = function (volumeCode) {
        $scope.volumeCode = volumeCode;
        $scope._getData(volumeCode);

        // 每隔15分钟刷新一次页面
        var sched = later.parse.recur().every(15).minute();
        later.setInterval(function () {
            $scope._getData($scope.volumeCode);
        }, sched);

    };

    $scope.recordFilter = function (record) {
        if (angular.isUndefined($scope.searchText)) {
            return true;
        }
        if (record.name.indexOf($scope.searchText) != -1 ||
            record.recordCode.indexOf($scope.searchText) != -1 ||
            record.establishDate.indexOf($scope.searchText) != -1) {
            return true;
        }
        return false;
    };

    $scope.deleteRecord = function (recordUuid) {
        art.dialog({
            id: "mess",
            fixed: true,
            title: "消息提示",
            content: "确认要删除吗",
            okValue: "确定",
            ok: function () {
                $http.delete("records/" + recordUuid).success(function (result) {
                    if (result.success) {
                        var index = -1;
                        for (var i = 0; i < $scope.records.length; i++) {
                            var record = $scope.records[i];
                            if (recordUuid === record.uuid) {
                                index = i;
                                break;
                            }
                        }
                        if (index !== -1) {
                            $scope.records.splice(index, 1);
                        }
                        window.pnotify("删除档案成功", "info");
                    } else {
                        window.pnotify("删除档案失败", "warn");
                    }
                })
            },
            cancelValue: "取消",
            cancel: function () {
            }
        });

    };

    $scope._getData = function (volumeCode) {
        $http.get('volume/' + volumeCode + '/records.json').success(function (result) {
            if (result.success) {
                $scope.records = result.records;
                $scope.hasData = result.records.length > 0;
                angular.forEach($scope.records, function (record) {
                    record.stateSecrets = $scope._getStateSecrets(record.stateSecrets);
                });
            }
        });
    }

    $scope._getStateSecrets = function (stateSecrets) {
        switch (stateSecrets) {
            case 1:
                return "公开";
            case 2:
                return "国内";
            case 3:
                return "内部";
            case 4 :
                return "秘密";
            case 5:
                return "机密";
            case 6:
                return "绝密";
        }
    }
}

$(function () {
    var volumeTree = App("cybertron").volumeTree;
    volumeTree.click(function (event, treeNode) {
        var $mainScope = angular.element('[ng-controller="RecordController"]').scope();
        $.get('volume/' + treeNode.id + '/records.json', function (result) {
            if (result.success) {
                $mainScope.$apply(function () {
                    $mainScope.records = result.records;
                    $mainScope.hasData = result.records.length > 0;
                    angular.forEach($mainScope.records, function (record) {
                        record.stateSecrets = $mainScope._getStateSecrets(record.stateSecrets);
                    });
                });
            }
        });
        if (treeNode.id > 9) {
            $("#createRecord").attr("href", "addRecord-view/" + treeNode.id);
            $("#createRecord").show();
        } else {
            $("#createRecord").hide();
        }

    });

});