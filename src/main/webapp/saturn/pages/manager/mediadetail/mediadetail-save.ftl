<#assign title="展示内容 - 新增">
<#-- 当前权限标识 -->

<#macro head>
<style type="text/css">
    .label {
        width: 80px;
        text-align: center;
        font-weight: normal;
        color: #000000;
        background-color: #ffffff;
    }

    .control-input {
        width: 220px;
    }
</style>
</#macro>

<#macro content>
<div class="container">
    <div class="row-fluid">
        <form id="mediaDetailFormSave" enctype="multipart/form-data" class="form-horizontal"
              action="manager/saveMediaDetail" method="post">
            <fieldset>
                <legend>
                    新增展示内容
                </legend>
                <div class="control-group">
                    <div class="controls">
                        <span class="label">展示类别</span>
                        <select name="mediaShowId" id="selectMediaShow">
                            <option value="0">请选择</option>
                            <#list mediaShows as mediaShow>
                                <option value="${mediaShow.id}">${mediaShow.title}</option>
                            </#list>
                        </select>


                        <span class="label">是否启用</span>
                        <select name="enable" id="enableForSave">
                            <option value="1">启用</option>
                            <option value="2">禁用</option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <span class="label">展示标题</span>
                        <input type="text" name="detailTitle" id="detailTitle">
                        <span class="label">副展示标题</span>
                        <input type="text" name="detailSubTitle" id="detailSubTitle">
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <span class="label">主展示内容</span>
                        <textarea name="detailContent" id="detailContent" cols="30" rows="5"></textarea>
                        <span class="label">副展示内容</span>
                        <textarea name="detailSubDesc" id="detailSubDesc" cols="30" rows="5"></textarea>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="span12">
                            <div class="span6" style="width: 300px;">
                                <span class="label">主图片浏览</span>
                                <img id="mainUploadPreview" style="max-width: 200px;background-color: red;max-height: 200px"/>
                            </div>
                            <div class="span6" style="width: 300px;">
                                <span class="label">副图片浏览</span>
                                <img id="subUploadPreview" style="background-color: red;max-width: 200px;max-height: 200px"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <span class="label">主图片</span>
                        <input type="file" name="detailImageFile" id="detailImage" style="width:220px">
                        <span class="label">副图片</span>
                        <input type="file" name="detailSubImageFile" id="detailSubImage" style="width:220px">
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <span class="label">时间</span>
                        <input class="Wdate" id="pubDate" type="text"
                               name="pubDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                        <span class="label">材质</span>
                        <select name="material" id="materialForSave" class="control-input">
                            <option value="0">请选择</option>
                            <#list textures as texture>
                                <option value="${texture.id}">${texture.name}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary save">保存</button>
                    <a class='btn' href="manager/mediaDetails">返回</a>
                </div>
            </fieldset>
        </form>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/artDialog/5.0.1-7012746/artDialog.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="/galaxy/assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="/galaxy/assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="../assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="../assets/bootstrap/2.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#detailImage").on('change',function(){
            var files = !!this.files ? this.files : [];

            // If no files were selected, or no FileReader support, return
            if (!files.length || !window.FileReader) return;

            // Only proceed if the selected file is an image
            if (/^image/.test( files[0].type)){

                // Create a new instance of the FileReader
                var reader = new FileReader();

                // Read the local file as a DataURL
                reader.readAsDataURL(files[0]);

                // When loaded, set image data as background of div
                reader.onloadend = function(){

                    $("#mainUploadPreview").attr("src", this.result);

                }

            }
        });
        $("#detailSubImage").on('change',function(){
            var files = !!this.files ? this.files : [];

            // If no files were selected, or no FileReader support, return
            if (!files.length || !window.FileReader) return;

            // Only proceed if the selected file is an image
            if (/^image/.test( files[0].type)){

                // Create a new instance of the FileReader
                var reader = new FileReader();

                // Read the local file as a DataURL
                reader.readAsDataURL(files[0]);

                // When loaded, set image data as background of div
                reader.onloadend = function(){

                    $("#subUploadPreview").attr("src", this.result);

                }

            }
        });
        $(".save").click(function () {
            $("#mediaDetailFormSave").submit();
        });
//        $("#mediaDetailFormSave").validate({
//            rules: {
//                detailTitle: {
//                    required: true,
//                    maxlength: 20
//                },
//                pubDate: {
//                    required: true,
//                    rangelength: [2, 10]
//                },
//                detailSubTitle: {
//                    required: true,
//                    maxlength: 50
//                },
//                line: {
//                    required: true,
//                    maxlength: 50
//                },
//                detailContent: {
//                    maxlength: 200
//                },
//                detailSubDesc: {
//                    maxlength: 200
//                }
//            },
//            messages: {
//                detailTitle: {
//                    required: "请输入主标题"
//                },
//                pubDate: {
//                    required: "请输入发表日期"
//                },
//                detailSubTitle: {
//                    required: "请输入副标题"
//                },
//                line: {
//                    required: "请输入序号"
//                },
//                detailContent: {
//                    required: "请输入主内容"
//                },
//                title: {
//                    required: "请输入副内容"
//                }
//            },
//            errorPlacement: function (error, element) {
//                if (element.is(":radio")) {
//                    error.appendTo(element.parent());
//                } else if (element.is(":checkbox")) {
//                    error.appendTo(element.parent());
//                } else {
//                    error.appendTo(element.next());
//                }
//            }
//        });
    })
</script>
</#macro>