<#--
配置人员停留区域
@author 许月希
@date 2014-09-3

-->
<#assign title="人员停留情况 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:config">

<#include  "../_common/helper.ftl">
<#include "/common/pages/common-tag.ftl">

<#macro head>

</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span12">
        <div class="title page-header">
            <h3>配置人员停留区域</h3>
        </div>
    </div>
</div>

<div class="row-fluid m-t-20">
    <div class="span12">
        <h4>请选择要显示的区域</h4>
        <div class="offset3 m-t-10">
            <form class="form-horizontal" action="uma/configPhoenix/zones">
                <#list zoneList as zone>
                    <div class="control-group">
                        <input type="checkbox" value="${zone.id}" name="zoneIds"
                            <#if zone.isCount==1>
                               checked
                            </#if>/>${zone.name}
                    </div>
                </#list>
                <button type="submit" class="btn btn-info">确认</button>
            </form>
        </div>
    </div>
</div>

</#macro>

<#macro script>
</#macro>
