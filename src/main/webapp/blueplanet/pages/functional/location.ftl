<!DOCTYPE html>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>站点概览 - 环境监控</title>

<#include "../_common/common-css.ftl">
<#include "../_common/ztree-css.ftl">

</head>
<body>

<#include "../_common/header.ftl">

<#-- 区域设备树 -->
<#--<div.left-aside-container">-->
<#include "../_common/zone-device-tree.ftl">
<#--</div>-->

<#--伸缩条-->
<div class="shrink-bar" title="点击收缩">
    <div class="shrink-bar-icon"></div>
</div>

<div class="content-container">
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <ul class="breadcrumb">
                    <li class="muted">当前位置</li>
                    <span id="area-device-path"></span>
                    <a class="btn m-l-20">确定</a>
                </ul>

                <div class="tabbable">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab0" data-toggle="tab">位置呈现</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab0">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "../_common/common-js.ftl">
<script type="text/javascript" src="../assets/ztree/3.4/js/jquery.ztree.core-3.4.min.js"></script>
<script type="text/javascript" src="../assets/ztree/3.4/js/jquery.ztree.excheck-3.4.min.js"></script>
<script type="text/javascript" src="../assets/underscore/1.4.2/underscore-min.js"></script>

<script type="text/javascript" src="js/device-tree.js"></script>

<#include "../_common/zone-device-path-template.ftl">
<script type="text/javascript">
    $(function () {
        var BluePlanet = App("blueplanet");
        BluePlanet.createNode(BluePlanet.allNodes);
    });
</script>

<#include "../_common/last-resources.ftl">
</body>
</html>