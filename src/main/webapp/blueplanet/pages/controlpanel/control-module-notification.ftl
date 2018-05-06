<#assign title=locale.getStr("blueplanet.controlPanel.controlSettingTitle")>

<#macro head>

</#macro>


<#include "/common/pages/common-tag.ftl">
<#include "/common/pages/pagging.ftl">
<#include "/common/pages/timeago.ftl">
<#include "../device/device-helper.ftl">
<#macro content>
<div class="row-fluid">
    <h3><a class="f-n" href="control-panel">${locale.getStr("blueplanet.controlPanel")}</a>
        <span class="muted f-n"
              style="font-size: 0.7em;">/${locale.getStr("blueplanet.controlPanel.notificationSettings")}</span></h3>
</div>
<form action="control-panel/notification/config" id="configForm">
<span class="span12">
<div class="well well-small">
    <div class="m-l-20">
        <label class="radio inline">
            <input name="subscribeType" id="allDevice" type="radio" value="1" <#if notification??>
                   <#if notification.subscribeType==2>checked="false"
                   <#else>checked="true"</#if><#else>checked="true"</#if> >${locale.getStr("blueplanet.controlPanel.allDevice")}
        </label>
        <label class="radio inline">
            <input style="margin-left: 20px" id="customize" name="subscribeType" type="radio" value="2"
                <#if notification??>
                   <#if notification.subscribeType==2>checked="true"</#if></#if>>${locale.getStr("blueplanet.controlPanel.customDevice")}
        </label>
    </div>
</div>
    <div class="well well-small <#if !notification??>hide<#elseif notification.deviceIds?size lt 1>hide</#if> "
         id="customizeDiv">
        <div>
            <span id="control">${locale.getStr("blueplanet.controlPanel.selectControlModule")}</span>
        </div>
        <div>
            <table id="" class="table table-bordered" style="max-width:400px ;max-height: 280px; overflow: auto;">
                <thead>
                <tr>
                    <th><input type="checkbox" id="selectAll"/></th>
                    <th>${locale.getStr("common.number")}</th>
                    <th>${locale.getStr("blueplanet.controlPanel.controlModule")}</th>
                </tr>
                </thead>
                <tbody>
                    <#list devices as device>
                    <tr>
                        <td><input type="checkbox" name="deviceList"
                                   value="${device.nodeId}"
                                   class="checks"
                            <#if notification??>
                                <#list notification.deviceIds as sclectedId>
                                    <#if sclectedId == device.nodeId>
                                   checked="true"
                                    </#if>
                                </#list>
                            </#if>
                        /></td>
                        <td>${device_index+1}</td>
                        <td>
                            <#if device.nodeName??>
                          ${device.nodeName}
                        <#else>
                            ${device.nodeId?substring(8)}
                            </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
    <div class="well well-small">
        <div>
            <span id="event">${locale.getStr("blueplanet.controlPanel.sendEvents")}：</span>
        </div>
        <div class="m-l-20">
            <label class="checkbox pull-left">
                <input name="triggerEvent" type="checkbox" value="1" class="trigger" <#if notification??>
                    <#if notification.triggerEvent ==1|| notification.triggerEvent ==3>
                       checked="true"
                    </#if>
                </#if>>${locale.getStr("blueplanet.controlPanel.batteryPowered")}
            </label>
            <label class="checkbox">
                <input style="margin-left: 42px" name="triggerEvent" type="checkbox" class="trigger"
                       value="2"<#if notification??>
                    <#if notification.triggerEvent ==2|| notification.triggerEvent ==3>
                       checked="true"
                    </#if>
                </#if>>${locale.getStr("blueplanet.controlPanel.switch")}
            </label>
            <span id="triggerError" style="color: red"></span>
        </div>
    </div>
     <div class="well well-small">
         <div>
             <span id="notice">${locale.getStr("blueplanet.controlPanel.notified")}：</span>
         </div>
         <div class="m-l-20">
             <label class="checkbox pull-left">
                 <input name="notifyMethod" type="checkbox" value="1" class="notify"
                     <#if notification??>
                         <#if notification.notifyMethod ==1|| notification.notifyMethod ==3>
                        checked="true"
                         </#if>
                     </#if>
                         >${locale.getStr("blueplanet.controlPanel.sms")}
             </label>
             <label class="checkbox">
                 <input style="margin-left: 70px" name="notifyMethod" type="checkbox" value="2" class="notify"
                     <#if notification??>
                         <#if notification.notifyMethod ==2|| notification.notifyMethod ==3>
                        checked="true"
                         </#if>
                     </#if>
                         >${locale.getStr("blueplanet.controlPanel.mail")}
             </label>
             <span id="notifyError" style="color: red"></span>
         </div>
     </div>
    <div>
        <button class="btn" id="submitBtn">${locale.getStr("common.save")}</button>
    </div>
</span>
</form>
    <#include "../_common/common-js.ftl">
</#macro>


<#macro script>
    <@scriptTag "../assets/jquery-validation/1.11.1/dist/jquery.validate.js"/>
    <@scriptTag "../assets/jquery-validation/1.11.1/localization/messages_zh.js"/>
<script type="text/javascript">

    $(function () {

        jQuery.validator.addMethod("checks", function (value, elem) {
            var customize = $("#customize").attr("checked");
            if ($(".checks:checked").length == 0 && customize == 'checked') {
                return false;
            } else {
                return true;
            }
        }, message.pleaseSelectDevice);

        jQuery.validator.addMethod("atLeastOne", function (value, elem) {
            if ($(".trigger:checked").length == 0) {
                return false;
            } else {
                return true;
            }
        }, message.required);

        jQuery.validator.addMethod("atLeastOne1", function (value, elem) {
            if ($(".notify:checked").length == 0) {
                return false;
            } else {
                return true;
            }
        }, message.required);

        $("#customize").click(function () {
            $("#customizeDiv").show();
        });

        $("#allDevice").click(function () {
            $("#customizeDiv").hide();
        });

        $("#selectAll").click(function () {
            if ('checked' == $("#selectAll").attr("checked")) {
                $("[name='deviceList']").attr("checked", true);
            } else {
                $("[name='deviceList']").attr("checked", false);
            }
        });

        $("[name='deviceList']").click(function () {
            var deviceId = $("[name='deviceList']");
            for (var i = 0; i < deviceId.length; i++) {
                if (deviceId[i].checked == false) {
                    $("#selectAll").attr("checked", false);
                    return;
                }
            }
            $("#selectAll").attr("checked", true);
        });

        $("#submitBtn").click(function () {
            var triggerEvent = $("[name='triggerEvent']");
            var notifyMethod = $("[name='notifyMethod']");

            $("#configForm").validate({
                rules: {
                    deviceList: "checks",
                    triggerEvent: "atLeastOne",
                    notifyMethod: "atLeastOne1"
                },
                errorPlacement: function (error, element) {
                    if (element.is(".checks"))
                        error.appendTo($("#control"));
                    else if (element.is(".notify"))
                        error.appendTo($("#notice"));
                    else if (element.is(".trigger"))
                        error.appendTo($("#event"));
                    else
                        error.insertAfter(element);
                }
            });
            $("#configForm").submit();

        });
    })
    ;
</script>
</#macro>