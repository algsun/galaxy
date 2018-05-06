/**
 * Created by LJF on 2014-07-21.
 */
var myApp = angular.module('app', ['ngSanitize']);

myApp.controller('ConfigController', ConfigController);
// 主控制器
function ConfigController($scope, $http) {
    $scope.uuid = "";
    $scope.officialId = "";

    // 初始化
    $scope.init = function (volumeCode) {
        $scope.volumeCode = volumeCode;
        $http.get('config').success(function (result) {
            if (result.success) {
                $scope.uuid = result.config.uuid;
                $scope.officialId = result.config.officialId;
            }
        });
    };

    // 保存或更新
    $scope.update = function () {
        var $configForm = $("#configForm");
        $configForm.validate({
            rules: {
                officialId: {
                    required: true,
                    number: true,
                    digits: true,
                    minlength: 9,
                    maxlength: 9
                }
            },
            messages: {
                officialId: {
                }
            },
            errorPlacement: function (error, element) {
                if (element.is(":radio")) {
                    error.appendTo(element.parent());
                } else if (element.is(":checkbox")) {
                    error.appendTo(element.parent());
                } else {
                    error.appendTo(element.next());
                }
            }
        });

        if (!$configForm.valid()) {
            return;
        }

        $http({
            url: 'config',
            method: "POST",
            data: $.param({uuid: $scope.uuid, officialId: $scope.officialId}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function (result) {
            if (result.success) {
                window.pnotify("保存档案全宗号成功", "info");
            } else {
                window.pnotify("保存档案全宗号失败", "warn");
            }
        })
    };

}

$(function () {
    var Cybertron = App("cybertron");
    Cybertron.volumeTree.selectVolumes(1);

});
