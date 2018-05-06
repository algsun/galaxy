/**
 * 历史数据
 *
 * @author gaohui
 * @date 2013-02-26
 * @dependency jquery
 */

$(function () {
    // 导出按钮点击
    $("#exportButton").click(function () {
        var $dialog = $("#historyDataExportDialog");
        var deviceId = $(this).attr("data-device-id");
        var param = $("#historyDataForm").serialize();
        // 导出等待页面链接
        var url = "device/" + deviceId + "/waiting-for-export?" + param ;
        var basePath = $("base").attr("href");
        if(basePath){ url = basePath + url; }
        // 打开等待页面
        window.open(url, "blank", "height=400, width=400");
    });
});
