<#import _pagePath as _page>

<!DOCTYPE html>
<html>
<head>
<#include "../_common/common-head.ftl">
    <title>${_page.title}</title>
<#include "../_common/common-css.ftl">
<@_page.head/>

</head>
<body>

<#include "../_common/header.ftl">


<div id="gcontent" class="container m-t-50">
    <div class="row">

        <div class="span3">
        <#if _page.currentPrivilege??>
            <#assign currentPrivilege = _page.currentPrivilege>
        </#if>
            <#-- 左侧导航 -->
            <#include "../_common/navs.ftl">
        </div>

    <#include "../_common/message.ftl">
    <@messageTooltip></@messageTooltip>

        <div class="span9">
            <div class="pull-right" style="margin-top: 20px">
                <a id="download" role="button" class="btn btn-primary btn-xs">导出图片</a>
            </div>
            <div id="pic">
            <@_page.content/>
            </div>
        </div>
    </div>
</div>

<#include "../_common/footer.ftl">
<#include "../_common/common-js.ftl">

<@_page.script/>
<script type="text/javascript">
    $(function () {
        $(document).on('click', '.subsystemHead', function () {
            art.dialog({
                title: '正在努力加载!'
            });
        });

        $("#download").click(function () {
            var $pic = $("#pic");
            var svgElements = $pic.find('svg');
//            console.log(svgElements.length);
            svgElements.each(function () {
                var canvas, xml;
                canvas = document.createElement("canvas");
                canvas.className = "screenShotTempCanvas";

                //convert SVG into a XML string
                xml = (new XMLSerializer()).serializeToString(this);

                // Removing the name space as IE throws an error
                xml = xml.replace(/xmlns=\"http:\/\/www\.w3\.org\/2000\/svg\"/, '');

                //draw the SVG onto a canvas
                canvg(canvas, xml);
                $(canvas).insertAfter(this);

                //hide the SVG element
                this.className = "tempHide";
                $(this).hide();
            });

            html2canvas($pic[0]).then(function (canvas) {
                svgElements.each(function () {
                    $(this).show();
                });
                return Canvas2Image.saveAsPNG(canvas);
            });
        });
    });
</script>

<#include "../_common/last-resources.ftl">

</body>
</html>