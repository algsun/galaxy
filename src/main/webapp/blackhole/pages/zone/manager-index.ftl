<!DOCTYPE HTML>
<html>
<head>
    <title>区域管理 - 系统管理</title>
<#include "/common/pages/common-tag.ftl"/>
<#include "../_common/common-head.ftl">
<#include "../_common/common-css.ftl">
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
<@linkTag "css/zone/zone-manage.css"/>
</head>

<body>
<!-- 导航栏 -->
<#assign currentTopPrivilege="blackhole:zone">
<#include "../_common/header.ftl">
<div id="gcontent" class="container">

<#include "../_common/message-tooltip.ftl">
    <div id="parent-zone-id" style="display: none;">${zoneId!}</div>
<#if zone??>
    <input name="parent-zone-position" type="hidden" value="${zone.position}"/>
<#else>
    <input name="parent-zone-position" type="hidden" value="0"/>
</#if>
    <div class="zone-list">
        <div class="zone-pre">
            <div class="zone-pre-a">
                <a href="zone<#if (zone.parentId)??>/${zone.parentId}</#if>"
                   style="font-weight: bold">
                    <i class="icon-chevron-left"></i>
                    <span class="v-a-2">上级</span>
                </a>
            </div>
            <span style="background-color: #d3d3d3;width:1px;height:60px;float: left;"></span>

            <div class="zone-current">
                当前：
                <a href="zone">${currentSite.siteName}</a>
            <#--倒序显示-->
            <#list currentZones?reverse as cz>
                >
                <a href="zone/${cz.zoneId}">${cz.zoneName}</a>
            </#list>
            </div>
        </div>
        <div class="zone-children">
        <@zoneView "zone-template" "" "" "" 1/>
            <div class="zone-child zone-title ">
                <div class="zone-column ">
                    <label class="title-text">区域</label>
                </div>
                <div class="current-zone-util zone-column">
                    <div id="current-zone-add-child" class="">
                        <i class="icon-file" style="margin-top:3px;"></i>
                        <span id="addZoneTemplate" style="">添加区域</span>
                    </div>
                </div>
            </div>
        <#if zones?size<1>
            <div class="no-zone-msg">当前区域没有下级区域</div>
        </#if>
        <#list zones as zone>
            <@zoneView "zone-row" zone.zoneId zone.zoneName!"" zone.planImage!"" zone.position/>
        </#list>
        </div>
        <div style="clear: both"></div>
    </div>
    <div class="zone-move-tree hide">
        <div id="dialog" class="span4" style="height:400px;overflow:auto">
            <div id="tree" class="ztree"></div>
            <span class="help-block red m-l-20"></span>
        </div>
    </div>
</div>

<!-- 页面底部 -->
<#include "../_common/footer.ftl">

<#--公共JS-->
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/jquery-form-plugin/3.19/jquery.form.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="js/pnotify.js"></script>

<#--<script type="text/javascript">-->
<#--// 在设备树中选中当前区域-->
<#--var BluePlanet = App("blackhole");-->
<#--BluePlanet.deviceTree.selectZone("${zoneId!}");-->
<#--</script>-->
<#--其他公共资源-->
<script type="text/javascript">
    $(function () {
        $(".zone-qrcode").click(function () {
            var zoneId = $(this).parents('.zone-child').children(".zone-id").text();
            var zoneName = $(this).parents('.zone-child').children(".zone-name").text();
            var $hugeQRCode = $("#zone-qrcodeDiv");
            var qrCode = $("#zone-qrcode");
            var download = $("#download");
            qrCode.attr("src", encodeURI("zone/createQRImage?qrString=" + "zn" + zoneId + "&qrFront=" + zoneName));
            download.attr("href", encodeURI("zone/downloadQRImage?qrString=" + "zn" + zoneId + "&qrFront=" + zoneName));
            if (zoneName.length > 12) {
                zoneName = zoneName.substring(0, 11) + "...";
            }
            art.dialog({
                title: zoneName + '的二维码',
                lock: true,
                opacity: 0.87,
                padding: 0,
                content: $hugeQRCode.html(),
                button: [
                    {
                        value: '关闭',
                        callback: function () {
                        }
                    }
                ]
            });
        });
    });
</script>
<@scriptTag "js/zone-manage.js"/>
<#include "../_common/last-resources.ftl">
</body>
</html>
<#macro zoneView type zoneId zoneName zonePlanImage position>
<div class="zone-child ${type}">
    <div class="zone-id" style="display: none;">${zoneId!}</div>
    <div class="zone-name" style="display: none;">${zoneName!}</div>
    <div class="zone-child-view">
        <#if type="zone-template">
            <div class="zone-column zone-name form-horizontal">
                <a href="#"><i class="icon-home"></i><span class="v-a-2"></span></a>

                    <div class="control-group">
                        <label class="control-label">名称</label>

                        <div class="controls">
                            <input id="zoneName" name="zoneName" type="text" maxlength="50">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">
                            位置
                        </label>

                        <div class="controls">
                            <label class="radio inline">
                                <input type="radio" name="position" id="indoor"
                                    <#if zone??>
                                       disabled="disabled"
                                       <#if (zone.position==1)>checked="checked"</#if>
                                    <#else>
                                       checked="checked"
                                    </#if>
                                       value=1>室内
                            </label>
                            <label class="radio inline">
                                <input type="radio" name="position" id="outdoor"
                                    <#if zone??>
                                       disabled="disabled"
                                       <#if (zone.position==2)>checked="checked"</#if>
                                    </#if>
                                       value=2>室外
                            </label>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button class="new-ok btn btn-mini btn-info">确定</button>
                            <button class="new-cancel btn btn-mini">取消</button>
                        </div>
                    </div>
            </div>
        <#else>
            <div class="zone-column zone-name">
                <a href="zone/${zoneId!}"><i class="icon-home"></i><span
                        class="v-a-2">${zoneName!}</span></a>
            </div>

            <div class="zone-util">
                <div class="zone-info" title="详细"><i class="icon-info-sign"></i></div>
                <div class="zone-alter" title="编辑"><i class="icon-pencil"></i></div>
                <div class="zone-move" title="移动"><i class="icon-move"></i></div>
                <div class="zone-delete" title="删除"><i class="icon-remove"></i></div>
                <div class="zone-qrcode">
                    <img style="width: 20px; height: 20px;"
                         src="../common/images/icon-qrcode-51.png"/></div>
                <div id="zone-qrcodeDiv" class="hide">
                    <img src="" id="zone-qrcode"
                         style="border-style:solid;border-width:thick;border-color:darkgrey;margin: 10px"/>
                    <br/> <a id="download" class="btn btn-small" style="float: right;margin-right: 10px">下载</a>
                </div>
            </div>
        </#if>
    </div>
    <div class="zone-info-alter">

        <div class="zone-info-alter-close" title="隐藏" rel="tooltip">
            <i class=" icon-eye-close"></i>
        </div>
        <div class="zone-update">
            <input type="hidden" name="zoneId" value="${zoneId!}">
            <input type="hidden" name="image" value="${zonePlanImage!}">

            <div class="zone-alter-submit">
                <input class="btn btn-primary" type="submit" value="保存">
            </div>

            <div class="zone-base-info">
                <form class="zone-info-alter-ele zone-base-info-name">
                    <label style="width:70px;padding-top:3px;float: left">区域名称</label>
                    <label class="zone-base-info-view zone-name-label"
                           style="width:240px;padding-top:3px;font-weight: bold">${zoneName!}</label>
                    <input name="zoneName" class="zone-base-info-input zone-name-input" type="text"
                           value="${zoneName!}" style="width: 240px;margin-left: 0;"/>
                    <label class="control-label" style="width:70px;padding-top:3px;float: left">
                        位置
                    </label>
                    <label class="radio inline">
                        <input type="radio" name="position" id="indoor"
                            <#if zone??>
                               disabled="disabled"
                            </#if>
                               <#if position==1>checked="checked"</#if>
                               value=1>室内
                    </label>
                    <label class="radio inline">
                        <input type="radio" name="position" id="outdoor"
                            <#if zone??>
                               disabled="disabled"
                            </#if>
                               <#if position==2>checked="checked"</#if>
                               value=2>室外
                    </label>
                </form>
            </div>
            <div class="zone-image">
                <div class="m-b-10 p-l-10">平面部署图</div>
                <div class="upload-zone-image zone-base-info-input">
                    <form class="upload-planimage-form" enctype="multipart/form-data"
                          action="zone/planimage/upload.json" method="post">
                        <input type="hidden" name="zoneId" value="${zoneId!}">
                        <input name="image" class="image-input" type="file" style="width:210px;">
                        <button class=" image-upload-btn btn btn-mini" onclick="function(){return false;}">
                            上传图片
                        </button>
                    </form>
                </div>
                <div class="zone-image-view">
                    <#if zonePlanImage!="">
                        <img src="${resourcesPath!}/${zonePlanImage!}?_=${.now?long?c}">
                    <#else>
                        <label class="muted">暂无实景图</label>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>

</#macro>
