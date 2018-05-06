<#--
位置点概览

@author liuzhu
@date 2014-6-26
-->
<#assign title=locale.getStr("blueplanet.location.locationOverview")>
<#include "/common/pages/common-tag.ftl">


<#macro head>
<link    rel="stylesheet" type="text/css" href="../assets/font-awesome/3.2.1/css/font-awesome.min.css">
<style type="text/css" xmlns="http://www.w3.org/1999/html">
    /* 风向表格 */
    table.wind-table td {
        padding: 5px;
    }

    /*.card-columns {*/
    /*-webkit-column-count: 5;*/
    /*-moz-column-count: 5;*/
    /*column-count: 5;*/
    /*-webkit-column-gap: 1.25rem;*/
    /*-moz-column-gap: 1.25rem;*/
    /*column-gap: 1.25rem*/
    /*}*/

    /*.card-columns .card {*/
    /*display: inline-block;*/
    /*width: 100%*/
    /*}*/
</style>

</#macro>


<#assign tabIndex = 0>
<#include "../device/device-helper-b4.ftl">
<#macro content>
<div>
        <a id="follower" class="btn btn-secondary btn-sm" style="float: right;margin-right: 30px;font-size: 15px;height:30px;color: #000000;margin-bottom: 10px" data-value="${locationId!}" data-follower="${follower!}">
        <#if !(follower??) || follower == 0>
            <i class="icon-plus" style="color: #FFD700;"></i>关注
        <#else>
            <i class="icon-ok" style="color: #6B8E23;"></i>已关注
        </#if>
        </a>

</div>
    <#if replaceSensor??>
    <input type="hidden" id="string_op_level" value="${replaceSensor.stringOPLevel!}"/>
    <input type="hidden" id="string_ip_level" value="${replaceSensor.stringIPLevel!}"/>
    <input type="hidden" id="string_sp_level" value="${replaceSensor.stringSPLevel!}"/>
    <input type="hidden" id="op_level" value="${replaceSensor.opLevel!}"/>
    <input type="hidden" id="ip_level" value="${replaceSensor.ipLevel!}"/>
    <input type="hidden" id="sp_level" value="${replaceSensor.spLevel!}"/>
    <input type="hidden" id="op_color" value="${replaceSensor.stringOPLevelColor!}"/>
    <input type="hidden" id="ip_color" value="${replaceSensor.stringIPLevelColor!}"/>
    <input type="hidden" id="sp_color" value="${replaceSensor.stringSPLevelCoLor!}"/>
    </#if>
<div class="row m-t-1">
    <div class="col-lg-9 col-md-12">

        <#if device??>
            <div class="row">
                <div class="col-md-12">
                    <div class="card card-header text-muted">
                        <div style="display:inline-block;vertical-align:middle;">
                            <i class="fa fa-clock-o" aria-hidden="true"></i>
                            <span class="text-primary">
                            ${device.stamp?string("yyyy-MM-dd HH:mm:ss")}
                            </span>
                            <span class="m-l-3">
                            ${locale.getStr("blueplanet.common.status")}<@deviceState device/>
                            </span>
                            <#if rainDeviceState??>
                                <#if rainDeviceState.rainState??>
                                    <span class="m-l-3">
                                        ${locale.getStr("blueplanet.location.rainfallSignal")}：
                                            <#switch rainDeviceState.rainState>
                                                <#case 0>
                                                ${locale.getStr("blueplanet.location.noRains")}
                                                    <#break>
                                                <#case 1>
                                                ${locale.getStr("blueplanet.location.isRain")}
                                                    <#break>
                                            </#switch>
                                    </span>
                                </#if>
                                <#if rainDeviceState.rainGaugeState??>
                                    <span class="m-l-3">
                                       ${locale.getStr("blueplanet.location.samplingStatus")}：
                                           <#switch rainDeviceState.rainGaugeState>
                                               <#case 0>
                                               ${locale.getStr("blueplanet.location.coverClosed")}
                                                   <#break>
                                               <#case 1>
                                               ${locale.getStr("blueplanet.location.coverOpened")}
                                                   <#break>
                                               <#case 2>
                                               ${locale.getStr("blueplanet.location.error")}
                                                   <#break>
                                               <#case 3>
                                               ${locale.getStr("blueplanet.location.coverPlug")}
                                                   <#break>
                                           </#switch>
                                    </span>
                                </#if>
                            </#if>
                            <span class="m-l-3">
                            ${locale.getStr("blueplanet.location.actionPeriod")}<@deviceWorkInterval device.interval/>
                            </span>
                            <span class="m-l-3">${locale.getStr("blueplanet.location.bindEquipment")}：
                                <a href="device/${device.nodeId!}/detail"><@strongNodeId device.nodeId, true/></a>
                            </span>
                            <#if security.isPermitted("blueplanet:manage:location:edit")>
                                <span class=""><a href="device/${device.nodeId}/detail"
                                        class="btn btn-secondary btn-sm">${locale.getStr("blueplanet.location.edit")}</a></span>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </#if>
        <#if replaceSensor??>
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-block">
                            <div class="row">
                                <div class="col-md-4" id="container-speed1" style="height: 180px;"></div>
                                <div class="col-md-4" id="container-speed2" style="height: 180px;"></div>
                                <div class="col-md-4" id="container-speed3" style="height: 180px;"></div>
                            </div>
                            <div class="row">
                                <div class="col-md-12 text-muted" align="center">
                                ${locale.getStr("blueplanet.location.note")}
                                    <a href="location/${locationId}/qcm_level">
                                    ${locale.getStr("blueplanet.location.moreDetails")}
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </#if>
        <#if (!recentDataList?? || recentDataList?size == 0)>
            <h4>${locale.getStr("common.noData")}</h4>
        <#else>
    <#--<div class="card-columns">-->
        <#list location.sensorInfoList as sensorinfo>
        <#-- 是不是偶数个, 从零算起 -->
            <#local even = (sensorinfo_index % 3 == 0)>
            <#if sensorinfo_index % 3 == 0>
            <div class="row">
            <div class="col-md-12">
            <div class="row">
            </#if>
            <div class="col-md-6 col-xs-12 col-lg-4">
                <#list recentDataList as recentData>
                <#-- 如果有数据 -->
                    <#if recentData.sensorInfoMap??>
                        <#if recentData.sensorInfoMap.get(sensorinfo.sensorPhysicalid)??>
                            <#local locationData = recentData.sensorInfoMap.get(sensorinfo.sensorPhysicalid)>
                            <#if locationData.state == 1 || locationData.state == 2 || locationData.state == 3>
                                <#if !recentData_has_next>
                                    <#assign sensorValue=locationData.sensorPhysicalValue>
                                </#if>
                            </#if>
                        </#if>
                    </#if>
                </#list>
                <div class="card">
                    <div class="card-block" style="padding: 10px;">
                        <div class="row">
                            <div class="col-xs-2 col-sm-3 col-md-4 col-lg-4">
                                <img src="pages/dataCenter/charts/images/sensor/sensor-${sensorinfo.sensorPhysicalid?c}.png"
                                     alt="..." class="img-rounded" style="width: 72px;height: 72px;">
                            </div>
                            <div class="col-xs-10 col-sm-9 col-md-8 col-lg-8">
                                <h4 class="card-title m-t-10">
                                ${sensorValue!}<span class="text-muted"
                                                    style="font-size: 20px">${sensorinfo.units}</span>
                                </h4>
                                <span class="card-subtitle text-muted">${sensorinfo.cnName}</span>
                            </div>
                        </div>
                    </div>
                    <div class="card-block" style="padding: 10px;">

                    <#--<span>${sensorinfo.cnName}</span><span class="text-muted">(${sensorinfo.units})</span>-->

                        <#if sensorinfo.showType == 0>
                            <div class="trend-chart" style="width: 100%; height:100px;"></div>
                        <#-- 图表数据 -->
                            <script type="text/x-chart-data">
                    {
                        data: [
                        <#list recentDataList as recentData>
                <#-- 如果有数据 -->
                    <#if recentData.sensorInfoMap??>
                        <#if recentData.sensorInfoMap.get(sensorinfo.sensorPhysicalid)??>
                            <#local locationData = recentData.sensorInfoMap.get(sensorinfo.sensorPhysicalid)>
                            <#if locationData.state == 1 || locationData.state == 2 || locationData.state == 3>
                                                [${recentData.stamp?long?c} , ${locationData.sensorPhysicalValue} ]
                                                <#if recentData_has_next>,</#if>
                            </#if>
                        </#if>
                    </#if>
                </#list>
                        ],
                        precision: ${sensorinfo.sensorPrecision},
                        cnName: "${sensorinfo.cnName}",
                        unit: "${sensorinfo.units}"
                    }
                            </script>
                        <#-- 风向类 -->
                        <#elseif sensorinfo.showType == 1>
                            <div style="overflow:auto; height: 100px;">
                                <table class="t-a-c wind-table" border="1"
                                       style="border-color: #ddd; max-height: 100px; ">
                                    <tbody>
                                    <tr>
                                        <#list recentDataList as recentDataList>
                                            <#if recentDataList.sensorInfoMap.get(sensorinfo.sensorPhysicalid)??>
                                                <#local locationData = recentDataList.sensorInfoMap.get(sensorinfo.sensorPhysicalid)>
                                                <#if locationData.state == 1 || locationData.state == 2 || locationData.state == 3>
                                                    <td>${locationData.sensorPhysicalValue}</td>
                                                </#if>
                                            </#if>
                                        </#list>
                                    </tr>
                                    <tr>
                                        <#list recentDataList as recentDataList>
                                            <#if recentDataList.sensorInfoMap.get(sensorinfo.sensorPhysicalid)??>
                                                <#local locationData = recentDataList.sensorInfoMap.get(sensorinfo.sensorPhysicalid)>
                                                <#if locationData.state == 1 || locationData.state == 2 || locationData.state == 3>
                                                    <td title="${recentDataList.stamp?string("yyyy-MM-dd HH:mm:ss")}">${recentDataList.stamp?string("HH:mm")}</td>
                                                </#if>
                                            </#if>
                                        </#list>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
            <#if sensorinfo_index % 3 == 2 || !sensorinfo_has_next>
            </div></div></div>
            </#if>
        </#list>
        </#if>
    <#--</div>-->
    </div>
    <div class="col-lg-3 hidden-md-down">
        <#assign pictureCount=0/>
        <#if location.photo??>
            <#assign pictureCount=pictureCount+1/>
        </#if>
        <#list location.relics as relic>
            <#if (relic.photos?size>0)>
                <#assign pictureCount=pictureCount+1/>
            </#if>
        </#list>
        <#if (pictureCount>0)>
        <div id="carousel-relic-photos" class="carousel slide" data-ride="carousel" data-interval="5000"
             style="height: 320px;">
            <ol class="carousel-indicators">
                <#list 0..(pictureCount-1) as index>
                    <li data-target="#carousel-relic-photos" data-slide-to="${index}"
                        <#if index==0>class="active"</#if>></li>
                </#list>
            </ol>
            <div class="carousel-inner" role="listbox">
                <#if location.photo??>
                    <div class="carousel-item active" align="center">
                        <img src="${locationPhotoPath}/${location.photo}" style="height: 320px; max-height: 320px;">
                        <div class="carousel-caption ">
                            <h3>${locale.getStr("blueplanet.location.moreDetails")}</h3>
                        </div>
                    </div>
                </#if>
                <#list location.relics as relic>
                    <#if (relic.photos?size>0)>
                        <#list relic.photos as photo>
                            <div class="carousel-item <#if (!location.photo?? && relic_index==0)>active</#if>"
                                 align="center">
                                <img src="${relicPhotoBasePath}/${location.siteId!}/${relic.id?c}/${photo.path!}"
                                     style="max-width:320px;max-height:300px"/>
                                <div class="carousel-caption ">
                                    <h3>${relic.name}</h3>
                                    <p>总登记号:${relic.totalCode!}</p>
                                </div>
                            </div>
                            <#break>
                        </#list>
                    </#if>
                </#list>
            </div>
        <#--<a class="left carousel-control" href="#carousel-relic-photos" role="button" data-slide="prev">-->
        <#--<span class="icon-prev" aria-hidden="true"></span>-->
        <#--<span class="sr-only">Previous</span>-->
        <#--</a>-->
        <#--<a class="right carousel-control" href="#carousel-relic-photos" role="button" data-slide="next">-->
        <#--<span class="icon-next" aria-hidden="true"></span>-->
        <#--<span class="sr-only">Next</span>-->
        <#--</a>-->
        </div>
        </#if>
        <#if locationHistoryVOList?size!=0>
            <div class="row m-t-5">
                <div class="col-md-12">

                    <span style="font-weight: bolder">${location.locationName!}</span> ${locale.getStr("blueplanet.location.bindingHistoricalRecords")}
                    <#if device??>
                        <a class="pull-right"
                           href="toEditLocation?locationId=${locationId}&nodeId=${device.nodeId}&page=1">
                            <i class="icon-arrow-right"></i> </a>
                    </#if>

                    <table class="table table-sm table-bordered table-hover">
                        <thead class="thead-default">
                        <tr>
                            <th>${locale.getStr("blueplanet.location.IDOfEquipment")}</th>
                            <th>${locale.getStr("common.startDate")}</th>
                            <th>${locale.getStr("common.endDate")}</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list locationHistoryVOList as lh>
                            <tr scope="row">
                                <td><@strongNodeId lh.nodeId, true/></td>
                                <td>${lh.startTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                <td><#if lh.endTime??>${lh.endTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </#if>
    </div>
</div>

</#macro>



<#macro script>
<!--[if lte IE 8]>
<script language="javascript" type="text/javascript" src="../assets/flot/d7c58b59f3/excanvas.min.js"></script>
<![endif]-->
<script type="text/javascript" src="../assets/flot/d7c58b59f3/jquery.flot.js"></script>
<script type="text/javascript" src="../assets/flot/d7c58b59f3/jquery.flot.time.js"></script>
<script type="text/javascript" src="../assets/timezone-js/0.4.4/src/date.js"></script>
<script type="text/javascript" src="../assets/flot.tooltip/0.6/jquery.flot.tooltip.min.js"></script>
<script type="text/javascript" src="../assets/highcharts/4.2.5/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/4.2.5/highcharts-more.js"></script>
<script type="text/javascript" src="../assets/highcharts/4.2.5/modules/solid-gauge.js"></script>
<script type="text/javascript" src=""></script>

    <@scriptTag "js/location/index.js"/>
<script type="text/javascript">
    var BluePlanet = App("blueplanet");
    BluePlanet.zoneLocationPath.redirectWhenLocationChange(function (location) {
        return 'location/' + location.id;
    });

    $(function () {
        $("#follower").click(function(){
            var $this = $(this);
            var $follower =  $(this).data("follower");
            var $locationId = $(this).data("value");
            $.get("location/editFollower?locationId=" + $locationId + "&follower=" + $follower,function(data){
                if(data){
                    $this.empty();
                    if($follower == 0 || $follower == null){
                        $this.data("follower",1);
                        $this.append("<i class='icon-ok' style='color: #6B8E23;'></i>已关注");
                    }else{
                        $this.data("follower",0);
                        $this.append("<i class='icon-plus' style='color: #FFD700;'></i>关注");
                    }
                }
            });
        });
        var op_level = $("#op_level").val();
        var ip_level = $("#ip_level").val();
        var sp_level = $("#sp_level").val();

        var op_color = $("#op_color").val();
        var ip_color = $("#ip_color").val();
        var sp_color = $("#sp_color").val();

        var string_op_level = $("#string_op_level").val();
        var string_ip_level = $("#string_ip_level").val();
        var string_sp_level = $("#string_sp_level").val();
        if (op_color != "") {

            Highcharts.createElement('link', {
                href: '//fonts.googleapis.com/css?family=Signika:400,700',
                rel: 'stylesheet',
                type: 'text/css'
            }, null, document.getElementsByTagName('head')[0]);

// Add the background image to the container
            Highcharts.wrap(Highcharts.Chart.prototype, 'getContainer', function (proceed) {
                proceed.call(this);
                this.container.style.background = 'url(http://www.highcharts.com/samples/graphics/sand.png)';
            });


            Highcharts.theme = {
                colors: ["#f45b5b", "#8085e9", "#8d4654", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee",
                    "#55BF3B", "#DF5353", "#7798BF", "#aaeeee"],
                chart: {
//        backgroundColor: '#DCDCDC',
                    style: {
                        fontFamily: "Signika, serif"
                    }
                },
                title: {
                    style: {
                        color: 'black',
                        fontSize: '16px',
                        fontWeight: 'bold'
                    }
                },
                subtitle: {
                    style: {
                        color: 'black'
                    }
                },
                tooltip: {
                    borderWidth: 0
                },
                legend: {
                    itemStyle: {
                        fontWeight: 'bold',
                        fontSize: '13px'
                    }
                },
                xAxis: {
                    labels: {
                        style: {
                            color: '#6e6e70'
                        }
                    }
                },
                yAxis: {
                    labels: {
                        style: {
                            color: 'none'
                        }
                    }
                },
                plotOptions: {
                    series: {
                        shadow: true
                    },
                    candlestick: {
                        lineColor: '#404048'
                    },
                    map: {
                        shadow: false
                    }
                },

                // Highstock specific
                navigator: {
                    xAxis: {
                        gridLineColor: '#D0D0D8'
                    }
                },
                rangeSelector: {
                    buttonTheme: {
                        fill: 'white',
                        stroke: '#C0C0C8',
                        'stroke-width': 1,
                        states: {
                            select: {
                                fill: '#D0D0D8'
                            }
                        }
                    }
                },
                scrollbar: {
                    trackBorderColor: '#C0C0C8'
                },

                // General
                background2: '#E0E0E8'

            };
// Apply the theme
            Highcharts.setOptions(Highcharts.theme);

            var gaugeOptions = function (color) {
                return {
                    chart: {
                        type: 'solidgauge',
                    },

                    title: null,

                    pane: {
                        center: ['50%', '95%'],
                        size: '140%',
                        startAngle: -90,
                        endAngle: 90,
                        background: {
                            backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
                            innerRadius: '60%',
                            outerRadius: '100%',
                            shape: 'arc'
                        }
                    },

                    tooltip: {
                        enabled: false
                    },

                    // the value axis
                    yAxis: {
                        stops: [
                            [0.9, color] // red
                        ],
                        lineWidth: 0,
                        minorTickInterval: null,
                        tickPixelInterval: 400,
                        tickWidth: 0,
                        title: {
                            y: -70
                        },
                        labels: {
                            y: 16
                        }
                    },

                    plotOptions: {
                        solidgauge: {
                            dataLabels: {
                                y: 5,
                                borderWidth: 0,
                                useHTML: true
                            }
                        }
                    }
                }
            };
            var opLevel = op_level.substring(1, 2);
            var ipLevel = ip_level.substring(1, 2);
            var spLevel = sp_level.substring(1, 2);

            // The speed gauge
            $('#container-speed1').highcharts(Highcharts.merge(gaugeOptions(op_color), {
                yAxis: {
                    min: 0,
                    max: 5,
                    title: {
                        text: message.organicPollutant
                    }
                },

                credits: {
                    enabled: false
                },

                series: [
                    {
                        data: [parseInt(opLevel)],
                        dataLabels: {
                            format: '<span style="font-size:12px;">' + string_op_level + '</span>'
                        }
                    }
                ]
            }));

            $('#container-speed2').highcharts(Highcharts.merge(gaugeOptions(ip_color), {
                yAxis: {
                    min: 0,
                    max: 5,
                    title: {
                        text: message.inorganicPollutants
                    }
                },

                credits: {
                    enabled: false
                },

                series: [
                    {
                        data: [parseInt(ipLevel)],
                        dataLabels: {
                            format: '<span style="font-size:12px;">' + string_ip_level + '</span>'
                        }
                    }
                ]
            }));

            $('#container-speed3').highcharts(Highcharts.merge(gaugeOptions(sp_color), {
                yAxis: {
                    min: 0,
                    max: 5,
                    title: {
                        text: message.sulfurPollutant
                    }
                },

                credits: {
                    enabled: false
                },

                series: [
                    {
                        data: [parseInt(spLevel)],
                        dataLabels: {
                            format: '<span style="font-size:12px;">' + string_sp_level + '</span>'
                        }
                    }
                ]
            }));
        }
    });
</script>
</#macro>