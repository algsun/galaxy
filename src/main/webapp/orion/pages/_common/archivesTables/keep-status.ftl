<div>
    <form id="statusQuoForm" action="archives/saveStatusQuo">
        <input id="hiddenRelicId" type="hidden" name="statusQuo.relic.id"/>
        <input id="hiddenRepairRecordId" type="hidden" name="repairRecordId"/>
        <input id="hiddenStatusQuoId" type="hidden" name="statusQuo.id"/>
        <table border="1px" style="border:2px #000 solid;">
            <caption align="top"><h3>文物保存现状表</h3></caption>
            <tr style="height:50px">
                <td colspan="4">文物保存环境</td>
                <td colspan="16">
                    <textarea id="sqConserve" name="statusQuo.conserve" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                </td>
            </tr>
            <tr style="height:50px">
                <td colspan="4">历次保护修复情况</td>
                <td colspan="16">
                    <textarea id="sqRepairCases" name="statusQuo.repairCases" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                </td>
            </tr>
            <tr style="height:400px" id="statusQuoPhotoShow">
                <td colspan="4">现状及病害描述</td>
                <td colspan="16">
                    <textarea id="sqQuoInfo" name="statusQuo.quoInfo" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                    <span id="statusQuo-input-help" class="help-inline"></span>
                </td>
            </tr>

            <tr style="height:80px">
                <td colspan="4">备注</td>
                <td colspan="16">
                    <textarea id="sqRemark" name="statusQuo.remark" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                </td>
            </tr>
        </table>
        <div class="m-t-30">
            <span id="statusQuoSubmit" class="btn" style="width: 80px;">提交</span>
        </div>
    </form>

    <table border="1px" style="border:2px #000 solid;">
        <tr id="keepStatusImageListupload">
            <td colspan="4">
                <form id="uploadStatusQuoImageform" action="archives/uploadStatusQuoImage" method="post"
                      enctype="multipart/form-data" name="uploadfileform">
                    <input type="hidden" name="repairPhoto.repairRecord.id" value="${repairRecordId!}"/>
                    <input type="hidden" name="relicId" value="${relicId!}"/>
                    <input id="hiddenKeepStatusId" type="hidden" name="statusQuo.id" value=""/>
                    <input id="hiddenActionName" type="hidden" name="actionName" value=""/>
                    <div class="m-t-30">
                        图片描述:<input style="width: 300px" type="text" id="statusQuoImageDescribe" name="repairPhoto.description"/>
                    </div>
                    <div class="fileupload-box m-t-20" id="fileupload-box">
                        <input type='button' class='fileupload-btn m-b-10' value='选择文件'/>
                        <input type='text' name='hideStatusQuoImageName' id='statusQuoTextfield'
                               class='fileupload-txt m-b-0'
                               readonly="true"/>
                        <input type="file" name="statusQuoImage" class="fileupload-file" id="statusQuoField" size="28"
                               onchange="document.getElementById('statusQuoTextfield').value=this.value"/>
                            <span class="upload-file-btn btn btn-small btn-primary"
                                  id="upload-statusQuo-image">上传
                            </span>
                        <div style="color: red"></div>
                    </div>
                </form>
            </td>
        </tr>
    </table>
</div>
