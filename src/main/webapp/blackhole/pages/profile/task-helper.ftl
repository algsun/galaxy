<#--
我的任务帮助页面

@author gaohui
@date 2013-09-25
 -->

<#-- 任务进度 -->
<#macro task_progress task>
    <#list [0, 50, 90, 100] as progress>
        <#if progress == task.completeStatus>
        <img src="images/${progress}percent.png" alt="" title="完成${progress}%"/>
        ${progress}%
        </#if>
    </#list>
</#macro>


