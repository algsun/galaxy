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
            <h1>银河接口说明</h1>

            <p>这里是银河对外的接口说明文档。接口遵守 rest 风格，无特殊说明，返回结果为<code>json</code>(application/json)。
                访问根路径为<code>${basePath()}/api</code>，以下接口均基于根路径。
            </p>
            <hr/>
        </div>
    </div>

    <div class="row">
        <div class="span2">
            <a href="#blackhole">blackhole - 系统管理</a>
            <ul>
                <li><a href="#登录">登录</a></li>
            </ul>
            <a href="#halley">halley - 哈雷</a>
            <ul>
                <li><a href="#获取外展列表">获取外展列表</a></li>
                <li><a href="#更新外展状态">更新外展状态</a></li>
                <li><a href="#查询外展车辆及相关设备">查询外展车辆及相关设备</a></li>
            </ul>
            <a href="#blueplanet">blueplanet - 环境监测</a>
            <ul>
                <li><a href="#根据设备编号查询实时数据">根据设备编号查询实时数据</a></li>
                <li><a href="#根据区域编号查询实时数据">根据区域编号查询实时数据</a></li>
                <li><a href="#根据站点编号查询实时数据">根据站点编号查询实时数据</a></li>
            </ul>
            <a href="#orion">orion - 本体监测</a>
            <ul>
                <li><a href="#获取出入库列表">获取出入库列表</a></li>
                <li><a href="#出入库单核对">出入库单核对</a></li>
                <li><a href="#签收">签收</a></li>
                <li><a href="#审批">审批</a></li>
                <li><a href="#库房确认">库房确认</a></li>
                <li><a href="#回库">回库</a></li>
                <li><a href="#取消申请">取消申请</a></li>
            </ul>
        </div>
        <div class="span10">
        <@markdown>
            <#include "blackhole.md">
            <#include "halley.md">
            <#include "blueplanet.md">
            <#include "orion.md">
        </@markdown>
        </div>
    </div>
</div>

<script type="application/javascript" src="${basePath()}/assets/jquery/1.8.3/jquery.min.js"></script>
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
