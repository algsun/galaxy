<!DOCTYPE HTML>
<html>
<head>
<#include "../../_common/common-head.ftl">
    <title>子系统开关 - 系统管理</title>
<#include "../../_common/common-css.ftl">

</head>
<body>

<#assign currentTopPrivilege="blackhole:logicGroup">
<#include "../../_common/header-for-superman.ftl">

<div id="gcontent" class="container">
<#include "../../_common/message-tooltip.ftl">
    <div class="row m-t-20">
        <div class="span12">
            <table class="table table table-hover">
                <tr>
                    <th>编号</th>
                    <th>业务系统名称</th>
                    <th>代号</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            <#list subsystemList as subsystem>
                <tr>
                    <td>${subsystem_index+1}</td>
                    <td>${subsystem.subsystemName}</td>
                    <td>${subsystem.subsystemCode}</td>
                    <td>
                        <#if subsystem.enable>
                            <span class="label label-success">启用</span>
                        <#else>
                            <span class="label label-important">禁用</span>
                        </#if>
                    </td>
                    <td>
                        <#if subsystem.subsystemId!=1>
                            <#if subsystem.enable>
                                <a class="	btn btn-small"
                                   href="changeState.action?systemId=${subsystem.subsystemId}&state=0">
                                    <span style="color: red">禁用</span>
                                </a>
                            <#else>
                                <a class="btn btn-small"
                                   href="changeState.action?systemId=${subsystem.subsystemId}&state=1">
                                    <span style="color: green">启用</span>
                                </a>
                            </#if>
                        <#else>
                             不可操作
                        </#if>
                    </td>
                </tr>
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
