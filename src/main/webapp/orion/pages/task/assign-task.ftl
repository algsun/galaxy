<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>分配任务 - 资产管理</title>
    <link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
<#include "../_common/common-css.ftl">

</head>
<body>
<#--<#assign currentTopPrivilege = "orion:stockManage">-->
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
    <form class="form-horizontal" id="handleTask_form" action="handleTask/do_assign_task" method="post">

        <fieldset>
            <legend>
                <#--<a style="text-decoration:none;color:#000000;float:left;margin-top: 15px;margin-bottom: 15px;" href="repairRecords" title="返回">-->
                    <#--<i class="mw-icon-prev"></i> <span style="font-size: 18px;">  </span> </a>-->
                分配任务
            </legend>

            <input name="repairRecord.id" id="repairRecord" type="hidden" value="${repairRecord.id!}"/>
            <input name="repairRecord.siteId" id="repairRecord.siteId" type="hidden" value="${repairRecord.siteId!}"/>
            <input name="repairRecord.relic.id" id="repairRecord.relicId" type="hidden" value="${repairRecord.relic.id!}"/>
            <input type="hidden" name="repairRecord.identifier" value="${repairRecord.identifier?c}"/>
            <input name="repairRecord.repairReason.Id" id="repairRecord.repairReason.Id" type="hidden" value="${repairRecord.repairReason.id!}"/>
            <input name="repairRecord.extractDate" id="repairRecord.extractDate" type="hidden" value="${repairRecord.extractDate?string("yyyy-MM-dd")}"/>
            <input name="repairRecord.returnDate" id="repairRecord.returnDate" type="hidden" value="${(repairRecord.returnDate?string("yyyy-MM-dd"))!}"/>

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
                    <input type="text" readonly="readonly" value="<#if relic.relicPropertyMap.source??>${relic.relicPropertyMap.source.propertyValue!}</#if>"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">收藏库房</label>

                <div class="controls">
                    <input type="text" readonly="readonly"
                    <#if institutionRoom??>
                           value="${institutionRoom.roomName}"
                    <#else>
                           value=""
                    </#if>/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">修复因由</label>

                <div class="controls">
                    <input type="text" readonly="readonly" value="${repairRecord.repairReason.reason}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">修复内容</label>

                <div class="controls">
                    <textarea rows="3" name="repairRecord.repairInfo" id="repairInfo"></textarea>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">修复方案</label>

                <div class="controls">
                    <select name="repairRecord.scheme.id" id="schemeId">
                    <#list schemes as scheme>
                        <option value="${scheme.id}">${scheme.name}</option>
                    </#list>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">修复单位</label>

                <div class="controls">
                    <select name="repairRecord.institution.id" id="institutionId">

                    <#list institutions as institution>
                        <#if institution.institutionType=2>
                            <option value="${institution.id}">${institution.name}</option>
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
                        <option value="${user.id}">${user.userName}</option>
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
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript">

    $(function () {
        var initFun = function(mainUserId){
            $.get("handleTask/secondaryUser.json", {"repairRecord.mainUser.id": mainUserId}, function (result) {
                var dataArray = eval(result);
                var str = "";
                for (var i in dataArray) {
                    str = str + "<option value='" + i + "'>" + dataArray[i] + "</option>";
                }
                $("#secondaryUserId").html(str);
                $("#secondaryUserId").select2();
            });
        };

        var mainUserId = $("#mainUserId").val()
        initFun(mainUserId);


        $("#mainUserId").change(function () {
            var $this = $(this);
            var value = $this.val();
            initFun(value);
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

