<#--
 分页
 @author Wang yunlong
 @date  12-11-21 下午1:57
 -->
<#--
  获取带当前页参数的url
  @param url 查询地址
  @param p 当前页
 -->
<#macro getUrl url p>${url}&index=${p?c}</#macro>
<#--
  分页 宏
 注意： 应用该宏的时候传入的参数url需要自带至少一个参数，即：url是....action?param=...格式
  @param url 查询的地址
  @param index 当前页
  @param pageCount 总页数
-->
<#macro pagging url index pageCount>
<div class="row">
    <div class="span12">
    <#--无结果-->
        <#if pageCount <= 0>
            <div class="alert alert-info">
                <a class="close" data-dismiss="alert">×</a>
                没有符合条件的结果！
            </div>
        <#else>
            <div class="pagination m-t-0">
                <ul>
                <#--页数小于10页-->
                    <#if (pageCount > 0 && pageCount <= 10)>
                        <li <#if index==1>class="disabled" </#if>>
                            <a href="<#if index!=1><@getUrl url index-1></@getUrl><#else><@getUrl url index></@getUrl></#if>">
                                <i class="icon-arrow-left"></i>
                            </a>
                        </li>
                        <#list 1..pageCount as p>
                            <#if p==index>
                                <li class="active"><a href="<@getUrl url p></@getUrl>">${p?c}</a></li>
                            <#else>
                                <li><a href="<@getUrl url p></@getUrl>">${p?c}</a></li>
                            </#if>
                        </#list>
                        <li <#if index == pageCount>class="disabled" </#if>>
                            <a href="<#if index == pageCount><@getUrl url index></@getUrl><#else><@getUrl url index+1></@getUrl></#if>">
                                <i class="icon-arrow-right"></i>
                            </a>
                        </li>
                    <#--页数大于10页-->
                    <#else>
                        <li <#if index == 1>class="disabled" </#if>>
                            <a title="上一页" href="<#if index != 1>
                            <@getUrl url index-1></@getUrl><#else><@getUrl url index></@getUrl></#if>">
                                <i class="icon-arrow-left"></i>
                            </a>
                        </li>
                    <#--当前页在前三页-->
                        <#if index<=3>
                            <#list 1..5 as p>
                                <li <#if p==index>class="active"</#if>><a href="<@getUrl url p></@getUrl>">${p?c}</a></li>
                            </#list>
                            <li class="disabled"><a href="">...</a></li>
                            <li><a href="<@getUrl url pageCount></@getUrl>">${pageCount?c}</a></li>
                        <#--当前也在后三页-->
                        <#elseif (index>=pageCount-3)>
                            <li><a href="<@getUrl url 1></@getUrl>">1</a></li>
                            <li class="disabled"><a href="">...</a></li>
                            <#list pageCount-5..pageCount as p>
                                <li <#if p==index>class="active"</#if>><a href="<@getUrl url p></@getUrl>">${p?c}</a></li>
                            </#list>
                        <#--当前页在中间-->
                        <#else>
                            <li><a href="<@getUrl url 1></@getUrl>">1</a></li>
                            <li class="disabled"><a href="">...</a></li>
                            <li><a href="<@getUrl url index-2></@getUrl>">${(index-2)?c}</a></li>
                            <li><a href="<@getUrl url index-1></@getUrl>">${(index-1)?c}</a></li>
                            <li class="active"><a href="<@getUrl url index></@getUrl>">${index?c}</a></li>
                            <li><a href="<@getUrl url index+1></@getUrl>">${(index+1)?c}</a></li>
                            <li><a href="<@getUrl url index+2></@getUrl>">${(index+2)?c}</a></li>
                            <li class="disabled"><a href="">...</a></li>
                            <li><a href="<@getUrl url pageCount></@getUrl>">${pageCount?c}</a></li>
                        </#if>
                        <li <#if index==pageCount>class="disabled" </#if>>
                            <a title="下一页" href="<#if index==pageCount><@getUrl url index></@getUrl><#else>
                            <@getUrl url index+1></@getUrl></#if>">
                                <i class="icon-arrow-right"></i>
                            </a>
                        </li>
                    </#if>
                </ul>
            </div>
        </#if>
    </div>
</div>
</#macro>
