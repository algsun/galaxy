<#--
监测指标定制

@author liuzhu
@date 2014-1-7
-->
<#assign title="监测指标定制">
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include "../_common/helper.ftl">

<#macro head>
<link rel="stylesheet" type="text/css" href="css/style_datauri.css"/>
<link rel="stylesheet" type="text/css" href="../assets/select2/3.3.1/select2.css"/>
<style type="text/css">
    .table thead {
        background-color: #ececec;
    }

    body {
        margin: 55px 210px;
    }
</style>
</#macro>

<#macro content>
<a href="" class="go-back" style="height: 24px;" title="返回">
    <i class="mw-icon-prev"></i>
    <span style="font-size: 18px;">站点地图</span>
</a>
    <#if customizeVOList?size!=0>
    <table class="table table-bordered" style="width: 940px;margin-top: 15px;">
        <tr>
            <td colspan="5" style="text-align: center;"><h4>${logicGroupPO.logicGroupName}</h4></td>
        </tr>
        <tr>
            <th>序号</th>
            <th>区域</th>
            <th>位置点</th>
            <th>监测指标</th>
            <th>定制说明</th>
            <th>操作</th>
        </tr>

        <#list customizeVOList as customize>
            <tr>
                <td>${customize_index+1}</td>
                <td>
                ${customize.zoneName!}
                </td>
                <td>
                ${customize.locationName!}
                </td>
                <td>
                ${customize.sensorName!}
                </td>
                <td>
                    ${customize.customizeRemark!}
                </td>
                <td>
                    <button class="btn btn-small btn-info editorCustomize" type="button" value="${customize.id}"
                            data-siteId="${customize.siteId}" data-sensorId=
                            "${customize.sensorId}" data-locationId="${customize.locationId}"
                            data-customizeRemark="${customize.customizeRemark!}"> 编辑
                    </button>
                    <button class="btn btn-small btn-danger deleteCustomize" type="button"
                            data-siteId="${customize.siteId}" value="${customize.id}">删除
                    </button>
                </td>
            </tr>
        </#list>

    </table>
    <#else>
    <h4>暂无数据，请选添加定制</h4>
    </#if>

<button class="btn btn-primary" type="button" id="addCustomize">添加</button>
<div id="myModal" class="modal hide fade" tabindex="-1" style="margin-top: 100px;" role="dialog"
     aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">请设置参数</h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" id="customizeFrom" method="post">
            <input type="hidden" id="siteId" name="siteId" value="${siteId}">
            <input type="hidden" id="customizeId" name="customizeId">
            <input type="hidden" id="flag">

            <div class="control-group">
                <label class="control-label">监测指标</label>

                <div class="controls">
                    <select id="sensorId" name="sensorId">
                        <#if logicGroupPO.sensorInfoVOList?? && logicGroupPO.sensorInfoVOList?size!=0>
                            <option value="0">请选择</option>
                            <#list logicGroupPO.sensorInfoVOList as sensor>
                                <option value="${sensor.sensorPhysicalid}">${sensor.cnName}</option>
                            </#list>
                        <#else>
                            <option>暂无数据</option>
                        </#if>

                    </select>
                    <span id="sensorIdSpan" style="color: red"></span>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">位置点 </label>

                <div class="controls">
                    <select id="zoneLocation" name="locationId" style="width:220px" class="select2-offscreen" tabindex="-1">

                    </select>
                    <span id="zoneLocationSpan" style="color: red"></span>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">定制说明</label>

                <div class="controls">
                    <textarea rows="3" name="customizeRemark" id="customizeRemark" maxlength="20"></textarea>
                    <span style="color:#969696 ">最多输入20个字符</span>
                </div>
            </div>

            <div class="control-group">
                <div class="controls">
                    <button type="button" class="btn" id="saveBtn">保存</button>
                    <button type="button" class="btn" id="returnBtn">返回</button>
                </div>
            </div>
        </form>
    </div>
</div>
<p style="position:absolute; bottom:0px;color: #969696;">温馨提示：每个站点只能定制3个检测指标</p>
</#macro>
<#macro script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript">
    $("#addCustomize").click(function () {
        var siteId = $("#siteId").val();
        $.ajax({
            type: "get",
            url: "customizeCount/" + siteId,
            async: false,
            success: function (result) {
                if (result) {
                    art.dialog({
                        id: "delete",
                        fixed: true,
                        title: "温馨提示",
                        content: "该站点定制信息已达到上限，请先删除再添加！",
                        okValue: "确定",
                        ok: function () {
                            $("#myModal").modal("hide");
                        },
                        cancelValue: "取消",
                        cancel: function () {
                            $("#myModal").modal("hide");
                        }
                    });
                    return;
                } else {
                    $("#myModal").modal("show");
                    $("#flag").attr("value", "add");
                }
            }
        });

    });

    $("#e1").select2();
    $("#zoneLocation").select2({formatNoMatches: function () {
        return '暂无数据';
    }});
    $("#sensorId").change(function () {
        $("#zoneLocation optgroup").remove();
        $("#zoneLocation option").remove();
        var sensorId = $("#sensorId").val();
        var siteId = $("#siteId").attr("value");
        var url = 'findZoneLocationBySiteSensorId.json';
        var param = {'siteId': siteId, 'sensorId': sensorId};
        var jsonStr = "";
        $.get(url, param, function (result) {
            jsonStr = eval(result);
            for (var i = 0; i < jsonStr.length; i++) {
                var optionStr = '<optgroup label="' + jsonStr[i].zoneName + '">'
                for (var j = 0; j < jsonStr[i].locationList.length; j++) {
                    var location = jsonStr[i].locationList[j];
                    optionStr += '<option value="' + location.id + '">' + location.locationName + '</option>';
                }
                optionStr += "</optgroup>";
                $("#zoneLocation").append(optionStr);
            }
            $("#zoneLocation").select2("val", $("#zoneLocation option").first());
        });
    });

    $("#saveBtn").click(function () {
        var flag = $("#flag").attr("value");
        if ($("#sensorId").val() == "0") {
            $("#sensorIdSpan").text("请选择监测指标");
            return;
        }
        if ($("#zoneLocation").val() == null) {
            $("#zoneLocationSpan").text("请选择位置点");
            return;
        }
        $("#sensorIdSpan").text("");
        $("#zoneLocationSpan").text("");
        var customizeId = $("#customizeId").attr("value");
        var siteId = $("#siteId").val();
        var deviceId = $("#zoneLocation").val();
        var sensorId = $("#sensorId").val();
        var customizeRemark = $("#customizeRemark").val();

        if (flag == "add") {
            $.get("verifyCustomize/" + siteId + "/" + deviceId + "/" + sensorId, function (result) {
                if (result) {
                    art.dialog({
                        id: "delete",
                        fixed: true,
                        title: "温馨提示",
                        content: "该数据已定制，请重新选择",
                        okValue: "确定",
                        ok: function () {
                        },
                        cancelValue: "取消",
                        cancel: function () {
                        }
                    });
                } else {
                    $("#customizeFrom").attr("action", "saveCustomize").submit();
                }
            });
        } else {
            $("#customizeFrom").attr("action", "updateCustomize").submit();
        }
    });

    $("#returnBtn").click(function () {
        $("#myModal").modal("hide");
    });
    $(".deleteCustomize").click(function () {
        var id = this.value;
        var siteId = this.dataset.siteid;
        art.dialog({
            id: "delete",
            fixed: true,
            title: "删除确认",
            content: "确定要删除吗？",
            okValue: "确定",
            ok: function () {
                var deleteUrl = "deleteCustomize/" + id + "/" + siteId;
                window.location.href = deleteUrl;
            },
            cancelValue: "取消",
            cancel: function () {
            }
        });
    });
    $(".editorCustomize").click(function () {
        $("#flag").attr("value", "editor");
        var customizeId = this.value;
        var siteId = this.dataset.siteid;
        var sensorId = this.dataset.sensorid;
        var locationId = this.dataset.locationId;
        var customizeRemark = this.dataset.customizeremark;
        $("#customizeId").attr("value", customizeId);
        $("#myModal").modal("show");
        $("#sensorId").val(sensorId);
        $("#customizeRemark").text(customizeRemark);
        $("#zoneLocation optgroup").remove();
        $("#zoneLocation option").remove();
        var url = 'findZoneLocationBySiteSensorId.json';
        var param = {'siteId': siteId, 'sensorId': sensorId};
        var jsonStr = "";
        $.get(url, param, function (result) {
            jsonStr = eval(result);
            for (var i = 0; i < jsonStr.length; i++) {
                var optionStr = '<optgroup label="' + jsonStr[i].zoneName + '">'
                for (var j = 0; j < jsonStr[i].locationList.length; j++) {
                    var location = jsonStr[i].locationList[j];
                    optionStr += '<option value="' + location.id + '">' + location.locationName + '</option>';
                }
                optionStr += "</optgroup>";
                $("#zoneLocation").append(optionStr);
            }
            $("#zoneLocation").select2("val", locationId);
        });
    })
</script>

</#macro>


