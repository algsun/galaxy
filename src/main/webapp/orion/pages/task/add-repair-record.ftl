<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>修复登记 - 资产管理</title>
<#include "../_common/common-css.ftl">
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<#--<#assign currentTopPrivilege = "orion:stockManage">-->
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
    <fieldset>
        <legend>
            <a class="go-back" href="" title="返回">
                <i class="mw-icon-prev"></i>修复
            </a>
        </legend>
    </fieldset>
    <form class="form-horizontal" id="repair_record_form" action="repairRecords" method="post">
        <input name="repairRecord.relic.id" type="hidden" value="${relic.id}"/>
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
                    <#if relic.relicPropertyMap??>
                        <#if relic.relicPropertyMap.source??>
                            <input type="text" readonly="readonly" value="${relic.relicPropertyMap.source.propertyValue!}"/>
                        <#else>
                            <input type="text" readonly="readonly"/>
                        </#if>
                    </#if>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">修复原因</label>

                <div class="controls">
                    <select name="repairRecord.repairReason.id" id="repairReasonId">
                    <#list repairReasons as repairReason>
                        <option value="${repairReason.id}">${repairReason.reason}</option>
                    </#list>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">提取时间</label>

                <div class="controls">
                    <input type="text"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'returnDate\')||\'2100-12-31\'}'})"
                           name="repairRecord.extractDate" id="extractDate"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">归还时间</label>

                <div class="controls">
                    <input type="text"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'extractDate\')}',maxDate:'2100-12-31'})"
                           name="repairRecord.returnDate" id="returnDate"/>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary" id="submit-btn">保存</button>
                <a class='btn' href="">返回</a>
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
<script type="text/javascript">
    $(function () {
        $("#repair_record_form").validate({
            rules: {
                'repairRecord.extractDate': {
                    required: true,
                    date: true
                },
                "repairRecord.returnDate": {
                    date: true
                }
            },
            messages: {
                'repairRecord.extractDate': {
                    required: "请输入提取时间"
                }
            }
        });
    });
</script>
</body>
</html>

