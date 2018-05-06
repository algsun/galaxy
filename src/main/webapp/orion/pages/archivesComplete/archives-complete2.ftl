<#--
档案填写

@author 王耕
@time  2015-9-14
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>档案填写 - 资产管理</title>

<#include "../_common/common-css.ftl">
<#include "/common/pages/common-tag.ftl">
    <link rel="stylesheet" href="../assets/jquery-ui/1.9.2/css/jquery-ui.css"/>
    <link rel="stylesheet" href="../assets/kinkeditor/4.1.7/themes/default/default.css"/>
    <style type="text/css">
        .list ul {
            margin: 0;
        }

        .list ul li {
            font-size: 14px;
            margin-top: 5px;
        }

        a {
            cursor: pointer;
        }

        /* table */
        td {
            width: 40px;
            height: 40px;
            text-align: center;
            vertical-align: middle;
        }

        table {
            width: 950px;
        }

        .b > td {
            height: 0;
            margin: 0;
            padding: 0;
        }

        .col1 {
            padding: 20px 0;
            width: 20px;
            margin: 0 auto;
        }

        .careful {
            width: 30px;
            margin: 0 auto;
            font-size: 30%;
            letter-spacing: 0;
        }

        .none-border {
            border: none;
        }

        .table-change {
            color: #00F;
            cursor: pointer;
        }

        .fileupload-box {
            position: relative;
            text-align: center;
        }

        .fileupload-txt {
            height: 22px;
            border: 1px solid #cdcdcd;
            width: 260px;
        }

        .fileupload-btn {
            background-color: #FFF;
            border: 1px solid #CDCDCD;
            height: 24px;
            width: 90px;
        }

        .fileupload-file {
            position: absolute;
            top: 0;
            right: 320px;
            height: 24px;
            filter: alpha(opacity:0);
            opacity: 0;
            width: 260px;
        }
    </style>
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
</head>
<body ng-app="archivesApp">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
<#-- 消息提示 -->
<#include "/common/pages/message-tooltip.ftl">
<@messageTooltip></@messageTooltip>
    <div class="row">
        <div class="span12">
            <fieldset>
                <legend>
                    <a class="go-back" href="queryRelic.action" title="返回">
                        <i class="mw-icon-prev"></i>档案填写
                    </a>
                </legend>
            </fieldset>
        </div>
    </div>
    <input type="hidden" id="repairRecordId" value="${repairRecordId!}"/>
    <input type="hidden" id="relicId" value="${relicId!}">


    <div class="row">
        <div id="tableChange" class="span12">
            <input type="hidden" id="institutionId" value="${institutionId!}">
            <input type="hidden" id="institutionRoomId" value="${institutionRoomId!}">
            <input type="hidden" id="actionName" value="${actionName!}"/>

            <div class="row" ng-controller="MainController">
                <div id="tableChange" class="span12" ng-view>
                    view
                </div>
                <div>
                    <div class="list p-5 m-l-40"
                         style="position: fixed;overflow:auto; height:450px;width: 250px; top: 100px;right:50px">
                        <ul style="text-align: center;">
                            <li>
                                <span class="table-change" address="base-info">文物基本信息表</span>
                                <a href="repairs/${repairRecordId}/archive/base-info">文物基本信息表</a>
                            </li>
                            <li>
                                <span class="table-change" address="design-and-repair-unit">单位信息记录表</span>
                            </li>
                            <li>
                                <span class="table-change" address="keep-status">文物保存现状表</span>
                            </li>
                            <li>
                                <span class="table-change" address="detection-analysis">文物检测分析表</span>
                            </li>
                            <li>
                                <span class="table-change" address="repair-record">修复过程记录表</span>
                            </li>
                            <li>
                            <#--<span class="table-change" address="draw-register">绘图登记表</span>-->
                                <a href="test">绘图登记表</a>
                                <a href="/">test</a>
                            </li>
                            <li>
                                <span class="table-change" address="image-datum">影像资料登记表</span>
                            </li>
                            <li>
                                <span class="table-change" address="assessment-acceptance">自评估与验收表</span>
                            </li>
                            <li>
                                <span id="all-tables" style="color: #00F;cursor: pointer;">预览所有表单</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

    <#include "../_common/footer.ftl">
    <#include "../_common/common-js.ftl">
        <script type="text/javascript"
                src="../assets/jquery-ui/1.9.2/js/jquery-ui.custom.min.js"></script>
        <script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
        <script type="text/javascript" src="../assets/kinkeditor/4.1.7/kindeditor-min.js"></script>
        <script type="text/javascript" src="../assets/kinkeditor/4.1.7/lang/zh_CN.js"></script>
    <@scriptTag "../assets/artDialog/5.0.1-7012746/artDialog.min.js"/>
        <script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
        <script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
        <script type="text/javascript" src="../assets/angular/1.4.7/angular.min.js"></script>
        <script type="text/javascript" src="../assets/angular/1.4.7/angular-route.min.js"></script>
        <script type="text/javascript" src="../assets/angular/1.4.7/angular-sanitize.min.js"></script>
        <script type="text/javascript" src="../assets/angular/1.4.7/i18n/angular-locale_zh-cn.js"></script>
        <script type="text/javascript" src="../assets/jquery-form-plugin/3.19/jquery.form.js"></script>
        <script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>

        <script type="text/javascript">
            var archivesApp = angular.module('archivesApp', ['ngRoute']);
            archivesApp
                    .config(['$routeProvider', '$locationProvider',
                        function ($routeProvider, $locationProvider) {
                            $routeProvider
                                    .when("",{
                                        templateUrl: "pages/_common/archivesTables/draw-register.ftl",
                                        controller: 'customersCtrl'
                                    })
                                    .when("",{
                                        templateUrl: "pages/_common/archivesTables/draw-register.ftl",
                                        controller: 'customersCtrl'
                                    })
                                    .when("",{
                                        templateUrl: "pages/_common/archivesTables/draw-register.ftl",
                                        controller: 'customersCtrl'
                                    })
                                    .when("",{
                                        templateUrl: "pages/_common/archivesTables/draw-register.ftl",
                                        controller: 'customersCtrl'
                                    })
                                    .when("",{
                                        templateUrl: "pages/_common/archivesTables/draw-register.ftl",
                                        controller: 'customersCtrl'
                                    })
                                    .when("",{
                                        templateUrl: "pages/_common/archivesTables/draw-register.ftl",
                                        controller: 'customersCtrl'
                                    })
                                    .when("/test", {
                                        templateUrl: "pages/_common/archivesTables/draw-register.ftl",
                                        controller: 'customersCtrl'
                                    })
                                    .otherwise({
                                        templateUrl: "pages/archivesComplete/a.html",
                                        controller: 'customersCtrl'
                                    });

                            $locationProvider.html5Mode(true);
                        }])
                    .controller('MainController', function ($scope, $route, $routeParams, $location) {
                        $scope.$route = $route;
                        $scope.$location = $location;
                        $scope.$routeParams = $routeParams;
                    })
                    .run(['$rootScope', '$location', function ($rootScope, $location) {
                        $rootScope.$on('$routeChangeError', function (evt, next, current) {
                        })
                    }]);
        </script>

    <@scriptTag "js/pnotify.js"/>
    <@scriptTag "js/archives/archive-tables.js"/>
    <@scriptTag "js/archives/draw-register.js"/>
    <@scriptTag "js/archives/image-datum.js"/>
    <@scriptTag "js/archives/assessment-acceptance.js"/>

        <script type="text/javascript">
            $(function () {
                var editor;
                (function () {
                    var KIND_EDITOR_CONFIG = {
                        resizeType: 1,
                        allowPreviewEmoticons: false,
                        allowImageUpload: true,
                        items: [
                            'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                            'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                            'insertunorderedlist', '|', 'emoticons', 'image', 'link', 'media']
                    };
                    KindEditor.ready(function (K) {
                        editor = K.create('#content', KIND_EDITOR_CONFIG);
                    });
                })();

                var repairRecordId = $("#repairRecordId").val();
                var relicId = $("#relicId").val();
                var actionName = $("#actionName").val();
                if (actionName.startsWith("base-info")) {
                    jsonForTable("base-info.json", "base-info");
                } else if (actionName == "all-tables") {
                    $(".includetable").hide();
                    $("#all-tables-browse").show();
                    $.get("archives/keep-status.json", {
                        relicId: relicId,
                        repairRecordId: repairRecordId
                    }, function (result) {
                        showStatusQuoPhotos(result, "allTableStatusQuoPhotos");
                    });
                } else {
                    jsonForTable(actionName + ".json", actionName);
                }

                $(".table-change").click(function () {
                    var $this = $(this);
                    var address = $this.attr("address");
                    $(".includetable").hide();
                    $("#" + address).show();
                    jsonForTable(address + ".json", address);
                });

                $("#all-tables").click(function () {
                    $(".includetable").hide();
                    $("#all-tables-browse").show();

                    var repairRecordId = $("#repairRecordId").val();
                    var relicId = $("#relicId").val();
                    location.href = "archives/allTableBrowse?repairRecordId=" + repairRecordId + "&relicId=" + relicId;
                });

                //基础信息表单提交  typea
                $("#baseInfoTypeaSubmit").click(function () {
                    var $reSize = $("#typeaRepairedSize");
                    var valueSize = $reSize.val();
                    $reSize.next().val(valueSize);


                    var $reWeight = $("#typeaRepairedWeight");
                    var valueWeight = $reWeight.val();
                    $reWeight.next().val(valueWeight);
                    var valueOfPic = $(".baseInfoImagesRow");
                    if (valueOfPic.length == 0) {
                        helpError($("#baseinfoPics-typea-help"), "至少需要一张照片");
                        return false;
                    } else {
                        helpOk($("#baseinfoPics-typea-help"), "");
                        $("#baseInfoTypeaForm").submit();
                    }
                });

                //基础信息表单提交  typeb
                $("#baseInfoTypebSubmit").click(function () {
                    var $reWeight = $("#typebRepairedWeight");
                    var valueWeight = $reWeight.val();
                    $reWeight.next().val(valueWeight);
                    var valueOfPic = $(".baseInfoImagesRow");
                    if (valueOfPic.length == 0) {
                        helpError($("#baseinfoPics-typeb-help"), "至少需要一张照片");
                        return false;
                    } else {
                        helpOk($("#baseinfoPics-typeb-help"), "");
                        $("#baseInfoTypebForm").submit();
                    }
                });

                //基础信息表单提交  typec
                $("#baseInfoTypecSubmit").click(function () {
                    var $reSize = $("#typecRepairedSize");
                    var valueSize = $reSize.val();
                    $reSize.next().val(valueSize);


                    var $reWeight = $("#typecRepairedWeight");
                    var valueWeight = $reWeight.val();
                    $reWeight.next().val(valueWeight);
                    var valueOfPic = $(".baseInfoImagesRow");
                    if (valueOfPic.length == 0) {
                        helpError($("#baseinfoPics-typec-help"), "至少需要一张照片");
                        return false;
                    } else {
                        helpOk($("#baseinfoPics-typec-help"), "");
                        $("#baseInfoTypecForm").submit();
                    }
                });

                //基础信息表单提交  typed
                $("#baseInfoTypedSubmit").click(function () {
                    var $reSize = $("#typedRepairedSize");
                    var valueSize = $reSize.val();
                    $reSize.next().val(valueSize);


                    var $reWeight = $("#typedRepairedWeight");
                    var valueWeight = $reWeight.val();
                    $reWeight.next().val(valueWeight);
                    var valueOfPic = $(".baseInfoImagesRow");
                    if (valueOfPic.length == 0) {
                        helpError($("#baseinfoPics-typed-help"), "至少需要一张照片");
                        return false;
                    } else {
                        helpOk($("#baseinfoPics-typed-help"), "");
                        $("#baseInfoTypedForm").submit();
                    }
                });

                $(".upload-file-btn").click(function () {
                    var $this = $(this);
                    $this.parent().parent().submit();
                });

                //文物保存现状表单提交
                $("#statusQuoSubmit").click(function () {
                    $("#statusQuoForm").submit();
                });
                //文物修复过程记录日志提交
                var repairRecordId = $("#repairRecordId").val();
                $("#repairLogSubmit").click(function () {
                    $("#hiddenLogObjId").val(repairRecordId);
                    $("#hiddenLogRepairId").val(repairRecordId);
                    $("#repairLogForm").submit();
                });

                $("#repairRecordConfirmRow .btn").click(function () {
                    $("#repairProcessRecordHiddenId").val(repairRecordId);
                    $("#repairRecordConfirmForm").submit();
                });

                $("#statusQuoForm").validate({
                    rules: {
                        'statusQuo.quoInfo': {
                            required: true,
                            maxlength: 500
                        }

                    },
                    messages: {
                        'statusQuo.quoInfo': {
                            required: "请输入现状及病害描述",
                            maxlength: jQuery.format("现状及病害描述不能大于{0}个字符")
                        }
                    }
                });
                $("#detectionAnalysisForm").validate({
                    rules: {
                        'detectionAnalysis.sampleName': {
                            required: true,
                            maxlength: 50
                        }
                    },
                    messages: {
                        'detectionAnalysis.sampleName': {
                            required: "请输入样品名称",
                            maxlength: jQuery.format("样品名称不能大于{0}个字符")
                        }
                    }
                });
                $("#myCarousel").carousel();
            });

            function jsonForTable(jsonName, actionName) {
                $(".includetable").hide();
                $("#" + actionName).show();
                var repairRecordId = $("#repairRecordId").val();
                var relicId = $("#relicId").val();

                if (actionName == "draw-register") {
                    var appElement = document.querySelector('[ng-controller=customersCtrl]');
                    var $scope = angular.element(appElement).scope();
                    $scope.drawRegisterFun();
                    $scope.$apply();
                } else if (actionName == "image-datum") {
                    var appElement = document.querySelector('[ng-controller=imageDatumCtrl]');
                    var $scope = angular.element(appElement).scope();
                    $scope.imageDatumFun();
                    $scope.$apply();
                } else {
                    $.get("archives/" + jsonName, {
                        repairRecordId: repairRecordId,
                        relicId: relicId,
                        actionName: actionName
                    }, function (result) {
                        if (result != null) {
                            if (result.actionName == "base-info-typea") {
                                writeBaseInfo(result, "typea");
                                getBaseInfoData("typea");
                            } else if (result.actionName == "base-info-typeb") {
                                writeBaseInfo(result, "typeb");
                                getBaseInfoData("typeb");
                            } else if (result.actionName == "base-info-typec") {
                                writeBaseInfo(result, "typec");
                                getBaseInfoData("typec");
                            } else if (result.actionName == "base-info-typed") {
                                writeBaseInfo(result, "typed");
                                getBaseInfoData("typed");
                            } else if (result.actionName == "design-and-repair-unit") {
                                writeUnitInfoTable(result.repairRecord);
                            } else if (result.actionName == "keep-status") {
                                writeStatusQuoTable(result);
                            } else if (result.actionName == "detection-analysis") {
                                writeDetectionAnalysisTable(result.detectionAnalysises, result.repairRecordId, result.relicId);
                            } else if (result.actionName == "repair-record") {
                                writeRepairRecordTable(result);
                            }
                        }
                    });
                }
            }

            //基础信息表,收藏单位,收藏库房,负责人,重量,修复后重量,尺寸,修复后尺寸数据获得
            function getBaseInfoData(type) {
                //收藏单位
                var institutionId = $("#institutionId").val();
                if (institutionId != "") {
                    $.post("institution/institutions/" + institutionId, function (institution) {
                        $("#" + type + "CollectionUnit").html(institution.name);
                    });
                }

                //收藏库房
                var institutionRoomId = $("#institutionRoomId").val();
                if (institutionRoomId != "") {
                    $.post("institutionRoom/institutionRooms/" + institutionRoomId, function (institutionRoom) {
                        $("#" + type + "CollectionRoom").html(institutionRoom.roomName);
                    });
                }
                //负责人
                var repairRecordId = $("#repairRecordId").val();
                $.post("archives/repairPerson.json", {repairRecordId: repairRecordId}, function (result) {
                    if (result.length > 0) {
                        $("#" + type + "RepairPerson").html(result);
                    }
                });
                //重量,修复后重量,尺寸,修复后尺寸
                var relicId = $("#relicId").val();
                $.get("archives/repairedSizeAndWeight.json", {
                    repairRecordId: repairRecordId,
                    relicId: relicId
                }, function (result) {
                    if (result != null) {
                        $("#" + type + "Size").val(result.size);
                        $("#" + type + "Weight").val(result.weight);
                        $("#" + type + "RepairedSize").val(result.repairedSize);
                        $("#" + type + "RepairedWeight").val(result.repairedWeight);
                        $("#" + type + "Craft").val(result.craft);
                        $("#" + type + "Kakemono").val(result.kakemono);
                        $("#" + type + "FrameStyle").val(result.frameStyle);
                        $("#" + type + "FrameMaterial").val(result.frameMaterial);
                        $("#" + type + "PackHead").val(result.packHead);
                        $("#" + type + "SpinningType").val(result.spinningType);
                        $("#" + type + "Craftsmanship").val(result.craftsmanship);
                        $("#" + type + "WeavingProcess").val(result.weavingProcess);
                        $("#" + type + "FabricPart").val(result.fabricPart);
                        $("#" + type + "DensityLong").val(result.densityLong);
                        $("#" + type + "DensityLat").val(result.densityLat);
                        $("#" + type + "ColorLong").val(result.colorLong);
                        $("#" + type + "ColorLat").val(result.colorLat);
                        $("#" + type + "Slender").val(result.slender);
                        $("#" + type + "TwistDirection").val(result.twistDirection);
                        $("#" + type + "TwistDegree").val(result.twistDegree);
                        $("#" + type + "Position").val(result.position);
                    }
                });
            }

            //错误提示
            var helpError = function ($help, msg) {
                var content = "<i class='mw-icon-cancel'></i> " + msg;
                $help.empty().append(content);
            };

            //正确提示
            var helpOk = function ($help, msg) {
                var content = "<i class='mw-icon-ok'></i> " + msg;
                $help.empty().append(content);
            };
        </script>
</body>
</html>