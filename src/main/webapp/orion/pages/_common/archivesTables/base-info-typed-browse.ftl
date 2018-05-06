<table border="1px" style="border:2px #000 solid;">
    <caption align="top"><h3>文物基本信息表</h3></caption>
    <tr>
        <td colspan="3">文物名称</td>
        <td colspan="7">${repairRecord.relic.name!}</td>
        <td colspan="3">文物编号</td>
        <td colspan="7">${repairRecord.relic.totalCode!}</td>
    </tr>
    <tr>
        <td colspan="3">年代</td>
        <td colspan="7">${repairRecord.relic.era.name!}</td>
        <td colspan="3">级别</td>
        <td colspan="7">${repairRecord.relic.level.name!}</td>
    </tr>
    <tr>
        <td colspan="3">质地</td>
        <td colspan="7">${repairRecord.relic.texture.name!}</td>
        <td colspan="3">收藏单位</td>
        <td colspan="7">${(institution.name)!}</td>
    </tr>
    <tr>
        <td colspan="3">来源</td>
        <td colspan="7">${(repairRecord.relic.relicPropertyMap.source.propertyValue)!}</td>
        <td colspan="3">收藏库房</td>
        <td colspan="7">${(institutionRoom.roomName)!}</td>
    </tr>
    <tr>
        <td colspan="3">修复人员</td>
        <td colspan="17">${persons!}</td>
    </tr>
    <tr>
        <td colspan="3">修复因由</td>
        <td colspan="17">${repairRecord.repairReason.reason!}</td>
    </tr>
    <tr>
        <td colspan="3">修复内容</td>
        <td colspan="17">${repairRecord.repairInfo!}</td>
    </tr>
    <tr>
        <td colspan="3">提取时间</td>
        <td colspan="7">${repairRecord.extractDate?string("yyyy-MM-dd")}</td>
        <td colspan="3">归还时间</td>
        <td colspan="7">${repairRecord.returnDate?string("yyyy-MM-dd")}</td>
    </tr>
    <tr>
        <td colspan="3">位置</td>
        <td colspan="17">${(repairRecord.situation.position)!}</td>
    </tr>
    <tr>
        <td colspan="3">尺寸<span class="careful">(cm)</span></td>
        <td colspan="7">${(repairRecord.situation.size)!}</td>
        <td colspan="3">质量<span class="careful">(g)</span></td>
        <td colspan="7">${(repairRecord.situation.weight)!}</td>
    </tr>
    <tr>
        <td colspan="3">修复后尺寸<span class="careful">(cm)</span></td>
        <td colspan="7">${(repairRecord.situation.repairedSize)!}</td>
        <td colspan="3">修复后质量<span class="careful">(g)</span></td>
        <td colspan="7">${(repairRecord.situation.repairedWeight)!}</td>
    </tr>
    <tr>
        <td colspan="3">照片</td>
        <td colspan="17">
            <#include "base-info-photos.ftl">
        </td>
    </tr>
    <tr>
        <td colspan="3">文物描述</td>
        <td colspan="17">${(repairRecord.relic.relicPropertyMap.describe.propertyValue)!}</td>
    </tr>
    <tr>
        <td colspan="3">相关文献</td>
        <td colspan="17">${(repairRecord.relic.relicPropertyMap.literature.propertyValue)!}</td>
    </tr>
    <tr>
        <td colspan="3">备注</td>
        <td colspan="17">${(repairRecord.relic.relicPropertyMap.remark.propertyValue)!}</td>
    </tr>
</table>
