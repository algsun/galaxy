<#--
区域管理员

@author duan qixin
@time  13-6-13
@check @gaohui #4251 2013-06-19
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
<title>库房管理 - 资产管理</title>
<#include "../_common/common-css.ftl">
    <#-- TODO 删除多余样式 -->
<style type="text/css">
.invent_exp{
	display: none;
	background-color:white;
}
.icon-plus-sign{
	cursor: pointer;
	float: right;
}
.icon-minus-sign{
	cursor: pointer;
	float: right;
}
</style>
</head>
<body>
<#--页面以及标题================= -->
<#assign currentTopPrivilege = "orion:storeroom">
<#include "../_common/header.ftl">

<#-- 页面业务==============begin================= -->
<div id="gcontent" class="container m-t-50">
	<#include "/common/pages/message-tooltip.ftl">
    <@messageTooltip />

	<div class="m-b-10 m-t-20 t-a-r">
		<a class="btn btn-success"  href="userList.action">
			<i class="icon-pencil icon-white"></i>绑定管理员
		</a>
	</div>
    <div class="row">
    	<div class="span12">
        <fieldset>
            <legend>管理员绑定列表</legend>
        </fieldset>
		<#--数据列表展示-->
    		<table class="table table-bordered table-center" style="margin-top: 30px;">
    			<thead>
		            <tr class="tb_head">
		                <th style="width: 40px">序号</th>
		                <th style="width: 400px">区域名称</th>
                        <th style="width: 400px">管理员</th>
                        <th style="width: 100px">操作</th>
		            </tr>
    			</thead>
	            <tbody>

                <#if uzs?size == 0>
                    <tr>
                        <td colspan="2"><strong>暂无数据</strong></td>
                    </tr>

                <#else>

                    <#list uzs as uz>
                        <tr>
                            <td>${uz_index+1}</td>
                            <td>${(uz.zone.name)!}</td>
                            <td style="padding: 0" colspan="2">
                                <table style="margin-bottom: 0;width: 100%">
                                    <tbody>
                                        <#list uz.users as user>
                                        <tr>
                                            <td style="width: 400px">${(user.userName)!}</td>
                                            <td style="width: 98px">
                                                <a href="deleteBind.action?userIds=${(user.id)!}&zoneId=${(uz.zone.id)!}" class="btn btn-mini btn-danger">
                                                    <i class="icon-stop icon-white"></i>删除
                                                </a>
                                            </td>
                                        </tr>
                                        </#list>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </#list>
                </#if>
	            </tbody>
			 </table>
    	</div>
    </div>
</div>
<#-- 页面业务==============end================= -->
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#-- 页面JS脚本==============begin================= -->
<script type="text/javascript">
$(document).ready(function(){
	//详细信息列表开关
	$(".icon-plus-sign").click(function() {
		if($(this).attr("class") == "icon-plus-sign") {
			$(this).attr("title","关闭");
			$(this).parent().parent().next().show();
		}else {
			$(this).attr("title","点击查看库房管理员详细");
			$(this).parent().parent().next().hide();
		}
		$(this).toggleClass("icon-plus-sign");
		$(this).toggleClass("icon-minus-sign");
	});
});
</script>
<#-- 页面JS脚本==============end================= -->
<#include "../_common/last-resources.ftl">
</body>
</html>
