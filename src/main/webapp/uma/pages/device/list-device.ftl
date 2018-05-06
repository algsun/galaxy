<#--
  - 设备列表
  -@author li.jianfei
  -@time  2013-4-11 13:16
  -->
<!DOCTYPE HTML>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>设备管理 - 人员行为</title>

<#include "../_common/common-css.ftl">

</head>
<body>
<#--页面一级标题-->
<#assign currentTopPrivilege = "uma:device">
<#include "../_common/header.ftl">

<div id="gcontent" class="container m-t-50">

    <#-- TODO 注释掉的代码，删掉 -->
<#--&lt;#&ndash;二级菜单&ndash;&gt;-->
<#--<#include "../_common/sub-navs.ftl">-->
<#--<@subNavs "uma:opticsDVPlace:list"></@subNavs>-->


<#--过滤条件-->
    <div class="row">
        <div class="span12">

            <form class="form-inline well well-small" action="queryDevices.action" method="post">
                <label>设备类型</label>

                <select id="deviceType" name="deviceType">
                    <option value="2" <#if deviceType==2> selected="selected" </#if>>激发器</option>
                    <option value="1" <#if deviceType==1> selected="selected" </#if>>读卡器</option>
                </select>

                <span id="stateSelector" <#if deviceType==1>class="hide"</#if>>
                    <label>设备状态</label>
                    <select id="deviceState" name="deviceState">
                        <option value="0" <#if deviceState==0> selected="selected" </#if>>已激活</option>
                        <option value="1" <#if deviceState==1> selected="selected" </#if>>全部</option>
                    </select>
                </span>

                <span id="zoneSelector" <#if deviceType==1>class="hide"</#if>>
                    <label>区域选择</label>

                    <select id="zoneId" name="zoneId">
                        <option value="" selected="selected">全部</option>
                    <#list zoneList as zone>
                        <option value="${zone.zoneId}"
                            <#if zone.zoneId==zoneId!"">
                                selected="selected"
                            </#if>>
                        ${zone.zoneName}</option>
                    </#list>
                    </select>
                </span>
            </form>

        </div>
    </div>

<#-- 消息提示 -->
        <#-- TODO 消息提示，统一风格 -->
<#include "../_common/message.ftl">
<@messageTooltip></@messageTooltip>

<#--your content-->


<#--数据列表-->
<#if (pageCount>0)>
    <div class="row">
        <div class="span12">
            <table class="table">
                <thead>
                <tr>
                    <th>序号</th>
                    <#if deviceType==2>
                        <th>区域</th>
                    </#if>
                    <th>名称</th>
                    <#if deviceType==1>
                        <th>工作状态</th>
                        <th>读卡器ID</th>
                    </#if>
                    <th>编号</th>
                    <#if deviceType==2>
                        <th>最新工作时间</th>
                    </#if>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                    <#list deviceList as device>
                    <tr>
                        <td>${device_index+1}</td>

                        <#if deviceType==2>
                            <td>${device.zoneName!"(暂无)"}</td>
                        </#if>
                        <td>${device.name!}</td>
                        <#if deviceType==1>
                            <td>
                                <#if device.state>
                                    <span class="label label-success">
                                    正常
                                    </span>
                                <#else>
                                    <span class="label label-important">
                                    异常
                                    </span>
                                </#if>
                            </td>
                            <td>${device.deviceId!}</td>
                        </#if>
                        <td>${device.sn!}</td>
                        <#if deviceType==2>
                            <td>
                                <#if device.updateTime??>
                                    ${device.updateTime?string("yyyy-MM-dd HH:mm:ss")}
                                </#if>
                            </td>
                        </#if>
                        <td>${device.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                        <td>

                            <#if security.isPermitted("uma:device:update")>
                                <a href="toUpdateDevice.action?deviceId=${device.id}&currentPageIndex=${index}"
                                   class="btn btn-mini"><i class="icon-pencil"></i>编辑</a>
                            </#if>

                            <#if security.isPermitted("uma:device:enable")>
                                <#if device.enable>
                                    <a href="setEnable.action?deviceId=${device.id}&deviceType=${device.type}&deviceState=${deviceState}&enable=false"
                                       class="btn btn-mini btn-danger"><i
                                            class="icon-stop icon-white"></i>停用</a>
                                <#else>
                                    <a href="setEnable.action?deviceId=${device.id}&deviceType=${device.type}&deviceState=${deviceState}&enable=true"
                                       class="btn btn-mini btn-success"><i
                                            class="icon-play icon-white"></i>启用</a>
                                </#if>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>

        </div>
    </div>
</#if>

        <#-- TODO 分页工具，重构为一个，统一风格 -->
<#include "../_common/pagging.ftl" >
<#assign url="queryDevices.action?deviceType=${deviceType}&deviceState=${deviceState!0}&zoneId=${zoneId!}">
<@pagination url index pageCount "index"></@pagination>

<#if deviceList?size == 0>
    <h4>暂无数据</h4>
</#if>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">
<#--your js-->

<script type="text/javascript">
    $(function () {
        // 设置区域选择框的显示隐藏
        $("#deviceType").change(function () {
            var $this = $(this);
            var deviceType = $this.find("option:selected").val();
            if (deviceType == 1) {
                $("#zoneId").get(0).selectedIndex = 0;
                $("#deviceState").hide();
                $("#zoneSelector").hide();
            } else if (deviceType == 2) {
                $("#deviceState").show();
                $("#zoneSelector").show();
            }
            $("form").submit();
        });

        // 设备状态改变时重新获取数据
        $("#deviceState").change(function () {
            $("form").submit();
        });

        // 区域改变是重新获取数据
        $("#zoneSelector").change(function () {
            $("form").submit();
        });
    });

</script>
<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>
