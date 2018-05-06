<div>
    <table border="1px" style="border:2px #000 solid;">
        <caption align="top"><h3>文物保护修复自评估与验收表</h3></caption>
        <form id="repair_assessment_form" action="archives/assessment/save.json">
            <input name="repairAssessment.repairRecord.id" value="${repairRecordId}" type="hidden"/>
            <tr style="height: 300px">
                <td colspan="20" class="none-border">
                    <#if repairAssessment??>
                        <textarea name="repairAssessment.selfAssessment" id="selfAssessment" cols="30" rows="12"
                                  style="width: 95%">${repairAssessment.selfAssessment}</textarea>
                    <#else>
                        <textarea name="repairAssessment.selfAssessment" id="selfAssessment" cols="30" rows="12"
                                  style="width: 95%"> </textarea>
                    </#if>

                </td>
            </tr>
            <tr>
                <td colspan="14" class="none-border"></td>
                <td colspan="2" class="none-border" style="text-align: right">签章:</td>
                <td colspan="4" class="none-border"></td>
            </tr>
            <tr>
                <td colspan="14" class="none-border"></td>
                <td colspan="2" class="none-border" style="text-align: right">日期:</td>
                <td colspan="4" class="none-border">
                    <#if repairAssessment??>
                        <input name="repairAssessment.stamp" id="assessmentStamp" type="text" value="${repairAssessment.stamp?string("yyyy-MM-dd")}"
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"
                               style="width: 65%;float: left"/>
                    <#else>
                        <input name="repairAssessment.stamp" id="assessmentStamp" type="text" value=""
                               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"
                               style="width: 65%;float: left"/>
                    </#if>

                </td>
            </tr>
            <tr>
                <td colspan="20" class="none-border">
                    <input type="button" id="assessment_submit_id" class="btn" value="添加"/>
                </td>
            </tr>
        </form>
    </table>

    <#if security.isPermitted("orion:repairRecord:handle")>
        <#if userId == repairRecord.mainUser.id>
        <div class="row m-t-20">
            <div class="span7 offset5">
                <a href="handleTask/submitCheck?repairRecord.id=${repairRecordId}" class="btn">提交审核</a>
            </div>
        </div>
        </#if>
    </#if>


</div>
