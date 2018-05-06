<#--
3d模型管理
@author 王耕
@date 2015.06.10

-->

<#assign title>${locale.getStr("blueplanet.threeDimensional.title")}</#assign>
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
<input type="hidden" id="dimensionalPath" value="${threeDimensional.path}"/>
<input type="hidden" id="dimensionalId" value="${threeDimensional.id}"/>
<input type="hidden" id="stamp" value="${stamp!?datetime}"/>
<input type="hidden" id="submit" value="${submit?string}"/>
<input type="hidden" id="sensorId" value="${sensorId}"/>
<div class="row-fluid">
    <div class="span12">
        <h5 class="m-t-50">
            <a href="three-dimensional">${locale.getStr("common.return")}></a>
        </h5>
    </div>
</div>
<div class="row-fluid">
    <div class="span12">
        <form action="three-dimensional/dimensionalPreview" class="m-b-10 form-inline well well-small"
              style="height: 30px">
            <label>${locale.getStr("common.time")}</label>
            <input type="hidden" id="dimensionalId" name="dimensionalId" value="${threeDimensional.id}"/>
            <input type="hidden" name="submit" value="true"/>
            <input id="stamp" class="input-small" name="stamp" type="text"
                   onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})"
                   value="${(stamp?string("yyyy-MM-dd"))!}"/>
            <label>${locale.getStr("common.temperature")}</label>

            <input type="radio" name="sensorId" value="33" <#if sensorId==33>checked</#if>/>
            <label>${locale.getStr("common.humiditys")}</label>
            <input type="radio" name="sensorId" value="32" <#if sensorId==32>checked</#if>/>
            <button id="commit" class="btn">${locale.getStr("common.select")}</button>
            <label>${locale.getStr("blueplanet.threeDimensional.axisDirectionControl")}</label>
            <input id="range" type="range" min="-90" max="90"/>
            <input type="text" readonly id="colorValue" style="width: 80px"/>
            <#if sensorId == 33>
                °C
            <#elseif sensorId == 32>
                %
            </#if>
        </form>
    </div>
</div>
<div class="row-fluid">
    <div class="span12">
        <div id="view" class="span7">
        </div>
        <div id="tag" class="span5">
        </div>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="js/three-dimensional/three.min.js"></script>
<script type="text/javascript" src="js/three-dimensional/OBJLoader.js"></script>
<script type="text/javascript" src="js/three-dimensional/Detector.js"></script>
<script type="text/javascript" src="js/three-dimensional/stats.min.js"></script>
<script type="text/javascript" src="js/three-dimensional/TrackballControls.js"></script>
<script type="text/javascript" src="js/three-dimensional/dimensional-field.js"></script>
<script type="text/javascript" src="js/three-dimensional/dat.gui.min.js"></script>
<script type="text/javascript" src="js/three-dimensional/OrbitControls.js"></script>
<script type="text/javascript" src="js/three-dimensional/Projector.js"></script>
<script type="text/javascript" src="js/three-dimensional/CanvasRenderer.js"></script>


<script type="text/javascript" src="js/three-dimensional/fonts/gentilis_bold.typeface.js"></script>
<script type="text/javascript" src="js/three-dimensional/fonts/gentilis_regular.typeface.js"></script>
<script type="text/javascript" src="js/three-dimensional/fonts/optimer_bold.typeface.js"></script>
<script type="text/javascript" src="js/three-dimensional/fonts/optimer_regular.typeface.js"></script>
<script type="text/javascript" src="js/three-dimensional/fonts/helvetiker_bold.typeface.js"></script>
<script type="text/javascript" src="js/three-dimensional/fonts/helvetiker_regular.typeface.js"></script>
<script type="text/javascript" src="js/three-dimensional/fonts/droid/droid_sans_regular.typeface.js"></script>
<script type="text/javascript" src="js/three-dimensional/fonts/droid/droid_sans_bold.typeface.js"></script>
<script type="text/javascript" src="js/three-dimensional/fonts/droid/droid_serif_regular.typeface.js"></script>
<script type="text/javascript" src="js/three-dimensional/fonts/droid/droid_serif_bold.typeface.js"></script>
<script type="text/javascript">
    $(function () {
        var path = $("#dimensionalPath").val();
        var dimensionalId = $("#dimensionalId").val();
        var stamp = $("#stamp").val();
        var submit = $("#submit").val();
        var sensorId = $("#sensorId").val();
//        var path = "pages/threedimensional/greatwall-300.obj";
//        var path = 'http://localhost/galaxy-resources/blueplanet/file/threedimensional/skull.obj';
        DIMENSIONAL_FIELD.execute(path, dimensionalId, stamp, submit, sensorId);
    });
</script>
</#macro>

