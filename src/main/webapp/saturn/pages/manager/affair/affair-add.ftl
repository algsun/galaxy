<#assign title="成果展示 - 专业成果 - 添加">
<#-- 当前权限标识 -->

<#macro head>
<link rel="stylesheet" href="/galaxy/assets/my97-DatePicker/4.8-beta3/skin/WdatePicker.css"/>
</#macro>

<#macro content>
<div class="container">
    <div class="row">
        <form id="affair-form" class="form-horizontal" action="manager/affairs/save" method="post">
            <fieldset>
                <legend>
                    添加专业成果
                </legend>
                <div class="control-group">
                    <label for="type" class="control-label">事务类型</label>

                    <div class="controls">
                        <select name="type" id="type">
                            <option value="1">发表论文/著作</option>
                            <option value="2">负责项目</option>
                            <option value="3">其他服务</option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label for="incharge" class="control-label">姓名</label>

                    <div class="controls">
                        <input type="text" id="incharge" name="incharge">
                        <span id="incharge-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label for="title" class="control-label">事务名称</label>

                    <div class="controls">
                        <input type="text" id="title" name="title">
                        <span id="title-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label for="publicDate" class="control-label">日期</label>

                    <div class="controls">
                        <input type="text" class="Wdate" id="publicDate" name="publicDate"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'%y-%M-{%d}'})">
                        <span id="publicDate-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label for="magazine" class="control-label">发表期刊</label>

                    <div class="controls">
                        <input type="text" id="magazine" name="magazine">
                        <span id="magazine-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label for="magazineSrc" class="control-label">刊源</label>

                    <div class="controls">
                        <input type="text" id="magazineSrc" name="magazineSrc">
                        <span id="magazineSrc-input-help" class="help-inline"></span>
                    </div>
                </div>

                <div class="control-group">
                    <label for="projectTYpe" class="control-label">项目属性</label>

                    <div class="controls">
                        <select name="projectType" id="projectType">
                            <option value="0">请选择</option>
                            <option value="1">科研</option>
                            <option value="2">修复</option>
                        </select>
                    </div>
                </div>

                <div class="control-group">
                    <label for="projectLevel" class="control-label">项目级别</label>

                    <div class="controls">
                        <select name="projectLevel" id="projectLevel">
                            <option value="1">请选择</option>
                            <option value="1">院级</option>
                            <option value="2">省级</option>
                            <option value="3">国家级</option>
                        </select>
                    </div>
                </div>

                <div class="control-group">
                    <label for="progress" class="control-label">进展情况</label>

                    <div class="controls">
                        <textarea id="progress" name="progress"></textarea>
                        <span id="progress-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label for="prize" class="control-label">获奖情况</label>

                    <div class="controls">
                        <textarea id="prize" name="prize"></textarea>
                        <span id="prize-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label for="remark" class="control-label">备注</label>

                    <div class="controls">
                        <textarea id="remark" name="remark"></textarea>
                        <span id="remark-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">保存</button>
                    <a class="btn btn-default" href="manager/affair">返回</a>
                </div>
            </fieldset>
        </form>
    </div
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="/galaxy/assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="/galaxy/assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="/galaxy/assets/my97-DatePicker/4.8-beta3/WdatePicker.js"></script>
<script type="text/javascript">
    $(function () {

        $("#affair-form").validate({
            rules: {
                incharge: {
                    required: true,
                    maxlength: 20
                },
                title: {
                    required: true,
                    maxlength: 20
                },
                publicDate: {
                    required: true
                }
            },
            messages: {
                incharge: {
                    required: "请输入姓名"
                },
                title: {
                    required: "请输入事务名称"
                },
                publicDate: {
                    required: "请输入日期"
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