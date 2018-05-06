<#include "/common/pages/common-tag.ftl">
<!DOCTYPE html>
<html>
<head>
<#-- import page START -->
<#-- 引入内容页面 -->
<#import _pagePath as page>
<#-- import page END -->

<#include "_common/common-head.ftl">
    <title>${page.title}</title>

<#include "_common/common-css.ftl">
    <style type="text/css">
        .ztree li a.level0 {
            width: 252px;
            height: 20px;
            text-align: center;
            display: block;
            background-color: #6495ED;
            border: 1px silver solid;
        }

        .ztree li a.level0.cur {
            background-color: #66A3D2;
        }

        .ztree li a.level0 span {
            display: block;
            color: white;
            padding-top: 3px;
            font-size: 12px;
            font-weight: bold;
            word-spacing: 2px;
        }

        .ztree li a.level0 span.button {
            float: right;
            margin-left: 10px;
            visibility: visible;
            display: none;
        }

        .ztree li span.button.switch.level0 {
            display: none;
        }

        .ztree li span.button.icon01_ico_docu {
            margin-right: 2px;
            background: url(css/zTreeStyle/img/folder.png) no-repeat scroll 0 0 transparent;
            vertical-align: top;
            *vertical-align: middle
        }
    </style>
<#-- head START -->
<@page.head/>
<#-- head END -->
</head>
<body data-server-time="${.now?long?c}">

<#assign currentTopPrivilege = "cybertron:home">
<#include "_common/header.ftl">

<#-- 区域设备树 -->
<#--<div.left-aside-container">-->
<#include "_common/volume-tree.ftl">
<#--</div>-->

<#-- 伸缩条 -->
<div class="shrink-bar">
<#--<div class="shrink-bar-icon"></div>-->
</div>

<div class="content-container">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
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
            <#include "../pages/_common/message.ftl">
            <@messageTooltip></@messageTooltip>
                <div class="tabbable"> <!-- Only required for left/right tabs -->
                <#--<ul class="nav nav-tabs">-->
                <#--<@contentTab 0, page.tabIndex, "index", "主卷"/>-->
                <#--</ul>-->
                <#--<div class="tab-content">-->
                <#--<div class="tab-pane active" id="tab1">-->

                <#-- content START -->
                <@page.content/>
                <#-- content END -->

                <#--</div>-->
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<#include "_common/common-js.ftl">

<@scriptTag "js/volume-tree.js"/>

<script type="text/javascript">
    var Cybertron = App("cybertron");
    <#if volumeCode??>
        <#if volumeCode?length==1>
            Cybertron.volumeTree.selectVolumes(${volumeCode});
        <#else>
            Cybertron.volumeTree.selectVolume(${volumeCode});
        </#if>
    </#if>
</script>
<#-- script START -->
<@page.script/>
<#-- script END -->

<#include "_common/last-resources.ftl">
</body>
</html>