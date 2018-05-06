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
<title>库存盘点 - 资产管理</title>
<#include "../_common/common-css.ftl">
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<#--页面以及标题================= -->
<#assign currentTopPrivilege = "orion:storeroom">
<#include "../_common/header.ftl">

<#-- 页面业务==============begin================= -->
<div class="hide">
    <div id="zoneTreeDialog" class="span4">
        <div id="zoneTree" class="ztree"></div>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>

<div id="gcontent" class="container m-t-50">
	<div>
		<fieldset>
		    <legend>
		        <a class="go-back" href="stockBind.action" title="返回">
		            <i class="mw-icon-prev"></i>返回
		        </a>
		    </legend>
		</fieldset>
	</div>
    <div class="row">
	<div class="span12">
    	<form class="form-inline well well-small" action="userList.action" method="post">
            <label>姓名</label>
            <input name="userName" type="text" maxlength="10" value="${(userName)!}">
            <button class="btn" type="submit">查询</button>

            <div class="pull-right">
                <label>区域</label>
                <input id="zoneInput" class="input-medium" type="text">
                <span id="bindUserBtn" class="btn">绑定人员</span>
            </div>
        </form>
    </div>
    </div>
    
    <div class="row">
	<div class="span12">
	    <table id="user_list" class="table">
			<thead>
	            <tr>
	                <th></th>
	                <th>序号</th>
	                <th>姓名</th>
	                <th>性别</th>
	            </tr>
			</thead>
	        <tbody>
	        	<#list users as user>
		            <tr>
		            	<td><input type="checkbox" value="${(user.id)!}"/></td>
		            	<td>${user_index+1}</td>
		            	<td>${(user.userName)!}</td>
		            	<#if user.sex == 1>
	            			<td>女</td>
	            		<#else>
	            			<td>男</td>
	            		</#if>
		            </tr>
		        </#list>
	        </tbody>
		 </table>
    </div>
    </div>
    
    <div class="row">
    <div class="span12">
    	<#include "/common/pages/pagging.ftl">    	
	    <@pagination "userList.action?userName=${userName!}", index, pageCount, "index"/>
    </div>
    </div>
</div>
<#-- 页面业务==============end================= -->
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#-- 页面JS脚本==============begin================= -->
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	// ztree 树配置
    var setting = {
        view: {
            showLine: false
        },
        async: {
            enable: true,
            url: '/galaxy/blackhole/zone/children.json',
            autoParam: ["id=zoneId"]
        }
    };
    
	// 区域输入框获取焦点时
	$("#zoneInput").focus(function () {
	    var $this = $(this);
	    $.getJSON("/galaxy/blackhole/zone/children.json", {zoneId: ""}, function (result) {
	        // 初始化树
	        $.fn.zTree.init($('#zoneTree'), setting, result);
	        var zoneTree = $.fn.zTree.getZTreeObj("zoneTree");
	
	        // 初始化弹出框
	        showDialog($this, zoneTree);
	    });
	});
	
	// 绑定设备
    $("#bindUserBtn").click(function () {
        var zoneId = $("#zoneInput").attr("data-zone-id");
        if (!zoneId) {
            art.dialog({title: "提示", content: "请选择区域"});
            return;
        }
        
        var userIds = []; // 选中的人
        var users = $("#user_list input[type='checkbox']:checked");
        if(users.length == 0) {
        	art.dialog({title: "提示", content: "请选择人员"});
            return;
        }
        users.each(function() {
        	 userIds.push($(this).val());
        });
        
        $.ajaxSetup({traditional: true});
        $.getJSON("userBind.action", {zoneId: zoneId, userIds: userIds.join(",")}, function (result) {
            if (result) {
            	$.pnotify({
				    title: "绑定成功",
				    type: "success",
				    addclass: "stack-bottomright",
				    stack: {"dir1": "up", "dir2": "left", "firstpos1": 25, "firstpos2": 25}
				});
            	//重置
            	$("#zoneInput").removeAttr("data-zone-id").val("");
            	users.each(function() {
            		$(this).attr("checked",false);
            	});
            } else {
				$.pnotify({
				    title: "绑定失败",
				    type: "warn",
				    addclass: "stack-bottomright",
				    stack: {"dir1": "up", "dir2": "left", "firstpos1": 25, "firstpos2": 25}
				});
            }
        });
    });
});

function showDialog($zoneInput, zoneTree) {
    var $help = $("#zoneTreeDialog .help-block");
    art.dialog({
        id: "zoneTreeDialog",
        title: "选择区域",
        content: $("#zoneTreeDialog")[0],
        fixed: true,
        okValue: "确定",
        ok: function () {
            var nodes = zoneTree.getSelectedNodes();
            if (nodes.length == 0) {
                $help.empty().append("请选择区域");
                return false;
            }
            var node = nodes[0];
            $zoneInput.val(node.name);
            $zoneInput.attr("data-zone-id", node.id);
        },
        cancelValue: "取消",
        cancel: function () {
            $help.empty();
        }
    });
};
</script>
<#-- 页面JS脚本==============end================= -->
<#include "../_common/last-resources.ftl">
</body>
</html>

