<div>
    <form id="baseInfoTypeaForm" action="archives/saveBaseInfo">
        <input type="hidden" name="repairRecordId" value="${repairRecordId!}"/>
        <input type="hidden" name="relicId" value="${relicId!}"/>
        <input type="hidden" name="situation.repairRecord.id" value="${repairRecordId!}"/>
        <input type="hidden" name="actionName" value="base-info-typea"/>
        <table border="1px" style="border:2px #000 solid;">
            <caption align="top"><h3>文物基本信息表</h3></caption>
            <tr>
                <td colspan="3">文物名称</td>
                <td colspan="7" id="typeaRelicName"></td>
                <td colspan="3">文物编号</td>
                <td colspan="7" id="typeaRelicNum"></td>
            </tr>
            <tr>
                <td colspan="3">年代</td>
                <td colspan="7" id="typeaEra"></td>
                <td colspan="3">级别</td>
                <td colspan="7" id="typeaLevel"></td>
            </tr>
            <tr>
                <td colspan="3">质地</td>
                <td colspan="7" id="typeaTexture"></td>
                <td colspan="3">收藏单位</td>
                <td colspan="7" id="typeaCollectionUnit"></td>
            </tr>
            <tr>
                <td colspan="3">来源</td>
                <td colspan="7" id="typeaSource"></td>
                <td colspan="3">收藏库房</td>
                <td colspan="7" id="typeaCollectionRoom"></td>
            </tr>
            <tr>
                <td colspan="3">修复人员</td>
                <td colspan="17" id="typeaRepairPerson"></td>
            </tr>
            <tr>
                <td colspan="3">修复因由</td>
                <td colspan="17" id="typeaRepairReason"></td>
            </tr>
            <tr>
                <td colspan="3">修复内容</td>
                <td colspan="17" id="typeaRepairContent"></td>
            </tr>
            <tr>
                <td colspan="3">提取时间</td>
                <td colspan="7" id="typeaExtractionTime"></td>
                <td colspan="3">归还时间</td>
                <td colspan="7" id="typeaReturnTime"></td>
            </tr>
            <tr>
                <td colspan="3">尺寸<span class="careful">(cm)</span></td>
                <td colspan="7">
                    <input id="typeaSize" name="situation.size" class="m-t-5" style="width: 80%" type="text" value=""/>
                </td>
                <td colspan="3">质量<span class="careful">(g)</span></td>
                <td colspan="7">
                    <input id="typeaWeight" name="situation.weight" class="m-t-5" style="width: 80%" placeholder="未称重"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">修复后尺寸<span class="careful">(cm)</span></td>
                <td colspan="7">
                    <input id="typeaRepairedSize" name="situation.repairedSize" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                    <input type="hidden" name="relicProperties[0].propertyValue" value=""/>
                    <input type="hidden" name="relicProperties[0].property.enName" value="sizes"/>
                    <input type="hidden" name="relicProperties[0].relic.id" value="${relicId}"/>
                </td>
                <td colspan="3">修复后质量<span class="careful">(g)</span></td>
                <td colspan="7">
                    <input id="typeaRepairedWeight" name="situation.repairedWeight" class="m-t-5" style="width: 80%"
                           placeholder="未称重" type="text" value=""/>
                    <input type="hidden" name="relicProperties[1].propertyValue" value=""/>
                    <input type="hidden" name="relicProperties[1].property.enName" value="weight"/>
                    <input type="hidden" name="relicProperties[1].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">照片</td>
                <td colspan="17" id="typeaPhotoPath">
                </td>
                <span id="baseinfoPics-typea-help" class="help-inline"></span>
            </tr>
            <tr>
                <td colspan="3">工艺</td>
                <td colspan="17">
                    <input id="typeaCraft" name="situation.craft" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">文物描述</td>
                <td colspan="17">
                    <textarea id="typeaDescription" name="relicProperties[2].propertyValue"
                              style="width: 80%;margin-top: 5px" cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[2].property.enName" value="describe"/>
                    <input type="hidden" name="relicProperties[2].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">相关文献</td>
                <td colspan="17">
                    <textarea id="typeaDocument" name="relicProperties[3].propertyValue" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[3].property.enName" value="literature"/>
                    <input type="hidden" name="relicProperties[3].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">备注</td>
                <td colspan="17">
                    <textarea id="typeaRemark" name="relicProperties[4].propertyValue" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[4].property.enName" value="remark"/>
                    <input type="hidden" name="relicProperties[4].relic.id" value="${relicId}"/>
                </td>
            </tr>
        </table>
        <div class="m-t-30">
            <span id="baseInfoTypeaSubmit" class="btn" style="width: 80px;">提交</span>
        </div>
    </form>
    <table border="1px" style="border:2px #000 solid;">
        <tr id="typeaImageListupload">
            <td colspan="3">
                <form id="typeaUploadfileform" action="archives/uploadPhoto" method="post"
                      enctype="multipart/form-data" name="uploadfileform">
                    <input type="hidden" name="repairRecordId" value="${repairRecordId!}"/>
                    <input type="hidden" name="relicId" value="${relicId!}"/>
                    <input id="typeaHiddenActionName" type="hidden" name="actionName"/>
                    <div class="fileupload-box m-t-20" id="fileupload-box-typea">
                        <input type='button' class='fileupload-btn m-b-10' value='选择文件'/>
                        <input type='text' name='hideFileName' id='textfieldTypea' class='fileupload-txt m-b-0'
                               readonly="true"/>
                        <input type="file" name="fileImage" class="fileupload-file" id="fileFieldTypea" size="28"
                               onchange="document.getElementById('textfieldTypea').value=this.value"/>
                                        <span class="upload-file-btn btn btn-small btn-primary"
                                              id="upload-file-btn-typea">上传
                                        </span>
                    </div>
                </form>
            </td>
        </tr>
    </table>
</div>
