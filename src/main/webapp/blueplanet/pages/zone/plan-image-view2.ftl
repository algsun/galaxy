<#--
平面图预览

@author gaohui
@date 2013-10-09
-->

<#assign title>${locale.getStr("blueplanet.planImageView.title")}</#assign>

<#include "/common/pages/common-tag.ftl"/>

<#macro head>
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>

    <@linkTag "css/zone/plan-image2.css"/>

<style type="text/css">
    .zone-image {
        background-image: url('${resourcesPath!}/${zone.planImage!}?_=${.now?long?c}');
    }
</style>
</#macro>

<#assign tabIndex = 2>

<#macro content>
<div ng-app="app"
     ng-controller="MainController"
     ng-mousemove="unDeployNodeMoving($event)"
     ng-mouseup="unDeployNodeMouseUp($event)"
     ng-init="init('${zoneId}', <#if zone.planImage??>true<#else>false</#if>)"
        >

    <div class="well well-small form-inline p-v-0">
        <label class="checkbox">
            <input type="checkbox"
                   ng-model="showLabel"
                   ng-init="showLabel=false"/>
        ${locale.getStr("blueplanet.location.LocationName")}</label>

        <label class="checkbox">
            <input type="checkbox"
                   ng-model="showNumber"
                   ng-init="showNumber=true"/>
        绑定设备</label>

        <#if security.isPermitted("blueplanet:planImage:edit")>
            <a class="btn btn-link" title="${locale.getStr("blueplanet.zone.editMode")}" href="zone/${zoneId!}/plan-image/edit" ng-show="!editMode()">
                <i class="icon-pencil"></i>${locale.getStr("blueplanet.location.edit")}</a>
        </#if>

        <a class="btn btn-link" title="${locale.getStr("blueplanet.zone.browseMode")}" href="zone/${zoneId!}/plan-image" ng-show="editMode()"><i
                class="icon-eye-open"></i> ${locale.getStr("blueplanet.threeDimensional.browse")}</a>

        <a class="btn btn-link" title="${locale.getStr("blueplanet.zone.cancelDeployment")}" ng-show="editMode() && showUnDeploy()"
           ng-click="unDeployLocation()">${locale.getStr("blueplanet.zone.cancelDeployment")}</a>

        <a class="btn btn-link" title="${locale.getStr("blueplanet.zone.cancelDisplay")}" ng-show="editMode() && showUnShow()" ng-click="unShowLocation()">${locale.getStr("blueplanet.zone.cancelDisplay")}</a>
    </div>

    <!-- 平面图 -->
    <div id="zone-image" class="zone-image"
         ng-mousemove="movingNode($event)"
         ng-mouseup="endMove($event)"
            >
        <span ng-hide="hasPlanImage">${locale.getStr("blueplanet.zone.noPlan")}</span>
        <span ng-show="hasPlanImage && loadImageError">${locale.getStr("blueplanet.zone.imageLoadFailed")}</span>

        <!-- 节点 -->
        <div ng-repeat="node in nodes" class="node"
             ng-style="nodeStyle(node)"
             ng-class="{ 'selected': node.selected }"
             ng-mousedown="startMove($event, node)"
             ng-mousemove="nodeMouseMove($event, node)"
             ng-mouseenter="nodeMouseEnter($event, node)"
             ng-mouseleave="nodeMouseLeave($event, node)"
             ng-mouseup="toggleSelect(node)"
             ng-dblclick="nodeDoubleClick(node)">

            <!-- 节点图标 -->
            <span class="node-icon" style="background-image: url('{{iconOfNode(node)}}')"></span>
            <!-- 节点名称 -->
            <span class="name-label" ng-show="showLabel" ng-bind="node.name"></span>
            <#--设备编号-->
            <span class="name-label" ng-show="showNumber" ng-bind="node.nodeId"></span>

        </div>

        <!-- 节点 -->
        <div ng-repeat="node in nodes" class="node"
             ng-style="nodeStyle(node)"
             ng-class="{ 'selected': node.selected }"
             ng-mousedown="startMove($event, node)"
             ng-mousemove="nodeMouseMove($event, node)"
             ng-mouseenter="nodeMouseEnter($event, node)"
             ng-mouseleave="nodeMouseLeave($event, node)"
             ng-mouseup="toggleSelect(node)"
             ng-dblclick="nodeDoubleClick(node)">

            <div class="node-tooltip" ng-show="nodeShowTooltip(node)"
                 ng-mousedown="stopProp($event)"
                 ng-click="stopProp($event)"
                    >
                <div class="content">
                    <div ng-show="node.type == 'zone' || node.type == 'dvPlace' ">
                        <div ng-show="node.name">${locale.getStr("blueplanet.location.name")}：{{node.name}}</div>
                    </div>

                    <div ng-show="node.type == 'location' || node.type == 'offline-location'">
                    <#--<div ng-show="node.id">位置点ID：{{node.id}}</div>-->
                        <div style="text-align:center;">
                            <img src="{{node.photo}}" ng-show="{{node.photo}}" style="border-radius:10px;max-width: 90%;"/>
                        </div>
                        <div ng-show="node.nodeName">${locale.getStr("blueplanet.location.LocationName")}：{{node.nodeName}}</div>
                        <div ng-show="node.device">
                            <div>${locale.getStr("blueplanet.location.bindingEquipment")}：{{node.device.nodeId}}</div>
                            <div ng-show="node.sensors">
                            ${locale.getStr("blueplanet.common.realtimeData")}：
                                <ul class="m-b-0" style="list-style-type: none;">
                                    <li ng-repeat="sensor in node.sensors">
                                        <div ng-if="sensor.state == 0">
                                            {{sensor.cnName}}：--
                                        </div>
                                        <div ng-if="sensor.state != 0">
                                            {{sensor.cnName}}：{{sensor.sensorPhysicalValue}}{{sensor.units}}
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <div ng-show="node.stamp">${locale.getStr("blueplanet.common.status")}：{{anomaly(node)}}</div>
                            <div ng-show="node.lowvoltage">${locale.getStr("blueplanet.zone.voltage")}：{{node.lowvoltage}}</div>
                            <div ng-show="node.lqi">${locale.getStr("blueplanet.location.linkQuality")}： <i class="mw-icon-connect"></i>{{node.lqi}}</div>
                            <div ng-show="node.rssi">${locale.getStr("blueplanet.location.signalIntensity")}：<i class="mw-icon-wifi"></i>{{node.rssi}}</div>
                            <div ng-show="node.stamp">${locale.getStr("common.time")}：{{node.stamp}}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div ng-show="editMode()">
        <h3>${locale.getStr("blueplanet.zone.notDeployedList")}</h3>
        <ul id="undeployed" ng-controller="UndeployedNodesController">
            <li ng-repeat="node in undeployedNodes | orderBy:orderNodes:true"
                ng-mousedown="nodeMouseDown($event, node)"
                ng-mousemove="nodeMouseMove($event, node)"
                ng-click="clickNode(node)"><span class="node-icon"
                                                 ng-style="background-image: url('{{iconOfNode(node)}}')"></span>{{node.name}}
            </li>
        </ul>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="../assets/angular/1.2.20/angular.min.js"></script>
<script type="text/javascript" src="../assets/angular/1.2.20/angular-sanitize.min.js"></script>
<script type="text/javascript" src="js/pnotify.js"></script>

    <@scriptTag "js/zone/plan-image2.js"/>
<script type="text/javascript">
    var blueplanet = App("blueplanet");
    blueplanet.zoneLocationPath.atPage("plan-image");

    <#-- 浏览模式 -->
    window.ZoneImageOption.editMode = <#if editMode>true<#else>false</#if>;
</script>
</#macro>
