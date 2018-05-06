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
    <input type="hidden" value="${exhibitionId}" name="exhibitionId">
    <ul class="nav nav nav-pills">
        <li><a id="tab1-history" href="routeHistory/exhibition/${exhibitionId}/timeIntervalIndex/0">地图路线历史数据</a></li>
        <li><a href="historyData/exhibition/${exhibitionId}/timeIntervalIndex/0">传感设备历史数据</a></li>
        <li class="active"><a href="messageHistory?exhibitionId=${exhibitionId}&page=1">短信记录</a></li>
    </ul>
    <div id="tab3">

        <table class="table table-bordered table-striped table-hover table-center m-t-20">
            <thead>
            <th>序号</th>
            <th>内容</th>
            <th>接收人</th>
            <th>接收时间</th>
            </thead>
            <tbody>
                <#if alarmList??>
                    <#list alarmList as alarm>
                    <tr>
                        <td>${alarm_index+1}</td>
                        <td>${alarm.content}</td>
                        <td>${alarm.contact}</td>
                        <td>${alarm.sendTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                    </tr>
                    </#list>
                </#if>
            </tbody>
        </table>
        <#include "/common/pages/pagging.ftl" >
        <#assign url = "messageHistory?exhibitionId=${exhibitionId}">
        <@pagination url, page, pageSum,"page"/>

        <#if !alarmList??>
            <h4>暂无数据</h4>
        <#elseif   alarmList?size==0>
            <h4>暂无数据</h4>
        </#if>
    </div>
</div>

</#macro>

<#macro script>
</#macro>