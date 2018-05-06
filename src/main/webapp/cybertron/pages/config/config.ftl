<#assign title = "首页 -系统设置">
<#assign showTree = false>

<#include "/common/pages/common-tag.ftl"/>

<#macro head>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span12">
        <div class="page-header title">
            <h3>
                <span class="f-n">档案列表</span>
                <a class="f-n" style="font-size: 0.7em;" href="volume/1/records">/返回</a></h3>
        </div>
    </div>
</div>

<div class="row-fluid"
     ng-app="app"
     ng-controller="ConfigController"
     ng-init="init()">
    <div class="span12">
        <form id="configForm" class="form-horizontal m-t-50" ng-submit="update()">
            <div class="control-group">
                <label class="control-label" for="officialId">档案全宗号</label>

                <div class="controls">
                    <input type="hidden" ng-model="uuid"/>
                    <input type="text" id="officialId" name="officialId" ng-model="officialId" maxlength="9"/>
                    <span id="officialId-input-help" class="help-inline"></span>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">保存</button>
            </div>
        </form>
    </div>
</div>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/angular/1.2.20/angular.min.js"></script>
<script type="text/javascript" src="../assets/angular/1.2.20/angular-sanitize.min.js"></script>
<script type="text/javascript" src="../assets/angular/1.2.20/i18n/angular-locale_zh-cn.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
    <@scriptTag "js/pnotify.js"/>
    <@scriptTag "js/config.js"></@scriptTag>
<script type="text/javascript">
    $(function () {
        $(document).on('click', '.subsystemHead', function () {
            art.dialog({
                title: '正在努力加载!'
            });
        });
    });
</script>
</#macro>
