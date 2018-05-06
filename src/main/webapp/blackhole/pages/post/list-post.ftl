<#--
新闻查询

@author gaohui
@date 2013-05-08
-->

<!DOCTYPE html>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>新闻查询 - 系统管理</title>
<#include "../_common/common-css.ftl">

</head>
<body>

<#include "/common/pages/common-tag.ftl">

<!-- 导航栏 -->
<#--当前一级权限ID-->
<#assign currentTopPrivilege = "blackhole:post">
<#include "../_common/header.ftl" >

<div id="gcontent" class="container">

<#include "../_common/sub-navs.ftl">
<@subNavs "blackhole:post:query"></@subNavs>

<#include "/common/pages/message-tooltip.ftl" >
<@messageTooltip/>

    <div class="row">
        <div class="span12">
            <form class="form-inline well well-small" action="queryPost.action">
                <label>类型</label>
                <select class="input-medium" name="scope">
                    <option value="-1" <@selected -1, scope/>>全部</option>
                    <option value="1" <@selected 1, scope/>>公共</option>
                    <option value="2" <@selected 2, scope/>>内部</option>
                </select>

                <button class="btn" type="submit">查询</button>
            </form>


            <table class="table">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>标题</th>
                    <th>类型</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list posts as post>
                <tr>
                    <td>${post_index + 1}</td>
                    <td>${post.title}</td>
                    <td><@post_scope post.scope/></td>
                    <td>${post.createDate?string("yyyy-MM-dd HH:mm")}</td>
                    <td>
                        <a class="btn btn-mini" href="updatePost.action?id=${post.id}"><i class="icon-pencil"></i> 编辑</a>
                        <button class="btn btn-mini btn-danger" data-role="delete" data-post-id="${post.id}"><i class="icon-trash icon-white"></i> 删除</button>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>

            <#include "/common/pages/pagging.ftl">
            <@pagination "queryPost.action?scope=${scope}", page, pageCount/>
        </div>
    </div>
</div>

<#macro post_scope postType>
    <#if postType == 1>
    公共
    <#elseif postType == 2>
    内部
    </#if>
</#macro>

<!-- 页面底部 -->
<#include "../_common/footer.ftl" >

<#--公共JS-->
<#include "../_common/common-js.ftl">

<script type="text/javascript">
    $(function(){
        $("button[data-role='delete']").click(function(){
            if(confirm("是否删除此新闻？")){
                var id = $(this).attr("data-post-id");
                location.href = "deletePost.action?id=" + id;
            }
        });
    });
</script>

<#--其他公共资源-->
<#include "../_common/last-resources.ftl">
</body>
</html>