<#--
  -区域管理页面布局
  -
  -@author Wang yunlong
  -@time  13-1-18  上午10:02
  -->
<!DOCTYPE html>
<html>
<head>
<#-- import page START -->
<#-- 引入内容页面 -->
<#import _pagePath as page>
<#-- import page END -->


<#include "../../_common/common-head.ftl">
    <title>${page.title}</title>

<#include "../../_common/common-css.ftl">

<#-- head START -->
<@page.head/>
<#-- head END -->

</head>
<body data-server-time="${.now?long?c}">
<#include "../../_common/header.ftl">


<#-- 区域设备树 -->
<#--<div.left-aside-container">-->
<#include "../../_common/zone-device-tree.ftl">
<#--</div>-->


<#-- 伸缩条 -->
<div class="shrink-bar" title="${locale.getStr("blueplanet.common.shrinkBar")}">
    <div class="shrink-bar-icon"></div>
</div>

<div class="content-container">
    <div class="container-fluid">
    <#--<div class="row-fluid">-->
            <#--<div class="span12">-->
                <#--<ul class="breadcrumb">-->
                    <#--<li class="muted">当前位置</li>-->
                    <#--<span id="area-device-path"></span>-->
                    <#--<a class="btn m-l-20">确定</a>-->
                <#--</ul>-->
            <#--</div>-->
        <#--</div>-->
    <@page.content/>
    </div>
</div>

<#include "../../_common/common-js.ftl">

<script type="text/javascript" src="../assets/underscore/1.4.2/underscore-min.js"></script>

<script type="text/javascript" src="js/device-tree.js"></script>

<#-- script START -->
<@page.script/>
<#-- script END -->

<#include "../../_common/last-resources.ftl">
</body>
</html>
