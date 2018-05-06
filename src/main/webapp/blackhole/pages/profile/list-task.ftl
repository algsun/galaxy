<#--
@author wanggeng
添加我的任务
@date 2013-07-25
@check @duan.qixin 2013年7月29日 #4720
-->

<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>我的任务 - 系统管理</title>
<#include "../_common/common-css.ftl">
</head>
<body>

<!-- 导航栏 -->
<#--当前一级权限ID-->
<#assign currentTopPrivilege = "blackhole:profile">
<#include "../_common/header.ftl">

<div id="gcontent" class="container">
<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:profile:mytask"></@subNavs>

<#include "task-helper.ftl"/>


<#include "../_common/message-tooltip.ftl">
    <div class="row">
        <div class="span12">
            <div class="form-horizontal">
                <fieldset>
                    <legend>任务列表</legend>
                <#if security.isUserPermitted("blackhole:profile:publishtask")>
                    <div class="f-r m-b-20">
                        <a class="btn btn-large btn-info" href="toAddTask.action">发布任务</a>
                    </div>
                </#if>
                    <div>
                        <form action="listTask.action">
                            <select name="taskState" id="taskState">
                                <option value="2" <#if taskState == 2>selected="true"</#if>>未结束</option>
                                <option value="1" <#if taskState == 1>selected="true"</#if>>全部</option>
                            </select>
                            <button type="submit" class="btn">查询</button>
                        </form>
                    </div>
                <#if taskList??>
                    <#if taskList?size != 0>
                        <div class="row">
                            <div class="span12">
                                <table id="taskList" class="table table-bordered table-striped table-center">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>标题</th>
                                        <th>发布时间</th>
                                        <th>截止时间</th>
                                        <th>发布人</th>
                                        <th>指派人</th>
                                        <th>完成度</th>
                                        <th>状态</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                        <#list taskList as task>
                                        <tr>
                                            <td>${task_index+1}</td>
                                            <td>
                                                <a href="toViewTask.action?id=${task.id?c}" class="bold">${task.taskTitle}</a>
                                            </td>
                                            <td>${task.releaseDate?string("yyyy-MM-dd")}</td>
                                            <td>${task.endDate?string("yyyy-MM-dd")}</td>
                                            <td>${task.releaser.userName}</td>
                                            <td>
                                                <#if task.designees??>
                                                    <#if task.designees?size != 0>
                                                        <#list task.designees as designee>
                                                            <span>${designee.userName}</span>
                                                        </#list>
                                                    </#if>
                                                </#if>
                                            </td>
                                            <td>
                                                <@task_progress task/>
                                            </td>
                                            <td>
                                                <#if task.state>
                                                    已结束
                                                <#else>
                                                    未结束
                                                </#if>
                                            </td>
                                            <td><a class="btn btn-success btn-mini"
                                                   href="toViewTask.action?id=${task.id?c}">详情</a></td>
                                        </tr>
                                        </#list>
                                    </tbody>
                                </table>

                                <#include  "../_common/pagging.ftl">
                                <#assign  url = "listTask.action?pageIndex=2">
                                <@pagging url,index,pageCount/>
                            </div>
                        </div>
                    <#else>
                        <h3>暂无任务</h3>
                    </#if>
                </#if>
                </fieldset>
            </div>
        </div>
    </div>
</div>

<!-- 页面底部 -->
<#include "../_common/footer.ftl">
<#--公共JS-->
<#include "../_common/common-js.ftl">
<script type="text/javascript">
    $(function () {

        (function () {
            setTimeout(function () {
                $(".close").click();
            }, 5000);
        })();
    });
</script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
