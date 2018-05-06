<!DOCTYPE HTML>
<html>
<head>
<#include "../../_common/common-head.ftl">
    <title>子系统 - 系统管理</title>
<#include "../../_common/common-css.ftl">

    <style type="text/css">
        .subsystem-icon {
            width: 300px;
            height: 75px;
            vertical-align: top;
        }

        #subsystems.thumbnails .thumbnail {
            filter: progid:DXImageTransform.Microsoft.Shadow(color=#707070, direction=120, strength=2);
            -ms-filter: "progid:DXImageTransform.Microsoft.Shadow(Strength=2, Direction=120, Color='#707070')";
            -moz-box-shadow: 1px 1px 10px #707070;
            -webkit-box-shadow: 1px 1px 10px #707070;
            box-shadow: 1px 1px 10px #707070;
            background-color: white;
        }

        .bulge {
            font-size: 40px;
            font-weight: normal;
        }

        /* 将业务系统块主变灰 */
        #subsystems .thumbnail.disable {
            filter: gray;
            -webkit-filter: grayscale(100%);
        }

        /* 业务系统变灰后的遮罩 */
        #subsystems.thumbnails .overlay {
            /* 白色背景，再将其透明, 要不然 IE 被遮罩的东西依然可点击 */
            position: absolute;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            background-color: white;
            cursor: pointer;
            filter: alpha(opacity=0);
            opacity: 0;
            z-index: 100;
        }

        #subsystems .caption {
            padding: 0 5px;
        }

        /* 环境监控 */
        #subsystems.thumbnails .thumbnail.blueplanet .caption {
            color: #7fb80e;
        }

        /* 本体监测 */
        #subsystems.thumbnails .thumbnail.proxima .caption {
            color: #377ef0;
        }

        /* 资产管理 */
        #subsystems.thumbnails .thumbnail.orion .caption {
            color: #cf6c00;
        }

        /* 人员管理 */
        #subsystems.thumbnails .thumbnail.uma .caption {
            color: #00ba5e;
        }
    </style>

<@linkTag "css/mode/mode1.css"/>
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
<div style="margin: 0 auto; width: 960px;">
    <img src="${titlePath}" style="max-height:300px; width: 960px;">
</div>

<#--
上博背景
<div style="height:200px;width: 100%; background: url(images/index/bj.jpg) 0 0 repeat;">
    <div style="height:200px; margin: 0 auto; width: 940px; background: url(images/index/content-shangbo.jpg) ;"></div>
</div>
-->
<!-- 系统图片 END -->
<#-- 加载模版-->
<div id="gcontent" class="container" style="min-height: 300px;background-color:#F0F8FF">

    <div>
        <iframe width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true"
                src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>
    </div>
    <div>
    <#if template == "template_1">
           <@template_1/>
     <#elseif template == "template_2">
        <@template_2/>
    <#elseif template == "template_3">
        <@template_3/>
    </#if>
    </div>
</div>


<!-- 页面底部 -->
<#include "../../_common/footer.ftl">

<#include "../../_common/common-js.ftl">
<#include "../../_common/last-resources.ftl">
<script type="text/javascript" src="../assets/underscore/1.4.2/underscore-min.js"></script>
<script type="text/javascript">
    (function ($) {
        //加载我的任务
        (function () {
            $.post("indexListTasks.action", function (tasks) {
                if (tasks.length == 0) {
                    var $this = $("#myTask");
                    $this.next().remove();
                    $this.remove();
                }
                $.each(tasks, function (i, task) {
                    if (!task.privilegeEnable) {
                        var $this = $("#myTask");
                        $this.next().remove();
                        $this.remove();
                        return;
                    }
                    task.dateShort = task.releaseDate.substr(0, task.releaseDate.indexOf("T"));
                    task.dateLong = task.releaseDate.replace("T", " ");
                });
                var taskTemplate = $("#t3").html();
                $("#taskTemplate").html(_.template(taskTemplate, {tasks: tasks}));
            });
        })();
        // 加载新闻
        (function () {
            $.post("publicPost.action?scope=-1&max=10", function (posts) {
                $.each(posts, function (i, post) {
                    // post.createDate => "2013-05-09T12:34:56"
                    post.dateShort = post.createDate.substr(0, post.createDate.indexOf("T"));
                    post.dateLong = post.createDate.replace("T", " ");
                });

                var template = $("#t2").html();
                $("#template").html(_.template(template, {posts: posts}));
            });
        })();
    })(jQuery);
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
<div class="row m-t-10">
    <div class="span7">
        <@PublicModule></@PublicModule>
    </div>

    <div class="offset1 span4">
        <@subsystemsForUpAndDowd></@subsystemsForUpAndDowd>
    </div>
</div>
</#macro>

<#macro template_3>
<div class="row m-t-10">
    <div class=" span4">
        <@subsystemsForUpAndDowd></@subsystemsForUpAndDowd>
    </div>
    <div class="offset1 span7">
        <@PublicModule></@PublicModule>
    </div>

</div>
</#macro>

<#macro template_2>
<div class="row m-t-10">

    <div class="span12">
        <@subsystemsForUpAndDowd></@subsystemsForUpAndDowd>
    </div>
    <div class="span12">
        <@PublicModule></@PublicModule>
    </div>

</div>
</#macro>


<#-- 公共模块 现 包括 、新闻、我的任务-->
<#macro PublicModule>
<div>
    <div class="page-header m-t-20 m-b-10" id="myTask" style="overflow:hidden;">
        <h4 class="m-v-0 f-n yahei-font f-l">我的任务</h4>
        <a class="f-r gray-b" style="font-size:1.0em" href="listTask.action">更多...</a>
    </div>

    <div id="taskTemplate">

    </div>

    <div class="page-header m-t-20 m-b-10" style="overflow: hidden;">
        <h4 class="m-v-0 f-n yahei-font f-l">新闻</h4>
        <a class="f-r gray-b" style="font-size:1.0em" href="listPosts.action?scope=-1">更多...</a>
    </div>

<#-- 内部新闻 -->
    <div id="template">
    </div>
</div>
</#macro>

<#-- 业务系统 按钮上下排列-->
<#macro subsystemsForUpAndDowd>
<#-- 是否是基层站点 -->
    <#assign isBaseSite = Session["currentLogicGroup"].site??>
<ul id="subsystems" class="thumbnails">
    <#list Session["subsystemList"] as subsystem>
        <#-- 除过系统管理 -->
        <#if subsystem.subsystemCode != "blackhole">
        <#if security.isUserPermitted("blackhole:subsystem:" +subsystem.subsystemCode)>
            <@_subsystemBlock subsystem/>
        </#if>
    </#if>
    </#list>
</ul>
</#macro>

<#macro _subsystemBlock subsystem>
    <#if !(subsystem.subsystemCode == 'biela' && isBaseSite)>
        <#if subsystem.subsystemCode != 'blackhole'>
        <li class="span4" style="position: relative;">
            <a href="../${subsystem.subsystemCode}/" style="border: 0px;padding: 0px"
               class="subsystemNail thumbnail ${subsystem.subsystemCode} t-d-none shadow1 <#if !isBaseSite && subsystem.subsystemId != 9>disable</#if>">
                <div class="caption" style="padding: 0px">
                    <img src="${basePath()}/blackhole/images/mode/mode1/${subsystem.subsystemCode}.png"
                         class="subsystem-icon">
                </div>
            </a>
            <#if !isBaseSite && subsystem.subsystemId != 9>
                <div class="overlay"
                     onclick="javascript:SWITCH_MAP.logicGroupMap('blackhole', '${subsystem.subsystemCode}'); return false;"></div>
            </#if>
        </li>
        </#if>
    </#if>
</#macro>