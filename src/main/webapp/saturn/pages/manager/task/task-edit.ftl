<#assign title="编辑任务">
<#-- 当前权限标识 -->

<#macro head>
</#macro>

<#macro content>
<div class="container">
<div class="row">
<form class="form-horizontal" id="task-form" action="task/edit" method="post">
<fieldset>
<legend>
    编辑任务
</legend>
<input type="hidden" name="id" value="${task.id}">

<div class="control-group">
    <label class="control-label">任务名称</label>

    <div class="controls">
        <textarea class="form-control" rows="3" style="width: 40%" id="title"
                  name="title">${task.title!}</textarea>
        <span id="title-input-help" class="help-inline"></span>
    </div>
</div>

<div class="control-group">
    <label class="control-label">负责人</label>

    <div class="controls">
        <input type="text" name="responsible" id="responsible" style="width: 40%" value="${task.responsible!}"/>
    </div>
</div>

<div class="control-group">
    <label class="control-label">参与人</label>

    <div class="controls">
        <input type="text" name="participate" id="participate" style="width: 40%" value="${task.participate!}"/>
    </div>
</div>

<div class="control-group">
    <label class="control-label">预计开始时间</label>

    <div class="controls">
        <input id="predictStart" type="text" class="form-control"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               name="predictStart"
               value="${task.predictStart?string('yyyy-MM-dd')}"/>
        <span id="predictStart-input-help" class="help-inline"></span>
    </div>
</div>

<div class="control-group">
    <label class="control-label">预计结束时间</label>

    <div class="controls">
        <input id="predictEnd" type="text" class="form-control"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
               name="predictEnd"
               value="${task.predictEnd?string('yyyy-MM-dd')}"/>
        <span id="predictEnd-input-help" class="help-inline"></span>
    </div>
</div>


<div class="control-group">
    <label class="control-label">任务描述</label>

    <div class="controls">
        <textarea class="form-control" rows="3" style="width: 90%" id="taskDescription"
                  name="taskDescription">${task.taskDescription}</textarea>
        <span id="taskDescription-input-help" class="help-inline"></span>
        <p> 您当前输入了 <span class="word_count_task_description">0</span> 个文字，
            <span class="word_surplus_task_description"></span>
        </p>
    </div>
</div>

<div class="control-group">
    <label class="control-label">任务目标</label>

    <div class="controls">
        <textarea class="form-control" rows="3" style="width: 90%" id="taskTarget"
                  name="taskTarget">${task.taskTarget}</textarea>
        <span id="taskTarget-input-help" class="help-inline"></span>
        <p> 您当前输入了 <span class="word_count_task_target">0</span> 个文字，
            <span class="word_surplus_task_target"></span>
        </p>
    </div>
</div>

<div class="control-group">
    <label class="control-label">需求保障</label>

    <div class="controls">
        <textarea class="form-control" rows="3" style="width: 90%" id="demand"
                  name="demand">${task.demand}</textarea>
        <span id="demand-input-help" class="help-inline"></span>
        <p> 您当前输入了 <span class="word_count_demand">0</span> 个文字，
            <span class="word_surplus_demand"></span>
        </p>
    </div>
</div>

<div class="control-group">
    <label class="control-label">备注</label>

    <div class="controls">
        <textarea class="form-control" rows="3" style="width: 90%" id="remark"
                  name="remark">${task.remark}</textarea>
        <span id="remark-input-help" class="help-inline"></span>
        <p> 您当前输入了 <span class="word_count_remark">0</span> 个文字，
            <span class="word_surplus_remark"></span>
        </p>
    </div>
</div>

<div class="control-group">
    <label class="control-label">创建人</label>

    <div class="controls">
        <#if task.createUser !=0>
            <#list users as user>
                <#if task.createUser == user.id><label class="control-label">${user.userName}</label></#if>
            </#list>
        <#else>
            <label class="control-label">无</label>
        </#if>
    </div>
</div>

<div class="control-group">
    <label class="control-label">创建时间</label>

    <div class="controls">
        <#if task.createDate??>
            <label class="control-label">${task.createDate?string('yyyy-MM-dd')}</label>
        <#else>
            <label class="control-label">无</label>
        </#if>
    </div>
</div>

<div class="control-group">
    <label class="control-label">审核人</label>

    <div class="controls">
        <#if task.checkUser !=1>
            <#list users as user>
                <#if task.checkUser == user.id><label class="control-label">${user.userName}</label></#if>
            </#list>
        <#else>
            <#if task.checkUser==1>
                <label class="control-label">超级管理员</label>
            <#else>
                <label class="control-label">无</label>
            </#if>
        </#if>
    </div>
</div>

<div class="control-group">
    <label class="control-label">审核时间</label>

    <div class="controls">
        <#if task.checkDate??>
            <label class="control-label">${task.checkDate?string('yyyy-MM-dd')}</label>
        <#else>
            <label class="control-label">无</label>
        </#if>
    </div>
</div>

<div class="control-group">
    <label class="control-label">作废人</label>

    <div class="controls">
        <#if task.invalidUser !=1>
            <#list users as user>
                <#if task.invalidUser == user.id><label class="control-label">${user.userName}</label></#if>
            </#list>
        <#else>
            <#if task.invalidUser==1>
                <label class="control-label">超级管理员</label>
            <#else>
                <label class="control-label">无</label>
            </#if>
        </#if>
    </div>
</div>

<div class="control-group">
    <label class="control-label">作废时间</label>

    <div class="controls">
        <#if task.invalidDate??>
            <label class="control-label">${task.invalidDate?string('yyyy-MM-dd')}</label>
        <#else>
            <label class="control-label">无</label>
        </#if>
    </div>
</div>

<div class="control-group">
    <label class="control-label">实际开始时间</label>

    <div class="controls">
        <#if task.realStart??>
            <label class="control-label">${task.realStart?string('yyyy-MM-dd')}</label>
        <#else>
            <label class="control-label">无</label>
        </#if>
    </div>
</div>

<div class="control-group">
    <label class="control-label">实际结束时间</label>

    <div class="controls">
        <#if task.realEnd??>
            <label class="control-label">${task.realEnd?string('yyyy-MM-dd')}</label>
        <#else>
            <label class="control-label">无</label>
        </#if>
    </div>
</div>

<div class="form-actions">
    <button type="submit" class="btn btn-primary" id="submit-btn">保存</button>
    <a class='btn' href="task/index">返回</a>
</div>
</fieldset>
</form>
</div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="../assets/kinkeditor/4.1.7/kindeditor-min.js"></script>
<script type="text/javascript" src="../assets/kinkeditor/4.1.7/lang/zh_CN.js"></script>
<script type="text/javascript" src="../assets/bootstrap/2.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../assets/artDialog/5.0.3-ec058ee8b2/artDialog.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/bootstrap/2.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="js/2datepicker-form-validation.js"></script>
<script type="text/javascript">
    $(function () {

        var editorTaskDescription, editorTaskTarget, editorDemand, editorRemark;
        (function () {
            var KIND_EDITOR_CONFIG1 = {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: true,
                items: [
                    'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', '|'],
                afterChange: function () {
//                $('.word_count_task_description').html(this.count()); //字数统计包含HTML代码
                    $('.word_count_task_description').html(this.count('text'));  //字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字
                    //////////
                    //限制字数
                    var limitNum = 5000;  //设定限制字数
                    var pattern = '还可以输入' + limitNum + '字。';
                    $('.word_surplus_task_description').html(pattern); //输入显示
                    console.log(this.count('text'));
                    if (this.count('text') > limitNum) {
                        pattern = ('字数超过限制，请适当删除部分内容。');
                        //超过字数限制自动截取
                        var strValue = editorTaskDescription.text();
                        console.log(strValue);
                        strValue = strValue.substring(0, limitNum);
                        editorTaskDescription.text(strValue);
                    } else {
                        //计算剩余字数
                        var result = limitNum - this.count('text');
                        pattern = '还可以输入' + result + '字。';
                    }
                    $('.word_surplus_task_description').html(pattern); //输入显示
                }
            };


            var KIND_EDITOR_CONFIG2 = {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: true,
                items: [
                    'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', '|'],
                afterChange: function () {
                    $('.word_count_task_target').html(this.count()); //字数统计包含HTML代码
//                $('.word_count2').html(this.count('text'));  //字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字
                    //////////
                    //限制字数
                    var limitNum = 5000;  //设定限制字数
                    var pattern = '还可以输入' + limitNum + '字。';
                    $('.word_surplus_task_target').html(pattern); //输入显示
                    if (this.count('text') > limitNum) {
                        pattern = ('字数超过限制，请适当删除部分内容。');
                        //超过字数限制自动截取
                        var strValue = editorTaskTarget.text();
                        strValue = strValue.substring(0, limitNum);
                        editorTaskTarget.text(strValue);
                    } else {
                        //计算剩余字数
                        var result = limitNum - this.count('text');
                        pattern = '还可以输入' + result + '字。';
                    }
                    $('.word_surplus_task_target').html(pattern); //输入显示
                }

            };

            var KIND_EDITOR_CONFIG3 = {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: true,
                items: [
                    'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', '|'],
                afterChange: function () {
                    $('.word_count_demand').html(this.count()); //字数统计包含HTML代码
//                $('.word_count2').html(this.count('text'));  //字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字
                    //////////
                    //限制字数
                    var limitNum = 5000;  //设定限制字数
                    var pattern = '还可以输入' + limitNum + '字。';
                    $('.word_surplus_demand').html(pattern); //输入显示
                    if (this.count('text') > limitNum) {
                        pattern = ('字数超过限制，请适当删除部分内容。');
                        //超过字数限制自动截取
                        var strValue = editorDemand.text();
                        strValue = strValue.substring(0, limitNum);
                        editorDemand.text(strValue);
                    } else {
                        //计算剩余字数
                        var result = limitNum - this.count('text');
                        pattern = '还可以输入' + result + '字。';
                    }
                    $('.word_surplus_demand').html(pattern); //输入显示
                }

            };

            var KIND_EDITOR_CONFIG4 = {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: true,
                items: [
                    'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', '|'],
                afterChange: function () {
                    $('.word_count_remark').html(this.count()); //字数统计包含HTML代码
//                $('.word_count2').html(this.count('text'));  //字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字
                    //////////
                    //限制字数
                    var limitNum = 5000;  //设定限制字数
                    var pattern = '还可以输入' + limitNum + '字。';
                    $('.word_surplus_remark').html(pattern); //输入显示
                    if (this.count('text') > limitNum) {
                        pattern = ('字数超过限制，请适当删除部分内容。');
                        //超过字数限制自动截取
                        var strValue = editorRemark.text();
                        strValue = strValue.substring(0, limitNum);
                        editorRemark.text(strValue);
                    } else {
                        //计算剩余字数
                        var result = limitNum - this.count('text');
                        pattern = '还可以输入' + result + '字。';
                    }
                    $('.word_surplus_remark').html(pattern); //输入显示
                }

            };
            KindEditor.ready(function (K) {
                editorTaskDescription = K.create('#taskDescription', KIND_EDITOR_CONFIG1);
                editorTaskTarget = K.create('#taskTarget', KIND_EDITOR_CONFIG2);
                editorDemand = K.create('#demand', KIND_EDITOR_CONFIG3);
                editorRemark = K.create('#remark', KIND_EDITOR_CONFIG4);
            });
        })();


        $("#task-form").validate({
            rules: {
                title: {
                    required: true,
                    maxlength: 50
                },
                taskDescription: {
                    required: true,
                    maxlength: 1000
                },
                taskTarget: {
                    required: true,
                    maxlength: 1000
                },
                demand: {
                    required: true,
                    maxlength: 1000
                },
                remark: {
                    maxlength: 500
                },
                predictStart: {
                    required: true,
                    rangelength: [2, 10]
                },
                predictEnd: {
                    required: true,
                    rangelength: [2, 10]
                }

            },
            messages: {
                title: {
                    required: "请输入任务名称"
                },
                predictStart: {
                    required: "请选择预计开始时间"
                },
                predictEnd: {
                    required: "请选择预计结束时间"
                },
                taskDescription: {
                    required: "请输入任务描述"
                },
                taskTarget: {
                    required: "请输入任务目标"
                },
                demand: {
                    required: "请输入需求保障"
                },
                remark: {
                    required: "备注字数过长"
                }
            },
            errorPlacement: function (error, element) {
                if (element.is(":radio")) {
                    error.appendTo(element.parent());
                } else if (element.is(":checkbox")) {
                    error.appendTo(element.parent());
                } else {
                    error.appendTo(element.next());
                }
            }
        });
    });
</script>
</#macro>
