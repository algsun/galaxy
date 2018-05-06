<#--
    -编辑红外标记区域
    -@author wang.geng
    -@date 2014-4-2
-->
<!DOCTYPE HTML>
<html>
<head>
<#--<%@include file="/common/commonHead.jsp" %>-->
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.infrared.region.edit")}</title>
<#include "../_common/common-css.ftl">
<#--<%@include file="/common/commonCss.jsp" %>-->
    <link rel="stylesheet" href="css/infraredImage/editMarkRegion.css">
    <link rel="stylesheet" href="js/draw4html/css/draw-rect.css">
    <link rel="stylesheet" href="../assets/artDialog/5.0.1-7012746/skins/default.css">

    <script type="text/javascript" src="../assets/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript" src="../assets/bootstrap/2.3.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../assets/artDialog/5.0.1-7012746/artDialog.min.js"></script>
    <script type="text/javascript" src="../assets/underscore/1.4.4/underscore-min.js"></script>
    <script type="text/javascript" src="../assets/json2/json2-20111019.js"></script>
    <script type="text/javascript" src="js/draw4html/js/detect-image-natural-size.js"></script>
    <script type="text/javascript" src="js/draw4html/js/draw-rect.js"></script>
    <script type="text/javascript" src="js/infraredImage/app-tooltip.js"></script>
    <script type="text/javascript" src="js/infraredImage/editMarkRegion.js"></script>
</head>
<body>
<#include "../_common/header.ftl">
<#include "../_common/common-js.ftl">

<div class="container-wrapper">
    <div id="container" class="container">
        <div class="row m-t-50 m-b-20">
            <div class="span12">
                <div id="editedImage" class="span9"
                     data-dv-place-id="${dvPlace.id}"
                     data-picture-id="${picture.id}"
                     data-image-src="${picturesBasePath}/${picture.path}/${picture.name}">
                </div>
                <div class="span3">
                    <button class="btn" onclick="javascript:window.history.go(-1);"><i
                            class="icon-arrow-left"></i>${locale.getStr("common.return")}
                    </button>
                    <button id="edit-button" class="btn btn-primary"
                            data-toggle="button">${locale.getStr("common.edit")}</button>
                    <button id="save-button" class="btn btn-primary" disabled="disabled">${locale.getStr("common.save")}</button>
                    <h5 class="m-t-20">${locale.getStr("proxima.infrared.region")}：</h5>
                    <ul id="markRegions" class="markRegions"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="hide">
    <div id="add-mark-region">
        <label>${locale.getStr("proxima.infrared.region.name")}</label>
        <input type="text" maxlength="20">

        <p class="help-block red"></p>
    </div>


    <ul id="init-markRegions">

    <#if markRegions?? && markRegions?size gt 0>
        <#list markRegions as markRegion>
            <li data-id="${markRegion.id}"
                data-name="${markRegion.name}"
                data-position-x="${markRegion.positionX}"
                data-position-y="${markRegion.positionY}"
                data-width="${markRegion.regionWidth}"
                data-height="${markRegion.regionHeight}">

            </li>
        </#list>
    </#if>
    </ul>
</div>

<#--<%@include file="/common/bodyFooter.jsp" %>-->
<#include "../_common/footer.ftl">
</body>
</html>
