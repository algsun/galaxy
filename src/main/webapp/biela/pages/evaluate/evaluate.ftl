<#--
区域中心实时评估

@author li.jianfei
@date 2014-10-29
-->
<#assign title="实时评估-区域中心">
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
    <!-- CONTAINER -->
    <div style="height: 20px">

    </div>
    <div class="row-fluid">
        <div class="span8">
            <ul class="nav nav-tabs nav-justified" id="ul">
                <li class="active"><a id="comprehensive" href="#main1" data-toggle="tab">温湿度综合评估</a></li>
                <li><a id="comRate-t" href="#main1" data-toggle="tab">温度达标率</a></li>
                <li><a id="comRate-h" href="#main1" data-toggle="tab">湿度达标率</a></li>
                <li><a id="waveValue-t" href="#main1" data-toggle="tab">温度日浮动值</a></li>
                <li><a id="waveValue-h" href="#main1" data-toggle="tab">湿度日浮动值</a></li>
            </ul>
            <div class="tab-content" id="main1">
                <div class="row-fluid">
                    <div class="span12" id="mixtureChartDiv" >
                        <div id="mixtureChart" style="height: 500px">
                        </div>
                    </div>
                    <div id="noData" style="display: none" class="span4">
                        暂无数据
                    </div>
                </div>
            </div>
        </div>
        <div class="span4">
            <div id="histogram" style="height: 500px">
            </div>
        </div>
    </div>
    <!--/row-->
    <h4 style="text-align: center" id="explanation">"湿度,CO2,光照" 关联分析</h4>

    <div class="row-fluid">
        <div class="span4" id="scatter1" style="height: 330px"></div>
        <div class="span4" id="scatter2" style="height: 330px"></div>
        <div class="span4" id="scatter3" style="height: 330px"></div>
    </div>
    <input type="hidden" id="siteId" value="">
    <input type="hidden" id="sensorId" value="">
    <input type="hidden" id="units" value="">
</div>

</#macro>

<#macro script>
<!-- core -->
<script src="js/evaluate/esl/esl.js"></script>
<script src="js/evaluate/option1.js"></script>
<script src="js/evaluate/option2.js"></script>
<script src="js/evaluate/option3.js"></script>
<script src="js/evaluate/province-evaluate.js"></script>
</#macro>
