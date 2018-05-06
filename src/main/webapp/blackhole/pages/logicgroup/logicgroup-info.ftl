<#--
  <pre>
   添加站点
  </pre>
  @author Wang yunlong
  @time  12-12-3  下午1:11
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl" >
    <title>站点详情 - 系统管理</title>
<#include "../_common/common-css.ftl" >
    <style type="text/css">
        .info {
            font-size: 110%;
            font-weight: bold;
        }

        .label-info1 {
            text-align: right;
            background: url("images/body-bg.png");
        }
    </style>
</head>
<body>
<#assign currentTopPrivilege = "blackhole:logicgroup">
<#if atSupermanPage>
    <#include "../_common/header-for-superman.ftl">
<#else>
    <#include "../_common/header.ftl">
</#if>

<div id="gcontent" class="container">
<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:logicGroup:query"></@subNavs>
    <div class="row m-l-10">
    </div>
<#--消息-->
<#include "../_common/message-tooltip.ftl">

    <div class="row">
        <div class="span12">
            <fieldset>
                <legend>
                    <a class="go-back" href="queryLogicGroup.action" title="返回">
                            <i class="mw-icon-prev"></i>
                    站点详情
                    </a>
                </legend>
                <div class="span12" style="text-align: left;">
                </div>
            <#include "logicgroup-view.ftl">
            </fieldset>
        </div>
    </div>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#include "../_common/last-resources.ftl">
</body>
</html>
