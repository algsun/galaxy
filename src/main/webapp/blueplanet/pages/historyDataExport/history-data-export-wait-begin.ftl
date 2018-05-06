<#--
历史数据导出等待页面
-->
<!DOCTYPE html>
<html>
<head>

<#include "../_common/common-head.ftl">
    <title>${locale.getStr("blueplanet.location.exportData")}</title>
<#include "../_common/common-css.ftl">

</head>
<body data-server-time="${.now?long?c}">
    <div style="text-align: center;height:200px; line-height:200px; "><img  src="images/wait.gif"></div>
</body>
</html>