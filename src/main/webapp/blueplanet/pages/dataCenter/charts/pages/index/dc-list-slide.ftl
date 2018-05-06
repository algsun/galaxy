<#--
幻灯片列表 - 数据中心

@author wang.geng
@date 2014-2-13
-->

<#assign title=locale.getStr("blueplanet.dataCenter.slideList")>
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>

</#macro>

<#macro content>
<div class="row">
    <div class="offset3 span12 m-t-10">
        <fieldset>
            <legend>
                <a class="go-back" href="dataCenter/charts/toPreView/${uuid}/edit"
                   title="${locale.getStr("common.return")}">
                    <i class="mw-icon-prev"></i>
                ${locale.getStr("blueplanet.dataCenter.layout")}
                </a>
            </legend>
        </fieldset>
        <div class="buttons">
            <a href="dataCenter/charts/index/slide/toAddSlide/${uuid}/${itemId}" class="btn btn-primary" id=""
               type="button">${locale.getStr("common.insert")}</a>
            <a class="btn btn-danger" id="deleteAllSlide"
               type="button">${locale.getStr("blueplanet.dataCenter.removeAll")}</a>
        </div>
        <#if slideLists?? && slideLists?size!=0>
        <div class="lists m-t-10" style="width: 949px">
            <table class="table table-bordered table-striped table-center">
                <thead>
                <tr>
                    <th style="width: 10%">${locale.getStr("common.number")}</th>
                    <th style="width: 40%">${locale.getStr("blueplanet.dataCenter.title")}</th>
                    <th style="width: 10%">${locale.getStr("blueplanet.dataCenter.location")}</th>
                    <th style="width: 10%">${locale.getStr("blueplanet.dataCenter.playbackDuration")}</th>
                    <th style="width: 30%">${locale.getStr("common.operating")}</th>
                </tr>
                </thead>
                <tbody>
                    <#list slideLists as slide>
                    <tr>
                        <td>${slide_index+1}</td>
                        <td>${slide.title}</td>
                        <td>${slide.locationId}</td>
                        <td>${slide.refresh}</td>
                        <td>
                            <a class="btn btn-mini btn-success"
                               href="dataCenter/charts/index/slide/toUpdateSlide/${slide.relatedLayoutId}/${slide.relatedItemId}/${slide.id}">
                            ${locale.getStr("common.update")}
                            </a>
                            <a class="btn btn-mini btn-danger" id="deleteLayout"
                               onclick="deleteLayout('${slide.id}','${slide.relatedLayoutId}','${slide.relatedItemId}');">
                            ${locale.getStr("common.delete")}
                            </a>
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
<div id="hiddenData">
    <input type="hidden" value="${uuid}" id="uuid"/>
    <input type="hidden" value="${itemId}" id="itemId"/>
</div>
</#macro>

<#macro script>
<script type="text/javascript">

    $(function () {
        $('#deleteAllSlide').click(function () {
            art.dialog({
                id: "confirmDelete",
                fixed: true,
                title: message.tips,
                content: message.sureToDeleteSubSlides,
                okValue: message.ok,
                ok: function () {
                    var uuidValue = $('#uuid').val();
                    var itemIdValue = $('#itemId').val();
                    window.location.href = "dataCenter/charts/index/slide/deleteAllSlide/" + uuidValue + "/" + itemIdValue;
                },
                cancelValue: message.cancel,
                cancel: function () {
                }
            });
        });
    });

    function deleteLayout(slideId, uuid, layoutId) {
        art.dialog({
            id: "delete",
            fixed: true,
            title: message.tips,
            content: message.sureToDelete,
            okValue: message.ok,
            ok: function () {
                window.location.href = "dataCenter/charts/index/slide/deleteSlide/" + uuid + "/" + layoutId + "/" + slideId;
            },
            cancelValue: message.cancel,
            cancel: function () {
            }
        });
    }
</script>
</#macro>