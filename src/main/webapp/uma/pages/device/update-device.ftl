<#--
  - 编辑设备
  -@author li.jianfei
  -@time  2013-4-11 13:16
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>编辑设备 - 人员行为</title>

<#include "../_common/common-css.ftl">

</head>
<body>
<#--页面一级标题-->
<#assign currentTopPrivilege = "uma:queryDevice">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
    <#-- TODO 无用的代码的话就删掉 -->
<#--<#include "sub-navs.ftl">-->
<#--<@subNavs "uma:ftp:list"></@subNavs>-->

<#-- 消息提示 -->
    <#-- 信息提示风格统一 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>


<#--your content-->
    <div class="row">
        <div class="span12">
            <form id="form" name="form" action="updateDevice.action" method="post"
                  class="form-horizontal">
                <input id="deviceType" type="hidden" name="deviceType" value="${device.type}">
                <input id="deviceId" type="hidden" name="deviceId" value="${device.id}">
                <input id="currentPageIndex" type="hidden" name="currentPageIndex" value="${currentPageIndex}">
                <fieldset>
                    <legend>
                        编辑<#if device.type==2>激发器<#elseif device.type==1>读卡器</#if>
                    </legend>
                <#if device.type==2>
                    <div class="control-group">
                        <label class="control-label" for="zone-input">
                            <span class="red">*</span>区域名称
                        </label>

                        <div class="controls">
                            <input id="zoneId" name="zoneId" type="hidden" value="${device.zoneId!}">
                            <input id="zone-input" name="zoneName" data-zone-id="" type="text"
                                   value="${device.zoneName!}">
                            <span id="zone-input-help" class="help-inline"></span>
                        </div>
                    </div>
                </#if>
                    <div class="control-group">
                        <label class="control-label" for="deviceName-input">
                            <span class="red">*</span><#if device.type==2>激发器<#elseif device.type==1>读卡器</#if>名称
                        </label>

                        <div class="controls">
                            <input id="deviceName-input" type="text"
                                   name="deviceName"
                                   value="${device.name!}"
                                   maxlength="25">
                            <span id="deviceName-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="deviceName-input">
                        <#if device.type==2>激发器<#elseif device.type==1>读卡器</#if>编号
                        </label>

                        <div class="controls">
                            <input type="text" value="${device.sn}" disabled>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" id="submit-button"
                                class="btn btn-primary">
                            确定
                        </button>
                        <a href="queryDevices.action?deviceType=${device.type}" class="btn">返回</a>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<div class="hide">
    <div id="zoneTreeDialog" class="span4">
        <div id="zoneTree" class="ztree"></div>

        <p class="help-block m-t-10 red"></p>

        <p class="help-block muted">只能选择叶子区域</p>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/ztree/3.5.01/js/jquery.ztree.core-3.5.min.js"></script>

<#--表单验证-->
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<#--your js-->
<script type="text/javascript">
    $(function () {
        // 区域选择
        (function () {
            var dtd;

            // ztree 树配置
            var setting = {
                view: {
                    showLine: false
                },
                async: {
                    enable: true,
                    url: '../blackhole/zone/children.json',
                    autoParam: ["id=zoneId"]
                },
                callback: {
                    onAsyncSuccess: function (event, treeId, treeNode, msg) {
                        if (dtd) {
                            dtd.resolve();
                        }
                    },
                    onClick: function (event, treeId, treeNode, clickFlag) {
                        if (treeNode.zAsync) {
                            return;
                        }

                        //强制加载子节点
                        var zoneTree = this.getZTreeObj(treeId);
                        zoneTree.reAsyncChildNodes(treeNode, 'refresh', false);
                    }
                }
            };

            var showDialog = function ($zoneInput, zoneTree) {
                var $help = $("#zoneTreeDialog .help-block.red");
                art.dialog({
                    id: "zoneTreeDialog",
                    title: "选择区域",
                    content: $("#zoneTreeDialog")[0],
                    fixed: true,
                    okValue: "确定",
                    ok: function () {
                        // TODO 重构代码 @gaohui 2013-04-23
                        var that = this;
                        var nodes = zoneTree.getSelectedNodes();
                        if (nodes.length == 0) {
                            $help.empty().append("请选择区域");
                            return false;
                        }
                        var node = nodes[0];
                        if (node.zAsync) { //如果子节点已经加载
                            if (node.children && node.children.length > 0) {
                                $help.empty().append('请选择叶子区域');
                                return false;
                            }

                            $zoneInput.val(node.name);
                            $zoneInput.attr("data-zone-id", node.id);
                            $("#zoneId").val(node.id);
                        } else {
                            dtd = $.Deferred();
                            $.when(dtd).done(function () {
                                if (node.children && node.children.length > 0) {
                                    $help.empty().append('请选择叶子区域');
                                } else {

                                    $zoneInput.val(node.name);
                                    $zoneInput.attr("data-zone-id", node.id);
                                    $("#zoneId").val(node.id);
                                    that.close();
                                }
                            });

                            return false;
                        }

                    },
                    cancelValue: "取消",
                    cancel: function () {
                        $help.empty();
                    }
                });
            };

            // 区域输入框获取焦点时
            $("#zone-input").focus(function () {
                var $this = $(this);
                $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
                    // 初始化树
                    $.fn.zTree.init($('#zoneTree'), setting, result);
                    var zoneTree = $.fn.zTree.getZTreeObj("zoneTree");

                    // 初始化弹出框
                    showDialog($this, zoneTree);
                });
            });
        })();


        // 表单验证
        $("#form").validate({
            rules: {
                zoneName: {
                    required: true
                },
                deviceName: {
                    required: true,
                    maxLength: 25
                }
            },
            messages: {
                zoneName: {
                    required: "请选择区域"
                },
                deviceName: {
                    required: "请输入设备名称",
                    maxLength: "设备名称不能超过25个字符"
                }
            },
            errorPlacement: function (error, element) {
                error.appendTo(element.next());
            }
        });
    });
</script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
