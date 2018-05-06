<#--
报警记录
author liuzhu
@date 2014-04-11
-->

<#assign title= locale.getStr("blueplanet.alarm.alarmRecordTitle")>

<#macro head>
<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
</#macro>

<#macro content>
<div class="container-fluid">
    <form class="form-inline well well-small" style="margin-top: 10px;" action="alarm/history" method="post">
        <input type="hidden" name="sensorHide" value="${sensorId!0}">
        <input type="hidden" name="measureHide" value="${measureId!0}">
        <div class="row-fluid">
            <div class="span3">
                <label class="inline-block m-t-5">${locale.getStr("blueplanet.alarm.alarmFactors")}</label>
                <select id="sensorId" name="sensorId" style="width: 164px; ">
                    <option value="0"> ${locale.getStr("common.all")}</option>
                    <#list sensorinfoVOs as sensor>
                        <option value="${sensor.sensorPhysicalid}" >${sensor.cnName}</option>
                    </#list>
                </select>
            </div>
            <div class="span3">
                <label class="inline-block">${locale.getStr("common.startDate")}</label>
                <input id="start-time" class="input-medium" name="startTime" type="text"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'end-time\')}'})"
                       value="${(startTime?string("yyyy-MM-dd HH:mm"))!}"/>
            </div>
            <div class="span3">
                <label class="inline-block">${locale.getStr("common.endDate")}</label>
                <input id="end-time" class="input-medium" name="endTime" type="text"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'start-time\')}'})"
                       value="${(endTime?string("yyyy-MM-dd HH:mm"))!}"/>
            </div>
            <div class="span3">
                  <input class="btn" type="submit" value="${locale.getStr("common.select")}">
            </div>
        </div>
    </form>
    <div class="row-fluid">
        <div class="span12">
            <#if (alarmRecords?size<1)>
                <h4 class="m-l-20">${locale.getStr("common.noData")}</h4>
            <#else >
                <table class="table table-bordered table-striped table-center">
                    <thead>
                    <tr>
                        <th>${locale.getStr("common.number")}</th>
                        <th>${locale.getStr("common.location")}</th>
                        <th>${locale.getStr("blueplanet.alarm.alarmFactors")}</th>
                        <th>${locale.getStr("blueplanet.alarm.alarmTime")}</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#if alarmRecords?size gt 0>
                            <#list alarmRecords as alarmRecord>
                            <tr>
                                <td>${alarmRecord_index+1}</td>
                                <td>${alarmRecord.locationName}</td>
                                <td>${alarmRecord.factor}</td>
                                <td>${alarmRecord.alarmTime?string('yyyy-MM-dd HH:mm')}</td>
                            </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </#if>
            <#if startTime?? && endTime??>
                <#assign time="startTime=${startTime?string('yyyy-MM-dd HH:mm')}&endTime=${endTime?string('yyyy-MM-dd HH:mm')}"/>
            <#elseif startTime??>
                <#assign time="startTime=${startTime?string('yyyy-MM-dd HH:mm')}&endTime="/>
            <#elseif endTime??>
                <#assign time="startTime=&endTime=${endTime?string('yyyy-MM-dd HH:mm')}"/>
            <#else>
                <#assign time="startTime=&endTime="/>
            </#if>
            <#include "/common/pages/pagging.ftl">

            <#assign url = "alarm/history?sensorId=${sensorId!}&measureId=${measureId!}&${time}&size=${size!}"/>
            <@pagination url, index, pageCount,"index"/>
        </div>
    </div>
</div>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript">
    $(function(){
        $("#sensorId").val($("input[name='sensorHide']").val()).select2();
        $("#measureId").val($("input[name='measureHide']").val()).select2();
    })
</script>
</#macro>