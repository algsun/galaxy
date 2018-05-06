<div>
    <form id="baseInfoTypedForm" action="archives/saveBaseInfo">
        <input type="hidden" name="repairRecordId" value="${repairRecordId!}"/>
        <input type="hidden" name="relicId" value="${relicId!}"/>
        <input type="hidden" name="situation.repairRecord.id" value="${repairRecordId!}"/>
        <input type="hidden" name="actionName" value="base-info-typed"/>
        <table border="1px" style="border:2px #000 solid;">
            <caption align="top"><h3>文物基本信息表</h3></caption>
            <tr>
                <td colspan="3">文物名称</td>
                <td colspan="7" id="typedRelicName"></td>
                <td colspan="3">文物编号</td>
                <td colspan="7" id="typedRelicNum"></td>
            </tr>
            <tr>
                <td colspan="3">年代</td>
                <td colspan="7" id="typedEra"></td>
                <td colspan="3">级别</td>
                <td colspan="7" id="typedLevel"></td>
            </tr>
            <tr>
                <td colspan="3">质地</td>
                <td colspan="7" id="typedTexture"></td>
                <td colspan="3">收藏单位</td>
                <td colspan="7" id="typedCollectionUnit"></td>
            </tr>
            <tr>
                <td colspan="3">来源</td>
                <td colspan="7" id="typedSource"></td>
                <td colspan="3">收藏库房</td>
                <td colspan="7" id="typedCollectionRoom"></td>
            </tr>
            <tr>
                <td colspan="3">修复人员</td>
                <td colspan="17" id="typedRepairPerson"></td>
            </tr>
            <tr>
                <td colspan="3">修复因由</td>
                <td colspan="17" id="typedRepairReason"></td>
            </tr>
            <tr>
                <td colspan="3">修复内容</td>
                <td colspan="17" id="typedRepairContent"></td>
            </tr>
            <tr>
                <td colspan="3">提取时间</td>
                <td colspan="7" id="typedExtractionTime"></td>
                <td colspan="3">归还时间</td>
                <td colspan="7" id="typedReturnTime"></td>
            </tr>
            <tr>
                <td colspan="3">位置</td>
                <td colspan="17">
                    <input id="typedPosition" name="situation.position" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">尺寸<span class="careful">(cm)</span></td>
                <td colspan="7">
                    <input id="typedSize" name="situation.size" class="m-t-5" style="width: 80%" type="text" value=""/>
                </td>
                <td colspan="3">质量<span class="careful">(g)</span></td>
                <td colspan="7">
                    <input id="typedWeight" name="situation.weight" class="m-t-5" style="width: 80%" placeholder="未称重"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">修复后尺寸<span class="careful">(cm)</span></td>
                <td colspan="7">
                    <input id="typedRepairedSize" name="situation.repairedSize" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                    <input type="hidden" name="relicProperties[0].propertyValue" value=""/>
                    <input type="hidden" name="relicProperties[0].property.enName" value="sizes"/>
                    <input type="hidden" name="relicProperties[0].relic.id" value="${relicId}"/>
                </td>
                <td colspan="3">修复后质量<span class="careful">(g)</span></td>
                <td colspan="7">
                    <input id="typedRepairedWeight" name="situation.repairedWeight" class="m-t-5" style="width: 80%"
                           placeholder="未称重" type="text" value=""/>
                    <input type="hidden" name="relicProperties[1].propertyValue" value=""/>
                    <input type="hidden" name="relicProperties[1].property.enName" value="weight"/>
                    <input type="hidden" name="relicProperties[1].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">照片</td>
                <td colspan="17" id="typedPhotoPath"></td>
                <span id="baseinfoPics-typed-help" class="help-inline"></span>
            </tr>
            <tr>
                <td colspan="3">文物描述</td>
                <td colspan="17">
                    <textarea id="typedDescription" name="relicProperties[2].propertyValue"
                              style="width: 80%;margin-top: 5px" cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[2].property.enName" value="describe"/>
                    <input type="hidden" name="relicProperties[2].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">相关文献</td>
                <td colspan="17">
                    <textarea id="typedDocument" name="relicProperties[3].propertyValue" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[3].property.enName" value="literature"/>
                    <input type="hidden" name="relicProperties[3].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">备注</td>
                <td colspan="17">
                    <textarea id="typedRemark" name="relicProperties[4].propertyValue" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[4].property.enName" value="remark"/>
                    <input type="hidden" name="relicProperties[4].relic.id" value="${relicId}"/>
                </td>
            </tr>
        </table>
        <div class="m-t-30">
            <span id="baseInfoTypedSubmit" class="btn" style="width: 80px;">提交</span>
        </div>
    </form>
    <table border="1px" style="border:2px #000 solid;">
        <tr id="typedImageListupload">
            <td colspan="3">
                <form id="typedUploadfileform" action="archives/uploadPhoto" method="post"
                      enctype="multipart/form-data" name="uploadfileform">
                    <input type="hidden" name="repairRecordId" value="${repairRecordId!}"/>
                    <input type="hidden" name="relicId" value="${relicId!}"/>
                    <input id="typedHiddenActionName" type="hidden" name="actionName"/>
                    <div class="fileupload-box m-t-20" id="fileupload-box-typed">
                        <input type='button' class='fileupload-btn m-b-10' value='选择文件'/>
                        <input type='text' name='hideFileName' id='textfieldTyped' class='fileupload-txt m-b-0'
                               readonly="true"/>
                        <input type="file" name="fileImage" class="fileupload-file" id="fileFieldTyped" size="28"
                               onchange="document.getElementById('textfieldTyped').value=this.value"/>
                                        <span class="upload-file-btn btn btn-small btn-primary"
                                              id="upload-file-btn-typed">上传
                                        </span>
                        <div style="color: red"></div>
                    </div>
                </form>
            </td>
        </tr>
    </table>
</div>
