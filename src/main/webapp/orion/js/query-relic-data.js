/**
 * 藏品信息数据
 *
 * @author liuzhu
 * @date 2013-07-29
 * @check @gaohui #5066 2013-0815
 *
 */

$(function () {
    // 导出按钮点击
    $("#exportButton").click(function () {
        var count = parseInt($("#totalCount").val());
        var exportCount = parseInt($("#exportCount").val());
        if (exportCount == count) {
            art.dialog({
                id: "property",
                fixed: true,
                title: "消息提示",
                content: "未做任何条件筛选，会导出全部藏品数据，是否继续？",
                okValue: "确定",
                ok: function () {
                    $('#myModal').modal('show');
                },
                cancelValue: "取消",
                cancel: function () {
                }
            })
        } else {
            $('#myModal').modal('show');
        }
        $("#all").removeAttr('checked');
        var noCheck = function ($checkboxes) {
            $checkboxes.each(function () {
                $(this).removeAttr('checked');
            });
        };

        var $pro = $("#selectProperty input[name='property']");
        var $pro2 = $("#selectProperty input[name='property2']");
        noCheck($pro);
        noCheck($pro2);
    });

    // 保存/下载 excel
    $("#property-submit").click(function () {
        var pro = $("#selectProperty input[name='property']:checked");
        var pro2 = $("#selectProperty input[name='property2']:checked");
        var checkPro = "";
        // TODO 建议改为 appendParams @gaohui 2013-08-14
        var appendParams = function ($checkboxes) {
            $checkboxes.each(function () {
                checkPro += $(this).attr('value') + ",";
            });
        };
        appendParams(pro);
        appendParams(pro2);
        if (checkPro == "") {
            art.dialog({
                id: "property",
                fixed: true,
                title: "消息提示",
                content: "至少选择一种属性",
                okValue: "确定",
                ok: function () {
                },
                cancelValue: "取消",
                cancel: function () {
                }
            });
            return;
        } else {
            $('#myModal').modal('hide')
        }
        var param = $("#queryRelicFrom").serialize();
        // 导出等待页面链接
        var url = "queryRelic/waiting-for-export?" + param + "&checkPro=" + checkPro;
        var basePath = $("base").attr("href");
        if (basePath) {
            url = basePath + url;
        }
        // 打开等待页面
        window.open(url, "blank", "height=400, width=400");

    });
});
