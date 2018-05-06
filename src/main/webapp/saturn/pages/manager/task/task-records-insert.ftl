<#assign title="添加任务记录">
<#-- 当前权限标识 -->

<#macro head>
<link rel="stylesheet" href="assets/bootstrap/2.3.1/css/bootstrap.min.css">
</#macro>

<#macro content>
<div class="container">
    <div class="row">
        <form class="form-horizontal" id="task-record-form" action="task_records/do_insert" method="post">
            <input type="hidden" id="taskId" name="taskId" value="${taskId!}"/>
            <fieldset>
                <legend>
                    添加任务记录
                </legend>
                <div class="control-group">
                    <label class="control-label">记录内容</label>

                    <div class="controls">
                        <textarea class="form-control" rows="5" style="width: 90%" id="recordContent"
                                  name="recordContent"></textarea>
                        <span id="title-input-help" class="help-inline"></span>
                        <p> 您当前输入了 <span class="word_count_record_content">0</span> 个文字，
                            <span class="word_surplus_record_content"></span>
                        </p>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">重要标识</label>

                    <div class="controls">
                        <select id="important" name="important">
                            <option value="1">否</option>
                            <option value="0">是</option>
                        </select>
                        <span id="title-input-help" class="help-inline"></span>
                    </div>
                </div>


                <div class="control-group">
                    <label class="control-label">记录人</label>

                    <div class="controls">
                        <input type="text" id="recordUserName" name="recordUserName"/>
                        <#--<select id="recordUserId" name="recordUserId">-->
                            <#--<#list users as user>-->
                                <#--<option <#if user.id == userId>selected="selected"</#if> value="${user.id}">${user.userName}</option>-->
                            <#--</#list>-->
                        <#--</select>-->
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">记录时间</label>

                    <div class="controls">
                        <input id="recordDate" type="text" class="form-control"
                               name="recordDate"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
                               value="${recordDate?string('yyyy-MM-dd')}"/>
                        <span id="recordDate-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">处理结果</label>

                    <div class="controls">
                        <textarea class="form-control" rows="5" style="width: 90%" id="handleResult"
                                  readonly="readonly"></textarea>
                        <span id="title-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">处理人</label>

                    <div class="controls">
                        <input type="text" id="handleUserId" readonly="readonly"/>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">处理时间</label>

                    <div class="controls">
                        <input id="handleDate" type="text" class="form-control"
                               readonly="readonly"/>
                        <span id="handleDate-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary" id="submit-btn">保存</button>
                    <a class='btn' href="task_records/${taskId!}/index">返回</a>
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
            allowImageUpload: true,
            items: [
                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', '|'],
            afterChange: function () {
//                $('.word_count_task_description').html(this.count()); //字数统计包含HTML代码
                $('.word_count_record_content').html(this.count('text'));  //字数统计包含纯文本、IMG、EMBED，不包含换行符，IMG和EMBED算一个文字
                //////////
                //限制字数
                var limitNum = 5000;  //设定限制字数
                var pattern = '还可以输入' + limitNum + '字。';
                $('.word_surplus_record_content').html(pattern); //输入显示
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
                $('.word_surplus_record_content').html(pattern); //输入显示
            }
        };


        var KIND_EDITOR_CONFIG2 = {
            resizeType: 1,
            allowPreviewEmoticons: false,
            allowImageUpload: true,
            readonlyMode:true,
            items: [
                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', '|']
        };

        KindEditor.ready(function (K) {
            editorTaskDescription = K.create('#recordContent', KIND_EDITOR_CONFIG1);
            editorTaskTarget = K.create('#handleResult', KIND_EDITOR_CONFIG2);
        });
    })();


    $(function () {

        $("#task-record-form").validate({
            rules: {
                recordContent: {
                    required: true,
                    maxlength: 5000
                },
                recordUserId:{
                    required:true
                }
            },
            messages: {
                recordContent: {
                    required: "请输入记录内容"
                },
                recordContent: {
                    required: "请输入记录人"
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
