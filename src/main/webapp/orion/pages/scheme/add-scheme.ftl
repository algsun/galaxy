<#--
新增方案

@author 王耕
@time  2015-9-11
-->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>新增方案 - 资产管理</title>

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
                    <a class="go-back" href="schemes/index" title="返回">
                        <i class="mw-icon-prev"></i>新增方案
                    </a>
                </legend>
            </fieldset>
        </div>
    </div>

    <div class="row">
        <div class="span12">
            <form id="schemeForm" class="form-horizontal" action="schemes/saveOrUpdateScheme"
                  method="post">
                <div class="control-group">
                    <label class="control-label" for="">
                        <em class="required">*</em> 方案编号
                    </label>

                    <div class="controls">
                        <input type="text" name="scheme.schemeId" id="schemeId">
                        <span id="schemeId-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="">
                        <em class="required">*</em> 方案名称
                    </label>

                    <div class="controls">
                        <input type="text" name="scheme.name" id="name">
                        <span id="name-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <span id="institutionSelector">
                         <label class="control-label">
                             <em class="required">*</em>设计单位
                         </label>
                          <div class="controls">
                          <@selectOptionInstitution institutions/>  <i class="icon-pencil" id="institution"></i>
                              <span id="institution-input-help" class="help-inline"></span>
                          </div>
                    </span>
                </div>

                <div class="control-group">
                    <label class="control-label" for="">
                        <em class="required">*</em>批准时间
                    </label>

                    <div class="controls">
                        <input class="input-medium Wdate" type="text"
                               name="scheme.confirmTime"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="confirmTime"/>
                        <span id="confirmTime-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="">
                        <em class="required">*</em> 批准文号
                    </label>

                    <div class="controls">
                        <input type="text" name="scheme.confirmNum" id="confirmNum">
                        <span id="confirmNum-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary" id="schemeSubmit">保存</button>
                    <a href="schemes/index" class="btn">返回</a>
                </div>

             </form>
        </div>
    </div>
</div>


<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<@scriptTag "../assets/artDialog/5.0.1-7012746/artDialog.min.js"/>
<#--your js-->
<!--表单验证-->
<@scriptTag "js/checkScheme.js"/>
</body>
</html>
<#--加载质地-->
<#macro selectOptionInstitution institutions>
<select style="width: 220px" name="scheme.institution.id" id="institutionId">
    <#if institutions?size != 0>
        <option value="0">--请选择--</option>
        <#list institutions as institution>
            <option value="${institution.id}">${institution.name}</option>
        </#list>
    </#if>
</select>
</#macro>