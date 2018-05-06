<#--
3d模型管理
@author 王耕
@date 2015.06.12

-->

<#assign title>${locale.getStr("blueplanet.threeDimensional.title")}</#assign>
<#include "/common/pages/common-tag.ftl"/>
<#-- 当前权限标识 -->
<#assign currentPrivilege = "blueplanet:monitor:threedimensional">
<#macro head>
<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
    <#include "../_common/common-css.ftl">
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span12">
        <div class="page-header title">
            <h3>
                <span class="f-n">${locale.getStr("blueplanet.threeDimensional.updateModeProperties")}</span>
            </h3>
        </div>
    </div>
</div>
<div class="row-fluid">
    <form class="form-horizontal" id="editFrom" action="three-dimensional/editDimensional" method="post"
          enctype="multipart/form-data" name="uploadfileform">
        <input type="hidden" name="dimensionalId" value="${threeDimensionalPO.id}"/>

        <div class="control-group">
            <label class="control-label">${locale.getStr("blueplanet.threeDimensional.describe")}</label>

            <div class="controls">
                <textarea name="remark" id="" cols="180" rows="10"
                          style="width:380px">${threeDimensionalPO.remark!}</textarea>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">${locale.getStr("common.location")}</label>

            <div class="controls">
                <@selectOption locationMap dimensionalLocations/><br>
                <span calss="help-block red" id="designSpan"></span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">${locale.getStr("blueplanet.threeDimensional.modelName")}</label>

            <div class="controls">
                <input type="text" readonly value="${threeDimensionalPO.path}"/>

                <div class="m-t-30">
                    <button class="btn btn-small btn-primary" type="button" id="upload-file-btn-save">
                    ${locale.getStr("common.save")}
                    </button>
                    <a class="btn btn-small" href="three-dimensional" id="upload-file-btn-return">
                    ${locale.getStr("common.return")}
                    </a>
                </div>
            </div>
        </div>
    </form>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#locationIds").select2();
        $("#upload-file-btn-save").click(function () {
            var locationIds = $("#locationIds").val();
            if (locationIds != null) {
                $("#editFrom").submit();
            } else {
                var content = "<i class='mw-icon-cancel'></i> " + "*" + message.locationEmpty;
                $("#designSpan").empty().append(content);
            }
        });
    });
</script>
</#macro>
<#macro selectOption locationMap dimensionalLocations>
<select multiple='' style="width: 300px;margin-left: 8px;" name="locationIds" id="locationIds">
    <#if locationMap?size != 0>
        <#list locationMap?keys as key>
        <optgroup label="${key}">
            <#list locationMap[key] as location>
                <option value="${location.id}"
                    <#list dimensionalLocations as dimensionalLocation>
                        <#if location.id == dimensionalLocation.locationId>selected="true" </#if>
                    </#list>
                > ${location.locationName}</option>
            </#list>
        </#list>
    </#if>
</select>
</#macro>

