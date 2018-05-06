<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>综合报告</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <style type="text/css">
        /**
        body {
            font-family: Arial, 'Microsoft Yahei', Simsun, sans-serif;
            font-size: 14px;
        }

        table {
            font-size: 14px;
        }
        */

        /* 每项统计表格 */
        /*
        table.item {
            padding: 10px 0;
        }

        .item-icon{
            width: 100px;
            height: 100px;
            margin-left: 20px;
            margin-right: 20px;;
        }

        .important {
            color: rgb(185, 74, 72);
            display: inline;
            font-family: Arial, 'Microsoft Yahei', Simsun, sans-serif;
            font-size: 16px;
            font-weight: bold;
            height: auto;
            line-height: 35px;
            width: auto;
        }
        */
    </style>
</head>
<body style="<@body/>">
<table border="0" cellpadding="0" cellspacing="0" width="100%" bgcolor="#ececec" style="<@table/>">
    <tr>
        <td style="padding: 20px;">
            <table bgcolor="#FFFFFF" align="center" border="1" cellpadding="0" cellspacing="0" width="600"
                   style="border-collapse: collapse; border-color: #ddd;">
                <tr align="center" bgcolor="#7fb80e">
                    <td style="color: white; padding: 10px 0;">
                        <h1>智慧博物馆.综合报告</h1>
                        <span style="font-size: 1.2em; font-weight: bold; margin: 0; padding: 0;">${chartDate}</span>
                        <a href="${basePath}phoenix/index/summaryReport"
                           style="margin-left: 20px; padding: 5px; display:inline-block; color: white; background-color: #006dcc;; text-decoration: none;">查看详细</a>
                    </td>
                </tr>

            <#--区域对比统计结论-->
            <#if (zoneDataList?size > 0)>
                <tr>
                    <td>
                        <table class="item" style="<@item/>">
                            <tr>
                                <td><img src="http://pic.yupoo.com/microwise/D2rYhqjJ/Pnwx3.png" class="item-icon" style="<@item_icon/>"/></td>
                                <td>
                                    <h3>区域统计</h3>
                                    <table style="<@table/>">
                                        <#list zoneDataList as zoneDataMap>
                                            <#list zoneDataMap?keys as sensorInfo>
                                                <#assign zoneDatas = zoneDataMap.get(sensorInfo)>
                                                <tr>
                                                    <td>
                                                        <span class="important" style="<@important/>">${sensorInfo.cnName}</span>最高的区域是
                                                        <span class="important" style="<@important/>">${zoneDatas[0].zoneName}</span>
                                                        <#if zoneDatas[0].maxTime?? >发生时刻是${zoneDatas[0].maxTime!}</#if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                    ${sensorInfo.cnName}最低的区域是
                                                        <span class="important" style="<@important/>">${zoneDatas[zoneDatas?size-1].zoneName}</span>
                                                        <#if zoneDatas[zoneDatas?size-1].minTime?? >发生时刻是${zoneDatas[zoneDatas?size-1].minTime!}</#if>
                                                    </td>
                                                </tr>
                                            </#list>
                                        </#list>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </#if>


            <#--文物出库排名结论-->
            <#if relicFrequencyByCondition??>
                <tr>
                    <td>
                        <table class="item" style="<@item/>">
                            <tr>
                                <td><img src="http://pic.yupoo.com/microwise/D2rYdZdl/n7Fzj.png" class="item-icon" style="<@item_icon/>"/></td>
                                <td>
                                    <h3>文物出库排名</h3>
                                    <table style="<@table/>">
                                        <tr>
                                            <td>
                                            ${chartDate}出库次数最多的文物是
                                <span class="important" style="<@important/>">${relicFrequencyByCondition.relicName}
                                    (${relicFrequencyByCondition.relicTotalCode})</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                总计出库次数最多的文物是
                                <span class="important" style="<@important/>">${relicFrequencyOfAll.relicName}
                                    (${relicFrequencyOfAll.relicTotalCode})</span>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </#if>


            <#--出库事件统计结论-->
            <#if outEventStats?? && outEventStats.yearSum?? >
                <tr>
                    <td>
                        <table class="item" style="<@item/>">
                            <tr>
                                <td><img src="http://pic.yupoo.com/microwise/D2rYi2wH/KzXHX.png" class="item-icon" style="<@item_icon/>"/></td>
                                <td>
                                   <h3>出库事件统计</h3>
                                    <table style="<@table/>">
                                        <tr>
                                            <td>
                                                ${chartDate}共有<span class="important" style="<@important/>">${outEventStats.yearCount}</span>
                                                    次文物出库，总计<span class="important" style="<@important/>">${outEventStats.yearSum!0}</span>件。
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </#if>

            <#--各属性饼状图结论-->
            <#if (relicCount > 0)>
                <tr>
                    <td>
                        <table class="item" style="<@item/>">
                            <tr>
                                <td><img src="http://pic.yupoo.com/microwise/D2rYiMnV/csC5X.png" class="item-icon"  style="<@item_icon/>"/></td>
                                <td>
                                    <h3>文物信息统计</h3>
                                    <table style="<@table/>">
                                        <tr>
                                            <td>
                                                文物总数 <span class="important" style="<@important/>">${relicCount?c}</span> 件。
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                时代中“<span class="important" style="<@important/>">${relicBasicStats.eraStat[0][0]}</span>”占比最高（<span
                                                    id="eraPercent" class="important" style="<@important/>">${relicBasicStats.eraStat[0][1]}</span>%），
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                级别中“<span class="important" style="<@important/>">${relicBasicStats.levelStat[0][0]}</span>”占比最高（<span
                                                    id="levelPercent" class="important" style="<@important/>">${relicBasicStats.levelStat[0][1]}</span>%），
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                质地中“<span class="important" style="<@important/>">${relicBasicStats.textureStat[0][0]}</span>”占比最高（<span
                                                    id="texturePercent"
                                                    class="important" style="<@important/>">${relicBasicStats.textureStat[0][1]}</span>%），
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                区域中“<span class="important" style="<@important/>">${relicBasicStats.zoneStat[0][0]}</span>”占比最高（<span
                                                    id="zonePercent"
                                                    class="important" style="<@important/>">${relicBasicStats.zoneStat[0][1]}</span>%）。
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </#if>

            <#--区域活动分布结论-->
            <#if userDistribution.hasData>
                <tr>
                    <td>
                        <table class="item" style="<@item/>">
                            <tr>
                                <td><img src="http://pic.yupoo.com/microwise/D2rYjPSN/7fR5I.png" class="item-icon" style="<@item_icon/>"/></td>
                                <td>
                                    <h3>区域活动分布</h3>
                                    <table style="<@table/>">
                                        <tr>
                                            <td>
                                                活动密度最高区域：<span
                                                                     class="important" style="<@important/>">${userDistribution.maxActiveArea}</span>(<span
                            class="important" style="<@important/>">${userDistribution.maxActiveAreaCount}</span>次)
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                活动最频繁的时段：<span
                                                    class="important" style="<@important/>">${userDistribution.maxActiveTime}</span>点(<span
                                                    class="important" style="<@important/>">${userDistribution.maxActiveTimeCount}</span>次)
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                其中<span
                                                    class="important" style="<@important/>">${userDistribution.maxActiveTime}</span>点间 <span
                                                    class="important" style="<@important/>">${userDistribution.maxActiveTimeArea}</span>
                                                人员活动最为密集(<span
                                                    class="important" style="<@important/>">${userDistribution.maxActiveTimeAreaCount}</span>次)
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </#if>


            <#--人员停留情况结论-->
            <#if userStopover.hasData>
            <tr>
                <td>
                    <table class="item" style="<@item/>">
                        <tr>
                            <td><img src="http://pic.yupoo.com/microwise/D2rYjdyd/pqw2g.png" class="item-icon"  style="<@item_icon/>"/></td>
                            <td>
                                <h3>人员停留情况</h3>
                                <table style="<@table/>">
                                    <tr>
                                        <td>
                                            停留总时间最长的位置是<span class="important" style="<@important/>">${userStopover.inZoneName}</span>，
                                            总计<span class="important" style="<@important/>">${userStopover.maxInTime}</span>小时
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            平均停留时间最长的位置是<span class="important" style="<@important/>">${userStopover.avgZoneName}</span>，
                                            时长为<span class="important" style="<@important/>">${userStopover.maxAvgTime}</span>小时
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span  class="important" style="<@important/>">${userStopover.inZoneName}</span>是重点管理区域呀。
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            </#if>

            <#--人员活动频率结论-->
            <#if highFrequencyUser??>
            <tr>
                <td>
                    <table class="item" style="<@item/>">
                        <tr>
                            <td><img src="http://pic.yupoo.com/microwise/D2rYjVKv/15izFC.png" class="item-icon"  style="<@item_icon/>"/></td>
                            <td>
                                <h3>人员活动频率</h3>
                                <table style="<@table/>">
                                    <tr>
                                        <td>
                                            活动最频繁的人员是<span class="important" style="<@important/>">${highFrequencyUser.userName}</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            活动最不频繁的人员是<span class="important" style="<@important/>">${lowFrequencyUser.userName}</span>
                                        </td>
                                    </tr>
                                </table>

                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            </#if>

            <#--规则触发频率结论-->
            <#if highFrequencyAction??>
            <tr>
                <td>
                    <table class="item" style="<@item/>">
                        <tr>
                            <td><img src="http://pic.yupoo.com/microwise/D2rYfhQh/pCfKF.png" class="item-icon"  style="<@item_icon/>"/></td>
                            <td>
                                <h3>规则触发频率</h3>
                                <table style="<@table/>">
                                    <tr>
                                        <td>
                                            触发最频繁的规则是<span class="important" style="<@important/>">${highFrequencyAction.actionName}</span>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            </#if>


                <tr style="background-color: #000;">
                    <td>
                        <table style="padding: 10px;">
                            <tr style="color: #ddd; font-size: 0.8em;">
                                <td><a href="${basePath}phoenix/index/summaryReport" style="color: white; text-decoration: underline;">查看详细</a> | 如果不想再收到类似邮件可以在 [ 数据分析.综合报告 ] 页面<a href="${basePath}phoenix/index/summaryReport" style="color: white;">取消订阅</a></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>

<#macro body>
font-family: Arial, 'Microsoft Yahei', Simsun, sans-serif;
font-size: 14px;
margin: 0;
padding: 0;
</#macro>

<#macro table>
font-size: 14px;
color: black;
</#macro>

<#-- 每项统计表格 -->
<#macro item>
padding: 10px 0;
</#macro>

<#-- 每项统计图标 -->
<#macro item_icon>
width: 100px;
height: 100px;
margin-left: 20px;
margin-right: 20px;;
</#macro>

<#-- 强调样式 -->
<#macro important>
color: rgb(185, 74, 72);
display: inline;
font-family: Arial, 'Microsoft Yahei', Simsun, sans-serif;
font-size: 16px;
font-weight: bold;
height: auto;
line-height: 35px;
width: auto;
</#macro>
