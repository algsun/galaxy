<#--
  -修改红外摄像机点位
  -@author lxu.yuexi
  -@time  14-4-15
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.cameras.edit.title")} - ${locale.getStr("proxima.common.systemName")}</title>
<#include "../_common/common-css.ftl">
<#include "/common/pages/common-tag.ftl">

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:infraredDVPlace">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:infraredDVPlace:list"></@subNavs>

<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--your content-->
    <div class="row">
        <div class="span12">

            <form name="form1" action="updateInfraredDVPlace.action" method="post"
                  class="form-horizontal">
                <input id="dvPlaceId" type="hidden" name="dvPlace.id"
                       value="${dvPlace.id}">
                <fieldset>
                    <legend>
                    ${locale.getStr("common.edit")}
                    </legend>
                    <div class="control-group">
                        <label class="control-label" for="zone-input">
                            <span class="red">*</span>${locale.getStr("blueplanet.controller.zoneName")}
                        </label>

                        <div class="controls">
                            <input id="zoneId" name="dvPlace.zone.id" type="hidden" value="${dvPlace.zone.id}">
                            <input id="zone-input" name="dvPlace.zone.name" data-zone-id="" type="text"
                                   value="${dvPlace.zone.name}">
                            <span id="zone-input-help" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="dvPlaceName-input">
                            <span class="red">*</span>${locale.getStr("proxima.cameras.pointName")}
                        </label>

                        <div class="controls">
                            <input id="dvPlaceName-input" type="text"
                                   name="dvPlace.placeName"
                                   value="${dvPlace.placeName}"
                                   maxlength="14">
                            <span id="dvPlaceName-input-help" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="ftp-select">
                            <span class="red">*</span>FTP
                        </label>

                        <div class="controls">
                            <select id="ftp-select" name="dvPlace.ftp.id">
                            <#list ftpList as ftp>
                                <option value="${ftp.id}"
                                    <#if ftp.id==dvPlace.ftp.id>
                                        selected="selected"
                                    </#if>>
                                ${ftp.name}
                                </option>
                            </#list>
                            </select>
                            <span id="ftp-select-help" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="dvPlaceIp-input">
                            <span class="red">*</span>${locale.getStr("proxima.common.camera")} IP
                        </label>

                        <div class="controls">
                            <input id="dvPlaceIp-input" type="text" name="dvPlace.dvIp"
                                   value="${dvPlace.dvIp}" maxlength="50"/>
                            <span id="dvPlaceIp-input-help" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="dvPlaceRemark-input">
                        ${locale.getStr("blueplanet.controlPanel.remark")}
                        </label>

                        <div class="controls">
                            <textarea id="dvPlaceRemark-input" name="dvPlace.remark"
                                      maxlength="200"><#if dvPlace.remark??>${dvPlace.remark}</#if></textarea>
                        </div>
                    </div>
                    <div class="form-actions">
                        <button type="submit" id="submit-button"
                                class="btn btn-primary">
                        ${locale.getStr("common.save")}
                        </button>
                        <a href="queryDVPlaceDefault.action" class="btn">${locale.getStr("common.return")}</a>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>

</div>
</div>

<div class="hide">
    <div id="zoneTreeDialog" class="span4">
        <div id="zoneTree" class="ztree"></div>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>
<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/my97-DatePicker/4.8-beta3/WdatePicker.js"></script>
<script type="text/javascript" src="../proxima/js/dvPlaceJs.js"></script>

<#include "../_common/last-resources.ftl">
</body>
</html>
