<#--
盘点报告

-@author duan qixin
-@time  13-5-28
-@check @gaohui #4069 2013-06-06
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
<title>盘点报告 - 资产管理</title>
<#include "../_common/common-css.ftl">

<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.click_btn{
	float: right;
	cursor: pointer;
}
.click_btn:hover{
	color: blue;
}
.invent_exp{
	display: none;
}
.icon-plus{
	cursor: pointer;
	float: right;
}
.icon-minus{
	cursor: pointer;
	float: right;
}
</style>
</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "orion:inventory">
<#include "../_common/header.ftl">
<#setting number_format="#">

	<div id="gcontent" class="container m-t-50">
	<div class="row m-t-10">
        <div class="span12">
            <fieldset>
                <legend>
                    <a class="go-back" href="inventoryCheck.action" title="返回">
                        <i class="mw-icon-prev"></i>库存盘点
                    </a>
                </legend>
            </fieldset>
        </div>
    </div>
    <div class="row">
	    <div class="span12 m-b-10 t-a-c">
            <div class="bold" style="font-size:20px;">
                <#if !inventory.state>
                    截止${(inventory.deadlineDate)?datetime!}盘点报告
                <#else>
                    ${(inventory.deadlineDate)?string('yyyy-MM-dd')!}盘点报告
                </#if>
            </div>
	    </div>
	</div>
    <div class="row">
    	<div class="span12">
    		<table class="table table-bordered table-center">
    			<col width="340px;"/>
    			<col width="300px;"/>
    			<thead>
		            <tr class="tb_head">
		            	<th></th>
		                <th>藏品数</th>
		                <th>件数</th>
		            </tr>
    			</thead>
	            <tbody>
                    <#-- TODO 其余也同样做法 @gaohui 2013-06-06 -->
                    <@invetoryItemDetail "总数", inventory.sumCount, inventory.sumNumber, inventory.countList />
                    <@invetoryItemDetail "在库", inventory.stockInCount, inventory.stockInNumber, inventory.stockInList />
                    <@invetoryItemDetail "出库", inventory.stockOutCount, inventory.stockOutNumber, inventory.stockOutList />
                    <@invetoryItemDetail "标签", inventory.tagCount, inventory.tagNumber, inventory.tagList />
                    <@invetoryItemDetail "扫描", inventory.scanCount, inventory.scanNumber, inventory.scanList />
                    <@invetoryItemDetail "异常", inventory.errorCount, inventory.errorNumber, inventory.errorList />
	            </tbody>
			 </table>
    	</div>
    </div>
    <div class="row">
    	<div class="span12">
	    	<div class="well well-small">
	    		<span style="font-size:20px;font-weight:700;">出库详单</span>
	    		<div class="click_btn" id="out_info_btn">
	    			详细
	    		</div>
	    		<div id="out_info_div" style="display:none;">
	    		<#--数据列表展示-->
			    <#if (inventoryOut?size<1)>
		            <h4 style="margin-left: 20px;">无数据</h4>
		        <#else >
		        	<table class="table table-bordered">
		    			<col width="20%"/>
						<col width="20%"/>
						<col width="20%"/>
		    			<thead style="background-color:#ececec;">
				            <tr class="tb_head">
				                <th>总登记号 </th>
				                <th>名称</th>
				                <th>库房位次</th>
				                <th>出库事件</th>
				            </tr>
		    			</thead>
			            <tbody>
			            <#list inventoryOut as intOut>
				            <tr>
				            	<td>${(intOut.relic.totalCode)!}</td>
				            	<td>${(intOut.relic.name)!}</td>
				            	<td>${(intOut.relic.zone.name)!"(暂无)"}</td>
				            	<td>${(intOut.outEvent.useFor)!}</td>
				            </tr>
			            </#list>
			            </tbody>
					 </table>
		        </#if>
	    		</div>
	    	</div>
	    </div>
    </div>
    <div class="row">
    	<div class="span12">
	    	<div class="well well-small">
	    		<span style="font-size:20px;font-weight:700;">异常详单</span>
	    		<div id="ept_info_btn" class="click_btn">
	    			详细
	    		</div>
	    		<div id="ept_info_div" style="display:none;">
	    		<#--数据列表展示-->
			    <#if (inventoryErrors?size<1)>
		            <h4 style="margin-left: 20px;">无数据</h4>
		        <#else >
		    		<table class="table table-bordered">
		    			<col width="20%"/>
						<col width="20%"/>
						<col width="20%"/>
		    			<thead style="background-color:#ececec;">
				            <tr class="tb_head">
				                <th>总登记号 </th>
				                <th>名称</th>
				                <th>库房位次</th>
				                <th>系统状态</th>
				                <th>扫描状态</th>
				            </tr>
		    			</thead>
			            <tbody>
			            <#list inventoryErrors as ierror>
				            <tr>
				            	<td>${(ierror.relic.totalCode)!}</td>
				            	<td>${(ierror.relic.name)!}</td>
				            	<td>${(ierror.relic.zone.name)!"(暂无)"}</td>
			            		<#if ierror.sysState == 2>
			            			<td>出库</td>
                                <#elseif ierror.sysState == 3>
                                    <td>待出库</td>
			            		<#elseif ierror.sysState == 0 || ierror.sysState == 1>
			            			<td>在库</td>
			            		</#if>
			            		<#if ierror.scanState == 0>
			            			<td>待扫描</td>
			            		<#elseif ierror.scanState == 1>
			            			<td>扫描到</td>
			            		<#elseif ierror.scanState == 2>
			            			<td>未扫到</td>
			            		</#if>
				            </tr>
			            </#list>
			            </tbody>
					 </table>
		        </#if>
	    		</div>
	    	</div>
	    </div>
    </div>
    <div class="row">
    	<form action="updateRemark.action?id=${(inventory.id)!}&backTo=${backTo!}" method="post">
	    	<div class="span12">
		    	<div>
		    		<div style="margin-left: 10px;">
		    			<span style="font-size:20px;font-weight:700;">备注信息</span>
		    		</div>
		    		<#--提交备注按钮权限设置-->
		        	<#if security.isPermitted("orion:inventory:update")>
			    		<div>
			    			<textarea id="apply_desc" style="resize:none;width:100%;height:40px;margin:0px;padding:0px;" name="remark">${(inventory.remark)!}</textarea>
			    		</div>
				    	<div style="text-align:right; margin-top: 10px;">
				    		<button type="submit" class="btn btn-primary" id="relicSubmit">修改备注</button>
				    	</div>
			    	<#else >
			    		<div>
			    			<textarea id="apply_desc" style="resize:none;width:100%;height:40px;margin:0px;padding:0px;" disabled="disabled">${(inventory.remark)!}</textarea>
			    		</div>
		            </#if>
		    	</div>
		    </div>
    	</form>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#out_info_btn").click(function() {
			var o = $("#out_info_div");
			if(o.css("display") == "none") {
				o.slideDown();
				$(this).html("隐藏");
			}else {
				o.slideUp();
				$(this).html("详细");
			}
		});
		
		$("#ept_info_btn").click(function() {
			var o = $("#ept_info_div");
			if(o.css("display") == "none") {
				o.slideDown();
				$(this).html("隐藏");
			}else {
				o.slideUp();
				$(this).html("详细");
			}
		});
		
		//盘点报告详细信息列表
		$(".icon-plus").click(function() {
			if($(this).attr("class") == "icon-plus") {
				$(this).attr("title","关闭");
				$(this).parent().parent().next().show();
			}else {
				$(this).attr("title","点击查看区域盘点详情");
				$(this).parent().parent().next().hide();
			}
			$(this).toggleClass("icon-minus");
			$(this).toggleClass("icon-plus");
		});
	});
</script>
<#include "../_common/last-resources.ftl">
</body>
</html>

<#--
了表格的列宽
-->
<#macro subTableCols>
<col width="339px;"/>
<col width="300px;"/>
</#macro>

<#macro invetoryItemDetail name, count, number, invetoryItem>
<tr>
    <td><span style="font-weight:bold;">${name}</span><i id="invent_icon" class="icon-plus" title="点击查看区域盘点详情"></i></td>
    <td>${(count)!}</td>
    <td>${(number)!}</td>
</tr>
<tr class="invent_exp">
    <td colspan=3 style="padding:0;background-color:white;">
    <#--数据列表展示-->
        <#if (invetoryItem?size<1)>
            <h4 style="margin-left: 20px;">无数据</h4>
        <#else >
            <table class="table table-bordered">
                <@subTableCols/>
                <tbody>
                    <#list invetoryItem as cnt>
                    <tr>
                        <td>${(cnt.zoneName)!"（暂无库房位次）"}</td>
                        <td>${(cnt.countNo)!}</td>
                        <td>${(cnt.sumNo)!}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
    </td>
</tr>
</#macro>
