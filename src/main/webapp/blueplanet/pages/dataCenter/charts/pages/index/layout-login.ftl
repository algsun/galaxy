<#--
快捷登陆布局文件
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
<body class="demos "  data-server-time="${.now?long?c}">

<#--<#include "../../../../_common/header.ftl">-->


<div id="gcontent" class="container" style="width: 100%;height: 100%">
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
</body>
</html>