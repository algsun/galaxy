<#--
设备管理页面布局

@author gaohui
@date 2013-01-24
-->

<#include "/common/pages/common-tag.ftl">
<!DOCTYPE html>
<html>
<head>

<#-- import page START -->
<#-- 引入内容页面 -->
<#import _pagePath as _page>
<#-- import page END -->


<#include "../../_common/common-head.ftl">
    <title>${_page.title}</title>

<#include "../../_common/common-css.ftl">

<#-- head START -->
<@_page.head/>
<#-- head END -->

</head>
<body data-server-time="${.now?long?c}">

<#include "../../_common/header.ftl">

<#-- 区域设备树 -->
<#--<div.left-aside-container">-->
<#include "../../_common/zone-device-tree.ftl">
<#--</div>-->

<#--伸缩条-->
<div class="shrink-bar" title="${locale.getStr("blueplanet.common.shrinkBar")}">
    <div class="shrink-bar-icon"></div>
</div>

<div class="content-container">
    <div class="container-fluid">

    <#-- content START -->
    <@_page.content/>
    <#-- content END -->

    </div>
</div>

<#include "../../_common/common-js.ftl">

<@scriptTag "js/device-tree.js"/>

<#-- script START -->
<@_page.script/>
<#-- script END -->

<#include "../../_common/last-resources.ftl">
</body>
</html>