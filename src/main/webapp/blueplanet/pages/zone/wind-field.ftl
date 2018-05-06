<#--
风场页面
@author xu.baoji
@time  2013.10.23
@check @gaohui 2013-11-06 6429
-->
<#assign title>${locale.getStr("blueplanet.wind.title")}</#assign>

<#include "/common/pages/common-tag.ftl"/>

<#-- head -->
<#macro head>
<style type="text/css">
    #paperjs {
        position: relative;
    }

    .scalarFieldFloat {
        position: absolute;
        top: 0;
        left: 0;
    }

    #scalarField {
        margin-top: 20px;
    }

    .float-left {
        float: left;
    }
</style>
</#macro>

<#assign tabIndex = 5>
<#macro content>

<div>
    <div class="form-inline well well-small">
        <label>${locale.getStr("common.time")}</label>
        <input class="input-medium Wdate" id="date" type="text" name="date"
               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:00', maxDate:'%y-%M-{%d}'})"
               value="${date?string("yyyy-MM-dd HH:mm:00")!}"/>
        <button class="btn" type="submit">${locale.getStr("common.select")}</button>
    </div>
    <div class="form-inline well well-small">
        <a id="up" title="${locale.getStr("blueplanet.zone.up")}" class="btn">
            <i class="icon-arrow-left"></i>
        </a>
        <select id="minute" class="input-small">
            <option value="10" selected="selected">10${locale.getStr("common.minute")}</option>
            <option value="20">20${locale.getStr("common.minute")}</option>
            <option value="30">30${locale.getStr("common.minute")}</option>
        </select>
        <a id="next" title="${locale.getStr("blueplanet.zone.down")}" class="btn disabled">
            <i class="icon-arrow-right"></i>
        </a>

        <a id="play-stop" title="${locale.getStr("blueplanet.zone.play")}" class="btn" isPlay="1">
            <p id="playOrStopP" class="inline">${locale.getStr("blueplanet.zone.play")}</p>
            <i id="playOrStopI" class="icon-play"></i>
        </a>
    </div>
    <span id="zone-id" class="hide">${zone.zoneId!}</span>
    <span id="zone-Image" class="hide">${zone.planImage!}</span>
    <span id="resourcesPath" class="hide">${resourcesPath!}</span>
</div>

<div id="scalarField">
    <div>
        <h4 id="noData" class="hide">${locale.getStr("common.noData")}</h4>
    </div>
    <div>
        <h4 id="noImage" class="hide">${locale.getStr("blueplanet.zone.noPlan")}</h4>
    </div>
    <div>
        <h4 id="imageNoDevice" class="hide">${locale.getStr("blueplanet.zone.equipmentsFirst")}</h4>
    </div>
    <div id="not_support_canvas" class="hide">
        <h4>${locale.getStr("blueplanet.zone.browserDown")}</h4>
    </div>
    <div id="hasData" class="hide">
        <div class="clear">
            <p class="gray">${locale.getStr("common.tips")}${locale.getStr("blueplanet.zone.tipwOne")}${locale.getStr("blueplanet.zone.tipwTwo")}</p>
        </div>
        <div id="paperjs" class="float-left">
            <img id="planImage" src="${resourcesPath!}/${zone.planImage!}?_=${.now}"/>
        </div>

    </div>
</div>
</#macro>
<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/paperjs/0.9.9/paper.js"></script>
<script type="text/javascript" src="../assets/moment/2.0.0/moment.min.js"></script>
<script type="text/javascript" src="../common/js/util.js"></script>
<script type="text/javascript" src="../common/js/tooltip.js"></script>
<script type="text/javascript" src="js/zone/wind-field.js"></script>
<script type="text/javascript">
    var blueplanet = App("blueplanet");
    blueplanet.zoneLocationPath.atPage("wind-field");
</script>
</#macro>




