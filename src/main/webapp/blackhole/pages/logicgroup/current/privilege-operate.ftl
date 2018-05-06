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
<@subNavs "blackhole:currentLogicGroup:privilegeOperate"></@subNavs>
<#include "../../_common/message-tooltip-x.ftl">
<@messageTooltip></@messageTooltip>
    <div class="row">
        <div class="span12">
            <form id="role-form" class="form-horizontal" action="disablePrivilege.action" method="post">
            <#include "../../index/anonymity-role-privilege-view.ftl">
                <div class="span12">
                    <div class="control-group">
                        <div class="controls">
                            <button class="btn btn-inverse" type="submit"><i
                                    class="icon-ban-circle icon-white"></i>
                                禁用
                            </button>
                            <span style="color: #ccc" class="m-l-20">温馨提示：已勾选的权限表示禁用</span>
                        </div>
                    </div>
                </div>
                <form>
        </div>
    </div>
</div>
<#include "../../_common/footer.ftl">
<#include "../../_common/common-js.ftl">
<#include "../../_common/last-resources.ftl">
</body>
<script type="text/javascript">
    $(function () {
        // 权限级联选中状态
        var $level2 = $(".level-2");
        var $level1 = $(".level-1");
        $level2.find("input").live("click", onLevel2InputClick);
        $level1.find("input").live("click", onLevel1InputClick);
    });

    //选中2级权限checked消息响应
    function onLevel2InputClick() {
        var $this = $(this);
        var $thisLevel1Privileges = $this.parent().parent().siblings(".level-1").find("input");
        var $sibs = $this.parent().parent().find("input");
        var flag = true;
        $.each($sibs, function (index, sib) {
            if (!($(sib).attr("checked") == "checked")) {
                flag = false;
                return;
            }
        });
        $thisLevel1Privileges.attr("checked", flag);
    }

    //选中1级权限checked消息响应
    function onLevel1InputClick() {
        var $this = $(this);
        var thisValue = $this.attr("checked");
        var $thisLevel2Privileges = $this.parent().parent().siblings(".level-2").find("input");
        $thisLevel2Privileges.attr("checked", thisValue == "checked");
    }

</script>
</html>
