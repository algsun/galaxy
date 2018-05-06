<#--
时间控件(年，月，日)

@author liuzhu
@date 2013-09-11
-->


<label class="m-l-10">时间类型</label>
<#if dateType==0>
    <#assign dateType=3>
</#if>
<label class="radio m-l-10">
    <input id="radioYear" name="dateType" type="radio" value="1" <@checked 1,dateType/> >年
</label>
<label class="radio m-l-10">
    <input id="radioMonth" name="dateType" type="radio" value="2" <@checked 2,dateType/>>月
</label>
<label class="radio m-l-10">
    <input id="radioMonth" name="dateType" type="radio" value="3" <@checked 3,dateType/>>日
</label>
<label class="m-l-10">时间</label>
<#assign dateString="">
<#if dateType==1>
    <#assign dateString=date?string("yyyy")!>
<#elseif  dateType==2>
    <#assign dateString=date?string("yyyy-MM")!>
<#elseif dateType==3>
    <#assign dateString=date?string("yyyy-MM-dd")!>
</#if>
<input id="date" name="date" class="input-medium Wdate" type="text" value="${dateString}">