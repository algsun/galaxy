<#include "/common/pages/common-tag.ftl">

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>接口说明</title>
    <link rel="stylesheet" href="${basePath()}/assets/bootstrap/2.3.1/css/bootstrap.min.css"/>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="span12">
            <h1>银河v1接口说明</h1>

            <p>这里是银河对内v1接口说明文档。接口遵守 rest 风格，无特殊说明，返回结果为<code>json</code>(application/json)。
                访问根路径为<code>${basePath()}/api/v1</code>，以下接口均基于根路径。
            </p>
            <hr/>
        </div>
    </div>

    <div class="row">
        <div class="span12">
            <div class="span3 affix">
                <div class="accordion" id="accordion">
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a href="#blueplanet" class="accordion-toggle collapsed" data-toggle="collapse"
                               data-parent="#accordion">blueplanet
                                - 环境监控</a>
                        </div>
                        <div id="blueplanet" class="accordion-body collapse">
                            <div class="accordion-inner">
                                <ul class="nav">
                                    <li><a href="#站点列表">站点列表</a></li>
                                    <li><a href="#区域列表line">区域列表(线型)</a></li>
                                    <li><a href="#区域列表tree">区域列表(树型)</a></li>
                                    <li><a href="#绑定的设备列表">绑定的设备列表</a></li>
                                    <li><a href="#未绑定的设备列表">未绑定的设备列表</a></li>
                                    <li><a href="#站点设备数据">站点设备数据</a></li>
                                    <li><a href="#区域设备数据">区域设备数据</a></li>
                                    <li><a href="#实时数据">设备实时数据</a></li>
                                    <li><a href="#历史数据">设备历史数据</a></li>
                                    <li><a href="#拓扑数据">拓扑数据</a></li>
                                    <li><a href="#站点概览">站点概览</a></li>
                                    <li><a href="#区域概览">区域概览</a></li>
                                    <li><a href="#设备实时数据">设备实时数据</a></li>
                                    <li><a href="#设备监测指标">设备监测指标</a></li>
                                    <li><a href="#设备状态">设备状态</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a href="#proxima" class="accordion-toggle collapsed" data-toggle="collapse"
                               data-parent="#accordion">proxima - 本体监测</a>
                        </div>
                        <div id="proxima" class="accordion-body collapse">
                            <div class="accordion-inner">
                                <ul class="nav">
                                    <li><a href="#获取有摄像机的区域(光学)">获取有摄像机的区域(光学)</a></li>
                                    <li><a href="#获取摄像机点位(光学)">获取摄像机点位(光学)</a></li>
                                    <li><a href="#获取摄像机图片">获取摄像机图片</a></li>
                                    <li><a href="#获取有摄像机的区域(红外)">获取有摄像机的区域(红外)</a></li>
                                    <li><a href="#获取摄像机点位(红外)">获取摄像机点位(红外)</a></li>
                                    <li><a href="#获取摄像机点位标记段">获取摄像机点位标记段</a></li>
                                    <li><a href="#获取标记段位置信息">获取标记段位置信息</a></li>
                                    <li><a href="#获取摄像机数据">获取摄像机数据</a></li>
                                    <li><a href="#添加标记区域">添加标记区域</a></li>
                                    <li><a href="#当前点数据">当前点数据</a></li>
                                    <li><a href="#删除标记区域">删除标记区域</a></li>
                                    <li><a href="#保存标记区域">保存标记区域</a></li>
                                    <li><a href="#查询标记区域">查询标记区域</a></li>
                                    <li><a href="#获取标记区域">获取标记区域</a></li>
                                    <li><a href="#获取标记区域数据">获取标记区域数据</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a href="#uma" class="accordion-toggle collapsed" data-toggle="collapse"
                               data-parent="#accordion">uma - 人员管理</a>
                        </div>
                        <div id="uma" class="accordion-body collapse">
                            <div class="accordion-inner">
                                <ul class="nav">
                                    <li><a href="#实时人员分布">实时人员分布</a></li>
                                    <li><a href="#区域当前人员显示">区域当前人员显示</a></li>
                                    <li><a href="#行为统计">行为统计</a></li>
                                    <li><a href="#人员信息">人员信息</a></li>
                                    <li><a href="#人员行为查询">人员行为查询</a></li>
                                    <li><a href="#行为规则详情">行为规则详情</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="offset3 span9">
            <@markdown>
                <#include "logicgroup.md">
                <#include "zoneline.md">
                <#include "zonetree.md">
                <#include "binddevice.md">
                <#include "unbinddevice.md">
                <#include "siterealtimedata.md">
                <#include "zonerealtimedata.md">
                <#include "realtimedata.md">
                <#include "historydata.md">
                <#include "topo.md">
                <#include "siteindex.md">
                <#include "zoneindex.md">
                <#include "devicerealtimedata.md">
                <#include "devicesensorinfo.md">
                <#include "device.md">
                <#include "opticsdvplaceszone.md">
                <#include "opticsdvplaces.md">
                <#include "pictures.md">
                <#include "infrareddvplaceszone.md">
                <#include "infrareddvplaces.md">
                <#include "opticsmarksenment.md">
                <#include "marksenmentposition.md">
                <#include "dvplace.md">
                <#include "infraredmarkregion.md">
                <#include "infraredmarkregiontempurature.md">
                <#include "deletemarkregion.md">
                <#include "savemarkregion.md">
                <#include "toeditmarkregion.md">
                <#include "markregions.md">
                <#include "markregiondata.md">
                <#include "realtimelocation.md">
                <#include "userlocationdetails.md">
                <#include "behaviorrule.md">
                <#include "userrules.md">
                <#include "useractiondetails.md">
            </@markdown>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript" src="${basePath()}/assets/jquery/1.8.3/jquery.min.js"></script>
<script type="application/javascript" src="${basePath()}/assets/bootstrap/2.3.1/js/bootstrap.min.js"></script>
<script>
    // 给 table 添加 bootstrap 样式
    $('table').addClass("table table-bordered");
</script>
</body>
</html>

<#-- pegdown 实现 -->
<#macro markdown >
    <#local md><#nested/></#local>
${action.markdown2html(md)}
</#macro>
