<#--
超时设备汇总
@author liuzhu
@date 2013.09.29
@check @xiedeng 2013-10-11 #5867

-->
<#assign title="环境监控-超时设备">
<#assign title>${locale.getStr("blueplanet.timeoutDevice.title")}</#assign>
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:monitor:topo">
<#include "/common/pages/common-tag.ftl">

<#macro head>
<link rel="stylesheet" href="../assets/select2/3.3.1/select2.css">
</#macro>

<#macro content>
<ul class="nav nav-tabs">
    <li><a href="topo.action">${locale.getStr("blueplanet.topo")}</a></li>
    <li class="active"><a href="timeoutDevice.action">${locale.getStr("blueplanet.topo.timeoutDevice")}</a></li>
    <li><a href="deviceAnalyse.action">${locale.getStr("blueplanet.topo.deviceAnalyse")}</a></li>
    <li><a href="lossPackage.action">${locale.getStr("blueplanet.topo.lossPackage")}</a></li>
    <li><a href="deviceLink.action">${locale.getStr("blueplanet.topo.deviceLink")}</a></li>
</ul>
    <#if topoViewVOList??>
        <#if topoViewVOList?size==0>
            <h4>${locale.getStr("common.noData")}</h4>
        <#else>
            <table class="table table-bordered" style="margin-top: 10px;">
                <thead>
                <tr>
                    <th>${locale.getStr("common.zone")}</th>
                    <th>${locale.getStr("common.deviceName")}</th>
                    <th>${locale.getStr("common.deviceType")}</th>
                    <th>${locale.getStr("blueplanet.topo.timeoutTime")}</th>
                    <th>${locale.getStr("blueplanet.topo.isControllable")}</th>
                    <th>${locale.getStr("blueplanet.topo.parentNode")}</th>
                    <th>rssi</th>
                    <th>lqi</th>
                </tr>
                </thead>
                <tbody>
                    <#list topoViewVOList as topo>
                    <tr>
                        <td>
                            <#if topo.zoomName??>
                                ${topo.zoomName!}
                            <#else>
                                ${locale.getStr("common.noData")}
                            </#if>
                        </td>
                        <td>${topo.nodeId?substring(8,13)}<#if topo.nodeName?? && topo.nodeName!="">(${topo.nodeName!}
                            )</#if></td>
                        <td>
                            <#if topo.nodeType==1>
                                ${locale.getStr("common.node")}
                            <#elseif topo.nodeType==2>
                                ${locale.getStr("common.relay")}
                            <#elseif topo.nodeType==3>
                                ${locale.getStr("common.mainModule")}
                            <#elseif topo.nodeType==4>
                                ${locale.getStr("common.childModule")}
                            <#elseif topo.nodeType==5>
                                ${locale.getStr("common.controlModule")}
                            <#elseif topo.nodeType==7>
                                ${locale.getStr("common.gateway")}
                            </#if>
                        </td>
                        <td>${topo.stamp?string("yyyy-MM-dd hh:mm:ss")!}</td>
                        <td>
                            <#if topo.control==0>
                                ${locale.getStr("blueplanet.topo.controllable")}
                            <#elseif topo.control==1>
                                ${locale.getStr("blueplanet.topo.uncontrollable")}
                            </#if>
                        </td>
                        <td>${topo.parentIpStr?substring(8)}</td>
                        <td>${topo.rssi!}</td>
                        <td>${topo.lqi!}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </#if>
    <#else>
        <h4>${locale.getStr("common.noData")}</h4>
    </#if>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
    <@scriptTag "js/date-format.js"/>
</#macro>
<#macro selectOption node>
<option value="${node.nodeId!}"
        <#if node.nodeId==nodeId>selected="selected" </#if>>${node.nodeId?substring(8,13)}<#if node.nodeName?? && node.nodeName!="">
    (${node.nodeName!})</#if></option>
</#macro>

