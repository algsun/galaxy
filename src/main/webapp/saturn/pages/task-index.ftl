<#assign title="成果展示 - 任务公告">
<#assign currentTopPrivilege = "saturn:task">
<#-- 当前权限标识 -->

<#macro head>
<link rel="stylesheet" href="/galaxy/assets/my97-DatePicker/4.8-beta3/skin/WdatePicker.css"/>
</#macro>

<#macro content>
<div class="container m-t-20">
    <div class="row">
        <div class="span12">
            <form class="form-inline well well-small" action="tasks" method="get">
                <label class="control-label" for="title">任务名称</label>
                <input type="text" id="title" name="title" value="${RequestParameters.title!}" class="input-medium">
                <label class="control-label" for="responsible">负责人</label>
                <select name="responsible" id="responsible" class="input-medium">
                    <option value="0">请选择</option>
                    <#list users as user>
                        <option value="${user.id}"
                                <#if RequestParameters.responsible?exists && user.id?eval == RequestParameters.responsible?eval>selected="selected"</#if>>${user.userName}</option>
                    </#list>
                </select>
                <label class="control-label" for="begin-time">预计开始时间</label>
                <input class="input-small Wdate" id="begin-time" type="text" name="beginDate"
                       onfocus="var endTime=$dp.$('end-time');WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'end-time\')}'})"
                       value="${beginDate?string("yyyy-MM-dd")}"/>
                <label class="control-label" for="end-time">-</label>
                <input class="input-small Wdate" id="end-time" type="text" name="endDate"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'begin-time\')}', maxDate:'%y-%M-{%d}'})"
                       value="${endDate?string("yyyy-MM-dd")}"/>

                <button class="btn" type="submit">查询</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <div class="tasks">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>任务名称</th>
                        <th>责任人</th>
                        <th>预计开始</th>
                        <th>预计结束</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>完成状态</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#if tasks??>
                        <#list tasks as task>
                        <tr style="background-color: #ffffff">
                            <td>${task_index+1}</td>
                            <td>${task.title!}</td>
                            <td>${task.userName!}</td>
                            <td>
                                <#if task.predictStart??>
                                    ${task.predictStart?string('yyyy-MM-dd')}
                                </#if>
                            </td>
                            <td>
                                <#if task.predictEnd??>
                                    ${task.predictEnd?string('yyyy-MM-dd')}
                                </#if>
                            </td>
                            <td>
                                <#if task.realStart??>
                                    ${task.realStart?string('yyyy-MM-dd')}
                                </#if>
                            </td>
                            <td>
                                <#if task.realEnd??>
                                    ${task.realEnd?string('yyyy-MM-dd')}
                                </#if>
                            </td>
                            <td
                                <#switch task.state>
                                    <#case 0>
                                            class="red">待审核
                                        <#break>
                                    <#case 1>
                                        >进行中
                                        <#break>
                                    <#case 2>
                                        class="green">已完成
                                        <#break>
                                    <#case 3>
                                        >已作废
                                        <#break>
                                    <#default>
                                        >未知
                                </#switch>
                            </td>
                        </tr>
                        </#list>
                        </#if>
                    </tbody>
                </table>
            </div>
            <#include "_common/pagging.ftl" >
            <#assign url="tasks?title=${RequestParameters.title!}&responsible=${RequestParameters.responsible!0?eval}&beginDate=${beginDate?string('yyyy-MM-dd')}&endDate=${endDate?string('yyyy-MM-dd')}">
            <@pagination url index pageCount "index"></@pagination>
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="/galaxy/assets/my97-DatePicker/4.8-beta3/WdatePicker.js"></script>
</#macro>