<div style="margin-top: 10px;padding-left: 2em;">
    <div style="margin-top: 10px;">
        <div>根据元智微环境大数据分析算法分析，${locationName}监测点附近的<#if (ascendSensors?size>0)><#list ascendSensors as ascendSensor>${ascendSensor.cnName}</#list>近期有可能呈上升趋势，</#if><#if (descendSensors?size>0)><#list descendSensors as descendSensor>${descendSensor.cnName}</#list>近期有可能呈下降趋势，</#if>请注意保持微环境的洁净平稳。 </div>
    </div>
</div>
