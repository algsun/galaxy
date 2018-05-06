<#--
qcm
-->
<#assign title=locale.getStr("blueplanet.location.qcmTitle")>

<#macro head>
    <#include "/common/pages/common-tag.ftl"/>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
</#macro>

<#assign tabIndex = 13>
<#macro content>
<div class="row-fluid">
    <input type="hidden" id="locationId" value="${locationId!}"/>
</div>
<div class="row-fluid">
    <div id="container">
        <div class="row-fluid">
            <div class="span12" style="text-align: center">
                <h4>${locationVO.locationName}${locale.getStr("blueplanet.qcm.assessmentLevel")}</h4>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span12">
                <#list stringListMap.keySet() as index>
                    <table class="table table-bordered m-t-20">
                        <thead>
                        <tr>
                            <th style="width: 20%;text-align: center;">${locale.getStr("blueplanet.qcm.lifeCycle")}</th>
                            <th style="width: 20%;text-align: center;">${locale.getStr("common.time")}</th>
                            <th style="width: 17%;text-align: center;">${locale.getStr("blueplanet.qcm.organicPollutants")}</th>
                            <th style="width: 17%;text-align: center;">${locale.getStr("blueplanet.qcm.inorganicContaminants")}</th>
                            <th style="width: 17%;text-align: center;">${locale.getStr("blueplanet.qcm.sulfurContaminants")}</th>
                        </tr>
                        </thead>
                        <tbody>

                            <#list stringListMap.get(index) as replaceSensor>
                            <tr>
                                <#if replaceSensor_index == 0>
                                    <td
                                        <#if stringListMap.keySet()?size==1>
                                            <#if replaceSensor_index == 0>rowspan="${stringListMap.get(index)?size+1}" </#if>
                                        <#else>
                                            <#if replaceSensor_index == 0>rowspan="${stringListMap.get(index)?size+1}" </#if>
                                        </#if>
                                    >
                                    ${index}
                                    </td>
                                </#if>
                                <td style="text-align: center;">
                                ${replaceSensor.stringDate}
                                </td>
                                <td style="background-color:${replaceSensor.stringOPLevelColor!};text-align: center;">
                                    <span title="${replaceSensor.OPSum}">
                                    ${replaceSensor.stringOPLevel}
                                    </span>
                                </td>
                                <td style="background-color:${replaceSensor.stringIPLevelColor!};text-align: center;">
                                    <span title="${replaceSensor.IPSum}">
                                    ${replaceSensor.stringIPLevel}
                                    </span>
                                </td>
                                <td style="background-color:${replaceSensor.stringSPLevelCoLor!};text-align: center;">
                                    <span title="${replaceSensor.SPSum}">
                                    ${replaceSensor.stringSPLevel}
                                    </span>
                                </td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </#list>
            </div>
        </div>
        <div class="row-fluid" style="display: ${more!}">
            <div class="span12" style="text-align: right;">
                <a href="location/${locationId}/qcm_level?allDataFlag=true">
                    ${locale.getStr("blueplanet.controlPanel.more")}
                </a>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span12">
                <div class="accordion" id="accordion2">
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2"
                               href="#collapseOne" style="text-align: right">
                            ${locale.getStr("blueplanet.qcm.assessmentGuidelines")}
                            </a>
                        </div>
                        <div id="collapseOne" class="accordion-body collapse">
                            <div class="accordion-inner">
                                <div class="row-fluid">
                                    <div class="span12">
                                        <h5 style="text-align: center">
                                        ${locale.getStr("blueplanet.qcm.title")}
                                        </h5>
                                    </div>
                                </div>
                                <table class="table table-bordered" style="color: #000000">
                                    <tr>
                                        <th>${locale.getStr("blueplanet.qcm.airQuality")}</th>
                                        <th style="background-color:#b3b3b3">${locale.getStr("blueplanet.qcm.organicPollutants")}</th>
                                        <th style="background-color:#b3b3b3">${locale.getStr("blueplanet.qcm.level")}</th>
                                        <th style="background-color: #fcefa1">${locale.getStr("blueplanet.qcm.inorganicContaminants")}</th>
                                        <th style="background-color: #fcefa1">${locale.getStr("blueplanet.qcm.level")}</th>
                                        <th style="background-color: #b9def0">${locale.getStr("blueplanet.qcm.sulfurContaminants")}</th>
                                        <th style="background-color: #b9def0">${locale.getStr("blueplanet.qcm.level")}</th>
                                    </tr>
                                    <#list evaluateCriterions as evaluateCriterion>
                                        <tr>
                                            <td style="background-color: ${evaluateCriterion.color};">${evaluateCriterion.airQuality}</td>
                                            <td style="background-color:#b3b3b3;"><${evaluateCriterion.opRange}</td>
                                            <td style="background-color:#b3b3b3;">${evaluateCriterion.opLevel}</td>
                                            <td style="background-color: #fcefa1;"><${evaluateCriterion.ipRange}</td>
                                            <td style="background-color: #fcefa1;">${evaluateCriterion.ipLevel}</td>
                                            <td style="background-color: #b9def0;"><${evaluateCriterion.spRange}</td>
                                            <td style="background-color: #b9def0;">${evaluateCriterion.spLevel}</td>
                                        </tr>

                                    </#list>
                                    <tr>
                                        <td style="background-color: #f8696b;">污染</td>
                                        <td style="background-color:#b3b3b3">>45</td>
                                        <td style="background-color:#b3b3b3">P5</td>
                                        <td style="background-color: #fcefa1">>175</td>
                                        <td style="background-color: #fcefa1">C5</td>
                                        <td style="background-color: #b9def0">>125</td>
                                        <td style="background-color: #b9def0">S5</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/lang/zh-cn.js"></script>
</script>
</#macro>