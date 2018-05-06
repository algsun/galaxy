<#--
位置点查询

@author xuyuexi
@date 2014-06-23
-->

<#assign title=locale.getStr("blueplanet.location.locationManagement")>
<#include "/common/pages/common-tag.ftl">

<#macro head>

<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>

</#macro>


<#include "../../device/device-helper.ftl">
<#macro content>

<div class="row-fluid m-t-10">
    <div class="span12">
        <form class="form-inline well well-small" action="../blueplanet/queryLocationsBy" method="post">
            <label>${locale.getStr("blueplanet.location.LocationName")}</label>

            <div class="input-prepend">
                <input class="input-small" name="locationName" type="text" value="${locationName!}">
            </div>
            <label>${locale.getStr("common.zone")}</label>
            <input class="input-medium zone" type="text" name="zoneName" value="${zoneName!}" data-zoneId="">
            <input type="hidden" name="zoneId" value="${zoneId!}"/>
            <button class="btn" type="submit">${locale.getStr("common.select")}</button>
            <div class="pull-right">
                <span id="addLocationButton" class="btn" style="margin-right: 20px;">${locale.getStr("blueplanet.location.addLocation")}</span>
                <label>${locale.getStr("common.zone")}</label>
                <input class="input-medium zone" type="text" value="" data-zoneId="">
                <input type="hidden"/>
                <span id="bindDeviceButton" class="btn">${locale.getStr("blueplanet.location.deployLocation")}</span>
            </div>
        </form>
        <#if locationList?size == 0>
            <h4>${locale.getStr("common.noData")}</h4>
        <#else>
            <table class="table table-bordered table-striped table-hover">
                <thead>
                <tr>
                    <th><label class="checkbox"><input type="checkbox">${locale.getStr("common.all")}</label></th>
                    <th>${locale.getStr("common.number")}</th>
                    <th>${locale.getStr("common.zone")}</th>
                    <th>${locale.getStr("blueplanet.location.name")}</th>
                    <th>${locale.getStr("blueplanet.location.bindingEquipment")}</th>
                    <th>${locale.getStr("blueplanet.location.creationTime")}</th>
                    <th>${locale.getStr("common.operating")}</th>
                </tr>
                </thead>
                <tbody>
                    <#list locationList as location>
                    <tr>
                        <td>
                            <input name="locationId" type="checkbox" value="${location.id}" class="locationId"/>
                        </td>
                        <td>
                        ${location_index+1}
                        </td>
                        <td>
                            <#if location.zone??>
                          ${location.zone.zoneName!}
                        </#if>
                        </td>
                        <td>
                        ${location.locationName}
                           <#if location.type==1><span class="icon-tag pull-right"></span></#if>
                        </td>
                        <td>
                            <#if location.device??>
                              <#if location.device.nodeName??>
                            ${location.device.nodeName}
                            <#else>
                            ${location.device.nodeId}
                            </#if>
                            </#if>
                        </td>
                        <td>
                        ${location.createTime?string("yyyy-MM-dd HH:mm:ss")!}
                        </td>
                        <td>
                            <#if security.isPermitted("blueplanet:manage:location:edit")>
                                <a id="editLocation" class="btn btn-mini"
                                   href="toEditLocation?zoneId=${location.zoneId!}&locationId=${location.id}&page=${page}<#if location.device??>&nodeId=${location.device.nodeId}</#if>">
                                ${locale.getStr("blueplanet.location.edit")}
                                </a>
                            </#if>
                            <input type="hidden" value="${location.id}"/>
                            <input type="hidden" value="${location.locationName}"/>
                            <#if security.isPermitted("blueplanet:manage:location:delete")>
                                <a class="btn btn-mini btn-danger" href="" title="${locale.getStr("blueplanet.location.deleteLocation")}"
                                   data-button="deleteLocation" data-value="${location.id}">${locale.getStr("common.delete")}</a>
                            </#if>
                            <#if location.zone??>
                                <a class="btn btn-mini btn btn-info"
                                   href="unDeployLocation?locationId=${location.id}&page=${page}&locationName=${locationName!}&zoneId=${zoneId!}&zoneName=${zoneName!}"
                                   title="${locale.getStr("blueplanet.location.undeployed")}"
                                   data-button="confirm"
                                   data-message="${locale.getStr("blueplanet.location.confirmUndeployment")}？">${locale.getStr("blueplanet.location.undeployed")}</a>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>

        <#include "../../_common/pagging.ftl">
        <#assign pagingUrl = "queryLocationsBy?locationName=${locationName!}&zoneId=${zoneId!}&zoneName=${zoneName!}">
        <@pagination pagingUrl, page, pageSum/>

        <#include "../../location/manage/delete-location.ftl">
    </div>
</div>

<form id="deployLocationForm" action="deployLocation" method="post">
    <input id="page" name="page" value="${page}" type="hidden"/>
    <input id="locationIds" name="locationIds" type="hidden"/>
    <input name="locationName" type="hidden" value="${locationName!}"/>
    <input name="zoneId" type="hidden" value="${zoneId!}"/>
    <input name="zoneName" type="hidden" value="${zoneName!}"/>
    <input id="bindZoneId" name="bindZoneId" type="hidden" value="${bindZoneId!}"/>
</form>

<div class="hide">
    <div id="zoneTreeDialog" class="span4" style="height:400px;overflow:auto">
        <div id="zoneTree" class="ztree"></div>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
    <@scriptTag "js/pnotify.js"/>
<script type="text/javascript">
    $(function () {
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

        $("#bindDeviceButton").click(function () {
            var $this = $(this);
            var $zoneId = $this.prev().prev();
            var zoneId = $zoneId.data("zoneId");
            if (!zoneId) {
                art.dialog({title: message.tips, content: message.pleaseSelectZone});
                return;
            }

            var locationIds = [];
            var $locationIds = $("tbody input[type='checkbox']:checked");
            $locationIds.each(function () {
                locationIds.push($(this).val());
            });

            if (locationIds.length == 0) {
                art.dialog({title: message.tips, content: message.pleaseSelectLocationToBind});
                return;
            }
            $("#bindZoneId").val(zoneId);
            $("#locationIds").val(locationIds);
            $("#deployLocationForm").submit();
        });

        var locationId;
        var $this;
        $("a[data-button='deleteLocation']").click(function () {
            $('#deleteLocationModal').modal('show');
            $("#verifyCodeImage").attr("src", "verifyCode.action?name=deleteLocation&t=" + new Date().getTime());
            $this = $(this);
            locationId = $this.attr("data-value");
            return false;
        });

        $("#deleteConfirm").click(function () {
            var verifyCodeText = $("#verifyCode").val();
            var $verifyMessage = $("#verifyMessage");
            var $verifyCode = $("#verifyCode");
            if (verifyCodeText == "") {
                $verifyMessage.text(message.inputVerificationCode);
                return;
            }

            var url = "deleteLocation?locationId=" + locationId + "&verifyCode=" + verifyCodeText;
            $.getJSON(url, function (result) {
                if (result.codeSuccess) {
                    $verifyCode.val("");
                    $verifyMessage.text("");
                    if (result.deleteSuccess) {
                        window.pnotify(message.deleteLocationSuccess, "info");
                        $this.parent().parent().remove();
                    } else {
                        window.pnotify(message.deleteLocationFailed, "error");
                    }
                    $('#deleteLocationModal').modal('hide');
                    var page = 1;
                    page = result.page;
                } else {
                    $verifyCode.val("");
                    $verifyMessage.text(message.verificationCodeError);
                    $("#verifyCodeImage").attr("src", "verifyCode.action?name=deleteDevice&t=" + new Date().getTime());
                }
            });
        });
        $("#verifyCodeImage,#refreshVerifyCodeImageButton").click(function () {
            $("#verifyCodeImage").attr("src", "verifyCode.action?name=deleteLocation&t=" + new Date().getTime());
            return false;
        });

        $("#closeLocation").click(function () {
            $('#deleteLocationModal').modal('hide');
            $("#verifyCode").val("");
            $("#verifyMessage").text("");
        });

        var $allCheck = $("th input[type='checkbox']");
        $allCheck.change(function () {
            if (this.checked) {
                $locationIds.attr("checked", "checked");
            } else {
                $locationIds.removeAttr("checked");
            }
        });
        var $locationIds = $(".locationId");
        $locationIds.click(function () {
            var isChecked = true;
            for (var i = 0; i < $locationIds.length; i++) {
                if (!$locationIds[i].checked) {
                    isChecked = false;
                }
            }
            if (isChecked) {
                $allCheck.attr("checked", "checked");
            } else {
                $allCheck.removeAttr("checked");
            }
        });

        $("#addLocationButton").click(function () {
            window.location.href = "toAddLocation";
        });

        // 解除设备绑定
        (function () {
            $("a[data-button='confirm']").click(function () {
                var $this = $(this);
                var message = $this.attr("data-message");
                var href = $this.attr("href");
                var okCallback = function () {
                    window.location.href = href;
                };
                art.dialog({
                    id: "unDeployLocation",
                    title: message.tips,
                    content: message,
                    okValue: message.ok,
                    ok: okCallback,
                    cancelValue: message.cancel,
                    cancel: function () {
                    }
                });
                return false;
            });
        })();

    });


</script>
</#macro>