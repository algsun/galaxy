/**
 * 历史数据
 *
 * @author xuyuexi
 * @date 2017-07-8
 * @dependency jquery
 */

$(function () {
    $("#submitBtn").click(function () {
        var startTime = $("#start-time").val();
        var endTime = $("#end-time").val();
        if (startTime.length < 1) {
            art.dialog({
                id: "info",
                title: message.tips,
                content: message.selectStartTime
            });
            return;
        }
        if (endTime.length < 1) {
            art.dialog({
                id: "info",
                title: message.tips,
                content: message.selectEndTime
            });
            return;
        }
        $("#historyDataForm").submit();
    });
    // 导出按钮点击
    $("#exportButton").click(function () {
        var locationId = $(this).attr("data-location-id");
        var param = $("#historyDataForm").serialize();
        // 导出等待页面链接
        var url = "location/" + locationId + "/waiting-for-export?" + param;
        var basePath = $("base").attr("href");
        if (basePath) {
            url = basePath + url;
        }
        // 打开等待页面
        window.open(url, "blank", "height=400, width=400");
    });
});
