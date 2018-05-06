<#--
  -添加光学摄像机点位
  -@author li.jianfei
  -@time  13-3-22
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.cameras.add.title")} - ${locale.getStr("proxima.common.systemName")}</title>

<#include "../_common/common-css.ftl">


</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:opticsDVPlace">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:dVPlace:addOptics"></@subNavs>

<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--your content-->

    <div class="row">
        <div class="span12">
            <form name="form1" action="addOpticsDVPlace.action" method="post"
                  class="form-horizontal">
            <#--每天周期-->
                <input type="hidden" name="everydayPeriod">
            <#--每天时间点-->
                <input type="hidden" name="everydayPoint">
            <#--7天（指的是星期一到星期天）周期-->
                <input type="hidden" name="sevendayPeriod">
            <#--7天（指的是星期一到星期天）时间点-->
                <input type="hidden" name="sevendayPoint">
                <fieldset>
                    <legend>
                    ${locale.getStr("proxima.cameras.add.optical")}
                    </legend>
                    <div class="control-group">
                        <label class="control-label" for="zone-input">
                            <span class="red">*</span>${locale.getStr("blueplanet.controller.zoneName")}
                        </label>

                        <div class="controls">
                            <input id="zoneId" name="dvPlace.zone.id" type="hidden">
                            <input id="zone-input" data-zone-id="" type="text">
                            <span id="zone-input-help" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="dvPlaceName-input">
                            <span class="red">*</span>${locale.getStr("proxima.cameras.pointName")}
                        </label>

                        <div class="controls">
                            <input id="dvPlaceName-input" type="text"
                                   name="dvPlace.placeName" maxlength="14">
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
                                <option value="${ftp.id}"> ${ftp.name}</option>
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
                                   maxlength="50"/>
                            <span id="dvPlaceIp-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="dvPlaceRemark-input">
                        ${locale.getStr("blueplanet.controlPanel.remark")}
                        </label>

                        <div class="controls">
        <textarea id="dvPlaceRemark-input" name="dvPlace.remark"
                  maxlength="200"></textarea>
                        <#--<span id="dvPlaceRemark-input-help" class="help-inline"></span>-->
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="lightOn-input">
                        ${locale.getStr("proxima.cameras.edit.externalControl")}
                        </label>

                        <div class="controls">
                            <label class="checkbox">
                                <input id="lightOn-input" type="checkbox"
                                       name="dvPlace.ioOn" value="true">
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
                                    <option value="2"> V2.0</option>
                                    <option value="1"> V1.0</option>
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
                                       maxlength="50"/>
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
                                       maxlength="10" value="10001"/>
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
                                    <option value="1"> 1</option>
                                    <option value="2"> 2</option>
                                    <option value="3"> 3</option>
                                    <option value="4"> 4</option>
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
                                       maxlength="50" value="80"/>
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
                                    <option value="1000" title="HK-1000毫秒">
                                        1000ms(HK)
                                    </option>
                                    <option value="500" title="ZYX-500毫秒">
                                        500ms(ZYX)
                                    </option>
                                </select>
                                <span id="photographTime-select-help" class="help-inline"></span>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">
                            ${locale.getStr("proxima.cameras.edit.turnOnTheLight")}
                            </label>

                            <div class="controls">
                                <label class="radio inline">
                                    <input id="isLightOn_input" name="dvPlace.lightOn" type="checkbox" value="true"
                                           checked="checked">
                                </label>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label"><span
                                    class="red">*</span>${locale.getStr("proxima.cameras.edit.lightLines")}</label>

                            <div class="controls">
                                <select name="dvPlace.ioLightRoute">
                                    <option value="1"> 1</option>
                                    <option value="2" selected="selected"> 2</option>
                                    <option value="3"> 3</option>
                                    <option value="4"> 4</option>
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
                                    <option value="5" title="5s">
                                        5s
                                    </option>
                                    <option value="10" title="10s">
                                        10s
                                    </option>
                                    <option value="15" title="15s">
                                        15s
                                    </option>
                                </select>
                                <span id="lightOnTime-select-help" class="help-inline"></span>
                            </div>
                        </div>

                        <div id="photograph_e_time" class="control-group">
                            <label class="control-label" for="lightOffTime-select">
                                <span class="red">*</span>${locale.getStr("proxima.cameras.edit.lightingTimeAfter")}
                            </label>

                            <div class="controls">
                                <select id="lightOffTime-select" name="dvPlace.lightOffTime">
                                    <option value="5" title="5s">
                                        5s
                                    </option>
                                    <option value="10" title="10s">
                                        10s
                                    </option>
                                    <option value="15" title="15s">
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
                                           checked="checked">
                                ${locale.getStr("proxima.cameras.edit.dailyCycle")}
                                </label>
                                <label class="radio inline">
                                    <input name="radioType" type="radio" value="point">
                                ${locale.getStr("proxima.cameras.edit.dailyTimePoint")}
                                </label>
                                <label class="radio inline">
                                    <input name="radioType" type="radio" value="7day">
                                ${locale.getStr("proxima.cameras.edit.sevenDayPeriod")}
                                </label>
                                <label class="radio inline">
                                    <input name="radioType" type="radio" value="7day_point">
                                ${locale.getStr("proxima.cameras.edit.sevenDaysTimePoint")}
                                </label>

                                <div class="form-inline well data-everyday"
                                     style="margin-top: 20px;">
                                    <label>
                                    ${locale.getStr("common.startDate")}
                                    </label>
                                    <input type="text" data-start="0" class="input-medium"
                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                    <label>
                                    ${locale.getStr("common.endDate")}
                                    </label>
                                    <input type="text" data-end="0" class="input-medium"
                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                    <label>
                                    ${locale.getStr("proxima.cameras.edit.period")}
                                    </label>
                                    <input type="text" data-period="0" class="input-mini"
                                           value="1">
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

                                    <div class="form-inline" style="margin-top: 10px;">
                                        <label>
                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                        </label>
                                        <input type="text" data-point-input="0"
                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                        <span class="help-inline"><i class="icon-remove"></i>
													</span>
                                    </div>
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
                                            <div class="tab-pane active" id="tab1">
                                                <div class="form-inline" style="margin: 10px 0;">
                                                    <label>
                                                    ${locale.getStr("common.startDate")}
                                                    </label>
                                                    <input type="text" data-start="1" class="input-medium"
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="1" class="input-medium"
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="1" class="input-mini">
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
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="2" class="input-medium"
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="2" class="input-mini">
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
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="3" class="input-medium"
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="3" class="input-mini">
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
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="4" class="input-medium"
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="4" class="input-mini">
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
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="5" class="input-medium"
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="5" class="input-mini">
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
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="6" class="input-medium"
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="6" class="input-mini">
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
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("common.endDate")}
                                                    </label>
                                                    <input type="text" data-end="7" class="input-medium"
                                                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">

                                                    <label>
                                                    ${locale.getStr("proxima.cameras.edit.period")}
                                                    </label>
                                                    <input type="text" data-period="7" class="input-mini">
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

                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="1"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i
                                                                class="icon-remove"></i>
																	</span>
                                                    </div>
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

                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="2"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i
                                                                class="icon-remove"></i>
																	</span>
                                                    </div>
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

                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="3"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i
                                                                class="icon-remove"></i>
																	</span>
                                                    </div>
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

                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="4"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i
                                                                class="icon-remove"></i>
																	</span>
                                                    </div>
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

                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="5"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i
                                                                class="icon-remove"></i>
																	</span>
                                                    </div>
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

                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="6"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i
                                                                class="icon-remove"></i>
																	</span>
                                                    </div>
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

                                                    <div class="form-inline" style="margin-top: 10px;">
                                                        <label>
                                                        ${locale.getStr("proxima.cameras.edit.timePoint")}
                                                        </label>
                                                        <input type="text" data-7daypoint-input="7"
                                                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'HH:mm'})">
                                                        <span class="help-inline"><i
                                                                class="icon-remove"></i>
																	</span>
                                                    </div>
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
                        <a href="queryOpticsDVPlace.action" class="btn">${locale.getStr("common.return")}</a>
                    </div>
                </fieldset>
            </form>

        </div>
    </div>

</div>
<div class="hide">
    <div id="zoneTreeDialog" class="span4">
        <ul id="zoneTree" class="ztree"></ul>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<script type="text/javascript" src="../assets/my97-DatePicker/4.8-beta3/WdatePicker.js"></script>
<script type="text/javascript" src="js/dvPlaceJs.js"></script>

<#include "../_common/last-resources.ftl">
</body>
</html>
