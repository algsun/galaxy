<#assign title = "首页 - 档案详情">
<#include "/common/pages/common-tag.ftl"/>
<#macro head>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span12">
        <div class="page-header title">
            <h3>
                <span class="f-n">档案详情</span>
                <a class="f-n" style="font-size: 0.7em;" href="volume/${volumeCode}/records">/返回</a></h3>
        </div>
    </div>
</div>

<div class="row-fluid"
     ng-app="app"
     ng-controller="DetailController"
     ng-init="init('${volumeCode}','${recordId}')">
    <div class="span12">
        <div class="form-horizontal">
            <fieldset>
                <div class="control-group">
                    <label class="control-label">档号</label>

                    <div class="controls p-v-5" ng-bind="record.recordCode"></div>
                </div>
                <div class="control-group">
                    <label class="control-label">卷案题名</label>

                    <div class="controls p-v-5" ng-bind="record.name"></div>
                </div>
                <div class="control-group">
                    <label class="control-label">密级</label>

                    <div class="controls p-v-5" ng-bind="stateSecrets(record.stateSecrets)"></div>
                </div>
                <div class="control-group">
                    <label class="control-label">立卷单位名称</label>

                    <div class="controls p-v-5" ng-bind="record.department"></div>
                </div>
                <div class="control-group">
                    <label class="control-label">立卷日期</label>

                    <div class="controls p-v-5" ng-bind="record.establishDate|date:'yyyy-MM-dd'"></div>
                </div>
            </fieldset>
        </div>
        <div class="row-fluid">
            <div class="span8">
                <table class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>名称</th>
                        <th>上传日期</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="attachment in record.attachments" align="bottom">
                        <td>{{$index+1}}</td>
                        <td>
                            <div>
                                <img alt="" style="width:20px;height: 20px" ng-src="images/{{attachment.type}}.png">
                                {{attachment.uploadName}}
                            </div>
                        </td>
                        <td>{{attachment.uploadDate|date:'yyyy-MM-dd HH:mm'}}</td>
                        <td>
                            <a type="button" class="btn btn-mini"
                               ng-href="attachments/{{attachment.uuid}}/download">下载</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/angular/1.2.20/angular.min.js"></script>
<script type="text/javascript" src="../assets/angular/1.2.20/angular-sanitize.min.js"></script>
<script type="text/javascript" src="../assets/angular/1.2.20/i18n/angular-locale_zh-cn.js"></script>
    <@scriptTag "js/record/detail-record.js"/>
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
