<#--
  -修改光学摄像机点位
  -@author li.jianfei
  -@time  13-3-22
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.cameras.edit.title")} - ${locale.getStr("proxima.common.systemName")}</title>
<#include "../_common/common-css.ftl">
<#include "/common/pages/common-tag.ftl">

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:opticsDVPlace">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:opticsDVPlace:list"></@subNavs>

<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--your content-->
    <div class="row">
        <div class="span12">

            <form name="form1" action="updateOpticsDVPlace.action" method="post"
                  class="form-horizontal">
            <#--每天周期-->
                <input type="hidden" name="everydayPeriod">
            <#--每天时间点-->
                <input type="hidden" name="everydayPoint">
            <#--7天（指的是星期一到星期天）周期-->
                <input type="hidden" name="sevendayPeriod">
            <#--7天（指的是星期一到星期天）时间点-->
                <input type="hidden" name="sevendayPoint">
                <input id="dvPlaceId" type="hidden" name="dvPlace.id"
                       value="${dvPlace.id}">
                <fieldset>
                    <legend>
                    ${locale.getStr("common.edit")}
                    </legend>
                    <div class="control-group">
                        <label class="control-label" for="zone-input">
                            <span class="red">*</span>${locale.getStr("blueplanet.controller.zoneName")}
                        </label>

                        <div class="controls">
                            <input id="zoneId" name="dvPlace.zone.id" type="hidden" value="${dvPlace.zone.id}">
                            <input id="zone-input" name="dvPlace.zone.name" data-zone-id="" type="text"
                                   value="${dvPlace.zone.name}">
                            <span id="zone-input-help" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="dvPlaceName-input">
                            <span class="red">*</span>${locale.getStr("proxima.cameras.pointName")}
                        </label>

                        <div class="controls">
                            <input id="dvPlaceName-input" type="text"
                                   name="dvPlace.placeName"
                                   value="${dvPlace.placeName}"
                                   maxlength="14">
                            <span id="dvPlaceName-input-help" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="ftp-select">
                            <span class="red">*</span>FTP
                        </label>

                        <div class="controls">
                            <select id="ftp-select" name="dvPlace.ftp.id">
                            <#list ftpList as ftp>
                                <option value="${ftp.id}"
                                    <#if ftp.id==dvPlace.ftp.id>
                                        selected="selected"
                                    </#if>>
                                ${ftp.name}
                                </option>
                            </#list>
                            </select>
                            <span id="ftp-select-help" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="dvPlaceIp-input">
                            <span class="red">*</span>${locale.getStr("proxima.common.camera")} IP
                        </label>

                        <div class="controls">
                            <input id="dvPlaceIp-input" type="text" name="dvPlace.dvIp"
                                   value="${dvPlace.dvIp}" maxlength="50"/>
                            <span id="dvPlaceIp-input-help" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="dvPlaceRemark-input">
                        ${locale.getStr("blueplanet.controlPanel.remark")}
                        </label>

                        <div class="controls">
        <textarea id="dvPlaceRemark-input" name="dvPlace.remark"
                  maxlength="200">${dvPlace.remark}</textarea>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="lightOn-input">
                        ${locale.getStr("proxima.cameras.edit.externalControl")}
                        </label>

                        <div class="controls">
                            <label class="checkbox">
                                <input id="lightOn-input" type="checkbox"
                                       name="dvPlace.ioOn" value="true"
                                <#if dvPlace.ioOn>
                                       checked="checked"
                                </#if>>
                            </label>
                        </div>
                    </div>
                    <div id="enable-external-io-display">
                        <div class="control-group">
                            <label class="control-label">
                                <span class="red">*</span>${locale.getStr("proxima.cameras.edit.ioVersion")}
                            </label>

                            <div class="controls">
                                <select name="dvPlace.ioVersion">
                                    <option value="2" <@selected 2, dvPlace.ioVersion/>> V2.0</option>
                                    <option value="1" <@selected 1, dvPlace.ioVersion/>> V1.0</option>
                                </select>
                                <p class="help-block">
                                ${locale.getStr("proxima.cameras.edit.ioVersion.invalid")}
                                </p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="ioip-input">
                                <span class="red">*</span>${locale.getStr("proxima.cameras.edit.IOModule")} IP
                            </label>

                            <div class="controls">
                                <input id="ioip-input" type="text" name="dvPlace.ioIp"
                                       maxlength="50" value="${dvPlace.ioIp}"/>
                                <span id="ioip-input-help" class="help-inline"></span>

                                <p class="help-block">
                                ${locale.getStr("proxima.cameras.edit.IOModuleIP.invalid")}
                                </p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="ioPort-input">
                                <span class="red">*</span>${locale.getStr("proxima.cameras.edit.IOModulePort")}
                            </label>

                            <div class="controls">
                                <input id="ioPort-input" type="text" name="dvPlace.ioPort"
                                       maxlength="10" value="${dvPlace.ioPort?c}"/>
                                <span id="ioPort-input-help" class="help-inline"></span>

                                <p class="help-block">
                                ${locale.getStr("proxima.cameras.edit.IOModulePort.invalid")}
                                </p>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label"><span
                                    class="red">*</span>${locale.getStr("proxima.cameras.edit.cameraLines")}</label>

                            <div class="controls">
                                <select name="dvPlace.ioDvRoute">
                                    <option value="1" <@selected 1, dvPlace.ioDvRoute/>> 1</option>
                                    <option value="2" <@selected 2, dvPlace.ioDvRoute/>> 2</option>
                                    <option value="3" <@selected 3, dvPlace.ioDvRoute/>> 3</option>
                                    <option value="4" <@selected 4, dvPlace.ioDvRoute/>> 4</option>
                                </select>

                                <p class="help-block">${locale.getStr("proxima.cameras.edit.cameraLines.invalid")}</p>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="dvPort-input">
                                <span class="red">*</span>${locale.getStr("proxima.cameras.edit.cameraPort")}
                            </label>

                            <div class="controls">
                                <input id="dvPort-input" type="text" name="dvPlace.dvPort"
                                       maxlength="50" value="${dvPlace.dvPort?c}"/>
                                <span id="dvPort-input-help" class="help-inline"></span>

                                <p class="help-block">
                                ${locale.getStr("proxima.cameras.edit.cameraPort.invalid")}
                                </p>
                            </div>

                        </div>
                        <div class="control-group">
                            <label class="control-label" for="photographTime-select">
                                <span class="red">*</span>${locale.getStr("proxima.cameras.edit.photoTime")}
                            </label>

                            <div class="controls">
                                <select id="photographTime-select"
                                        name="dvPlace.photographTime">
                                    <option value="1000" title="HK-1000毫秒"
                                    <#if dvPlace.photographTime==1000>
                                            selected="selected"
                                    </#if>>
                                        1000 ms(HK)
                                    </option>
                                    <option value="500" title="ZYX-500毫秒"
                                    <#if dvPlace.photographTime==500>
                                            selected="selected"
                                    </#if>>
                                        500 ms(ZYX)
                                    </option>
                                </select>
                                <span id="photographTime-select-help" class="help-inline"></span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">
                                <span class="red">*</span>${locale.getStr("proxima.cameras.edit.turnOnTheLight")}
                            </label>

                            <div class="controls">
                                <label class="radio inline">
                                    <input id="isLightOn_input" name="dvPlace.lightOn" type="checkbox" value="true"
                                    <#if dvPlace.lightOn>
                                           checked="checked"
                                    </#if>>
                                </label>

                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label"><span
                                    class="red">*</span>${locale.getStr("proxima.cameras.edit.lightLines")}</label>

                            <div class="controls">
                                <select name="dvPlace.ioLightRoute">
                                    <option value="1" <@selected 1, dvPlace.ioLightRoute/>> 1</option>
                                    <option value="2" <@selected 2, dvPlace.ioLightRoute/>> 2</option>
                                    <option value="3" <@selected 3, dvPlace.ioLightRoute/>> 3</option>
                                    <option value="4" <@selected 4, dvPlace.ioLightRoute/>> 4</option>
                                </select>

                                <p class="help-block">${locale.getStr("proxima.cameras.edit.cameraLines.invalid")}</p>
                            </div>
                        </div>

                        <div id="photograph_s_time" class="control-group">
                            <label class="control-label" for="lightOnTime-select">
                                <span class="red">*</span>${locale.getStr("proxima.cameras.edit.lightingTimeBefore")}
                            </label>

                            <div class="controls">
                                <select id="lightOnTime-select" name="dvPlace.lightOnTime">
                                    <option value="5" title="5s"
                                    <#if dvPlace.lightOnTime==5>
                                            selected="selected"
                                    </#if>>
                                        5s
                                    </option>
                                    <option value="10" title="10s"
                                    <#if dvPlace.lightOnTime==10>
                                            selected="selected"
                                    </#if>>
                                        10s
                                    </option>
                                    <option value="15" title="15s"
                                    <#if dvPlace.lightOnTime==15>
                                            selected="selected"
                                    </#if>>
                                        15s
                                    </option>
                                </select>
                                <span id="lightOnTime-select-help" class="help-inline"></span>
                            </div>
                        </div>

                        <div id="photograph_e_time" class="control-group">
                            <label class="control-label" for="lightofftime-select">
                                <span class="red">*</span>${locale.getStr("proxima.cameras.edit.lightingTimeAfter")}
                            </label>

                            <div class="controls">
                                <select id="lightOffTime-select" name="dvPlace.lightOffTime">
                                    <option value="5" title="5s"
                                    <#if dvPlace.lightOffTime==5>
                                            selected="selected"
                                    </#if>>
                                        5s
                                    </option>
                                    <option value="10" title="10s"
                                    <#if dvPlace.lightOffTime==10>
                                            selected="selected"
                                    </#if>>
                                        10s
                                    </option>
                                    <option value="15" title="15s"
                                    <#if dvPlace.lightOffTime==15>
                                            selected="selected"
                                    </#if>>
                                        15s
                                    </option>
                                </select>
                                <span id="lightOffTime-select-help" class="help-inline"></span>
                            </div>
                        </div>
                    </div>
                    <div id="enable-external-display">
                        <div class="control-group">
                            <label class="control-label">
                            ${locale.getStr("proxima.cameras.edit.photoPlan")}
                            </label>

                            <div class="controls">
                                <label class="radio inline">
                                    <input name="radioType" type="radio" value="day"
                                    <#if dayOfWeek! =='day'>
                                           checked="checked"
                                    </#if>>
                                ${locale.getStr("proxima.cameras.edit.dailyCycle")}
                                </label>
                                <label class="radio inline">
                                    <input name="radioType" type="radio" value="point"
                                    <#if dayOfWeek! =='point'>
                                           checked="checked"
                                    </#if>>
                                ${locale.getStr("proxima.cameras.edit.dailyTimePoint")}
                                </label>
                                <label class="radio inline">
                                    <input name="radioType" type="radio" value="7day"
                                    <#if dayOfWeek! =='7day'>
                                           checked="checked"
                                    </#if>>
                                ${locale.getStr("proxima.cameras.edit.sevenDayPeriod")}
                                </label>
                                <label class="radio inline">
                                    <input name="radioType" type="radio" value="7day_point"
                                    <#if dayOfWeek! =='7day_point'>
                                           checked="checked"
                                    </#if>>
                                ${locale.getStr("proxima.cameras.edit.sevenDaysTimePoint")}
                                </label>

                                <div class="form-inline well data-everyday"
                                     style="margin-top: 20px;">
                                <#if dayOfWeek! =='day'>
                                    <#list psbList as psb>
                                        <#global dayPS=psb>
                                    </#list>
                                </#if>
                                    <label>
                                    ${locale.getStr("common.startDate")}
                                    </label>
                                    <input type="text" data-start="0" class="input-medium"
                                    <#if dayPS??>
                                           value="${dayPS.startTime?string("HH:mm")}"
                                    </#if>
                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                    <label>
                                    ${locale.getStr("common.endDate")}
                                    </label>
                                    <input type="text" data-end="0" class="input-medium"
                                    <#if dayPS??>
                                           value="${dayPS.endTime?string("HH:mm")}"
                                    </#if>
                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                    <label>
                                    ${locale.getStr("proxima.cameras.edit.period")}
                                    </label>
                                    <input type="text" data-period="0" class="input-mini"
                                    <#if dayPS??>
                                           value="${dayPS.interval}"
                                    </#if>
                                           value="0">
                                    (minute)
                                    <p id="everyday-help"></p>

                                </div>


                                <div class="well data-everypoint" style="margin-top: 20px;">
                                    <div class="form-inline">
                                        <button id="add-data-point-button"
                                                onclick="javascript:return false;" class="btn">
                                        ${locale.getStr("proxima.cameras.edit.addTimePoint")}
                                        </button>
                                    </div>

                                    <p id="everypoint-help"></p>
                                <#if psbList?? && dayOfWeek! =="point">
                                    <#list psbList as psbPoind>
                                        <div class="form-inline" style="margin-top: 10px;">
                                            <label>
                                            ${locale.getStr("proxima.cameras.edit.timePoint")}
                                            </label>
                                            <input type="text" data-point-input="0"
                                                   value="${psbPoind.timePoint?string("HH:mm")}"
                                                   onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                            <span class="help-inline"><i class="icon-remove"></i> </span>
                                        </div>
                                    </#list>
                                <#else>
                                    <div class="form-inline" style="margin-top: 10px;">
                                        <label>
                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                        </label>
                                        <input type="text" data-point-input="0"
                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                        <span class="help-inline"><i class="icon-remove"></i> </span>
                                    </div>
                                </#if>
                                </div>

                                <div class="well data-7day" style="margin-top: 20px;">
                                    <div class="tabbable">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab1" data-toggle="tab">${locale.getStr("common.monday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab2" data-toggle="tab">${locale.getStr("common.tuesday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab3"
                                                   data-toggle="tab">${locale.getStr("common.wednesday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab4"
                                                   data-toggle="tab">${locale.getStr("common.thursday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab5" data-toggle="tab">${locale.getStr("common.friday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab6"
                                                   data-toggle="tab">${locale.getStr("common.saturday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab7" data-toggle="tab">${locale.getStr("common.sunday")}</a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">

                                        <#--遍历7天周期-->
                                        <#if dayOfWeek! =='7day'>
                                            <#list psbList as psb7dayPeriod>
                                                <#if psb7dayPeriod.dayOfWeek.toString() == 'MON'>
                                                    <#global MON=psb7dayPeriod>
                                                <#elseif psb7dayPeriod.dayOfWeek.toString() == 'TUES'>
                                                    <#global TUES=psb7dayPeriod>
                                                <#elseif psb7dayPeriod.dayOfWeek.toString() == 'WED'>
                                                    <#global WED=psb7dayPeriod>
                                                <#elseif psb7dayPeriod.dayOfWeek.toString() == 'THURS'>
                                                    <#global THURS=psb7dayPeriod>
                                                <#elseif psb7dayPeriod.dayOfWeek.toString() == 'FRI'>
                                                    <#global FRI=psb7dayPeriod>
                                                <#elseif  psb7dayPeriod.dayOfWeek.toString() == 'SAT'>
                                                    <#global SAT=psb7dayPeriod>
                                                <#elseif psb7dayPeriod.dayOfWeek.toString() == 'SUN'>
                                                    <#global SUN=psb7dayPeriod>
                                                </#if>
                                            </#list>
                                        </#if>
                                            <div class="tab-pane active" id="tab1">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <label>
                                                    ${locale.getStr("common.startDate")}
                                                    </label>
                                                    <input type="text" data-start="1" class="input-medium"
                                                    <#if MON??>
                                                           value="${MON.startTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="1" class="input-medium"
                                                    <#if MON??>
                                                           value="${MON.endTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="1"
                                                    <#if MON??>
                                                           value="${MON.interval}"
                                                    </#if>
                                                           class="input-mini">
                                                    (minute)
                                                    <p data-7dayPeriod-help class="help-block"></p>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab2">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <label>
                                                    ${locale.getStr("common.startDate")}
                                                    </label>
                                                    <input type="text" data-start="2" class="input-medium"
                                                    <#if TUES??>
                                                           value="${TUES.startTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="2" class="input-medium"
                                                    <#if TUES??>
                                                           value="${TUES.endTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="2"
                                                    <#if TUES??>
                                                           value="${TUES.interval}"
                                                    </#if>
                                                           class="input-mini">
                                                    (minute)
                                                    <p data-7dayPeriod-help class="help-block"></p>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab3">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <label>
                                                    ${locale.getStr("common.startDate")}
                                                    </label>
                                                    <input type="text" data-start="3" class="input-medium"
                                                    <#if WED??>
                                                           value="${WED.startTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="3" class="input-medium"
                                                    <#if WED??>
                                                           value="${WED.endTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="3"
                                                    <#if WED??>
                                                           value="${WED.interval}"
                                                    </#if>
                                                           class="input-mini">
                                                    (minute)
                                                    <p data-7dayPeriod-help class="help-block"></p>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab4">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <label>
                                                    ${locale.getStr("common.startDate")}
                                                    </label>
                                                    <input type="text" data-start="4" class="input-medium"
                                                    <#if THURS??>
                                                           value="${THURS.startTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="4" class="input-medium"
                                                    <#if THURS??>
                                                           value="${THURS.endTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="4"
                                                    <#if THURS??>
                                                           value="${THURS.interval}"
                                                    </#if>
                                                           class="input-mini">
                                                    (minute)
                                                    <p data-7dayPeriod-help class="help-block"></p>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab5">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <label>
                                                    ${locale.getStr("common.startDate")}
                                                    </label>
                                                    <input type="text" data-start="5" class="input-medium"
                                                    <#if FRI??>
                                                           value="${FRI.startTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="5" class="input-medium"
                                                    <#if FRI??>
                                                           value="${FRI.endTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="5"
                                                    <#if FRI??>
                                                           value="${FRI.interval}"
                                                    </#if>
                                                           class="input-mini">
                                                    (minute)
                                                    <p data-7dayPeriod-help class="help-block"></p>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab6">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <label>
                                                    ${locale.getStr("common.startDate")}
                                                    </label>
                                                    <input type="text" data-start="6" class="input-medium"
                                                    <#if SAT??>
                                                           value="${SAT.startTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="6" class="input-medium"
                                                    <#if SAT??>
                                                           value="${SAT.endTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="6"
                                                    <#if SAT??>
                                                           value="${SAT.interval}"
                                                    </#if>
                                                           class="input-mini">
                                                    (minute)
                                                    <p data-7dayPeriod-help class="help-block"></p>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab7">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <label>
                                                    ${locale.getStr("common.startDate")}
                                                    </label>
                                                    <input type="text" data-start="7" class="input-medium"
                                                    <#if SUN??>
                                                           value="${SUN.startTime?string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="7" class="input-medium"
                                                    <#if SUN??>
                                                           value="${SUN.endTime.string("HH:mm")}"
                                                    </#if>
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="7"
                                                    <#if SUN??>
                                                           value="${SUN.interval}"
                                                    </#if>
                                                           class="input-mini">
                                                    (minute)
                                                    <p data-7dayPeriod-help class="help-block"></p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <div class="well data-7daypoint" style="margin-top: 20px;">
                                    <div class="tabbable">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab11" data-toggle="tab">${locale.getStr("common.monday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab22"
                                                   data-toggle="tab">${locale.getStr("common.tuesday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab33"
                                                   data-toggle="tab">${locale.getStr("common.wednesday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab44"
                                                   data-toggle="tab">${locale.getStr("common.thursday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab55" data-toggle="tab">${locale.getStr("common.friday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab66"
                                                   data-toggle="tab">${locale.getStr("common.saturday")}</a>
                                            </li>
                                            <li>
                                                <a href="#tab77" data-toggle="tab">${locale.getStr("common.sunday")}</a>
                                            </li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="tab-pane active" id="tab11">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <div class="form-inline">
                                                        <button data-7daypoint-button="1"
                                                                onclick="javascript:return false;" class="btn">
                                                        ${locale.getStr("proxima.cameras.edit.addTimePoint")}
                                                        </button>
                                                    </div>

                                                    <p data-7daypoint-help="1"></p>
                                                <#if psbList?? && dayOfWeek! =='7day_point'>
                                                    <#list psbList as psb7dayPoind>
                                                        <#if psb7dayPoind.dayOfWeek.toString() == 'MON'>
                                                            <div class="form-inline" style="margin-top: 10px;">
                                                                <label>
                                                                ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                                </label>
                                                                <input type="text" data-7daypoint-input="1"
                                                                       value="${psb7dayPoind.timePoint?string("HH:mm")}"
                                                                       onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                                <span class="help-inline"><i
                                                                        class="icon-remove"></i> </span>
                                                            </div>
                                                        </#if>
                                                    </#list>
                                                <#else>
                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="1"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i class="icon-remove"></i> </span>
                                                    </div>
                                                </#if>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab22">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <div class="form-inline">
                                                        <button data-7daypoint-button="2"
                                                                onclick="javascript:return false;" class="btn">
                                                        ${locale.getStr("proxima.cameras.edit.addTimePoint")}
                                                        </button>
                                                    </div>

                                                    <p data-7daypoint-help="2"></p>
                                                <#if psbList?? && dayOfWeek! =='7day_point'>
                                                    <#list psbList as psb7dayPoind>
                                                        <#if psb7dayPoind.dayOfWeek.toString() == 'TUES'>
                                                            <div class="form-inline" style="margin-top: 10px;">
                                                                <label>
                                                                ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                                </label>
                                                                <input type="text" data-7daypoint-input="2"
                                                                       value="${psb7dayPoind.timePoint?string("HH:mm")}"
                                                                       onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                                <span class="help-inline"><i
                                                                        class="icon-remove"></i> </span>
                                                            </div>
                                                        </#if>
                                                    </#list>
                                                <#else>
                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="2"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i class="icon-remove"></i> </span>
                                                    </div>
                                                </#if>

                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab33">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <div class="form-inline">
                                                        <button data-7daypoint-button="3"
                                                                onclick="javascript:return false;" class="btn">
                                                        ${locale.getStr("proxima.cameras.edit.addTimePoint")}
                                                        </button>
                                                    </div>

                                                    <p data-7daypoint-help="3"></p>
                                                <#if psbList?? && dayOfWeek! =='7day_point'>
                                                    <#list psbList as psb7dayPoind>
                                                        <#if psb7dayPoind.dayOfWeek.toString() == 'WED'>
                                                            <div class="form-inline" style="margin-top: 10px;">
                                                                <label>
                                                                ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                                </label>
                                                                <input type="text" data-7daypoint-input="3"
                                                                       value="${psb7dayPoind.timePoint?string("HH:mm")}"
                                                                       onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                                <span class="help-inline"><i
                                                                        class="icon-remove"></i> </span>
                                                            </div>
                                                        </#if>
                                                    </#list>
                                                <#else>
                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="3"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i class="icon-remove"></i> </span>
                                                    </div>
                                                </#if>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab44">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <div class="form-inline">
                                                        <button data-7daypoint-button="4"
                                                                onclick="javascript:return false;" class="btn">
                                                        ${locale.getStr("proxima.cameras.edit.addTimePoint")}
                                                        </button>
                                                    </div>

                                                    <p data-7daypoint-help="4"></p>
                                                <#if psbList?? && dayOfWeek! =='7day_point'>
                                                    <#list psbList as psb7dayPoind>
                                                        <#if psb7dayPoind.dayOfWeek.toString() == 'THURS'>
                                                            <div class="form-inline" style="margin-top: 10px;">
                                                                <label>
                                                                ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                                </label>
                                                                <input type="text" data-7daypoint-input="4"
                                                                       value="${psb7dayPoind.timePoint?string("HH:mm")}"
                                                                       onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                                <span class="help-inline"><i
                                                                        class="icon-remove"></i> </span>
                                                            </div>
                                                        </#if>
                                                    </#list>
                                                <#else>
                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="4"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i class="icon-remove"></i> </span>
                                                    </div>
                                                </#if>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab55">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <div class="form-inline">
                                                        <button data-7daypoint-button="5"
                                                                onclick="javascript:return false;" class="btn">
                                                        ${locale.getStr("proxima.cameras.edit.addTimePoint")}
                                                        </button>
                                                    </div>

                                                    <p data-7daypoint-help="5"></p>
                                                <#if psbList?? && dayOfWeek! =='7day_point'>
                                                    <#list psbList as psb7dayPoind>
                                                        <#if psb7dayPoind.dayOfWeek.toString() == 'FRI'>
                                                            <div class="form-inline" style="margin-top: 10px;">
                                                                <label>
                                                                ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                                </label>
                                                                <input type="text" data-7daypoint-input="5"
                                                                       value="${psb7dayPoind.timePoint?string("HH:mm")}"
                                                                       onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                                <span class="help-inline"><i
                                                                        class="icon-remove"></i> </span>
                                                            </div>
                                                        </#if>
                                                    </#list>
                                                <#else>
                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="5"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i class="icon-remove"></i> </span>
                                                    </div>
                                                </#if>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab66">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <div class="form-inline">
                                                        <button data-7daypoint-button="6"
                                                                onclick="javascript:return false;" class="btn">
                                                        ${locale.getStr("proxima.cameras.edit.addTimePoint")}
                                                        </button>
                                                    </div>

                                                    <p data-7daypoint-help="6"></p>
                                                <#if psbList?? && dayOfWeek! =='7day_point'>
                                                    <#list psbList as psb7dayPoind>
                                                        <#if psb7dayPoind.dayOfWeek.toString() == 'SAT'>
                                                            <div class="form-inline" style="margin-top: 10px;">
                                                                <label>
                                                                ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                                </label>
                                                                <input type="text" data-7daypoint-input="6"
                                                                       value="${psb7dayPoind.timePoint?string("HH:mm")}"
                                                                       onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                                <span class="help-inline"><i
                                                                        class="icon-remove"></i> </span>
                                                            </div>
                                                        </#if>
                                                    </#list>
                                                <#else>
                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                            ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="6"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i class="icon-remove"></i> </span>
                                                    </div>
                                                </#if>
                                                </div>
                                            </div>
                                            <div class="tab-pane" id="tab77">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <div class="form-inline">
                                                        <button data-7daypoint-button="7"
                                                                onclick="javascript:return false;" class="btn">
                                                            ${locale.getStr("proxima.cameras.edit.addTimePoint")}
                                                        </button>
                                                    </div>

                                                    <p data-7daypoint-help="7"></p>
                                                <#if psbList?? && dayOfWeek! =='7day_point'>
                                                    <#list psbList as psb7dayPoind>
                                                        <#if psb7dayPoind.dayOfWeek.toString() == 'SUN'>
                                                            <div class="form-inline" style="margin-top: 10px;">
                                                                <label>
                                                                    ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                                </label>
                                                                <input type="text" data-7daypoint-input="7"
                                                                       value="${psb7dayPoind.timePoint?string("HH:mm")}"
                                                                       onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                                <span class="help-inline"><i
                                                                        class="icon-remove"></i> </span>
                                                            </div>
                                                        </#if>
                                                    </#list>
                                                <#else>
                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                            ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="7"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i class="icon-remove"></i> </span>
                                                    </div>
                                                </#if>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-actions">
                        <button type="submit" id="submit-button"
                                class="btn btn-primary">
                            ${locale.getStr("common.save")}
                        </button>
                        <a href="queryDVPlaceDefault.action" class="btn">${locale.getStr("common.return")}</a>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>

</div>
</div>

<div class="hide">
    <div id="zoneTreeDialog" class="span4">
        <div id="zoneTree" class="ztree"></div>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/my97-DatePicker/4.8-beta3/WdatePicker.js"></script>
<script type="text/javascript" src="../proxima/js/dvPlaceJs.js"></script>

<#include "../_common/last-resources.ftl">
</body>
</html>
