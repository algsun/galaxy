<!DOCTYPE HTML>
<html>
<head>
<#include "../../_common/common-head.ftl">
    <title>子系统 - 系统管理</title>
<#include "../../_common/common-css.ftl">

<@linkTag "css/mode/index4.css"/>

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
    <img src="images/mode/mode4/smart.png" alt=""/>

    <p class="index-title2">智慧博物馆</p>
</div>

<#-- 加载模版-->
<div id="content" class="container" style="min-height: 300px;">
    <div>
    <@content/>
    </div>
</div>


<!-- 页面底部 -->
<#--<#include "../../_common/footer.ftl">-->

<#include "../../_common/common-js.ftl">
<#include "../../_common/last-resources.ftl">
<script type="text/javascript">
    $(function () {
        $('#footer img[data-logo]').mouseenter(function () {
            $(this).attr('src', '../common/images/logo-top-150x46.png')
        }).mouseleave(function () {
                    $(this).attr('src', '../common/images/logo-top-150x46.png')
                });

    });
</script>
</body>
</html>

<#macro content>
<div class="row div-subsystem offset1">
    <div class="span10 m-b-10 m-t-20">
        <div class="row">
            <div class="span4">
                <img src="images/mode/mode4/line-left.png" alt=""/>
            </div>
            <div class="span1 index-shortcut-color" style="font-size: 15px;">功能模块</div>
            <div class="span4">
                <img src="images/mode/mode4/line-right.png" alt=""/>
            </div>
        </div>
    </div>
    <div class="span3">
        <a href="../blueplanet/">
            <div class="row-fluid m-t-10 subsystem-round-large">
                <div class="span12">
                    <img class="m-t-60 m-r-15" src="images/mode/mode4/blueplanet.png" alt=""/>
                </div>
            </div>
            <p style="font-size: 30px;" class="m-t-20 m-b-20 index-color">环境监控</p>
        </a>

        <div class="row-fluid m-t-10">
            <a href="../blueplanet/site/realtime-data">
                <div class="span6 subsystem-round-small" style="width: 100px;">
                    <p class="m-t-30 font-20 index-shortcut-color">实时</br>数据</p>
                </div>
            </a>

            <a href="../blueplanet/site">
                <div class="span6 subsystem-round-small" style="width: 100px;margin-left: 5px;">
                    <p class="m-t-30 font-20 index-shortcut-color">站点</br>概览</p>
                </div>
            </a>
        </div>
    </div>
    <div class="span3">
        <a href="../phoneix/index/summaryReport">
            <div class="row-fluid m-t-10 subsystem-round-large">
                <div class="span12">
                    <img class="m-t-60 m-r-15" src="images/mode/mode4/phoenix.png" alt=""/>
                </div>
            </div>
            <p style="font-size: 30px;" class="m-t-20 m-b-20 index-color">数据分析</p>
        </a>

        <div class="row-fluid m-t-10">
            <a href="../phoenix/index/health">
                <div class="span6 subsystem-round-small" style="width: 100px;margin-left: 46px;">
                    <p class="m-t-30 index-shortcut-color font-20">健康</br>指数</p>
                </div>
            </a>
        </div>
    </div>
    <div class="span3">
        <a href="../proxima/">
            <div class="row-fluid m-t-10 subsystem-round-large">
                <div class="span12">
                    <img class="m-t-60 m-r-15" src="images/mode/mode4/proxima.png" alt=""/>
                </div>
            </div>
            <p style="font-size: 30px;" class="m-t-20 m-b-20 index-color">本体监测</p>
        </a>

        <div class="row-fluid m-t-10">
            <a href="../proxima/trendChartDefault.action">
                <div class="span6 subsystem-round-small" style="width: 100px;">
                    <p class="m-t-30 font-20 index-shortcut-color">裂隙</br>监测</p>
                </div>
            </a>

            <a href="../proxima/queryDVPlaceDefault.action">
                <div class="span6 subsystem-round-small" style="width: 100px;margin-left: 5px;">
                    <p class="font-20 index-shortcut-color" style="margin-top: 35px;">摄像机</p>
                </div>
            </a>
        </div>
    </div>
</div>
<#--<div class="row m-t-20" style="text-align: center">-->
    <#--<iframe width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true"-->
            <#--src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>-->
<#--</div>-->

    <#include "footer4.ftl">
</#macro>

