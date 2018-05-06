<#assign title="展示内容 - 修改">
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
        <form id="mediaDetailFormEdit" enctype="multipart/form-data" class="form-horizontal"
              action="manager/editMediaDetail" method="post">
            <fieldset>
                <legend>
                    修改展示内容
                </legend>
                <input type="hidden" name="id" value="${mediaDetail.id}"/>

                <div class="control-group">
                    <div class="controls">
                        <span class="label">展示类别</span>
                        <select name="mediaShowId" id="selectMediaShow">
                            <option value="0">请选择</option>
                            <#list mediaShows as mediaShow>
                                <option value="${mediaShow.id}"
                                        <#if mediaDetail.mediaShowId == mediaShow.id>selected="true"</#if>>${mediaShow.title}</option>
                            </#list>
                        </select>
                        <span class="label">是否启用</span>
                        <select name="enable" id="enableForEdit">
                            <option value="1" <#if mediaDetail.enable == 1>selected="true"</#if>>启用</option>
                            <option value="2" <#if mediaDetail.enable == 2>selected="true"</#if>>禁用</option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <span class="label">展示标题</span>
                        <input type="text" name="detailTitle" id="detailTitle" value="${mediaDetail.detailTitle}">
                        <span class="label">副展示标题</span>
                        <input type="text" name="detailSubTitle" id="detailSubTitle"
                               value="${mediaDetail.detailSubTitle}">
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <span class="label">主展示内容</span>
                        <textarea name="detailContent" id="detailContent" cols="30"
                                  rows="5">${mediaDetail.detailContent}</textarea>
                        <span class="label">副展示内容</span>
                        <textarea name="detailSubDesc" id="detailSubDesc" cols="30"
                                  rows="5">${mediaDetail.detailSubDesc}</textarea>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="span12">
                            <div class="span6" style="width: 300px;">
                                <span class="label">主图片浏览</span>
                                <img id="mainUploadPreview" style="max-width: 200px;max-height: 200px"
                                     src="${imagePath}\${mediaDetail.detailImage}" alt=""/>
                                <input type="hidden" name="detailImage" value="${mediaDetail.detailImage!}"/>
                            </div>
                            <div class="span6" style="width: 300px;">
                                <span class="label">副图片浏览</span>
                                <img id="subUploadPreview" style="max-width: 200px;max-height: 200px"
                                     src="${imagePath}\${mediaDetail.detailSubImage}" alt=""/>
                                <input type="hidden" name="detailSubImage" value="${mediaDetail.detailSubImage!}"/>
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
                               name="pubDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                               value="${(mediaDetail.pubDate!)?string("yyyy-MM-dd")!}"/>
                        <span class="label">材质</span>
                        <select name="material" id="materialForEdit" class="control-input">
                            <option value="0">请选择</option>
                            <#list textures as texture>
                                <option value="${texture.id}"
                                        <#if (mediaDetail.material)?number == texture.id>selected="true"</#if>>${texture.name}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="form-actions">
                    <button class="btn btn-primary update">保存</button>
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
        $(".update").click(function () {
            $("#mediaDetailFormEdit").submit();
        });
    })
</script>
</#macro>