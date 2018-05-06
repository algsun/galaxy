<#assign title="成果展示 - 文献资料">
<#assign currentTopPrivilege = "saturn:literature">
<#-- 当前权限标识 -->

<#macro head>
<style>
    .literature {
    }

    .literature .search {
        padding: 0 28px 16px;
    }

    .literature .search span {
        color: #9c0000;
        font-size: 16px;
        margin-right: 10px;
    }

    .literature .search select {
        width: 90px;
        height: 34px;
        border: 1px solid #e8e7e3;
        margin-right: 8px;
        padding-left: 6px;
        font-family: "微软雅黑";
    }

    .literature .search input {
        width: 270px;
        height: 32px;
        border: 1px solid #e8e7e3;
        margin-right: 10px;
        padding: 0 10px;
    }

    .literature .search input.button {
        width: 68px;
        height: 38px;
        line-height: 34px;
        color: #fff;
        font-size: 16px;
        text-align: center;
        background: url(../images/bg2.jpg) center top repeat-x;
        font-family: "微软雅黑";
        cursor: pointer;
    }

    .literature .list {
        padding: 20px 28px;
        border-top: 1px dashed #e8e7e3;
        font-size: 15px;
    }

    .literature .list .title {
        font-size: 18px;
        color: #9c0000;
        line-height: 20px;
    }

    .literature .list p {
        text-indent: 2em;
        line-height: 25px;
        margin: 9px 0;
    }

    .literature .list .fujia {
        line-height: 20px;
        color: #8d8d8d;
    }

    .page {
        padding: 0px;
        margin: 0px;
        font-size: 14px;
        width: 904px;
        text-align: right;
    }
</style>
</#macro>

<#macro content>
<div class="container m-t-20">
    <div class="row">
        <div class="span12">
            <form class="form-inline well well-small" action="literatures" method="get">
                <label class="control-label" for="inputEmail">标题</label>
                <input type="text" id="title" name="title" value="${Request.title!}">
                <label class="control-label" for="keywords">关键字</label>
                <input type="text" id="keywords" name="keywords" value="${Request.keywords!}">
                <button class="btn" type="submit">查询</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <div class="literature">
                <#list literatures as literature>
                    <div class="list">
                        <div class="title">${literature.title!}</div>
                        <p>
                        ${literature.summary!}
                        </p>

                        <div class="fujia">期刊：${literature.magazine!} &nbsp; 期别：${literature.periodical!} &nbsp;
                            作者：${literature.author!} &nbsp; 发表时间：${literature.publicDate?string("yyyy-MM-dd")}</div>
                    </div>
                </#list>
            </div>
            <#include "_common/pagging.ftl" >
            <#assign url="literatures?title=${Request.title!}&keywords=${Request.keywords!}">
            <@pagination url Request.index!0 pageCount "index"></@pagination>
        </div>
    </div>
</div>
</#macro>

<#macro script>
</#macro>