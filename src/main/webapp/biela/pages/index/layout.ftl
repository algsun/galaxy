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

<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>
<@_page.content/>
<#include "../_common/common-js.ftl">

<@_page.script/>

</body>
</html>