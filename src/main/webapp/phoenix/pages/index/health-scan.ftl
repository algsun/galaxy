<#--
健康指数扫描
@author duan.qixin
@date 2013-07-25

@check @wanggeng 2013年7月29日 #4705
-->

<#assign title="健康指数扫描 - 数据分析">
<#include  "../_common/helper.ftl">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:index:health">
<#macro head>
<style type="text/css">
.hs_head{
	line-height: 50px;
	font-size: 160%;
}

/* 总分状态: 优良 */
#hs_total{
    color: #61be30;
}

/* 总分：一般 */
#hs_total.waring{
    color: #febe39;
}

/* 总分：不及格 */
#hs_total.error{
    color: #f34443;
}

.hs_context i{
	margin-top: 4px;
}

.forLook{
	display: none;
	margin-top: 10px;
	margin-right: 5px;
}

.hs_title{
	display: none;
	font-size: 120%;
}

.hs_desData{
	display: none;
	margin-left: 20px;
	margin-right: 20px;
}

.hs_context{
	background-color:#EEE;
	font-size: 130%;
	padding-left: 12px;
	line-height: 41px;
}

.hs_desc{
	display: none;
	margin-left: 40px;
}

.hs_c_info{
	font-weight:bold;
}
</style>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="page-header title">
            <h3>健康指数扫描</h3>
        </div>
    </div>
</div>

<div id="hs_core">
	<div class="well hs_head yahei-font">
		<div class="f-l m-r-20" style="float:left; margin-right:20px;">
			<span id="hs_total" class="bold success" style="font-size:260%;">100</span>分
		</div>
		<span class="blue">${groupName!}</span>
		<#--<span>健康指数扫描</span>-->
		<button id="hs_scan" class="btn btn-success pull-right m-10">开始体检</button>
	</div>
	<div>
		<div class="hs_title">
			共检查<span class="red">${itemCount!}</span>项。
			<span>其中<span id="hs_e_count" class="red">0</span>项存在问题。</span>
		</div>
		<!--=====================体检项目=====================-->
		<#if (items?size>0)>
			<#list items as item>
			<div class="m-t-10">
				<div class="hs_context">
					<i></i>
					<span>${item.subsystemName!}</span>
					<span><img style="display:none;" src="images/loading.gif" alt="正在加载..."/></span>
					<span class="btn btn-mini pull-right forLook">查看</span>
				</div>
				<div class="hs_desc">
				<#if (item.checkItems?size>0)>
					<#list item.checkItems as ci>
						<div>有<span id="${ci.checkUrl}" class="hs_c_info red"></span>个${ci.name}异常</div>
		            </#list>
				</#if>
				</div>
				<div class="hs_desData">
					无异常信息
				</div>
			</div>
            </#list>
		</#if>
	</div>
</div>
</#macro>

<#macro script>
<script type="text/javascript">
// 总分数
var total = 100;
// 异常统计项数目
var errorItemCount = 0;
$(document).ready(function () {
	//体检
	$("#hs_scan").click(function() {
        // 初始化状态
        total = 100;
        errorItemCount = 0;
        $("#hs_total").removeClass('waring error');

		if($(this).text() == "开始体检") {
			$(".hs_title").show();
			$(this).text("重新体检");
		}
		//查询连接，并返回数据
		$(".hs_c_info").each(function() {
			var checkFlag = $(this).attr("id");
			//打开加载图标
			$("#hs_core img").show();
			//AJAX访问数据
			$.ajax({
				type: 'POST',
				url: "index/count.json?checkFlag=" + checkFlag,
				dataType: "json",
				success: function(o) {
					comTotal(o[0]);//计算总分
					//显示健康扫描数据
					var cDom = $("#" + checkFlag).html(o[1]).parent().parent();
					//显示健康情况图标并关闭加载图标
					showImg(o[1], cDom);
				}
			});
		});
	});
	
	//点击查看按钮
	$(".forLook").click(function() {
		//扫描异常说明对象
		var fl = $(this).parent().next();
		if($(this).text() === "查看") {
			//存在异常信息时触发
			if($(fl.find(".hs_c_info")[0]).html() != "0") {
				if($(this).attr("id") != "looked") {
					//显示加载图标
					$(this).parent().find("img").show();
					//清空详细描述信息
					var tDom = $(this).parent().parent();
					var descDom = tDom.find(".hs_desData");
					$(descDom[0]).html("");
					
					//Ajax访问异常数据
					errorData(tDom, descDom);
					$(this).attr("id","looked");
				}
			}
			
			$(this).html("关闭");
			fl.next().slideDown();
		}else {
			$(this).html("查看");
			fl.next().slideUp();
		}
	});
});

//Ajax访问异常详细数据，并对数据进行处理
function errorData(tm, dm) {
	var eData = tm.find(".hs_c_info");
	if(eData.length > 0) {//是否有异常信息
		eData.each(function() {
			var checkFlag = $(this).attr("id");
			
			$.ajax({
				type: 'POST',
				url: "index/item.json?checkFlag=" + checkFlag,
				dataType: "json",
				success: function(arr) {
					//关闭加载图标
					tm.find("img").hide();
					//对异常详细信息进行处理
					if(arr && arr.length > 0) {
						$(dm[0]).append(createTable(arr));
					}
				}
			});
		});
	}
}

//按钮开关函数
function showBtn(b, o) {
	//b=true表示显示按钮
	if(b) {
	}else {
	}
}

//计算总分
function comTotal(c) {
	total -= c;
	$("#hs_total").html(total);
	if(c > 0) {
		$("#hs_e_count").text(errorItemCount + 1);
		errorItemCount = errorItemCount + 1;
	}
	if(total >= 60 && total < 80) {
		$("#hs_total").removeClass('waring error').addClass('waring');
	}else if(total < 60) {
		$("#hs_total").removeClass('waring error').addClass('error');
	}
}

//生成表HTML数据
function createTable(arr) {
	var html = "<table class='table table-bordered table-center'><tr>";
			 
	for(var i = 0; i < arr[0].length; i++) {
		html += "<th>" + arr[0][i] + "</th>";
	}
	html += "</tr><tr>";
	for(var i = 1; i < arr.length; i++) {
		for(var j = 0; j < arr[i].length; j++) {
			html += "<td>" + arr[i][j] + "</td>";
		}
		html += "</tr><tr>";
	}
	return html + "</tr></table>";
}

//显示健康情况图标
function showImg(i, cDom) {
	var tDom = cDom.prev();
	tDom.find("img").hide();
	tDom.find(".forLook").show();
	if(i === 0) {//健康
		tDom.find("i").attr("class", "icon-ok-sign");
	}else {//不健康
		cDom.show();
		tDom.find("i").attr("class", "icon-exclamation-sign");
	}
}
</script>
</#macro>