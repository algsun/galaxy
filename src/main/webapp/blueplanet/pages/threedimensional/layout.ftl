<#--
网络拓扑图页面布局

@author 王耕
@date 2015-06-10
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

<#assign currentTopPrivilege = "blueplanet:monitor"/>
<#include "../_common/header.ftl">
<#-- 区域设备树 -->
<#--<div.left-aside-container">-->
<#--<#include "../_common/zone-device-tree.ftl">-->
<#--</div>-->
<#-- 伸缩条 -->
<#--<div class="shrink-bar" title="点击收缩">-->
    <#--<div class="shrink-bar-icon"></div>-->
<#--</div>-->

<#--<div class="content-container">-->
<div style="margin-top: 50px">
    <div class="container-fluid ">
        <div class="row-fluid" >
            <div class="span12">
                        <@page.content/>
            </div>
        </div>
    </div>
   </div>
<#--</div>-->

<#include "../_common/common-js.ftl">
<@scriptTag "js/device-tree.js"/>

<#-- script START -->
<@page.script/>
<#-- script END -->

<#include "../_common/last-resources.ftl">
</body>
</html>