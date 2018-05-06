var myApp = angular.module('app', ['ngSanitize']);

myApp.controller('DetailController', DetailController);
// 主控制器
function DetailController($scope, $http) {
    // 初始化
    $scope.init = function (volumeCode, recordId) {
        $scope.volumeCode = volumeCode;
        $http.get('records/' + recordId).success(function (result) {
            if (result.success) {
                $scope.record = result.record;
                angular.forEach($scope.record.attachments, function (attachment) {
                    var nameSplitArray = attachment.uploadName.split(".");
                    var type= nameSplitArray[nameSplitArray.length-1];
                    if(type=="jpg"||type=="JPG"||type=="png"||type=="PNG"){
                        attachment.type ="jpg";
                    }else if(type=="pdf"){
                        attachment.type ="pdf";
                    }else if(type=="txt"||type=="TXT"){
                        attachment.type ="txt";
                    }else if(type=="doc"||type=="docx"){
                        attachment.type ="word";
                    } else if(type=="xls"||type=="xlsx"){
                        attachment.type ="excel";
                    }else{
                        attachment.type ="unknow";
                    }
                });
            }
        });
    };

    $scope.stateSecrets = function (stateSecrets) {
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
    var Cybertron = App("cybertron");
    var $mainScope = angular.element('[ng-controller="DetailController"]').scope();
    Cybertron.volumeTree.selectVolume($mainScope.volumeCode);
});
