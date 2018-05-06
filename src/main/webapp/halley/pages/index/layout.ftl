<#import _pagePath as _page>

<!DOCTYPE html>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${_page.title}</title>
<#include "../_common/common-css.ftl">
<@_page.head/>

</head>
<body>
SELECT
COUNT
<#include "../_common/header.ftl">


<div id="gcontent" class="container m-t-50">
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>
    <div class="row">

    <#--<div class="span3">-->
    <#--<#if _page.currentPrivilege??>-->
    <#--<#assign currentPrivilege = _page.currentPrivilege>-->
    <#--</#if>-->
    <#--&lt;#&ndash; 左侧导航 &ndash;&gt;-->
    <#--&lt;#&ndash;<#include "../_common/navs.ftl">&ndash;&gt;-->
    <#--</div>-->

        <div class="span12">
        <@_page.content/>
        </div>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<@_page.script/>
<script type="text/javascript">
    $(function () {
        $(document).on('click', '.subsystemHead', function () {
            art.dialog({
                title: '正在努力加载!'
            });
        });
    });
</script>

<#include "../_common/last-resources.ftl">

</body>
</html>