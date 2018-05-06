<#--
  -站点总览
  -@time 2014-10-21
  -->
<#assign title = "站点预览 - 环境监控">
<#include "/common/pages/common-tag.ftl"/>

<#macro head>
<link type="text/css" rel="stylesheet" href="../blueplanet/css/index.css">
</#macro>
<#assign tabIndex = 0>
<#macro content>
<div class="row-fluid">
    <#-- 滚动条暂时被隐藏，需要的时候去掉hide类，并放开JS注释 -->
    <div id="notice" class="span12 alert alert-info hide" style="height: 20px;">
        <ul style="font-size: 15px;">
            <li>1</li>
            <li>2</li>
            <li>3</li>
        </ul>
    </div>
</div>
<div class="well">
    <div class="row-fluid">
        <div class="span6">
            <div id="container1" style="min-width: 100%; height: 100%; margin: 0 auto"></div>
        </div>
        <div class="span6">
            <div id="container2"></div>
        </div>
    <#--<div class="span4">-->
    <#--<div id="container3"></div>-->
    <#--</div>-->
    </div>
    <div class="row-fluid">
        <div class="span6">
            <div id="container7"></div>
        </div>
        <div class="span6">
            <div id="container8"></div>
        </div>
    </div>
    <div class="row-fluid m-t-20">
        <div class="span12">
            <div id="container6"></div>
        </div>
    </div>
    <div class="row-fluid m-t-20">
        <div class="span6">
            <div id="container4"></div>
        </div>
        <div class="span6">
            <div id="container5"></div>
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/4.0.4/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/4.0.4/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/jquery-vTicker/1.15/jquery.vticker.min.js"></script>
<script type="text/javascript" src="../blueplanet/js/site/zone-humidity.js"></script>
<script type="text/javascript" src="../blueplanet/js/site/zone-stability.js"></script>
<script type="text/javascript" src="../blueplanet/js/site/temperature-avg.js"></script>
<script type="text/javascript" src="../blueplanet/js/site/realtime-temperature.js"></script>
<script type="text/javascript" src="../blueplanet/js/site/realtime-humidity.js"></script>
<script type="text/javascript" src="../blueplanet/js/site/device-status.js"></script>
<script type="text/javascript" src="../blueplanet/js/site/sensor-useInfo.js"></script>
<script type="text/javascript" src="../assets/moment/2.0.0/moment.min.js"></script>

<script type="text/javascript">
    $(function () {
        $(document).on('click', '.subsystemHead', function () {
            art.dialog({
                title: message.loading
            });
        });
    });
//    $("#notice").vTicker();
</script>
</#macro>
