## 如何生成站点(site)

执行 maven 命令

    mvn site

如果测试不通过会导致生成站点失败，如果只是想看站点，可以跳过测试(会影响测试覆盖率报告)

    mvn site -DskipTests=true

生成的站点会输出在 `target/site/`  目录下, 点击 `index.html` 即可打开此站点。

## 添加内容

自定义内容目前在 `src/site/markdown` 目录下，采用 [markdown](http://daringfireball.net/projects/markdown/) 语法撰写。
例如在 `src/site/markdown` 下添加 `my-content.md`, 生成站点时会在 `target/site/` 下生成同名的 `my-content.html` 文件。

添加新的内容后，还需要在 `src/site/site.xml` 中添加链接。

    <body>
    ...
        <menu name="一级分类">
            ...
            <item name="我的内容" href="my-content.html"/>
        </menu>
    </body>

item 也可嵌套。

    <menu name="一级分类">
        <item name="二级分类" href="....">
            ...
            <item name="我的内容" href="my-content.html"/>
        </item>
    </menu>

注意，链接尽量使用相对路径。
