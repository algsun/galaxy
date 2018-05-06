<form id="detectionAnalysisForm" class="form-horizontal" action="archives/saveDetectionAnalysis">
    <input type="hidden" name="detectionAnalysis.repairRecord.id" value="${repairRecordId!}"/>
    <input type="hidden" name="relicId" value="${relicId!}"/>
    <div class="control-group">
        <label class="control-label">样品名称</label>
        <div class="controls">
            <input type="text" id="sampleName" name="detectionAnalysis.sampleName">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">样品描述</label>
        <div class="controls">
            <textarea name="detectionAnalysis.sampleDescription" id="sampleDescription" cols="50" rows="5"></textarea>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">分析目的</label>
        <div class="controls">
            <input type="text" id="analysisPurpose" name="detectionAnalysis.analysisPurpose">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">分析方法</label>
        <div class="controls">
            <input type="text" id="analysisMethod" name="detectionAnalysis.analysisMethod">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">分析结果</label>
        <div class="controls">
            <textarea name="detectionAnalysis.analysisResult" id="analysisResult" cols="50" rows="5"></textarea>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">报告代码</label>
        <div class="controls">
            <input type="text" id="reportCode" name="detectionAnalysis.reportCode">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">分析时间</label>
        <div class="controls">
            <input id="analysisTime" name="detectionAnalysis.analysisTime" class="input-medium Wdate" type="text"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注</label>
        <div class="controls">
            <input type="text" id="remark" name="detectionAnalysis.remark">
        </div>
    </div>
    <div class="control-group">
        <div class="controls">
            <button type="submit" class="btn">保存</button>
        </div>
    </div>
</form>
<table id="detectionAnalysisTable" border="1px" style="border:2px #000 solid;">
    <caption align="top"><h3>文物检测分析表</h3></caption>
    <tr>
        <td colspan="1">编号</td>
        <td colspan="2">样品名称</td>
        <td colspan="3">样品描述</td>
        <td colspan="2">分析目的</td>
        <td colspan="3">分析方法</td>
        <td colspan="2">分析结果</td>
        <td colspan="2">报告代码</td>
        <td colspan="2">分析时间</td>
        <td colspan="2">备注</td>
        <td colspan="1">操作</td>
    </tr>
</table>