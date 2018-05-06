<#assign title=locale.getStr("blueplanet.controlPanel.controlActivitiesTitle")>

<#macro head>

</#macro>


<#include "/common/pages/common-tag.ftl">
<#include "/common/pages/pagging.ftl">
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
    <li>
        <a href="control-panel/${device.nodeId}">${locale.getStr("blueplanet.controlPanel.basic")}</a>
    </li>
    <li><a href="control-panel/${device.nodeId}/actions">${locale.getStr("blueplanet.controlPanel.action")}</a></li>
    <li class="active"><a href="control-panel/${device.nodeId}/activities">${locale.getStr("blueplanet.controlPanel.activity")}</a></li>
</ul>

<form class="form-inline well well-small" action="control-panel/${device.nodeId}/activities" method="post">
    <label>${locale.getStr("blueplanet.controlPanel.route")}</label>
    <select name="route" class="input-small">
        <@optionsTag [[-1, "${locale.getStr("blueplanet.controlPanel.all")}"], [1, 1], [2, 2], [3, 3], [4, 4]], route/>
    </select>

    <label>${locale.getStr("blueplanet.controlPanel.action")}</label>
    <select name="switchAction" class="input-small">
        <@optionsTag
            [[-1,"${locale.getStr("blueplanet.controlPanel.all")}"],
            [1, "${locale.getStr("common.open")}"],
            [0, "${locale.getStr("common.close")}"]], switchAction/>
    </select>

    <label>${locale.getStr("common.startDate")}</label>
    <input class="input-medium Wdate" id="start-time" type="text" name="startTime"
           onfocus="var endTime=$dp.$('end-time');WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00',onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'end-time\')}'})"
           value="${startTime?string("yyyy-MM-dd HH:mm:ss")!}"/>

    <label>${locale.getStr("common.endDate")}</label>
    <input class="input-medium Wdate" id="end-time" type="text" name="endTime"
           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59', minDate:'#F{$dp.$D(\'start-time\')}', maxDate:'%y-%M-{%d}'})"
           value="${endTime?string("yyyy-MM-dd HH:mm:ss")!}"/>

    <button class="btn" type="submit">${locale.getStr("common.select")}</button>
</form>

<table class="table table-bordered">
    <thead>
    <tr>
        <th>${locale.getStr("common.number")}</th>
        <th>${locale.getStr("blueplanet.controlPanel.route")}</th>
        <th>${locale.getStr("blueplanet.controlPanel.action")}</th>
        <th>${locale.getStr("blueplanet.controlPanel.actionType")}</th>
        <th>${locale.getStr("blueplanet.controlPanel.time")}</th>
    </tr>
    </thead>
    <tbody>

        <#if switchChanges.size() == 0>
        <tr>
            <td colspan="4">${locale.getStr("common.noData")}</td>
        </tr>
        </#if>

        <#list switchChanges as change>
        <tr>
            <td>${change_index + 1}</td>
            <td><strong>#${change.route}</strong></td>
            <td>
                <#if change.action == 1>${locale.getStr("common.open")}<#else>${locale.getStr("common.close")}</#if>
            </td>
            <td>
                <#if change.actionDriver== 1>
                    ${locale.getStr("blueplanet.controlPanel.deviceTriggers")}
                <#else>
                    ${locale.getStr("blueplanet.controlPanel.systemTriggers")}
                </#if>
            </td>
            <td>
                <span title="${change.timestampAfter?datetime}">
                    ${autoRelative(change.timestampAfter)}
                </span>
            </td>
        </tr>
        </#list>
    </tbody>

</table>

    <#assign url = "control-panel/" + deviceId + "/activities?route=" + route + "&action=" + switchAction + "&startTime=" + startTime?string("yyyy-MM-dd HH:mm") + "&endTime=" + endTime?string("yyyy-MM-dd HH:mm")>
    <@pagination url, page, pageSum/>

</#macro>


<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
</#macro>