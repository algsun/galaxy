<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>单位库房管理 - 资产管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<#--<#assign currentTopPrivilege = "orion:stockManage">-->

<div id="gcontent" class="container m-t-50">
<#include "../_common/header.ftl">

    <div class="row m-t-10">
        <a class="go-back" style="text-decoration:none;color:#000000;float:left;margin-left: 15px;" href="institution/index" title="返回">
            <i class="mw-icon-prev"></i>
            <span style="font-size: 18px;">
            返回
            </span>
        </a>
        <p style="text-align: center;font-size: 20px;">
            单位库房管理
        </p>
    </div>

    <div class="row">
        <div class="span12">
        <#include "/common/pages/message-tooltip.ftl">
        <@messageTooltip></@messageTooltip>
        </div>
    </div>

    <table class="table table-hover">
       <#if (institutionRooms?size==0)>
            <h4>暂无数据</h4>
       <#else>
           <tr>
               <th>序号</th>
               <th>库房名称</th>
               <th>操作</th>
           </tr>
           <#list institutionRooms as institutionRoom>
               <tr>
                   <td>${institutionRoom_index+1}</td>
                   <td>${institutionRoom.roomName}</td>
                   <td>
                       <a class="btn btn-mini btn-danger" data-id="${institutionRoom.id}" data-institutionId="${institutionRoom.institutionId}" data-button="deleteLocation">删除</a>
                   </td>
               </tr>
           </#list>
       </#if>



    </table>
    <a class="btn btn-mini btn-success f-r m-b-10" href="institutionRoom/to_add?institutionRoom.institutionId=${institutionRoom.institutionId}">添加</a>
<#include "/common/pages/pagging.ftl">
</div>

<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">
<#include  "../_common/last-resources.ftl">
<script>
    $(function () {
        $("a[data-button='deleteLocation']").click(function () {
            var $this = $(this);
            var id = $this.data("id");
            var institutionId = $this.data("institutionid");
            art.dialog({
                id: "delete",
                fixed: true,
                title: "删除确认",
                content: "确定要删除吗？",
                okValue: "确定",
                ok: function () {
                    window.location.href = "institutionRoom/delete?institutionRoom.id=" + id+"&institutionRoom.institutionId="+institutionId;
                },
                cancelValue: "取消",
                cancel: function () {
                }
            })
        });

    })
</script>
</body>
</html>

