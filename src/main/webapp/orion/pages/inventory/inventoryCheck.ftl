<#--
出库盘点

@author duan qixin
@time  13-5-28
-@check @gaohui #3950 2013-06-06
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
<title>库存盘点 - 资产管理</title>
<#include "../_common/common-css.ftl">

<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "orion:inventory">
<#include "../_common/header.ftl">
<#setting number_format="#">

	<div id="gcontent" class="container m-t-50">

    <#include "/common/pages/message-tooltip.ftl">
    <@messageTooltip />

    <div class="row">
	    <div class="span12">
	    	<div class="m-b-10 m-t-5 t-a-r">
	    	<#--创建盘点按钮权限设置-->
        	<#if security.isPermitted("orion:inventory:add")>
				<a id="create_btn" class="btn btn-success">
					<i class="icon-pencil icon-white"></i>创建盘点
				</a>
            </#if>
	    	</div>
	    </div>
    </div>

    <div class="row">
    	<div class="span12">

		<#--数据列表展示-->
	    <#if (inventorys?size<1)>
            <h4 class="m-l-20">无数据</h4>
        <#else >
    		<table class="table table-bordered table-center">
    			<thead>
		            <tr class="tb_head">
		                <th>截止时间</th>
		                <th>总数</th>
		                <th>在库</th>
		                <th>出库</th>
		                <th>标签</th>
		                <th>扫描</th>
		                <th>异常</th>
		                <th>操作</th>
		            </tr>
    			</thead>
	            <tbody>
	            <#list inventorys as inty>
	            	<#if !inty.state>
			            <tr>
			            	<td>${(inty.deadlineDate)?string('yyyy-MM-dd HH:mm:ss')!}</td>
			            	<td colspan="6">正在盘点中</td>
			            	<td>
                            <a href="realtimeInventory.action?id=${(inty.id)!}" class="btn btn-mini btn-success">
                                <i class="icon-file icon-white"></i>实时报告
                            </a>
			            	<#--结束按钮权限设置-->
			            	<#if security.isPermitted("orion:inventory:close")>
		            			<a href="stopInventory.action?id=${(inty.id)!}" class="btn btn-mini btn-danger">
		            				<i class="icon-stop icon-white"></i>结束
		            			</a>
			                </#if>
			            	</td>
			            </tr>
	            	<#else>
			            <tr>
			            	<td>${(inty.deadlineDate)!}</td>
			            	<td>${(inty.sumCount)!}</td>
			            	<td>${(inty.stockInCount)!}</td>
			            	<td>${(inty.stockOutCount)!}</td>
			            	<td>${(inty.tagCount)!}</td>
			            	<td>${(inty.scanCount)!}</td>
			            	<td>${(inty.errorCount)!}</td>
			            	<td>
			            		<a class="btn btn-success btn-mini" href="inventoryCheckInfo.action?id=${(inty.id)!}">
			            			<i class="icon-file icon-white"></i>报告
			            		</a>
			            	</td>
			            </tr>
     				</#if>
		        </#list>
	            </tbody>
			 </table>
		</#if>
    	</div>
    </div>

    <div class="row">
        <div class="span12">
        <#include "/common/pages/pagging.ftl">
	    <#assign url = "inventoryCheck.action?"/>
        <@pagination url, index, pageCount,"index"/>
        </div>
    </div>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#create_btn").click(function() {
	<#if isStart>
		art.dialog({
		    title: '提示',
		    content: '请结束正在进行中的盘点！',
		    okValue: '确定',
		    ok: function () {
		    }
		});
	<#else>
    	window.location.href = "createInventory.action";
	</#if>
	});
});
</script>
<#include "../_common/last-resources.ftl">
</body>
</html>

