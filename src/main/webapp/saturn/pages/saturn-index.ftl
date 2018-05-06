<#assign title="成果展示 - 首页">
<#assign currentTopPrivilege="saturn:index">
<#-- 当前权限标识 -->

<#macro head>
<link rel="stylesheet" type="text/css" href="css/jquery.jslides2.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="css/css.css"/>
</#macro>

<#macro content>
<div class="m-t-20"></div>
<div class="content">
    <div class="baogao">
        <div class="item"><a href="tasks">更多>></a></div>
        <table border="0" cellpadding="0" cellspacing="0" class="baogaolist">
            <tr>
                <th>编号</th>
                <th>任务标题</th>
                <th>执行人</th>
                <th>预计起始时间</th>
                <th>完成状态</th>
            </tr>
            <#if tasks??>
                <#list tasks as task>
                    <tr <#if task_index%2==0>class="tr1"</#if>>
                        <td>${task_index+1}</td>
                        <td class="td1">${task.title!}</td>
                        <td>${task.userName!}</td>
                        <td>${task.predictStart?string("yyyy-MM-dd")}</td>
                        <td
                            <#switch task.state>
                                <#case 0>
                                        class="red">待审核
                                    <#break>
                                <#case 1>
                                    >进行中
                                    <#break>
                                <#case 2>
                                    class="green">已完成
                                    <#break>
                                <#case 3>
                                    >已作废
                                    <#break>
                                <#default>
                                    >未知
                            </#switch>
                        </td>
                    </tr>
                </#list>
            </#if>
        </table>
    </div>
    <div class="indexwrap">
        <ul id="slides">
            <li style="background:url('images/tu2.jpg') no-repeat center top"></li>
            <li style="background:url('images/tu3.jpg') no-repeat center top"></li>
            <li style="background:url('images/tu4.jpg') no-repeat center top"></li>
        </ul>
    </div>
    <div class="kuaijie">
        <div class="item"></div>
        <ul class="show">
            <a href="#">
                <li class="l1 m-l-20">环境分析</li>
            </a>
            <a href="result">
                <li class="l2 m-l-20"
                ">成果展示</li>
            </a>
            <a href="literatures">
                <li class="l3 m-l-20"
                ">文献资料</li>
            </a>
            <a href="statistics/repair">
                <li class="l4 m-l-20"
                ">数据统计</li>
            </a>
            <a href="tasks">
                <li class="l5 m-l-20">任务公告</li>
            </a>
        </ul>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="js/jquery.jslides2.js"></script>
</#macro>