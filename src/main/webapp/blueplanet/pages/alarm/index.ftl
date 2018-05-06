<#--
监测预警
@author liuzhu
@date 2014-04-11
-->

<#assign title=locale.getStr("blueplanet.zone.monitoringwarning")>

<#macro head>
    <#include "/common/pages/common-tag.ftl"/>
    <#include "../_common/common-head.ftl">
    <#include "../_common/common-css.ftl">
    <@linkTag "css/zone/zone-manage.css"/>
<style type="text/css">
    .zone-child ul {
        list-style: none;
        margin: 0px;
    }
</style>
</#macro>

<#macro content>
<div id="parent-zone-id" style="display: none;">${zoneId!}</div>
<div class="zone-list">
    <div class="zone-pre">
        <div class="zone-pre-a">
            <a href="alarm/index<#if (zone.parentId)??>/${zone.parentId}</#if>"
               style="font-weight: bold">
                <i class="icon-chevron-left"></i>
                <span class="v-a-2">${locale.getStr("blueplanet.alarm.superior")}</span>
            </a>
        </div>
        <span style="background-color: #d3d3d3;width:1px;height:60px;float: left;"></span>

        <div class="zone-current">
        ${locale.getStr("blueplanet.alarm.current")}：
            <a href="alarm/index">${currentSite.siteName}</a>
        <#--倒序显示-->
            <#list currentZones?reverse as cz>
                >
                <a href="alarm/index/${cz.zoneId}">${cz.zoneName}</a>
            </#list>
        </div>
    </div>
    <div class="zone-children">
        <div class="zone-child zone-title ">
            <div class="row-fluid" style="text-align: center">
                <div class="span4">
                    <span class="title-text">${locale.getStr("common.zone")}</span>
                </div>
                <div class="span4" style="text-align: center;">
                    <span>${locale.getStr("blueplanet.alarm.threshold")}</span>
                </div>
               <#-- <div class="span4" style="text-align: left;">
                    <span>${locale.getStr("blueplanet.controller.measure")}</span>
                </div>-->
                <div class="span4">
                    <span>${locale.getStr("common.operating")}</span>
                </div>
            </div>
        </div>
        <#if zones?size<1>
            <div class="no-zone-msg">${locale.getStr("blueplanet.alarm.currentAreaNoLowerArea")}</div>
        </#if>
        <#list zones as zone>
            <div class="zone-child zone-row">
                <div class="zone-id" style="display: none;">${zone.zoneId!}</div>
                <div class="zone-name" style="display: none;">${zone.zoneName!}</div>
                <div class="zone-child-view">
                    <div class="zone-column zone-name">
                        <a href="alarm/index/${zone.zoneId!}"><span
                                class="v-a-2" style="font-size: 25px;margin-left: 160px"><i class="icon-home" style="margin-top: 10px;"></i>${zone.zoneName!}</span></a>
                    </div>

                    <div style="float: right;margin-right: 160px;"><a
                            href="zone/${zone.zoneId!}/threshold/view">${locale.getStr("blueplanet.alarm.thresholdConfiguration")}</a>
                    </div>
                </div>
                <div class="zone-info-alter" style="display: block;">
                    <div class="row-fluid m-t-10" style="text-align: center;margin-left: 300px;">
                        <div class="span2"></div>
                        <div class="span4" style="text-align: left">
                            <ul>
                                <#if zone.thresholdVOs?size!=0>
                                    <#list zone.thresholdVOs as threshold>
                                        <li>
                                        ${threshold_index+1}.${threshold.cnName}
                                            <#if threshold.target??>
                                                ${locale.getStr("blueplanet.alarm.target")}${threshold.target?c}${threshold.units}
                                            </#if>
                                            <#if threshold.floating??>
                                                ，${locale.getStr("blueplanet.alarm.floating")}${threshold.floating?c}${threshold.units}
                                            </#if>
                                            <#if zone.thresholdVOs?size==threshold_index+1>。<#else>；</#if>
                                        </li>
                                    </#list>
                                <#else>
                                ${locale.getStr("common.noData")}
                                </#if>
                            </ul>
                        </div>
                        <#--<div class="span4" style="text-align: left">
                            <ul>
                                <#if zone.measureVOs?size!=0>
                                    <#list zone.measureVOs as measure>
                                        <li>
                                        ${measure_index+1}.${measure.description}
                                            <#if zone.measureVOs?size==measure_index+1>。<#else>；</#if>
                                        </li>
                                    </#list>
                                <#else>
                                ${locale.getStr("common.noData")}
                                </#if>
                            </ul>
                        </div>
                        <div class="span2"></div>-->
                    </div>
                </div>
            </div>
        </#list>
    </div>
</#macro>
<#macro script>
</#macro>


