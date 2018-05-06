<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>修改单位 - 资产管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<#--<#assign currentTopPrivilege = "orion:stockManage">-->
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
    <form class="form-horizontal" id="institution_form" action="institution/do_update" method="post" >
        <input name="institution.id" type="hidden" value="${institution.id}"/>
        <input name="institution.siteId" type="hidden" value="${institution.siteId}"/>
        <input name="institution.createDate" type="hidden" value="${institution.createDate?string("yyyy-MM-dd HH:mm:ss")}"/>
        <fieldset>
            <legend>
                修改单位
            </legend>
            <div class="control-group">
                <label class="control-label">名称</label>

                <div class="controls">
                    <input type="text" id="name" name="institution.name" value="${institution.name}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">所在地</label>

                <div class="controls">
                    <input type="text" id="seat" name="institution.seat" value="${institution.seat}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">通讯地址</label>

                <div class="controls">
                    <input type="text" id="mailing" name="institution.mailing" value="${institution.mailing}">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">邮编</label>

                <div class="controls">
                    <input type="text" id="zipcode" name="institution.zipcode" value="${institution.zipcode?c}">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">资质证书</label>

                <div class="controls">
                    <input type="text" id="qualification" name="institution.qualification" value="${institution.qualification}">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">代号</label>

                <div class="controls">
                    <input type="text" id="code" name="institution.code" value="${institution.code}">
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">单位类型</label>

                <div class="controls">
                    <select name="institution.institutionType" id="institutionType">
                        <option value="0" <#if institution.institutionType==0>selected="selected"</#if>>设计单位</option>
                        <option value="1" <#if institution.institutionType==1>selected="selected"</#if>>收藏单位</option>
                        <option value="2" <#if institution.institutionType==2>selected="selected"</#if>>修复单位</option>
                    </select>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary" id="submit-btn">保存</button>
                <a class='btn' href="institution/index">返回</a>
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
    jQuery.validator.addMethod("isZipCode", function(value, element) {
        var tel = /^[0-9]{6}$/;
        return this.optional(element) || (tel.test(value));
    }, "请正确填写您的邮政编码");

    $(function(){
        $("#institution_form").validate({
            rules: {
                'institution.name': {
                    required: true,
                    maxlength: 50
                },
                'institution.seat': {
                    required: true,
                    maxlength: 50
                },
                'institution.mailing': {
                    required: true,
                    maxlength: 50
                },
                'institution.zipcode': {
                    required:true,
                    isZipCode: true
                },
                'institution.qualification': {
                    required: true,
                    maxlength: 50
                },
                'institution.code': {
                    required: true,
                    maxlength: 25
                }
            },
            messages: {
                'institution.name': {
                    required: "请输入名称",
                    maxlength: jQuery.format("名称不能大于{0}个字符")
                },
                'institution.seat': {
                    required: "请输入所在地",
                    maxlength: "所在地不能大于{0}个字符"
                },
                'institution.mailing': {
                    required: "请输入通讯地址",
                    maxlength: "通讯地址不能大于于{0}个字符"
                },
                'institution.zipcode': {
                    required:"请输入邮编"
                },
                'institution.qualification': {
                    required: "请输入资质证书",
                    maxlength: "资质证书不能大于{0}个字符"
                },
                'institution.code': {
                    required: "请输入代号",
                    maxlength: "代码不能大于{0}个字符"
                }
            }
        });
    });
</script>
</body>
</html>

