<#--
外展管理，外展列表

@author li.jianfei
@date 2013-09-27
-->
<#assign title="外展列表 - 外展管理">
<#-- 当前权限标识 -->

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
<style type="text/css">
    .table thead {
        background-color: #ececec;
    }
</style>
</#macro>

<#macro content>
<div>
    <form class="well well-small" action="queryExhibitions" method="post" id="queryRelicFrom">
        <input type="hidden" name="tempStartDate" value="${startDate?string('yyyy-MM-dd HH:mm')}">
        <input type="hidden" name="tempEndDate" value="${endDate?string('yyyy-MM-dd HH:mm')}">
          <span class="p-5 inline-block">
                        <label class="inline-block" for="era" style="width:56px;text-align: center">外展状态</label>
                        <select id="selectId" name="state" style="width: 184px">
                            <@optionsTag [[5, "全部"], [0, "准备中"], [1, "准备就绪"],[2,"运输中"],[3,"陈展中/到达目的地"],[4,"已结束"]], state />
                        </select>
              <span class="p-5 inline-block ">
                         <label class="inline-block" for="startDate">开始时间</label>
                        <input id="startDate" type="text"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 00:00'})"
                               name="startDate"
                            <#if tempStartDate??>
                               value="${tempStartDate?string('yyyy-MM-dd HH:mm')}"
                            <#else>
                               value="${startDate?string('yyyy-MM-dd HH:mm')}"
                            </#if>
                                />
                     </span>
                        <span class="inline-block p-5 ">
                          <label class="inline-block" for="endDate">结束时间</label>
                        <input id="endDate" type="text"
                               onclick="WdatePicker({maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd 23:59'})"
                               name="endDate"
                            <#if tempEndDate??>
                               value="${tempEndDate?string('yyyy-MM-dd HH:mm')}"
                            <#else>
                               value="${endDate?string('yyyy-MM-dd HH:mm')}"
                            </#if>
                                />
                        <button id="submit-btn" class="btn m-b-10" type="submit">查询</button>
                    </span>
           </span>
    </form>

</div>
    <#if exhibitionList?? && (exhibitionList?size >0)>
    <table class="table table-bordered table-striped table-center">
        <thead>
        <tr>
            <th>序号</th>
            <th>申请单号</th>
            <th>申请人</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>当前状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
            <#list exhibitionList as exhibition>
            <tr>
                <td>${exhibition_index+1}</td>
                <td>${exhibition.outEventId}</td>
                <td>${exhibition.applicant!}
                <td>${(exhibition.beginTime!exhibition.estimatedBeginTime)?string("yyyy-MM-dd")}</td>
            <td>
                <#if  exhibition.state==0 >
                        ${(exhibition.endTime!exhibition.estimatedEndTime)?string("yyyy-MM-dd")}</td>
                <#else>
                    <#if exhibition.endTime??>
                    ${exhibition.endTime?string("yyyy-MM-dd")}
                    </#if>
                </#if>

                <td>
                    <#if  exhibition.state==0 >
                        准备中
                    </#if>
                    <#if  exhibition.state==1 >
                        准备就绪
                    </#if>
                    <#if  exhibition.state==2 >
                        运输中
                    </#if>
                    <#if  exhibition.state==3>
                        <#if exhibition.onDisplay>
                            陈展中
                        <#else>
                            到达目的地
                        </#if>
                    </#if>
                    <#if  exhibition.state==4 >
                        已结束
                    </#if>
                </td>
                <td>
                    <a class="btn btn-info btn-mini"
                       href="queryExhibitionStateList/exhibition/${exhibition.exhibitionId}">外展状态</a>

                    <#if exhibition.state != 0>
                        <a class="btn btn-info btn-mini"
                           href="routeRealtime/exhibition/${exhibition.exhibitionId}">实时监测</a>
                    </#if>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>
        <#include "/common/pages/pagging.ftl" >
        <#assign url = "queryExhibitions?state=${state}&startDate=${startDate?string('yyyy-MM-dd HH:mm:ss')}&endDate=${endDate?string('yyyy-MM-dd HH:mm:ss')}">
        <@pagination url, page, pageSum,"page"/>
    <#else>
    <div>
        <h4>暂无数据</h4>
    </div>
    </#if>

</#macro>

<#macro script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.timers-1.2.js"></script>
<script type="text/javascript" src="../assets/galleryView-3.0-dev/js/jquery.easing.1.3.js"></script>
    <@scriptTag "js/2datepicker-form-validation.js"/>
<script type="text/javascript">
    $(function () {
        $("#startDate").change(function () {
            var startDate = $("#startDate").attr('value');
            $('#tempStartDate').attr('value', startDate);
        });
        $("#endDate").change(function () {
            var endDate = $("#endDate").attr('value');
            $('#tempEndDate').attr('value', endDate);
        });
    });
</script>
</#macro>
