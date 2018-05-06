<#assign title="处理任务记录">
<#-- 当前权限标识 -->

<#macro head>
<link rel="stylesheet" href="assets/bootstrap/2.3.1/css/bootstrap.min.css">
</#macro>

<#macro content>
<div class="container">
    <div class="row">
        <form class="form-horizontal" id="task-handle-form" action="task_records/do_handle" method="post">
            <input type="hidden" id="id" name="id" value="${taskRecordVO.id}"/>
            <input type="hidden" id="taskId" name="taskId" value="${taskRecordVO.taskId}"/>
            <fieldset>
                <legend>
                    处理任务记录
                </legend>
                <div class="control-group">
                    <label class="control-label">记录内容</label>

                    <div class="controls">
                        <textarea class="form-control" rows="5" style="width: 90%" id="recordContent"
                                  readonly="readonly"
                                  name="recordContent">${taskRecordVO.recordContent!}</textarea>
                        <span id="title-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">重要标识</label>

                    <div class="controls">
                        <label>
                            <#if taskRecordVO.important == 1>
                                否
                            <#else>
                                是
                            </#if>
                        </label>
                    </div>
                </div>


                <div class="control-group">
                    <label class="control-label">记录人</label>

                    <div class="controls">
                        <input id="recordUserName" type="text" value="${taskRecordVO.recordUserName!}"
                               readonly="readonly">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">记录时间</label>

                    <div class="controls">
                        <input id="recordDate" type="text" class="form-control"
                               name="recordDate" readonly="readonly"
                               value="${taskRecordVO.recordDate?string('yyyy-MM-dd')}"/>
                        <span id="recordDate-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">处理结果</label>

                    <div class="controls">
                        <textarea class="form-control" rows="5" style="width: 90%" id="handleResult"
                                  name="handleResult">${taskRecordVO.handleResult!}</textarea>
                        <span id="title-input-help" class="help-inline"></span>
                        <p> 您当前输入了 <span class="word_count_handle_result">0</span> 个文字，
                            <span class="word_surplus_handle_result"></span>
                        </p>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">处理人</label>

                    <div class="controls">
                        <input type="text" name="handleUserName" id="handleUserName" value="${taskRecordVO.handleUserName!}" />
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">处理时间</label>

                    <div class="controls">
                        <input id="handleDate" type="text" class="form-control" name="handleDate"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
                               value="<#if taskRecordVO.handleDate??>${taskRecordVO.handleDate?string('yyyy-MM-dd')}<#else>${handleDate?string('yyyy-MM-dd')}</#if>"/>
                        <span id="handleDate-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary" id="submit-btn">保存</button>
                    <a class='btn' href="task_records/${taskRecordVO.taskId!}/index">返回</a>
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
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="js/2datepicker-form-validation.js"></script>
<script type="text/javascript">

    var editorTaskDescription, editorTaskTarget;
    (function () {
        var KIND_EDITOR_CONFIG1 = {
            resizeType: 1,
            allowPreviewEmoticons: false,
            readonlyMode:true,
            items: [
                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', '|']
        };


        var KIND_EDITOR_CONFIG2 = {
            resizeType: 1,
            allowPreviewEmoticons: false,
            items: [
                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', '|'],
            afterChange: function () {
                $('.word_count_handle_result').html(this.count()); //字数统计包含HTML代码
//                $('.word_count2').html(this.count('text'));  //字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字
                //////////
                //限制字数
                var limitNum = 5000;  //设定限制字数
                var pattern = '还可以输入' + limitNum + '字。';
                $('.word_surplus_handle_result').html(pattern); //输入显示
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
                $('.word_surplus_handle_result').html(pattern); //输入显示
            }

        };

        KindEditor.ready(function (K) {
            editorTaskDescription = K.create('#recordContent', KIND_EDITOR_CONFIG1);
            editorTaskTarget = K.create('#handleResult', KIND_EDITOR_CONFIG2);
        });
    })();

    $(function () {

        $("#task-handle-form").validate({
            rules: {
                handleResult: {
                    required: true,
                    maxlength: 500
                }
            },
            messages: {
                handleResult: {
                    required: "请输入处理结果"
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
