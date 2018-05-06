<#--
分页工具

@author gaohui
@date 2013-01-30
-->

<#--
分页

参数:
url:String 被分页的链接，不包括分页参数
currentPageIndex:Integer 当前页, 从 1 开始
pageSum:Integer 总页数
pageIndexName:String 分页参数名称, 默认为 "page"

-->
<#macro pagination url currentPageIndex, pageSum, pageIndexName = "page">
<#-- 如果没有数据，直接返回 -->
    <#if pageSum == 0>
        <#return>
    </#if>

<div class="pagination pagination-right">
    <ul>

    <#-- 上一页 -->
        <li><a href="${url}&${pageIndexName}=${_max(currentPageIndex - 1, 1)?c}" title="上一页">«</a></li>

        <#list _genPageSegment(currentPageIndex, pageSum) as segment>
            <@_pageSegment url, currentPageIndex, pageSum, segment.start, segment.end, pageIndexName/>

            <#if segment_has_next>
                <li class="disabled"><a>...</a></li>
            </#if>
        </#list>


    <#-- 下一页 -->
        <li><a href="${url}&${pageIndexName}=${_min(currentPageIndex + 1, pageSum)?c}" title="下一页">»</a></li>

    </ul>
</div>
</#macro>

<#--
生成一段链接

url:String 链接
currentPageIndex:Integer 当前页面
pageSum:Integer 总页数
pageStart:Integer 开始页面
pageEnd:Integer 结束页面
pageIndexName:String 分页参数名
-->
<#macro _pageSegment url, currentPageIndex, pageSum, pageStart, pageEnd, pageIndexName>
    <#list pageStart..pageEnd as pageIndex>
    <li
        <#if currentPageIndex??><#if currentPageIndex == pageIndex>class="disabled"</#if></#if>
            >
        <a href="${url}&${pageIndexName}=${pageIndex?c}">${pageIndex?c}</a>
    </li>
    </#list>
</#macro>


<#--
生成分页段数

currentPageIndex: 当前页
pageSum: 总页数
-->
<#function _genPageSegment currentPageIndex, pageSum>
    <#local segmentSize = 5>
<#--  如果页数不超过 segmentSize 则是一段 -->
    <#if pageSum <= segmentSize>
        <#return [{"start": 1, "end": pageSum}]>
    <#else>

    <#-- 如果当前页在第一段, 返回两段 -->
        <#if currentPageIndex <= 3>
            <#return [
            {"start": 1, "end": segmentSize},
            {"start": pageSum, "end": pageSum}
            ]>
        <#-- 如果当前页在最后一段, 返回两段 -->
        <#elseif (currentPageIndex >= pageSum -2)>
            <#return [
            {"start": 1, "end": 1},
            {"start": pageSum - (segmentSize - 1), "end": pageSum}
            ]>
        <#-- 如果当前页在中间一段, 返回三段 -->
        <#else>
            <#return [
            {"start": 1, "end": 1},
            {"start": currentPageIndex - 2, "end": currentPageIndex + 2},
            {"start": pageSum, "end": pageSum}
            ]>
        </#if>

    </#if>
</#function>

<#function _max x y>
    <#if (x<y)><#return y><#else><#return x></#if>
</#function>

<#function _min x y>
    <#if (x<y)><#return x><#else><#return y></#if>
</#function>