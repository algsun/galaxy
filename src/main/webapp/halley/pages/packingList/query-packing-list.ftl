<#--
外展管理，装箱单

@author li.jianfei
@date 2013-09-27
-->
<#assign title="装箱单 - 外展管理">
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
<style type="text/css">
    .table thead {
        background-color: #ececec;
    }
</style>
</#macro>

<#macro content>
<div class="row">
    <div class="span12">
        <fieldset>
            <legend>
                <a class="go-back" href="queryExhibitionStateList/exhibition/${exhibitionId}" title="返回">
                    <i class="mw-icon-prev"></i>装箱清单
                </a>
            </legend>
        </fieldset>
    </div>
</div>

    <#if mapPackingLists?? && (mapPackingLists?size >0)>
    <div class="tabbable"> <!-- Only required for left/right tabs -->
        <ul class="nav nav-tabs">
            <#list mapPackingLists?keys as car>
                <li <#if car_index==0>class="active"</#if>><a href="#tab${car.id}"
                                                              data-toggle="tab">${car.plateNumber!}</a></li>
            </#list>
        </ul>
        <div class="tab-content">

            <#list mapPackingLists?keys as car>
                <#assign packingLists = mapPackingLists.get(car)>
                <div class="tab-pane <#if car_index==0>active</#if>" id="tab${car.id}">
                    <#if packingLists?? && (packingLists?size>0)>
                        <#list packingLists as packingList>
                            集装箱 ${packingList.sequence}
                            <table class="table table-bordered table-striped table-center">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>文物名称</th>
                                    <th>总登记号</th>
                                    <th>时代</th>
                                    <th>级别</th>
                                    <th>质地</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <#list packingList.relicList as relic>
                                    <tr>
                                        <td>${relic_index+1}</td>
                                        <td>${relic.name}</td>
                                        <td>${relic.totalCode}
                                            <#if relic.hasTag>
                                                <li class="icon-tag" title="标签"></li>
                                            </#if>
                                        </td>
                                        <td>${relic.era!}</td>
                                        <td>${relic.level!}</td>
                                        <td>${relic.texture!}</td>
                                    </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </#list>
                    <#else>
                        <h4>暂无数据</h4>
                    </#if>
                </div>
            </#list>
        </div>
    </div>
    <#else>
    <div>
        <h4>暂无数据</h4>
    </div>
    </#if>
</#macro>

<#macro script>

</#macro>
