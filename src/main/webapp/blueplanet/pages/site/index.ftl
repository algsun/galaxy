<#--
  -站点概览
  -@author liuzhu
  -@time  2013-10-25
  -@check xie.deng2013-11-14 #6604
  TODO 2.过滤掉中继，网关，控制模块？？
  -->
<#assign title = locale.getStr("blueplanet.site.summarize.title")>
<#include "/common/pages/common-tag.ftl"/>

<#macro head>
<link type="text/css" rel="stylesheet" href="../blueplanet/css/index.css">
</#macro>
<#assign tabIndex = 1>
<#macro content>
<div class="row-fluid">
    <#if findLocationDataFiveDay?? && findLocationDataFiveDay?size!=0>
        <div class="span6">
        <#--天气定制插件 http://www.tianqi.com/plugin/-->
            <div>
                <iframe width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true"
                        src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=3"></iframe>
            </div>
            <p>
                ${locale.getStr("blueplanet.site.maxValue")} <span class="label"
                          style="background-color: #F08080;width:20px;height:10px;display:inline-block;"></span>
                ${locale.getStr("blueplanet.site.minValue")}<span class="label"
                          style="background-color: #458B74;width:20px;height:10px;display:inline-block;"></span>
                    均值<span class="label"
                            style="background-color: #2eaeff;width:20px;height:10px;display:inline-block;"></span>
            </p>
            <#list findLocationDataFiveDay as locationData>

                <#if locationData_index%2==0>
                <div class="row-fluid m-b-20">
                </#if>
                <div class="span6">
                    <p style="font-size: 18px;">${locationData.cnName!}：<span
                            style="font-weight: bolder">${locationData.sensorPhysicalValue!}${locationData.units!}</span>
                    </p>

                    <div class="trend-chart" style="height: 100px; border: solid 1px #ccc"></div>
                    <div class="hide">
                        {
                        <#if locationData.sensorPhysicalid==33>
                            data:[
                            <#list locationData.sensorPhysicalValueMap.keySet() as locationDataKey>
                                [ ${locationDataKey?long?c}
                                ,${locationData.sensorPhysicalValueMap.get(locationDataKey).bigValue}
                                ,${locationData.sensorPhysicalValueMap.get(locationDataKey).smallValue}]
                                <#if locationDataKey_has_next>,</#if>
                            </#list>
                            ],
                        <#else>
                            maxData:[
                            <#list locationData.sensorPhysicalValueMap.keySet() as locationDataBigKey>
                                [${locationDataBigKey?long?c}
                                ,${locationData.sensorPhysicalValueMap.get(locationDataBigKey).bigValue}]
                                <#if locationDataBigKey_has_next >,</#if>
                            </#list>
                            ],
                            minData:[
                            <#list locationData.sensorPhysicalValueMap.keySet() as locationDataSmallKey>
                                [${locationDataSmallKey?long?c}
                                ,${locationData.sensorPhysicalValueMap.get(locationDataSmallKey).smallValue}]
                                <#if locationDataSmallKey_has_next >,</#if>
                            </#list>
                            ],
                            avgData:[
                            <#list locationData.sensorPhysicalValueMap.keySet() as locationDataAvgKey>
                                [${locationDataAvgKey?long?c}
                                ,${locationData.sensorPhysicalValueMap.get(locationDataAvgKey).avgValue}]
                                <#if locationDataAvgKey_has_next >,</#if>
                            </#list>
                            ],
                        </#if>
                        cnName: "${locationData.cnName!}",
                        unit: "${locationData.units!}",
                        sensorPhysicalId:${locationData.sensorPhysicalid?c}
                        }
                    </div>
                </div>
                <#if locationData_index%2==1 || !locationData_has_next>
                </div>
                </#if>

            </#list>
        </div>
        <div class="span6">
            <ul id="li-box">
                <#if findLocationDataRealTimeAvg??>
                    <#list findLocationDataRealTimeAvg.keySet() as key>
                        <#if findLocationDataRealTimeAvg.get(key)?size!=0>
                            <li>
                                <h4 class="m-l-5"><i class="mw-icon-zoon"></i> ${key!}</h4>
                                <#list findLocationDataRealTimeAvg.get(key) as locationData>
                                    <p class="m-l-5">
                                        <i class="mw-icon-sensor sensor-${locationData.sensorPhysicalid?c}"></i>
                                    ${locationData.cnName!}
                                        ：${locationData.sensorPhysicalValue}${locationData.units!}
                                    </p>
                                </#list>
                            </li>
                        </#if>
                    </#list>
                </#if>
            </ul>
        </div>
    <#else>
        <h4>${locale.getStr("common.noData")}</h4>
    </#if>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/modules/exporting.js"></script>
    <@scriptTag "js/index.js"/>
<script type="text/javascript">
    $(function () {
        $(document).on('click', '.subsystemHead', function () {
            art.dialog({
                title: message.loading
            });
        });
    });
</script>
</#macro>