<#--
外展管理，新增车辆信息

@author wang.geng
@date 2013-09-27
-->
<#assign title="新建车辆 - 外展管理">
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
<style type="text/css">
    .table thead {
        background-color: #ececec;
    }
</style>
</#macro>
<#macro content>
  <input type="hidden" name="exhibitionId" id="exhibitionId" value="${exhibitionId}">
<div class="row">
    <div class="span12">
        <fieldset>
            <legend>
                <a class="go-back" href="exhibition/carConfig/${exhibitionId}" title="返回">
                    <i class="mw-icon-prev"></i>新建车辆
                </a>
            </legend>
        </fieldset>
    </div>
</div>
<form id="queryDeviceForm" class="form-horizontal" action="exhibition/addCar/${exhibitionId}" method="post">
    <div class="tabbable m-t-30">
        <ul class="nav nav-tabs">
            <li class="active"><a href="#tab1" data-toggle="tab"> 车辆基本信息配置</a></li>
            <li><a href="#tab2" data-toggle="tab">摄像机设备配置</a></li>
            <li><a href="#tab3" data-toggle="tab">位置点配置</a></li>
            <div class="m-r-30 f-r">
                <button class="addCar-btn row-edit btn btn-primary btn-small" type="submit" title="保存"><i
                        class="icon-pencil icon-white"></i>保存
                </button>
            </div>
        </ul>


        <div class="tab-content">
            <div class="f-r">

            </div>
            <div class="tab-pane active" id="tab1">

                <div class="control-group">
                    <label class="control-label" for="">
                        <em class="required">*</em> 车牌号:
                    </label>

                    <div class="controls">
                        <input type="text" name="plateNumber" id="plateNumber">
                        <span id="name-input-help" class="help-inline red"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="">
                        <em class="required">*</em> 责任人:
                    </label>

                    <div class="controls">
                        <input type="text" name="director" id="director">
                        <span id="name-input-help" class="help-inline red"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="">
                        <em class="required">*</em> 责任人电话:
                    </label>

                    <div class="controls">
                        <input type="text" name="directorPhone" id="directorPhone">
                        <span id="name-input-help" class="help-inline red"></span>
                    </div>
                </div>
            </div>


            <div class="tab-pane" id="tab2">

                <div class="row">
                    <div class="span5">
                        <h4 class="muted">待选摄像机设备</h4>

                        <div id="query-dvs-form" class="form-inline">
                        <#--<label>摄像机名称</label>-->
                        <#--<input type="text" style="width:180px;"/>-->
                            <label>区域选择</label>

                            <select name="zoneId">
                                <option value="" selected="selected">全部</option>
                                <#list zoneList as zone>
                                    <option value="${zone.id}"
                                        <#if zone.id==zoneId!"">
                                            selected="selected"
                                        </#if>>
                                    ${zone.name}</option>
                                </#list>
                            </select>
                            <span class="btn" style="float:right;">查询</span>
                        </div>
                    </div>
                    <div class="span2"></div>
                    <div class="span5">
                        <h4 class="muted">已选摄像机设备</h4>

                        <div class="form-inline">
                            <label>总数：</label>
                            <span id="dvCount">0</span>
                        </div>
                    </div>
                </div>
                <div class="row m-t-20">
                    <div class="span5" style="max-height: 380px; overflow: auto;">
                        <table id="availableDvTable" class="table table-bordered">
                            <thead>
                            <tr>
                                <th><input type="checkbox"/></th>
                                <th>摄像机名称</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <div class="span2">
                        <span id="chooseDvButton" class="btn btn-block m-b-20"><i class="icon-arrow-right"></i></span>
                        <span id="unChooseDvButton" class="btn btn-block"><i class="icon-arrow-left"></i></span>
                    </div>
                    <div class="span5" style="max-height: 380px; overflow: auto;">
                        <table id="chosenDvTable" class="table table-bordered">
                            <thead>
                            <tr>
                                <th><input type="checkbox"/></th>
                                <th>摄像机名称</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="tab-pane" id="tab3">
                <div class="row">
                    <div class="span5">
                        <h4 class="muted">待选位置点</h4>

                        <div id="query-nodes-form" class="form-inline">
                            <label>位置点名称:</label>
                            <input type="text" style="width:180px;"/>
                            <span class="btn" style="float:right;">查询</span>
                        </div>
                    </div>
                    <div class="span2"></div>
                    <div class="span5">
                        <h4 class="muted">已选位置点</h4>

                        <div class="form-inline">
                            <label>总数：</label>
                            <span id="nodeCount">0</span>
                        </div>
                    </div>
                </div>
                <div class="row m-t-20">
                    <div class="span5" style="max-height: 380px; overflow: auto;">
                        <table id="availableNodesTable" class="table table-bordered">
                            <thead>
                            <tr>
                                <th><input type="checkbox"/></th>
                                <th>位置点名称</th>
                                <th>绑定设备Id</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <div class="span2">
                        <span id="chooseNodeButton" class="btn btn-block m-b-20"><i class="icon-arrow-right"></i></span>
                        <span id="unChooseNodeButton" class="btn btn-block"><i class="icon-arrow-left"></i></span>
                    </div>
                    <div class="span5" style="max-height: 380px; overflow: auto;">
                        <table id="chosenNodesTable" class="table table-bordered">
                            <thead>
                            <tr>
                                <th><input type="checkbox"/></th>
                                <th>位置点名称</th>
                                <th>绑定设备Id</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>


</form>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/underscore/1.4.4/underscore-min.js"></script>
<script type="text/javascript" src="../common/js/form-check.js"></script>
<script type="text/javascript">
$(function () {

    //JS验证
    (function () {
        $(document).on('click', '.addCar-btn', function () {

            var flag1 = true;
            var flag2 = true;
            var flag3 = true;

            var $plateNumber = $("#plateNumber");
            var $director = $("#director");
            var $directorPhone = $("#directorPhone");

            var reg = new RegExp("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$");

            if (!$.trim($plateNumber.val())) {
                App.helpError($directorPhone.next(), "车牌号不能为空");
                flag1 = false;
            } else if ($plateNumber.val().length > 10) {
                App.helpError($plateNumber.next(), "车牌照号不能超过10位");
                flag1 = false;
            } else if (!reg.test($plateNumber.val())) {
                App.helpError($plateNumber.next(), "不是正确的车牌照号");
                flag1 = false;
            } else {
                App.helpOk($plateNumber.next(), '');
                flag1 = true;
            }

            if (!$.trim($director.val())) {
                App.helpError($director.next(), "责任人不能为空");
                flag2 = false;
            } else if ($director.val().length > 20) {
                App.helpError($director.next(), "责任人名字不能超过20位");
                flag2 = false;
            } else {
                App.helpOk($director.next(), '');
                flag2 = true;
            }

            var reg = new RegExp("^[0-9]*$");
            if (!$.trim($directorPhone.val())) {
                App.helpError($directorPhone.next(), "责任人联系方式不能为空");
                flag3 = false;
            } else if (!reg.test($directorPhone.val())) {
                App.helpError($directorPhone.next(), "责任人联系方式必须为数字");
                flag3 = false;
            } else if ($directorPhone.val().length > 11) {
                App.helpError($directorPhone.next(), "电话号码不能超过11位");
                flag3 = false;
            } else {
                App.helpOk($directorPhone.next(), '');
                flag3 = true;
            }

            return flag1 && flag2 && flag3;
        });
    })();

    (function () {
        var $querydvsform = $("#query-dvs-form");
        $querydvsform.find(".btn").click(function () {
            var zoneId = $querydvsform.find("select").val();
            $.get("queryDVPlaces.json?zoneId=" + zoneId, function (result) {
                if (result.length <= 0) {
                    $("#availableDvTable tbody").empty().append('<tr><td colspan="4">查无结果</td></tr>');
                } else {
                    var dvDom = _.template($("#dvs-template").html(), {dv: result});
                    $("#availableDvTable tbody").empty().append(dvDom);
                }
            });
        });
        var $querynodesform = $("#query-nodes-form");
        $querynodesform.find(".btn").click(function () {
            var locationName = $querynodesform.find("input").val();
            var exhibitionId = $("#exhibitionId").val();
            $.get("queryNodes.json?locationName=" + locationName+"&exhibitionId="+exhibitionId, function (result) {
                if (result.length <= 0) {
                    $("#availableNodesTable tbody").empty().append('<tr><td colspan="4">查无结果</td></tr>');
                } else {
                    var nodeDom = "";
                    for (var i = 0; i < result.length; i++) {
                        nodeDom += "<tr><td><input type='checkbox'  value='" + result[i].id + "'/></td>";
                        var nodeId = result[i].nodeId == null ? "" : result[i].nodeId;
                        nodeDom += " <td>" + result[i].locationName + "</td><td>" + nodeId + "</td></tr>";
//                    var nodeDom = _.template($("#nodes-template").html(), {locations: result});
                        $("#availableNodesTable tbody").empty().append(nodeDom);
                    }
                }
            });
        });
    })();

    // 选择摄像机设备
    (function () {
        var $availableDvTable = $('#availableDvTable');
        var $chosenDvTable = $('#chosenDvTable');

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
        })([$availableDvTable, $chosenDvTable]);

        $("#chooseDvButton").click(function () {
            $availableDvTable.find("tbody tr input:checked").each(function () {
                var $tr = $(this).parents('tr');
                var $checkbox = $tr.find('input[type="checkbox"]');
                var dvId = $checkbox.val();

                // 判断是否重复 @gaohui 2013-05-29
                if ($.inArray(dvId, getChosenDvIds()) == -1) {
                    $checkbox.after('<input type="hidden" name="dvIds" value="' + dvId + '"/>');
                    $tr.remove().appendTo($chosenDvTable.find('tbody'));
                }
            });

            updateDvCount();
        });

        $("#unChooseDvButton").click(function () {
            $chosenDvTable.find("tbody tr input:checked").each(function () {

                var $tr = $(this).parents('tr');
                var $checkbox = $tr.find('input[type="checkbox"]');
                var dvId = $checkbox.val();

                $checkbox.removeAttr('checked');

                // 判断是否重复 @gaohui 2013-05-29
                if ($.inArray(dvId, getUnChosenDvIds()) == -1) {
                    $(this).parents('tr').remove()
                            .appendTo($availableDvTable.find('tbody'))
                            .find('input[type="hidden"]').remove();
                } else {
                    $(this).parents('tr').remove();
                }
            });

            updateDvCount();
        });

    })();
    // 更新已选摄像机数量
    function updateDvCount() {
        $("#dvCount").text(getChosenDvCount());
    }

    // 已选摄像机数量
    function getChosenDvCount() {
        return $('#chosenDvTable').find('tbody tr input[type="checkbox"]').length;
    }

    // 返回已选摄像机 id 集合
    function getChosenDvIds() {
        var dvIds = [];
        $('#chosenDvTable').find('tbody tr input[type="checkbox"]').each(function () {
            dvIds.push($(this).val());
        });
        return dvIds;
    }

    // 返回未选摄像机 id 集合
    function getUnChosenDvIds() {
        var dvIds = [];
        $('#availableDvTable').find('tbody tr input[type="checkbox"]').each(function () {
            dvIds.push($(this).val());
        });
        return dvIds;
    }

    // 选择传感机设备
    (function () {
        var $availableNodesTable = $('#availableNodesTable');
        var $chosenNodesTable = $('#chosenNodesTable');

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
        })([$availableNodesTable, $chosenNodesTable]);

        $("#chooseNodeButton").click(function () {
            $availableNodesTable.find("tbody tr input:checked").each(function () {
                var $tr = $(this).parents('tr');
                var $checkbox = $tr.find('input[type="checkbox"]');
                var text=$tr.find("td").last()[0].innerText;
                if (!text||text.trim()=="请绑定设备") {
                    $tr.find("td").last().html("<p style='color: red'>请绑定设备</p>");
                    return;
                }

                var nodeId = $checkbox.val();

                // 判断是否重复 @gaohui 2013-05-29
                if ($.inArray(nodeId, getChosenNodeIds()) == -1) {
                    $checkbox.after('<input type="hidden" name="locationIds" value="' + nodeId + '"/>');
                    $tr.remove().appendTo($chosenNodesTable.find('tbody'));
                }
            });

            updateNodeCount();
        });

        $("#unChooseNodeButton").click(function () {
            $chosenNodesTable.find("tbody tr input:checked").each(function () {

                var $tr = $(this).parents('tr');
                var $checkbox = $tr.find('input[type="checkbox"]');
                var nodeId = $checkbox.val();

                $checkbox.removeAttr('checked');

                // TODO 判断是否重复 @gaohui 2013-05-29
                if ($.inArray(nodeId, getUnChosenNodeIds()) == -1) {
                    $(this).parents('tr').remove()
                            .appendTo($availableNodesTable.find('tbody'))
                            .find('input[type="hidden"]').remove();
                } else {
                    $(this).parents('tr').remove();
                }
            });

            updateNodeCount();
        });

    })();
    // 更新已选传感设备数量
    function updateNodeCount() {
        $("#nodeCount").text(getChosenNodeCount());
    }

    // 已选传感设备数量
    function getChosenNodeCount() {
        return $('#chosenNodesTable').find('tbody tr input[type="checkbox"]').length;
    }

    // 返回已选传感设备 id 集合
    function getChosenNodeIds() {
        var nodeIds = [];
        $('#chosenNodesTable').find('tbody tr input[type="checkbox"]').each(function () {
            nodeIds.push($(this).val());
        });
        return nodeIds;
    }

    // 返回未选传感设备 id 集合
    function getUnChosenNodeIds() {
        var nodeIds = [];
        $('#availableNodesTable').find('tbody tr input[type="checkbox"]').each(function () {
            nodeIds.push($(this).val());
        });
        return nodeIds;
    }
});
</script>
<script id="dvs-template" type="text/template-underscore">
    <% for(var i = 0; i< dv.length; i++){ %>
    <tr>
        <td>
            <input type="checkbox" value="<%= dv[i].id %>"/>
        </td>
        <td><%= dv[i].placeName %></td>
    </tr>
    <% } %>
</script>
<#--<script id="nodes-template" type="text/template-underscore">-->
<#--<% for(var i = 0; i< locations.length; i++){ %>-->
<#--<tr>-->
<#--<td>-->
<#--<input type="checkbox" value="<%= location[i].id %>"/>-->
<#--</td>-->
<#--<td><%= location[i].locationName %></td>-->
<#--<td><%= location[i].nodeId %></td>-->
<#--</tr>-->
<#--<% } %>-->
<#--</script>-->
</#macro>