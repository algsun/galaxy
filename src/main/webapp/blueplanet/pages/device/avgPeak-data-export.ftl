<!DOCTYPE html>
<html>
<head>

<#include "../_common/common-head.ftl">
    <title>均峰值数据导出 - 环境监控</title>
<#include "../_common/common-css.ftl">

</head>
<body data-server-time="${.now?long?c}">

<div class="container-fluid m-t-30" style="width: 300px;">
    <div class="row-fluid">
        <div class="span12">
        <#if (count > 0)>
            <div class="progress progress-striped active">
                <div class="bar" style="width: 100%;"></div>
            </div>
            <h3>请稍候...</h3>
        <#else>
            <h3>暂无数据</h3>
        </#if>
        </div>
    </div>
</div>
<#include "../_common/common-js.ftl">

<#if (count > 0)>
<script type="text/javascript">
    var downloadUrl = "avgpeak-data-export.action?deviceId=${deviceId}&startDate=${startDate?string("yyyy-MM-dd HH:mm:ss")}&endDate=${endDate?string("yyyy-MM-dd HH:mm:ss")}";
    var basePath = $("base").attr("href");
    if (basePath) {
        downloadUrl = basePath + downloadUrl;
    }
    location.href = downloadUrl;
</script>
</#if>

<#include "../_common/last-resources.ftl">
</body>
</html>