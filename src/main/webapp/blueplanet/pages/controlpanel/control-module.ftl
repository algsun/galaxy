<#assign title=locale.getStr("blueplanet.controlPanel.controlModuleTitle")>

<#macro head>
<link rel="stylesheet" href="../assets/bootstrap-switch/2.0.1/css/bootstrap2/bootstrap-switch.min.css"/>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
    /* 控制开关 */
    .control-ports {
    }

    .control-ports li {
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .control-ports li .alias {
        margin-left: 10px;
    }

    /* 列表 */
    ul.list {
        border-top: 1px solid #ddd;
        list-style-type: none;
        margin-left: 0;
    }

    ul.list > li {
        border-bottom: 1px solid #ddd;
        padding-top: 10px;
        padding-bottom: 10px;
    }

    ul.list > li:hover {
        background-color: #fff;
    }

    .form-inline {
        display: inline-block;
    }

    .form-inline.hide {
        display: none;
    }

</style>
</#macro>


<#include "/common/pages/common-tag.ftl">
<#include "/common/pages/timeago.ftl">
<#include "../device/device-helper.ftl">
<#macro content>
<div class="row-fluid">

    <div class="span12">
    <h3><a class="f-n" href="control-panel">${locale.getStr("blueplanet.controlPanel")}</a> /
        <@typeIconOfDevice device.nodeType/> ${deviceId?substring(8)}
        <#if device.nodeName??>
            <span class="muted f-n" style="font-size: 0.7em;">${device.nodeName}</span></h3>
        </#if>
    </div>

</div>

<ul class="nav nav-tabs">
    <li class="active">
        <a href="control-panel/${device.nodeId}">${locale.getStr("blueplanet.controlPanel.basic")}</a>
    </li>
    <li><a href="control-panel/${device.nodeId}/actions"></a>${locale.getStr("blueplanet.controlPanel.action")}</li>
    <li><a href="control-panel/${device.nodeId}/activities">${locale.getStr("blueplanet.controlPanel.activity")}</a>
    </li>
</ul>

<div class="row-fluid">
    <div class="span5">
        <#if switches??>
            <h4>${locale.getStr("blueplanet.controlPanel.controlState")}</h4>


            <div class="well well-small">
                <div class="muted">
                    <i class="icon-time"></i><span
                        title="${device.stamp?string('yyyy-MM-dd HH:mm:ss')}">${autoRelative(device.stamp)}</span>
                    <i class=" icon-repeat"></i>${locale.getStr("blueplanet.controlPanel.workingPeriod")}
                    ：<@deviceWorkInterval device.interval/>
                    <@deviceState device />

                    <a class="pull-right muted"
                       href="device/${device.nodeId}/detail">${locale.getStr("blueplanet.controlPanel.detail")}</a>
                </div>

                <ul class="control-ports unstyled">
                    <#list switches.values as switch>
                        <#if switch.enable>
                            <li>
                                <strong class="m-r-10">#${switch.route}</strong>
                                <input class="switch-mini" type="checkbox" <@checked true, switch.onOff/>
                                       data-type="switch-button"
                                       data-on-label="${locale.getStr("common.open")}"
                                       data-off-label="${locale.getStr("common.close")}"
                                       data-route="${switch.route}"
                                       data-device-id="${device.nodeId}"
                                    <#if device.anomaly == -1||!security.isPermitted("blueplanet:monitor:controlPanel:edit")>
                                       disabled
                                    </#if>
                                >
                               <span data-role="alias">
                                   <#if aliasList??>
                                   <#list aliasList as alias>
                                       <#if alias.route==switch.route>
                                           <#assign aliasName = alias.alias>
                                       ${aliasName}
                                       </#if>
                                   </#list>
                                   </#if>
                               </span>
                                <#if security.isPermitted("blueplanet:monitor:controlPanel:edit")>
                                    <a class="alias" data-role="edit-button"
                                       href="javascript: void(0);">${locale.getStr("blueplanet.controlPanel.remark")}</a>

                                    <form class="form-inline hide" name="aliasForm"
                                          action="control-panel/${switches.nodeId}/alias" method="post"
                                          style="margin-bottom: 0;">
                                        <input type="hidden" name="route" value="${switch.route}"/>
                                        <input class="input-small" maxlength="50" name="alias"
                                               data-value="${aliasName!}" value="${aliasName!}">
                                        <button class="btn btn-mini" type="submit"
                                                name="btnSave">${locale.getStr("common.save")}</button>
                                        <a class="btn btn-mini"
                                           name="btnCancel">${locale.getStr("blueplanet.controlPanel.cancel")}</a>
                                    </form>
                                </#if>

                            </li>
                        </#if>
                    </#list>
                </ul>
            </div>
        </#if>



        <h4>${locale.getStr("blueplanet.controlPanel.childEquipment")}</h4>

        <table class="table">
            <thead>
            <tr>
                <th>${locale.getStr("blueplanet.controlPanel.deviceID")}</th>
                <th>${locale.getStr("blueplanet.controlPanel.name")}</th>
                <th>${locale.getStr("blueplanet.controlPanel.monitoringIndicators")}</th>
                <th>${locale.getStr("blueplanet.controlPanel.status")}</th>
            </tr>
            </thead>
            <tbody>
                <#if subDevices.size() == 0>
                <tr>
                    <td colspan="4">${locale.getStr("blueplanet.controlPanel.noEquipment")}</td>
                </tr>
                </#if>
                <#list subDevices as device>
                <tr>
                    <td>
                        <a href="device/${device.nodeId}"><@typeIconOfDevice device.nodeType/> ${device.nodeId?substring(8)}</a>
                    </td>
                    <td>${device.nodeName!}</td>
                    <td>
                        <#list device.sensors as sensor>
                            <#if sensor_index != 0>,</#if>
                        ${sensor.cnName}
                        </#list>
                    </td>
                    <td><@deviceState device/></td>
                </tr>
                </#list>
            </tbody>

        </table>
    </div>

    <div class="offset1 span6">
        <h4>${locale.getStr("blueplanet.controlPanel.recentActivity")} <a
                href="control-panel/${device.nodeId}/activities" class="f-n"
                style="font-size: 0.6em;">${locale.getStr("blueplanet.controlPanel.more")}</a></a>
        </h4>

        <ul class="list">
            <#list switchChanges as change>
                <li>
                    <span title="${change.timestampAfter?datetime}" class="il-blk"
                          style="min-width: 100px;">${autoRelative(change.timestampAfter)}</span>
                    <strong class="m-l-20" style="font-size: 1.3em;">#${change.route}</strong>
                    <span class="label m-l-20"><#if change.action == 1>${locale.getStr("common.open")}<#else>${locale.getStr("common.close")}</#if></span>
                </li>
            </#list>
        </ul>
    </div>
</div>

<div class="row-fluid">
    <div class="span12">
    </div>
</div>



</#macro>


<#macro script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
    <@scriptTag "js/pnotify.js"/>
<script type="text/javascript" src="../assets/bootstrap-switch/2.0.1/js/bootstrap-switch.min.js"></script>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");
        <#if deviceId??>
        BluePlanet.deviceTree.selectDevice("${deviceId}");
        </#if>
</script>
<script type="text/javascript">
    $(function () {
        $('input[data-type="switch-button"]').bootstrapSwitch()
                .bootstrapSwitch('setAnimated', false)
                .bootstrapSwitch('setOnClass', 'success')
                .on('switch-change', function (e) {
                    var onOff = $(this).is(':checked');
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
                                    window.pnotify("#" + route + message.commandSentSuccess + "，" + message.commandExecutionFailed, "warn");
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

        // 备注编辑状态切换.
        $("[data-role='edit-button'], [name='btnCancel']").click(function () {
            var $li = $(this).parents('li');
            // init show
            $li.find('[data-role="alias"]').toggle();
            // init show
            $li.find('[data-role="edit-button"]').toggle();
            // init hide
            $li.find('form').toggleClass('hide');
        });

    });
</script>
</#macro>