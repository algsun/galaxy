<#--
区域稳定性对比
@author wanggeng
@date 2013-08-06
@check 2013-08-14 zhangpeng svn:4923
-->
<#assign title="区域稳定性对比 - 数据分析">
<#-- 当前权限标识 -->
<#assign currentPrivilege = "phoenix:blueplanet:zoneStability">

<#include "/common/pages/common-tag.ftl">
<#include  "../_common/helper.ftl">

<#macro head>
<link rel="stylesheet" href="../assets/bootstrap-datepicker/1.7.0/css/bootstrap-datepicker3.min.css">
<style type="text/css">
    .cbox {
        width: 30px;
        height: 18px;
        display: inline-block;
        background-color: #CCC;
        border-radius: 5px;
        margin-right: 5px;
        cursor: pointer;
    }

    .cboxed {
        width: 30px;
        height: 18px;
        display: inline-block;
        background-color: blue;
        border-radius: 5px;
        margin-right: 5px;
    }
</style>
</#macro>

<#macro content>
<div class="row-fluid">
    <div class="span10">
        <div class="title page-header">
            <h3>区域数据分析</h3>
        </div>
    </div>
</div>
<div class="row-fluid">
    <form id="zoneAnalysisForm" class="form-inline col-xl-12" action="blueplanet/zoneAnalysis" method="post">
        <div class="form-group">
            <label>区域</label>
            <input class="form-control zone" type="text" name="zoneName" value="${zoneName!}" data-zoneId="">
            <input type="hidden" name="zoneId" value="${zoneId!}"/>
        </div>
        <input id="sensorId" type="hidden" name="sensorId" value="${sensorId!}"/>
        <#include "../_common/date-year-month-day-control-b4.ftl">
        <input type="submit" id="query" class="btn" value="查询">
    </form>
</div>

<div>
    <#if (sensors?size>0)>
        <table class="table table-bordered table-center">
            <tbody>
            <tr>
                <#list sensors as sen>
                    <#if sen_index%4 == 0>
                    </tr>
                    <tr>
                    </#if>
                    <td><span id=${(sen.sensorPhysicalid)!}class="cbox"></span>${(sen.cnName)!}（${(sen.units)!}）</td>
                </#list>
            <#--补齐table空td-->
                <#if !(sensors?size%4 == 0)>
                    <#list 1..(4-sensors?size%4) as i>
                        <td></td>
                    </#list>
                </#if>
            </tr>
            </tbody>
        </table>
    </#if>
</div>

<div class="hide">
    <div id="zoneTreeDialog" class="col-xl-4">
        <div id="zoneTree" class="ztree"></div>
        <p class="help-block m-t-10 red"></p>
    </div>
</div>
</#macro>

<#macro script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts.js"></script>
<script type="text/javascript" src="../assets/highcharts/3.0.2/highcharts-more.js"></script>
<#--<script type="text/javascript" src="../assets/my97-DatePicker/4.72/WdatePicker.js"></script>-->
<script type="text/javascript" src="../assets/bootstrap-datepicker/1.7.0/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript"
        src="../assets/bootstrap-datepicker/1.7.0/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript" src="../assets/moment/1.7.2/moment.min.js"></script>

    <@scriptTag "js/index/date-format.js"/>
    <@scriptTag "js/date-year-month-day-control-bdp.js"/>

<script type="text/javascript">
    $(function () {
        // ztree 树配置
        var setting = {
            view: {
                showLine: false
            },
            async: {
                enable: true,
                url: '../blackhole/zone/children.json',
                autoParam: ["id=zoneId"]
            }
        };

        var showDialog = function ($zoneInput, zoneTree) {
            var $help = $("#zoneTreeDialog .help-block");
            art.dialog({
                id: "zoneTreeDialog",
                title: "选择区域",
                content: $("#zoneTreeDialog")[0],
                fixed: true,
                okValue: "确定",
                ok: function () {
                    var nodes = zoneTree.getSelectedNodes();
                    if (nodes.length == 0) {
                        $help.empty().append("请选择区域");
                        return false;
                    }
                    var node = nodes[0];
                    $zoneInput.val(node.name);
                    $zoneInput.data("zoneId", node.id);
                    $zoneInput.next().val(node.id);
                },
                cancelValue: "取消",
                cancel: function () {
                    $help.empty();
                },
                button: [
                    {
                        value: "清空",
                        callback: function () {
                            $zoneInput.val('');
                            $zoneInput.next().val('');
                        }
                    }
                ]
            });
        };

        // 区域输入框获取焦点时
        $(".zone").focus(function () {
            var $this = $(this);
            $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
                // 初始化树
                $.fn.zTree.init($('#zoneTree'), setting, result);
                var zoneTree = $.fn.zTree.getZTreeObj("zoneTree");

                // 初始化弹出框
                showDialog($this, zoneTree);
            });
        });


        $("#${sensorId}").attr("class", "cboxed");

        $(".cbox").click(function () {
            $("#sensorId").val($(this).attr("id"));
            $("#zoneAnalysisForm").submit();
        });
    });
</script>

</#macro>