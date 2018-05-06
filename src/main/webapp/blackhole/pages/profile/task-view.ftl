<#--
@author wanggeng
我的任务详情
@date 2013-07-25
@check @duan.qixin 2013年7月29日 #4738
-->

<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>我的任务 - 系统管理</title>
<#include "../_common/common-css.ftl">
    <link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
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
            <div class="well well-small">
                <h3>${task.taskTitle}</h3>

                <div class="muted">
                    <icon class="icon-user"></icon>${task.releaser.userName}
                    发布于${task.releaseDate?string("yyyy-MM-dd HH:mm")}
                    <span class="bold m-h-5">·</span>
                    截止时间${task.endDate?string("yyyy-MM-dd")}
                    <span class="bold m-h-5">·</span>
                <#if task.state>
                    已结束
                <#else>
                    未结束
                </#if>,
                    进度<@task_progress task/>
                    <span class="bold m-h-5">·</span>
                    接受人:
                <#list task.designees as designee>
                    <span>${designee.userName}</span>
                    <#if designee_has_next>,</#if>
                </#list>
                </div>
                <hr style="margin: 10px 0;">
                <p>
                ${task.taskInfo}
                </p>
            </div>


            <div>
            <#if task.taskRecords?size != 0>
                <h5>回复</h5>
                <#assign preTaskRord = {}>
                <#list task.taskRecords as taskRecord>
                    <@archivesRow2 preTaskRord taskRecord/>
                    <#assign preTaskRord = taskRecord>
                </#list>
            </#if>
            </div>

            <div class="m-t-50 well well-small">
                <form action="addTaskRecord.action" method="post" class="form-horizontal">
                    <fieldset>
                        <legend>添加回复</legend>
                        <input type="hidden" name="id" value="${task.id}"/>

                        <div class="control-group">
                            <label class="control-label"><em class="red">*</em>描述:</label>

                            <div class="controls">
                                <textarea name="recordInfo" id="recordInfo" rows="3" class="span4"></textarea>
                                <span class="help-inline red" id="recordInfoSpan"></span>
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label"><em class="red">*</em>进度:</label>

                            <div class="controls">
                            <@taskPercent task/>
                            </div>
                        </div>

                        <div class="control-group">

                            <label class="control-label"><em class="red">*</em>状态:</label>

                            <div class="controls">
                            <@taskComplete task/>
                            </div>
                        </div>

                        <div class="control-group">

                            <label class="control-label"><em class="red">*</em>接受人:</label>

                            <div class="controls">
                            <#if task.designees??>
                                <#if task.designees?size != 0>
                                    <#if Session.currentUser.id != task.releaser.id>
                                        <#list task.designees as designee>
                                            <span class="bold il-blk m-r-5 m-t-5">${designee.userName!}</span>
                                            <#if designee_has_next>,</#if>
                                        </#list>
                                    <#else >
                                        <@selectOption userMap task.designees/>
                                    </#if>
                                </#if>
                            </#if>
                            </div>
                        </div>

                        <div class="control-group">
                            <div class="control-label"></div>
                            <div class="controls">
                                <button type="submit" class="btn btn-primary save-btn">回复</button>
                                <a class="btn" href="listTask.action">返回</a>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 页面底部 -->
<#include "../_common/footer.ftl">
<#--公共JS-->
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../common/js/form-check.js"></script>
<script type="text/javascript">
    $(function () {
        (function () {
            setTimeout(function () {
                $(".close").click();
            }, 5000);
        })();

        $("#userIds").select2();

        //验证
        (function () {
            var state = $("#state").val();
            $("#state").change(function () {
                if (state) {
                    if ($("#completeStatus").val() != 100) {
                        $("#state").find("option[id='off']").attr("selected", true);
                    }
                }
            });
            $("#completeStatus").change(function () {
                if ($("#completeStatus").val() != 100) {
                    $("#state").find("option[id='off']").attr("selected", true);
                }
            });

            $(document).on('click', '.save-btn', function () {
                var $recordInfo = $("#recordInfo");
                var $recordInfoSpan = $("#recordInfoSpan");
                if (verifyIsNotNull($recordInfo)) {
                    App.helpOk($recordInfoSpan, '');
                    return true;
                } else {
                    App.helpError($recordInfoSpan, "回复信息不能为空");
                    return false;
                }
            });
            var verifyIsNotNull = function ($this) {
                if ($.trim($this.val())) {
                    return true;
                } else {
                    return false;
                }
            };
        })();
    });
</script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
<#macro archivesRow2 preTaskRord taskRecord>

<li style="list-style-type: none; border-top: 1px solid #ddd; padding: 10px 0;">
    <div style="width: 50px; height: 50px; float: left;">
        <#local photo = defaultPhoto>
        <#local replier = taskRecord.replier>
        <#if replier.photo??>
            <#local photo = resourcesPath + "/user/" + replier.photo>
        </#if>
        <img src="${photo}" alt="头像" style="max-width: 50px; max-height: 50px;"/>
    </div>
    <div style="float: left; margin-left: 10px; width: 850px;">
        <div>
            <strong>${replier.userName}</strong>
            <span class="muted m-l-10">
            ${taskRecord.recordDate?string("yyyy-MM-dd HH:mm")}
            <span class="bold m-h-5">·</span>
            进度${taskRecord.completeStatus}%
            </span>
        </div>
        <p class="m-t-10">${taskRecord.recordInfo}</p>

        <#if preTaskRord.id?? && preTaskRord.state != taskRecord.state>
            <p class="m-t-10">
                任务被
                <#if preTaskRord.state && !taskRecord.state>
                    <strong style="font-style: italic;">重新打开</strong>
                </#if>
                <#if !preTaskRord.state && taskRecord.state>
                    <strong style="font-style: italic;">关闭</strong>
                </#if>
            </p>
        </#if>
    </div>

    <div class="clear"></div>
</li>
</#macro>

<#macro archivesRow preTaskRord taskRecord>
<div class="m-t-30">
    <table class="table table-bordered table-striped table-center m-b-0">
        <thead>
        <tr>
            <th>
                <div style="margin: 0;padding: 0;" class="span8">
                    <div class="span2"><span class="gray">回复人:</span>${taskRecord.replier.userName}</div>
                    <div class="span2">
                        <small>${taskRecord.recordDate?string("yyyy-MM-dd HH:mm:ss")}</small>
                    </div>
                    <div class="span3">
                        任务进度:${taskRecord.completeStatus}%
                    </div>
                </div>
            </th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>
                <div style="padding-bottom: 20px">
                ${taskRecord.recordInfo}
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <div>
        <#if preTaskRord.id??>
            <div>
                <#if preTaskRord.completeStatus != taskRecord.completeStatus>
                    <span class="gray">任务进度由</span><strong>${preTaskRord.completeStatus}%</strong><span class="gray">修改为了</span><strong>${taskRecord.completeStatus}
                    %</strong>
                </#if>
            </div>
            <div>
                <#if preTaskRord.state && !taskRecord.state>
                    <span class="gray">该任务被</span><strong>重新打开</strong>
                </#if>
                <#if !preTaskRord.state && taskRecord.state>
                    <span class="gray">该任务被</span><strong>关闭</strong>
                </#if>
            </div>
        </#if>
    </div>
</div>
</#macro>
<#macro selectOption userMap designees>
<select multiple='multiple' style="width: 300px;margin-bottom: 10px" name="userIds" id="userIds">
    <#if userMap?size != 0>
        <#list userMap?keys as key>
        <optgroup label="${key}">
            <#list userMap[key] as user>
                <#assign flag = "true">
                <#list designees as designee>
                    <#if designee.id = user.id>
                        <#assign flag = "false">
                    </#if>
                </#list>
                <option value="${user.id}" <#if flag == "false">selected="true"</#if>>${user.userName}</option>
            </#list>
        </#list>
    </#if>
</select>
</#macro>
<#macro taskPercent task>
<select name="completeStatus" id="completeStatus">
    <option value="0" <#if task.completeStatus == 0>selected="true"</#if>>0%</option>
    <option value="50" <#if task.completeStatus == 50>selected="true"</#if>>50%</option>
    <option value="90" <#if task.completeStatus == 90>selected="true"</#if>>90%</option>
    <option value="100" <#if task.completeStatus == 100>selected="true"</#if>>100%</option>
</select>
</#macro>
<#macro taskComplete task>
<select name="state" id="state">
    <optgroup label="任务完成状态">
        <option id="off" value="false" <#if !task.state>selected="true" </#if>>未结束</option>

    <#-- 只有任务发布人才可以关闭 -->
        <#if task.releaser.id == Session.currentUser.id>
            <option value="true" <#if task.state>selected="true" </#if>>已结束</option>
        </#if>
    </optgroup>
</select>
</#macro>
