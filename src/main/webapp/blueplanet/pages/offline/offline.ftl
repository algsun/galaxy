<#--
@author chen.yaofei
@date 2016.04.20
-->
<#assign title>位置点列表 - 离线数据</#assign>
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">

<#macro head>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
    .fileupload-file {
        position: absolute;
        top: 0;
        left: 0;
        height: 22px;
        filter: alpha(opacity:0);
        opacity: 0;
        margin-top: -10px;
    }
    .upload{
        float: right;
        margin-right: 30px
    }
    .add{
        float: right;
        margin-right: 10px
    }
</style>
</#macro>

<#macro content>
<#--<div id="gcontent" class="container m-t-10">-->
<div class="container">
    <h3>${locale.getStr("blueplanet.offline.location")}</h3>
    <div class="well well-small" style="height: 30px">
                <span class="inline-block">
                    <form class="form-inline" action="offline/offline.action" method="post">
                        <label class="control-label" style="margin-left: 30px">${locale.getStr("blueplanet.location.LocationName")}</label>
                        <input style="width: 150px;" name="locationName" type="text" value="${locationName!}">
                        <label class="control-label">${locale.getStr("common.zone")}</label>
                        <input class="zone" style="width: 150px" type="text" name="zoneName" value="${zoneName!}"
                               data-zoneId="">
                        <input type="hidden" name="zoneId" value="${zoneId!}"/>
                        <button id="submit-btn" class="btn"
                                type="submit">${locale.getStr("common.select")}</button>
                    </form>
                </span>
                <span class="inline-block upload">
                    <form id="fileForm" action="offline/readExcel" enctype="multipart/form-data" method="post">
                        <span style="position: relative">
                        <a type="input" class="btn btn-primary m-b-10">
                        ${locale.getStr("blueplanet.offline.importData")}
                        </a>
                        <input class="fileupload-file" type="File" style="width: 82px" name="readFile" id="fileFile">
                        </span>
                    </form>
                </span>
                <span class="inline-block add">
                    <a type="input" class="btn btn-primary m-b-10"
                       href="offline/addLocation">${locale.getStr("common.insert")}</a>
                </span>
    </div>
    <div>
        <#if locationList?? &&(locationList?size gt 0)>
            <table class="table table-bordered table-striped table-center">
                <thead>
                <tr>
                    <th>${locale.getStr("common.number")}</th>
                    <th>${locale.getStr("common.zone")}</th>
                    <th>${locale.getStr("common.name")}</th>
                    <th>${locale.getStr("common.time")}</th>
                    <th>${locale.getStr("common.operating")}</th>
                </tr>
                </thead>
                <tbody>

                    <#list locationList as location>
                    <tr>
                        <td>
                        ${location_index+1+(page-1)*10}
                        </td>
                        <td>
                            <#if  location.zone??>
                                 ${location.zone.zoneName}
                             </#if>
                        </td>
                        <td>
                        ${location.locationName}
                        </td>
                        <td>
                        ${location.createTime?string('yyyy-MM-dd HH:mm')}
                        </td>
                        <td>
                            <#if security.isPermitted("blueplanet:offline:location:update")>
                                <a class="btn btn-success btn-mini" href="offline/updateLocation/${location.id}">
                                    <i class="icon-white icon-pencil"></i>${locale.getStr("common.update")}
                                </a>
                            </#if>
                            <#if security.isPermitted("blueplanet:offline:location:delete")>
                                    <a class="btn btn-mini btn-danger" title="${locale.getStr("blueplanet.location.deleteLocation")}"
                                       data-button="deleteLocation" data-value="${location.id}"><i class="icon-trash icon-white"></i>${locale.getStr("common.delete")}</a>
                            </#if>
                            <#if security.isPermitted("blueplanet:offline:data:edit")>
                                <a class="btn btn-info btn-mini"
                                   href="offline/batchDataView.action?locationId=${location.id}">
                                    <i class="icon-white icon-list"></i>
                                ${locale.getStr("common.data")}
                                </a>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
            <#include "/common/pages/pagging.ftl" >
            <#assign url = "offline/offline.action?locationName=${locationName!}&zoneId=${zoneId!}&zoneName=${zoneName!}">
            <@pagination url, page, pageSum,"page"/>
        <#else>
        ${locale.getStr("common.noData")}
        </#if>
    </div>
    <#include "delete-offline-location.ftl">
</div>
<div class="hide">
    <div id="zoneTreeDialog" class="span4">
        <div id="zoneTree" class="ztree" style="overflow: auto;height: 400px"></div>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>
</#macro>

<#macro script>
    <@scriptTag "../assets/artDialog/5.0.1-7012746/artDialog.min.js"/>
    <@scriptTag "../assets/jquery-validation/1.11.1/dist/jquery.validate.js"/>
    <@scriptTag "../assets/jquery-validation/1.11.1/localization/messages_zh.js"/>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="js/pnotify.js"></script>
<script type="text/javascript">
    $("#fileFile").change(function () {
        $("#fileForm").submit();
    });
    //删除验证
    //刷新验证码
    var $verifyCodeImage = $("#verifyCodeImage");
    $("#verifyCodeImage,#refreshVerifyCodeImage").click(function () {
        $verifyCodeImage.attr("src", "verifyCode.action?name=deleteOfflineLocation&t=" + new Date().getTime());
        return false;
    });
    //关闭对话框
    $("#closeModal").click(function () {
        $("#mainModal").modal('hide');
        $("#deleteVerifyCode").val("");
        $("#deleteVerifyMessage").text("");
    });
    //删除按钮
    var locationId;
    var $this
    $("a[data-button='deleteLocation']").click(function () {
        $this = $(this);
        locationId = $(this).data("value");
        $('#mainModal').modal('show');
        $("#verifyCodeImage").attr("src", "verifyCode.action?name=deleteOfflineLocation&t=" + new Date().getTime());
    });

    //提交
    $("#deleteConfirm").click(function () {
        var verifyCodeText = $("#deleteVerifyCode").val().trim();
        var $verifyMessage = $("#deleteVerifyMessage");
        var $verifyCode = $("#deleteVerifyCode");
        if (verifyCodeText == "") {
            $verifyMessage.text(message.inputVerificationCode);
            return;
        }

        var url = "offline/delete?locationId=" + locationId + "&verifyCode=" + verifyCodeText;
        $.getJSON(url, function (data) {
            if (data.codeFlag) {
                if (data.deleteFlag) {
//                     window.location.href = "offline/offline.action";
                    $("#mainModal").modal('hide');
                    $("#deleteVerifyCode").val("");
                    $("#deleteVerifyMessage").text("");
                    $this.parent().parent().remove();
                    window.pnotify(message.deleteLocationSuccess, "info");
                } else {
                    $("#mainModal").modal('hide');
                    $("#deleteVerifyCode").val("");
                    $("#deleteVerifyMessage").text("");
                    window.pnotify(message.deleteLocationFailed, "error");
                }
//
            } else {
                $verifyCode.val("");
                $verifyMessage.text(message.verificationCodeError);
                $("#verifyCodeImage").attr("src", "verifyCode.action?name=deleteOfflineLocation&t=" + new Date().getTime());
            }
        });
    });

    //选择区域
    // ztree 树配置
    var setting = {
        view: {
            showLine: false
        },
        async: {
            enable: true,
            url: '../blackhole/zone/children.json',
            autoParam: ["id=zoneId"]
        }
    };

    var showDialog = function ($zoneInput, zoneTree) {
        var $help = $("#zoneTreeDialog .help-block");
        art.dialog({
            id: "zoneTreeDialog",
            title: message.selectZone,
            content: $("#zoneTreeDialog")[0],
            fixed: true,
            okValue: message.ok,
            ok: function () {
                var nodes = zoneTree.getSelectedNodes();
                if (nodes.length == 0) {
                    $help.empty().append(message.pleaseSelectZone);
                    return false;
                }
                var node = nodes[0];
                $zoneInput.val(node.name);
                $zoneInput.data("zoneId", node.id);
                $zoneInput.next().val(node.id);
            },
            cancelValue: message.cancel,
            cancel: function () {
                $help.empty();
            },
            button: [
                {
                    value: message.clear,
                    callback: function () {
                        $zoneInput.val('');
                        $zoneInput.next().val('');
                    }
                }
            ]
        });
    };

    // 区域输入框获取焦点时
    $(".zone").focus(function () {
        var $this = $(this);
        $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
            // 初始化树
            $.fn.zTree.init($('#zoneTree'), setting, result);
            var zoneTree = $.fn.zTree.getZTreeObj("zoneTree");

            // 初始化弹出框
            showDialog($this, zoneTree);
        });
    });
</script>
</#macro>