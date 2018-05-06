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

    <input type="hidden" id="exhibitionId"  name="exhibitionId" value="${exhibitionId}">
    <input type="hidden" value="${alarmRange!}" id="alarmRange"/>
    <input id="pathType" type="hidden" value="${pathType!}"/>
    <#--<input id="Lat" type="hidden" value="${Session["currentLogicGroup"].site.latGoogle}"/>-->
    <#--<input id="Lng" type="hidden" value="${Session["currentLogicGroup"].site.lngGoogle}"/>-->
    <ul class="nav nav nav-pills">
        <li  class="active"><a id="tab1-history" href="routeHistory/exhibition/${exhibitionId}/timeIntervalIndex/0">地图路线历史数据</a></li>
        <li><a href="historyData/exhibition/${exhibitionId}/timeIntervalIndex/0">位置点历史数据</a></li>
        <li><a href="messageHistory?exhibitionId=${exhibitionId}&page=1">短信记录</a></li>
    </ul>
        <div id="tab1">
            <div id="mapContainer" style="height: 600px;"></div>
        </div>
    </div>
</#macro>

<#macro script>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=33d76103b0f6b703e009a15a83453f0f"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.timers-1.2.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.easing.1.3.js"></script>
 <@scriptTag "js/route/history-path.js"/>
</#macro>