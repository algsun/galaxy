<#--
未登录的布局页面

@author gaohui
@date 2013-05-10
-->
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

<#include "../_common/header-not-logined-for-galaxy.ftl">


<div id="gcontent" class="container">
    <@_page.content/>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<@_page.script/>

<#include "../_common/last-resources.ftl">
</body>
</html>

