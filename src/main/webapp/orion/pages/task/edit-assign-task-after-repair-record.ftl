<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>修复登记 - 资产管理</title>
<#include "../_common/common-css.ftl">
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
</head>
<body>
<#--<#assign currentTopPrivilege = "orion:stockManage">-->
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
    <form class="form-horizontal" id="handleTask_form" action="handleTask/do_assign_task_after_update" method="post">
        <input name="repairRecord.id" id="repairRecord" type="hidden" value="${repairRecord.id!}"/>
        <input name="repairRecord.siteId" id="repairRecord.siteId" type="hidden" value="${repairRecord.siteId!}"/>
        <input name="repairRecord.relic.id" id="repairRecord.relicId" type="hidden" value="${repairRecord.relic.id!}"/>
        <input name="repairRecord.state" id="repairRecord.state" type="hidden" value="${repairRecord.state!}"/>
        <input type="hidden" name="repairRecord.identifier" value="${repairRecord.identifier?c}"/>
        <input name="repairRecord.repairReason.Id" id="repairRecord.repairReason.Id" type="hidden" value="${repairRecord.repairReason.id!}"/>
        <input name="repairRecord.extractDate" id="repairRecord.extractDate" type="hidden" value="${repairRecord.extractDate?string("yyyy-MM-dd")}"/>
        <input name="repairRecord.returnDate" id="repairRecord.returnDate" type="hidden" value="${repairRecord.returnDate?string("yyyy-MM-dd")}"/>

        <input id="secondaryUserIdValue"  value="${repairRecord.secondaryUserId}" type="hidden"/>
        <fieldset>
            <legend>
                修复登记
            </legend>
            <div class="control-group">
                <label class="control-label">文物名称</label>

                <div class="controls">
                    <input type="text" readonly="readonly" value="${relic.name}"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">总登记号</label>

                <div class="controls">
                    <input type="text" readonly="readonly" value="${relic.totalCode!}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">时代</label>

                <div class="controls">
                    <input type="text" readonly="readonly" value="${relic.era.name}"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">级别</label>

                <div class="controls">
                    <input type="text" readonly="readonly" value="${relic.level.name}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">质地</label>

                <div class="controls">
                    <input type="text" readonly="readonly" value="${relic.texture.name}"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">收藏单位</label>

                <div class="controls">

                    <input type="text" readonly="readonly"
                    <#if institution??>
                           value="${institution.name}"
                    <#else>
                           value=""
                    </#if>/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">来源</label>

                <div class="controls">
                    <input type="text" readonly="readonly" value="${(relic.relicPropertyMap.source.propertyValue)!}"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">修复原因</label>

                <div class="controls">
                    <input type="text" value="${repairRecord.repairReason.reason}" readonly="readonly"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">提取时间</label>

                <div class="controls">
                    <input id="extractDate" type="text" value="${repairRecord.extractDate?string("yyyy-MM-dd")}"
                           readonly="readonly"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">归还时间</label>

                <div class="controls">
                    <input id="returnDate" type="text" value="<#if repairRecord.returnDate??>${repairRecord.returnDate?string("yyyy-MM-dd")}</#if>"
                           readonly="readonly"/>
                </div>
            </div>


            <div class="control-group">
                <label class="control-label">修复内容</label>

                <div class="controls">
                    <textarea rows="3" name="repairRecord.repairInfo" id="repairInfo">${repairRecord.repairInfo}</textarea>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">修复方案</label>

                <div class="controls">
                    <select name="repairRecord.scheme.id" id="institutionId">
                    <#list schemes as scheme>
                        <option value="${scheme.id}" <#if scheme.id==repairRecord.scheme.id>selected="selected" </#if>>${scheme.name}</option>
                    </#list>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">修复单位</label>

                <div class="controls">
                    <select name="repairRecord.institution.id" id="schemeId">
                    <#list institutions as institution>
                        <#if institution.institutionType=2>
                            <option value="${institution.id}" <#if repairRecord.institution.id==institution.id>selected="selected" </#if> >${institution.name}</option>
                        </#if>
                    </#list>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">修复负责人</label>

                <div class="controls">
                    <select name="repairRecord.mainUser.id" id="mainUserId">
                    <#list users as user>
                        <option value="${user.id}" <#if repairRecord.mainUser.id==user.id>selected="selected" </#if> >${user.userName}</option>
                    </#list>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">修复协助人</label>

                <div class="controls">
                    <select name="repairRecord.secondaryUserId" style="width: 29%" multiple id="secondaryUserId"></select>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary" id="submit-btn">保存</button>
                <a class='btn' href="repairRecords">返回</a>
            </div>

            <fieldset>
    </form>
</div>
<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">
<#include  "../_common/last-resources.ftl">
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(function () {
            $.get("handleTask/secondaryUser.json", {"repairRecord.mainUser.id": $("#mainUserId").val()}, function (result) {
                var dataArray = eval(result);
                var str = "";
                for (var i in dataArray) {
                    str = str + "<option value='" + i + "'>" + dataArray[i] + "</option>";
                }
                $("#secondaryUserId").html(str);
                $("#secondaryUserId").select2();
                var selectValueTemp = $("#secondaryUserIdValue").val().split(",");
                var selectValue = [];
                for(var v in selectValueTemp){
                   selectValue.push(selectValueTemp[v].trim());
                }
                $("#secondaryUserId").select2("val",selectValue);
            });
        });

        $("#mainUserId").change(function () {
            var $this = $(this);
            var value = $this.val();
            $.get("handleTask/secondaryUser.json", {"repairRecord.mainUser.id": value}, function (result) {
                var dataArray = eval(result);
                var str = "";
                for (var i in dataArray) {
                    str = str + "<option value='" + i + "'>" + dataArray[i] + "</option>";
                }
                $("#secondaryUserId").html(str);
                $("#secondaryUserId").select2();
            });
        });


        $("#handleTask_form").validate({
            rules: {
                'repairRecord.repairInfo': {
                    required: true,
                    maxlength: 255
                },
                'repairRecord.secondaryUserId': {
                    required: true
                }

            },
            messages: {
                'repairRecord.repairInfo': {
                    required: "请输入名称",
                    maxlength: jQuery.format("名称不能大于{0}个字符")
                },
                'repairRecord.secondaryUserId': {
                    required: "请至少选择一个"
                },
                errorPlacement: function (error, element) {
                    if (element.is(":radio")) {
                        error.appendTo(element.parent());
                    } else if (element.is(":checkbox")) {
                        error.appendTo(element.next());
                    } else {
                        error.appendTo(element.next());
                    }
                }
            }
        });
    });
</script>
</body>
</html>

