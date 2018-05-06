<#--
  - 页面模版
  -@author li.jianfei
  -@time  2013-4-11 13:16
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
<#assign currentTopPrivilege = "uma:home">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#--<#include "sub-navs.ftl">-->
<#--<@subNavs "uma:ftp:list"></@subNavs>-->

<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>


    <#--your content-->
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#--your js-->
</body>
</html>