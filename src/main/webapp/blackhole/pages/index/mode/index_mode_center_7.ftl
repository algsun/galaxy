<!DOCTYPE HTML>
<html>
<head>
<#include "../../_common/common-head.ftl">
    <title>子系统 - 系统管理</title>
<#include "../../_common/common-css.ftl">
    <link href="../assets/hover/css/hover-min.css" rel="stylesheet" media="all">
    <link rel="stylesheet" type="text/css" href="../assets/gridster/0.2.1/css/jquery.gridster.css">
    <style type="text/css">

        body {
            background-image: url(images/mode/mode6/background.jpg);
            background-position: center top;
            background-repeat: no-repeat;
            background-size: 100% auto;
        }

        ul {
            list-style-type: none;
        }

        /*/
        /* gridster
        /*/

        .gridster ul {
            background-color: #EFEFEF;
        }

        .gridster li {
            font-size: 1em;
            font-weight: bold;
            text-align: center;
            line-height: 100%;
        }

        .gridster {
            margin: 0 auto;

            opacity: .8;

            -webkit-transition: opacity .6s;
            -moz-transition: opacity .6s;
            -o-transition: opacity .6s;
            -ms-transition: opacity .6s;
            transition: opacity .6s;
        }

        .gridster .gs-w {
            background: #DDD;
            cursor: pointer;
        }

        .gridster .player {
            background: #BBB;
        }

        .gridster .preview-holder {
            border: none !important;
            background: red !important;
        }

        .my_button {
            display: inline-block;
            padding: 1em;
            background-color: #79BD9A;
            text-decoration: none;
            color: white;
        }

    </style>
<#if mode == "mode1">
    <@linkTag "css/mode/mode1.css"/>
<#elseif mode=="mode2">

</#if>
</head>
<body>

<!-- 导航栏 -->
<#assign currentTopPrivilege = "blackhole:home">
<#include "../../_common/header.ftl">


<!-- 系统图片 START -->
<#--
默认背景
-->
<#--<#assign _contentImage = localeUI.getStr("blackhole.index.content-image")>-->
<#--<div style="margin: 0 auto; width: 960px;">-->
<#--<img src="${titlePath}" style="max-height:300px; width: 960px;">-->
<#--</div>-->

<#--
上博背景
<div style="height:200px;width: 100%; background: url(images/index/bj.jpg) 0 0 repeat;">
    <div style="height:200px; margin: 0 auto; width: 940px; background: url(images/index/content-shangbo.jpg) ;"></div>
</div>
-->
<!-- 系统图片 END -->
<#-- 加载模版-->
<div id="gcontent" class="container" style="background-color: inherit">
    <div>
    <#if template == "template_1">
           <@template_1/>
    </#if>
    </div>
</div>


<!-- 页面底部 -->
<#include "footer6.ftl">

<#include "../../_common/common-js.ftl">
<#include "../../_common/last-resources.ftl">
<script type="text/javascript" src="../assets/underscore/1.4.2/underscore-min.js"></script>
<script type="text/javascript" src="../assets/gridster/0.2.1/js/jquery.gridster.min.js"></script>
<script type="text/javascript">
    var gridster;
    $(function () {
        gridster = $(".gridster ul").gridster({
            widget_base_dimensions: [100, 100],
            widget_margins: [17, 15],
            helper: 'clone'
        }).data('gridster');

        // resize widgets on hover
        gridster.$el
                .on('click', '> li', function () {
                    var href = $(this).data("href");
                    window.location.href = href;
                })
//                .on('mouseenter', '> li', function() {
//                    gridster.resize_widget($(this), 2, 2);
//                })
//                .on('mouseleave', '> li', function() {
//                    gridster.resize_widget($(this), 1, 1);
//                });
    });
</script>
<script id="t2" type="text/template">
    <ul style="list-style-type:circle;">
        <% _.each(posts, function(item){ %>
        <li class="m-t-10">
        <#--elippsis 去掉 该class  避免 li 格式问题-->
            <a href="viewPost.action?id=<%= item.id %>&scope=-1" class=""
               style="width:430px; display:inline-block;" title="<%= item.title %>"><%= item.title %></a>
                <span class="f-r gray-b" style="font-size:0.8em"
                      title="<%= item.dateLong %>"><%= item.dateShort %></span></li>
        <% }); %>
    </ul>
</script>
<script id="t3" type="text/template">
    <ul style="list-style-type:circle;">
        <% _.each(tasks,function(item){ %>
        <li class="m-t-10">
            <a href="toViewTask.action?id=<%= item.id %>" class=""
               style="width:430px; display:inline-block;" title="<%= item.title %>"><%= item.title %></a>
                <span class="f-r gray-b" style="font-size:0.8em"
                      title="<%= item.dateLong %>"><%= item.dateShort %></span>
        </li>
        <% }); %>
    </ul>
</script>
</body>
</html>


<#--自定义模版页面
   @author xu.baoji
   @time 2013.12.30
-->
<#macro template_1>
<div class="row m-t-40">
    <div class="span12">
        <@PublicModule></@PublicModule>
    </div>
</div>
</#macro>

<#-- 公共模块 现 包括 天气、新闻、我的任务-->
<#macro PublicModule>
<#-- 天气插件 -->
<#-- 经典，250x260
<iframe src="http://www.thinkpage.cn/weather/weather.aspx?uid=&cid=101010100&l=zh-CHS&p=CMA&a=1&u=C&s=1&m=0&x=1&d=3&fc=&bgc=&bc=&ti=1&in=1&li=2&ct=iframe" frameborder="0" scrolling="no" width="250" height="260" allowTransparency="true"></iframe>
-->
<#-- 横版 500x110
<iframe src="http://www.thinkpage.cn/weather/weather.aspx?uid=&cid=101010100&l=zh-CHS&p=CMA&a=1&u=C&s=3&m=0&x=1&d=3&fc=&bgc=&bc=&ti=1&in=1&li=2&ct=iframe" frameborder="0" scrolling="no" width="500" height="110" allowTransparency="true"></iframe>
-->
<#--<iframe width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true"-->
<#--src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>-->

<div class="gridster ready">

    <ul style="height: 500px; width: 100%; position: relative; background-color:aliceblue">

        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/1.1.png);background-repeat: no-repeat" data-row="1" data-col="1" data-sizex="2"
            data-sizey="2" class="gs-w" data-href="../biela/evaluate">
            <div class="row-fluid"
                 style="margin-top: 200px; background-color: #000000;;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="line-height: 30px;color: white">北京文博</div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/tcywt.png);background-repeat: no-repeat" data-row="1" data-col="2" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=40">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="line-height: 30px;color: white">
                    团城演武厅
                </div>
            </div>

        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/gdjz.png);background-repeat: no-repeat" data-row="1" data-col="3" data-sizex="2"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=25">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="line-height: 30px;color: white">
                    北京古代建筑博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/bjdbt.jpg);background-repeat: no-repeat" data-row="1" data-col="4" data-sizex="2"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=41">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="color: white;line-height: 30px;">
                    北京大葆台西汉墓博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/zym.jpg);background-repeat: no-repeat" data-row="1" data-col="5" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=33">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="line-height: 30px;color: white">
                    正阳门
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/sd.png);background-repeat: no-repeat" data-row="1" data-col="6" data-sizex="1"
            data-sizey="2" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=26">
            <div class="row-fluid"
                 style="margin-top: 200px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="line-height: 30px;color: white">
                    首都博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/bjljch.jpg);background-repeat: no-repeat" data-row="2" data-col="1" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=42">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="color: white;line-height: 30px;">
                    辽金城垣博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/bjwbjl.jpg);background-repeat: no-repeat" data-row="2" data-col="2" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=30">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="line-height: 30px;color: white">
                    北京文博交流馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/dzsgz.jpg);background-repeat: no-repeat" data-row="2" data-col="3" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=35">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="color: white;line-height: 30px;">
                    大钟寺博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/bjgdqb.jpg);background-repeat: no-repeat" data-row="2" data-col="4" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=27">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="color: white;line-height: 30px;">
                    古代钱币博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/bjxdyz.jpg);background-repeat: no-repeat" data-row="2" data-col="5" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=43">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="color: white;line-height: 30px;">
                    西周燕都博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/djs.jpeg);background-repeat: no-repeat" data-row="2" data-col="6" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=39">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="line-height: 30px;color: white">
                    大觉寺
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/bjskys.jpg);background-repeat: no-repeat" data-row="3" data-col="1" data-sizex="1"
            data-sizey="2" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=38">
            <div class="row-fluid"
                 style="margin-top: 200px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="color: white;line-height: 30px;">
                    石刻艺术博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/bts.jpg);background-repeat: no-repeat" data-row="3" data-col="2" data-sizex="2"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=28">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="color: white;line-height: 30px">
                    白塔寺博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/bjay.jpg);background-repeat: no-repeat" data-row="3" data-col="3" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=34">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="color: white;line-height: 30px;font-size: 14px;">
                    北京奥运博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/hmhgzj.jpg);background-repeat: no-repeat" data-row="3" data-col="4" data-sizex="2"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=31">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="line-height:30px;color: white;">
                    孔庙和国子监博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/bjys.jpg);background-repeat: no-repeat" data-row="3" data-col="5" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=37">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="line-height:30px;color: white;">
                    北京艺术博物馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/ls.jpg);" data-row="3" data-col="6" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=32">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="line-height:30px;color: white;">
                    老舍纪念馆
                </div>
            </div>
        </li>
        <li class ="hvr-pulse" style="background-image: url(images/mode/mode7/xbhjng.jpg);background-repeat: no-repeat" data-row="3" data-col="6" data-sizex="1"
            data-sizey="1" class="gs-w"
            data-href="doChooseLogicGroup.action?logicGroupId=29">
            <div class="row-fluid"
                 style="margin-top: 70px; background-color: #000000;background-color: rgba(0, 0, 0, 0.6);">
                <div class="span12" style="line-height:30px;color: white;">
                    徐悲鸿纪念馆
                </div>
            </div>
        </li>
    </ul>
</div>
</#macro>
<#-- 业务系统 按钮上下排列-->
<#macro subsystemsForUpAndDowd>
</#macro>
