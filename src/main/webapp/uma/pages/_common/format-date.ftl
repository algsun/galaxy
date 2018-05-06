<#--
处理日期格式
@author wang.rensong
@date 2013-05-06
-->

<#--
参数
  dateString 日期格式 例如，年（yyyy） 年月（yyyy-MM） 年月日（yyyy-MM-dd）
  dateStr  日期格式 例如，年（yyyy年） 年月（yyyy年MM月） 年月日（yyyy年MM月dd日）
-->

<#function formatDate dateType,date>
    <#if dateType==0>
        <#assign dateType=3>
    </#if>

    <#if dateType==1>
        <#local dateString=date?string("yyyy")!>
    <#elseif dateType==2>
        <#local dateString=date?string("yyyy-MM")!>
    <#elseif dateType==3>
        <#local dateString=date?string("yyyy-MM-dd")!>
    </#if>
    <#return dateString>
</#function>

<#function formatDateChinese dateType,date>
    <#if dateType==0>
        <#assign dateType=3>
    </#if>
    <#if dateType==1>
        <#local dateStr=date?string("yyyy")!>
    <#elseif dateType==2>
        <#local dateStr=date?string("yyyy年MM月")!>
    <#elseif dateType==3>
        <#local dateStr=date?string("yyyy年MM月dd日")!>
    </#if>
    <#return dateStr>
</#function>