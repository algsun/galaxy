<#--
数据中心-图表

@author wang.geng
@date 2013-11-26
@check @liu.zhu wang.geng 2013-12-18 #7228
-->

<#assign title=locale.getStr("blueplanet.dataCenter.chartTitle")>
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" type="text/css" href="../assets/gridster/0.2.1/css/jquery.gridster.css">
<link rel="stylesheet" type="text/css" href="../assets/select2/3.3.1/select2.css">
<link rel="stylesheet" type="text/css" href="pages/dataCenter/charts/css/base.css">
<link rel="stylesheet" type="text/css" href="pages/dataCenter/charts/css/full-window-layout.css">
<link rel="stylesheet" type="text/css" href="pages/dataCenter/charts/css/layout.css">
<link rel="stylesheet" type="text/css" href="pages/dataCenter/charts/css/style.css">
<link rel="stylesheet" type="text/css" href="../assets/artDialog/5.0.1-7012746/skins/default.css">
<link rel="stylesheet" type="text/css" href="pages/dataCenter/charts/css/css.css">
<link rel="stylesheet" type="text/css" href="pages/dataCenter/charts/css/dc-add-layout.css">
<link rel="stylesheet" type="text/css" href="../assets/ds-digit/ds-digit-font.css">
</#macro>

<#macro content>
<div class="container-fluid" style="margin-top: 10px; margin-bottom: 10px;">
    <div class="row-fluid">
        <div class="span12">
            <input type="hidden" id="operMark" value="${operMark}"/>
            <#if operMark == 'preview' || operMark == 'edit'>
                <a href="dataCenter/charts/downLoadShort/${uuid}/${layoutDescription!}"
                   class="btn btn-success m-l-10 f-r" id="getShortcutLoginLink" type="button" style="width: 120px">${locale.getStr("blueplanet.dataCenter.downloadShortcuts")}</a>
            </#if>
            <#if operMark == 'preview' || operMark == 'shortcut'>
                <button class="btn btn-inverse f-r" id="fsbutton" type="button" style="width: 80px">${locale.getStr("blueplanet.dataCenter.fullScreen")}</button>
                <input type="hidden" id="isFullScreen" value="${operMark}">
            <#else>
                <button class="btn btn-inverse f-r" id="fsbutton" type="button" style="width: 80px">${locale.getStr("blueplanet.dataCenter.preview")}</button>
                <span style="font-weight: bolder;font-size: 16px;">${locale.getStr("common.insert")}</span>
                <button class="btn btn-info" id="program" type="button">${locale.getStr("blueplanet.dataCenter.slideshow")}</button>

                <button style="margin-left: 15px;" class="btn btn-success" id="save" type="button">${locale.getStr("common.save")}</button>
                <span>${locale.getStr("blueplanet.dataCenter.layoutName")}：</span>
                <input type="text" class="m-b-0" style="margin-bottom: 0" name="layoutDescription" id="layoutName"
                       value="${layoutDescription!}"/>
                <span style="color: red" id="layoutDescSpan"></span>
            </#if>
        </div>
    </div>
</div>

<div class="dataCenter">
    <#assign url = "${picturesBasePath}/${uuid}.jpg">
    <div class="gridster" id="container" style="<#if hasBgUrl>background:url(${url});<#else>background: #004756;</#if>">
        <div class="header">
            <span style="text-align: center;color: #ffffff;margin: 0;font-family: '黑体';font-size: 32px"
                  id=" layoutDescription">${layoutDescription!}</span>
        </div>
        <ul style="position: relative;" id="divUl">
            <#if itemList??>
                <#list itemList as item>
                    <#if item.itemType == 0>
                        <li id="chart-${item.item_id}" class="new gs-w" name="${item.item_id}"
                            itemType="${item.itemType}" data-id="${item.item_id}"
                            data-col="${item.data_col}" data-row="${item.data_row}"
                            data-sizex="${item.data_sizex}" data-sizey="${item.data_sizey}"
                            data-divId="${item.item_id}" data-chartName="${item.chartName!}"
                            data-locationId="${item.locationId!}" data-chart="${item.chart_type!}"
                            data-dateNum="${item.dateNum!}" data-sensorId=" ${item.sensorPhysicalid!}"
                            style="display: list-item;">
                            <input type="hidden" id="params" class="${item.item_id}"
                                   data-url="${item.url!}"
                                   data-params="${item.serializationParams!}">
                            <#if operMark!='preview'>
                                <i class="icon-remove" style="position:absolute;right: 1px;z-index: 99;"
                                   onclick="deleteDiv(${item.item_id});"></i>
                                <i class="icon-pencil" style="position: absolute;right: 17px;z-index: 99;"
                                   onclick="showModel(${item.item_id})"></i>
                            </#if>

                            <div style="opacity: 0.7;height: 90%;margin-top: 20px"
                                 id="${item.item_id}"></div>
                        </li>
                    <#elseif item.itemType ==1>
                        <li id="chart-${item.item_id}" class="new gs-w" name="${item.item_id}"
                            itemType="${item.itemType}" data-id="${item.item_id}"
                            data-col="${item.data_col}" data-row="${item.data_row}"
                            data-sizex="${item.data_sizex}" data-sizey="${item.data_sizey}"
                            data-divId="${item.item_id}" data-chartName="${item.chartName!}"
                            data-locationId="${item.locationId!}" data-chart="${item.chart_type!}"
                            data-dateNum="${item.dateNum!}" data-sensorId=" ${item.sensorPhysicalid!}"
                            style="display: list-item;background: rgb(255, 255, 255);background: rgba(255, 255, 255, 0.4);">
                            <input type="hidden" id="params" class="${item.item_id}" data-url="${item.url!}"
                                   data-params="${item.serializationParams!}">
                            <#if operMark!='preview' && operMark != 'shortcut'>
                                <i class="icon-remove" style="position: absolute;right: 1px;z-index: 99;"
                                   onclick="deleteSlideDiv('${item.related_layoutId}',${item.item_id});"></i>
                                <i class="icon-pencil" style="position: absolute;right: 17px;z-index: 99;"
                                   onclick="showSlideList('${item.related_layoutId}',${item.item_id})"></i>
                            </#if>
                            <table class="containerSlide" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td class="left">
                                        <table border="0" cellpadding="0" cellspacing="0" class="info">
                                            <tr>
                                                <td class="top">
                                                    <table border="0" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td class="t1">&nbsp;</td>
                                                            <td class="t2">&nbsp;</td>
                                                            <td class="t3">&nbsp;</td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="pic">
                                                    <table border="0" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td class="t1">&nbsp;</td>
                                                            <td class="t2">
                                                                <img id="slideImage${item.item_id}" class="slideImage"
                                                                     src=""/>
                                                            </td>
                                                            <td class="t3">&nbsp;</td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="bottom">
                                                    <table border="0" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td class="t1">&nbsp;</td>
                                                            <td class="t2">&nbsp;</td>
                                                            <td class="t3">&nbsp;</td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td>
                                        <div class="divContent">
                                            <h4 class="item" id="title${item.item_id}"></h4>
                                            <pre class="contentPre" id="detail${item.item_id}"></pre>
                                            <div class="divLocaltime" id="stamptime">
                                            </div>
                                        </div>
                                        <div class="sensor">
                                            <div style="text-align: center" id="sensor${item.item_id}"></div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </li>
                    </#if>
                </#list>
            </#if>
        </ul>
        <div class="footer">
            <div>
                <img src="pages/dataCenter/charts/images/logo.png"/>西安元智系统技术有限责任公司 &nbsp; &nbsp;
                地址：西安市高新区锦业路69号创业研发园E座4层<br/>www.microwise-system.com &nbsp; &nbsp; 电话：029-88246388 88346356 &nbsp;
                &nbsp; 传真：88330309
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="uuid" name="uuid" value="${uuid}">
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/exporting.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/data.js"></script>
<script type="text/javascript" src="../assets/gridster/0.2.1/js/jquery.gridster.min.js"></script>
<script type="text/javascript" src="../assets/select2/3.3.1/select2.min.js"></script>
<script type="text/javascript" src="../assets/artDialog/5.0.1-7012746/artDialog.min.js"></script>
<script type="text/javascript" src="pages/dataCenter/charts/js/fullScreen.js"></script>
<script type="text/javascript" src="../assets/underscore/1.4.4/underscore-min.js"></script>
<script type="text/javascript" src="pages/dataCenter/charts/js/dataCenter-charts.js"></script>

<script type="text/javascript">
    $(function(){
        var uuid = $("#uuid").val();
        var url ="http://"+ window.location.host+"/galaxy-resources/blueplanet/images/dataCenterConf/"+uuid+".jpg";
        $("#container").css("background-image","url("+url+")");
    });
</script>

<script type="text/template" id="template">
    <% _.each(slideData, function (item) { %>
    <div class="sensorDiv inline-block">
        <div class="sensorValueDiv"><span
                class="num sensorValue"><%= item.sensorValue%></span></div>
        <div class="sensorImageCnNameDiv">
            <div class="sensorImageDiv">
                <img src="pages/dataCenter/charts/images/sensor/sensor-<%=item.sensorImg%>.png">
            </div>
            <div class="sensorCnNameUnits">
                <div class="sensorCnName"> <%=item.sensorCnName%></div>

                <div class="sensorUnits">(<%=item.sensorUnits%>)</div>
            </div>
        </div>
    </div>
    <% }); %>
</script>
</#macro>
