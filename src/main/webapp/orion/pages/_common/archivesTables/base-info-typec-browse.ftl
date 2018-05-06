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
        <td colspan="3">种类</td>
        <td colspan="7">${(repairRecord.situation.spinningType)!}</td>
        <td colspan="3">制作工艺</td>
        <td colspan="7">${(repairRecord.situation.craftsmanship)!}</td>
    </tr>
    <tr>
        <td colspan="3">织造工艺</td>
        <td colspan="7">${(repairRecord.situation.weavingProcess)!}</td>
        <td colspan="3">织物组织</td>
        <td colspan="7">${(repairRecord.situation.fabricPart)!}</td>
    </tr>
    <tr>
        <td colspan="3" rowspan="2">织物密度</td>
        <td colspan="2">经线</td>
        <td colspan="5">${(repairRecord.situation.densityLong)!}</td>
        <td colspan="3" rowspan="2">丝线颜色</td>
        <td colspan="2">经线</td>
        <td colspan="5">${(repairRecord.situation.colorLong)!}</td>
    </tr>
    <tr>
        <td colspan="2">纬线</td>
        <td colspan="5">${(repairRecord.situation.densityLat)!}</td>
        <td colspan="2">纬线</td>
        <td colspan="5">${(repairRecord.situation.colorLat)!}</td>
    </tr>
    <tr>
        <td colspan="3">丝线细度</td>
        <td colspan="4">${(repairRecord.situation.slender)!}</td>
        <td colspan="3">丝线捻向</td>
        <td colspan="3">${(repairRecord.situation.twistDirection)!}</td>
        <td colspan="3">丝线捻度</td>
        <td colspan="4">${(repairRecord.situation.twistDegree)!}</td>
    </tr>
    <tr>
        <td colspan="3">照片</td>
        <td colspan="17">
            <#include "base-info-photos.ftl">
        </td>
    </tr>
    <tr>
        <td colspan="3">工艺</td>
        <td colspan="17">${(repairRecord.situation.craft)!}</td>
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
