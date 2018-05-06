<#--
停留时间计算

@author wang.geng
@date 2013-05-02
-->


<#--
参数
duration：long类型，停留时间，单位是秒

小于1秒大于0毫秒 显示为 1秒，如：200毫秒显示为1秒
大于1秒小于60秒  显示结果+1秒，如：23秒400毫秒显示为24秒
分钟显示进一位，如：45分钟20秒显示为46分钟
大于一天显示天数进一位，如：2天8小时显示为3天
-->
<#macro difftime duration>

    <#assign min = duration / 60>
    <#assign hour = min / 60>
    <#assign day = hour / 24>
    <#assign diffmin = min % 60>
    <#assign diffhour = hour % 24>

    <#if (duration >=0) && (duration <= 60)>
    ${duration+1} 秒
    <#elseif (0 < min) && (min <= 60) >
    ${min?int+1} 分钟
    <#elseif (min > 60) && (hour > 0) && (hour <= 24) >
    ${hour?int} 小时 ${diffmin?int} 分钟
    <#elseif (hour > 24) && (day > 0) >
    ${day?int+1} 天
    </#if>
</#macro>