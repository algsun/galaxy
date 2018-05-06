<#--
数据中心目前暂时在环境监控中，不排除以后会移动的可能
-->
<#import _pagePath as _page>

<!DOCTYPE html>
<html>
<head>
<#include "../../../../_common/common-head.ftl">
    <title>${_page.title}</title>
<#include "../../../../_common/common-css.ftl">
<@_page.head/>

</head>
<body class="demos " style="height：100%" data-server-time="${.now?long?c}">
    <#if siteId??>
        <#include "../../../../_common/header.ftl">
    <#else>
        <#include "../../../../_common/issue-info-header.ftl">
    </#if>
<div id="gcontent" class="container m-t-50" style="width: 100%;height: 100%">
<#include "../_common/message.ftl">
<div class="row">
    <div class="offset3 span12">
        <@messageTooltip></@messageTooltip>
    </div>
</div>
    <@_page.content/>
</div>

<#include "../../../../_common/common-js.ftl">

<@_page.script/>

<#--<#include "../../../../_common/last-resources.ftl">-->

</body>
</html>