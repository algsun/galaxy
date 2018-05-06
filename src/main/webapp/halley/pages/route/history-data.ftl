<#--
外展管理，实时数据与运行状态

@author xu.yuexi
@date 2013-11-4
-->

<#assign title="历史数据 - 外展管理">
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
<style type="text/css">
        /* 解决 bootstrap css 与google map 冲突问题*/
    #mapCanvas img {
        max-width: none;
    }

</style>
</#macro>

<#macro content>

<div class="row">
    <div class="span12">
        <fieldset>
            <legend>
                <a class="go-back" href="queryExhibitionStateList/exhibition/${exhibitionId}" title="返回">
                    <i class="mw-icon-prev"></i>历史数据
                </a>
            </legend>
        </fieldset>
    </div>
</div>
<div class="tabbable m-t-30">
<ul class="nav  nav-pills">
    <input type="hidden" value="${exhibitionId}" name="exhibitionId">
    <li><a id="tab1-history" href="routeHistory/exhibition/${exhibitionId}/timeIntervalIndex/0">地图路线历史数据</a></li>
    <li class="active"><a href="historyData/exhibition/${exhibitionId}/timeIntervalIndex/0">传感设备历史数据</a></li>
    <li><a href="messageHistory?exhibitionId=${exhibitionId}&page=1">短信记录</a></li>
</ul>

<div id="tab2">
    <form id="historyForm" class="well well-small form-inline m-b-10"
          action="routeHistory?page=1"
          method="post">

        <div class="control-group">
            <input id="exhibitionId" type="hidden" value="${exhibitionId}" name="exhibitionId"/>
            <#--<input id="Lat" type="hidden" value="${Session["currentLogicGroup"].site.latGoogle}"/>-->
            <#--<input id="Lng" type="hidden" value="${Session["currentLogicGroup"].site.lngGoogle}"/>-->
        </div>

        <div class="row m-t-10">
            <div class="span11">
                <div class="row">
                    <div class="span1 t-a-r">
                        <label for="timeInterval">时间段</label>
                    </div>
                    <div class="span3">
                        <select name="timeIntervalIndex" id="timeInterval">
                            <#if timeIntervalVOs?size gt 0>
                                <#list timeIntervalVOs as time>
                                    <option
                                        <#if time.endTime??>data-endTime="${time.endTime?string('yyyy-MM-dd HH:mm:ss')}" </#if>
                                        <#if time.beginTime??>
                                        data-beginTime="${time.beginTime?string('yyyy-MM-dd HH:mm:ss')}"   </#if>
                                        value="${time_index}"
                                        <@selected time_index,timeIntervalIndex />
                                            >${time.name}</option>
                                </#list>
                            </#if>
                        </select>
                    </div>
                    <div class="span2 t-a-r">
                        <label for="location">位置点</label>
                    </div>
                    <div class="span3">
                        <select name="locationId" id="location">
                            <#if locations?size gt 0>
                                <#list locations as location>
                                    <option value="${location.id}"<#if locationId??>
                                        <@selected location.id,locationId />
                                    </#if>><#if location.locationName??>${location.locationName}<#else>
                                    ${location.id}</#if> </option>
                                </#list>
                            <#else>
                                <option value="-1">暂无位置点</option>
                            </#if>
                        </select>
                    </div>
                    <div class="span1"></div>
                </div>
                <div class="row m-t-10 ">
                    <div class="span1 t-a-r">
                        <label class="m-t-3" for="startDate">开始时间</label>
                    </div>
                    <div class="span3">
                        <input id="startDate" type="text"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               name="startDate"
                               value="${startDate?string('yyyy-MM-dd HH:mm:ss')}"/>
                    </div>
                    <div class="span2 t-a-r">
                        <label class="m-t-3" for="endDate">结束时间</label>
                    </div>
                    <div class="span3">
                        <input id="endDate" type="text"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               name="endDate"
                               value="${endDate?string('yyyy-MM-dd HH:mm:ss')}"/>
                    </div>
                    <div class="span1">
                        <button id="submit-btn" class="btn m-b-8" type="submit">查询</button>
                    </div>
                </div>
            </div>
        </div>

    </form>
    <table class="table table-bordered table-striped table-hover table-center m-t-20">
        <thead>
        <th>序号</th>
        <th>时间</th>
        <th>状态</th>
            <#if sensorinfos??>
                <#list sensorinfos as sensorinfo>
                <th>
                ${sensorinfo.cnName}<br>
                    <span class="muted f-n">(${sensorinfo.units})</span>
                </th>
                </#list>
            </#if>
        </thead>
        <tbody>
            <#if historyDataVOList??>
                <#list historyDataVOList as historyData>
                    <#local data = historyData.sensorInfoMap>
                <#-- 设备状态 -->
                    <#local _deviceState = {"anomaly": historyData.anomaly, "lowvoltage": historyData.lowvoltage}>
                <tr>
                    <td>${historyData_index + 1}</td>
                    <td>${historyData.stamp?string("yyyy-MM-dd HH:mm:ss")}</td>
                    <td><@deviceState _deviceState/></td>
                    <#list sensorinfos as sensorinfo>
                        <td>
                            <#if data.get(sensorinfo.sensorPhysicalid)??>
                                <#local locationData = data.get(sensorinfo.sensorPhysicalid)>
                            <#-- 采样失败 -->
                                <#if locationData.state == 0>
                                    --
                                <#else>
                                ${locationData.sensorPhysicalValue}
                                </#if>
                            </#if>
                        </td>
                    </#list>
                </tr>
                </#list>
            </#if>
        </tbody>
    </table>
    <#include "/common/pages/pagging.ftl" >
    <#assign url = "routeHistory?tabP=1&startDate=${startDate?string('yyyy-MM-dd HH:mm:ss')}&endDate=${endDate?string('yyyy-MM-dd HH:mm:ss')}&locationId=${locationId!}&exhibitionId=${exhibitionId}">
    <@pagination url, page, pageSum,"page"/>
    <#if !historyDataVOList??>
        <h4>暂无数据</h4>
    <#elseif   historyDataVOList?size==0>
        <h4>暂无数据</h4>
    </#if>
</div>

</div>
</#macro>

<#-- 设备状态 -->
<#macro deviceState location>
<#-- 超时 -->
    <#if location.anomaly == -1>
        <@_deviceStateVew location, "label-important", "超时"/>
    <#-- 正常 -->
    <#elseif location.anomaly == 0 >
        <@_deviceStateVew location, "label-success", "正常"/>
    <#-- 低电 -->
    <#elseif location.anomaly == 1>
        <@_deviceStateVew location, "label-warning", "低电"/>
    <#-- 掉电 -->
    <#elseif location.anomaly == 2>
        <@_deviceStateVew location, "label-warning", "掉电"/>
    </#if>
</#macro>
<#macro _deviceStateVew location, class, name>
    <#if location.lowvoltage == -1>
    <span class="label ${class}">${name}</span>
    <#else>
    <span class="label ${class}">${location.lowvoltage}V</span>
    </#if>
</#macro>
<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.timers-1.2.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.easing.1.3.js"></script>
    <@scriptTag "js/2datepicker-form-validation.js"/>
<script type="text/javascript">

    var beginTime;
    var endTime;
    var $exhibitionId = $("#exhibitionId").val();
    var $startDate = $("input[name='startDate']");
    var $endDate = $("input[name='endDate']");
    var $check1 = $startDate.popover({
        title: "提示",
        content: "开始时间不能早于当前阶段的开始时间",
        placement: 'bottom',
        trigger: 'manual'
    });
    var $check2 = $endDate.popover({
        title: "提示",
        content: "结束时间不能晚于当前阶段的结束时间",
        placement: 'bottom',
        trigger: 'manual'
    });
    $("#timeInterval").change(function () {
        var $selectedOption = $("#timeInterval").find("option:selected");
        beginTime = $selectedOption.attr("data-beginTime");
        endTime = $selectedOption.attr("data-endTime");
        $("#startDate").val(beginTime);
        $("#endDate").val(endTime);
    });

    $("#tab1-history").click(function () {
//        location.href = 'routeHistory/exhibition/'+$exhibitionId+'/timeIntervalIndex/1';
    });

    $("#submit-btn").click(function () {
        var $selectedOption = $("#timeInterval").find("option:selected");
        beginTime = $selectedOption.attr("data-beginTime");
        endTime = $selectedOption.attr("data-endTime");
        if ($endDate.val() > endTime) {
            $check2.popover('show');
            return false;
        }
        if ($startDate.val() < beginTime) {
            $check1.popover('show');
            return false;
        }

    });
    //点击开始时间取消时间验证提示
    $startDate.click(function () {
        $check1.popover('hide');
    });

    //点击结束时间取消时间验证
    $endDate.click(function () {
        $check2.popover('hide');
    });

</script>
</#macro>