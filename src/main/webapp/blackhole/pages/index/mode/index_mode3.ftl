<!DOCTYPE HTML>
<html>
<head>
<#include "../../_common/common-head.ftl">
    <title>子系统 - 系统管理</title>
<#include "../../_common/common-css.ftl">
<@linkTag "css/mode/index3.css"/>
</head>
<body>

<!-- 导航栏 -->
<#assign currentTopPrivilege = "blackhole:home">
<#include "../../_common/header.ftl">


<!-- 系统图片 START -->
<#--
默认背景
-->
<div style="" class="index-image">
    <img src="images/mode/mode3/smartmuseum.png" alt=""/>
    <p class="index-title2">智慧博物馆</p>
</div>

<#-- 加载模版-->
<div id="content" class="container" style="min-height: 300px;">
    <div>
        <@content/>
    </div>
</div>


<!-- 页面底部 -->
<#include "footer3.ftl">

<#include "../../_common/common-js.ftl">
<#include "../../_common/last-resources.ftl">
</body>
</html>

<#macro content>
<div class="row div-subsystem offset1">
    <div class="span3">
        <div class="row-fluid m-t-10 subsystem--hexagon-large">
            <a href="../blueplanet/">
                <div class="span12">
                    <img src="images/mode/mode3/blueplanet.png" alt=""/>

                    <p class="blueplanet-p font-30">环境监控</p>
                </div>
            </a>
        </div>

        <div class="row-fluid m-t-10">
            <a href="../blueplanet/site/realtime-data">
                <div class="span6 subsystem-hexagon-small" style="width: 97px;">
                    <p class="m-t-30 blueplanet-p font-20">实时</br>数据</p>
                </div>
            </a>

            <a href="../blueplanet/site">
                <div class="span6 subsystem-hexagon-small" style="width: 97px;margin-left: 5px;">
                    <p class="m-t-30 blueplanet-p font-20">站点</br>概览</p>
                </div>
            </a>
        </div>
    </div>
    <div class="span3">
        <a href="../phoenix/index/summaryReport">
            <div class="row-fluid m-t-10 subsystem--hexagon-large">
                <div class="span12">
                    <img src="images/mode/mode3/phoenix.png" alt=""/>

                    <p class="phoenix-p font-30">数据分析</p>
                </div>
            </div>
        </a>

        <a href="../phoenix/index/health">
            <div class="row-fluid m-t-10 ">
                <div class="span6 subsystem-hexagon-small" style="width: 97px;margin-left: 46px;">
                    <p class="m-t-30 phoenix-p font-20">健康</br>指数</p>
                </div>
            </div>
        </a>
    </div>
    <div class="span3">
        <a href="../proxima/viewOpticsImageDefault.action">
            <div class="row-fluid m-t-10 subsystem--hexagon-large">
                <div class="span12">
                    <img src="images/mode/mode3/proxima.png" alt=""/>

                    <p class="proxima-p font-30">本体监测</p>
                </div>
            </div>
        </a>

        <div class="row-fluid m-t-10 ">
            <a href="../proxima/trendChartDefault.action">
                <div class="span6 subsystem-hexagon-small" style="width: 97px;">
                    <p class="m-t-30 proxima-p font-20">裂隙</br>监测</p>
                </div>
            </a>

            <a href="../proxima/queryDVPlaceDefault.action">
                <div class="span6 subsystem-hexagon-small" style="width: 97px;margin-left: 5px;">
                    <p class="proxima-p font-18" style="margin-top: 35px;">摄像机</p>
                </div>
            </a>
        </div>
    </div>
</div>
<#--<div class="row m-t-40" style="text-align: center">-->
    <#--<iframe width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true"-->
            <#--src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>-->
<#--</div>-->
</#macro>

