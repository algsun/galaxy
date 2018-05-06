<#--
修改原因

@author 王耕
@time  2015-9-11
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>修改原因 - 资产管理</title>

<#include "../_common/common-css.ftl">
<#include "/common/pages/common-tag.ftl">
    <style type="text/css">
    </style>
</head>
<body>
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
    <div class="row">
        <div class="span12">
            <fieldset>
                <legend>
                    <a class="go-back" href="reason/index" title="返回">
                        <i class="mw-icon-prev"></i>修改原因
                    </a>
                </legend>
            </fieldset>
        </div>
    </div>

    <div class="row">
        <div class="span12">
            <form id="reasonForm" class="form-horizontal" action="reason/saveOrUpdateReason"
                  method="post">
                <input type="hidden" name="repairReason.id" id="repairReasonId" value="${repairReason.id}"/>
                <div class="control-group">
                    <label class="control-label">
                        <em class="required">*</em> 修复因由
                    </label>

                    <div class="controls">
                        <input type="text" name="repairReason.reason" id="repairReason"
                               value="${repairReason.reason}">
                        <span id="repairReason-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="button" class="btn btn-primary" id="reasonSubmit">保存</button>
                    <a href="reason/index" class="btn">返回</a>
                </div>
            </form>
        </div>
    </div>
</div>


<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<@scriptTag "../assets/artDialog/5.0.1-7012746/artDialog.min.js"/>
<#--your js-->
<!--表单验证-->
<@scriptTag "js/checkReason.js"/>
</body>
</html>