<#--
  - 光学摄像机管理
  -@author li.jianfei
  -@time  13-3-22
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.common.camera")} - ${locale.getStr("proxima.common.systemName")}</title>
<#include "../_common/common-css.ftl">
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:dVPlace:list">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:dVPlace:list"></@subNavs>

<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--your content-->

    <div class="row">
        <div class="span12">

            <form class="form-inline well well-small" action="queryDVPlaceDefault.action" method="post">
                <label>${locale.getStr("proxima.pictures.areaSelection")}</label>

                <select name="zoneId">
                    <option value="" selected="selected">${locale.getStr("common.all")}</option>
                <#list zoneList as zone>
                    <option value="${zone.id}"
                        <#if zone.id==zoneId!"">
                            selected="selected"
                        </#if>>
                    ${zone.name}</option>
                </#list>
                </select>

                <button class="btn" type="submit">${locale.getStr("common.select")}</button>
            </form>

        </div>
    </div>
<#if (pageCount>0)>
    <div class="row">
        <div class="span12">
            <table class="table">
                <thead>
                <tr>
                    <th>${locale.getStr("common.number")}</th>
                    <th>${locale.getStr("blueplanet.controller.zoneName")}</th>
                    <th>${locale.getStr("proxima.cameras.pointName")}</th>
                    <th>${locale.getStr("proxima.common.type")}</th>
                    <th>${locale.getStr("proxima.cameras.pointCode")}</th>
                    <th>${locale.getStr("blueplanet.dataCenter.createTime")}</th>
                    <th>${locale.getStr("blueplanet.common.status")}</th>
                    <th>${locale.getStr("common.operating")}</th>
                </tr>
                </thead>
                <tbody>
                    <#list dvPlaceList as dvPlace>
                    <tr>
                        <td> ${dvPlace_index+1}</td>
                        <td> ${dvPlace.zone.name!}</td>
                        <td> ${dvPlace.placeName!}</td>
                        <td><#if dvPlace.dvType==1>${locale.getStr("proxima.common.optical")}<#else>${locale.getStr("proxima.common.infrared")}</#if></td>
                        <td> ${dvPlace.placeCode!}</td>
                        <td> ${dvPlace.createTime?string("yyyy-MM-dd HH:mm")}</td>
                        <td>
                            <#if dvPlace.enable>
                                <i class="icon-ok-sign"></i>${locale.getStr("proxima.cameras.enable")}
                            <#else>
                                <i class="icon-minus-sign"></i>${locale.getStr("proxima.cameras.disable")}
                            </#if>
                        </td>
                        <td>
                            <div class="btn-toolbar" style="margin: 0;">
                                <#if security.isPermitted("proxima:dVPlace:updateOptics")>
                                    <div class="btn-group">
                                        <#if dvPlace.dvType==1>
                                            <a href="toUpdateOpticsDVPlace.action?id=${dvPlace.id}"
                                        <#else>
                                                <a href="toUpdateInfraredDVPlace.action?id=${dvPlace.id}"
                                        </#if>
                                                   class="btn btn-mini"><i
                                            class="icon-pencil"></i>${locale.getStr("common.edit")}</a>

                                    </div>
                                </#if>

                                <#if security.isPermitted("proxima:dVPlace:enableInfrared")||
                                security.isPermitted("proxima:dVPlace:enableOptics")>
                                    <div class="btn-group">
                                        <#if dvPlace.enable>
                                            <a href="updateDVPlaceEnable.action?id=${dvPlace.id}"
                                               class="btn btn-mini btn-danger"><i
                                                    class="icon-stop icon-white"></i>${locale.getStr("proxima.cameras.disable")}
                                            </a>
                                        <#else>
                                            <a href="updateDVPlaceEnable.action?id=${dvPlace.id}"
                                               class="btn btn-mini btn-success"><i
                                                    class="icon-play icon-white"></i>${locale.getStr("proxima.cameras.enable")}
                                            </a>
                                        </#if>
                                    </div>
                                </#if>
                                <#if dvPlace.dvType==1>
                                    <div class="btn-group">
                                    <span href="#" class="btn btn-mini"
                                          title="${(dvPlace.imageRealWidth?c)!0} mm(毫米)"
                                          data-role="imageRealWidth"
                                          data-width="${(dvPlace.imageRealWidth?c)!0}"
                                          data-id="${dvPlace.id}">${locale.getStr("proxima.cameras.realWidth")}</span>
                                        <button class="btn btn-mini dropdown-toggle" data-toggle="dropdown">
                                            <span class="caret"></span>
                                        </button>
                                        <ul class="dropdown-menu">
                                            <#if dvPlace.dvPort??>
                                                <li>
                                                    <a href="http://${dvPlace.dvIp}:${dvPlace.dvPort}" target="_blank">
                                                        <i class="icon-facetime-video"></i> ${locale.getStr("proxima.common.camera")}
                                                    </a>
                                                </li>
                                            </#if>
                                            <li>
                                                <a href="toUploadDVrealmapView.action?dvId=${dvPlace.id}">
                                                    <i class="icon-picture"></i> ${locale.getStr("proxima.common.JCV")}
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </#if>
                            </div>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>

        </div>
    </div>
</#if>

    <div class="hide">
        <!-- 输入图片实景宽度窗口 -->
        <div id="imageRealWidthDialog">
            <div class="input-append">
                <input class="span2" id="appendedInput" type="text">
                <span class="add-on">mm</span>
            </div>
            <div class="help-block red"></div>
        </div>
    </div>


<#include "../_common/pagging.ftl" >
<#assign url="queryDVPlaceDefault.action?zoneId=${zoneId!}">
<@pagination url index pageCount "index"></@pagination>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<@scriptTag "../common/js/util.js"/>
<@scriptTag "js/opticsDVPlace/list-dv-place.js"/>

<#include "../_common/last-resources.ftl">

</body>
</html>

