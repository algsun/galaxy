<#--
藏品数据导出等待页面

@author liuzhu
@date 2013-07-29
@cehck @gaohui #5066 2013-08-15
-->
<!DOCTYPE html>
<html>
<head>

<#include "../_common/common-head.ftl">
    <title>藏品信息导出 - 资产管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>

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
    <input type="hidden" value="${param}" id="param">
    <input type="hidden" value="${checkPro}" id="checkPro">
</div>
<#include "../_common/common-js.ftl">

<#if (count > 0)>
<script type="text/javascript">

    var param = $("#param").val();
    var checkPro = $("#checkPro").val();
    var downloadUrl = "relic-data-export.action"+param+"&checkPro="+checkPro;
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