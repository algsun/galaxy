<#assign title="成果展示 - 文献资料 - 添加">
<#-- 当前权限标识 -->

<#macro head>
<#--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>-->
<link rel="stylesheet" href="/galaxy/assets/my97-DatePicker/4.8-beta3/skin/WdatePicker.css"/>
</#macro>

<#macro content>
<div class="container">
    <div class="row">
        <form id="literature-form" class="form-horizontal" action="manager/literatures/save" method="post">
            <fieldset>
                <legend>
                    编辑文献资料
                </legend>
                <div class="control-group">
                    <label for="catalog" class="control-label">文献类型</label>

                    <div class="controls">
                        <select name="catalog" id="catalog">
                            <option value="1">论文</option>
                            <option value="2">著作</option>
                            <option value="3">其他</option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label for="author" class="control-label">作者</label>

                    <div class="controls">
                        <input type="text" id="author" name="author">
                        <span id="author-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label for="publicDate" class="control-label">发表日期</label>

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
                    <label for="periodical" class="control-label">期别</label>

                    <div class="controls">
                        <input type="text" id="periodical" name="periodical">
                        <span id="periodical-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label for="keywords" class="control-label">关键字</label>

                    <div class="controls">
                        <input type="text" id="keywords" name="keywords">
                        <span id="keywords-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label for="title" class="control-label">文献标题</label>

                    <div class="controls">
                        <input type="text" id="title" name="title">
                        <span id="title-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label for="summary" class="control-label">摘要</label>

                    <div class="controls">
                        <textarea id="summary" name="summary"></textarea>
                        <span id="summary-input-help" class="help-inline"></span>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">保存</button>
                    <a class="btn btn-default" href="manager/literature">返回</a>
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

        $("#literature-form").validate({
            rules: {
                author: {
                    required: true,
                    maxlength: 20
                },
                publicDate: {
                    required: true,
                    rangelength: [2, 10]
                },
                magazine: {
                    required: true,
                    maxlength: 50
                },
                periodical: {
                    required: true,
                    maxlength: 50
                },
                keywords: {
                    required: true,
                    maxlength: 50
                },
                title: {
                    required: true,
                    maxlength: 50
                },
                summary: {
                    maxlength: 200
                }
            },
            messages: {
                author: {
                    required: "请输入作者"
                },
                publicDate: {
                    required: "请输入发表日期"
                },
                magazine: {
                    required: "请输入发表期刊"
                },
                periodical: {
                    required: "请输入期别"
                },
                keywords: {
                    required: "请输入关键字"
                },
                title: {
                    required: "请输入文献标题"
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