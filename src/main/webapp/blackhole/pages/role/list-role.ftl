<#--
角色查询

@author Wang yunlong
@date  12-11-28 下午1:53
-->

<!DOCTYPE html>
<html>
<head>
<#include "../_common/common-head.ftl" >
    <title>查询角色 - 系统管理</title>
<#include "../_common/common-css.ftl" >

</head>
<body>

<#assign currentTopPrivilege = "blackhole:role">
<#include "../_common/header.ftl">

<div id="gcontent" class="container">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:role:query"></@subNavs>

<#-- 消息提示 -->
<#include "../_common/message-tooltip-x.ftl">
<@messageTooltip></@messageTooltip>

<#--角色列表-->
<#include "list-role-view.ftl">
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript">
    $(function () {
        $(document).on("click", ".delete-role", function () {
            var $this = $(this);
            art.dialog({
                id: "delete",
                fixed: true,
                title: "删除确认",
                content: "确定删除当前角色？",
                okValue: "确定",
                ok: function () {
                    window.location.href = "deleteRole.action?roleId=" + $this.attr("roleId");
                },
                cancelValue: "取消",
                cancel: function () {
                }
            });
        });
    });
</script>

<#include "../_common/last-resources.ftl">
</body>
</html>

