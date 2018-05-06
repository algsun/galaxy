<#--
    @param name 条件名称
    @param labelList 标签列表
    @param linesequence 用来判断是第一行还是最后一行

-->
<#macro queryConditions name enName labelList linesequence>
<input id="hiddenValue${enName}" name="${enName}" type="hidden"
       value="<#if enName == "zoneId">${zoneId!}<#elseif enName == "state">${state!}<#elseif enName == "levelId">${levelId!}<#elseif enName == "textureId">${textureId!}<#elseif enName == "eraId">${eraId!}<#elseif enName =="labelId">${labelId!}<#elseif enName =="logOff">${logOff!}<#else></#if>"/>
<#-- num 一行最多显示多少个标签 -->
    <#assign num = 6>

<#-- itemWidth 标签站宽度的百分比 -->
    <#assign itemWidth = "8%"/>

<#-- labels 共有多少个标签 -->
    <#assign labels = labelList?size>

    <#if labels <= num>
    <tr id="${enName}" class="${enName}firstLine" line-seq="first">
        <@labelhead name "全部" "" 0/>
        <@labelbody 0 num-1 labels labelList itemWidth ""/>
        <td style="width: ${itemWidth}"></td>
        <@labeltail linesequence/>
    </tr>
    <#elseif (labels > num)>
        <#if labels%num == 0>
            <#assign lineNum = (labels/num)?int>
        <#elseif (labels%num > 0)>
            <#assign lineNum = (labels/num)?int +1>
        </#if>
        <#list 0..(lineNum-1) as index>
            <#if index == 0>
            <tr id="${enName}" class="${enName}firstLine"  line-seq="first">
                <@labelhead name "全部" "" index/>
                <@labelbody 0 num-1 labels labelList itemWidth ""/>
                <td style="text-align:right;width: ${itemWidth}"><small class="link" onclick="moreLabel('${enName}')">更多</small></td>
                <@labeltail linesequence/>
            </tr>
            <#elseif (index > 0)>
            <tr class="${enName} <#if index == (lineNum-1)> ${enName}lastLine</#if>" style="display: none">
                <@morelabelhead "" "" "border-bottom: none;" index/>
                <@labelbody num*index num*index+num-1 labels labelList itemWidth "border-bottom: none;"/>
                <td style="width: ${itemWidth};border-bottom: none;"></td>
            </tr>
            </#if>
        </#list>
    </#if>
</#macro>

<#-- 一行条件标签的头部，包括条件名称，全部选项 -->
<#macro labelhead name all style index>
<td class="labelName" style="${style};width: 8%">${name}：</td>
<td class="allSelected" style="${style}"><span style="<#if index == 0>background-color:#5FBCEB;color: #ffffff;</#if>" class="link">${all}</span></td>
</#macro>

<#macro morelabelhead name all style index>
<td class="labelName" style="${style};width: 8%">${name}</td>
<td class="allSelected" style="${style}"><span style="<#if index == 0>background-color:#5FBCEB;color: #ffffff;</#if>" class="link">${all}</span></td>
</#macro>

<#-- 一行条件标签的标签部分 -->
<#macro labelbody start end labels labelList itemWidth style>
    <#list start..end as index>
        <#if index < labels>
        <td class="selected" style="width: ${itemWidth};${style}" data-value="${labelList[index].id}"><span class="link">${labelList[index].name}</span></td>
        <#else>
        <td style="width: ${itemWidth};${style}"></td>
        </#if>
    </#list>
</#macro>

<#-- 一行标签末尾的操作 -->
<#macro labeltail linesequence>
    <#if linesequence == "first">
    <div style="width: 18%;position:relative;left: 50px;top: 30px;float: right">
        <span id="exportButton" class="btn btn-small" style="width: 40px">导出</span>
    </div>
    <#elseif linesequence == "midd">
    <#elseif linesequence == "last">
    <td style="width: 18%;text-align: center">
        <a class="close-input <#if inputView>hide</#if>" title="收起查询条件"> 收起</a>
        <a class="open-input  <#if !inputView>hide</#if>" title="展开查询条件"> 精确查找</a>
    </td>
    </#if>
</#macro>