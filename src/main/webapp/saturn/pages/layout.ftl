<!DOCTYPE html>
<html>
<head>
<#-- import page START -->
<#-- 引入内容页面 -->
<#import _pagePath as page>
<#-- import page END -->

<#include "_common/common-head.ftl">
    <title>${page.title}</title>

<#include "_common/common-css.ftl">

<#-- head START -->
<@page.head/>
<#-- head END -->
</head>
<body data-server-time="${.now?long?c}">
<#--页面以及标题-->
<#assign currentTopPrivilege = page.currentTopPrivilege>
<#include "_common/header.ftl">

<div class="m-t-20">
    &nbsp;
</div>
<#--your content-->
<@page.content/>

<#include "_common/footer.ftl">
<#include "_common/common-js.ftl">
<@page.script/>
<script type="text/javascript">
    $(function () {
        $(document).on('click', '.subsystemHead', function () {
            art.dialog({
                title: '正在努力加载!'
            });
        });
    });
</script>
</body>
</html>