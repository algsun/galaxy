<#--
新闻列表

@author gaohui
@date 2013-05-10
-->

<#assign title = locale.getStr("blackhole.login.newsTitle")>
<#macro head>
<#if scope != 1>
<!-- 导航栏 -->
    <#assign currentTopPrivilege = "blackhole:home">
    <#include "../_common/header.ftl">
</#if>
</#macro>

<#macro content>
<div class="row">
    <div class="span12">
        <h3>${locale.getStr("blackhole.login.newsList")}</h3>
    </div>
</div>

<div class="row">
    <div class="span12">
        <table class="table">
            <thead>
            <tr>
                <th>${locale.getStr("common.number")}</th>
                <th>${locale.getStr("blueplanet.dataCenter.title")}</th>
                <th>${locale.getStr("blueplanet.dataCenter.createTime")}</th>
                <th>${locale.getStr("proxima.common.type")}</th>
            </tr>
            </thead>
            <tbody>
                <#list posts as post>
                <tr>
                    <td>${post_index + 1}</td>
                    <td><a href="viewPost.action?id=${post.id}&scope=${scope!1}">${post.title}</a></td>
                    <td>${post.createDate?string("yyyy-MM-dd HH:mm")}</td>
                    <td><@post_scope post.scope/></td>
                </tr>
                </#list>
            </tbody>
        </table>

        <#include "/common/pages/pagging.ftl">
        <@pagination "listPosts.action?scope=${scope}", page, pageCount/>
    </div>
</div>
</#macro>

<#macro script>

</#macro>

<#macro post_scope postType>
    <#if postType == 1>
    ${locale.getStr("blackhole.login.public")}
    <#elseif postType == 2>
    ${locale.getStr("blackhole.login.internal")}
    </#if>
</#macro>
