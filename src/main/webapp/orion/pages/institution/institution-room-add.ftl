<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>添加单位 - 资产管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<#--<#assign currentTopPrivilege = "orion:stockManage">-->
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
    <form class="form-horizontal" id="institution_form" action="institutionRoom/do_add" method="post" >
        <input type="hidden" name="institutionRoom.institutionId" value="${institutionRoom.institutionId}"/>
        <fieldset>
            <legend>
                添加单位库房
            </legend>
            <div class="control-group">
                <label class="control-label">库房名称 </label>

                <div class="controls">
                    <input type="text" id="name" name="institutionRoom.roomName">
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary" id="submit-btn">保存</button>
                <a class='btn' href="institutionRoom/index?institutionRoom.institutionId=${institutionRoom.institutionId}">返回</a>
            </div>
            <fieldset>
    </form>
</div>
<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">
<#include  "../_common/last-resources.ftl">
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript">
    $(function(){
        $("#institution_form").validate({
            rules: {
                'institutionRoom.roomName': {
                    required: true,
                    maxlength: 25
                }
            },
            messages: {
                'institutionRoom.roomName': {
                    required: "请输库房名称",
                    maxlength: jQuery.format("名称不能大于{0}个字符")
                }
            }
        });
    });
</script>
</body>
</html>

