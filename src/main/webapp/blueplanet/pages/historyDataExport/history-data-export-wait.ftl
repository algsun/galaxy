<#--
历史数据导出等待页面
-->
<!DOCTYPE html>
<html>
<head>

<#include "../_common/common-head.ftl">
    <title>${locale.getStr("blueplanet.location.exportData")}</title>
<#include "../_common/common-css.ftl">

</head>
<body data-server-time="${.now?long?c}">

<div class="container-fluid m-t-30" style="width: 300px;">
    <div class="row-fluid">
        <div class="span12">
        <#if (count > 0)>
            <div class="progress progress-striped active">
                <div id="progressWidth" class="bar"></div>
            </div>
            <h5 id="progess">${locale.getStr("blueplanet.location.wait")}...</h5>
        <#else>
            <h3>${locale.getStr("common.noData")}</h3>
        </#if>
        </div>
    </div>
</div>
<#include "../_common/common-js.ftl">

<#if (count > 0)>
<script type="text/javascript" src="${basePath()}/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script type="text/javascript" src="${basePath()}/webjars/stomp-websocket/2.3.3-1/stomp.min.js"></script>
<script type="text/javascript">
    //function connect() {
    var userId = ${Session.currentUser.id};
    var total = ${locationCount?c};
    var progress = 0;
    var websocketServerURL = "${websocketServerURL}";
    var url = websocketServerURL + 'data-export?timeStamp' + new Date();
    var socket = new SockJS(url);
    var stompClient = Stomp.over(socket);
    stompClient.connect({userId: userId}, function () {
        stompClient.subscribe('/user/exportProgress', function (exportProgress) {
            ++progress;
            $("#progess").text("正在导出位置点----" + exportProgress.body);
            $("#progressWidth").width(Math.ceil(100 * progress / total) + '%');
            $("#progressWidth").text(Math.ceil(100 * progress / total) + '%');
            if (exportProgress.body=="complete") {
                $("#progess").text("位置点导出完成。");
                $("#progressWidth").width("100%");
                $("#progressWidth").text("100%");
            }
        });
    });
    //}
    function exportExcel() {
        var downloadUrl = "../api/history-data-export?siteId=${siteId}&zoneId=${zoneId}&locationId=${locationId}&startTime=${startTime?string("yyyy-MM-dd HH:mm:ss")}&endTime=${endTime?string("yyyy-MM-dd HH:mm:ss")}";
        var basePath = $("base").attr("href");
        if (basePath) {
            downloadUrl = basePath + downloadUrl;
        }
        location.href = downloadUrl;
    }
    $(function () {
        //connect();
        exportExcel();
    });
</script>
</#if>

<#include "../_common/last-resources.ftl">
</body>
</html>