<#--
页面辅助

@author gaohui
@date 2013-07-16
-->

<#--
显示 div.alert

参数:
msg : 展示的内容
type: alert 类型(默认为 "info"，与bootstrap 支持的类型一致)

-->
<#macro alertMsg msg, type = "info">
<div class="alert alert-${type}">
    <i class="icon-info-sign"></i>

    ${msg!}
</div>
</#macro>

<#--

参数同上
-->
<#macro alertNested type = "info">
<div class="alert alert-${type}">
    <i class="icon-info-sign"></i>

    <#nested >
</div>
</#macro>
