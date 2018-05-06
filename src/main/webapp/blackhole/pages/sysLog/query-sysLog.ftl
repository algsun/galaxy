<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>日志管理 - 系统管理</title>
<#include "../_common/common-css.ftl">
    <style type="text/css">
        .query-input {
        <#if !inputView>
            display: none;
        </#if>
        }

        .open-input, .close-input {
            margin-right: 74px;
            text-align: right;
            cursor: pointer;
            color: #dd4444;
        }
    </style>
</head>
<body>


<#assign currentTopPrivilege = "blackhole:log">
<#include "../_common/header.ftl">


<div id="gcontent" class="container">
<#if message!="">
    <div class="row m-t-20">
        <div id="alert" class="span12">
            <div class="alert alert-error">
                <a class="close" data-dismiss="alert">×</a>
            ${message!}
            </div>
        </div>
    </div>
<#else>
<fieldset>
    <legend>日志查询</legend>
    <div class="close-input m-t-10 m-b-10 <#if !inputView>hide</#if>" title="收起查询条件"> 收起</div>
    <div class="open-input m-t-10 m-b-10 <#if inputView>hide</#if>" title="展开查询条件"> 高级选项</div>
    <div class="row query-input">
        <div class="span12">
            <form class="well well-small" action="querySysLog.action" method="post">
                <input type="hidden" name="inputView" value="${inputView?string("true","false")}">
                <input type="hidden" name="start" value="1">

                <div class="row time-input">
                    <div class="span12">
                            <span class="span4">
                                <label class="inline-block" for="start-time">开始时间</label>
                                <input class="inline-block Wdate" id="start-time" type="text" name="startTime"
                                       onfocus="var endTime=$dp.$('end-time');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'end-time\')}'})"
                                       value="${startTime!}"/>
                            </span>
                            <span class="span4">
                                <label class="inline-block" for="end-time">结束时间</label>
                                <input class="inline-block Wdate" id="end-time" type="text" name="endTime"
                                       onfocus="WdatePicker({minDate:'#F{$dp.$D(\'start-time\')}'})"
                                       value="${endTime!}"/>
                            </span>
                            <span class="span3">
                                <input class="btn" type="submit" value="查询"/>
                            </span>
                    </div>
                </div>
                <div class="row attr-input">
                    <div class="span12">
                        <span class="span4">
                            <label class="inline-block" style="margin-left: 28px;" for="user-name">姓名</label>
                            <input id="user-name" class="inline-block" type="text" name="userName" value="${userName!}">
                        </span>
                        <span class="span4">
                            <label class="inline-block" for="operate-name">操作名称</label>
                            <input class="inline-block" id="operate-name" type="text" name="operateName"
                                   value="${operateName!}">
                        </span>
                        <span class="span4">
                            <label class="inline-block" for="sys-sever">业务系统</label>
                            <select class="inline-block" id="sys-sever" name="subsystem">
                                <option <#if subsystem==0>selected="selected"</#if> value="0"><nobr>不限制</nobr></option>
                                <#list subsystems as s>
                                    <option <#if subsystem==s.subsystemId>selected="selected"</#if>
                                            value="${s.subsystemId!}">${s.subsystemName!}</option>
                                </#list>
                            </select>
                        </span>
                        <span class="span4">
                            <label class="inline-block p-r-20 p-t-10">是否成功</label>
                            <label class="inline-block p-r-20 radio" for="success">
                                <input class="inline-block" id="success" type="radio" name="result"
                                       value="2" <#if result==2>checked="checked"</#if>>
                                是</label>
                            <label class="inline-block p-r-20 radio" for="failure">
                                <input class="inline-block" id="failure" type="radio" name="result"
                                       value="1" <#if result==1>checked="checked"</#if>>
                                否</label>
                            <label class="inline-block p-r-20 radio" for="ignore">
                                <input class="inline-block" id="ignore" type="radio" name="result"
                                       value="0" <#if result==0>checked="checked"</#if>>
                                不限制</label>
                        </span>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <#if (count>0)>
        <div class="row log-table m-t-20">
            <div class="span12">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>姓名</th>
                    <#--<th>站点</th>-->
                        <th>日志信息</th>
                        <th>业务系统</th>
                        <th>是否成功</th>
                        <th>操作时间</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list logs as log>
                        <tr>
                            <td>${log_index+1}</td>
                            <td>${log.userName!}</td>
                        <#--<td>${log.logicGroupName!}</td>-->
                            <td>
                                <label rel="popover" data-trigger="hover" data-placement="top"
                                       data-original-title="${log.logName!}"
                                       data-content="${log.logContent!}">
                                ${log.logName!}
                                </label>
                            </td>
                            <td>${blackhole.getSubSystemName(log.subsystemId!)}</td>
                            <td><#if log.logState>成功<#else>失败</#if></td>
                            <td>${log.logTime?string("yyyy-MM-dd HH:mm:ss")!}</td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </#if>
    <#include "../_common/pagging.ftl">
    <#assign  url = "querySysLog.action?startTime=${startTime!}&endTime=${endTime!}&userName=${userName!}&operateName=${operateName!}&subsystem=${subsystem!}&result=${result!}&inputView=${inputView?string('true','false')}">
    <@pagging url index count></@pagging>
</#if>
</div>
<#include "../_common/footer.ftl" />
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<#include "../_common/common-js.ftl">
<script type="text/javascript">
    /**
     * 查询条件显示动画效果
     */
    $(function () {
        var $queryInput = $(".query-input");
        var $open = $(".open-input");
        var $close = $(".close-input");
        var $inputView = $("input[name=inputView]");
        $open.click(function () {
            $queryInput.slideDown("slow");
            $inputView.val("true");
            $open.hide();
            $close.show();
        });
        $close.click(function () {
            $queryInput.slideUp("slow");
            $inputView.val("false");
            $close.hide();
            $open.show();
        });
    }(),
            /**
             * 鼠标移到日志名称上时显示日志内容
             */
            function () {
                $('[rel="popover"]').popover();
            }());
</script>

<#include "../_common/last-resources.ftl">
</body>
</html>
