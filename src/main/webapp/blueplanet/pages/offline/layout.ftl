<#import _pagePath as _page>

<!DOCTYPE html>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${_page.title}</title>
<#include "../_common/common-css.ftl">
<@_page.head/>
</head>
<body>
<#include "../_common/header.ftl">
<div id="gcontent" class="container-fluid m-t-50">
<#include "../_common/message.ftl">
<div class="container m-t-10">
<@messageTooltip></@messageTooltip>
</div>

    <div class="row-fluid">
        <div class="span12">
        <@_page.content/>
        </div>
    </div>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<@_page.script/>

<#include "../_common/last-resources.ftl">

</body>
</html>