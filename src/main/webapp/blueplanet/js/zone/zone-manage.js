/**
 *
 * 区域管理
 *
 * @author: Wang yunlong
 * @time: 13-2-26 下午1:38
 */
$(function () {
    var BluePlanet = App("blueplanet");
    //当前区域Id
    var currentZoneId = $("#parent-zone-id").text();
    //移动鼠标在某个区域上显示其操作按钮
    $(document).on("mouseenter", ".zone-child", function () {
        $(this).children(".zone-child-view").children(".zone-util").show();
    });
    $(document).on("mouseleave", ".zone-child", function () {
        $(this).children(".zone-child-view").children(".zone-util").hide();
    });
    //修改按钮点击事件处理
    $(document).on("click", ".zone-alter", function () {
        var $this = $(this);
        var $zoneDetail = $this.parent().parent().siblings(".zone-info-alter");
        $zoneDetail.slideDown("slow");
        $zoneDetail.find(".zone-base-info-view").hide();
        $zoneDetail.find(".zone-base-info-input").show();
        $zoneDetail.find(".zone-alter-submit").show();
    });
    //详细信息按钮点击事件处理
    $(document).on("click", ".zone-info", function () {
        var $this = $(this);
        var $zoneDetail = $this.parent().parent().siblings(".zone-info-alter");
        $zoneDetail.slideDown("slow");
        $zoneDetail.find(".zone-base-info-input").hide();
        $zoneDetail.find(".zone-alter-submit").hide();
        $zoneDetail.find(".zone-base-info-view").show();
    });
    //添加区域确定
    $(document).on("click", ".new-ok", function () {
        var $this = $(this);
        var $newValue = $this.siblings("input");
        if ($.trim($newValue.val()).length < 1) {
            $newValue.val("新建区域");
        }
        var zoneName = $newValue.val();
        $.post("zone/add.json", {zoneName: zoneName, parentZoneId: currentZoneId}, function (result) {
            if (result.success) {
                $newValue.hide();
                $this.siblings("a").show().children("span").text(zoneName);
                $this.siblings("a").attr("href", "zone/" + result.zone.zoneId + "/manage");
                var $zoneInfoAlter = $this.parent().parent().siblings(".zone-info-alter");
                $zoneInfoAlter.find(".zone-base-info-view").text(zoneName);
                $zoneInfoAlter.find(".zone-base-info-input").val(zoneName);
                $zoneInfoAlter.find(".zone-base-info-input-id").val(result.zone.zoneId);
                $this.parent().parent().siblings(".zone-id").text(result.zone.zoneId);
                $zoneInfoAlter.find("input[name='zoneId']").val(result.zone.zoneId);
                var utilHtml = "<div class='zone-util'>" +
                    "<div class='zone-info' title='详细'><i class='icon-info-sign'></i></div>" +
                    "<div class='zone-alter' title='编辑'><i class='icon-pencil'></i></div>" +
                    "<div class='zone-move' title='移动'><i class='icon-move'></i></div>" +
                    "<div class='zone-delete' title='删除'><i class='icon-remove'></i></div>" +
                    "</div>";
                $this.parent().parent().append(utilHtml);
                $this.siblings("span.new-cancel").remove();
                $this.remove();
                $(".no-zone-msg").remove();
                BluePlanet.deviceTree.reload();
                window.pnotify("添加成功", "success");
            } else {
                $this.parent().parent().parent().remove();
                window.pnotify("添加失败," + result.msg, "warn");
            }
        });
    });
    //添加区域   取消
    $(document).on("click", ".new-cancel", function () {
        $(this).parent().parent().parent().remove();
    });
    //删除区域
    $(document).on("click", ".zone-delete", function () {
        var $this = $(this);
        var zoneId = $(this).parent().parent().siblings(".zone-id").text();
        art.dialog({
            id: "delete",
            fixed: true,
            title: "删除确认",
            content: "确定删除此区域？",
            okValue: "确定",
            ok: function () {
                $.post("zone/" + zoneId + "/delete", function (result) {
                    if (result.success) {
                        $this.parent().parent().parent().remove();
                        BluePlanet.deviceTree.reload();
                        window.pnotify("删除成功", "success");
                    } else {
                        window.pnotify("删除失败," + result.msg, "warn");
                    }
                });
            },
            cancelValue: "取消",
            cancel: function () {
            }
        });
    });
    //添加区域点击事件
    $("#current-zone-add-child").click(function () {
        var $newZone = $(".zone-template").clone().removeClass("zone-template").addClass("zone-row");
        var $newZoneView = $newZone.children(".zone-child-view").children(".zone-name");
        var $newZoneInput = $newZoneView.children("input");
        $newZoneView.children("a").hide();
        $(".zone-children").children(":eq(1)").after($newZone);
        $newZoneInput.show().focus();
    });
    //隐藏区域详细信息
    $(document).on("click", ".zone-info-alter-close", function () {
        $(this).parent().slideUp();
    });
    //区域移动按钮点击事件
    $(document).on("click", ".zone-move", function () {
        var $zoneDom = $(this).parent().parent().parent();
        var zoneId = $(this).parent().parent().siblings(".zone-id").text();
        var newParentId = "";
        var $dialogDiv = $("#dialog");
        // 初始化 zTree, 加载顶级区域
        $.getJSON("../blackhole/zone/children.json", {zoneId: ""}, function (result) {
            var setting = {
                view: {
                    showLine: false
                },
                async: {
                    enable: true,
                    url: '../blackhole/zone/children.json',
                    autoParam: ["id=zoneId"]
                },
                callback: {
                    onClick: function (event, treeId, treeNode) {
                        newParentId = treeNode.id;
                    }
                }
            };
            $.fn.zTree.init($('#tree'), setting, result);
            // 初始化弹出框
            art.dialog({
                id: "treeDialog",
                title: "选择上级区域",
                content: $dialogDiv[0],
                fixed: true,
                lock: true,
                okValue: "确定",
                ok: function () {
                    // 必须选择一个站点
                    var canMove = true;
                    if (newParentId == "") {
                        $dialogDiv.find(".help-block").empty().append("请选择区域");
                        canMove = false;
                    }
                    if (newParentId == currentZoneId) {
                        $dialogDiv.find(".help-block").empty().append("已经属于该区域");
                        canMove = false;
                    }
                    if (newParentId == zoneId) {
                        $dialogDiv.find(".help-block").empty().append("不能以自己作为上级");
                        canMove = false;
                    }
                    if (canMove) {
                        $.getJSON("zone/manage/contains.json", {zoneId: zoneId, newParentId: newParentId}, function (result) {
                            if (result) {
                                moveZone(zoneId, newParentId, $zoneDom);
                                $dialogDiv.find(".help-block").empty();
                                art.dialog.get("treeDialog").close();
                            } else {
                                $dialogDiv.find(".help-block").empty()
                                    .append("不能移动到下级区域");
                            }
                        });
                    }
                    return false;
                },
                cancelValue: "取消",
                cancel: function () {
                    $dialogDiv.find(".help-block").empty();
                },

                // 添加为顶级区域按钮
                button: [
                    {
                        value: '设为顶级区域',
                        callback: function () {
                            moveZone(zoneId, "", $zoneDom);
                        }
                    }
                ]
            });
        });
    });
    //区域移动
    var moveZone = function (zoneId, newParentId, $zoneDom) {
        $.post("zone/move.json", {zoneId: zoneId, newParentId: newParentId}, function (result) {
            if (result.success) {
                window.pnotify("修改成功", "success");
                $zoneDom.remove();
                if ($(".zone-row").length < 1) {
                    $(".zone-children").append("<div class='no-zone-msg'>当前区域没有下级区域</div>")
                }
                BluePlanet.deviceTree.reload();
            } else {
                window.pnotify("修改失败," + result.msg, "warn");
            }
        });
    };
    //区域更新
    $(document).on("click", ".zone-alter-submit>input", function () {
        var $this = $(this);
        var $parent = $this.parent().parent();
        var pImage = $parent.find("input[name='image']").val();
        var pZoneName = $parent.find("input[name='zoneName']").val();
        var pZoneId = $parent.find("input[name='zoneId']").val();
        $.post("zone/update.json", {image: pImage, zoneName: pZoneName, zoneId: pZoneId}, function (result) {
            if (result.success) {
                var $zoneDetail = $this.parent().parent().parent().siblings(".zone-child-view");
                $zoneDetail.find("a").children("span").text(result.zone.zoneName);
                var $zoneInfo = $this.parent().siblings(".zone-base-info");
                $zoneInfo.find(".zone-name-input").val(result.zone.zoneName);
                $zoneInfo.find(".zone-name-label").text(result.zone.zoneName);
                var $zonePlanImageView = $this.parent().siblings(".zone-image");
                $zonePlanImageView.find("img").attr("src", result.resourcesPath + "/" + result.zone.planImage);
                $this.parent().parent().find(".zone-base-info-input").hide();
                $this.parent().hide();
                $this.parent().parent().find(".zone-base-info-view").show();
                window.pnotify("更新成功", "success");
                BluePlanet.deviceTree.reload();
            } else {
                window.pnotify("更新失败," + result.msg, "warn");
            }
        });
    });
    $(document).on("change", ".image-input", function () {
        var $this = $(this);
        var image = $this.val();
        if (image == null) {
            return;
        }
        if (image != "" && !/.(gif|jpg|jpeg|png)$/.test(image.toLowerCase())) {
            return;
        }
        $this.parent().ajaxSubmit({
            dataType: 'json',
            success: function (result) {
                if (result != null) {
                    var imageUrl = result.imagePath + "?_=" + new Date().getTime();
                    $this.parent().parent().siblings(".zone-image-view").html("<img src='" + imageUrl + "'>");
                    $this.parent().parent().parent().siblings("input[name='image']").val(result.imageFileName);
                }
            }
        });
    });
});
