<#--
  -
  -@author Wang yunlong
  -@time  13-1-18  上午10:02
  @check  @gaohui 2013-02-25 #1714
  -->
<#-- 页面标题 -->

<#assign title>${locale.getStr("blueplanet.index.title")}</#assign>

<#include "/common/pages/common-tag.ftl"/>
<#-- head -->
<#macro head>
<#-- style or css -->
<link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
<link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
    <@linkTag "css/zone/zone-manage.css"/>
</#macro>


<#-- content -->
<#macro content>
<div id="parent-zone-id" style="display: none;">${zoneId!}</div>
<div class="zone-list">
    <div class="zone-pre">
        <div class="zone-pre-a">
            <a href="zone/<#if (zone.parentId)??>${zone.parentId}/</#if>manage"
               style="font-weight: bold">
                <i class="icon-chevron-left"></i>
                <span class="v-a-2">上级</span>
            </a>
        </div>
        <span style="background-color: #d3d3d3;width:1px;height:60px;float: left;"></span>

        <div class="zone-current">
            当前：
            <a href="zone/manage">${currentSite.siteName}</a>
        <#--倒序显示-->
            <#list currentZones?reverse as cz>
                >
                <a href="zone/${cz.zoneId}/manage">${cz.zoneName}</a>
            </#list>
        </div>
    </div>
    <div class="zone-children">
        <@zoneView "zone-template" "" "" ""/>
        <div class="zone-child zone-title ">
            <div class="zone-column ">
                <label class="title-text">区域</label>
            </div>
            <div class="current-zone-util zone-column">
                <div id="current-zone-add-child" class="">
                    <i class="icon-file" style="margin-top:3px;"></i>
                    <span style="">添加区域</span>
                </div>
            </div>
        </div>
        <#if zones?size<1>
            <div class="no-zone-msg">当前区域没有下级区域</div>
        </#if>
        <#list zones as zone>
            <@zoneView "zone-row" zone.zoneId zone.zoneName zone.planImage!""/>
        </#list>
    </div>
    <div style="clear: both"></div>
</div>
<div class="zone-move-tree hide">
    <div id="dialog" class="span4">
        <div id="tree" class="ztree"></div>
        <span class="help-block red m-l-20"></span>
    </div>
</div>
</#macro>


<#-- script -->
<#macro script>
<script type="text/javascript" src="../assets/jquery-form-plugin/3.19/jquery.form.js"></script>
<script type="text/javascript" src="../assets/pnotify/1.2.0/jquery.pnotify.min.js"></script>
<script type="text/javascript" src="js/pnotify.js"></script>
<script type="text/javascript">
    // 在设备树中选中当前区域
    var BluePlanet = App("blueplanet");
    BluePlanet.deviceTree.selectZone("${zoneId!}");
</script>
<#--区域管理页面交互处理-->
    <@scriptTag "js/zone/zone-manage.js"/>
<!-- html comment -->
</#macro>
<#--区域展示-->
<#macro zoneView type zoneId zoneName zonePlanImage>
<div class="zone-child ${type}">
    <div class="zone-id" style="display: none;">${zoneId!}</div>
    <div class="zone-child-view">
        <#if type="zone-template">
            <div class="zone-column zone-name">
                <a href="#"><i class="icon-home"></i><span class="v-a-2"></span></a>
                <input class="input" type="text" value="">
                <span class="new-ok">确定</span>
                <span class="new-cancel">取消</span>
            </div>
        <#else>
            <div class="zone-column zone-name">
                <a href="zone/${zoneId!}/manage"><i class="icon-home"></i><span
                        class="v-a-2">${zoneName!}</span></a>
            </div>
            <div class="zone-util">
                <div class="zone-info" title="详细"><i class="icon-info-sign"></i></div>
                <div class="zone-alter" title="编辑"><i class="icon-pencil"></i></div>
                <div class="zone-move" title="移动"><i class="icon-move"></i></div>
                <div class="zone-delete" title="删除"><i class="icon-remove"></i></div>
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
                <div class="zone-info-alter-ele zone-base-info-name">
                    <label style="width:70px;padding-top:3px;float: left">区域名称</label>
                    <label class="zone-base-info-view zone-name-label"
                           style="width:240px;padding-top:3px;font-weight: bold">${zoneName!}</label>
                    <input name="zoneName" class="zone-base-info-input zone-name-input" type="text"
                           value="${zoneName!}" style="width: 240px;margin-left: 0;"/>
                </div>
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
