<#--
  -添加红外摄像机点位
  -@author xu.yuexi
  -@time  14-4-3
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.cameras.add.title")} - ${locale.getStr("proxima.common.systemName")}</title>

<#include "../_common/common-css.ftl">


</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:opticsDVPlace">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:dVPlace:addInfrared"></@subNavs>

<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--your content-->

    <div class="row">
        <div class="span12">
            <form name="form1" action="addInfraredDVPlace.action" method="post"
                  class="form-horizontal">
                <fieldset>
                    <legend>
                    ${locale.getStr("proxima.cameras.add.infrared")}
                    </legend>
                    <div class="control-group">
                        <label class="control-label" for="zone-input">
                            <span class="red">*</span>${locale.getStr("blueplanet.controller.zoneName")}
                        </label>

                        <div class="controls">
                            <input id="zoneId" name="dvPlace.zone.id" type="hidden">
                            <input id="zone-input" data-zone-id="" type="text">
                            <span id="zone-input-help" class="help-inline"></span>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="dvPlaceName-input">
                            <span class="red">*</span>${locale.getStr("proxima.cameras.pointName")}
                        </label>

                        <div class="controls">
                            <input id="dvPlaceName-input" type="text"
                                   name="dvPlace.placeName" maxlength="14">
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
                                <option value="${ftp.id}"> ${ftp.name}</option>
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
                                   maxlength="50"/>
                            <span id="dvPlaceIp-input-help" class="help-inline"></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="dvPlaceRemark-input">
                        ${locale.getStr("blueplanet.controlPanel.remark")}
                        </label>

                        <div class="controls">
                            <textarea id="dvPlaceRemark-input" name="dvPlace.remark"
                                      maxlength="200"></textarea>
                        </div>
                    </div>
                    <div class="form-actions">
                        <button type="submit" id="submit-button"
                                class="btn btn-primary">
                        ${locale.getStr("common.save")}
                        </button>
                        <a href="queryOpticsDVPlace.action" class="btn">${locale.getStr("common.return")}</a>
                    </div>
                </fieldset>
            </form>

            <div class="hide">
                <div id="zoneTreeDialog" class="span4">
                    <ul id="zoneTree" class="ztree"></ul>
                    <p class="help-block m-t-10 red"></p>
                </div>
            </div>
        <#include "../_common/footer.ftl">
        <#include "../_common/common-js.ftl">

            <script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
            <script type="text/javascript" src="js/dvPlaceJs.js"></script>

        <#include "../_common/last-resources.ftl">
</body>
</html>
