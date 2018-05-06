<#--
  -
  -@author Wang yunlong
  -@time  13-3-22  上午10:40
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${locale.getStr("proxima.ftp.query.title")} - ${locale.getStr("proxima.common.systemName")}</title>

<#include "../_common/common-css.ftl">

</head>
<body>
<#--页面以及标题-->
<#assign currentTopPrivilege = "proxima:ftp">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

<#--二级菜单-->
<#include "../_common/sub-navs.ftl">
<@subNavs "proxima:ftp:list"></@subNavs>
<#-- 消息提示 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--your content-->
    <div class="row">
        <div class="span12">
            <div class="ftp-list">
            <#if ftpProfiles?size<1>
                <h3>${locale.getStr("common.noData")}</h3>
            <#else>
                <table class="table table-striped table-center">
                    <thead>
                    <tr>
                        <th>${locale.getStr("blueplanet.controlPanel.name")}</th>
                        <th>${locale.getStr("proxima.ftp.host")}</th>
                        <th>${locale.getStr("proxima.ftp.port")}</th>
                        <th>${locale.getStr("proxima.ftp.userName")}</th>
                        <th>${locale.getStr("common.operating")}</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list ftpProfiles as ftpProfile>
                        <tr>
                            <td>${ftpProfile.name}</td>
                            <td>${ftpProfile.host}</td>
                            <td>${ftpProfile.port?c}</td>
                            <td>${ftpProfile.username}</td>
                            <td>
                                <#if (!security.isPermitted("proxima:ftp:update")&&(!security.isPermitted("proxima:ftp:delete")))>
                                    <span class="muted"></span>
                                </#if>
                                <#if security.isPermitted("proxima:ftp:update")>
                                    <a class="btn btn-success btn-mini" href="toUpdateFTP.action?id=${ftpProfile.id}"
                                       title="修改">
                                        <i class="icon-pencil icon-white"></i>${locale.getStr("common.update")}
                                    </a>
                                </#if>
                                <#if security.isPermitted("proxima:ftp:delete")>
                                    <button class="btn btn-danger btn-mini delete-ftp" title="删除"
                                            ftp-id="${ftpProfile.id}">
                                        <i class="icon-trash icon-white"></i>${locale.getStr("common.delete")}
                                    </button>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </#if>
            </div>
        </div>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#--your js-->
<script type="text/javascript">
    $(function () {
        $(".delete-ftp").click(function () {
            var $this = $(this);
            art.dialog({
                id: 'info',
                title: message.tips,
                content: message.sureToDelete,
                okValue: message.ok,
                ok: function () {
                    window.location.href = "deleteFTP.action?id=" + $this.attr("ftp-id");
                },
                cancelValue: message.cancel,
                cancel: function () {
                }
            });
            return false;
        });
    });
</script>

<#include "../_common/last-resources.ftl">
</body>
</html>
