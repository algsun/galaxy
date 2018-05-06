<#--
时间控件(年，月，日)

@author liuzhu
@date 2013-09-11
-->

<div class="form-group">
    <label class="m-l-10">时间类型</label>
<#if dateType==0>
    <#assign dateType=3>
</#if>
    <div class="radio-inline">
        <label class="m-t-10">
            <input id="radioYear" name="dateType" type="radio" value="1" <@checked 1,dateType/>>年
        </label>
    </div>
    <div class="radio-inline">
        <label class="m-t-10">
            <input id="radioMonth" name="dateType" type="radio" value="2" <@checked 2,dateType/>>月
        </label>
    </div>
    <div class="radio-inline">
        <label class="m-t-10">
            <input id="radioMonth" name="dateType" type="radio" value="3" <@checked 3,dateType/>>日
        </label>
    </div>
</div>
<div class="form-group">
    <label for="date">时间</label>
<#assign dateString="">
<#if dateType==1>
    <#assign dateString=date?string("yyyy")!>
<#elseif  dateType==2>
    <#assign dateString=date?string("yyyy-MM")!>
<#elseif dateType==3>
    <#assign dateString=date?string("yyyy-MM-dd")!>
</#if>

    <div class="input-group date">
        <input id="date" name="date" type="text" class="form-control" value="${dateString}">
        <div class="input-group-addon">
            <span class="glyphicon glyphicon-th"></span>
        </div>
    </div>
</div>

