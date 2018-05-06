<#import _pagePath as _page>

<!DOCTYPE html>
<html>
<head>
    <#include "../_common/common-head.ftl">
    <title>${_page.title}</title>
    <#include "../_common/common-css-b4.ftl">
    <@_page.head/>
        <style type="text/css">
            body {
                font-size: 14px;
            }
        </style>

</head>
<body data-server-time="${.now?long?c}">

<#include "../_common/header-b4.ftl">


<div id="gcontent" class="container-fluid m-t-50">
    <div class="row">

        <div class="col-xl-3">
            <#if _page.currentPrivilege??>
                <#assign currentPrivilege = _page.currentPrivilege>
            </#if>
            <#-- 左侧导航 -->
            <#include "../_common/navs-b4.ftl">
        </div>

        <div class="col-xl-9">
        <#include "../_common/message.ftl">
           <@messageTooltip></@messageTooltip>
        <@_page.content/>
        </div>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<@_page.script/>
<script type="text/javascript">
    $(function () {
        $(document).on('click', '.subsystemHead', function () {
            art.dialog({
                title: '正在努力加载!'
            });
        });
    });
</script>

<#include "../_common/last-resources.ftl">

</body>
</html>