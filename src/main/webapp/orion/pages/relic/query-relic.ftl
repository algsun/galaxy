<#--
藏品信息

@author Wang.yunlong
@time  13-5-8
@author wang.geng
@date 2013-06-04
@author liu.zhu
@date 2013-08-08
@check @gaohui #3960 2013-06-04
@check @gaohui #5058 2013-08-15

-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>藏品信息 - 资产管理</title>

<#include "../_common/common-css.ftl">
    <link rel="stylesheet" href="css/conditionPanel.css"/>
    <style type="text/css">
        .query-input {
        <#if inputView>
            display: none;
        </#if>
        }

        .query-input input {
            margin: 10px 20px 0 0;
            width: 150px;
        }

        .open-input, .close-input {
            cursor: pointer;
        }

        .table thead {
            background-color: #ececec;
        }

        .query-input-row {
            float: left;
            width: 400px;
        }

        .input-name > label {
            margin-bottom: 0;
            margin-top: 8px;
        }

        .input-name {
            float: left;
            text-align: right;
            width: 100px;
        }

        .input {
            vertical-align: text-bottom;
            margin-left: 10px;
        }

        /* 内容居中 */
        .table.table-center td {
            text-align: center;
            vertical-align: middle;
        }
    </style>

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "orion:culturalRelic">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "orion:culturalRelic:list"></@subNavs>

<#-- 消息提示 -->
<#include "/common/pages/message-tooltip.ftl">
<@messageTooltip></@messageTooltip>

<#include "../_common/conditionPanel.ftl">

<#--your content-->
<#--查询条件-->
    <div class="row">
        <div class="span12">
            <form action="queryRelic.action" method="post" id="queryRelicFrom">

                <input type="hidden" value="${labelId!}" name="exportLabelId"/>

                <input type="hidden" value="${totalCount?c}" id="totalCount">
                <input type="hidden" value="${count?c}" id="exportCount">
                <input type="hidden" name="inputView" value="${inputView?string("true","false")}">
                <table class="table">
                <#assign stateList = [{"name":"在库","id":0},{"name":"出库","id":2},{"name":"待出库","id":1}]>
                <#assign logOffState = [{"name":"未注销","id":0},{"name":"已注销","id":1}]>
                <@queryConditions "库房位次" "zoneId" listZone "first"/>
                <@queryConditions "库存状态" "state" stateList "midd"/>
                <@queryConditions "级别" "levelId" listLevel "midd"/>
                <@queryConditions "文物质地" "textureId" listTexture "midd"/>
                <@queryConditions "时代" "eraId" listEra "midd"/>
                <@queryConditions "标签" "labelId" relicLabels "midd"/>
                <@queryConditions "注销状态" "logOff" logOffState "last"/>
                </table>
                <div class="query-input">
                    <div>
                    <span class="inline-block">
                        <input placeholder="藏品名称" class="m-l-5" id="name" type="text" name="name" value="${name!}">
                    </span>
                    <span class="p-5 inline-block">
                        <input placeholder="总登记号" class="m-l-5" id="total-code" type="text" name="totalCode"
                               value="${totalCode!}">
                    </span>
                    <span class="inline-block">
                        <input placeholder="编目号" class="m-l-5" id="catalog-code" type="text" name="catalogCode"
                               value="${catalogCode!}">
                    </span>
                    <span class="p-5 inline-block">
                        <input placeholder="分类号" class="m-l-5" id="type-code" type="text" name="typeCode"
                               value="${typeCode!}">
                    </span>
                    <span class="inline-block">
                        <input style="width: 50px;margin-left: 50px" class="btn btn-small" type="submit" value="查询"/>
                    </span>
                    </div>
                </div>

            </form>
        </div>
    </div>
<#--查询统计信息-->
    <div class="row">
        <div class="span12">
            <div class="m-v-10">
                <span>藏品总数：${count?c}</span>
                <span class="m-l-20">藏品总件数：${allCount?c}</span>
            </div>
        </div>
    </div>
<#--文物列表-->
    <div class="row">
        <div class="span12">
        <#if (relics?size<1)>
            <h4 class="m-l-20">无数据</h4>
        <#else >
        <table class="table table-bordered table-striped table-center">
            <thead>
            <tr>
                <th>序号</th>
                <th>藏品名称</th>
                <th>总登记号</th>
                <th>编目号</th>
                <th>分类号</th>
                <th>库房位次</th>
                <th>时代</th>
                <th>级别</th>
                <th>质地</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                <#list relics as relic>
                <tr>
                    <td>${relic_index+1}</td>
                    <td>
                        <a href="relicArchive.action?pre=0&totalCode=${relic.totalCode!}&index=${index}&totalCodeAsCondition=${totalCode!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}">${relic.name}</a>
                    </td>
                    <td>${relic.totalCode!}
                        <#if relic.hasTag>
                            <li class="icon-tag" title="标签"></li>
                        </#if>
                    </td>
                    <td>
                        <a href="relicCard.action?pre=0&totalCode=${relic.totalCode!}&index=${index}&totalCodeAsCondition=${totalCode!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}">${relic.catalogCode!}</a>
                    </td>
                    <td>${(relic.typeCode)!}</td>
                    <td>${(relic.zone.name)!"(暂无)"}</td>
                    <td>${(relic.era.name)!}</td>
                    <td>${(relic.level.name)!}</td>
                    <td>${(relic.texture.name)!}</td>
                    <td>
                    <#-- 如果已经注销，只显示注销状态；无注销，显示库存状态 -->
                        <#if relic.iscanceled>
                            已注销
                        <#else>
                            <#if relic.state == 0>
                                在库
                            <#elseif relic.state == 3>
                                出库申请中
                            <#elseif relic.state == 1>
                                待出库
                            <#elseif relic.state == 2>
                                出库
                            </#if>
                        </#if>
                    </td>
                    <td>

                        <a class="btn btn-mini"
                           href="relicCardRelicId.action?pre=0&relicId=${relic.id?c}&index=${index}&totalCodeAsCondition=${totalCode!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}"
                           title="藏品卡">藏品卡</a>
                        <a class="btn btn-mini"
                           href="relicArchiveRelicId.action?pre=0&relicId=${relic.id?c}&index=${index}&totalCodeAsCondition=${totalCode!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}"
                           title="档案">档案</a>
                        <#if security.isPermitted("orion:culturalRelic:update")>
                            <a class="btn btn-success btn-mini"
                               href="toUpdateRelic.action?relicId=${relic.id?c}&index=${index}&totalCodeAsCondition=${totalCode!}&name=${name!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&labelId=${labelId!}&zoneId=${zoneId!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}"
                               title="更新">
                                <i class="icon-pencil icon-white"></i>编辑
                            </a>
                        </#if>
                        <a class="btn btn-mini" href="repairRecords/new?relic.id=${relic.id?c}" title="修复">修复</a>
                        <#if security.isPermitted("orion:culturalRelic:healthEvaluation")>
                            <a class="btn btn-mini" href="healthEvaluation/index?relic.id=${relic.id?c}" title="评测">评测</a>
                        </#if>
                        <#if security.isPermitted("orion:culturalRelic:logOut")>
                            <a onclick="javascript:return confirm('是否确认注销？')"
                               class="btn <#if relic.iscanceled>btn-success<#else>btn-danger</#if> btn-mini"
                               href="logOffRelic.action?relicId=${relic.id?c}&cancelState=<#if relic.iscanceled>0<#else>1</#if>&logOff=${logOff}">
                                <i class="<#if relic.iscanceled>icon-play<#else>icon-stop</#if> icon-white"></i><#if relic.iscanceled>
                                取消注销<#else>注销</#if>
                            </a>
                        </#if>
                        <a class="btn btn-mini" href="initialize?relicId=${relic.id?c}" title="监测数据">监测数据</a>
                    </td>
                </tr>
                </#list>
            </tbody>
        </#if>
        </table>
        </div>
    </div>
    <div class="row">
        <div class="span12">
        <#include "/common/pages/pagging.ftl">
        <#assign url = "queryRelic.action?logOff=${logOff!}&totalCode=${totalCode!}&name=${name!}&zoneId=${zoneId!}&catalogCode=${catalogCode!}&typeCode=${typeCode!}&eraId=${eraId!}&levelId=${levelId!}&textureId=${textureId!}&state=${state!}&labelId=${labelId!}"/>
        <@pagination url, index, pageCount,"index"/>
        </div>
    </div>
</div>
<div class="hide">
    <div id="zoneTreeDialog" class="span4">
        <div id="zoneTree" class="ztree"></div>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="js/conditionPanel.js"></script>
<script type="text/javascript" src="js/query-relic-data.js"></script>
<script type="text/javascript">
    $(function () {
        /**
         * 查询条件显示动画效果
         */
        (function () {
            var $queryInput = $(".query-input");
            var $open = $(".open-input");
            var $close = $(".close-input");
            var $inputView = $("input[name=inputView]");
            $open.click(function () {
                $queryInput.slideDown("fast");
                $inputView.val("true");
                $open.hide();
                $close.show();
            });
            $close.click(function () {
                $queryInput.slideUp("fast");
                $inputView.val("false");
                $close.hide();
                $open.show();
            });
        })();
    });
</script>
<script type="text/javascript">

    //面板展开
    var visible = true;

    $(function () {
        $(document).on('click', '.subsystemHead', function () {
            art.dialog({
                title: '正在努力加载!'
            });
        });
    });
    /**
     * 隐藏条件
     * @param labelId
     */
    function moreLabel(labelId) {
        var fistLine = labelId + "firstLine";
        var lastLine = labelId + "lastLine";
        if (visible) {
            $("." + labelId).css('display', '');
            $("." + fistLine + "> td ").css('border-bottom', 'none');
            $("." + lastLine + "> td ").css('border-bottom', '');
            $("." + fistLine + "> td > small").text("收起");
            visible = false;
        } else {
            $("." + labelId).css('display', 'none');
            $("." + fistLine + "> td ").css('border-bottom', '');
            $("." + lastLine + "> td ").css('border-bottom', 'none');
            $("." + fistLine + "> td > small").text("更多");
            visible = true;
        }
    }
</script>

<#include "../_common/last-resources.ftl">

<#--导出excle时，则是属性-->
<#include "select-property.ftl" >
</body>
</html>
<#macro selectOption name enName objList defaultId=0>
<span class="inline-block">
    <label style="text-align: center;width: 56px;">${name}</label>
</span>
<span class="inline-block">
    <select style="width: 184px" name="${enName}" id="${enName}">
        <#if objList?size != 0>
            <option value="">全部</option>
            <#list objList as obj>
                <option value="${obj.id}" <#if defaultId?string == (obj.id)?string>selected</#if>>${obj.name}</option>
            </#list>
        </#if>
    </select>
</span>
</#macro>

<#--全选-->
<script type="text/javascript">
    $("#all").change(function () {
        var checked = $(this).is(":checked");

        var doCheck = function ($checkboxes, checked) {
            $checkboxes.each(function () {
                if (checked) {
                    $(this).attr('checked', 'checked');
                } else {
                    $(this).removeAttr('checked');
                }
            });
        };

        var $pro = $("#selectProperty input[name='property']");
        var $pro2 = $("#selectProperty input[name='property2']");
        doCheck($pro, checked);
        doCheck($pro2.filter(':visible'), checked);
    });

    var $queryPro = $("#queryPro");
    var unfold = $("#unfold");
    unfold.text("展开");
    var isOpen = false;
    unfold.click(function () {
        isOpen = !isOpen;
        unfold.text(isOpen ? '收缩' : '展开');
        $queryPro.slideToggle('slow');
    });
</script>