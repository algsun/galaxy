<#--
外展状态记录

@author xu.yuexi
@time  13-10-22
-->



<#assign title="外展状态 - 外展管理">
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

<div id="gcontent" class="container m-t-5">
    <fieldset>
        <legend>
            <a class="go-back" href="queryExhibitions"><i class="mw-icon-prev"></i>外展列表</a>
        </legend>
    </fieldset>

<div class="row">
    <#include "exhibitionState-helper.ftl">
    <input id="exhibitionId" type="hidden" value="${exhibitionId}"/>
    <#if exhibitionList?size==0>
        <div class="m-l-30 m-b-30">
            <button id="btnStart" class="btn btn-info btn-mini">开始外展</button>
        </div>
    </#if>
    <#if exhibitionList?size gt 0>
        <div class="span12">

            <table class="table table-bordered table-striped table-center">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>外展状态</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>操作人</th>
                    <th>操作</th>
                    <th>下一目的地</th>
                </tr>
                </thead>
                <tbody>
                    <#list exhibitionList as exhibition>
                    <tr>
                        <td>${exhibition_index + 1}</td>
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
                            <#if  exhibition.state==3 >
                                <#if exhibition.pathPO??>
                                    陈展中
                                <#else>
                                    到达目的地
                                </#if>
                            </#if>
                            <#if  exhibition.state==4 >
                                已结束
                            </#if></td>
                        <td name="startDate">
                            <#if exhibition.beginTime??>
                    ${(exhibition.beginTime)?string("MM-dd HH:mm")}
                    </#if>
                        </td>
                        <td>
                            <#if exhibition.endTime??>
                            ${(exhibition.endTime)?string("MM-dd HH:mm")}
                        </#if>
                        </td>
                        <td>${exhibition.user.userName}</td>
                        <td>
                            <#if exhibitionVO.state==exhibition.state&&exhibitionVO.state!=4>
                                <#if (exhibition_index + 1)==exhibitionList?size>
                                    <#--<#if exhibitionVO.state!=4>-->
                                        <#if exhibition.state==1>
                                            <#if exhibitionList?size ==1>
                                            <#--<button class="btn btn-info btn-mini" onclick="location.href='updateExhibitionState/exhibition/${exhibitionId}/exhibitionState/${exhibition.state+1}'">开始运输</button>-->
                                                <button id="beginTrs" class="btn btn-info btn-mini">开始运输</button>
                                            </#if>
                                        </#if>
                                        <#if exhibition.state==2>
                                            <button id="endTrs" class="btn btn-info btn-mini">结束运输</button>
                                        </#if>
                                        <#if exhibition.state==3>
                                            <#if exhibition.pathPO??>
                                                <button id="beginTrs" class="btn btn-info btn-mini">开始运输</button>
                                            <#else>
                                                <button id="btnEnd" class="btn btn-info btn-mini">结束外展</button>
                                            </#if>
                                        </#if>
                                    </#if>
                                </#if>
                            <#--</#if>-->
                            <#if exhibition_index!=0>
                                <#if exhibition.pathPO??>
                                    <a class="btn btn-info btn-mini"
                                       href="routeHistory/exhibition/${exhibition.exhibitionId}/timeIntervalIndex/${exhibition_index-1}">历史数据</a>
                                    <#if exhibition.state==3>
                                        <a class="btn btn-info btn-mini"
                                           href="defaultImageView/exhibition/${exhibition.exhibitionId}/timeIntervalIndex/${exhibition_index-1}">图片浏览</a>
                                    </#if>
                                </#if>
                            </#if>
                        </td>
                        <td>
                            <#if exhibition.pathPO??>
                            ${exhibition.pathPO.destinationName!"无"}
                            </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>

    </#if>

</#macro>
<#macro script>
    <script type="text/javascript">
        $(function () {
            // 初始化外展ID
            var exhibitionId = $("#exhibitionId").val();
            $("#btnStart").click(function () {
                art.dialog({
                    title: '提示信息',
                    content: "是否确认开始外展？",
                    okValue: "确定",
                    cancelValue: '取消',
                    ok: function () {
                        window.location.href = "updateExhibitionState/exhibition/" + exhibitionId + "/exhibitionState/1";
                    },
                    cancel: function () {
                    }
                });
            });
        });

        $(function () {
            // 初始化外展ID
            var exhibitionId = $("#exhibitionId").val();

            $("#btnEnd").click(function () {
                art.dialog({
                    title: '提示信息',
                    content: "是否确认结束外展？",
                    okValue: "确定",
                    cancelValue: '取消',
                    ok: function () {

                        window.location.href = "updateExhibitionState/exhibition/" + exhibitionId + "/exhibitionState/4";
                    },
                    cancel: function () {
                    }
                });
            });
            $("#endTrs").click(function () {
                $('#endTrs').attr('disabled', 'true');
                window.location.href = "updateExhibitionState/exhibition/" + exhibitionId + "/exhibitionState/3";
            });

            $("#beginTrs").click(function () {
                $('#beginTrs').attr('disabled', 'disabled');
                window.location.href = "updateExhibitionState/exhibition/" + exhibitionId + "/exhibitionState/2";
            });
        });
    </script>
</#macro>
