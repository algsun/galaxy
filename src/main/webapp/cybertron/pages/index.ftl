<#assign title = "首页 - 档案管理">
<#include "/common/pages/common-tag.ftl"/>

<#macro head>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
</#macro>

<#macro content>
<div class="row-fluid"
     ng-app="app"
     ng-controller="RecordController"
     ng-init="init('${volumeCode}')">
    <div class="span12">
        <div class="well well-small m-t-20">
            <input type="text" placeholder="可输入过滤条件筛选文档" ng-model="searchText" class="input-xlarge">
            <#if security.isPermitted("cybertron:manage:add")>
                <a id="createRecord" class="pull-right btn <#if volumeCode lt 10>hide</#if> "<#if volumeCode gt 9>
                   href="addRecord-view/${volumeCode}"</#if>>创建</a>
            </#if>
        </div>
        <table ng-show="hasData" class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>序号</th>
                <th>档号</th>
                <th>卷案题名</th>
                <th>立卷日期</th>
                <th>密级</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="record in records | filter:recordFilter">
                <td ng-bind="$index+1"></td>
                <td>
                    <a ng-href="volumes/{{record.volumeCode}}/records/{{record.uuid}}/view"
                       ng-bind="record.recordCode"></a>
                </td>
                <td ng-bind="record.name"></td>
                <td ng-bind="record.establishDate|date:'yyyy-MM-dd'"></td>
                <td ng-bind="record.stateSecrets"></td>
                <td>
                    <#if security.isPermitted("cybertron:manage:delete")>
                        <button class="btn btn-mini btn-danger" type="button" ng-click="deleteRecord(record.uuid)">删除
                        </button>
                    </#if>
                    <#if security.isPermitted("cybertron:manage:edit")>
                        <a class="btn btn-mini btn-info" type="button"
                           href="editRecord-view/{{record.uuid}}/volume/{{record.volumeCode}}">编辑
                        </a>
                    </#if>
                </td>
            </tr>
            </tbody>
        </table>
        <h4 class="hide" ng-hide="hasData">暂无数据</h4>
    </div>
</div>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/angular/1.2.20/angular.min.js"></script>
<script type="text/javascript" src="../assets/angular/1.2.20/angular-sanitize.min.js"></script>
<script type="text/javascript" src="../assets/angular/1.2.20/i18n/angular-locale_zh-cn.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="../assets/artDialog/5.0.3-ec058ee8b2/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="../assets/later/1.1.6/later.min.js"></script>
    <@scriptTag "js/pnotify.js"/>
    <@scriptTag "js/record/query-record.js"/>
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