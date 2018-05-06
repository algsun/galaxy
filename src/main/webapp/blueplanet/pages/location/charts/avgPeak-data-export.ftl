<!DOCTYPE html>
<html>
<head>

<#include "../../_common/common-head.ftl">
    <title>${locale.getStr("blueplanet.location.valueExportTitle")}</title>
<#include "../../_common/common-css.ftl">

</head>
<body data-server-time="${.now?long?c}">

<div class="container-fluid m-t-30" style="width: 300px;">
    <div class="row-fluid">
        <div class="span12">
        <#if (count > 0)>
            <div class="progress progress-striped active">
                <div class="bar" style="width: 100%;"></div>
            </div>
            <h3>${locale.getStr("blueplanet.location.wait")}...</h3>
        <#else>
                <h3>${locale.getStr("common.noData")}</h3>
        </#if>
        </div>
    </div>
</div>
<#include "../../_common/common-js.ftl">

<#if (count > 0)>
<script type="text/javascript">
    var downloadUrl = "avgpeak-data-export.action?locationId=${locationId}&startDate=${startDate?string("yyyy-MM-dd HH:mm:ss")}&endDate=${endDate?string("yyyy-MM-dd HH:mm:ss")}";
    var basePath = $("base").attr("href");
    if (basePath) {
        downloadUrl = basePath + downloadUrl;
    }
    location.href = downloadUrl;
</script>
</#if>

<#include "../../_common/last-resources.ftl">
</body>
</html>