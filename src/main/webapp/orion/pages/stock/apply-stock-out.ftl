<#--
申请出库

@author gaohui
@time  13-5-28
@check xiedeng 2013-6-6 14:39  svn:3899
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>出库申请 - 资产管理</title>
<#include "../_common/common-css.ftl">
    <#-- TODO 样式，是否需要提出来 -->
    <style type="text/css">
        .span1_5 {
            width: 80px;
        }
        .query_div_css {
        	display: none;
        	padding-top: 10px;
        	padding-bottom: 6px;
        	position: absolute;
        	background-color:#EEE;
        	width:400px;
        }
    </style>

</head>
<body>
<#assign currentTopPrivilege = "orion:stockManage">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "orion:stockManage:applyOut"></@subNavs>

<#include "/common/pages/message-tooltip.ftl">
<@messageTooltip/>

    <form action="doApplyStockOut.action" method="post">
        <div class="row">
            <div class="span12">
                <div class="well well-small">
                    <div class="row">
                        <div class="span5">

                            <div class="form-inline">
                                <label class="span1 span1_5"><em class="required">*</em>申请人</label>
                                <input type="text" name="outEvent.applicant" value="${outEvent.applicant!}"/>
                            </div>

                            <div class="form-inline m-t-10">
                                <label class="span1 span1_5"><em class="required">*</em>提用目的</label>
                                <textarea maxlength="50" name="outEvent.useFor">${outEvent.useFor!}</textarea>
                            </div>

                            <div class="form-inline m-t-10">
                                <label class="span1 span1_5">备注</label>
                                <textarea maxlength="200" name="outEvent.remark">${outEvent.remark!}</textarea>
                            </div>

                        </div>

                        <div class="span4">
                            <div class="form-inline">
                                <label class="span1 span1_5"><em class="required">*</em>出馆</label>
                                <lable class="radio inline"><input type="radio" name="outEvent.outBound" value="false"
                                                                   checked="checked"/>否
                                </lable>
                                <lable class="radio inline"><input type="radio" name="outEvent.outBound" value="true"/>是
                                </lable>
                            </div>

                            <div class="form-inline m-t-10">
                                <label class="span1 span1_5"><em class="required">*</em>类型</label>
                                <select class="input-medium" name="outEvent.eventType">
                                    <option value="2">科研修复</option>
                                    <option value="1">外出借展</option>
                                </select>
                            </div>

                            <div class="form-inline m-t-10">
                                <label class="span1 span1_5"><em class="required">*</em>开始时间</label>
                                <input id="start-time" class="input-medium" name="outEvent.beginDate" type="text"
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end-time\')}'})"
                                       value="${(outEvent.beginDate?string("yyyy-MM-dd"))!}"/>
                            </div>


                            <div class="form-inline m-t-10">
                                <label class="span1 span1_5"><em class="required">*</em>结束时间</label>
                                <input id="end-time" class="input-medium" name="outEvent.endDate" type="text"
                                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start-time\')}'})"
                                        value="${(outEvent.endDate?string("yyyy-MM-dd"))!}"/>
                            </div>
                        </div>

                        <div class="span2">
                            <button id="submitButton" class="btn btn-primary pull-right" type="submit">提交申请</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="span5">
                <h4 class="muted">待选文物</h4>

                <div id="query-relics-form" class="form-inline">
                    <label>总登记号</label>
                    <input type="text" style="width:180px;"/>
                    <span class="btn" style="float:right;">查询</span>
                    <span id="open_input" style="cursor:pointer;float:right;margin:5px;">高级选项</span>
                </div>
                <div id="query_div" class="form-inline query_div_css">
                	<div>
                        <label>藏品名称</label>
                        <input type="text" id="cp_name" style="width: 130px">
                        <label>分类号</label>
                        <input type="text" id="typeCode" style="width: 130px;">
                        <label>库房位次</label>
                        <select id="zoneId" style="width: 145px">
                            <option value="">全部</option>
                            <#if listZone??>
                                <#list listZone as zone>
                                    <option value="${zone.id}" <#if (zoneId!) == zone.id>selected</#if>>${zone.name}</option>
                                </#list>
                            </#if>
                        </select>
                        <label style="margin-left:12px;">级别</label>
                        <select style="width: 145px" id="levelId">
				        	<option value="-1">全部</option>
				        	<#if listLevel??>
                                <#list listLevel as level>
                                    <option value="${level.id}">${level.name}</option>
                                </#list>
                            </#if>
						</select>
						<label>文物质地</label>
						<select style="width: 145px" id="textureId">
							<option value="-1">全部</option>
							<#if listTexture??>
                                <#list listTexture as text>
                                    <option value="${text.id}">${text.name}</option>
                                </#list>
                            </#if>
						</select>
						<label style="margin-left:12px;">时代</label>
						<select style="width: 145px" id="eraId">
				        	<option value="-1">全部</option>
				            <#if listEra??>
                                <#list listEra as era>
                                    <option value="${era.id}">${era.name}</option>
                                </#list>
                            </#if>
						</select>
                	</div>
                </div>
            </div>
            <div class="span2"></div>
            <div class="span5">
                <h4 class="muted">已选文物</h4>

                <div class="form-inline">
                    <label>总数：</label>
                    <span id="relicCount">0</span>
                </div>
            </div>
        </div>

        <div class="row m-t-20">
            <div class="span5" style="max-height: 380px; overflow: auto;">
                <table id="availableRelicsTable" class="table table-bordered">
                    <thead>
                    <tr>
                        <th><input type="checkbox"/></th>
                        <th>总登记号</th>
                        <th>文物名称</th>
                        <th>库存状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="span2">
                <span id="chooseRelicButton" class="btn btn-block m-b-20"><i class="icon-arrow-right"></i></span>
                <span id="unChooseRelicButton" class="btn btn-block"><i class="icon-arrow-left"></i></span>
            </div>
            <div class="span5" style="max-height: 380px; overflow: auto;">
                <table id="chosenRelicsTable" class="table table-bordered">
                    <thead>
                    <tr>
                        <th><input type="checkbox"/></th>
                        <th>总登记号</th>
                        <th>文物名称</th>
                        <th>库存状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
</div>

<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">

<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/underscore/1.4.4/underscore-min.js"></script>

<script type="text/javascript">
    $(function () {
        // 查询文物
        (function () {
            var $queryRelicForm = $("#query-relics-form");
            $queryRelicForm.find(".btn").click(function () {
            	
                var totalCode = $queryRelicForm.find("input").val();
                $.get("queryRelicByTotalCode.action", {
                		totalCode: $.trim(totalCode),
                		name: $("#cp_name").val(),
                		zoneId: $("#zoneId").val(),
                		typeCode: $("#typeCode").val(),
                		eraId: $("#eraId").val(),
						levelId: $("#levelId").val(),
						textureId: $("#textureId").val()
                	}, function (result) {
                    if (result.relics.length <= 0) {
                        $("#availableRelicsTable tbody").empty().append('<tr><td colspan="4">查无结果</td></tr>');
                    } else {
                        var relicsDom = _.template($("#relics-template").html(), {relics: result.relics});
                        $("#availableRelicsTable tbody").empty().append(relicsDom);
                    }
                });
            });
        })();

        // 选择文物
        (function () {
            var $availableRelics = $('#availableRelicsTable');
            var $chosenRelicsRelics = $('#chosenRelicsTable');

            // 全选
            (function (tables) {
                $.each(tables, function (i, $table) {
                    $table.find('thead input').change(function () {
                        if ($(this).is(':checked')) {
                            $table.find('tr input[type="checkbox"]').attr('checked', 'checked');
                        } else {
                            $table.find('tr input[type="checkbox"]').removeAttr('checked');
                        }
                    });
                })
            })([$availableRelics, $chosenRelicsRelics]);

            $("#chooseRelicButton").click(function () {
                $availableRelics.find("tbody tr input:checked").each(function () {
                    var $tr = $(this).parents('tr');
                    var $checkbox = $tr.find('input[type="checkbox"]');
                    var relicId = $checkbox.val();

                    // 判断是否重复 @gaohui 2013-05-29
                    if($.inArray(relicId, getChosenRelicIds()) == -1){
                        $checkbox.after('<input type="hidden" name="relicIds" value="'+ relicId + '"/>');
                        $tr.remove().appendTo($chosenRelicsRelics.find('tbody'));
                    }
                });

                updateRelicCount();
            });

            $("#unChooseRelicButton").click(function () {
                $chosenRelicsRelics.find("tbody tr input:checked").each(function () {
                    // TODO 判断是否重复 @gaohui 2013-05-29
                    $(this).parents('tr').remove()
                            .appendTo($availableRelics.find('tbody'))
                            .find('input[type="hidden"]').remove();
                });

                updateRelicCount();
            });

        })();


        // 表单验证
        (function(){
            $('#submitButton').click(function(){
                if(!$.trim($('input[name="outEvent.applicant"]').val())){
                    alert('请填写申请人');
                    return false;
                }

                if(!$.trim($('textarea[name="outEvent.useFor"]').val())){
                    alert('请填写提用目的');
                    return false;
                }

                if(!$.trim($('input[name="outEvent.beginDate"]').val())){
                    alert('请填写开始时间');
                    return false;
                }

                if(!$.trim($('input[name="outEvent.endDate"]').val())){
                    alert('请填写结束时间');
                    return false;
                }

                if(getChosenRelicCount() < 1){
                    alert('请填选择文物');
                    return false;
                }
            });
        })();

        // 更新已选文物数量
        function updateRelicCount() {
            $("#relicCount").text(getChosenRelicCount());
        }

        // 已选文物数量
        function getChosenRelicCount() {
            return $('#chosenRelicsTable').find('tbody tr input[type="checkbox"]').length;
        }

        // 返回已选文物 id 集合
        function getChosenRelicIds(){
            var relicIds = [];
            $('#chosenRelicsTable').find('tbody tr input[type="checkbox"]').each(function(){
                relicIds.push($(this).val());
            });
            return relicIds;
        }
    });
    

$(function () {
	//时间选择器
    $("#open_input").click(function () {
    	if($(this).html() == "收起") {
    		$(this).html("高级选项");
    		$("#query_div").hide();
    	}else {
    		$(this).html("收起");
    		$("#query_div").show();
    	}
    });
});
</script>

<script id="relics-template" type="text/template-underscore">
    <% for(var i = 0; i< relics.length; i++){ %>
    <tr
    <% if(relics[i].state != 0){ %> class="error" <% } %>>
    <td>
        <% if(relics[i].state == 0){ %>
        <input type="checkbox" value="<%= relics[i].id %>"/>
        <% } %>
    </td>
    <td><%= relics[i].totalCode %></td>
    <td><%= relics[i].name %></td>
    <td>
        <% var state = relics[i].state; %>
        <% if(state == 0){ %>在库<% } %>
        <% if(state == 3){ %>出库申请中<% } %>
        <% if(state == 1){ %>待出库<% } %>
        <% if(state == 2){ %>出库<% } %>
    </td>
    </tr>
    <% } %>
</script>

<#include  "../_common/last-resources.ftl">
</body>
</html>
