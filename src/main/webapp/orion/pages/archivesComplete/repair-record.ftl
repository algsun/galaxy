<form id="repairRecordConfirmForm" class="form-horizontal" action="archives/saveRepairProcessRecord">
    <input type="hidden" id="repairProcessRecordHiddenId" name="repairRecordId"/>
    <input type="hidden" name="relicId" value="${relicId!}"/>
    <table border="1px" style="border:2px #000 solid;">
        <caption align="top"><h3>文物保护修复过程记录表</h3></caption>
        <tr style="height: 400px">
            <td colspan="20">
                <textarea style="width: 100%;height: 400px" rows="10" id="content"
                          name="content">${repairRecord.situation.summarize!}</textarea>
            </td>
        </tr>
        <tr>
            <td colspan="4">技术变更</td>
            <td colspan="16">
                <input style="width: 80%" type="text" id="repairProcessRecordTechChange" name="situation.techChange">
            </td>
        </tr>
        <tr>
            <td colspan="4">项目负责人</td>
            <td colspan="6"></td>
            <td colspan="4">保护修复人</td>
            <td colspan="6"></td>
        </tr>
        <tr>
            <td colspan="4">完成日期</td>
            <td colspan="6"></td>
            <td colspan="4">审核人</td>
            <td colspan="6"></td>
        </tr>
        <tr id="repairRecordConfirmRow">
            <td colspan="20"><span class="btn btn-small">确认</span></td>
        </tr>
    </table>
</form>
<form id="repairLogForm" class="form-horizontal m-t-50" action="archives/saveRepairLog">
    <input id="hiddenLogObjId" type="hidden" name="repairLog.repairRecord.id"/>
    <input id="hiddenLogRepairId" type="hidden" name="repairRecordId"/>
    <table border="1px" style="border:2px #000 solid;">
        <tr>
            <td colspan="10"><span class="careful">日志</span></td>
        </tr>
        <tr>
            <td colspan="3" style="width: 30%">保护修复人</td>
            <td colspan="7">
            <#if users?size gt 0>
                <select name="repairLog.repairPerson" id="repairLogRepairPerson" multiple style="width: 240px;margin-left: 5px;">
                    <#list users as user>
                        <option value="${user.id}">${user.userName}</option>
                    </#list>
                </select>
            </#if>
                <span id="repairLog-input-help" class="help-inline"></span>
            </td>

        </tr>
        <tr>
            <td colspan="3" style="width: 30%">日期</td>
            <td colspan="7">
                <input id="repairLogStamp" name="repairLog.stamp" class="input-medium Wdate" type="text"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </td>
        </tr>
        <tr>
            <td colspan="3" style="width: 30%">日志</td>
            <td colspan="7">
                <textarea style="width: 80%" name="repairLog.log" id="" cols="50" rows="5"></textarea>
            </td>
        </tr>
        <tr>
            <td colspan="10"><span id="repairLogSubmit" class="btn btn-small">添加</span></td>
        </tr>
    </table>
</form>

<table border="1px" style="border:2px #000 solid;">
    <tr id="repairRecordImageListupload">
        <td colspan="4">
            <form id="uploadRepairRecordImageform" action="archives/uploadRepairRecordImage" method="post"
                  enctype="multipart/form-data" name="uploadfileform">
                <input type="hidden" name="repairPhoto.repairRecord.id" value="${repairRecordId!}"/>
                <input type="hidden" name="relicId" value="${relicId!}"/>
                <input id="hiddenRepairRecordActionName" type="hidden" name="actionName" value="repair-record"/>

                <div class="m-t-30">
                    图片描述:<input style="width: 300px" type="text" id="repairRecordImageDescribe"
                                name="repairPhoto.description"/>
                </div>
                <div class="fileupload-box m-t-20" id="fileupload-box">
                    <input type='button' class='fileupload-btn m-b-10' value='选择文件'/>
                    <input type='text' name='hideRepairRecordImageName' id='repairRecordTextfield'
                           class='fileupload-txt m-b-0'
                           readonly="true"/>
                    <input type="file" name="repairRecordImage" class="fileupload-file" id="repairRecordField" size="28"
                           onchange="document.getElementById('repairRecordTextfield').value=this.value"/>
                            <span class="upload-file-btn btn btn-small btn-primary"
                                  id="upload-repairRecord-image">上传
                            </span>
                </div>
            </form>
        </td>
    </tr>
</table>
