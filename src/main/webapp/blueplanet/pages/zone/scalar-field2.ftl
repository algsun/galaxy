<#--
标量场页面
@author xu.yuexi
@time  2014.10.21
-->

<!DOCTYPE html>
<html>
<head>
    <title>${locale.getStr("blueplanet.scalar.title")}</title>
    <link rel="shortcut icon" type="image/x-icon" href="../favicon.ico"/>
    <link rel="stylesheet" href="../../assets/bootstrap/2.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../assets/common-css/0.1.2/common.min.css">
<#-- head -->
    <link type= "text/css"  rel="stylesheet" href="../css/base.css"/>
    <link type= "text/css"  rel="stylesheet" href="../css/full-window-layout.css"/>
    <link type= "text/css"  rel="stylesheet" href="../css/layout.css"/>
<style type="text/css">
    #paperjs {
        position: relative;
    }

    #scalarField {
        margin-top: 20px;
    }

    .scalarFieldFloat {
        position: absolute;
        top: 0;
        left: 0;
    }

    .float-left {
        float: left;
    }
</style>

</head>

<body>
<div>
    <input class="hide" id="date" name="date"   value="${date?string("yyyy-MM-dd HH:mm:00")!}"/>
    <input class="hide" id="sensorPhysicalId" name="sensorPhysicalId"  value="${sensorPhysicalId}"/>
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
    <div>
        <h4 id="not_support_canvas" class="hide">${locale.getStr("blueplanet.zone.browserDown")}</h4>
    </div>
    <div id="hasData" class="hide">
        <div class="clear">
            <p class="muted">${locale.getStr("blueplanet.zone.samplingTips")}。</p>
        </div>
        <div id="mapping" class="float-left" style="width: 40px;">
            <label id="maxValue"></label>
            <canvas id="valueHueMapping" width="20"></canvas>
            <label id="minValue"></label>
        </div>
        <div id="paperjs" class="float-left">
            <img id="planImage" src="${resourcesPath!}/${zone.planImage!}?_=${.now}"/>
        </div>
    </div>
</div>
<script type="text/javascript" src="../../assets/jquery/1.8.3/jquery.min.js"></script>
<script type="text/javascript" src="../../assets/bootstrap/2.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../../assets/paperjs/0.9.9/paper.js"></script>
<script type="text/javascript" src="../../assets/moment/2.0.0/moment.min.js"></script>
<script type="text/javascript" src="../../common/js/util.js"></script>
<script type="text/javascript" src="../../common/js/tooltip.js"></script>
<script type="text/javascript" src="../js/zone/scalar-field2.js"></script>

</body>
</html>



