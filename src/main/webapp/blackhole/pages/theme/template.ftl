<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>主题 - 系统管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>

<!-- 导航栏 -->
<#--当前一级权限ID-->
<#assign currentTopPrivilege = "blackhole:theme">
<#include "../_common/header.ftl">

<div id="gcontent" class="container">
<#include "../_common/sub-navs.ftl">
<#include "../_common/message-tooltip.ftl">
    <div class="row m-t-10">
        <div class="span12">
            <ul class="nav nav-pills">
                <li>
                    <a href="theme.action">主题</a>
                </li>
                <li class="active">
                    <a href="template.action">首页模版</a>
                </li>
            </ul>
        </div>
    </div>

    <div class="row m-t-40">
        <div class="span4">
            <div class="template_1">
                <h5 class="text-center">模版1</h5>
                <img id="" src="images/template/template_1.png">

                <div class="text-center m-t-20">
                <#if template == "template_1">
                    <button class="btn btn-success" type="button" disabled>当前应用模版</button>
                <#else >
                    <button class="btn" type="button" template="template_1"> 选择此模版</button>
                </#if>
                </div>
            </div>
        </div>
        <div class="span4">
            <div class="template_2">
                <h5 class="text-center">模版2</h5>
                <img id="" src="images/template/template_2.png">

                <div class="text-center m-t-20">
                <#if template == "template_2">
                    <button class="btn btn-success" type="button" disabled>当前应用模版</button>
                <#else >
                    <button class="btn" type="button" template="template_2">选择此模版</button>
                </#if>
                </div>
            </div>
        </div>
        <div class="span4">
            <div class="template_3">
                <h5 class="text-center">模版3</h5>
                <img id="" src="images/template/template_3.png" style="height: 240px">

                <div class="text-center m-t-20">
                <#if template == "template_3">
                    <button class="btn btn-success" type="button" disabled>当前应用模版</button>
                <#else >
                    <button class="btn" type="button" template="template_3">选择此模版</button>
                </#if>
                </div>
            </div>
        </div>
    </div>


</div>


<!-- 页面底部 -->
<#include "../_common/footer.ftl">
<#--公共JS-->
<#include "../_common/common-js.ftl">


<!--表单验证-->
<script type="text/javascript" src="../assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="../common/js/util.js"></script>

<script type="text/javascript">
    $(function () {
        $("button").click(function () {
            var template = $(this).attr("template");
            window.location.href = "updateTemplate.action?template=" + template;
        })
    });
</script>

<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
