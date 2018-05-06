<#--
  首页
-->

<#assign isBaseSite = Session["currentLogicGroup"].site??>
<#if mode=="mode0">
    <#include "mode/index_mode0.ftl">
<#elseif mode=="mode1">
    <#include "mode/index_mode1.ftl">
<#elseif mode=="mode2">
    <#include "mode/index_mode2.ftl">
<#elseif mode=="mode3">
    <#if isBaseSite>
        <#include "mode/index_mode3.ftl">
    <#else>
        <#include "mode/index_mode0.ftl">
    </#if>
<#elseif mode=="mode4">
    <#if isBaseSite>
        <#include "mode/index_mode4.ftl">
    <#else>
        <#include "mode/index_mode0.ftl">
    </#if>
<#elseif mode=="mode5">
    <#if isBaseSite>
        <#include "mode/index_mode5.ftl">
    <#else>
        <#include "mode/index_mode0.ftl">
    </#if>
<#elseif mode=="mode6">
    <#if isBaseSite>
        <#include "mode/index_mode6.ftl">
    <#else>
        <#include "mode/index_mode0.ftl">
    </#if>
<#elseif mode=="mode7">
    <#if isBaseSite>
        <#--<#include "mode/index_mode7.ftl">-->
        <#include "mode/index_mode0.ftl">
    <#else>
        <#include "mode/index_mode_center_7.ftl">
    </#if>
<#elseif mode=="mode8">
    <#if isBaseSite>
        <#include "mode/index_mode8.ftl">
    <#else>
        <#if appLocale=="bjwwj">
            <#include "mode/index_mode_center_7.ftl">
        <#else>
            <#include "mode/index_mode8.ftl">
        </#if>
    </#if>
</#if>


<script type="text/javascript">
    $(function () {
        $(document).on('click', '.subsystemNail', function () {
//            art.dialog({
//                title: '正在努力加载!',
//                content:'随便敲'
//            });
            MSG.showMsg("正在努力加载，请耐心等待...");
        });
        $(document).on('click', '.subsystemHead', function () {
//            art.dialog({
//                title: '正在努力加载!'
//            });
            MSG.showMsg("正在努力加载，请耐心等待...");
        });
    });
    var MSG = {
        closeable: false,
        showMsg: function (title) {
            var MSG = this;
            MSG.dialog = art.dialog({
                id: "info",
                title: title,
                cancel: false
            });
        },
        closeDialog: function () {
            var MSG = this;
            if (MSG.dialog) {
                MSG.dialog.close();
            }
        }
    };
</script>