<#--
措施管理
@author liuzhu
@date 2014-04-11
-->

<#assign title=locale.getStr("blueplanet.alarm.managementMeasure")>

<#macro head>

</#macro>

<#macro content>

<div class="container-fluid">
    <div class="row-fluid" style="margin-top: 15px;">
        <div class="span12">
            <#include "message.ftl">
            <@messageTooltip/>
            <#if measureVOList?size!=0>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>
                        ${locale.getStr("common.number")}
                        </th>
                        <th>
                        ${locale.getStr("blueplanet.alarm.decisionDescription")}
                        </th>
                        <th>
                        ${locale.getStr("common.time")}
                        </th>
                        <th>
                        ${locale.getStr("common.operating")}
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list measureVOList as measure>
                        <tr>
                            <td>${measure_index+1}</td>
                            <td>${measure.description}</td>
                            <td>${measure.createTime?string('yyyy-MM-dd')}</td>
                            <td>
                                <a class="btn btn-mini btn-info updateMeasure" title="${locale.getStr("common.update")}"
                                   data-id="${measure.id}">${locale.getStr("common.update")}</a>
                                <a class="btn btn-mini btn-danger deleteMeasure"
                                   title="${locale.getStr("common.delete")}"
                                   data-id="${measure.id}">${locale.getStr("common.delete")}</a>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            <#else>
                <h4>${locale.getStr("common.noData")}</h4>
            </#if>

            <button id="addMeasure" class="btn btn-primary f-r" type="button">${locale.getStr("common.insert")}</button>
        </div>
    </div>
</div>

<div class="modal hide fade" id="addFrom">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3>
        ${locale.getStr("blueplanet.alarm.addMeasure")}
        </h3>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" id="addMeasureFrom" action="alarm/doAddMeasure" method="post">
            <div class="control-group">
                <input id="measureId" name="measureId" type="hidden" value="">
                <label class="control-label">${locale.getStr("blueplanet.alarm.descriptionOfMeasures")}</label>

                <div class="controls">
                    <textarea name="description" id="description" rows="3"></textarea>
                    <span style="color: red" id="measureDescSpan"></span>
                </div>
            </div>

        </form>
    </div>
    <div class="modal-footer">
        <a id="saveBtn" class="btn btn-primary">${locale.getStr("common.save")}</a>
        <a id="returnBtn" class="btn">${locale.getStr("common.return")}</a>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="js/alarm.js"></script>
</#macro>