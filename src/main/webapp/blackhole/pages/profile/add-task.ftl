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
    <link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
</head>
<body>

<!-- 导航栏 -->
<#--当前一级权限ID-->
<#assign currentTopPrivilege = "blackhole:profile">
<#include "../_common/header.ftl">

<div class="container-wrapper">
    <div id="container" class="container">
    <#include "../_common/sub-navs.ftl">
    <@subNavs "blackhole:profile:mytask"></@subNavs>


    <#include "../_common/message-tooltip.ftl">

        <div class="row">
            <div class="span12">
                <form action="addTask.action" method="post" class="form-horizontal">
                    <fieldset>
                        <legend>新建任务</legend>
                    </fieldset>
                    <div class="control-group">
                        <label class="control-label"><em class="red">*</em>任务标题:</label>
                        <div class="controls">
                            <input type="text" name="taskTitle" id="taskTitle" style="margin-left: 10px;"/>
                            <span class="help-inline red" id="taskTitleSpan"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"><em class="red">*</em>任务描述:</label>
                        <div class="controls">
                            <textarea name="taskInfo" id="taskInfo" cols="30" rows="5"
                                      style="margin-left: 10px;width: 400px"></textarea>
                            <span class="help-inline red" id="taskInfoSpan"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"><em class="red">*</em>截至时间:</label>
                        <div class="controls">
                            <input class="Wdate" type="text" name="endDate" id="endDate"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd ', minDate:'%y-%M-{%d}'})"
                                   style="margin-left: 10px;"/>
                            <span class="help-inline red" id="endDateSpan"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label"><em class="red">*</em>接受人:</label>

                        <div class="controls">
                            <@selectOption userMap/>
                            <span class="help-block red" id="designeeSpan"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label"></label>
                        <div class="controls">
                            <button type="submit" class="btn btn-primary save-btn">添加</button>
                            <a class="btn m-l-20" href="listTask.action">返回</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<!-- 页面底部 -->
<#include "../_common/footer.ftl">
<#--公共JS-->
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../common/js/form-check.js"></script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#userIds").select2();

        (function () {
            $(document).on('click', '.save-btn', function () {
                var flag1 = false;
                var flag2 = false;
                var flag3 = false;
                var flag4 = false;

                var $taskTitle = $("#taskTitle");
                var $taskInfo = $("#taskInfo");
                var $endDate = $("#endDate");
                var $userIds = $("#userIds");

                var $taskTitleSpan = $("#taskTitleSpan");
                var $taskInfoSpan = $("#taskInfoSpan");
                var $endDateSpan = $("#endDateSpan");
                var $designeeSpan = $("#designeeSpan");

                var taskTitleFlag = verifyIsNull($taskTitle);
                var taskInfoFlag = verifyIsNull($taskInfo);
                var endDateFlag = verifyIsNull($endDate);

                //--taskTitle--任务标题 非空验证 start
                if (!taskTitleFlag) {
                    App.helpError($taskTitleSpan, "任务标题不能为空");
                    flag1 = false;
                } else {
                    App.helpOk($taskTitleSpan, '');
                    flag1 = true;
                }
                //----任务标题 非空验证 end

                //--taskInfo--任务描述 非空验证 start
                if (!taskInfoFlag) {
                    App.helpError($taskInfoSpan, "任务描述不能为空");
                    flag2 = false;
                } else {
                    App.helpOk($taskInfoSpan, '');
                    flag2 = true;
                }
                //----任务描述 非空验证 end

                //--endDate--任务截止时间 非空验证 start
                if (!endDateFlag) {
                    App.helpError($endDateSpan, "任务截止时间不能为空");
                    flag3 = false;
                } else {
                    App.helpOk($endDateSpan, '');
                    flag3 = true;
                }
                //----任务截止时间 end

                //--userId-- 任务指派人id 非空验证，不等于0验证
                if ($userIds.val() == null) {
                    App.helpError($designeeSpan, "请选择任务接受人");
                    flag4 = false;
                } else {
                    App.helpOk($designeeSpan, '');
                    flag4 = true;
                }
                //---- 任务指派人 非空验证，不等于0验证
                if (flag1 && flag2 && flag3 && flag4) {
                    return true;
                } else {
                    return false;
                }

            });
            var verifyIsNull = function ($this) {
                if (!$.trim($this.val())) {
                    return false;
                } else {
                    return true;
                }
            };
        })();
    });
</script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
<#macro selectOption userMap>
<select multiple='' style="width: 300px;margin-left: 8px;" name="userIds" id="userIds">
    <#if userMap?size != 0>
        <#list userMap?keys as key>
        <optgroup label="${key}">
            <#list userMap[key] as user>
                <option value="${user.id}">${user.userName}</option>
            </#list>
        </#list>
    </#if>
</select>
</#macro>

