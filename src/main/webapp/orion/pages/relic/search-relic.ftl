<#--
藏品信息

@author xie.deng
@time  14-4-21
@check 2014-04-22 liuzhu xuyuexi #8456
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>藏品信息 - 全文检索</title>
<#include "../_common/common-css.ftl">
</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "orion:culturalRelic">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
<#-- 消息提示 -->
<#-- TODO 消息提示风格统一，并将多个消息提示工具重构为一个文件 -->
<#include "/common/pages/message-tooltip.ftl">
<@messageTooltip></@messageTooltip>
<#if fullTextSearchRelicVos??>
    <div class="muted p-v-20" style="font-size: 1.6em;">搜索&nbsp;<strong
            style="color: red; font-style:italic;">${key!}</strong> ，找到 ${dataCount} 条结果 （用时 ${consumTime} 秒）
    </div>
    <#list fullTextSearchRelicVos as relicVo>
        <div class="row">
            <div class="span2">
                <ul class="thumbnails">
                    <li class="span2">
                        <a href="relicCard.action?type=fullTextSearch&totalCode=${relicVo.totalCode}" target="_blank"
                           class="thumbnail">
                            <img src="${relicVo.photo!"#"}" style="max-width: 120px;max-height: 130px;">
                        </a>
                    </li>
                </ul>
            </div>
            <div class="span8">
                <ul class="unstyled">
                    <li><a href="relicCard.action?type=fullTextSearch&totalCode=${relicVo.totalCode}" target="_blank">
                        <h3>
                        ${relicVo.name}</h3></a></li>
                    <li>
                    <span style="color: #006400">
                        <b>
                            总登记号:${relicVo.totalCode}&nbsp;
                            库房位次:${relicVo.relicLocation}&nbsp;
                            级别:${relicVo.level}&nbsp;
                            状态:${relicVo.status}&nbsp;
                        </b>
                    </span>
                    </li>
                    <#list relicVo.highlightTexts?keys as key>
                        <li> ${key} : ${relicVo.highlightTexts[key]} </li>
                    </#list>
                    <a class="btn btn-mini"
                       href="relicCardRelicId.action?pre=0&relicId=${relicVo.id}"
                       title="藏品卡">藏品卡</a>
                    <a class="btn btn-mini"
                       href="relicArchiveRelicId.action?pre=0&relicId=${relicVo.id}"
                       title="档案">档案</a>
                    <#if security.isPermitted("orion:culturalRelic:update")>
                        <a class="btn btn-success btn-mini"
                           href="toUpdateRelic.action?relicId=${relicVo.id}"
                           title="更新"> 编辑 </a>
                    </#if>
                </ul>
            </div>
        </div>
    </#list>
<#else>
<div class="muted p-v-20" style="font-size: 1.6em;">搜索&nbsp;<strong
        style="color: red; font-style:italic;">${key!}</strong> ，找到 ${dataCount} 条结果 （用时 ${consumTime} 秒）
</#if>
    <div class="row">
        <div class="span12">
        <#include "/common/pages/pagging.ftl">
        <#assign url = "fulltextRetrieval?key=${key}"/>
        <@pagination url, index, pageCount,"index"/>
        </div>
    </div>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
</body>
</html>