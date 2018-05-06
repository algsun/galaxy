<div>
    <form id="baseInfoTypebForm" action="archives/saveBaseInfo">
        <input type="hidden" name="repairRecordId" value="${repairRecordId!}"/>
        <input type="hidden" name="relicId" value="${relicId!}"/>
        <input type="hidden" name="situation.repairRecord.id" value="${repairRecordId!}"/>
        <input type="hidden" name="actionName" value="base-info-typeb"/>
        <table border="1px" style="border:2px #000 solid;">
            <caption align="top"><h3>文物基本信息表</h3></caption>
            <tr>
                <td colspan="3">文物名称</td>
                <td colspan="7" id="typebRelicName"></td>
                <td colspan="3">文物编号</td>
                <td colspan="7" id="typebRelicNum"></td>
            </tr>
            <tr>
                <td colspan="3">年代</td>
                <td colspan="7" id="typebEra"></td>
                <td colspan="3">级别</td>
                <td colspan="7" id="typebLevel"></td>
            </tr>
            <tr>
                <td colspan="3">质地</td>
                <td colspan="7" id="typebTexture"></td>
                <td colspan="3">收藏单位</td>
                <td colspan="7" id="typebCollectionUnit"></td>
            </tr>
            <tr>
                <td colspan="3">来源</td>
                <td colspan="7" id="typebSource"></td>
                <td colspan="3">收藏库房</td>
                <td colspan="7" id="typebCollectionRoom"></td>
            </tr>
            <tr>
                <td colspan="3">修复人员</td>
                <td colspan="17" id="typebRepairPerson"></td>
            </tr>
            <tr>
                <td colspan="3">修复因由</td>
                <td colspan="17" id="typebRepairReason"></td>
            </tr>
            <tr>
                <td colspan="3">修复内容</td>
                <td colspan="17" id="typebRepairContent"></td>
            </tr>
            <tr>
                <td colspan="3">提取时间</td>
                <td colspan="7" id="typebExtractionTime"></td>
                <td colspan="3">归还时间</td>
                <td colspan="7" id="typebReturnTime"></td>
            </tr>
            <tr>
                <td colspan="3">尺寸<span class="careful">(cm)</span></td>
                <td colspan="17">
                    <input id="typebSize" name="situation.size" class="m-t-5" style="width: 80%" type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">质量<span class="careful">(g)</span></td>
                <td colspan="17">
                    <input id="typebWeight" name="situation.weight" class="m-t-5" style="width: 80%" placeholder="未称重"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">修复后质量<span class="careful">(g)</span></td>
                <td colspan="17">
                    <input id="typebRepairedWeight" name="situation.repairedWeight" class="m-t-5" style="width: 80%"
                           placeholder="未称重" type="text" value=""/>
                    <input type="hidden" name="relicProperties[0].propertyValue" value=""/>
                    <input type="hidden" name="relicProperties[0].property.enName" value="weight"/>
                    <input type="hidden" name="relicProperties[0].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">画轴</td>
                <td colspan="17">
                    <input id="typebKakemono" name="situation.kakemono" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">装裱样式</td>
                <td colspan="7">
                    <input id="typebFrameStyle" name="situation.frameStyle" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
                <td colspan="3">装裱用料</td>
                <td colspan="7">
                    <input id="typebFrameMaterial" name="situation.frameMaterial" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">包首</td>
                <td colspan="17">
                    <input id="typebPackHead" name="situation.packHead" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">照片</td>
                <td colspan="17" id="typebPhotoPath"></td>
                <span id="baseinfoPics-typeb-help" class="help-inline"></span>
            </tr>
            <tr>
                <td colspan="3">工艺</td>
                <td colspan="17">
                    <input id="typebCraft" name="situation.craft" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">文物描述</td>
                <td colspan="17">
                    <textarea id="typebDescription" name="relicProperties[1].propertyValue"
                              style="width: 80%;margin-top: 5px" cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[1].property.enName" value="describe"/>
                    <input type="hidden" name="relicProperties[1].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">相关文献</td>
                <td colspan="17">
                    <textarea id="typebDocument" name="relicProperties[2].propertyValue" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[2].property.enName" value="literature"/>
                    <input type="hidden" name="relicProperties[2].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">备注</td>
                <td colspan="17">
                    <textarea id="typebRemark" name="relicProperties[3].propertyValue" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[3].property.enName" value="remark"/>
                    <input type="hidden" name="relicProperties[3].relic.id" value="${relicId}"/>
                </td>
            </tr>
        </table>
        <div class="m-t-30">
            <span id="baseInfoTypebSubmit" class="btn" style="width: 80px;">提交</span>
        </div>
    </form>
    <table border="1px" style="border:2px #000 solid;">
        <tr id="typebImageListupload">
            <td colspan="3">
                <form id="typebUploadfileform" action="archives/uploadPhoto" method="post"
                      enctype="multipart/form-data" name="uploadfileform">
                    <input type="hidden" name="repairRecordId" value="${repairRecordId!}"/>
                    <input type="hidden" name="relicId" value="${relicId!}"/>
                    <input id="typebHiddenActionName" type="hidden" name="actionName"/>
                    <div class="fileupload-box m-t-20" id="fileupload-box-typeb">
                        <input type='button' class='fileupload-btn m-b-10' value='选择文件'/>
                        <input type='text' name='hideFileName' id='textfieldTypeb' class='fileupload-txt m-b-0'
                               readonly="true"/>
                        <input type="file" name="fileImage" class="fileupload-file" id="fileFieldTypeb" size="28"
                               onchange="document.getElementById('textfieldTypeb').value=this.value"/>
                                        <span class="upload-file-btn btn btn-small btn-primary"
                                              id="upload-file-btn-typeb">上传
                                        </span>
                        <div style="color: red"></div>
                    </div>
                </form>
            </td>
        </tr>
    </table>
</div>
