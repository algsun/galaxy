<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>选择站点 - 系统管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>

<!-- 导航栏 -->
<#include "../_common/header-for-guests.ftl">

<div id="gcontent" class="container">

<#include "../_common/message-tooltip-x.ftl">
<@messageTooltip 10></@messageTooltip>

    <div class="row">
        <div class="span12">
            <div class="page-header">
                <h2>请选择站点</h2>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="span2">
            <button id="gotoLogicGroupButton" class="btn" disabled="disabled">进入站点<i class="icon-chevron-right icon-white"></i></button>
        </div>
    </div>

    <div class="row m-t-20">
        <div class="span12">
            <div id="tree" class="ztree" style="padding:0;"></div>
        </div>
    </div>
</div>


<!-- 页面底部 -->
<#include "../_common/footer.ftl">

<div class="hide">
<#-- 顶级站点 -->
    <div id="topLogicGroups">
        <ul>
        <#list logicGroups as logicGroup>
            <li data-id="${logicGroup.id}" data-name="${logicGroup.logicGroupName}">${logicGroup.logicGroupName}</li>
        </#list>
        </ul>
    </div>
</div>

<#include "../_common/common-js.ftl">

<script type="text/javascript">
    (function ($) {
        $(function () {
            var loadTopLogicGroups = function () {
                var logicGroupNodes = [];
                $('#topLogicGroups > ul').children().each(function () {
                    var id = parseInt($(this).attr('data-id'));
                    var name = $(this).attr('data-name');
                    logicGroupNodes.push({id:id, name:name});
                });
                return logicGroupNodes;
            };

            var logicGroupNodes = loadTopLogicGroups();

            for (var i = 0; i < logicGroupNodes.length; i++) {
                logicGroupNodes[i].isParent = true;
                logicGroupNodes[i].iconSkin = 'site';
            }

            // 站点树
            $.fn.zTree.init($('#tree'), {
                view:{
                    showLine:false
                },
                async:{
                    enable:true,
                    url:'querySubLogicGroups.action',
                    autoParam:["id=logicGroupId", "name=n", "level=lv"],
                    otherParam:{"otherParam":"zTreeAsyncTest"}
                },
                callback:{
                    onClick:function (event, treeId, treeNode, clickFlag) {
                        //进入站点
                        $('#gotoLogicGroupButton').unbind('click')
                                .removeAttr('disabled')
                                .addClass('btn-primary')
                                .click(function () {
                                    location.href = 'doChooseLogicGroup.action?logicGroupId=' + treeNode.id;
                                });
                    }
                }
            }, logicGroupNodes);
        });
    })(jQuery);
</script>


<#include "../_common/last-resources.ftl">
</body>
</html>