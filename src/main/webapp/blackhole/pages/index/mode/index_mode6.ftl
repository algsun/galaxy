<!DOCTYPE HTML>
<html>
<head>
<#include "../../_common/common-head.ftl">
    <title>子系统 - 系统管理</title>
<#include "../../_common/common-css.ftl">

<@linkTag "css/mode/index6.css"/>
</head>
<body>

<!-- 导航栏 -->
<#assign currentTopPrivilege = "blackhole:home">
<#include "../../_common/header.ftl">

<!-- 系统图片 START -->
<#--
默认背景
-->
<div style="" class="index-image"></div>

<#-- 加载模版-->
<div id="content" class="container" style="min-height: 500px;">
    <div>
    <@content/>
    </div>
</div>

<!-- 页面底部 -->
<#include "footer6.ftl">

<#include "../../_common/common-js.ftl">
<#include "../../_common/last-resources.ftl">
</body>
</html>

<#macro content>
<div class="row">
    <div class="span4" style="margin-left: 130px;">
        <img src="images/mode/mode6/site-name-2row.png" style="max-height: 65px;" alt=""/>
    </div>
    <div class="span6">
        <img src="images/mode/mode6/index-site-img.png" style="margin-top: -30px;max-height: 185px;" alt=""/>
    </div>
</div>
<div class="row">

    <div class="span3 offset3">
        <a href="../blueplanet/site/realtime-data">
            <img src="images/mode/mode6/realtime.png" style="max-width: 160px;max-height: 160px;" alt=""/>
        </a>
    </div>
    <div class="span3 offset1">
        <a href="../blueplanet/offline/offline.action">
            <img src="images/mode/mode6/offline.png" style="max-width: 160px;max-height: 160px;" alt=""/>
        </a>
    </div>
</div>

<div class="row">

    <div class="span3 offset1">
        <a href="../phoenix/index/summaryReport">
            <img src="images/mode/mode6/phoenix.png" style="max-width: 160px;max-height: 160px;" alt=""/>
        </a>
    </div>
    <div class="span3 offset1">
        <a href="../blueplanet/site">
            <img src="images/mode/mode6/view.png" style="max-width: 160px;max-height: 160px;" alt=""/>
        </a>
    </div>
    <div class="span3 offset1">
         <a href="../blueplanet/controller/index">
            <img src="images/mode/mode6/regulation.png" style="max-width: 160px;max-height: 160px;" alt=""/>
        </a>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $('#footer img[data-logo]').mouseenter(function () {
            $(this).attr('src', '../common/images/logo-top-150x46.png')
        }).mouseleave(function () {
                    $(this).attr('src', '../common/images/logo-top-150x46.png')
                });

    });
</script>
</#macro>

