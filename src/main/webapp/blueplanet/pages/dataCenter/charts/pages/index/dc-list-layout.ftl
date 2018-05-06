<#--
布局列表 - 数据中心

@author wang.geng
@date 2013-12-06
-->

<#assign title=locale.getStr("blueplanet.dataCenter.layoutList")>
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>

</#macro>

<#macro content>
<div class="row">
    <div class="offset3 span12">
        <div class="m-t-40 m-l-10">
            <div class="buttons">
                <#if security.isPermitted("blueplanet:datacenter:addlayout")>
                    <a href="dataCenter/charts/toAddLayout" class="btn btn-primary" id="addLayout"
                       type="button">${locale.getStr("blueplanet.dataCenter.newLayout")}</a>
                </#if>
            </div>
            <#if layoutList?? && layoutList?size!=0>
            <div class="lists m-t-10" style="width: 949px">
                <table class="table table-bordered table-striped table-center">
                    <thead>
                    <tr>
                        <th style="width: 10%">${locale.getStr("common.number")}</th>
                        <th>${locale.getStr("blueplanet.dataCenter.layoutName")}</th>
                        <th style="width: 20%">${locale.getStr("blueplanet.dataCenter.createTime")}</th>
                        <th style="width: 30%">${locale.getStr("common.operating")}</th>
                    </tr>
                    </thead>
                    <tbody>

                        <#list layoutList as layout>
                        <tr>
                            <td>${layout_index+1}</td>
                            <td>${layout.description}</td>
                            <td>${layout.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td>
                                <#if security.isPermitted("blueplanet:datacenter:preview")>
                                    <a class="btn btn-mini btn-inverse"
                                       href="dataCenter/charts/toPreView/${layout.layoutId}/preview">${locale.getStr("blueplanet.dataCenter.preview")}</a>
                                </#if>
                                <#if security.isPermitted("blueplanet:datacenter:edit")>
                                    <a class="btn btn-mini btn-success"
                                       href="dataCenter/charts/toPreView/${layout.layoutId}/edit">${locale.getStr("common.update")}</a>
                                </#if>
                                <#if security.isPermitted("blueplanet:datacenter:delete")>
                                    <a class="btn btn-mini btn-danger" id="deleteLayout"
                                       onclick="deleteLayout('${layout.layoutId}');">${locale.getStr("common.delete")}</a>
                                </#if>
                                <#if security.isPermitted("blueplanet:datacenter:edit")>
                                    <a class="btn btn-mini btn-primary" id="config"
                                       href="dataCenter/charts/toConfig/${layout.layoutId}">${locale.getStr("blueplanet.dataCenter.setting")}</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>

                    </tbody>
                </table>
            <#else>
                <h4>${locale.getStr("common.noData")}</h4>
            </#if>
        </div>
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript">

    function deleteLayout(layoutId) {
        art.dialog({
            id: "delete",
            fixed: true,
            title: message.tips,
            content: message.sureToDelete,
            okValue: message.ok,
            ok: function () {
                window.location.href = "dataCenter/charts/deleteLayout/" + layoutId;
            },
            cancelValue: message.cancel,
            cancel: function () {
            }
        });
    }
</script>
</#macro>