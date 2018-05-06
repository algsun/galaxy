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
    <p class="index-title2">考古发掘现场综合信息管理系统</p>
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
</body>
</html>


<#macro content>
<div class="row div-subsystem offset1">
    <div class="span10 m-b-10 m-t-30">
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
    </div>
    <div class="span3">
        <a href="../saturn/">
            <div class="row-fluid m-t-10 subsystem-round-large">
                <div class="span12">
                    <img class="m-t-60 m-r-15" src="images/mode/mode5/saturn.png" alt=""/>
                </div>
            </div>
            <p style="font-size: 30px;" class="m-t-20 m-b-20 index-color">科研成果</p>
        </a>

        <div class="row-fluid m-t-10">
            <a href="../saturn/literatures">
                <div class="span6 subsystem-round-small" style="width: 100px;margin-left: 46px;">
                    <p class="m-t-30 index-shortcut-color font-20">文献</br>资料</p>
                </div>
            </a>
        </div>
    </div>
    <div class="span3">
    </div>
</div>
<div class="row m-t-30" style="text-align: center">
    <iframe width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true"
            src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>
</div>

    <#include "footer4.ftl">
</#macro>

