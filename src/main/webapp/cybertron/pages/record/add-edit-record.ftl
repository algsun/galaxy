<#assign title = "首页 - 档案管理">
<#include "/common/pages/common-tag.ftl"/>

<#macro head>
<link rel="stylesheet" type="text/css" href="../assets/jquery-easyui/1.3.4/themes/default/easyui.css">
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>

<style type="text/css">
        /*照片上传 input file 样式*/
    #uploadFile {
        font-size: 12px;
        overflow: hidden;
        position: absolute
    }

    #fileInput {
        position: absolute;
        width: 74px;
        z-index: 100;
        opacity: 0;
        filter: alpha(opacity=0);
        margin-top: -5px;
    }
</style>
<style type="text/css">

    #addRecord .controls {
        margin-left: 80px;
    }

    #addRecord .control-label {
        width: 100px;
        margin-right: 15px;
    }

</style>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span12">
        <div class="page-header title">
            <h3>
                <#if !record??>
                    <span class="f-n">添加档案</span>
                <#else>
                    <span class="f-n">编辑档案</span>
                </#if>

                <a class="f-n" style="font-size: 0.7em;" href="volume/${volumeCode}/records">/返回</a></h3>
        </div>
    </div>
</div>

<div class="row-fluid">
    <div id="addRecord">
        <form class="form-horizontal"
            <#if record??>
              action="editRecord"
            <#else>
              action="addRecord"
            </#if>

              id="addRecordForm">
            <input type="hidden" value="<#if volumeCode gt 9>${volumeCode}<#else>${record.volumeCode}</#if>"
                   name="record.volumeCode" id="volumeCode"/>
            <input type="hidden" value="" id="attachement" name="files">
            <input type="hidden" value="<#if record??>${record.uuid}</#if>" name="record.uuid"/>
            <input type="hidden" value="${code!-1}" name="code"/>

            <div class="control-group">
                <label class="control-label">档号</label>

                <div class="controls">
                    <div class="input-prepend">
                        <span class="add-on" id="officialId" data-Id="${officialId}"
                              data-VolumeCode="<#if volumeCode gt 9>${volumeCode}<#else>${record.volumeCode}</#if>">${officialId}
                            -<#if volumeCode gt 9>${volumeCode}<#else>${record.volumeCode}</#if></span>
                        <input class="input-small required digits" id="recode-code" name="record.recordCode" type="text"
                               maxlength="5"
                               placeholder="档号"  <#if record??>
                               value="${code}"
                        </#if>>
                    </div>
                    <p id="recordHelp" class="help-block m-t-10 m-l-30 red"></p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">案卷题名</label>

                <div class="controls">
                    <input id="recordName" name="record.name"
                           value="<#if !record??>${volume.name}<#else>${record.name}</#if>"
                            />
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">密级</label>
                <#if record??>
                    <#assign stateSecrets=record.stateSecrets/>
                <#else>
                    <#assign stateSecrets=0/>
                </#if>
                <div class="controls">
                    <select name="record.stateSecrets">
                        <option value="1" <#if stateSecrets==1>
                                selected
                        </#if>>公开
                        </option>
                        <option value="2" <#if stateSecrets==2>
                                selected
                        </#if>>国内
                        </option>
                        <option value="3" <#if stateSecrets==3>
                                selected
                        </#if>>内部
                        </option>
                        <option value="4" <#if stateSecrets==4>
                                selected
                        </#if>>秘密
                        </option>
                        <option value="5" <#if stateSecrets==5>
                                selected
                        </#if>>机密
                        </option>
                        <option value="6" <#if stateSecrets==6>
                                selected
                        </#if>>绝密
                        </option>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">立卷单位名称</label>

                <div class="controls">
                    <input name="record.department" required  <#if record??>
                           value="${record.department}"
                    </#if>/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">立卷日期</label>

                <div class="controls">
                    <input name="record.establishDate"
                           onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
                           required  <#if record??>
                           value="${record.establishDate?string("yyyy-MM-dd")}"
                    </#if>/>
                </div>
            </div>
        </form>
        <div>
            <div class="offset2 b-t-10">
                <button id="addRecordBtn" class="btn btn-primary">确认</button>
            </div>
        </div>
    </div>
    <legend class="m-r-50">
        <span>文档</span>
    </legend>
</div>
<div id="file-list" class="row-fluid">
    <div class="span8">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>序号</th>
                <th>名称</th>
                <th>上传日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                <#if record??>
                    <#if record.attachments??>
                        <#list record.attachments as attachment>
                        <tr align="center">
                            <td> ${attachment_index+1}</td>
                            <td>
                                <div>
                                    <#assign type=attachment.uploadName?substring(attachment.uploadName?index_of(".")+1)/>
                                    <img alt="" style="width:20px;height: 20px"
                                        <#if type=="jpg"||type=="JPG"||type=="png"||type=="PNG">
                                         src="images/jpg.png"
                                        <#elseif type=="pdf">
                                         src="images/pdf.png"
                                        <#elseif type=="txt"||type=="TXT">
                                         src="images/txt.png"
                                        <#elseif type=="doc"||type=="docx">
                                         src="images/word.png"
                                        <#elseif type=="xls"||type=="xlsx">
                                         src="images/excel.png"
                                        <#else>
                                         src="images/unknow.png"
                                        </#if>
                                            >
                                ${attachment.uploadName}
                                </div>
                            </td>
                            <td>${attachment.uploadDate?string("yyyy-MM-dd")}</td>
                            <td>
                                <button class="btn btn-danger btn-mini deleteFile"
                                        data-Attachment-Id="${attachment.uuid}"
                                        title="删除文档">删除
                                </button>
                            </td>
                        </tr>
                        </#list>

                    </#if>

                </#if>

            </tbody>
        </table>
    </div>
</div>
<div class="row-fluid">
    <form id="uploadForm" action="cybertron/uploadFiles.action"
          enctype="multipart/form-data" method="post">
        <input type="file" name="file" id="fileInput" size="1">
        <input type="hidden" name="fileName" id="fileName" value=""/>
        <a href="#" id="uploadFile" class="btn btn-info">上传文档</a>
    </form>
</div>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/angular/1.2.20/angular.min.js"></script>
<script type="text/javascript" src="../assets/angular/1.2.20/angular-sanitize.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/jquery-easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../assets/jquery-form-plugin/3.19/jquery.form.js"></script>
    <@scriptTag "../assets/jquery-validation/1.11.1/dist/jquery.validate.js"/>
    <@scriptTag "../assets/jquery-validation/1.11.1/localization/messages_zh.js"/>
    <@scriptTag "js/record/addRecord.js"/>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="js/pnotify.js"></script>
<script type="text/javascript">
    $(function () {


        $(document).on('click', '.subsystemHead', function () {
            art.dialog({
                title: '正在努力加载!'
            });
        });

    })

</script>
</#macro>