<#--
@author li.jianfei
@date 2013-06-19
@check @gaohui #4246 2013-06-20
-->
<div>
    <div>
    </div>

<#--your content-->
<#--数据列表-->
<#if (reportVOList?size > 0)>
    <div>
        <table cellpadding="0" cellspacing="0" width="600"
               style="border-radius: 4px; border-collapse: collapse; border: 1px solid #dddddd; border-left: 0; border-top: 0; ">
            <thead>
            <tr style="background-color: #F9F9F9;">
                <th style="<@thStyle />">序号</th>
                <th style="<@thStyle />">监测指标</th>
                <th style="<@thStyle />">最高值</th>
                <th style="<@thStyle />">最低值</th>
                <th style="<@thStyle />">波动值</th>
            </tr>
            </thead>
            <tbody>

                <#list reportVOList as report>
                <tr <#if report_index%2==1>style="background-color: #F9F9F9;"</#if>>
                    <td style="<@tdStyle />">${report_index + 1}</td>
                    <td style="<@tdStyle />">${report.sensorPhysicalName}(${report.units})</td>
                    <td style="<@tdStyle />">${report.maValue!"N/A"}</td>
                    <td style="<@tdStyle />">${report.minValue!"N/A"}</td>
                    <td style="<@tdStyle />">${report.waveValue!"N/A"}</td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
<#else>
    <h4>暂无数据</h4>
</#if>
</div>

<#macro thStyle>
border: 1px solid #dddddd;
border-right: 0;
border-bottom: 0;

font-size: 14px;
font-weight: bold;
vertical-align: bottom;
padding: 8px;
line-height: 20px;
text-align: left;
</#macro>

<#macro tdStyle>
border: 1px solid #dddddd;
border-right: 0;
border-bottom: 0;

font-size: 14px;
vertical-align: bottom;
padding: 8px;
line-height: 20px;
text-align: left;
</#macro>
