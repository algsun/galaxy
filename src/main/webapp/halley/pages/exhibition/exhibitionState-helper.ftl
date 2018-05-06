<div>
    <div>
        <span style="font-weight: bold;margin-left:20px">申请单号：</span><span><@strongOutEventId exhibitionVO.outEventId/></span>
        <a class="btn btn-info btn-mini"
           href="exhibition/carConfig/${exhibitionVO.exhibitionId}">外展配置</a>
        <a class="btn btn-info btn-mini"
           href="queryPackingList/exhibition/${exhibitionVO.exhibitionId}">装箱单</a>
        <a class="btn btn-info btn-mini"
           href="routePlanning/exhibition/${exhibitionVO.exhibitionId}">预设路线</a></div>
    <table style="width: 100%;margin-top: 30px;margin-bottom: 10px;margin-left:20px ">
        <tbody>
        <tr>
            <td style="width: 65px;font-weight: bold">申请人:</td>
            <td>${exhibitionVO.applicant}</td>
            <td style="width: 65px;font-weight: bold">提用目的:</td>
            <td>${exhibitionVO.useFor}</td>

        </tr>
        <tr>

            <td style="width: 100px;font-weight: bold">预计开始时间:</td>
            <td>${exhibitionVO.estimatedBeginTime?string("yyyy-MM-dd")}</td>
            <td style="width: 100px;font-weight: bold">预计结束时间:</td>
            <td>${exhibitionVO.estimatedEndTime?string("yyyy-MM-dd")}</td>
        </tr>
        </tbody>
    </table>
</div>
<#macro strongOutEventId outEventId>
${outEventId?substring(0,8)}<strong style="font-size: 1.11em;">${outEventId?substring(8)}</strong>
</#macro>

