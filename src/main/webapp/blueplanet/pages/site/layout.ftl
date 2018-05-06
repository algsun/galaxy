<#--
站点页面布局

@author gaohui
@date 2013-01-23
-->

<#include "/common/pages/common-tag.ftl">
<!DOCTYPE html>
<html>
<head>
<#-- import page START -->
<#-- 引入内容页面 -->
<#import _pagePath as page>
<#-- import page END -->

<#include "../_common/common-head.ftl">
    <title>${page.title}</title>

<#include "../_common/common-css.ftl">

<#-- head START -->
<@page.head/>
<#-- head END -->

</head>
<body data-server-time="${.now?long?c}">

<#assign currentTopPrivilege = "blueplanet:logicGroup"/>
<#include "../_common/header.ftl">

<#-- 区域设备树 -->
<#--<div.left-aside-container">-->
<#include "../_common/zone-device-tree.ftl">
<#--</div>-->

<#-- 伸缩条 -->
<div class="shrink-bar" title="${locale.getStr("blueplanet.common.shrinkBar")}">
    <div class="shrink-bar-icon"></div>
</div>

<div class="content-container">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <ul class="breadcrumb m-b-10 p-v-5">
                    <li class="muted">${locale.getStr("blueplanet.common.currentLocation")}</li>
                    <li class="m-l-10 il-blk">
                        <a class="btn disabled" href="site/realtime-data"
                           style="border-radius: 0; cursor: pointer;">${locale.getStr("common.site")}</a>
                    </li>
                </ul>
            <#-- 显示功能 tab -->
            <#macro contentTab tabIndex, currentIndex, href, name>
                <li <#if currentIndex == tabIndex>class="active"</#if>>
                    <a <#if currentIndex == tabIndex>
                            href="#tab0" data-toggle="tab"
                    <#else>
                            href="${href}"
                    </#if>
                    >${name}</a>
                </li>
            </#macro>
                <div class="tabbable"> <!-- Only required for left/right tabs -->
                    <ul class="nav nav-tabs">
                        <@contentTab 0, page.tabIndex, "summarize", locale.getStr("blueplanet.common.overview") />
                        <@contentTab 1, page.tabIndex, "site", locale.getStr("blueplanet.common.summary") />
                        <@contentTab 2, page.tabIndex, "site/realtime-data", locale.getStr("blueplanet.common.realtimeData") />
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab1">

                        <#-- content START -->
                        <@page.content/>
                        <#-- content END -->

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "../_common/common-js.ftl">

<@scriptTag "js/device-tree.js"/>

<#-- script START -->
<@page.script/>
<#-- script END -->

<#include "../_common/last-resources.ftl">
</body>
</html>