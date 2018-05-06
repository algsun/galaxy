<#-- TODO 没有作者，时间等，代码片段，是否需要总结，或者将代码片段，宏，函数都放在一起 -->
<div>
    <div>
        <span style="font-weight: bold">申请单号：</span><span><@strongOutEventId outEvent.id/></span>
    </div>
    <table style="width: 100%;margin-top: 30px;margin-bottom: 10px">
        <tbody>
        <tr>
            <td style="width: 65px;font-weight: bold">申请人:</td>
            <td>${outEvent.applicant}</td>
            <td style="width: 65px;font-weight: bold">提用目的:</td>
            <td>${outEvent.useFor}</td>
            <td style="width: 65px;font-weight: bold">类型:</td>
            <td><@_eventTypeName outEvent.eventType/></td>
            <td style="width: 65px;font-weight: bold">是否出馆:</td>
            <td>${outEvent.outBound?string("是","否")}</td>
        </tr>
        <tr>
            <td style="width: 65px;font-weight: bold">待办人:</td>
            <td>${outEvent.user.userName}</td>
            <td style="width: 65px;font-weight: bold">开始时间:</td>
            <td>${outEvent.beginDate?string("yyyy-MM-dd")}</td>
            <td style="width: 65px;font-weight: bold">结束时间:</td>
            <td>${outEvent.endDate?string("yyyy-MM-dd")}</td>
        </tr>
        </tbody>
    </table>
</div>