<#--
新闻详情

@author gaohui
@date 2013-05-10
-->

<#assign title = locale.getStr("blackhole.login.newsDetailsTitle")>
<#macro head>
    <#if scope != 1>
    <!-- 导航栏 -->
        <#assign currentTopPrivilege = "blackhole:home">
        <#include "../_common/header.ftl">
    </#if>
    <#-- 响应式，为了更好的手机客户端体验 -->
    <link rel="stylesheet" href="../assets/bootstrap/2.3.1/css/bootstrap-responsive.min.css"/>
</#macro>

<#macro content>
<div class="row">
    <div class="span7">
        <div class="page-header m-b-5">
            <h3>${post.title}</h3>
        </div>
        <div><i class="icon-time"></i> ${post.createDate?string("yyyy年MM月dd日 HH:mm")}</div>

        <p class="m-t-20">
        ${post.content}
        </p>
    </div>

    <div class="span5">
        <div style="margin-top: 80px;" >
        <a href="listPosts.action?scope=${scope}"><i class="icon-arrow-left"></i> ${locale.getStr("blackhole.login.newsList")}</a>
        </div>

        <ul class="m-b-20" style="min-height: 250px;">
            <#list posts as post>
            <li class="m-t-10"><a href="viewPost.action?id=${post.id}&scope=${scope!1}">${post.title}</a></li>
            </#list>
        </ul>

        <script type="text/javascript"
                src="http://www.thinkpage.cn/reader/widget.aspx?an=0&mc=10&sd=0&sde=0&ss=0&feed=http%3A//news.baidu.com/ns%3Fword%3D%25CE%25C4%25CE%25EF%25B1%25A3%25BB%25A4%26tn%3Dnewsrss%26sr%3D0%26cl%3D2%26rn%3D20%26ct%3D0"></script>
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
