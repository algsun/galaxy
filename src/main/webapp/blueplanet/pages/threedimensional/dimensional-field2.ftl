<#--
3d模型管理
@author 王耕
@date 2015.07.13

-->
<#assign title>${locale.getStr("blueplanet.threeDimensionalField.title")}</#assign>
<#include "/common/pages/common-tag.ftl"/>
<#-- 当前权限标识 -->
<#assign currentPrivilege = "blueplanet:monitor:threedimensional">
<#macro head>
    <#include "../_common/common-css.ftl">
<style type="text/css">
    body {
        font-family: Monospace;
        background-color: #f0f0f0;
        margin: 0px;
        overflow: hidden;
    }
</style>
</#macro>

<#macro content>
<input type="hidden" id="dimensionalPath" value="${path}"/>
<div class="row-fluid">
    <div class="span12">
        <h5 class="m-t-50">
            <a href="three-dimensional">${locale.getStr("common.return")}></a>
        </h5>
    </div>
</div>
<div class="row-fluid">
    <div  id="view" class="span12" style="text-align: center">
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="js/three-dimensional/three.min.js"></script>
<script type="text/javascript" src="js/three-dimensional/OBJLoader.js"></script>
<script type="text/javascript" src="js/three-dimensional/Detector.js"></script>
<script type="text/javascript" src="js/three-dimensional/stats.min.js"></script>
<script type="text/javascript" src="js/three-dimensional/TrackballControls.js"></script>
<script type="text/javascript" src="js/three-dimensional/dimensional-field2.js"></script>
<script type="text/javascript" src="js/three-dimensional/OrbitControls.js"></script>
<script type="text/javascript" src="js/three-dimensional/Projector.js"></script>
<script type="text/javascript" src="js/three-dimensional/CanvasRenderer.js"></script>

<script type="text/javascript">
    $(function () {
        var path = $("#dimensionalPath").val();
//        var path = "pages/threedimensional/greatwall-300.obj";
//        var path = 'http://localhost/galaxy-resources/blueplanet/file/threedimensional/skull.obj';
        DIMENSIONAL_FIELD.execute(path);
    });
</script>
</#macro>

