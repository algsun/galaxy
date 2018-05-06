<#--
超时设备汇总
@author liuzhu
@date 2013.09.29
@check @xiedeng 2013-10-11 #5867

-->
<#assign title="环境监控-设备链路">
<#assign title>${locale.getStr("blueplanet.deviceLink.title")}</#assign>
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:monitor:topo">

<#include "/common/pages/common-tag.ftl">

<#macro head>
<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
    <#include "../_common/common-css.ftl">
</#macro>

<#macro content>
<ul class="nav nav-tabs">
    <li><a href="topo.action">${locale.getStr("blueplanet.topo")}</a></li>
    <li><a href="timeoutDevice.action">${locale.getStr("blueplanet.topo.timeoutDevice")}</a></li>
    <li><a href="deviceAnalyse.action">${locale.getStr("blueplanet.topo.deviceAnalyse")}</a></li>
    <li><a href="lossPackage.action">${locale.getStr("blueplanet.topo.lossPackage")}</a></li>
    <li class="active"><a href="deviceLink.action">${locale.getStr("blueplanet.topo.deviceLink")}</a></li>
</ul>
<div class="row-fluid">
    <div class="span12">

        <form id="marksementFrom" class="well well-small form-inline" action="blueplanet/deviceLink.action"
              method="post">
            <select style="width: 200px; height: 30px;" id="device" name="nodeId" class="easyui-combobox">
                <optgroup label="${locale.getStr("common.gateway")}">
                    <#list topoNodes as node>
                        <#if node.nodeType==7>
                            <@selectOption node/>
                        </#if>
                    </#list>
                    <optgroup label="${locale.getStr("common.relay")}">
                        <#list topoNodes as node>
                            <#if node.nodeType==2>
                                <@selectOption node/>
                            </#if>
                        </#list>
                        <optgroup label="${locale.getStr("common.controlModule")}">
                            <#list topoNodes as node>
                                <#if node.nodeType==5>
                                    <@selectOption node/>
                                </#if>
                            </#list>
                            <optgroup label="${locale.getStr("common.node")}">
                                <#list topoNodes as node>
                                    <#if node.nodeType==1>
                                        <@selectOption node/>
                                    </#if>
                                </#list>
                                <optgroup label="${locale.getStr("common.mainModule")}">
                                    <#list topoNodes as node>
                                        <#if node.nodeType==3>
                                            <@selectOption node/>
                                        </#if>
                                    </#list>
                                    <optgroup label="${locale.getStr("common.childModule")}">
                                        <#list topoNodes as node>
                             <#if node.nodeType==4>
                                            <@selectOption node/>
                                        </#if>
                    </#list>
            </select>

            <label class="m-l-20" for="startDate">${locale.getStr("common.startDate")}</label>
            <input id="startDate" type="text"
                   name="startDate"
                   class="input-medium Wdate"
                   value="${startDate?string('yyyy-MM-dd HH:mm')}"/>
            <label class="m-l-20" for="endDate">${locale.getStr("common.endDate")}</label>
            <input id="endDate" type="text"
                   name="endDate"
                   class="input-medium Wdate"
                   value="${endDate?string('yyyy-MM-dd HH:mm')}"/>
            <input type="submit" class="btn" id="submit-btn" value="${locale.getStr("common.select")}">
            <span style="color: #969696; text-align: left;">(${locale.getStr("blueplanet.topo.deviceLinkTips")})</span>
        </form>
    </div>
</div>
    <#if topoLossPackage??>
    <div class="row-fluid">
    ${locale.getStr("blueplanet.topo.devicelossPack")}【${locale.getStr("blueplanet.topo.estimatedNumber")}
        ：${topoLossPackage.expectedCount?c}
        ；${locale.getStr("blueplanet.topo.actualPacketNumber")}：${topoLossPackage.actualCount?c}
        ；${locale.getStr("blueplanet.topo.packetLossRatio")}：${topoLossPackage.loseRate}%；】
    </div>
    </#if>
    <#if deviceLinkList??>

        <#if deviceLinkList?size==0>
        <h4>${locale.getStr("common.noData")}</h4>
        <#else>
        <table class="table table-bordered" style="margin-top: 10px;">
            <thead>
            <tr>
                <th>${locale.getStr("common.deviceName")}</th>
                <th>${locale.getStr("common.zone")}</th>
                <th>${locale.getStr("blueplanet.topo.parentNode")}</th>
                <th>rssi</th>
                <th>lqi</th>
                <th>${locale.getStr("common.time")}</th>
            </tr>
            </thead>
            <tbody>

                <#list deviceLinkList as topo>
                <tr>
                    <td>${topo.nodeId?substring(8,13)}<#if topo.nodeName?? && topo.nodeName!="">(${topo.nodeName!}
                        )</#if></td>
                    <td>${topo.zoomName!}</td>
                    <td>
                        <#if topo.parentIpStr??>
                            ${topo.parentIpStr?substring(8)}
                        </#if>
                    </td>
                    <td>${topo.rssi!}</td>
                    <td>${topo.lqi!}</td>
                    <td>${topo.stamp?string("yyyy-MM-dd HH:mm:ss")!}</td>
                </tr>
                </#list>
            </tbody>
        </table>
        <#--分页-->
            <#include "/common/pages/pagging.ftl" >
            <#assign sTime = startDate?string('yyyy-MM-dd HH:mm')/>
            <#assign eTime = endDate?string('yyyy-MM-dd HH:mm')/>
            <#assign url = "deviceLink.action?nodeId=${nodeId}&startDate=${sTime}&endDate=${eTime}">
            <@pagination url,index,pageCount,"index" ></@pagination>
        </#if>
    <#else>
    <h4>${locale.getStr("common.noData")}</h4>
    </#if>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
    <@scriptTag "js/2datepicker-form-validation.js"/>
<script type="text/javascript">
    $("#device").select2({
        //TODO placeholder
        placeholder: "",
        allowClear: true,
        formatNoMatches: function () {
            return message.noData
        }
    });
    $(document).ready(function () {
        var $timeInput = $("#startDate");
        var $endDate = $("#endDate");
        $timeInput.unbind("click.time");
        function planningDate(month) {
            var uom = new Date();
            uom.setMonth(uom.getMonth() + month)
            uom = uom.getFullYear() + "-" + (uom.getMonth() + 1) + "-" + uom.getDate();
            return uom;
        };
        //前3个月的数据
        var threeMonthAgo = planningDate(-3);
        var nowTime = new Date();
        //时间选择器
        $timeInput.bind("click.time", function () {
            WdatePicker({
                dateFmt: 'yyyy-MM-dd HH:mm',
                el: $timeInput[0],
                minDate: threeMonthAgo,
                maxDate: '%y-%M-%d'
            });
        });
        $endDate.bind("click.time", function () {
            WdatePicker({
                dateFmt: 'yyyy-MM-dd HH:mm',
                minDate: threeMonthAgo,
                maxDate: '%y-%M-%d'
            });
        });
    });
</script>
</#macro>
<#macro selectOption node>
<option value="${node.nodeId!}"
        <#if node.nodeId==nodeId>selected="selected" </#if>>${node.nodeId?substring(8,13)}<#if node.nodeName?? && node.nodeName!="">
    (${node.nodeName!})</#if></option>
</#macro>
