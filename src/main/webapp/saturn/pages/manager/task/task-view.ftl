<#assign title="查看任务">
<#-- 当前权限标识 -->

<#macro head>
</#macro>


<#macro content>
<div class="container">
    <div class="row">
        <div class="form-horizontal"   >
            <fieldset>
                <legend>
                    查看任务
                </legend>
                <div class="control-group">
                    <label class="control-label">任务名称</label>

                    <div class="controls">
                        <label class="control-label">${task.title}</label>
                    </div>
                </div>
                <#if task.responsible!="">
                <div class="control-group">
                    <label class="control-label">负责人</label>

                    <div class="controls">
                        <label class="control-label"> ${task.responsible} </label>
                    </div>
                </div>
                </#if>
                <#if task.participate!="">
                    <div class="control-group">
                        <label class="control-label">参与人</label>

                        <div class="controls">
                            <label class="control-label">${task.participate}</label>
                        </div>
                    </div>
                </#if>
                <#if task.predictStart??>
                <div class="control-group">
                    <label class="control-label">预计开始时间</label>

                    <div class="controls">
                            <label class="control-label">${task.predictStart?string('yyyy-MM-dd')}</label>
                    </div>
                </div>
                </#if>
                <#if task.predictEnd??>
                <div class="control-group">
                    <label class="control-label">预计结束时间</label>

                    <div class="controls">
                            <label class="control-label">${task.predictEnd?string('yyyy-MM-dd')}</label>
                    </div>
                </div>
                </#if>
                <#if task.taskDescription!="" >
                    <div class="control-group">
                        <label class="control-label">任务描述</label>

                        <div class="controls">
                            <label class="control-label">${task.taskDescription}</label>
                        </div>
                    </div>
                </#if>
                <#if task.taskTarget!="" >
                    <div class="control-group">
                        <label class="control-label">任务目标</label>

                        <div class="controls">
                            <label class="control-label">${task.taskTarget}</label>
                        </div>
                    </div>
                </#if>
                <#if task.demand!="">
                    <div class="control-group">
                        <label class="control-label">需求保障</label>

                        <div class="controls">
                            <label class="control-label">${task.demand}</label>
                        </div>
                    </div>
                </#if>
                <#if task.remark!="">
                    <div class="control-group">
                        <label class="control-label">备注</label>

                        <div class="controls">
                            <label class="control-label">${task.remark}</label>
                        </div>
                    </div>
                </#if>
            <#if task.createUser !=0>
                <div class="control-group">
                    <label class="control-label">创建人</label>

                    <div class="controls">
                            <#list users as user>
                                <#if task.createUser == user.id><label
                                        class="control-label">${user.userName}</label></#if>
                            </#list>
                    </div>
                </div>
            </#if>
                <#if task.createDate??>
                <div class="control-group">
                    <label class="control-label">创建时间</label>

                    <div class="controls">
                            <label class="control-label">${task.createDate?string('yyyy-MM-dd')}</label>
                    </div>
                </div>
                </#if>
                <div class="control-group">
                    <label class="control-label">审核人</label>

                    <div class="controls">
                        <#if task.checkUser !=1>
                            <#list users as user>
                                <#if task.checkUser == user.id>
                                    <label class="control-label">${user.userName}</label>
                                </#if>
                            </#list>
                        <#else>
                            <#if task.checkUser==1>
                                <label class="control-label">超级管理员</label>
                            <#else>
                                <label class="control-label">无</label>
                            </#if>
                        </#if>
                    </div>
                </div>
                <#if task.checkDate??>
                <div class="control-group">
                    <label class="control-label">审核时间</label>

                    <div class="controls">

                            <label class="control-label">${task.checkDate?string('yyyy-MM-dd')}</label>

                    </div>
                </div>
                </#if>
                <div class="control-group">
                    <label class="control-label">作废人</label>

                    <div class="controls">
                        <#if task.invalidUser !=1>
                            <#list users as user>
                                <#if task.invalidUser == user.id>
                                    <label class="control-label">${user.userName}</label>
                                </#if>
                            </#list>
                        <#else>
                                <label class="control-label">超级管理员</label>
                        </#if>
                    </div>
                </div>
                <#if task.invalidDate??>
                <div class="control-group">
                    <label class="control-label">作废时间</label>

                    <div class="controls">
                            <label class="control-label">${task.invalidDate?string('yyyy-MM-dd')}</label>
                    </div>
                </div>
                </#if>
                <#if task.realStart??>
                <div class="control-group">
                    <label class="control-label">实际开始时间</label>

                    <div class="controls">

                            <label class="control-label">${task.realStart?string('yyyy-MM-dd')}</label>
                    </div>
                </div>
                </#if>
                <#if task.realEnd??>
                <div class="control-group">
                    <label class="control-label">实际结束时间</label>
                    <div class="controls">
                            <label class="control-label">${task.realEnd?string('yyyy-MM-dd')}</label>
                    </div>
                </div>
                </#if>
                <div class="form-actions">
                    <a class='btn' href="task/index">返回</a>
                </div>
            </fieldset>
        </div>
    </div>
</div>
</#macro>

<#macro script>
</#macro>
