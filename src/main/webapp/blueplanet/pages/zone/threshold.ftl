<#--
报警阈值设置
@author liuzhu
@time  2013-09-02

@check @wang.geng 2013年9月2日 #5345
-->
<#assign title>${locale.getStr("blueplanet.threshold.title")}</#assign>

<#include "/common/pages/common-tag.ftl"/>
<#-- head -->
<#macro head>
<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
<style type="text/css">
    #target .checkbox.inline {
        margin-left: 10px;
    }
</style>
</#macro>

<#assign tabIndex = 3>

<#macro content>
<div class="threshold-set-container">
    <#if sensorinfoVOList?? && sensorinfoVOList?size!=0>
    <div id="chooseTarget">
    <#else>
    <div class="hide" id="chooseTarget">
    </#if>
        <div style="float: left;width: 50%">
            <h5>${locale.getStr("common.monitoringIndicators")}</h5>
        </div>
        <div style="float: left;width: 50%; text-align: right">
            <#if parentId??>
                <a href="alarm/index/${parentId!}">${locale.getStr("blueplanet.zone.monitoringwarning")}</a>
            <#else>
                <a href="alarm/index">${locale.getStr("blueplanet.zone.monitoringwarning")}</a>
            </#if>

        </div>
        <input type="hidden" id="zoneId" value="${zoneId!}">

            <div class="row-fluid">
                <div class="span8" id="target">
                    <#if sensorinfoVOList?? && sensorinfoVOList?size!=0>
                        <#list sensorinfoVOList as SensorinfoVO>
                            <label class='checkbox inline '>
                                <input type='checkbox' name='sensor' title='${SensorinfoVO.sensorPhysicalid}'
                                       id="${SensorinfoVO.cnName}"
                                       value='${SensorinfoVO.cnName}(${SensorinfoVO.units})'> ${SensorinfoVO.cnName}
                            </label>
                        </#list>
                    </#if>
                </div>

                <div class="span2">
                    <label class="checkbox inline"><input type="checkbox" id="all"><span
                            style="font-weight: bold;font-size: 12px;">${locale.getStr("common.all")}</span></label>
                    <button class="btn btn-mini inline m-t-5 m-l-5" id="add" style="font-weight: bold">${locale.getStr("common.insert")}</button>
                </div>
            </div>
    </div>

    <form id="thresholdData">
        <input type="hidden" name="zoneId" value="${zoneId!}">
     <#--未知-->
        <div class="m-t-20">
            <div id="thresholdInfo" name="fff">
                <table class="table table-bordered" id="dataTable">
                    <input type="hidden" id="thresholdSize" value="${zoneThresholdVOs?size}">
                    <#if zoneThresholdVOs?size!=0>
                        <tr>
                            <td style="width: 150px;">${locale.getStr("common.monitoringIndicators")}</td>
                            <td style="width: 100px;">${locale.getStr("blueplanet.zone.conditionType")}</td>
                            <td style="width: 100px;">${locale.getStr("blueplanet.zone.targetValue")}</td>
                            <td style="width: 100px;">${locale.getStr("blueplanet.zone.floatingValue")}</td>
                            <td style="width: 100px;">${locale.getStr("common.operating")}</td>
                        </tr>
                        <#list zoneThresholdVOs as tsvo>
                            <tr id=${tsvo.sensorPhysicalId}>
                                <td>
                                ${tsvo.cnName}(${tsvo.units})
                                </td>
                                <td>
                                    <select id="${tsvo.sensorPhysicalId}conditionType" style="width: 100px;"
                                            name="maps[${tsvo_index}].conditionType"
                                            class="conditionType">
                                        <option value="1"
                                                <#if tsvo.conditionType == 1>selected="selected" </#if>>
                                            ${locale.getStr("blueplanet.zone.arrage")}
                                        </option>
                                        <option value="2" <#if tsvo.conditionType == 2>selected="selected" </#if>>
                                            ${locale.getStr("blueplanet.zone.greaterThan")}
                                        </option>
                                        <option value="3" <#if tsvo.conditionType == 3>selected="selected" </#if>>
                                            ${locale.getStr("blueplanet.zone.lessThan")}
                                        </option>
                                        <option value="4" <#if tsvo.conditionType == 4>selected="selected" </#if>>
                                            ${locale.getStr("blueplanet.zone.thanOrEqual")}
                                        </option>
                                        <option value="5" <#if tsvo.conditionType == 5>selected="selected" </#if>>
                                            ${locale.getStr("blueplanet.zone.lessThan")}
                                        </option>
                                        <option value="6" <#if tsvo.conditionType == 6>selected="selected" </#if>>
                                            ${locale.getStr("blueplanet.zone.equal")}
                                        </option>
                                    </select>
                                </td>
                                <td>
                                    <input type="hidden" name="maps[${tsvo_index}].sensorPhysicalId"
                                           value="${tsvo.sensorPhysicalId}">
                                    <input type="hidden" id="${tsvo.sensorPhysicalId}Id"
                                           value="${tsvo.cnName}(${tsvo.units})" title="${tsvo.cnName!}">
                                    <input type="hidden" name="maps[${tsvo_index}].cnName" value="${tsvo.cnName!}">
                                    <input type="text" class="target" id="${tsvo.sensorPhysicalId}target"
                                           style="width: 100px;"
                                           name="maps[${tsvo_index}].target"
                                        <#if tsvo.target??>
                                           value="${tsvo.target?c}"
                                        </#if>
                                            />
                                    <span id="${tsvo.sensorPhysicalId!}targetMsg" class="inline"
                                          style="color:red;"></span>
                                </td>
                                <td>
                                <input type="text" class="floating" id="${tsvo.sensorPhysicalId}floating"
                                       style="width: 100px;"
                                       name="maps[${tsvo_index}].floating"
                                    <#if tsvo.conditionType != 1>
                                       disabled="disabled"
                                    </#if>
                                    <#if tsvo.conditionType == 1>
                                        <#if tsvo.floating??>
                                       value="${tsvo.floating?c}"
                                        </#if> / >
                                    </#if>
                                    <span id="${tsvo.sensorPhysicalId!}floatingMsg" class="inline"
                                          style="color:red;"></span>
                                </td>
                                <td>
                                        <span class="btn btn-mini btn-danger"
                                              onclick='deleteThreshold(${tsvo.sensorPhysicalId},"${tsvo.cnName}","${tsvo.cnName}(${tsvo.units})")'>
                                                <i class="icon-trash icon-white"></i> ${locale.getStr("common.delete")}
                                        </span>
                                </td>
                            </tr>
                        </#list>
                    </#if>
                </table>
            </div>
        </div>

        <div class="row-fluid">
            <div class="span1"></div>
            <div class="span8">
                <button type="button" id="threshold-submit" class="btn btn-primary">${locale.getStr("common.save")}</button>
                <button class="btn btn-small m-l-30" id="deleteAll" type="button"><span
                        style="color: #a52a2a">${locale.getStr("blueplanet.zone.clearAll")}</span>
                </button>
            </div>
        </div>
    </form>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="js/threshold.js"></script>
<script type="text/javascript">
    var blueplanet = App("blueplanet");
    blueplanet.zoneLocationPath.atPage("threshold");
</script>
</#macro>