<#assign title=locale.getStr("blueplanet.controlPanel.controlPanelTitle")>

<#macro head>
<link rel="stylesheet" href="../assets/bootstrap-switch/2.0.1/css/bootstrap2/bootstrap-switch.min.css"/>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
    .control-ports {
    }

    .control-ports li {
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .control-ports li .alias {
        margin-left: 10px;
    }
</style>
</#macro>


<#include "/common/pages/common-tag.ftl">
<#include "/common/pages/timeago.ftl">
<#include "../device/device-helper.ftl">
<#macro content>
<div class="row-fluid">

    <div class="span12">
        <h3 class="pull-left">${locale.getStr("blueplanet.controlPanel")}</h3>
        <#if devices??>
            <a class="btn  btn-link m-t-20 m-l-10 "
               href="control-panel/notification">${locale.getStr("blueplanet.controlPanel.notificationSettings")}</a>
        </#if>
    </div>

</div>

    <#list devices as device>

        <#assign newRow = false >
        <#if device_index % 4 == 0>
            <#assign newRow = true>
        </#if>

        <#if newRow>
            <#if device_index != 0>
            </ul>
            </div>
            </div>
            </#if>
        <div class="row-fluid">
        <div class="span12">
        <ul class="thumbnails">
        </#if>



        <li class="span3">
            <div class="thumbnail">
                <div style="padding: 5px 0; border-bottom: 1px solid #ddd;">
                    <a style="font-size: 1.4em;" href="control-panel/${device.nodeId}">
                        <@typeIconOfDevice device.nodeType/>
                    ${device.nodeId?substring(8)}
                        <#if device.nodeName??>
                            <span class="muted f-n" style="font-size: 0.6em;">${device.nodeName}</span>
                        </#if>
                    </a>

                    <a class="pull-right muted"
                       href="device/${device.nodeId}/detail">${locale.getStr("blueplanet.controlPanel.detail")}</a>
                </div>

                <div class="muted">
                    <i class="icon-time"></i><span
                        title="${device.stamp?string('yyyy-MM-dd HH:mm:ss')}">${autoRelative(device.stamp)}</span>
                    <@deviceState device />
                </div>

                <ul class="control-ports unstyled">
                    <#if switchesMap.get(device.nodeId)??>
                        <#assign switches = switchesMap.get(device.nodeId)>

                        <#list switches.values as switch>
                            <#if switch.enable>
                                <li>
                                    <strong>#${switch.route}</strong>
                                    <input class="switch-mini" type="checkbox" <@checked true, switch.onOff/>
                                           data-device-id="${switches.nodeId}"
                                           data-type="switch-button"
                                           data-on-label="${locale.getStr("common.open")}"
                                           data-off-label="${locale.getStr("common.close")}"
                                           data-route="${switch.route}"
                                        <#if device.anomaly == -1||!security.isPermitted("blueplanet:monitor:controlPanel:edit")>
                                           disabled
                                        </#if>
                                    >
                                    <span>
                                        <#if aliasMap.get(device.nodeId)??>
                                         <#assign aliases = aliasMap.get(device.nodeId)>
                                        <#list aliases as alias>
                                            <#if alias.route==switch.route>
                                            ${alias.alias}
                                            </#if>
                                        </#list>
                                        </#if>
                                    </span>
                                </li>
                            </#if>
                        </#list>
                    </#if>
                </ul>

            </div>
        </li>

    </#list>
</ul>
</div>
</div>
</#macro>


<#macro script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
    <@scriptTag "js/pnotify.js"/>
<script type="text/javascript" src="../assets/bootstrap-switch/2.0.1/js/bootstrap-switch.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('input[data-type="switch-button"]').bootstrapSwitch()
                .bootstrapSwitch('setAnimated', false)
                .bootstrapSwitch('setOnClass', 'success')
                .on('switch-change', function (e) {
                    var onOff = !!( $(this).attr("checked") == "checked");
                    var route = $(this).data('route');
                    var deviceId = $(this).data('device-id');
                    var $this = $(this);
                    $.post("control-panel/switch", {
                        deviceId: deviceId,
                        route: route,
                        onOrOff: onOff
                    }, function (result) {
                        var map = result.success;
                        if (map != null && map.success) {
                            if (map.sendSuccess) {
                                if (map.doSuccess) {
                                    window.pnotify("#" + route + message.commandCompletedSuccess, "info");
                                } else {
                                    window.pnotify("#" + route + message.commandCompletedSuccess + "，" + message.commandExecutionFailed, "warn");
                                    $this.bootstrapSwitch('toggleState', true);
                                }

                            } else {
                                window.pnotify("#" + route + message.commandSentFailed, "warn");
                                $this.bootstrapSwitch('toggleState', true);
                            }
                        } else {
                            window.pnotify(message.error, "warn");
                            $this.bootstrapSwitch('toggleState', true);
                        }
                    });

                });

    });
</script>
</#macro>
