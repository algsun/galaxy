<#--
  -
  -@author Wang yunlong
  -@time  13-3-22  上午10:40
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>index</title>

<#include "../_common/common-css.ftl">

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:home">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#--<#include "sub-navs.ftl">-->
<#--<@subNavs "proxima:ftp:list"></@subNavs>-->

    <#--your content-->
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#--your js-->
</body>
</html>