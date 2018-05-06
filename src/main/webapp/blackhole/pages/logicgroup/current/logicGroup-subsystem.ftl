<!DOCTYPE HTML>
<html>
<head>
<#include "../../_common/common-head.ftl">
    <title>子系统开关 - 系统管理</title>
<#include "../../_common/common-css.ftl">

</head>
<body>

<!-- 导航栏 -->
<#assign currentTopPrivilege="blackhole:currentLogicGroup">
<#include "../../_common/header.ftl">

<div id="gcontent" class="container">
<#--二级菜单-->
<#include "../../_common/sub-navs.ftl">
<@subNavs "blackhole:currentLogicGroup:logicGroupSubsystem"></@subNavs>
    <div class="row m-l-10">
    </div>
<#--消息-->
<#include "../../_common/message-tooltip.ftl">
    <div class="row">
        <div class="span12">
            <table class="table table table-hover">
                <tr>
                    <th>编号</th>
                    <th>名称</th>
                    <th>代号</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            <#list subsystemList as subsystem>
                <#assign isBaseSite = Session["currentLogicGroup"].site??>
                <#if subsystem.subsystemId != 9>
                    <tr>
                        <td>${subsystem_index+1}</td>
                        <td>${subsystem.subsystemName}</td>
                        <td>${subsystem.subsystemCode}</td>
                        <td>
                            <#if !subsystem.enable>
                                全局配置已将此系统禁用
                            <#else>
                                <#if subsystem.state==0>
                                    <span class="label label-success">启用</span>
                                <#else>
                                    <span class="label label-important">禁用</span>
                                </#if>
                            </#if>

                        </td>
                        <td>
                            <#if subsystem.subsystemId!=1>
                                <#if subsystem.enable>
                                    <#if subsystem.state==0>
                                        <a class="	btn btn-small"
                                           href="disable.action?systemId=${subsystem.subsystemId}">
                                            <span style="color: red">禁用</span>
                                        </a>
                                    <#else>
                                        <a class="btn btn-small"
                                           href="able.action?systemId=${subsystem.subsystemId}">
                                            <span style="color: green">启用</span>
                                        </a>
                                    </#if>
                                </#if>
                            <#else>
                                不可操作
                            </#if>
                        </td>
                    </tr>
                <#else>
                    <#if !(isBaseSite)>
                        <tr>
                            <td>${subsystem_index+1}</td>
                            <td>${subsystem.subsystemName}</td>
                            <td>${subsystem.subsystemCode}</td>
                            <td>
                                <#if !subsystem.enable>
                                    全局配置已将此系统禁用
                                <#else>
                                    <#if subsystem.state==0>
                                        <a class="btn btn-small btn-success" style="border-radius: 20px;">启用</a>
                                    <#else>
                                        <a class="	btn btn-small btn-danger" style="border-radius: 20px;">禁用</a>
                                    </#if>
                                </#if>

                            </td>
                            <td>
                                <#if subsystem.subsystemId!=1>
                                    <#if subsystem.enable>
                                        <#if subsystem.state==0>
                                            <a class="	btn btn-small"
                                               href="disable.action?systemId=${subsystem.subsystemId}">
                                                <span style="color: red">禁用</span>
                                            </a>
                                        <#else>
                                            <a class="btn btn-small"
                                               href="able.action?systemId=${subsystem.subsystemId}">
                                                <span style="color: green">启用</span>
                                            </a>
                                        </#if>
                                    </#if>
                                <#else>
                                    不可操作
                                </#if>
                            </td>
                        </tr>
                    </#if>
                </#if>

            </#list>
            </table>
        </div>
    </div>
    <p style="margin-top: 30px;color: #CCCCCC">温馨提示：操作完之后重新登录可生效,系统管理不能关闭。</p>
</div>
<#include "../../_common/footer.ftl">
<#include "../../_common/common-js.ftl">
<#include "../../_common/last-resources.ftl">
</body>
</html>
