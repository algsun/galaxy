<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>单位管理 - 资产管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>
<#--<#assign currentTopPrivilege = "orion:stockManage">-->

<div id="gcontent" class="container m-t-50">
<#include "../_common/header.ftl">

    <div class="row m-t-10">
        <p style="text-align: center;font-size: 20px;">
            单位管理
        <p>
    </div>

    <div class="row">
        <div class="span12">
        <#include "/common/pages/message-tooltip.ftl">
        <@messageTooltip></@messageTooltip>
        </div>
    </div>

    <table class="table table-hover">
        <tr>
            <th>序号</th>
            <th>名称</th>
            <th>所在地</th>
            <th>通讯地址</th>
            <th>邮编</th>
            <th>资质证书</th>
            <th>代号</th>
            <th>单位类型</th>
            <th>操作</th>
        </tr>

    <#list institutions as institution>
        <tr>
            <td>${institution_index+1}</td>
            <td>${institution.name}</td>
            <td>${institution.seat}</td>
            <td>${institution.mailing}</td>
            <td>${institution.zipcode?c}</td>
            <td>${institution.qualification}</td>
            <td>${institution.code}</td>
            <td>
                <#if institution.institutionType == 0>
                    设计单位
                <#elseif institution.institutionType==1>
                    收藏单位
                <#elseif institution.institutionType==2>
                    修复单位
                </#if>
            </td>
            <td>
                <#if security.isPermitted("orion:institution:addRoom")>
                    <#if institution.institutionType==1>
                        <a href="institutionRoom/index?institutionRoom.institutionId=${institution.id}" class="btn btn-mini btn-info">库房</a>
                    </#if>
                </#if>

                <#if security.isPermitted("orion:institution:update")>
                    <a href="institution/to_update?institution.id=${institution.id}" class="btn btn-mini btn-primary">编辑</a>
                </#if>

                <#if security.isPermitted("orion:institution:delete")>
                    <a class="btn btn-mini btn-danger" data-id="${institution.id}" data-button="deleteLocation">删除</a>
                </#if>
            </td>
        </tr>
    </#list>
    </table>
    <#if security.isPermitted("orion:institution:add")>
        <a class="btn btn-mini btn-success f-r m-b-10" href="institution/to_add">添加</a>
    </#if>
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
            var info = "确认要删除吗？";
            var hasRoom = false;
            $.get("institution/findInstitutionRoom",{'institution.id':id},function(result){
                if(result>0){
                   info = "该单位下有库房，是否删除？";
                   hasRoom = true;
                }
                art.dialog({
                    id: "delete",
                    fixed: true,
                    title: "删除确认",
                    content: info,
                    okValue: "确定",
                    ok: function () {
                        window.location.href = "institution/delete?institution.id=" + id+"&institution.hasRoom="+hasRoom;
                    },
                    cancelValue: "取消",
                    cancel: function () {
                    }
                })
            });

        });

    })
</script>
</body>
</html>

