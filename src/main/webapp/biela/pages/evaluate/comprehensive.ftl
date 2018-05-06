<#--
区域中心综合评估

@author li.jianfei
@date 2014-10-29
-->
<#assign title="综合评估-区域中心">
<#-- 当前权限标识 -->
application/octet-stream
<#include "/common/pages/common-tag.ftl">
<#include "../_common/helper.ftl">

<#macro head>
<link rel="stylesheet" href="../assets/bootstrap/3.1.1/bootstrap.css">
<link rel="stylesheet" href="../assets/bootstrap/3.1.1/bootstrap-responsive.css">
<style type="text/css">
    * {
        font-family: "Microsoft YaHei" !important;
    }

    body {
        background-image: url("images/groovepaper.png");
        background-repeat: repeat;
    }

    .tab-content {
        padding: 20px;
        border: 1px solid #dddddd;
        border-top: 0px;
    }

</style>
</#macro>

<#macro content>
<#--页面以及标题-->
    <#assign currentTopPrivilege = "biela:evaluate">

<div class="container-fluid m-t-50">
    <div class="row">
        <div class="span12">
            <h3>
                <#--<legend>-->
                    <a class="go-back" href="evaluate" title="返回">
                        <i class="mw-icon-prev"></i>区域中心
                    </a>
                <#--</legend>-->
            </h3>
        </div>
    </div>
    <!-- CONTAINER -->
    <div class="row-fluid">
        <div id="overview" class="span12">
            <!-- Nav tabs -->
            <ul class="nav nav-tabs nav-justified">
                <li class="active"><a id="o-t" href="#main0" data-toggle="tab">温度<br>(单位:℃)</a></li>
                <li><a id="o-h" href="#main0" data-toggle="tab">湿度<br>(单位:%)</a></li>
                <li><a id="o-lux" href="#main0" data-toggle="tab">光照<br>(单位:lux)</a></li>
                <li><a id="o-uw" href="#main0" data-toggle="tab">紫外<br>(单位:uw/c㎡)</a></li>
                <li><a id="o-ppm" href="#main0" data-toggle="tab">二氧化碳<br>(单位：ppm)</a></li>
                <li><a id="o-voc" href="#main0" data-toggle="tab">VOC-高灵敏度<br>(单位:ppb)</a></li>
                <li ><a id="o-comprehensive" href="#main1" data-toggle="tab">综合评估<br>(单位:%)</a></li>
            </ul>
            <div class="tab-content" id="main0">
                <div class="row-fluid">
                    <div class="span8">
                        <div id="comRateMap" style="height:700px"></div>
                    </div>
                    <div class="span4" style="margin-top: 100px">
                        <div class="span12" id="siteName" style="text-align: center"></div>
                        <div class="span12" id="avgPeak" style="height: 400px"></div>
                    </div>
                    <div id="noData" style="display: none" class="span4">
                        暂无数据
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--/row-->

    <input type="hidden" id="siteId" value="">
    <input type="hidden" id="sensorId" value="">
    <input type="hidden" id="units" value="">
    <input type="hidden" id="provinceName" value="${name!}"/>
    <input type="hidden" id="comprehenseive"/>
</div>


</#macro>

<#macro script>
<!-- core -->
<script src="js/evaluate/esl/esl.js"></script>
<script src="js/evaluate/comrate-map.js"></script>
<script src="js/evaluate/option-avg-peak.js"></script>
<script src="js/evaluate/site-comprehensive.js"></script>
</#macro>
