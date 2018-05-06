<#--
返回相对时间, 如 2 分钟前。

@author gaohui
@date 2014-02-24
-->

<#-- 自动显示相对时间 -->
<#function autoRelative date>
    <#local diffMilli = .now?long - date?long >
    <#if (diffMilli < 0)>
        <#local diffMilli = -diffMilli>
    </#if>

    <#-- 一小时内使用相对时间 -->
    <#if (diffMilli / (1000 * 60 * 60) <= 1)>
        <#return timeago(date?long)>
    </#if>

    <#-- 同一年不显示年分 -->
    <#if .now?string("yyyy") == date?string("yyyy")>
        <#return date?string("MM-dd HH:mm:ss")>
    <#else>
        <#return date?string("yyyy-MM-dd HH:mm:ss")>
    </#if>
</#function>

<#function date d>
    <#return d?string("yyyy-MM-dd")>
</#function>

<#function time t>
    <#return  t?string("HH:mm:ss")>
</#function>

<#function dateTime date>
    <#return date?string("yyyy-MM-dd HH:mm:ss")>
</#function>

<#function dateTimeShort date>
    <#return date?string("yyyy-MM-dd HH:mm")>
</#function>


<#function timeago milliseconds>
    <#local diffMilli = .now?long - milliseconds>

    <#local isFutuer = (diffMilli < 0)>
    <#if diffMilli < 0>
        <#local diffMilli = -diffMilli>
    </#if>
    <#local seconds = (diffMilli / 1000)?round>
    <#local minutes = (seconds / 60)?round>
    <#local hours = (minutes / 60)?round>
    <#local days = (hours / 24)?round>
    <#local years = (days / 365)?round>

    <#local humanText = _humanize(seconds, minutes, hours, days, years)>
    <#if isFutuer>
        <#return humanText + "后">
    <#else>
        <#return humanText + "前">
    </#if>

</#function>

<#function _humanize(seconds, minutes, hours, days, years)>
    <#if (seconds < 45) >
        <#return seconds + "秒">
    <#elseif (minutes == 1)>
        <#return 1 + "分钟">
    <#elseif (minutes < 45)>
        <#return minutes + "分钟">
    <#elseif (hours == 1)>
        <#return 1 + "小时">
    <#elseif (hours < 22)>
        <#return hours + "小时">
    <#elseif (days == 1)>
        <#return 1 + "天">
    <#elseif (days <= 25)>
        <#return days + "天">
    <#elseif (days <= 45)>
        <#return 1 + "月">
    <#elseif (days < 345)>
        <#return (days / 30) + "月">
    <#elseif yeas == 1>
        <#return years + "年">
    <#else>
        <#return years + "年">
    </#if>
</#function>