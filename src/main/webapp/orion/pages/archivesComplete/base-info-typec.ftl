<div>
    <form id="baseInfoTypecForm" action="archives/saveBaseInfo">
        <input type="hidden" name="repairRecordId" value="${repairRecordId!}"/>
        <input type="hidden" name="relicId" value="${relicId!}"/>
        <input type="hidden" name="situation.repairRecord.id" value="${repairRecordId!}"/>
        <input type="hidden" name="actionName" value="base-info-typec"/>
        <table border="1px" style="border:2px #000 solid;">
            <caption align="top"><h3>文物基本信息表</h3></caption>
            <tr>
                <td colspan="3">文物名称</td>
                <td colspan="7" id="typecRelicName"></td>
                <td colspan="3">文物编号</td>
                <td colspan="7" id="typecRelicNum"></td>
            </tr>
            <tr>
                <td colspan="3">年代</td>
                <td colspan="7" id="typecEra"></td>
                <td colspan="3">级别</td>
                <td colspan="7" id="typecLevel"></td>
            </tr>
            <tr>
                <td colspan="3">质地</td>
                <td colspan="7" id="typecTexture"></td>
                <td colspan="3">收藏单位</td>
                <td colspan="7" id="typecCollectionUnit"></td>
            </tr>
            <tr>
                <td colspan="3">来源</td>
                <td colspan="7" id="typecSource"></td>
                <td colspan="3">收藏库房</td>
                <td colspan="7" id="typecCollectionRoom"></td>
            </tr>
            <tr>
                <td colspan="3">修复人员</td>
                <td colspan="17" id="typecRepairPerson"></td>
            </tr>
            <tr>
                <td colspan="3">修复因由</td>
                <td colspan="17" id="typecRepairReason"></td>
            </tr>
            <tr>
                <td colspan="3">修复内容</td>
                <td colspan="17" id="typecRepairContent"></td>
            </tr>
            <tr>
                <td colspan="3">提取时间</td>
                <td colspan="7" id="typecExtractionTime"></td>
                <td colspan="3">归还时间</td>
                <td colspan="7" id="typecReturnTime"></td>
            </tr>
            <tr>
                <td colspan="3">尺寸<span class="careful">(cm)</span></td>
                <td colspan="7">
                    <input id="typecSize" name="situation.size" class="m-t-5" style="width: 80%" type="text" value=""/>
                </td>
                <td colspan="3">质量<span class="careful">(g)</span></td>
                <td colspan="7">
                    <input id="typecWeight" name="situation.weight" class="m-t-5" style="width: 80%" placeholder="未称重"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">修复后尺寸<span class="careful">(cm)</span></td>
                <td colspan="7">
                    <input id="typecRepairedSize" name="situation.repairedSize" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                    <input type="hidden" name="relicProperties[0].propertyValue" value=""/>
                    <input type="hidden" name="relicProperties[0].property.enName" value="sizes"/>
                    <input type="hidden" name="relicProperties[0].relic.id" value="${relicId}"/>
                </td>
                <td colspan="3">修复后质量<span class="careful">(g)</span></td>
                <td colspan="7">
                    <input id="typecRepairedWeight" name="situation.repairedWeight" class="m-t-5" style="width: 80%"
                           placeholder="未称重" type="text" value=""/>
                    <input type="hidden" name="relicProperties[1].propertyValue" value=""/>
                    <input type="hidden" name="relicProperties[1].property.enName" value="weight"/>
                    <input type="hidden" name="relicProperties[1].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">种类</td>
                <td colspan="7">
                    <input id="typecSpinningType" name="situation.spinningType" class="m-t-5" style="width: 80%"
                           type="text" value=""/>

                </td>
                <td colspan="3">制作工艺</td>
                <td colspan="7">
                    <input id="typecCraftsmanship" name="situation.craftsmanship" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">织造工艺</td>
                <td colspan="7">
                    <input id="typecWeavingProcess" name="situation.weavingProcess" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
                <td colspan="3">织物组织</td>
                <td colspan="7">
                    <input id="typecFabricPart" name="situation.fabricPart" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3" rowspan="2">织物密度</td>
                <td colspan="2">经线</td>
                <td colspan="5">
                    <input id="typecDensityLong" name="situation.densityLong" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
                <td colspan="3" rowspan="2">丝线颜色</td>
                <td colspan="2">经线</td>
                <td colspan="5">
                    <input id="typecColorLong" name="situation.colorLong" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="2">纬线</td>
                <td colspan="5">
                    <input id="typecDensityLat" name="situation.densityLat" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
                <td colspan="2">纬线</td>
                <td colspan="5">
                    <input id="typecColorLat" name="situation.colorLat" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">丝线细度</td>
                <td colspan="4">
                    <input id="typecSlender" name="situation.slender" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
                <td colspan="3">丝线捻向</td>
                <td colspan="3">
                    <input id="typecTwistDirection" name="situation.twistDirection" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
                <td colspan="3">丝线捻度</td>
                <td colspan="4">
                    <input id="typecTwistDegree" name="situation.twistDegree" class="m-t-5" style="width: 80%"
                           type="text" value=""/>
                </td>
            </tr>
            <tr>
                <td colspan="3">照片</td>
                <td colspan="17" id="typecPhotoPath">
                </td>
                <span id="baseinfoPics-typec-help" class="help-inline"></span>
            </tr>
            <tr>
                <td colspan="3">工艺</td>
                <td colspan="17">
                    <input id="typecCraft" name="situation.craft" class="m-t-5" style="width: 80%"
                           type="text" value=""/>

                </td>
            </tr>
            <tr>
                <td colspan="3">文物描述</td>
                <td colspan="17">
                    <textarea id="typecDescription" name="relicProperties[2].propertyValue"
                              style="width: 80%;margin-top: 5px" cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[2].property.enName" value="describe"/>
                    <input type="hidden" name="relicProperties[2].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">相关文献</td>
                <td colspan="17">
                    <textarea id="typecDocument" name="relicProperties[3].propertyValue" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[3].property.enName" value="literature"/>
                    <input type="hidden" name="relicProperties[3].relic.id" value="${relicId}"/>
                </td>
            </tr>
            <tr>
                <td colspan="3">备注</td>
                <td colspan="17">
                    <textarea id="typecRemark" name="relicProperties[4].propertyValue" style="width: 80%;margin-top: 5px"
                              cols="60" rows="5"></textarea>
                    <input type="hidden" name="relicProperties[4].property.enName" value="remark"/>
                    <input type="hidden" name="relicProperties[4].relic.id" value="${relicId}"/>
                </td>
            </tr>
        </table>
        <div class="m-t-30">
            <span id="baseInfoTypecSubmit" class="btn" style="width: 80px;">提交</span>
        </div>
    </form>
    <table border="1px" style="border:2px #000 solid;">
        <tr id="typecImageListupload">
            <td colspan="3">
                <form id="typecUploadfileform" action="archives/uploadPhoto" method="post"
                      enctype="multipart/form-data" name="uploadfileform">
                    <input type="hidden" name="repairRecordId" value="${repairRecordId!}"/>
                    <input type="hidden" name="relicId" value="${relicId!}"/>
                    <input id="typecHiddenActionName" type="hidden" name="actionName"/>
                    <div class="fileupload-box m-t-20" id="fileupload-box-typec">
                        <input type='button' class='fileupload-btn m-b-10' value='选择文件'/>
                        <input type='text' name='hideFileName' id='textfieldTypec' class='fileupload-txt m-b-0'
                               readonly="true"/>
                        <input type="file" name="fileImage" class="fileupload-file" id="fileFieldTypec" size="28"
                               onchange="document.getElementById('textfieldTypec').value=this.value"/>
                                        <span class="upload-file-btn btn btn-small btn-primary"
                                              id="upload-file-btn-typec">上传
                                        </span>
                    </div>
                </form>
            </td>
        </tr>
    </table>
</div>
