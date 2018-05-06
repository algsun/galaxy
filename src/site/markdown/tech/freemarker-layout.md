# freemarker layout 技术

为了减少页面复杂性，减少页面之间的冗余，借鉴 rails 和 jekyll 的 layout 技术，我们找出了一种适应 struts + freemarker 的
layout 的解决办法。

示例如下：

layout.ftl 布局页面


    <#import "page.ftl" as _page>

    <!DOCTYPE html>
    <html>
        <head>
            <title>${_page.title}</title>

            <@_page.head/>
        </head>

        <body>

        <@_page.content/>


        <@_page.script/>

        </body>
    </html>

page.ftl 内容页面

    <#assign title = "我是标题">

    <#marcro head>
    <style type="text/css">
    /* 我是样式 */
    </style>
    </#marcro>

    <#marcro content>
    <p>我是内容</p>
    </#marcro>

    <#marcro script>
    <script type="text/javascript">
    // 我是 js 脚本
    </script>
    </#marcro>
