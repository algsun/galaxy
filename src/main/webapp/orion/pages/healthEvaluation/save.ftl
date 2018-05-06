
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>文物健康评测添加 - 资产管理</title>

<#include "../_common/common-css.ftl">
</head>
<body>
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
    <form class="form-horizontal" id="healthEvaluation_form" action="healthEvaluation/save" method="post" >
        <input type="hidden"  name="healthEvaluation.relic.id" value="${relic.id}">
        <fieldset>
            <legend>
                添加文物保护健康评测
            </legend>
            <div class="control-group">
                <label class="control-label" for="evaluator">评测人员</label>

                <div class="controls">
                    <input type="text" id="evaluator" readonly name="healthEvaluation.evaluator" value="${Session.currentUser.userName}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="sampleNumber">样品编号</label>

                <div class="controls">
                    <input type="text" id="sampleNumber" name="healthEvaluation.sampleNumber">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="sampleDesc">样品描述</label>

                <div class="controls">
                    <input type="text" id="sampleDesc" name="healthEvaluation.sampleDesc">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="quoInfo">现状描述</label>

                <div class="controls">
                    <input type="text" id="quoInfo" name="statusQuo.quoInfo" >
                    <#--<input type="hidden" name="statusQuo.id" value="${statusQuo.id}">-->
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">综合评测结论</label>

                <div class="controls">
                    <select name="healthEvaluation.conclusion" id="conclusion">
                        <option value="1">好</option>
                        <option value="2">很好</option>
                        <option value="3">非常好</option>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">保护建议</label>

                <div class="controls">
                    <select id="suggestion" name="healthEvaluation.suggestion">
                        <option value="1">建议1</option>
                        <option value="2">建议2</option>
                        <option value="3">建议3</option>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">评测日期</label>

                <div class="controls">
                    <input class="Wdate" type="text" name="healthEvaluation.evaluationDate"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" value="${.now?string('yyyy-MM-dd hh:mm')}" />
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary" id="submit-btn">保存</button>
                <a class='btn' href="healthEvaluation/index?relic.id=${relic.id}">返回</a>
            </div>
            <fieldset>
    </form>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script>
    $(function(){
        $("#healthEvaluation_form").validate({
            rules: {
                'healthEvaluation.sampleNumber': {
                    required: true,
                    maxlength: 50
                },
                'healthEvaluation.sampleDesc': {
                    required: true,
                    maxlength: 50
                },
                'statusQuo.quoInfo': {
                    required: true,
                    maxlength: 200
                },
                'healthEvaluation.evaluationDate': {
                    required:true
                }
            },
            messages: {
                'healthEvaluation.sampleNumber': {
                    required: "请输入样品编号",
                    maxlength: jQuery.format("编号不能大于{0}个字符")
                },
                'healthEvaluation.sampleDesc': {
                    required: "请输入样品描述",
                    maxlength: "描述不能大于{0}个字符"
                },
                'statusQuo.quoInfo': {
                    required: "请输入现状描述",
                    maxlength: "现状描述不能大于于{0}个字符"
                },
                'healthEvaluation.evaluationDate': {
                    required:"请选择日期"
                }
            }
        });
    });
</script>
</body>
</html>


