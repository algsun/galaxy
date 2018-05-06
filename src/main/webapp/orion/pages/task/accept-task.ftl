<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>修复登记 - 资产管理</title>
<#include "../_common/common-css.ftl">
    <link href="../assets/pnotify/1.2.0/jquery.pnotify.default.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="../assets/pnotify/1.2.0/custom.css" rel="stylesheet" type="text/css"/>
    <style>
        .font-weight{
            font-weight: bold;
        }
    </style>
</head>
<body>
<#--<#assign currentTopPrivilege = "orion:stockManage">-->
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">
    <div class="row">
        <div class="span12">
            <a style="text-decoration:none;color:#000000;float:left;margin-top: 15px;margin-bottom: 15px;" href="repairRecords" title="返回">
                <i class="mw-icon-prev"></i> <span style="font-size: 18px;"> 返回 </span> </a>
            <table class="table table-bordered" style="margin-top: 10px;">
                <tr>

                    <td colspan="4" class="font-weight" style="text-align: center;">文物基本信息</td>
                </tr>

                <tr>
                    <td class="font-weight">文物名称</td>
                    <td>${relic.name}</td>
                    <td class="font-weight">文物编号</td>
                    <td>${relic.totalCode!}</td>
                </tr>

                <tr>
                    <td class="font-weight">时代</td>
                    <td>${relic.era.name}</td>
                    <td class="font-weight">级别</td>
                    <td>${relic.level.name}</td>
                </tr>

                <tr>
                    <td class="font-weight">质地</td>
                    <td>${relic.texture.name}</td>
                    <td class="font-weight">收藏单位</td>
                    <td>
                        <#if institution??>
                            ${institution.name}
                        </#if>
                    </td>
                </tr>

                <tr>
                    <td class="font-weight">来源</td>
                    <td><#if relic.relicPropertyMap.source??>${relic.relicPropertyMap.source.propertyValue!}</#if></td>
                    <td class="font-weight">库房</td>
                    <td>
                        <#if institutionRoom??>
                            ${institutionRoom.roomName}
                        </#if>
                    </td>
                </tr>

                <tr>
                    <td class="font-weight">修复人</td>
                    <td colspan="3">
                            ${repairRecord.getMainUser().getUserName()}
                    </td>
                </tr>

                <tr>
                    <td class="font-weight">协助人</td>
                    <td colspan="3">
                        <#list userNames as userName>
                            ${userName}
                        </#list>
                    </td>
                </tr>

                <tr>
                    <td class="font-weight">修复因由</td>
                    <td colspan="3">${repairRecord.repairReason.reason}</td>
                </tr>

                <tr>
                    <td class="font-weight">修复内容</td>
                    <td colspan="3">${repairRecord.repairInfo}</td>
                </tr>

                <tr>
                    <td class="font-weight">提取时间</td>
                    <td>${repairRecord.extractDate?string("yyyy-MM-dd")}</td>
                    <td class="font-weight">归还时间</td>
                    <td>${(repairRecord.returnDate?string("yyyy-MM-dd"))!}</td>
                </tr>

                <tr>
                    <td colspan="4" class="font-weight" style="text-align: center;background-color: #FFFFF0">
                        <button class="btn btn-small btn-primary" type="button" id="accept_btn" data-id="${repairRecord.id}">确认接受</button>
                    </td>
                </tr>

            </table>
        </div>
    </div>
</div>
<#include  "../_common/footer.ftl">
<#include  "../_common/common-js.ftl">
<#include  "../_common/last-resources.ftl">
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/js/jquery.validate.js"></script>
<script type="text/javascript" src="../assets/jquery-validation/1.10.0/localization/messages_zh.js"></script>
<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>
<script type="text/javascript">
    $(function(){
        $("#accept_btn").click(function(){
            var $this = $(this);
            var id = $this.data("id");
            window.location.href = "handleTask/do_accept_task?repairRecord.id="+id;
        });
    });
</script>
</body>
</html>

