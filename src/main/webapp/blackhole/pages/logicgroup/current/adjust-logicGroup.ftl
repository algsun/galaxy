<!DOCTYPE HTML>
<html>
<head>
<#include "../../_common/common-head.ftl">
    <title>调整站点结构 - 系统管理</title>
<#include "../../_common/common-css.ftl">

</head>
<body>

<!-- 导航栏 -->
<#assign currentTopPrivilege="blackhole:currentLogicGroup">
<#include "../../_common/header.ftl">

<div id="gcontent" class="container">

<#include "../../_common/sub-navs.ftl">
<@subNavs "blackhole:currentLogicGroup:changeStructure"></@subNavs>

<#include "../../_common/message-tooltip.ftl">

    <div class="row">
        <div class="span12">
            <form class="form-horizontal" id="adjustForm" action="changeLogicGroupSiteAndParent.action" method="post">
                <input id="current-id" type="hidden" name="currentId" value="${logicGroup.id!}">
                <input id="parent-id" type="hidden" name="parentId" value="${(parentLogicGroup.id)!"-1"}">
                <input id="site-id" type="hidden" name="siteId" value="${(logicGroup.site.siteId)!}">
                <fieldset>
                    <legend>结构调整</legend>
                    <div class="control-group">
                        <label class="control-label" for="pLogicGroup">上级站点</label>

                        <div class="controls">

                            <input id="pLogicGroup" type="text" readonly="readonly"
                                   value="${(parentLogicGroup.logicGroupName)!"无"}">
                            <span id="pLogicGroupButton" class="btn btn-mini"><i
                                    class="icon-retweet"></i>更改</span>
                        </div>
                    </div>

                <#if logicGroup.site??>
                    <div class="control-group">
                        <label class="control-label">绑定站点</label>

                        <div class="controls">
                            <input id="logicgroup-name" type="text" disabled="true"
                                   value="${logicGroup.logicGroupName!}"/>
                            <span id="bind-logicgroup-site" class="btn btn-mini"><i
                                    class="icon-retweet"></i>更改</span>
                        </div>
                    </div>
                </#if>
                    <div class="control-group">
                        <div class="controls">
                            <button id="logicgroup-submit" class="btn btn-primary" type="submit">保存</button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<div class="hide">
    <div id="dialog" class="span4">
        <div id="tree" class="ztree"></div>
        <span class="help-block red m-l-20"></span>
    </div>

    <div id="address-area">
        <div class="container-fluid">
            <div class="row-fluid">
                <div class="span12">
                    <div class="control-group">
                        <label class="control-label">省/直辖市</label>

                        <div class="controls">
                            <select id="province">
                                <option value="0">请选择</option>
                            <#list areaCodeCNs as area>
                                <option value="${area.areaCode?c}">${area.areaName!}</option>
                            </#list>
                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">市</label>

                        <div class="controls">
                            <select id="city"></select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">区/县</label>

                        <div class="controls">
                            <select id="zone"></select>
                        </div>
                    </div>
                    <div id="site" class="control-group">
                        <label class="control-label">绑定站点</label>

                        <div class="controls">
                            <select class="i-w-270" name="siteId">
                                <option value="0">请选择</option>
                            </select>

                            <p class="help-block red"></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "../../_common/footer.ftl">
<#include "../../_common/common-js.ftl">

<script type="text/javascript">
    $(function () {
        $("#pLogicGroupButton").click(function () {

            var $dialogDiv = $("#dialog");
            $.post("logicGroups.action", function (result) {

                if (result.length == 0) {
                    $dialogDiv.find(".help-block").empty().text("无可用站点");

                    art.dialog({
                        id: "treeDialog",
                        title: "选择上级站点",
                        content: $dialogDiv[0],
                        fixed: true,
                        locked: true,
                        cancelValue: "取消",
                        cancel: function () {
                        },
                        // 添加顶级站点按钮 @gaohui 2012-12-05
                        button: [
                            {
                                value: '设为顶级站点',
                                callback: function () {
                                    $("#pLogicGroup").val('无');
                                    $("#parent-id").val(-1);
                                }
                            }
                        ]
                    });
                    return;
                }


                showParentLogicGroupDialog(result);

            }, "json");

        });

        function showParentLogicGroupDialog(nodes) {
            //上级站点ID
            var logicGroupId = 0;
            //上级站点名称
            var logicGroupName = "";
            var $dialogDiv = $("#dialog");

            // 初始化弹出框
            art.dialog({
                id: "treeDialog",
                title: "选择上级站点",
                content: $dialogDiv[0],
                fixed: true,
                locked: true,//TODO 正确的属性是'lock'
                okValue: "确定",
                ok: function () {
                    $.fn.zTree.init($('#tree'), setting, nodes);
                    // 必须选择一个站点
                    if (logicGroupId == 0) {
                        $dialogDiv.find(".help-block").empty()
                                .append("请选择站点");
                        return false;
                    }

                    //更新上级站点名称和对应ID
                    $("#pLogicGroup").val(logicGroupName);
                    $("#parent-id").val(logicGroupId);
                    $dialogDiv.find(".help-block").empty();
                },
                cancelValue: "取消",
                cancel: function () {
                    $dialogDiv.find(".help-block").empty();
                },

                // 添加顶级站点按钮 @gaohui 2012-12-05
                button: [
                    {
                        value: '设为顶级站点',
                        callback: function () {
                            $("#pLogicGroup").val('无');
                            $("#parent-id").val(-1);
                        }
                    }
                ]
            });

            var setting = {
                view: {
                    showLine: false
                },
                async: {
                    enable: true,
                    url: 'logicGroups.action',
                    autoParam: ["id=logicGroupId"]
                },
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        logicGroupId = treeNode.id;
                        logicGroupName = treeNode.name;
                    }
                }
            };
            // 初始化 zTree
            $.fn.zTree.init($('#tree'), setting, nodes);

        }

        //选择站点后将logicgroup名称修改
        (function () {
            $("#bind-logicgroup-site").click(function () {
                art.dialog({
                    title: "选择站点",
                    content: $("#address-area")[0],
                    fixed: true,
                    locked: true,
                    okValue: "确定",
                    ok: function () {
                        var $site = $("#site").find("select");
                        var $help = $("#site").find("p.help-block");
                        if ($site.val() == '0') {
                            $help.empty().text('请选择站点');
                            return false;
                        }

                        //设置新的 siteId 和 site 名称
                        $("#logicgroup-name").val($site.find("option:selected").text());
                        $("#site-id").val($site.val());
                    },
                    cancelValue: "取消",
                    cancel: function () {
                        $('#site').find('p.help-block').empty();
                    }
                });
            });
        })();

        $("#logicgroup-submit").click(function () {

            art.dialog({
                id: "save",
                title: "提示",
                content: "是否保存？",
                fixed: true,
                locked: true,
                okValue: "确定",
                ok: function () {
                    $("#adjustForm").submit();
                },
                cancelValue: "取消",
                cancel: function () {
                }
            });
            return false;
        });
    });
</script>
<script type="text/javascript" src="js/site-area.js"></script>
<#include "../../_common/last-resources.ftl">
</body>
</html>
