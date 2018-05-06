/**
 * 
 */

$(function() {
	var $keywordNameInputDiv = $("#keyword-name");
	var $keywordNameInput = $keywordNameInputDiv.find("input");
	$("#btnAdd").on('click',
					function() {
						var dialog = art
								.dialog({
									title : "添加关键字",
									content : $("#keyword-name")[0],
									lock : true,
									fixed : true,
									okValue : "保存",
									cancelValue : "取消",
									ok : function() {
										var keywordName = $.trim($keywordNameInput
												.val());
										
										if (keywordName == "") {
											$keywordNameInput.focus();
											$keywordNameInputDiv.find(
													".help-block").empty()
													.append("输入不能为空");
											return false;
										}

										(function() {
											$
													.post(
															"addKeyword.action",
															{
																keywordName : keywordName
															},
															function(result) {
																if (result.success) {
																	//setTimeout(show, 1000);
																	//var show=function(){
																	var $newKeyword;
																	var $list=$("#keywordList");
																	//alert(result.id+"------"+result.keywordName);
																	var $children=$("#keywordList").children();
																	//alert($children);
																	if($children.length===0){
																		$list.append($("#data-keyword-hide").children().clone());
																		$newKeyword=$($("#keywordList").children());	
																	}else{
																		$list.append($("#data-keyword-hide").children().clone());
																	    $newKeyword=$list.find("div[data-keyword]").last();																	    
																	}
																	$newKeyword.find("#id").text(result.id);
																	$newKeyword.find("#keywordName").text(result.keywordName);
																	$newKeyword.find("button[data-edit-btn]");
																	var divMessage="<div class='alert alert-success'> "+
																	"<a class='close' data-dismiss='alert'>×</a>" +
															     	result.message+	
																    "</div>";
															        $("#alert").empty().append(divMessage);
																	dialog.close();
																	//}();
																	//window.location.href = "queryKeywords.action";
																} else {
																	$keywordNameInput
																			.focus();
																	$keywordNameInputDiv
																			.find(
																					".help-block")
																			.empty()
																			.append(
																					result.message);
																}
															});

										})();
										return false;
									},
									cancel : function() {
									}
								});
						$keywordNameInput.val("");
						$keywordNameInputDiv.find(".help-block").empty();
						$keywordNameInput.focus();
					});

	$(document).on(
			"click", "button[data-edit-btn]",
			function() {
				$btnEdit = $(this);
				$id = $btnEdit.parent().prev().children("#id");
				$name = $btnEdit.parent().prev().children("#keywordName");

				$keywordNameInput.val($.trim($name.text()));

				var dialog = art.dialog({
					title : "编辑关键字",
					content : $("#keyword-name")[0],
					lock : true,
					fixed : true,
					okValue : "保存",
					cancelValue : "取消",
					ok : function() {
						var keywordName = $.trim($keywordNameInput.val());
						
						if (keywordName == "") {
							$keywordNameInput.focus();
							$keywordNameInputDiv.find(".help-block").empty()
									.append("输入不能为空");
							return false;
						}

						(function() {
							$.post("editKeyword.action", {
								id : $.trim($id.text()),
								keywordName : keywordName
							}, function(result) {
								if (result.success) {
									$name.text(result.keywordName);
									var divMessage="<div class='alert alert-success'> "+
									"<a class='close' data-dismiss='alert'>×</a>" +
							     	result.message+	
								    "</div>";
							        $("#alert").empty().append(divMessage);
									dialog.close();
								} else {
									$keywordNameInput.focus();
									$keywordNameInputDiv.find(".help-block")
											.empty().append(result.message);
								}
							});
						})();
						return false;
					},
					cancel : function() {
					}
				});
				$keywordNameInputDiv.find(".help-block").empty();
				$keywordNameInput.focus();
			});
	$(document).on("click","button[data-delete-btn]",function() {
		$btnDelete = $(this);
		$id = $btnDelete.parent().prev().children("#id");
		$name = $btnDelete.parent().prev().children("#keywordName");
		var dialog = art.dialog({
			title : '确认提示',
			content : '您确定执行该操作吗?',
			lock : true,
			fixed : true,
			button: [
			         {
			             value: '确定',
			             callback: function () {
			        		$.post("deleteKeyword.action", {
								id : $.trim($id.text()),
								keywordName : $.trim($name.text())
							}, function(result) {
								if (result.success) {
									//window.location.href = "queryKeywords.action";
									$btnDelete.parent().parent().remove();
									var divMessage="<div class='alert alert-success'> "+
									"<a class='close' data-dismiss='alert'>×</a>" +
									result.message+	
									"</div>";
									$("#alert").empty().append(divMessage);
								}else{
									var divMessage="<div class='alert alert-success'> "+
											"<a class='close' data-dismiss='alert'>×</a>" +
										result.message+	
										"</div>";
									$("#alert").empty().append(divMessage);
								}
							}); 
			             },
			             focus: false
			         },
			         {
			             value: '取消',
			             callback: function () {
			                
			          	  },
			         	 focus: true
			         }
			     ]
		});
	});
	
});