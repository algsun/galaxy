<#--

@author gaohui
@time  2013-5-22
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>藏品卡 - 资产管理</title>
<#include "../_common/common-css.ftl">


<#include  "/common/pages/common-tag.ftl">
    <style type="text/css">
        .td-title {
            width: 120px;
            height: 40px;
            text-align: center;
            /*font-weight: bold;*/
        }

        .td-content {
            width: 140px;
            height: 40px;
            padding-left: 10px;

        }
    </style>
</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "orion:culturalRelic">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

    <fieldset>
        <legend>
            <a class="go-back" href="queryRelic.action" title="返回">
                <i class="mw-icon-prev"></i>藏品卡
            </a>
        </legend>
    </fieldset>

    <div class="row m-t-10">
        <div class="span12">
            <div class="form-inline m-b-10">
                <div class="btn-group m-r-10">
                <#if preCode?? && preCode != "">
                    <a title="上一个" class="btn"
                       href="relicCard.action?totalCode=${preCode}"><i
                            class="icon-arrow-left"></i></a>
                <#else>
                    <a title="上一个" class="btn disabled"><i class="icon-arrow-left"></i></a>
                </#if>

                <#if nextCode?? && nextCode != "">
                    <a title="下一个" class="btn"
                       href="relicCard.action?totalCode=${nextCode}"><i
                            class="icon-arrow-right"></i></a>
                <#else>
                    <a title="下一个" class="btn disabled"><i class="icon-arrow-right"></i></a>
                </#if>
                </div>

                <label class="inline-block">总登记号</label>
            <#--<span class="inline-block p-5 bold" style="font-size: 1.5em;">${(relicCard.totalCode)!}</span>-->

                <div id="total-code-input" class="input-append">
                    <input class="input-small" id="appendedInputButton" type="text" value="${totalCode}" onchange="keyEnter()">

                    <div class="btn-group">
                        <button id="total-code-go-button" class="btn" type="button">跳到</button>
                        <button class="btn dropdown-toggle" data-toggle="dropdown">
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a class="disabled" href="culturalRelicArchives.action?code=${totalCode}"><i
                                    class="icon-list-alt"></i> 档案</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="span12"><h4>未找到对应的文物</h4></div>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<script type="text/javascript">
    $(function () {
        // 总登记号跳转
        $("#total-code-go-button").click(function () {
            // TODO basedUrl @gaohui 2013-05-21
            location.href = "relicCard.action?totalCode=" + $("#total-code-input > input").val();
        });
    });
    function keyEnter() {
        location.href = "relicArchive.action?totalCode=" + $("#total-code-input > input").val();
    }
</script>
<#include "../_common/last-resources.ftl">
</body>
</html>