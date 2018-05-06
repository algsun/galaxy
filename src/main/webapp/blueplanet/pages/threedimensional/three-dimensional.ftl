<#--
3d模型管理
@author 王耕
@date 2015.06.10

-->

<#assign title>${locale.getStr("blueplanet.threeDimensional.title")}</#assign>
<#include "/common/pages/common-tag.ftl"/>
<#-- 当前权限标识 -->
<#assign currentPrivilege = "blueplanet:monitor:threedimensional">
<#macro head>
    <#include "../_common/common-css.ftl">
</#macro>

<#macro content>
<fieldset>
    <legend>
        ${locale.getStr("blueplanet.threeDimensional")}
    </legend>
</fieldset>
<div class="row-fluid">
    <a href="three-dimensional/toAddDimensional" class="btn btn-small btn-success">${locale.getStr("blueplanet.threeDimensional.newModel")}</a>
</div>
<div class="row-fluid m-t-30">
    <#include "../_common/message.ftl">
    <@messageTooltip/>
    <table class="table table-bordered table-striped table-center">
        <thead>
        <th style="width: 50px;">${locale.getStr("common.number")}</th>
        <th style="width: 330px;">${locale.getStr("blueplanet.threeDimensional.modelName")}</th>
        <th style="width: 100px">${locale.getStr("blueplanet.threeDimensional.describe")}</th>
        <th style="width: 200px">${locale.getStr("common.location")}</th>
        <th style="width: 100px;">${locale.getStr("blueplanet.threeDimensional.uploadDate")}</th>
        <th style="width: 100px;">${locale.getStr("common.operating")}</th>
        </thead>
        <tbody>
            <#if threeDimensionals?size != 0>
                <#list threeDimensionals as threeDimensional>
                <tr>
                    <td>${threeDimensional_index+1}</td>
                    <td style="text-align: left">${threeDimensional.path}</td>
                    <td>${threeDimensional.remark!}</td>
                    <td>
                        <#list threeDimensional.locationVOs as location>
                        ${location.locationName}<br/>
                        </#list>
                    </td>
                    <td>${threeDimensional.uploadtime?string("yyyy-MM-dd")}</td>
                    <td>
                        <#if security.isPermitted("blueplanet:monitor:threedimensional:preview")>
                            <button value="${threeDimensional.id}" class="btn btn-mini btn-success btn-preview">
                                ${locale.getStr("blueplanet.threeDimensional.browse")}
                            </button>
                        </#if>
                        <#if security.isPermitted("blueplanet:monitor:threedimensional:look")>
                            <button value="${threeDimensional.id}" class="btn btn-mini btn-inverse btn-look">
                                ${locale.getStr("blueplanet.threeDimensional.analysis")}
                            </button>
                        </#if>
                        <#if security.isPermitted("blueplanet:monitor:threedimensional:edit")>
                            <button value="${threeDimensional.id}" class="btn btn-mini btn-primary btn-edit">
                                ${locale.getStr("common.update")}
                            </button>
                        </#if>
                        <#if security.isPermitted("blueplanet:monitor:threedimensional:delete")>
                            <button value="${threeDimensional.id}" class="btn btn-mini btn-danger btn-delete">
                                ${locale.getStr("common.delete")}
                            </button>
                        </#if>
                    </td>
                </tr>
                </#list>
            </#if>
        </tbody>
    </table>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../common/js/form-check.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript"
        src="../assets/jquery-ui/1.9.2/js/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="../orion/js/editRelicLabel.js"></script>
    <@scriptTag "../common/js/util.js"/>
    <@scriptTag "js/pnotify.js"/>
<script type="text/javascript">
    $(function () {
        $(".btn-delete").click(function () {
            var id = this.value;
            art.dialog({
                id: "delete",
                fixed: true,
                title: message.tips,
                content: message.sureToDelete,
                okValue: message.ok,
                ok: function () {
                    window.location.href = "three-dimensional/deleteFile?dimensionalId=" + id;
                },
                cancelValue: message.cancel,
                cancel: function () {
                }
            });
        });
        $(".btn-look").click(function () {
            var id = this.value;
            window.location.href = "three-dimensional/dimensionalPreview?dimensionalId=" + id;
        });
        $(".btn-edit").click(function () {
            var id = this.value;
            window.location.href = "three-dimensional/toEditDimensional?dimensionalId=" + id;
        });
        $(".btn-preview").click(function () {
            var id = this.value;
            window.location.href = "three-dimensional/dimensionalPreview2?dimensionalId=" + id;
        });
    });
</script>
</#macro>

