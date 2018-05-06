<#--
  - 光学摄像机管理
  -@author li.jianfei
  -@time  13-3-22
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>查询摄像机 - 本体监测</title>
<#include "../_common/common-css.ftl">
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:opticsDVPlace">
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

            <form class="form-inline well well-small" action="queryOpticsDVPlace.action" method="post">
                <label>区域选择</label>

                <select name="zoneId">
                    <option value="" selected="selected">全部</option>
                <#list zoneList as zone>
                    <option value="${zone.id}"
                        <#if zone.id==zoneId!"">
                            selected="selected"
                        </#if>>
                    ${zone.name}</option>
                </#list>
                </select>

                <button class="btn" type="submit">查询</button>
            </form>

        </div>
    </div>
<#if (pageCount>0)>
    <div class="row">
        <div class="span12">
            <table class="table">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>区域名称</th>
                    <th>点位名称</th>
                    <th>点位编码</th>
                    <th>添加时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#list dvPlaceList as dvPlace>
                    <tr>
                        <td> ${dvPlace_index+1} </td>
                        <td> ${dvPlace.zone.name!} </td>
                        <td> ${dvPlace.placeName!} </td>
                        <td> ${dvPlace.placeCode!} </td>
                        <td> ${dvPlace.createTime?string("yyyy-MM-dd HH:mm")} </td>
                        <td>
                            <#if dvPlace.enable>
                                <i class="icon-ok-sign"></i>启用
                            <#else>
                                <i class="icon-minus-sign"></i>停用
                            </#if>
                        </td>
                        <td>
                            <div class="btn-toolbar" style="margin: 0;">

                                <#if security.isPermitted("proxima:opticsDVPlace:update")>
                                    <div class="btn-group">
                                        <a href="toUpdateOpticsDVPlace.action?id=${dvPlace.id}"
                                           class="btn btn-mini"><i class="icon-pencil"></i>编辑</a>
                                    </div>
                                </#if>

                                <#if security.isPermitted("proxima:opticsDVPlace:enable")>
                                    <div class="btn-group">
                                        <#if dvPlace.enable>
                                            <a href="updateOpticsDVPlaceEnable.action?id=${dvPlace.id}"
                                               class="btn btn-mini btn-danger"><i
                                                    class="icon-stop icon-white"></i>停用</a>
                                        <#else>
                                            <a href="updateOpticsDVPlaceEnable.action?id=${dvPlace.id}"
                                               class="btn btn-mini btn-success"><i
                                                    class="icon-play icon-white"></i>启用</a>
                                        </#if>
                                    </div>
                                </#if>
                                <div class="btn-group">
                                    <span href="#" class="btn btn-mini"
                                          title="${(dvPlace.imageRealWidth?c)!0} mm(毫米)"
                                          data-role="imageRealWidth"
                                          data-width="${(dvPlace.imageRealWidth?c)!0}"
                                          data-id="${dvPlace.id}">实景宽度</span>
                                    <button class="btn btn-mini dropdown-toggle" data-toggle="dropdown">
                                        <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="http://${dvPlace.dvIp}:${dvPlace.dvPort}" target="_blank">
                                                <i class="icon-facetime-video"></i> 摄像机</a>
                                        </li>
                                        <li>
                                            <a href="toUploadDVrealmapView.action?dvId=${dvPlace.id}">
                                                <i class="icon-picture"></i> 实景图</a>
                                        </li>
                                    </ul>
                                </div>
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
                <span class="add-on">mm(毫米)</span>
            </div>
            <div class="help-block red"></div>
        </div>
    </div>


<#include "../_common/pagging.ftl" >
<#assign url="queryOpticsDVPlace.action?zoneId=${zoneId!}">
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

